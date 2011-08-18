package twistter.android.client.activities;

import twistter.android.client.R;
import twistter.android.client.utils.MyHttpClient;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OAuthRegister extends Activity {

	private static final Uri CALLBACK_URL = Uri.parse("twistter://register");
	private static final String CONSUMER_KEY = "1fJQ3sBPrFtXCPYQIgIW9w";
	private static final String CONSUMER_SECRET =  "Y9ESDVow06aPd4v8uuP5mmVoEYAoq32QjhBPzz9u24";
	private Twitter twitter;
	private RequestToken requestToken;
	private String LOGIN_SERVLET_URL;
	
	
    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        
        LOGIN_SERVLET_URL = "http://" + getString(R.string.ServerIP) + ":" 
    	+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.RegisterServlet);
        
        //initiate twitter
        twitter = createAuthenticatedService();
        try {
			requestToken = twitter.getOAuthRequestToken(CALLBACK_URL.toString());
		} catch (TwitterException e) {
			Log.e(getClass().getSimpleName(), e.toString());
			e.printStackTrace();
		}

		//open browser with oAuthURL
        String oAuthURL = requestToken.getAuthorizationURL();
		this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(oAuthURL)));
		
    }

    protected void onNewIntent(Intent intent) { 
    	super.onNewIntent(intent);

    	Uri uri = intent.getData(); 
    	try{
    		if (uri != null && uri.toString().startsWith(CALLBACK_URL.toString())) {
    			String verifier = uri.getQueryParameter("oauth_verifier");
    			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
    			
    			//TODO Send Token to server for storage!
    	    	MyHttpClient.sendObjectToServer(LOGIN_SERVLET_URL, accessToken);
    			
    			String userName = twitter.showUser(accessToken.getUserId()).getName();
    			Toast.makeText(getApplicationContext(),"Thanks " + userName + " you have been successfully registered",Toast.LENGTH_SHORT).show();
    		}    		
    	}catch (TwitterException e) {
			Log.e(getClass().getName(), e.getErrorMessage());
		} finally{
			Intent myIntent = new Intent(OAuthRegister.this, LoginActivity.class);
			startActivity(myIntent);
		}
    }
   
    public static Twitter createAuthenticatedService(){
    	ConfigurationBuilder configurationBuilder = new ConfigurationBuilder(); 
    	configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY); 
    	configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET); 
    	Configuration configuration = configurationBuilder.build(); 
		return new TwitterFactory(configuration).getInstance(); 
	}
}
