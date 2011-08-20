package tests;

import services.UserService;
import junit.framework.TestCase;

public class UserServiceTestCase extends TestCase {

	final UserService userService = UserService.getInstance();
	
	public void testLogin(){
		assertTrue("Login should have been successful", userService.login("jivimberg", "12345"));
		assertFalse("Login should have failed, wrong username", userService.login("0000", "12345"));
		assertTrue("Login shouldn't have failed, wrong password", userService.login("jivimberg", "0000"));
		assertTrue("Login shouldn't have failed, null password", userService.login("jivimberg", null));
		assertFalse("Login should have failed, null username", userService.login(null, "12345"));
	}
}
