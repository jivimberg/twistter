package twistter.android.client.activities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import twistter.android.client.R;
import twistter.android.client.utils.MyHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateStatusActivity extends Activity {
   
	private String UPDATE_STATUS__SERVLET_URL; 
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_status_layout);

        UPDATE_STATUS__SERVLET_URL = "http://" + getString(R.string.ServerIP) + ":" 
    	+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.UpdateStatusServlet);
        
        Button tweetButton = (Button) findViewById(R.id.tweet_button);

        tweetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String response = null;
            	try {
            		EditText statusText = (EditText) findViewById(R.id.statusText);
            		String msg = statusText.getText().toString();
            		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                	postParameters.add(new BasicNameValuePair("message", msg));
            	    response = MyHttpClient.executeHttpPost(UPDATE_STATUS__SERVLET_URL, postParameters);
            	    if(response != null && response.contains((String)"true")){ 
                            Toast.makeText(getApplicationContext(),"Message updated successfully",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(UpdateStatusActivity.this, TimelineActivity.class);
                            UpdateStatusActivity.this.startActivity(myIntent);
                		}
            	} catch (Exception e) {
            		Toast.makeText(getApplicationContext(),"Couldn't connect to server: "
            				+ UPDATE_STATUS__SERVLET_URL + ". Try again later.",Toast.LENGTH_LONG).show();
            	}   	
            } 
        });
    }
    

}
