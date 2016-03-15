package com.rf_user.activity;

import java.util.Locale;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.MyBookingAdapter;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class OnlineOrderBookings extends Activity {

	ListView rf_my_booking_list_oo;
	LinearLayout ly_No_oo_Booking;
	public MyBookingAdapter my_booking_adapter;
	ConnectionDetector cd;
	ProgressDialog progressDialog;
	String TAG_SUCCESS = "success";

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_online_order_bookings);

		try {

			initializeWidgets();

			System.out.println("OnlineActivityActivity"
					+ Global_variable.booking_listData_oo_order);
			if (Global_variable.booking_listData_oo_order.size() == 0) {
				ly_No_oo_Booking.setVisibility(View.VISIBLE);
				rf_my_booking_list_oo.setVisibility(View.GONE);
			}
			if (Global_variable.booking_listData_oo_order.size() == 0) {
				if (Global_variable.booking_listData_oo_order != null) {
					Global_variable.booking_listData_oo_order.clear();
				}

				rf_my_booking_list_oo.invalidateViews();
				ly_No_oo_Booking.setVisibility(View.VISIBLE);
				rf_my_booking_list_oo.setVisibility(View.GONE);
				System.out.println("pankajsakariyadata123");
			} else {
				ly_No_oo_Booking.setVisibility(View.GONE);
				rf_my_booking_list_oo.setVisibility(View.VISIBLE);
				if (Global_variable.booking_listData_oo_order != null) {
					my_booking_adapter = new MyBookingAdapter(
							OnlineOrderBookings.this,
							Global_variable.booking_listData_oo_order, "OO");
					System.out.println("pankaj_inside_hotel_list");
					if (my_booking_adapter != null) {
						rf_my_booking_list_oo.setAdapter(my_booking_adapter);
						// listview_adapter.notifyDataSetChanged();
						System.out.println("pankaj_inside_list_adapter");
						rf_my_booking_list_oo.invalidateViews();

					}

				} else {
					System.out.println("pankaj_inside_else");
					Global_variable.booking_listData_oo_order.clear();
					my_booking_adapter = new MyBookingAdapter(
							OnlineOrderBookings.this,
							Global_variable.booking_listData_oo_order, "OO");
					rf_my_booking_list_oo.setAdapter(my_booking_adapter);

					// listview_adapter.notifyDataSetChanged();
					// Hotel_list_listviw.invalidateViews();
				}

			}

//			loadLocale();

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

	/* Language conversion methods */
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

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		rf_my_booking_list_oo = (ListView) findViewById(R.id.rf_my_booking_list_oo);
		ly_No_oo_Booking = (LinearLayout) findViewById(R.id.ly_No_oo_Booking);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			System.out.println("!!!!!!!!!!!!!!!!!!!!!on back"
					+ Global_variable.activity);

			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {

		System.out.println("!!!!!!!!!!!!!!!!!!!!!on back"
				+ Global_variable.activity);
		
		
		try {
			if (Global_variable.activity == "PostReview") {
				

			} else {
				super.onBackPressed();

			}
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
		
		
//		try {
//
//		
//		if (Global_variable.activity!="null"
//				|| Global_variable.activity != "") {
//			if (Global_variable.activity.equalsIgnoreCase("PostReview")) {
//
//			} else {
//				// Global_variable.activity="";
//				super.onBackPressed();
//			}
//		} else {
//			// Global_variable.activity="";
//			super.onBackPressed();
//		}
//		
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		}

	}
}
