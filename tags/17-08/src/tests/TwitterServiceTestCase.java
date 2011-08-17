package tests;

import java.util.List;

import junit.framework.TestCase;
import services.TwitterService;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONException;
import twitter4j.json.DataObjectFactory;

public class TwitterServiceTestCase extends TestCase {

	private TwitterService twitterService = TwitterService.getInstance();
	private final static String userId = "jivimberg";
	
	public void testUseAccessToken() throws IllegalStateException, TwitterException{
		assertEquals("The returned user name is not the expected", "Juan Ignacio Vimberg" , twitterService.getName(userId));
	}
	
	public void testGetTimeline() throws TwitterException{
		final List<Status> timeline = twitterService.getHomeTimeline(userId);
		assertFalse("The timeline is empty", timeline.isEmpty());
		assertTrue("The timeline size is not the expected", 20 >= timeline.size());
		
		System.out.println("Timeline");
		for (Status status : timeline) {
			System.out.println(status.getUser().getName() + "\n");
			System.out.println(status.getText() + "\n");
			System.out.println("-------------------------------");
		}
	}
	
	public void testGetJSONTimeline() throws TwitterException, JSONException{
		final List<String> timeline = twitterService.getJSONHomeTimeline(userId);
		assertFalse("The timeline is empty", timeline.isEmpty());
		assertTrue("The timeline size is not the expected", 20 >= timeline.size());
		
		for (String string : timeline) {
			System.out.println(string + "\n");
			
			Status statusFromJSON = DataObjectFactory.createStatus(string);
			System.out.println(statusFromJSON.getUser().getName() + "\n");
			System.out.println(statusFromJSON.getText() + "\n");
			System.out.println("-------------------------------");
		}
	}
	
}
