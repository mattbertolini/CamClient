package com.mattbertolini.camclient.net.httpclient;

import com.mattbertolini.camclient.net.AbstractCamResponseAdapter;
import com.mattbertolini.camclient.net.CamResponseAdapter;
import com.mattbertolini.camclient.response.CamResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Matt Bertolini
 */
public class HttpClientCamResponseAdapter extends AbstractCamResponseAdapter<HttpResponse> {
    @Override
    public CamResponse buildResponse(HttpResponse response) {
        boolean error = false;
        String errorText = null;
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if(statusCode >= 400) {
            error = true;
            errorText = statusLine.getReasonPhrase();
        }
        HttpEntity responseEntity = response.getEntity();
        String encoding = responseEntity.getContentEncoding().getValue();
        if(encoding == null) {
            encoding = "ISO-8859-1";
        }
        CamResponse camResponse = null;
        InputStream responseStream = null;
        try {
            responseStream = responseEntity.getContent();
            camResponse = this.foo(responseStream, encoding, error, errorText);
        } catch (IOException e) {
            //
        } finally {
            if(responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    //
                }
            }
        }

        return camResponse;
    }
}
