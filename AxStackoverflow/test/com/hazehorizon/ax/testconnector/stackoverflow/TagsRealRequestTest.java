package com.hazehorizon.ax.testconnector.stackoverflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;
import com.hazehorizon.ax.connector.stackoverflow.TagsConnector;
import com.hazehorizon.ax.connector.stackoverflow.TagsResult;

public class TagsRealRequestTest extends AbstractConnectorTest {
	private static final String TAGS = "tags";

	@Test
	public void defaultQuery() throws Exception {
		AbstractQuery q = this.connector.createQuery(TAGS);
		TagsResult r = (TagsResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertNotNull(r.getQuery());
		assertTrue(0 < r.getItems().size());
		assertTrue(100 <= r.getItems().size());
	}
	
	@Test
	public void filterQuery() throws Exception {
		AbstractQuery q = this.connector.createQuery(TAGS, "java");
		TagsResult r = (TagsResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertTrue(0 < r.getItems().size());
		assertTrue(100 <= r.getItems().size());
		for (TagsResult.TagItem i: r.getItems()) {
			assertTrue(i.getName(), i.getName().contains("java"));
		}
	}	
		
	@Test
	public void limit0Query() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(TAGS);
		q.setLimit(0);
		TagsResult r = (TagsResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertEquals(0, r.getItems().size());
	}	

	@Test
	public void limit99Query() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(TAGS);
		q.setLimit(99);
		TagsResult r = (TagsResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertEquals(99, r.getItems().size());
	}		

	@Test
	public void limit120Query() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(TAGS);
		q.setLimit(120);
		TagsResult r = (TagsResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertEquals(120, r.getItems().size());
	}		

	@Override
	protected TagsConnector createConnector() {
		return new TagsConnector();
	}
}
