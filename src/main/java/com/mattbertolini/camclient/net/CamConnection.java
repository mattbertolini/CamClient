package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;

/**
 * @author Matt Bertolini
 */
public interface CamConnection {
    CamResponse submitRequest(CamRequest request);
}
