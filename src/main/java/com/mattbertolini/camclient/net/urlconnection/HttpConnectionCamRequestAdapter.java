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

package com.mattbertolini.camclient.net.urlconnection;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.Parameter;
import com.mattbertolini.camclient.net.support.urlconnection.Url;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.net.support.urlconnection.HttpRequest;
import com.mattbertolini.camclient.net.support.urlconnection.HttpRequestBuilder;
import com.mattbertolini.camclient.net.support.urlconnection.RequestMethod;
import com.mattbertolini.camclient.net.support.urlconnection.UrlEncodedFormPayload;

import java.net.URL;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionCamRequestAdapter implements CamRequestAdapter<HttpRequest> {
    @Override
    public HttpRequest buildRequest(URL url, CamCredentials credentials, CamRequest camRequest) {
        //
        if(url == null) {
            throw new IllegalArgumentException();
        }
        if(credentials == null) {
            throw new IllegalArgumentException();
        }
        if(camRequest == null) {
            throw new IllegalArgumentException();
        }

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        requestBuilder.setUrl(new Url(url));
        requestBuilder.setMethod(RequestMethod.POST);

        UrlEncodedFormPayload postData = new UrlEncodedFormPayload();
        postData.addParameter(RequestParameter.OPERATION.getName(), camRequest.getOperation().getName());
        Map<Parameter, String> parameters = camRequest.getParameters();
        for(Map.Entry<Parameter, String> entry : parameters.entrySet()) {
            postData.addParameter(entry.getKey().getName(), entry.getValue());
        }
        postData.addParameter(RequestParameter.ADMIN_USERNAME.getName(), credentials.getUsername());
        postData.addParameter(RequestParameter.ADMIN_PASSWORD.getName(), credentials.getPassword());
        requestBuilder.setPayload(postData);
        return requestBuilder.build();
    }
}
