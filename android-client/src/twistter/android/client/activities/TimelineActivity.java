package twistter.android.client.activities;


import java.util.ArrayList;

import twistter.android.client.R;
import twistter.android.client.services.TimelineService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
        myHandler = new Handler(){
			public void handleMessage(Message msg) {
				dismissProgressLoader();
				ArrayList<Object> arrayMessage = (ArrayList<Object>) msg.obj;
				
				if(arrayMessage.get(0).equals(getString(R.string.TimelineServiceId))){
					View statusView = (View) arrayMessage.get(1);
					LinearLayout scroll = (LinearLayout) findViewById(R.id.timeline);
					scroll.addView(statusView, 0);
					
					//TextView filterTweetsCounter = (TextView)  findViewById(R.id.filtered_tweets_counter);
					//filterTweetsCounter.setText(20 - statusViews.size() + " filtered");
					//toastNotify(20 - statusViews.size() + " tweets filtrados"); //TODO valor hardcodeado!
				}
					
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
}
