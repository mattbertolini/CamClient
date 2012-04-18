package com.mattbertolini.camclient.net.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * @author Matt Bertolini
 */
public class InputStreamPayload implements HttpPayload {
    private InputStream payload;
    private String contentType;

    public InputStreamPayload(InputStream payload, String contentType) {
        this.payload = payload;
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public InputStream getPayload() {
        return this.payload;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Scanner scanner = new Scanner(this.payload);
        while(scanner.hasNextByte()) {
            outputStream.write(scanner.nextByte());
        }
    }
}
