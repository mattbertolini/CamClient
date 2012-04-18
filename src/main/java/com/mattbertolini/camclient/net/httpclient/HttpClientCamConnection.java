package com.mattbertolini.camclient.net.httpclient;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URL;

/**
 * @author Matt Bertolini
 */
public class HttpClientCamConnection implements CamConnection {
    private URL url;
    private HttpClient httpClient;
    private CamRequestAdapter<HttpPost> requestAdapter;

    public HttpClientCamConnection(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public CamResponse submitRequest(CamRequest request) {
        //
        HttpPost httpRequest = this.requestAdapter.buildRequest(this.url, request);
        try {
            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
        } catch (IOException e) {
            //
        }
        return null;
    }
}
