package twistter.android.client.activities;

import twistter.android.client.R;
import twistter.android.client.ws.interfaces.HessianServiceProvider;
import twistter.android.client.ws.interfaces.UpdateStatusWebService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateStatusActivity extends Activity {
   
	private String UPDATE_STATUS_WEB_SERVICE_URL; 
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_status_layout);

        UPDATE_STATUS_WEB_SERVICE_URL = "http://" + getString(R.string.ServerIP) + ":" 
    	+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.UpdateStatusWebService);
        
        Button tweetButton = (Button) findViewById(R.id.tweet_button);
        
        final EditText statusText = (EditText) findViewById(R.id.statusText);
        final TextView textView = (TextView)findViewById(R.id.counter);
        statusText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                int charCount = statusText.getText().toString().length();
                final int charsLeft = 140 - charCount;
                textView.setText(String.valueOf(charsLeft) + " characters left");
                if(charsLeft < 0 ) {
                	textView.setTextColor(Color.RED);                	
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        }); 

        tweetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String response = null;
            	final String msg = statusText.getText().toString();
            	
            	if(msg.toString().length() > 140){
            		Toast.makeText(getApplicationContext(),"Your message is to long, please make it shorter.",Toast.LENGTH_LONG).show();
            		return;
            	}
            	
            	try {
            		final String username = getSharedPreferences(getString(R.string.PrefsName), MODE_PRIVATE).getString(getString(R.string.PrefUserName), null);
            		
            		UpdateStatusWebService updateStatusWebService = HessianServiceProvider.getUpdateStatusWebService(UPDATE_STATUS_WEB_SERVICE_URL, getClassLoader());
            		response = updateStatusWebService.updateStatus(username, msg);
            		
            	    if(response != null && response.contains("true")){ 
                            Toast.makeText(getApplicationContext(),"Message updated successfully",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(UpdateStatusActivity.this, TimelineActivity.class);
                            UpdateStatusActivity.this.startActivity(myIntent);
                		}
            	} catch (Exception e) {
            		e.printStackTrace();
            		Toast.makeText(getApplicationContext(),"Couldn't connect to server. Please try again later.",Toast.LENGTH_LONG).show();
            	}   	
            } 
        });
    }
    

}
