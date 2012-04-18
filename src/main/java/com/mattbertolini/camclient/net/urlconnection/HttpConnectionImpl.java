package com.mattbertolini.camclient.net.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionImpl implements HttpConnection {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String USER_AGENT = "User-Agent";
    private static final String HTTP = "HTTP";
    private static final String HTTPS = "HTTPS";

    @Override
    public HttpResponse submitRequest(HttpRequest request) {
        if(request == null) {
            throw new IllegalArgumentException("Request argument cannot be null.");
        }
        URL url = request.getUrl();
        if(url == null) {
            throw new IllegalStateException("");
        }
        String protocol = url.getProtocol();
        if(!HTTP.equalsIgnoreCase(protocol) && !HTTPS.equalsIgnoreCase(protocol)) {
            //
            throw new IllegalStateException("");
        }
        HttpResponse response = null;
        try {
            Proxy proxy = request.getProxy();
            HttpURLConnection conn;
            if(proxy != null) {
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setRequestMethod(request.getMethod().toString().toUpperCase(Locale.ROOT));
            // This will be overridden if the request object has a user agent header set.
            conn.setRequestProperty(USER_AGENT, "");

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
                conn.setRequestProperty(CONTENT_TYPE, requestPayload.getContentType());
                OutputStream outputStream = conn.getOutputStream();
                requestPayload.writeTo(outputStream);
                outputStream.close();
            }

            // Response handling
            int responseCode = conn.getResponseCode();
            InputStream responseStream;
            if(responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                responseStream = conn.getErrorStream();
            } else {
                responseStream = conn.getInputStream();
            }
            HttpPayload responsePayload = new InputStreamPayload(responseStream, conn.getHeaderField(CONTENT_TYPE));
            String responseMessage = conn.getResponseMessage();
            Map<String,List<String>> responseHeaders = conn.getHeaderFields();

            response = new HttpResponseImpl(responseCode, responseMessage, responsePayload, responseHeaders);

            conn.disconnect();

        } catch (ProtocolException e) {
            //
        } catch (IOException e) {
            //
        }
        return response;
    }

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
}
