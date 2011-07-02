package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.TwitterService;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;

public class TimelineServlet extends GenericServlet {

	private static final long serialVersionUID = 3313648362012066879L;
	private HttpSession session;
	private String method;
	private TwitterService service = new TwitterService();
	
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
					service.useAccessToken(userId);
					jsonTimeline = service.getJSONHomeTimeline();
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
