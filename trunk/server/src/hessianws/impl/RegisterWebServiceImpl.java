package hessianws.impl;

import hessianws.RegisterWebService;
import services.TwitterService;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import com.caucho.hessian.server.HessianServlet;

public class RegisterWebServiceImpl extends HessianServlet implements RegisterWebService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2351520641207089394L;
	
	private final TwitterService service = TwitterService.getInstance();
	
    /* (non-Javadoc)
	 * @see hessianws.impl.RegisterWebServiceInterface#register(java.lang.String, java.lang.String)
	 */
    public String register(String token, String tokenSecret){
    	System.out.println(".register.");
		try {
			final AccessToken accessToken = new AccessToken(token, tokenSecret);
			service.persistAccessToken(accessToken);
			// TODO save token
			return "true";
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		} 
    }
}
