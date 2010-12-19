package com.mattbertolini.camclient.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class HttpConnectionServiceImpl implements HttpConnectionService {
    public HttpURLConnection getConnection(URL url) throws IOException {
        return this.getConnection(url, null);
    }

    public HttpURLConnection getConnection(URL url, Proxy proxy) throws IOException {
        if(url == null) {
            throw new NullPointerException("URL object cannot be null.");
        }
        HttpURLConnection conn = null;
        if(proxy != null) {
            conn = (HttpURLConnection) url.openConnection(proxy);
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        return conn;
    }
}
