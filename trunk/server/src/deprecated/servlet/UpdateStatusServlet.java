package deprecated.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.TwitterService;
import twitter4j.TwitterException;

public class UpdateStatusServlet extends GenericServlet {

	private static final long serialVersionUID = 4003175649047885489L;
	private HttpSession session;

	private TwitterService service = TwitterService.getInstance();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		session = req.getSession(false);
		String userId = (String) session.getAttribute("username");
		if ( userId != null) {
			System.out.println(".updateStatus.");
			String message = req.getParameter("message");
			try {
				service.updateStatus(message, userId);
				res.getWriter().write("true");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
