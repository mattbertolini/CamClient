package com.mattbertolini.camclient.net.urlconnection;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;

import java.net.URL;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionCamConnection implements CamConnection {
    private URL url;
    private HttpConnection connection;
    private CamRequestAdapter<HttpRequest> requestAdapter;

    @Override
    public CamResponse submitRequest(CamRequest request) {
        //
        HttpRequest httpRequest = this.requestAdapter.buildRequest(this.url, request);
        HttpResponse httpResponse = this.connection.submitRequest(httpRequest);
        return null;
    }
}
