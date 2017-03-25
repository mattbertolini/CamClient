/*
 * Copyright (c) 2013, Matthew Bertolini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of CamClient nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mattbertolini.camclient.net.urlconnection.support;

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
    public HttpResponse executeRequest(HttpRequest request) throws IOException {
        if(request == null) {
            throw new IllegalArgumentException("Request argument cannot be null.");
        }
        Url url = request.getUrl();
        if(url == null) {
            throw new IllegalStateException("URL cannot be null.");
        }
        String protocol = url.getProtocol();
        if(!HTTP.equalsIgnoreCase(protocol) && !HTTPS.equalsIgnoreCase(protocol)) {
            throw new IllegalStateException("Only HTTP and HTTPS protocols are supported.");
        }
        InputStream responseStream = null;
        HttpResponse response;
        try {
            Proxy proxy = request.getProxy();
            HttpURLConnection conn;
            if(proxy != null) {
                conn = url.openConnection(proxy);
            } else {
                conn = url.openConnection();
            }
            Method method = request.getMethod();
            conn.setRequestMethod(method.toString().toUpperCase(Locale.ROOT));

            // Headers
            MultivaluedMap<String, String> requestHeaders = request.getHeaders();
            if(requestHeaders != null && !requestHeaders.isEmpty()) {
                for(Map.Entry<String, List<String>> header : requestHeaders.entrySet()) {
                    conn.setRequestProperty(header.getKey(), this.buildHeaderValueString(header.getValue()));
                }
            }

            HttpPayload requestPayload = request.getPayload();
            if((Method.POST == method || Method.PUT == method) && requestPayload != null) {
                conn.setDoOutput(true);
                // We override any content type that has already been set with the content type in the payload object.
                ContentType contentType = requestPayload.getContentType();
                conn.setRequestProperty(CONTENT_TYPE, contentType.toString());
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
            ContentType responseContentType = ContentType.valueOf(contentTypeHeader);
            HttpPayload responsePayload = new InputStreamPayload(responseStream, responseContentType);
            String responseMessage = conn.getResponseMessage();
            MultivaluedMap<String, String> responseHeaders = new MultivaluedHashMap<String, String>(conn.getHeaderFields());
            response = new HttpResponseImpl(responseCode, responseMessage, responsePayload, responseHeaders);
        } catch (IOException e) {
            // We are only closing the response input stream if there is an exception because the input stream is given
            // to the response object in a non-exception scenario. It is the user's responsibility to close the stream
            // in that case.
            if(responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e1) {
                    // Do nothing
                }
            }
            // Re-throw IOException after closing input stream.
            throw e;
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
}
