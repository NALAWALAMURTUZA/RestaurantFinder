package com.rf_user.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.ListViewAdapter;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_Bean.DatabaseData;
import com.rf_user.sqlite_Bean.DateDetail;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Search_Restaurant_List extends Activity {
	boolean flag_call = false, global_flag = false;
	boolean flag_district = false;
	// boolean flag_city = false;
	// boolean flag_delivery = false;
	// boolean flag_category = false;
	// boolean flag_minimum_time_order = false;

	// spinner array
	String[] City_Array;
	String[] Retaurant_Array;
	String[] Categories_Array;
	boolean dialogShown = false;
	String str_response = "";
	// int selectedcity = 0;
	String flag;
	int int_city_id = 0;
	ProgressDialog progressDialog;
	/* hasmap */
	HashMap Hasmap_City = new HashMap();
	HashMap Hasmap_District = new HashMap();
	HashMap Hasmap_Categories = new HashMap();
	HashMap Hasmap_rstaurant_list = new HashMap();

	// ArrayList<HashMap<String, String>> hotel_listData;
	String TAG_SUCCESS = "success", str_select_city;
	// SwipeListView swipelistview;
	// ItemAdapter adapter_item;
	JSONArray jsonArray = null;
	public int[] lenth_array;
	ListViewAdapter listview_adapter;
	boolean search_button = false;
	boolean city_flag = false;
	/* Imageview Edittext Adapter */
	ImageView footer_viewmenu_img, footer_ordernow_img, photo_img, search_img,
			header_search_icon, header_search_select_icon, hotel_icon, Back,
			most_booked1, most_booked_select1, near_by_restaurant1,
			near_by_restaurant_select1, footer_featured_img,
			footer_setting_img, rf_home_screen_header_menu_icon;
	TextView rf_home_screen_header_loyalty_icon, city_spinner;

	TextView most_booked, most_booked_select, near_by_restaurant,
			near_by_restaurant_select;
	EditText search_edittext;
	TextView Name, Newest, Top_Rated, Name_Select, Newest_Select,
			Top_Rated_Select;
	LinearLayout nearby_rest_linear;
	ArrayAdapter<String> region_adapter, city_adapter, mto_adapter;
	ArrayAdapter<String> categories_adapter;
	ArrayAdapter<String> adapter_restaurant;
	ArrayAdapter<String> delivery_option;
	ArrayList<String> hotel_categories;
	JSONArray array_categories;

	/* String */
	String str_city_spinner, str_region_spinner, str_restaurant_list_spinner,
			str_city_id, str_region_id, str_delivery_option,
			str_minimum_order_time, str_categories, str_hotel_searchview,
			str_proximity;
	String str_name, str_newest, str_top_rated;
	String str_search_editetx;
	public String city_id, district_id, categories_id;

	/* Spinner */
	Spinner region_spinner, district_spinner, delivery_area_spinner,
			restaurant_categories_spinner, minimum_time_order_spinner,
			proximity;
	/* Layout Ratingbar Listview */
	LinearLayout Search_linearlayout, listview_layout,
			food_details_linearlayout, swipe_layout;
	LinearLayout ly_Not_Hotel;
	ListView Hotel_list_listviw;
	RatingBar ratingbar;

	/** Declaration for Hotel list */

	String str_delivery_id;
	String sessid;
	int int_City_Position, int_Region_Position, int_Delivery_Position,
			int_proximity_position;

	/** sqlite **/

	public static Boolean CheckNetworkandSQlitecall = false;
	List<DateDetail> Date_Detail;
	public static String log1 = "", datetime = "", DateDetail = "",
			data_sql = "";
	String log;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	String mostbooked = "no";

	// Title of the action bar
	String mTitle = "";
	Intent in;

	/* Declaration for http call */
	HttpConnection http = new HttpConnection();

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.search_restaurant);
		try {
			System.out.println("Global.cart" + Global_variable.cart);
			// Global_variable.cart = new JSONArray();
			System.out.println("Global.cart_after" + Global_variable.cart);

			// Intent i = getIntent();
			// str_city_spinner = i.getStringExtra("City_Name");
			// str_city_id = i.getStringExtra("City_id");
			// int_City_Position = i.getIntExtra("City_Position", 0);
			// int_Delivery_Position = i.getIntExtra("Delivery_Position", 0);
			// str_delivery_id = i.getStringExtra("Delivery_id");
			// sessid = i.getStringExtra("sessid");

			// Global_variable.FR_City_Name="Cluj";
			// Global_variable.FR_City_id="3";
			// Global_variable.FR_City_Position=4;

			System.out.println("SEarchRestaurantList"
					+ Global_variable.FR_City_Name + ".."
					+ Global_variable.FR_City_id + ".."
					+ Global_variable.FR_City_Position);

			System.out.println("SEarchRestaurantList"
					+ Global_variable.FR_Region_Name + ".."
					+ Global_variable.FR_Region_id + ".."
					+ Global_variable.FR_Region_Position);

			str_city_spinner = Global_variable.FR_City_Name;
			str_city_id = Global_variable.FR_City_id;
			int_City_Position = Global_variable.FR_City_Position;

			str_region_spinner = Global_variable.FR_Region_Name;
			str_region_id = Global_variable.FR_Region_id;
			int_Region_Position = Global_variable.FR_Region_Position;

			// int_Delivery_Position = Global_variable.FR_Delivery_Position;
			// str_delivery_id = Global_variable.FR_Delivery_id;
			sessid = SharedPreference.getsessid(getApplicationContext());

			System.out.println("str_delivery_id" + str_delivery_id);
			System.out.println("str_city_id" + str_city_spinner);
			System.out.println("sessid" + sessid);
			// city_spinner.setSelection(int_city_id);
			// System.out.println("str_city_name"+city_spinner);

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			Global_variable.abt_rest_flag = false;

			initializeWidgets();
			//
			// /* Set value to proximity spinner */
			//
			// adapter = new ArrayAdapter<String>(Search_Restaurant_List.this,
			// android.R.layout.simple_spinner_dropdown_item,
			// R.array.proximity);
			// proximity.setAdapter(adapter);
			// //proximity.setSelection(int_proximity_position);

			System.out.println("Global.sessid"
					+ SharedPreference.getsessid(getApplicationContext()));

			/* onclick nullpointer handel */
			try {

				if (str_region_id != null) {

					setlistener();
					setonitem();
				} else {
					System.out.println("NullPointerException");
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
			}
			/********/
			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {
				try {
					new async_Search_Restaurant().execute();
				} catch (NullPointerException n) {
					n.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
				}
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

							new async_Search_Restaurant().execute();

						}
						// } while (cd.isConnectingToInternet() == false);

					}

				});
			}

			// loadLocale();

		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
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
					try {
						// new async_district_from_city().execute();
					} catch (NullPointerException n) {
						n.printStackTrace();
					} catch (IndexOutOfBoundsException e) {
						// TODO: handle exception
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

			} else {
				log1 = "";
				System.out.println("District_Data Available in sqlite"
						+ data_City);
				// DBAdapter.CheckTimeExpire();
				// DBAdapter.deleteall();
				// new async_district_from_city().onPostExecute(null);
				Date_Detail = DBAdapter.CheckTimeExpire();

				if (Date_Detail.size() != 0) {
					DBAdapter.CheckFortyeighthouser(Date_Detail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		Intent i = new Intent(Search_Restaurant_List.this, FindRestaurant.class);
		startActivity(i);

	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	// ****initializeWidgets
	private void initializeWidgets() {

		initializeAllWidgetsOnly();
		initializeHashmap();
		setValueToSpinner();
		setlistener();

	}

	public void initializeHashmap() {
		// TODO Auto-generated method stub
		Global_variable.hashmap_district = new HashMap<String, String>();

	}

	public void initializeAllWidgetsOnly() {

		// swipelistview=(SwipeListView)findViewById(R.id.hotel_list_listview);

		// ****textview
		Name = (TextView) findViewById(R.id.name_textview);
		Name_Select = (TextView) findViewById(R.id.name_select_textview);
		Newest = (TextView) findViewById(R.id.newest_textview);
		Newest_Select = (TextView) findViewById(R.id.newest_select_textview);
		Top_Rated = (TextView) findViewById(R.id.top_rated_textview);
		Top_Rated_Select = (TextView) findViewById(R.id.top_rated_select_textview);

		// imageview
		header_search_icon = (ImageView) findViewById(R.id.search_imageview);
		header_search_select_icon = (ImageView) findViewById(R.id.search_select_imageview);
		Back = (ImageView) findViewById(R.id.back_imageview);
		footer_viewmenu_img = (ImageView) findViewById(R.id.footer_viewmenu_img);
		footer_ordernow_img = (ImageView) findViewById(R.id.footer_ordernow_img);
		// hotel_icon = (ImageView) findViewById(R.id.photo_imageview);
		search_img = (ImageView) findViewById(R.id.search_Layout_imageview);
		footer_featured_img = (ImageView) findViewById(R.id.footer_featured_img);
		rf_home_screen_header_menu_icon = (ImageView) findViewById(R.id.rf_home_screen_header_menu_icon);
		footer_setting_img = (ImageView) findViewById(R.id.footer_setting_img);
		rf_home_screen_header_loyalty_icon = (TextView) findViewById(R.id.rf_home_screen_header_loyalty_icon);

		// linear layout
		Search_linearlayout = (LinearLayout) findViewById(R.id.search_layout);
		ly_Not_Hotel = (LinearLayout) findViewById(R.id.ly_Not_Hotel);

		search_edittext = (EditText) findViewById(R.id.search_edittext);
		Hotel_list_listviw = (ListView) findViewById(R.id.hotel_list_listview);

		/* Spinner initialization */
		region_spinner = (Spinner) findViewById(R.id.region_spinner);
		city_spinner = (TextView) findViewById(R.id.city_spinner);
		district_spinner = (Spinner) findViewById(R.id.district_spinner);
		delivery_area_spinner = (Spinner) findViewById(R.id.delivery_option_spinner);
		minimum_time_order_spinner = (Spinner) findViewById(R.id.minimum_time_order_spinner);
		restaurant_categories_spinner = (Spinner) findViewById(R.id.categories_spinner);
		proximity = (Spinner) findViewById(R.id.proximity);

		// ArrayAdapter<String> proximity_adapter = new
		// ArrayAdapter<String>(getApplicationContext(),
		// android.R.layout.simple_dropdown_item_1line, R.array.proximity);
		// proximity.setAdapter(proximity_adapter);
		int length = getResources().getStringArray(R.array.proximity).length;
		proximity.setSelection(length - 1);

		/* Image view of most booked & Nearby drivers */
		most_booked = (TextView) findViewById(R.id.most_booked);
		most_booked_select = (TextView) findViewById(R.id.most_booked_select);
		near_by_restaurant = (TextView) findViewById(R.id.near_by_restaurant);
		near_by_restaurant_select = (TextView) findViewById(R.id.near_by_restaurant_select);

		nearby_rest_linear = (LinearLayout) findViewById(R.id.nearby_rest_linear);

	}

	public void setValueToSpinner() {

		try {

			System.out.println("!!!!!!!!!setValueToSpinner"
					+ int_Region_Position + "!!!!!" + int_City_Position);

			System.out.println("!!!!!!!!!!!!!!!!!Search"
					+ Global_variable.Region_Array.length + "!!!!!!!!"
					+ Global_variable.Region_Array.toString());

			for (String str : Global_variable.Region_Array) {

				System.out
						.println("!!!!!!!!!!!!!!!!!!!!region_values..." + str);

			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
			// /** Set Default Value to Region spinner */

			if (Global_variable.Region_Array != null) {
				/** Set Adapter to city spinner */
				region_adapter = new ArrayAdapter<String>(
						Search_Restaurant_List.this,
						android.R.layout.simple_spinner_item,
						Global_variable.Region_Array);
				region_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				region_spinner.setAdapter(region_adapter);
				region_spinner.setSelection(int_Region_Position);

			} else {

			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
			/** Set Default Value to City spinner */

			city_spinner.setText(Global_variable.FR_City_Name);
			// city_spinner();

			// if (Global_variable.City_Array != null) {
			// /** Set Adapter to city spinner */
			// city_adapter = new ArrayAdapter<String>(
			// Search_Restaurant_List.this,
			// android.R.layout.simple_spinner_item,
			// Global_variable.City_Array);
			// city_adapter
			// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// city_spinner.setAdapter(city_adapter);
			// city_spinner.setSelection(int_City_Position);
			//
			// } else {
			//
			// }

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		/** Set Default Value to District spinner */
		try {
			DBCheckCityAvaibility(str_city_id);
		} catch (NullPointerException n) {

		}
		// new async_district_from_city().execute();

		try {

			/** Set Default Value to Delivery Spinner */

			/** Convert Hashmap to array */

			System.out.println("!!!!!!!!!Global_variable.Delivery_area_Array"
					+ Global_variable.Delivery_area_Array);

			for (String str : Global_variable.Delivery_area_Array) {

				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!str"
						+ str);

			}

			if (Global_variable.Delivery_area_Array != null) {

				// delivery_option= new
				// ArrayAdapter<String>(getApplicationContext(),
				// android.R.layout.simple_dropdown_item_1line,Global_variable.Delivery_area_Array);
				// delivery_option.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// delivery_area_spinner.setAdapter(delivery_option);
				// delivery_area_spinner.setSelection(2);

				delivery_option = new ArrayAdapter<String>(
						Search_Restaurant_List.this,
						android.R.layout.simple_spinner_item,
						Global_variable.Delivery_area_Array);
				delivery_option
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				delivery_area_spinner.setAdapter(delivery_option);
				delivery_area_spinner.setSelection(1);
			} else {

			}

			/** Set Default Value to Restaurant Category Spinner */

			/** Convert Hashmap to array */
			Global_variable.Restaurant_Category_Array = new String[Global_variable.hashmap_restaurantcategory
					.size() + 1];
			String[] values = new String[Global_variable.hashmap_restaurantcategory
					.size() + 1];
			Global_variable.Restaurant_Category_Array[0] = Global_variable.Default_Restaurant_Category_String;
			int index = 1;
			for (Entry<String, String> mapEntry : Global_variable.hashmap_restaurantcategory
					.entrySet()) {
				Global_variable.Restaurant_Category_Array[index] = mapEntry
						.getKey();
				values[index] = mapEntry.getKey();
				index++;
			}

			System.out.println("str_array_district_array"
					+ Global_variable.Restaurant_Category_Array);

			if (Global_variable.Restaurant_Category_Array != null) {
				categories_adapter = new ArrayAdapter<String>(
						Search_Restaurant_List.this,
						android.R.layout.simple_spinner_item,
						Global_variable.Restaurant_Category_Array);
				categories_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				restaurant_categories_spinner.setAdapter(categories_adapter);
			} else {

			}

			/** Set Default Value to Minimum Time Order Spinner */

			Global_variable.Minimum_Time_Order_Array[0] = Global_variable.hashmap_texts
					.get("Within 1 Day");
			Global_variable.Minimum_Time_Order_Array[1] = Global_variable.hashmap_texts
					.get("1 Day Before");
			Global_variable.Minimum_Time_Order_Array[2] = Global_variable.hashmap_texts
					.get("2 Day Before");
			Global_variable.Minimum_Time_Order_Array[3] = Global_variable.hashmap_texts
					.get("3 Day Before");
			Global_variable.Minimum_Time_Order_Array[4] = Global_variable.hashmap_texts
					.get("4 Day Before");
			Global_variable.Minimum_Time_Order_Array[5] = Global_variable.hashmap_texts
					.get("5 Day Before");
			Global_variable.Minimum_Time_Order_Array[6] = Global_variable.hashmap_texts
					.get("6 Day Before");
			Global_variable.Minimum_Time_Order_Array[7] = Global_variable.hashmap_texts
					.get("7 Day Before");

			if (Global_variable.Minimum_Time_Order_Array != null) {
				mto_adapter = new ArrayAdapter<String>(
						Search_Restaurant_List.this,
						android.R.layout.simple_spinner_item,
						Global_variable.Minimum_Time_Order_Array);
				mto_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				minimum_time_order_spinner.setAdapter(mto_adapter);
				minimum_time_order_spinner
						.setSelection((Global_variable.Minimum_Time_Order_Array.length) - (1));
			} else {

			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		return super.onCreateOptionsMenu(menu);

	}

	public void setlistener() {

		try {

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
		}

		rf_home_screen_header_loyalty_icon
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent in = new Intent(getApplicationContext(),
								LoyaltyRewardsScreen.class);
						startActivity(in);

					}
				});

		footer_setting_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							Intent in = new Intent(getApplicationContext(),
									SettingsScreen.class);
							startActivity(in);
						}
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.str_please_login),
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
									Search_Restaurant_List.this,
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
													Global_variable.activity = "Search_Restaurant";

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
																Search_Restaurant_List.this)
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

		footer_ordernow_img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (Global_variable.cart.length() == 0) {
						Toast.makeText(Search_Restaurant_List.this,
								R.string.empty_cart, Toast.LENGTH_SHORT).show();
					} else {
						Intent i = new Intent(Search_Restaurant_List.this,
								Cart.class);
						startActivity(i);

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		Name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Name.setClickable(true);
				// city_spinner.setClickable(false);
				// district_spinner.setClickable(false);
				// delivery_area_spinner.setClickable(false);
				// minimum_time_order_spinner.setClickable(false);
				// restaurant_categories_spinner.setClickable(false);
				// Newest.setClickable(false);
				// Top_Rated.setClickable(false);
				try {

					most_booked.setVisibility(View.VISIBLE);
					most_booked_select.setVisibility(View.GONE);
					near_by_restaurant.setVisibility(View.VISIBLE);
					near_by_restaurant_select.setVisibility(View.GONE);

					Name.setVisibility(View.GONE);
					Name_Select.setVisibility(View.VISIBLE);
					Newest_Select.setVisibility(View.GONE);
					Newest.setVisibility(View.VISIBLE);
					Top_Rated.setVisibility(View.VISIBLE);
					Top_Rated_Select.setVisibility(View.GONE);
					str_name = Name.getText().toString().toLowerCase();
					runOnUiThread(new Runnable() {
						public void run() {
							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {

								new async_Search_Restaurant().execute();

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

					// Search_Restaurant();
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
		search_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Search_Restaurant();
				try {
					runOnUiThread(new Runnable() {
						public void run() {
							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {
								try {
									new async_Search_Restaurant().execute();
								} catch (NullPointerException n) {
									n.printStackTrace();
								}
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
		Newest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Newest.setClickable(true);
				// city_spinner.setClickable(false);
				// district_spinner.setClickable(false);
				// delivery_area_spinner.setClickable(false);
				// minimum_time_order_spinner.setClickable(false);
				// restaurant_categories_spinner.setClickable(false);
				// Name.setClickable(false);
				// Top_Rated.setClickable(false);

				str_name = null;

				most_booked.setVisibility(View.VISIBLE);
				most_booked_select.setVisibility(View.GONE);
				near_by_restaurant.setVisibility(View.VISIBLE);
				near_by_restaurant_select.setVisibility(View.GONE);

				Newest.setVisibility(View.GONE);
				Newest_Select.setVisibility(View.VISIBLE);
				Top_Rated_Select.setVisibility(View.GONE);
				Top_Rated.setVisibility(View.VISIBLE);
				Name.setVisibility(View.VISIBLE);
				Name_Select.setVisibility(View.GONE);
				str_name = Newest.getText().toString().toLowerCase();
				runOnUiThread(new Runnable() {
					public void run() {
						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							try {
								new async_Search_Restaurant().execute();
							} catch (NullPointerException n) {
								n.printStackTrace();
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
				});

			}
		});

		Top_Rated.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Top_Rated.setClickable(true);
				// city_spinner.setClickable(false);
				// district_spinner.setClickable(false);
				// delivery_area_spinner.setClickable(false);
				// minimum_time_order_spinner.setClickable(false);
				// restaurant_categories_spinner.setClickable(false);
				// Newest.setClickable(false);
				// Name.setClickable(false);
				str_name = null;

				most_booked.setVisibility(View.VISIBLE);
				most_booked_select.setVisibility(View.GONE);
				near_by_restaurant.setVisibility(View.VISIBLE);
				near_by_restaurant_select.setVisibility(View.GONE);

				Top_Rated.setVisibility(View.GONE);
				Top_Rated_Select.setVisibility(View.VISIBLE);
				Newest.setVisibility(View.VISIBLE);
				Newest_Select.setVisibility(View.GONE);
				Name.setVisibility(View.VISIBLE);
				Name_Select.setVisibility(View.GONE);
				str_name = Top_Rated.getText().toString().toLowerCase();
				runOnUiThread(new Runnable() {
					public void run() {
						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							try {
								new async_Search_Restaurant().execute();
							} catch (NullPointerException n) {
								n.printStackTrace();
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
				});

			}
		});
		header_search_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					header_search_icon.setVisibility(View.GONE);
					header_search_select_icon.setVisibility(View.VISIBLE);
					Search_linearlayout.setVisibility(View.VISIBLE);

					nearby_rest_linear.setVisibility(View.GONE);

					flag_call = true;
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
		header_search_select_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					header_search_icon.setVisibility(View.VISIBLE);
					header_search_select_icon.setVisibility(View.GONE);
					Search_linearlayout.setVisibility(View.GONE);

					nearby_rest_linear.setVisibility(View.VISIBLE);

					flag_call = false;
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		Back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Search_Restaurant_List.this,
						FindRestaurant.class);
				startActivity(i);

			}
		});

		most_booked.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					Top_Rated.setVisibility(View.VISIBLE);
					Top_Rated_Select.setVisibility(View.GONE);
					Newest.setVisibility(View.VISIBLE);
					Newest_Select.setVisibility(View.GONE);
					Name.setVisibility(View.VISIBLE);
					Name_Select.setVisibility(View.GONE);

					most_booked.setVisibility(View.GONE);
					most_booked_select.setVisibility(View.VISIBLE);
					near_by_restaurant.setVisibility(View.VISIBLE);
					near_by_restaurant_select.setVisibility(View.GONE);
					mostbooked = "yes";

					new async_Search_Restaurant().execute();
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		near_by_restaurant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					Top_Rated.setVisibility(View.VISIBLE);
					Top_Rated_Select.setVisibility(View.GONE);
					Newest.setVisibility(View.VISIBLE);
					Newest_Select.setVisibility(View.GONE);
					Name.setVisibility(View.VISIBLE);
					Name_Select.setVisibility(View.GONE);

					near_by_restaurant.setVisibility(View.GONE);
					near_by_restaurant_select.setVisibility(View.VISIBLE);
					most_booked.setVisibility(View.VISIBLE);
					most_booked_select.setVisibility(View.GONE);
					mostbooked = "no";

					new async_Search_Restaurant().execute();
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
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							Global_variable.activity = "Search_Restaurant";

							Intent in = new Intent(getApplicationContext(),
									MyFavourites.class);
							startActivity(in);
						}
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.str_please_login),
								Toast.LENGTH_SHORT).show();
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

		});

	}

	private void setonitem() {

		Hotel_list_listviw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {

					System.out.println("In listview on click..."
							+ Global_variable.hotel_listData);

					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!"
							+ Global_variable.previous_restaurant_selected_id);
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!" + position);

					if (Global_variable.previous_restaurant_selected_id != position) {
						Global_variable.cart = new JSONArray();
					}

					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Cart"
							+ Global_variable.cart);
					if (Global_variable.hotel_listData.size() != 0
							|| Global_variable.hotel_listData != null) {
						Global_variable.previous_restaurant_selected_id = position;

						Global_variable.hotel_iconurl = Global_variable.hotel_listData
								.get(position).get("hotel_iconurl");
						Global_variable.hotel_id = Global_variable.hotel_listData
								.get(position).get("hotel_id");

						Global_variable.hotel_name = Global_variable.hotel_listData
								.get(position).get("hotel_name");

						Global_variable.user_fav = Global_variable.hotel_listData
								.get(position).get("user_fav");

						Global_variable.indine_available = Global_variable.hotel_listData
								.get(position).get("hotel_indine");

						Global_variable.hotel_map_lat = Double
								.parseDouble(Global_variable.hotel_listData
										.get(position).get("hotel_map_lat"));

						Global_variable.hotel_map_long = Double
								.parseDouble(Global_variable.hotel_listData
										.get(position).get("hotel_map_long"));

						Intent i = new Intent(Search_Restaurant_List.this,
								Categories.class);

						i.putExtra("hotel_name", Global_variable.hotel_listData
								.get(position).get("hotel_name"));
						i.putExtra("hotel_address",
								Global_variable.hotel_listData.get(position)
										.get("hotel_address"));
						i.putExtra("hotel_day", Global_variable.hotel_listData
								.get(position).get("hotel_day"));
						i.putExtra("hotel_iconurl",
								Global_variable.hotel_listData.get(position)
										.get("hotel_iconurl"));
						i.putExtra("hotel_id", Global_variable.hotel_listData
								.get(position).get("hotel_id"));
						i.putExtra("hotel_rating",
								Global_variable.hotel_listData.get(position)
										.get("hotel_rating"));
						i.putExtra("hotel_pick", Global_variable.hotel_listData
								.get(position).get("hotel_pick"));
						i.putExtra("hotel_minimum",
								Global_variable.hotel_listData.get(position)
										.get("hotel_minimum"));
						i.putExtra("hotel_delivery",
								Global_variable.hotel_listData.get(position)
										.get("hotel_delivery"));

						i.putExtra("hotel_indine",
								Global_variable.hotel_listData.get(position)
										.get("hotel_indine"));

						i.putExtra("position", position);
						// hotel_listData.get(position).get("hotel_categories")
						// i.putParcelableArrayListExtra("a",hotel_listData.get(position).get("hotel_categories"));
						// ArrayList<String> arraylist=new ArrayList<String>();
						// arraylist.add(Global_variable.hotel_listData.get(position).get("hotel_categories"));
						// System.out.println("malyu arraylistma"+arraylist);
						// for(int abc=0;abc<arraylist.size();abc++)
						// {
						// i.putStringArrayListExtra("hotel_categories",arraylist.get(0));
						// System.out.println("categories_in_listview"
						// + arraylist.get(0));

						// }
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
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (IllegalThreadStateException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		});

		region_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// city_spinner_class();
				try {
					region_spinner.setClickable(true);
					city_spinner.setClickable(false);
					// Name.setClickable(false);
					// district_spinner.setClickable(false);
					// delivery_area_spinner.setClickable(false);
					// minimum_time_order_spinner.setClickable(false);
					// restaurant_categories_spinner.setClickable(false);
					// Newest.setClickable(false);
					// Top_Rated.setClickable(false);

					str_region_spinner = region_spinner.getSelectedItem()
							.toString();
					Global_variable.FR_Region_Position = region_spinner
							.getSelectedItemPosition();
					int_Region_Position = Global_variable.FR_Region_Position;

					System.out
							.println("!!!!!!!!Global_variable.hashmap_region..."
									+ Global_variable.hashmap_region);
					System.out.println("!!!!!!!!str_region_spinner..."
							+ str_region_spinner);
					System.out.println("!!!!!!!!str_region_spinner..."
							+ Global_variable.hashmap_region
									.get(str_region_spinner));

					str_region_id = (String) Global_variable.hashmap_region
							.get(str_region_spinner);
					city_spinner.setClickable(true);

					System.out.println("hotel_list_sessid_city_spinner");

					if (flag_call) {

						System.out.println("ishita...1");

						System.out.println("hotel_list_sessid_str_spinner"
								+ str_region_spinner);
						// if(str_city_spinner.equals(city_spinner.getSelectedItem().toString()))
						{
							System.out.println("async call");
							runOnUiThread(new Runnable() {
								public void run() {

									if (global_flag == true) {

										if (Global_variable.hashmap_cities != null) {
											Global_variable.hashmap_cities
													.clear();
											// Global_variable.City_Array=new
											// String[0];

											region_spinner.setClickable(false);

											new async_city_Spinner().execute();

											// DBCheckCityAvaibility(str_region_id);

											// city_flag = true;

										}

									} else {
										// global_flag=true;
									}
								}
							});

						}

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				} catch (IndexOutOfBoundsException n) {
					n.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		city_spinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				city_spinner();
			}
		});

		// city_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// // TODO Auto-generated method stub
		// // city_spinner_class();
		// try {
		// city_spinner.setClickable(true);
		// // Name.setClickable(false);
		// // district_spinner.setClickable(false);
		// // delivery_area_spinner.setClickable(false);
		// // minimum_time_order_spinner.setClickable(false);
		// // restaurant_categories_spinner.setClickable(false);
		// // Newest.setClickable(false);
		// // Top_Rated.setClickable(false);
		//
		// str_city_spinner = city_spinner.getSelectedItem()
		// .toString();
		// Global_variable.FR_City_Position = city_spinner
		// .getSelectedItemPosition();
		// int_City_Position = Global_variable.FR_City_Position;
		//
		// str_city_id = (String) Global_variable.hashmap_cities
		// .get(str_city_spinner);
		// district_spinner.setClickable(true);
		//
		// System.out.println("hotel_list_sessid_city_spinner");
		//
		// if (flag_call) {
		//
		// System.out.println("ishita...1");
		//
		// System.out.println("hotel_list_sessid_str_spinner"
		// + str_city_spinner);
		// //
		// if(str_city_spinner.equals(city_spinner.getSelectedItem().toString()))
		// {
		// System.out.println("async call");
		// runOnUiThread(new Runnable() {
		// public void run() {
		//
		// if (global_flag == true) {
		//
		// // if (Global_variable.hashmap_district
		// // != null) {
		// // Global_variable.hashmap_district
		// // .clear();
		// DBCheckCityAvaibility(str_city_id);
		// // new async_district_from_city()
		// // .execute();
		//
		// city_flag = true;
		// /** check Internet Connectivity */
		// if (cd.isConnectingToInternet()) {
		//
		// new async_Search_Restaurant()
		// .execute();
		//
		// } else {
		//
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// // TODO Auto-generated
		// // method stub
		// Toast.makeText(
		// getApplicationContext(),
		// R.string.No_Internet_Connection,
		// Toast.LENGTH_SHORT)
		// .show();
		//
		// }
		//
		// });
		// }
		//
		// }
		//
		// // } else {
		// // // global_flag=true;
		// // }
		// }
		// });
		//
		// }
		//
		// }
		// } catch (NullPointerException n) {
		// n.printStackTrace();
		// } catch (IndexOutOfBoundsException n) {
		// n.printStackTrace();
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		district_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						try {
							district_spinner.setClickable(true);
							// city_spinner.setClickable(false);
							// district_spinner.setClickable(false);
							// Name.setClickable(false);
							// minimum_time_order_spinner.setClickable(false);
							// restaurant_categories_spinner.setClickable(false);
							// Newest.setClickable(false);
							// Top_Rated.setClickable(false);

							// TODO Auto-generated method stub

							System.out
									.println("hotel_list_sessid_district_spinner");
							if (flag_call) {

								System.out.println("ishita...2");

								runOnUiThread(new Runnable() {
									public void run() {

										if (global_flag == true) {

											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new async_Search_Restaurant()
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO Auto-generated
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
											// global_flag=true;
										}

									}
								});

							}
						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		delivery_area_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						//
						try {
							delivery_area_spinner.setClickable(true);
							// city_spinner.setClickable(false);
							// district_spinner.setClickable(false);
							// Name.setClickable(false);
							// minimum_time_order_spinner.setClickable(false);
							// restaurant_categories_spinner.setClickable(false);
							// Newest.setClickable(false);
							// Top_Rated.setClickable(false);

							str_delivery_option = delivery_area_spinner
									.getSelectedItem().toString();
							restaurant_categories_spinner.setClickable(true);
							minimum_time_order_spinner.setClickable(true);
							System.out
									.println("hotel_list_sessid_delivery_area_spinner"
											+ str_delivery_option);
							if (flag_call) {

								System.out.println("ishita..3");

								System.out
										.println("hotel_list_sessid_delivery_area_spinner_call"
												+ str_delivery_option);
								runOnUiThread(new Runnable() {
									public void run() {

										if (global_flag == true) {
											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new async_Search_Restaurant()
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO Auto-generated
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
											// global_flag=true;
										}

									}
								});

							}
						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

		minimum_time_order_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						try {
							minimum_time_order_spinner.setClickable(true);
							// city_spinner.setClickable(true);
							// district_spinner.setClickable(false);
							// delivery_area_spinner.setClickable(false);
							// Name.setClickable(false);
							// restaurant_categories_spinner.setClickable(false);
							// Newest.setClickable(false);
							// Top_Rated.setClickable(false);

							str_minimum_order_time = (String) minimum_time_order_spinner
									.getItemAtPosition(minimum_time_order_spinner
											.getSelectedItemPosition());
							System.out.println("mto_spinner_position"
									+ str_minimum_order_time);
							// Search_linearlayout.setVisibility(View.GONE);
							// header_search_select_icon.setVisibility(View.GONE);
							System.out
									.println("hotel_list_sessid_minimum_time_spinner");
							if (flag_call) {

								System.out.println("ishita..4");

								runOnUiThread(new Runnable() {
									public void run() {

										if (global_flag == true) {
											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new async_Search_Restaurant()
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO Auto-generated
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
											global_flag = true;
										}

									}
								});

							}
						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		restaurant_categories_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						try {
							restaurant_categories_spinner.setClickable(true);
							str_categories = (String) restaurant_categories_spinner
									.getItemAtPosition(restaurant_categories_spinner
											.getSelectedItemPosition());
							System.out.println("cat_spinner_position"
									+ str_categories);
							System.out
									.println("hotel_list_sessid_restaurant_category_spinner");
							if (flag_call) {

								System.out.println("ishita..5");

								runOnUiThread(new Runnable() {
									public void run() {
										if (global_flag == true) {
											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new async_Search_Restaurant()
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO Auto-generated
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
											// global_flag=true;
										}
									}
								});

							}
						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		proximity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					str_proximity = proximity.getSelectedItem().toString();
					int_proximity_position = proximity
							.getSelectedItemPosition();

					new async_Search_Restaurant().execute();
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void city_spinner() {
		try {
			str_select_city = null;
			city_id = null;

			if (!city_spinner.getText().toString()
					.equalsIgnoreCase(getString(R.string.Register_Select_City))) {
				String city_name = city_spinner.getText().toString();

				int index = -1;
				for (int i = 0; i < Global_variable.City_Array.length; i++) {
					if (Global_variable.City_Array[i].equals(city_name)) {
						index = i;
						int_City_Position = index;
						break;
					}
				}

				System.out.println("!!!!!!!!!!!!" + index);
			}

			Builder builder = new AlertDialog.Builder(
					Search_Restaurant_List.this);
			builder.setTitle(R.string.Select_City);
			builder.setSingleChoiceItems(Global_variable.City_Array,
					int_City_Position, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// District_Array=null;
							int_City_Position = which;
							str_select_city = Global_variable.City_Array[which];
							System.out.println("Value of City name"
									+ str_select_city);
							city_id = (String) Global_variable.hashmap_cities
									.get(str_select_city);
							System.out.println("City_id_value" + city_id);

							try {

								if (city_spinner.getText().toString() != str_select_city) {
									city_spinner.setText(str_select_city);
									System.out.println("textview_cityset"
											+ str_select_city);
									str_city_id = (String) Global_variable.hashmap_cities
											.get(city_spinner.getText()
													.toString());
									// district_spinner.setClickable(true);

									System.out
											.println("hotel_list_sessid_city_spinner");

									if (flag_call) {

										System.out.println("ishita...1");

										System.out
												.println("hotel_list_sessid_str_spinner"
														+ str_city_spinner);
										// if(str_city_spinner.equals(city_spinner.getSelectedItem().toString()))
										{
											System.out.println("async call");
											runOnUiThread(new Runnable() {
												public void run() {

													if (global_flag == true) {

														// if
														// (Global_variable.hashmap_district
														// != null) {
														// Global_variable.hashmap_district
														// .clear();
														// DBCheckCityAvaibility(str_city_id);
														// new
														// async_district_from_city()
														// .execute();

														city_flag = true;
														/**
														 * check Internet
														 * Connectivity
														 */
														if (cd.isConnectingToInternet()) {

															new async_Search_Restaurant()
																	.execute();

														} else {

															runOnUiThread(new Runnable() {

																@Override
																public void run() {

																	// TODO
																	// Auto-generated
																	// method
																	// stub
																	Toast.makeText(
																			getApplicationContext(),
																			R.string.No_Internet_Connection,
																			Toast.LENGTH_SHORT)
																			.show();

																}

															});
														}

													}

													// } else {
													// // global_flag=true;
													// }
												}
											});

										}

									}

									dialogShown = false;
									dialog.dismiss();
									// DBCheckCityAvaibility(city_id);
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
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	public class async_Search_Restaurant extends AsyncTask<Void, Void, Void> {
		JSONObject data;
		JSONObject json = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("hotel_list_Search Restaurant Call");
			// Showing progress dialog
			// if (city_flag == false) {
			//
			// try {
			progressDialog = new ProgressDialog(Search_Restaurant_List.this);
			progressDialog.setCancelable(false);
			progressDialog.show();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			//
			// else if (city_flag == true) {
			// city_flag = false;
			// }

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject fetch_hotel_detail = new JSONObject();

				try {
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							fetch_hotel_detail.put("user_id", SharedPreference
									.getuser_id(getApplicationContext()));
							System.out.println("user_id" + fetch_hotel_detail);
						}
					} else {
						fetch_hotel_detail.put("user_id", "");
						System.out.println("user_id" + fetch_hotel_detail);
					}

					if (search_edittext.getText().toString() != "") {
						fetch_hotel_detail.put("q", search_edittext.getText()
								.toString());
						System.out.println("hotel_list_q" + fetch_hotel_detail);
					}

					fetch_hotel_detail.put("city",
							Global_variable.hashmap_cities.get(city_spinner
									.getText().toString()));

					// fetch_hotel_detail.put("city",
					// Global_variable.hashmap_cities.get(city_spinner
					// .getSelectedItem().toString()));
					System.out.println("hotel_list_city" + fetch_hotel_detail);
					// System.out.println("hotel_list_city_district"
					// + district_spinner.getSelectedItem().toString());

					System.out.println("str_city_id.." + str_city_id);

					// if (flag_district) {
					// if (str_city_id != null) {
					//
					// System.out
					// .println("district_spinner.getSelectedItem().toString().."
					// + district_spinner
					// .getSelectedItem()
					// .toString());
					// System.out.println("!!!!shikha.."
					// + district_spinner.getSelectedItem()
					// .toString());
					// if (district_spinner.getSelectedItem().toString() != "")
					// {
					//
					// System.out.println("!!!!shikha..in if");
					//
					// System.out
					// .println("Global_variable.hashmap_district"
					// + Global_variable.hashmap_district
					// .get(district_spinner
					// .getSelectedItem()
					// .toString()));
					//
					// fetch_hotel_detail.put("district",
					// Global_variable.hashmap_district
					// .get(district_spinner
					// .getSelectedItem()
					// .toString()));
					//
					// System.out.println("hotel_list_district if 1"
					// + fetch_hotel_detail);
					//
					// // System.out.println("hotel_list_district bahar"
					// // + fetch_hotel_detail);
					// }
					//
					// else {
					//
					// System.out.println("!!!!shikha..in else");
					//
					// fetch_hotel_detail.put("district", "");
					// System.out.println("hotel_list_district if"
					// + fetch_hotel_detail);
					//
					// }
					//
					// System.out.println("hotel_list_district"
					// + fetch_hotel_detail);
					//
					// }

					System.out
							.println("!!!!shikha.."
									+ restaurant_categories_spinner
											.getSelectedItem()
											.toString()
											.equals(Global_variable.Default_Restaurant_Category_String));

					if ((restaurant_categories_spinner.getSelectedItem()
							.toString()
							.equals(Global_variable.Default_Restaurant_Category_String))) {
						fetch_hotel_detail.put("cat", "-1");
						System.out.println("hotel_list_cats"
								+ fetch_hotel_detail);

					} else {
						fetch_hotel_detail.put("cat",
								Global_variable.hashmap_restaurantcategory
										.get(restaurant_categories_spinner
												.getSelectedItem().toString()));
						System.out.println("hotel_list_cats"
								+ fetch_hotel_detail);
					}
					// } else {
					//
					// }

					fetch_hotel_detail.put("deltype", delivery_area_spinner
							.getSelectedItem().toString().toLowerCase());
					System.out.println("hotel_list_deltype"
							+ fetch_hotel_detail);

					System.out.println("str_name.." + str_name);

					if (str_name != null) {
						fetch_hotel_detail.put("sort", str_name);
					} else {
						fetch_hotel_detail.put("sort", "");
					}

					if (Global_variable.latitude + "" != null) {
						fetch_hotel_detail.put("lat", Global_variable.latitude);
					} else {
						fetch_hotel_detail.put("lat", "");
					}

					if (Global_variable.longitude + "" != null) {
						fetch_hotel_detail.put("long",
								Global_variable.longitude);
					} else {
						fetch_hotel_detail.put("long", "");
					}

					if (mostbooked != null) {
						fetch_hotel_detail.put("mostbook", mostbooked);
					} else {
						fetch_hotel_detail.put("mostbook", "");
					}

					if (proximity.getSelectedItem().toString() != null) {
						fetch_hotel_detail.put("proximity", proximity
								.getSelectedItem().toString());
					} else {
						fetch_hotel_detail.put("proximity", "");
					}

					System.out.println("hotel_list_sort" + fetch_hotel_detail);
					fetch_hotel_detail.put("mto", minimum_time_order_spinner
							.getSelectedItemPosition()+"");
					System.out.println("hotel_list_mto" + fetch_hotel_detail);
					fetch_hotel_detail
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("hotel_list_sessid_lat"
							+ Global_variable.latitude);
					System.out.println("hotel_list_sessid_long"
							+ Global_variable.longitude);
					System.out
							.println("hotel_list_sessid" + fetch_hotel_detail);
				} catch (JSONException e) {
					e.printStackTrace();

				} catch (NullPointerException n) {
					n.printStackTrace();
				}
				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				System.out.println("Global_variable.rf_lang_Url+url"
						+ Global_variable.rf_lang_Url
						+ Global_variable.rf_searchrestaurant_api_path);
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_searchrestaurant_api_path);
				System.out.println("hotel_url..." + httppostreq);
				StringEntity se = new StringEntity(
						fetch_hotel_detail.toString(), "UTF-8");
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
					str_Hotel_list = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					
					str_Hotel_list=str_Hotel_list.substring(str_Hotel_list.indexOf("{"), str_Hotel_list.lastIndexOf("}") + 1);
					System.out.println("!!!!!hotel_list_last_text"
							+ str_Hotel_list);

					json = new JSONObject(str_Hotel_list);
					System.out
							.println("!!!!!!!!!!!hotel_list_last_json_final_response"
									+ json);

				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				} catch (NullPointerException n) {
					n.printStackTrace();
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

						if (json != null) {
							// json success tag
							String success1 = null;
							String str_data = null;

							try {
								success1 = json.getString(TAG_SUCCESS);

								System.out.println("tag" + success1);

								str_data = json.getString("data");
								System.out.println("Search_restaurant_str_data"
										+ str_data);

								if (success1.equals("true")) {
									if (!str_data.equalsIgnoreCase("[]")) {
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

											int lenth = hotel_list_response_array
													.length();
											// System.out.println("respose_array Value"+
											// response_array.keys().toString());
											System.out.println("hotel_list"
													+ lenth);
											Global_variable.hotel_listData = new ArrayList<HashMap<String, String>>();

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
													System.out
															.println("Vtype_hotel_n"
																	+ hotel_name);
													String hotel_id = hotel_list_getvalue
															.getString("id");
													System.out
															.println("Vtype_hotelid"
																	+ hotel_id);
													String hotel_address = hotel_list_getvalue
															.getString("address");
													System.out
															.println("Vtype_hoteladd"
																	+ hotel_address);

													String hotel_user_fav_rest = hotel_list_getvalue
															.getString("user_fav");
													System.out
															.println("hotel_user_fav_rest"
																	+ hotel_user_fav_rest);

													String hotel_minimum = hotel_list_getvalue
															.getString("min_order");
													System.out
															.println("Vtype_hotelmto"
																	+ hotel_minimum);

													String day = hotel_list_getvalue
															.getString("day");
													System.out.println("day"
															+ day);

													//

													String hotel_iconurl = hotel_list_getvalue
															.getString("icon_url");
													System.out
															.println("Vtype_hotelicon"
																	+ hotel_iconurl);

													String hotel_banner_image = hotel_list_getvalue
															.getString("banner_image");
													System.out
															.println("hotel_banner_image"
																	+ hotel_banner_image);

													String hotel_cuisine = hotel_list_getvalue
															.getString("cuisine");
													System.out
															.println("hotel_cuisine"
																	+ hotel_cuisine);

													String hotel_distance = hotel_list_getvalue
															.getString("distance");
													System.out
															.println("hotel_distance"
																	+ hotel_distance);

													String hotel_map_lat = hotel_list_getvalue
															.getString("map_lat");
													System.out
															.println("hotel_map_lat"
																	+ hotel_map_lat);

													String hotel_map_long = hotel_list_getvalue
															.getString("map_long");
													System.out
															.println("hotel_map_long"
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
																		.getString(
																				j)
																		.toString());

													}
													System.out
															.println("categories_arraylist"
																	+ hotel_categories);
													String hotel_pick = hotel_list_getvalue
															.getString("pick");
													System.out
															.println("Vtype_hotepick"
																	+ hotel_pick);
													String hotel_delivery = hotel_list_getvalue
															.getString("delivery");
													System.out
															.println("Vtype_hoteldel"
																	+ hotel_delivery);

													String hotel_indine = hotel_list_getvalue
															.getString("indine");

													String hotel_day = hotel_list_getvalue
															.getString("day");
													System.out
															.println("Vtype_hotelday"
																	+ hotel_day);
													String hotel_rating = hotel_list_getvalue
															.getString("rating");
													System.out
															.println("Vtype_hotelday"
																	+ hotel_rating);

													System.out
															.println("chetan");
													HashMap<String, String> map = new HashMap<String, String>();

													map.put("hotel_name",
															hotel_name);
													System.out
															.println("hotel_n"
																	+ map);
													map.put("hotel_address",
															hotel_address);
													System.out
															.println("hotel_a"
																	+ map);
													map.put("hotel_minimum",
															hotel_minimum);
													System.out
															.println("hotel_m"
																	+ map);
													map.put("hotel_day",
															hotel_day);
													System.out
															.println("hotel_d"
																	+ map);
													map.put("hotel_iconurl",
															hotel_iconurl);
													System.out
															.println("hotel_i"
																	+ map);
													map.put("hotel_banner_image",
															hotel_banner_image);
													System.out
															.println("hotel_i"
																	+ map);
													//
													// map.put("user_loyalty_pts",
													// hotel_list_getvalue
													// .getString("user_loyalty_pts"));
													// System.out.println("hotel_i"
													// + map);

													map.put("day", day);
													map.put("user_fav",
															hotel_user_fav_rest);

													map.put("cuisine",
															hotel_cuisine);

													map.put("hotel_map_lat",
															hotel_map_lat);

													map.put("hotel_map_long",
															hotel_map_long);

													map.put("hotel_distance",
															hotel_distance);
													System.out
															.println("hotel_i"
																	+ map);

													map.put("hotel_id",
															hotel_id);
													System.out
															.println("hotel_id"
																	+ map);
													map.put("hotel_rating",
															hotel_rating);
													System.out
															.println("hotel_rating"
																	+ map);
													map.put("hotel_pick",
															hotel_pick);
													System.out
															.println("hotel_pick"
																	+ map);
													map.put("hotel_delivery",
															hotel_delivery);
													System.out
															.println("hotel_delivery"
																	+ map);

													map.put("hotel_indine",
															hotel_indine);
													System.out
															.println("hotel_indine"
																	+ map);

													map.put("hotel_categories",
															hotel_categories
																	.toString());
													System.out
															.println("hotel_categories"
																	+ map);

													Global_variable.hotel_listData
															.add(map);

													System.out
															.println("value_before_clear"
																	+ Global_variable.hotel_listData);

													//

												} catch (Exception ex) {
													System.out.println("Error"
															+ ex);
												}
											}

											runOnUiThread(new Runnable() {

												@Override
												public void run() {
													// TODO Auto-generated
													// method
													// stub
													ly_Not_Hotel
															.setVisibility(View.GONE);
													Hotel_list_listviw
															.setVisibility(View.VISIBLE);
													if (Global_variable.hotel_listData != null
															|| Global_variable.hotel_listData
																	.size() != 0) {
														listview_adapter = new ListViewAdapter(
																Search_Restaurant_List.this,
																Global_variable.hotel_listData);
														System.out
																.println("pankaj_inside_hotel_list");
														if (listview_adapter != null) {
															Hotel_list_listviw
																	.setAdapter(listview_adapter);
															listview_adapter
																	.notifyDataSetChanged();
															System.out
																	.println("pankaj_inside_list_adapter");
															Hotel_list_listviw
																	.invalidateViews();

														}

													} else {
														System.out
																.println("pankaj_inside_else");
														if (Global_variable.hotel_listData != null
																|| Global_variable.hotel_listData
																		.size() != 0) {
															Global_variable.hotel_listData
																	.clear();
															listview_adapter = new ListViewAdapter(
																	Search_Restaurant_List.this,
																	Global_variable.hotel_listData);
															Hotel_list_listviw
																	.setAdapter(listview_adapter);

															listview_adapter
																	.notifyDataSetChanged();
														}
														// Hotel_list_listviw.invalidateViews();
													}

												}

											});

										}
									}
								} else {

									if (progressDialog.isShowing()) {
										progressDialog.dismiss();
									}
									// Toast.makeText(getApplicationContext(),
									// "hello there is issue",
									// Toast.LENGTH_LONG).show();

									// error by pankaj
									System.out.println("pankajsakariyadata"
											+ data);
									// if (data == (null)) {
									// ly_Not_Hotel.setVisibility(View.VISIBLE);
									// Hotel_list_listviw.setVisibility(View.GONE);
									// System.out.println("pankajsakariyadata1234");
									// }
									if (data == null) {

										runOnUiThread(new Runnable() {

											@Override
											public void run() {

												// listview_adapter
												// .notifyDataSetChanged();

												if (Global_variable.hotel_listData != null) {
													Global_variable.hotel_listData
															.clear();
												}

												// Hotel_list_listviw.invalidateViews();
												ly_Not_Hotel
														.setVisibility(View.VISIBLE);
												Hotel_list_listviw
														.setVisibility(View.GONE);
												System.out
														.println("pankajsakariyadata123");

												// if (listview_adapter != null)
												// {
												// Global_variable.hotel_listData.clear();
												// listview_adapter = new
												// ListViewAdapter(
												// Search_Restaurant_List.this,
												// Global_variable.hotel_listData);
												// Hotel_list_listviw
												// .setAdapter(listview_adapter);

												// }

											}
										});
									} else {

										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												ly_Not_Hotel
														.setVisibility(View.GONE);
												Hotel_list_listviw
														.setVisibility(View.VISIBLE);
												if (Global_variable.hotel_listData != null
														|| Global_variable.hotel_listData
																.size() != 0) {
													listview_adapter = new ListViewAdapter(
															Search_Restaurant_List.this,
															Global_variable.hotel_listData);
													System.out
															.println("pankaj_inside_hotel_list");
													if (listview_adapter != null) {
														Hotel_list_listviw
																.setAdapter(listview_adapter);
														listview_adapter
																.notifyDataSetChanged();
														System.out
																.println("pankaj_inside_list_adapter");
														Hotel_list_listviw
																.invalidateViews();

													}

												} else {
													System.out
															.println("pankaj_inside_else");
													if (Global_variable.hotel_listData != null
															|| Global_variable.hotel_listData
																	.size() != 0) {
													Global_variable.hotel_listData
															.clear();
													listview_adapter = new ListViewAdapter(
															Search_Restaurant_List.this,
															Global_variable.hotel_listData);
													Hotel_list_listviw
															.setAdapter(listview_adapter);

													listview_adapter
															.notifyDataSetChanged();
													}
													// Hotel_list_listviw.invalidateViews();
												}

											}

										});

									}

								}

								try {
									System.out
											.println("!!!!!!!!!!!!!!!!!!!!!!!!user_id"
													+ SharedPreference
															.getuser_id(getApplicationContext()));

									// System.out
									// .println("!!!!!!!!!!!!!!!!!!!!!!!!user_loyalty_pts"
									// + json.getString("user_loyalty_pts"));

									System.out
											.println("!!!!!!!!!!!!!!!!!!!!!!!!user_loyalty_pts fetch"
													+ SharedPreference
															.get_user_loyalty_pts(getApplicationContext()));
									if (SharedPreference
											.getuser_id(getApplicationContext()) != "") {
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {

											SharedPreference
													.set_user_loyalty_pts(
															getApplicationContext(),
															json.getString("user_loyalty_pts"));

											System.out
													.println("!!!!!!!!!!!!!!!!!!!!!!!!user_loyalty_pts fetch_after_setting"
															+ SharedPreference
																	.get_user_loyalty_pts(getApplicationContext()));

											rf_home_screen_header_loyalty_icon
													.setText(SharedPreference
															.get_user_loyalty_pts(getApplicationContext()));

											// System.out.println("user_id"
											// +
											// fetch_hotel_detail);
										}
									} else {

									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							if (progressDialog.isShowing()) {
								progressDialog.dismiss();
							}
						}

					}

				});

			}

			catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// if (city_flag == false) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			// } else {
			//
			// }
		}
	}

	public class async_district_from_city extends AsyncTask<Void, Void, Void> {
		String responseText = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			CheckNetworkandSQlitecall = true;
			district_spinner.setClickable(false);

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// Check for success tag
			int success;
			JSONObject fetch_spinner = new JSONObject();
			try {
				fetch_spinner.put("type", "District");
				// System.out.println("fix_city_spinner"+fetch_spinner);
				if (str_city_id != null) {
					fetch_spinner.put("parent_id", str_city_id);
				} else {
					fetch_spinner.put("parent_id", "");
				}

				fetch_spinner.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				System.out.println("fetch_spinner_wjbty_city_id" + str_city_id);
				System.out.println("city_session_id" + fetch_spinner);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("fetch_spinner_wjbty_final" + fetch_spinner);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_Geosearch_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(fetch_spinner.toString(),
						"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);

				// ****** response text
				try {
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("spinner_last_text" + responseText);
					Log.d("Insert: ", "Inserting ..");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String currentTimeStamp = dateFormat.format(new Date()); // Find
																				// todays
																				// date

					System.out.println("Timestamp" + currentTimeStamp);

					System.out.println("Current Time" + currentTimeStamp);
					DBAdapter.addData(new DatabaseData(str_city_id,
							responseText, currentTimeStamp));

				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				} catch (NullPointerException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
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

			JSONObject json = new JSONObject();
			try {
				if (CheckNetworkandSQlitecall) {
					// if (progressDialog.isShowing())
					// {
					// progressDialog.dismiss();
					// }
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
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// json = new JSONObject(responseText);
				//
				// System.out.println("spinner_last_json" + json);

				// json success tag
				String success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				// JSONObject data = json.getJSONObject("data");
				JSONArray response_array = json.getJSONArray("data");
				System.out.println("rsponse_get_parameter" + response_array);
				int length = response_array.length();
				System.out.println("length" + length);
				Retaurant_Array = new String[length];
				for (int i = 0; i < length; i++) {
					try {
						JSONObject array_Object = response_array
								.getJSONObject(i);
						String str_District_Name = array_Object
								.getString("name");
						System.out.println("Vtype_str_District_Name"
								+ str_District_Name);
						String str_District_Id = array_Object.getString("id");
						System.out.println("Vtype_str_District_Id"
								+ str_District_Id);
						// Retaurant_Array[i] =vtype.toString();
						// System.out.println("Retaurant_Array["+i+"]"+Retaurant_Array[i]);
						Global_variable.hashmap_district.put(str_District_Name,
								str_District_Id);
						System.out.println("Murtuza nalavala"
								+ Global_variable.hashmap_district);

					} catch (Exception ex) {
						System.out.println("Error" + ex);
					}
				}
				System.out.println("wjbty_search_restaurant_district_list"
						+ Global_variable.hashmap_district);

				/** Convert Hashmap to array */
				Global_variable.District_Array = new String[Global_variable.hashmap_district
						.size() + 1];
				String[] values = new String[Global_variable.hashmap_district
						.size() + 1];
				Global_variable.District_Array[0] = Global_variable.Default_District_String;
				int index = 1;
				for (Entry<String, String> mapEntry : Global_variable.hashmap_district
						.entrySet()) {
					Global_variable.District_Array[index] = mapEntry.getKey();
					values[index] = mapEntry.getValue();
					index++;
				}

				System.out.println("str_array_district_array"
						+ Global_variable.District_Array);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/** Set District Spinner */
			if (Global_variable.District_Array != null) {
				/** Set Adapter to district spinner */
				mto_adapter = new ArrayAdapter<String>(
						Search_Restaurant_List.this,
						android.R.layout.simple_spinner_item,
						Global_variable.District_Array);
				mto_adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				district_spinner.setAdapter(mto_adapter);
				district_spinner.setSelection(0);
				district_spinner.setClickable(true);
				flag_district = true;
			}
		}
	}

	public class async_city_Spinner extends AsyncTask<Void, Void, Void> {
		JSONObject city_jsonobj;
		String responseText;
		ProgressDialog city_spinner_dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			System.out.println("In city async....!!");

			city_spinner_dialog = new ProgressDialog(
					Search_Restaurant_List.this);
			city_spinner_dialog.setIndeterminate(false);
			city_spinner_dialog.setCancelable(true);
			city_spinner_dialog.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		protected Void doInBackground(Void... params) {
			try {

				try {
					city_jsonobj = new JSONObject();
					city_jsonobj.put("type", "City");
					System.out.println("city_spinner" + city_jsonobj);
					if (str_region_id != null) {
						city_jsonobj.put("parent_id", str_region_id);
					} else {
						city_jsonobj.put("parent_id", "");
					}

					System.out.println("region_spinner" + city_jsonobj);
					city_jsonobj
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("city_session_id" + city_jsonobj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// updating UI from Background Thread

				// *************
				// for request
				responseText = http.connection(Search_Restaurant_List.this,
						Global_variable.rf_Geosearch_api_path, city_jsonobj);

			} catch (NullPointerException e2) {
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
					// Select_city_textview.setText(R.string.No_City);
					City_Array = new String[length];
				} else {

					try {
						// Select_city_textview.setText(R.string.Register_Select_City);
						// select_district.setText(R.string.Select_District);

						String city_name = null;

						City_Array = new String[length];
						Global_variable.City_Array = new String[length];
						// Hashmap_City = new HashMap<String, String>();
						for (int i = 0; i < length; i++) {

							try {
								JSONObject array_Object = data.getJSONObject(i);
								city_name = array_Object.getString("name");
								System.out.println("city_name" + city_name);
								String city_id = array_Object.getString("id");
								System.out.println("city_id" + city_id);
								// Hashmap_City.put(city_name, city_id);
								City_Array[i] = city_name.toString();
								System.out.println("City_Array[" + i + "]"
										+ City_Array[i]);
								Global_variable.City_Array[i] = city_name
										.toString();
								Global_variable.hashmap_cities.put(city_name,
										city_id);

							} catch (Exception ex) {
								System.out.println("Error" + ex);
							}
						}

						city_spinner.setText(data.getJSONObject(0).getString(
								"name"));

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!one"
										+ Global_variable.City_Array.length);

						/** Set Default Value to City spinner */

						setcity_adapter();
						// city_spinner();

						// if (Global_variable.City_Array != null) {
						// /** Set Adapter to city spinner */
						//
						// System.out
						// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!one.."
						// + Global_variable.City_Array.length);
						// city_adapter = new ArrayAdapter<String>(
						// Search_Restaurant_List.this,
						// android.R.layout.simple_spinner_item,
						// Global_variable.City_Array);
						//
						// System.out
						// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!two.."
						// + Global_variable.City_Array.length);
						//
						// city_adapter
						// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						//
						// System.out
						// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!three.."
						// + Global_variable.City_Array.length);
						//
						// city_spinner.setAdapter(city_adapter);
						//
						// System.out
						// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!four.."
						// + Global_variable.City_Array.length);
						//
						// city_spinner.setSelection(int_City_Position);
						//
						// System.out
						// .println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!five.."
						// + Global_variable.City_Array.length);
						//
						// } else {
						//
						// }

					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					}

					city_spinner.setClickable(true);

					// System.out.println("!!!!!!!In async...City"+City_Array+"...."+selectedcity+"....."+City_Array[selectedcity]);
					//
					// Select_city_textview
					// .setText(Global_variable.City_Array[selectedcity]);

					// city_spinner.setClickable(true);
				}

				// region_flag=true;

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

				region_spinner.setClickable(true);

			}

			catch (Exception e) {
				e.printStackTrace();
			}
			if (city_spinner_dialog.isShowing()) {
				city_spinner_dialog.dismiss();
			}

		}

		private void setcity_adapter() {
			// TODO Auto-generated method stub

			try {

				str_select_city = null;
				city_id = null;

				if (!city_spinner
						.getText()
						.toString()
						.equalsIgnoreCase(
								getString(R.string.Register_Select_City))) {
					String city_name = city_spinner.getText().toString();

					int index = -1;
					for (int i = 0; i < Global_variable.City_Array.length; i++) {
						if (Global_variable.City_Array[i].equals(city_name)) {
							index = i;
							int_City_Position = index;
							break;
						}
					}

					System.out.println("!!!!!!!!!!!!" + index);
				}

				if (city_spinner.getText().toString() != str_select_city) {
					// city_spinner
					// .setText(str_select_city);
					// System.out.println("textview_cityset"
					// + str_select_city);
					str_city_id = (String) Global_variable.hashmap_cities
							.get(city_spinner.getText().toString());
					// district_spinner.setClickable(true);

					System.out.println("hotel_list_sessid_city_spinner");

					if (flag_call) {

						System.out.println("ishita...1");

						System.out.println("hotel_list_sessid_str_spinner"
								+ str_city_spinner);
						// if(str_city_spinner.equals(city_spinner.getSelectedItem().toString()))
						{
							System.out.println("async call");
							runOnUiThread(new Runnable() {
								public void run() {

									if (global_flag == true) {

										// if (Global_variable.hashmap_district
										// != null) {
										// Global_variable.hashmap_district
										// .clear();
										// DBCheckCityAvaibility(str_city_id);
										// new async_district_from_city()
										// .execute();

										city_flag = true;
										/** check Internet Connectivity */
										if (cd.isConnectingToInternet()) {

											new async_Search_Restaurant()
													.execute();

										} else {

											runOnUiThread(new Runnable() {

												@Override
												public void run() {

													// TODO Auto-generated
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

									// } else {
									// // global_flag=true;
									// }
								}
							});

						}

					}

				} else {

				}

			} catch (Exception e) {

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
