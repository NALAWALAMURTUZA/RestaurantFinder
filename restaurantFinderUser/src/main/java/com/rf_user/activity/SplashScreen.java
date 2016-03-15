package com.rf_user.activity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;

public class SplashScreen extends Activity implements LocationListener {
	Intent in;
	/** Called when the activity is first created. */
	// long sp_time = 5000;
	// long ms = 0;
	// boolean sp_Activity = true;
	// boolean pause = false;

	GoogleMap googleMap;
	String provider;
	LocationManager locationManager;

	ConnectionDetector cd;

	com.rf_user.connection.GPSTracker gps;
	Timer timer2;
	boolean firstTime = true;

	/* Declaration for http call */
	HttpConnection http = new HttpConnection();

	HashMap Hashmap_Region = new HashMap();
	String[] Region_Array;
	String TAG_SUCCESS = "success";
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.rf_splash_background);

		
		try {

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());
			
			SharedPreference.setvalue(getApplicationContext(), "lang", "en");

			// initializeMap();
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {
//				in = new Intent(SplashScreen.this, FindRestaurant.class);
//				startActivity(in);
//karyu
				getlatitude();

				// new async_region().execute();

			} else {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub

						// do {

						Toast.makeText(SplashScreen.this,
								R.string.No_Internet_Connection,
								Toast.LENGTH_LONG).show();

						System.out.println("do-while");
						if (cd.isConnectingToInternet()) {

							// new async_region().execute();
//							getlatitude();

						}
						// } while (cd.isConnectingToInternet() == false);

					}

				});
			}

		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (Exception n) {
			n.printStackTrace();
		}

	}


	/* Geosearch api to fill Region spinner */

	public class async_region extends AsyncTask<Void, Void, Void> {
		JSONObject region_obj;
		String responseText;

		// ProgressDialog region_dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			System.out.println("!!!!!!!!!!!!!!first time...");

			// region_dialog = new ProgressDialog(FindRestaurant.this);
			// region_dialog.setIndeterminate(false);
			// region_dialog.setCancelable(true);
			// region_dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			region_obj = new JSONObject();

			try {
				region_obj.put("type", "Region");
				System.out.println("fix_region_spinner" + region_obj);
				if (Global_variable.Region_Parent_id != null) {
					region_obj.put("parent_id",
							Global_variable.Region_Parent_id);
				} else {
					region_obj.put("parent_id", "");
				}

				System.out.println("fix_region_spinner" + region_obj);

				if (Global_variable.FR_region_flag == true) {
					System.out.println("!!!!!!!!!!!!!!!!sessid.."
							+ Global_variable.sessid);
					region_obj.put("sessid", Global_variable.sessid);
					System.out.println("!!!!!!!!!!!!!!!!!!!!in flag true"
							+ region_obj);
				} else {
					System.out.println("!!!!!!!!!!!!!!regionPrefrence"
							+ SharedPreference
									.getsessid(getApplicationContext()));
					if (SharedPreference.getsessid(getApplicationContext()) != "") {
						System.out.println("!!!!! in not null");
						if (SharedPreference.getsessid(getApplicationContext())
								.length() != 0) {
							System.out.println("!!!!! in length not null");
							region_obj.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));

							System.out
									.println("!!!!!!!!!!!!!!!!sessid.."
											+ SharedPreference
													.getsessid(getApplicationContext()));

						}

					} else {
						System.out.println("!!!!! in else");
						region_obj
								.put("sessid", "565fgfgfgfggdxdxdxdxd56654jh");
					}
				}

				System.out.println("session_id" + region_obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}

			// Check for success tag
			try {

				// *************
				// for request
				responseText = http.connection(SplashScreen.this,
						 Global_variable.rf_Geosearch_api_path, region_obj);

				System.out.println("!!!!!!!!!!!!!!!!!in background"
						+ responseText);

			} catch (NullPointerException e1) {
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

				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					if (responseText != null) {
						JSONObject json = new JSONObject(responseText);
						System.out.println("spinner_last_json" + json);
						// json success tag
						String success1 = json.getString(TAG_SUCCESS);
						System.out.println("tag" + success1);
						// JSONObject data = json.getJSONObject("data");
						JSONArray response_array = json.getJSONArray("data");
						System.out.println("rsponse_get_parameter"
								+ response_array);
						int length = response_array.length();
						System.out.println("lenth" + length);
						Region_Array = new String[length];
						Global_variable.Region_Array = new String[length];
						Global_variable.hashmap_region = new HashMap<String, String>();
						Hashmap_Region = new HashMap<String, String>();

						for (int i = 0; i <= length; i++) {
							try {
								JSONObject array_Object = response_array
										.getJSONObject(i);
								String Region_Name = array_Object
										.getString("name");
								System.out.println("Region_Name" + Region_Name);
								String Region_Id = array_Object.getString("id");
								System.out.println("Region_Id" + Region_Name);
								Hashmap_Region.put(Region_Name, Region_Id);
								Global_variable.Region_Array[i] = Region_Name
										.toString();
								Region_Array[i] = Region_Name.toString();

								Global_variable.hashmap_region.put(Region_Name,
										Region_Id);

								System.out.println("Region_Array[" + i + "]"
										+ Region_Array[i]);
							} catch (Exception ex) {
								System.out.println("Error" + ex);
							}
						}
						// Region_spinner();
						System.out.println("HashMapValue"
								+ Global_variable.hashmap_region);

						for (String str : Region_Array) {

							System.out
									.println("!!!!!!!!!!!!!!!!!!!!region_values_find_normal_async..."
											+ str);

						}

						for (String str : Global_variable.Region_Array) {

							System.out
									.println("!!!!!!!!!!!!!!!!!!!!region_values_find_async..."
											+ str);

						}

						// System.out.println("!!!!!!!In async...Region"+Region_Array+"...."+selectedregion+"....."+Region_Array[selectedregion]);
						//
						//
						// Select_region_textview
						// .setText(Region_Array[selectedregion]);
						//
						// Region_id = (String) Hashmap_Region
						// .get(Select_region_textview.getText().toString());
						// System.out.println("Region_id_value" + Region_id);
						//
						//
						// if(region_flag==false)
						// {
						// new async_city_Spinner().execute();
						// }

						// Select_region_textview.setClickable(true);

						try {
							if (Global_variable.Region_Array.length != 0) {
								// setlistener();
							} else {
								System.out.println("Region_Array null");
							}
						} catch (NullPointerException e) {
							e.printStackTrace();
						}

						System.out.println("!!!!!!!!!!!!!!!latitude..."
								+ Global_variable.latitude);
						System.out.println("!!!!!!!!!!!!!!!longitude..."
								+ Global_variable.longitude);

						//karyu
						getlatitude();

					} else {
						// new async_region().execute();
					}

				} else {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// TODO Auto-generated method stub

							// do {

							Toast.makeText(SplashScreen.this,
									R.string.No_Internet_Connection,
									Toast.LENGTH_LONG).show();

							System.out.println("do-while");
							if (cd.isConnectingToInternet()) {

								new async_region().execute();

								// getlatitude();

							}
							// } while (cd.isConnectingToInternet() == false);

						}

					});

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			// Select_region_textview.setClickable(true);

		}

	}

	private void getlatitude() {
		// TODO Auto-generated method stub

		System.out.println("!!!!!!!!!!!!!!!latitude..."
				+ Global_variable.latitude);
		System.out.println("!!!!!!!!!!!!!!!longitude..."
				+ Global_variable.longitude);

		try {
			// if (Global_variable.latitude == 0.0
			// && Global_variable.longitude == 0.0) {
			gps = new com.rf_user.connection.GPSTracker(SplashScreen.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

				Global_variable.latitude = gps.getLatitude();
				Global_variable.longitude = gps.getLongitude();

				// \n is for new line
				// Toast.makeText(
				// getApplicationContext(),
				// "Your Location is - \nLat: "
				// + Global_variable.latitude + "\nLong: "
				// + Global_variable.longitude,
				// Toast.LENGTH_LONG).show();

				if (Global_variable.latitude != 0.0
						&& Global_variable.longitude != 0.0) {
					initialize();

					setlistner();

					// Thread my = new Thread() {
					// public void run() {
					// try {
					// while (sp_Activity && ms < sp_time) {
					// if (!pause)
					// ms = ms + 100;
					// sleep(100);
					// }
					// }
					//
					// catch (Exception e) {
					// // TODO: handle exception
					// } finally {
//					karyu
					in = new Intent(SplashScreen.this, FindRestaurant.class);
					startActivity(in);
					// }
					// }
					// };
					// my.start();
				} else {

					try {
						gps.showSettingsAlert();
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (StackOverflowError e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					// Toast.makeText(getApplicationContext(),
					// "Your GPS is off", Toast.LENGTH_LONG).show();

					// gps.showSettingsAlert();
				}

			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings

				// Toast.makeText(SplashScreen.this,
				// "Your GPS is off", Toast.LENGTH_LONG).show();

				gps.showSettingsAlert();

				// getlatitude();
			}
			// }

		} catch (IllegalStateException exception) {
			exception.printStackTrace();
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		} catch (StackOverflowError exception) {
			exception.printStackTrace();
		}

	}

	/** Default Map is Initialize */
	private void initializeMap() {
		if (googleMap == null) {
			// googleMap = ((MapFragment) getFragmentManager().findFragmentById(
			// R.id.splashmap)).getMap();
			// if (googleMap != null) {
			//
			// displayMap();
			//
			// }
		}
	}

	/** Display The Current Location on Map */
	private void displayMap() {
		// Enable MyLocation Layer of Google Map

		try {

			googleMap.setMyLocationEnabled(true);

			// Get LocationManager object from System Service LOCATION_SERVICE
			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Create a criteria Object to retrieve provider
			Criteria criteria = new Criteria();

			// Get the name of best provider
			provider = locationManager.getBestProvider(criteria, true);
			// Get Current Location
			final Location myLocation = locationManager
					.getLastKnownLocation(provider);

			// Get latitude of Current Location
			try {
				Global_variable.user_latitide = myLocation.getLatitude();
				Log.i("Mylocation latitude", "GPS latitude"
						+ Global_variable.user_latitide);

				// Get longitude of Current Location
				Global_variable.user_longitude = myLocation.getLongitude();
				Log.i("Mylocation longitude", "GPS longitude"
						+ Global_variable.user_longitude);
				// Create a LatLng object for the current location
				LatLng latLng = new LatLng(Global_variable.user_latitide,
						Global_variable.user_longitude);

				locationManager.requestLocationUpdates(provider, 2000, 0, this);
				// set Map Type
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

				//
			} catch (NullPointerException n) {
				n.printStackTrace();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void setlistner() {
		// TODO Auto-generated method stub
		// imgsplash.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// in = new Intent(SplashScreen.this, HomeScreen.class);
		// startActivity(in);
		//
		// }
		// });
	}

	private void initialize() {
		// TODO Auto-generated method stub
		// imgsplash = (ImageView) findViewById(R.id.img_splash);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("test", "onResume");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {

			// new async_region().execute();

			//karyu
			getlatitude();

		} else {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// TODO Auto-generated method stub

					// do {

					Toast.makeText(SplashScreen.this,
							R.string.No_Internet_Connection, Toast.LENGTH_LONG)
							.show();

					System.out.println("do-while");
					if (cd.isConnectingToInternet()) {

						// new async_region().execute();

						//karyu
						getlatitude();

					}
					// } while (cd.isConnectingToInternet() == false);

				}

			});
		}

		// if (firstTime) {
		// Log.i("test", "it's the first time");
		// firstTime = false;
		// }
		//
		// else {
		//
		// if (Global_variable.latitude != 0.0
		// && Global_variable.longitude != 0.0) {
		// initialize();
		//
		// setlistner();
		//
		//
		//
		//
		// // Thread my = new Thread() {
		// // public void run() {
		// // try {
		// // while (sp_Activity && ms < sp_time) {
		// // if (!pause)
		// // ms = ms + 100;
		// // sleep(100);
		// // }
		// // }
		// //
		// // catch (Exception e) {
		// // // TODO: handle exception
		// // } finally {
		// // in = new Intent(SplashScreen.this,
		// // FindRestaurant.class);
		// // startActivity(in);
		// // }
		// // }
		// // };
		// // my.start();
		// } else {
		//
		// getlatitude();
		//
		// // Toast.makeText(getApplicationContext(),
		// // "Your GPS is off", Toast.LENGTH_LONG).show();
		//
		// // gps.showSettingsAlert();
		// }
		//
		// Log.i("test", "it's not the first time");
		//
		// }

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
