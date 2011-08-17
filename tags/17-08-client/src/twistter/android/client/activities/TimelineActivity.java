package twistter.android.client.activities;


import java.util.Iterator;
import java.util.List;

import twistter.android.client.R;
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
				List<View> statusViews = (List<View>) msg.obj;

				LinearLayout scroll = (LinearLayout) findViewById(R.id.timeline);

				// Borrar los tweets antes almacenados
				scroll.removeAllViews();

				// Agregar los tweets traídos
				for (View view : statusViews) {
					scroll.addView(view);
				}
				
				toastNotify(20 - statusViews.size() + " tweets filtrados"); //TODO valor hardcodeado!
	    	}  
	    };
	}
	
	private void startService(){
	    TimelineService.ACTIVIDAD=this;

	    try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio desde el login...");
		    timelineService = new Intent(this, TimelineService.class);

		    if(startService(timelineService)==null){
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
    
    public void onDestroy(){
    	super.onDestroy();
    	stopService(timelineService);
    }

	public Handler getMyHandler() {
		return myHandler;
	} 
}
