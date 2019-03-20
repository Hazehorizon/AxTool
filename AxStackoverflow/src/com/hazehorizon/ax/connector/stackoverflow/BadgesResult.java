package com.hazehorizon.ax.connector.stackoverflow;

import org.codehaus.jackson.annotate.JsonProperty;

public class BadgesResult extends AbstractStackOverflowResult<BadgesResult.BadgeItem> {

	public static class BadgeItem extends AbstractStackOverflowResult.AbstractItem {
		private String rank;
		private Integer awardCount;
		
		public String getRank() {
			return rank;
		}

		public void setRank(String rank) {
			this.rank = rank;
		}

		@JsonProperty("award_count")
		public Integer getAwardCount() {
			return awardCount;
		}

		public void setAwardCount(Integer awardCount) {
			this.awardCount = awardCount;
		}

		@Override
		public String toString() {
			return getName() + " - " + getAwardCount();
		}	
	}
}