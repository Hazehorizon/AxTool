package com.hazehorizon.ax;

public class AxException extends Exception {
	public AxException() {
	}
	
	public AxException(String message) {
		super(message);
	}

	public AxException(String message, Throwable cause) {
		super(message, cause);
	}	
}
