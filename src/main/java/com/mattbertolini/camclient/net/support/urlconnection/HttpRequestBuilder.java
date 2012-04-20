package com.mattbertolini.camclient.net.support.urlconnection;

import java.net.Proxy;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpRequestBuilder {
    private HttpPayload content;
    private RequestMethod method;
    private Map<String, List<String>> headers;
    private Url url;
    private Proxy proxy;

    public HttpRequestBuilder() {
        this.headers = new LinkedHashMap<String, List<String>>();
    }

    public HttpRequestBuilder(HttpRequest request) {
        //
        this.content = request.getPayload();
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
        this.content = content;
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
        return new HttpRequestImpl(this.url, this.method, this.headers, this.content, this.proxy);
    }
}
