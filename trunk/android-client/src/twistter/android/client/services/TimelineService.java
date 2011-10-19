package twistter.android.client.services;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import twistter.android.client.R;
import twistter.android.client.activities.TimelineActivity;
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
	
	private String TIMELINE_WEB_SERVICE_URL; 
	public static TimelineActivity ACTIVIDAD;
	private Timer timer = null;
	private Handler uiHandler;
	private static JSONArray lastJsonArray;

	public static void establecerActividadPrincipal(TimelineActivity actividad){
		TimelineService.ACTIVIDAD=actividad;
	}

	public void onCreate(){
		super.onCreate();
		
		TIMELINE_WEB_SERVICE_URL = "http://" + getString(R.string.ServerIP) + ":" 
		+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.TimelineWebService);
		this.iniciarServicio();
		
		Log.i(getClass().getSimpleName(), "Servicio iniciado");
		lastJsonArray = new JSONArray();
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

	public void iniciarServicio(){
		try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio...");
			this.timer=new Timer();

			// Configuramos lo que tiene que hacer
			
			this.timer.scheduleAtFixedRate(
				new TimerTask(){
					public void run() {
						ejecutarTarea();
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
	
	public boolean isCached(String rawJson){
		
		for(int i=0; i<lastJsonArray.length();i++){
			try {
				if(rawJson.equals(lastJsonArray.getString(i))){
					return true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}return false;
		
	}
	

	private void ejecutarTarea(){
		Log.i(getClass().getSimpleName(), "Trayendo timeline...");
    	String response = null;
    	try {
    		final TimelineWebService timelineWebService = HessianServiceProvider.getTimelineWebService(TIMELINE_WEB_SERVICE_URL, getClassLoader());
    		final String username = getSharedPreferences(getString(R.string.PrefsName), MODE_PRIVATE).getString(getString(R.string.PrefUserName), null);
			response = timelineWebService.getTimeline(username);
    	    
    	    final JSONArray jsonArray = new JSONArray(response);  	    
   
    	    for (int i = 0; i < jsonArray.length(); i++){
    	    	if(!isCached(jsonArray.getString(i))){
    	    		if(lastJsonArray.length() != 0){
    	    			Toast.makeText(getApplicationContext(),"Timeline Updated",Toast.LENGTH_SHORT).show();
    	    		}
    	    		View inflatedView = View.inflate(ACTIVIDAD, R.layout.status, null);
        	    	
        	    	TextView tweet_username = (TextView) inflatedView.findViewById(R.id.tweet_username);
        	    	TextView tweet_text = (TextView) inflatedView.findViewById(R.id.tweet_text);
        	    	
        	    	
        	    	String rawJson = jsonArray.getString(i);
        	    	Status status = TwitterUtils.getStatusFromJSON(rawJson);
        	    	
        	    	
        	    	tweet_username.setText("@"+status.getUser().getScreenName()+" ("+status.getUser().getName()+")");
        	    	tweet_text.setText(status.getText());
        	    	
        	    	// Reflejamos la tarea en la actividad principal
            	    Message message = new Message();
            	    ArrayList<Object> arrayMessage = new ArrayList<Object>();
            	    arrayMessage.add(inflatedView);
            	    arrayMessage.add(status.getUser().getProfileImageURL().toString());
                    message.obj = arrayMessage;
                    uiHandler = TimelineService.ACTIVIDAD.getMyHandler();
                    uiHandler.sendMessage(message);
                    
                   
                    
    	    	}
    	    	
			}
    	    lastJsonArray = jsonArray;
    	               
    	}catch(JSONException e){
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
		}
	}
	
	
            
}

