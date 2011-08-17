package tests;

import java.util.List;

import junit.framework.TestCase;
import twitter4j.Status;
import twitter4j.TwitterException;
import utils.FileUtils;
import filters.impl.WordFilter;

public class FilterTestCase extends TestCase{
	
	//TODO this is not yet an automatic test case. Perhaps we should mock Status
	public void testWordTestCase() throws TwitterException{
		final String filteredWord = "BORT";
		
		final List<Status> timeline = loadMockTimeline();
		assertEquals("The timeline size is not the expected", 20, timeline.size());
		
		final WordFilter wordFilter = new WordFilter(filteredWord);
		
		final List<Status> filteredTimeline = wordFilter.filter(timeline);
		assertEquals("The filtered timeline size is not the expected", 19, filteredTimeline.size());
	}
	
	private List<Status> loadMockTimeline(){
		return FileUtils.readFromFile("src/tests/data/timeline");
	}

}
