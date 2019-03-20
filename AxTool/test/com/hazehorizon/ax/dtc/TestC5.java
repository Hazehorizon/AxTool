package com.hazehorizon.ax.dtc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.AbstractResult;
import com.hazehorizon.ax.ConnectorException;
import com.hazehorizon.ax.IQueryConnector;
import com.hazehorizon.ax.QueryConstructionException;
import com.hazehorizon.ax.UnsupportedTypeException;

public class TestC5 implements IQueryConnector {
	private static final Set<String> TYPES = new HashSet<>();;
	
	static {
		Collections.addAll(TYPES, "z6", "z1");
	}
	@Override
	public Set<String> getSupportedTypes() {
		return TYPES;
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
}