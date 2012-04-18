package com.mattbertolini.camclient.net.httpclient;

import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.Parameter;
import com.mattbertolini.camclient.request.CamRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class HttpClientCamRequestAdapter implements CamRequestAdapter<HttpPost> {
    @Override
    public HttpPost buildRequest(URL url, CamRequest camRequest) {
        HttpPost request = null;
        try {
            request = new HttpPost(url.toURI());
            List<NameValuePair> formParams = new LinkedList<NameValuePair>();
            Map<Parameter, String> parameters = camRequest.getParameters();
            for(Map.Entry<Parameter, String> parameter : parameters.entrySet()) {
                formParams.add(new BasicNameValuePair(parameter.getKey().getName(), parameter.getValue()));
            }
            UrlEncodedFormEntity postData = new UrlEncodedFormEntity(formParams, "UTF-8");
            request.setEntity(postData);
        } catch (URISyntaxException e) {
            //
        } catch (UnsupportedEncodingException e) {
            //
        }
        return request;
    }
}
