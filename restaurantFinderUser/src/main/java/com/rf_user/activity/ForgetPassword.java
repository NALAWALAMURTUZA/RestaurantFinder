package com.rf_user.activity;

import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

@SuppressLint("CutPasteId")
public class ForgetPassword extends Activity {

	TextView Reset_Password;
	EditText username, email;

	String TAG_contact = "data";
	String TAG_SUCCESS = "success";
	String flag;

	/*** Network Connection Instance **/
	ConnectionDetector cd;
	
	/* Language conversion */
	private Locale myLocale;


	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.forgetpassword);

		try{
			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			initializeWidget();
			// Global_variable.activity="ForgetPassword";
			loadLocale();	
		}
		catch(NullPointerException e)
		{
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

		Intent i = new Intent(ForgetPassword.this, Login.class);
		startActivity(i);

	}

	public void initializeWidget() {
		ScrollView scrollable_contents = (ScrollView) findViewById(R.id.forget_scrollview);
		getLayoutInflater().inflate(
				R.layout.forgetpassword_scrollview_contents,
				scrollable_contents);

		Reset_Password = (TextView) findViewById(R.id.resetpassword_textview);
		username = (EditText) findViewById(R.id.forget_username_edittext);
		email = (EditText) findViewById(R.id.forget_email_edittext);
		setlistener();
	}

	private void setlistener() {
		Reset_Password.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// if (username.getText().toString().equals("") &&
					// email.getText().toString().equals("")) {
					// Toast.makeText(getApplicationContext(),
					// "Please Enter Email Or Username",
					// Toast.LENGTH_SHORT).show();
					// }
					//
					// if (username.getText().toString()!=null ||
					// email.getText().toString()!=null)
					// {
					runOnUiThread(new Runnable() {
						public void run() {

							System.out.println("||");
							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {

								new forgetpassword().execute();

							} else {

								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// TODO Auto-generated method stub
										Toast.makeText(
												getApplicationContext(),
												R.string.No_Internet_Connection,
												Toast.LENGTH_SHORT).show();

									}

								});
							}

						}
					});

					// }
					//
					// else if(username.getText().toString()!=null &&
					// email.getText().toString()!=null)
					// {
					// runOnUiThread(new Runnable()
					// {
					// public void run() {
					//
					// System.out.println("&&");
					// new forgetpassword().execute();
					//
					// }
					// });
					// }
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

		});

	}

	class forgetpassword extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/**
		 * Getting user details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				String text = null;

				public void run() {

					// Check for success tag
					int success;
					try {
						JSONObject Forget_Password_Obj = new JSONObject();

						try {
							Forget_Password_Obj.put("username", username
									.getText().toString());
							System.out
									.println("username" + Forget_Password_Obj);
							Forget_Password_Obj.put("email", email.getText()
									.toString());
							System.out.println("email" + Forget_Password_Obj);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JSONObject forget_datastreams = new JSONObject();
						forget_datastreams.put("ForgetPasswordForm",
								Forget_Password_Obj);
						System.out.println("forget_form" + forget_datastreams);
						forget_datastreams
								.put("sessid", SharedPreference.getsessid(getApplicationContext()));
						System.out.println("session_id" + forget_datastreams);
						// *************
						// for request
						DefaultHttpClient httpclient = new DefaultHttpClient();
						HttpPost httppostreq = new HttpPost(
								Global_variable.rf_lang_Url
										+ Global_variable.rf_forgetpassword_api_path);
						System.out.println("forget_post_url" + httppostreq);
						StringEntity se = new StringEntity(
								forget_datastreams.toString(), "UTF-8");
						System.out.println("forget_url_request" + se);
						se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
								"application/json"));
						se.setContentType("application/json;charset=UTF-8");
						se.setContentEncoding(new BasicHeader(
								HTTP.CONTENT_TYPE,
								"application/json;charset=UTF-8"));
						httppostreq.setEntity(se);

						HttpResponse httpresponse = httpclient
								.execute(httppostreq);

						String responseText = null;

						// ****** response text
						try {
							responseText = EntityUtils.toString(httpresponse
									.getEntity(), "UTF-8");
							responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
							System.out.println("forget_last_text"
									+ responseText);
						} catch (ParseException e) {
							e.printStackTrace();

							Log.i("Parse Exception", e + "");

						} catch (NullPointerException np) {

						}
						JSONObject json = new JSONObject(responseText);
						System.out.println("forget_last_json" + json);

						// json success tag
						String success1 = json.getString(TAG_SUCCESS);
						System.out.println("tag" + success1);
						JSONObject data = json.getJSONObject("data");
						String Data_Success = data.getString("success");
						System.out.println("Data tag" + Data_Success);
						// ******** data succsess
						if (Data_Success.equalsIgnoreCase("true")) {
							String forget_message = data.getString("message");

							Toast.makeText(getApplicationContext(),
									forget_message, Toast.LENGTH_LONG).show();
							Intent ii = new Intent(getApplicationContext(),
									Login.class);
							startActivity(ii);

						}
						// **** invalid username password
						else if (Data_Success.equalsIgnoreCase("false")) {
							JSONObject Data_Error = data
									.getJSONObject("errors");
							System.out.println("Data_Error" + Data_Error);

							if (Data_Error.has("username")) {
								JSONArray Array_email = Data_Error
										.getJSONArray("username");
								System.out.println("Array_email" + Array_email);
								String Str_username = Array_email.getString(0);
								System.out.println("Str_email" + Str_username);
								if (Str_username != null) {
									Toast.makeText(getApplicationContext(),
											Str_username, Toast.LENGTH_LONG)
											.show();
								}

							} else if (Data_Error.has("email")) {
								JSONArray Array_first_name = Data_Error
										.getJSONArray("email");
								System.out.println("Array fist"
										+ Array_first_name);
								String Str_email = Array_first_name
										.getString(0);
								System.out
										.println("Str_first_name" + Str_email);
								if (Str_email != null) {
									Toast.makeText(getApplicationContext(),
											Str_email, Toast.LENGTH_LONG)
											.show();
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NullPointerException np) {

					}
				}

			});
			//
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			try {
				if (flag == "" + 1) {

					Intent ii = new Intent(getApplicationContext(), Login.class);

					startActivity(ii);
					// finish();
				} else {
				}

			} catch (NullPointerException n) {
				n.printStackTrace();
			}
			// ***** login class coplete
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
