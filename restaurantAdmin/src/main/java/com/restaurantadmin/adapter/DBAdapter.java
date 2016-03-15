package com.restaurantadmin.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bean.DatabaseData;
import com.bean.DateDetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	
	/******************** if debug is set true then it will show all Logcat message ************/
	public static final boolean DEBUG = true;
	public static List<DatabaseData> contactList;
	
	/******************** Logcat TAG ************/
	public static final String LOG_TAG = "DBAdapter";
	
	/******************** Table Fields ************/
	
	//field in the db for our it is only one field  ..
//	public static final String KEY_ID = "_id";

	public static final String KEY_API = "Api";
	public static final String KEY_DATA = "Data";
	public static final String KEY_TIMESTAMP = "Timestamp";
	
	
	/******************** Database Name ************/
	
	
	public static final String DATABASE_NAME = "DB_wjbty";
	
	/******************** Database Version (Increase one if want to also upgrade your database) ************/
	public static final int DATABASE_VERSION = 3;// started at 1

	/** Table names */
	public static final String USER_TABLE = "Table_Cache_Data_Wjbty";
	
	/******************** Set all table with comma seperated like USER_TABLE,ABC_TABLE ************/
	private static final String[] ALL_TABLES = { USER_TABLE };
	
	/** Create table syntax */
	private static final String USER_CREATE = "create table Table_Cache_Data_Wjbty(Api text not null primary key, Data text not null, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP );";
	
	/******************** Used to open database in syncronized way ************/
	private static DataBaseHelper DBHelper = null;

	protected DBAdapter() {
	}
    /******************* Initialize database *************/
	public static void init(Context context) {
		if (DBHelper == null) {
			if (DEBUG)
				Log.i("DBAdapter", context.toString());
			DBHelper = new DataBaseHelper(context);
		}
	}
	
  /********************** Main Database creation INNER class ********************/
	private static class DataBaseHelper extends SQLiteOpenHelper {
		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (DEBUG)
				Log.i(LOG_TAG, "new create");
			try {
				db.execSQL(USER_CREATE);
				

			} catch (Exception exception) {
				if (DEBUG)
					Log.i(LOG_TAG, "Exception onCreate() exception");
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (DEBUG)
				Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
						+ "to" + newVersion + "...");

			for (String table : ALL_TABLES) {
				db.execSQL("DROP TABLE IF EXISTS " + table);
			}
			onCreate(db);
		}

	} // Inner class closed
	
	
	/********************** Open database for insert,update,delete in syncronized manner ********************/
	private static synchronized SQLiteDatabase open() throws SQLException {
		return DBHelper.getWritableDatabase();
	}


	/************************ General functions**************************/
	
	
	/*********************** Escape string for single quotes (Insert,Update)************/
	private static String sqlEscapeString(String aString) {
		String aReturn = "";
		
		if (null != aString) {
			//aReturn = aString.replace("'", "''");
			aReturn = DatabaseUtils.sqlEscapeString(aString);
			// Remove the enclosing single quotes ...
			aReturn = aReturn.substring(1, aReturn.length() - 1);
		}
		
		return aReturn;
	}
	/*********************** UnEscape string for single quotes (show data)************/
	private static String sqlUnEscapeString(String aString) {
		
		String aReturn = "";
		
		if (null != aString) {
			aReturn = aString.replace("''", "'");
		}
		
		return aReturn;
	}
	
	
	/********************************************************************/
	
	
	 /**
     * All Operations (Create, Read, Update, Delete) 
     */
	// Adding new contact
 
    public static void addData(DatabaseData dbData) {
    	final SQLiteDatabase db = open();
    	
    	String Api = sqlEscapeString(dbData.get_Api());
		String Date = sqlEscapeString(dbData.get_Data());
		String Timestemp = sqlEscapeString(dbData.get_Timestamp());
		
		ContentValues cVal = new ContentValues();
		cVal.put(KEY_API, Api);
		cVal.put(KEY_DATA, Date);
		cVal.put(KEY_TIMESTAMP, Timestemp);
		db.insert(USER_TABLE, null, cVal);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    public static DatabaseData getParticularData(String Api) {
    	final SQLiteDatabase db = open();
 
        Cursor cursor = db.query(USER_TABLE, new String[] { 
        		KEY_API, KEY_DATA , KEY_TIMESTAMP }, KEY_API + "=?",
                new String[] {String.valueOf(Api)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        DatabaseData data = new DatabaseData((cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return data;
    }
 
    // Getting All Contacts
    public static List<DatabaseData> getAllData() 
    {
        List<DatabaseData> contactList = new ArrayList<DatabaseData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE;
 
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	DatabaseData data = new DatabaseData();
            	data.set_Api(cursor.getString(0));
            	data.set_Data(cursor.getString(1));
            	data.set_Timestamp(cursor.getString(2));
                // Adding contact to list
                contactList.add(data);
                
                System.out.println("contactList.."+contactList);
                
                
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public static int updateData(DatabaseData data) {
    	final SQLiteDatabase db = open();
 
        ContentValues values = new ContentValues();
        values.put(KEY_API, data.get_Api());
        values.put(KEY_DATA, data.get_Data());
        values.put(KEY_TIMESTAMP, data.get_Timestamp());
 
        // updating row
        return db.update(USER_TABLE, values, KEY_API + " = ?",
                new String[] { String.valueOf(data.get_Api()) });
    }
 
    // Deleting single contact
    public static void deleteData(DatabaseData data) {
    	final SQLiteDatabase db = open();
        db.delete(USER_TABLE, KEY_API + " = ?",
                new String[] { String.valueOf(data.get_Api()) });
        db.close();
    }
 
    // Getting contacts Count
    public static int getDataCount() {
        String countQuery = "SELECT  * FROM " + USER_TABLE;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
    public static void deleteall()
	{
		SQLiteDatabase database = DBHelper.getWritableDatabase();
		database.execSQL("delete from Table_Cache_Data_Wjbty");
		//database.delete("map_navigation_final", " Status = 'Yes'", null);
		System.out.println("deleted successfully");
		database.close();
	}
    
    public static List<DateDetail> CheckTimeExpire()
    {
    	 List<DateDetail> Expire_Data_List = new ArrayList<DateDetail>();
         // Select All Query
         String selectQuery = "SELECT Timestamp FROM " + USER_TABLE;
  
         final SQLiteDatabase db = open();
         Cursor cursor = db.rawQuery(selectQuery, null);
  
         // looping through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
            	 System.out.println("Date_In_Database"+ cursor.getString(0));
             	DateDetail Date_Data = new DateDetail();
             	Date_Data.Set_Date_Deatil(cursor.getString(0));	
             	 System.out.println("Date_In_Database Get Data"+Date_Data.get_Date_Detail());
                 // Adding contact to list
             	Expire_Data_List.add(Date_Data); 
             	
             	System.out.println("Expire_Data_List.size()"+Expire_Data_List.size());
              
                 
                 
             } while (cursor.moveToNext());
             System.out.println("Expire_Data_List.."+Expire_Data_List.get(0));
         }
         System.out.println("Expire_Data_List......"+Expire_Data_List);
         // return contact list
         return Expire_Data_List;
  
    }
	public static void DeleteExpierdDateRow(ArrayList<String> expierdate) 
	{
		// TODO Auto-generated method stub
		final SQLiteDatabase db = open();
		if(expierdate.size()!=0)
		{
			for(int i=0;i<expierdate.size();i++)
			{

				db.delete(USER_TABLE, KEY_TIMESTAMP + " = ?",new String[] {expierdate.get(i)});
				System.out.println("Database Delete successfully");
			}
		}
		db.close();
		// return contact
	     /* String selectQuery = "delete  from " + USER_TABLE;
		delete  from tbl_cache_data_wjbty where datetime = "2014-11-26 06:54:24"
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	UserData data = new UserData();
            	data.setName(cursor.getString(0));
            	data.set_data(cursor.getString(1));
            	data.set_datetime(cursor.getString(2));
                // Adding contact to list
                contactList.add(data);
                
                System.out.println("contactList.."+contactList);
                
                
            } while (cursor.moveToNext());
        }*/
 
 
	
	}
	
	public static void CheckFortyeighthouser(List<DateDetail> date_Detail) 
	{
		System.out.println("checkfortieightmethod"+date_Detail);
		// TODO Auto-generated method stub
		ArrayList<String> Expierdate=new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		
		//Calendar now = Calendar.getInstance();
	    //now.add(Calendar.DATE, -2);
	    //String Str_CurrentDate = (now.get(Calendar.MONTH) + 1) + "/"
	      //  + now.get(Calendar.DATE) + "/" + now.get(Calendar.YEAR) +" "+now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);
	    
	//    System.out.println("sdghdfg"+Str_CurrentDate);
	    
		
		
		String Str_CurrentDate=format.format(new Date());
		java.util.Date CurrentDate=null;
		java.util.Date DataBaseDate=null;		
		//System.out.println("Date_Detail_List_CurrentDate"+CurrentDate);
		//System.out.println("Date_Detail_List"+Date_Detail);
        for (DateDetail dt : date_Detail) 
        {		
        	try
        	{
            	CurrentDate=format.parse(Str_CurrentDate);
	        	System.out.println("you_are_in_for_loop"+CurrentDate);
	        	System.out.println("you_are_in_for_loop_dt_Datetime"+dt.get_Date_Detail());
	        	String Str_DataBaseDate=dt.get_Date_Detail().toString();
	        	DataBaseDate = format.parse(Str_DataBaseDate);
	        	System.out.println("you_are_in_for_loop_Databasedate"+DataBaseDate);
	        	
	        	
	        	long diff = DataBaseDate.getTime() - CurrentDate.getTime();
	        	 
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
	 
				System.out.println("diffDays"+diffDays);
				System.out.println("diffHours"+diffHours);
				System.out.println("diffMinutes"+diffMinutes);
				System.out.println("diffSeconds"+diffSeconds);
				
				if(diffDays>=2)	
				{
					Expierdate.add(Str_DataBaseDate);
				}
			}
        	catch (Exception e) 
        	{
    			e.printStackTrace();
    		}
        	
        }
        DBAdapter.DeleteExpierdDateRow(Expierdate);
   
	}

}
