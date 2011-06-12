package tests;

import services.TwitterService;
import twitter4j.TwitterException;
import junit.framework.TestCase;

public class TwitterServiceTestCase extends TestCase {

	private TwitterService twitterService;
	
	public void testUseAccessToken() throws IllegalStateException, TwitterException{
		final String userId = "jivimberg";
		twitterService = new TwitterService(userId);
		
		assertEquals("The returned user name is not the expected", "Juan Ignacio Vimberg" , twitterService.getName());
	}
}
