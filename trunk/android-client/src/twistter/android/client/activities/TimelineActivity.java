package twistter.android.client.activities;


import java.util.ArrayList;

import twistter.android.client.R;
import twistter.android.client.services.TimelineService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TimelineActivity extends Activity{
	
	Intent timelineService;
	private Handler myHandler;
	private ProgressDialog pd;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline); 
        startService();
        pd = ProgressDialog.show(this, "Twistter", "Loading timeline...");
        final Drawable defaultProfilePicture = getResources().getDrawable(R.drawable.defaultavatar);
        
        myHandler = new Handler(){
			public void handleMessage(Message msg) {
				dismissProgressLoader();
				ArrayList<Object> arrayMessage = (ArrayList<Object>) msg.obj;
				View statusView = (View) arrayMessage.get(0);
				
				LinearLayout scroll = (LinearLayout) findViewById(R.id.timeline);
				
				scroll.addView(statusView,0);
				
				ImageView tweet_user_image = (ImageView) statusView.findViewById(R.id.tweet_user_image);
				//temporal 
				tweet_user_image.setBackgroundDrawable(defaultProfilePicture);
//					try{
//						String url =  (String) arrayMessage.get(1);
//						
//						Drawable profilePicture = drawable_from_url(url, "image");
//						tweet_user_image.setBackgroundDrawable(profilePicture);    	    					
//					}catch (Exception e) {
//						tweet_user_image.setBackgroundDrawable(defaultProfilePicture);
//						Log.w(getClass().getSimpleName(), "No se pudo cargar la imagen");
//					}
				
				//TextView filterTweetsCounter = (TextView)  findViewById(R.id.filtered_tweets_counter);
				//filterTweetsCounter.setText(20 - statusViews.size() + " filtered");
				//toastNotify(20 - statusViews.size() + " tweets filtrados"); //TODO valor hardcodeado!
			}
			
	    };
	}
	
	private void startService(){
		//TODO -> this sucks
		TimelineService.timelineActivity = this;
	    try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio desde el login...");
		    timelineService = new Intent(this, TimelineService.class);

		    if(startService(timelineService) == null){
                toastNotify("No se ha podido iniciar el servicio");
		    }
		    else{
                toastNotify("Servicio iniciado correctamente");
		    }
	    }
	    catch(Exception e){
	    	toastNotify(e.getMessage());
	    }

    }
    
    private void toastNotify(String string)
	{
        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
	}
    
    public void onStop(){
    	super.onStop();
    	stopService(timelineService);
    }
     

	public Handler getMyHandler() {
		return myHandler;
	}
	
	public void dismissProgressLoader(){
		pd.dismiss();
	}
	
	public android.graphics.drawable.Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
	    return android.graphics.drawable.Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	}
}
