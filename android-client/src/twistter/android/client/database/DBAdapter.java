package twistter.android.client.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	public static final String KEY_ROWID = "_id";
    public static final String KEY_TEXT ="jasonText";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "Tweets";
    private static final String DATABASE_TABLE = "jason";
    private static final int DATABASE_VERSION = 1;	
	

    private static final String DATABASE_CREATE =
        "create table titles (_id integer primary key autoincrement, "
        + "username text not null, " 
        + "tweetText text not null);";
    
    
 private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
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
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                  + " to "
                  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
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
     
     //---insert a title into the database---
     public long insertTweet(String ussername, String text,String image) 
     {
         ContentValues initialValues = new ContentValues();
         initialValues.put(KEY_TEXT, text);
         return db.insert(DATABASE_TABLE, null, initialValues);
     }

  
     //---retrieves all the titles---
     public Cursor getAllTweets() 
     {
         return db.query(DATABASE_TABLE, new String[] {
         		KEY_ROWID, 
         		KEY_TEXT},
                 null, 
                 null, 
                 null, 
                 null, null);
     }

    
     //---updates a title---
     public boolean updateTweet(long rowId, String ussername, 
     String text, String image) 
     {
         ContentValues args = new ContentValues();
         args.put(KEY_TEXT, text);
         return db.update(DATABASE_TABLE, args, 
                          KEY_ROWID + "=" + rowId, null) > 0;
     }
    
    
}


