package tests;

import services.UserService;
import junit.framework.TestCase;

public class UserServiceTestCase extends TestCase {

	final UserService userService = UserService.getInstance();
	
	public void testLogin(){
		assertTrue("Login should have been successful", userService.isRegistered("jivimberg", "12345"));
		assertFalse("Login should have failed, wrong username", userService.isRegistered("0000", "12345"));
		assertTrue("Login shouldn't have failed, wrong password", userService.isRegistered("jivimberg", "0000"));
		assertTrue("Login shouldn't have failed, null password", userService.isRegistered("jivimberg", null));
		assertFalse("Login should have failed, null username", userService.isRegistered(null, "12345"));
	}
}
