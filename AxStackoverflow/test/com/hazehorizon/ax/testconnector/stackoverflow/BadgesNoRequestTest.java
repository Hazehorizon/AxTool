package com.hazehorizon.ax.testconnector.stackoverflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.hazehorizon.ax.ConnectorException;
import com.hazehorizon.ax.connector.stackoverflow.BadgesConnector;
import com.hazehorizon.ax.connector.stackoverflow.BadgesResult;
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;
import com.hazehorizon.ax.testconnector.stackoverflow.server.AbstractService;
import com.hazehorizon.ax.testconnector.stackoverflow.server.BadgesService;

public class BadgesNoRequestTest extends AbstractNoRequestTest {
	private static final String DEFAULT_RESPONCE = "{\"items\": [ { \"name\": \"c#\", \"award_count\": \"10\", \"rank\": \"gold\" } ], \"has_more\": false }";
	private static final String BADGES = "badges";

	@Override
	protected Application configure() {
		return new ResourceConfig(BadgesService.class);
	}
	
	@Test
	public void defaultQuery() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(BADGES);
		AbstractService.setup(createMap(), false, 
				DEFAULT_RESPONCE);
		
		BadgesResult res = (BadgesResult)this.connector.executeQuery(q);
		
		assertNotNull(res);
		assertNotNull(res.getQuery());
		assertEquals(res.getHasMore(), false);
		assertEquals(1, res.getItems().size());
		assertEquals("c#", res.getItems().iterator().next().getName());
		assertEquals(10, res.getItems().iterator().next().getAwardCount().intValue());
		assertEquals("gold", res.getItems().iterator().next().getRank());
	}
	
	@Test
	public void limit3() throws Exception {
		BadgesResult r = (BadgesResult)super.limit(BADGES, 3, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit0() throws Exception {
		BadgesResult r = (BadgesResult)super.limit(BADGES, 0, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit100() throws Exception {
		BadgesResult r = (BadgesResult)super.limit(BADGES, 100, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit500early() throws Exception {
		BadgesResult r = (BadgesResult)super.limit(BADGES, 500, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit500() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(BADGES);
		q.setLimit(500);
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("pageSize", "100");
		params.remove("page");
		
		AbstractService.setup(params, false, "{\"items\": [ { \"name\": \"c#\", \"award_count\": \"10\", \"rank\": \"gold\" } ], \"has_more\": true }");
		
		BadgesResult r = (BadgesResult)this.connector.executeQuery(q);
		assertEquals(true, r.getHasMore());
		assertEquals(5, r.getItems().size());
	}	
	
	@Test
	public void max() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(BADGES);
		q.setMax("maxFilter");
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("max", "maxFilter");

		AbstractService.setup(params, false, 
				DEFAULT_RESPONCE);
		
		this.connector.executeQuery(q);
	}	

	@Test
	public void min() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(BADGES);
		q.setMin("minFilter");
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("min", "minFilter");

		AbstractService.setup(params, false, 
				DEFAULT_RESPONCE);
		
		this.connector.executeQuery(q);
	}		
	
	@Test
	public void sort() throws Exception {
		super.sort(BADGES, DEFAULT_RESPONCE);
	}

	@Test
	public void order() throws Exception {
		super.order(BADGES, DEFAULT_RESPONCE);
	}
	
	@Test
	public void site() throws Exception {
		super.site(BADGES, DEFAULT_RESPONCE);
	}

	@Test(expected=ConnectorException.class)
	public void exception() throws Exception {
		super.exception(BADGES);
	}
	
	@Override
	protected BadgesConnector createConnector() {
		return new BadgesConnector() {
			@Override
			protected WebTarget getTarget() {
				return target(getPath()).register(JacksonFeature.class);
			}
		};
	}
}