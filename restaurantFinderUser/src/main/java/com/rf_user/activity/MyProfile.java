package com.rf_user.activity;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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
import org.json.JSONTokener;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_Bean.DatabaseData;
import com.rf_user.sqlite_Bean.DateDetail;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class MyProfile extends Activity {

	String TAG_contact = "data";
	String TAG_SUCCESS = "success";
	public ProgressDialog pDialog;
	// String flag;
	boolean dialogShown = false, profile_flag = true;

	HashMap<String, String> Hashmap_Region = new HashMap<String, String>();
	HashMap<String, String> Hashmap_City = new HashMap<String, String>();
	HashMap<String, String> Hashmap_District = new HashMap<String, String>();

	// ArrayList<String> region_spinner,city_spinner,_district_spinner;
	ArrayList<String> arraylist = new ArrayList<String>();

	String[] Region_Array;
	String[] City_Array;
	String[] District_Array;

	int selectedcity = 0;
	int selecteddistrict = 0;
	int selectedregion = 0;

	String str_select_region, str_select_city, str_select_district;
	Button Update;
	public String Region_id, city_id, District_id;
	TextView select_region, select_city, select_district;
	EditText firstname_edittext, lastname_edittext, Username_edittext,
			emial_edittext, Password_edittext, Street_edittext,
			Repeat_password_edittext, House_number_edittext,
			Mobile_number_edittext;

	ProgressDialog progressDialog;
	/** sqlite **/

	public static Boolean CheckNetworkandSQlitecall = false;
	List<DateDetail> Date_Detail;
	public static String log1 = "", datetime = "", DateDetail = "",
			data_sql = "";
	String log;
	CheckBox check_box;
	ProgressDialog profiledialog;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	HttpConnection http = new HttpConnection();
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_my_profile);

		/* create Object* */
		try {
			cd = new ConnectionDetector(getApplicationContext());

			initializeWidgets();

			//Global_variable.post_review_activity="";

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new getUserAccountInfo().execute();

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

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new async_region().execute();

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

			setlistener();
			select_city.setClickable(false);
			select_district.setClickable(false);
			select_region.setClickable(false);
			
			loadLocale();
			
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

	private void DBCheckCityAvaibility(String str_city_id) {
		// TODO Auto-generated method stub
		// DBAdapter.init(this);
		// /DBAdapter.deleteall();
		// Reading all contacts
		System.out.println("String_str_city_id" + str_city_id);
		Log.d("Reading: ", "Reading all contacts..");
		List<DatabaseData> data_City = DBAdapter.getAllData();
		for (DatabaseData dt : data_City) {
			System.out.println("district_cache_api_for" + log1 + "dat_api"
					+ dt.get_Api());
			if (dt.get_Api().equals(str_city_id)) {
				log1 = dt.get_Api();
				data_sql = dt.get_Data();
				datetime = dt.get_Timestamp();

				// Writing Contacts to log
				System.out.println("district_cache_api" + log1);
				Log.d("Api: ", log1);
				Log.d("Data: ", data_sql);
				Log.d("DateTime: ", datetime);
			}
		}

		try {

			String d = log1;
			System.out.println("District_In_try" + log1 + "dddd" + d);
			if (log1.equals("")) {
				System.out.println("District_In_try_call_network" + log1
						+ "haha");
				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					new async_district_Spinner().execute();

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

			} else {
				log1 = "";
				System.out.println("District_Data Available in sqlite"
						+ data_City);
				// DBAdapter.CheckTimeExpire();
				// DBAdapter.deleteall();
				new async_district_Spinner().onPostExecute(null);
				Date_Detail = DBAdapter.CheckTimeExpire();

				if (Date_Detail.size() != 0) {
					DBAdapter.CheckFortyeighthouser(Date_Detail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_BACK:
//			onBackPressed();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	public void onBackPressed() {
//
//		Intent i = new Intent(MyProfile.this, SettingsScreen.class);
//		startActivity(i);
//
//	}

	// region spinner
	private void Region_spinner() {
		try {
			System.out.println("!!!!Profile_Region_Id in spinner method.."
					+ Region_id);
			// System.out.println("!!!!Profile..."+Hashmap_Region.get());
			System.out.println("!!!!Hashmap_Region in spinner method..."
					+ Hashmap_Region.toString());
			System.out.println("Region_Array..." + Region_Array);

			String Region_name = select_region.getText().toString();

			int index = -1;
			for (int i = 0; i < Region_Array.length; i++) {
				if (Region_Array[i].equals(Region_name)) {
					index = i;
					selectedregion = index;
					break;
				}
			}

			System.out.println("!!!!!!!!!!!!" + index);

			Builder builder = new AlertDialog.Builder(MyProfile.this);
			builder.setTitle(R.string.Select_Region);
			builder.setSingleChoiceItems(Region_Array, selectedregion,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							// District_Array=null;
							selectedregion = which;

							System.out.println("!!!!selectedregion..."
									+ selectedregion);

							str_select_region = Region_Array[which];
							System.out.println("str_select_region"
									+ str_select_region);
							Region_id = (String) Hashmap_Region
									.get(str_select_region);
							System.out.println("Region_id_value" + Region_id);

							// builder.setPositiveButton(R.string.select,
							// new DialogInterface.OnClickListener() {
							// @Override
							// public void onClick(DialogInterface dialog, int
							// which) {

							try {
								if (select_region.getText().toString() != str_select_region) {
									Hashmap_City.clear();
									select_region.setText(str_select_region);
									dialogShown = false;
									dialog.dismiss();
									System.out.println("textview_set"
											+ str_select_region);
									City_Array = new String[0];
									District_Array = new String[0];
									/** check Internet Connectivity */
									if (cd.isConnectingToInternet()) {
										System.out.println("shikha....");
										new async_city_Spinner().execute();

									} else {

										runOnUiThread(new Runnable() {

											@Override
											public void run() {

												// TODO Auto-generated method
												// stub
												Toast.makeText(
														getApplicationContext(),
														R.string.No_Internet_Connection,
														Toast.LENGTH_SHORT)
														.show();

											}

										});
									}

								} else {
									dialogShown = false;
									dialog.dismiss();
								}

							} catch (Exception e) {

							}
							// if(Region_id!=null)
							// {

							// }
							// }
							// });

						}
					});
			if (dialogShown) {
				return;
			} else {
				dialogShown = true;
				AlertDialog alert = builder.create();
				alert.setCanceledOnTouchOutside(false);
				alert.setCancelable(false);
				alert.show();

			}
			// alert.setCanceledOnTouchOutside(false);
			// alert.setCancelable(false);
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	// city spinner
	private void city_spinner() {

		System.out.println("!!!!Profile_City_id in spinner method.." + city_id);
		// System.out.println("!!!!Profile..."+Hashmap_Region.get());
		System.out.println("!!!!Hashmap_City in spinner method..."
				+ Hashmap_City.toString());

		System.out.println("City_Array.." + City_Array);

		String City_name = select_city.getText().toString();

		int index = -1;
		for (int i = 0; i < City_Array.length; i++) {
			if (City_Array[i].equals(City_name)) {
				index = i;
				selectedcity = index;
				break;
			}
		}

		System.out.println("!!!!!!!!!!!!" + index);
		str_select_city = null;
		city_id = null;
		Builder builder = new AlertDialog.Builder(MyProfile.this);
		builder.setTitle(R.string.Select_City);
		builder.setSingleChoiceItems(City_Array, selectedcity,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// District_Array=null;
						selectedcity = which;
						str_select_city = City_Array[which];
						System.out.println("Value of City name"
								+ str_select_city);
						city_id = (String) Hashmap_City.get(str_select_city);
						System.out.println("City_id_value" + city_id);

						try {

							if (select_city.getText().toString() != str_select_city) {
								select_city.setText(str_select_city);
								System.out.println("textview_cityset"
										+ str_select_city);
								dialogShown = false;
								dialog.dismiss();
								DBCheckCityAvaibility(city_id);
								// new async_district_Spinner().execute();

							} else {
								dialogShown = false;
								dialog.dismiss();

							}

						} catch (Exception e) {

						}
						// if(city_id!=null)
						// {

						// }
					}
				});
		if (dialogShown) {
			return;
		} else {
			dialogShown = true;
			AlertDialog alert = builder.create();
			alert.setCanceledOnTouchOutside(false);
			alert.setCancelable(false);
			alert.show();
		}

	}

	// district spinner
	private void district_spinner() {

		System.out.println("!!!!Profile_District_id in spinner method.."
				+ District_id);
		// System.out.println("!!!!Profile..."+Hashmap_Region.get());
		System.out.println("!!!!Hashmap_District in spinner method..."
				+ Hashmap_District.toString());

		String district_name = select_district.getText().toString();

		int index = -1;
		for (int i = 0; i < District_Array.length; i++) {
			if (District_Array[i].equals(district_name)) {
				index = i;
				selecteddistrict = index;
				break;
			}
		}

		System.out.println("!!!!!!!!!!!!" + index);

		str_select_district = null;
		Builder builder = new AlertDialog.Builder(MyProfile.this);
		builder.setTitle(R.string.Select_District);
		builder.setSingleChoiceItems(District_Array, selecteddistrict,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						selecteddistrict = which;
						str_select_district = District_Array[which];

						try {

							if (select_district.getText().toString() != str_select_district) {
								select_district.setText(str_select_district);
								System.out.println("textview_districtset"
										+ str_select_district);
								dialogShown = false;
								dialog.dismiss();
							} else {
								dialogShown = false;
								dialog.dismiss();
							}

						} catch (Exception e) {

						}

					}
				});
		if (dialogShown) {
			return;
		} else {
			dialogShown = true;
			AlertDialog alert = builder.create();
			alert.setCanceledOnTouchOutside(false);
			alert.setCancelable(false);
			alert.show();
		}

	}

	private void initializeWidgets() {
		// ****find view by id

		// text view
		select_region = (TextView) findViewById(R.id.select_region);
		select_city = (TextView) findViewById(R.id.select_city);
		select_district = (TextView) findViewById(R.id.select_district);
		Update = (Button) findViewById(R.id.update_textview);
		// editext
		firstname_edittext = (EditText) findViewById(R.id.firstname_edittext);
		lastname_edittext = (EditText) findViewById(R.id.lastname_edittext);
		Username_edittext = (EditText) findViewById(R.id.Username_edittext);
		Username_edittext.setEnabled(false);
		emial_edittext = (EditText) findViewById(R.id.emial_edittext);
		Password_edittext = (EditText) findViewById(R.id.Password_edittext);
		Repeat_password_edittext = (EditText) findViewById(R.id.Repeat_password_edittext);
		House_number_edittext = (EditText) findViewById(R.id.House_no_edittext);
		Street_edittext = (EditText) findViewById(R.id.Street_edittext);
		Mobile_number_edittext = (EditText) findViewById(R.id.mobile_number_edittext);

		// checkbox
		check_box = (CheckBox) findViewById(R.id.check_box);

		select_city.setClickable(false);
		select_district.setClickable(false);
		select_region.setClickable(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// *** set listner
	private void setlistener() {
		Update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						 new UpdateUserProfile().execute();

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
		select_region.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					if (Region_Array.length != 0) {
						Region_spinner();
					} else {
						System.out.println("in_region_else");
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		select_city.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// city_spinner_class();
				try {
					System.out.println("get_region" + Region_id);
					if (Region_id != null) {
						if (City_Array.length != 0) {
							city_spinner();
						}
					}

					else {
						System.out.println("city_else");
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		select_district.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					if (city_id != null) {
						System.out.println("district array" + District_Array);
						if (District_Array.length != 0) {
							district_spinner();
						} else {
							System.out.println("district_else");
							// select_district.setText(R.string.No_District);
						}

					} else {
						System.out.println("district_else");
						// select_district.setText(R.string.No_District);
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

	}

	/* Fetch user detail from db */

	class getUserAccountInfo extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MyProfile.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();

		}
		
		/**
		 * Getting user details in background thread
		 * */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject ProfileForm = new JSONObject();

						ProfileForm.put("user_id",
								SharedPreference
								.getuser_id(getApplicationContext()));
						System.out.println("user_id" + ProfileForm);

						ProfileForm.put("sessid", SharedPreference.getsessid(getApplicationContext()));
						System.out.println("session_id" + ProfileForm);
						// *************

						String responseText = http
								.connection(MyProfile.this,
										 Global_variable.rf_getUserAccountInfo_api_path,
										ProfileForm);

						try {

							System.out.println("after_connection.."
									+ responseText);

							json = new JSONObject(responseText);
							System.out.println("responseText" + json);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			});
			
			return null;
		}

		
		

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(Void result) {

			
			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONObject data = json.getJSONObject("data");
				// String Data_Success = data.getString(TAG_SUCCESS);
				// System.out.println("Data tag" + Data_Success);
				// ******** data succsess

				if (success1.equals("true")) {
					if (data.length() != 0) {

						String First_name = data.getString("first_name");
						String Last_name = data.getString("last_name");
						String Mobile_no = data.getString("mobile_number");
						String House_Number = data.getString("house_number");
						String Street = data.getString("street");
						city_id = data.getString("city_id");
						//District_id = data.getString("district_id");
						Region_id = data.getString("region_id");
						String Username = data.getString("username");
						String Password = data.getString("password");
						String Email = data.getString("email");

						if (First_name.equalsIgnoreCase("")
								|| First_name.equalsIgnoreCase(null)
								|| First_name.equalsIgnoreCase("null")) {
							firstname_edittext.setText("");
						} else {
							firstname_edittext.setText(First_name);
						}

						if (Last_name.equalsIgnoreCase("")
								|| Last_name.equalsIgnoreCase(null)
								|| Last_name.equalsIgnoreCase("null")) {
							lastname_edittext.setText("");
						} else {
							lastname_edittext.setText(Last_name);
						}

						if (Mobile_no.equalsIgnoreCase("")
								|| Mobile_no.equalsIgnoreCase(null)
								|| Mobile_no.equalsIgnoreCase("null")) {
							Mobile_number_edittext.setText("");
						} else {
							Mobile_number_edittext.setText(Mobile_no);
						}

						if (House_Number.equalsIgnoreCase("")
								|| House_Number.equalsIgnoreCase(null)
								|| House_Number.equalsIgnoreCase("null")) {
							House_number_edittext.setText("");
						} else {
							House_number_edittext.setText(House_Number);
						}

						if (Street.equalsIgnoreCase("")
								|| Street.equalsIgnoreCase(null)
								|| Street.equalsIgnoreCase("null")) {
							Street_edittext.setText("");
						} else {
							Street_edittext.setText(Street);
						}

						// if
						// (City_id.equalsIgnoreCase("")||City_id.equalsIgnoreCase(null)||City_id.equalsIgnoreCase("null"))
						// {
						// firstname_edittext.setText("");
						// } else {
						// firstname_edittext.setText(First_name);
						// }
						//
						// if
						// (First_name.equalsIgnoreCase("")||First_name.equalsIgnoreCase(null)||First_name.equalsIgnoreCase("null"))
						// {
						// firstname_edittext.setText("");
						// } else {
						// firstname_edittext.setText(First_name);
						// }
						//
						if (Username.equalsIgnoreCase("")
								|| Username.equalsIgnoreCase(null)
								|| Username.equalsIgnoreCase("null")) {
							Username_edittext.setText("");
						} else {
							Username_edittext.setText(Username);
						}
						
						if (Password.equalsIgnoreCase("")
								|| Password.equalsIgnoreCase(null)
								|| Password.equalsIgnoreCase("null")) {
							Password_edittext.setText("");
						} else {
							Password_edittext.setText(Password);
						}


						if (Email.equalsIgnoreCase("")
								|| Email.equalsIgnoreCase(null)
								|| Email.equalsIgnoreCase("null")) {
							emial_edittext.setText("");
						} else {
							emial_edittext.setText(Email);
						}

						// new async_city_Spinner().execute();
						// new async_district_Spinner().execute();

//						Toast.makeText(getApplicationContext(),
//								"Fetch Detail Successful", Toast.LENGTH_LONG)
//								.show();
					}

				}

				// **** invalid output
				else {
					if (success1.equalsIgnoreCase("false")) {
						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("user_id")) {
							JSONArray Array_user_id = Data_Error
									.getJSONArray("user_id");
							System.out.println("Array_email" + Array_user_id);
							String Str_user_id = Array_user_id.getString(0);
							System.out.println("Str_email" + Str_user_id);
							if (Str_user_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_user_id, Toast.LENGTH_LONG).show();
							}
						}
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(dialog.isShowing())
			{
			dialog.dismiss();
			}

		}

		

	}

	/*** My Profile class *****************/

	class UpdateUserProfile extends AsyncTask<String, String, String> {

		JSONObject json;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			profiledialog = new ProgressDialog(MyProfile.this);
			profiledialog.setIndeterminate(false);
			profiledialog.setCancelable(true);
			profiledialog.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject ProfileForm = new JSONObject();

						try {
							ProfileForm.put("first_name", firstname_edittext
									.getText().toString());
							System.out.println("firstname_edittext"
									+ ProfileForm);
							ProfileForm.put("last_name", lastname_edittext
									.getText().toString());
							System.out.println("lastname_edittext"
									+ ProfileForm);
							StringBuffer sb = new StringBuffer(
									Global_variable.Mobile_no_code
											+ Mobile_number_edittext.getText()
													.toString());
							ProfileForm.put("mobile_number", sb.delete(0, 2));
							System.out.println("MobileNumber_vaue"
									+ sb.delete(0, 2));
							System.out.println("mobile_number" + ProfileForm);
							ProfileForm.put("address_line_1",
									House_number_edittext.getText().toString());
							System.out.println("House_number_edittext"
									+ ProfileForm);
							ProfileForm.put("address_line_2", Street_edittext
									.getText().toString());
							System.out.println("Street_edittext" + ProfileForm);
							ProfileForm.put("region_id", URLEncoder
									.encode(select_region.getText().toString(),
											"utf-8"));
							System.out.println("region_id" + ProfileForm);
							ProfileForm.put("city_id", select_city.getText()
									.toString());
							System.out.println("city_id" + ProfileForm);
//							ProfileForm.put("district_id", select_district
//									.getText().toString());
//							System.out.println("district_id" + ProfileForm);
							
							System.out.println("Username_edittext"
									+ ProfileForm);
							ProfileForm.put("username", Username_edittext
									.getText().toString());
							System.out.println("Username_edittext"
									+ ProfileForm);
							ProfileForm.put("email", emial_edittext.getText()
									.toString());
							System.out.println("emial_edittext" + ProfileForm);
							if(SharedPreference
									.getuser_id(getApplicationContext()).length()!=0)
							{
								ProfileForm.put("user_id",SharedPreference
										.getuser_id(getApplicationContext()));	
							}
							else
							{
								ProfileForm.put("user_id","");
							}
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						

						JSONObject datastreams = new JSONObject();
						datastreams.put("ProfileForm", ProfileForm);
						//datastreams.put("UserForm", UserForm);
						System.out.println("login_form" + datastreams);
						datastreams.put("sessid", SharedPreference.getsessid(getApplicationContext()));
						System.out.println("!!!!!!!!!1final_obj.." + datastreams);
						
						/* Http Request */

						try {
						String responseText = http
								.connection(MyProfile.this,
										 Global_variable.rf_update_profile_api_path,
										datastreams);

						
							json = new JSONObject(responseText);
							System.out.println("last_json" + json);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NullPointerException n) {
						n.printStackTrace();
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

			if(profiledialog.isShowing())
			{
				profiledialog.dismiss();
			}
			

			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONObject data = json.getJSONObject("data");
				String Data_Success = data.getString(TAG_SUCCESS);
				System.out.println("Data tag" + Data_Success);
				// ******** data succsess
				if (Data_Success.equalsIgnoreCase("true")) {
					
					String message = data.getString("message");

					// Global_variable.Str_FirstName=firstname_edittext.getText().toString();
					// Global_variable.Str_LastName=lastname_edittext.getText().toString();
					// Global_variable.Str_email=emial_edittext.getText().toString();
					// Global_variable.Str_Mobile_No=Mobile_number_edittext.getText().toString();

					Toast.makeText(getApplicationContext(),
							message, Toast.LENGTH_LONG).show();
					Intent ii = new Intent(getApplicationContext(), Search_Restaurant_List.class);
					startActivity(ii);

				}
				// **** invalid output
				else {
					if (Data_Success.equalsIgnoreCase("false")) {
						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("email")) {
							JSONArray Array_email = Data_Error
									.getJSONArray("email");
							System.out.println("Array_email" + Array_email);
							String Str_email = Array_email.getString(0);
							System.out.println("Str_email" + Str_email);
							if (Str_email != null) {
								Toast.makeText(getApplicationContext(),
										Str_email, Toast.LENGTH_LONG).show();
							}
						} else if (Data_Error.has("first_name")) {
							JSONArray Array_first_name = Data_Error
									.getJSONArray("first_name");
							System.out.println("Array fist" + Array_first_name);
							String Str_first_name = Array_first_name
									.getString(0);
							System.out.println("Str_first_name"
									+ Str_first_name);
							if (Str_first_name != null) {
								Toast.makeText(getApplicationContext(),
										Str_first_name, Toast.LENGTH_LONG)
										.show();
							}
						} else if (Data_Error.has("last_name")) {
							JSONArray Array_last_name = Data_Error
									.getJSONArray("last_name");
							System.out.println("Array_last_name"
									+ Array_last_name);
							String Str_last_name = Array_last_name.getString(0);
							System.out.println("Str_last_name" + Str_last_name);
							if (Str_last_name != null) {
								Toast.makeText(getApplicationContext(),
										Str_last_name, Toast.LENGTH_LONG)
										.show();
							}
						} else if (Data_Error.has("address_line_1")) {
							JSONArray Array_address_line_1 = Data_Error
									.getJSONArray("address_line_1");
							System.out.println("Array_address_line_1"
									+ Array_address_line_1);
							String Str_address_line_1 = Array_address_line_1
									.getString(0);
							System.out.println("Str_address_line_1"
									+ Str_address_line_1);
							if (Str_address_line_1 != null) {
								Toast.makeText(getApplicationContext(),
										Str_address_line_1, Toast.LENGTH_LONG)
										.show();
							}
						} else if (Data_Error.has("address_line_2")) {
							JSONArray Array_address_line_2 = Data_Error
									.getJSONArray("address_line_2");
							System.out.println("Array_address_line_2"
									+ Array_address_line_2);
							String Str_address_line_2 = Array_address_line_2
									.getString(0);
							System.out.println("Str_address_line_2"
									+ Str_address_line_2);
							if (Str_address_line_2 != null) {
								Toast.makeText(getApplicationContext(),
										Str_address_line_2, Toast.LENGTH_LONG)
										.show();
							}
						}
						if (Data_Error.has("region_id")) {
							JSONArray Array_region_id = Data_Error
									.getJSONArray("region_id");
							System.out.println("Array_region_id"
									+ Array_region_id);
							String Str_region_id = Array_region_id.getString(0);
							System.out.println("Str_region_id" + Str_region_id);
							if (Str_region_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_region_id, Toast.LENGTH_LONG)
										.show();
							}
						} else if (Data_Error.has("city_id")) {
							JSONArray Array_city_id = Data_Error
									.getJSONArray("city_id");
							System.out.println("Array_city_id" + Array_city_id);
							String Str_city_id = Array_city_id.getString(0);
							System.out.println("Str_city_id" + Str_city_id);
							if (Str_city_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_city_id, Toast.LENGTH_LONG).show();
							}
						} 
						//	else if (Data_Error.has("district_id")) {
//							JSONArray Array_district_id = Data_Error
//									.getJSONArray("district_id");
//							System.out.println("Array_district_id"
//									+ Array_district_id);
//							String Str_district_id = Array_district_id
//									.getString(0);
//							System.out.println("Str_district_id"
//									+ Str_district_id);
//							if (Str_district_id != null) {
//								Toast.makeText(getApplicationContext(),
//										Str_district_id, Toast.LENGTH_LONG)
//										.show();
//							}
//						}
							else if (Data_Error.has("username")) {
							JSONArray Array_username = Data_Error
									.getJSONArray("username");
							System.out.println("Array_username"
									+ Array_username);
							String Str_username = Array_username.getString(0);
							System.out.println("Str_username" + Str_username);
							if (Str_username != null) {
								Toast.makeText(getApplicationContext(),
										Str_username, Toast.LENGTH_LONG).show();
							}
						} else if (Data_Error.has("mobile_number")) {
							JSONArray Array_mobile_number = Data_Error
									.getJSONArray("mobile_number");
							System.out.println("Array_cpassword"
									+ Array_mobile_number);
							String Str_mobile_number = Array_mobile_number
									.getString(0);
							System.out.println("Str_cpassword"
									+ Str_mobile_number);
							if (Str_mobile_number != null) {
								Toast.makeText(getApplicationContext(),
										Str_mobile_number, Toast.LENGTH_LONG)
										.show();
							}
						}
					}

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

	public class async_city_Spinner extends AsyncTask<Void, Void, Void> {
		JSONObject city_jsonobj;
		String responseText;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MyProfile.this);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

			try {
				city_jsonobj = new JSONObject();
				city_jsonobj.put("type", "City");
				System.out.println("city_spinner" + city_jsonobj);
				if (Region_id != null) {
					city_jsonobj.put("parent_id", Region_id);
				} else {
					city_jsonobj.put("parent_id", "");
				}

				System.out.println("region_spinner" + city_jsonobj);
				city_jsonobj.put("sessid", SharedPreference.getsessid(getApplicationContext()));
				System.out.println("city_session_id" + city_jsonobj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}

		}

		/**
		 * Getting user details in background thread
		 * */
		protected Void doInBackground(Void... params) {
			try {

				// updating UI from Background Thread

				// *************
				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_Geosearch_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(city_jsonobj.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				responseText = null;
				try {

					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("spinner_last_text" + responseText);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				} catch (NullPointerException n) {
					n.printStackTrace();
				}

			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			//
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(Void Result) {
			super.onPostExecute(Result);
			try {
				System.out.println("get_respontext" + responseText);
				JSONObject json = new JSONObject(responseText);
				System.out.println("spinner_last_json_city" + json);

				// json success tag
				String success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONArray data = json.getJSONArray("data");
				System.out.println("ayu??" + data);

				int length = data.length();
				System.out.println("lenth" + length);
				if (length == 0) {
					select_city.setText(R.string.No_City);
					City_Array = new String[length];
				} else {
					select_city.setText(R.string.Register_Select_City);
					select_district.setText(R.string.Select_District);
					City_Array = new String[length];
					Hashmap_City = new HashMap<String, String>();
					for (int i = 0; i < length; i++) {

						try {
							JSONObject array_Object = data.getJSONObject(i);
							String city_name = array_Object.getString("name");
							System.out.println("city_name" + city_name);
							String city_id = array_Object.getString("id");
							System.out.println("city_id" + city_id);
							Hashmap_City.put(city_name, city_id);
							City_Array[i] = city_name.toString();
							System.out.println("City_Array[" + i + "]"
									+ City_Array[i]);

						} catch (Exception ex) {
							System.out.println("Error" + ex);
						}
					}
					select_city.setClickable(true);
				}
				// Region_spinner();
				// System.out.println("Hasmap Value"+Hashmap_City);
				// JSONObject name_city = json.getJSONObject("data");
				// System.out.println("name_city"+name_city);
				//
				// String Data_Successs = name_city.getString(TAG_SUCCESS);
				// System.out.println("Data tag"+Data_Successs);
				//
				// //******** data succsess
				// if (Data_Successs == "true")
				// {
				//
				// }
				// else
				// {
				//
				// }

				new async_district_Spinner().execute();

			}

			catch (Exception e) {
				e.printStackTrace();
			}
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}

		}

	}

	// ***** registration class coplete

	// city spinner class *****************

	public class async_district_Spinner extends AsyncTask<Void, Void, Void> {
		JSONObject district_jsonobject;
		String responseText;
		JSONObject spinner_final;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			CheckNetworkandSQlitecall = true;
//			pDialog = new ProgressDialog(MyProfile.this);
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();

			district_jsonobject = new JSONObject();

			try {
				district_jsonobject.put("type", "District");
				System.out.println("district_spinner" + district_jsonobject);
				if (city_id != null) {
					district_jsonobject.put("parent_id", city_id);
				} else {
					district_jsonobject.put("parent_id", "");
				}

				System.out.println("region_spinner" + district_jsonobject);
				System.out.println("session_id" + SharedPreference.getsessid(getApplicationContext()));
				district_jsonobject.put("sessid", SharedPreference.getsessid(getApplicationContext()));
				System.out.println("district_session_id" + district_jsonobject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * Getting user details in background thread
		 * */
		protected Void doInBackground(Void... params) {
			// updating UI from Background Thread

			// Check for success tag
			try {

				// *************
				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_Geosearch_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						district_jsonobject.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("district_spinner_last_text"
							+ responseText);
					Log.d("Insert: ", "Inserting ..");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String currentTimeStamp = dateFormat.format(new Date()); // Find
																				// todays
																				// date

					System.out.println("Timestamp" + currentTimeStamp);

					System.out.println("Current Time" + currentTimeStamp);
					DBAdapter.addData(new DatabaseData(city_id, responseText,
							currentTimeStamp));
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				} catch (NullPointerException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				}

			}

			catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(Void Result) {
			super.onPostExecute(Result);
			System.out.println("CheckNetworkandSQlitecall"
					+ CheckNetworkandSQlitecall);
			JSONObject json = new JSONObject();
			try {
				if (CheckNetworkandSQlitecall) {
					if (pDialog.isShowing()) {
						pDialog.dismiss();
					}
					System.out.println("Your_Data_Come_In_Network_call");
					/* you are come to network call */

					json = (JSONObject) new JSONTokener(responseText)
							.nextValue();

					// System.out.println("wjbty_finalResult"+Json_Main);

					CheckNetworkandSQlitecall = false;
				} else {
					System.out.println("Your_Data_Come_In_Sqlite_Call"
							+ data_sql.length());
					/* you are come to sqlite call */

					json = (JSONObject) new JSONTokener(data_sql.toString())
							.nextValue();
					System.out.println("wjbty_District" + json.toString());

					// CheckNetworkandSQlitecall=false;
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				// json success tag
				String success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONArray response_array = json.getJSONArray("data");
				System.out.println("rsponse_get_parameter" + response_array);
				int lenth = response_array.length();
				System.out.println("lenth" + lenth);
				if (lenth == 0) {
					select_district.setText(R.string.No_District);
					District_Array = new String[lenth];
				} else {
					select_district.setText(R.string.Select_District);
					District_Array = new String[lenth];
					Hashmap_District = new HashMap<String, String>();

					for (int i = 0; i <= lenth; i++) {
						try {
							JSONObject array_Object = response_array
									.getJSONObject(i);
							String district_name = array_Object
									.getString("name");
							System.out.println("district_name" + district_name);
							String district_id = array_Object.getString("id");
							System.out.println("district_id" + district_id);
							Hashmap_District.put(district_name, district_id);
							District_Array[i] = district_name.toString();
							System.out.print("District_Array[" + i + "]"
									+ District_Array[i]);
						} catch (Exception ex) {
							System.out.println("Error" + ex);
						}
					}
					select_district.setClickable(true);
				}
				// Region_spinner();
				// JSONObject name_district = json.getJSONObject("data");
				// System.out.println("name_district"+name_district);
				//
				// String Data_Success = name_district.getString(TAG_SUCCESS);
				// System.out.println("Data tag"+Data_Success);
				//
				// //******** data succsess
				// if (Data_Success == "true")
				// {
				//
				// }
				// else
				// {
				//
				// }

				if (profile_flag == true) {
					System.out.println("!!!!In aysncRegion_id" + Region_id);
					System.out.println("!!!!In async..Region" + Hashmap_Region);
					for (Entry<String, String> entry : Hashmap_Region
							.entrySet()) {
						if (entry.getValue().equals(Region_id)) {
							System.out.println("!!!!!!!!!!!!!!"
									+ entry.getKey());
							String str_selectedregion = entry.getKey();
							// select_region=Hashmap_Region.get

							select_region.setText(str_selectedregion);

						}
					}

					System.out.println("!!!!In aysncCity_id" + city_id);
					System.out.println("!!!!In async..City.." + Hashmap_City);
					for (Entry<String, String> entry : Hashmap_City.entrySet()) {
						if (entry.getValue().equals(city_id)) {
							System.out.println("!!!!!!!!!!!!!!"
									+ entry.getKey());
							String str_selectedcity = entry.getKey();
							// select_region=Hashmap_Region.get

							select_city.setText(str_selectedcity);

						}
					}
					System.out.println("!!!!In aysncDistrict_id" + District_id);
					System.out.println("!!!!In async..District.."
							+ Hashmap_District);
					for (Entry<String, String> entry : Hashmap_District
							.entrySet()) {
						if (entry.getValue().equals(District_id)) {
							System.out.println("!!!!!!!!!!!!!!"
									+ entry.getKey());
							String str_selecteddistict = entry.getKey();
							// select_region=Hashmap_Region.get

							select_district.setText(str_selecteddistict);

						}
					}

					profile_flag = false;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}

	}

	public class async_region extends AsyncTask<Void, Void, Void> {
		JSONObject fetch_spinner;
		String responseText;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			fetch_spinner = new JSONObject();

			try {
				fetch_spinner.put("type", "Region");
				System.out.println("fix_region_spinner" + fetch_spinner);
				if (Global_variable.Region_Parent_id != null) {
					fetch_spinner.put("parent_id",
							Global_variable.Region_Parent_id);
				} else {
					fetch_spinner.put("parent_id", "");
				}

				System.out.println("fix_region_spinner" + fetch_spinner);
				fetch_spinner.put("sessid", SharedPreference.getsessid(getApplicationContext()));
				System.out.println("session_id" + fetch_spinner);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// Check for success tag
			try {

				// *************
				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_Geosearch_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(fetch_spinner.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("spinner_last_text" + responseText);

				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				}

			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {

				JSONObject json = new JSONObject(responseText);
				System.out.println("spinner_last_json" + json);
				// json success tag
				String success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				// JSONObject data = json.getJSONObject("data");
				JSONArray response_array = json.getJSONArray("data");
				System.out.println("rsponse_get_parameter" + response_array);
				int length = response_array.length();
				System.out.println("lenth" + length);
				Region_Array = new String[length];
				Hashmap_Region = new HashMap<String, String>();

				for (int i = 0; i <= length; i++) {
					try {
						JSONObject array_Object = response_array
								.getJSONObject(i);
						String Region_Name = array_Object.getString("name");
						System.out.println("Region_Name" + Region_Name);
						String Region_Id = array_Object.getString("id");
						System.out.println("Region_Id" + Region_Name);
						Hashmap_Region.put(Region_Name, Region_Id);
						Region_Array[i] = Region_Name.toString();
						System.out.println("Region_Array[" + i + "]"
								+ Region_Array[i]);
					} catch (Exception ex) {
						System.out.println("Error" + ex);
					}
				}
				// Region_spinner();
				System.out.println("HashMapValue"
						+ Global_variable.hashmap_region);
				// JSONObject name_region = json.getJSONObject("data");
				// System.out.println("name_region"+name_region);
				//
				// String Data_Success = name_region.getString(TAG_SUCCESS);
				// System.out.println("Data tag"+Data_Success);
				// //******** data succsess
				// if (Data_Success == "true")
				// {
				//
				//
				// }
				// //**** invalid username password
				// else
				// {
				// }
				//

				new async_city_Spinner().execute();

			} catch (Exception e) {
				e.printStackTrace();
			}
			select_region.setClickable(true);

		}

	}

	// hm is the map you are trying to get value from it
	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
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
