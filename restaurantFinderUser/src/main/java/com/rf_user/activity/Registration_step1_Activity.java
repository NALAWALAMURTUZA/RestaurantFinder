package com.rf_user.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.connection.ServiceHandler;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Registration_step1_Activity extends Activity
		implements
			OnMarkerDragListener,
			OnMapClickListener,
			OnMapLongClickListener,
			OnMarkerClickListener,
			LocationListener {

	TextView rf_regi_step2_tv_reg,
			  rf_regi_step2_tv_step,
			rf_regi_step2_tv_youhave, 
			 tv_rf_registration_title,
			rf_registration_tv_fname, rf_registration_tv_lname,
			rf_registration_tv_pwd, rf_registration_tv_country,
			rf_registration_tv_restaurant, rf_registration_ed_email,
			rf_registration_tv_rphone, rf_registration_tv_add,
			rf_registration_tv_zip_code, rf_registration_tv_city,
			rf_registration_tv_website, rf_registration_tv_made_deliver,
			rf_registration_tv_latitude, rf_registration_tv_longitude,
			txv_rf_country_code, rf_registration_tv_pwd_retype;
	EditText rf_registration_ed_fname, rf_registration_ed_lname,
			rf_registration_ed_pwd, rf_registration_ed_pwd1,
			rf_registration_ed_restaurant, rf_registration_ed_remail,
			rf_registration_ed_rphone, rf_registration_ed_add,
			rf_registration_ed_zip_code, rf_registration_ed_website,
			rf_registration_ed_made_deliver, rf_registration_ed_latitude,
			rf_registration_ed_longitude, rf_registration_ed_pwd_retype;
	Button rf_registration_btn_continue, rf_registration_btn_map;
	RadioButton rb_rf_regi_mr, rb_rf_regi_miss, rb_rf_regi_misss;
	RadioGroup rg_rf_regi;
	ImageView img_rf_regi_header_logo, img_rf_regi_filter_icon, Yes, No;
	Spinner sp_rf_country, sp_rf_region, sp_rf_city;
	// Spinner sp_rf_district;
	String str_rf_registration_ed_fname, str_rf_registration_ed_lname,
			str_rf_registration_ed_pwd, str_rf_registration_ed_pwd1,
			str_rf_registration_ed_restaurant, str_rf_registration_ed_remail,
			str_rf_registration_ed_rphone, str_rf_registration_ed_add,
			str_rf_registration_ed_zip_code, str_rf_registration_ed_city,
			str_rf_registration_ed_website,
			str_rf_registration_ed_made_deliver,
			str_rf_registration_ed_pwd_retype;
	public static String str_country_id, str_country_name,
			str_selected_country_name, str_selected_country_id,
			str_country_call_code;
	public static String str_gender = null;
	public static String str_selected_region_name, str_selected_region_id;
	public static String str_selected_city_name, str_selected_city_id;
	// public static String str_selected_district_name,
	// str_selected_district_id;
	public static String str_region_id, str_region_name;
	public static String str_city_id, str_city_name;
	// public static String str_district_id, str_district_name;
	public static ProgressDialog p;

	public static ArrayList<String> countryarraylist;
	public static ArrayList<String> regionarraylist;
	public static ArrayList<String> cityarraylist;
	// public static ArrayList<String> districtarraylist;

	public static ArrayList<HashMap<String, String>> hash_countryarraylist;
	public static ArrayList<HashMap<String, String>> hash_regionarraylist;
	public static ArrayList<HashMap<String, String>> hash_cityarraylist;
	// public static ArrayList<HashMap<String, String>> hash_districtarraylist;

	public static HashMap<String, String> hashmap_country;
	public static HashMap<String, String> hashmap_region;
	public static HashMap<String, String> hashmap_city;
	// public static HashMap<String, String> hashmap_district;
	LinearLayout ll_googlemap;
	ScrollView ll_registration;
	String jsonStr1;
	private GoogleMap googleMap;
	boolean markerClicked;
	LatLng latLng;
	MarkerOptions markerOptions;
	ConnectionDetector cd;
	LocationManager locationManager;
	public static double latitude, DriverLatitude;
	public static double longitude, DriverLongitude;
	boolean statusOfGPS;
	String fetch_current_location_from_google;
	public static Marker Markar;
	public static Boolean flag_rg = false;
	private Locale myLocale;
	boolean locale_flag = false;
	TextView txv_rf_region, txv_rf_city;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_registration_step1_);
		cd = new ConnectionDetector(getApplicationContext());
		initialization();

		Set_Spinner();
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
			// System.out.println("DeliveryActivity_Geocoder"
			// + Global_variable.FR_City_Name);

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new GeocoderTask().execute("Romania");

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

		} catch (Exception e) {
			e.printStackTrace();
		}
		secOnclicklistner();
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

		loadLocale();
	}
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
	
	private void Set_Spinner() {
		// TODO Auto-generated method stub
		countryarraylist = new ArrayList<String>();
		regionarraylist = new ArrayList<String>();
		cityarraylist = new ArrayList<String>();
		// districtarraylist = new ArrayList<String>();
		hash_countryarraylist = new ArrayList<HashMap<String, String>>();
		hash_regionarraylist = new ArrayList<HashMap<String, String>>();
		hash_cityarraylist = new ArrayList<HashMap<String, String>>();
		// hash_districtarraylist = new ArrayList<HashMap<String, String>>();
		try {
			if (Global_variable.array_CountryArray != null) {
				if (Global_variable.array_CountryArray.length() != 0) {
					for (int i = 0; i < Global_variable.array_CountryArray
							.length(); i++) {
						try {

							JSONObject json_object = Global_variable.array_CountryArray
									.getJSONObject(i);
							System.out.println("1111json_objectcountry"
									+ json_object);
							hashmap_country = new HashMap<String, String>();
							str_country_id = json_object
									.getString("country_id");
							str_country_name = json_object
									.getString("cname_en");
							str_country_call_code = json_object
									.getString("country_call_code");
							System.out.println("1111country_id_namne_code"
									+ str_country_call_code + str_country_id
									+ str_country_name);
							// Global_variable.SR_User_Country = json_object
							// .getString("SR_CountryName");
							hashmap_country.put("str_country_id",
									str_country_id);
							hashmap_country.put("str_country_name",
									str_country_name);
							hashmap_country.put("str_country_call_code",
									str_country_call_code);
							// countryarraylist.add(SR_CountryID);
							countryarraylist.add(str_country_name);
							hash_countryarraylist.add(hashmap_country);
							System.out.println("1111countryarraylist.."
									+ countryarraylist);

							sp_rf_country
									.setAdapter(new ArrayAdapter<String>(
											Registration_step1_Activity.this,
											android.R.layout.simple_spinner_dropdown_item,
											countryarraylist));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (Global_variable.array_RegionArray != null) {
				if (Global_variable.array_RegionArray.length() != 0) {
					for (int i = 0; i < Global_variable.array_RegionArray
							.length(); i++) {
						try {
							txv_rf_region.setVisibility(View.GONE);
							sp_rf_region.setVisibility(View.VISIBLE);
							JSONObject json_object = Global_variable.array_RegionArray
									.getJSONObject(i);
							System.out.println("1111json_objectregion"
									+ json_object);
							hashmap_region = new HashMap<String, String>();
							str_country_id = json_object
									.getString("country_id");
							str_region_id = json_object.getString("region_id");
							str_region_name = json_object.getString("name_en");
							System.out.println("1111region_id_namne_code"
									+ str_country_id + str_region_id
									+ str_region_name);
							// Global_variable.SR_User_Country = json_object
							// .getString("SR_CountryName");
							hashmap_region
									.put("str_country_id", str_country_id);
							hashmap_region.put("str_region_id", str_region_id);
							hashmap_region.put("str_region_name",
									str_region_name);
							// countryarraylist.add(SR_CountryID);
							regionarraylist.add(str_region_name);
							hash_regionarraylist.add(hashmap_region);
							System.out.println("1111regionarraylist.."
									+ regionarraylist);
							//
							// sp_rf_region.setAdapter(new ArrayAdapter<String>(
							// Registration_step1_Activity.this,
							// android.R.layout.simple_spinner_dropdown_item,
							// regionarraylist));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					txv_rf_region.setVisibility(View.VISIBLE);
					sp_rf_region.setVisibility(View.GONE);
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (Global_variable.array_CitytArray != null) {
				if (Global_variable.array_CitytArray.length() != 0) {
					for (int i = 0; i < Global_variable.array_CitytArray
							.length(); i++) {
						try {
							txv_rf_city.setVisibility(View.GONE);
							sp_rf_city.setVisibility(View.VISIBLE);
							JSONObject json_object = Global_variable.array_CitytArray
									.getJSONObject(i);
							System.out.println("1111json_objectcity"
									+ json_object);
							hashmap_city = new HashMap<String, String>();
							str_region_id = json_object.getString("region_id");
							str_city_id = json_object.getString("city_id");
							str_city_name = json_object.getString("name_en");
							System.out.println("1111city_id_namne_code"
									+ str_region_id + str_city_id
									+ str_city_name);
							// Global_variable.SR_User_Country = json_object
							// .getString("SR_CountryName");
							hashmap_city.put("str_region_id", str_region_id);
							hashmap_city.put("str_city_id", str_city_id);
							hashmap_city.put("str_city_name", str_city_name);
							// countryarraylist.add(SR_CountryID);
							cityarraylist.add(str_city_name);
							hash_cityarraylist.add(hashmap_city);
							System.out.println("1111cityarraylist.."
									+ cityarraylist);

							// sp_rf_city.setAdapter(new ArrayAdapter<String>(
							// Registration_step1_Activity.this,
							// android.R.layout.simple_spinner_dropdown_item,
							// cityarraylist));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					txv_rf_city.setVisibility(View.VISIBLE);
					sp_rf_city.setVisibility(View.GONE);
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Global_variable.array_DistrictArray.length() != 0) {

		}

	}
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

			if (myLocation != null) {

				latitude = myLocation.getLatitude();
				System.out.println("GPS latitude" + latitude);

				// Get longitude of Current Location
				longitude = myLocation.getLongitude();
				System.out.println("GPS latitude" + longitude);

				LatLng latLng = new LatLng(latitude, longitude);
				System.out.println("1111LatLng" + latLng);

				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				onLocationChanged(myLocation);

			}

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// if(location.getAccuracy() < 100.0 && location.getSpeed() < 6.95){
		// Do something

		statusOfGPS = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		// System.out.println("boolean" + statusOfGPS);

		// System.out.println("On create 12"
		// + Global_variable.global_str_CR_Availability_Status);

		if (statusOfGPS == false) {
			// Log.i("In status false", "In status false");

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// System.out
					// .println("On create 13"
					// + Global_variable.global_str_CR_Availability_Status);

					Toast.makeText(getApplicationContext(), "GPS is Off",
							Toast.LENGTH_LONG).show();

					// latitude = 0.000000;
					// longitude = 0.000000;

				}
			});

		} else {

			// System.out.println("On create 14"
			// + Global_variable.global_str_CR_Availability_Status);
			try {
				final Calendar calendar = Calendar.getInstance();

				java.util.Date now = calendar.getTime();
				System.out.println("timimg.." + now);

				// latitude = 23.00000;
				// longitude = 72.0000;
				// latitude =latitude + Math.random();
				// longitude = longitude + 1.0;
				//
				// System.out.println("after location new method"+latitude+"..."+longitude);

				// Getting latitude of the current location
				// latitude = location.getLatitude();
				// Log.i("Onlocation latitude", "Onlocation" + latitude);
				// // Getting longitude of the current location
				// longitude = location.getLongitude();
				// Log.i("Onlocation longitude", "Onlocation" + longitude);
				// }
				// Creating a LatLng object for the current location
				LatLng latLng = new LatLng(latitude, longitude);

				// Showing the current location in Google Map
				// googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
				//
				// // Zoom in the Google Map
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

				fetch_current_location_from_google = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
						+ latitude + "," + longitude + "&sensor=true";

				// System.out.println("On create 15 "
				// + Global_variable.global_str_CR_Availability_Status);

				System.out.println("!!!!!url"
						+ fetch_current_location_from_google);
				if (cd.isConnectingToInternet()) {

					new fetch_current_location_from_google().execute();
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

				// System.out.println("!!!!Url fetch_driver_location_from_google456");

				// System.out.println("On create 20"
				// + Global_variable.global_str_CR_Availability_Status);

				// Show the current location in Google Map
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
				//
				// // Zoom in Google map
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

				googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker arg0) {
						if (arg0.getTitle().equals(
								getString(R.string.str_you_r_here))) // if
						// marker
						// source
						// is
						// clicked
						{

						}

						return true;
					}

				});
			} catch (NullPointerException n) {
				n.printStackTrace();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

	private void secOnclicklistner() {
		// TODO Auto-generated method stub

		rf_registration_btn_continue
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cd.isConnectingToInternet()) {
							Global_variable.str_selected_city_name = sp_rf_city
									.getSelectedItem().toString();
//							if (rf_registration_ed_made_deliver.getText()
//									.length() > 4) {
//								Toast.makeText(
//										Registration_step1_Activity.this,
//										getString(R.string.str_text_length_limit),
//										Toast.LENGTH_LONG).show();
//							} else {
								if (rf_registration_ed_pwd_retype
										.getText()
										.toString()
										.equals(rf_registration_ed_pwd
												.getText().toString())) {
									new async_regi_step1().execute();
								} else {
									Toast.makeText(
											Registration_step1_Activity.this,
											getString(R.string.str_repeat_password),
											Toast.LENGTH_LONG).show();

								}

							//}

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
		rf_registration_btn_map.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_googlemap.setVisibility(View.VISIBLE);
				ll_registration.setVisibility(View.GONE);

			}
		});
		Yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_googlemap.setVisibility(View.GONE);
				ll_registration.setVisibility(View.VISIBLE);
				fetch_current_location_from_google = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
						+ Global_variable.latitude
						+ ","
						+ Global_variable.longitude + "&sensor=true";
				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					new fetch_current_location_from_google().execute();

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
		No.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_googlemap.setVisibility(View.GONE);
				ll_registration.setVisibility(View.VISIBLE);
			}
		});

		rg_rf_regi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

					case R.id.rb_rf_regi_mr :
						// do operations specific to this selection
						// checkbox_saved_add.setClickable(true);
						flag_rg = true;
						str_gender = "mr";
						break;

					case R.id.rb_rf_regi_mrs :
						flag_rg = true;
						str_gender = "mrs";
						// do operations specific to this selection
						break;
					case R.id.rb_rf_regi_misss :
						flag_rg = true;
						str_gender = "miss";
						// do operations specific to this selection
						break;

				}

			}
		});

		sp_rf_country.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					str_selected_country_name = sp_rf_country.getSelectedItem()
							.toString();
					int str_countryID = sp_rf_country.getSelectedItemPosition();

					str_selected_country_id = hash_countryarraylist.get(
							str_countryID).get("str_country_id");
					str_country_call_code = hash_countryarraylist.get(
							str_countryID).get("str_country_call_code");
					System.out.println("1111selectedcountryid"
							+ str_selected_country_id);
					System.out.println("1111selectedcountrycall_id"
							+ str_country_call_code);
					System.out.println("1111ahasamapcountry" + str_country_id);
					txv_rf_country_code.setText(str_country_call_code);

					regionarraylist = new ArrayList<String>();
					if (hash_regionarraylist.size() != 0) {
						txv_rf_region.setVisibility(View.GONE);
						sp_rf_region.setVisibility(View.VISIBLE);
						for (int i = 0; i < hash_regionarraylist.size(); i++) {
							str_country_id = hash_regionarraylist.get(i).get(
									"str_country_id");
							System.out.println("1111if"
									+ str_selected_country_id
									+ "--"
									+ hash_regionarraylist.get(i).get(
											"str_region_id"));
							if (str_selected_country_id
									.equalsIgnoreCase(str_country_id)) {
								System.out.println("1111if"
										+ str_selected_country_id
										+ "--"
										+ hash_regionarraylist.get(i).get(
												"str_region_id"));
								String str_hashmap = hash_regionarraylist
										.get(i).get("str_region_name");
								System.out.println("1111hashmapregion"
										+ str_hashmap);
								regionarraylist.add(str_hashmap);
								System.out.println("1111afterregionarray"
										+ regionarraylist);

							}
						}
						sp_rf_region.setAdapter(new ArrayAdapter<String>(
								Registration_step1_Activity.this,
								android.R.layout.simple_spinner_dropdown_item,
								regionarraylist));
					} else {
						txv_rf_region.setVisibility(View.VISIBLE);
						sp_rf_region.setVisibility(View.GONE);
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		sp_rf_region.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					str_selected_region_name = sp_rf_region.getSelectedItem()
							.toString();
					int str_regionID = sp_rf_region.getSelectedItemPosition();
					System.out.println("idregionselescted" + str_regionID);
					str_selected_region_id = hash_regionarraylist.get(
							str_regionID).get("str_region_id");
					System.out.println("1111selectedregion"
							+ str_selected_region_id);
					System.out.println("1111hashmapregion" + str_region_id);

					cityarraylist = new ArrayList<String>();
					if (hash_cityarraylist.size() != 0) {
						txv_rf_city.setVisibility(View.GONE);
						sp_rf_city.setVisibility(View.VISIBLE);
						for (int i = 0; i < hash_cityarraylist.size(); i++) {
							str_region_id = hash_cityarraylist.get(i).get(
									"str_region_id");
							System.out.println("1111ifout"
									+ str_selected_region_id
									+ "--"
									+ hash_cityarraylist.get(i).get(
											"str_region_id"));
							if (str_selected_region_id
									.equalsIgnoreCase(str_region_id)) {
								System.out.println("1111if"
										+ str_selected_region_id
										+ "--"
										+ hash_cityarraylist.get(i).get(
												"str_region_id"));
								String str_hashmap = hash_cityarraylist.get(i)
										.get("str_city_name");
								System.out.println("1111hashmapcity"
										+ str_hashmap);
								cityarraylist.add(str_hashmap);
								System.out.println("1111aftercityarray"
										+ cityarraylist);

							}

						}
						sp_rf_city.setAdapter(new ArrayAdapter<String>(
								Registration_step1_Activity.this,
								android.R.layout.simple_spinner_dropdown_item,
								cityarraylist));

						// *********************
					} else {
						txv_rf_city.setVisibility(View.VISIBLE);
						sp_rf_city.setVisibility(View.GONE);
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		sp_rf_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				try {

					int str_cityID = 0;
					str_selected_city_name = sp_rf_city.getSelectedItem()
							.toString();
					Global_variable.str_selected_city_name = sp_rf_city
							.getSelectedItem().toString();
					System.out.println("namefromcityid"
							+ Global_variable.str_selected_city_name);
					System.out.println("!!!!str_hashmap" + hash_cityarraylist);
					for (int i = 0; i < hash_cityarraylist.size(); i++) {
						System.out.println("!!!!str_selected_city_name"
								+ Global_variable.str_selected_city_name);
						System.out.println("!!!!str_cityname"
								+ hash_cityarraylist.get(i)
										.get("str_city_name"));
						if (hash_cityarraylist
								.get(i)
								.get("str_city_name")
								.equalsIgnoreCase(
										Global_variable.str_selected_city_name)) {
							System.out.println("samein");
							str_cityID = Integer.parseInt(hash_cityarraylist
									.get(i).get("str_city_id"));
							System.out.println("samenamegetid" + str_cityID);
						}

					}
					// System.out.println("!!!!str_selected_city_name"+str_selected_city_name);
					System.out.println("!!!!str_city_idout" + str_cityID);

					// str_selected_city_id = hash_cityarraylist.get(str_cityID)
					// .get("str_city_id");
					str_selected_city_id = str_cityID + "";
					System.out.println("1111selectedcity"
							+ str_selected_city_id);

				} catch (NullPointerException n) {
					n.printStackTrace();
				} catch (IndexOutOfBoundsException n) {
					n.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}
	private void initialization() {
		// TODO Auto-generated method stub

		sp_rf_country = (Spinner) findViewById(R.id.sp_rf_country);
		sp_rf_city = (Spinner) findViewById(R.id.rf_registration_sp_city);
		sp_rf_region = (Spinner) findViewById(R.id.sp_rf_region);
		txv_rf_region = (TextView) findViewById(R.id.txv_rf_region);
		txv_rf_city = (TextView) findViewById(R.id.txv_rf_city);
		// sp_rf_district = (Spinner) findViewById(R.id.sp_rf_district);
		rf_regi_step2_tv_reg = (TextView) findViewById(R.id.rf_regi_step2_tv_reg);
		rf_regi_step2_tv_step = (TextView) findViewById(R.id.rf_regi_step2_tv_step);
		rf_regi_step2_tv_youhave = (TextView) findViewById(R.id.rf_regi_step2_tv_youhave);
		tv_rf_registration_title = (TextView) findViewById(R.id.tv_rf_registration_title);

		txv_rf_country_code = (TextView) findViewById(R.id.txv_rf_country_code);

		rf_registration_ed_fname = (EditText) findViewById(R.id.rf_registration_ed_fname);
		rf_registration_ed_lname = (EditText) findViewById(R.id.rf_registration_ed_lname);
		rf_registration_ed_pwd = (EditText) findViewById(R.id.rf_registration_ed_pwd);
		rf_registration_ed_pwd_retype = (EditText) findViewById(R.id.rf_registration_ed_pwd_retype);
		rf_registration_ed_restaurant = (EditText) findViewById(R.id.rf_registration_ed_restaurant);
		rf_registration_ed_remail = (EditText) findViewById(R.id.rf_registration_ed_email);
		rf_registration_ed_rphone = (EditText) findViewById(R.id.rf_registration_ed_rphone);
		rf_registration_ed_add = (EditText) findViewById(R.id.rf_registration_ed_add);
		rf_registration_ed_zip_code = (EditText) findViewById(R.id.rf_registration_ed_zip_code);

		rf_registration_ed_website = (EditText) findViewById(R.id.rf_registration_ed_website);
		rf_registration_ed_made_deliver = (EditText) findViewById(R.id.rf_registration_ed_made_deliver);
		rf_registration_ed_latitude = (EditText) findViewById(R.id.rf_registration_ed_latitude);
		rf_registration_ed_longitude = (EditText) findViewById(R.id.rf_registration_ed_longitude);

		rf_registration_btn_continue = (Button) findViewById(R.id.rf_registration_btn_continue);
		rf_registration_btn_map = (Button) findViewById(R.id.rf_registration_btn_map);
		rg_rf_regi = (RadioGroup) findViewById(R.id.rg_rf_regi);
		Yes = (ImageView) findViewById(R.id.yes);
		No = (ImageView) findViewById(R.id.wrong);
		img_rf_regi_header_logo = (ImageView) findViewById(R.id.img_rf_regi_header_logo);
		img_rf_regi_filter_icon = (ImageView) findViewById(R.id.img_rf_regi_filter_icon);
		ll_googlemap = (LinearLayout) findViewById(R.id.google_map_layout);
		ll_registration = (ScrollView) findViewById(R.id.ll_registration);
		rf_registration_ed_made_deliver
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
//						if (rf_registration_ed_made_deliver.getText().length() >= 4) {
//							Toast.makeText(Registration_step1_Activity.this,
//									getString(R.string.str_text_length_limit),
//									Toast.LENGTH_LONG).show();
//						} else {
//
//						}
					}
				});

		rf_registration_ed_pwd_retype
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (rf_registration_ed_pwd_retype.getText().toString()
								.length() != 0
								&& rf_registration_ed_pwd.getText().toString()
										.length() != 0) {
							if (rf_registration_ed_pwd_retype
									.getText()
									.toString()
									.equals(rf_registration_ed_pwd.getText()
											.toString())) {

							} else {
								Toast.makeText(
										Registration_step1_Activity.this,
										getString(R.string.str_repeat_password),
										Toast.LENGTH_LONG).show();

							}
						}
					}
				});
	}

	public class async_regi_step1 extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(Registration_step1_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			JSONObject obj_regi1 = new JSONObject();
			JSONObject restaurantregistrationstep1 = new JSONObject();
			try {
				if (flag_rg == true) {
					obj_regi1.put("gender", str_gender);
				} else {
					obj_regi1.put("gender", "mr");
				}
				obj_regi1.put("FirstName", rf_registration_ed_fname.getText()
						.toString());
				obj_regi1.put("LastName", rf_registration_ed_lname.getText()
						.toString());
				obj_regi1.put("password", rf_registration_ed_pwd.getText()
						.toString());
				obj_regi1.put("country_id", str_selected_country_id);
				obj_regi1.put("name_en", rf_registration_ed_restaurant
						.getText().toString());
				obj_regi1.put("contact_email", rf_registration_ed_remail
						.getText().toString());
				obj_regi1.put("country_call_id", txv_rf_country_code.getText()
						.toString());
				obj_regi1.put("contact_number", rf_registration_ed_rphone
						.getText().toString());
				obj_regi1.put("street", rf_registration_ed_add.getText()
						.toString());
				obj_regi1.put("zip", rf_registration_ed_zip_code.getText()
						.toString());
				obj_regi1.put("city_id", str_selected_city_id);
				obj_regi1.put("website", rf_registration_ed_website.getText()
						.toString());
				obj_regi1.put("reservation_made_daily",
						rf_registration_ed_made_deliver.getText().toString());
				// obj_regi1.put("map_lat",
				// rf_registration_ed_latitude.getText()
				// .toString());
				// obj_regi1.put("map_long", rf_registration_ed_longitude
				// .getText().toString());
				obj_regi1.put("map_lat", rf_registration_ed_latitude.getText()
						.toString());
				obj_regi1.put("map_long", rf_registration_ed_longitude
						.getText().toString());
				obj_regi1.put("map_zoom", "12");
				System.out.println("1111inreqregid" + str_selected_region_id);
				obj_regi1.put("region_id", str_selected_region_id);
				// obj_regi1.put("district_id", rf_registration_ed_longitude
				// .getText().toString());
				obj_regi1.put("district_id", "");

				System.out.println("1111requobjregi1" + obj_regi1);

				restaurantregistrationstep1.put("restaurantregistrationstep1",
						obj_regi1);
				restaurantregistrationstep1.put("sessid",
						Global_variable.sessid);
				System.out.println("1111reqrestaurantregistrationstep1"
						+ restaurantregistrationstep1);

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {

				// *************
				// for request

				try {
					HttpConnection con = new HttpConnection();
					String str_response = con.connection_rest_reg(Registration_step1_Activity.this,
							Global_variable.rf_api_registrationstep1,
							restaurantregistrationstep1);
					
					json = new JSONObject(str_response);
				//responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("1111finaljsonstepTG" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i(getString(R.string.str_Parse_Exception), e + "");

				} catch (NullPointerException np) {

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NullPointerException np) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			if (p.isShowing())
				p.dismiss();
			try {
				jsonSuccessStr = json.getString("success");
				System.out.println("1111success" + jsonSuccessStr);
				Global_variable.sessid = json.getString("sessid");
				System.out.println("1111step1sessid" + Global_variable.sessid);
				if (jsonSuccessStr.equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					System.out.println("1111obj_Data" + Data);
					if (Data != null) {
						Global_variable.restaurantregistrationstep1 = Data
								.getJSONObject("restaurantregistrationstep1");
						System.out.println("1111restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);

						RegistrationTablayout.tab.setCurrentTab(1);
						RegistrationTablayout.tab.getTabWidget()
								.getChildAt(1).setClickable(true);
						if (Global_variable.restaurantregistrationstep1 != null) {
							try {
								System.out
										.println("1111step4registrstep1"
												+ Global_variable.restaurantregistrationstep1);
								// String str_step1_firstname =
								// Global_variable.restaurantregistrationstep1
								// .getString("FirstName");
								// String str_step1_lastname =
								// Global_variable.restaurantregistrationstep1
								// .getString("LastName");
								// String str_step1_email =
								// Global_variable.restaurantregistrationstep1
								// .getString("contact_email");
								// String str_step1_zipcode =
								// Global_variable.restaurantregistrationstep1
								// .getString("zip");
								// String str_step1_address =
								// Global_variable.restaurantregistrationstep1
								// .getString("street");
								// Registration_step4_Activity.ed_firstname_representative
								// .setText(str_step1_firstname);
								// Registration_step4_Activity.ed_lastname_representative
								// .setText(str_step1_lastname);
								// Registration_step4_Activity.ed_billing_email
								// .setText(str_step1_email);
								// Registration_step4_Activity.ed_zipcode
								// .setText(str_step1_zipcode);
								// Registration_step4_Activity.ed_inoice_address
								// .setText(str_step1_address);
							} catch (IndexOutOfBoundsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				} else {
					JSONObject Error = json.getJSONObject("errors");
					System.out.println("1111errors" + Error);
					if (Error != null) {
						if (Error.has("FirstName")) {
							JSONArray FirstName = Error
									.getJSONArray("FirstName");
							System.out.println("1111FirstName" + FirstName);
							if (FirstName != null) {
								String str_FirstName = FirstName.getString(0);
								System.out.println("1111str_FirstName"
										+ str_FirstName);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_FirstName, Toast.LENGTH_LONG)
										.show();
							}

						}

						if (Error.has("LastName")) {
							JSONArray LastName = Error.getJSONArray("LastName");
							System.out.println("1111LastName" + LastName);
							if (LastName != null) {
								String str_LastName = LastName.getString(0);
								System.out.println("1111str_LastName"
										+ str_LastName);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_LastName, Toast.LENGTH_LONG).show();
							}

						}

						if (Error.has("password")) {
							JSONArray password = Error.getJSONArray("password");
							System.out.println("1111password" + password);
							if (password != null) {
								String str_password = password.getString(0);
								System.out.println("1111str_LastName"
										+ str_password);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_password, Toast.LENGTH_LONG).show();
							}

						}

						if (Error.has("name_en")) {
							JSONArray name_en = Error.getJSONArray("name_en");
							System.out.println("1111name_en" + name_en);
							if (name_en != null) {
								String str_name_en = name_en.getString(0);
								System.out.println("1111str_name_en"
										+ str_name_en);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_name_en, Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("contact_email")) {
							JSONArray contact_email = Error
									.getJSONArray("contact_email");
							System.out.println("1111contact_email"
									+ contact_email);
							if (contact_email != null) {
								String str_contact_email = contact_email
										.getString(0);
								System.out.println("1111str_name_en"
										+ str_contact_email);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_contact_email, Toast.LENGTH_LONG)
										.show();
							}

						}

						if (Error.has("contact_number")) {
							JSONArray contact_number = Error
									.getJSONArray("contact_number");
							System.out.println("1111contact_number"
									+ contact_number);
							if (contact_number != null) {
								String str_contact_number = contact_number
										.getString(0);
								System.out.println("1111str_contact_number"
										+ str_contact_number);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_contact_number, Toast.LENGTH_LONG)
										.show();
							}

						}
						if (Error.has("street")) {
							JSONArray street = Error.getJSONArray("street");
							System.out.println("1111street" + street);
							if (street != null) {
								String str_street = street.getString(0);
								System.out.println("1111str_street"
										+ str_street);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_street, Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("zip")) {
							JSONArray zip = Error.getJSONArray("zip");
							System.out.println("1111zip" + zip);
							if (zip != null) {
								String str_zip = zip.getString(0);
								System.out.println("1111str_zip" + str_zip);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_zip, Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("website")) {
							JSONArray website = Error.getJSONArray("website");
							System.out.println("1111website" + website);
							if (website != null) {
								String str_website = website.getString(0);
								System.out.println("1111str_website"
										+ str_website);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_website, Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("reservation_made_daily")) {
							JSONArray reservation_made_daily = Error
									.getJSONArray("reservation_made_daily");
							System.out.println("1111reservation_made_daily"
									+ reservation_made_daily);
							if (reservation_made_daily != null) {
								String str_reservation_made_daily = reservation_made_daily
										.getString(0);
								System.out
										.println("1111str_reservation_made_dail"
												+ str_reservation_made_daily);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_reservation_made_daily,
										Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("map_lat")) {
							JSONArray map_lat = Error.getJSONArray("map_lat");
							System.out.println("1111map_lat" + map_lat);
							if (map_lat != null) {
								String str_map_lat = map_lat.getString(0);
								System.out.println("1111str_map_lat"
										+ str_map_lat);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_map_lat, Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("map_long")) {
							JSONArray map_long = Error.getJSONArray("map_long");
							System.out.println("1111map_long" + map_long);
							if (map_long != null) {
								String str_map_long = map_long.getString(0);
								System.out.println("1111str_map_long"
										+ str_map_long);
								Toast.makeText(
										Registration_step1_Activity.this,
										str_map_long, Toast.LENGTH_LONG).show();
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

		}
	}

	public class fetch_current_location_from_google
			extends
				AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(Void... params) {

			ServiceHandler sh = new ServiceHandler();

			System.out.println("after new method2..google "
					+ Global_variable.latitude + "..."
					+ Global_variable.longitude);
			jsonStr1 = sh.makeServiceCall(
					"http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ Global_variable.latitude + ","
							+ Global_variable.longitude + "&sensor=true",
					ServiceHandler.GET);

			System.out
					.println("!!!Url"
							+ "http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ Global_variable.latitude + ","
							+ Global_variable.longitude + "&sensor=true");
			// System.out.println("!!!!jsonstr1.."+jsonStr1);

			if (jsonStr1 != null) {

				try {

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
						if (Type.toString().equals(
								getString(R.string.str_route))) {
							Global_variable.route = long_name;
							System.out.println("!!!!!route"
									+ Global_variable.route);
						}

					}

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
			// Markar = googleMap
			// .addMarker(new MarkerOptions()
			// .position(latLng)
			// .title("")
			// .icon(BitmapDescriptorFactory
			// .fromResource(R.drawable.pin)));
			rf_registration_ed_latitude.setText(Global_variable.latitude + "");
			rf_registration_ed_longitude
					.setText(Global_variable.longitude + "");
			super.onPostExecute(result);

		}

	}

	// An AsyncTask class for accessing the GeoCoding Web Service
	private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;

			// System.out.println("delivery_activity.location" +
			// locationName[0]);
			try {
				addresses = geocoder.getFromLocationName(locationName[0], 3);
				while (addresses.size() == 0) {
					addresses = geocoder
							.getFromLocationName(locationName[0], 3);
				}
				if (addresses.size() > 0) {
					Address addr = addresses.get(0);
					System.out.println("1111address" + addr);
					Global_variable.latitude = (addr.getLatitude());
					Global_variable.longitude = (addr.getLongitude());
					System.out.println("delivery_activity.addr" + addr);
					System.out.println("delivery_activity.latt"
							+ Global_variable.latitude);
					System.out.println("delivery_activity.long"
							+ Global_variable.longitude);

				} else {
					System.out.println("!!!!!!!!in else" + addresses.size());
				}
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
			return addresses;
		}

		@Override
		protected void onPostExecute(List<Address> addresses) {

			try {
				if (addresses == null || addresses.size() == 0) {
					Toast.makeText(getBaseContext(),
							getString(R.string.str_no_location),
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
					System.out.println("1111latlang" + latLng);
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
					Global_variable.latitude = markerOptions.getPosition().latitude;
					Global_variable.longitude = markerOptions.getPosition().longitude;
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

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));

		markerClicked = false;
	}

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
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

		Global_variable.latitude = marker.getPosition().latitude;
		Global_variable.longitude = marker.getPosition().longitude;

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

	//
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[]{latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10)};
	}
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// switch (keyCode) {
	// case KeyEvent.KEYCODE_BACK:
	// onBackPressed();
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }
	//
	// public void onBackPressed() {
	// /** check Internet Connectivity */
	// if (cd.isConnectingToInternet()) {
	//
	// Intent i = new Intent(Registration_step1_Activity.this,
	// Login_Activity.class);
	// startActivity(i);
	// } else {
	//
	// runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(),
	// R.string.No_Internet_Connection, Toast.LENGTH_SHORT)
	// .show();
	//
	// }
	//
	// });
	// }
	//
	// }
}
