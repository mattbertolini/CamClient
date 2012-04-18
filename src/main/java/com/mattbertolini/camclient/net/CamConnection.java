package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.response.CamResponse;

public interface CamConnection {
    CamResponse submitRequest(CamRequest request);
}
