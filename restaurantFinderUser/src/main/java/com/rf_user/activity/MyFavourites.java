package com.rf_user.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.ListViewAdapter;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class MyFavourites extends Activity {

	ImageView rf_my_favourites_img_back_arrow, rf_my_fav_menu_icon;
	TextView rf_more_tv_information;
	ListView rf_fav_rest_list;

	String TAG_SUCCESS = "success";
	ListViewAdapter listview_adapter;
	ArrayList<String> hotel_categories;
	JSONArray array_categories;
	LinearLayout listviewlayout, ly_Not_Hotel;

	Intent in;
	
	/* Internet connection */
	ConnectionDetector cd;
	
	/* Language conversion */
	private Locale myLocale;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_my_favourites);
		try {
			/* create Object of internet connection* */
			cd = new ConnectionDetector(getApplicationContext());
			
			Global_variable.abt_rest_flag=false;

			//Global_variable.post_review_activity="";
			
			initializeWidgets();
			setlistner();

			if(cd.isConnectingToInternet())
			{
				new getFavRestaurants().execute();
			}
			else
			{
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

							new getFavRestaurants().execute();

						}
						// } while (cd.isConnectingToInternet() == false);

					}

				});
			}
			
			
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

		try{
			
			rf_my_fav_menu_icon
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(
								MyFavourites.this,
								rf_my_fav_menu_icon);
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyBooking.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyProfile.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											//Global_variable.activity = "Categories";

											Intent in = new Intent(
													getApplicationContext(),
													MyFavourites.class);
											startActivity(in);
										} 
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyNetworking.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											if (Global_variable.abt_rest_flag == true) {
												in = new Intent(
														getApplicationContext(),
														AboutRestaurant.class);
												startActivity(in);
											}

										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {

											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new UserLogout(
														MyFavourites.this)
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
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
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
			
		
		rf_fav_rest_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Global_variable.activity = "MyFavourites";

				if (Global_variable.previous_restaurant_selected_id != position) {
					Global_variable.cart = new JSONArray();
				}
				Global_variable.previous_restaurant_selected_id = position;

				Global_variable.hotel_iconurl = Global_variable.fav_hotel_listData
						.get(position).get("hotel_iconurl");
				Global_variable.hotel_id = Global_variable.fav_hotel_listData.get(
						position).get("hotel_id");
				
				Global_variable.hotel_name = Global_variable.fav_hotel_listData
						.get(position).get("hotel_name");

				
				Global_variable.user_fav = Global_variable.fav_hotel_listData
						.get(position).get("user_fav");
				
				Global_variable.hotel_map_lat = Double.parseDouble(Global_variable.fav_hotel_listData
						.get(position).get("hotel_map_lat"));
				
				Global_variable.hotel_map_long = Double.parseDouble(Global_variable.fav_hotel_listData
						.get(position).get("hotel_map_long"));
				

				Intent i = new Intent(MyFavourites.this, Categories.class);

				i.putExtra(
						"hotel_name",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_name"));
				i.putExtra(
						"hotel_address",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_address"));
				i.putExtra(
						"hotel_day",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_day"));
				i.putExtra(
						"hotel_iconurl",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_iconurl"));
				i.putExtra(
						"hotel_id",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_id"));
				i.putExtra(
						"hotel_rating",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_rating"));
				i.putExtra(
						"hotel_pick",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_pick"));
				i.putExtra(
						"hotel_minimum",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_minimum"));
				i.putExtra("hotel_delivery", Global_variable.fav_hotel_listData
						.get(position).get("hotel_delivery"));

				i.putExtra(
						"hotel_indine",
						Global_variable.fav_hotel_listData.get(position).get(
								"hotel_indine"));

				i.putExtra("position", position);

				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

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
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		rf_my_favourites_img_back_arrow = (ImageView) findViewById(R.id.rf_my_favourites_img_back_arrow);
		rf_more_tv_information = (TextView) findViewById(R.id.rf_more_tv_information);
		rf_my_fav_menu_icon = (ImageView) findViewById(R.id.rf_my_fav_menu_icon);
		rf_fav_rest_list = (ListView) findViewById(R.id.rf_fav_rest_list);

		// listviewlayout= (LinearLayout)findViewById(R.id.listviewlayout);
		ly_Not_Hotel = (LinearLayout) findViewById(R.id.ly_Not_Hotel);

	}

	public class getFavRestaurants extends AsyncTask<Void, Void, Void> {
		JSONObject data;

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

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				JSONObject fetch_hotel_detail = new JSONObject();

				try {
					if (SharedPreference
							.getuser_id(getApplicationContext()).length() == 0) {
						fetch_hotel_detail.put("user_id", "");
					} else {
						fetch_hotel_detail.put("user_id",
								SharedPreference
								.getuser_id(getApplicationContext()));
					}
					if (Global_variable.latitude + "" != null) {
						fetch_hotel_detail.put("lat",
								Global_variable.latitude);
					} else {
						fetch_hotel_detail.put("lat", "");
					}

					if (Global_variable.longitude + "" != null) {
						fetch_hotel_detail.put("long",
								Global_variable.longitude);
					} else {
						fetch_hotel_detail.put("long", "");
					}

					fetch_hotel_detail.put("sessid", SharedPreference.getsessid(getApplicationContext()));
					System.out.println("fetch_hotel_detail"
							+ fetch_hotel_detail);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_getFavRestaurants_api_path);
				System.out.println("hotel_url..." + httppostreq);
				StringEntity se = new StringEntity(
						fetch_hotel_detail.toString(),"UTF-8");
				System.out.println("hotel_list_url_request" + se.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String str_Hotel_list = null;

				// ****** response text
				try {
					str_Hotel_list = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					System.out.println("hotel_list_last_text" + str_Hotel_list);
					str_Hotel_list=str_Hotel_list.substring(str_Hotel_list.indexOf("{"), str_Hotel_list.lastIndexOf("}") + 1);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				}

				try {

					JSONObject json = new JSONObject(str_Hotel_list);
					System.out.println("hotel_list_last_json" + json);

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println("tag" + success1);

					String str_data = json.getString("data");
					System.out.println("Search_restaurant_str_data" + str_data);

					if (success1.equals("true")) {
						if (str_data != "[]") {
							data = json.getJSONObject("data");
							System.out
									.println("data_rsponse_categories_parameter"
											+ data);

							if (data != null) {
								JSONArray hotel_list_response_array = data
										.getJSONArray("restaurants");
								System.out
										.println("hotel_list_rsponse_get_parameter"
												+ hotel_list_response_array
														.toString());

								int lenth = hotel_list_response_array.length();
								// System.out.println("respose_array Value"+
								// response_array.keys().toString());
								System.out.println("hotel_list" + lenth);
								Global_variable.fav_hotel_listData = new ArrayList<HashMap<String, String>>();

								for (int i = 0; i <= lenth; i++) {
									try {
										JSONObject hotel_list_getvalue = hotel_list_response_array
												.getJSONObject(i);
										System.out
												.println("last_rsponse_hotel_list"
														+ hotel_list_getvalue);

										// Categories.add(array_categories.toString());

										String hotel_name = hotel_list_getvalue
												.getString("name");
										System.out.println("Vtype_hotel_n"
												+ hotel_name);
										String hotel_id = hotel_list_getvalue
												.getString("id");
										System.out.println("Vtype_hotelid"
												+ hotel_id);
										String hotel_address = hotel_list_getvalue
												.getString("address");
										System.out.println("Vtype_hoteladd"
												+ hotel_address);
										String hotel_minimum = hotel_list_getvalue
												.getString("min_order");
										System.out.println("Vtype_hotelmto"
												+ hotel_minimum);
										String hotel_iconurl = hotel_list_getvalue
												.getString("icon_url");
										System.out.println("Vtype_hotelicon"
												+ hotel_iconurl);
										String hotel_user_fav_rest = hotel_list_getvalue
												.getString("user_fav");
										
										String hotel_banner_image = hotel_list_getvalue
												.getString("banner_image");
										System.out.println("hotel_banner_image"
												+ hotel_banner_image);
										
										String hotel_distance = hotel_list_getvalue
												.getString("distance");
										System.out.println("hotel_distance"
												+ hotel_distance);
										
										String hotel_map_lat = hotel_list_getvalue
												.getString("map_lat");
										System.out.println("hotel_map_lat"
												+ hotel_map_lat);
										
										String hotel_map_long = hotel_list_getvalue
												.getString("map_long");
										System.out.println("hotel_map_long"
												+ hotel_map_long);
										
										
										array_categories = hotel_list_getvalue
												.getJSONArray("cats");
										System.out
												.println("categories_array_value"
														+ array_categories);

										/** CHETAN CATS **/
										hotel_categories = new ArrayList<String>();
										for (int j = 0; j < array_categories
												.length(); j++) {
											hotel_categories
													.add(array_categories
															.getString(j)
															.toString());

										}
										System.out
												.println("categories_arraylist"
														+ hotel_categories);
										String hotel_pick = hotel_list_getvalue
												.getString("pick");
										System.out.println("Vtype_hotepick"
												+ hotel_pick);
										String hotel_delivery = hotel_list_getvalue
												.getString("delivery");
										System.out.println("Vtype_hoteldel"
												+ hotel_delivery);

										String hotel_indine = hotel_list_getvalue
												.getString("indine");
										
										String hotel_cuisine = hotel_list_getvalue
												.getString("cuisine");
										System.out.println("hotel_cuisine"
												+ hotel_cuisine);

										String hotel_day = hotel_list_getvalue
												.getString("day");
										System.out.println("Vtype_hotelday"
												+ hotel_day);
										String hotel_rating = hotel_list_getvalue
												.getString("rating");
										System.out.println("Vtype_hotelday"
												+ hotel_rating);

										System.out.println("chetan");
										HashMap<String, String> map = new HashMap<String, String>();

										map.put("hotel_name", hotel_name);
										System.out.println("hotel_n" + map);
										map.put("hotel_address", hotel_address);
										System.out.println("hotel_a" + map);
										map.put("hotel_minimum", hotel_minimum);
										System.out.println("hotel_m" + map);
										map.put("hotel_day", hotel_day);
										System.out.println("hotel_d" + map);
										map.put("hotel_iconurl", hotel_iconurl);
										System.out.println("hotel_i" + map);
										
										map.put("hotel_banner_image",
												hotel_banner_image);
										System.out.println("hotel_i" + map);
										
										map.put("user_fav", hotel_user_fav_rest);
										
										map.put("hotel_map_lat", hotel_map_lat);
										
										map.put("hotel_map_long", hotel_map_long);
										
										map.put("hotel_distance",
												hotel_distance);
										System.out.println("hotel_i" + map);
										
										map.put("cuisine",hotel_cuisine);
										
										map.put("hotel_id", hotel_id);
										System.out.println("hotel_id" + map);
										map.put("hotel_rating", hotel_rating);
										System.out
												.println("hotel_rating" + map);
										map.put("hotel_pick", hotel_pick);
										System.out.println("hotel_pick" + map);
										map.put("hotel_delivery",
												hotel_delivery);
										System.out.println("hotel_delivery"
												+ map);

										map.put("hotel_indine", hotel_indine);
										System.out
												.println("hotel_indine" + map);

										map.put("hotel_categories",
												hotel_categories.toString());
										System.out.println("hotel_categories"
												+ map);
										Global_variable.fav_hotel_listData.add(map);

										System.out
												.println("value_before_clear"
														+ Global_variable.fav_hotel_listData);

										//

									} catch (Exception ex) {
										System.out.println("Error" + ex);
									}
								}
							}
						}
					} else {

					}

				} catch (JSONException e) {
					e.printStackTrace();

				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			} catch (NullPointerException e) {

			} catch (UnsupportedEncodingException e1) {
				// TODO: handle exception
				e1.printStackTrace();
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
						System.out.println("pankajsakariyadata" + data);
						if (data == (null)) {
							ly_Not_Hotel.setVisibility(View.VISIBLE);
							rf_fav_rest_list.setVisibility(View.GONE);
						}
						if (data == null) {
							if (Global_variable.fav_hotel_listData != null) {
								Global_variable.fav_hotel_listData.clear();
							}

							rf_fav_rest_list.invalidateViews();
							ly_Not_Hotel.setVisibility(View.VISIBLE);
							rf_fav_rest_list.setVisibility(View.GONE);
							System.out.println("pankajsakariyadata123");
						} else {
							ly_Not_Hotel.setVisibility(View.GONE);
							rf_fav_rest_list.setVisibility(View.VISIBLE);
							if (Global_variable.fav_hotel_listData != null) {
								listview_adapter = new ListViewAdapter(
										MyFavourites.this,
										Global_variable.fav_hotel_listData);
								System.out.println("pankaj_inside_hotel_list");
								if (listview_adapter != null) {
									rf_fav_rest_list
											.setAdapter(listview_adapter);
									// listview_adapter.notifyDataSetChanged();
									System.out
											.println("pankaj_inside_list_adapter");
									rf_fav_rest_list.invalidateViews();

								}

							} else {
								System.out.println("pankaj_inside_else");
								Global_variable.fav_hotel_listData.clear();
								listview_adapter = new ListViewAdapter(
										MyFavourites.this,
										Global_variable.fav_hotel_listData);
								rf_fav_rest_list.setAdapter(listview_adapter);

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
