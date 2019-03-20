package com.hazehorizon.ax;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.reflections.Reflections;

public class SelfInitializedConnectorComposite extends ConnectorComposite {
	private static final String AX_PROPERTY_PREFIX_EXP = "^ax\\.";
	private static final Pattern SELF_PROPERTIES = Pattern.compile("^ax\\.((?!connectors\\.).)+$"); //"^ax\\.(^).+"
	private static final Pattern CONNECTORS_PROPERTIES = Pattern.compile("^ax\\.connectors\\..+");
	
	private String connectorsPackage = "com.hazehorizon.ax.connector";
	private String includeClassesExp = ".*";
	private String excludeClassesExp = ".*(ConnectorComposite|SelfInitializedConnectorComposite)$";
	
	public SelfInitializedConnectorComposite() {
		Map<Object, Object> properties =  System.getProperties();
		configureProperties(properties, SELF_PROPERTIES);
		loadConnectors();
		configureProperties(properties, CONNECTORS_PROPERTIES);
	}
	
	protected void configureProperties(Map<Object, Object> properties, Pattern pattern) {
		for (Map.Entry<Object, Object> e: properties.entrySet()) {
			String propName = (String)e.getKey();
			if (pattern.matcher(propName).matches()) {
				propName = propName.replaceFirst(AX_PROPERTY_PREFIX_EXP, "");
				try {
					BeanUtils.copyProperty(this, propName, e.getValue());
				} 
				catch (Exception ex) {
					// TODO: log it
				}
			}
		}
	}
	
	protected void loadConnectors() {
		try {
			Reflections reflections = new Reflections(this.connectorsPackage);
			Set<Class<? extends IQueryConnector>> subTypes = reflections.getSubTypesOf(IQueryConnector.class);
			Pattern includePattern = Pattern.compile(this.includeClassesExp);
			Pattern excludePattern = Pattern.compile(this.excludeClassesExp);
			for (Class<? extends IQueryConnector> clazz : subTypes) {
				if ( !clazz.isInterface() 
						&& !Modifier.isAbstract(clazz.getModifiers())
						&& !excludePattern.matcher(clazz.getName()).matches()
						&& includePattern.matcher(clazz.getName()).matches() ) {
					addConnector(clazz.newInstance());
				}
			}
		} 
		catch (Exception e) {
			throw new RuntimeException("SelfInitializedConnectorComposite initialization exception!", e);
		}
	}

	public String getConnectorsPackage() {
		return connectorsPackage;
	}

	public void setConnectorsPackage(String connectorsPackage) {
		this.connectorsPackage = connectorsPackage;
	}

	public String getIncludeClassesExp() {
		return includeClassesExp;
	}

	public void setIncludeClassesExp(String includeClassesExp) {
		this.includeClassesExp = includeClassesExp;
	}

	public String getExcludeClassesExp() {
		return excludeClassesExp;
	}

	public void setExcludeClassesExp(String excludeClassesExp) {
		this.excludeClassesExp = excludeClassesExp;
	}
}