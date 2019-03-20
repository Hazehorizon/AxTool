package com.hazehorizon.ax;


public abstract class AbstractResult {
	private AbstractQuery query;
	
	public AbstractQuery getQuery() {
		return query;
	}

	protected void setQuery(AbstractQuery query) {
		this.query = query;
	}
}