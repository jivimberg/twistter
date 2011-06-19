package twistter.android.client;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import twistter.android.client.utils.TwitterUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineService extends Service {
	public static Activity ACTIVIDAD;
	private Timer timer = null;
	private Handler handler = new Handler();
	private TwitterUtils utils = new TwitterUtils();

	public static void establecerActividadPrincipal(Activity actividad){
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
						
						handler.post(new Runnable() {
				              public void run() {
				            	
				                 ejecutarTarea();
				                 
				              }
				           });
						
					}
						
					
				},
				0,
				15000	// Cada 1 segundo se repite
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
    	    response = TwistterHttpClient.executeHttpPost("http://127.0.0.1:8080/Server/TimelineServlet", postParameters);
    	    String[] jsonElements = response.split("&&&");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
		// Reflejamos la tarea en la actividad principal
		
		
		TimelineService.ACTIVIDAD.runOnUiThread(new Runnable(){
				public void run(){
					try{
					
					final int N = 25; //Quantity of tweets
					final TextView[] usernames = new TextView[N];
					final TextView[] tweet = new TextView[N];
					ScrollView scroll = (ScrollView) TimelineService.ACTIVIDAD.findViewById(R.id.scroll_view);
					//LinearLayout linear_layout = new LinearLayout(TimelineService.ACTIVIDAD);
					//linear_layout.setOrientation(LinearLayout.VERTICAL);
					//scroll.addView(linear_layout);

					for (int i = 0; i < N; i++) {
						
					    TextView tweet_username = (TextView) TimelineService.ACTIVIDAD.findViewById(R.id.tweet_username);
					    TextView tweet_text = (TextView) TimelineService.ACTIVIDAD.findViewById(R.id.tweet_text);

					    tweet_username.setText("@lucasapaa");
					    tweet_text.setText("Jivi gay");
					    //username.setText(utils.getStatusFromJSON(jsonElements[i]).getUser());
					    //tweet_text.setText(utils.getStatusFromJSON(jsonElements[i]).getText());
					    
					    scroll.addView(tweet_username);
					    scroll.addView(tweet_text);
	
					    
					    usernames[i] = tweet_username;
					    tweet[i] = tweet_text;
					}

					}catch(Exception e){
						e.printStackTrace();
					}
				
				}
			}
		);
	}
}

