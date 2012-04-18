package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.request.CamRequest;

import java.net.URL;

/**
 * @param <T> The type to adapt the CamRequest to.
 * @author Matt Bertolini
 */
public interface CamRequestAdapter<T> {
    T buildRequest(URL url, CamRequest camRequest);
}
