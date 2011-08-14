package tests;

import java.util.List;

import junit.framework.TestCase;
import services.TwitterService;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONException;
import twitter4j.json.DataObjectFactory;
import utils.JSONUtils;

public class JSONTestCase extends TestCase {

	private TwitterService twitterService;
	
	public void testsStatusToJSON() throws TwitterException, JSONException{
		twitterService = new TwitterService("jivimberg");
		
		List<Status> timeline = twitterService.getHomeTimeline();
		Status status = timeline.get(0);
		System.out.println(status.getText());
		
		String statusRawJSON = JSONUtils.writeStatusToJSON(status);
		System.out.println(statusRawJSON);
		
		Status statusFromJSON = DataObjectFactory.createStatus(statusRawJSON);
		assertEquals("The builded tweet is not the expected" , status, statusFromJSON);
	}
}
