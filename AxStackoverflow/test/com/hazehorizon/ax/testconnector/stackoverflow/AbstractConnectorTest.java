package com.hazehorizon.ax.testconnector.stackoverflow;

import org.junit.Before;

import com.hazehorizon.ax.connector.stackoverflow.AbstractStackOverflowConnector;

public abstract class AbstractConnectorTest {
	protected AbstractStackOverflowConnector connector;

	@Before
	public void init() {
		if (null == this.connector) {
			this.connector = createConnector();
		}
	}

	protected abstract AbstractStackOverflowConnector createConnector();
}
