package com.hazehorizon.ax.tc;

import java.util.Collections;
import java.util.Set;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.AbstractResult;
import com.hazehorizon.ax.ConnectorException;
import com.hazehorizon.ax.IQueryConnector;
import com.hazehorizon.ax.QueryConstructionException;
import com.hazehorizon.ax.UnsupportedTypeException;

public class TestC1 implements IQueryConnector {
	private String prop1;
	
	@Override
	public Set<String> getSupportedTypes() {
		return Collections.singleton("type1");
	}

	@Override
	public AbstractQuery createQuery(String type, String... args)
			throws UnsupportedTypeException, QueryConstructionException {
		return null;
	}

	@Override
	public AbstractResult executeQuery(AbstractQuery query)
			throws UnsupportedTypeException, ConnectorException {
		return null;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}
}