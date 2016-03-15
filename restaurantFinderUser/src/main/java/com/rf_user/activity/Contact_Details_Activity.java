package com.rf_user.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.Adapter_for_Payment_Offers;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Contact_Details_Activity extends Activity

{
	LinearLayout ly_contact_details, ly_date_time;
	ImageView img_continue, img_next_date_time, img_calender;
	EditText ED_FirstName, ED_LastName, ED_Email, ED_Mobile, ED_Zipcode;
	TextView txv_selectdate, txv_selecttime;
	String str_previous_date, str_selected_date;
	String str_previous_time, str_selected_time,output;
	int selecteddate = 0;
	int selectedtime = 0;
	boolean dialogShown = false;
	Adapter_for_Payment_Offers adapter_for_payment_offer;
	ProgressDialog progressDialog;
	/*** Network Connection Instance **/

	// GPSTracker class
	com.rf_user.connection.GPSTracker gps;

	ConnectionDetector cd;
	
	java.sql.Date currDate;

	int year, month, day, hour, minutes, second, hour_to;
	Calendar c;
	/* Declaration for http call */
	HttpConnection http = new HttpConnection();
	String TAG_SUCCESS = "success";
	
	/* Language conversion */
	private Locale myLocale;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.delivery_contact_details);

		try {
			
			currDate = new java.sql.Date(System
					.currentTimeMillis());

			getIntialCalender();

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());
			System.out.println("Str_Houseno" + Global_variable.Str_Houseno);
			System.out.println("Str_Street" + Global_variable.Str_Street);
			System.out.println("Str_CityName" + Global_variable.Str_CityName);
			System.out.println("Str_DistrictName"
					+ Global_variable.Str_DistrictName);
			System.out.println("Str_Zipcode" + Global_variable.Str_Zipcode);
			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new async_GetCurrentUserDetails().execute();

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();
						//do {
							System.out.println("do-while");
							if (cd.isConnectingToInternet()) {

								new async_GetCurrentUserDetails().execute();

							}
						//} while (cd.isConnectingToInternet() == false);

					}

				});
			}

			initializeWidgets();
			setlistener();
			
			loadLocale();

		} catch (NullPointerException e) {
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

		Intent i = new Intent(Contact_Details_Activity.this, Cart.class);
		startActivity(i);

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		try {

			/** image view */
			img_continue = (ImageView) findViewById(R.id.img_continue);
			img_next_date_time = (ImageView) findViewById(R.id.img_next_date_time);
			/****** linear layout ***/
			ly_contact_details = (LinearLayout) findViewById(R.id.ly_contact_details);
			ly_date_time = (LinearLayout) findViewById(R.id.ly_date_time);
			ED_FirstName = (EditText) findViewById(R.id.firstname_edittext);
			ED_LastName = (EditText) findViewById(R.id.lastname_edittext);
			ED_Email = (EditText) findViewById(R.id.email_edittext);
			ED_Mobile = (EditText) findViewById(R.id.mobile_edittext);
			ED_Zipcode = (EditText) findViewById(R.id.zipcode_edittext);

			/** Date and time screen */
			txv_selectdate = (TextView) findViewById(R.id.txv_selectdate);
			txv_selecttime = (TextView) findViewById(R.id.txv_selecttime);
			// img_calender = (ImageView) findViewById(R.id.img_calender);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	private void setlistener() {

		try {

			img_continue.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					System.out.println("!!!!!!!!!!!!!!!latitude..."
							+ Global_variable.latitude);
					System.out.println("!!!!!!!!!!!!!!!longitude..."
							+ Global_variable.longitude);
					if (Global_variable.latitude == 0.0
							&& Global_variable.longitude == 0.0) {
						gps = new com.rf_user.connection.GPSTracker(
								Contact_Details_Activity.this);

						// check if GPS enabled
						if (gps.canGetLocation()) {

							Global_variable.latitude = gps.getLatitude();
							Global_variable.longitude = gps.getLongitude();

							// \n is for new line
//							Toast.makeText(
//									getApplicationContext(),
//									getString(R.string.str_lat)
//											+ Global_variable.latitude
//											+ getString(R.string.str_long)
//											+ Global_variable.longitude,
//									Toast.LENGTH_LONG).show();
						} else {
							// can't get location
							// GPS or Network is not enabled
							// Ask user to enable GPS/network in settings

							Toast.makeText(getApplicationContext(),
									getString(R.string.str_gpsoff), Toast.LENGTH_LONG)
									.show();

							gps.showSettingsAlert();
						}
					}

					if (Global_variable.latitude != 0.0
							&& Global_variable.longitude != 0.0) {
						Global_variable.Str_FirstName = ED_FirstName.getText()
								.toString();
						Global_variable.Str_LastName = ED_LastName.getText()
								.toString();
						Global_variable.Str_email = ED_Email.getText()
								.toString();
						Global_variable.Str_Mobile = ED_Mobile.getText()
								.toString();
						Global_variable.Str_Zipcode = ED_Zipcode.getText()
								.toString();

						Global_variable.str_User_FirstName = ED_FirstName
								.getText().toString();
						Global_variable.str_User_LastName = ED_LastName
								.getText().toString();
						Global_variable.str_User_Email = ED_Email.getText()
								.toString();
						Global_variable.str_User_ContactNumber = ED_Mobile
								.getText().toString();
						/*Global_variable.Str_Zipcode = ED_Zipcode.getText()
								.toString();*/

						if (Global_variable.Str_FirstName.equals("")) {
							Toast.makeText(Contact_Details_Activity.this,
									R.string.blank_first_name,
									Toast.LENGTH_LONG).show();
							ED_FirstName.requestFocus();
						}
						if (Global_variable.Str_LastName.equals("")) {
							Toast.makeText(Contact_Details_Activity.this,
									R.string.blank_last_name, Toast.LENGTH_LONG)
									.show();
							ED_LastName.requestFocus();
						} else if (Global_variable.Str_email.equals("")) {
							Toast.makeText(Contact_Details_Activity.this,
									R.string.blank_email, Toast.LENGTH_LONG)
									.show();
							ED_Email.requestFocus();
						} else if (!(ED_Mobile.getText().toString()
								.matches("[0-9]{10}+"))) {

							Toast.makeText(Contact_Details_Activity.this,
									R.string.blank_mobile_number,
									Toast.LENGTH_LONG).show();
							ED_Mobile.requestFocus();
						}
						// else if (!(CR_Telephone_No.getText().toString()
						// .matches("[+]{1}[0-9]{11,12}+")))
						else if (isEmailValid(Global_variable.Str_email) == false) {
							Toast.makeText(Contact_Details_Activity.this,
									R.string.invalid_email, Toast.LENGTH_LONG)
									.show();
							ED_Email.requestFocus();
						} else {
							System.out.println("Str_Delivery_ok"
									+ Global_variable.shipping_tag_delivery_ok);
							System.out.println("Str_Houseno"
									+ Global_variable.Str_Houseno);
							System.out.println("Str_Street"
									+ Global_variable.route);
							System.out.println("Str_CityId"
									+ Global_variable.Str_CityName);
							System.out.println("Str_DistrictId"
									+ Global_variable.Str_DistrictId);
							System.out.println("Str_DistrictName"
									+ Global_variable.Str_DistrictName);
							System.out.println("Str_latitude"
									+ Global_variable.latitude);
							System.out.println("Str_longitude"
									+ Global_variable.longitude);
							System.out.println("Str_zoom"
									+ Global_variable.zoom);
							System.out.println("Str_Zipcode"
									+ Global_variable.postal_code);
							System.out.println("Str_FirstName"
									+ Global_variable.Str_FirstName);
							System.out.println("Str_LastName"
									+ Global_variable.Str_LastName);
							System.out.println("Str_email"
									+ Global_variable.Str_email);
							System.out.println("Str_Mobile"
									+ Global_variable.Str_Mobile);
							System.out.println("Str_Comment"
									+ Global_variable.Str_Comment);
							System.out.println("Str_delivery_schedule_on"
									+ Global_variable.Str_Delivery_schedule_On);
							System.out.println("Str_shipping_tag_addr_type"
									+ Global_variable.shipping_tag_addr_type);

							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {
								ly_contact_details.setVisibility(View.GONE);
								ly_date_time.setVisibility(View.VISIBLE);

								new async_Shipping_Request().execute();

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

						Checkout_Tablayout.tab.getTabWidget().getChildAt(1)
								.setClickable(true);

					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.str_gpsoff), Toast.LENGTH_LONG).show();

						gps.showSettingsAlert();
					}

				}
			});

			img_next_date_time.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						
						boolean Flag_Datetime = false;
						
						System.out.println("Murtuza_Chetan_Date_Time"
								+ txv_selectdate.getText().toString()
								+ txv_selecttime.getText().toString());
						// new async_Step_Choose_Time().execute();
						if (txv_selectdate.getText().toString()
								.equalsIgnoreCase("")) {
							System.out.println("Murtuza_Chetan_Date");
							Flag_Datetime = true;
							Toast.makeText(Contact_Details_Activity.this,
									getString(R.string.str_select_date), Toast.LENGTH_LONG).show();
							txv_selectdate.requestFocus();
						}
						if (txv_selecttime.getText().toString()
								.equalsIgnoreCase("")) {
							System.out.println("Murtuza_Chetan_Time");
							Flag_Datetime = true;
							Toast.makeText(Contact_Details_Activity.this,
									getString(R.string.str_select_time), Toast.LENGTH_LONG).show();
							txv_selecttime.requestFocus();
						}
						
						
						if (Flag_Datetime == false) {
							
							
							String[] fields = txv_selecttime.getText().toString().split("[:]");

							try{
							
							
							int str_hour = Integer.parseInt(fields[0]);
							int str_minute = Integer.parseInt(fields[1]);
							

							System.out.println("!!!!" + fields[0]);
							System.out.println("!!!!" + fields[1]);
						
							
							

							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {
								
								
								System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
										+ txv_selectdate.getText().toString());

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
										+ currDate.toString());

						if (txv_selectdate.getText().toString()
								.equalsIgnoreCase(currDate.toString())) {
							c = Calendar.getInstance();
							int curr_hour = c.get(Calendar.HOUR_OF_DAY);
							int curr_minutes = c.get(Calendar.MINUTE);

							System.out
									.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
											+ curr_hour);
							System.out
									.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
											+ curr_minutes);

							

							if (str_hour < curr_hour
									&& str_minute < curr_minutes || str_hour<curr_hour && str_minute<=curr_minutes || str_hour==curr_hour && str_minute<curr_minutes) {
								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in if");
								Toast.makeText(getApplicationContext(),
										getString(R.string.str_Please_choose_valid_time),
										Toast.LENGTH_SHORT).show();
							} else {

								minutes = str_minute;
								hour = str_hour;

								String time1 = hour + ":" + minutes;
								
								//String db_time = hour + ":" + "00";
								
								System.out.println("!!!!!!!!!!!!!!!!!time..."+time1);
								
								Date time;
								try {
									time = new SimpleDateFormat("HH:mm")
									.parse(hour + ":" + minutes);
									
									DateFormat outputFormatter = new SimpleDateFormat(
											"HH:mm");
									String final_time = outputFormatter.format(time);
									
									System.out.println("!!!!!!!!!!!!!!!!!final_time..."+final_time);
									
									// Display Selected time in textbox
								//	txv_selecttime.setText(final_time);
									Global_variable.str_Time_From = final_time;
									Global_variable.str_Time_To = final_time;
									
									
									new GetValidOrderDateTime().execute();
									
									
								} catch (java.text.ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} else {
							minutes = str_minute;
							hour = str_hour;

							String time1 = hour + ":" + minutes;
							
							//String db_time = hour + ":" + "00";
							
							System.out.println("!!!!!!!!!!!!!!!!!time..."+time1);
							
							Date time;
							try {
								time = new SimpleDateFormat("HH:mm")
								.parse(hour + ":" + minutes);
								
								DateFormat outputFormatter = new SimpleDateFormat(
										"HH:mm");
								String final_time = outputFormatter.format(time);
								
								System.out.println("!!!!!!!!!!!!!!!!!final_time..."+final_time);
								
								// Display Selected time in textbox
								//txv_selecttime.setText(final_time);
								Global_variable.str_Time_From = final_time;
								Global_variable.str_Time_To = final_time;
								
								new GetValidOrderDateTime().execute();
								
								
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
								
								
//								c = Calendar.getInstance();
//								int curr_hour_next = c.get(Calendar.HOUR_OF_DAY);
//								int curr_minute_next = c.get(Calendar.MINUTE);
//								
//								String curr_time_next = curr_hour_next+":"+curr_minute_next; 
//
//								System.out.println("!!!!!!!!!!!!!!!!!!!!curr_time_next"+curr_time_next);
//								System.out.println("!!!!!!!!!!!!!!!!!!!!txv_selecttime.getText().toString()"+txv_selecttime.getText().toString());
//								System.out.println("!!!!!!!!!!!!!!!!!!!!txv_selecttime.getText().toString().compareTo(curr_time_next)"+txv_selecttime.getText().toString().compareTo(curr_time_next));
//								
//								if(txv_selectdate.getText().toString().equalsIgnoreCase(currDate.toString()))
//								{
//									if(txv_selecttime.getText().toString().compareTo(curr_time_next)>=0)
//									{
//										
//									}
//									else
//									{
//										Toast.makeText(getApplicationContext(), getString(R.string.str_Please_choose_valid_time), Toast.LENGTH_SHORT).show();
//									}
//								}
//								else
//								{
//									new GetValidOrderDateTime().execute();
//								}
//								
								

								

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
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
							


						}
					} catch (NullPointerException n) {

					}
				}
			});

			txv_selectdate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					try {
						// if (Global_variable.Date.length != 0) {

						// date_spinner();

						getCalenderView();

						// }
					} catch (NullPointerException e) {
						System.out.println("Exception" + e);
					}

				}
			});

			txv_selecttime.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					try {

						System.out
								.println("!!!!!!!!!!!!!!!!!Global_variable.Time_To"
										+ Global_variable.Time_To);

						// if (Global_variable.Time_To.length != 0) {

						// time_spinner();

						getTimeView();

						// }
					} catch (NullPointerException e) {
						System.out.println("Exception" + e);
					}

				}
			});

			// txv_selecttime.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			//
			// System.out
			// .println("!!!!!!!!!!!!!!!!!Global_variable.Time_To"
			// + Global_variable.Time_To);
			//
			// try {
			// // if (Global_variable.Time_To.length != 0) {
			// // time_spinner();
			// getTimeView();
			// // }
			// } catch (NullPointerException e) {
			// System.out.println("Exception" + e);
			// }
			//
			// }
			// });

		} catch (NullPointerException e) {

		}

	}

	/* Fetch valid date and time from db for OO */

	class GetValidOrderDateTime extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Contact_Details_Activity.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(false);
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
						JSONObject OrderDateTime = new JSONObject();

						if(Global_variable.hotel_id!=null)
						{
							OrderDateTime.put("rest_id",
									Global_variable.hotel_id);
						}
						else
						{
							OrderDateTime.put("rest_id",
									"");
						}
						System.out.println("hotel_id" + OrderDateTime);
						
						if(Global_variable.str_Date!=null)
						{
							OrderDateTime.put("date",
									Global_variable.str_Date);
						}
						else
						{
							OrderDateTime.put("date",
									"");
						}
						System.out.println("date" + OrderDateTime);
						
						if(Global_variable.str_Time_From!=null)
						{
							OrderDateTime.put("time",
									Global_variable.str_Time_From);
						}
						else
						{
							OrderDateTime.put("time",
									"");
						}
						System.out.println("time" + OrderDateTime);
						
						OrderDateTime.put("type","OO");
						
						OrderDateTime.put("sessid", SharedPreference
								.getsessid(getApplicationContext()));
						System.out.println("session_id" + OrderDateTime);
						// *************

						String responseText = http
								.connection(Contact_Details_Activity.this,
										 Global_variable.rf_GetValidOrderDateTime_api_path,
												OrderDateTime);

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
			
			
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

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

						String open_time = data.getString("open_time");
						String close_name = data.getString("close_time");
						
						String lunch_open_day = data.getString("lunch_open_day");
						String dinner_open_day = data.getString("dinner_open_day");
						
						
						if(lunch_open_day.equals("0") && dinner_open_day.equals("0"))
						{
							Toast.makeText(getApplicationContext(), getString(R.string.restaurant_closed), Toast.LENGTH_SHORT).show();
						}
						else
						{
							if (Checkout_Tablayout.language.equals("ar")) {
								Checkout_Tablayout.tab.getTabWidget().getChildAt(2)
										.setClickable(true);
								Checkout_Tablayout.tab.setCurrentTab(2);
							} else {
								Checkout_Tablayout.tab.getTabWidget().getChildAt(2)
										.setClickable(true);
								Checkout_Tablayout.tab.setCurrentTab(2);
							}
						}
						

						

					}

				}

				// **** invalid output
				else {
					if (success1.equalsIgnoreCase("false")) {
						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("rest_id")) {
							JSONArray Array_rest_id = Data_Error
									.getJSONArray("rest_id");
							System.out.println("Array_rest_id" + Array_rest_id);
							String Str_rest_id = Array_rest_id.getString(0);
							System.out.println("Str_rest_id" + Str_rest_id);
							if (Str_rest_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_rest_id, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("type")) {
							JSONArray Array_type = Data_Error
									.getJSONArray("type");
							System.out.println("Array_type" + Array_type);
							String Str_type = Array_type.getString(0);
							System.out.println("Str_type" + Str_type);
							if (Str_type != null) {
								Toast.makeText(getApplicationContext(),
										Str_type, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("date")) {
							JSONArray Array_date = Data_Error
									.getJSONArray("date");
							System.out.println("Array_date" + Array_date);
							String Str_date = Array_date.getString(0);
							System.out.println("Str_date" + Str_date);
							if (Str_date != null) {
								Toast.makeText(getApplicationContext(),
										Str_date, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("time")) {
							JSONArray Array_time = Data_Error
									.getJSONArray("time");
							System.out.println("Array_time" + Array_time);
							String Str_time = Array_time.getString(0);
							System.out.println("Str_time" + Str_time);
							if (Str_time != null) {
								Toast.makeText(getApplicationContext(),
										Str_time, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("sessid")) {
							JSONArray Array_sessid = Data_Error
									.getJSONArray("sessid");
							System.out.println("Array_sessid" + Array_sessid);
							String Str_sessid = Array_sessid.getString(0);
							System.out.println("Str_email" + Str_sessid);
							if (Str_sessid != null) {
								Toast.makeText(getApplicationContext(),
										Str_sessid, Toast.LENGTH_LONG).show();
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

	public class async_Shipping_Request extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(Contact_Details_Activity.this);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			if (Global_variable.shipping_tag_delivery_ok == "1") // shipping
																	// information
			{
				try {

					if (Global_variable.shipping_tag_addr_type == "pre") {
						Global_variable.Shipping_Request_Child.put(
								"delivery_ok",
								Global_variable.shipping_tag_delivery_ok);
						// System.out.println("fix_city_spinner"+fetch_spinner);
						Global_variable.Shipping_Request_Child.put(
								"user_first_name",
								Global_variable.Str_FirstName);
						Global_variable.Shipping_Request_Child.put(
								"user_last_name", Global_variable.Str_LastName);
						Global_variable.Shipping_Request_Child.put(
								"user_email", Global_variable.Str_email);
						Global_variable.Shipping_Request_Child.put(
								"user_contact_number",
								Global_variable.Str_Mobile);

						if (Global_variable.Str_Zipcode != null) {
							Global_variable.Shipping_Request_Child.put(
									"user_comment",Global_variable.Str_Zipcode);
						} else {
							Global_variable.Shipping_Request_Child.put(
									"user_comment", "");
						}

						Global_variable.Shipping_Request_Child.put(
								"delivery_schedule_on",
								Global_variable.Str_Delivery_schedule_On);
						Global_variable.Shipping_Request_Child.put(
								"addr_number", Global_variable.Str_addr_number); // response
																					// from
																					// pre
																					// saved
																					// result

						System.out.println("Shipping_Request_Child"
								+ Global_variable.Shipping_Request_Child);

						System.out.println("!!!!!CheckoutStep1 api pre..."
								+ Global_variable.Shipping_Request_Child);

					} else if (Global_variable.shipping_tag_addr_type == "custom") {
						Global_variable.Shipping_Request_Child.put(
								"delivery_ok",
								Global_variable.shipping_tag_delivery_ok);
						Global_variable.Shipping_Request_Child.put(
								"house_number", Global_variable.Str_Houseno);

						if (Global_variable.route != null) {
							Global_variable.Shipping_Request_Child.put(
									"street", Global_variable.route);

						} else {
							Global_variable.Shipping_Request_Child.put(
									"street", "");
						}

						Global_variable.Shipping_Request_Child.put("city_id",
								Global_variable.Str_CityId);
						Global_variable.Shipping_Request_Child.put(
								"district_id", Global_variable.Str_DistrictId);
						Global_variable.Shipping_Request_Child.put("district",
								Global_variable.Str_DistrictName);
						Global_variable.Shipping_Request_Child.put("lat",
								Global_variable.latitude_del);
						Global_variable.Shipping_Request_Child.put("long",
								Global_variable.longitude_del);

						if (Global_variable.zoom != null) {
							Global_variable.Shipping_Request_Child.put("zoom",
									Global_variable.zoom);
						} else {
							Global_variable.Shipping_Request_Child.put("zoom",
									"");
						}

						if (Global_variable.postal_code != null) {
							Global_variable.Shipping_Request_Child.put("zip",
									Global_variable.postal_code);
						} else {
							Global_variable.Shipping_Request_Child.put("zip",
									"");
						}

						Global_variable.Shipping_Request_Child.put(
								"user_first_name",
								Global_variable.Str_FirstName);
						Global_variable.Shipping_Request_Child.put(
								"user_last_name", Global_variable.Str_LastName);
						Global_variable.Shipping_Request_Child.put(
								"user_email", Global_variable.Str_email);
						Global_variable.Shipping_Request_Child.put(
								"user_contact_number",
								Global_variable.Str_Mobile);

						if (Global_variable.Str_Zipcode != null) {
							Global_variable.Shipping_Request_Child
									.put("user_comment",
											Global_variable.Str_Zipcode);
						} else {
							Global_variable.Shipping_Request_Child.put(
									"user_comment", "");
						}

						if (Global_variable.Str_Delivery_schedule_On != null) {
							Global_variable.Shipping_Request_Child.put(
									"delivery_schedule_on",
									Global_variable.Str_Delivery_schedule_On);
						} else {
							Global_variable.Shipping_Request_Child.put(
									"delivery_schedule_on", "0");
						}

						System.out.println("!!!!!CheckoutStep1 api custom..."
								+ Global_variable.Shipping_Request_Child);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException nupoi) {
					nupoi.printStackTrace();
				}
			} else if (Global_variable.shipping_tag_delivery_ok == "0") // I
																		// will
																		// be
																		// pick
																		// up
			{
				try {
					Global_variable.Shipping_Request_Child.put("delivery_ok",
							Global_variable.shipping_tag_delivery_ok);
					Global_variable.Shipping_Request_Child.put(
							"user_first_name", Global_variable.Str_FirstName);
					Global_variable.Shipping_Request_Child.put(
							"user_last_name", Global_variable.Str_LastName);
					Global_variable.Shipping_Request_Child.put("user_email",
							Global_variable.Str_email);
					Global_variable.Shipping_Request_Child.put(
							"user_contact_number", Global_variable.Str_Mobile);

					if (Global_variable.Str_Zipcode != null) {
						Global_variable.Shipping_Request_Child.put(
								"user_comment", Global_variable.Str_Zipcode);
					} else {
						Global_variable.Shipping_Request_Child.put(
								"user_comment", "");
					}

					if (Global_variable.Str_Delivery_schedule_On != null) {
						Global_variable.Shipping_Request_Child.put(
								"delivery_schedule_on",
								Global_variable.Str_Delivery_schedule_On);
					} else {
						Global_variable.Shipping_Request_Child.put(
								"delivery_schedule_on", "0");
					}

					Global_variable.Shipping_Request_Child.put("addr_number",
							"");

					System.out.println("");
					System.out.println("Shipping_Request_Child"
							+ Global_variable.Shipping_Request_Child);

					System.out.println("!!!!!CheckoutStep1 api pick myself..."
							+ Global_variable.Shipping_Request_Child);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException np) {
					np.printStackTrace();
				}
			}
			JSONObject Shipping_Request_Main = new JSONObject();

			try {
				if (Global_variable.shipping_tag_delivery_ok == "1") {
					Shipping_Request_Main.put("rest_id",
							Global_variable.hotel_id);
					Shipping_Request_Main.put("addr_type",
							Global_variable.shipping_tag_addr_type);
					// System.out.println("fix_city_spinner"+fetch_spinner);
					Shipping_Request_Main.put("saveAddressmine", "1");
					Shipping_Request_Main
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					Shipping_Request_Main
					.put("user_id", SharedPreference
							.getuser_id(getApplicationContext()));
					Shipping_Request_Main.put("ShippingForm",
							Global_variable.Shipping_Request_Child);
					Shipping_Request_Main.put("cart", Global_variable.cart);

					System.out.println("Shipping_Request_Child"
							+ Shipping_Request_Main);
				} else if (Global_variable.shipping_tag_delivery_ok == "0") {
					Shipping_Request_Main.put("rest_id",
							Global_variable.hotel_id);
					Shipping_Request_Main.put("addr_type",
							Global_variable.shipping_tag_addr_type);
					// System.out.println("fix_city_spinner"+fetch_spinner);
					Shipping_Request_Main.put("saveAddressmine", "1");
					Shipping_Request_Main
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					Shipping_Request_Main
					.put("user_id", SharedPreference
							.getuser_id(getApplicationContext()));
					Shipping_Request_Main.put("ShippingForm",
							Global_variable.Shipping_Request_Child);
					Shipping_Request_Main.put("cart", Global_variable.cart);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException nlp) {
				nlp.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + Shipping_Request_Main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_CheckoutStep4_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						Shipping_Request_Main.toString(), "UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("Shipping_last_text" + responseText);

					json = new JSONObject(responseText);
					System.out.println("Shipping_last_json" + json);

				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				System.out.println("Shipping_last_json_in post" + json);
				Global_variable.json_array_datetime = new JSONArray();
				// json success tag

				String success1 = json.getString("success");
				System.out.println("tag" + success1);
				// JSONObject data = json.getJSONObject("data");

				//Global_variable.json_array_datetime = json.getJSONArray("data");

//				System.out.println("Global_variable.json_array_datetime"
//						+ Global_variable.json_array_datetime);
//				int length_Data = Global_variable.json_array_datetime.length();
//				System.out.println("length_Data" + length_Data);
//				// Restaurant_Array = new String[length_Data];
//				Global_variable.Date = new String[Global_variable.json_array_datetime
//						.length()];

//				for (int i = 0; i < Global_variable.json_array_datetime
//						.length(); i++) {
//					try {
//						JSONObject array_Object = Global_variable.json_array_datetime
//								.getJSONObject(i);
//						String str_date = array_Object.getString("date");
//
//						System.out.println("date" + str_date);
//						Global_variable.Date[i] = str_date;
//
//						// Retaurant_Array[i] =vtype.toString();
//						// System.out.println("Retaurant_Array["+i+"]"+Retaurant_Array[i]);
//
//						System.out.println("Murtuza nalavala"
//								+ Global_variable.Date[i]);
//
//					} catch (Exception ex) {
//						System.out.println("Error" + ex);
//					}
//				}
//				System.out.println("wjbty_search_restaurant_district_list"
//						+ Global_variable.Date);

				
				
				// /** Convert Hashmap to array */
				// Global_variable.District_Array = new
				// String[Global_variable.hashmap_district
				// .size() + 1];
				// String[] values = new String[Global_variable.hashmap_district
				// .size() + 1];
				// Global_variable.District_Array[0] =
				// Global_variable.Default_District_String;
				// int index = 1;
				// for (Entry<String, String> mapEntry :
				// Global_variable.hashmap_district
				// .entrySet()) {
				// Global_variable.District_Array[index] = mapEntry.getKey();
				// values[index] = mapEntry.getValue();
				// index++;
				// }
				//
				// System.out.println("str_array_district_array"
				// + Global_variable.District_Array);

				/** Set District Spinner */
				// if (Global_variable.District_Array != null) {
				// /** Set Adapter to district spinner */
				//
				// }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException nexp) {

			}

			progressDialog.dismiss();
		}

	}

	private void date_spinner() {

		try {

			Builder builder = new AlertDialog.Builder(
					Contact_Details_Activity.this);
			builder.setTitle(R.string.contact_details_delivery_select_DATE);

			System.out.println("wjbty_city_array_0" + Global_variable.Date);
			str_previous_date = txv_selectdate.getText().toString();

			int index = -1;
			for (int i = 0; i < Global_variable.Date.length; i++) {
				if (Global_variable.Date[i].equals(str_previous_date)) {
					index = i;
					break;
				}
			}

			// previous_city_id=(String)Global_variable.hashmap_cities.get(str_previous_city);

			builder.setSingleChoiceItems(Global_variable.Date, index,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								selecteddate = which;
								// str_previous_city = str_select_city;
								str_selected_date = Global_variable.Date[which];
								Global_variable.str_Date = Global_variable.Date[which];
								System.out.println("Value of selected date"
										+ str_selected_date);
								int selected_date = -1;
								for (int i = 0; i < Global_variable.Date.length; i++) {
									if (Global_variable.Date[i]
											.equals(str_selected_date)) {
										selected_date = i;
										break;
									}
								}
								for_time_spinner(selected_date);

								// city_id=(String)Global_variable.ha.get(str_selected_date);
								// System.out.println("City_id_value"+city_id);
								//
								try {

									if (str_selected_date != null) {
										txv_selectdate
												.setText(str_selected_date);
										System.out.println("textview_cityset"
												+ str_selected_date);
										dialogShown = false;
										dialog.dismiss();
									} else {
										txv_selectdate
												.setText(str_previous_date);
										dialogShown = false;
										dialog.dismiss();
									}
								} catch (Exception e) {

								}
							} catch (IndexOutOfBoundsException in) {

							} catch (NullPointerException n) {

							}

						}

						private void for_time_spinner(int selected_index) {
							// TODO Auto-generated method stub
							int index = selected_index;
							System.out.println("selected index" + index);
							int length_times = 0;

							// Restaurant_Array = new String[length_Data];
							try {
								Global_variable.Time_From = new String[Global_variable.json_array_datetime
										.getJSONObject(index)
										.getJSONArray("times").length()];
								Global_variable.Time_To = new String[Global_variable.json_array_datetime
										.getJSONObject(index)
										.getJSONArray("times").length()];
								length_times = Global_variable.json_array_datetime
										.getJSONObject(index)
										.getJSONArray("times").length();
								System.out
										.println("length_Data" + length_times);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							for (int i = 0; i < length_times; i++) {
								try {

									Global_variable.Time_From[i] = Global_variable.json_array_datetime
											.getJSONObject(index)
											.getJSONArray("times")
											.getJSONObject(i).getString("from");
									Global_variable.Time_To[i] = Global_variable.json_array_datetime
											.getJSONObject(index)
											.getJSONArray("times")
											.getJSONObject(i).getString("to");

									System.out.println("Time_from"
											+ Global_variable.Time_From[i]);
									System.out.println("Time_to"
											+ Global_variable.Time_To[i]);

									// Retaurant_Array[i] =vtype.toString();
									// System.out.println("Retaurant_Array["+i+"]"+Retaurant_Array[i]);

								} catch (Exception ex) {
									System.out.println("Error" + ex);
								}
							}
							System.out.println("wjbty_Time_date"
									+ Global_variable.Time_From + " and to  "
									+ Global_variable.Time_To);

						}
					});

			/** Only one dialog show at a time */

			if (dialogShown) {
				return;
			} else {
				dialogShown = true;
				AlertDialog alert = builder.create();
				alert.setCanceledOnTouchOutside(false);
				alert.setCancelable(false);
				alert.show();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void time_spinner() {

		try {

			Builder builder = new AlertDialog.Builder(
					Contact_Details_Activity.this);
			builder.setTitle(R.string.contact_details_delivery_select_DATE);

			System.out.println("wjbty_city_array_0" + Global_variable.Time_To);
			str_previous_time = txv_selecttime.getText().toString();

			int index = -1;
			for (int i = 0; i < Global_variable.Time_To.length; i++) {
				if (Global_variable.Time_To[i].equals(str_previous_time)) {
					index = i;
					break;
				}
			}

			// previous_city_id=(String)Global_variable.hashmap_cities.get(str_previous_city);

			builder.setSingleChoiceItems(Global_variable.Time_To, index,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							selectedtime = which;
							// str_previous_city = str_select_city;
							str_selected_time = Global_variable.Time_To[which];
							Global_variable.str_Time_From = Global_variable.Time_From[which];
							Global_variable.str_Time_To = Global_variable.Time_To[which];
							System.out.println("Value of selected date"
									+ str_selected_time);
							int selected_time = -1;
							for (int i = 0; i < Global_variable.Time_To.length; i++) {
								if (Global_variable.Time_To[i]
										.equals(str_selected_time)) {
									selected_time = i;
									break;
								}
							}
							// for_time_spinner(selected_date);

							// city_id=(String)Global_variable.ha.get(str_selected_date);
							// System.out.println("City_id_value"+city_id);
							//
							try {

								if (str_selected_time != null) {
									txv_selecttime.setText(str_selected_time);
									System.out.println("textview_cityset"
											+ str_selected_time);
									dialogShown = false;
									dialog.dismiss();
								} else {
									txv_selecttime.setText(str_previous_time);
									dialogShown = false;
									dialog.dismiss();
								}
							} catch (Exception e) {

							}

						}

						// private void for_time_spinner(int selected_index) {
						// // TODO Auto-generated method stub
						// int index = selected_index;
						// System.out.println("selected index"+index);
						// int length_times = 0;
						//
						// //Restaurant_Array = new String[length_Data];
						// try {
						// Global_variable.Time_From = new
						// String[Global_variable.json_array_datetime.getJSONObject(index).getJSONArray("times").length()];
						// Global_variable.Time_To = new
						// String[Global_variable.json_array_datetime.getJSONObject(index).getJSONArray("times").length()];
						// length_times =
						// Global_variable.json_array_datetime.getJSONObject(index).getJSONArray("times").length();
						// System.out.println("length_Data" + length_times);
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						//
						//
						// for (int i = 0; i < length_times; i++) {
						// try {
						//
						// Global_variable.Time_From[i] =
						// Global_variable.json_array_datetime.getJSONObject(index).getJSONArray("times").getJSONObject(i).getString("from");
						// Global_variable.Time_To[i] =
						// Global_variable.json_array_datetime.getJSONObject(index).getJSONArray("times").getJSONObject(i).getString("to");
						//
						// System.out.println("Time_from"+
						// Global_variable.Time_From[i]);
						// System.out.println("Time_to"+
						// Global_variable.Time_To[i]);
						//
						// // Retaurant_Array[i] =vtype.toString();
						// //
						// System.out.println("Retaurant_Array["+i+"]"+Retaurant_Array[i]);
						//
						//
						// } catch (Exception ex) {
						// System.out.println("Error" + ex);
						// }
						// }
						// System.out.println("wjbty_Time_date"+
						// Global_variable.Time_From+" and to  "+Global_variable.Time_To);
						//
						//
						// }
					});

			/** Only one dialog show at a time */

			if (dialogShown) {
				return;
			} else {
				dialogShown = true;
				AlertDialog alert = builder.create();
				alert.setCanceledOnTouchOutside(false);
				alert.setCancelable(false);
				alert.show();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public class async_Step_Choose_Time extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = new ProgressDialog(
						Contact_Details_Activity.this);
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("async_Step_Choose_Time+on_pre");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			JSONObject Shipping_Request_Child = new JSONObject();

			System.out.println("async_Step_Choose_Time+on_doin");
			if (Global_variable.shipping_tag_delivery_ok == "1") // shipping
																	// information
			{
				try {

					if (Global_variable.shipping_tag_addr_type == "pre") {
						Shipping_Request_Child.put("delivery_ok",
								Global_variable.shipping_tag_delivery_ok);
						// System.out.println("fix_city_spinner"+fetch_spinner);
						Shipping_Request_Child.put("user_first_name",
								Global_variable.Str_FirstName);
						Shipping_Request_Child.put("user_last_name",
								Global_variable.Str_LastName);
						Shipping_Request_Child.put("user_email",
								Global_variable.Str_email);
						Shipping_Request_Child.put("user_contact_number",
								Global_variable.Str_Mobile);
						Shipping_Request_Child.put("user_comment",
								Global_variable.Str_Comment);
						Shipping_Request_Child.put("delivery_schedule_on",
								Global_variable.Str_Delivery_schedule_On);
						Shipping_Request_Child.put("addr_number",
								Global_variable.Str_addr_number); // response
																	// from pre
																	// saved
																	// result

						System.out.println("Shipping_Request_Child_pre"
								+ Shipping_Request_Child);
					} else if (Global_variable.shipping_tag_addr_type == "custom") {
						Shipping_Request_Child.put("delivery_ok",
								Global_variable.shipping_tag_delivery_ok);
						Shipping_Request_Child.put("house_number",
								Global_variable.Str_Houseno);
						Shipping_Request_Child.put("street",
								Global_variable.route);
						Shipping_Request_Child.put("city_id",
								Global_variable.Str_CityId);
						// Shipping_Request_Child.put("district_id",
						// Global_variable.Str_DistrictId);
						// Shipping_Request_Child.put("district",
						// Global_variable.Str_DistrictName);
						Shipping_Request_Child.put("lat",
								Global_variable.latitude);
						Shipping_Request_Child.put("long",
								Global_variable.longitude);
						Shipping_Request_Child
								.put("zoom", Global_variable.zoom);
						Shipping_Request_Child.put("zip",
								Global_variable.postal_code);
						Shipping_Request_Child.put("user_first_name",
								Global_variable.Str_FirstName);
						Shipping_Request_Child.put("user_last_name",
								Global_variable.Str_LastName);
						Shipping_Request_Child.put("user_email",
								Global_variable.Str_email);
						Shipping_Request_Child.put("user_contact_number",
								Global_variable.Str_Mobile);
						Shipping_Request_Child.put("user_comment",
								Global_variable.Str_Comment);
						Shipping_Request_Child.put("delivery_schedule_on",
								Global_variable.Str_Delivery_schedule_On);
						System.out.println("Shipping_Request_Child_custom"
								+ Shipping_Request_Child);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (Global_variable.shipping_tag_delivery_ok == "0") // I
																		// will
																		// be
																		// pick
																		// up
			{
				try {
					Shipping_Request_Child.put("delivery_ok",
							Global_variable.shipping_tag_delivery_ok);
					Shipping_Request_Child.put("house_number",
							Global_variable.Str_Houseno);
					Shipping_Request_Child.put("street", Global_variable.route);
					Shipping_Request_Child.put("city_id",
							Global_variable.Str_CityId);
					// Shipping_Request_Child.put("district_id",
					// Global_variable.Str_DistrictId);
					// Shipping_Request_Child.put("district",
					// Global_variable.Str_DistrictName);
					Shipping_Request_Child.put("lat", Global_variable.latitude);
					Shipping_Request_Child.put("long",
							Global_variable.longitude);
					Shipping_Request_Child.put("zoom", Global_variable.zoom);
					Shipping_Request_Child.put("zip",
							Global_variable.postal_code);
					Shipping_Request_Child.put("user_first_name",
							Global_variable.Str_FirstName);
					Shipping_Request_Child.put("user_last_name",
							Global_variable.Str_LastName);
					Shipping_Request_Child.put("user_email",
							Global_variable.Str_email);
					Shipping_Request_Child.put("user_contact_number",
							Global_variable.Str_Mobile);
					Shipping_Request_Child.put("user_comment",
							Global_variable.Str_Comment);
					Shipping_Request_Child.put("delivery_schedule_on",
							Global_variable.Str_Delivery_schedule_On);
					Shipping_Request_Child.put("addr_number",
							Global_variable.Str_addr_number);

					System.out.println("");
					System.out.println("Shipping_Request_Child_pickup"
							+ Shipping_Request_Child);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException nexc) {
					nexc.printStackTrace();
				}
			}

			JSONObject Shipping_Request_Main = new JSONObject();
			try {
				Shipping_Request_Main.put("rest_id", Global_variable.hotel_id);
				Shipping_Request_Main.put("addr_type",
						Global_variable.shipping_tag_addr_type);
				// System.out.println("fix_city_spinner"+fetch_spinner);
				Shipping_Request_Main.put("saveAddressmine", "1");
				Shipping_Request_Main.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				Shipping_Request_Main
				.put("user_id", SharedPreference
						.getuser_id(getApplicationContext()));
				Shipping_Request_Main.put("ShippingForm",
						Shipping_Request_Child);
				Shipping_Request_Main.put("cart", Global_variable.cart);
				Shipping_Request_Main.put("time_from",
						Global_variable.str_Time_From);
				Shipping_Request_Main.put("time_to",
						Global_variable.str_Time_To);
				Shipping_Request_Main.put("date", Global_variable.str_Date);

				System.out.println("Shipping_Request_Child_main"
						+ Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Shipping_Request_Child_Main"
					+ Shipping_Request_Main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_StepChooseTime_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						Shipping_Request_Main.toString(), "UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("async_Shipping_last_text"
							+ responseText);
					json = new JSONObject(responseText);
					System.out
							.println("Shipping_Request_Child_async_Shipping_last_json"
									+ json);

				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				} catch (NullPointerException npexc) {
					npexc.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				System.out
						.println("Step_choose_time_Shipping_last_json_in_post"
								+ json);
				JSONArray offers = new JSONArray();
				try {
					offers = json.getJSONObject("data").getJSONArray("offers");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				progressDialog.dismiss();
				// adapter_for_payment_offer = new
				// Adapter_for_Payment_Offers(Contact_Details_Activity.this,
				// offers);
				// Checkout_Tablayout.tab.setCurrentTab(2);
				if (Checkout_Tablayout.language.equals("ar")) {

					Checkout_Tablayout.tab.setCurrentTab(2);
				} else {

					Checkout_Tablayout.tab.setCurrentTab(2);
				}
				// Payment_Activity.lv_cart_details.setAdapter(adapter_for_payment_offer);

			} catch (NullPointerException e) {

			}

		}

	}

	public class async_GetCurrentUserDetails extends
			AsyncTask<Void, Void, Void> {
		JSONObject json;
		JSONObject data;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = new ProgressDialog(
						Contact_Details_Activity.this);
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			JSONObject GetCurrentUserDetails = new JSONObject();
			try {

				GetCurrentUserDetails.put("user_id",
						SharedPreference.getuser_id(getApplicationContext()));
				GetCurrentUserDetails.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));

				System.out.println("Shipping_Request_Child"
						+ GetCurrentUserDetails);

				System.out.println("Shipping_Request_Main"
						+ GetCurrentUserDetails);
				// *************
				// for request
				try {
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httppostreq = new HttpPost(
							Global_variable.rf_lang_Url
									+ Global_variable.rf_GetCurrentUserDetails_api_path);
					System.out.println("post_url" + httppostreq);
					StringEntity se = new StringEntity(
							GetCurrentUserDetails.toString(), "UTF-8");
					System.out.println("url_request" + se);
					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
							"application/json"));
					se.setContentType("application/json;charset=UTF-8");
					se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
							"application/json;charset=UTF-8"));
					httppostreq.setEntity(se);

					HttpResponse httpresponse = httpclient.execute(httppostreq);

					System.out.println("http_response" + httpresponse);
					String responseText = null;

					// ****** response text
					try {
						responseText = EntityUtils.toString(httpresponse
								.getEntity(), "UTF-8");
						responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
						System.out.println("Shipping_last_text" + responseText);
					} catch (ParseException e) {
						e.printStackTrace();
						Log.i("Parse Exception", e + "");
					}

					json = new JSONObject(responseText);
					System.out.println("Shipping_last_json" + json);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			data = new JSONObject();
			try {
				data = json.getJSONObject("data");
				String str_success = json.getString("success");
				if (str_success.equalsIgnoreCase("true")) {
					// String str_message = json.getString("message");
					Global_variable.str_User_FirstName = data
							.getString("user_first_name");
					Global_variable.str_User_LastName = data
							.getString("user_last_name");
					Global_variable.str_User_Email = data
							.getString("user_email");
					Global_variable.str_User_ContactNumber = data
							.getString("user_contact_number");
					ED_FirstName.setText(Global_variable.str_User_FirstName);
					ED_LastName.setText(Global_variable.str_User_LastName);
					ED_Email.setText(Global_variable.str_User_Email);
					ED_Mobile.setText(Global_variable.str_User_ContactNumber);
					
                    Global_variable.Country_code_array= new JSONArray();
					
					Global_variable.Country_code_array= json.getJSONArray("country");
					for(int i=0;i<Global_variable.Country_code_array.length();i++)
					{
						//JSONObject obj = new JSONObject();
						Global_variable.country_code=Global_variable.Country_code_array.getJSONObject(i).getString("country_call_code");
					}
					
					
					
				}
				else
				{
					
					JSONObject Data_Error = data.getJSONObject("errors");
					System.out.println("Data_Error" + Data_Error);
					if (Data_Error.has("user_id")) {
						JSONArray Array_user_id = Data_Error
								.getJSONArray("user_id");
						System.out.println("Array_user_id" + Array_user_id);
						String Str_user_id = Array_user_id.getString(0);
						System.out.println("Str_user_id" + Str_user_id);
						if (Str_user_id != null) {
							Toast.makeText(getApplicationContext(),
									Str_user_id, Toast.LENGTH_SHORT)
									.show();
						}

					} else if (Data_Error.has("sessid")) {
						JSONArray Array_sessid = Data_Error
								.getJSONArray("sessid");
						System.out.println("Array_sessid" + Array_sessid);
						String Str_sessid = Array_sessid.getString(0);
						System.out.println("Str_sessid" + Str_sessid);
						if (Str_sessid != null) {
							Toast.makeText(getApplicationContext(),
									Str_sessid, Toast.LENGTH_SHORT)
									.show();
						}
					} 
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {

			}

			progressDialog.dismiss();
		}

	}

	@SuppressLint("SimpleDateFormat")
	private void getCalenderView() {
		// TODO Auto-generated method stub

		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int selectedyear,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub

						year = selectedyear;
						month = monthOfYear;
						day = dayOfMonth;
						try {

							Date date = new SimpleDateFormat("yyyy-MM-dd")
									.parse(year + "-" + (month + 1) + "-" + day);

							DateFormat outputFormatter = new SimpleDateFormat(
									"yyyy-MM-dd");
							String selectedDate = outputFormatter.format(date); // Output
																				// :
																				// 01/20/2012

							System.out.println("!!!!!!selectedDate..."
									+ selectedDate);

							// Date currDate=new Date();

							

							System.out.println("!!!!!!!!!currDate." + currDate);
							System.out.println("output.compareTo(currDate).."
									+ selectedDate.compareTo(currDate
											.toString()));

							if (selectedDate.compareTo(currDate.toString()) >= 0
									&& selectedDate.compareTo(output
											.toString()) <= 0) {
								// then do your work
								// Display Selected date in textbox

								DateFormat outputFormatter1 = new SimpleDateFormat(
										"dd MMM, yyyy");
								String date_formating = outputFormatter1
										.format(date);

								System.out.println("!!!!!!!!!after_formating.."
										+ outputFormatter1 + "!!!!!"
										+ date_formating);

								txv_selectdate.setText(selectedDate);

								Global_variable.str_Date = selectedDate;

								// txv_selectdate.setText(year + "-" + (month +
								// 1)
								// + "-" + day);

							} else {
								// show message

								Toast.makeText(getApplicationContext(),
										"Invalid Date", Toast.LENGTH_SHORT)
										.show();

							}

						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, year, month, day);
		dpd.getDatePicker().setMinDate(c.getTimeInMillis());

		c.add(Calendar.MONTH, 1);

		dpd.getDatePicker().setMaxDate(c.getTimeInMillis());

		c.add(Calendar.MONTH, -1);

		System.out.println("!!!!!pankaj" + c.getTimeInMillis());
		// dpd.getDatePicker().setMaxDate(c.);
		
		dpd.show();
		dpd.setCancelable(false);
		dpd.setCanceledOnTouchOutside(false);

	}

	private void getTimeView() {
		// TODO Auto-generated method stub

		System.out.println("!!!!!!!!!!!!!In time method..");

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						
						System.out
						.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
								+ txv_selectdate.getText().toString());

				System.out
						.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
								+ currDate.toString());

				if (txv_selectdate.getText().toString()
						.equalsIgnoreCase(currDate.toString())) {
					c = Calendar.getInstance();
					int curr_hour = c.get(Calendar.HOUR_OF_DAY);
					int curr_minutes = c.get(Calendar.MINUTE);

					System.out
							.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
									+ curr_hour);
					System.out
							.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
									+ curr_minutes);

					System.out
							.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!hourOfDay<curr_hour"
									+ (hourOfDay < curr_hour));
					System.out
							.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!minute<curr_minutes"
									+ (minute < curr_minutes));

					if (hourOfDay < curr_hour
							&& minute < curr_minutes || hourOfDay<curr_hour && minute<=curr_minutes || hourOfDay==curr_hour && minute<curr_minutes) {
						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in if");
						Toast.makeText(getApplicationContext(),
								getString(R.string.str_Please_choose_valid_time),
								Toast.LENGTH_SHORT).show();
					} else {

						minutes = minute;
						hour = hourOfDay;

						String time1 = hour + ":" + minutes;
						
						//String db_time = hour + ":" + "00";
						
						System.out.println("!!!!!!!!!!!!!!!!!time..."+time1);
						
						Date time;
						try {
							time = new SimpleDateFormat("HH:mm")
							.parse(hour + ":" + minutes);
							
							DateFormat outputFormatter = new SimpleDateFormat(
									"HH:mm");
							String final_time = outputFormatter.format(time);
							
							System.out.println("!!!!!!!!!!!!!!!!!final_time..."+final_time);
							
							// Display Selected time in textbox
							txv_selecttime.setText(final_time);
							Global_variable.str_Time_From = final_time;
							Global_variable.str_Time_To = final_time;
							
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					minutes = minute;
					hour = hourOfDay;

					String time1 = hour + ":" + minutes;
					
					//String db_time = hour + ":" + "00";
					
					System.out.println("!!!!!!!!!!!!!!!!!time..."+time1);
					
					Date time;
					try {
						time = new SimpleDateFormat("HH:mm")
						.parse(hour + ":" + minutes);
						
						DateFormat outputFormatter = new SimpleDateFormat(
								"HH:mm");
						String final_time = outputFormatter.format(time);
						
						System.out.println("!!!!!!!!!!!!!!!!!final_time..."+final_time);
						
						// Display Selected time in textbox
						txv_selecttime.setText(final_time);
						Global_variable.str_Time_From = final_time;
						Global_variable.str_Time_To = final_time;
						
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
						
						

					}
				}, hour, minutes, false);
		tpd.show();
		tpd.setCancelable(false);
		tpd.setCanceledOnTouchOutside(false);

	}

	private void getIntialCalender() {
		// TODO Auto-generated method stub
		c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minutes = c.get(Calendar.MINUTE);
		// minutes = 00;
		second = c.get(Calendar.SECOND);
		
		
		currDate = new java.sql.Date(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date()); // Now use today date.
		c1.add(Calendar.DATE, 30); // Adding 30 days
		output = sdf.format(c1.getTime());
		System.out.println("!!!!!!!!!!!!!!!" + output);
		
		

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
