package com.example.restaurantadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.Login_Activity;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ForgetPasswordActivity extends Activity {

	public static EditText ed_email;
	public static Button btn_forgetpass;
	ConnectionDetector cd;
	public static ProgressDialog p;
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_forget_password);
		cd = new ConnectionDetector(getApplicationContext());
		initialiwidgets();
		setonclicklistner();
		
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

//				loadLocale();
//				LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
				// language*****************
	}
	
//	language***************
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

//	language***************
//	@Override
//	public void onResume() {
//		System.out.println("murtuza_nalawala");
//		super.onResume(); // Always call the superclass method first
//	}
	private void setonclicklistner() {
		// TODO Auto-generated method stub
		btn_forgetpass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cd.isConnectingToInternet()) {

					if (ed_email.getText().toString().length() != 0) {
						new asynch_forgetpassword().execute();
					} else {
						Toast.makeText(ForgetPasswordActivity.this,
								R.string.str_Enter_Email, Toast.LENGTH_LONG).show();
					}
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

	private void initialiwidgets() {
		// TODO Auto-generated method stub
		btn_forgetpass = (Button) findViewById(R.id.btn_forgetpass);
		ed_email = (EditText) findViewById(R.id.ed_email);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_password, menu);
		return true;
	}

	public class asynch_forgetpassword extends AsyncTask<Void, Void, Void> {
		JSONObject json;
		ProgressDialog p;
		JSONObject obj_req;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(ForgetPasswordActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

//			JSONObject data = new JSONObject();

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("email", ed_email.getText().toString());

				obj_parent.put("Forget_Password", obj_child);
				obj_parent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("Activity_forgetpass" + obj_parent);
				

				try {

					// *************
					// for request
					myconnection con = new myconnection();
					String str_response = con.connection(ForgetPasswordActivity.this,
							Global_variable.ForgetPassword, obj_parent);

					json = new JSONObject(str_response);

				} catch (NullPointerException np) {

				}

				return null;

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			if (p.isShowing()) {
				p.dismiss();
				System.out.println("Activity admin forgetpass" + json);
			}
			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");

					System.out.println("11111dataforget" + Data);

					Toast.makeText(getApplicationContext(),
							json.getJSONObject("data").getString("message"),
							Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),
							Login_Activity.class);
					startActivity(i);

				} else {
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("email")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("email").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

						if (Errors.has("sessid")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("sessid").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

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
}
