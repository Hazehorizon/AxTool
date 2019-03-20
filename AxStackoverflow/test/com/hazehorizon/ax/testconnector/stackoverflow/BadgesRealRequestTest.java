package com.hazehorizon.ax.testconnector.stackoverflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.connector.stackoverflow.BadgesConnector;
import com.hazehorizon.ax.connector.stackoverflow.BadgesResult;
import com.hazehorizon.ax.connector.stackoverflow.StackOverflowQuery;

public class BadgesRealRequestTest extends AbstractConnectorTest {
	private static final String BADGES = "badges";

	@Test
	public void defaultQuery() throws Exception {
		AbstractQuery q = this.connector.createQuery(BADGES, "bronze");
		BadgesResult r = (BadgesResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertNotNull(r.getQuery());
		assertTrue(0 < r.getItems().size());
		assertTrue(500 <= r.getItems().size());
		for (BadgesResult.BadgeItem i: r.getItems()) {
			assertEquals("bronze", i.getRank());
		}
	}
			
	@Test
	public void limit0Query() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(BADGES, "bronze");
		q.setLimit(0);
		BadgesResult r = (BadgesResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertEquals(0, r.getItems().size());
	}	

	@Test
	public void limit100Query() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(BADGES, "gold");
		q.setLimit(100);
		BadgesResult r = (BadgesResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertEquals(100, r.getItems().size());
		for (BadgesResult.BadgeItem i: r.getItems()) {
			assertEquals("gold", i.getRank());
		}		
	}		

	@Test
	public void limit500Query() throws Exception {
		StackOverflowQuery q = (StackOverflowQuery)this.connector.createQuery(BADGES, "silver");
		q.setLimit(500);
		BadgesResult r = (BadgesResult)this.connector.executeQuery(q);
		assertNotNull(r);
		assertEquals(500, r.getItems().size());
		for (BadgesResult.BadgeItem i: r.getItems()) {
			assertEquals("silver", i.getRank());
		}
	}			
	
	@Override
	protected BadgesConnector createConnector() {
		return new BadgesConnector();
	}
}