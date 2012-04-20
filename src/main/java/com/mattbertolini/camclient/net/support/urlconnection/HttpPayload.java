package com.mattbertolini.camclient.net.support.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Matt Bertolini
 */
public interface HttpPayload {
    String getContentType();
    String getCharacterEncoding();
    InputStream getInputStream();
    void writeTo(OutputStream outputStream) throws IOException;
}
