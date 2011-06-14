package twistter.android.client;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class TimelineService extends Service {
	public static Activity ACTIVIDAD;
	private Timer timer = null;

	public static void establecerActividadPrincipal(Activity actividad){
		TimelineService.ACTIVIDAD=actividad;
	}

	public void onCreate(){
		super.onCreate();

		// Iniciamos el servicio
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
					public void run(){
						ejecutarTarea();
						
					}
				},
				0,
				1000	// Cada 1 segundo se repite
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
    	    response = TwistterHttpClient.executeHttpPost("http://127.0.0.1/TimelineServlet", postParameters);
    	    
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
		// Reflejamos la tarea en la actividad principal
		TimelineService.ACTIVIDAD.runOnUiThread(new Runnable(){
				public void run(){
					TextView tweet=(TextView)TimelineService.ACTIVIDAD.findViewById(R.id.textview_error);
					//TextView ejecuciones=(TextView)TimelineService.ACTIVIDAD.findViewById(R.id.TextView01);
					//ejecuciones.append(".");

				}
			}
		);
	}
}

