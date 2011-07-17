package daos;

import java.util.List;

import filters.TimelineFilter;

public interface FilterDAO {

	List<TimelineFilter> getFiltersFromUser(String username);
}
