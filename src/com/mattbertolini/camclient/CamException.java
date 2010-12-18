package com.mattbertolini.camclient;

public class CamException extends Exception {
	private static final long serialVersionUID = 5947975468952957595L;

	public CamException() {
		super();
	}

	public CamException(String message) {
		super(message);
	}
	
	public CamException(String message, Throwable cause) {
		super(message, cause);
	}

	public CamException(Throwable cause) {
		super(cause);
	}
}
