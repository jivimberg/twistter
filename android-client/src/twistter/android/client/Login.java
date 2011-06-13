package twistter.android.client;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;


public class Login extends Activity {
    EditText un,pw;
	TextView error;
    Button ok;
    ImageView logo;
    CheckBox savepass;
    
    
    public static final String PREFS_NAME = "TwistterLoginPref";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        un=(EditText)findViewById(R.id.edittext_username);
        pw=(EditText)findViewById(R.id.edittext_password);
        ok=(Button)findViewById(R.id.button_login);
        error=(TextView)findViewById(R.id.textview_error);
        logo = (ImageView) findViewById(R.id.logo);
        savepass = (CheckBox) findViewById(R.id.savepass);

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	
            	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            	
            	postParameters.add(new BasicNameValuePair(PREF_USERNAME, un.getText().toString()));
            	postParameters.add(new BasicNameValuePair(PREF_PASSWORD, pw.getText().toString()));

            	String response = null;
            	try {
            	    response = TwistterHttpClient.executeHttpPost("http://L/LoginServlet", postParameters);
            	} catch (Exception e) {
            	    e.printStackTrace();
            	}   	
            	
            	if(response == "true"){

            		if(savepass.isChecked()){
                        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                            .edit()
                            .putString(PREF_USERNAME, un.getText().toString())
                            .putString(PREF_PASSWORD, pw.getText().toString())
                            .commit();
                        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                        //Ver si sacamos el toast
            		}
            	    	Timeline timeline = new Timeline(); 	
            	}else{
            	    	error.setText("Incorrect Username or Password");
            	}
            } 
        	
            
        });
        
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);   
        un.setText(pref.getString(PREF_USERNAME, null));
        pw.setText(pref.getString(PREF_PASSWORD, null));
    }
    

}