package daos;

import twitter4j.auth.AccessToken;

public interface AccessTokenDAO {
	
	void persist(AccessToken accessToken, String userId);

	AccessToken getAccessToken(String userId);
	
	boolean existAccessToken(String userId);

}
