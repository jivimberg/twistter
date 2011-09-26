package deprecated.ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import services.TwitterService;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

@Path("/register")
public class RegisterWebService {

	private final TwitterService service = TwitterService.getInstance();
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
    public String register(@FormParam("token") String token,
						   @FormParam("tokenSecret") String tokenSecret){
    	System.out.println(".register.");
		try {
			AccessToken accessToken = new AccessToken(token, tokenSecret);
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
