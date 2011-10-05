package twistter.android.client.activities;

import twistter.android.client.R;
import twistter.android.client.database.DBAdapter;
import android.app.Activity;
import android.os.Bundle;

public class DatabaseActivity extends Activity {
    private DBAdapter db;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = new DBAdapter(this); 
    }
    
    public DBAdapter getDBAdapter(){
    	return db;
    }
}

