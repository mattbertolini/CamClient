package com.mattbertolini.camclient.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public interface HttpConnectionService {
    HttpURLConnection getConnection(URL url) throws IOException;
    HttpURLConnection getConnection(URL url, Proxy proxy) throws IOException;
}
