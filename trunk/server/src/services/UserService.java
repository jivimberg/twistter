package services;

import com.sun.istack.internal.NotNull;

import daos.AccessTokenDAO;
import daos.impl.FileAccessTokenDAO;


public class UserService {

	public static UserService singleInstance;
	public static AccessTokenDAO accessToken;
	
	private UserService() {
		accessToken = new FileAccessTokenDAO();
	}
	
	public boolean login(@NotNull String username,@NotNull String password){
		return (username != null && accessToken.existAccessToken(username));
	}
	
	public static UserService getInstance(){
		if(singleInstance == null){
			singleInstance = new UserService();
		}
		return singleInstance;
	}
}
