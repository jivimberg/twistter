package twistter.android.client.utils;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.json.DataObjectFactory;

public class TwitterUtils {
	
	public static Status getStatusFromJSON(String json) throws TwitterException{
		return DataObjectFactory.createStatus(json);
	}

}
