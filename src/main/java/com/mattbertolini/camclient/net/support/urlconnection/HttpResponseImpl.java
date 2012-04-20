package com.mattbertolini.camclient.net.support.urlconnection;

import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public final class HttpResponseImpl implements HttpResponse {
    private final int code;
    private final String message;
    private final HttpPayload payload;
    private final Map<String, List<String>> headers;

    public HttpResponseImpl(int code, String message, HttpPayload payload, Map<String, List<String>> headers) {
        this.code = code;
        this.message = message;
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public HttpPayload getPayload() {
        return this.payload;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.code;
    }

    @Override
    public String getStatusMessage() {
        return this.message;
    }
}
