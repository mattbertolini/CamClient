package com.mattbertolini.camclient.net.support.urlconnection;

import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public final class HttpRequestImpl implements HttpRequest {
    private final HttpPayload payload;
    private final RequestMethod method;
    private final Map<String, List<String>> headers;
    private final Url url;
    private final Proxy proxy;

    public HttpRequestImpl(Url url, RequestMethod method, Map<String, List<String>> headers, HttpPayload payload, Proxy proxy) {
        this.url = url;
        this.method = method;
        this.headers = new HashMap<String, List<String>>(headers);
        this.payload = payload;
        this.proxy = proxy;
    }

    @Override
    public HttpPayload getPayload() {
        return this.payload;
    }

    @Override
    public boolean containsHeader(String name) {
        return this.headers.containsKey(name);
    }

    @Override
    public String getHeader(String name) {
        return null; // TODO
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return new HashMap<String, List<String>>(this.headers);
    }

    @Override
    public RequestMethod getMethod() {
        return this.method;
    }

    @Override
    public Url getUrl() {
        return this.url;
    }

    @Override
    public Proxy getProxy() {
        return this.proxy;
    }
}
