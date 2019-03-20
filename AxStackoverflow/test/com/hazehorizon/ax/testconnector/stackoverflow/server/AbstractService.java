package com.hazehorizon.ax.testconnector.stackoverflow.server;

import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

public abstract class AbstractService {
	private static String responce;
	private static Boolean exception = false;
	private static MultivaluedMap<String, String> parameters;
	
	public static void setup(MultivaluedMap<String, String> parameters, Boolean exception, String responce) {
		AbstractService.responce = responce;
		AbstractService.exception = exception;
		AbstractService.parameters = parameters;
	}
	
	@GET
	public String process(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>(uriInfo.getQueryParameters());
		if (!AbstractService.parameters.containsKey("page")) {
			queryParams.remove("page");
		}
		if (!AbstractService.parameters.containsKey("pageSize")) {
			queryParams.remove("pageSize");
		}		
		if (!queryParams.equalsIgnoreValueOrder(AbstractService.parameters)) {
			throw new RuntimeException("Parameters not equal!");
		}
		if (AbstractService.exception) {
			throw new RuntimeException(AbstractService.responce);
		}
		return AbstractService.responce;
	}
}
