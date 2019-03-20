package com.hazehorizon.ax.connector.stackoverflow;

import com.hazehorizon.ax.AbstractQuery;

public class StackOverflowQuery extends AbstractQuery {
	private Integer limit;
	private OrderType order;
	private String sort;
	private String min;
	private String max;
	private String inname;
	private String site;

	public StackOverflowQuery(String type) {
		super(type);
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public OrderType getOrder() {
		return order;
	}

	public void setOrder(OrderType order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getInname() {
		return inname;
	}

	public void setInname(String inname) {
		this.inname = inname;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}	
}