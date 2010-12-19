package com.mattbertolini.camclient.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MockHttpConnectionService implements HttpConnectionService {
    private Map<TestParameter, String> testParameters = new HashMap<TestParameter, String>();

    @Override
    public HttpURLConnection getConnection(URL url) throws IOException {
        return getConnection(url, null);
    }

    @Override
    public HttpURLConnection getConnection(URL url, Proxy proxy) throws IOException {
        return new MockHttpURLConnection(url, proxy, this.testParameters);
    }

    public void addTestParameter(TestParameter name, String value) {
        this.testParameters.put(name, value);
    }

    public void resetTestParameters() {
        this.testParameters.clear();
    }
}
