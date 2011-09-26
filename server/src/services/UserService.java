package services;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import daos.AccessTokenDAO;
import daos.impl.FileAccessTokenDAO;


public class UserService {

	public static UserService singleInstance;
	public static AccessTokenDAO accessToken;
	
	private UserService() {
		accessToken = new FileAccessTokenDAO();
	}
	
	public boolean isRegistered(@NotNull String username, @Nullable String password){
		return (username != null && accessToken.existAccessToken(username));
	}
	
	public boolean isRegistered(@NotNull String username){
		return isRegistered(username, null);
	}
	
	public static UserService getInstance(){
		if(singleInstance == null){
			singleInstance = new UserService();
		}
		return singleInstance;
	}
}
