package com.mattbertolini.camclient.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

public class MockHttpURLConnection extends HttpURLConnection {
    private static final String UTF_8 = "UTF-8";

    private ByteArrayOutputStream requestData;
    private TestResponseGenerator trg;

    public MockHttpURLConnection(URL u, Proxy p, Map<TestParameter, String> testParameters) {
        this(u, testParameters);
    }

    protected MockHttpURLConnection(URL u, Map<TestParameter, String> testParameters) {
        super(u);
        this.requestData = new ByteArrayOutputStream();
        this.trg = new TestResponseGenerator(testParameters);
    }

    @Override
    public void disconnect() {
        // Do nothing
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
        if(this.trg.throwIoExceptionOnConnect()) {
            throw new IOException();
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        String requestString = this.requestData.toString(UTF_8);
        this.trg.parseRequest(requestString);
        String s = this.trg.generateResponse();
        return new ByteArrayInputStream(s.getBytes(UTF_8));
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.requestData;
    }

    @Override
    public int getResponseCode() throws IOException {
        return this.trg.getResposneCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
