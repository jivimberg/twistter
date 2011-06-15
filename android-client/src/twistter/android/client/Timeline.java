package twistter.android.client;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Timeline extends Activity{
	
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline); //Agregar el nuevo activity
        startService();
	}
	
	private void startService(){
	    TimelineService.ACTIVIDAD=this;

	    try{
			Log.i(getClass().getSimpleName(), "Iniciando servicio desde el login...");
		    Intent timelineService = new Intent(this, TimelineService.class);

		    EditText test=(EditText)findViewById(R.id.tweet_username);
		    if(startService(timelineService)==null){
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
        Toast.makeText(getApplicationContext(),cadena,Toast.LENGTH_SHORT).show();

	}

}
