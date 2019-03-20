package com.hazehorizon.ax;

import java.util.Set;

public interface IQueryConnector {
	public Set<String> getSupportedTypes();
	public AbstractQuery createQuery(String type, String... args) throws UnsupportedTypeException, QueryConstructionException;
	public AbstractResult executeQuery(AbstractQuery query) throws UnsupportedTypeException, ConnectorException;
}
