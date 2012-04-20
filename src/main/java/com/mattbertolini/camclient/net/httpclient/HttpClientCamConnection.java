package com.mattbertolini.camclient.net.httpclient;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URL;

/**
 * @author Matt Bertolini
 */
public class HttpClientCamConnection implements CamConnection {
    private URL url;
    private CamCredentials credentials;
    private HttpClient httpClient;
    private CamRequestAdapter<HttpPost> requestAdapter;

    public HttpClientCamConnection() {
        //
    }

    public HttpClientCamConnection(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public CamResponse submitRequest(CamRequest request) {
        //
        if(this.httpClient == null) {
            throw new IllegalStateException("HTTP client is null.");
        }
        HttpPost httpRequest = this.requestAdapter.buildRequest(this.url, this.credentials, request);
        //TODO: Add common method for generating CamClient user agent string.
        Header userAgentHeader = new BasicHeader("User-Agent", "CamClient/2.0.0 Java/1.6.0_31 Linux/3.2.0");
        httpRequest.addHeader(userAgentHeader);
        try {
            HttpResponse httpResponse = this.httpClient.execute(httpRequest);
        } catch (IOException e) {
            //
        }
        return null;
    }

    public void setCredentials(CamCredentials credentials) {
        this.credentials = credentials;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
