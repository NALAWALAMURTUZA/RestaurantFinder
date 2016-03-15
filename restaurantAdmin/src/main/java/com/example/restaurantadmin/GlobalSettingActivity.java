package com.example.restaurantadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import android.widget.VideoView;

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
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.imageloader.ImageLoader;
import com.restaurantadmin.servicehandler.ServiceHandler;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GlobalSettingActivity extends Activity
		implements
			OnMarkerDragListener,
			OnMapClickListener,
			OnMapLongClickListener,
			OnMarkerClickListener,
			LocationListener {
	int serverResponseCode = 0;
	public static int package_id = 0;
	public static String package_name = null;
	ConnectionDetector cd;
	public static RadioGroup radioGroup_subscription;
	Button btn_ms_button;
	public static ProgressDialog p;
	public static Activity activity = null;
	EditText rf_registration_gs_ed_fname, rf_registration_gs_ed_lname,
			rf_registration_gs_ed_pwd, rf_registration_gs_ed_pwd1,
			rf_registration_gs_ed_restaurant, rf_registration_gs_ed_remail,
			rf_registration_gs_ed_rphone, rf_registration_gs_ed_add,
			rf_registration_gs_ed_zip_code, rf_registration_gs_ed_website,
			rf_registration_gs_ed_made_deliver, rf_registration_gs_ed_latitude,
			rf_registration_gs_ed_longitude;
	Button rf_registration_gs_btn_confirme, rp_btn_cancel,
			rf_registration_gs_btn_map, rf_registration_gs_btn_cancel;
	RadioButton rb_rf_gs_regi_mr, rb_rf_gs_regi_miss, rb_rf_gs_regi_misss;
	RadioGroup rg_rf_gs_regi;
	ImageView img_rf_gs_regi_header_logo, img_rf_gs_regi_filter_icon, Yes, No;
	Spinner sp_rf_gs_country, sp_rf_gs_region, sp_rf_gs_city, sp_gs_mto;
	public static ArrayList<String> countryarraylist;
	public static ArrayList<String> regionarraylist;
	public static ArrayList<String> cityarraylist;
	public static ArrayList<HashMap<String, String>> hash_countryarraylist;
	public static ArrayList<HashMap<String, String>> hash_regionarraylist;
	public static ArrayList<HashMap<String, String>> hash_cityarraylist;
	public static HashMap<String, String> hashmap_country;
	public static HashMap<String, String> hashmap_region;
	public static HashMap<String, String> hashmap_city;
	public static String str_region_id, str_region_name;
	public static String str_city_id, str_city_name;
	public static String str_selected_region_name, str_selected_region_id;
	public static String str_selected_city_name, str_selected_city_id;
	LinearLayout ll_googlemap;
	ScrollView ll_registration;
	String jsonStr1;
	private GoogleMap googleMap;
	boolean markerClicked;
	LatLng latLng;
	MarkerOptions markerOptions;
	LocationManager locationManager;
	public static double latitude, DriverLatitude;
	public static double longitude, DriverLongitude;
	boolean statusOfGPS;
	String fetch_current_location_from_google;
	public static Marker Markar;
	public static Boolean flag_rg = false;
	public static String str_country_id, str_country_name,
			str_country_call_code, str_selected_country_name,
			str_selected_country_id;
	public static String str_gender = null;
	TextView txv_rf_gs_country_code;
	ArrayAdapter<String> adapter_country, adapter_region, adapter_city,
			adapter_district;
	ArrayAdapter<CharSequence> adapter_sp_gs_mto;
	String str_pickup = null, str_delivery = null;// str_indine=null;
	CheckBox ch_gs_pickup, ch_gs_delivery;// ch_gs_indine;
	ImageView img_gs_upload_icon, img_gs_upload_banner;
	public static String result_baner = "";
	public static String result_icon = "";
	public static String result = "";
	boolean image_click = true;
	// public static ProgressDialog dialog = null;
	public static ImageView img_home;
	private Locale myLocale;

	// changes chetan***********
	public static EditText ed_iban;
	public static EditText ed_no;
	public static EditText ed_email;

	public static EditText ed_restaurant_seat;
	public static EditText ed_average_bill, ed_aboutrest;
	public static RadioGroup rg_last_minute_booking;
	public static RadioButton rb_last_minute_booking_yes;
	public static RadioButton rb_last_minute_booking_no;
	public static String str_rb_last_minute_booking, str_mto, picturePath,
			SELECT = "CAMERA";
	private Uri fileUri;
	public static int sp_index;
	private String filePath = null;

	private static final String TAG = GlobalSettingActivity.class
			.getSimpleName();

	// private ProgressBar progressBar;
	// private TextView txtPercentage;
	private ImageView imgPreview;
	private VideoView vidPreview;
	private Button btnUpload;
	long totalSize = 0;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int GALLERY_CAPTURE_IMAGE_REQUEST_CODE = 300;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_global_setting);
		initialization();
		activity = GlobalSettingActivity.this;
		cd = new ConnectionDetector(getApplicationContext());
		setvalue();
		Set_Spinner();
		secOnclicklistner();

		try {
			// Loading map
			initilizeMap();
			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				latLng = new LatLng(Double.valueOf(Global_variable.logindata
						.getJSONObject("global_setting").getString("map_lat")
						.toString()), Double.valueOf(Global_variable.logindata
						.getJSONObject("global_setting").getString("map_long")
						.toString()));
				markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.draggable(true);
				googleMap.addMarker(markerOptions);
				Global_variable.latitude = markerOptions.getPosition().latitude;
				Global_variable.longitude = markerOptions.getPosition().longitude;
				googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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

		// loadLocale();
		// LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
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
	// @Override
	// public void onResume() {
	// System.out.println("murtuza_nalawala");
	// super.onResume(); // Always call the superclass method first
	// }
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
	private void displayMap() {
		// Enable MyLocation Layer of Google Map
		try {
			googleMap.setMyLocationEnabled(true);
			// Get LocationManager object from System Service LOCATION_SERVICE
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			System.out.println("locationManager" + locationManager);
			// Create a criteria Object to retrieve provider
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
	private void setvalue() {
		// TODO Auto-generated method stub
		try {

			// System.out
			// .println("featured_icon111111122"
			// + Global_variable.logindata
			// .getJSONObject("global_setting"));
			rf_registration_gs_ed_fname.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("FirstName"));
			rf_registration_gs_ed_lname.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("LastName"));
			// rf_registration_gs_ed_pwd.setText(Global_variable.logindata.getJSONObject("global_setting").getString("password"));
			// rf_registration_gs_ed_pwd1.setText(Global_variable.logindata.getJSONObject("global_setting").getString("password"));
			rf_registration_gs_ed_restaurant.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("name_en"));
			rf_registration_gs_ed_remail
					.setText(Global_variable.logindata.getJSONObject(
							"global_setting").getString("contact_email"));
			rf_registration_gs_ed_rphone.setText(Global_variable.logindata
					.getJSONObject("global_setting")
					.getString("contact_number"));
			rf_registration_gs_ed_add.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("street"));
			rf_registration_gs_ed_zip_code.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("zip"));
			rf_registration_gs_ed_website.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("website"));
			rf_registration_gs_ed_made_deliver
					.setText(Global_variable.logindata.getJSONObject(
							"global_setting").getString(
							"reservation_made_daily"));
			rf_registration_gs_ed_latitude.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("map_lat"));
			rf_registration_gs_ed_longitude.setText(Global_variable.logindata
					.getJSONObject("global_setting").getString("map_long"));
			// new changes
			ed_iban.setText(Global_variable.logindata.getJSONObject(
					"global_setting").getString("iban_number"));
			ed_no.setText(Global_variable.logindata.getJSONObject(
					"global_setting").getString("bank_mobile_number"));
			ed_email.setText(Global_variable.logindata.getJSONObject(
					"global_setting").getString("bank_email"));
			ed_average_bill.setText(Global_variable.logindata.getJSONObject(
					"global_setting").getString("delivery_min_order"));
			ed_restaurant_seat.setText(Global_variable.logindata.getJSONObject(
					"global_setting").getString("restaurant_seat"));
			str_rb_last_minute_booking = Global_variable.logindata
					.getJSONObject("global_setting").getString(
							"accept_last_minute_booking");
			ed_aboutrest.setText(Global_variable.logindata.getJSONObject(
					"global_setting").getString("comment"));
			// ******************
			if (str_rb_last_minute_booking.equalsIgnoreCase("0")) {
				rb_last_minute_booking_no.setChecked(true);
			} else {
				rb_last_minute_booking_yes.setChecked(true);
			}
			// str_gender=Global_variable.logindata.getJSONObject("global_setting").getString("gender").toString();
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("gender").toString().equalsIgnoreCase("mr")) {
				System.out.println("str_gender" + str_gender);
				str_gender = "mr";
				rg_rf_gs_regi.check(R.id.rb_rf_gs_regi_mr);
			}
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("gender").toString().equalsIgnoreCase("mrs")) {
				System.out.println("str_gender" + str_gender);
				str_gender = "mrs";
				rg_rf_gs_regi.check(R.id.rb_rf_gs_regi_mrs);
			}
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("gender").toString().equalsIgnoreCase("miss")) {
				System.out.println("str_gender" + str_gender);
				str_gender = "miss";
				rg_rf_gs_regi.check(R.id.rb_rf_gs_regi_misss);
			}
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("pickupavail").toString().equalsIgnoreCase("1")) {
				ch_gs_pickup.setChecked(true);
				str_pickup = "1";
				System.out.println("str_pickup" + str_pickup);

			} else {
				ch_gs_pickup.setChecked(false);
				str_pickup = "0";
				System.out.println("str_pickup" + str_pickup);
			}
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("delivery_avail").toString()
					.equalsIgnoreCase("1")) {
				ch_gs_delivery.setChecked(true);
				str_delivery = "1";
				System.out.println("str_delivery" + str_delivery);
			} else {
				ch_gs_delivery.setChecked(false);
				str_delivery = "0";
				System.out.println("str_delivery" + str_delivery);
			}
			/*
			 * if(Global_variable.logindata.getJSONObject("global_setting").
			 * getString("indine_avail").toString().equalsIgnoreCase("1")) {
			 * //ch_gs_indine.setChecked(true); //str_indine="1";
			 * //System.out.println("str_indine"+str_indine); } else {
			 * ch_gs_indine.setChecked(false); str_indine="0";
			 * System.out.println("str_indine"+str_indine); }
			 */
			// if
			// (Global_variable.logindata.getJSONObject("global_setting").has(
			// "mto")) {
			// int indexlevel = adapter_sp_gs_mto
			// .getPosition(Global_variable.logindata.getJSONObject(
			// "global_setting").getString("mto")
			// + " " + getResources().getString(R.string.daybefore));
			// // int indexlevel = adapter_sp_gs_mto
			// // .getPosition(Global_variable.logindata.getJSONObject(
			// // "global_setting").getString("mto"));
			// Log.i("indexCapacity", "indexCapacity" + indexlevel);
			// sp_gs_mto.setSelection(indexlevel);
			// }
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("featured_icon") != null) {
				String helloWorld = Global_variable.logindata.getJSONObject(
						"global_setting").getString("featured_icon");
				System.out.println("featured_icon1111111" + helloWorld);
				String hellWrld = helloWorld.replace(
						Global_variable.img_pre_path, "");
				result_icon = hellWrld;
				ImageLoader img = new ImageLoader(GlobalSettingActivity.this);
				img.DisplayImage(
						Global_variable.logindata.getJSONObject(
								"global_setting").getString("featured_icon"),
						img_gs_upload_icon);

			}
			if (Global_variable.logindata.getJSONObject("global_setting")
					.getString("featured_image") != null) {
				String helloWorld = Global_variable.logindata.getJSONObject(
						"global_setting").getString("featured_image");
				System.out.println("featurdimage1111111" + helloWorld);
				String hellWrld = helloWorld.replace(
						Global_variable.img_pre_path, "");
				result_baner = hellWrld;
				ImageLoader img = new ImageLoader(GlobalSettingActivity.this);
				img.DisplayImage(
						Global_variable.logindata.getJSONObject(
								"global_setting").getString("featured_image"),
						img_gs_upload_banner);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void secOnclicklistner() {
		// TODO Auto-generated method stub
		sp_gs_mto.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// str_mto = sp_gs_mto.getSelectedItem().toString();
				// System.out.println("str_mto" + str_mto);
				sp_index = sp_gs_mto.getSelectedItemPosition();
				System.out.println("getselected_sppositon" + sp_index);
				// Toast.makeText(Registration_step2_Activity.this,
				// "select" +" "+ sp_index, Toast.LENGTH_LONG).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		rg_last_minute_booking
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {

							case R.id.rb_last_minute_booking_yes :
								// do operations specific to this selection
								// checkbox_saved_add.setClickable(true);
								str_rb_last_minute_booking = "1";
								break;

							case R.id.rb_last_minute_booking_no :
								str_rb_last_minute_booking = "0";
								// do operations specific to this selection
								break;

						}

					}
				});
		// **********************
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(GlobalSettingActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		img_gs_upload_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				image_click = true;
				result = "";
				selectImage();

			}
		});

		img_gs_upload_banner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				image_click = false;
				result = "";
				selectImage();

			}
		});

		ch_gs_delivery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (ch_gs_delivery.isChecked()) {
					str_delivery = "1";
				} else {
					str_delivery = "0";
				}
			}
		});
		/*
		 * ch_gs_indine.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub if (ch_gs_indine.isChecked()) { str_indine="1"; } else {
		 * str_indine="0"; } } });
		 */
		ch_gs_pickup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (ch_gs_pickup.isChecked()) {
					str_pickup = "1";
				} else {
					str_pickup = "0";
				}
			}
		});
		rf_registration_gs_btn_confirme
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cd.isConnectingToInternet()) {
							Global_variable.str_selected_city_name = sp_rf_gs_city
									.getSelectedItem().toString();
							if (str_pickup.equalsIgnoreCase("0")
									&& str_delivery.equalsIgnoreCase("0")) {
								Toast.makeText(GlobalSettingActivity.this,
										getString(R.string.str_facility),
										Toast.LENGTH_LONG).show();
							} else {
								new update_global_setting().execute();
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
		rf_registration_gs_btn_map
				.setOnClickListener(new View.OnClickListener() {
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

		rg_rf_gs_regi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

					case R.id.rb_rf_gs_regi_mr :
						// do operations specific to this selection
						// checkbox_saved_add.setClickable(true);
						flag_rg = true;
						str_gender = "mr";
						System.out.println("str_gender" + str_gender);
						break;

					case R.id.rb_rf_gs_regi_mrs :
						flag_rg = true;
						str_gender = "mrs";
						System.out.println("str_gender" + str_gender);
						// do operations specific to this selection
						break;
					case R.id.rb_rf_gs_regi_misss :
						flag_rg = true;
						str_gender = "miss";
						System.out.println("str_gender" + str_gender);
						// do operations specific to this selection
						break;

				}

			}
		});

		sp_rf_gs_country
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_selected_country_name = sp_rf_gs_country
									.getSelectedItem().toString();
							int str_countryID = sp_rf_gs_country
									.getSelectedItemPosition();
							str_selected_country_id = hash_countryarraylist
									.get(str_countryID).get("str_country_id");
							str_country_call_code = hash_countryarraylist.get(
									str_countryID).get("str_country_call_code");
							txv_rf_gs_country_code
									.setText(str_country_call_code);
							regionarraylist = new ArrayList<String>();
							for (int i = 0; i < hash_regionarraylist.size(); i++) {
								str_country_id = hash_regionarraylist.get(i)
										.get("str_country_id");
								if (str_selected_country_id
										.equalsIgnoreCase(str_country_id)) {
									String str_hashmap = hash_regionarraylist
											.get(i).get("str_region_name");
									regionarraylist.add(str_hashmap);
								}
							}
							adapter_region = new ArrayAdapter<String>(
									GlobalSettingActivity.this,
									android.R.layout.simple_spinner_dropdown_item,
									regionarraylist);
							sp_rf_gs_region.setAdapter(adapter_region);
							try {
								int indexlevel = adapter_region
										.getPosition(Global_variable.logindata
												.getJSONObject("global_setting")
												.getString("regions_name")
												.toString());
								Log.i("indexCapacity", "indexCapacity"
										+ indexlevel);
								sp_rf_gs_region.setSelection(indexlevel);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
		sp_rf_gs_region.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					str_selected_region_name = sp_rf_gs_region
							.getSelectedItem().toString();
					int str_regionID = sp_rf_gs_region
							.getSelectedItemPosition();
					str_selected_region_id = hash_regionarraylist.get(
							str_regionID).get("str_region_id");
					cityarraylist = new ArrayList<String>();
					for (int i = 0; i < hash_cityarraylist.size(); i++) {
						str_region_id = hash_cityarraylist.get(i).get(
								"str_region_id");

						if (str_selected_region_id
								.equalsIgnoreCase(str_region_id)) {
							String str_hashmap = hash_cityarraylist.get(i).get(
									"str_city_name");
							cityarraylist.add(str_hashmap);
						}
					}
					adapter_city = new ArrayAdapter<String>(
							GlobalSettingActivity.this,
							android.R.layout.simple_spinner_dropdown_item,
							cityarraylist);
					sp_rf_gs_city.setAdapter(adapter_city);
					try {
						int indexlevel = adapter_city
								.getPosition(Global_variable.logindata
										.getJSONObject("global_setting")
										.getString("cities_name").toString());
						Log.i("indexCapacity", "indexCapacity" + indexlevel);
						sp_rf_gs_city.setSelection(indexlevel);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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

		rf_registration_gs_btn_cancel
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						Intent i = new Intent(GlobalSettingActivity.this,
								Home_Activity.class);
						startActivity(i);
					}
				});
		sp_rf_gs_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					int str_cityID = 0;
					str_selected_city_name = sp_rf_gs_city.getSelectedItem()
							.toString();
					Global_variable.str_selected_city_name = sp_rf_gs_city
							.getSelectedItem().toString();
					for (int i = 0; i < hash_cityarraylist.size(); i++) {
						if (hash_cityarraylist.get(i).get("str_city_name")
								.equalsIgnoreCase(str_selected_city_name)) {
							str_cityID = Integer.parseInt(hash_cityarraylist
									.get(i).get("str_city_id"));
						}
					}
					// str_selected_city_id =
					// hash_cityarraylist.get(str_cityID).get("str_city_id");
					str_selected_city_id = str_cityID + "";
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
		// changes chetan*********

		ed_restaurant_seat = (EditText) findViewById(R.id.ed_restaurant_seat);
		ed_average_bill = (EditText) findViewById(R.id.ed_average_bill);
		// ed_minimum_order = (EditText) findViewById(R.id.ed_minimum_order);

		rg_last_minute_booking = (RadioGroup) findViewById(R.id.rg_last_minute_booking);
		rb_last_minute_booking_yes = (RadioButton) findViewById(R.id.rb_last_minute_booking_yes);
		rb_last_minute_booking_no = (RadioButton) findViewById(R.id.rb_last_minute_booking_no);

		ed_iban = (EditText) findViewById(R.id.ed_iban);
		ed_no = (EditText) findViewById(R.id.ed_no);
		ed_email = (EditText) findViewById(R.id.ed_email);
		ed_aboutrest = (EditText) findViewById(R.id.ed_aboutrest);
		// **************************************************
		img_home = (ImageView) findViewById(R.id.img_home);
		sp_rf_gs_country = (Spinner) findViewById(R.id.sp_rf_gs_country);
		sp_rf_gs_city = (Spinner) findViewById(R.id.rf_registration_gs_sp_city);
		sp_rf_gs_region = (Spinner) findViewById(R.id.sp_rf_gs_region);

		ch_gs_pickup = (CheckBox) findViewById(R.id.ch_gs_pickup);
		ch_gs_delivery = (CheckBox) findViewById(R.id.ch_gs_delivery);
		// ch_gs_indine = (CheckBox) findViewById(R.id.ch_gs_indine);

		img_gs_upload_icon = (ImageView) findViewById(R.id.img_gs_upload_icon);
		img_gs_upload_banner = (ImageView) findViewById(R.id.img_gs_upload_banner);
		rf_registration_gs_ed_fname = (EditText) findViewById(R.id.rf_registration_gs_ed_fname);
		rf_registration_gs_ed_lname = (EditText) findViewById(R.id.rf_registration_gs_ed_lname);
		// rf_registration_gs_ed_pwd = (EditText)
		// findViewById(R.id.rf_registration_gs_ed_pwd);
		// rf_registration_gs_ed_pwd1 = (EditText)
		// findViewById(R.id.rf_registration_gs_ed_pwd1);
		rf_registration_gs_ed_restaurant = (EditText) findViewById(R.id.rf_registration_gs_ed_restaurant);
		rf_registration_gs_ed_remail = (EditText) findViewById(R.id.rf_registration_gs_ed_email);
		rf_registration_gs_ed_rphone = (EditText) findViewById(R.id.rf_registration_gs_ed_rphone);
		rf_registration_gs_ed_add = (EditText) findViewById(R.id.rf_registration_gs_ed_add);
		rf_registration_gs_ed_zip_code = (EditText) findViewById(R.id.rf_registration_gs_ed_zip_code);
		rf_registration_gs_ed_website = (EditText) findViewById(R.id.rf_registration_gs_ed_website);
		rf_registration_gs_ed_made_deliver = (EditText) findViewById(R.id.rf_registration_gs_ed_made_deliver);
		rf_registration_gs_ed_latitude = (EditText) findViewById(R.id.rf_registration_gs_ed_latitude);
		rf_registration_gs_ed_longitude = (EditText) findViewById(R.id.rf_registration_gs_ed_longitude);
		rf_registration_gs_btn_confirme = (Button) findViewById(R.id.rf_registration_gs_btn_confirme);
		rf_registration_gs_btn_map = (Button) findViewById(R.id.rf_registration_gs_btn_map);
		rf_registration_gs_btn_cancel = (Button) findViewById(R.id.rf_registration_gs_btn_cancel);
		// rp_btn_cancel=(Button)
		// findViewById(R.id.rf_registration_gs_btn_cancel);
		rg_rf_gs_regi = (RadioGroup) findViewById(R.id.rg_rf_gs_regi);
		txv_rf_gs_country_code = (TextView) findViewById(R.id.txv_rf_gs_country_code);
		Yes = (ImageView) findViewById(R.id.gs_yes);
		No = (ImageView) findViewById(R.id.gs_wrong);
		ll_googlemap = (LinearLayout) findViewById(R.id.google_gs_map_layout);
		ll_registration = (ScrollView) findViewById(R.id.ll_registration);
		sp_gs_mto = (Spinner) findViewById(R.id.sp_gs_mto);
		adapter_sp_gs_mto = ArrayAdapter.createFromResource(
				GlobalSettingActivity.this, R.array.array_mto,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_sp_gs_mto
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_gs_mto.setAdapter(adapter_sp_gs_mto);
		try {
			if (Global_variable.logindata.getJSONObject("global_setting").has(
					"mto")) {
				String mto = Global_variable.logindata.getJSONObject(
						"global_setting").getString("mto");
				if (mto.equalsIgnoreCase("0") || mto.equalsIgnoreCase("1")) {
					int indexlevel = adapter_sp_gs_mto
							.getPosition(getResources().getString(
									R.string.mto_order)
									+ " "
									+ mto
									+ " "
									+ getResources().getString(
											R.string.mto_1before));
					Log.i("indexCapacity", "indexCapacity" + indexlevel);
					System.out.println("indexlevelmto" + indexlevel);
					sp_gs_mto.setSelection(indexlevel);

				} else {
					int indexlevel = adapter_sp_gs_mto
							.getPosition(getResources().getString(
									R.string.mto_order)
									+ " "
									+ mto
									+ " "
									+ getResources().getString(
											R.string.daybefore));
					Log.i("indexCapacity", "indexCapacity" + indexlevel);
					System.out.println("indexlevelmto" + indexlevel);
					sp_gs_mto.setSelection(indexlevel);
				}
				// + " " + getResources().getString(R.string.daybefore));
				// int indexlevel = adapter_sp_gs_mto
				// .getPosition(Global_variable.logindata.getJSONObject(
				// "global_setting").getString("mto"));

			}
		} catch (NotFoundException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (Global_variable.array_CountryArray.length() != 0) {
			for (int i = 0; i < Global_variable.array_CountryArray.length(); i++) {
				try {
					JSONObject json_object = Global_variable.array_CountryArray
							.getJSONObject(i);
					hashmap_country = new HashMap<String, String>();
					str_country_id = json_object.getString("country_id");
					str_country_name = json_object.getString("cname_en");
					str_country_call_code = json_object
							.getString("country_call_code");
					hashmap_country.put("str_country_id", str_country_id);
					hashmap_country.put("str_country_name", str_country_name);
					hashmap_country.put("str_country_call_code",
							str_country_call_code);
					// countryarraylist.add(SR_CountryID);
					countryarraylist.add(str_country_name);
					hash_countryarraylist.add(hashmap_country);
					adapter_country = new ArrayAdapter<String>(
							GlobalSettingActivity.this,
							android.R.layout.simple_spinner_dropdown_item,
							countryarraylist);
					sp_rf_gs_country.setAdapter(adapter_country);
					try {
						int indexlevel = adapter_country
								.getPosition(Global_variable.logindata
										.getJSONObject("global_setting")
										.getString("cname_en").toString());
						Log.i(getString(R.string.str_indexCapacity),
								getString(R.string.str_indexCapacity)
										+ indexlevel);
						sp_rf_gs_country.setSelection(indexlevel);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		if (Global_variable.array_RegionArray.length() != 0) {
			for (int i = 0; i < Global_variable.array_RegionArray.length(); i++) {
				try {
					JSONObject json_object = Global_variable.array_RegionArray
							.getJSONObject(i);
					hashmap_region = new HashMap<String, String>();
					str_country_id = json_object.getString("country_id");
					str_region_id = json_object.getString("region_id");
					str_region_name = json_object.getString("name_en");
					hashmap_region.put("str_country_id", str_country_id);
					hashmap_region.put("str_region_id", str_region_id);
					hashmap_region.put("str_region_name", str_region_name);
					regionarraylist.add(str_region_name);
					hash_regionarraylist.add(hashmap_region);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (Global_variable.array_CitytArray.length() != 0) {
			for (int i = 0; i < Global_variable.array_CitytArray.length(); i++) {
				try {
					JSONObject json_object = Global_variable.array_CitytArray
							.getJSONObject(i);
					hashmap_city = new HashMap<String, String>();
					str_region_id = json_object.getString("region_id");
					str_city_id = json_object.getString("city_id");
					str_city_name = json_object.getString("name_en");
					hashmap_city.put("str_region_id", str_region_id);
					hashmap_city.put("str_city_id", str_city_id);
					hashmap_city.put("str_city_name", str_city_name);
					cityarraylist.add(str_city_name);
					hash_cityarraylist.add(hashmap_city);
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
	}
	@Override
	public void onMarkerDragEnd(Marker marker) {
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
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	private void selectImage() {
		final CharSequence[] options = {
				getResources().getString(R.string.str_take_photo),
				getResources().getString(R.string.str_choose_from),
				getResources().getString(R.string.csm_cancel)};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				GlobalSettingActivity.this);
		builder.setTitle(getResources().getString(R.string.str_add_photo));
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals(getResources().getString(
						R.string.str_take_photo))) {
					/*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);*/
					SELECT = "CAMERA";
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
					startActivityForResult(intent,
							CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
					
					
					
				} else if (options[item].equals(getResources().getString(
						R.string.str_choose_from))) {
					SELECT = "GALLERY";
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent,
							GALLERY_CAPTURE_IMAGE_REQUEST_CODE);

				} else if (options[item].equals(getResources().getString(
						R.string.str_Cancel))) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				// successfully captured the image
				// launching upload activity
				launchUploadActivity(true);

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();

			}
			// else {
			// // failed to capture image
			// Toast.makeText(getApplicationContext(),
			// "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
			// .show();
			// }

		} else if (requestCode == GALLERY_CAPTURE_IMAGE_REQUEST_CODE) {
			System.out.println("wdqwd==");
			if (resultCode == RESULT_OK) {

				// successfully captured the image
				// launching upload activity
				System.out.println("wdqwd==ok");
				fileUri = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				System.out.println("path of image from gallery====cursur=="
						+ fileUri);
				System.out.println("FILEPATHCURSOR==" + filePath);
				Cursor c = getContentResolver().query(fileUri, filePath, null,
						null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				picturePath = c.getString(columnIndex);
				// launchUploadActivity(true);

				c.close();
				System.out.println("pictuerpathfromexternal" + picturePath);
				// Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				System.out.println("path of image from gallery====" + fileUri);
				Log.i("selectedImage", "path of image from gallery......"
						+ fileUri);

				runOnUiThread(new Runnable() {
					public void run() {
						// tv.setText("File Upload Completed.");
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
								.permitAll().build();
						StrictMode.setThreadPolicy(policy);
						System.out.println("strictmodthead");
						launchUploadActivity(true);

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!" + picturePath);

						// Toast.makeText(MainActivity.this,
						// "File Upload Start.", Toast.LENGTH_SHORT).show();
					}
				});

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();

			}
			// else {
			// // failed to capture image
			// Toast.makeText(getApplicationContext(),
			// "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
			// .show();
			// }

		} else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				// video successfully recorded
				// launching upload activity
				launchUploadActivity(false);

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled recording
				Toast.makeText(getApplicationContext(),
						"User cancelled video recording", Toast.LENGTH_SHORT)
						.show();

			} else {
				// failed to record video
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to record video", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}


	private void launchUploadActivity(boolean isImage) {
		System.out.println("ayuknai");
		// System.out.println("inlaunchmethodfileuri" + fileUri.getPath());
		System.out.println("inlaunchmethodpicturePath" + picturePath);

		if (SELECT.equalsIgnoreCase("CAMERA")) {
			UploadActivity(fileUri.getPath());
		} else {
			UploadActivity(picturePath);
		}
	}
	private void UploadActivity(String path) {
		// TODO Auto-generated method stub
		if (path != null) {
			// Displaying the image or video on the screen
			//Toast.makeText(getApplicationContext(), "file path is correct",
				//	Toast.LENGTH_LONG).show();

			previewMedia(path);
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}
	}

	private void previewMedia(String path) {
		// Checking whether captured media is image or video
		System.out.println("imgPreview==visible");
		// bimatp factory
		BitmapFactory.Options options = new BitmapFactory.Options();

		// down sizing image as it throws OutOfMemory Exception for larger
		// images
		options.inSampleSize = 8;
		System.out.println("filepathein previewmwdia" + path);
		final Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		if (image_click == true) {
			img_gs_upload_icon.setImageBitmap(bitmap);
		} else {
			img_gs_upload_banner.setImageBitmap(bitmap);
		}

		new UploadFileToServer().execute();
	}

	private class UploadFileToServer extends AsyncTask<Void, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			System.out.println("uploadfiletoserver===");
			// setting progress bar to zero
			p = new ProgressDialog(GlobalSettingActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			System.out.println("amagyudoingma");

			if (SELECT.equalsIgnoreCase("CAMERA")) {
				return uploadFile(fileUri.getPath());
			} else {
				return uploadFile(picturePath);
			}
		}

		protected void onPostExecute(Void result) {
			
			// super.onPostExecute(result);
		}

	}
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Response from Servers")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	public int uploadFile(String sourceFileUri) {
		/*
		 * dialog = ProgressDialog.show(GlobalSettingActivity.this, "",
		 * getResources().getString(R.string.str_Uploading), true);
		 */
		/*
		 * p = new ProgressDialog(activity); p.setMessage("Please wait...");
		 * p.setCancelable(false); p.setIcon(R.drawable.ic_launcher); p.show();
		 */

		String fileName = sourceFileUri;

		System.out.println("!!!!pankaj_sakariya_file_upload_uri"
				+ sourceFileUri);

		try {
			if (!sourceFileUri.equalsIgnoreCase("")
					|| !sourceFileUri.equalsIgnoreCase("null")
					|| !sourceFileUri.equalsIgnoreCase(null)) {

				HttpURLConnection conn = null;
				DataOutputStream dos = null;
				String lineEnd = "\r\n";
				String twoHyphens = "--";
				String boundary = "*****";
				int bytesRead, bytesAvailable, bufferSize;
				byte[] buffer;
				int maxBufferSize = 1 * 1024 * 1024;
				File sourceFile = new File(sourceFileUri);
				if (!sourceFile.isFile()) {
					Log.e(getString(R.string.str_uploadFile),
							getString(R.string.str_Source_File_not_exist));
					return 0;
				}
				try { // open a URL connection to the Servlet
					FileInputStream fileInputStream = new FileInputStream(
							sourceFile);
					URL url = new URL(Global_variable.upLoadServerUri);
					conn = (HttpURLConnection) url.openConnection(); // Open a
																		// HTTP
																		// connection
																		// to
																		// the
																		// URL
					conn.setDoInput(true); // Allow Inputs
					conn.setDoOutput(true); // Allow Outputs
					conn.setUseCaches(false); // Don't use a Cached Copy
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Connection", "Keep-Alive");
					conn.setRequestProperty("ENCTYPE", "multipart/form-data");
					conn.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					conn.setRequestProperty("uploaded_file", fileName);
					dos = new DataOutputStream(conn.getOutputStream());
					System.out.println("!!!!DOS" + dos.toString());
					System.out.println("!!!!url" + url);
					System.out.println("!!!!filename" + fileName);

					dos.writeBytes(twoHyphens + boundary + lineEnd);
					dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
							+ fileName + "\"" + lineEnd);
					dos.writeBytes(lineEnd);

					bytesAvailable = fileInputStream.available(); // create a
																	// buffer of
																	// maximum
																	// size

					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					buffer = new byte[bufferSize];

					// read file and write it into form...
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					while (bytesRead > 0) {
						dos.write(buffer, 0, bufferSize);
						bytesAvailable = fileInputStream.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						bytesRead = fileInputStream.read(buffer, 0, bufferSize);
					}

					// send multipart form data necesssary after file data...
					dos.writeBytes(lineEnd);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

					// Responses from the server (code and message)
					serverResponseCode = conn.getResponseCode();
					String serverResponseMessage = conn.getResponseMessage();

					Log.i(getString(R.string.str_uploadFile),
							getString(R.string.str_http_response)
									+ serverResponseMessage + ": "
									+ serverResponseCode);
					if (serverResponseCode == 200) {
						runOnUiThread(new Runnable() {
							public void run() {
								/*
								 * if (p.isShowing()) { p.dismiss(); }
								 */
								// tv.setText("File Upload Completed.");
								// Toast.makeText(Food_Detail_Parent_Activity.this,
								// "File Upload Complete.",
								// Toast.LENGTH_SHORT).show();
							}
						});
					}

					// close the streams //
					fileInputStream.close();
					dos.flush();
					dos.close();

					InputStream stream = conn.getInputStream();
					InputStreamReader isReader = new InputStreamReader(stream);

					// put output stream into a string
					BufferedReader br = new BufferedReader(isReader);

					String line = "";
					while ((line = br.readLine()) != null) {
						System.out.println(line);
						result += line;
					}

					br.close();
					System.out.println("!!!!result" + result);

					if (result.equalsIgnoreCase(getResources().getString(
							R.string.str_Please_Try_again))) {
						// Toast some error
						// Toast.makeText(Food_Detail_Parent_Activity.this,
						// ""+result,
						// Toast.LENGTH_LONG).show();
					} else {
						p.dismiss();
						if (image_click == true) {
							String helloWorld = result;
							String hellWrld = helloWorld.replace(
									Global_variable.img_pre_path, "");
							result_icon = hellWrld;
							// result_icon=result;

							System.out.println("final_uploaded_URL_icon"
									+ result_icon);
						} else {
							String helloWorld = result;
							String hellWrld = helloWorld.replace(
									Global_variable.img_pre_path, "");
							result_baner = hellWrld;

							// result_baner=result;
							System.out.println("final_uploaded_URL_banner"
									+ result_baner);
						}

						
						// Toast.makeText(Food_Detail_Parent_Activity.this,
						// ""+result,
						// Toast.LENGTH_LONG).show();
					}

				} catch (MalformedURLException ex) {
					// dialog.dismiss();
					ex.printStackTrace();
					Toast.makeText(GlobalSettingActivity.this,
							R.string.str_MalformedURLException,
							Toast.LENGTH_SHORT).show();
					Log.e(getString(R.string.str_Upload_file_to),
							getString(R.string.str_error) + ex.getMessage(), ex);
				} catch (Exception e) {
					// dialog.dismiss();
					e.printStackTrace();
					Toast.makeText(GlobalSettingActivity.this,
							R.string.str_Exception + e.getMessage(),
							Toast.LENGTH_SHORT).show();
					Log.e(getString(R.string.str_Upload_file_to_server),
							R.string.str_Exception + e.getMessage(), e);
				}
				// dialog.dismiss();
				return serverResponseCode;

			} else {
				// dialog.dismiss();
				// Toast.makeText(getApplicationContext(),
				// "pankaj sakariya else part"+sourceFileUri+"file name",
				// Toast.LENGTH_LONG).show();
				return serverResponseCode = 0;
			}
		} catch (Exception e) {
			// dialog.dismiss();
			e.printStackTrace();
			// Toast.makeText(getApplicationContext(), "pankaj sakariya catch",
			// Toast.LENGTH_LONG).show();
			return serverResponseCode = 0;
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
			jsonStr1 = sh.makeServiceCall(
					"http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ Global_variable.latitude + ","
							+ Global_variable.longitude + "&sensor=true",
					ServiceHandler.GET);
			if (jsonStr1 != null) {
				try {
					JSONObject jsonObj = (JSONObject) new JSONTokener(jsonStr1)
							.nextValue();
					JSONArray Results = jsonObj.getJSONArray("results");
					JSONObject zero = Results.getJSONObject(0);
					JSONArray address_components = zero
							.getJSONArray("address_components");
					for (int i = 0; i < address_components.length(); i++) {
						JSONObject zero2 = address_components.getJSONObject(i);
						String long_name = zero2.getString("long_name");
						JSONArray mtypes = zero2.getJSONArray("types");
						String Type = mtypes.getString(0);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("jsonstr is null");
			}
			return null;
		}
		protected void onPostExecute(Void result) {
			rf_registration_gs_ed_latitude.setText(Global_variable.latitude
					+ "");
			rf_registration_gs_ed_longitude.setText(Global_variable.longitude
					+ "");
			super.onPostExecute(result);
		}
	}

	public class update_global_setting extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		protected void onPreExecute() {
			super.onPreExecute();
			p = new ProgressDialog(GlobalSettingActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
		}
		protected Void doInBackground(Void... params) {
			JSONObject obj_parent = new JSONObject();
			JSONObject obj_main = new JSONObject();
			try {

				obj_parent.put("gender", str_gender);
				obj_parent.put("FirstName", rf_registration_gs_ed_fname
						.getText().toString());
				obj_parent.put("LastName", rf_registration_gs_ed_lname
						.getText().toString());
				obj_parent.put("password", rf_registration_gs_ed_fname
						.getText().toString());
				obj_parent.put("country_id", str_selected_country_id);
				obj_parent.put("region_id", str_selected_region_id);
				obj_parent.put("city_id", str_selected_city_id);
				obj_parent.put("name_en", rf_registration_gs_ed_restaurant
						.getText().toString());
				obj_parent.put("contact_email", rf_registration_gs_ed_remail
						.getText().toString());
				obj_parent.put("country_call_id", txv_rf_gs_country_code
						.getText().toString());
				obj_parent.put("contact_number", rf_registration_gs_ed_rphone
						.getText().toString());
				obj_parent.put("street", rf_registration_gs_ed_add.getText()
						.toString());
				obj_parent.put("zip", rf_registration_gs_ed_zip_code.getText()
						.toString());
				obj_parent.put("website", rf_registration_gs_ed_website
						.getText().toString());
				obj_parent
						.put("reservation_made_daily",
								rf_registration_gs_ed_made_deliver.getText()
										.toString());
				obj_parent.put("map_lat", rf_registration_gs_ed_latitude
						.getText().toString());
				obj_parent.put("map_long", rf_registration_gs_ed_longitude
						.getText().toString());

				obj_parent.put("featured_icon", result_icon);
				obj_parent.put("featured_image", result_baner);
				obj_parent.put("pickupavail", str_pickup);
				obj_parent.put("delivery_avail", str_delivery);
				// **********************************
				obj_parent.put("iban_number", ed_iban.getText().toString());
				obj_parent
						.put("bank_mobile_number", ed_no.getText().toString());
				obj_parent.put("bank_email", ed_email.getText().toString());
				obj_parent.put("restaurant_seat", ed_restaurant_seat.getText()
						.toString());
				obj_parent.put("delivery_min_order", ed_average_bill.getText()
						.toString());
				obj_parent.put("accept_last_minute_booking",
						str_rb_last_minute_booking);
				obj_parent.put("comment", ed_aboutrest.getText().toString());
				// obj_parent.put("minimum_req_order",
				// ed_minimum_order.getText().toString());
				// ***********************************
				// obj_parent.put("indine_avail",str_indine);
				obj_parent.put("mto", sp_index + "");

				obj_main.put("global_setting", obj_parent);
				obj_main.put("sessid", Global_variable.sessid.toString());
				obj_main.put("restaurant_id", Global_variable.restaurant_id);
				obj_main.put("admin_id", Global_variable.admin_uid);
				System.out.println("obj_main" + obj_main);
				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						GlobalSettingActivity.this,
						Global_variable.rf_update_global_setting, obj_main));
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
			System.out.println("output" + obj_output);
			if (p.isShowing()) {
				p.dismiss();
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {
					Global_variable.logindata.put(
							"global_setting",
							obj_output.getJSONObject("data").getJSONObject(
									"global_setting"));
					System.out.println("globalsettingupdate"
							+ Global_variable.logindata.put("global_setting",
									obj_output.getJSONObject("data")
											.getJSONObject("global_setting")));
					Toast.makeText(getApplicationContext(),
							R.string.str_update_succ, Toast.LENGTH_LONG).show();
					Intent i = new Intent(GlobalSettingActivity.this,
							Home_Activity.class);
					startActivity(i);
				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("iban_number")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("iban_number").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("bank_mobile_number")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("bank_mobile_number")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("bank_email")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("bank_email").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("restaurant_seat")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("restaurant_seat")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("delivery_min_order")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("delivery_min_order")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("restaurant_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("restaurant_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("admin_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("admin_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("gender")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("gender").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("FirstName")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("FirstName").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("LastName")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("LastName").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("country_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("country_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("region_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("region_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("contact_email")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("contact_email").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("country_call_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("country_call_id")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("street")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("street").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("zip")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("zip").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("website")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("website").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("reservation_made_daily")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray(
											"reservation_made_daily").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("map_lat")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("map_lat").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("map_long")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("map_long").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}

						if (Errors.has("featured_icon")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("featured_icon").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("featured_image")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("featured_image")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("pickupavail")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("pickupavail").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("delivery_avail")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("delivery_avail")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("indine_avail")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("indine_avail").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("mto")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("mto").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}

					}

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
		getMenuInflater().inflate(R.menu.manage_subscription, menu);
		return true;
	}

}
