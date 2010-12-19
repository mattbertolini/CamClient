package com.mattbertolini.camclient.net;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import com.mattbertolini.camclient.request.RequestBuilder;
import com.mattbertolini.camclient.response.ResponseBuilder;

public class CamConnectionFactory {
    public static CamConnection getConnection(String urlString, String username, String password) throws MalformedURLException {
        return getConnection(urlString, null, username, password);
    }

    public static CamConnection getConnection(String urlString, Proxy proxy, String username, String password) throws MalformedURLException {
        URL url = new URL(urlString);
        return getConnection(url, proxy, username, password);
    }

    public static CamConnection getConnection(URL url, String username, String password) {
        return getConnection(url, null, username, password);
    }

    public static CamConnection getConnection(URL url, Proxy proxy, String username, String password) {
        return new CamConnection(injectHttpConnectionService(), 
                injectRequestBuilder(), injectResponseBuilder(), 
                injectUserAgent(injectUserAgentBuilder()), url, proxy, username, password);
    }

    private static HttpConnectionService injectHttpConnectionService() {
        return new HttpConnectionServiceImpl();
    }

    private static RequestBuilder injectRequestBuilder() {
        return new RequestBuilder();
    }

    private static ResponseBuilder injectResponseBuilder() {
        return new ResponseBuilder();
    }

    private static UserAgentBuilder injectUserAgentBuilder() {
        return new UserAgentBuilder();
    }

    private static String injectUserAgent(UserAgentBuilder userAgentBuilder) {
        return userAgentBuilder.buildUserAgentString();
    }
}
