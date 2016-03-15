package com.example.restaurantadmin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.adapter.OnlineOrderOrderCartAdapter;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class OnlineOrderOrderDetailsActivity extends Activity {
	private TextView ood_txv_customer_name;
	private TextView ood_txv_address;
	private TextView ood_txv_email, txv_order_type;
	private TextView ood_txv_payment_status;
	private TextView ood_txv_contact_no;
	private ListView LV_Cart_Details;
	private TextView ood_txv_order_total;
	JSONObject Obj_Order, Obj_Profile, Obj_Shipment;
	JSONArray array_cart_item;
	OnlineOrderOrderCartAdapter OnlineOrderOrderCartAdapter;
	ConnectionDetector cd;
	String str_firstname, str_lastname, str_email, str_contact, str_address1,
			str_address2, str_address3, str_address4, str_order_status,
			str_total, str_mobile, str_ordertype, shipment_zip,
			str_shipment_lat = "", str_shipment_lon = "";

	private Locale myLocale;
	private ImageView img_map, img_wrong;
	LinearLayout ll_googlemap, ll_main;
	private GoogleMap googleMap;
	LocationManager locationManager;
	MarkerOptions markerOptions;
	LatLng latLng;
	public static double latitude, longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_online_order_order_details);
		initialization();
		cd = new ConnectionDetector(getApplicationContext());

		Bundle b = getIntent().getExtras();
		// *********order object************
		String str_order = b.getString("Order");
		System.out.println("oodstrorder" + str_order);
		try {
			if (str_order != null) {
				Obj_Order = new JSONObject(str_order);
				System.out.println("44oodorder" + Obj_Order);
				str_firstname = Obj_Order.getString("order_first_name");
				str_lastname = Obj_Order.getString("order_last_name");
				str_ordertype = Obj_Order.getString("order_delivery_ok");
				System.out.println("oodfirstname" + str_firstname);
				System.out.println("str_ordertype" + str_ordertype);
				if (!str_ordertype.equalsIgnoreCase("")) {
					// txv_order_type.setText(str_ordertype);
					// service process**********
					if (str_ordertype.equalsIgnoreCase("0")) {
						txv_order_type.setText(getResources().getString(R.string.str_Pickup));
						System.out.println("gyupickup hoyto");
						String redString = getResources().getString(
								R.string.oo_pickup_msg);
						ood_txv_address.setText(Html.fromHtml(redString));
						img_map.setVisibility(View.GONE);
					} else {
						txv_order_type
								.setText(getResources().getString(R.string.step2_delivery));
						img_map.setVisibility(View.VISIBLE);
					}
				}

				str_email = Obj_Order.getString("order_email");
				System.out.println("oodstr_order_email" + str_email);
				str_total = Obj_Order.getString("order_total");
				ood_txv_email.setText(str_email);
				ood_txv_customer_name.setText(str_firstname + " "
						+ str_lastname);
				ood_txv_order_total.setText(Global_variable.euro + " "
						+ str_total);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// *****PROFILE***************
		String str_Profile = b.getString("Profile");
		System.out.println("oodstrProfile" + str_Profile);

		try {
			if (str_Profile != null) {
				Obj_Profile = new JSONObject(str_Profile);
				str_mobile = Obj_Profile.getString("profile_mobile_number");
				System.out.println("oodstr_mobile" + str_mobile);
				ood_txv_contact_no.setText(str_mobile);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ***PROFILE***************
		// *********cartitem object************
		String str_CartItem = b.getString("CartItem");
		System.out.println("oodstrcartiteam" + str_CartItem);

		try {
			if (str_CartItem != null) {
				array_cart_item = new JSONArray(str_CartItem);
				System.out.println("44oodarraycartitem" + array_cart_item);
				if (array_cart_item != null) {
					OnlineOrderOrderCartAdapter = new OnlineOrderOrderCartAdapter(
							OnlineOrderOrderDetailsActivity.this,
							array_cart_item);
					LV_Cart_Details.setAdapter(OnlineOrderOrderCartAdapter);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// *********Shipment object************
		String str_shipment = b.getString("Shipment");
		System.out.println("oodstrstr_shipment" + str_shipment);

		try {
			if (str_shipment != null) {
				Obj_Shipment = new JSONObject(str_shipment);
				System.out.println("33oodObj_Shipment" + Obj_Shipment);

				if (Obj_Shipment != null) {
					str_address1 = Obj_Shipment
							.getString("shipment_address_line_1");
					System.out.println("oodstr_str_address1" + str_address1);

					str_address2 = Obj_Shipment
							.getString("shipment_address_line_2");
					System.out.println("oodstr_str_address2" + str_address2);

					str_address3 = Obj_Shipment.getString("shipment_cname_en");
					System.out.println("oodstr_str_address3" + str_address3);

					str_address4 = Obj_Shipment.getString("shipment_ciname_en");
					System.out.println("oodstr_str_address4" + str_address4);
					shipment_zip = Obj_Shipment.getString("shipment_zip");
					System.out.println("shipment_zip" + shipment_zip);
					str_shipment_lat = Obj_Shipment
							.getString("shipment_map_lat");
					System.out.println("shipment_lat" + str_shipment_lat);
					if (str_shipment_lat.equalsIgnoreCase("")
							|| str_shipment_lat.equalsIgnoreCase("null")) {
						System.out.println("latnull");
					} else {
						System.out.println("lat!=null");
						System.out.println("");
						latitude = Double.valueOf(str_shipment_lat)
								.doubleValue();
						System.out.println("latitude" + latitude);
					}

					str_shipment_lon = Obj_Shipment
							.getString("shipment_map_long");
					System.out.println("shipment_lon" + str_shipment_lon);
					if (str_shipment_lon.equalsIgnoreCase("")
							|| str_shipment_lon.equalsIgnoreCase("null")) {
						System.out.println("longnull");
					} else {
						System.out.println("long!=null");
						longitude = Double.valueOf(str_shipment_lon)
								.doubleValue();
						System.out.println("longitude" + longitude);
					}

					if (str_ordertype.equalsIgnoreCase("1")) {
						ood_txv_address.setText(str_address1.replaceAll("null", "") + ", "
								+ str_address2.replaceAll("null", "") + "\n" + str_address4 + ", "+ Obj_Shipment
								.getString("shipment_regions_en")+", "
								+ str_address3 + " " + shipment_zip.replaceAll("null", ""));
					} else {
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// language*****************
		Locale.getDefault().getLanguage();
		System.out.println("Device_language"
				+ Locale.getDefault().getLanguage());

		String langPref = "Language";
		SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs_oncreat.getString(langPref, "");

		System.out.println("Murtuza_Nalawala_language_oncreat" + language);
		if (language.equalsIgnoreCase("")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if");

		} else if (language.equalsIgnoreCase("ro")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_ar");
			setLocaleonload("ro");

		} else if (language.equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
			setLocaleonload("en");

		} else {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
			setLocaleonload("en");

		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initilizeMap();

//		loadLocale();
//		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
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
	// ********MAP

	private void initilizeMap() {
		googleMap = null;
		if (googleMap == null) {

			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();

				// check if map is created successfully or not
				if (googleMap != null) {

					if (cd.isConnectingToInternet()) {

						displayMap();
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
		}
	}

	/** Display The Current Location on Map */
	private void displayMap() {
		// Enable MyLocation Layer of Google Map
		try {
			googleMap.setMyLocationEnabled(true);

			// Get LocationManager object from System Service LOCATION_SERVICE
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			System.out.println("locationManager" + locationManager);
			// Create a criteria Object to retrieve provider

			//
			Criteria criteria = new Criteria();

			final Location myLocation = locationManager
					.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

			System.out.println("myLocation.." + myLocation);
			latLng = new LatLng(latitude, longitude);

			markerOptions = new MarkerOptions();
			markerOptions.position(latLng);
			googleMap.addMarker(markerOptions);
			// if (myLocation != null) {
			//
			// latitude = myLocation.getLatitude();
			// System.out.println("GPS latitude" + latitude);
			//
			// // Get longitude of Current Location
			// longitude = myLocation.getLongitude();
			// System.out.println("GPS latitude" + longitude);
			//
			// LatLng latLng = new LatLng(latitude, longitude);
			// System.out.println("1111LatLng" + latLng);
			//
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			//
			// onLocationChanged(myLocation);
			//
			// }
			setlistner();
		} catch (NullPointerException ne) {
			ne.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
	}
	// *******************************************************
	private void setlistner() {
		// TODO Auto-generated method stub
		img_map.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!str_shipment_lat.equalsIgnoreCase("")) {
					ll_googlemap.setVisibility(View.VISIBLE);
					ll_main.setVisibility(View.GONE);
				} else {
					Toast.makeText(OnlineOrderOrderDetailsActivity.this,
							R.string.oo_details_nolocation, Toast.LENGTH_LONG)
							.show();
				}

			}
		});
		img_wrong.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_googlemap.setVisibility(View.GONE);
				ll_main.setVisibility(View.VISIBLE);

			}
		});
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
	private void initialization() {
		// TODO Auto-generated method stub
		txv_order_type = (TextView) findViewById(R.id.order_type);
		// ***********
		ood_txv_customer_name = (TextView) findViewById(R.id.ood_txv_customer_name);
		ood_txv_address = (TextView) findViewById(R.id.ood_txv_address);
		ood_txv_email = (TextView) findViewById(R.id.ood_txv_email);
		// ood_txv_payment_status = (TextView)
		// findViewById(R.id.ood_txv_payment_status);
		ood_txv_contact_no = (TextView) findViewById(R.id.ood_txv_contact_no);
		ood_txv_order_total = (TextView) findViewById(R.id.ood_txv_order_total);
		LV_Cart_Details = (ListView) findViewById(R.id.LV_Cart_Details);
		img_map = (ImageView) findViewById(R.id.img_map);
		img_wrong = (ImageView) findViewById(R.id.wrong);
		ll_googlemap = (LinearLayout) findViewById(R.id.google_map_layout);
		ll_main = (LinearLayout) findViewById(R.id.ll_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.online_order_order_details, menu);
		return true;
	}

}
