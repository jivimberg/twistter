package services;

import com.sun.istack.internal.NotNull;


public class UserService {

	public boolean login(@NotNull String username,@NotNull String password){
		return (username != null && password != null 
				&& username.equals("jivimberg") && password.equals("12345"));
	}
}
