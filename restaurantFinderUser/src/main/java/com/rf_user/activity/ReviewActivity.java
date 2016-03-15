package com.rf_user.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.ReviewAdapter;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class ReviewActivity extends Activity {

	/* Xml file declaration */

	TextView txt_count_of_reviews, overall_review_rating;
	ListView review_list;
	ProgressDialog progressDialog;
	LinearLayout ly_No_reviews, review_layout;

	ImageView footer_viewmenu_img, footer_ordernow_img, header_search_icon,
			Back, footer_featured_img, footer_setting_img,
			rf_home_screen_header_menu_icon;

	ReviewAdapter review_adpater;

	Intent in;

	int position = 0;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	String TAG_SUCCESS = "success";

	HttpConnection http = new HttpConnection();
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_review);

		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			initialize();
			setlistner();

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new async_fetch_rest_review_list().execute();

				// Set a listener to be invoked when the list should be
				// refreshed.
				// ((PullToRefreshListView)
				// getListView()).setOnRefreshListener(new OnRefreshListener() {
				// @Override
				// public void onRefresh() {
				// // Do work to refresh the list here.
				// new async_fetch_rest_review_list().execute();
				// }
				// });

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								R.string.No_Internet_Connection,
								Toast.LENGTH_LONG).show();

						//do {
							System.out.println("do-while");
							if (cd.isConnectingToInternet()) {

								// Set a listener to be invoked when the list
								// should be refreshed.
								// ((PullToRefreshListView)
								// getListView()).setOnRefreshListener(new
								// OnRefreshListener() {
								// @Override
								// public void onRefresh() {
								// // Do work to refresh the list here.
								new async_fetch_rest_review_list().execute();
								// }
								// });

							}
						//} while (cd.isConnectingToInternet() == false);

					}

				});
			}
			
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

	private void setlistner() {
		// TODO Auto-generated method stub
		footer_setting_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference
							.getuser_id(getApplicationContext()).length() != 0) {
						Intent in = new Intent(getApplicationContext(),
								SettingsScreen.class);
						startActivity(in);
					} 
					}else {
						Toast.makeText(getApplicationContext(), getString(R.string.str_please_login),
								Toast.LENGTH_SHORT).show();
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		rf_home_screen_header_menu_icon
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							PopupMenu popup = new PopupMenu(
									ReviewActivity.this,
									rf_home_screen_header_menu_icon);
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
													.getuser_id(getApplicationContext())
													.length() != 0) {
												in = new Intent(
														getApplicationContext(),
														MyBooking.class);
												startActivity(in);
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
													.getuser_id(getApplicationContext())
													.length() != 0) {
												in = new Intent(
														getApplicationContext(),
														MyProfile.class);
												startActivity(in);
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
													.getuser_id(getApplicationContext())
													.length() != 0) {

												Intent in = new Intent(
														getApplicationContext(),
														MyFavourites.class);
												startActivity(in);
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
													.getuser_id(getApplicationContext())
													.length() != 0) {
												in = new Intent(
														getApplicationContext(),
														MyNetworking.class);
												startActivity(in);
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
													.getuser_id(getApplicationContext())
													.length() != 0) {
												if (Global_variable.abt_rest_flag == true) {
													in = new Intent(
															getApplicationContext(),
															AboutRestaurant.class);
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

									else if (item.getTitle().toString()
											.equals(getString(R.string.logout))) {

										try {
											if (SharedPreference
													.getuser_id(getApplicationContext())
													.length() != 0) {

												/** check Internet Connectivity */
												if (cd.isConnectingToInternet()) {

													new UserLogout(
															ReviewActivity.this)
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

		footer_ordernow_img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (Global_variable.cart.length() == 0) {
						Toast.makeText(ReviewActivity.this,
								R.string.empty_cart, Toast.LENGTH_SHORT).show();
					} else {
						Intent i = new Intent(ReviewActivity.this, Cart.class);
						startActivity(i);

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		footer_featured_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (SharedPreference
							.getuser_id(getApplicationContext()).length() != 0) {

						Intent in = new Intent(getApplicationContext(),
								MyFavourites.class);
						startActivity(in);
					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.str_please_login),
								Toast.LENGTH_SHORT).show();
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

		});

		header_search_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					Intent i = new Intent(ReviewActivity.this,
							Search_Restaurant_List.class);
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

			}
		});
	}

	private void initialize() {
		// TODO Auto-generated method stub

		ly_No_reviews = (LinearLayout) findViewById(R.id.ly_No_reviews);
		review_layout = (LinearLayout) findViewById(R.id.review_layout);
		txt_count_of_reviews = (TextView) findViewById(R.id.txt_count_of_reviews);
		overall_review_rating = (TextView) findViewById(R.id.overall_review_rating);
		review_list = (ListView) findViewById(R.id.review_list);
		footer_viewmenu_img = (ImageView) findViewById(R.id.footer_viewmenu_img);
		footer_ordernow_img = (ImageView) findViewById(R.id.footer_ordernow_img);
		footer_featured_img = (ImageView) findViewById(R.id.footer_featured_img);
		rf_home_screen_header_menu_icon = (ImageView) findViewById(R.id.rf_home_screen_header_menu_icon);
		footer_setting_img = (ImageView) findViewById(R.id.footer_setting_img);
		header_search_icon = (ImageView) findViewById(R.id.search_imageview);
	}

	public class async_fetch_rest_review_list extends
			AsyncTask<Void, Void, Void> {
		JSONObject data, json;
		String overall_rating, review_count;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("async_fetch_rest_review_list  Call");
			// Showing progress dialog

			progressDialog = new ProgressDialog(ReviewActivity.this);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject fetch_reviews = new JSONObject();

				try {
					if (Global_variable.hotel_id.length() != 0) {
						fetch_reviews.put("rest_id", Global_variable.hotel_id);
						System.out.println("fetch_reviews" + fetch_reviews);
					} else {
						fetch_reviews.put("rest_id", "");
					}

					fetch_reviews
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("fetch_reviews" + fetch_reviews);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				String responseText = http.connection(ReviewActivity.this,
						 Global_variable.rf_get_restaurant_review_api_path,
						fetch_reviews);

				try {

					System.out.println("after_connection.." + responseText);

					json = new JSONObject(responseText);
					System.out.println("responseText" + data);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}

				try {

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println("tag" + success1);

					String str_data = json.getString("data");
					System.out.println("My_ReviewList_str_data" + str_data);

					if (success1.equals("true")) {
						if (str_data != "[]") {
							data = json.getJSONObject("data");
							System.out
									.println("data_rsponse_categories_parameter"
											+ data);

							if (data != null) {

								overall_rating = data
										.getString("overall_rating");
								review_count = data.getString("review_count");

								JSONArray reviews_array = data
										.getJSONArray("reviews");
								System.out.println("reviews_array"
										+ reviews_array.toString());

								int length = reviews_array.length();
								// System.out.println("respose_array Value"+
								// response_array.keys().toString());
								System.out.println("reviews_array_length"
										+ length);
								Global_variable.reviews_listData = new ArrayList<HashMap<String, String>>();

								for (int i = 0; i <= length; i++) {
									try {
										JSONObject review_obj = reviews_array
												.getJSONObject(i);
										System.out.println("review_obj"
												+ review_obj);

										String tg_order_id = review_obj
												.getString("tg_order_id");
										System.out.println("tg_order_id"
												+ tg_order_id);
										String comment = review_obj
												.getString("comment");
										System.out.println("comment" + comment);
										String order_rating = review_obj
												.getString("order_rating");
										System.out.println("order_rating"
												+ order_rating);
										String firstname = review_obj
												.getString("firstname");
										System.out.println("firstname"
												+ firstname);
										String lastname = review_obj
												.getString("lastname");
										System.out.println("lastname"
												+ lastname);

										HashMap<String, String> map = new HashMap<String, String>();

										map.put("tg_order_id", tg_order_id);
										System.out.println("map" + map);
										map.put("comment", comment);
										System.out.println("map" + map);
										map.put("order_rating", order_rating);
										System.out.println("map" + map);
										map.put("firstname", firstname);
										System.out.println("map" + map);
										map.put("lastname", lastname);
										System.out.println("map" + map);

										System.out.println("map" + map);
										Global_variable.reviews_listData
												.add(map);

										System.out
												.println("!!!!!In background..."
														+ Global_variable.reviews_listData);

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

			if (overall_rating != null) {
				overall_review_rating.setText(overall_rating + getString(R.string.str_static_ratings));

			} else {
				overall_review_rating.setText(getString(R.string.str_ratings));
			}

			if (review_count != null) {
				txt_count_of_reviews.setText(getString(R.string.str_according_to) + review_count
						+ " reviews");
			} else {
				txt_count_of_reviews.setText(getString(R.string.str_acc_to_0_ratings));
			}

			// Call onRefreshComplete when the list has been refreshed.
			// ((PullToRefreshListView) getListView()).onRefreshComplete();

			try {
				runOnUiThread(new Runnable() {
					public void run() {
						System.out.println("pankajsakariyadata" + data);
						if (data == (null)) {
							ly_No_reviews.setVisibility(View.VISIBLE);
							review_layout.setVisibility(View.GONE);
						}
						if (data == null) {
							if (Global_variable.reviews_listData != null) {
								Global_variable.reviews_listData.clear();
							}

							review_list.invalidateViews();
							ly_No_reviews.setVisibility(View.VISIBLE);
							review_layout.setVisibility(View.GONE);
							System.out.println("pankajsakariyadata123");
						} else {
							ly_No_reviews.setVisibility(View.GONE);
							review_list.setVisibility(View.VISIBLE);
							if (Global_variable.reviews_listData != null) {
								review_adpater = new ReviewAdapter(
										ReviewActivity.this,
										Global_variable.reviews_listData);
								System.out.println("pankaj_inside_hotel_list");
								if (review_adpater != null) {
									review_list.setAdapter(review_adpater);
									// listview_adapter.notifyDataSetChanged();
									System.out
											.println("pankaj_inside_list_adapter");
									review_list.invalidateViews();

								}

							} else {
								System.out.println("pankaj_inside_else");
								Global_variable.reviews_listData.clear();
								review_adpater = new ReviewAdapter(
										ReviewActivity.this,
										Global_variable.reviews_listData);
								review_list.setAdapter(review_adpater);

							}

						}
					}
				});

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
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
