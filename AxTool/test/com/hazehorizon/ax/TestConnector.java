package com.hazehorizon.ax;

import java.util.Set;

import org.mockito.Mockito;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.AbstractResult;
import com.hazehorizon.ax.ConnectorException;
import com.hazehorizon.ax.IQueryConnector;
import com.hazehorizon.ax.QueryConstructionException;
import com.hazehorizon.ax.UnsupportedTypeException;


public class TestConnector implements IQueryConnector {
	private static IQueryConnector mock = Mockito.mock(IQueryConnector.class);
	
	public static IQueryConnector getMock() {
		return TestConnector.mock;
	}

	@Override
	public Set<String> getSupportedTypes() {
		return TestConnector.mock.getSupportedTypes();
	}

	@Override
	public AbstractQuery createQuery(String type, String... args)
			throws UnsupportedTypeException, QueryConstructionException {
		return TestConnector.mock.createQuery(type, args);
	}

	@Override
	public AbstractResult executeQuery(AbstractQuery query)
			throws UnsupportedTypeException, ConnectorException {
		return TestConnector.mock.executeQuery(query);
	}
}