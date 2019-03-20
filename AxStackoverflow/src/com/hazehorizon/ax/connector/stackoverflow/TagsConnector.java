package com.hazehorizon.ax.connector.stackoverflow;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hazehorizon.ax.QueryConstructionException;

public class TagsConnector extends AbstractStackOverflowConnector {
	public TagsConnector() {
		super(TagsResult.class);
		setPath("2.2/tags");
		setDefaultOrder(OrderType.ASC);
		setDefaultSort("name");
	}

	@Override
	public Set<String> getSupportedTypes() {
		return Collections.singleton("tags");
	}

	@Override
	protected void setParameters(StackOverflowQuery query, String... args) throws QueryConstructionException {
		super.setParameters(query, args);
		if (null != args && args.length > 0 && StringUtils.isNotBlank(args[0])) {
			query.setInname(args[0]);
		}
	}
}