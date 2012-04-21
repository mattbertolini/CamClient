package com.mattbertolini.camclient.net.urlconnection;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.CamResponseAdapter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.net.support.urlconnection.HttpConnection;
import com.mattbertolini.camclient.net.support.urlconnection.HttpRequest;
import com.mattbertolini.camclient.net.support.urlconnection.HttpResponse;

import java.io.IOException;
import java.net.URL;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionCamConnection implements CamConnection {
    private URL url;
    private CamCredentials credentials;
    private HttpConnection httpConnection;
    private CamRequestAdapter<HttpRequest> requestAdapter;
    private CamResponseAdapter<HttpResponse> responseAdapter;

    public HttpConnectionCamConnection() {
        //
    }

    public HttpConnectionCamConnection(HttpConnection httpConnection, URL url, CamCredentials credentials, CamRequestAdapter<HttpRequest> requestAdapter, CamResponseAdapter<HttpResponse> responseAdapter) {
        this.httpConnection = httpConnection;
        this.url = url;
        this.credentials = credentials;
        this.requestAdapter = requestAdapter;
        this.responseAdapter = responseAdapter;
    }

    @Override
    public CamResponse submitRequest(CamRequest request) {
        if(this.httpConnection == null) {
            throw new IllegalStateException("Http connection is null.");
        }
        if(this.url == null) {
            throw new IllegalStateException("URL is null.");
        }
        if(this.credentials == null) {
            throw new IllegalStateException("Credentials object is null.");
        }
        if(this.requestAdapter == null) {
            throw new IllegalStateException("Request adapter is null.");
        }
        if(this.responseAdapter == null) {
            throw new IllegalStateException("Response adapter is null.");
        }
        CamResponse camResponse = null;
        try {
            HttpRequest httpRequest = this.requestAdapter.buildRequest(this.url, this.credentials, request);
            //TODO: Add user agent
            HttpResponse httpResponse = this.httpConnection.submitRequest(httpRequest);
            camResponse = this.responseAdapter.buildResponse(httpResponse);
        } catch (IOException e) {
            //
        }
        return camResponse;
    }

    public void setHttpConnection(HttpConnection connection) {
        this.httpConnection = connection;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setCredentials(CamCredentials credentials) {
        this.credentials = credentials;
    }

    public void setRequestAdapter(CamRequestAdapter<HttpRequest> requestAdapter) {
        this.requestAdapter = requestAdapter;
    }

    public void setResponseAdapter(CamResponseAdapter<HttpResponse> responseAdapter) {
        this.responseAdapter = responseAdapter;
    }
}
