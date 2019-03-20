package com.hazehorizon.ax;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;



public class ConnectorComposite implements IQueryConnector {
	private Map<String, IQueryConnector> connectors = new HashMap<>();
	
	@Override
	public Set<String> getSupportedTypes() {
		return this.connectors.keySet();
	}

	@Override
	public AbstractQuery createQuery(String type, String... args) throws UnsupportedTypeException, QueryConstructionException {
		IQueryConnector c = getConnectorForType(type);
		return c.createQuery(type, args);
	}

	@Override
	public AbstractResult executeQuery(AbstractQuery query) throws UnsupportedTypeException, ConnectorException {
		IQueryConnector c = getConnectorForType(query.getType());		
		return c.executeQuery(query);
	}

	protected void addConnector(IQueryConnector connector) {
		Set<String> types = connector.getSupportedTypes();
		if (CollectionUtils.isEmpty(types)) {
			throw new RuntimeException("InitializationError! Connector " + connector.getClass().getName() + " doesn't support any query type");
		}
		for (String type: types) {
			synchronized (this.connectors) {
				if (this.connectors.containsKey(type)) {
					throw new RuntimeException("InitializationError! Connector for type " + type + " already defined");
				}
				this.connectors.put(type, connector);
			}
		}
	}
	
	protected IQueryConnector getConnectorForType(String type) throws UnsupportedTypeException {
		if (StringUtils.isBlank(type)) {
			throw new UnsupportedTypeException(type);
		}
		IQueryConnector c = this.connectors.get(type);
		if (null == c) {
			throw new UnsupportedTypeException(type);
		}
		return c;
	}

	public Map<String, IQueryConnector> getConnectors() {
		return Collections.unmodifiableMap(this.connectors);
	}
}