package com.hazehorizon.ax;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CompositeTest {
	protected ConnectorComposite connector;

	@Before
	public void init() {
		this.connector = createConnector();
	}

	@Test(expected=UnsupportedTypeException.class)
	public void noConnector() throws Exception {
		this.connector.getConnectorForType("type");
	}
	
	@Test
	public void addConnector() throws Exception {
		IQueryConnector c = mock(IQueryConnector.class);
		when(c.getSupportedTypes()).thenReturn(Collections.singleton("trest"));
		this.connector.addConnector(c);
		
		checkForType("trest", c);
	}
	
	@Test
	public void severalTypes() throws Exception {
		IQueryConnector c = mock(IQueryConnector.class);
		Set<String> types = new HashSet<>();
		Collections.addAll(types, "test", "trest", "rest");
		when(c.getSupportedTypes()).thenReturn(types);
		this.connector.addConnector(c);
		
		checkForType("test", c);
		checkForType("trest", c);
		checkForType("rest", c);
	}
	
	@Test(expected=RuntimeException.class)
	public void theSameType() {
		IQueryConnector c = mock(IQueryConnector.class);
		when(c.getSupportedTypes()).thenReturn(Collections.singleton("c111"));
		this.connector.addConnector(c);
		this.connector.addConnector(c);
	}
	
	@Test(expected=UnsupportedTypeException.class)
	public void nullType() throws Exception {
		this.connector.createQuery(null);
	}

	@Test(expected=RuntimeException.class)
	public void nullQuery() throws Exception {
		this.connector.executeQuery(null);
	}
	
	@Test(expected=UnsupportedTypeException.class)
	public void nullTypeQuery() throws Exception {
		AbstractQuery q = mock(AbstractQuery.class);
		this.connector.executeQuery(q);
	}
	
	@Test
	public void unmodifieble() throws Exception {
		IQueryConnector c = mock(IQueryConnector.class);
		when(c.getSupportedTypes()).thenReturn(Collections.singleton("+wwwq"));
		this.connector.addConnector(c);
		
		Map<String, IQueryConnector> mapc = this.connector.getConnectors();
		try {
			mapc.remove("+wwwq");
			Assert.fail("Exception is expected!");
		}
		catch(RuntimeException e) {
		}
		
		try {
			mapc.put("zoo", c);
			Assert.fail("Exception is expected!");
		}
		catch(RuntimeException e) {
		}
		
		try {
			mapc.put("+wwwq", c);
			Assert.fail("Exception is expected!");
		}
		catch(RuntimeException e) {
		}
	}
	
	protected void checkForType(String type, IQueryConnector c) throws Exception {
		reset(c);
		
		IQueryConnector c2 = this.connector.getConnectorForType(type);
		Assert.assertNotNull(c2);
		Assert.assertSame(c, c2);
		
		this.connector.createQuery(type);
		verify(c).createQuery(type);
		
		AbstractQuery q = mock(AbstractQuery.class);
		when(q.getType()).thenReturn(type);
		this.connector.executeQuery(q);
		verify(c).executeQuery(q);
	}
	
	protected ConnectorComposite createConnector() {
		return new ConnectorComposite();
	}
}