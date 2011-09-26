package deprecated.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import services.FilterService;
import services.TwitterService;
import services.UserService;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import utils.JSONUtils;

@Path("/getTimeline/{username}")
public class TimelineWebService {
	
	private final TwitterService twitterService = TwitterService.getInstance();
	private final FilterService filterService = FilterService.getInstance();
	private final UserService userService = UserService.getInstance();


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTimeline(@PathParam("username") String username){
		//check user is registered
		if(userService.isRegistered(username)){
			//TODO fail
			return "User is not registered";
		}

		List<Status> timeline;
		try {
			timeline = twitterService.getHomeTimeline(username);
			List<Status> filteredTimeline = filterService.filter(username, timeline);
			
			System.out.println(timeline.size() - filteredTimeline.size() + " tweets filtered");
			
			List<String> jsonTimeline = JSONUtils.getJSON(filteredTimeline);
			JSONArray jsonArray = new JSONArray(jsonTimeline);
			return jsonArray.toString();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		
	}

}
