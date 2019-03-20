package com.hazehorizon.ax;

public abstract class AbstractQuery {
	private String type;

	public AbstractQuery(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
