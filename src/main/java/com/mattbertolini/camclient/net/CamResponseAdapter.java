package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.response.CamResponse;

/**
 * @author Matt Bertolini
 */
public interface CamResponseAdapter<T> {
    CamResponse buildResponse(T response);
}
