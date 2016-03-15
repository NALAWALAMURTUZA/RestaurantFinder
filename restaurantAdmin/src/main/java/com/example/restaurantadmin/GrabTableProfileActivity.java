package com.example.restaurantadmin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.widget.EditText;

import com.restaurantadmin.adapter.DBAdapter;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class GrabTableProfileActivity extends Activity {
	ConnectionDetector cd;
	EditText ed_firstname, ed_lastname, ed_email, ed_zip, ed_address,
			ed_country, ed_region, ed_city, ed_phone;
	String str_firstname, str_lastname, str_email_profile, str_mobile,
			str_address1, str_address2, str_zip, str_country, str_region,
			str_city, str_district;
	EditText ed_bussiness, ed_date, ed_time, ed_occasion, ed_meal_duration;
	JSONObject Obj_Profile;
	private Locale myLocale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_grab_table_profile);
		initialization();
		cd = new ConnectionDetector(getApplicationContext());

		Bundle b = getIntent().getExtras();
		Intent i = getIntent();
		// *********order profile************
		String str_profile = b.getString("Profile");

		
		String str_date = i.getStringExtra("date");
		String str_time = i.getStringExtra("time");
		String str_fname = i.getStringExtra("firstname");
		String str_sname = i.getStringExtra("surname");
		String str_phone = i.getStringExtra("phone");
		String str_email = i.getStringExtra("email");
		String str_bussiness = i.getStringExtra("bussiness");
		String str_occassion = i.getStringExtra("occassions");
		String str_meal = i.getStringExtra("duration");
		
		

		System.out.println("tgpprofile" + str_profile);
		System.out.println("str_fname" + str_fname);
		System.out.println("str_sname" + str_sname);
		System.out.println("str_phone" + str_phone);
		System.out.println("str_email" + str_email);
		System.out.println("str_occassion" + str_occassion);
		System.out.println("str_bussiness" + str_bussiness);
		System.out.println("str_date" + str_date);
		System.out.println("str_time" + str_time);
		System.out.println("str_meal" + str_meal);
		try {
			if (str_profile != null) {
				Obj_Profile = new JSONObject(str_profile);
				System.out.println("tgpObj_Profile" + Obj_Profile);
				str_firstname = Obj_Profile.getString("profile_first_name");
				str_lastname = Obj_Profile.getString("profile_last_name");
				System.out.println("tgpfirstname" + str_firstname);
				str_email_profile = Obj_Profile.getString("profile_email");
				System.out.println("tgpstr_order_email" + str_email);
				str_mobile = Obj_Profile.getString("profile_mobile_number");
				System.out.println("str_mobile" + str_mobile);

				str_address1 = Obj_Profile.getString("profile_address_line_1");
				str_address2 = Obj_Profile.getString("profile_address_line_2");
				str_zip = Obj_Profile.getString("profile_zip");
				str_country = Obj_Profile
						.getString("profile_countries_name_en");
				str_region = Obj_Profile.getString("profile_regions_name_en");
				str_city = Obj_Profile.getString("profile_cities_name_en");
				// str_district = Obj_Profile
				// .getString("profile_district_name_en");
				if (str_firstname.length() != 0
						&& !str_firstname.equalsIgnoreCase("null")) {
					ed_firstname.setText(str_firstname);
				}
				if (str_lastname.length() != 0
						&& !str_lastname.equalsIgnoreCase("null")) {
					ed_lastname.setText(str_lastname);
				}
				if (str_email_profile.length() != 0
						&& !str_email_profile.equalsIgnoreCase("null")) {
					ed_email.setText(str_email_profile);
				}
				if(str_address1.length() != 0
						&& !str_address1.equalsIgnoreCase("null")&& str_address2.length() != 0
								|| !str_address2.equalsIgnoreCase("null"))
				{
					ed_address.setText(str_address1 + "," + str_address2);
				}
				else
				{
					if(str_address1.length() != 0
							&& !str_address1.equalsIgnoreCase("null"))
					{
						ed_address.setText(str_address1);
					}
					if(str_address2.length() != 0
							&& !str_address2.equalsIgnoreCase("null"))
					{
						ed_address.setText(str_address2);
					}
				}
			
				

				if (str_zip.equalsIgnoreCase("null")) {

				} else {
					ed_zip.setText(str_zip);
				}
				ed_country.setText(str_country);
				ed_region.setText(str_region);
				ed_city.setText(str_city);
				if (str_mobile.length() != 0
						&& !str_mobile.equalsIgnoreCase("null")
						&& !str_mobile.equalsIgnoreCase("0")) {
					ed_phone.setText(str_mobile);
				}

			}
			
			if (str_date.length() != 0 && !str_date.equalsIgnoreCase("null")) {
				ed_date.setText(str_date);
			}
			if (str_time.length() != 0 && !str_time.equalsIgnoreCase("null")) {
				ed_time.setText(str_time);
			}
			if (str_fname.length() != 0 && !str_fname.equalsIgnoreCase("null")) {
				ed_firstname.setText(str_fname);
			}
			if (str_sname.length() != 0 && !str_sname.equalsIgnoreCase("null")) {
				ed_lastname.setText(str_sname);
			}
			if (str_phone.length() != 0 && !str_phone.equalsIgnoreCase("null")
					&& !str_phone.equalsIgnoreCase("0")) {
				ed_phone.setText(str_phone);
			}
			if (str_email.length() != 0 && !str_email.equalsIgnoreCase("null")) {
				ed_email.setText(str_email);
			}
			if (str_bussiness.length() != 0
					&& !str_bussiness.equalsIgnoreCase("null")) {
				ed_bussiness.setText(str_bussiness);
			}
			if (str_occassion.length() != 0
					&& !str_occassion.equalsIgnoreCase("null")) {
				ed_occasion.setText(str_occassion);
			}
			if (str_meal.length() != 0 && !str_meal.equalsIgnoreCase("null")) {
				ed_meal_duration.setText(str_meal);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// language*****************
		Locale.getDefault().getLanguage();
		System.out.println("Device_language"
				+ Locale.getDefault().getLanguage());

		String langPref = "Language";
		SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs_oncreat.getString(langPref, "");

		System.out.println("Murtuza_Nalawala_language_oncreat" + language);
		if (language.equalsIgnoreCase("")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if");

		} else if (language.equalsIgnoreCase("ro")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_ar");
			setLocaleonload("ro");

		} else if (language.equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
			setLocaleonload("en");

		} else {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
			setLocaleonload("en");

		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// loadLocale();
		// LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		// language*****************
	}
	// language***************
	@Override
	public void onResume() {
		System.out.println("murtuza_nalawala");
		super.onResume(); // Always call the superclass method first
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}
	@Override
	protected void onStop() {
		super.onStop();

	}
	@Override
	protected void onPause() {
		super.onPause();

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	public void loadLocale() {

		System.out.println("Murtuza_Nalawala");
		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");
		System.out.println("Murtuza_Nalawala_language" + language);

		changeLang(language);
	}
	public void changeLang(String lang) {
		System.out.println("Murtuza_Nalawala_changeLang");

		if (lang.equalsIgnoreCase(""))
			return;
		myLocale = new Locale(lang);
		System.out.println("Murtuza_Nalawala_123456" + myLocale);
		if (myLocale.toString().equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_if" + myLocale);

		} else if (myLocale.toString().equalsIgnoreCase("ar")) {
			System.out.println("Murtuza_Nalawala_language_else" + myLocale);
			System.out.println("IN_arabic");

		}
		saveLocale(lang);
		DBAdapter.deleteall();
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
		// updateTexts();

	}

	public void saveLocale(String lang) {

		String langPref = "Language";
		System.out.println("Murtuza_Nalawala_langPref_if" + langPref);
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		System.out.println("Murtuza_Nalawala_langPref_prefs" + prefs);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
	}
	public void setLocaleonload(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		System.out.println("Murtuza_Nalawala_deleteall");

	}

	// language***************

	// @Override
	// public void onResume() {
	// System.out.println("murtuza_nalawala");
	// super.onResume(); // Always call the superclass method first
	// }
	private void initialization() {
		// TODO Auto-generated method stub
		ed_firstname = (EditText) findViewById(R.id.tg_ed_fname);
		ed_lastname = (EditText) findViewById(R.id.tg_ed_lname);
		ed_email = (EditText) findViewById(R.id.tg_ed_email);
		ed_phone = (EditText) findViewById(R.id.tg_ed_phone);
		ed_address = (EditText) findViewById(R.id.tg_ed_address);
		ed_zip = (EditText) findViewById(R.id.tg_ed_zip);
		ed_country = (EditText) findViewById(R.id.tg_ed_country);
		ed_region = (EditText) findViewById(R.id.tg_ed_region);
		ed_city = (EditText) findViewById(R.id.tg_ed_city);

		ed_bussiness = (EditText) findViewById(R.id.tg_ed_business);
		ed_occasion = (EditText) findViewById(R.id.tg_ed_occasion);
		ed_date = (EditText) findViewById(R.id.tg_ed_date);
		ed_time = (EditText) findViewById(R.id.tg_ed_time);
		ed_meal_duration = (EditText) findViewById(R.id.tg_ed_meal_duration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grab_table_profile, menu);
		return true;
	}

}
