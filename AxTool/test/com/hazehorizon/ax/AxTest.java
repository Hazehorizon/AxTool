package com.hazehorizon.ax;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AxTest {
	
	@Test
	public void main() throws Exception {
		System.setProperty("ax.mainConnector", TestConnector.class.getName());
		IQueryConnector c = TestConnector.getMock();
		
		Ax.main(new String[]{"test"});
		
		Assert.assertNotNull(c);
		verify(c).createQuery("test");
		verify(c).executeQuery(null);
		verifyNoMoreInteractions(c);
	}
	
	@Test
	public void noParameter() {
		System.setProperty("ax.mainConnector", TestConnector.class.getName());
		IQueryConnector c = TestConnector.getMock();
		
		Ax.main(new String[]{});
		
		Assert.assertNotNull(c);
		verifyNoMoreInteractions(c);
	}
	
	@Test(expected=RuntimeException.class)
	public void wrongMain() throws Exception {
		System.setProperty("ax.mainConnector", "com.err.NoConnector");
		Ax.main(new String[]{"test"});
	}
	
	@Test
	public void theSameQuery() throws Exception {
		System.setProperty("ax.mainConnector", TestConnector.class.getName());
		IQueryConnector c = TestConnector.getMock();
		AbstractQuery q = new AbstractQuery("") {
		};
		when(c.createQuery(anyString())).thenReturn(q);
		
		Ax.main(new String[]{"test"});

		verify(c).executeQuery(q);
	}
	
	@After
	public void resetMock() {
		reset(TestConnector.getMock());
	}
}