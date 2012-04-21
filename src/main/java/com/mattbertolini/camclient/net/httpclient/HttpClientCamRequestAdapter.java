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
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.Parameter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.RequestParameter;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpClientCamRequestAdapter implements CamRequestAdapter<HttpPost> {
    @Override
    public HttpPost buildRequest(URL url, CamCredentials credentials, CamRequest camRequest) {
        HttpPost request = null;
        try {
            request = new HttpPost(url.toURI());
            List<NameValuePair> formParams = new LinkedList<NameValuePair>();
            formParams.add(new BasicNameValuePair(RequestParameter.OPERATION.getName(), camRequest.getOperation().getName()));
            Map<Parameter, String> parameters = camRequest.getParameters();
            for(Map.Entry<Parameter, String> parameter : parameters.entrySet()) {
                formParams.add(new BasicNameValuePair(parameter.getKey().getName(), parameter.getValue()));
            }
            formParams.add(new BasicNameValuePair(RequestParameter.ADMIN_USERNAME.getName(), credentials.getUsername()));
            formParams.add(new BasicNameValuePair(RequestParameter.ADMIN_PASSWORD.getName(), credentials.getPassword()));
            UrlEncodedFormEntity postData = new UrlEncodedFormEntity(formParams, "UTF-8");
            request.setEntity(postData);
        } catch (URISyntaxException e) {
            //
        } catch (UnsupportedEncodingException e) {
            //
        }
        return request;
    }
}
