package hessianws.impl;

import hessianws.LoginWebService;
import services.UserService;

import com.caucho.hessian.server.HessianServlet;

public class LoginWebServiceImpl extends HessianServlet implements LoginWebService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3223091672965455055L;
	
	private final UserService userService = UserService.getInstance();
	
	/* (non-Javadoc)
	 * @see hessianws.impl.LoginWebServiceInterface#isRegistered(java.lang.String)
	 */
	public String isRegistered(String username){
		System.out.println(".Login.");
		return (userService.isRegistered(username)) ? "true" : "false";
	}

}
 