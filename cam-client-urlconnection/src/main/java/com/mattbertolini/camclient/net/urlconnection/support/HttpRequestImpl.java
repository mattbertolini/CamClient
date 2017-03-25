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

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matt Bertolini
 */
public final class HttpRequestImpl implements HttpRequest {
    private Url url;
    private Proxy proxy;
    private HttpPayload payload;
    private Method method;
    private MultivaluedMap<String, String> headers;

    public HttpRequestImpl() {
        this.headers = new MultivaluedHashMap<String, String>();
    }

    @Override
    public HttpPayload getPayload() {
        return this.payload;
    }

    @Override
    public void setPayload(HttpPayload payload) {
        this.payload = payload;
    }

    @Override
    public void addHeader(String name, String value) {
        this.headers.add(name, value);
    }

    @Override
    public void addHeader(String name, List<String> values) {
        this.headers.addAll(name, values);
    }

    @Override
    public void addHeader(String name, String... values) {
        this.headers.addAll(name, values);
    }

    @Override
    public boolean containsHeader(String name) {
        return this.headers.containsKey(name);
    }

    @Override
    public String getHeader(String name) {
        return this.headers.getFirst(name);
    }

    @Override
    public MultivaluedMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public void setHeader(String name, String value) {
        this.headers.putSingle(name, value);
    }

    @Override
    public void setHeader(String name, List<String> values) {
        this.headers.put(name, values);
    }

    @Override
    public void setHeader(String name, String... values) {
        this.headers.put(name, Arrays.asList(values));
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public Url getUrl() {
        return this.url;
    }

    @Override
    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public Proxy getProxy() {
        return this.proxy;
    }

    @Override
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
}
