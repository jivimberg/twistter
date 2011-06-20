package servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends GenericServlet {

	private static final long serialVersionUID = 4003175649047885489L;
	private String username;
	private String password;
	private HttpSession session;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		username = (String) req.getParameter("username");
		password = (String) req.getParameter("password");
		session = req.getSession(true);
		try{
			if(password.equals("12345")) {
				if(session != null) {
					session.setAttribute("username", username.trim());
					session.setAttribute("password", password.trim());
					res.getWriter().write("true");
				}
			}			
		}catch (IOException e) {
			try {
				res.getWriter().write("false");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	

}
