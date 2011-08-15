package utils;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONException;
import twitter4j.json.DataObjectFactory;

public class JSONUtils {
	
	public static String writeStatusToJSON(Status status) throws JSONException{
		return DataObjectFactory.getRawJSON(status);
	}

	public static List<String> getJSON(List<Status> timeline) throws TwitterException, JSONException{
		List<String> jsonTimeline = new ArrayList<String>();
		
		for (Status status : timeline) {
			jsonTimeline.add(JSONUtils.writeStatusToJSON(status));
		}
		
		return jsonTimeline;
	}
}
