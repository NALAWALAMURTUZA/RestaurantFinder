package com.rf_user.activity;

import java.util.Locale;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class MyNetworking extends Activity {

	ImageView rf_networking_menu_icon, rf_networking_facebook_icon,
			rf_networking_twitter_icon;
	Intent in;
	String subject;
	String composeEmail ;
	String link;

	/* Internet connection */
	ConnectionDetector cd;
	
	/* Language conversion */
	private Locale myLocale;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_my_networking);

		try {
			
			//Global_variable.post_review_activity="";

			/* create Object of internet connection* */
			cd = new ConnectionDetector(getApplicationContext());

			subject = getString(R.string.str_my_networking_subject);
			 composeEmail = getString(R.string.str_my_networking_compose_email);
			
			initialize();
			setlistner();
			
			loadLocale();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

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

	private void setlistner() {
		// TODO Auto-generated method stub

		try {

			rf_networking_facebook_icon
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							PackageManager pm = getPackageManager();
							try {

								Intent waIntent = new Intent(Intent.ACTION_SEND);
								waIntent.setType("text/plain");
								// String text =
								// "https://play.google.com/store/apps/details?id=com.cricketbuzz.cricscore";

								link = "https://play.google.com/store/apps";
								
								PackageInfo info = pm.getPackageInfo(
										"com.facebook.katana",
										PackageManager.GET_META_DATA);
								// Check if package exists or not. If not then
								// code
								// in catch block will be called
								waIntent.setPackage("com.facebook.katana");

								waIntent.putExtra(Intent.EXTRA_TEXT, link);
								startActivity(Intent.createChooser(waIntent,
										"Share with"));

							} catch (NameNotFoundException e) {

								Toast.makeText(getApplicationContext(),
										getString(R.string.str_Fb_not_Installed),
										Toast.LENGTH_SHORT).show();
							}

						}

					});

			rf_networking_twitter_icon
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							PackageManager pm = getPackageManager();
							try {

								Intent waIntent = new Intent(Intent.ACTION_SEND);
								waIntent.setType("text/plain");
								// String text =
								// "https://play.google.com/store/apps/details?id=com.cricketbuzz.cricscore";

								link = "https://play.google.com/store/apps";
								
								PackageInfo info = pm.getPackageInfo(
										"com.twitter.android",
										PackageManager.GET_META_DATA);
								// Check if package exists or not. If not then
								// code
								// in catch block will be called
								waIntent.setPackage("com.twitter.android");

								waIntent.putExtra(Intent.EXTRA_TEXT, link);
								startActivity(Intent.createChooser(waIntent,
										"Share with"));

							} catch (NameNotFoundException e) {

								Toast.makeText(getApplicationContext(),
										getString(R.string.str_twitter_not),
										Toast.LENGTH_SHORT).show();
							}

						}

					});

			rf_networking_menu_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(MyNetworking.this,
								rf_networking_menu_icon);
						popup.getMenuInflater().inflate(R.menu.popup,
								popup.getMenu());

						popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem item) {
								// TODO Auto-generated method stub

								System.out.println("!!!!!Item"
										+ item.getTitle());

								if (item.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_booking))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyBooking.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_profile))) {
									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyProfile.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_favourites))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											// Global_variable.activity =
											// "Categories";

											Intent in = new Intent(
													getApplicationContext(),
													MyFavourites.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();
										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_networking))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyNetworking.class);
											startActivity(in);
										}
										}
										else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equals(getString(R.string.about_restaurant))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											if (Global_variable.abt_rest_flag == true) {
												in = new Intent(
														getApplicationContext(),
														AboutRestaurant.class);
												startActivity(in);
											}

										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item.getTitle().toString()
										.equals(getString(R.string.logout))) {

									try {
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {

											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new UserLogout(
														MyNetworking.this)
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO
														// Auto-generated
														// method stub
														Toast.makeText(
																getApplicationContext(),
																R.string.No_Internet_Connection,
																Toast.LENGTH_SHORT)
																.show();

													}

												});
											}

										} else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								return false;
							}
						});

						popup.show();
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void initialize() {
		// TODO Auto-generated method stub
		try {

			rf_networking_menu_icon = (ImageView) findViewById(R.id.rf_networking_menu_icon);
			rf_networking_facebook_icon = (ImageView) findViewById(R.id.rf_networking_facebook_icon);
			rf_networking_twitter_icon = (ImageView) findViewById(R.id.rf_networking_twitter_icon);

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
