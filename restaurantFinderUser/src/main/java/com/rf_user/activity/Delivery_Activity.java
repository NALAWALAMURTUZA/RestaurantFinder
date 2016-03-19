package com.rf_user.activity;

import java.util.List;
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
import org.json.JSONTokener;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rf.restaurant_user.R;
import com.rf_user.adapter.Adapter_for_Saved_Address;
import com.rf_user.connection.ServiceHandler;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Delivery_Activity extends Activity implements
		OnMarkerDragListener, OnMapClickListener, OnMapLongClickListener,
		OnMarkerClickListener {
	// Google Map
	private GoogleMap googleMap;
	boolean markerClicked;
	TextView tvLocInfo;
	ImageView Right, Wrong;
	private SupportMapFragment mMapFragment;
	LinearLayout ly_googlemap,ly_No_order_details,main;
	ScrollView Main_delivery_scrollview;
	LatLng latLng;
	MarkerOptions markerOptions;
	// double latitude;
	// double longitude;
	String url_fetch_driver_location_from_google;
	String jsonStr1;
	public static Adapter_for_Saved_Address adapter_for_saved_address;
	EditText House_no_edittext, street_edittext, zipcode_edittext;
	TextView txv_city, txv_district;
	ImageView Next, back, img_googlemap;
	CheckBox checkbox_saved_add;
	int selectedRadioId, str_CR_Radio_Value;
	RadioGroup rg_delivery, rg_delivery_saved_add;
	RadioButton rdbtn_shipping_inf, rdbtn_pickmy_self, adbtn_saved_add;
	String str_radiobtn_value;
	LinearLayout ly_shipping_inf, ly_used_saved_addres, ly_main_header_rdbtn;
	int selectedDistrict = 0;
	boolean dialogShown = false;
	
	ScrollView main_delivery_scrollview;

	/*** Pick my **/

	TextView txv_mobile_verify;
	ImageView pick_my_img_next;
	LinearLayout ly_pickmyself;

	/*** Used saved add **/
	ListView LV_usedsavedadd;
	//ImageView img_next_saved_add;
	TextView txv_hotel_address;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	com.rf_user.connection.GPSTracker gps;

	JSONArray data = new JSONArray();
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.delivery_layout);
		/* create Object* */

		try {

			cd = new ConnectionDetector(getApplicationContext());
			initializeWidgets();
			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {
				new async_Saved_Address().execute();

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

								new async_Saved_Address().execute();

							}
						//} while (cd.isConnectingToInternet() == false);

					}

				});
			}
			
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!Global_variable.str_data_pickup_avail"+Global_variable.str_data_pickup_avail);
			

			if (Global_variable.str_data_pickup_avail.equals("1")
					&& Global_variable.str_data_delivery_avail.equals("1")) {
				
			} else if (Global_variable.str_data_pickup_avail.equals("1")) {
				ly_shipping_inf.setVisibility(View.GONE);
				ly_pickmyself.setVisibility(View.VISIBLE);
				rdbtn_pickmy_self.setChecked(true);
				rdbtn_shipping_inf.setVisibility(View.GONE);
				checkbox_saved_add.setVisibility(View.GONE);
				
				txv_hotel_address
				.setText(Global_variable.str_data_hotel_address);
				

			} else if (Global_variable.str_data_delivery_avail.equals("1")) {
				ly_pickmyself.setVisibility(View.GONE);
				ly_shipping_inf.setVisibility(View.VISIBLE);
				rdbtn_pickmy_self.setVisibility(View.INVISIBLE);
				rdbtn_shipping_inf.setVisibility(View.VISIBLE);
				checkbox_saved_add.setVisibility(View.VISIBLE);
			}
			
			else if (Global_variable.str_data_delivery_avail.equals("0") && Global_variable.str_data_pickup_avail.equals("0")) {
				ly_shipping_inf.setVisibility(View.GONE);
				ly_pickmyself.setVisibility(View.GONE);
				rdbtn_pickmy_self.setVisibility(View.GONE);
				ly_No_order_details.setVisibility(View.VISIBLE);
				rdbtn_shipping_inf.setVisibility(View.GONE);
				checkbox_saved_add.setVisibility(View.GONE);
				main.setVisibility(View.GONE);
			}

			txv_city.setText(Global_variable.FR_City_Name);

			Global_variable.Str_CityId = Global_variable.hashmap_cities
					.get(txv_city.getText().toString());
			System.out.println("Global_variable.Str_CityId"
					+ Global_variable.Str_CityId);
			addListenerOnChkIos();
			try {
				setlistener();
			} catch (NullPointerException n) {

			}
			Global_variable.shipping_tag_delivery_ok = "1"; // shipping
			Global_variable.shipping_tag_addr_type = "custom"; // default custom

			try {
				// Loading map
				initilizeMap();

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
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				// Enable / Disable Compass icon
				googleMap.getUiSettings().setCompassEnabled(true);

				// Enable / Disable Rotate gesture
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				// Enable / Disable zooming functionality
				googleMap.getUiSettings().setZoomGesturesEnabled(true);

				googleMap.setOnMarkerDragListener(this);
				googleMap.setOnMapLongClickListener(this);
				googleMap.setOnMapClickListener(this);
				googleMap.setOnMarkerClickListener(this);
				markerClicked = false;
				// double latitude = 21.543399;
				// double longitude = 39.172849;
				System.out.println("DeliveryActivity_Geocoder"
						+ Global_variable.FR_City_Name);

				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					new GeocoderTask().execute(Global_variable.FR_City_Name);

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



				// lets place some 10 random markers
				// for (int i = 0; i < 1; i++) {
				// // random latitude and logitude
				// double[] randomLocation = createRandLocation(latitude,
				// longitude);
				//
				// // Adding a marker
				// MarkerOptions marker = new MarkerOptions()
				// .position(
				// new LatLng(randomLocation[0], randomLocation[1]))
				// .title("Hello Maps " + i).draggable(true);
				//
				// Log.e("Random", "> " + randomLocation[0] + ", "
				// + randomLocation[1]);
				//
				// // changing marker color
				// if (i == 0)
				// marker.icon(BitmapDescriptorFactory
				// .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				// googleMap.addMarker(marker);
				//
				// // Move the camera to last position with a zoom level
				// }

			} catch (Exception e) {
				e.printStackTrace();
			}

			// int selectedId = rg_delivery.getCheckedRadioButtonId();
			//
			// // find the radiobutton by returned id
			// rdbtn_shipping_inf = (RadioButton) findViewById(selectedId);

			// int selectedId_saved_add =
			// rg_delivery_saved_add.getCheckedRadioButtonId();
			//
			// // find the radiobutton by returned id
			// adbtn_saved_add = (RadioButton)
			// findViewById(selectedId_saved_add);
			
			
