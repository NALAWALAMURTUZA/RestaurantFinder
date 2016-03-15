package com.rf.restaurantadmin;
import static com.rf.restaurantadmin.CommonUtilities.TAG;
import static com.rf.restaurantadmin.CommonUtilities.displayMessage;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.rf.restaurantadmin.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.rf.restaurantadmin.CommonUtilities.EXTRA_MESSAGE;
import static com.rf.restaurantadmin.CommonUtilities.SENDER_ID;

import com.example.restaurantadmin.ConnectionDetector;
import com.example.restaurantadmin.ForgetPasswordActivity;
import com.example.restaurantadmin.Home_Activity;
import com.google.android.gcm.GCMRegistrar;
import com.restaurantadmin.Global.Global_variable;

import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadminconnection.myconnection;
import com.sharedprefernce.LanguageConvertLocalPrefernce;
import com.sharedprefernce.LanguageConvertPreferenceClass;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;

public class Login_Activity extends Activity {

	public static ProgressDialog p, p1;
	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();
	Button btn_register, btn_login, btn_hi;
	EditText rf_login_ed_username, rf_login_ed_password;
	ConnectionDetector cd;
	CheckBox ch_rememberme;
	TextView rf_login_tv_forpassword;
	private ImageView img_en, img_ar;
	private Locale myLocale;
	boolean locale_flag = false;
	boolean reme = false;
	// boolean RO_flag = false;
	Dialog ExitAppDialog;
	public static ImageView img_yes, img_no;
	boolean regi = false;
	boolean login = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_login_);

		this.img_en = (ImageView) findViewById(R.id.btn_en);
		this.img_ar = (ImageView) findViewById(R.id.btn_ar);
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
		} else if (language.equalsIgnoreCase("hi")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
			setLocaleonload("hi");
		} else {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
			setLocaleonload("en");
		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

//		loadLocale();
		
		loadSavedPreferences();
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


		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");


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

		} else if (myLocale.toString().equalsIgnoreCase("ro")) {
			System.out.println("Murtuza_Nalawala_language_else" + myLocale);
			System.out.println("IN_arabic");

		}
		saveLocale(lang);
		DBAdapter.deleteall();
		Locale.setDefault(myLocale);
		Configuration config = new Configuration();
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
		Editor editor = prefs.edit();
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

	}

	private void loadSavedPreferences() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean cbValue = sp.getBoolean("CHECKBOX", false);
		String uname = sp.getString("USERNAME", "Enter username");
		String pwd = sp.getString("PASSWORD", "Enter password");
		if (cbValue) {
			ch_rememberme.setChecked(true);

			rf_login_ed_username.setText(uname);
			rf_login_ed_password.setText(pwd);

		} else {
			ch_rememberme.setChecked(false);
		}

	}

	private void savePreferences(String key, boolean value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	private void savePreferences(String username_key, String username_value,
			String password_key, String password_value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(username_key, username_value);
		edit.putString(password_key, password_value);
		edit.commit();

		System.out.print("shared.." + edit);
	}
	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		System.out.println("Murtuza_Nalawala_deleteall");

		Intent refresh = new Intent(this, Login_Activity.class);
		startActivity(refresh);
	}
	private void secOnclicklistner() {
		// TODO Auto-generated method stub
		img_ar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("langaugero1"
						+ LanguageConvertPreferenceClass
								.getlanguage(Login_Activity.this));
//				if (LanguageConvertPreferenceClass.getlanguage(
//						Login_Activity.this).equalsIgnoreCase("en")) {





				if (LanguageConvertLocalPrefernce
						.getLocale(getApplicationContext()).toString()
						.equalsIgnoreCase("ro")) {

				}
				else {
					LanguageConvertLocalPrefernce.saveLocale("ro",getApplicationContext());
					LanguageConvertLocalPrefernce.setLocale("ro",getApplicationContext());

					LanguageConvertPreferenceClass.setlanguage(
							Login_Activity.this, "ro");
					System.out.println("langaugero"
							+ LanguageConvertPreferenceClass
									.getlanguage(Login_Activity.this));
					// Global_variable.wjbty_en_Url =
					// "http://www.wjbty.com/ar/api/";
					locale_flag = true;
					setLocale("ro");
				}
//				}

			}
		});

		img_en.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("languageen1"
						+ LanguageConvertPreferenceClass
								.getlanguage(Login_Activity.this));
