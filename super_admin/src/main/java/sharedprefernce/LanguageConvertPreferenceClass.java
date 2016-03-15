package sharedprefernce;

import java.util.Locale;

import com.superadmin.global.Global_variable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class LanguageConvertPreferenceClass {

	public static Locale myLocale;

	public static String getLocale(Context act) {

		String langPref = "Language";
		SharedPreferences prefs = act.getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");
		myLocale = new Locale(language);
		System.out.println("Murtuza_Nalawala_123456" + myLocale);

		return myLocale.toString();

	}

	// language***************
	public static void loadLocale(Context act) {

		System.out.println("Murtuza_Nalawala_LOAD_LOCALE");
		String langPref = "Language";
		SharedPreferences prefs = act.getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");
		System.out.println("Murtuza_Nalawala_languagePANKAJ" + language
				+ "AFTERPANKAJ");

		changeLang(language, act);
	}

	public static void changeLang(String lang, Context act) {
		System.out.println("Murtuza_Nalawala_changeLang");

		if (lang.equalsIgnoreCase(""))
			return;
		myLocale = new Locale(lang);
		System.out.println("Murtuza_Nalawala_123456" + myLocale);
		if (myLocale.toString().equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_if" + myLocale);
			Global_variable.rf_api_Url = Global_variable.rf_api_Url_en;

		} else if (myLocale.toString().equalsIgnoreCase("ro")) {
			System.out.println("Murtuza_Nalawala_language_else" + myLocale);
			System.out.println("IN_arabic");
			Global_variable.rf_api_Url = Global_variable.rf_api_Url_ro;

		}
		saveLocale(lang, act);
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		act.getResources().updateConfiguration(config,
				act.getResources().getDisplayMetrics());
		// updateTexts();

	}

	public static void saveLocale(String lang, Context act) {

		String langPref = "Language";
		System.out.println("Murtuza_Nalawala_langPref_if" + langPref);
		SharedPreferences prefs = act.getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		System.out.println("Murtuza_Nalawala_langPref_prefs" + prefs);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();

		String language = prefs.getString(langPref, "");
		System.out.println("Murtuza_Nalawala_languagePANKAJ_INsAVElOCALE"
				+ language);
	}

	public static void setLocale(String lang, Context act) {

		myLocale = new Locale(lang);
		Resources res = act.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;

		res.updateConfiguration(conf, dm);
		System.out.println("Murtuza_Nalawala_set_locale" + lang);

	}

	// language**************

}
