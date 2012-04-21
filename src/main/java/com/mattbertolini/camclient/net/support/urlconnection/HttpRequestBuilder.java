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

package com.mattbertolini.camclient.net.support.urlconnection;

import java.net.Proxy;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpRequestBuilder {
    private HttpPayload payload;
    private RequestMethod method;
    private Map<String, List<String>> headers;
    private Url url;
    private Proxy proxy;

    public HttpRequestBuilder() {
        this.headers = new LinkedHashMap<String, List<String>>();
    }

    public HttpRequestBuilder(HttpRequest request) {
        this.payload = request.getPayload();
        this.method = request.getMethod();
        this.headers = request.getHeaders();
        this.url = request.getUrl();
        this.proxy = request.getProxy();
    }

    public HttpRequestBuilder setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder addHeader(String name, String value) {
        this.addHeader(name, Collections.singletonList(value));
        return this;
    }

    public HttpRequestBuilder addHeader(String name, List<String> values) {
        this.headers.put(name, values);
        return this;
    }

    public HttpRequestBuilder setPayload(HttpPayload content) {
        this.payload = content;
        return this;
    }

    public HttpRequestBuilder setUrl(Url url) {
        this.url = url;
        return this;
    }

    public HttpRequestBuilder setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public HttpRequest build() {
        return new HttpRequestImpl(this.url, this.method, this.headers, this.payload, this.proxy);
    }
}
