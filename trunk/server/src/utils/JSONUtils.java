package utils;

import twitter4j.Status;
import twitter4j.internal.org.json.JSONException;
import twitter4j.json.DataObjectFactory;

public class JSONUtils {
	
	public static String writeStatusToJSON(Status status) throws JSONException{
		return DataObjectFactory.getRawJSON(status);
	}
	
}
