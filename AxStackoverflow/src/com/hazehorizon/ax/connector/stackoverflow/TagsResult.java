package com.hazehorizon.ax.connector.stackoverflow;


public class TagsResult extends AbstractStackOverflowResult<TagsResult.TagItem> {	

	public static class TagItem extends AbstractStackOverflowResult.AbstractItem {

		@Override
		public String toString() {
			return getName();
		}	
	}
}