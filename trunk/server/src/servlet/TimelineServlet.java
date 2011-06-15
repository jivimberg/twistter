package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.TwitterService;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONException;

public class TimelineServlet extends GenericServlet {

	private static final long serialVersionUID = 3313648362012066879L;
	private HttpSession session;
	private String method;
	private TwitterService service = new TwitterService();
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		session = req.getSession(false);
		method = (String) req.getAttribute("method");
		if (session.getAttribute("username") != null) {
			String timeline = "";
			List<String> jsonTimeline = null;
			System.out.println("."+method+".");
			if(true) {
				try {
					service.useAccessToken((String)session.getAttribute("username"));
					timeline = makeStringTimelineForResponse(service.getJSONHomeTimeline());
				} catch (TwitterException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					res.getWriter().write(" holas");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	private String makeStringTimelineForResponse(List<String> jsonTimeline) {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < jsonTimeline.size()-1; i++) {
			buffer.append(jsonTimeline.get(i));
			buffer.append("&&&");
		}
		return buffer.toString();
	}

}
