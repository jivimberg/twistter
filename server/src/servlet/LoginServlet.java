package servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.UserService;

public class LoginServlet extends GenericServlet {

	private static final long serialVersionUID = 4003175649047885489L;
	private String username;
	private String password;
	private HttpSession session;
	private UserService userService = new UserService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		username = (String) req.getParameter("username");
		password = (String) req.getParameter("password");
		session = req.getSession(true);

		System.out.println(".Login.");
		try {
			if (userService.login(username, password)) {
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
