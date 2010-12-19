package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.CamException;

public class CamConnectionException extends CamException {
    private static final long serialVersionUID = 7017148199085982635L;

    public CamConnectionException() {
        super();
    }

    public CamConnectionException(String message) {
        super(message);
    }

    public CamConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CamConnectionException(Throwable cause) {
        super(cause);
    }

}
