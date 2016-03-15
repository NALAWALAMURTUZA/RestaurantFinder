package com.rf_user.activity;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.MyBookingAdapter;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class MyBooking extends TabActivity {

	ListView rf_my_booking_list;
	ImageView rf_boooking_menu_icon,rf_my_booking_img_back_arrow;
	LinearLayout ly_No_Booking;
	ProgressDialog progressDialog;
	public MyBookingAdapter my_booking_adapter;
	HttpConnection http = new HttpConnection();

	Intent in;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	String TAG_SUCCESS = "success";

	/* Tabhost declaration */
	public static TabHost tab;
	public static Resources res;
	public static TabSpec step1spec, step2spec;
	public static Intent intent1, intent2;

	public static LinearLayout order_tblayout_header;

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_my_booking);
		try {
			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			initialize();
			setlistner();

			tab = getTabHost();
			tab.getTabWidget().setClickable(false);
			tab.setClickable(false);

			// Tab for Step 1
			step1spec = tab.newTabSpec(getString(R.string.Table_Graber));
			// setting Title and Icon for the Tab
			step1spec.setIndicator(getString(R.string.Table_Graber));
			// step1spec.setIndicator("Booking Confirmation");
			intent1 = new Intent(getApplicationContext(),
					TableGraberBookings.class);
			step1spec.setContent(intent1);

			// Tab for Step 2
			step2spec = tab.newTabSpec(getString(R.string.Online_Order));
			step2spec.setIndicator(getString(R.string.Online_Order));
			// step2spec.setIndicator("Invitation");
			intent2 = new Intent(getApplicationContext(),
					OnlineOrderBookings.class);
			step2spec.setContent(intent2);

			// Adding all TabSpec to TabHost
			tab.addTab(step1spec); // Adding step 1 tab
			tab.addTab(step2spec); // Adding step 2 tab

			tab.getTabWidget()
					.getChildTabViewAt(0)
					.setBackgroundResource(
							R.drawable.icon_booking_confirmation_tab);
			tab.getTabWidget()
					.getChildTabViewAt(1)
					.setBackgroundResource(
							R.drawable.icon_booking_confirmation_tab);

			TextView tv = (TextView) tab.getTabWidget().getChildAt(0)
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.WHITE);
			// tv.setTextSize(12);
			// center text
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			// wrap text
			tv.setSingleLine(false);

			// explicitly set layout parameters
			// tv.getLayoutParams().height =
			// ViewGroup.LayoutParams.MATCH_PARENT;
			// tv.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

			TextView tv1 = (TextView) tab.getTabWidget().getChildAt(1)
					.findViewById(android.R.id.title);
			tv1.setTextColor(Color.WHITE);
			// tv1.setTextSize(12);
			// center text
			tv1.setGravity(Gravity.CENTER_HORIZONTAL);
			// wrap text
			tv1.setSingleLine(false);

			// explicitly set layout parameters
			// tv1.getLayoutParams().height =
			// ViewGroup.LayoutParams.MATCH_PARENT;
			// tv1.getLayoutParams().width =
			// ViewGroup.LayoutParams.MATCH_PARENT;

			tab.setCurrentTab(0);
			// tab.getTabWidget().getChildAt(1).setClickable(false);

