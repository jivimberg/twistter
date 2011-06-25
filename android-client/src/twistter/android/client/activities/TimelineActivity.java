package twistter.android.client.activities;


import org.json.JSONArray;

import twistter.android.client.R;
import twistter.android.client.R.id;
import twistter.android.client.R.layout;
import twistter.android.client.services.TimelineService;
import twistter.android.client.utils.TwitterUtils;
import twitter4j.Status;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class TimelineActivity extends Activity{
	
	Intent timelineService;
	private Handler myHandler;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline); //Agregar el nuevo activity
        startService();
        
        myHandler = new Handler(){
        	
			public void handleMessage(Message msg) {
				try{
					JSONArray jsonArray = (JSONArray) msg.obj;
					
	    			final int qtyOfTweets = jsonArray.length(); 
	    			LinearLayout scroll = (LinearLayout) findViewById(R.id.timeline);
	    			
	    			//Borrar los tweets antes almacenados
	    			scroll.removeAllViews();
	    			
	    			for (int i = 0; i < qtyOfTweets; i++) {
	    				View inflatedView = View.inflate(TimelineService.ACTIVIDAD, R.layout.status, null);
	    				 
	    				TextView tweet_username = (TextView) inflatedView.findViewById(R.id.tweet_username);
	    				TextView tweet_text = (TextView) inflatedView.findViewById(R.id.tweet_text);
	    				ImageView tweet_user_image = (ImageView) inflatedView.findViewById(R.id.tweet_user_image);
	    			
	    				String rawJson = jsonArray.getString(i);
	    				Status status = TwitterUtils.getStatusFromJSON(rawJson);
	    				
	    				try{
	    					Drawable profilePicture = drawable_from_url(status.getUser().getProfileImageURL().toString(), "src");
	    					tweet_user_image.setBackgroundDrawable(profilePicture);    	    					
	    				}catch (Exception e) {
	    					Log.i(getClass().getSimpleName(), "No se pudo cargar la imagen");
						}
	    				tweet_username.setText(status.getUser().getName());
	    				tweet_text.setText(status.getText());
	    				
	    				scroll.addView(inflatedView);
	    			}
    				

	    			
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    		
	    	}
			
			android.graphics.drawable.Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
			    return android.graphics.drawable.Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
			}
	    };
	}
	
	private void startService(){
	    TimelineService.ACTIVIDAD=this;

	    try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio desde el login...");
		    timelineService = new Intent(this, TimelineService.class);

		    if(startService(timelineService)==null){
                this.notificar("No se ha podido iniciar el servicio");

		    }
		    else{

                this.notificar("Servicio iniciado correctamente");
		    }
	    }
	    catch(Exception e){
	    	this.notificar(e.getMessage());
	    }

    }
    
    private void notificar(String cadena)
	{
        Toast.makeText(getApplicationContext(),cadena,Toast.LENGTH_SHORT).show();

	}
    
    public void onDestroy(){
    	super.onDestroy();
    	stopService(timelineService);
    }

	public Handler getMyHandler() {
		return myHandler;
	}

    
    
}
