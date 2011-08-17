package daos.impl;

import java.util.ArrayList;
import java.util.List;

import daos.FilterDAO;
import filters.TimelineFilter;
import filters.impl.WordFilter;

public class MockFilterDao implements FilterDAO{

	@Override
	public List<TimelineFilter> getFiltersFromUser(String username) {
		List<TimelineFilter> filters = new ArrayList<TimelineFilter>();
		
		TimelineFilter wordFilter = new WordFilter("Estudiando");
		filters.add(wordFilter);
		
		return filters;
	}

}
