package com.hazehorizon.ax.connector.stackoverflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.hazehorizon.ax.AbstractQuery;
import com.hazehorizon.ax.AbstractResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractStackOverflowResult<T extends AbstractStackOverflowResult.AbstractItem> extends AbstractResult {
	private Boolean hasMore;
	private List<T> items;

	void assignQuery(AbstractQuery query) {
		super.setQuery(query);
	}
	
	public void merge(AbstractStackOverflowResult<T> another, int count) {
		boolean leftItems = count < another.items.size();
		count = Math.min(count, another.items.size());
		List<T> mList = new ArrayList<>(this.items.size() + count);
		mList.addAll(this.items);
		mList.addAll(another.items.subList(0, count));
		this.items = mList;
		this.hasMore = leftItems || another.hasMore;
	}
	
	@JsonProperty("has_more")
	public Boolean getHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}
	
	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		if (CollectionUtils.isNotEmpty(this.items)) {
			for (AbstractItem item: this.items) {
				if (ret.length() > 0) {
					ret.append("\n");
				}
				ret.append(item.toString());
			}
		}
		return ret.toString();
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	protected static abstract class AbstractItem { 
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
