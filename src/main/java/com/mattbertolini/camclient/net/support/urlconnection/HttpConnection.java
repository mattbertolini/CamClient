package com.mattbertolini.camclient.net.support.urlconnection;

/**
 * @author Matt Bertolini
 */
public interface HttpConnection {
    HttpResponse submitRequest(HttpRequest request);
}
