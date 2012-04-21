package com.mattbertolini.camclient.net.support.urlconnection;

import java.io.IOException;

/**
 * @author Matt Bertolini
 */
public interface HttpConnection {
    HttpResponse submitRequest(HttpRequest request) throws IOException;
}
