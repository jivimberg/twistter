package tests;

import java.util.List;

import junit.framework.TestCase;
import services.TwitterService;
import twitter4j.Status;
import twitter4j.TwitterException;
import filters.impl.WordFilter;

public class FilterTestCase extends TestCase{
	
private TwitterService twitterService;
	
	//TODO this is not yet an automatic test case. Perhaps we should mock Status
	public void testWordTestCase() throws TwitterException{
		twitterService = new TwitterService("jivimberg");
		final String filteredWord = "influencias";
		
		final List<Status> timeline = twitterService.getHomeTimeline();
		assertEquals("The timeline size is not the expected", 20, timeline.size());
		
		final WordFilter wordFilter = new WordFilter(filteredWord);
		
		final List<Status> filteredTimeline = wordFilter.filter(timeline);
		assertEquals("The filtered timeline size is not the expected", 19, filteredTimeline.size());
	}

}
