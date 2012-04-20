package com.mattbertolini.camclient.net.support.urlconnection;

import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public interface HttpRequest {
    HttpPayload getPayload();
    boolean containsHeader(String name);
    String getHeader(String name);
    Map<String, List<String>> getHeaders();
    RequestMethod getMethod();
    Url getUrl();
    Proxy getProxy();
}