//			loadLocale();

			
		} catch (NullPointerException e)

		{
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
		try {
			Payment_Activity.Bool_Apply = false;
			Intent i = new Intent(Delivery_Activity.this, Cart.class);
			startActivity(i);
		} catch (NullPointerException n) {
			n.printStackTrace();
		}

	}

	/******* InitializeWidgets *****/
	private void initializeWidgets() {

		try {

			// imageview
			rg_delivery = (RadioGroup) findViewById(R.id.radio_group_checkout);
			rdbtn_shipping_inf = (RadioButton) findViewById(R.id.radio_btn_Shipping_information);
			rdbtn_pickmy_self = (RadioButton) findViewById(R.id.radio_btn_pick_myself);
			img_googlemap = (ImageView) findViewById(R.id.img_googlemap);
			checkbox_saved_add = (CheckBox) findViewById(R.id.checkbox_savedadd);
			ly_main_header_rdbtn = (LinearLayout) findViewById(R.id.ly_main_header_rdbtn);
			ly_shipping_inf = (LinearLayout) findViewById(R.id.ly_shiping_information);
			ly_pickmyself = (LinearLayout) findViewById(R.id.ly_pickmy_self);
			ly_used_saved_addres = (LinearLayout) findViewById(R.id.ly_used_savedadd);
			ly_googlemap = (LinearLayout) findViewById(R.id.google_map_layout);
			Main_delivery_scrollview = (ScrollView) findViewById(R.id.main_delivery_scrollview);

			/************ Shipping info **************/
			House_no_edittext = (EditText) findViewById(R.id.House_no_edittext);
			street_edittext = (EditText) findViewById(R.id.street_edittext);
			zipcode_edittext = (EditText) findViewById(R.id.zipcode_edittext);
			txv_city = (TextView) findViewById(R.id.txv_city);
			txv_district = (TextView) findViewById(R.id.txv_district);
			Next = (ImageView) findViewById(R.id.img_next);
			/************ Google map **************/
			tvLocInfo = (TextView) findViewById(R.id.locinfo);
			Right = (ImageView) findViewById(R.id.yes);
			Wrong = (ImageView) findViewById(R.id.wrong);

			/************ Pick Myself **************/
			pick_my_img_next = (ImageView) findViewById(R.id.pick_my_img_next);
			txv_mobile_verify = (TextView) findViewById(R.id.txv_mobile_verify);
			txv_hotel_address = (TextView) findViewById(R.id.txv_hotel_address);

			/************ Used saved add **************/
			LV_usedsavedadd = (ListView) findViewById(R.id.LV_usedsavedadd);
			main=(LinearLayout)findViewById(R.id.main);
			ly_No_order_details=(LinearLayout)findViewById(R.id.ly_No_order_details);
			
			//img_next_saved_add = (ImageView) findViewById(R.id.img_next_saved_add);
			// initilizeMap();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/******* Setlistner *****/
	private void setlistener() {

		try {

			Next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					System.out.println("!!!!!!!!!!!!!!!latitude..."
							+ Global_variable.latitude_del);
					System.out.println("!!!!!!!!!!!!!!!longitude..."
							+ Global_variable.longitude_del);

					if (Global_variable.latitude_del == 0.0
							&& Global_variable.longitude_del == 0.0) {
						gps = new com.rf_user.connection.GPSTracker(
								Delivery_Activity.this);

						// check if GPS enabled
						if (gps.canGetLocation()) {

							Global_variable.latitude_del = gps.getLatitude();
							Global_variable.longitude_del = gps.getLongitude();

							// \n is for new line
//							Toast.makeText(
//									getApplicationContext(),
//									"Your Location is - \nLat: "
//											+ Global_variable.latitude_del
//											+ "\nLong: "
//											+ Global_variable.longitude_del,
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

					if (Global_variable.latitude_del != 0.0
							&& Global_variable.longitude_del != 0.0) {
						Global_variable.Str_Houseno = House_no_edittext
								.getText().toString();
						//Global_variable.Str_DistrictName = txv_district
						//		.getText().toString();
						Global_variable.Str_Street = street_edittext.getText()
								.toString();
						Global_variable.route = street_edittext.getText()
								.toString();
						System.out.println("Global_variable.Str_Houseno"
								+ Global_variable.Str_Houseno);
						if (Global_variable.Str_Houseno.length() == 0) {
							Toast.makeText(Delivery_Activity.this,
									R.string.blank_house_number,
									Toast.LENGTH_LONG).show();
							House_no_edittext.requestFocus();
							System.out
									.println("Global_variable.Str_DistrictName"
											+ Global_variable.Str_DistrictName);
						} else if (Global_variable.Str_Street.length() == 0) {
							Toast.makeText(Delivery_Activity.this,
									R.string.blank_street, Toast.LENGTH_LONG)
									.show();
							House_no_edittext.requestFocus();
							System.out
									.println("Global_variable.Str_DistrictName"
											+ Global_variable.Str_DistrictName);
						}

//						else if (Global_variable.Str_DistrictName.equals("")
//								|| Global_variable.Str_DistrictName
//										.equals("Select City")) {
//
//							Toast.makeText(Delivery_Activity.this,
//									"Select City", Toast.LENGTH_LONG).show();
//							txv_district.requestFocus();
//						}
						else {
							Global_variable.Str_Houseno = House_no_edittext
									.getText().toString();
							System.out.println("Str_Houseno"
									+ Global_variable.Str_Houseno);
							Global_variable.Str_Street = street_edittext
									.getText().toString();
							System.out.println("Str_Street"
									+ Global_variable.Str_Street);
							Global_variable.route = street_edittext.getText()
									.toString();
							Global_variable.Str_CityName = txv_city.getText()
									.toString();
							System.out.println("Str_CityName"
									+ Global_variable.Str_CityName);
							//Global_variable.Str_DistrictName = txv_district
							//		.getText().toString();
							Global_variable.Str_Zipcode = zipcode_edittext
									.getText().toString();
							System.out.println("Str_Zipcode"
									+ Global_variable.Str_Zipcode);
							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {
								
								
								if(rdbtn_pickmy_self.isChecked())
								{
									
									System.out.println("In if...."+rdbtn_pickmy_self.isChecked());
									
									Global_variable.shipping_tag_delivery_ok="0";
								}
								else
								{
									System.out.println("In else...."+rdbtn_pickmy_self.isChecked());
									Global_variable.shipping_tag_delivery_ok="1";
								}
								

								if (Checkout_Tablayout.language.equals("ar")) {
									Checkout_Tablayout.tab.getTabWidget()
											.getChildAt(3).setClickable(true);
									Checkout_Tablayout.tab.setCurrentTab(3);
								} else {
									Checkout_Tablayout.tab.setCurrentTab(1);
									Checkout_Tablayout.tab.getTabWidget()
											.getChildAt(1).setClickable(true);
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
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.str_gpsoff), Toast.LENGTH_LONG).show();

						gps.showSettingsAlert();
					}

				}
			});
			img_googlemap.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {
						
						System.out.println("hotel_list_sessid_lat_del_map"+Global_variable.latitude);
						System.out.println("hotel_list_sessid_long_del_map"+Global_variable.longitude);

						System.out.println("hotel_list_sessid_!!!!!!!!!!!!!!!latitude..."
								+ Global_variable.latitude_del);
						System.out.println("hotel_list_sessid_!!!!!!!!!!!!!!!longitude..."
								+ Global_variable.longitude_del);
						if (Global_variable.latitude_del == 0.0
								&& Global_variable.longitude_del == 0.0) {
							gps = new com.rf_user.connection.GPSTracker(
									Delivery_Activity.this);

							// check if GPS enabled
							if (gps.canGetLocation()) {

								Global_variable.latitude_del = gps.getLatitude();
								Global_variable.longitude_del = gps.getLongitude();

								// \n is for new line
								Toast.makeText(
										getApplicationContext(),
										"Your Location is - \nLat: "
												+ Global_variable.latitude_del
												+ "\nLong: "
												+ Global_variable.longitude_del,
										Toast.LENGTH_LONG).show();
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

						if (Global_variable.latitude_del != 0.0
								&& Global_variable.longitude_del != 0.0) {
							ly_main_header_rdbtn.setVisibility(View.GONE);
							Main_delivery_scrollview.setVisibility(View.GONE);
							ly_used_saved_addres.setVisibility(View.GONE);
							ly_googlemap.setVisibility(View.VISIBLE);
						} else {
							Toast.makeText(getApplicationContext(),
									getString(R.string.str_gpsoff), Toast.LENGTH_LONG)
									.show();

							gps.showSettingsAlert();
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
			pick_my_img_next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							/* Ishita yadav */
							if (Checkout_Tablayout.language.equals("ar")) {
								Checkout_Tablayout.tab.getTabWidget()
										.getChildAt(3).setClickable(true);
								Checkout_Tablayout.tab.setCurrentTab(3);
							} else {
								
								if(rdbtn_pickmy_self.isChecked())
								{
									
									System.out.println("In if...."+rdbtn_pickmy_self.isChecked());
									
									Global_variable.shipping_tag_delivery_ok="0";
								}
								else
								{
									System.out.println("In else...."+rdbtn_pickmy_self.isChecked());
									Global_variable.shipping_tag_delivery_ok="1";
								}
								
								
								Checkout_Tablayout.tab.setCurrentTab(1);
								Checkout_Tablayout.tab.getTabWidget()
										.getChildAt(1).setClickable(true);
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
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});
			// img_googlemap.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			//
			// Global_variable.Str_Houseno = House_no_edittext.getText()
			// .toString();
			// Global_variable.Str_DistrictName = txv_district.getText()
			// .toString();
			// System.out.println("Global_variable.Str_Houseno"
			// + Global_variable.Str_Houseno);
			// if (Global_variable.Str_Houseno.equals("")) {
			// Toast.makeText(Delivery_Activity.this,
			// R.string.blank_house_number, Toast.LENGTH_LONG)
			// .show();
			// House_no_edittext.requestFocus();
			// } else if (Global_variable.Str_DistrictName.equals("")
			// || Global_variable.Str_DistrictName.equals("District")) {
			// Toast.makeText(Delivery_Activity.this, "Select District",
			// Toast.LENGTH_LONG).show();
			// txv_district.requestFocus();
			// } else {
			// Global_variable.Str_Houseno = House_no_edittext.getText()
			// .toString();
			// System.out.println("Str_Houseno"
			// + Global_variable.Str_Houseno);
			// Global_variable.Str_Street = street_edittext.getText()
			// .toString();
			// System.out.println("Str_Street"
			// + Global_variable.Str_Street);
			// Global_variable.Str_CityName = txv_city.getText()
			// .toString();
			// System.out.println("Str_CityName"
			// + Global_variable.Str_CityName);
			// Global_variable.Str_DistrictName = txv_district.getText()
			// .toString();
			// Global_variable.Str_Zipcode = zipcode_edittext.getText()
			// .toString();
			// System.out.println("Str_Zipcode"
			// + Global_variable.Str_Zipcode);
			// // Checkout_Tablayout.tab.setCurrentTab(1);
			// }
			//
			// }
			//
			//
			// });
			// LV_usedsavedadd.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			//
			// Checkout_Tablayout.tab.setCurrentTab(1);
			// }
			// });

			txv_district.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						runOnUiThread(new Runnable() {
							public void run() {

								District();

							}
						});
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			Right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// googleMap.getView().setVisibility(View.INVISIBLE);
					// getFragmentManager().beginTransaction().hide(googleMap).commit();
					// googleMap.

					ly_googlemap.setVisibility(View.GONE);
					ly_main_header_rdbtn.setVisibility(View.VISIBLE);
					Main_delivery_scrollview.setVisibility(View.VISIBLE);
					ly_used_saved_addres.setVisibility(View.GONE);
					url_fetch_driver_location_from_google = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ Global_variable.latitude_del
							+ ","
							+ Global_variable.longitude_del + "&sensor=true";
					
					
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!url_fetch_driver_location_from_google"+url_fetch_driver_location_from_google);
					
					
					
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						new fetch_driver_location_from_google().execute();

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
			Wrong.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						ly_googlemap.setVisibility(View.GONE);
						ly_main_header_rdbtn.setVisibility(View.VISIBLE);
						Main_delivery_scrollview.setVisibility(View.VISIBLE);
						ly_used_saved_addres.setVisibility(View.GONE);
						url_fetch_driver_location_from_google = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
								+ Global_variable.latitude_del
								+ ","
								+ Global_variable.longitude_del + "&sensor=true";
						
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!URL"+url_fetch_driver_location_from_google);
						
						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							new fetch_driver_location_from_google().execute();
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
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			rg_delivery
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							switch (checkedId) {

							case R.id.radio_btn_Shipping_information:
								// do operations specific to this selection
								// checkbox_saved_add.setClickable(true);
								Global_variable.shipping_tag_delivery_ok = "1";
								Main_delivery_scrollview
										.setVisibility(View.VISIBLE);
								checkbox_saved_add.setVisibility(View.VISIBLE);
								ly_pickmyself.setVisibility(View.GONE);
								ly_shipping_inf.setVisibility(View.VISIBLE);
								ly_used_saved_addres.setVisibility(View.GONE);
								break;

							case R.id.radio_btn_pick_myself:
								
								System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Global_variable.str_data_hotel_address"+Global_variable.str_data_hotel_address);
								
								txv_hotel_address
										.setText(Global_variable.str_data_hotel_address);
								Global_variable.shipping_tag_delivery_ok = "0";
								Main_delivery_scrollview
										.setVisibility(View.VISIBLE);
								checkbox_saved_add.setVisibility(View.GONE);
								checkbox_saved_add.setChecked(isFinishing());
								ly_pickmyself.setVisibility(View.VISIBLE);
								ly_shipping_inf.setVisibility(View.GONE);
								ly_used_saved_addres.setVisibility(View.GONE);
								// do operations specific to this selection
								break;

							}

						}
					});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void addListenerOnChkIos() {

		try {

			checkbox_saved_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// is chkIos checked?
					if (((CheckBox) v).isChecked()) {
//					 rdbtn_shipping_inf.setChecked(true);
						try {
							Global_variable.shipping_tag_addr_type = "pre";
							System.out.println("IN_checkbox_saved_add" + data);
							if (data.length() == 0) {
								System.out.println("IN_checkbox_saved_add1"
										+ data);
								if (cd.isConnectingToInternet()) {
									System.out.println("IN_checkbox_saved_add2"
											+ data);
									new async_Saved_Address().execute();
									System.out.println("IN_checkbox_saved_add3"
											+ data);
								} else {
									runOnUiThread(new Runnable() {

										@Override
										public void run() {

											// TODO Auto-generated method stub
											Toast.makeText(
													getApplicationContext(),
													R.string.No_Internet_Connection,
													Toast.LENGTH_SHORT).show();
											//do {
												System.out
														.println("IN_checkbox_saved_add_do-while");
												if (cd.isConnectingToInternet()) {

													System.out
															.println("IN_checkbox_saved_add4"
																	+ data);
													new async_Saved_Address()
															.execute();
													System.out
															.println("IN_checkbox_saved_add5"
																	+ data);

												}
//											} while (cd
//													.isConnectingToInternet() == false);

										}

									});
								}
							}
							ly_used_saved_addres.setVisibility(View.VISIBLE);
							// ly_shipping_inf.setVisibility(View.GONE);
							// ly_pickmyself.setVisibility(View.GONE);
							Main_delivery_scrollview.setVisibility(View.GONE);
						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					} else {
						try {
							Global_variable.shipping_tag_addr_type = "custom";
							ly_used_saved_addres.setVisibility(View.GONE);
							Main_delivery_scrollview
									.setVisibility(View.VISIBLE);
							// ly_shipping_inf.setVisibility(View.VISIBLE);
							// ly_pickmyself.setVisibility(View.GONE);

						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}

				}
			});

		} catch (NullPointerException e)

		{

		}

	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//		initilizeMap();
//
//		System.out.println("!!!!!!!!!!!!!!!latitude..."
//				+ Global_variable.latitude_del);
//		System.out.println("!!!!!!!!!!!!!!!longitude..."
//				+ Global_variable.longitude_del);
//	}

	private void initilizeMap() {
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

	private void District() {
		try {
			System.out.println("hashamap_district_"
					+ Global_variable.hashmap_district);
			System.out.println("hashamap_city_"
					+ Global_variable.hashmap_cities);
			System.out.println("global_cityarray"
					+ Global_variable.District_Array);
			System.out.println("global_district"
					+ Global_variable.District_Array);
			Builder builder = new AlertDialog.Builder(Delivery_Activity.this);
			builder.setTitle(R.string.Select_District);

			System.out.println("wjbty_city_array_0"
					+ Global_variable.District_Array[selectedDistrict]);
			Global_variable.Str_previous_DistrictName = txv_district.getText()
					.toString();
			Global_variable.Str_previous_DistrictId = (String) Global_variable.hashmap_district
					.get(Global_variable.Str_previous_DistrictName);

			builder.setSingleChoiceItems(Global_variable.District_Array,
					selectedDistrict, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								selectedDistrict = which;
								// str_previous_city = str_select_city;
								Global_variable.Str_DistrictName = Global_variable.District_Array[which];
								System.out.println("Value of City name"
										+ Global_variable.Str_DistrictName);

								Global_variable.Str_DistrictId = (String) Global_variable.hashmap_district
										.get(Global_variable.Str_DistrictName);
								System.out.println("City_id_value"
										+ Global_variable.Str_DistrictId);

								try {

									if (Global_variable.Str_DistrictName != null) {
										txv_district
												.setText(Global_variable.Str_DistrictName);
										System.out
												.println("textview_cityset"
														+ Global_variable.Str_DistrictName);
										dialogShown = false;
										dialog.dismiss();
									} else {
										txv_district
												.setText(Global_variable.Str_DistrictName);
										dialogShown = false;
										dialog.dismiss();
									}
								} catch (Exception e) {

								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
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

		} catch (NullPointerException n) {
           n.printStackTrace();
		}
	}

	/*
	 * creating random postion around a location for testing purpose only
	 */
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}

	public void onMapClick(LatLng point) {
		// tvLocInfo.setText(point.toString());
		googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));

		markerClicked = false;
	}

	@Override
	public void onMapLongClick(LatLng point) {
		// tvLocInfo.setText("New marker added@" + point.toString());
		/*
		 * googleMap.addMarker(new MarkerOptions() .position(point)
		 * .draggable(true));
		 */

		markerClicked = false;
	}

	@Override
	public void onMarkerDrag(Marker marker) {
		// tvLocInfo.setText("Marker " + marker.getId() + " Drag@"
		// + marker.getPosition());
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		// tvLocInfo.setText("Marker " + marker.getId() + " Drag@"
		// + marker.getPosition() + " DragEnd");

		Global_variable.latitude_del = marker.getPosition().latitude;
		Global_variable.longitude_del = marker.getPosition().longitude;

	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		// tvLocInfo.setText("Marker " + marker.getId() + " DragStart");

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		markerClicked = false;
		return false;
	}

	// An AsyncTask class for accessing the GeoCoding Web Service
	private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

		@Override
		protected List<Address> doInBackground(String... locationName) {
			
			
			// Creating an instance of Geocoder class
						Geocoder geocoder = new Geocoder(getBaseContext());
						List<Address> addresses = null;

			try{
			
			System.out.println("delivery_activity.location" + locationName[0]);
			try {
				addresses = geocoder.getFromLocationName(locationName[0], 3);
				while (addresses.size() == 0) {
					addresses = geocoder
							.getFromLocationName(locationName[0], 3);
				}
				if (addresses.size() > 0) {
					Address addr = addresses.get(0);
					Global_variable.latitude_del = (addr.getLatitude());
					Global_variable.longitude_del = (addr.getLongitude());
					System.out.println("delivery_activity.addr" + addr);
					System.out.println("delivery_activity.latt"
							+ Global_variable.latitude_del);
					System.out.println("delivery_activity.long"
							+ Global_variable.longitude_del);

				} else {
					System.out.println("!!!!!!!!in else" + addresses.size());
				}
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}

			// try {
			// // Getting a maximum of 3 Address that matches the input text
			// addresses = geocoder.getFromLocationName(locationName[0], 3);
			// System.out.println("delivery_activity.address"+addresses);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			
			

			
			}
			catch(NullPointerException e)
			{
				
				e.printStackTrace();
			}
			
			return addresses;
			
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			try {
				if (addresses == null || addresses.size() == 0) {
					Toast.makeText(getBaseContext(), getString(R.string.str_no_locatiojn),
							Toast.LENGTH_SHORT).show();
				}

				// Clears all the existing markers on the map
				googleMap.clear();

				// Adding Markers on Google Map for each matching address
				for (int i = 0; i < addresses.size(); i++) {

					Address address = (Address) addresses.get(i);

					// Creating an instance of GeoPoint, to display in Google
					// Map
					latLng = new LatLng(address.getLatitude(),
							address.getLongitude());

					String addressText = String.format(
							"%s, %s",
							address.getMaxAddressLineIndex() > 0 ? address
									.getAddressLine(0) : "", address
									.getCountryName());

					markerOptions = new MarkerOptions();
					markerOptions.position(latLng);
					// markerOptions.title(addressText);
					markerOptions.draggable(true);

					googleMap.addMarker(markerOptions);
					Global_variable.latitude_del = markerOptions.getPosition().latitude;
					Global_variable.longitude_del = markerOptions.getPosition().longitude;
					// Locate the first location
					if (i == 0)
						googleMap.animateCamera(CameraUpdateFactory
								.newLatLng(latLng));
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public class fetch_driver_location_from_google extends
			AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			// p1 = new ProgressDialog(Driver_Home_Screen.this);
			// p1.setMessage("Please wait...");
			// p1.setCancelable(false);
			// p1.show();
			// Global_variable.postal_code="";
			// Global_variable.route="";
			/*
			 * System.out.println("On create 16 " +
			 * Global_variable.global_str_CR_Availability_Status);
			 */
		}

		protected Void doInBackground(Void... params) {

			ServiceHandler sh = new ServiceHandler();
			// Making a request to url and getting response

			/*
			 * System.out.println("On create 16.1 " +
			 * Global_variable.global_str_CR_Availability_Status);
			 */

			// if (TAG_str_CR_Availability_Status.equals("Busy")) {
			// availability_green.setVisibility(View.GONE);
			// availability_red.setVisibility(View.VISIBLE);
			// }
			// else if (TAG_str_CR_Availability_Status.equals("Available")) {
			// availability_red.setVisibility(View.GONE);
			// availability_green.setVisibility(View.VISIBLE);
			//
			// }

			System.out.println("after new method2..google "
					+ Global_variable.latitude_del + "..."
					+ Global_variable.longitude_del);
			jsonStr1 = sh.makeServiceCall(
					"http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ Global_variable.latitude_del + ","
							+ Global_variable.longitude_del + "&sensor=true",
					ServiceHandler.GET);

			System.out
					.println("!!!Url"
							+ "http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ Global_variable.latitude_del + ","
							+ Global_variable.longitude_del + "&sensor=true");
			// System.out.println("!!!!jsonstr1.."+jsonStr1);

			if (jsonStr1 != null) {

				try {

					/*
					 * System.out .println("On create 16.2 " +
					 * Global_variable.global_str_CR_Availability_Status);
					 */
					JSONObject jsonObj = (JSONObject) new JSONTokener(jsonStr1)
							.nextValue();
					JSONArray Results = jsonObj.getJSONArray("results");
					// System.out.println("Result"+Results);
					JSONObject zero = Results.getJSONObject(0);
					JSONArray address_components = zero
							.getJSONArray("address_components");
					for (int i = 0; i < address_components.length(); i++) {

						JSONObject zero2 = address_components.getJSONObject(i);
						String long_name = zero2.getString("long_name");
						JSONArray mtypes = zero2.getJSONArray("types");
						String Type = mtypes.getString(0);
						System.out.println("!!!!!Type" + Type);
						System.out.println("!!!!!long_name" + long_name);

						// System.out.println("!!!!!"+Results+"   "+zero+"   "+address_components+"   "+zero2+"  "+long_name+"  "+mtypes+"  "+Type);

						if (Type.toString().equals("postal_code")) {

							Global_variable.postal_code = long_name;
							System.out.println("!!!!!postal_code"
									+ Global_variable.postal_code);
						}
						if (Type.toString().equals("route")) {
							Global_variable.route = long_name;
							System.out.println("!!!!!route"
									+ Global_variable.route);
						}
						// if
						// (Type.toString().equals("administrative_area_level_1")
						// ) {
						// Global_variable.route = long_name;
						// System.out.println("!!!!!route" +
						// Global_variable.route);
						// }

						/*
						 * if (TextUtils.isEmpty(long_name) == false ||
						 * !long_name.equals(null) || long_name.length() > 0 ||
						 * long_name != "") { // if
						 * (Type.equalsIgnoreCase("street_number")) { //
						 * Address1 = long_name + " ";
						 * System.out.println("!!!!!street_number" + Address1);
						 * // } if (Type.equalsIgnoreCase("route")) { Address1 =
						 * long_name + ""; System.out.println("!!!!!route" +
						 * Address1); } }
						 */
					}

					/*
					 * System.out .println("On create 16.3 " +
					 * Global_variable.global_str_CR_Availability_Status);
					 */
				}

				catch (Exception e) {
					// TODO Auto-generated catch block

					// System.out.println("!!!!!jsonLog"+e.toString());
					e.printStackTrace();
					// System.out.println("!!!!!jsonLog"+e.toString());
				}
			} else {
				System.out.println("jsonstr is null");
			}
			return null;
			// }
		}

		protected void onPostExecute(Void result) {
			street_edittext.setText(Global_variable.route);
			zipcode_edittext.setText(Global_variable.postal_code);
			super.onPostExecute(result);

		}

	}

	public class async_Saved_Address extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			JSONObject Shipping_Request_Main = new JSONObject();
			try {

				Shipping_Request_Main.put("sessid", SharedPreference.getsessid(getApplicationContext()));
				Shipping_Request_Main.put("user_id", SharedPreference.getuser_id(getApplicationContext()));
				System.out.println("Shipping_Request_Child"
						+ Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + Shipping_Request_Main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_GetSavedAddressList_api_path);
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
					json = new JSONObject(responseText);
					System.out.println("Shipping_last_json" + json);
					data = json.getJSONArray("data");
					System.out.println("Shipping_last_text" + responseText);
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

			// try {
			//
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			System.out.println("JSONArray data" + data);
			adapter_for_saved_address = new Adapter_for_Saved_Address(
					Delivery_Activity.this, data);
			LV_usedsavedadd.setAdapter(adapter_for_saved_address);

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
