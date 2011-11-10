package twistter.android.client.activities;

import twistter.android.client.R;
import twistter.android.client.database.DBAdapter;
import android.app.Activity;
import android.os.Bundle;

public class DBActivity extends Activity {
    private DBAdapter dbAdapter;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbAdapter = new DBAdapter(this); 
        dbAdapter.open();
        dbAdapter.insertTweet("jiviName", "jiviJson");
        setContentView(R.layout.main); 
    }
    
    public DBAdapter getDBAdapter(){
    	return dbAdapter;
    }
}

