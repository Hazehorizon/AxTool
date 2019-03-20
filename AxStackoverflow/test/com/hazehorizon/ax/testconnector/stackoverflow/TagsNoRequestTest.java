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
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;
import com.hazehorizon.ax.connector.stackoverflow.TagsConnector;
import com.hazehorizon.ax.connector.stackoverflow.TagsResult;
import com.hazehorizon.ax.testconnector.stackoverflow.server.AbstractService;
import com.hazehorizon.ax.testconnector.stackoverflow.server.TagsService;

public class TagsNoRequestTest extends AbstractNoRequestTest {
	private static final String DEFAULT_RESPONCE = "{\"items\": [ { \"name\": \"c#\" } ], \"has_more\": false }";
	private static final String TAGS = "tags";

	@Override
	protected Application configure() {
		return new ResourceConfig(TagsService.class);
	}
	
	@Test
	public void defaultQuery() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(TAGS);
		AbstractService.setup(createMap(), false, 
				DEFAULT_RESPONCE);
		
		TagsResult res = (TagsResult)this.connector.executeQuery(q);
		
		assertNotNull(res);
		assertNotNull(res.getQuery());
		assertEquals(res.getHasMore(), false);
		assertEquals(1, res.getItems().size());
		assertEquals("c#", res.getItems().iterator().next().getName());
	}
	
	@Test
	public void limit10() throws Exception {
		TagsResult r = (TagsResult)super.limit(TAGS, 10, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit0() throws Exception {
		TagsResult r = (TagsResult)super.limit(TAGS, 0, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit100() throws Exception {
		TagsResult r = (TagsResult)super.limit(TAGS, 100, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit500early() throws Exception {
		TagsResult r = (TagsResult)super.limit(TAGS, 500, DEFAULT_RESPONCE);
		assertEquals(1, r.getItems().size());
	}
	
	@Test
	public void limit500() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(TAGS);
		q.setLimit(500);
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("pageSize", "100");
		params.remove("page");
		
		AbstractService.setup(params, false, "{\"items\": [ { \"name\": \"c#\" } ], \"has_more\": true }");
		
		TagsResult r = (TagsResult)this.connector.executeQuery(q);
		assertEquals(true, r.getHasMore());
		assertEquals(5, r.getItems().size());
	}	
	
	@Test
	public void inname() throws Exception {
		StackOverflowQuery q = new StackOverflowQuery(TAGS);
		q.setInname("filter");;
		MultivaluedMap<String, String> params = createMap();
		params.putSingle("inname", "filter");

		AbstractService.setup(params, false, 
				DEFAULT_RESPONCE);
		
		this.connector.executeQuery(q);
	}	
	
	@Test
	public void sort() throws Exception {
		super.sort(TAGS, DEFAULT_RESPONCE);
	}

	@Test
	public void order() throws Exception {
		super.order(TAGS, DEFAULT_RESPONCE);
	}
	
	@Test
	public void site() throws Exception {
		super.site(TAGS, DEFAULT_RESPONCE);
	}

	@Test(expected=ConnectorException.class)
	public void exception() throws Exception {
		super.exception(TAGS);
	}
	
	@Override
	protected TagsConnector createConnector() {
		return new TagsConnector() {
			@Override
			protected WebTarget getTarget() {
				return target(getPath()).register(JacksonFeature.class);
			}
		};
	}
}