package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class GenericServlet extends HttpServlet {

	private static final long serialVersionUID = 2483902181864313142L;

	public abstract void doPost(final HttpServletRequest req, HttpServletResponse res);
	
	public void doGet (final HttpServletRequest req, HttpServletResponse res) {
		this.doPost(req, res);
	}

}
