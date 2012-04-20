package com.mattbertolini.camclient.net.httpclient;

import com.mattbertolini.camclient.CamCredentials;
import com.mattbertolini.camclient.net.CamRequestAdapter;
import com.mattbertolini.camclient.net.Parameter;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.RequestParameter;
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
    public HttpPost buildRequest(URL url, CamCredentials credentials, CamRequest camRequest) {
        HttpPost request = null;
        try {
            request = new HttpPost(url.toURI());
            List<NameValuePair> formParams = new LinkedList<NameValuePair>();
            formParams.add(new BasicNameValuePair(RequestParameter.OPERATION.getName(), camRequest.getOperation().getName()));
            Map<Parameter, String> parameters = camRequest.getParameters();
            for(Map.Entry<Parameter, String> parameter : parameters.entrySet()) {
                formParams.add(new BasicNameValuePair(parameter.getKey().getName(), parameter.getValue()));
            }
            formParams.add(new BasicNameValuePair(RequestParameter.ADMIN_USERNAME.getName(), credentials.getUsername()));
            formParams.add(new BasicNameValuePair(RequestParameter.ADMIN_PASSWORD.getName(), credentials.getPassword()));
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
