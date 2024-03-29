package twistter.android.client.services;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import twistter.android.client.R;
import twistter.android.client.activities.TimelineActivity;
import twistter.android.client.database.DBAdapter;
import twistter.android.client.database.PersistTweetsTask;
import twistter.android.client.utils.TwitterUtils;
import twistter.android.client.ws.interfaces.HessianServiceProvider;
import twistter.android.client.ws.interfaces.TimelineWebService;
import twitter4j.Status;
import twitter4j.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TimelineService extends Service {
	
	private String TIMELINE_WEB_SERVICE_URL; 
	public static TimelineActivity timelineActivity;
	private Timer timer = null;
	private Handler uiHandler;
	private DBAdapter dbAdapter;
	
	private Long lastRetrievedTweetId;

	public void onCreate(){
		super.onCreate();
		
		TIMELINE_WEB_SERVICE_URL = "http://" + getString(R.string.ServerIP) + ":" 
		+ getString(R.string.ServerPort) + "/" + getString(R.string.ServerRootName) + "/" + getString(R.string.TimelineWebService);
		dbAdapter = new DBAdapter(this);
		
		executePopulateTimeline();
		this.initScheduledTask();
		
		Log.i(getClass().getSimpleName(), "Servicio iniciado");
	}

	public void onDestroy()
	{
		super.onDestroy();

		// Detenemos el servicio
		this.stopScheduledTask();
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

	public void stopScheduledTask(){
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
	
	private void executePopulateTimeline() {
		// Get tweets from the database and send them to the UI
		dbAdapter = new DBAdapter(timelineActivity);
		dbAdapter.open();
		final Cursor cursor = dbAdapter.getLastNTweets(10);
		if (!cursor.moveToFirst()) {
			return;
		}

		while (!cursor.isAfterLast()) {
			try {
				final Status status = TwitterUtils.getStatusFromJSON(cursor
						.getString(2));
				sendMessageToUI(status);

				if (cursor.isFirst()) {
					lastRetrievedTweetId = status.getId();
					Log.i(this.getClass().getName(),
							"Timeline populated from database, last retrieved tweet id: " + lastRetrievedTweetId);
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			cursor.moveToNext();

		}
		cursor.close();
		dbAdapter.close();
	}
	
	private void executeTask(){
		Log.i(getClass().getSimpleName(), "Retrieving timeline, Last retrieved tweet id: " + lastRetrievedTweetId);
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
    	    	final String rawJson = jsonArray.getString(i);
    	    	final Status status = TwitterUtils.getStatusFromJSON(rawJson);
    	    	sendMessageToUI(status);

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
	
	private void sendMessageToUI(Status status){
	  	final View inflatedView = View.inflate(timelineActivity, R.layout.status, null);

    	final TextView tweet_username = (TextView) inflatedView.findViewById(R.id.tweet_username);
    	final TextView tweet_text = (TextView) inflatedView.findViewById(R.id.tweet_text);

    	tweet_username.setText("@"+status.getUser().getScreenName()+" ("+status.getUser().getName()+")");
    	tweet_text.setText(status.getText());
    	
    	//Load image
    	ImageView tweet_user_image = (ImageView) inflatedView.findViewById(R.id.tweet_user_image);
		try{
//			String url = status.getUser().getProfileImageURL().toString();
//			
//			Drawable profilePicture = drawable_from_url(url, "image");
//			tweet_user_image.setBackgroundDrawable(profilePicture);    	    					
			tweet_user_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaultavatar));
		}catch (Exception e) {
			//load default picture and log the error
			tweet_user_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.defaultavatar));
			Log.w(getClass().getSimpleName(), "No se pudo cargar la imagen");
		}
    	
    	// Reflejamos la tarea en la actividad principal
	    final Message message = new Message();
	    ArrayList<Object> arrayMessage = new ArrayList<Object>();
	    arrayMessage.add(getString(R.string.TimelineServiceId));
	    arrayMessage.add(inflatedView);
        message.obj = arrayMessage;
        uiHandler = TimelineService.timelineActivity.getMyHandler();
        uiHandler.sendMessage(message);
	}
	
	public android.graphics.drawable.Drawable drawable_from_url(String url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
	    return android.graphics.drawable.Drawable.createFromStream(((java.io.InputStream)new java.net.URL(url).getContent()), src_name);
	}
            
}

