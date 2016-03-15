package com.rf_user.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.Loyalty_Rewards_Adapter;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class LoyaltyRewardsScreen extends Activity {

	ListView loyalty_listview;
	LinearLayout main_linear, ly_No_loyalty_Booking;
	TextView rf_loyalty_screen_header_loyalty_icon;
	ScrollView loyalty_static_scrollview;
	ImageView rf_loyalty_screen_header_menu_icon;
	SeekBar seekBar;

	public Loyalty_Rewards_Adapter my_loyalty_booking_adapter;
	HttpConnection http = new HttpConnection();
	ArrayList<HashMap<String, String>> loyalty_listdata;

	Intent in;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	String TAG_SUCCESS = "success";
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_loyalty_rewards_screen);

		try {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
					+ SharedPreference.getuser_id(getApplicationContext())
					+ "!!!!!!"
					+ SharedPreference.getuser_id(getApplicationContext())
							.length());
			if (SharedPreference.getuser_id(getApplicationContext()) != "") {
				System.out.println("ishita....");

				try {

					/* create Object* */
					cd = new ConnectionDetector(getApplicationContext());

					initialize();
					setlistner();

					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							loyalty_static_scrollview.setVisibility(View.GONE);
							main_linear.setVisibility(View.VISIBLE);

							System.out
									.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
											+ SharedPreference
													.get_user_loyalty_pts(getApplicationContext()));

							if (SharedPreference
									.get_user_loyalty_pts(getApplicationContext()) != "") {
								try {
									rf_loyalty_screen_header_loyalty_icon
											.setText(SharedPreference
													.get_user_loyalty_pts(getApplicationContext())
													+ " points");
									seekBar.setMax(Global_variable.max_lp_to_redeem);
									seekBar.setProgress(Integer.parseInt(SharedPreference
											.get_user_loyalty_pts(getApplicationContext())));
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
							} else {
								rf_loyalty_screen_header_loyalty_icon
										.setText(getString(R.string.loyalty_txt));

								seekBar.setMax(Global_variable.max_lp_to_redeem);
								seekBar.setProgress(0);

							}
							
							seekBar.setEnabled(false);

							// seekBar.setOnSeekBarChangeListener(new
							// OnSeekBarChangeListener() {
							// int progress = 0;
							//
							// @Override
							// public void onProgressChanged(SeekBar seekBar,
							// int progresValue, boolean fromUser) {
							// progress = progresValue;
							// Toast.makeText(getApplicationContext(),
							// "Changing seekbar's progress",
							// Toast.LENGTH_SHORT).show();
							// }
							//
							// @Override
							// public void onStartTrackingTouch(SeekBar seekBar)
							// {
							// Toast.makeText(getApplicationContext(),
							// "Started tracking seekbar",
							// Toast.LENGTH_SHORT).show();
							// }
							//
							// @Override
							// public void onStopTrackingTouch(SeekBar seekBar)
							// {
							// // textView.setText("Covered: " + progress + "/"
							// + seekBar.getMax());
							// Toast.makeText(getApplicationContext(),
							// "Stopped tracking seekbar",
							// Toast.LENGTH_SHORT).show();
							// }
							// });

							/*
							 * Async to fetch user loyalty points details during
							 * tg order
							 */

							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {

								new async_my_loyalty_details_list().execute();

							} else {

								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// TODO Auto-generated method stub
										Toast.makeText(
												getApplicationContext(),
												R.string.No_Internet_Connection,
												Toast.LENGTH_LONG).show();

										// do {
										System.out.println("do-while");
										if (cd.isConnectingToInternet()) {

											new async_my_loyalty_details_list()
													.execute();

										}
										// } while (cd.isConnectingToInternet()
										// == false);

									}

								});
							}

						}
					} else {
						loyalty_static_scrollview.setVisibility(View.VISIBLE);
						main_linear.setVisibility(View.GONE);
					}

				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("ishita123....");
				loyalty_static_scrollview.setVisibility(View.VISIBLE);
				main_linear.setVisibility(View.GONE);
			}
			
			loadLocale();

		} catch (NullPointerException e) {
			// TODO: handle exception
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

		rf_loyalty_screen_header_menu_icon
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							PopupMenu popup = new PopupMenu(
									LoyaltyRewardsScreen.this,
									rf_loyalty_screen_header_menu_icon);
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
											if (SharedPreference
													.getuser_id(getApplicationContext()) != "") {
												if (SharedPreference
														.getuser_id(
																getApplicationContext())
														.length() != 0) {
													in = new Intent(
															getApplicationContext(),
															MyBooking.class);
													startActivity(in);
												}
											} else {
												Toast.makeText(
														getApplicationContext(),
														getString(R.string.str_please_login),
														Toast.LENGTH_SHORT)
														.show();

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
											if (SharedPreference
													.getuser_id(getApplicationContext()) != "") {
												if (SharedPreference
														.getuser_id(
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
														getString(R.string.str_please_login),
														Toast.LENGTH_SHORT)
														.show();

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
												if (SharedPreference
														.getuser_id(
																getApplicationContext())
														.length() != 0) {
													// Global_variable.activity
													// = "Search_Restaurant";

													Intent in = new Intent(
															getApplicationContext(),
															MyFavourites.class);
													startActivity(in);
												}
											} else {
												Toast.makeText(
														getApplicationContext(),
														getString(R.string.str_please_login),
														Toast.LENGTH_SHORT)
														.show();
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
												if (SharedPreference
														.getuser_id(
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
														getString(R.string.str_please_login),
														Toast.LENGTH_SHORT)
														.show();

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
												if (SharedPreference
														.getuser_id(
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
														getString(R.string.str_please_login),
														Toast.LENGTH_SHORT)
														.show();

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
												if (SharedPreference
														.getuser_id(
																getApplicationContext())
														.length() != 0) {

													/**
													 * check Internet
													 * Connectivity
													 */
													if (cd.isConnectingToInternet()) {

														new UserLogout(
																LoyaltyRewardsScreen.this)
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
														getString(R.string.str_please_login),
														Toast.LENGTH_SHORT)
														.show();

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

	}

	private void initialize() {
		// TODO Auto-generated method stub
		rf_loyalty_screen_header_loyalty_icon = (TextView) findViewById(R.id.rf_loyalty_screen_header_loyalty_icon);
		loyalty_listview = (ListView) findViewById(R.id.loyalty_listview);
		loyalty_static_scrollview = (ScrollView) findViewById(R.id.loyalty_static_scrollview);
		main_linear = (LinearLayout) findViewById(R.id.main_linear);
		ly_No_loyalty_Booking = (LinearLayout) findViewById(R.id.ly_No_loyalty_Booking);
		rf_loyalty_screen_header_menu_icon = (ImageView) findViewById(R.id.rf_loyalty_screen_header_menu_icon);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
	}

	public class async_my_loyalty_details_list extends
			AsyncTask<Void, Void, Void> {
		JSONObject data;
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("async_my_loyalty_details_list  Call");
			// Showing progress dialog

			// progressDialog = new ProgressDialog(MyBooking.this);
			// progressDialog.setCancelable(false);
			// progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject fetch_loyalty_booking_details = new JSONObject();

				try {
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							fetch_loyalty_booking_details
									.put("user_id",
											SharedPreference
													.getuser_id(getApplicationContext()));
							System.out.println("fetch_loyalty_booking_details"
									+ fetch_loyalty_booking_details);
						}
					} else {
						fetch_loyalty_booking_details.put("user_id", "");
					}

					fetch_loyalty_booking_details
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("fetch_booking_details"
							+ fetch_loyalty_booking_details);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// for request
				String responseText = http.connection(LoyaltyRewardsScreen.this,Global_variable.rf_GetMyLoyaltyPointsDetail,
						fetch_loyalty_booking_details);

				try {

					json = new JSONObject(responseText);

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println("tag" + success1);

					String str_data = json.getString("data");
					System.out.println("My_loyalty_bookingList_str_data"
							+ str_data);

					if (success1.equals("true")) {
						if (str_data != "[]") {
							data = json.getJSONObject("data");
							System.out.println("data" + data);

							if (data != null) {
								JSONArray loyalty_list_response_array = data
										.getJSONArray("loyalty_details");
								System.out
										.println("loyalty_list_response_array"
												+ loyalty_list_response_array
														.toString());

								int length = loyalty_list_response_array
										.length();
								// System.out.println("respose_array Value"+
								// response_array.keys().toString());
								System.out
										.println("loyalty_list_response_array_length"
												+ length);
								loyalty_listdata = new ArrayList<HashMap<String, String>>();

								for (int i = 0; i <= length; i++) {
									try {
										JSONObject loyalty_obj = loyalty_list_response_array
												.getJSONObject(i);
										System.out.println("loyalty_obj"
												+ loyalty_obj);

										String tg_order_id = loyalty_obj
												.getString("tg_order_id");
										System.out.println("tg_order_id"
												+ tg_order_id);
										String rest_id = loyalty_obj
												.getString("rest_id");
										System.out.println("rest_id" + rest_id);
										String user_id = loyalty_obj
												.getString("user_id");
										System.out.println("user_id" + user_id);

										String booking_date = loyalty_obj
												.getString("booking_date");
										System.out.println("booking_date"
												+ booking_date);
										String booking_time = loyalty_obj
												.getString("booking_time");
										System.out.println("booking_time"
												+ booking_time);

										String name_en = loyalty_obj
												.getString("name_en");
										System.out.println("name_en" + name_en);

										HashMap<String, String> map = new HashMap<String, String>();

										map.put("tg_order_id", tg_order_id);
										System.out.println("map" + map);
										map.put("rest_id", rest_id);
										System.out.println("map" + map);
										map.put("user_id", user_id);
										System.out.println("map" + map);

										map.put("booking_date", booking_date);
										System.out.println("map" + map);
										map.put("booking_time", booking_time);
										System.out.println("map" + map);

										map.put("name_en", name_en);
										System.out.println("map" + map);

										System.out.println("map" + map);
										loyalty_listdata.add(map);

										System.out
												.println("!!!!!In background..."
														+ loyalty_listdata);

										//

									} catch (Exception ex) {
										System.out.println("Error" + ex);
									}
								}
							}
						}
					} else {

					}

				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					e.printStackTrace();
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
				runOnUiThread(new Runnable() {
					public void run() {
						System.out.println("pankajsakariyadata" + data);
						if (data == (null)) {
							ly_No_loyalty_Booking.setVisibility(View.VISIBLE);
							loyalty_listview.setVisibility(View.GONE);
						}
						if (data == null) {
							if (loyalty_listdata != null) {
								loyalty_listdata.clear();
							}

							loyalty_listview.invalidateViews();
							ly_No_loyalty_Booking.setVisibility(View.VISIBLE);
							loyalty_listview.setVisibility(View.GONE);
							System.out.println("pankajsakariyadata123");
						} else {
							ly_No_loyalty_Booking.setVisibility(View.GONE);
							loyalty_listview.setVisibility(View.VISIBLE);
							if (loyalty_listdata != null) {
								my_loyalty_booking_adapter = new Loyalty_Rewards_Adapter(
										LoyaltyRewardsScreen.this,
										loyalty_listdata);
								System.out.println("pankaj_inside_hotel_list");
								if (my_loyalty_booking_adapter != null) {
									loyalty_listview
											.setAdapter(my_loyalty_booking_adapter);
									// listview_adapter.notifyDataSetChanged();
									System.out
											.println("pankaj_inside_list_adapter");
									loyalty_listview.invalidateViews();

								}

							} else {
								System.out.println("pankaj_inside_else");
								loyalty_listdata.clear();
								my_loyalty_booking_adapter = new Loyalty_Rewards_Adapter(
										LoyaltyRewardsScreen.this,
										loyalty_listdata);
								loyalty_listview
										.setAdapter(my_loyalty_booking_adapter);

								// listview_adapter.notifyDataSetChanged();
								// Hotel_list_listviw.invalidateViews();
							}

						}
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

}
