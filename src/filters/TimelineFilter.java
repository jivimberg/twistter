package filters;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public abstract class TimelineFilter {
	
	public List<Status> filter(List<Status> statuses){
		List<Status> filteredStatuses = new ArrayList<Status>();
		for (Status status : statuses) {
			if(accept(status)) {
				filteredStatuses.add(0, status);
			}
		}
		return filteredStatuses;
	}
	
	protected abstract boolean accept(Status status);

}
