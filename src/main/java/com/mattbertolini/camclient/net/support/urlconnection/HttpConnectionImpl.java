package com.mattbertolini.camclient.net.support.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionImpl implements HttpConnection {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HTTP = "HTTP";
    private static final String HTTPS = "HTTPS";

    @Override
    public HttpResponse submitRequest(HttpRequest request) {
        if(request == null) {
            throw new IllegalArgumentException("Request argument cannot be null.");
        }
        Url url = request.getUrl();
        if(url == null) {
            throw new IllegalStateException("");
        }
        String protocol = url.getProtocol();
        if(!HTTP.equalsIgnoreCase(protocol) && !HTTPS.equalsIgnoreCase(protocol)) {
            //
            throw new IllegalStateException("");
        }
        InputStream responseStream = null;
        HttpResponse response = null;
        try {
            Proxy proxy = request.getProxy();
            HttpURLConnection conn;
            if(proxy != null) {
                conn = url.openConnection(proxy);
            } else {
                conn = url.openConnection();
            }
            conn.setRequestMethod(request.getMethod().toString().toUpperCase(Locale.ROOT));

            // Headers
            Map<String, List<String>> requestHeaders = request.getHeaders();
            if(requestHeaders != null && !requestHeaders.isEmpty()) {
                for(Map.Entry<String, List<String>> header : requestHeaders.entrySet()) {
                    conn.setRequestProperty(header.getKey(), this.buildHeaderValueString(header.getValue()));
                }
            }

            HttpPayload requestPayload = request.getPayload();
            if(requestPayload != null) {
                conn.setDoOutput(true);
                // We override any content type that has already been set by the content type in the payload object.
                String contentTypeStr = this.buildContentTypeHeader(requestPayload.getContentType(), requestPayload.getCharacterEncoding());
                conn.setRequestProperty(CONTENT_TYPE, contentTypeStr);
                OutputStream outputStream = conn.getOutputStream();
                requestPayload.writeTo(outputStream);
                outputStream.close();
            }
            conn.connect(); // Send request

            // Response handling
            int responseCode = conn.getResponseCode();

            if(responseCode >= HttpURLConnection.HTTP_BAD_REQUEST || responseCode == -1) {
                responseStream = conn.getErrorStream();
            } else {
                responseStream = conn.getInputStream();
            }
            String contentTypeHeader = conn.getHeaderField(CONTENT_TYPE);
            String responseCharacterEncoding = this.getCharacterEncoding(contentTypeHeader);
            String responseContentType = this.getContentType(contentTypeHeader);
            HttpPayload responsePayload = new InputStreamPayload(responseStream, responseContentType, responseCharacterEncoding);
            String responseMessage = conn.getResponseMessage();
            Map<String,List<String>> responseHeaders = conn.getHeaderFields();
            response = new HttpResponseImpl(responseCode, responseMessage, responsePayload, responseHeaders);
            conn.disconnect();
        } catch (IOException e) {
            // We are only closing the response input stream if there is an exception because the input stream is given
            // to the response object in a non-exception scenario. It is the user's responsibility to close the stream
            // in that case.
            if(responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e1) {
                    //
                }
            }
            // throw new HttpConnectionException();
        }
        return response;
    }

    /**
     * Builds a comma separated string of values for a multi-valued header.
     *
     * @param input The list of values to concatenate into a comma separated string.
     * @return A string suitable for use in an HTTP header.
     */
    private String buildHeaderValueString(List<String> input) {
        StringBuilder retVal = new StringBuilder();
        boolean first = true;
        for(String str : input) {
            if(first) {
                first = false;
            } else {
                retVal.append(',');
            }
            retVal.append(str);
        }
        return retVal.toString();
    }

    /**
     * Extracts the content type from the <code>Content-Type</code> header.
     *
     * @param contentTypeHeader The string contents of the <code>Content-Type</code> header.
     * @return A string containing the content type.
     */
    private String getContentType(String contentTypeHeader) {
        if(contentTypeHeader.contains(";")) {
            return contentTypeHeader.substring(0, contentTypeHeader.indexOf(";"));
        } else {
            return contentTypeHeader;
        }
    }

    /**
     * Extracts the character encoding from the <code>Content-Type</code> header if it is present.
     *
     * @param contentTypeHeader The string contents of the <code>Content-Type</code> header.
     * @return A string containing the character set or null if no character encoding is specified.
     */
    private String getCharacterEncoding(String contentTypeHeader) {
        if(!contentTypeHeader.contains(";")) {
            return null;
        }
        String charset = null;
        String[] split = contentTypeHeader.replace(" ", "").split(";");
        for(String s : split) {
            if(s.toLowerCase(Locale.ROOT).startsWith("charset=")) {
                charset = s.split("=", 2)[1];
            }
        }
        return charset;
    }

    /**
     * Builds an appropriate string for the <code>Content-Type</code> header based on the given content type and
     * character encoding.
     *
     * @param contentType The content type. This field is required.
     * @param characterEncoding The character encoding or character set. This field is optional.
     * @return A string representing the final content type.
     */
    private String buildContentTypeHeader(String contentType, String characterEncoding) {
        if(characterEncoding != null) {
            return contentType + "; " + characterEncoding;
        }
        return contentType;
    }
}
