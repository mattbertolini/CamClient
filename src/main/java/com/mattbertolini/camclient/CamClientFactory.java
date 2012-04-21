package com.mattbertolini.camclient;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.CamResponseAdapter;
import com.mattbertolini.camclient.net.support.urlconnection.HttpConnection;
import com.mattbertolini.camclient.net.support.urlconnection.HttpConnectionImpl;
import com.mattbertolini.camclient.net.support.urlconnection.HttpRequest;
import com.mattbertolini.camclient.net.support.urlconnection.HttpResponse;
import com.mattbertolini.camclient.net.urlconnection.HttpConnectionCamConnection;
import com.mattbertolini.camclient.net.urlconnection.HttpConnectionCamRequestAdapter;
import com.mattbertolini.camclient.net.urlconnection.HttpConnectionCamResponseAdapter;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Matt Bertolini
 */
public class CamClientFactory {
    public static CamClient newCamClient(URL camUrl, String username, String password) {
        if(camUrl == null) {
            throw new IllegalArgumentException("URL cannot be null.");
        }
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        if(password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        CamCredentials credentials = new BasicCamCredentials(username, password);
        HttpConnection httpConnection = new HttpConnectionImpl();
        CamRequestAdapter<HttpRequest> requestAdapter = new HttpConnectionCamRequestAdapter();
        CamResponseAdapter<HttpResponse> responseAdapter = new HttpConnectionCamResponseAdapter();
        CamConnection camConnection = new HttpConnectionCamConnection(httpConnection, camUrl, credentials, requestAdapter, responseAdapter);
        return new CamClientImpl(camConnection);
    }

    public static CamAsyncClient newCamAsyncClient(URL camUrl, String username, String password) {
        //
        ExecutorService executorService = Executors.newCachedThreadPool();
        return new CamAsyncClientImpl();
    }
}
