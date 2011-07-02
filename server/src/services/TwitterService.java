package services;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.internal.org.json.JSONException;
import utils.JSONUtils;
import daos.AccessTokenDAO;
import daos.impl.FileAccessTokenDAO;

public class TwitterService {
	
	private Twitter myTwitter;
	private AccessTokenDAO accessTokenDAO;
	
	public TwitterService(){
		initialize();
	}
	
	public TwitterService(String userId){
		initialize();
		useAccessToken(userId);
	}
	
	public void initialize(){
		myTwitter = MyTwitterFactory.createAuthenticatedService();
		accessTokenDAO = new FileAccessTokenDAO();
	}
	
	public RequestToken getRequestToken() throws TwitterException{
		//Consumer request Token
		RequestToken requestToken = myTwitter.getOAuthRequestToken();
		return requestToken;
	}
	
	//The request token passed here must be the one used to get the authorization URL
	public void persistAccessToken(AccessToken accessToken) throws TwitterException{
		//TODO ver como se resuelve
		myTwitter.setOAuthAccessToken(accessToken);	
		User user = myTwitter.showUser(accessToken.getUserId());
		System.out.println(user.getName());
		accessTokenDAO.persist(accessToken, user.getScreenName()); 		
	}
	
	public void useAccessToken(String userId){
			AccessToken accessToken = accessTokenDAO.getAccessToken(userId);
			myTwitter.setOAuthAccessToken(accessToken);
	}

	public List<Status> getHomeTimeline() throws TwitterException{
		return myTwitter.getHomeTimeline();
	}
	
	public List<String> getJSONHomeTimeline() throws TwitterException, JSONException{
		List<Status> timeline = myTwitter.getHomeTimeline();
	
		List<String> jsonTimeline = new ArrayList<String>();
		
		for (Status status : timeline) {
			jsonTimeline.add(JSONUtils.writeStatusToJSON(status));
		}
		
		return jsonTimeline;
	}
	
	public void updateStatus(String msg) throws TwitterException{
		myTwitter.updateStatus(msg);
	}
	
	public String getName() throws IllegalStateException, TwitterException{
		User user = myTwitter.showUser(myTwitter.getId());
		return user.getName();
	}

}
