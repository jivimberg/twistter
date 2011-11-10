package twistter.android.client.database;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

public class PersistTweetsTask extends Thread{

	private DBAdapter dbAdapter;
	private JSONArray jsonArray;
	private String username;
		
	public PersistTweetsTask(Context context, JSONArray jsonArray, String username) {
		super();
		this.dbAdapter = new DBAdapter(context);
		this.jsonArray = jsonArray;
		this.username = username;
	}

	public void run() {
		dbAdapter.open();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				final String rawJson = jsonArray.getString(i);
				dbAdapter.insertTweet(username, rawJson);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			dbAdapter.close();			
		}
	}
}
