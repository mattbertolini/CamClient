package com.mattbertolini.camclient.net.urlconnection;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.Parameter;
import com.mattbertolini.camclient.net.support.urlconnection.Url;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.net.support.urlconnection.HttpRequest;
import com.mattbertolini.camclient.net.support.urlconnection.HttpRequestBuilder;
import com.mattbertolini.camclient.net.support.urlconnection.RequestMethod;
import com.mattbertolini.camclient.net.support.urlconnection.UrlEncodedFormPayload;

import java.net.URL;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionCamRequestAdapter implements CamRequestAdapter<HttpRequest> {
    @Override
    public HttpRequest buildRequest(URL url, CamCredentials credentials, CamRequest camRequest) {
        //
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        requestBuilder.setUrl(new Url(url));
        requestBuilder.setMethod(RequestMethod.POST);

        UrlEncodedFormPayload postData = new UrlEncodedFormPayload();
        postData.addParameter(RequestParameter.OPERATION.getName(), camRequest.getOperation().getName());
        Map<Parameter, String> parameters = camRequest.getParameters();
        for(Map.Entry<Parameter, String> entry : parameters.entrySet()) {
            postData.addParameter(entry.getKey().getName(), entry.getValue());
        }
        postData.addParameter(RequestParameter.ADMIN_USERNAME.getName(), credentials.getUsername());
        postData.addParameter(RequestParameter.ADMIN_PASSWORD.getName(), credentials.getPassword());
        requestBuilder.setPayload(postData);
        return requestBuilder.build();
    }
}
