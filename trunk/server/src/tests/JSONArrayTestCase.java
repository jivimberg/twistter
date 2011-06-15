package tests;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;

import services.TwitterService;
import twitter4j.Status;
import twitter4j.TwitterException;
import junit.framework.TestCase;

public class JSONArrayTestCase extends TestCase{

	private TwitterService twitterService;
	
	public void testJSONArray() throws TwitterException{
		twitterService = new TwitterService("jivimberg");
		
		List<Status> timeline = twitterService.getHomeTimeline();
		
		JSONArray jsonArray = new JSONArray(timeline);
		System.out.println(jsonArray);
		
		
	}
}
