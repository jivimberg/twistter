package daos.impl;

import twitter4j.auth.AccessToken;
import utils.FileUtils;
import daos.AccessTokenDAO;

public class FileAccessTokenDAO implements AccessTokenDAO{

	private static final String EXTENSION = ".accessToken";

	@Override
	public void persist(AccessToken accessToken, String userId) {
		FileUtils.saveToFile(accessToken, buildFileName(userId));
	}

	@Override
	public AccessToken getAccessToken(String userId) {
		return FileUtils.<AccessToken>readFromFile(buildFileName(userId));
	}
	
	private String buildFileName(String userId) {
		return userId + EXTENSION;
	}
	

}
