package hessianws.impl;

import hessianws.TimelineWebService;

import java.util.List;

import com.caucho.hessian.server.HessianServlet;

import services.FilterService;
import services.TwitterService;
import services.UserService;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import utils.JSONUtils;

public class TimelineWebServiceImpl extends HessianServlet implements TimelineWebService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8316950981444872232L;
	
	private final TwitterService twitterService = TwitterService.getInstance();
	private final FilterService filterService = FilterService.getInstance();
	private final UserService userService = UserService.getInstance();


	/* (non-Javadoc)
	 * @see hessianws.impl.TimelineWebServiceInterface#getTimeline(java.lang.String)
	 */
	public String getTimeline(String username, Long sinceId){
		//check user is registered
		if(!userService.isRegistered(username)){
			//TODO fail
			System.out.println("User is not registered!");
			return "User is not registered";
		}

		List<Status> timeline;
		try {
			timeline = twitterService.getHomeTimeline(username, sinceId);
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
