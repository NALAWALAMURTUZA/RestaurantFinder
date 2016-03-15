package com.rf_user.activity;

import java.util.Arrays;
import java.util.Locale;

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
import android.os.StrictMode;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class AboutRestaurant extends Activity {

	TextView abt_rest_txt;

	private LinearLayout rf_about_us_header;
	private ImageView rf_about_us_back_arrow;
	private TextView rf_about_us_header_name;
	private ImageView rf_about_us_menu_icon;
	private LinearLayout rf_about_us_tital;
	private TextView rf_abt_rest_rest_name;
	private LinearLayout set_menu_linear;
	private TextView setmenu_txt;
	private TextView setmenu_starters_txt;
	private TextView setmenu_maincoarse_txt;
	private TextView setmenu_deserts_txt;
	private LinearLayout drinks_linear;
	private TextView drinks_txt;
	private TextView drinks_various_txt;
	private LinearLayout few_words_linear,ly_No_abt_rest;
	private TextView few_words_static_txt;
	private TextView few_words_comment_txt;
	private TextView comment_txt;
	private LinearLayout services_linear;
	private TextView lunch_timing_txt;
	private TextView dinner_timing_txt;
	private TextView services_txt;
	private TextView accepted_creditcards_txt, cuisine_txt;
	ScrollView abt_rest_details_scrollview;

	Intent in;

	String TAG_SUCCESS = "success", URL, rest_id, rest_name, activity;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	HttpConnection http = new HttpConnection();

	/*Language conversion*/
	private Locale myLocale;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_about_restaurant);

		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
		//	Global_variable.post_review_activity="";

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			in = getIntent();
			activity = in.getStringExtra("activity");
			if (activity != null) {
				if (activity.equalsIgnoreCase("Mybooking")) {
					rest_id = in.getStringExtra("rest_id");
					rest_name = in.getStringExtra("rest_name");
				}
			}

			Initialize();

			setlistner();

			runOnUiThread(new Runnable() {
				public void run() {

					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						// URL=Global_variable.rf_AboutRestaurant_api_path

						// if (Global_variable.login_user_id.length() != 0) {
						new async_AboutRestaurant().execute();
						// }

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
			
			
//			loadLocale();
			

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

	private void setlistner() {
		// TODO Auto-generated method stub

		try {

			rf_about_us_menu_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(AboutRestaurant.this,
								rf_about_us_menu_icon);
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
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {
												in = new Intent(
														getApplicationContext(),
														MyBooking.class);
												startActivity(in);
											} 
										}
										else {
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
												getString(R.string.my_profile))) {
									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {
												in = new Intent(
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
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
									}else {
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
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {

											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new UserLogout(
														AboutRestaurant.this)
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

	}

	private void Initialize() {
		// TODO Auto-generated method stub
		try {
			// abt_rest_txt = (TextView) findViewById(R.id.abt_rest_txt);
			rf_abt_rest_rest_name = (TextView) findViewById(R.id.rf_abt_rest_rest_name);

			if (activity != null) {
				rf_abt_rest_rest_name.setText(rest_name);

			} else {
				if (Global_variable.hotel_name != null) {
					if (Global_variable.hotel_name.length() != 0) {
						rf_abt_rest_rest_name
								.setText(Global_variable.hotel_name);
					} else {

					}
				}
			}

			rf_about_us_menu_icon = (ImageView) findViewById(R.id.rf_about_us_menu_icon);

			rf_about_us_header = (LinearLayout) findViewById(R.id.rf_about_us_header);
			rf_about_us_back_arrow = (ImageView) findViewById(R.id.rf_about_us_back_arrow);
			rf_about_us_header_name = (TextView) findViewById(R.id.rf_about_us_header_name);
			rf_about_us_menu_icon = (ImageView) findViewById(R.id.rf_about_us_menu_icon);
			rf_about_us_tital = (LinearLayout) findViewById(R.id.rf_about_us_tital);
			rf_abt_rest_rest_name = (TextView) findViewById(R.id.rf_abt_rest_rest_name);
			set_menu_linear = (LinearLayout) findViewById(R.id.set_menu_linear);
			setmenu_txt = (TextView) findViewById(R.id.setmenu_txt);
			setmenu_starters_txt = (TextView) findViewById(R.id.setmenu_starters_txt);
			setmenu_maincoarse_txt = (TextView) findViewById(R.id.setmenu_maincoarse_txt);
			setmenu_deserts_txt = (TextView) findViewById(R.id.setmenu_deserts_txt);
			drinks_linear = (LinearLayout) findViewById(R.id.drinks_linear);
			drinks_txt = (TextView) findViewById(R.id.drinks_txt);
			drinks_various_txt = (TextView) findViewById(R.id.drinks_various_txt);
			few_words_linear = (LinearLayout) findViewById(R.id.few_words_linear);
			few_words_static_txt = (TextView) findViewById(R.id.few_words_static_txt);
			few_words_comment_txt = (TextView) findViewById(R.id.few_words_comment_txt);
			comment_txt = (TextView) findViewById(R.id.comment_txt);
			services_linear = (LinearLayout) findViewById(R.id.services_linear);
			lunch_timing_txt = (TextView) findViewById(R.id.lunch_timing_txt);
			dinner_timing_txt = (TextView) findViewById(R.id.dinner_timing_txt);
			services_txt = (TextView) findViewById(R.id.services_txt);
			accepted_creditcards_txt = (TextView) findViewById(R.id.accepted_creditcards_txt);
			cuisine_txt = (TextView) findViewById(R.id.cuisine_txt);
			ly_No_abt_rest=(LinearLayout)findViewById(R.id.ly_No_abt_rest);
			abt_rest_details_scrollview=(ScrollView)findViewById(R.id.abt_rest_details_scrollview);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/* Fetch about restaurant details from db */

	public class async_AboutRestaurant extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		ProgressDialog dialog;
		String[] arr_services_name,arr_lunch_opening_days,arr_dinner_opening_days;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(AboutRestaurant.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();

		}

		/**
		 * Getting about restaurant details in background thread
		 * */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject obj_abtrest = new JSONObject();

						if (activity != null) {
							if (rest_id != null) {
								obj_abtrest.put("rest_id", rest_id);
							} else {
								obj_abtrest.put("rest_id", "");
							}

						} else {
							if (Global_variable.hotel_id != null) {
								obj_abtrest.put("rest_id",
										Global_variable.hotel_id);
							} else {
								obj_abtrest.put("rest_id", "");
							}

						}

						System.out.println("rest_id" + obj_abtrest);

						obj_abtrest.put("sessid", SharedPreference
								.getsessid(getApplicationContext()));
						System.out
								.println("final....obj_abtrest" + obj_abtrest);
						// *************

						String responseText = http
								.connection(AboutRestaurant.this,
												Global_variable.rf_AboutRestaurant_api_path,
										obj_abtrest);

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
			
			if(json!=null)
			{
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

							JSONArray services_arr = new JSONArray();

							services_arr = data.getJSONArray("services");

							arr_services_name = new String[services_arr.length()];

							for (int i = 0; i < services_arr.length(); i++) {
								JSONObject service_json = services_arr
										.getJSONObject(i);
								arr_services_name[i] = service_json
										.getString("service_name");
								String restaurant_id = service_json
										.getString("restaurant_id");
								String service_id = service_json
										.getString("service_id");

							}
							
							String arr_service = Arrays.toString(arr_services_name);

							System.out.println("!!!!!!!!!!!arr_service" + arr_service);

							System.out.println("in before splitting");
							String new_value_srvice = arr_service.replace("[", "");

							System.out.println("!!!!!!!!!new_value_srvice" + new_value_srvice);
							String final_services = new_value_srvice.replace("]", "");
							System.out.println("!!!!!!!!!final_services"
									+ final_services);
							

							JSONObject set_menu_json = data
									.getJSONObject("set_menu");

							String set_menu_id = set_menu_json
									.getString("set_menu_id");
							String set_menu_restaurant_id = set_menu_json
									.getString("set_menu_restaurant_id");

							String starter_1 = set_menu_json.getString("starter_1");
							String starter_price_1 = set_menu_json
									.getString("starter_price_1");
							String starter_2 = set_menu_json.getString("starter_2");
							String starter_price_2 = set_menu_json
									.getString("starter_price_2");
							String starter_3 = set_menu_json.getString("starter_3");
							String starter_price_3 = set_menu_json
									.getString("starter_price_3");
							String main_course_1 = set_menu_json
									.getString("main_course_1");
							String main_course_price_1 = set_menu_json
									.getString("main_course_price_1");
							String main_course_2 = set_menu_json
									.getString("main_course_2");
							String main_course_price_2 = set_menu_json
									.getString("main_course_price_2");

							String main_course_3 = set_menu_json
									.getString("main_course_3");
							String main_course_price_3 = set_menu_json
									.getString("main_course_price_3");
							String desserts_1 = set_menu_json
									.getString("desserts_1");
							String desserts_price_1 = set_menu_json
									.getString("desserts_price_1");
							String desserts_2 = set_menu_json
									.getString("desserts_2");
							String desserts_price_2 = set_menu_json
									.getString("desserts_price_2");
							String desserts_3 = set_menu_json
									.getString("desserts_3");
							String desserts_price_3 = set_menu_json
									.getString("desserts_price_3");

							JSONObject drinks_json = data.getJSONObject("drinks");

							String drinks_id = drinks_json.getString("drinks_id");
							String drinks_glass_wine_desc = drinks_json
									.getString("glass_wine_desc");

							String drinks_glass_wine_min_price = drinks_json
									.getString("glass_wine_min_price");
							String drinks_glass_wine_max_price = drinks_json
									.getString("glass_wine_max_price");
							String drinks_bottle_wine_desc = drinks_json
									.getString("bottle_wine_desc");
							String drinks_bottle_wine_min_price = drinks_json
									.getString("bottle_wine_min_price");
							String drinks_bottle_wine_max_price = drinks_json
									.getString("bottle_wine_max_price");
							String drinks_water_bottle_desc = drinks_json
									.getString("water_bottle_desc");
							String drinks_water_bottle_min_price = drinks_json
									.getString("water_bottle_min_price");
							String drinks_water_bottle_max_price = drinks_json
									.getString("water_bottle_max_price");
							String drinks_champagne_desc = drinks_json
									.getString("champagne_desc");
							String drinks_champagne_min_price = drinks_json
									.getString("champagne_min_price");

							String drinks_champagne_max_price = drinks_json
									.getString("champagne_max_price");
							String drinks_coffee_desc = drinks_json
									.getString("coffee_desc");
							String drinks_coffee_min_price = drinks_json
									.getString("coffee_min_price");
							String drinks_coffee_max_price = drinks_json
									.getString("coffee_max_price");

							JSONObject cuisine_json = data.getJSONObject("cuisine");

							String cuisine = cuisine_json.getString("cuisine");
							String comment = cuisine_json.getString("comment");

							String accepted_credit_cards = cuisine_json
									.getString("accepted_credit_cards");

							JSONObject lunch_timing_obj = data
									.getJSONObject("lunch_timing");
							
							JSONArray lunch_opening_days= new JSONArray();
							
							lunch_opening_days = lunch_timing_obj.getJSONArray("lunch_opening_days");

							arr_lunch_opening_days = new String[lunch_opening_days.length()];

							for (int i = 0; i < arr_lunch_opening_days.length; i++) {
								
								arr_lunch_opening_days[i] = lunch_opening_days.get(i).toString();
								

							}	
							
							String arr = Arrays.toString(arr_lunch_opening_days);

							System.out.println("!!!!!!!!!!!arr" + arr);

							System.out.println("in before splitting");
							String new_value = arr.replace("[", "");

							System.out.println("!!!!!!!!!new_value" + new_value);
							String final_lunch_opening_days = new_value.replace("]", "");
							System.out.println("!!!!!!!!!final_lunch_opening_days"
									+ final_lunch_opening_days);
							

							String lunch_open_time = lunch_timing_obj
									.getString("open_time");
							String lunch_close_time = lunch_timing_obj
									.getString("close_time");

							JSONObject dinner_timing_obj = data
									.getJSONObject("dinner_timing");
							
							
							
							JSONArray dinner_opening_days= new JSONArray();
							
							dinner_opening_days = dinner_timing_obj.getJSONArray("dinner_opening_days");

							arr_dinner_opening_days = new String[dinner_opening_days.length()];

							for (int i = 0; i < arr_dinner_opening_days.length; i++) {
								
								arr_dinner_opening_days[i] = dinner_opening_days.get(i).toString();
								

							}	
							
							String arr1 = Arrays.toString(arr_lunch_opening_days);

							System.out.println("!!!!!!!!!!!arr" + arr1);

							System.out.println("in before splitting");
							String new_value1 = arr1.replace("[", "");

							System.out.println("!!!!!!!!!new_value1" + new_value1);
							String final_dinner_opening_days = new_value1.replace("]", "");
							System.out.println("!!!!!!!!!final_dinner_opening_days"
									+ final_dinner_opening_days);

							String dinner_open_time = dinner_timing_obj
									.getString("open_time");
							String dinner_close_time = dinner_timing_obj
									.getString("close_time");

							

							String str_starters = "<b>" + getResources().getString(R.string.abr_Starters) + "</b>"
									+ "<br />" + starter_1
									+ Global_variable.multiline_space
									+ starter_price_1 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ starter_2 + Global_variable.multiline_space
									+ starter_price_2 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ starter_3 + Global_variable.multiline_space
									+ starter_price_3 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />";

							String str_maincoarse = "<b>" + getResources().getString(R.string.abr_Main_Coarse) + "</b>"
									+ "<br />" + main_course_1
									+ Global_variable.multiline_space
									+ main_course_price_1 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ main_course_2
									+ Global_variable.multiline_space
									+ main_course_price_2 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ main_course_3
									+ Global_variable.multiline_space
									+ main_course_price_3 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />";

							String str_deserts = "<b>" + getResources().getString(R.string.abr_Deserts) + "</b>"
									+ "<br />" + desserts_1
									+ Global_variable.multiline_space
									+ desserts_price_1 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ desserts_2 + Global_variable.multiline_space
									+ desserts_price_2 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ desserts_3 + Global_variable.multiline_space
									+ desserts_price_3 + Global_variable.blank
									+ Global_variable.Categories_sr + "<br />";

							String str_drinks = drinks_glass_wine_desc
									+ Global_variable.multiline_space
									+ drinks_glass_wine_min_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "-"
									// + Global_variable.multiline_space
									+ drinks_glass_wine_max_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "<br />"
									+ drinks_bottle_wine_desc
									+ Global_variable.multiline_space
									+ drinks_bottle_wine_min_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "-"
									// + Global_variable.multiline_space
									+ drinks_bottle_wine_max_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "<br />"
									+ drinks_water_bottle_desc
									+ Global_variable.multiline_space
									+ drinks_water_bottle_min_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "-"
									// + Global_variable.multiline_space
									+ drinks_water_bottle_max_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "<br />"
									+ drinks_champagne_desc
									+ Global_variable.multiline_space
									+ drinks_champagne_min_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "-"
									// + Global_variable.multiline_space
									+ drinks_champagne_max_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr + "<br />"
									+ drinks_coffee_desc
									+ Global_variable.multiline_space
									+ drinks_coffee_min_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr
									+ "-"
									// + Global_variable.multiline_space
									+ drinks_coffee_max_price
									+ Global_variable.blank
									+ Global_variable.Categories_sr + "<br />";

							String str_lunch_timing = "<b>" + getResources().getString(R.string.abr_Lunch_Timing)

							+ "</b>" + "<br />" + getResources().getString(R.string.abr_Opening_Days)
							+ "</b>"
									+ Global_variable.multiline_space
									+final_lunch_opening_days

									 + "<br />" + getResources().getString(R.string.abr_Opening_Time)
									+ Global_variable.multiline_space
									+ lunch_open_time + "<br />" + getResources().getString(R.string.abr_Closing_Time)
									+ Global_variable.multiline_space
									+ lunch_close_time + "<br />";

							String str_dinner_timing = "<b>" + getResources().getString(R.string.abr_Dinner_Timing)

							+ "</b>" + "<br />" + getResources().getString(R.string.abr_Opening_Days)
							+ "</b>"
							+ Global_variable.multiline_space
							+final_dinner_opening_days

									+ "</b>" + "<br />" + getResources().getString(R.string.abr_Opening_Time)
									+ Global_variable.multiline_space
									+ dinner_open_time + "<br />" + getResources().getString(R.string.abr_Closing_Time)
									+ Global_variable.multiline_space
									+ dinner_close_time + "<br />";

							if (starter_1 != null || starter_price_1 != null
									|| starter_2 != null || starter_price_2 != null
									|| starter_3 != null || starter_price_3 != null) {
								setmenu_starters_txt.setText(Html
										.fromHtml(str_starters));
							} else {
								setmenu_starters_txt.setVisibility(View.GONE);
							}

							if (main_course_1 != null
									|| main_course_price_1 != null
									|| main_course_2 != null
									|| main_course_price_2 != null
									|| main_course_3 != null
									|| main_course_price_3 != null) {
								setmenu_maincoarse_txt.setText(Html
										.fromHtml(str_maincoarse));
							} else {
								setmenu_maincoarse_txt.setVisibility(View.GONE);
							}

							if (desserts_1 != null || desserts_price_1 != null
									|| desserts_2 != null
									|| desserts_price_2 != null
									|| desserts_3 != null
									|| desserts_price_3 != null) {
								setmenu_deserts_txt.setText(Html
										.fromHtml(str_deserts));
							} else {
								setmenu_deserts_txt.setVisibility(View.GONE);
							}

							if (drinks_bottle_wine_desc != null
									|| drinks_bottle_wine_max_price != null
									|| drinks_bottle_wine_min_price != null
									|| drinks_champagne_desc != null
									|| drinks_champagne_max_price != null
									|| drinks_champagne_min_price != null
									|| drinks_coffee_desc != null
									|| drinks_coffee_max_price != null
									|| drinks_coffee_min_price != null
									|| drinks_glass_wine_desc != null
									|| drinks_glass_wine_max_price != null
									|| drinks_glass_wine_min_price != null
									|| drinks_water_bottle_desc != null
									|| drinks_water_bottle_max_price != null
									|| drinks_water_bottle_min_price != null) {
								drinks_various_txt.setText(Html
										.fromHtml(str_drinks));
							} else {
								drinks_linear.setVisibility(View.GONE);
							}

							if (comment != null) {
								if (comment.length() != 0) {
									few_words_comment_txt.setText(Html
											.fromHtml(comment));
								} else {
									few_words_linear.setVisibility(View.GONE);
								}

							} else {
								few_words_linear.setVisibility(View.GONE);
							}

							if (cuisine != null) {
								cuisine_txt.setText(Html
										.fromHtml("<b>" + getResources().getString(R.string.abr_Cuisine) + "</b>"
												+ Global_variable.multiline_space
												+ cuisine));
							} else {
								cuisine_txt.setVisibility(View.GONE);
							}

							if (lunch_open_time != null || lunch_close_time != null) {
								lunch_timing_txt.setText(Html
										.fromHtml(str_lunch_timing));
							} else {
								lunch_timing_txt.setVisibility(View.GONE);
							}

							if (dinner_open_time != null
									|| dinner_close_time != null) {
								dinner_timing_txt.setText(Html
										.fromHtml(str_dinner_timing));
							} else {
								dinner_timing_txt.setVisibility(View.GONE);
							}

							if (final_services != null) {
								services_txt.setText(Html.fromHtml("<b>"
										+ getResources().getString(R.string.abr_Services) + "</b>"
										+ Global_variable.multiline_space
										+ final_services));
							} else {
								services_txt.setVisibility(View.GONE);
							}

							if (accepted_credit_cards != null) {
								accepted_creditcards_txt.setText(Html
										.fromHtml("<b>" + getResources().getString(R.string.abr_Accepted_Credit_Cards)
												+ "</b>"
												+ Global_variable.multiline_space
												+ accepted_credit_cards));

							} else {
								accepted_creditcards_txt.setVisibility(View.GONE);
							}

							// Toast.makeText(getApplicationContext(),
							// "Fetch Detail Successful", Toast.LENGTH_LONG)
							// .show();
						}

					}

					// **** invalid output
					else {
						if (success1.equalsIgnoreCase("false")) {
							JSONObject Data_Error = data.getJSONObject("errors");
							System.out.println("Data_Error" + Data_Error);

							if (Data_Error.has("sessid")) {
								JSONArray Array_sessid = Data_Error
										.getJSONArray("sessid");
								System.out.println("Array_sessid" + Array_sessid);
								String Str_sessid = Array_sessid.getString(0);
								System.out.println("Str_sessid" + Str_sessid);
								if (Str_sessid != null) {
									Toast.makeText(getApplicationContext(),
											Str_sessid, Toast.LENGTH_LONG).show();
								}
							} else if (Data_Error.has("rest_id")) {
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

							else if (Data_Error.has("status")) {
								JSONArray Array_status = Data_Error
										.getJSONArray("status");
								System.out.println("Array_status" + Array_status);
								String Str_status = Array_status.getString(0);
								System.out.println("Str_rest_id" + Str_status);
								if (Str_status != null) {
									
									if(Str_status.equalsIgnoreCase("No Detail Found."))
									{
										abt_rest_details_scrollview.setVisibility(View.GONE);
										ly_No_abt_rest.setVisibility(View.VISIBLE);
										
									}
									
									// Toast.makeText(getApplicationContext(),
									// Str_status, Toast.LENGTH_LONG).show();

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

				if (dialog.isShowing()) {
					dialog.dismiss();
				}

			}
			else
			{
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				
				abt_rest_details_scrollview.setVisibility(View.GONE);
				ly_No_abt_rest.setVisibility(View.VISIBLE);
				
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
