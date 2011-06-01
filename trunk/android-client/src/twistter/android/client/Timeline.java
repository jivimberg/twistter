package twistter.android.client;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



public class Timeline extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //Agregar el nuevo activity
        startService();
	}
	
	private void startService(){
	    TimelineService.ACTIVIDAD=this;

	    try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio desde el login...");

		    Intent servicio = new Intent(this, TimelineService.class);
		    if(startService(servicio)==null){
		    	this.notificar("No se ha podido iniciar el servicio");
		    }
		    else{
		    	this.notificar("Servicio iniciado correctamente");
		    }
	    }
	    catch(Exception e){
	    	this.notificar(e.getMessage());
	    }

    }
    
    private void notificar(String cadena)
	{
		Context contexto = getApplicationContext();

		CharSequence texto = cadena;
		int duracion = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(contexto, texto, duracion);
		toast.show();
	}

}
