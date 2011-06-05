package services;

import java.util.List;

import daos.AccessTokenDAO;
import daos.impl.FileAccessTokenDAO;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterService {
	
	private Twitter myTwitter;
	private AccessTokenDAO accessTokenDAO;
	
	public TwitterService(){
		myTwitter = MyTwitterFactory.createAuthenticatedService();
		accessTokenDAO = new FileAccessTokenDAO();
	}
	
	public RequestToken getRequestToken() throws TwitterException{
		//Consumer request Token
		RequestToken requestToken = myTwitter.getOAuthRequestToken();
		return requestToken;
	}
	
	//The request token passed here should be the same that was used to get the authorization URL
	public void persistAccessToken(String userId, String requestToken, String pin) throws TwitterException{
		AccessToken accessToken = myTwitter.getOAuthAccessToken(requestToken, pin);
		myTwitter.setOAuthAccessToken(accessToken);		
		accessTokenDAO.persist(accessToken, userId); 		
	}
	
	public void useAccessToken(String userId){
			AccessToken accessToken = accessTokenDAO.getAccessToken(userId);
			myTwitter.setOAuthAccessToken(accessToken);
	}

	public List<Status> getHomeTimeline() throws TwitterException{
		return myTwitter.getHomeTimeline();
	}
	
	public void updateStatus(String msg) throws TwitterException{
		myTwitter.updateStatus(msg);
	}
	
	public String getName() throws IllegalStateException, TwitterException{
		User user = myTwitter.showUser(myTwitter.getId());
		return user.getName();
	}

}
