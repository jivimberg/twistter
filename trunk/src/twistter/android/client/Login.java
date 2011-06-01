package twistter.android.client;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
    EditText un,pw;
	TextView error;
    Button ok;
    ImageView logo;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        un=(EditText)findViewById(R.id.edittext_username);
        pw=(EditText)findViewById(R.id.edittext_password);
        ok=(Button)findViewById(R.id.button_login);
        error=(TextView)findViewById(R.id.textview_error);
        logo = (ImageView) findViewById(R.id.logo);

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            	if(pw.getText().toString().equals("pass")){
            	    	error.setText("GOL");
            	    	Timeline timeline = new Timeline();
            	    	
            	}else{
            	    	error.setText("Incorrect Username or Password");
            	}
            } 
        	
            
        });
        
        
    }
    

}