//				if (LanguageConvertPreferenceClass.getlanguage(
//						Login_Activity.this).equalsIgnoreCase("ro")) {
				if (LanguageConvertLocalPrefernce
						.getLocale(getApplicationContext()).toString()
						.equalsIgnoreCase("en")) {

				}else
				{
				LanguageConvertLocalPrefernce.saveLocale("en",getApplicationContext());
				LanguageConvertLocalPrefernce.setLocale("en",getApplicationContext());
					LanguageConvertPreferenceClass.setlanguage(
							Login_Activity.this, "en");
					System.out.println("languageen"
							+ LanguageConvertPreferenceClass
									.getlanguage(Login_Activity.this));
					// Global_variable.wjbty_en_Url =
					// "http://www.wjbty.com/en/api/";
					locale_flag = true;
					// reload();
					setLocale("en");
				}

			}
		});
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cd.isConnectingToInternet()) {
					regi = true;
					new async_init().execute();
				} else {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							Toast.makeText(getApplicationContext(),
									R.string.No_Internet_Connection,
									Toast.LENGTH_LONG).show();

						}

					});
				}

			}
		});
		rf_login_tv_forpassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(Login_Activity.this,
						ForgetPasswordActivity.class);
				startActivity(in);
			}
		});

		ch_rememberme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savePreferences("CHECKBOX", ch_rememberme.isChecked());
				if (ch_rememberme.isChecked())
					reme = false;
				savePreferences("USERNAME", rf_login_ed_username.getText()
						.toString(), "PASSWORD", rf_login_ed_password.getText()
						.toString());

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							// new login().execute();

						} else {

							runOnUiThread(new Runnable() {

								@Override
								public void run() {

									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(),
											R.string.No_Internet_Connection,
											Toast.LENGTH_SHORT).show();

								}

							});
						}

					}

				});

			}

		});
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("hi"
						+ rf_login_ed_username.getText().toString());
				if (cd.isConnectingToInternet()) {
					if (ch_rememberme.isChecked()) {
						reme = false;
						savePreferences("CHECKBOX", ch_rememberme.isChecked());
						savePreferences("USERNAME", rf_login_ed_username
								.getText().toString(), "PASSWORD",
								rf_login_ed_password.getText().toString());

					} else {
						savePreferences("CHECKBOX", ch_rememberme.isChecked());
					}
					login = true;
					new login().execute();
				} else {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),
									R.string.No_Internet_Connection,
									Toast.LENGTH_SHORT).show();

						}

					});
				}

			}
		});
	}

	/**** AsyncClass */
	// /
	public class login extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(Login_Activity.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("username", rf_login_ed_username.getText()
						.toString());
				obj_child.put("password", rf_login_ed_password.getText()
						.toString());
				obj_child.put("regId", gcmcall().toString());
				obj_parent.put("sessid", Global_variable.sessid);
				obj_parent.put("LoginForm", obj_child);
				System.out.println("Activity_Login" + obj_parent);
				// myconnection con = new myconnection();
//				String str_response = "";
//
//				str_response = Global_variable.language(Login_Activity.this,
//						Global_variable.rf_api_login, obj_parent);
//				System.out.println("!!!!pankaj" + str_response);
				myconnection con = new myconnection();
				obj_output = new JSONObject(
						con.connection(Login_Activity.this,
								Global_variable.rf_api_login,
								obj_parent));
//				obj_output = new JSONObject(str_response);
				System.out.println("loginoutout" + obj_output);

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (p.isShowing()) {
				p.dismiss();
				System.out.println("Login_response" + obj_output);
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = obj_output.getJSONObject("data");
					System.out.println("logindata" + Data);
					Global_variable.logindata = new JSONObject();
					// Global_variable.sessid="";
					Global_variable.admin_uid = "";
					Global_variable.restaurant_id = "";
					Global_variable.rest_uid = "";
					Global_variable.logindata = obj_output
							.getJSONObject("data");
					Global_variable.sessid = obj_output.getString("sessid");
					System.out.println("Login_response_data"
							+ Global_variable.logindata);
					System.out.println("11111datalogin" + Data);
					JSONObject obj_rest_details = Data
							.getJSONObject("restaurant_details");
					System.out
							.println("1111objrest_details" + obj_rest_details);
					Global_variable.admin_uid = Data.getString("user_id");
					Global_variable.restaurant_id = obj_rest_details
							.getString("id");
					Global_variable.rest_uid = obj_rest_details
							.getString("uid");

					System.out.println("1111adminuid"
							+ Global_variable.admin_uid);
					System.out.println("1111restaurant_id"
							+ Global_variable.restaurant_id);
					System.out.println("1111restid" + Global_variable.rest_uid);
					new async_init().execute();
					// Global_variable.array_food=new JSONArray();
					// Intent in = new Intent(Login_Activity.this,
					// Home_Activity.class);
					// startActivity(in);
				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("username")) {
							System.out.println("gyuknai");
							// System.out.println("inerrorlanguage"
							// + myLocale.toString()
							// .equalsIgnoreCase("en"));
							System.out.println("englishlangerro"
									+ LanguageConvertPreferenceClass
											.getlanguage(Login_Activity.this));
							// if (LanguageConvertPreferenceClass.getlanguage(
							// Login_Activity.this).equalsIgnoreCase("en")) {
							// System.out.println("ennnnnnn");
							// String str_en = "";
							// for (int i = 0; i <
							// Global_variable.array_error_en
							// .length(); i++) {
							// JSONObject error_obj =
							// Global_variable.array_error_en
							// .getJSONObject(i);
							// System.out.println("objen" + error_obj);
							// str_en = error_obj
							// .getString("error_desc_en");
							//
							// }
							// Toast.makeText(getApplicationContext(), str_en,
							// Toast.LENGTH_LONG).show();
							// } else {
							// System.out.println("roooooooo");
							// String str_ro = "";
							// for (int i = 0; i <
							// Global_variable.array_error_other
							// .length(); i++) {
							// JSONObject error_obj =
							// Global_variable.array_error_other
							// .getJSONObject(i);
							// System.out.println("objro" + error_obj);
							// str_ro = error_obj
							// .getString("error_desc_other");
							//
							// }
							// Toast.makeText(getApplicationContext(), str_ro,
							// Toast.LENGTH_LONG).show();
							// }

							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("username").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("password")) {
							Toast.makeText(
									Login_Activity.this,
									Errors.getJSONArray("password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
							// if (LanguageConvertPreferenceClass.getlanguage(
							// Login_Activity.this).equalsIgnoreCase("en")) {
							// System.out.println("ennnnnnn");
							// String str_en = "";
							// for (int i = 0; i <
							// Global_variable.array_error_en
							// .length(); i++) {
							// JSONObject error_obj =
							// Global_variable.array_error_en
							// .getJSONObject(i);
							// System.out.println("objen" + error_obj);
							// str_en = error_obj
							// .getString("error_desc_en");
							//
							// }
							// Toast.makeText(getApplicationContext(), str_en,
							// Toast.LENGTH_LONG).show();
							// } else {
							// System.out.println("roooooooo");
							// String str_ro = "";
							// for (int i = 0; i <
							// Global_variable.array_error_other
							// .length(); i++) {
							// JSONObject error_obj =
							// Global_variable.array_error_other
							// .getJSONObject(i);
							// System.out.println("objro" + error_obj);
							// str_ro = error_obj
							// .getString("error_desc_other");
							//
							// }
							// Toast.makeText(getApplicationContext(), str_ro,
							// Toast.LENGTH_LONG).show();
							// }

						}
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

		}
	}

	private void initialization() {
		// TODO Auto-generated method stub
		btn_register = (Button) findViewById(R.id.btn_register);
		// btn_hi = (Button) findViewById(R.id.btn_hi);
		// btn_hi.setText("हिंदी");
		btn_login = (Button) findViewById(R.id.btn_loging);
		rf_login_ed_password = (EditText) findViewById(R.id.rf_login_ed_password);
		rf_login_ed_username = (EditText) findViewById(R.id.rf_login_ed_username);
		ch_rememberme = (CheckBox) findViewById(R.id.ch_rememberme);
		rf_login_tv_forpassword = (TextView) findViewById(R.id.rf_login_tv_forpassword);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_step1_, menu);
		return true;
	}
	// Onback press Exit app
	public void ExitFromAppDialog() {

		ExitAppDialog = new Dialog(Login_Activity.this);
		ExitAppDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		ExitAppDialog.setContentView(R.layout.popup_exitapp);
		ExitAppDialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.rf_admin);
		ExitAppDialog.setTitle(getResources().getString(R.string.Popup_Exit_title));
		img_yes = (ImageView) ExitAppDialog.findViewById(R.id.img_yes);
		img_no = (ImageView) ExitAppDialog.findViewById(R.id.img_no);
		TextView txv_Dialogtext = (TextView) ExitAppDialog
				.findViewById(R.id.txv_dialog);
		txv_Dialogtext.setText(R.string.Popup_Exit);
		try {
			img_yes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// Intent intent = new Intent(Intent.ACTION_MAIN);
					// intent.addCategory(Intent.CATEGORY_HOME);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(intent);
					// android.os.Process.killProcess(android.os.Process.myPid());

					Intent homeIntent = new Intent(Intent.ACTION_MAIN);
					homeIntent.addCategory(Intent.CATEGORY_HOME);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(homeIntent);
					finish();
					System.exit(0);
					// System.runFinalizersOnExit(true);
					// System.exit(0);

				}
			});
			img_no.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ExitAppDialog.dismiss();
				}
			});

			// customHandler.post(updateTimerThread, 0);
			ExitAppDialog.show();
			ExitAppDialog.setCancelable(false);
			ExitAppDialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
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

			ExitFromAppDialog();
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

	public String gcmcall() {
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);
		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		String regId = GCMRegistrar.getRegistrationId(this);
		System.out.println("regId" + regId);

		// Toast.makeText(getApplicationContext(), "gcm reg id"+regId,
		// Toast.LENGTH_LONG).show();

		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			if(!regId.equals(""))
			{
				return GCMRegistrar.getRegistrationId(this);
			}
			else
			{
				GCMRegistrar.register(this, SENDER_ID);
				gcmcall();
			}
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				return GCMRegistrar.getRegistrationId(this);

			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
				// Once GCM returns a registration id, we need to register on our server
				// As the server might be down, we will retry it a couple
				// times.
				for (int i = 1; i <= MAX_ATTEMPTS; i++) {
					Log.d(TAG, "Attempt #" + i + " to register");
					try {
						displayMessage(context, context.getString(
								R.string.server_registering, i, MAX_ATTEMPTS));

						GCMRegistrar.setRegisteredOnServer(context, true);
						String message = context.getString(R.string.server_registered);
						CommonUtilities.displayMessage(context, message);
						return GCMRegistrar.getRegistrationId(this);
					} catch (Exception e) {
						// Here we are simplifying and retrying on any error; in a real
						// application, it should retry only on unrecoverable errors
						// (like HTTP error code 503).
						Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
						if (i == MAX_ATTEMPTS) {
							break;
						}
						try {
							Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
							Thread.sleep(backoff);
						} catch (InterruptedException e1) {
							// Activity finished before we complete - exit.
							Log.d(TAG, "Thread interrupted: abort remaining retries!");
							Thread.currentThread().interrupt();
							return null;
						}
						// increase backoff exponentially
						backoff *= 2;
					}
				}
				String message = context.getString(R.string.server_register_error,
						MAX_ATTEMPTS);
				CommonUtilities.displayMessage(context, message);
			}
		}

		return GCMRegistrar.getRegistrationId(this);

	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			// lblMessage.append(newMessage + "\n");
			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};
	// *************init call for reg and login new changes
	public class async_init extends AsyncTask<Void, Void, Void> {

		String jsonStr;
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			if (regi == true) {
				p1 = new ProgressDialog(Login_Activity.this);
				p1.setMessage(getResources().getString(R.string.str_please_wait));
				p1.setCancelable(false);
				p1.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {

			JSONObject obj_init = new JSONObject();

			try {

				// *************
				// for request
				System.out.println("1111urlinit" + Global_variable.rf_api_init);
//				myconnection con = new myconnection();

				// ****** response text
				try {
//					String str_response = "";
//
//					str_response = Global_variable.language(
//							Login_Activity.this, Global_variable.rf_api_init,
//							obj_init);
//					System.out.println("!!!!pankaj" + str_response);
					myconnection con = new myconnection();
					json = new JSONObject(
							con.connection(Login_Activity.this,
									Global_variable.rf_api_init,
									obj_init));
//					json = new JSONObject(str_response);
					System.out.println("1111jsonsplashscreen" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i(getString(R.string.str_Parse_Exception), e + "");

				} catch (NullPointerException np) {

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NullPointerException np) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			Global_variable.array_CountryArray = new JSONArray();
			Global_variable.array_RegionArray = new JSONArray();
			Global_variable.array_DistrictArray = new JSONArray();
			Global_variable.array_CitytArray = new JSONArray();
			Global_variable.sessid = null;
			if (regi == true) {
			if (p1.isShowing())
				p1.dismiss();
			}
			try {
				String json_success_str = json.getString("success");
				System.out.println("1111success" + json_success_str);
				if (json_success_str.equalsIgnoreCase("true")) {

					Global_variable.sessid = json.getString("sessid");
					System.out.println("1111sessid" + Global_variable.sessid);

					// /********************shaer prefrence
					// preferences = PreferenceManager
					// .getDefaultSharedPreferences(SplashScreenActivity.this);
					// SharedPreferences.Editor editor = preferences.edit();
					// editor.putString("sessid", Global_variable.sessid);
					// editor.apply();
					// /********************shaer prefrence
					JSONObject data = json.getJSONObject("data");
					System.out.println("1111data" + data);
					// country*region*city****district*****
					Global_variable.array_CountryArray = data
							.getJSONArray("country");
					System.out.println("1111country"
							+ Global_variable.array_CountryArray);
					Global_variable.array_RegionArray = data
							.getJSONArray("region");
					System.out.println("1111region"
							+ Global_variable.array_RegionArray);
					Global_variable.array_CitytArray = data
							.getJSONArray("city");
					System.out.println("1111city"
							+ Global_variable.array_CitytArray);
					Global_variable.array_DistrictArray = data
							.getJSONArray("district");
					System.out.println("1111districtarray"
							+ Global_variable.array_DistrictArray);
					// service*******************package*********
					Global_variable.array_Services = data
							.getJSONArray("services");
					System.out.println("1111servicesarray"
							+ Global_variable.array_Services);
					Global_variable.array_Package = data
							.getJSONArray("package");
					System.out.println("1111packagesarray"
							+ Global_variable.array_Package);

					Global_variable.array_restaurantcategory = data
							.getJSONArray("restaurantcategory");
					System.out.println("1111array_RestaurantCategory"
							+ Global_variable.array_restaurantcategory);

					Global_variable.array_payment_method = data
							.getJSONArray("payment_method");
					System.out.println("1111array_payment_method"
							+ Global_variable.array_payment_method);
					if (data.has("error_en")) {
						Global_variable.array_error_en = data
								.getJSONArray("error_en");
						System.out.println("1111array_error_en"
								+ Global_variable.array_error_en);
					}
					if (data.has("error_other")) {
						Global_variable.array_error_other = data
								.getJSONArray("error_other");
						System.out.println("array_error_other"
								+ Global_variable.array_payment_method);
					}
					if (regi == true) {
						Intent in = new Intent(Login_Activity.this,
								com.example.restaurantadmin.Registration_Tablayout_Activity.class);
						startActivity(in);
						regi=false;
					} else if (login == true) {
						Intent in = new Intent(Login_Activity.this,
								Home_Activity.class);
						startActivity(in);
						login=false;
					}
				} else {
					Toast.makeText(Login_Activity.this,
							getString(R.string.str_No_Data_Available),
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
