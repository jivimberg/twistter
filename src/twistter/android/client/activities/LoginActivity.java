package twistter.android.client.activities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import twistter.android.client.R;
import twistter.android.client.utils.MyHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {
   
	private String LOGIN_SERVLET_URL; 
	EditText un,pw;
    Button ok;
    Button register;
    ImageView logo;
    CheckBox savepass;
    
    
    public static final String PREFS_NAME = "TwistterLoginPref";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.main);
        un=(EditText)findViewById(R.id.edittext_username);
        pw=(EditText)findViewById(R.id.edittext_password);
        ok=(Button)findViewById(R.id.button_login);
        register = (Button) findViewById(R.id.button_register);
        logo = (ImageView) findViewById(R.id.logo);
        savepass = (CheckBox) findViewById(R.id.savepass);
        
        LOGIN_SERVLET_URL = "http://" + getString(R.string.ServerIP) + ":" 
    	+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.LoginServlet);

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	
            	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            	
            	postParameters.add(new BasicNameValuePair(PREF_USERNAME, un.getText().toString()));
            	postParameters.add(new BasicNameValuePair(PREF_PASSWORD, pw.getText().toString()));

            	String response = null;
            	try {
            	    response = MyHttpClient.executeHttpPost(LOGIN_SERVLET_URL, postParameters);
            	    if(response != null && response.contains((String)"true")){ 
                		if(savepass.isChecked()){
                            getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                                .edit()
                                .putString(PREF_USERNAME, un.getText().toString())
                                .putString(PREF_PASSWORD, pw.getText().toString())
                                .commit();
                            Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                            //Ver si sacamos el toast
                		}
	        	    	Intent myIntent = new Intent(LoginActivity.this, TimelineActivity.class);
	        	    	LoginActivity.this.startActivity(myIntent);
	                    Toast.makeText(getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                	}else{
                        Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_LONG).show();

                	}
            	} catch (Exception e) {
            		Toast.makeText(getApplicationContext(),"Couldn't connect to server: "
            				+ LOGIN_SERVLET_URL + ". Try again later.",Toast.LENGTH_LONG).show();
            	}   	
            } 
        });
        
        register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(LoginActivity.this, OAuthRegister.class);
    	    	LoginActivity.this.startActivity(myIntent);
			}
        	
        });
        
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);   
        un.setText(pref.getString(PREF_USERNAME, null));
        pw.setText(pref.getString(PREF_PASSWORD, null));
    }
    

}
