package com.hazehorizon.ax.testconnector.stackoverflow;

import org.junit.Test;

import com.hazehorizon.ax.UnsupportedTypeException;


public abstract class AbstractQueryBuilderTest extends AbstractConnectorTest {
	protected String type;
	
	@Test(expected=UnsupportedTypeException.class)
	public void wrongType() throws Exception {
		this.connector.createQuery("zoo");
	}
	
	@Test(expected=UnsupportedTypeException.class)
	public void nullType() throws Exception {
		this.connector.createQuery(null);
	}
}
