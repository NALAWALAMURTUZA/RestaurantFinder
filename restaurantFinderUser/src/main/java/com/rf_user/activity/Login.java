package com.rf_user.activity;

//import com.example.product.product_search;

import java.io.IOException;
import java.util.HashMap;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
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

public class Login extends Activity {

	TextView Login, Register, Forget_Password, admin_rest_registration_link;
	EditText username, password;
	ProgressDialog progressDialog;

	HashMap<String, String> Hasmap_Restaurant_Categories = new HashMap<String, String>();

	String TAG_contact = "data";
	String TAG_SUCCESS = "success";
	String flag;
	/*** Network Connection Instance **/
	ConnectionDetector cd;

	/* Language conversion */
	private Locale myLocale;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.login);
		/* create Object* */
		try {
			cd = new ConnectionDetector(getApplicationContext());

			// init() ;
			initializeWidget();

			setlistener();

			loadLocale();

			// TODO Auto-generated method stub
		} catch (NullPointerException n) {
			n.printStackTrace();
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

		try {
			if (Global_variable.activity == "cart") {
				Intent i = new Intent(Login.this, Cart.class);
				startActivity(i);

			} else if (Global_variable.activity == "Categories") {
				Intent i = new Intent(Login.this, Categories.class);
				startActivity(i);
			} else {
				Intent i = new Intent(Login.this, FindRestaurant.class);

				startActivity(i);

			}
		} catch (NullPointerException n) {
			n.printStackTrace();
		}

	}

	public void initializeWidget() {
		ScrollView scrollable_contents = (ScrollView) findViewById(R.id.login_scrollview);
		getLayoutInflater().inflate(R.layout.loginscrollview_contents,
				scrollable_contents);
		Login = (TextView) findViewById(R.id.Login_textview);
		Forget_Password = (TextView) findViewById(R.id.forget_password_textview);
		admin_rest_registration_link = (TextView) findViewById(R.id.admin_rest_registration_link);
		Register = (TextView) findViewById(R.id.register_textview);
		username = (EditText) findViewById(R.id.username_edittext);
		password = (EditText) findViewById(R.id.password_edittext);
		admin_rest_registration_link
				.setText(getResources().getString(R.string.admin_rest_registration_link));
//		admin_rest_registration_link
//		.setText(getResources().getString(R.string.admin_rest_registration_link));
	}

	public class async_init extends AsyncTask<Void, Void, Void> {

		String jsonStr;
		JSONObject json;
		ProgressDialog p;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(Login.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			JSONObject obj_init = new JSONObject();

			try {

				// *************
				// for request
				System.out.println("1111urlinit"
						+ Global_variable.rf_api_Url_lang
						+ Global_variable.rf_api_init);
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_api_Url_lang
								+ Global_variable.rf_api_init);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(obj_init.toString(), "UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("1111final_response" + responseText);

					json = new JSONObject(responseText);
					System.out.println("1111jsonsplashscreen" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				} catch (NullPointerException np) {

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			if (p.isShowing())
				p.dismiss();
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

					Intent in = new Intent(getApplicationContext(),
							RegistrationTablayout.class);
					startActivity(in);

				} else {
					Toast.makeText(Login.this, R.string.str_No_Data_Available,
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

	private void setlistener() {

		admin_rest_registration_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (cd.isConnectingToInternet()) {
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

				// Intent intent = new Intent(Intent.ACTION_VIEW);
				// intent.setData(Uri.parse("https://play.google.com/store/apps/"));
				// startActivity(intent);

				// intent.setData(Uri.parse("market://details?id="+packagename));
				// Intent intent =
				// IntentUtils.openPlayStore(getApplicationContext());
				// startActivity(intent);

			}
		});

		Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/** check Internet Connectivity */
				try {
					if (cd.isConnectingToInternet()) {

						new GetUserDetail().execute();

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
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

		});

		Register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					runOnUiThread(new Runnable() {
						public void run() {
							if (cd.isConnectingToInternet()) {

								Intent i = new Intent(Login.this,
										Register_Activity.class);
								startActivity(i);

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
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
		Forget_Password.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (cd.isConnectingToInternet()) {

						Intent i = new Intent(Login.this, ForgetPassword.class);
						startActivity(i);

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
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// login class *****************

	class GetUserDetail extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Login.this);
			progressDialog.setCancelable(false);
			System.out.println("-11111111");
			progressDialog.show();
		}

		/**
		 * Getting user details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread

			String text = null;

			// Check for success tag
			try {
				JSONObject LoginForm = new JSONObject();

				try {
					LoginForm.put("username", username.getText().toString());
					System.out.println("username_login" + LoginForm);
					LoginForm.put("password", password.getText().toString());
					System.out.println("password_login" + LoginForm);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject datastreams = new JSONObject();
				datastreams.put("LoginForm", LoginForm);
				System.out.println("login_form" + datastreams);
				datastreams.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				System.out.println("session_id" + datastreams);
				// *************
				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_login_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(datastreams.toString(),
						"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("last_text" + responseText);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				} catch (NullPointerException np) {

				}
				json = new JSONObject(responseText);
				System.out.println("last_json" + json);

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

			//
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			try {
				// json success tag
				String success1;
				System.out.println("json_json_json" + json);
				if (json != null) {
					success1 = json.getString(TAG_SUCCESS);

					System.out.println("tag" + success1);
					JSONObject data = json.getJSONObject("data");
					String Data_Success = data.getString(TAG_SUCCESS);

					System.out.println("Data tag" + Data_Success);
					// ******** data succsess
					if (Data_Success.equalsIgnoreCase("true")) {
						Global_variable.login_user_id = data
								.getString("user_id");
						System.out.println("login.user_id"
								+ Global_variable.login_user_id);

						/* Storing user_id in SharedPref */
						SharedPreference.setuser_id(getApplicationContext(),
								data.getString("user_id"));

						// String login_message=data.getString("message");
						//
						// Toast.makeText(getApplicationContext(),
						// login_message, Toast.LENGTH_SHORT).show();
						Login.setClickable(false);

						if (Global_variable.activity.equals("cart")) {
							progressDialog.dismiss();
							Intent ii = new Intent(getApplicationContext(),
									Checkout_Tablayout.class);
							startActivity(ii);

						} else if (Global_variable.activity
								.equals("Find_Restaurant")) {
							progressDialog.dismiss();
							Intent ii = new Intent(getApplicationContext(),
									FindRestaurant.class);
							startActivity(ii);
						} else if (Global_variable.activity
								.equals("Categories")) {
							progressDialog.dismiss();
							Intent ii = new Intent(getApplicationContext(),
									GrabTableActivity.class);
							startActivity(ii);
						}

					}
					// **** invalid username password
					else if (Data_Success.equalsIgnoreCase("false")) {
						progressDialog.dismiss();
						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);
						if (Data_Error.has("username")) {
							JSONArray Array_email = Data_Error
									.getJSONArray("username");
							System.out.println("Array_email" + Array_email);
							String Str_username = Array_email.getString(0);
							System.out.println("Str_email" + Str_username);
							if (Str_username != null) {
								Toast.makeText(getApplicationContext(),
										Str_username, Toast.LENGTH_SHORT)
										.show();
							}

						} else if (Data_Error.has("password")) {
							JSONArray Array_password = Data_Error
									.getJSONArray("password");
							System.out.println("Array fist" + Array_password);
							String Str_password = Array_password.getString(0);
							System.out.println("Str_first_name" + Str_password);
							if (Str_password != null) {
								Toast.makeText(getApplicationContext(),
										Str_password, Toast.LENGTH_SHORT)
										.show();
							}
						} else if (Data_Error.has("password")
								&& Data_Error.has("username")) {
							JSONArray Array_email = Data_Error
									.getJSONArray("username");
							System.out.println("Array_email" + Array_email);
							JSONArray Array_first_name = Data_Error
									.getJSONArray("password");
							System.out.println("Array fist" + Array_first_name);
							String Str_username = Array_email.getString(0);
							System.out.println("Str_email" + Str_username);
							String Str_password = Array_first_name.getString(0);
							System.out.println("Str_first_name" + Str_password);
							if ((Str_username.equals(null))
									&& (Str_password.equals(null))) {
								Toast.makeText(getApplicationContext(),
										Str_password + Str_username,
										Toast.LENGTH_SHORT).show();
							}
						}
						// Toast.makeText(getApplicationContext(),
						// "Check ID and Password", Toast.LENGTH_LONG).show();

					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException np) {

			}

		}
		// ***** login class coplete

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
