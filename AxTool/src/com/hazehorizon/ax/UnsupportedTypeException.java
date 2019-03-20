package com.hazehorizon.ax;

public class UnsupportedTypeException extends AxException {
	public String type;

	public UnsupportedTypeException(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	@Override
	public String getMessage() {
		return "Query type: " + getType() + " isn't supported!";
	}	
}