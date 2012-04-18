package com.mattbertolini.camclient.net.urlconnection;

import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.Parameter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.RequestParameter;

import java.net.URL;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionCamRequestAdapter implements CamRequestAdapter<HttpRequest> {
    @Override
    public HttpRequest buildRequest(URL url, CamRequest camRequest) {
        //
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        requestBuilder.setUrl(url);
        requestBuilder.setMethod(RequestMethod.POST);

        UrlEncodedFormPayload postData = new UrlEncodedFormPayload();
        postData.addParameter(RequestParameter.OPERATION.getName(), camRequest.getOperation().getName());
        Map<Parameter, String> parameters = camRequest.getParameters();
        for(Map.Entry<Parameter, String> entry : parameters.entrySet()) {
            postData.addParameter(entry.getKey().getName(), entry.getValue());
        }
        requestBuilder.setContent(postData);
        return requestBuilder.build();
    }
}
