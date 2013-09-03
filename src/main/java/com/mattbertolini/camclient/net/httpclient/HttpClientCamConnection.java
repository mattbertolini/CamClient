/*
 * Copyright (c) 2012, Matthew Bertolini
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

package com.mattbertolini.camclient.net.httpclient;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.net.AbstractCamConnection;
import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URL;

/**
 * @author Matt Bertolini
 */
public class HttpClientCamConnection extends AbstractCamConnection implements CamConnection {
    private URL url;
    private CamCredentials credentials;
    private HttpClient httpClient;
    private CamRequestAdapter<HttpPost> requestAdapter;

    public HttpClientCamConnection() {
        //
    }

    public HttpClientCamConnection(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public CamResponse submitRequest(CamRequest request) {
        //
        if(this.httpClient == null) {
            throw new IllegalStateException("HTTP client is null.");
        }
        HttpPost httpRequest = this.requestAdapter.buildRequest(this.url, this.credentials, request);
        String userAgent = this.getUserAgent();
        Header userAgentHeader = new BasicHeader("User-Agent", userAgent);
        httpRequest.addHeader(userAgentHeader);
        try {
            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
        } catch (IOException e) {
            //
        }
        return null;
    }

    public void setCredentials(CamCredentials credentials) {
        this.credentials = credentials;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
