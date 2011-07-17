package tests;

import java.util.List;

import junit.framework.TestCase;

import org.json.JSONArray;

import services.TwitterService;
import twitter4j.Status;
import twitter4j.TwitterException;

public class JSONArrayTestCase extends TestCase{

	private TwitterService twitterService;
	
	public void testJSONArray() throws TwitterException{
		twitterService = new TwitterService("jivimberg");
		
		List<Status> timeline = twitterService.getHomeTimeline();
		
		JSONArray jsonArray = new JSONArray(timeline);
		System.out.println(jsonArray);
	}
}
