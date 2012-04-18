package com.mattbertolini.camclient.net.urlconnection;

import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public interface HttpRequest {
    HttpPayload getPayload();
    String getHeader(String name);
    Map<String, List<String>> getHeaders();
    RequestMethod getMethod();
    URL getUrl();
    Proxy getProxy();
}
