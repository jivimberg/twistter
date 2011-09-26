package hessianws.impl;

import hessianws.UpdateStatusWebService;
import services.TwitterService;
import services.UserService;
import twitter4j.TwitterException;

import com.caucho.hessian.server.HessianServlet;

public class UpdateStatusWebServiceImpl extends HessianServlet implements UpdateStatusWebService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5899174053106000030L;
	
	private final TwitterService twitterService = TwitterService.getInstance();
	private final UserService userService = UserService.getInstance();

	
	public String updateStatus(String username, String message){
		String result;
		if(!userService.isRegistered(username)){
			result = "User is not registered!";
			System.out.println(result);
			return result;
		}
		
		try {
			twitterService.updateStatus(message, username);
			result = "true";
			return result;
		} catch (TwitterException e) {
			e.printStackTrace();
			result = "Sorry there was a problem :(";
			return result;
		}
	}
}
