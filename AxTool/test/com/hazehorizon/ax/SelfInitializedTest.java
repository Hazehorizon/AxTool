package com.hazehorizon.ax;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.hazehorizon.ax.tc.TestC1;
import com.hazehorizon.ax.tc.TestC2;

public class SelfInitializedTest extends CompositeTest {

	@Test
	public void setWrongPackage() {
		System.setProperty("ax.connectorsPackage", "z.rr.t");
		SelfInitializedConnectorComposite c = spy(createConnector());
		
		Assert.assertEquals("z.rr.t", c.getConnectorsPackage());
		verify(c, never()).getConnectors();
		Assert.assertEquals(0, c.getConnectors().size());
	}

	@Test
	public void load() {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		SelfInitializedConnectorComposite c = spy(createConnector());

		Assert.assertEquals("com.hazehorizon.ax.tc", c.getConnectorsPackage());
		verify(c, never()).getConnectors();
		Assert.assertEquals(3, c.getConnectors().size());		
	}
	
	@Test(expected=RuntimeException.class)
	public void loadNullType() {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.ntc");
		createConnector();
	}
	
	@Test(expected=RuntimeException.class)
	public void loadDuplicatedType() {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.dtc");
		createConnector();
	}
	
	@Test
	public void noInclude() {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.includeClassesExp", ".*1$");
		SelfInitializedConnectorComposite c = createConnector();
		
		Assert.assertEquals(1, c.getConnectors().size());
	}
	
	@Test
	public void exclude() {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.excludeClassesExp", ".*1$");
		SelfInitializedConnectorComposite c = createConnector();
		
		Assert.assertEquals(2, c.getConnectors().size());
	}
	
	@Test
	public void wrongProp() {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.package", "com.hazehorizon.ax.tc");
		createConnector();
	}
	
	@Test
	public void setForConnector() throws Exception {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.connectors.type1.prop1", "vaal");
		
		SelfInitializedConnectorComposite c = createConnector();
		
		TestC1 c1 = (TestC1)c.getConnectorForType("type1");
		Assert.assertEquals("vaal", c1.getProp1());
		TestC2 c2 = (TestC2)c.getConnectorForType("type2");
		Assert.assertNotEquals("vaal", c2.getProp1());
	}
	
	@Test
	public void setWrongPropForConnector() throws Exception {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.connectors.type1.prop11", "vaal");
		createConnector();		
	}
	
	@Test
	public void setIntForConnector() throws Exception {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.connectors.type2.prop2", "11");
		SelfInitializedConnectorComposite c = createConnector();
		
		TestC2 c2 = (TestC2)c.getConnectorForType("type2");
		Assert.assertEquals(11, c2.getProp2().intValue());
	}
	
	@Test
	public void setForConnectorSeveralTypes() throws Exception {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.connectors.type3.prop1", "zi");
		SelfInitializedConnectorComposite c = createConnector();
		
		TestC2 c2 = (TestC2)c.getConnectorForType("type2");
		Assert.assertEquals("zi", c2.getProp1());
		c2 = (TestC2)c.getConnectorForType("type3");
		Assert.assertEquals("zi", c2.getProp1());
	}
	
	@Test
	public void setForNoConnector() throws Exception {
		System.setProperty("ax.connectorsPackage", "com.hazehorizon.ax.tc");
		System.setProperty("ax.connectors.type9.prop1", "++");
		createConnector();
	}
	
	@After
	public void clean() {
		System.clearProperty("ax.connectorsPackage");
		System.clearProperty("ax.includeClassesExp");
		System.clearProperty("ax.excludeClassesExp");
		System.clearProperty("ax.connectors.type1.prop1");
		System.clearProperty("ax.connectors.type2.prop1");
		System.clearProperty("ax.connectors.type2.prop2");
		System.clearProperty("ax.connectors.type3.prop1");
		System.clearProperty("ax.connectors.type3.prop2");
		System.clearProperty("ax.connectors.type1.prop11");
		System.clearProperty("ax.connectors.type9.prop1");
	}
	
	@Override
	protected SelfInitializedConnectorComposite createConnector() {
		return new SelfInitializedConnectorComposite();
	}
	
}
