package twistter.android.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import twistter.android.client.utils.TwitterUtils;
import twitter4j.ProfileImage;
import twitter4j.Status;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
				30000	// Cada 30 segundo se repite
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
    	    response = TwistterHttpClient.executeHttpPost("http://192.168.0.4:8080/TwistterServer/TimelineServlet", postParameters);
    	    Log.i(getClass().getSimpleName(), response);
    	    final JSONArray jsonArray = new JSONArray(response);
    	    
    	    // Reflejamos la tarea en la actividad principal
    	    
    	    
    	    TimelineService.ACTIVIDAD.runOnUiThread(new Runnable(){
    	    	public void run(){
    	    		try{
    	    			
    	    			final int qtyOfTweets = jsonArray.length(); 
    	    			LinearLayout scroll = (LinearLayout) TimelineService.ACTIVIDAD.findViewById(R.id.timeline);
    	    			
    	    			for (int i = 0; i < qtyOfTweets; i++) {
    	    				View inflatedView = View.inflate(TimelineService.ACTIVIDAD, R.layout.status, null);
    	    				 
    	    				TextView tweet_username = (TextView) inflatedView.findViewById(R.id.tweet_username);
    	    				TextView tweet_text = (TextView) inflatedView.findViewById(R.id.tweet_text);
    	    				ImageView tweet_user_image = (ImageView) inflatedView.findViewById(R.id.tweet_user_image);
    	    			
    	    				String rawJson = jsonArray.getString(i);
    	    				Status status = TwitterUtils.getStatusFromJSON(rawJson);
    	    				
    	    				Drawable profilePicture = drawable_from_url(status.getUser().getProfileImageURL().toString(), "src");
    	    				tweet_user_image.setBackgroundDrawable(profilePicture);
    	    				tweet_username.setText(status.getUser().getName());
    	    				tweet_text.setText(status.getText());
    	    				
    	    				scroll.addView(inflatedView);
    	    			}
    	    			
    	    		}catch(Exception e){
    	    			e.printStackTrace();
    	    		}
    	    		
    	    	}
    	    }
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
	}
	
	android.graphics.drawable.Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
	    return android.graphics.drawable.Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	}
}

