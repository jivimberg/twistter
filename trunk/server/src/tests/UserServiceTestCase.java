package tests;

import services.UserService;
import junit.framework.TestCase;

public class UserServiceTestCase extends TestCase {

	final UserService userService = new UserService();
	
	public void testLogin(){
		assertTrue("Login should have been successful", userService.login("jivimberg", "12345"));
		assertFalse("Login should have failed, wrong username", userService.login("0000", "12345"));
		assertFalse("Login should have failed, wrong password", userService.login("jivimberg", "0000"));
		assertFalse("Login should have failed, null password", userService.login("jivimberg", null));
		assertFalse("Login should have failed, null username", userService.login("jivimberg", null));
	}
}
