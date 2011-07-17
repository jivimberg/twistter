package services;

import java.util.List;

import twitter4j.Status;
import daos.FilterDAO;
import daos.impl.MockFilterDao;
import filters.TimelineFilter;

public class FilterService {
	
	FilterDAO filterDao = new MockFilterDao();
	
	public List<Status> filter(String username, List<Status> timeline){
		final List<TimelineFilter> filters = filterDao.getFiltersFromUser(username);
		List<Status> filteredTimeline = timeline;
		
		for (TimelineFilter timelineFilter : filters) {
			filteredTimeline = timelineFilter.filter(filteredTimeline);
		}
		
		return filteredTimeline;
	}

}
