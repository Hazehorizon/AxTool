package com.hazehorizon.ax.testconnector.stackoverflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.hazehorizon.ax.connector.stackoverflow.OrderType;
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;
import com.hazehorizon.ax.connector.stackoverflow.TagsConnector;

public class TagsQueryBuilderTest extends AbstractQueryBuilderTest {
	public TagsQueryBuilderTest() {
		this.type = "tags";
	}

	@Test
	public void defaultQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type);
		assertNotNull(q);
		assertEquals("stackoverflow", q.getSite());
		assertEquals(100, q.getLimit().intValue());
		assertEquals(OrderType.ASC, q.getOrder());
		assertEquals("name", q.getSort());
		assertEquals(this.type, q.getType());
		assertNull(q.getInname());
	}
	
	@Test
	public void filterQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type, "rozo");
		assertNotNull(q);
		assertEquals("rozo", q.getInname());
	}
	
	@Test
	public void nullFilterQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type, null);
		assertNull(q.getInname());
	}
	
	@Override
	protected TagsConnector createConnector() {
		return new TagsConnector();
	}
}
