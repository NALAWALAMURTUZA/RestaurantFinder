package com.rf_user.activity;

import java.io.IOException;
import java.util.ArrayList;
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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.MyBookingAdapter;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class TableGraberBookings extends Activity {
	ListView rf_my_booking_list_tg;
	LinearLayout ly_No_tg_Booking;
	public MyBookingAdapter my_booking_adapter;
	ConnectionDetector cd;
	ProgressDialog progressDialog;
	String TAG_SUCCESS = "success";

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_table_graber_bookings);

		try {
			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			initializeWidgets();
			setlistner();

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new async_my_booking_list().execute();

				//

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								R.string.No_Internet_Connection,
								Toast.LENGTH_LONG).show();

						// do {
						System.out.println("do-while");
						if (cd.isConnectingToInternet()) {

							new async_my_booking_list().execute();

						}
						// } while (cd.isConnectingToInternet() == false);

					}

				});
			}
//			loadLocale();

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

	private void setlistner() {
		// TODO Auto-generated method stub

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		rf_my_booking_list_tg = (ListView) findViewById(R.id.rf_my_booking_list_tg);
		ly_No_tg_Booking = (LinearLayout) findViewById(R.id.ly_No_tg_Booking);

	}

	public class async_my_booking_list extends AsyncTask<Void, Void, Void> {
		JSONObject data;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("async_my_booking_list  Call");
			// Showing progress dialog

			progressDialog = new ProgressDialog(TableGraberBookings.this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage(getString(R.string.str_please_wait));
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject fetch_booking_details = new JSONObject();

				try {
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							fetch_booking_details
									.put("user_id",
											SharedPreference
													.getuser_id(getApplicationContext()));
							System.out.println("fetch_booking_details"
									+ fetch_booking_details);
						}
					} else {
						fetch_booking_details.put("user_id", "");
					}

					fetch_booking_details
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("fetch_booking_details"
							+ fetch_booking_details);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_GetMyBooking_api_path);
				System.out.println("hotel_url..." + httppostreq);
				StringEntity se = new StringEntity(
						fetch_booking_details.toString(), "UTF-8");
				System.out.println("hotel_list_url_request" + se.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String str_booking_list = null;

				JSONObject json = null;

				// ****** response text
				try {
					str_booking_list = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					str_booking_list=str_booking_list.substring(str_booking_list.indexOf("{"), str_booking_list.lastIndexOf("}") + 1);
					System.out.println("fetch_booking_details"
							+ str_booking_list);

					json = new JSONObject(str_booking_list);
					System.out.println("fetch_booking_details_response" + json);

				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				}

				if (json != null) {
					try {

						// json success tag
						String success1 = json.getString(TAG_SUCCESS);
						System.out.println("tag" + success1);

						String str_data = json.getString("data");
						System.out
								.println("My_bookingList_str_data" + str_data);

						Global_variable.booking_listData_tg_order = new ArrayList<HashMap<String, String>>();
						Global_variable.booking_listData_oo_order = new ArrayList<HashMap<String, String>>();

						if (success1.equals("true")) {
							if (str_data != "[]") {
								data = json.getJSONObject("data");

								Global_variable.booking_time_limit = data
										.getString("booking_time_limit");

								System.out
										.println("data_rsponse_categories_parameter"
												+ data);

								if (data != null) {
									JSONArray booking_list_response_array_tg = data
											.getJSONArray("tg_order");
									System.out
											.println("booking_list_response_array"
													+ booking_list_response_array_tg
															.toString());

									int tg_length = booking_list_response_array_tg
											.length();
									// System.out.println("respose_array Value"+
									// response_array.keys().toString());
									System.out.println("booking_list_tg_length"
											+ tg_length);

									JSONArray booking_list_response_array_oo = data
											.getJSONArray("oo_order");
									System.out
											.println("booking_list_response_array"
													+ booking_list_response_array_oo
															.toString());

									int oo_length = booking_list_response_array_oo
											.length();
									// System.out.println("respose_array Value"+
									// response_array.keys().toString());
									System.out.println("booking_list_oo_length"
											+ oo_length);

									if (tg_length != 0) {
										for (int i = 0; i < tg_length; i++) {
											try {
												JSONObject booking_obj_tg = booking_list_response_array_tg
														.getJSONObject(i);
												System.out
														.println("booking_obj"
																+ booking_obj_tg);

												String tg_order_id = booking_obj_tg
														.getString("tg_order_id");
												System.out
														.println("tg_order_id"
																+ tg_order_id);
												String rest_id = booking_obj_tg
														.getString("rest_id");
												System.out.println("rest_id"
														+ rest_id);
												String user_id = booking_obj_tg
														.getString("user_id");
												System.out.println("user_id"
														+ user_id);
												String booking_mode = booking_obj_tg
														.getString("booking_mode");
												System.out
														.println("booking_mode"
																+ booking_mode);
												String number_of_people = booking_obj_tg
														.getString("number_of_people");
												System.out
														.println("number_of_people"
																+ number_of_people);

												String booking_date = booking_obj_tg
														.getString("booking_date");
												System.out
														.println("booking_date"
																+ booking_date);
												String booking_time = booking_obj_tg
														.getString("booking_time");
												System.out
														.println("booking_time"
																+ booking_time);

												String booking_status = booking_obj_tg
														.getString("booking_status");
												System.out
														.println("booking_status"
																+ booking_status);

												String name_en = booking_obj_tg
														.getString("name_en");
												System.out.println("name_en"
														+ name_en);
												String country_id = booking_obj_tg
														.getString("country_id");
												System.out.println("country_id"
														+ country_id);

												String city_id = booking_obj_tg
														.getString("city_id");
												System.out.println("city_id"
														+ city_id);

												String cname_en = booking_obj_tg
														.getString("cname_en");
												System.out.println("cname_en"
														+ cname_en);

												String ciname_en = booking_obj_tg
														.getString("ciname_en");
												System.out.println("ciname_en"
														+ ciname_en);

												HashMap<String, String> map = new HashMap<String, String>();

												map.put("tg_order_id",
														tg_order_id);
												System.out.println("map" + map);
												map.put("rest_id", rest_id);
												System.out.println("map" + map);
												map.put("user_id", user_id);
												System.out.println("map" + map);
												map.put("booking_mode",
														booking_mode);
												System.out.println("map" + map);
												map.put("number_of_people",
														number_of_people);
												System.out.println("map" + map);
												map.put("booking_date",
														booking_date);
												System.out.println("map" + map);
												map.put("booking_time",
														booking_time);
												System.out.println("map" + map);
												map.put("booking_status",
														booking_status);
												System.out.println("map" + map);
												map.put("name_en", name_en);
												System.out.println("map" + map);

												map.put("country_id",
														country_id);
												System.out.println("map" + map);

												map.put("city_id", city_id);
												map.put("cname_en", cname_en);

												map.put("ciname_en", ciname_en);

												System.out.println("map" + map);
												Global_variable.booking_listData_tg_order
														.add(map);

											} catch (Exception ex) {
												System.out
														.println("Error" + ex);
											}
										}
										System.out
												.println("!!!!!In background Global_variable.booking_listData_tg_order..."
														+ Global_variable.booking_listData_tg_order);

									}

									if (oo_length != 0) {
										for (int i = 0; i < oo_length; i++) {
											try {
												JSONObject booking_obj_oo = booking_list_response_array_oo
														.getJSONObject(i);
												System.out
														.println("booking_obj_oo"
																+ booking_obj_oo);

												String tg_order_id = booking_obj_oo
														.getString("tg_order_id");
												System.out
														.println("tg_order_id"
																+ tg_order_id);

												String uid = booking_obj_oo
														.getString("uid");
												System.out.println("uid" + uid);

												String rest_id = booking_obj_oo
														.getString("rest_id");
												System.out.println("rest_id"
														+ rest_id);
												String user_id = booking_obj_oo
														.getString("user_id");
												System.out.println("user_id"
														+ user_id);

												String first_name = booking_obj_oo
														.getString("first_name");
												System.out.println("first_name"
														+ first_name);
												String last_name = booking_obj_oo
														.getString("last_name");
												System.out.println("last_name"
														+ last_name);

												String email = booking_obj_oo
														.getString("email");
												System.out.println("email"
														+ email);

												String contact_number = booking_obj_oo
														.getString("contact_number");
												System.out
														.println("contact_number"
																+ contact_number);

												String final_total = booking_obj_oo
														.getString("total");
												System.out
														.println("final_total"
																+ final_total);

												String delivery_charge = booking_obj_oo
														.getString("delivery_charge");
												System.out
														.println("delivery_charge"
																+ delivery_charge);

												String cart_id = booking_obj_oo
														.getString("cart_id");
												System.out.println("cart_id"
														+ cart_id);

												String booking_date = booking_obj_oo
														.getString("booking_date");
												System.out
														.println("booking_date"
																+ booking_date);
												String booking_time = booking_obj_oo
														.getString("booking_time");
												System.out
														.println("booking_time"
																+ booking_time);

												String booking_status = booking_obj_oo
														.getString("booking_status");
												System.out
														.println("booking_status"
																+ booking_status);

												String name_en = booking_obj_oo
														.getString("name_en");
												System.out.println("name_en"
														+ name_en);
												String country_id = booking_obj_oo
														.getString("country_id");
												System.out.println("country_id"
														+ country_id);

												String city_id = booking_obj_oo
														.getString("city_id");
												System.out.println("city_id"
														+ city_id);

												String cname_en = booking_obj_oo
														.getString("cname_en");
												System.out.println("cname_en"
														+ cname_en);

												String ciname_en = booking_obj_oo
														.getString("ciname_en");
												System.out.println("ciname_en"
														+ ciname_en);

												HashMap<String, String> map = new HashMap<String, String>();

												map.put("tg_order_id",
														tg_order_id);
												System.out.println("map" + map);

												map.put("uid", uid);
												System.out.println("map" + map);

												map.put("rest_id", rest_id);
												System.out.println("map" + map);
												map.put("user_id", user_id);
												System.out.println("map" + map);

												map.put("first_name",
														first_name);
												System.out.println("map" + map);
												map.put("last_name", last_name);
												System.out.println("map" + map);

												map.put("email", email);
												System.out.println("map" + map);
												map.put("contact_number",
														contact_number);
												System.out.println("map" + map);

												map.put("final_total",
														final_total);
												System.out.println("map" + map);
												map.put("delivery_charge",
														delivery_charge);
												System.out.println("map" + map);

												map.put("cart_id", cart_id);
												System.out.println("map" + map);

												map.put("booking_date",
														booking_date);
												System.out.println("map" + map);
												map.put("booking_time",
														booking_time);
												System.out.println("map" + map);
												map.put("booking_status",
														booking_status);
												System.out.println("map" + map);
												map.put("name_en", name_en);
												System.out.println("map" + map);

												map.put("country_id",
														country_id);
												System.out.println("map" + map);

												map.put("city_id", city_id);
												map.put("cname_en", cname_en);

												map.put("ciname_en", ciname_en);

												System.out.println("map" + map);
												Global_variable.booking_listData_oo_order
														.add(map);

												//

											} catch (Exception ex) {
												System.out
														.println("Error" + ex);
											}
										}

										System.out
												.println("!!!!!In background Global_variable.booking_listData_oo_order..."
														+ Global_variable.booking_listData_oo_order);

									}

								}
							}
						} else if (success1.equals("false"))  
						{

							data = json.getJSONObject("data");
							
						

							if (Global_variable.booking_listData_tg_order.size() == 0) 
							{
								
								if(ly_No_tg_Booking.VISIBLE == View.GONE)
								{
									ly_No_tg_Booking.setVisibility(View.VISIBLE);
									rf_my_booking_list_tg.setVisibility(View.GONE);
								}
								else
								{
									
								}
								if (Global_variable.booking_listData_tg_order != null) 
								{
									Global_variable.booking_listData_tg_order.clear();
								}
								if(!my_booking_adapter.isEmpty())
								{
									rf_my_booking_list_tg.invalidateViews();
								}
								
//								ly_No_tg_Booking.setVisibility(View.VISIBLE);
//								rf_my_booking_list_tg.setVisibility(View.GONE);
//								System.out.println("pankajsakariyadata123");
							}
							 else
							 {
								ly_No_tg_Booking.setVisibility(View.GONE);
								rf_my_booking_list_tg.setVisibility(View.VISIBLE);
								if (Global_variable.booking_listData_tg_order != null) {
									my_booking_adapter = new MyBookingAdapter(
											TableGraberBookings.this,
											Global_variable.booking_listData_tg_order,
											"TG");
									System.out
											.println("pankaj_inside_hotel_list");
									if (my_booking_adapter != null) {
										rf_my_booking_list_tg
												.setAdapter(my_booking_adapter);
										// listview_adapter.notifyDataSetChanged();
										System.out
												.println("pankaj_inside_list_adapter");
										rf_my_booking_list_tg.invalidateViews();

									}

								} else {
									System.out.println("pankaj_inside_else");
									Global_variable.booking_listData_tg_order
											.clear();
									my_booking_adapter = new MyBookingAdapter(
											TableGraberBookings.this,
											Global_variable.booking_listData_tg_order,
											"TG");
									rf_my_booking_list_tg
											.setAdapter(my_booking_adapter);

									// listview_adapter.notifyDataSetChanged();
									// Hotel_list_listviw.invalidateViews();
								}

							}

							Global_variable.booking_time_limit = data
									.getString("booking_time_limit");

						}

					} catch (NullPointerException ex) {
						ex.printStackTrace();
					} catch (IndexOutOfBoundsException e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.connection_timeout),
							Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException e) {
				e.printStackTrace();

			} catch (NullPointerException e) {
				e.printStackTrace();
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
				runOnUiThread(new Runnable() {
					public void run() {

						if (progressDialog.isShowing()) {
							progressDialog.dismiss();
						}

						System.out.println("TableGraberActivity"
								+ Global_variable.booking_listData_tg_order);

						if (Global_variable.booking_listData_tg_order.size() == 0) {
							ly_No_tg_Booking.setVisibility(View.VISIBLE);
							rf_my_booking_list_tg.setVisibility(View.GONE);
						}
						if (Global_variable.booking_listData_tg_order.size() == 0) {
							if (Global_variable.booking_listData_tg_order != null) {
								Global_variable.booking_listData_tg_order
										.clear();
							}

							rf_my_booking_list_tg.invalidateViews();
							ly_No_tg_Booking.setVisibility(View.VISIBLE);
							rf_my_booking_list_tg.setVisibility(View.GONE);
							System.out.println("pankajsakariyadata123");
						} else {
							ly_No_tg_Booking.setVisibility(View.GONE);
							rf_my_booking_list_tg.setVisibility(View.VISIBLE);
							if (Global_variable.booking_listData_tg_order != null) {
								my_booking_adapter = new MyBookingAdapter(
										TableGraberBookings.this,
										Global_variable.booking_listData_tg_order,
										"TG");
								System.out.println("pankaj_inside_hotel_list");
								if (my_booking_adapter != null) {
									rf_my_booking_list_tg
											.setAdapter(my_booking_adapter);
									// listview_adapter.notifyDataSetChanged();
									System.out
											.println("pankaj_inside_list_adapter");
									rf_my_booking_list_tg.invalidateViews();

								}

							} else {
								System.out.println("pankaj_inside_else");
								Global_variable.booking_listData_tg_order
										.clear();
								my_booking_adapter = new MyBookingAdapter(
										TableGraberBookings.this,
										Global_variable.booking_listData_tg_order,
										"TG");
								rf_my_booking_list_tg
										.setAdapter(my_booking_adapter);

								// listview_adapter.notifyDataSetChanged();
								// Hotel_list_listviw.invalidateViews();
							}

						}
						// System.out.println("pankajsakariyadata" + data);
						// if (data == (null)) {
						// ly_No_Booking.setVisibility(View.VISIBLE);
						// rf_my_booking_list.setVisibility(View.GONE);
						// }
						// if (data == null) {
						// if (Global_variable.booking_listData != null) {
						// Global_variable.booking_listData.clear();
						// }
						//
						// rf_my_booking_list.invalidateViews();
						// ly_No_Booking.setVisibility(View.VISIBLE);
						// rf_my_booking_list.setVisibility(View.GONE);
						// System.out.println("pankajsakariyadata123");
						// } else {
						// ly_No_Booking.setVisibility(View.GONE);
						// rf_my_booking_list.setVisibility(View.VISIBLE);
						// if (Global_variable.booking_listData != null) {
						// my_booking_adapter = new MyBookingAdapter(
						// MyBooking.this,
						// Global_variable.booking_listData);
						// System.out.println("pankaj_inside_hotel_list");
						// if (my_booking_adapter != null) {
						// rf_my_booking_list
						// .setAdapter(my_booking_adapter);
						// // listview_adapter.notifyDataSetChanged();
						// System.out
						// .println("pankaj_inside_list_adapter");
						// rf_my_booking_list.invalidateViews();
						//
						// }
						//
						// } else {
						// System.out.println("pankaj_inside_else");
						// Global_variable.booking_listData.clear();
						// my_booking_adapter = new MyBookingAdapter(
						// MyBooking.this,
						// Global_variable.booking_listData);
						// rf_my_booking_list
						// .setAdapter(my_booking_adapter);
						//
						// // listview_adapter.notifyDataSetChanged();
						// // Hotel_list_listviw.invalidateViews();
						// }
						//
						// }
					}
				});

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// if (progressDialog.isShowing()) {
			// progressDialog.dismiss();
			// }

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			System.out.println("!!!!!!!!!!!!!!!!!!!!!on back"
					+ Global_variable.activity);

			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {

		try {

			System.out.println("!!!!!!!!!!!!!!!!!!!!!on back"
					+ Global_variable.activity);
			
			
			try {
				if (Global_variable.activity == "PostReview") {
					

				} else {
					super.onBackPressed();

				}
			} catch (NullPointerException n) {
				n.printStackTrace();
			}

//			if (!Global_variable.activity.equalsIgnoreCase("null")
//					|| Global_variable.activity != "") {
//				if (Global_variable.activity.equalsIgnoreCase("PostReview")) {
//
//				} else {
//
//					// Global_variable.activity="";
//
//					super.onBackPressed();
//				}
//			} else {
//				// Global_variable.activity="";
//				super.onBackPressed();
//			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

}
