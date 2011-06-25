package twistter.android.client.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import twistter.android.client.activities.TimelineActivity;
import twistter.android.client.utils.MyHttpClient;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class TimelineService extends Service {
	public static TimelineActivity ACTIVIDAD;
	private Timer timer = null;
	private Handler uiHandler;

	public static void establecerActividadPrincipal(TimelineActivity actividad){
		TimelineService.ACTIVIDAD=actividad;
	}

	public void onCreate(){
		super.onCreate();
		this.iniciarServicio();

		Log.i(getClass().getSimpleName(), "Servicio iniciado");
	}

	public void onDestroy()
	{
		super.onDestroy();

		// Detenemos el serviciop
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
				90000	// Cada 30 segundo se repite
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

	private void ejecutarTarea(){
		Log.i(getClass().getSimpleName(), "Trayendo timeline...");

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    	
    	postParameters.add(new BasicNameValuePair("method", "getTimeline"));
    	String response = null;
    	try {
    	    response = MyHttpClient.executeHttpPost("http://172.20.18.183:8080/TwistterServer/TimelineServlet", postParameters);
    	    Log.i(getClass().getSimpleName(), response);
    	    final JSONArray jsonArray = new JSONArray(response);
    	    
    	    // Reflejamos la tarea en la actividad principal
    	    
    	    Message message = new Message();
            message.obj = jsonArray;
            uiHandler = TimelineService.ACTIVIDAD.getMyHandler();
            uiHandler.sendMessage(message);
    	}catch(JSONException e){
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
		}
	}
            
}

