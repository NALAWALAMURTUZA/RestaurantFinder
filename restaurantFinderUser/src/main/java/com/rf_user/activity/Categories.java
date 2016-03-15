package com.rf_user.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rf.restaurant_user.R;
import com.rf_user.adapter.Categories_ListviewAdapter;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Categories extends Activity {

	ArrayList<String> categories_arraylist;
	/* imagview */
	ImageView img_Categories, img_back, img_hotel_image, img_search,
			footer_ordernow_img, footer_setting_img, rf_view_menu_icon,
			categories_rest_map_selected;
	/* textview */
	TextView txv_hotelname, txv_hotel_addres, txv_minimum_time_order,
			txv_minimum_order, txv_notes;
	HashMap<String, String> hashmap_food_categories_items,
			hashmap_food_categories_items_children;
	Iterator itr_keys_items, itr_keys_children, itr_keys_items_obj;

	/* ratingbar */
	RatingBar rate_service, rate_food, rate_cleanliness;
	AutoCompleteTextView ed_Autocomplete_search;
	ListView lv_food_Categories;
	Categories_ListviewAdapter categories_listviewadapter;
	public ArrayList<Categories_Model> categories_model_arraylist = new ArrayList<Categories_Model>();
	public ArrayList<Categories_model_id> categories_model_arraylist_id = new ArrayList<Categories_model_id>();
	String[] strarray_Categories;
	ProgressDialog progressDialog;
	ArrayList<String> array_Categories_filtring;

	HashMap<String, String> hashmap_item_parent_child;
	ArrayList<HashMap<String, String>> hashmap_item_arraylist_child;
	ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>> hashmap_item_arraylist;

	// ArrayList<ArrayList<HashMap<HashMap<String,
	// String>,ArrayList<HashMap<String, String>>>>> ArrayList_Final_Item = new
	// ArrayList<ArrayList<HashMap<HashMap<String,String>,ArrayList<HashMap<String,String>>>>>();

	/* String */
	String str_children_items;
	String TAG_SUCCESS = "success";
	String str_hotel_name, str_hotel_address, str_hotel_id, str_hotel_iconurl,
			str_hotel_ratingbar, str_minimum_order_time, str_minimum_order,
			str_categories, str_key_items, str_value_items, str_key_children,
			str_value_children, str_key_items_obj, str_value_items_obj;

	/********* String for Api response DATA ********/
	String str_data_hotel_name, str_data_hotel_address, str_data_hotel_id,
			str_data_minimum_ordertime, str_data_delivery_min_order,
			str_data_note, str_data_customer_service_rating,
			str_data_cleanliness_rating, str_data_food_rating, str_data_state,
			str_data_selling_facillities, str_data_pickup_avail,
			str_data_delivery_avail, str_categories_id;
	String[] str_categories_name;
	String[] strarray_categories_id;
	/********* String for Api response Items ********/
	String str_items_name, str_items_full_name, str_items_img, str_items_desc,
			str_items_index, str_items_id, str_items_category_id,
			str_items_spicy_level, str_items_spicy_level_text,
			str_items_currentRatingStats, str_items_vary, str_items_visible,
			str_items_current_ratings, str_items_num_of_ratings,
			str_items_spicy_level_req_on;

	/********* String for Api response Children ********/
	String str_children_price, str_children_name, str_children_full_name,
			str_children_index, str_children_id, str_children_indexs,
			str_children_desc, str_children_spicy_level_req_on;

	String str_search_categories;
	int position;
	com.rf_user.roundimage.RoundImage roundedImage;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	/* TG */
	ImageView img_grabtable, img_reviews, img_fav_default, img_fav_select,
			footer_featured_img, img_gallery, categories_rest_map, img_cross;

	RelativeLayout categories_google_map_layout;
	Intent in;
	/* Google Map declaratrion */
	GoogleMap googleMap;
	MarkerOptions markerOptions;
	LatLng latLng;

	ScrollView categories_scrollview;
	
	/* Language conversion */
	private Locale myLocale;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.view_menu_categories);

		try {

			System.out.println("hotel_list_sessid_lat_category"+Global_variable.latitude);
			System.out.println("hotel_list_sessid_long_category"+Global_variable.longitude);
			categories_arraylist = new ArrayList<String>();
			// get value of hotel
			Intent i = getIntent();
			// str_hotel_id = i.getStringExtra("hotel_id");
			// System.out.println("id_str" + str_hotel_id);
			// str_hotel_iconurl = i.getStringExtra("hotel_iconurl");
			// System.out.println("icon_str" + str_hotel_ratingbar);
			str_hotel_id = Global_variable.hotel_id;
			System.out.println("id_str" + str_hotel_id);
			str_hotel_iconurl = Global_variable.hotel_iconurl;
			System.out.println("icon_str" + str_hotel_ratingbar);
			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			Global_variable.abt_rest_flag = true;

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new async_restaurant_Menu().execute();

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
			initializeWidgets();

			if (SharedPreference.getuser_id(getApplicationContext()) != "") {
				if (Global_variable.user_fav.equalsIgnoreCase("yes")) {

					img_fav_default.setVisibility(View.GONE);
					img_fav_select.setVisibility(View.VISIBLE);

				} else {
					img_fav_default.setVisibility(View.VISIBLE);
					img_fav_select.setVisibility(View.GONE);
				}

			} else {
				if (Global_variable.user_fav.equalsIgnoreCase("yes")) {

					img_fav_default.setVisibility(View.GONE);
					img_fav_select.setVisibility(View.VISIBLE);

				} else {
					img_fav_default.setVisibility(View.VISIBLE);
					img_fav_select.setVisibility(View.GONE);
				}
			}

			if(Global_variable.indine_available!=""||Global_variable.indine_available!=null)
			{
				if (Global_variable.indine_available.equalsIgnoreCase("1")) {
					img_grabtable.setVisibility(View.VISIBLE);

				} else {
					img_grabtable.setVisibility(View.INVISIBLE);
				}
	
			}
			
			
			initializeMap();

			// set value of hotel
			ImageLoader imgLoader = new ImageLoader(getApplicationContext());
			System.out.println("image_listviewadp" + imgLoader);
			imgLoader.DisplayImage(Global_variable.image_url
					+ str_hotel_iconurl, img_hotel_image);
			System.out.println("!!!url" + Global_variable.image_url
					+ str_hotel_iconurl);

			if (array_Categories_filtring != null) {
				categories_listviewadapter = new Categories_ListviewAdapter(
						Categories.this, categories_model_arraylist);
				lv_food_Categories.setAdapter(categories_listviewadapter);
				categories_listviewadapter.notifyDataSetChanged();
				ed_Autocomplete_search.setAdapter(new ArrayAdapter<String>(
						Categories.this,
						android.R.layout.simple_spinner_dropdown_item,
						array_Categories_filtring));
				ed_Autocomplete_search.setThreshold(1);
				System.out.println("pankajsak" + ed_Autocomplete_search);

			}

			else {
				// Toast.makeText(getApplicationContext(), "No data available",
				// Toast.LENGTH_LONG).show();
			}

			// ed_Autocomplete_search.setThreshold(1);
			/* onitem_click_of Autocomplete textview */
			ed_Autocomplete_search.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					str_search_categories = ed_Autocomplete_search.getText()
							.toString().toLowerCase(Locale.getDefault());
					System.out.println("search_categories"
							+ str_search_categories);
					String strautocomplete = ed_Autocomplete_search.getText()
							.toString().toLowerCase(Locale.getDefault());
					System.out.println("search_categories_autocompete"
							+ str_search_categories);

					System.out.println("adapterfill" + str_categories_name);

					categories_listviewadapter.filter(strautocomplete);
					System.out.println("dhamoadii"
							+ categories_listviewadapter.toString());

				}
			});

			setlistener();
			
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

	private void initializeMap() {
		if (googleMap == null) {

			try {

				/*
				 * mMapFragment =
				 * ((SupportMapFragment)getSupportFragmentManager(
				 * ).findFragmentById (R.id.map)); googleMap =
				 * mMapFragment.getMap();
				 * 
				 * mMapFragment.getView().setVisibility(View.INVISIBLE);
				 */
				if (googleMap == null) {

					googleMap = ((MapFragment) getFragmentManager()
							.findFragmentById(R.id.map)).getMap();

					// googleMap = ((MapFragment) getFragmentManager()
					// .findFragmentById(R.id.map)).getMap();

					// check if map is created successfully or not
					if (googleMap == null) {
						Toast.makeText(getApplicationContext(),
								getString(R.string.str_Sorry_map),
								Toast.LENGTH_SHORT).show();
					}

				}

			} catch (NullPointerException e) {

				e.printStackTrace();
			}
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
		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {

			System.out.println("!!!!!!!!!!!!!activity"
					+ Global_variable.activity);
			if (Global_variable.activity != null) {
				if(SharedPreference.getuser_id(
						getApplicationContext())!="")
				{
				if (SharedPreference.getuser_id(getApplicationContext())
						.length() != 0) {
					// if (Global_variable.activity
					// .equalsIgnoreCase("MyFavourites")) {
					// Intent i = new Intent(Categories.this,
					// MyFavourites.class);

					// startActivity(i);
					// } else {
					Intent i = new Intent(Categories.this,
							Search_Restaurant_List.class);

					startActivity(i);
					// }

				}
			}else {
					Intent i = new Intent(Categories.this,
							Search_Restaurant_List.class);
					// i.putExtra("City_Name", Global_variable.FR_City_Name);
					// i.putExtra("City_id", Global_variable.FR_City_id);
					// i.putExtra("City_Position",
					// Global_variable.FR_City_Position);
					// i.putExtra("Delivery_id",
					// Global_variable.FR_Delivery_id);
					// i.putExtra("Delivery_Position",
					// Global_variable.FR_Delivery_Position);
					// i.putExtra("sessid", Global_variable.sessid);
					startActivity(i);
				}
			} else {
				Intent i = new Intent(Categories.this,
						Search_Restaurant_List.class);
				startActivity(i);
			}

		} else {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),
							R.string.No_Internet_Connection, Toast.LENGTH_SHORT)
							.show();

				}

			});
		}

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		try {

			// imageview
			footer_ordernow_img = (ImageView) findViewById(R.id.footer_ordernow_img);
			img_Categories = (ImageView) findViewById(R.id.categories_imageview);
			img_back = (ImageView) findViewById(R.id.back_imageview);
			img_hotel_image = (ImageView) findViewById(R.id.hotel_imageview);
			img_search = (ImageView) findViewById(R.id.categories_search_imageview);

			/* TG */
			img_fav_default = (ImageView) findViewById(R.id.img_fav_default);
			img_fav_select = (ImageView) findViewById(R.id.img_fav_select);
			footer_featured_img = (ImageView) findViewById(R.id.footer_featured_img);
			img_gallery = (ImageView) findViewById(R.id.img_gallery);
			footer_setting_img = (ImageView) findViewById(R.id.footer_setting_img);
			rf_view_menu_icon = (ImageView) findViewById(R.id.rf_view_menu_icon);
			categories_rest_map = (ImageView) findViewById(R.id.categories_rest_map);
			img_cross = (ImageView) findViewById(R.id.img_cross);

			img_grabtable = (ImageView) findViewById(R.id.img_grabtable);
			img_reviews = (ImageView) findViewById(R.id.img_reviews);
			// textview
			txv_minimum_order = (TextView) findViewById(R.id.minimum_order_detail_textview);
			txv_notes = (TextView) findViewById(R.id.notes_detail_textview);
			txv_hotelname = (TextView) findViewById(R.id.hotel_name_textview);
			txv_hotel_addres = (TextView) findViewById(R.id.hotel_address_textview);
			txv_minimum_time_order = (TextView) findViewById(R.id.minimum_time_to_orderdetail_textview);
			// ratingbar
			rate_service = (RatingBar) findViewById(R.id.service_Rating);
			rate_food = (RatingBar) findViewById(R.id.food_Rating);
			rate_cleanliness = (RatingBar) findViewById(R.id.cleanliness_Rating);

			// listview
			lv_food_Categories = (ListView) findViewById(R.id.foodcategories_listview);

			ed_Autocomplete_search = (AutoCompleteTextView) findViewById(R.id.search_autoCompleteTextView);

			categories_scrollview = (ScrollView) findViewById(R.id.categories_scrollview);
			categories_google_map_layout = (RelativeLayout) findViewById(R.id.categories_google_map_layout);
			categories_rest_map_selected = (ImageView) findViewById(R.id.categories_rest_map_selected);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void setlistener() {

		try {

			img_cross.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					categories_scrollview.setVisibility(View.VISIBLE);
					lv_food_Categories.setVisibility(View.VISIBLE);
					categories_google_map_layout.setVisibility(View.GONE);
					categories_rest_map_selected.setVisibility(View.GONE);
					categories_rest_map.setVisibility(View.VISIBLE);

				}
			});

			categories_rest_map_selected.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					categories_scrollview.setVisibility(View.VISIBLE);
					lv_food_Categories.setVisibility(View.VISIBLE);
					categories_google_map_layout.setVisibility(View.GONE);
					categories_rest_map.setVisibility(View.VISIBLE);
					categories_rest_map_selected.setVisibility(View.GONE);

				}
			});

			categories_rest_map.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					System.out.println("hotel_list_sessid_lat_cat_map"+Global_variable.latitude);
					System.out.println("hotel_list_sessid_long_cat_map"+Global_variable.longitude);
					if (Global_variable.hotel_map_lat != 0.0
							&& Global_variable.hotel_map_long != 0.0) {
						
						categories_rest_map_selected.setVisibility(View.VISIBLE);
						categories_rest_map.setVisibility(View.GONE);
						categories_scrollview.setVisibility(View.GONE);
						lv_food_Categories.setVisibility(View.GONE);
						categories_google_map_layout
								.setVisibility(View.VISIBLE);

						// Changing map type
						googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

						// Showing / hiding your current location
						googleMap.setMyLocationEnabled(true);

						// Enable / Disable zooming controls
						googleMap.getUiSettings().setZoomControlsEnabled(false);

						// Enable / Disable my location button
						googleMap.getUiSettings().setMyLocationButtonEnabled(
								true);

						// Enable / Disable Compass icon
						googleMap.getUiSettings().setCompassEnabled(true);

						// Enable / Disable Rotate gesture
						googleMap.getUiSettings()
								.setRotateGesturesEnabled(true);

						// Enable / Disable zooming functionality
						googleMap.getUiSettings().setZoomGesturesEnabled(true);

						// googleMap.setOnMarkerDragListener(this);
						// googleMap.setOnMapLongClickListener(this);
						// googleMap.setOnMapClickListener(this);
						// googleMap.setOnMarkerClickListener(this);
						// markerClicked = false;

						// Map
						latLng = new LatLng(Global_variable.hotel_map_lat,
								Global_variable.hotel_map_long);

						markerOptions = new MarkerOptions();
						markerOptions.position(latLng);
						// markerOptions.title(addressText);
						// markerOptions.draggable(true);

						googleMap.addMarker(markerOptions);

					}

				}
			});

			img_gallery.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (cd.isConnectingToInternet()) {
						Intent in = new Intent(getApplicationContext(),
								RestaurantGallery.class);
						startActivity(in);
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

			footer_setting_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						Intent in = new Intent(getApplicationContext(),
								SettingsScreen.class);
						startActivity(in);
					} 
				}else {
						Toast.makeText(getApplicationContext(),
								R.string.please_login, Toast.LENGTH_SHORT)
								.show();
					}

				}
			});

			img_back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						if (Global_variable.activity != null) {

							if (SharedPreference.getuser_id(
									getApplicationContext()).length() != 0) {
								if (Global_variable.activity
										.equalsIgnoreCase("MyFavourites")) {
									Intent i = new Intent(Categories.this,
											MyFavourites.class);

									startActivity(i);
								} else {
									Intent i = new Intent(Categories.this,
											Search_Restaurant_List.class);

									startActivity(i);
								}

							} else {

								Toast.makeText(getApplicationContext(),
										R.string.please_login,
										Toast.LENGTH_SHORT).show();

								Intent i = new Intent(Categories.this,
										Search_Restaurant_List.class);

								startActivity(i);
							}

						} else {
							Intent i = new Intent(Categories.this,
									Search_Restaurant_List.class);
							startActivity(i);
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
			footer_ordernow_img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global_variable.activity = "Categories";
					if (Global_variable.cart.length() == 0) {
						Toast.makeText(Categories.this, R.string.empty_cart,
								Toast.LENGTH_SHORT).show();
					} else {
						Intent i = new Intent(Categories.this, Cart.class);
						startActivity(i);

					}

				}
			});
			img_search.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/** check Internet Connectivity */
					Global_variable.activity = "Categories";
					if (cd.isConnectingToInternet()) {

						Intent i = new Intent(Categories.this,
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

			lv_food_Categories
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Global_variable.activity = "Categories";

							Global_variable.cat_str_categories_name = categories_model_arraylist
									.get(position).getStr_Categories();
							Global_variable.cat_strarray_categories_id = categories_model_arraylist_id
									.get(position).getStr_categories_id();

							Intent i = new Intent(Categories.this,
									Food_Categories_Details_List.class);
							i.putExtra("str_categories_name",
									categories_model_arraylist.get(position)
											.getStr_Categories());

							System.out.println("on item click name"
									+ categories_model_arraylist.get(position)
											.getStr_Categories());
							i.putExtra("strarray_categories_id",
									categories_model_arraylist_id.get(position)
											.getStr_categories_id());
							System.out.println("on item click id"
									+ categories_model_arraylist_id.get(
											position).getStr_categories_id());

							// i.putExtra(
							// "str_items_name",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_name"));
							// i.putExtra(
							// "str_items_full_name",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_full_name"));
							// i.putExtra(
							// "str_items_img",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_img"));
							// i.putExtra(
							// "str_items_desc",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_desc"));
							// i.putExtra(
							// "str_items_index",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_index"));
							// i.putExtra(
							// "str_items_id",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_id"));
							// i.putExtra(
							// "str_items_spicy_level",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_spicy_level"));
							// i.putExtra(
							// "str_items_spicy_level_text",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_spicy_level_text"));
							// i.putExtra(
							// "str_items_visible",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_visible"));
							// i.putExtra(
							// "str_items_current_ratings",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_current_ratings"));
							// i.putExtra(
							// "str_items_num_of_ratings",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_num_of_ratings"));
							// i.putExtra(
							// "str_items_currentRatingStats",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_currentRatingStats"));
							// i.putExtra(
							// "str_items_spicy_level_req_on",
							// Global_variable.hotel_MenuData.get(position).get(
							// "str_items_spicy_level_req_on"));

							startActivity(i);

						}
					});

			/* TG setlistner */

			img_grabtable.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
						Global_variable.activity = "Categories";

						System.out.println("!!!!!!!Global_variable.login_user_id"
								+ SharedPreference
										.getuser_id(getApplicationContext()));

						if (SharedPreference
								.getuser_id(getApplicationContext()).equals("")) {
							Global_variable.activity = "Categories";
							Intent i = new Intent(Categories.this, Login.class);
							startActivity(i);
							/** check Internet Connectivity */
						} else {
							// if (Global_variable.cart.length() != 0)
							// {

							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {

								Intent iN = new Intent(Categories.this,
										GrabTableActivity.class);
								startActivity(iN);
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

							// }

						}

					} else {
						Toast.makeText(getApplicationContext(),
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}

				}
			});

			img_reviews.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {

						if (SharedPreference
								.getuser_id(getApplicationContext()) != "") {
							if (SharedPreference.getuser_id(
									getApplicationContext()).length() != 0) {
								Global_variable.activity = "Categories";
								// finish();
								// Intent i = new Intent(Categories.this,
								// ReviewPulltoRefresh.class);
								// startActivity(i);

								Intent i = new Intent(Categories.this,
										ReviewActivity.class);
								startActivity(i);

							} else {
								Toast.makeText(getApplicationContext(),
										getString(R.string.please_login),
										Toast.LENGTH_SHORT).show();

							}
						}else {
							Toast.makeText(getApplicationContext(),
									getString(R.string.please_login),
									Toast.LENGTH_SHORT).show();

						}

					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.No_Internet_Connection),
								Toast.LENGTH_SHORT).show();

					}

				}
			});

			img_fav_default.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Global_variable.activity = "Categories";

					Global_variable.activity = "Categories";

					System.out.println("Global_variable.login_user_id in categories"
							+ SharedPreference
									.getuser_id(getApplicationContext()));
					System.out
							.println("Global_variable.login_user_id length in categories"
									+ SharedPreference.getuser_id(
											getApplicationContext()).length());
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() == 0) {
						Toast.makeText(getApplicationContext(),
								R.string.please_login, Toast.LENGTH_SHORT)
								.show();
					} else {
						img_fav_default.setVisibility(View.GONE);
						img_fav_select.setVisibility(View.VISIBLE);
						new InsertFavRestaurants().execute();

						Global_variable.user_fav = "yes";

					}

				}
			});

			img_fav_select.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global_variable.activity = "Categories";

					/* Delete fav implementation */
					System.out.println("Global_variable.login_user_id in categories"
							+ SharedPreference
									.getuser_id(getApplicationContext()));
					System.out
							.println("Global_variable.login_user_id length in categories"
									+ SharedPreference.getuser_id(
											getApplicationContext()).length());
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() == 0) {
						Toast.makeText(getApplicationContext(),
								R.string.please_login, Toast.LENGTH_SHORT)
								.show();
					} else {
						img_fav_select.setVisibility(View.GONE);
						img_fav_default.setVisibility(View.VISIBLE);
						// img_gallery.setVisibility(View.VISIBLE);
						new DeleteFavRestaurants().execute();

						Global_variable.user_fav = "no";

					}

				}
			});

			footer_featured_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						Global_variable.activity = "Categories";

						Intent in = new Intent(getApplicationContext(),
								MyFavourites.class);
						startActivity(in);
					}
				}else {
						Toast.makeText(getApplicationContext(),
								R.string.please_login, Toast.LENGTH_SHORT)
								.show();
					}

				}
			});

			rf_view_menu_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(Categories.this,
								rf_view_menu_icon);
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
											Global_variable.activity = "Categories";

											Intent in = new Intent(
													getApplicationContext(),
													MyFavourites.class);
											startActivity(in);
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

												new UserLogout(Categories.this)
														.execute();

											} 
										}else {

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

	class InsertFavRestaurants extends AsyncTask<String, String, String> {

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
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				String text = null;

				public void run() {

					// Check for success tag
					int success;
					try {
						JSONObject tg_insert_fav_obj = new JSONObject();

						System.out.println("!!!!!!!!!!!!!!!!!!login_user_id.."
								+ SharedPreference
										.getuser_id(getApplicationContext())
								+ "!!!!!!hotel_id.."
								+ Global_variable.hotel_id
								+ "!!!!!!sessid.."
								+ SharedPreference
										.getsessid(getApplicationContext()));
						try {
							if (SharedPreference
									.getuser_id(getApplicationContext()) != "") {
								System.out
										.println("!!!!!!!!!!!!!!!!!!123"
												+ SharedPreference
														.getuser_id(getApplicationContext()));

								tg_insert_fav_obj
										.put("user_id",
												SharedPreference
														.getuser_id(getApplicationContext()));
							} else {
								System.out
										.println("!!!!!!!!!!!!!!!!!!12345"
												+ SharedPreference
														.getuser_id(getApplicationContext()));
								tg_insert_fav_obj.put("user_id", "");
							}
							if (Global_variable.hotel_id != null) {
								tg_insert_fav_obj.put("rest_id",
										Global_variable.hotel_id);
							} else {
								tg_insert_fav_obj.put("rest_id", "");
							}

							tg_insert_fav_obj.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// JSONObject tg_order_datastreams = new JSONObject();
						// tg_order_datastreams.put("", tg_order_obj);
						// System.out.println("tg_order_datastreams"
						// + tg_order_datastreams);

						System.out.println("final_obj" + tg_insert_fav_obj);
						// *************
						// for request
//						DefaultHttpClient httpclient = new DefaultHttpClient();
//						HttpPost httppostreq = new HttpPost(
//								Global_variable.rf_en_Url
//										+ Global_variable.rf_InsertFavRestaurants_api_path);
//						System.out.println("insertfav_post_url" + httppostreq);
//						StringEntity se = new StringEntity(
//								tg_insert_fav_obj.toString(), "UTF-8");
//						System.out.println("insertfav_url_request" + se);
//						se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
//								"application/json"));
//						se.setContentType("application/json;charset=UTF-8");
//						se.setContentEncoding(new BasicHeader(
//								HTTP.CONTENT_TYPE,
//								"application/json;charset=UTF-8"));
//						httppostreq.setEntity(se);
//
//						HttpResponse httpresponse = httpclient
//								.execute(httppostreq);
//
//						String responseText = null;
//
//						// ****** response text
//						try {
//							responseText = EntityUtils.toString(httpresponse
//									.getEntity(), "UTF-8");
//							System.out.println("forget_last_text"
//									+ responseText);
//						} catch (ParseException e) {
//							e.printStackTrace();
//
//							Log.i("Parse Exception", e + "");
//
//						} catch (NullPointerException np) {
//
//						}
//						JSONObject json = new JSONObject(responseText);
//						System.out.println("forget_last_json" + json);
						
						HttpConnection con = new HttpConnection();
						JSONObject json = new JSONObject(
									con.connection(Categories.this,
											Global_variable.rf_InsertFavRestaurants_api_path,
											tg_insert_fav_obj));

						// json success tag
						String success1 = json.getString(TAG_SUCCESS);
						System.out.println("tag" + success1);
						JSONObject data = json.getJSONObject("data");
						String Data_Success = data.getString("success");
						System.out.println("Data tag" + Data_Success);

						/* If success true */
						if (Data_Success.equalsIgnoreCase("true")) {
							String insert_message = data.getString("message");

							Toast.makeText(getApplicationContext(),
									insert_message, Toast.LENGTH_LONG).show();
							// Intent i = new Intent(GrabTableActivity.this,
							// BookingConfirmationScreen.class);
							//
							// i.putExtra("booking_date",
							// txtCalender.getText().toString());
							// i.putExtra("booking_time",
							// txtTime.getText().toString());
							// i.putExtra("number_of_people",
							// txtCalPerson.getText()
							// .toString());
							//
							// startActivity(i);

						}
						/* If success false */
						else if (Data_Success.equalsIgnoreCase("false")) {
							JSONObject Data_Error = data
									.getJSONObject("errors");
							System.out.println("Data_Error" + Data_Error);

							if (Data_Error.has("sessid")) {
								JSONArray Array_sessid = Data_Error
										.getJSONArray("sessid");
								System.out.println("Array_sessid"
										+ Array_sessid);
								String Str_sessid = Array_sessid.getString(0);
								System.out.println("Str_sessid" + Str_sessid);
								if (Str_sessid != null) {
									Toast.makeText(getApplicationContext(),
											Str_sessid, Toast.LENGTH_LONG)
											.show();
								}

							} else if (Data_Error.has("user_id")) {
								JSONArray Array_user_id = Data_Error
										.getJSONArray("user_id");
								System.out.println("Array_user_id"
										+ Array_user_id);
								String Str_user_id = Array_user_id.getString(0);
								System.out.println("Str_user_id" + Str_user_id);
								if (Str_user_id != null) {
									Toast.makeText(getApplicationContext(),
											Str_user_id, Toast.LENGTH_LONG)
											.show();
								}
							} else if (Data_Error.has("rest_id")) {
								JSONArray Array_rest_id = Data_Error
										.getJSONArray("rest_id");
								System.out.println("Array_rest_id"
										+ Array_rest_id);
								String Str_rest_id = Array_rest_id.getString(0);
								System.out.println("Str_rest_id" + Str_rest_id);
								if (Str_rest_id != null) {
									Toast.makeText(getApplicationContext(),
											Str_rest_id, Toast.LENGTH_LONG)
											.show();
								}
							} else if (Data_Error.has("dublicate_entry")) {
								JSONArray Array_dublicate_value = Data_Error
										.getJSONArray("dublicate_entry");
								System.out.println("Array_dublicate_value"
										+ Array_dublicate_value);
								String Str_dublicate_entry = Array_dublicate_value
										.getString(0);
								System.out.println("Str_rest_id"
										+ Str_dublicate_entry);
								if (Str_dublicate_entry != null) {
									// Toast.makeText(getApplicationContext(),
									// Str_dublicate_entry,
									// Toast.LENGTH_LONG).show();
								}
							}

						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (NullPointerException np) {

					}
				}

			});
			//
			return null;
		}

	}

	class DeleteFavRestaurants extends AsyncTask<String, String, String> {

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
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				String text = null;

				public void run() {

					// Check for success tag
					int success;
					try {
						JSONObject tg_insert_fav_obj = new JSONObject();

						System.out.println("!!!!!!!!!!!!!!!!!!login_user_id.."
								+ SharedPreference
										.getuser_id(getApplicationContext())
								+ "!!!!!!hotel_id.."
								+ Global_variable.hotel_id
								+ "!!!!!!sessid.."
								+ SharedPreference
										.getsessid(getApplicationContext()));
						try {
							if (SharedPreference
									.getuser_id(getApplicationContext()) != "") {
								System.out
										.println("!!!!!!!!!!!!!!!!!!123"
												+ SharedPreference
														.getuser_id(getApplicationContext()));

								tg_insert_fav_obj.put("uid", SharedPreference
										.getuser_id(getApplicationContext()));
							} else {
								System.out
										.println("!!!!!!!!!!!!!!!!!!12345"
												+ SharedPreference
														.getuser_id(getApplicationContext()));
								tg_insert_fav_obj.put("uid", "");
							}
							if (Global_variable.hotel_id != null) {
								tg_insert_fav_obj.put("restaurant_id",
										Global_variable.hotel_id);
							} else {
								tg_insert_fav_obj.put("restaurant_id", "");
							}

							tg_insert_fav_obj.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// JSONObject tg_order_datastreams = new JSONObject();
						// tg_order_datastreams.put("", tg_order_obj);
						// System.out.println("tg_order_datastreams"
						// + tg_order_datastreams);

						System.out.println("final_obj" + tg_insert_fav_obj);
						// *************
						// for request
						DefaultHttpClient httpclient = new DefaultHttpClient();
						HttpPost httppostreq = new HttpPost(
								Global_variable.rf_lang_Url
										+ Global_variable.rf_DeleteFavRestaurants_api_path);
						System.out.println("insertfav_post_url" + httppostreq);
						StringEntity se = new StringEntity(
								tg_insert_fav_obj.toString(), "UTF-8");
						System.out.println("insertfav_url_request" + se);
						se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
								"application/json"));
						se.setContentType("application/json;charset=UTF-8");
						se.setContentEncoding(new BasicHeader(
								HTTP.CONTENT_TYPE,
								"application/json;charset=UTF-8"));
						httppostreq.setEntity(se);

						HttpResponse httpresponse = httpclient
								.execute(httppostreq);

						String responseText = null;

						// ****** response text
						try {
							responseText = EntityUtils.toString(httpresponse
									.getEntity(), "UTF-8");
							responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
							System.out.println("forget_last_text"
									+ responseText);
						} catch (ParseException e) {
							e.printStackTrace();

							Log.i("Parse Exception", e + "");

						} catch (NullPointerException np) {

						}
						JSONObject json = new JSONObject(responseText);
						System.out.println("forget_last_json" + json);

						// json success tag
						String success1 = json.getString(TAG_SUCCESS);
						System.out.println("tag" + success1);
						JSONObject data = json.getJSONObject("data");
						String Data_Success = data.getString("success");
						System.out.println("Data tag" + Data_Success);

						/* If success true */
						if (Data_Success.equalsIgnoreCase("true")) {
							String insert_message = data.getString("message");

							Toast.makeText(getApplicationContext(),
									insert_message, Toast.LENGTH_LONG).show();
							// Intent i = new Intent(GrabTableActivity.this,
							// BookingConfirmationScreen.class);
							//
							// i.putExtra("booking_date",
							// txtCalender.getText().toString());
							// i.putExtra("booking_time",
							// txtTime.getText().toString());
							// i.putExtra("number_of_people",
							// txtCalPerson.getText()
							// .toString());
							//
							// startActivity(i);

						}
						/* If success false */
						else if (Data_Success.equalsIgnoreCase("false")) {
							JSONObject Data_Error = data
									.getJSONObject("errors");
							System.out.println("Data_Error" + Data_Error);

							if (Data_Error.has("sessid")) {
								JSONArray Array_sessid = Data_Error
										.getJSONArray("sessid");
								System.out.println("Array_sessid"
										+ Array_sessid);
								String Str_sessid = Array_sessid.getString(0);
								System.out.println("Str_sessid" + Str_sessid);
								if (Str_sessid != null) {
									Toast.makeText(getApplicationContext(),
											Str_sessid, Toast.LENGTH_LONG)
											.show();
								}

							} else if (Data_Error.has("uid")) {
								JSONArray Array_user_id = Data_Error
										.getJSONArray("uid");
								System.out.println("Array_user_id"
										+ Array_user_id);
								String Str_user_id = Array_user_id.getString(0);
								System.out.println("Str_user_id" + Str_user_id);
								if (Str_user_id != null) {
									Toast.makeText(getApplicationContext(),
											Str_user_id, Toast.LENGTH_LONG)
											.show();
								}
							} else if (Data_Error.has("restaurant_id")) {
								JSONArray Array_rest_id = Data_Error
										.getJSONArray("restaurant_id");
								System.out.println("Array_rest_id"
										+ Array_rest_id);
								String Str_rest_id = Array_rest_id.getString(0);
								System.out.println("Str_rest_id" + Str_rest_id);
								if (Str_rest_id != null) {
									Toast.makeText(getApplicationContext(),
											Str_rest_id, Toast.LENGTH_LONG)
											.show();
								}
							}

						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NullPointerException np) {

					}
				}

			});
			//
			return null;
		}

	}

	public class async_restaurant_Menu extends AsyncTask<Void, Void, Void> {
		JSONObject fetch_categories_detail = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray hotel_menu_categories_array = new JSONArray();
		JSONArray hotel_menu_items_array = new JSONArray();
		JSONObject Hotel_menu_items_Object = new JSONObject();
		JSONArray hotel_menu_children_array = new JSONArray();
		JSONObject hotel_menu_children_Object = new JSONObject();
		JSONObject Categories_Array = new JSONObject();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();

			try {
				progressDialog = new ProgressDialog(Categories.this);
				progressDialog.setCancelable(false);
				System.out.println("-11111111");
				progressDialog.show();

				try {
					System.out.println("0000000000");
					System.out.println("id_get" + str_hotel_id);
					fetch_categories_detail.put("id", str_hotel_id);
					System.out.println("hotel_id_cats"
							+ fetch_categories_detail);
					fetch_categories_detail
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("hotel_cats_sessid"
							+ fetch_categories_detail);
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			array_Categories_filtring = new ArrayList<String>();

			try {

				// for request
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_RestaurantMenu_api_path);
				System.out.println("hotel_cats_url..." + Global_variable.rf_lang_Url
						+ Global_variable.rf_RestaurantMenu_api_path);
				StringEntity se = new StringEntity(
						fetch_categories_detail.toString(), "UTF-8");
				System.out.println("hotel_menu_url_request" + se.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_menu_response" + httpresponse);
				String str_Hotel_menu_list = null;

				// ****** response text
				try {
					str_Hotel_menu_list = EntityUtils.toString(httpresponse
							.getEntity(), "UTF-8");
					str_Hotel_menu_list=str_Hotel_menu_list.substring(str_Hotel_menu_list.indexOf("{"), str_Hotel_menu_list.lastIndexOf("}") + 1);
					System.out.println("hotel_menu_list_last_text"
							+ str_Hotel_menu_list);

				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JSONObject main_json = new JSONObject(str_Hotel_menu_list);
				System.out.println("wjbty_wjbty_hotel_list_last_json"
						+ main_json);

				// json success tag
				String success1 = main_json.getString(TAG_SUCCESS);
				System.out.println("success_tag" + success1);

				/******* Main Data json object *********/
				data = main_json.getJSONObject("data");
				System.out.println("wjbty_wjbty_hotel_menu_data_object" + data);

			} catch (JSONException J) {

			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}
			return null;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {

				if (data != null) {

					/****** String from DATA Object *******/
					str_data_hotel_name = data.getString("name");
					Global_variable.str_data_hotel_address = data
							.getString("address");
					str_data_minimum_ordertime = data.getString("min_order");
					str_data_delivery_min_order = data
							.getString("delivery_min_order");
					str_data_note = data.getString("note");
					str_data_customer_service_rating = data
							.getString("customer_service_rating");
					str_data_food_rating = data.getString("food_rating");
					str_data_cleanliness_rating = data
							.getString("cleanliness_rating");
					str_data_state = data.getString("state");
					str_data_selling_facillities = data
							.getString("selling_facilities");
					Global_variable.str_data_pickup_avail = data
							.getString("pickup_avail");
					Global_variable.str_data_delivery_avail = data
							.getString("delivery_avail");
					// System.out.println("hotel_data_delivery_avail"+str_data_delivery_avail);

					/******* second CATEGORIES json ARRAY *********/
					hotel_menu_categories_array = data
							.getJSONArray("categories");
					System.out
							.println("wjbty_wjbty_hotel_menu_Categories_array"
									+ hotel_menu_categories_array.toString()
											.length());

					int length = hotel_menu_categories_array.length();

					System.out.println("hotel_menu_length" + length);
					// Global_variable.array_listhashmap_food_categories_items=new
					// ArrayList<HashMap<String,String>>();
					Global_variable.hotel_MenuData = new ArrayList<HashMap<String, String>>();

					Global_variable.ArrayList_Final_Item = new ArrayList<ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>>>();
					/******* For loop second CATEGORIES json ARRAY *********/
					str_categories_name = new String[length];
					strarray_categories_id = new String[length];

					for (int i = 0; i < length; i++) {

						System.out.println("555555555555 = " + i);
						try {

							/** Object from categories array */

							Categories_Array = hotel_menu_categories_array
									.getJSONObject(i);
							System.out.println("last_rsponse_Cat"
									+ Categories_Array);
							String str_categories_list = Categories_Array
									.getString("name");
							System.out.println("Categoreis_Categories_name_str"
									+ str_categories_list);
							array_Categories_filtring.add(str_categories_list);
							str_categories_name[i] = Categories_Array
									.getString("name");
							System.out.println("Categoreis_Categories_name"
									+ str_categories_name[i]);

							Categories_Model categories_class_model = new Categories_Model(
									str_categories_name[i]);
							System.out.println("classmodel"
									+ categories_class_model);
							categories_model_arraylist
									.add(categories_class_model);

							str_categories_id = Categories_Array
									.getString("id");
							System.out.println("Categoreis_Categories_id"
									+ str_categories_id);
							strarray_categories_id[i] = str_categories_id;
							Categories_model_id categories_class_model_id = new Categories_model_id(
									strarray_categories_id[i]);
							System.out.println("Categoreis_Categories_name_id"
									+ strarray_categories_id[i]);
							System.out.println("classmodel"
									+ categories_class_model_id);
							categories_model_arraylist_id
									.add(categories_class_model_id);
							/******* Third CATEGORIES object to get ITEMS ARRAY *********/
							hotel_menu_items_array = Categories_Array
									.getJSONArray("items");
							hashmap_item_arraylist = new ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>>();
							hashmap_food_categories_items = new HashMap<String, String>();

							System.out
									.println("hotel_menu_items_array00000000000"
											+ hotel_menu_items_array.toString());
							hashmap_food_categories_items_children = new HashMap<String, String>();

							System.out.println("wjbty_wjbty_item_length"
									+ hotel_menu_items_array.length());
							for (int p = 0; p < hotel_menu_items_array.length(); p++) {

								HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>> hashmap_item = new HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>();
								HashMap<String, String> hashmap_item_parent = new HashMap<String, String>();
								itr_keys_items = hotel_menu_items_array
										.getJSONObject(p).keys();

								while (itr_keys_items.hasNext()) {
									str_key_items = (String) itr_keys_items
											.next();
									str_value_items = hotel_menu_items_array
											.getJSONObject(p).getString(
													str_key_items);
									System.out
											.println("chetan_children_item_new"
													+ str_key_items + " := "
													+ str_value_items);
									hashmap_item_parent.put(str_key_items,
											str_value_items);

								} // While End
								System.out.println("hashmap_item_parent"
										+ hashmap_item_parent);
								hotel_menu_children_array = hotel_menu_items_array
										.getJSONObject(p).getJSONArray(
												"children");
								System.out
										.println("hashmap_item_parent_child_length"
												+ hotel_menu_children_array
														.length());
								hashmap_item_arraylist_child = new ArrayList<HashMap<String, String>>();

								for (int l = 0; l < hotel_menu_children_array
										.length(); l++) {
									hashmap_item_parent_child = new HashMap<String, String>();
									itr_keys_children = hotel_menu_children_array
											.getJSONObject(l).keys();
									while (itr_keys_children.hasNext()) {
										str_key_children = (String) itr_keys_children
												.next();
										str_value_children = hotel_menu_children_array
												.getJSONObject(l).getString(
														str_key_children);
										System.out
												.println("chetan_children_inside if"
														+ str_key_children
														+ " := "
														+ str_value_children);
										hashmap_item_parent_child.put(
												str_key_children,
												str_value_children);
									} // End Child While

									hashmap_item_arraylist_child
											.add(hashmap_item_parent_child);

								} // End Child Forloop

								System.out.println("hashmap_item_parent_child"
										+ hashmap_item_parent_child);
								hashmap_item.put(hashmap_item_parent,
										hashmap_item_arraylist_child);
								System.out
										.println("hotel_items_parent_final_first_item_hashmap_ARRAYLIST"
												+ hashmap_item_arraylist_child);
								System.out
										.println("hotel_items_parent_final_first_item_hashmap"
												+ hashmap_item);
								hashmap_item_arraylist.add(hashmap_item);
								System.out.println("chetanpankajmurtu"
										+ hashmap_item_arraylist);
							} // End of parent foor loop

						} catch (Exception ex) {

						}

						System.out.println("champakMurtu"
								+ hashmap_item_arraylist);
						Global_variable.ArrayList_Final_Item
								.add(hashmap_item_arraylist);
						System.out.println("final_ayo_knai"
								+ Global_variable.ArrayList_Final_Item);
					}
					/***** 1st loop *****/

					// System.out.println("champakMurtu"+hashmap_item_arraylist);
					System.out.println("champakMurtu123"
							+ Global_variable.ArrayList_Final_Item);

					// System.out.println("aray_hotel_menu_items_hashmap"+
					// Global_variable.array_listhashmap_food_categories_items);
				}
				/***** IF *****/

			} catch (JSONException J) {

			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

			try {
				System.out.println("111111111111111"
						+ Global_variable.ArrayList_Final_Item);
				txv_hotelname.setText(str_data_hotel_name);
				txv_notes.setText(str_data_note);
				txv_hotel_addres
						.setText(Global_variable.str_data_hotel_address);
				// txv_minimum_time_order.setText(getString(R.string.order) +
				// " "
				// + str_data_minimum_ordertime + " "
				// + getString(R.string.day_before));

				txv_minimum_time_order.setText(str_data_minimum_ordertime);

				txv_minimum_order.setText(getString(R.string.order_sr) + " "
						+ str_data_delivery_min_order + " "
						+ getString(R.string.Riquired));
				try {

					System.out
							.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ratings..."
									+ str_data_customer_service_rating
									+ "!!!"
									+ str_data_food_rating
									+ "!!!"
									+ str_data_cleanliness_rating);

					rate_service.setRating(Float
							.parseFloat(str_data_customer_service_rating));
					rate_food.setRating(Float.parseFloat(str_data_food_rating));
					rate_cleanliness.setRating(Float
							.parseFloat(str_data_cleanliness_rating));
					System.out.println("adapter_categories_name"
							+ str_categories_name);
				} catch (NullPointerException e) {

					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				categories_listviewadapter = new Categories_ListviewAdapter(
						Categories.this, categories_model_arraylist);

				lv_food_Categories.setAdapter(categories_listviewadapter);
				progressDialog.dismiss();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
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
