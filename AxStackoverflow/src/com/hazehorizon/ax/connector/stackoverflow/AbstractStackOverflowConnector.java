package com.hazehorizon.ax.connector.stackoverflow;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.GZipEncoder;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.AbstractResult;
import com.hazehorizon.ax.ConnectorException;
import com.hazehorizon.ax.IQueryConnector;
import com.hazehorizon.ax.QueryConstructionException;
import com.hazehorizon.ax.UnsupportedTypeException;

public abstract class AbstractStackOverflowConnector implements IQueryConnector {
	private Class<? extends AbstractStackOverflowResult<?>> resultClass;
	private String host = "http://api.stackexchange.com";
	private String path;
	private String key = "tIztnPrCXQFI*qF50pojSg((";
	private Integer maxLimit = 100;
	
	private Integer defaultLimit = 100;
	private String defaultSite = "stackoverflow";
	private String defaultSort;
	private OrderType defaultOrder;
	
	public AbstractStackOverflowConnector(
			Class<? extends AbstractStackOverflowResult<?>> resultClass) {
		this.resultClass = resultClass;
	}
	
	@Override
	public AbstractQuery createQuery(String type, String... args)
			throws UnsupportedTypeException, QueryConstructionException {
		checkType(type);
		StackOverflowQuery query = new StackOverflowQuery(type);
		setParameters(query, args);
		return query;
	}
	@Override
	public AbstractResult executeQuery(AbstractQuery query)
			throws UnsupportedTypeException, ConnectorException {
		checkType(query.getType());
		WebTarget target = getTarget();
		target = assignQueryParameters(query, target);
		Integer left = ((StackOverflowQuery)query).getLimit();
		Integer pageSize = null == left ? this.maxLimit : Math.min(left, this.maxLimit);
		Integer page = 1;
		boolean hasMore = true;
		AbstractStackOverflowResult<?> result = null;
		
		do {
			AbstractStackOverflowResult<?> part = makeRequest(target.queryParam("page", page).queryParam("pageSize", pageSize));
			hasMore = part.getHasMore();
			if (null == result) {
				result = part;
			}
			else {
				result.merge((AbstractStackOverflowResult)part, left == null ? part.getItems().size() : left);
			}
			++page;
			if (null != left) {
				left -= pageSize;
			}
		}
		while (hasMore && (null==left || left > 0));
		
		result.assignQuery(query);
		return result;
	}
	
	protected AbstractStackOverflowResult<?> makeRequest(WebTarget target) throws ConnectorException {
		try {
			AbstractStackOverflowResult<?> result = target.request(MediaType.APPLICATION_JSON_TYPE).get(this.resultClass);
			return result;
		}
		catch (Throwable e) {
			throw new ConnectorException("Connector error " + getClass().getName(), e);
		}		
	}
	
	protected WebTarget getTarget() {
		Client c = ClientBuilder.newBuilder().register(JacksonFeature.class).register(GZipEncoder.class).build();
		return c.target(this.host).path(this.path);
	}
	
	protected WebTarget assignQueryParameters(AbstractQuery query, WebTarget target) {
		try {
			Map<String, String> params = BeanUtils.describe(query);
			params.remove("type");
			params.remove("class");
			params.remove("limit");
			for (Map.Entry<String, String> e : params.entrySet()) {
				if (null != e.getValue()) {
					target = target.queryParam(e.getKey(), e.getValue());
				}
			}
			if (StringUtils.isNoneBlank(getKey())) {
				target = target.queryParam("key", getKey());
			}
			return target;
		} 
		catch (Exception e) {
			throw new RuntimeException("assignQueryParameters error", e);
		}
	}
	
	protected void setParameters(StackOverflowQuery query, String... args) throws QueryConstructionException {
		query.setLimit(this.defaultLimit);
		query.setSite(this.defaultSite);
		query.setSort(this.defaultSort);
		query.setOrder(this.defaultOrder);
	}
	
	protected void checkType(String type) throws UnsupportedTypeException {
		if (!getSupportedTypes().contains(type)) {
			throw new UnsupportedTypeException(type);
		}
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getDefaultLimit() {
		return defaultLimit;
	}
	public void setDefaultLimit(Integer defaultLimit) {
		this.defaultLimit = defaultLimit;
	}
	public String getDefaultSite() {
		return defaultSite;
	}
	public void setDefaultSite(String defaultSite) {
		this.defaultSite = defaultSite;
	}
	public String getDefaultSort() {
		return defaultSort;
	}
	public void setDefaultSort(String defaultSort) {
		this.defaultSort = defaultSort;
	}
	public OrderType getDefaultOrder() {
		return defaultOrder;
	}
	public void setDefaultOrder(OrderType defaultOrder) {
		this.defaultOrder = defaultOrder;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getMaxLimit() {
		return maxLimit;
	}
	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}
}