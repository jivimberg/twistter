package servlet;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.TwitterService;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class LoginServlet extends GenericServlet {

	private static final long serialVersionUID = 4003175649047885489L;
	private String username;
	private String password;
	private HttpSession session;

	private TwitterService service = new TwitterService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		username = (String) req.getParameter("username");
		password = (String) req.getParameter("password");
		session = req.getSession(true);

		System.out.println(".Login.");
		try {
			if (username.equals("jivimberg") && password.equals("12345")) {
				if (session != null) {
					session.setAttribute("username", username.trim());
					session.setAttribute("password", password.trim());
					res.getWriter().write("true");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
