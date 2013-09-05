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

package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.CamResponseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @param <Request> The type to translate the CamRequest into.
 * @param <Response> The type to be translated into a CamResponse.
 * @author Matt Bertolini
 */
public abstract class AbstractCamConnection<Request, Response> implements CamConnection {
    protected static final String USER_AGENT = "User-Agent";
    private static final String COMMENT_BEGIN_PATTERN = "\\s*<!--\\s*";
    private static final String COMMENT_END_PATTERN = "\\s*-->\\s*";
    private static final String NAME_VALUE_DELIMITER_PATTERN = "[=,]";
    private static final String ERROR_KEY = "error";
    private static final String COUNT_KEY = "count";
    private static final String EMPTY_STRING = "";
    private static final String ZERO = "0";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private final URI uri;
    private final CamCredentials credentials;
    private UserAgentBuilder userAgentBuilder;

    public AbstractCamConnection(URI uri, CamCredentials credentials) {
        this.uri = uri;
        this.credentials = credentials;
        this.userAgentBuilder = new DefaultUserAgentBuilder();
    }

    /**
     * Translate the given CamRequest into the defined request type.
     * @param camRequest The CamRequest object to translate.
     * @return The request type to use in the connection.
     */
    public abstract Request buildRequest(CamRequest camRequest);

    /**
     * Translate the response from the connection into a CamResponse.
     * @param httpResponse The defined response type to translate.
     * @return The CamResponse object.
     */
    public abstract CamResponse buildResponse(Response httpResponse);

    /**
     * Execute the request with the underlying HTTP connection.
     * @param request The HTTP request to execute
     * @return The HTTP response.
     */
    public abstract Response submitRequest(Request request);

    public String getUserAgent() {
        return this.userAgentBuilder.buildUserAgentString();
    }

    @Override
    public CamResponse executeRequest(CamRequest camRequest) {
        Request request = this.buildRequest(camRequest);
        Response response = this.submitRequest(request);
        return this.buildResponse(response);
    }

    /**
     * This method will close the given input stream.
     *
     * @param responseBody The input stream containing the response body.
     * @param encoding The input stream's corresponding character encoding.
     * @return A CamResponse object populated from the response body.
     */
    protected CamResponse parseResponse(InputStream responseBody, String encoding) {
        if(responseBody == null) {
            throw new IllegalArgumentException("Response body stream is null.");
        }
        Scanner scanner = new Scanner(responseBody, encoding);
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append(LINE_SEPARATOR);
        }
        scanner.close();
        try {
            responseBody.close();
        } catch (IOException e) {
            // TODO: finish
        }

        boolean error = false;
        String errorText = null;
        String responseString = sb.toString();
        String[] rows = responseString.split(COMMENT_END_PATTERN);
        if(rows == null || rows.length == 0) {
            // TODO: throw exception
            throw new RuntimeException();
        }
        List<Map<String, String>> data = new ArrayList<Map<String, String>>(rows.length);
        for(String row : rows) {
            Map<String, String> rowData = new HashMap<String, String>();
            row = row.replaceAll(COMMENT_BEGIN_PATTERN, EMPTY_STRING);
            String[] nvp = row.split(NAME_VALUE_DELIMITER_PATTERN);
            if(nvp.length % 2 != 0) {
                // TODO: throw exception
                throw new RuntimeException();
            }

            for(int i = 0; i < nvp.length; i += 2) {
                rowData.put(nvp[i].trim().toLowerCase(), nvp[i + 1].trim());
            }

            if(rowData.containsKey(ERROR_KEY)
                    && !rowData.get(ERROR_KEY).equals(ZERO)) {
                error = true;
                errorText = "CAM Error - " + rowData.get(ERROR_KEY);
                data = Collections.emptyList();
                break;
            } else if(rowData.containsKey(COUNT_KEY)) {
                if(rowData.get(COUNT_KEY).equals(ZERO)) {
                    data = Collections.emptyList();
                    break;
                }
            } else {
                // Since the error value is zero (success), we don't need it
                // anymore. Not adding it to the final data collection.
                if(!rowData.containsKey(ERROR_KEY)
                        && !rowData.containsKey(COUNT_KEY)) {
                    data.add(rowData);
                }
            }
        }

        return new CamResponseImpl(responseString, data, error, errorText);
    }

    public URI getUri() {
        return this.uri;
    }

    public CamCredentials getCredentials() {
        return this.credentials;
    }
}
