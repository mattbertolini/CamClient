package com.mattbertolini.camclient.net.support.urlconnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * @author Matt Bertolini
 */
public class Url {
    private final URL url;

    public Url(URL url) {
        this.url = url;
    }

    public HttpURLConnection openConnection() throws IOException {
        return (HttpURLConnection) this.url.openConnection();
    }

    public HttpURLConnection openConnection(Proxy proxy) throws IOException {
        return (HttpURLConnection) this.url.openConnection(proxy);
    }

    public String getProtocol() {
        return this.url.getProtocol();
    }

    @Override
    public String toString() {
        return this.url.toString();
    }
}
