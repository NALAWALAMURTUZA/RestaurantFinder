package com.rf_user.activity;



import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class ChangePassword extends Activity {
	
	private Button btn_cancel;
	ProgressDialog p;
	
	EditText rf_profile_ed_oldpassword,	rf_profile_ed_newpassword, rf_profile_ed_retypepassword;
	private Button btn_ok;
	HttpConnection http = new HttpConnection();

	/* Internet connection */
	ConnectionDetector cd;
	
	/* Language conversion */
	private Locale myLocale;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_change_password);
		
		try{
			initializeWidget();
			
			/* create Object of internet connection* */
			cd = new ConnectionDetector(getApplicationContext());
			setonclicklistener();
//			loadLocale();
		}
		catch(NullPointerException e)
		{
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

	
	private void initializeWidget() {
		//img_menu = (ImageView) findViewById(R.id.img_menu);
		
		
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		rf_profile_ed_oldpassword = (EditText) findViewById(R.id.rf_profile_ed_oldpassword);
		rf_profile_ed_newpassword = (EditText) findViewById(R.id.rf_profile_ed_newpassword);
		rf_profile_ed_retypepassword = (EditText) findViewById(R.id.rf_profile_ed_retypepassword);

	}
	
	private void setonclicklistener() {
		// TODO Auto-generated method stub

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							
							 if(rf_profile_ed_oldpassword.getText().toString().equalsIgnoreCase(""))
							 {
								 System.out.println("!!!!pankaj"+getString(R.string.str_current_pwd));
								 rf_profile_ed_oldpassword.setError(getString(R.string.str_current_pwd));
								 
								 rf_profile_ed_oldpassword.setError(Html.fromHtml("<font color='#ff0000'>"+getString(R.string.str_current_pwd)+"</font> "));
								 rf_profile_ed_oldpassword.requestFocus();

							 }
							
							 else if(rf_profile_ed_newpassword.getText().toString().equalsIgnoreCase(""))
							 {
								 rf_profile_ed_newpassword.setError(Html.fromHtml("<font color='#ff0000'>"+getString(R.string.str_not_blank_pwd)+"</font> "));
								 rf_profile_ed_newpassword.requestFocus();
							 }	
							
							 else if(rf_profile_ed_retypepassword.getText().toString().equalsIgnoreCase(""))
							 {
								 rf_profile_ed_retypepassword.setError(Html.fromHtml("<font color='#ff0000'>"+getString(R.string.str_confirm_pwd)+"</font> "));								 
								 rf_profile_ed_retypepassword.requestFocus();
							 }	
							 
							 else if(rf_profile_ed_newpassword.getText().toString().equalsIgnoreCase(rf_profile_ed_retypepassword.getText().toString()))
							 {
//								 Toast.makeText(getApplicationContext(), "clicked",	Toast.LENGTH_LONG	).show();
								 new update_user_password().execute();
							 }	
								 else
								 {
									 Toast.makeText(getApplicationContext(), getString(R.string.str_psw_not_match),	Toast.LENGTH_LONG	).show();
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
		});
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							
							if(rf_profile_ed_oldpassword.getText().toString().equalsIgnoreCase("")&&rf_profile_ed_newpassword.getText().toString().equalsIgnoreCase("")&&rf_profile_ed_retypepassword.getText().toString().equalsIgnoreCase(""))
							{
								Intent i = new Intent(getApplicationContext(),
										SettingsScreen.class);
								
								startActivity(i);
								
							}
							else
							{
									 rf_profile_ed_oldpassword.setText("");
									 rf_profile_ed_newpassword.setText("");
									 rf_profile_ed_retypepassword.setText("");
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
		});
		
		
	}
	
	public class update_user_password extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(ChangePassword.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("hi"
					+ rf_profile_ed_newpassword.getText().toString());
			JSONObject obj_parent_psw = new JSONObject();
			JSONObject obj_child_rpsw = new JSONObject();
			try {
				obj_child_rpsw.put("old_password", rf_profile_ed_oldpassword.getText().toString());
				obj_child_rpsw.put("new_password", rf_profile_ed_newpassword.getText().toString());
				obj_child_rpsw.put("user_id", SharedPreference.getuser_id(getApplicationContext()));
				
				obj_parent_psw.put("sessid", SharedPreference.getsessid(getApplicationContext()));
				obj_parent_psw.put("User_Password", obj_child_rpsw);
				System.out.println("Activity user change password" + obj_parent_psw);

				try {

					// *************
					// for request
					String responseText = http.connection(ChangePassword.this,
							Global_variable.rf_update_user_password, obj_parent_psw);

					try {

						System.out.println("after_connection.." + responseText);

						json = new JSONObject(responseText);
						System.out.println("responseText" + json);
					} catch (NullPointerException ex) {
						ex.printStackTrace();
					}

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
				System.out.println("Activity User Change Password" + json);
			}
			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					
					System.out.println("11111datalogin" + Data);

					Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),
							SettingsScreen.class);
					startActivity(i);

				} else {
					JSONObject Data = json.getJSONObject("data");
					JSONObject Errors = Data.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("old_password")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("old_password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("new_password")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("new_password")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						
						if (Errors.has("user_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("user_id")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						
						if (Errors.has("sessid")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("sessid")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

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
