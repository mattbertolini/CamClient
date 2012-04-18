package com.mattbertolini.camclient.net.urlconnection;

import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public final class HttpRequestImpl implements HttpRequest {
    private final HttpPayload payload;
    private final RequestMethod method;
    private final Map<String, List<String>> headers;
    private final URL url;
    private final Proxy proxy;

    public HttpRequestImpl(URL url, RequestMethod method, Map<String, List<String>> headers, HttpPayload payload, Proxy proxy) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.payload = payload;
        this.proxy = proxy;
    }

    @Override
    public HttpPayload getPayload() {
        return this.payload;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    @Override
    public RequestMethod getMethod() {
        return this.method;
    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public Proxy getProxy() {
        return this.proxy;
    }
}