//			loadLocale();

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

	private void setlistner() {
		// TODO Auto-generated method stub

		try {

			rf_boooking_menu_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(MyBooking.this,
								rf_boooking_menu_icon);
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

//									try {
//										if (SharedPreference
//												.getuser_id(getApplicationContext()) != "") {
//											if (SharedPreference.getuser_id(
//													getApplicationContext())
//													.length() != 0) {
//												in = new Intent(
//														getApplicationContext(),
//														MyBooking.class);
//												startActivity(in);
//											}
//										} else {
//											Toast.makeText(
//													getApplicationContext(),
//													R.string.please_login,
//													Toast.LENGTH_SHORT).show();
//
//										}
//									} catch (NullPointerException n) {
//										n.printStackTrace();
//									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_profile))) {
									try {
										if (SharedPreference
												.getuser_id(getApplicationContext()) != "") {
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {
												in = new Intent(
														getApplicationContext(),
														MyProfile.class);
												startActivity(in);
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

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_favourites))) {

									try {
										if (SharedPreference
												.getuser_id(getApplicationContext()) != "") {
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {
												// Global_variable.activity =
												// "Categories";

												Intent in = new Intent(
														getApplicationContext(),
														MyFavourites.class);
												startActivity(in);
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

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_networking))) {

									try {
										if (SharedPreference
												.getuser_id(getApplicationContext()) != "") {
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {
												in = new Intent(
														getApplicationContext(),
														MyNetworking.class);
												startActivity(in);
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

								else if (item
										.getTitle()
										.toString()
										.equals(getString(R.string.about_restaurant))) {

									try {
										if (SharedPreference
												.getuser_id(getApplicationContext()) != "") {
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {
												if (Global_variable.abt_rest_flag == true) {
													in = new Intent(
															getApplicationContext(),
															AboutRestaurant.class);
													startActivity(in);
												}

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

								else if (item.getTitle().toString()
										.equals(getString(R.string.logout))) {

									try {
										if (SharedPreference
												.getuser_id(getApplicationContext()) != "") {
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {

												/** check Internet Connectivity */
												if (cd.isConnectingToInternet()) {

													new UserLogout(
															MyBooking.this)
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
		rf_my_booking_img_back_arrow.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					 Intent in = new Intent(MyBooking.this,
					 Search_Restaurant_List.class);
					 startActivity(in);
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		// TODO Auto-generated method stub

		// ly_No_Booking = (LinearLayout) findViewById(R.id.ly_No_Booking);
		// rf_my_booking_list = (ListView)
		// findViewById(R.id.rf_my_booking_list);
		rf_boooking_menu_icon = (ImageView) findViewById(R.id.rf_boooking_menu_icon);
		rf_my_booking_img_back_arrow = (ImageView) findViewById(R.id.rf_my_booking_img_back_arrow);

	}

	public class async_cancel_order extends AsyncTask<Void, Void, Void> {
		JSONObject json, data;

		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("async_cancel_order  Call");
			// Showing progress dialog

			progressDialog = new ProgressDialog(MyBooking.this);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject cancel_order = new JSONObject();

				try {
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						cancel_order.put("user_id", SharedPreference
								.getuser_id(getApplicationContext()));
						System.out.println("cancel_order" + cancel_order);
					} else {
						cancel_order.put("user_id", "");
					}

					if (Global_variable.tg_order_id.length() != 0) {
						cancel_order.put("tg_order_id",
								Global_variable.tg_order_id);
						System.out.println("cancel_order" + cancel_order);
					} else {
						cancel_order.put("tg_order_id", "");
					}
					if (Global_variable.order_type.equalsIgnoreCase("TG")) {

						cancel_order.put("booking_status", "8");
					} else {

						cancel_order.put("booking_status", "CancelUser");
					}

					if (Global_variable.order_type != null) {
						cancel_order.put("type", Global_variable.order_type);
					}

					cancel_order
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("cancel_order" + cancel_order);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// for request
				/* Http Request */

				try {
					String responseText = http.connection(MyBooking.this,
							 Global_variable.rf_cancel_order_api_path,
							cancel_order);

					json = new JSONObject(responseText);
					System.out.println("last_json" + json);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}

			} catch (JSONException e) {
				e.printStackTrace();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}

				try {

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println("tag" + success1);

					String str_data = json.getString("data");
					System.out.println("Cancel_order_str_data" + str_data);

					if (success1.equals("true")) {
						if (str_data != "[]") {
							data = json.getJSONObject("data");
							System.out
									.println("data_rsponse_categories_parameter"
											+ data);

							if (data != null) {

								String message = data.getString("message");

								// MyBooking booking = new MyBooking();
								// new async_my_booking_list().execute();

								// Intent in = new Intent(activity,
								// MyBooking.class);
								// activity.startActivity(in);

								// MyBooking my_booking = new MyBooking();
								// my_booking.new async_my_booking_list();
								// notifyDataSetChanged();

							}
						}
					} else {

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
										Str_user_id, Toast.LENGTH_LONG).show();
							}
						} else if (Data_Error.has("tg_order_id")) {
							JSONArray Array_tg_order_id = Data_Error
									.getJSONArray("tg_order_id");
							System.out.println("Array_tg_order_id"
									+ Array_tg_order_id);
							String Str_tg_order_id = Array_tg_order_id
									.getString(0);
							System.out.println("Str_tg_order_id"
									+ Str_tg_order_id);
							if (Str_tg_order_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_tg_order_id, Toast.LENGTH_LONG)
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
										Str_sessid, Toast.LENGTH_LONG).show();
							}
						}

					}

				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

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
