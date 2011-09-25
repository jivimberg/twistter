package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.FilterService;
import services.TwitterService;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import utils.JSONUtils;

public class TimelineServlet extends GenericServlet {

	private static final long serialVersionUID = 3313648362012066879L;
	private HttpSession session;
	private String method;
	private TwitterService twitterService = TwitterService.getInstance();
	private FilterService filterService = FilterService.getInstance();
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		session = req.getSession(false);
		method = (String) req.getParameter("method");
		String userId = (String) session.getAttribute("username");
		if ( userId != null) {
			List<String> jsonTimeline = null;
			System.out.println("."+method+".");
			if(method.equals("getTimeline")) {
				try {
					List<Status> timeline = twitterService.getHomeTimeline(userId);
					List<Status> filteredTimeline = filterService.filter(userId, timeline);
					System.out.println(timeline.size() - filteredTimeline.size() + " tweets filtered");
					jsonTimeline = JSONUtils.getJSON(filteredTimeline);
					
					JSONArray jsonArray = new JSONArray(jsonTimeline);
					
					res.getWriter().write(jsonArray.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}

}
