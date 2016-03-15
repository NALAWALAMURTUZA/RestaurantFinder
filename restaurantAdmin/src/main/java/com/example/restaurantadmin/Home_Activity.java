package com.example.restaurantadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.rf.restaurantadmin.Login_Activity;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class Home_Activity extends Activity {

	public static ProgressDialog p;

	Button btn_register, btn_login;
	EditText rf_login_ed_username, rf_login_ed_password;
	LinearLayout ll_manage_booking, ll_takebooking, ll_allbooking,
			ll_manage_feedback, ll_manage_customers, ll_manage_photos,
			ll_subscription, ll_rest_presantation, ll_glob_set, ll_cart_menu,
			ll_foodorder, ll_logout;
	ConnectionDetector cd;
	public static ImageView img_menu;
	private Locale myLocale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_home_screen);
		initialization();
		cd = new ConnectionDetector(getApplicationContext());
		secOnclicklistner();
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

//		loadLocale();
//		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());

	}
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

//	@Override
//	public void onResume() {
//		System.out.println("murtuza_nalawala");
//		super.onResume(); // Always call the superclass method first
//	}

	private void secOnclicklistner() {
		// TODO Auto-generated method stub
		img_menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// FilterDialog();
				PopupMenu popup = new PopupMenu(Home_Activity.this,
						Home_Activity.img_menu);
				popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						if (item.getTitle().toString()
								.equals(getResources().getString(R.string.popup_mp))) {

							Intent intent = new Intent(Home_Activity.this,
									ManageGalleryActivity.class);
							startActivity(intent);
							// finish();

						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_mb))) {

							Intent intent = new Intent(Home_Activity.this,

									TakeBooking_Tablayout.class);
							startActivity(intent);
							// finish();

						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_tb))) {

							Intent intent = new Intent(Home_Activity.this,

							TakeBookingActivity.class);
							startActivity(intent);
							// finish();

						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_rp))) {

							Intent intent = new Intent(Home_Activity.this,

							RestaurantPrasentationActivity.class);
							startActivity(intent);
							// finish();

						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_cm))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										CartSetMenuActivity.class);
								startActivity(intent);
							}
							// finish();
						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_ab))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										AllBookingActivity.class);
								startActivity(intent);
							}
							// finish();
						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_mc))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										ManageCustomersActivity.class);
								startActivity(intent);
							}
							// finish();
						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_mf))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										ManageFeedbackActivity.class);
								startActivity(intent);
							}
							// finish();
						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_cs))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										ManageSubscriptionActivity.class);
								startActivity(intent);
							}
							// finish();
						} 
						else if (item.getTitle().toString()
								.equals(getString(R.string.popup_fo))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										Food_Detail_Categories_Activity.class);
								startActivity(intent);
							}
							// finish();
						}else if (item.getTitle().toString()
								.equals(getString(R.string.popup_gs))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										GlobalSettingActivity.class);
								startActivity(intent);
							}
							// finish();
						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_tb))) {

							{
								Intent intent = new Intent(Home_Activity.this,
										TakeBooking_Tablayout.class);
								startActivity(intent);
							}
							// finish();
						} else if (item.getTitle().toString()
								.equals(getString(R.string.popup_signout))) {

							{
								System.out.println("logout");
								Global_variable.logindata=new JSONObject();
//								Global_variable.sessid="";
								Global_variable.admin_uid="";
								Global_variable.restaurant_id="";
								Global_variable.rest_uid="";
								Global_variable.array_food=new JSONArray();
								Global_variable.loginflag=false;
								System.out.println("loginflag"+Global_variable.loginflag);
								Intent intent = new Intent(Home_Activity.this,
										Login_Activity.class);
								startActivity(intent);
							}
							// finish();
						}
						return true;

					}
				});

				popup.show();
			}
		});
		ll_manage_booking.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						TakeBooking_Tablayout.class);
				startActivity(i);
			}
		});
		ll_takebooking.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						TakeBookingActivity.class);
				startActivity(i);
			}
		});
		ll_allbooking.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						AllBookingActivity.class);
				startActivity(i);
			}
		});
		ll_manage_customers.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						ManageCustomersActivity.class);
				startActivity(i);
			}
		});
		ll_manage_feedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						ManageFeedbackActivity.class);
				startActivity(i);
			}
		});
		ll_manage_photos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						ManageGalleryActivity.class);
				startActivity(i);
			}
		});
		ll_rest_presantation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						RestaurantPrasentationActivity.class);
				startActivity(i);
			}
		});
		ll_subscription.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						ManageSubscriptionActivity.class);
				startActivity(i);
			}
		});
		ll_glob_set.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(Home_Activity.this,
						GlobalSettingActivity.class);
				startActivity(i);
			}
		});
		ll_logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Global_variable.logindata=new JSONObject();
//				Global_variable.array_food=new JSONArray();
				
				System.out.println("logoutll");
				Global_variable.logindata=new JSONObject();
				//Global_variable.sessid="";
				Global_variable.admin_uid="";
				Global_variable.restaurant_id="";
				Global_variable.rest_uid="";
				Global_variable.array_food=new JSONArray();
				Global_variable.loginflag=false;
				System.out.println("loginflagll"+Global_variable.loginflag);
				Intent i = new Intent(Home_Activity.this, Login_Activity.class);
				startActivity(i);
			}
		});
		ll_foodorder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						Food_Detail_Categories_Activity.class);
				startActivity(i);
			}
		});
		ll_cart_menu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home_Activity.this,
						CartSetMenuActivity.class);
				startActivity(i);
			}
		});
	}

	private void initialization() {
		// TODO Auto-generated method stub
		img_menu = (ImageView) findViewById(R.id.img_menu);
		ll_manage_booking = (LinearLayout) findViewById(R.id.ll_manage_booking);
		ll_manage_customers = (LinearLayout) findViewById(R.id.ll_manage_customers);
		ll_manage_feedback = (LinearLayout) findViewById(R.id.ll_manage_feedback);
		ll_manage_photos = (LinearLayout) findViewById(R.id.ll_manage_photos);
		ll_takebooking = (LinearLayout) findViewById(R.id.ll_takebooking);
		ll_glob_set = (LinearLayout) findViewById(R.id.ll_glob_set);
		ll_allbooking = (LinearLayout) findViewById(R.id.ll_allbooking);
		ll_subscription = (LinearLayout) findViewById(R.id.ll_subscription);
		ll_cart_menu = (LinearLayout) findViewById(R.id.ll_cart_menu);
		ll_foodorder = (LinearLayout) findViewById(R.id.ll_foodorder);
		ll_logout = (LinearLayout) findViewById(R.id.ll_logout);
		ll_rest_presantation = (LinearLayout) findViewById(R.id.ll_rest_presantation);
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

}
