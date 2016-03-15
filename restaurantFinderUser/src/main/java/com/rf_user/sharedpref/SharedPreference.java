package com.rf_user.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreference {

	static final String PREF_sessid = "sessid";
	static final String PREF_user_id = "user_id";
	static final String PREF_user_loyalty_pts = "user_loyalty_pts";

	static SharedPreferences getSharedPreferences(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	/* For storing sessid in Pref.  */
	public static void setsessid(Context ctx, String sessid) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_sessid, sessid);
		
		editor.commit();
	}

	/* For retrieving sessid from Pref.  */
	public static String getsessid(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_sessid, "");
	}

	/* For removing sessid from Pref.  */
	public static void removesessid(Context ctx) {

		getSharedPreferences(ctx).edit().putString(PREF_sessid, null)
				.commit();

	}
	
	
	/* For storing user_id in Pref.  */
	public static void setuser_id(Context ctx, String user_id) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_user_id, user_id);
		editor.commit();
	}

	/* For retrieving user_id from Pref.  */
	public static String getuser_id(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_user_id, "");
	}

	/* For removing user_id from Pref.  */
	public static void removeuser_id(Context ctx) {

		getSharedPreferences(ctx).edit().putString(PREF_user_id, null)
				.commit();

	}
	
	/* For storing user_loyalty_pts in Pref.  */
	public static void set_user_loyalty_pts(Context ctx, String user_loyalty_pts) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(PREF_user_loyalty_pts, user_loyalty_pts);
		editor.commit();
	}

	/* For retrieving user_loyalty_pts from Pref.  */
	public static String get_user_loyalty_pts(Context ctx) {
		return getSharedPreferences(ctx).getString(PREF_user_loyalty_pts,"");
	}

	/* For removing user_loyalty_pts from Pref.  */
	public static void remove_user_loyalty_pts(Context ctx) {

		getSharedPreferences(ctx).edit().putString(PREF_user_loyalty_pts, null)
				.commit();

	}
	
	
	/*Dynamic sharedpref*/
	
	/* For storing value in Pref.  */
	public static void setvalue(Context ctx,String Key, String sessid) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(Key, sessid);
		editor.commit();
	}

	/* For retrieving value from Pref.  */
	public static String getvalue(Context ctx,String Key) {
		return getSharedPreferences(ctx).getString(Key, "");
	}

	/* For removing value from Pref.  */
	public static void removevalue(Context ctx,String Key) {

		getSharedPreferences(ctx).edit().putString(Key, null)
				.commit();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
