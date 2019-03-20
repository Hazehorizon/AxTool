package com.hazehorizon.ax.testconnector.stackoverflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hazehorizon.ax.QueryConstructionException;
import com.hazehorizon.ax.connector.stackoverflow.BadgesConnector;
import com.hazehorizon.ax.connector.stackoverflow.OrderType;
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;

public class BadgesQueryBuilderTest extends AbstractQueryBuilderTest {
	public BadgesQueryBuilderTest() {
		this.type = "badges";
	}

	@Test
	public void defaultQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type, "silver");
		assertNotNull(q);
		assertEquals("stackoverflow", q.getSite());
		assertEquals(500, q.getLimit().intValue());
		assertEquals(OrderType.ASC, q.getOrder());
		assertEquals("rank", q.getSort());
		assertEquals(this.type, q.getType());
		assertEquals("silver", q.getMax());
		assertEquals("silver", q.getMin());
	}
	
	@Test
	public void goldFilterQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type, "gold");
		assertEquals("gold", q.getMax());
		assertEquals("gold", q.getMin());
	}
	
	@Test
	public void silverFilterQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type, "silver");
		assertEquals("silver", q.getMax());
		assertEquals("silver", q.getMin());
	}
	
	@Test
	public void bronzeFilterQuery() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(this.type, "bronze");
		assertEquals("bronze", q.getMax());
		assertEquals("bronze", q.getMin());
	}
	
	@Test(expected=QueryConstructionException.class)
	public void noFilterQuery() throws Exception {
		this.connector.createQuery(this.type);
	}
	
	@Test(expected=QueryConstructionException.class)
	public void wrongFilterQuery() throws Exception {
		this.connector.createQuery(this.type, "xxu");
	}
	
	@Test(expected=QueryConstructionException.class)
	public void nullFilterQuery() throws Exception {
		this.connector.createQuery(this.type, null);
	}
	
	@Override
	protected BadgesConnector createConnector() {
		return new BadgesConnector();
	}	
}
