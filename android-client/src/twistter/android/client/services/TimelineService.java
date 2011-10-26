package twistter.android.client.services;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import twistter.android.client.R;
import twistter.android.client.activities.TimelineActivity;
import twistter.android.client.database.PersistTweetsTask;
import twistter.android.client.utils.TwitterUtils;
import twistter.android.client.ws.interfaces.HessianServiceProvider;
import twistter.android.client.ws.interfaces.TimelineWebService;
import twitter4j.Status;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineService extends Service {
	
	private static final String TIMELINE_ID = "TIMELINE_SERVICE";
	private String TIMELINE_WEB_SERVICE_URL; 
	public static TimelineActivity ACTIVIDAD;
	private Timer timer = null;
	private Handler uiHandler;
	
	private Long lastRetrievedTweetId;

	public static void establecerActividadPrincipal(TimelineActivity actividad){
		TimelineService.ACTIVIDAD=actividad;
	}

	public void onCreate(){
		super.onCreate();
		
		TIMELINE_WEB_SERVICE_URL = "http://" + getString(R.string.ServerIP) + ":" 
		+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.TimelineWebService);
		this.initScheduledTask();
		
		Log.i(getClass().getSimpleName(), "Servicio iniciado");
	}

	public void onDestroy()
	{
		super.onDestroy();

		// Detenemos el servicio
		this.finalizarServicio();
		Log.i(getClass().getSimpleName(), "Servicio detenido");
	}

	public IBinder onBind(Intent intent){
		return null;
	}

	public void initScheduledTask(){
		try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio...");
			this.timer=new Timer();

			// Configuramos lo que tiene que hacer
			
			this.timer.scheduleAtFixedRate(
				new TimerTask(){
					public void run() {
						executeTask();
					}
				},
				0,
				30000 //30 segundos
			);

			Log.i(getClass().getSimpleName(), "Temporizador de timeline iniciado");
		}
		catch(Exception e){
			Log.i(getClass().getSimpleName(), e.getMessage());
		}
	}

	public void finalizarServicio(){
		try{
			Log.i(getClass().getSimpleName(), "Finalizando servicio...");

			// Detenemos el timer
			this.timer.cancel();

			Log.i(getClass().getSimpleName(), "Temporizador de timeline detenido");

		}
		catch(Exception e){
			Log.i(getClass().getSimpleName(), e.getMessage());
		}
	}
	

	private void executeTask(){
		Log.i(getClass().getSimpleName(), "Trayendo timeline...");
    	String response = null;
    	try {
    		//Get new tweets
    		final TimelineWebService timelineWebService = HessianServiceProvider.getTimelineWebService(TIMELINE_WEB_SERVICE_URL, getClassLoader());
    		final String username = getSharedPreferences(getString(R.string.PrefsName), MODE_PRIVATE).getString(getString(R.string.PrefUserName), null);
			response = timelineWebService.getTimeline(username, lastRetrievedTweetId);
    	    
    	    final JSONArray jsonArray = new JSONArray(response);  	   
    	    
    	    //Init persist task
    	    final PersistTweetsTask persistTweetsTask = new PersistTweetsTask(this, jsonArray, username);
    	    persistTweetsTask.run();
   
    	    //Send them to the UI
    	    for (int i = 0; i < jsonArray.length(); i++){
	    		final View inflatedView = View.inflate(ACTIVIDAD, R.layout.status, null);
    	    	
    	    	final TextView tweet_username = (TextView) inflatedView.findViewById(R.id.tweet_username);
    	    	final TextView tweet_text = (TextView) inflatedView.findViewById(R.id.tweet_text);
    	    	
    	    	
    	    	final String rawJson = jsonArray.getString(i);
    	    	final Status status = TwitterUtils.getStatusFromJSON(rawJson);
    	    	
    	    	tweet_username.setText("@"+status.getUser().getScreenName()+" ("+status.getUser().getName()+")");
    	    	tweet_text.setText(status.getText());
    	    	
    	    	// Reflejamos la tarea en la actividad principal
        	    final Message message = new Message();
        	    ArrayList<Object> arrayMessage = new ArrayList<Object>();
        	    arrayMessage.add(TIMELINE_ID);
        	    arrayMessage.add(inflatedView);
        	    arrayMessage.add(status.getUser().getProfileImageURL().toString());
                message.obj = arrayMessage;
                uiHandler = TimelineService.ACTIVIDAD.getMyHandler();
                uiHandler.sendMessage(message);

                //store last tweet id
                if(i == jsonArray.length() - 1){
                	lastRetrievedTweetId = status.getId();
                	Log.i(this.getClass().getName(), "Timeline updated " + (i + 1) + " new tweets");
                }
                    
			}
    	}catch(JSONException e){
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
		}
	}
	
	
            
}

