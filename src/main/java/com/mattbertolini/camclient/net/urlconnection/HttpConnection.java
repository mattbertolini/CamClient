package com.mattbertolini.camclient.net.urlconnection;

/**
 * @author Matt Bertolini
 */
public interface HttpConnection {
    HttpResponse submitRequest(HttpRequest request);
}
