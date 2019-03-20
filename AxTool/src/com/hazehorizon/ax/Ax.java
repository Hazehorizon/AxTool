package com.hazehorizon.ax;

import java.util.Arrays;

public class Ax {
	private static final String MAIN_CONNECTOR_PROP = "ax.mainConnector";
	private static final String MAIN_CONNECTOR_DEFAULT_VAL = "com.hazehorizon.ax.SelfInitializedConnectorComposite";
	
	public static void main(String[] args) {
		try {
			IQueryConnector connector = initializeMainConnactor();
			AbstractQuery query = createQuery(connector, args);
			AbstractResult res = connector.executeQuery(query);
			System.out.println(res);
		}
		catch (ConnectorException e) {
			e.printStackTrace(System.err);
		}
		catch (AxException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static AbstractQuery createQuery(IQueryConnector connector, String args[]) throws AxException {
		if (args.length < 1) {
			throw new AxException("Wrong usage! Run: ax <type> [<parameter>]");
		}
		return connector.createQuery(args[0], Arrays.copyOfRange(args, 1, args.length));
	}
	
	private static IQueryConnector initializeMainConnactor() {
		try {
			String className = System.getProperty(MAIN_CONNECTOR_PROP, MAIN_CONNECTOR_DEFAULT_VAL);
			return (IQueryConnector)Class.forName(className).newInstance();
		} 
		catch (Exception e) {
			throw new RuntimeException("Main connector initialization exception!", e);
		}
	}
}