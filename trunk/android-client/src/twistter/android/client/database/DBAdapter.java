package twistter.android.client.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    
    private static final String DATABASE_NAME = "TwistterDB";
    private static final String TWEETS_TABLE_NAME = "tweets";
    private static final int DATABASE_VERSION = 1;	
	
    public static final String KEY_ROWID = "_id";
    public static final String KEY_JSON ="json";
    public static final String KEY_USERNAME ="username";

    private static final String DATABASE_CREATE =
        "create table " + TWEETS_TABLE_NAME + " (_id integer primary key autoincrement, "
        + "username text not null, " 
        + "json text not null);";
    
    
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    //---opens the database---
     public DBAdapter open() throws SQLException 
     {
         db = DBHelper.getWritableDatabase();
         return this;
     }

     //---closes the database---    
     public void close() 
     {
         DBHelper.close();
     }
     
     //---insert a tweet---
     public long insertTweet(String username, String json) 
     {
         ContentValues values = createContentValues(username, json);
         return db.insert(TWEETS_TABLE_NAME, null, values);
     }

     //---updates a tweet---
     public boolean updateTweet(long rowId, String username, String json) 
     {
         ContentValues newValues = createContentValues(username, json);
         return db.update(TWEETS_TABLE_NAME, newValues, 
                          KEY_ROWID + "=" + rowId, null) > 0;
     }
     
     //---deletes tweet---
     public boolean deleteTodo(long rowId) {
 		return db.delete(TWEETS_TABLE_NAME, KEY_ROWID + "=" + rowId, null) > 0;
 	}
  
     //---retrieves all the titles---
     public Cursor getAllTweets() 
     {
		return db.query(TWEETS_TABLE_NAME,
				new String[] { KEY_ROWID, KEY_JSON }, null, null, null, null, null);
     }
     
     //---retrieves a single tweet---
     public Cursor fetchTweet(long rowId) throws SQLException {
 		Cursor mCursor = db.query(true, TWEETS_TABLE_NAME, new String[] {
 				KEY_ROWID, KEY_USERNAME, KEY_JSON},
 				KEY_ROWID + "=" + rowId, null, null, null, null, null);
 		if (mCursor != null) {
 			mCursor.moveToFirst();
 		}
 		return mCursor;
 	}

     private ContentValues createContentValues(String username, String json) 
     {
 		ContentValues values = new ContentValues();
 		values.put(KEY_USERNAME, username);
 		values.put(KEY_JSON, json);
 		return values;
 	}
     
     
     private static class DatabaseHelper extends SQLiteOpenHelper 
     {
         DatabaseHelper(Context context) 
         {
             super(context, DATABASE_NAME, null, DATABASE_VERSION);
         }

         @Override
         public void onCreate(SQLiteDatabase db) 
         {
             db.execSQL(DATABASE_CREATE);
         }

         @Override
         public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                               int newVersion) 
         {
             Log.w("DatabaseAdapter", "Upgrading database from version " + oldVersion 
                   + " to "
                   + newVersion + ", which will destroy all old data");
             db.execSQL("DROP TABLE IF EXISTS titles");
             onCreate(db);
         }
     }  
    
    
}


