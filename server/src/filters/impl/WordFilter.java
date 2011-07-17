package filters.impl;

import twitter4j.Status;
import filters.TimelineFilter;

public class WordFilter extends TimelineFilter{
	
	private String filterWord;
	
	public WordFilter(String filterWord) {
		super();
		this.filterWord = filterWord;
	}

	@Override
	protected boolean accept(Status status) {
		if(status.getText().contains(filterWord)){
			return false;
		}
		return true;
	}
	
	public String getFilterWord() {
		return filterWord;
	}

	public void setFilterWord(String filterWord) {
		this.filterWord = filterWord;
	}

}
