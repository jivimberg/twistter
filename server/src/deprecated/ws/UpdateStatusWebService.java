package deprecated.ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import services.TwitterService;
import services.UserService;
import twitter4j.TwitterException;

@Path("/getTimeline")
public class UpdateStatusWebService {
	
	private final TwitterService twitterService = TwitterService.getInstance();
	private final UserService userService = UserService.getInstance();

	
	@POST
	public void updateStatus(@FormParam("username") String username, @FormParam("message") String message){
		if(userService.isRegistered(username)){
			//TODO fail
		}
		
		try {
			twitterService.updateStatus(message, username);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
