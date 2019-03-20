package com.hazehorizon.ax.connector.stackoverflow;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hazehorizon.ax.QueryConstructionException;

public class BadgesConnector extends AbstractStackOverflowConnector {
	private static final Set<String> RANKS = new HashSet<>();

	static {
		Collections.addAll(RANKS, "gold", "silver", "bronze");
	}
	
	public BadgesConnector() {
		super(BadgesResult.class);
		setPath("2.2/badges");
		setDefaultOrder(OrderType.ASC);
		setDefaultSort("rank");
		setDefaultLimit(500);
	}

	@Override
	public Set<String> getSupportedTypes() {
		return Collections.singleton("badges");
	}

	@Override
	protected void setParameters(StackOverflowQuery query, String... args)
			throws QueryConstructionException {
		super.setParameters(query, args);
		if (null == args || args.length < 1) {
			throw new QueryConstructionException("Second parameters for badges must be provided for query type " + query.getType());
		}
		else if (StringUtils.isBlank(args[0]) || !RANKS.contains(args[0]) ) {
			throw new QueryConstructionException("Undefined value for badges rank " + args[0]);
		}
		query.setMin(args[0]);
		query.setMax(args[0]);
	}
}