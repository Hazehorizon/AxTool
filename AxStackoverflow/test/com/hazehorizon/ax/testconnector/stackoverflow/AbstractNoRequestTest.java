package com.hazehorizon.ax.testconnector.stackoverflow;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import com.hazehorizon.ax.AbstractResult;
import com.hazehorizon.ax.UnsupportedTypeException;
import com.hazehorizon.ax.connector.stackoverflow.AbstractStackOverflowConnector;
import com.hazehorizon.ax.connector.stackoverflow.OrderType;
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;
import com.hazehorizon.ax.testconnector.stackoverflow.server.AbstractService;

public abstract class AbstractNoRequestTest extends JerseyTest {
	protected AbstractStackOverflowConnector connector;

	@Before
	public void init() {
		if (null == this.connector) {
			this.connector = createConnector();
		}
	}
	
	@Test(expected=UnsupportedTypeException.class)
	public void wrongType() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery("unknown");
		this.connector.executeQuery(q);
	}
	
	@Test(expected=UnsupportedTypeException.class)
	public void nullType() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(null);
		this.connector.executeQuery(q);
	}
	
	public AbstractResult order(String type, String responce) throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(type);
		q.setOrder(OrderType.DESC);
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("order", "DESC");
		
		AbstractService.setup(params, false, responce);
		
		return this.connector.executeQuery(q);		
	}
	
	public AbstractResult sort(String type, String responce) throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(type);
		q.setSort("field");
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("sort", "field");
		
		AbstractService.setup(params, false, responce);
		
		return this.connector.executeQuery(q);		
	}

	public AbstractResult site(String type, String responce) throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(type);
		q.setSite("zombo");
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("site", "zombo");
		
		AbstractService.setup(params, false, responce);
		
		return this.connector.executeQuery(q);		
	}

	public void exception(String type) throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(type);
		MultivaluedMap<String, String> params = createMap();
		
		AbstractService.setup(params, true, "Exception");
		
		this.connector.executeQuery(q);		
	}
	
	public AbstractResult limit(String type, Integer limit, String responce) throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(type);
		q.setLimit(limit);
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("pageSize", Integer.toString(Math.min(100, limit)));

		AbstractService.setup(params, false, responce);
		
		return this.connector.executeQuery(q);
	}	
	
	protected MultivaluedMap<String, String> createMap() {
		MultivaluedMap<String, String> ret = new MultivaluedHashMap<>();
		ret.add("key", "tIztnPrCXQFI*qF50pojSg((");
		ret.add("page", "1");
		ret.add("pageSize", "100");
		return ret;
	}

	protected abstract AbstractStackOverflowConnector createConnector();
}
