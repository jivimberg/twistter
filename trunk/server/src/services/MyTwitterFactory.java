package services;
import data.ConsumerData;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


public class MyTwitterFactory {
	
	public static Twitter createAuthenticatedService(){
		final TwitterFactory myTwitterFactory = new TwitterFactory();
		//Configuration is taken from the file: twitter4j.properties
		final Twitter myTwitter = myTwitterFactory.getInstance();
		return myTwitter;
	}
	
}
