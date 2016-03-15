package com.example.restaurantadmin;

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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.food_detail.restaurant_presentation_credit_cards;
import com.restaurantadmin.food_detail.restaurant_presentation_services;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class RestaurantPrasentationActivity extends Activity {
	public static ImageView img_home;
	public static CheckBox rp_ch_launch_monday;
	public static CheckBox rp_ch_launch_tuesday;
	public static CheckBox rp_ch_launch_wednesday;
	public static CheckBox rp_ch_launch_thursday;
	public static CheckBox rp_ch_launch_friday;
	public static CheckBox rp_ch_launch_saturday;
	public static CheckBox rp_ch_launch_sunday;

	public static CheckBox rp_ch_dinner_monday;
	public static CheckBox rp_ch_dinner_tuesday;
	public static CheckBox rp_ch_dinner_wednesday;
	public static CheckBox rp_ch_dinner_thursday;
	public static CheckBox rp_ch_dinner_friday;
	public static CheckBox rp_ch_dinner_saturday;
	public static CheckBox rp_ch_dinner_sunday;

	public static Spinner rp_sp_cuisine;
	public static ListView rp_lv_service;
	public static ListView rp_lv_cards;
	public static Button rp_btn_confirme;
	public static Button rp_btn_cancel;
	public static ProgressDialog p;
	public static ArrayAdapter<CharSequence> adapter_launch_opening;
	public static ArrayAdapter<CharSequence> adapter_launch_closing;
	public static ArrayAdapter<CharSequence> adapter_dinner_opening;
	public static ArrayAdapter<CharSequence> adapter_dinner_closing;
	public static ArrayAdapter<String> adapter_sp_cuisine;
	ConnectionDetector cd;
	restaurant_presentation_services restaurant_services;
	restaurant_presentation_credit_cards restaurant_credit_cards;
	private static String selected_cuisine = null;
	/**
	 * Find the Views in the layout<br />
	 * <br />
	 * Auto-created on 2015-05-01 16:37:00 by Android Layout Finder
	 * (http://www.w3tutorialschool.com/tools/android-layout-finder)
	 */

	// Opening time is always less then closing time
	public static String str_lunch_opening, str_lunch_close;
	public static String str_dinner_opening, str_dinner_close;
	public static int int_lunch_opening, int_lunch_close;
	public static int int_dinner_opening, int_dinner_close;
	public static Spinner rp_sp_launch_opening_time;
	public static Spinner rp_sp_launch_last_booking;
	public static Spinner rp_sp_dinner_opening_time;
	public static Spinner rp_sp_dinner_last_booking;

	private Locale myLocale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(RestaurantPrasentationActivity.this);
		setContentView(R.layout.activity_restaurant_prasentation);
		cd = new ConnectionDetector(getApplicationContext());
		
		Initizlization();
		setListener();

		

		if (Global_variable.logindata != null) {

			System.out.println("hi");
			try {
				if (selected_cuisine != null) {
					System.out.println("selected_cuisine" + selected_cuisine);
					int indexavailable = RestaurantPrasentationActivity.adapter_sp_cuisine
							.getPosition(selected_cuisine);
					Log.i(getString(R.string.str_indexCapacity),
							getString(R.string.str_indexCapacity)
									+ indexavailable);
					RestaurantPrasentationActivity.rp_sp_cuisine
							.setSelection(indexavailable);
				}

				if (Global_variable.logindata.getJSONObject("lunch") != null) {
					System.out.println("hi123"
							+ Global_variable.logindata.getJSONObject("lunch")
									.getString("monday"));
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("monday").toString()
							.equalsIgnoreCase("1")) {
						System.out.println("hi1234");
						rp_ch_launch_monday.setChecked(true);
					} else {
						rp_ch_launch_monday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("tuesday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_launch_tuesday.setChecked(true);
					} else {
						rp_ch_launch_tuesday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("wednesday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_launch_wednesday.setChecked(true);
					} else {
						rp_ch_launch_wednesday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("thursday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_launch_thursday.setChecked(true);
					} else {
						rp_ch_launch_thursday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("friday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_launch_friday.setChecked(true);
					} else {
						rp_ch_launch_friday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("saturday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_launch_saturday.setChecked(true);
					} else {
						rp_ch_launch_saturday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("sunday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_launch_sunday.setChecked(true);
					} else {
						rp_ch_launch_sunday.setChecked(false);

					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("open_time").toString() != "") {
						int indexavailable = RestaurantPrasentationActivity.adapter_launch_opening
								.getPosition(Global_variable.logindata
										.getJSONObject("lunch")
										.getString("open_time")
										.toString());
						Log.i(getString(R.string.str_indexCapacity),
								getString(R.string.str_indexCapacity)
										+ indexavailable);
						RestaurantPrasentationActivity.rp_sp_launch_opening_time
								.setSelection(indexavailable);
					} else {
							
					}
					if (Global_variable.logindata.getJSONObject("lunch")
							.getString("close_time").toString() != "") {
						int indexavailable = RestaurantPrasentationActivity.adapter_launch_closing
								.getPosition(Global_variable.logindata
										.getJSONObject("lunch")
										.getString("close_time")
										.toString());
						Log.i(getString(R.string.str_indexCapacity),
								getString(R.string.str_indexCapacity)
										+ indexavailable);
						RestaurantPrasentationActivity.rp_sp_launch_last_booking
								.setSelection(indexavailable);
					} else {

					}
				}
				System.out.println("dinnerinrestpresant1"+Global_variable.logindata.getJSONObject("dinner"));
				if (Global_variable.logindata.getJSONObject("dinner") != null) {
					System.out.println("dinnerinrestpresant10");
					System.out.println("dinnerinrestpresant2"+Global_variable.logindata.getJSONObject("dinner"));
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("monday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_monday.setChecked(true);
					} else {
						rp_ch_dinner_monday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("tuesday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_tuesday.setChecked(true);
					} else {
						rp_ch_dinner_tuesday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("wednesday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_wednesday.setChecked(true);
					} else {
						rp_ch_dinner_wednesday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("thursday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_thursday.setChecked(true);
					} else {
						rp_ch_dinner_thursday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("friday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_friday.setChecked(true);
					} else {
						rp_ch_dinner_friday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("saturday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_saturday.setChecked(true);
					} else {
						rp_ch_dinner_saturday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("sunday").toString()
							.equalsIgnoreCase("1")) {
						rp_ch_dinner_sunday.setChecked(true);
					} else {
						rp_ch_dinner_sunday.setChecked(false);
					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("open_time").toString() != "") {
						int indexavailable = RestaurantPrasentationActivity.adapter_dinner_opening
								.getPosition(Global_variable.logindata
										.getJSONObject("dinner")
										.getString("open_time")
										.toString());
						Log.i(getString(R.string.str_indexCapacity),
								getString(R.string.str_indexCapacity)
										+ indexavailable);
						RestaurantPrasentationActivity.rp_sp_dinner_opening_time
								.setSelection(indexavailable);
					} else {

					}
					if (Global_variable.logindata.getJSONObject("dinner")
							.getString("close_time").toString() != "") {
						int indexavailable = RestaurantPrasentationActivity.adapter_dinner_closing
								.getPosition(Global_variable.logindata
										.getJSONObject("dinner")
										.getString("close_time")
										.toString());
						Log.i(getString(R.string.str_indexCapacity),
								getString(R.string.str_indexCapacity)
										+ indexavailable);
						RestaurantPrasentationActivity.rp_sp_dinner_last_booking
								.setSelection(indexavailable);
					} else {

					}
			
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				restaurant_services = new restaurant_presentation_services(
						RestaurantPrasentationActivity.this,
						Global_variable.array_Services);
				rp_lv_service.setAdapter(restaurant_services);
				restaurant_credit_cards = new restaurant_presentation_credit_cards(
						RestaurantPrasentationActivity.this,
						Global_variable.array_payment_method);
				rp_lv_cards.setAdapter(restaurant_credit_cards);
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	
		
//		loadLocale();

		// language*****************
	}
	@Override
	public void onResume() {
		System.out.println("murtuza_nalawala");
		super.onResume(); // Always call the superclass method first
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}
	@Override
	protected void onStop() {
		super.onStop();
	
	}
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	// language***************
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

	// language***************
//	@Override
//	public void onResume() {
//		System.out.println("murtuza_nalawala");
//		super.onResume(); // Always call the superclass method first
//	}
	private void setListener() {
		// TODO Auto-generated method stub
		// *********changes
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(RestaurantPrasentationActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		rp_sp_dinner_opening_time
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_dinner_opening = "";
							String str_d_opening = rp_sp_dinner_opening_time
									.getSelectedItem().toString();

							str_dinner_opening = str_d_opening.substring(0, 2);

							System.out.println("str_dinner_opening"
									+ str_dinner_opening);
							int_dinner_opening = Integer
									.parseInt(str_dinner_opening);
							System.out.println("int_dinner_opening"
									+ int_dinner_opening);
							// *************
							str_dinner_close = "";
							String str_d_close = rp_sp_dinner_last_booking
									.getSelectedItem().toString();
							str_dinner_close = str_d_close.substring(0, 2);
							System.out.println("str_dinner_close"
									+ str_dinner_close);
							int_dinner_close = Integer
									.parseInt(str_dinner_close);
							System.out.println("int_dinner_close"
									+ int_dinner_close);
							if (int_dinner_opening < int_dinner_close) {

							} else {
								System.out.println("ifloop>");
								Toast.makeText(
										RestaurantPrasentationActivity.this,
										R.string.str_dinner_opening,
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		rp_sp_dinner_last_booking
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_dinner_opening = "";
							String str_d_opening_booking = rp_sp_dinner_opening_time
									.getSelectedItem().toString();

							str_dinner_opening = str_d_opening_booking
									.substring(0, 2);
							System.out.println("str_dinner_opening1"
									+ str_dinner_opening);
							int_dinner_opening = Integer
									.parseInt(str_dinner_opening);
							System.out.println("int_dinner_opening1"
									+ int_dinner_opening);
							// *************
							str_dinner_close = "";
							String str_d_close_time = rp_sp_dinner_last_booking
									.getSelectedItem().toString();

							str_dinner_close = str_d_close_time.substring(0, 2);
							System.out.println("str_dinner_close1"
									+ str_dinner_close);
							int_dinner_close = Integer
									.parseInt(str_dinner_close);
							System.out.println("int_dinner_close1"
									+ int_dinner_close);
							if (int_dinner_opening < int_dinner_close) {

							} else {
								System.out.println("ifloop<");
								Toast.makeText(
										RestaurantPrasentationActivity.this,
										R.string.str_dinner_opening,
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		rp_sp_launch_opening_time
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						try {
							str_lunch_opening = "";
							String str_c_opening = rp_sp_launch_opening_time
									.getSelectedItem().toString();

							str_lunch_opening = str_c_opening.substring(0, 2);

							System.out.println("str_launch_opening"
									+ str_lunch_opening);
							int_lunch_opening = Integer
									.parseInt(str_lunch_opening);
							System.out.println("int_launch_opening"
									+ int_lunch_opening);
							// *************
							str_lunch_close = "";
							String str_c_close = rp_sp_launch_last_booking
									.getSelectedItem().toString();
							str_lunch_close = str_c_close.substring(0, 2);
							System.out.println("str_launch_close"
									+ str_lunch_close);
							int_lunch_close = Integer.parseInt(str_lunch_close);
							System.out.println("int_launch_close"
									+ int_lunch_close);
							if (int_lunch_opening < int_lunch_close) {

							} else {
								System.out.println("ifloop>");
								Toast.makeText(
										RestaurantPrasentationActivity.this,
										R.string.str_Lunch_opening,
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		rp_sp_launch_last_booking
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_lunch_opening = "";
							String str_c_opening = rp_sp_launch_opening_time
									.getSelectedItem().toString();

							str_lunch_opening = str_c_opening.substring(0, 2);

							System.out.println("str_launch_opening"
									+ str_lunch_opening);
							int_lunch_opening = Integer
									.parseInt(str_lunch_opening);
							System.out.println("int_launch_opening"
									+ int_lunch_opening);
							// *************
							str_lunch_close = "";
							String str_c_close = rp_sp_launch_last_booking
									.getSelectedItem().toString();
							str_lunch_close = str_c_close.substring(0, 2);
							System.out.println("str_launch_close"
									+ str_lunch_close);
							int_lunch_close = Integer.parseInt(str_lunch_close);
							System.out.println("int_launch_close"
									+ int_lunch_close);
							if (int_lunch_opening < int_lunch_close) {

							} else {
								System.out.println("ifloop>");
								Toast.makeText(
										RestaurantPrasentationActivity.this,
										R.string.str_Lunch_opening,
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		// *************************
		rp_btn_confirme.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Boolean flag_call = true;
				if (restaurant_presentation_services.obj_services.length() == 0) {
					Toast.makeText(getApplicationContext(),
							R.string.str_select_one_service, Toast.LENGTH_SHORT)
							.show();
					flag_call = false;

				}
				if (restaurant_presentation_credit_cards.selected_paymentmethod
						.size() == 0) {
					flag_call = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_select_one_payment, Toast.LENGTH_SHORT)
							.show();
				}

				if (flag_call == true) {
					if (cd.isConnectingToInternet()) {
						if (int_lunch_opening >= int_lunch_close) {
							Toast.makeText(RestaurantPrasentationActivity.this,
									R.string.str_Lunch_opening,
									Toast.LENGTH_LONG).show();
						} else if (int_dinner_opening >= int_dinner_close) {
							Toast.makeText(RestaurantPrasentationActivity.this,
									R.string.str_dinner_opening,
									Toast.LENGTH_LONG).show();
						} else {
							new update_restaurant_Presemtation().execute();
						}

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

			}
		});
		rp_btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Intent i = new Intent(RestaurantPrasentationActivity.this,
						Home_Activity.class);
				startActivity(i);
			}
		});

	}
	private void Initizlization() {
		img_home = (ImageView) findViewById(R.id.img_home);
		rp_ch_launch_monday = (CheckBox) findViewById(R.id.rp_ch_launch_monday);
		rp_ch_launch_tuesday = (CheckBox) findViewById(R.id.rp_ch_launch_tuesday);
		rp_ch_launch_wednesday = (CheckBox) findViewById(R.id.rp_ch_launch_wednesday);
		rp_ch_launch_thursday = (CheckBox) findViewById(R.id.rp_ch_launch_thursday);
		rp_ch_launch_friday = (CheckBox) findViewById(R.id.rp_ch_launch_friday);
		rp_ch_launch_saturday = (CheckBox) findViewById(R.id.rp_ch_launch_saturday);
		rp_ch_launch_sunday = (CheckBox) findViewById(R.id.rp_ch_launch_sunday);
		rp_sp_launch_opening_time = (Spinner) findViewById(R.id.rp_sp_launch_opening_time);
		rp_sp_launch_last_booking = (Spinner) findViewById(R.id.rp_sp_launch_last_booking);
		rp_ch_dinner_monday = (CheckBox) findViewById(R.id.rp_ch_dinner_monday);
		rp_ch_dinner_tuesday = (CheckBox) findViewById(R.id.rp_ch_dinner_tuesday);
		rp_ch_dinner_wednesday = (CheckBox) findViewById(R.id.rp_ch_dinner_wednesday);
		rp_ch_dinner_thursday = (CheckBox) findViewById(R.id.rp_ch_dinner_thursday);
		rp_ch_dinner_friday = (CheckBox) findViewById(R.id.rp_ch_dinner_friday);
		rp_ch_dinner_saturday = (CheckBox) findViewById(R.id.rp_ch_dinner_saturday);
		rp_ch_dinner_sunday = (CheckBox) findViewById(R.id.rp_ch_dinner_sunday);
		rp_sp_dinner_opening_time = (Spinner) findViewById(R.id.rp_sp_dinner_opening_time);
		rp_sp_dinner_last_booking = (Spinner) findViewById(R.id.rp_sp_dinner_last_booking);
		rp_sp_cuisine = (Spinner) findViewById(R.id.rp_sp_cuisine);
		rp_lv_service = (ListView) findViewById(R.id.rp_lv_service);
		rp_lv_cards = (ListView) findViewById(R.id.rp_lv_cards);
		rp_btn_confirme = (Button) findViewById(R.id.rp_btn_confirme);
		rp_btn_cancel = (Button) findViewById(R.id.rp_btn_cancel);
		// rp_btn_cancel=(Button) findViewById(R.id.rp_btn_cancel);

		adapter_launch_opening = ArrayAdapter.createFromResource(
				RestaurantPrasentationActivity.this,
				R.array.array_launch_openingtime,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_launch_opening
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter_launch_closing = ArrayAdapter.createFromResource(
				RestaurantPrasentationActivity.this,
				R.array.array_launch_openingtime,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_launch_opening
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter_dinner_opening = ArrayAdapter.createFromResource(
				RestaurantPrasentationActivity.this,
				R.array.array_launch_openingtime,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_launch_opening
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter_dinner_closing = ArrayAdapter.createFromResource(
				RestaurantPrasentationActivity.this,
				R.array.array_launch_openingtime,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_launch_opening
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayList<String> stringArrayList = new ArrayList<String>();
		for (int i = 0; i < Global_variable.array_restaurantcategory.length(); i++) {
			try {
				stringArrayList.add(Global_variable.array_restaurantcategory
						.getJSONObject(i).getString("name_en").toString());
				System.out.println("catagory"
						+ Global_variable.array_restaurantcategory
								.getJSONObject(i).getString("id"));
				System.out.println("selected"
						+ Global_variable.logindata
								.getJSONObject("restaurant_details")
								.getString("cuisine").toString());
				if ((Global_variable.array_restaurantcategory.getJSONObject(i)
						.getString("id")
						.equalsIgnoreCase(Global_variable.logindata
								.getJSONObject("restaurant_details")
								.getString("cuisine").toString()))) { // System.out.println("namecatagoryes"+Global_variable.array_restaurantcategory.getJSONObject(i).getString("id"));
					selected_cuisine = Global_variable.array_restaurantcategory
							.getJSONObject(i).getString("name_en");
					// System.out.println("selected_cuisine"+selected_cuisine);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // add to arraylist
		}
		adapter_sp_cuisine = new ArrayAdapter<String>(
				RestaurantPrasentationActivity.this,
				android.R.layout.simple_spinner_dropdown_item, stringArrayList);
		rp_sp_cuisine.setAdapter(adapter_sp_cuisine);
		
	}

	/* AsyncTask */
	public class update_restaurant_Presemtation
			extends
				AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		protected void onPreExecute() {
			super.onPreExecute();
			p = new ProgressDialog(RestaurantPrasentationActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			JSONObject obj_main = new JSONObject();
			JSONObject obj_restaurant_schedule = new JSONObject();
			JSONObject obj_lunch = new JSONObject();
			JSONObject obj_dinner = new JSONObject();
			try {
				// obj_parent.put("restaurant_id", "50");
				obj_main.put("restaurant_id", Global_variable.restaurant_id);
				obj_main.put("sessid", Global_variable.sessid);
				if (rp_ch_launch_monday.isChecked()) {
					obj_lunch.put("monday", "1");
				} else {
					obj_lunch.put("monday", "0");
				}
				if (rp_ch_launch_tuesday.isChecked()) {
					obj_lunch.put("tuesday", "1");
				} else {
					obj_lunch.put("tuesday", "0");
				}
				if (rp_ch_launch_wednesday.isChecked()) {
					obj_lunch.put("wednesday", "1");
				} else {
					obj_lunch.put("wednesday", "0");
				}
				if (rp_ch_launch_thursday.isChecked()) {
					obj_lunch.put("thursday", "1");
				} else {
					obj_lunch.put("thursday", "0");
				}
				if (rp_ch_launch_friday.isChecked()) {
					obj_lunch.put("friday", "1");
				} else {
					obj_lunch.put("friday", "0");
				}
				if (rp_ch_launch_saturday.isChecked()) {
					obj_lunch.put("saturday", "1");
				} else {
					obj_lunch.put("saturday", "0");
				}
				if (rp_ch_launch_sunday.isChecked()) {
					obj_lunch.put("sunday", "1");
				} else {
					obj_lunch.put("sunday", "0");
				}
				if (rp_ch_dinner_monday.isChecked()) {
					obj_dinner.put("monday", "1");
				} else {
					obj_dinner.put("monday", "0");
				}
				if (rp_ch_dinner_tuesday.isChecked()) {
					obj_dinner.put("tuesday", "1");
				} else {
					obj_dinner.put("tuesday", "0");
				}
				if (rp_ch_dinner_wednesday.isChecked()) {
					obj_dinner.put("wednesday", "1");
				} else {
					obj_dinner.put("wednesday", "0");
				}
				if (rp_ch_dinner_thursday.isChecked()) {
					obj_dinner.put("thursday", "1");
				} else {
					obj_dinner.put("thursday", "0");
				}
				if (rp_ch_dinner_friday.isChecked()) {
					obj_dinner.put("friday", "1");
				} else {
					obj_dinner.put("friday", "0");
				}
				if (rp_ch_dinner_saturday.isChecked()) {
					obj_dinner.put("saturday", "1");
				} else {
					obj_dinner.put("saturday", "0");
				}
				if (rp_ch_dinner_sunday.isChecked()) {
					obj_dinner.put("sunday", "1");
				} else {
					obj_dinner.put("sunday", "0");
				}
				obj_lunch.put("open_time", rp_sp_launch_opening_time
						.getSelectedItem().toString());
				obj_lunch.put("last_booking_time_general",
						rp_sp_launch_last_booking.getSelectedItem().toString());
				obj_dinner.put("open_time", rp_sp_dinner_opening_time
						.getSelectedItem().toString());
				obj_dinner.put("last_booking_time_general",
						rp_sp_dinner_last_booking.getSelectedItem().toString());
				obj_restaurant_schedule.put("lunch", obj_lunch);
				obj_restaurant_schedule.put("dinner", obj_dinner);
				obj_main.put("restaurant_schedule", obj_restaurant_schedule);
				obj_main.put("restaurant_services",
						restaurant_presentation_services.obj_services);
				String str_selected_cuisine = new String();
				String str_selected_id = new String();
				str_selected_cuisine = (String) rp_sp_cuisine.getSelectedItem();
				for (int i = 0; i < Global_variable.array_restaurantcategory
						.length(); i++) {
					if (Global_variable.array_restaurantcategory
							.getJSONObject(i).getString("name_en")
							.equalsIgnoreCase(str_selected_cuisine)) {
						str_selected_id = Global_variable.array_restaurantcategory
								.getJSONObject(i).getString("id");
					}
				}
				if (str_selected_id != null) {
					obj_main.put("cuisine", str_selected_id);
				} else {
					obj_main.put(
							"cuisine",
							Global_variable.logindata
									.getJSONObject("restaurant_details")
									.getString("cuisine").toString());
				}

				String craditcard = new String();
				for (int i = 0; i < restaurant_presentation_credit_cards.selected_paymentmethod
						.size(); i++) {
					craditcard += restaurant_presentation_credit_cards.selected_paymentmethod
							.get(i);
					if (i < restaurant_presentation_credit_cards.selected_paymentmethod
							.size() - 1) {
						craditcard += ',';
					}
				}
				System.out.println("craditcard" + craditcard);
				if (craditcard.length() == 0) {
					obj_main.put("accepted_credit_cards", "");
				} else {
					obj_main.put("accepted_credit_cards", craditcard);
				}

				System.out.println("obj_main" + obj_main);
				System.out.println("obj_services_background"
						+ restaurant_presentation_services.obj_services);

				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						RestaurantPrasentationActivity.this,
						Global_variable.rf_api_update_restaurant_Presemtation,
						obj_main));

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			System.out.println("output_Resturant_Presentation" + obj_output);
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {

					Global_variable.logindata.getJSONObject(
							"restaurant_details").put(
							"accepted_credit_cards",
							obj_output.getJSONObject("data")
									.getJSONObject("restaurant_details")
									.getString("accepted_credit_cards"));
					Global_variable.logindata.getJSONObject(
							"restaurant_details").put(
							"cuisine",
							obj_output.getJSONObject("data")
									.getJSONObject("restaurant_details")
									.getString("cuisine"));
					Global_variable.logindata.put("services", obj_output
							.getJSONObject("data").getJSONArray("services"));
					Global_variable.logindata.put("lunch", obj_output
							.getJSONObject("data").getJSONObject("lunch"));
					Global_variable.logindata.put("dinner", obj_output
							.getJSONObject("data").getJSONObject("dinner"));
					System.out.println("Loginditail"
							+ Global_variable.logindata);
					if (p.isShowing()) {
						p.dismiss();
						Toast.makeText(getApplicationContext(),
								R.string.str_update_succ, Toast.LENGTH_SHORT)
								.show();
						Intent i = new Intent(
								RestaurantPrasentationActivity.this,
								Home_Activity.class);
						startActivity(i);
					}

				} else {

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_prasentation, menu);
		return true;
	}

}
