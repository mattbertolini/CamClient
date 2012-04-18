package com.mattbertolini.camclient.net.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Matt Bertolini
 */
public interface HttpPayload {
    String getContentType();
    InputStream getPayload();
    void writeTo(OutputStream outputStream) throws IOException;
}
