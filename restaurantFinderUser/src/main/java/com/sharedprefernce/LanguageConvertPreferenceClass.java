package com.sharedprefernce;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Locale;


public class LanguageConvertPreferenceClass {

	public static final String language = "language";
	private Locale myLocale;
	static SharedPreferences getSharedPreferences(Context ctx) {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}


	/* For Storing latitude */
	public static void setlanguage(Context ctx, String latitude) {
		Editor editor = getSharedPreferences(ctx).edit();
		editor.putString(language, latitude);
		editor.commit();
	}

	/* For retrieving latitude */
	public static String getlanguage(Context ctx) {
		return getSharedPreferences(ctx).getString(language, "");
	}

	/* For deleting latitude */
	public static void removelanguage(Context ctx) {

		getSharedPreferences(ctx).edit().putString(language, null)
				.commit();

	}

	

}
