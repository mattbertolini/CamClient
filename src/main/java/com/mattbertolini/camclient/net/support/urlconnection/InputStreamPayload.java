package com.mattbertolini.camclient.net.support.urlconnection;

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
    private String characterEncoding;

    public InputStreamPayload(InputStream payload, String contentType, String characterEncoding) {
        this.payload = payload;
        this.contentType = contentType;
        this.characterEncoding = characterEncoding;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public InputStream getInputStream() {
        return this.payload;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Scanner scanner = new Scanner(this.payload, this.characterEncoding);
        while(scanner.hasNextByte()) {
            outputStream.write(scanner.nextByte());
        }
    }
}
