package com.example.restaurantadmin;

import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import java.util.Locale;

public class Registration_Tablayout_Activity extends TabActivity {
	public static TabHost tab;
	public static Resources res;
	public static TabSpec spec;
	public static Intent intent1, intent2, intent3, intent4;
	Dialog ExitAppDialog;
	ConnectionDetector cd;
	public static TextView txv_Dialogtext;
	public static ImageView img_yes, img_no;
	private Locale myLocale;
	boolean locale_flag = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_registration);
		cd = new ConnectionDetector(getApplicationContext());
		InitializeWidget();
		cd = new ConnectionDetector(getApplicationContext());
		setListener();

		tab = getTabHost();
		tab.getTabWidget().setClickable(false);
		tab.setClickable(false);
		// Tab for Step 1
		TabSpec step1spec = tab
				.newTabSpec(getResources().getString(R.string.str_rf_registration_step1));
		// setting Title and Icon for the Tab
		// step1spec.setIndicator("Step 1",
		// getResources().getDrawable(R.drawable.icon_photos_tab));
		step1spec.setIndicator(getResources().getString(R.string.str_rf_registration_step1));
		intent1 = new Intent(this, Registration_step1_Activity.class);
		step1spec.setContent(intent1);

		// Tab for Step 2
		TabSpec step2spec = tab
				.newTabSpec(getResources().getString(R.string.str_rf_registration_step2));
		step2spec.setIndicator(getResources().getString(R.string.str_rf_registration_step2));
		intent2 = new Intent(this, Registration_step2_Activity.class);
		step2spec.setContent(intent2);

		// Tab for Step 3
		TabSpec step3spec = tab
				.newTabSpec(getResources().getString(R.string.str_rf_registration_step3));
		step3spec.setIndicator(getResources().getString(R.string.str_rf_registration_step3));
		intent3 = new Intent(this, Registration_step3_Activity.class);
		step3spec.setContent(intent3);

		// Tab for Step 4
		TabSpec step4spec = tab
				.newTabSpec(getResources().getString(R.string.str_rf_registration_step4));
		step4spec.setIndicator(getResources().getString(R.string.str_rf_registration_step4));
		intent4 = new Intent(this, Registration_step4_Activity.class);
		step4spec.setContent(intent4);

		// Adding all TabSpec to TabHost
		tab.addTab(step1spec); // Adding step 1 tab
		tab.addTab(step2spec); // Adding step 2 tab
		tab.addTab(step3spec); // Adding step 3 tab
		tab.addTab(step4spec); // Adding step 4 tab
		tab.setCurrentTab(0);
		tab.getTabWidget().getChildAt(1).setClickable(false);
		tab.getTabWidget().getChildAt(2).setClickable(false);
		tab.getTabWidget().getChildAt(3).setClickable(false);
		Locale.getDefault().getLanguage();
		System.out.println("Device_language"
				+ Locale.getDefault().getLanguage());

		String langPref = "Language";
		SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs_oncreat.getString(langPref, "");

		System.out.println("Murtuza_Nalawala_language_oncreat" + language);
		

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

//		loadLocale();

//		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		}
//	public void loadLocale() {
//
//		System.out.println("Murtuza_Nalawala");
//		String langPref = "Language";
//		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
//				Activity.MODE_PRIVATE);
//		String language = prefs.getString(langPref, "");
//		System.out.println("Murtuza_Nalawala_language" + language);
//
//		changeLang(language);
//	}
//	public void changeLang(String lang) {
//		System.out.println("Murtuza_Nalawala_changeLang");
//
//		if (lang.equalsIgnoreCase(""))
//			return;
//		myLocale = new Locale(lang);
//		System.out.println("Murtuza_Nalawala_123456" + myLocale);
//		if (myLocale.toString().equalsIgnoreCase("en")) {
//			System.out.println("Murtuza_Nalawala_language_if" + myLocale);
//
//		} else if (myLocale.toString().equalsIgnoreCase("ar")) {
//			System.out.println("Murtuza_Nalawala_language_else" + myLocale);
//			System.out.println("IN_arabic");
//
//		}
//		saveLocale(lang);
//		DBAdapter.deleteall();
//		Locale.setDefault(myLocale);
//		android.content.res.Configuration config = new android.content.res.Configuration();
//		config.locale = myLocale;
//		getBaseContext().getResources().updateConfiguration(config,
//				getBaseContext().getResources().getDisplayMetrics());
//		// updateTexts();
//
//	}
//
//	public void saveLocale(String lang) {
//
//		String langPref = "Language";
//		System.out.println("Murtuza_Nalawala_langPref_if" + langPref);
//		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
//				Activity.MODE_PRIVATE);
//		System.out.println("Murtuza_Nalawala_langPref_prefs" + prefs);
//		SharedPreferences.Editor editor = prefs.edit();
//		editor.putString(langPref, lang);
//		editor.commit();
//	}
//	public void setLocaleonload(String lang) {
//
//		myLocale = new Locale(lang);
//		Resources res = getResources();
//		DisplayMetrics dm = res.getDisplayMetrics();
//		Configuration conf = res.getConfiguration();
//		conf.locale = myLocale;
//		res.updateConfiguration(conf, dm);
//		System.out.println("Murtuza_Nalawala_deleteall");
//
//	}
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

	private void setListener() {
		// TODO Auto-generated method stub

	}

	private void InitializeWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				onBackPressed();
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {

			// ExitFromAppDialog();
		} else {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),
							R.string.No_Internet_Connection, Toast.LENGTH_SHORT)
							.show();

				}

			});
		}

	}

	// // Onback press Exit app
	// public void ExitFromAppDialog() {
	//
	// ExitAppDialog = new Dialog(Registration_Tablayout_Activity.this);
	// ExitAppDialog.setContentView(R.layout.popup_exitapp);
	// ExitAppDialog.setTitle("Exit");
	// img_yes = (ImageView) ExitAppDialog.findViewById(R.id.img_yes);
	// img_no = (ImageView) ExitAppDialog.findViewById(R.id.img_no);
	// txv_Dialogtext = (TextView) ExitAppDialog.findViewById(R.id.txv_dialog);
	// try {
	// img_yes.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// Intent i = new Intent(Registration_Tablayout_Activity.this,
	// Login_Activity.class);
	// startActivity(i);
	// }
	// });
	// img_no.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// ExitAppDialog.dismiss();
	// }
	// });
	//
	// // customHandler.post(updateTimerThread, 0);
	// ExitAppDialog.show();
	// ExitAppDialog.setCancelable(false);
	// ExitAppDialog.setCanceledOnTouchOutside(false);
	// } catch (NullPointerException n) {
	//
	// }
	// }
}
