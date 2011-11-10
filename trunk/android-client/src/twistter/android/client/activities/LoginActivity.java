package twistter.android.client.activities;

import twistter.android.client.R;
import twistter.android.client.ws.interfaces.HessianServiceProvider;
import twistter.android.client.ws.interfaces.LoginWebService;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {
   
	private String LOGIN_WEBSERVICE; 
	EditText un,pw;
    Button ok;
    Button register;
    ImageView logo;
    CheckBox saveuser;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.main);
        un=(EditText)findViewById(R.id.edittext_username);
        //pw=(EditText)findViewById(R.id.edittext_password);
        ok=(Button)findViewById(R.id.button_login);
        register = (Button) findViewById(R.id.button_register);
        logo = (ImageView) findViewById(R.id.logo);
        saveuser = (CheckBox) findViewById(R.id.saveuser);
        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.PrefsName), MODE_PRIVATE); 
        
        LOGIN_WEBSERVICE = "http://" + getString(R.string.ServerIP) + ":" 
    	+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.LoginWebService);

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	String response;
            	try {
            		LoginWebService loginWebService = HessianServiceProvider.getLoginWebService(LOGIN_WEBSERVICE, getClassLoader());
            		response = loginWebService.isRegistered(un.getText().toString());
            		Log.i("response", response);
            	    
            	    if(response != null && response.equals("true")){ 
            	    	//saving username
            	    	sharedPreferences
                                .edit()
                                .putString(getString(R.string.PrefUserName), un.getText().toString())
                                .commit();
	        	    	Intent myIntent = new Intent(LoginActivity.this, TimelineActivity.class);
	        	    	LoginActivity.this.startActivity(myIntent);
	                    Toast.makeText(getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                	}else{
                        Toast.makeText(getApplicationContext(),"Incorrect Username",Toast.LENGTH_LONG).show();

                	}
            	} catch (Exception  e) {
            		Toast.makeText(getApplicationContext(),"Couldn't connect to server: "
            				+ LOGIN_WEBSERVICE + ". Try again later.",Toast.LENGTH_LONG).show();
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
        
        un.setText(sharedPreferences.getString(getString(R.string.PrefUserName), null));
    }
    

}
