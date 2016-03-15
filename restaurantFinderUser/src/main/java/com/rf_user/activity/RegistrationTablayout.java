package com.rf_user.activity;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.internet.ConnectionDetector;

public class RegistrationTablayout extends TabActivity {
	public static TabHost tab;
	public static Resources res;
	public static TabSpec spec;
	public static Intent intent1, intent2, intent3, intent4;
	Dialog ExitAppDialog;
	ConnectionDetector cd;
	public static TextView txv_Dialogtext;
	public static ImageView img_yes, img_no;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
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

		tab.getTabWidget()
				.getChildTabViewAt(0)
				.setBackgroundResource(R.drawable.icon_booking_confirmation_tab);
		tab.getTabWidget()
				.getChildTabViewAt(1)
				.setBackgroundResource(R.drawable.icon_booking_confirmation_tab);
		
		tab.getTabWidget()
		.getChildTabViewAt(2)
		.setBackgroundResource(R.drawable.icon_booking_confirmation_tab);
		
		tab.getTabWidget()
		.getChildTabViewAt(3)
		.setBackgroundResource(R.drawable.icon_booking_confirmation_tab);

		TextView tv = (TextView) tab.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(12);
		// center text
		tv.setGravity(Gravity.CENTER);
		// wrap text
		tv.setSingleLine(true);
		
		TextView tv1 = (TextView) tab.getTabWidget().getChildAt(1)
				.findViewById(android.R.id.title);
		tv1.setTextColor(Color.WHITE);
		tv1.setTextSize(12);
		// center text
		tv1.setGravity(Gravity.CENTER);
		// wrap text
		tv1.setSingleLine(true);
		
		TextView tv2 = (TextView) tab.getTabWidget().getChildAt(2)
				.findViewById(android.R.id.title);
		tv2.setTextColor(Color.WHITE);
		tv2.setTextSize(12);
		// center text
		tv2.setGravity(Gravity.CENTER);
		// wrap text
		tv2.setSingleLine(true);
		
		
		TextView tv3 = (TextView) tab.getTabWidget().getChildAt(3)
				.findViewById(android.R.id.title);
		tv3.setTextColor(Color.WHITE);
		tv3.setTextSize(12);
		// center text
		tv3.setGravity(Gravity.CENTER);
		// wrap text
		tv3.setSingleLine(true);

		tab.setCurrentTab(0);
		tab.getTabWidget().getChildAt(1).setClickable(false);
		tab.getTabWidget().getChildAt(2).setClickable(false);
		tab.getTabWidget().getChildAt(3).setClickable(false);
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
	private void setListener() {
		// TODO Auto-generated method stub

	}

	private void InitializeWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
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

}
