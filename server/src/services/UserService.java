package services;

import com.sun.istack.internal.NotNull;


public class UserService {

	public static UserService singleInstance;
	
	private UserService() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean login(@NotNull String username,@NotNull String password){
		return (username != null && username.equals("jivimberg"));
	}
	
	public static UserService getInstance(){
		if(singleInstance == null){
			singleInstance = new UserService();
		}
		return singleInstance;
	}
}
