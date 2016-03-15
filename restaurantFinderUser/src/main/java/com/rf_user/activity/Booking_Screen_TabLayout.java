package com.rf_user.activity;

import java.util.Locale;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.global.Global_variable;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Booking_Screen_TabLayout extends TabActivity {
	public static  TabHost tab;
	public  Resources res;
	public  TabSpec spec;
	public  Intent intent1, intent2;

	public static LinearLayout checkout_tblayout_header;

	public static TextView rf_booking_date_header, rf_booking_time_header,
			rf_booking_number_of_people_header, rf_booking_restaurant_name;

	/* String declaration */
	String str_booking_date, str_booking_time, str_booking_number_of_people,
			str_loyalty, str_offer_id;
	
	/*Language conversion*/
	private Locale myLocale;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_booking_confirmation_screen);

		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			Intent in = getIntent();
			Global_variable.str_booking_date = in
					.getStringExtra("booking_date");
			Global_variable.str_booking_time = in
					.getStringExtra("booking_time");
			Global_variable.str_booking_number_of_people = in
					.getStringExtra("number_of_people");

			System.out.println("shikha...." + str_booking_date + "...."
					+ str_booking_time + "..." + str_booking_number_of_people);

			InitializeWidget();

			setdata();

			setListener();

			tab = getTabHost();
			tab.getTabWidget().setClickable(false);
			tab.setClickable(false);

			// Tab for Step 1
			TabSpec step1spec = tab.newTabSpec(getString(R.string.Booking_Confirmation));
			// setting Title and Icon for the Tab
			step1spec.setIndicator(getString(R.string.Booking_Confirmation));
			// step1spec.setIndicator("Booking Confirmation");
			intent1 = new Intent(this, BookingConfirmationScreen.class);
			step1spec.setContent(intent1);

			// Tab for Step 2
			TabSpec step2spec = tab.newTabSpec(getString(R.string.Invitation));
			step2spec.setIndicator(getString(R.string.Invitation));
			// step2spec.setIndicator("Invitation");
			intent2 = new Intent(this, InvitationScreen.class);
			step2spec.setContent(intent2);

			// Adding all TabSpec to TabHost
			tab.addTab(step1spec); // Adding step 1 tab
			tab.addTab(step2spec); // Adding step 2 tab

			tab.getTabWidget()
					.getChildTabViewAt(0)
					.setBackgroundResource(
							R.drawable.icon_booking_confirmation_tab);
			tab.getTabWidget()
					.getChildTabViewAt(1)
					.setBackgroundResource(
							R.drawable.icon_booking_confirmation_tab);
			
			TextView tv = (TextView) tab.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
	        tv.setTextColor(Color.WHITE);
	        tv.setTextSize(12);
	     // center text
	        tv.setGravity(Gravity.CENTER);
            // wrap text
            tv.setSingleLine(false);
	        TextView tv1 = (TextView) tab.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
	        tv1.setTextColor(Color.WHITE);
	        tv1.setTextSize(12);
	     // center text
	        tv1.setGravity(Gravity.CENTER);
            // wrap text
            tv1.setSingleLine(false);

			tab.setCurrentTab(0);
			tab.getTabWidget().getChildAt(1).setClickable(false);
			
			
//			loadLocale();
			
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
	
	/*Language conversion methods */
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

	private void setdata() {
		// TODO Auto-generated method stub

		try {
			System.out.println("!!!!!!!!!!!!!!!!" + Global_variable.hotel_name);
			if (Global_variable.hotel_name.length() != 0) {
				rf_booking_restaurant_name.setText(Global_variable.hotel_name);
			}

			if (Global_variable.str_booking_date != null) {

				System.out.println("In date not null"
						+ Global_variable.str_booking_date);

				rf_booking_date_header
						.setText(Global_variable.str_booking_date);

				// rf_booking_date_box.setText(Global_variable.str_booking_date);

			} else {

				System.out.println("In date  null"
						+ Global_variable.str_booking_date);

			}
			if (Global_variable.str_booking_time != null) {

				System.out.println("In time not null"
						+ Global_variable.str_booking_time);

				rf_booking_time_header
						.setText(Global_variable.str_booking_time);

				// rf_booking_time_box.setText(Global_variable.str_booking_time);

			} else {

				System.out.println("In time null"
						+ Global_variable.str_booking_time);

			}
			if (Global_variable.str_booking_number_of_people != null) {

				System.out.println("In no of people not null"
						+ Global_variable.str_booking_number_of_people);

				// rf_booking_people_box.setText(Global_variable.str_booking_number_of_people);

				rf_booking_number_of_people_header
						.setText(Global_variable.str_booking_number_of_people);
			} else {

				System.out.println("In no of people  null");

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void setListener() {
		// TODO Auto-generated method stub

	}

	private void InitializeWidget() {
		// TODO Auto-generated method stub

		/* Header initialization */

		try {

			checkout_tblayout_header = (LinearLayout) findViewById(R.id.checkout_tblayout_header);

			rf_booking_date_header = (TextView) findViewById(R.id.rf_booking_date_header);
			rf_booking_time_header = (TextView) findViewById(R.id.rf_booking_time_header);
			rf_booking_number_of_people_header = (TextView) findViewById(R.id.rf_booking_number_of_people_header);
			rf_booking_restaurant_name = (TextView) findViewById(R.id.rf_booking_restaurant_name);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
		System.out.println("!!!!!!!!!!!!!!!onStart");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		System.out.println("!!!!!!!!!!!!!!!onRestart");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		System.out.println("!!!!!!!!!!!!!!!onResume");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		System.out.println("!!!!!!!!!!!!!!!onPause");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		System.out.println("!!!!!!!!!!!!!!!onStop");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		System.out.println("!!!!!!!!!!!!!!!onDestroy");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}
}
