package com.mattbertolini.camclient;

public class CamClientException extends Exception {
    private static final long serialVersionUID = 5947975468952957595L;

    public CamClientException() {
        super();
    }

    public CamClientException(String message) {
        super(message);
    }

    public CamClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public CamClientException(Throwable cause) {
        super(cause);
    }
}
