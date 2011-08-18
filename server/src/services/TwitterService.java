package services;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import twitter4j.Paging;
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
	private static TwitterService singleInstance;
	
	private TwitterService(){
		myTwitter = MyTwitterFactory.createAuthenticatedService();
		accessTokenDAO = new FileAccessTokenDAO();
	}
	
	public static TwitterService getInstance(){
		if(singleInstance == null){
			singleInstance = new TwitterService();
		}
		return singleInstance;
	}
	
	public RequestToken getRequestToken() throws TwitterException{
		//Consumer request Token
		RequestToken requestToken = myTwitter.getOAuthRequestToken();
		return requestToken;
	}
	
	//The request token passed here must be the one used to get the authorization URL
	public void persistAccessToken(AccessToken accessToken) throws TwitterException{
		//TODO ver como se resuelve
		final User user = myTwitter.showUser(accessToken.getUserId());
		final String userId = user.getScreenName();
		accessTokenDAO.persist(accessToken, userId); 
		useAccessToken(userId);
	}
	
	private void useAccessToken(String userId){
			final AccessToken accessToken = accessTokenDAO.getAccessToken(userId);
			myTwitter.setOAuthAccessToken(accessToken);
	}

	public List<Status> getHomeTimeline(@NotNull String userId) throws TwitterException{
		useAccessToken(userId);
		
		final Paging paging = new Paging (1,20);
		return myTwitter.getHomeTimeline(paging);
	}
	
	public List<String> getJSONHomeTimeline(@NotNull String userId) throws TwitterException, JSONException{
		useAccessToken(userId);
		
		final Paging paging = new Paging (1,20);
		List<Status> timeline = myTwitter.getHomeTimeline(paging);
	
		List<String> jsonTimeline = new ArrayList<String>();
		for (Status status : timeline) {
			jsonTimeline.add(JSONUtils.writeStatusToJSON(status));
		}
		
		return jsonTimeline;
	}
	
	public void updateStatus(@NotNull String msg, @NotNull String userId) throws TwitterException{
		useAccessToken(userId);
		System.out.println(msg);
		myTwitter.updateStatus(msg);
	}
	
	public String getName(@NotNull String userId) throws IllegalStateException, TwitterException{
		useAccessToken(userId);
		User user = myTwitter.showUser(myTwitter.getId());
		return user.getName();
	}

}
