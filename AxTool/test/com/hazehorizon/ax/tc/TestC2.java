package com.hazehorizon.ax.tc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.AbstractResult;
import com.hazehorizon.ax.ConnectorException;
import com.hazehorizon.ax.IQueryConnector;
import com.hazehorizon.ax.QueryConstructionException;
import com.hazehorizon.ax.UnsupportedTypeException;


public class TestC2 implements IQueryConnector {
	private static final Set<String> TYPES = new HashSet<>();;
	private String prop1 = "default";
	private Integer prop2;
	
	static {
		Collections.addAll(TYPES, "type2", "type3");
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

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public Integer getProp2() {
		return prop2;
	}

	public void setProp2(Integer prop2) {
		this.prop2 = prop2;
	}
}