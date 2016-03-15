package com.rf_user.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_Bean.DatabaseData;
import com.rf_user.sqlite_Bean.DateDetail;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class FindRestaurant extends Activity {
	TextView Find_restaurant, Select_city_textview, Select_region_textview;
	String str_previous_city;
	public String previous_city_id;
	TextView TXV_Menu_Button;
	String flag;
	HashMap Hasmap_City = new HashMap();
	String TAG_SUCCESS = Messages.getString("FindRestaurant.0"); //$NON-NLS-1$

	Dialog ExitAppDialog;
	public static ImageView img_yes, img_no;
	/** Declaration for init */
	ProgressDialog progressDialog;
	com.rf_user.connection.GPSTracker gps;
	ImageView orderImageView, footer_viewmenu_img, footer_featured_img,
			footer_setting_img;
	String responseContent, data_Sql;
	JSONObject Json_Main;
	String success, message;
	JSONObject data, texts, cities, restaurantcategory;
	Iterator itr_keys_texts, itr_keys_cities, itr_keys_restaurantcategory;
	String str_key_texts, str_value_texts, str_key_cities, str_value_cities,
			str_key_restaurantcategory, str_value_restaurantcategory;

	/** Declaration for Find Restaurant */
	public Set<String> al_city;
	boolean dialogShown = false;
	Intent intent;
	/** Declaration for Delivery Areas */

	String str_select_delivery;
	String str_previous_delivery;
	public String delivery_id;
	public String previous_delivery_id;
	int selecteddelivery = 1;

	/** sqlite **/

	public static Boolean CheckNetworkandSQlitecall = false;
	List<DateDetail> Date_Detail;
	public static String log1 = Messages.getString("FindRestaurant.1"), datetime = Messages.getString("FindRestaurant.2"), DateDetail = Messages.getString("FindRestaurant.3"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			DB_Row_Init = Messages.getString("FindRestaurant.4"); //$NON-NLS-1$
	String log;

	/** Localization */
	boolean locale_flag = false;
	private ImageView img_en, img_ar;
	public static final String MyPREFERENCES = Messages.getString("FindRestaurant.5"); //$NON-NLS-1$
	SharedPreferences sharedpreferences;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	/* Declaration for http call */
	HttpConnection http = new HttpConnection();

	HashMap Hashmap_City = new HashMap();

	String[] City_Array;

	int selectedcity = 0;
	int selectedregion = 0;

	String str_select_region, str_select_city;
	public String Region_id, city_id;
	public boolean region_flag = false;

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {

			LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
			setContentView(R.layout.second_screen);
			ScrollView scrollable_contents = (ScrollView) findViewById(R.id.scrollableContents);
			getLayoutInflater().inflate(R.layout.findrestaurant_scrollview_content,
					scrollable_contents);
			Global_variable.login_user_id = SharedPreference
					.getuser_id(getApplicationContext());
			System.out.println(Messages.getString("FindRestaurant.6") //$NON-NLS-1$
					+ Global_variable.login_user_id);

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			this.img_en = (ImageView) findViewById(R.id.btn_en);
			this.img_ar = (ImageView) findViewById(R.id.btn_ar);
			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			Global_variable.abt_rest_flag = false;

			initializeWidget();
			DBAdapter.init(this);
			// /DBAdapter.deleteall();
			// Reading all contacts
			Log.d(Messages.getString("FindRestaurant.7"), Messages.getString("FindRestaurant.8")); //$NON-NLS-1$ //$NON-NLS-2$
			List<DatabaseData> data = DBAdapter.getAllData();

			for (DatabaseData dt : data) {
				if (dt.get_Api().equals(DB_Row_Init)) {
					log1 = dt.get_Api();
					data_Sql = dt.get_Data();
					datetime = dt.get_Timestamp();

					// Writing Contacts to log
					Log.d(Messages.getString("FindRestaurant.9"), log1); //$NON-NLS-1$
					Log.d(Messages.getString("FindRestaurant.10"), data_Sql); //$NON-NLS-1$
					Log.d(Messages.getString("FindRestaurant.11"), datetime); //$NON-NLS-1$
				}
			}

			try {

				String d = log1;
				System.out.println(Messages.getString("FindRestaurant.12") + log1 + Messages.getString("FindRestaurant.13") + d); //$NON-NLS-1$ //$NON-NLS-2$
				if (log1.equals(Messages.getString("FindRestaurant.14"))) { //$NON-NLS-1$

					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {
						try {
							new async_init().execute();
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
										Toast.LENGTH_LONG).show();
								// do {
								System.out.println(Messages.getString("FindRestaurant.15")); //$NON-NLS-1$
								if (cd.isConnectingToInternet()) {
									try {
										new async_init().execute();
									} catch (NullPointerException n) {
										n.printStackTrace();
									}
								}
								// } while (cd.isConnectingToInternet() ==
								// false);
							}

						});
					}

				} else {
					log1 = Messages.getString("FindRestaurant.16"); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.17") + data); //$NON-NLS-1$
					// DBAdapter.CheckTimeExpire();
					// DBAdapter.deleteall();
					new async_init().onPostExecute(null);
					Date_Detail = DBAdapter.CheckTimeExpire();
					System.out.println(Messages.getString("FindRestaurant.18") + Date_Detail); //$NON-NLS-1$
					if (Date_Detail.size() != 0) {
						System.out.println(Messages.getString("FindRestaurant.19") //$NON-NLS-1$
								+ Date_Detail.size());
						DBAdapter.CheckFortyeighthouser(Date_Detail);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Global_variable.FR_Region_Name != null) {
				if (Global_variable.FR_Region_Name.length() != 0) {
					if (!Global_variable.FR_Region_Name
							.equalsIgnoreCase(getString(R.string.Select_Region))) {
						Select_region_textview.setClickable(true);
						Select_region_textview
								.setText(Global_variable.FR_Region_Name);
					}

				}

			}

			if (Global_variable.FR_City_Name != null) {
				if (Global_variable.FR_City_Name.length() != 0) {
					if (!Global_variable.FR_City_Name
							.equalsIgnoreCase(getString(R.string.Register_Select_City))) {
						Select_city_textview.setClickable(true);
						Select_city_textview
								.setText(Global_variable.FR_City_Name);
					}

				}

			}

			setlistener();

			loadLocale();
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	public void setLocaleonload(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		System.out.println(Messages.getString("FindRestaurant.20")); //$NON-NLS-1$

	}

	public void setLocale(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		System.out.println(Messages.getString("FindRestaurant.21")); //$NON-NLS-1$

		Intent refresh = new Intent(this, FindRestaurant.class);
		startActivity(refresh);
	}

	public void loadLocale() {
		System.out.println(Messages.getString("FindRestaurant.22")); //$NON-NLS-1$
		String langPref = Messages.getString("FindRestaurant.23"); //$NON-NLS-1$
		SharedPreferences prefs = getSharedPreferences(Messages.getString("FindRestaurant.24"), //$NON-NLS-1$
				Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, Messages.getString("FindRestaurant.25")); //$NON-NLS-1$
		System.out.println(Messages.getString("FindRestaurant.26") + language); //$NON-NLS-1$

		changeLang(language);
	}

	public void saveLocale(String lang) {

		String langPref = Messages.getString("FindRestaurant.27"); //$NON-NLS-1$
		System.out.println(Messages.getString("FindRestaurant.28") + langPref); //$NON-NLS-1$
		SharedPreferences prefs = getSharedPreferences(Messages.getString("FindRestaurant.29"), //$NON-NLS-1$
				Activity.MODE_PRIVATE);
		System.out.println(Messages.getString("FindRestaurant.30") + prefs); //$NON-NLS-1$
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
	}

	public void changeLang(String lang) {
		System.out.println(Messages.getString("FindRestaurant.31")); //$NON-NLS-1$

		if (lang.equalsIgnoreCase(Messages.getString("FindRestaurant.32"))) //$NON-NLS-1$
			return;
		myLocale = new Locale(lang);
		System.out.println(Messages.getString("FindRestaurant.33") + myLocale); //$NON-NLS-1$
		if (myLocale.toString().equalsIgnoreCase(Messages.getString("FindRestaurant.34"))) { //$NON-NLS-1$
			System.out.println(Messages.getString("FindRestaurant.35") + myLocale); //$NON-NLS-1$
			// Global_variable.wjbty_en_Url = "http://www.wjbty.com/en/api/";
			System.out.println(Messages.getString("FindRestaurant.36") //$NON-NLS-1$
					+ Global_variable.rf_en_Url);

		} else if (myLocale.toString().equalsIgnoreCase(Messages.getString("FindRestaurant.37"))) { //$NON-NLS-1$
			System.out.println(Messages.getString("FindRestaurant.38") + myLocale); //$NON-NLS-1$
			System.out.println(Messages.getString("FindRestaurant.39")); //$NON-NLS-1$

		}
		saveLocale(lang);
		DBAdapter.deleteall();
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

	}

	@Override
	public void onConfigurationChanged(
			android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		System.out.println(Messages.getString("FindRestaurant.40")); //$NON-NLS-1$
		if (myLocale != null) {

			newConfig.locale = myLocale;
			System.out.println(Messages.getString("FindRestaurant.41") + myLocale); //$NON-NLS-1$
			Locale.setDefault(myLocale);
			getBaseContext().getResources().updateConfiguration(newConfig,
					getBaseContext().getResources().getDisplayMetrics());
			setContentView(R.layout.second_screen);
			setTitle(R.string.app_name);

			System.out.println(Messages.getString("FindRestaurant.42") + newConfig.locale); //$NON-NLS-1$
			// Checks the active language
			if (newConfig.locale.equals(Messages.getString("FindRestaurant.43"))) { //$NON-NLS-1$
				Toast.makeText(this, getString(R.string.str_English),
						Toast.LENGTH_SHORT).show();
			} else if (newConfig.locale.equals(Messages.getString("FindRestaurant.44"))) { //$NON-NLS-1$
				Toast.makeText(this, getString(R.string.str_French),
						Toast.LENGTH_SHORT).show();
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

		// Intent i = new Intent(Categories.this,
		// Search_Restaurant_List.class);
		// i.putExtra("City_Name", Global_variable.FR_City_Name);
		// i.putExtra("City_id", Global_variable.FR_City_id);
		// i.putExtra("City_Position", Global_variable.FR_City_Position);
		// i.putExtra("Delivery_id", Global_variable.FR_Delivery_id);
		// i.putExtra("Delivery_Position",
		// Global_variable.FR_Delivery_Position);
		// i.putExtra("sessid", Global_variable.sessid);
		// startActivity(i);

		if (cd.isConnectingToInternet()) {

			ExitFromAppDialog();
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

	// Onback press Exit app
	public void ExitFromAppDialog() {

		ExitAppDialog = new Dialog(FindRestaurant.this);
		ExitAppDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		ExitAppDialog.setContentView(R.layout.popup_exitapp);
		ExitAppDialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.logo_icon);
		ExitAppDialog.setTitle(getResources().getString(
				R.string.Popup_Exit_title));
		img_yes = (ImageView) ExitAppDialog.findViewById(R.id.img_yes);
		img_no = (ImageView) ExitAppDialog.findViewById(R.id.img_no);
		TextView txv_Dialogtext = (TextView) ExitAppDialog
				.findViewById(R.id.txv_dialog);
		txv_Dialogtext.setText(R.string.Popup_Exit);
		try {
			img_yes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// Intent intent = new Intent(Intent.ACTION_MAIN);
					// intent.addCategory(Intent.CATEGORY_HOME);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(intent);
					// android.os.Process.killProcess(android.os.Process.myPid());

					Intent homeIntent = new Intent(Intent.ACTION_MAIN);
					homeIntent.addCategory(Intent.CATEGORY_HOME);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(homeIntent);
					finish();
					System.exit(0);
					// System.runFinalizersOnExit(true);
					// System.exit(0);

				}
			});
			img_no.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ExitAppDialog.dismiss();
				}
			});

			// customHandler.post(updateTimerThread, 0);
			ExitAppDialog.show();
			ExitAppDialog.setCancelable(false);
			ExitAppDialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
	}

	public void initializeWidget() {
		
		Find_restaurant = (TextView) findViewById(R.id.find_restaurant);
		Select_city_textview = (TextView) findViewById(R.id.Select_city_textview);
		Select_city_textview = (TextView) findViewById(R.id.Select_city_textview);
		Select_region_textview = (TextView) findViewById(R.id.Select_region_textview);
		orderImageView = (ImageView) findViewById(R.id.footer_ordernow_img);
		footer_viewmenu_img = (ImageView) findViewById(R.id.search_footer_viewmenu_img);
		footer_featured_img = (ImageView) findViewById(R.id.footer_featured_img);
		footer_setting_img = (ImageView) findViewById(R.id.footer_setting_img);
	}

	private void setlistener() {

		try {
			System.out.println(Messages.getString("FindRestaurant.45") //$NON-NLS-1$
					+ SharedPreference.getuser_id(getApplicationContext()));

			footer_setting_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						if (SharedPreference
								.getuser_id(getApplicationContext()) != Messages.getString("FindRestaurant.46")) { //$NON-NLS-1$
							if (SharedPreference.getuser_id(
									getApplicationContext()).length() != 0) {
								Intent in = new Intent(getApplicationContext(),
										SettingsScreen.class);
								startActivity(in);
							} else {
								Toast.makeText(getApplicationContext(),
										R.string.please_login,
										Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									R.string.please_login, Toast.LENGTH_SHORT)
									.show();
						}

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
						if (SharedPreference
								.getuser_id(getApplicationContext()) != Messages.getString("FindRestaurant.47")) { //$NON-NLS-1$
							if (SharedPreference.getuser_id(
									getApplicationContext()).length() != 0) {

								Intent in = new Intent(getApplicationContext(),
										MyFavourites.class);
								startActivity(in);
							}
						} else {
							Toast.makeText(getApplicationContext(),
									R.string.please_login, Toast.LENGTH_SHORT)
									.show();
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			Find_restaurant.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						runOnUiThread(new Runnable() {
							public void run() {

								/** check Internet Connectivity */
								if (cd.isConnectingToInternet()) {

									if (!Select_region_textview.getText().toString().equalsIgnoreCase(getString(R.string.Select_Region))) {

										if (!Select_city_textview.getText().toString().equalsIgnoreCase(getString(R.string.Register_Select_City))) {
										        gps = new com.rf_user.connection.GPSTracker(FindRestaurant.this);
										        if (gps.canGetLocation()) {
												Global_variable.latitude = gps.getLatitude();
												Global_variable.longitude = gps.getLongitude();
										        }
										        intent = new Intent(FindRestaurant.this,Search_Restaurant_List.class);
											if (city_id == null) {
												city_id = (String) Global_variable.hashmap_cities.get(Select_city_textview.getText().toString());
												// delivery_id = (String)
												// Global_variable.hashmap_deliveryareas.get(Delivery_areas_textview.getText().toString());
											} else {
												Global_variable.FR_City_Name = Select_city_textview.getText().toString();
												Global_variable.FR_City_id = city_id;
												Global_variable.FR_City_Position = selectedcity;
												Global_variable.FR_Region_Name = Select_region_textview.getText().toString();
												Global_variable.FR_Region_id = Region_id;
												Global_variable.FR_Region_Position = selectedregion;
												System.out.println(Messages.getString("FindRestaurant.48") //$NON-NLS-1$
														+ Global_variable.Region_Array.length);

												for (String str : Global_variable.Region_Array) {

													System.out.println(Messages
															.getString("FindRestaurant.49") //$NON-NLS-1$
															+ str);

												}

												Global_variable.init_cityarraylist = new ArrayList<String>();

												for (int i = 0; i < Global_variable.init_hash_cityarraylist
														.size(); i++) {
													String str_region_id = Global_variable.init_hash_cityarraylist
															.get(i)
															.get(Messages
																	.getString("FindRestaurant.50")); //$NON-NLS-1$
													System.out.println(Messages
															.getString("FindRestaurant.51") //$NON-NLS-1$
															+ Global_variable.FR_Region_id
															+ Messages
																	.getString("FindRestaurant.52") //$NON-NLS-1$
															+ Global_variable.init_hash_cityarraylist
																	.get(i)
																	.get(Messages
																			.getString("FindRestaurant.53"))); //$NON-NLS-1$
													if (Global_variable.FR_Region_id
															.equalsIgnoreCase(str_region_id)) {
														System.out.println(Messages
																.getString("FindRestaurant.54") //$NON-NLS-1$
																+ Global_variable.FR_Region_id
																+ Messages
																		.getString("FindRestaurant.55") //$NON-NLS-1$
																+ Global_variable.init_hash_cityarraylist
																		.get(i)
																		.get(Messages
																				.getString("FindRestaurant.56"))); //$NON-NLS-1$
														String str_hashmap = Global_variable.init_hash_cityarraylist
																.get(i)
																.get(Messages
																		.getString("FindRestaurant.57")); //$NON-NLS-1$
														System.out.println(Messages
																.getString("FindRestaurant.58") //$NON-NLS-1$
																+ str_hashmap);
														Global_variable.init_cityarraylist
																.add(str_hashmap);
														System.out.println(Messages
																.getString("FindRestaurant.59") //$NON-NLS-1$
																+ Global_variable.init_cityarraylist);

													}

												}

											}
											 if (Global_variable.latitude == 0.0 && Global_variable.longitude == 0.0) 
											{
											    
											    gps.showSettingsAlert();
											}
											else
											{
											    startActivity(intent);
											}
										} else {
											Toast.makeText(
													getApplicationContext(),
													R.string.Select_City,
													Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(getApplicationContext(),
												R.string.Select_Region,
												Toast.LENGTH_SHORT).show();
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

			Select_region_textview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						runOnUiThread(new Runnable() {
							public void run() {
								if (Select_region_textview.getText().toString()
										.equals(Messages.getString("FindRestaurant.60"))) { //$NON-NLS-1$

								} else {
									if (Global_variable.Region_Array != null) {
										if (Global_variable.Region_Array.length != 0) {
											Region_spinner();
										}
									}

								}

							}
						});
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			Select_city_textview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						runOnUiThread(new Runnable() {
							public void run() {
								if (Select_city_textview.getText().toString()
										.equals(Messages.getString("FindRestaurant.61"))) { //$NON-NLS-1$

								} else {

									System.out
											.println(Messages.getString("FindRestaurant.62") //$NON-NLS-1$
													+ Select_region_textview
															.getText()
															.toString());

									if (!Select_region_textview
											.getText()
											.toString()
											.equalsIgnoreCase(
													getString(R.string.Select_Region)))

									{

										System.out
												.println(Messages.getString("FindRestaurant.63") //$NON-NLS-1$
														+ Global_variable.City_Array.length);

										if (Global_variable.City_Array != null) {
											if (Global_variable.City_Array.length != 0) {
												city_spinner();
											}
										}

									} else {

										Toast.makeText(getApplicationContext(),
												R.string.Select_Region,
												Toast.LENGTH_SHORT).show();

									}
								}

							}
						});
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			orderImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {

						System.out.println(Messages.getString("FindRestaurant.64") //$NON-NLS-1$
								+ SharedPreference
										.getuser_id(getApplicationContext()));

						if (SharedPreference
								.getuser_id(getApplicationContext()).equals(Messages.getString("FindRestaurant.65"))) { //$NON-NLS-1$
							Global_variable.activity = Messages.getString("FindRestaurant.66"); //$NON-NLS-1$
							Intent i = new Intent(FindRestaurant.this,
									Login.class);
							startActivity(i);

						} else if (!SharedPreference.getuser_id(
								getApplicationContext()).equals(Messages.getString("FindRestaurant.67"))) { //$NON-NLS-1$
							if (Global_variable.cart.length() == 0) {
								Toast.makeText(FindRestaurant.this,
										R.string.empty_cart, Toast.LENGTH_SHORT)
										.show();
							} else {
								Intent i = new Intent(FindRestaurant.this,
										Cart.class);
								startActivity(i);
							}

						} else {

						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			img_ar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (LanguageConvertPreferenceClass
							.getLocale(getApplicationContext()).toString()
							.equalsIgnoreCase(Messages.getString("FindRestaurant.68"))) { //$NON-NLS-1$

					} else {
						Global_variable.rf_lang_Url = Global_variable.rf_ro_Url;
						System.out.println(Messages.getString("FindRestaurant.69") //$NON-NLS-1$
								+ Global_variable.rf_lang_Url);
						LanguageConvertPreferenceClass.saveLocale(Messages.getString("FindRestaurant.70"), //$NON-NLS-1$
								getApplicationContext());
						LanguageConvertPreferenceClass.setLocale(Messages.getString("FindRestaurant.71"), //$NON-NLS-1$
								getApplicationContext());

						Intent refresh = new Intent(FindRestaurant.this,
								FindRestaurant.class);
						// refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(refresh);

					}

				}
			});
			img_en.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (LanguageConvertPreferenceClass
							.getLocale(getApplicationContext()).toString()
							.equalsIgnoreCase(Messages.getString("FindRestaurant.72"))) { //$NON-NLS-1$

					} else {
						Global_variable.rf_lang_Url = Global_variable.rf_en_Url;
						System.out.println(Messages.getString("FindRestaurant.73") //$NON-NLS-1$
								+ Global_variable.rf_lang_Url);
						LanguageConvertPreferenceClass.saveLocale(Messages.getString("FindRestaurant.74"), //$NON-NLS-1$
								getApplicationContext());
						LanguageConvertPreferenceClass.setLocale(Messages.getString("FindRestaurant.75"), //$NON-NLS-1$
								getApplicationContext());

						Intent refresh = new Intent(FindRestaurant.this,
								FindRestaurant.class);
						// refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(refresh);

					}
				}
			});

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	// region spinner
	private void Region_spinner() {
		try {

			System.out.println(Messages.getString("FindRestaurant.76") //$NON-NLS-1$
					+ Global_variable.Region_Array);

			if (!Select_region_textview.getText().toString()
					.equalsIgnoreCase(getString(R.string.Select_Region))) {
				String Region_name = Select_region_textview.getText()
						.toString();

				int index = -1;
				for (int i = 0; i < Global_variable.Region_Array.length; i++) {
					if (Global_variable.Region_Array[i].equals(Region_name)) {
						index = i;
						selectedregion = index;
						break;
					}
				}

				System.out.println(Messages.getString("FindRestaurant.77") + index); //$NON-NLS-1$
			}

			Builder builder = new AlertDialog.Builder(FindRestaurant.this);
			builder.setTitle(R.string.Select_Region);
			builder.setSingleChoiceItems(Global_variable.Region_Array,
					selectedregion, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							// Select_city_textview.setClickable(true);

							Hashmap_City.clear();
							// District_Array=null;
							selectedregion = which;
							str_select_region = Global_variable.Region_Array[which];
							System.out.println(Messages.getString("FindRestaurant.78") //$NON-NLS-1$
									+ str_select_region);
							Region_id = (String) Global_variable.hashmap_region
									.get(str_select_region);
							System.out.println(Messages.getString("FindRestaurant.79") + Region_id); //$NON-NLS-1$

							try {
								if (Select_region_textview.getText().toString() != str_select_region) {
									Select_region_textview
											.setText(str_select_region);
									dialogShown = false;
									dialog.dismiss();
									System.out.println(Messages.getString("FindRestaurant.80") //$NON-NLS-1$
											+ str_select_region);
									City_Array = new String[0];
									// District_Array = new String[0];
									/** check Internet Connectivity */
									if (cd.isConnectingToInternet()) {

										new async_city_Spinner().execute();

									} else {

										runOnUiThread(new Runnable() {

											@Override
											public void run() {

												// TODO Auto-generated method
												// stub
												Toast.makeText(
														getApplicationContext(),
														R.string.No_Internet_Connection,
														Toast.LENGTH_SHORT)
														.show();

											}

										});
									}

								} else {
									dialogShown = false;
									dialog.dismiss();
								}

							} catch (Exception e) {

							}

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

	private void city_spinner() {
		try {
			str_select_city = null;
			city_id = null;

			if (!Select_city_textview.getText().toString()
					.equalsIgnoreCase(getString(R.string.Register_Select_City))) {
				String city_name = Select_city_textview.getText().toString();

				int index = -1;
				for (int i = 0; i < Global_variable.City_Array.length; i++) {
					if (Global_variable.City_Array[i].equals(city_name)) {
						index = i;
						selectedcity = index;
						break;
					}
				}

				System.out.println(Messages.getString("FindRestaurant.81") + index); //$NON-NLS-1$
			}

			Builder builder = new AlertDialog.Builder(FindRestaurant.this);
			builder.setTitle(R.string.Select_City);
			builder.setSingleChoiceItems(Global_variable.City_Array,
					selectedcity, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// District_Array=null;
							selectedcity = which;
							str_select_city = Global_variable.City_Array[which];
							System.out.println(Messages.getString("FindRestaurant.82") //$NON-NLS-1$
									+ str_select_city);
							city_id = (String) Global_variable.hashmap_cities
									.get(str_select_city);
							System.out.println(Messages.getString("FindRestaurant.83") + city_id); //$NON-NLS-1$

							try {

								if (Select_city_textview.getText().toString() != str_select_city) {
									Select_city_textview
											.setText(str_select_city);
									System.out.println(Messages.getString("FindRestaurant.84") //$NON-NLS-1$
											+ str_select_city);
									dialogShown = false;
									dialog.dismiss();

								} else {
									dialogShown = false;
									dialog.dismiss();

								}

							} catch (Exception e) {

							}

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

	public class async_init extends AsyncTask<Void, Void, Void> {
		JSONObject init_sessid;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			CheckNetworkandSQlitecall = true;
			// Showing progress dialog
			progressDialog = new ProgressDialog(FindRestaurant.this);
			progressDialog.setCancelable(false);
			progressDialog.show();

			System.out.println(Messages.getString("FindRestaurant.85") //$NON-NLS-1$
					+ SharedPreference.getsessid(getApplicationContext()));
			if (SharedPreference.getsessid(getApplicationContext()) != null) {
				if (SharedPreference.getsessid(getApplicationContext())
						.length() != 0) {
					init_sessid = new JSONObject();
					try {
						init_sessid.put(Messages.getString("FindRestaurant.86"), SharedPreference //$NON-NLS-1$
								.getsessid(getApplicationContext()));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			System.out.println(Messages.getString("FindRestaurant.87") + init_sessid); //$NON-NLS-1$

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(Global_variable.rf_lang_Url
					+ Global_variable.rf_init_api_path);
			HttpResponse response;
			try {
				if (init_sessid != null) {
					if (init_sessid.length() != 0) {
						StringEntity se = new StringEntity(
								init_sessid.toString(), Messages.getString("FindRestaurant.88")); //$NON-NLS-1$
						System.out.println(Messages.getString("FindRestaurant.89") + se); //$NON-NLS-1$
						se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
								Messages.getString("FindRestaurant.90"))); //$NON-NLS-1$
						se.setContentType(Messages.getString("FindRestaurant.91")); //$NON-NLS-1$
						se.setContentEncoding(new BasicHeader(
								HTTP.CONTENT_TYPE,
								Messages.getString("FindRestaurant.92"))); //$NON-NLS-1$
						httpPost.setEntity(se);

						response = httpClient.execute(httpPost);

					} else {
						response = httpClient.execute(httpPost);
					}
				} else {
					response = httpClient.execute(httpPost);
				}

				Log.d(Messages.getString("FindRestaurant.93"), response.toString()); //$NON-NLS-1$
				System.out.println(Messages.getString("FindRestaurant.94") + response.toString()); //$NON-NLS-1$
				responseContent = EntityUtils.toString(response.getEntity(),
						Messages.getString("FindRestaurant.95")); //$NON-NLS-1$
				responseContent=responseContent.substring(responseContent.indexOf("{"), responseContent.lastIndexOf("}") + 1);

				try {

					// Json_Main=new JSONObject(responseContent);

					Json_Main = (JSONObject) new JSONTokener(responseContent)
							.nextValue();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				System.out.println(Messages.getString("FindRestaurant.96") + Json_Main); //$NON-NLS-1$

				String Str_responseContent = responseContent;
				Log.d(Messages.getString("FindRestaurant.97"), responseContent); //$NON-NLS-1$
				Log.d(Messages.getString("FindRestaurant.98"), Messages.getString("FindRestaurant.99")); //$NON-NLS-1$ //$NON-NLS-2$
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						Messages.getString("FindRestaurant.100")); //$NON-NLS-1$
				String currentTimeStamp = dateFormat.format(new Date()); // Find
																			// todays
																			// date

				System.out.println(Messages.getString("FindRestaurant.101") + currentTimeStamp); //$NON-NLS-1$

				System.out.println(Messages.getString("FindRestaurant.102") + currentTimeStamp); //$NON-NLS-1$

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Global_variable.init_cities = new JSONArray();
			Global_variable.init_cityarraylist = new ArrayList<String>();
			Global_variable.init_hash_cityarraylist = new ArrayList<HashMap<String, String>>();

			try {

				// if (CheckNetworkandSQlitecall) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}

				if (responseContent != null) {
					System.out.println(Messages.getString("FindRestaurant.103") //$NON-NLS-1$
							+ responseContent);

					/** Fetch Data from Json_Main which is response of init **/

					/** Fetch Data from which is response of init **/
					success = Json_Main.getString(Messages.getString("FindRestaurant.104")); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.105") + success); //$NON-NLS-1$
					message = Json_Main.getString(Messages.getString("FindRestaurant.106")); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.107") + message); //$NON-NLS-1$
					data = Json_Main.getJSONObject(Messages.getString("FindRestaurant.108")); //$NON-NLS-1$
					// System.out.println("wjbty_data"+data);

					String sessid = Json_Main.getString(Messages.getString("FindRestaurant.109")); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.110") + sessid); //$NON-NLS-1$
					SharedPreference.setsessid(getApplicationContext(), sessid);

					texts = data.getJSONObject(Messages.getString("FindRestaurant.111")); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.112") + texts); //$NON-NLS-1$

					System.out.println(Messages.getString("FindRestaurant.113") + cities); //$NON-NLS-1$

					restaurantcategory = data
							.getJSONObject(Messages.getString("FindRestaurant.114")); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.115") //$NON-NLS-1$
							+ restaurantcategory);

					JSONArray region_array = new JSONArray();
					region_array = data.getJSONArray(Messages.getString("FindRestaurant.116")); //$NON-NLS-1$

					int length = region_array.length();
					System.out.println(Messages.getString("FindRestaurant.117") + length); //$NON-NLS-1$
					Global_variable.Region_Array = new String[length];
					Global_variable.hashmap_region = new HashMap<String, String>();

					for (int i = 0; i <= length; i++) {
						try {
							JSONObject array_Object = region_array
									.getJSONObject(i);
							String Region_Name = array_Object.getString(Messages.getString("FindRestaurant.118")); //$NON-NLS-1$
							System.out.println(Messages.getString("FindRestaurant.119") + Region_Name); //$NON-NLS-1$
							String Region_Id = array_Object.getString(Messages.getString("FindRestaurant.120")); //$NON-NLS-1$
							System.out.println(Messages.getString("FindRestaurant.121") + Region_Name); //$NON-NLS-1$
							Global_variable.Region_Array[i] = Region_Name
									.toString();

							Global_variable.hashmap_region.put(Region_Name,
									Region_Id);

							System.out.println(Messages.getString("FindRestaurant.122") + i + Messages.getString("FindRestaurant.123") //$NON-NLS-1$ //$NON-NLS-2$
									+ Global_variable.Region_Array[i]);
						} catch (Exception ex) {
							System.out.println(Messages.getString("FindRestaurant.124") + ex); //$NON-NLS-1$
						}
					}

					// Global_variable.user_id = data.getString("user_id");
					// System.out.println("wjbty_user_id" +
					// Global_variable.user_id);

					/** Read data from texts and store it **/
					Global_variable.hashmap_texts = new HashMap<String, String>();
					Global_variable.hashmap_deliveryareas = new HashMap<String, String>();
					itr_keys_texts = texts.keys();
					while (itr_keys_texts.hasNext()) {
						str_key_texts = (String) itr_keys_texts.next();
						str_value_texts = texts.getString(str_key_texts);
						if (texts.get(str_key_texts) != null) {
							System.out.println(Messages.getString("FindRestaurant.125") + str_key_texts //$NON-NLS-1$
									+ Messages.getString("FindRestaurant.126") + str_value_texts); //$NON-NLS-1$
							Global_variable.hashmap_texts.put(str_key_texts,
									str_value_texts);
						}

					}
					System.out.println(Messages.getString("FindRestaurant.127") //$NON-NLS-1$
							+ Global_variable.hashmap_texts);

					/**
					 * Read delivery areas from hashmap_texts for
					 * hashmap_deliveryareas
					 */

					//

					Global_variable.hashmap_deliveryareas.put(
							Global_variable.hashmap_texts.get(Messages.getString("FindRestaurant.128")), //$NON-NLS-1$
							Messages.getString("FindRestaurant.129")); //$NON-NLS-1$
					Global_variable.hashmap_deliveryareas.put(
							Global_variable.hashmap_texts.get(Messages.getString("FindRestaurant.130")), //$NON-NLS-1$
							Messages.getString("FindRestaurant.131")); //$NON-NLS-1$

					Global_variable.hashmap_deliveryareas.put(
							Global_variable.hashmap_texts.get(Messages.getString("FindRestaurant.132")), Messages.getString("FindRestaurant.133")); //$NON-NLS-1$ //$NON-NLS-2$

					Global_variable.hashmap_deliveryareas.put(
							Global_variable.hashmap_texts.get(Messages.getString("FindRestaurant.134")), //$NON-NLS-1$
							Messages.getString("FindRestaurant.135")); //$NON-NLS-1$

					/** Convert Hashmap to array */
					Global_variable.Delivery_area_Array = new String[Global_variable.hashmap_deliveryareas
							.size()];
					String[] values = new String[Global_variable.hashmap_deliveryareas
							.size()];
					int index = 0;
					for (Entry<String, String> mapEntry : Global_variable.hashmap_deliveryareas
							.entrySet()) {
						Global_variable.Delivery_area_Array[index] = mapEntry
								.getKey();
						values[index] = mapEntry.getValue();
						index++;
					}

					for (int j = 0; j < Global_variable.Delivery_area_Array.length; j++) {
						System.out.println(Messages.getString("FindRestaurant.136") //$NON-NLS-1$
								+ Global_variable.Delivery_area_Array[j]);

						System.out
								.println(Messages.getString("FindRestaurant.137") //$NON-NLS-1$
										+ Global_variable.hashmap_deliveryareas);

					}
					//

					/** Read data from cities and store it **/

					/** Read data from restaurantcategory and store it **/
					Global_variable.hashmap_restaurantcategory = new HashMap<String, String>();
					// Global_variable.hashmap_restaurantcategory.put(Global_variable.Default_Restaurant_Category_String,
					// "-1");
					itr_keys_restaurantcategory = restaurantcategory.keys();
					while (itr_keys_restaurantcategory.hasNext()) {
						str_key_restaurantcategory = (String) itr_keys_restaurantcategory
								.next();
						str_value_restaurantcategory = restaurantcategory
								.getString(str_key_restaurantcategory);
						if (restaurantcategory.get(str_key_restaurantcategory) != null) {
							System.out.println(Messages.getString("FindRestaurant.138") //$NON-NLS-1$
									+ str_key_restaurantcategory + Messages.getString("FindRestaurant.139") //$NON-NLS-1$
									+ str_value_restaurantcategory);
							Global_variable.hashmap_restaurantcategory.put(
									str_value_restaurantcategory,
									str_key_restaurantcategory);
						}

					}
					System.out.println(Messages.getString("FindRestaurant.140") //$NON-NLS-1$
							+ Global_variable.hashmap_restaurantcategory);
					System.out.println(Messages.getString("FindRestaurant.141") //$NON-NLS-1$
							+ Global_variable.Region_Array);
					System.out.println(Messages.getString("FindRestaurant.142") //$NON-NLS-1$
							+ Global_variable.Region_Array.length);

					/* Fetching loyalty pts. from response */
					try {
						JSONObject loyalty_pts_details = data
								.getJSONObject(Messages.getString("FindRestaurant.143")); //$NON-NLS-1$
						Global_variable.min_lp_to_redeem = Integer
								.parseInt(loyalty_pts_details
										.getString(Messages.getString("FindRestaurant.144"))); //$NON-NLS-1$
						Global_variable.max_lp_to_redeem = Integer
								.parseInt(loyalty_pts_details
										.getString(Messages.getString("FindRestaurant.145"))); //$NON-NLS-1$
						Global_variable.lp_equals_to_lei = Integer
								.parseInt(loyalty_pts_details
										.getString(Messages.getString("FindRestaurant.146"))); //$NON-NLS-1$
						Global_variable.lp_to_tg_customer = Integer
								.parseInt(loyalty_pts_details
										.getString(Messages.getString("FindRestaurant.147"))); //$NON-NLS-1$

					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					if (Global_variable.Region_Array != null) {

					} else {

						Global_variable.FR_region_flag = true;
						Global_variable.sessid = sessid;

						new async_init().execute();

					}

				} else {
					new async_init().execute();
				}

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public class async_city_Spinner extends AsyncTask<Void, Void, Void> {
		JSONObject city_jsonobj;
		String responseText;

		ProgressDialog city_spinner;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			city_spinner = new ProgressDialog(FindRestaurant.this);
			city_spinner.setIndeterminate(false);
			city_spinner.setCancelable(false);
			city_spinner.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		protected Void doInBackground(Void... params) {
			try {

				try {
					city_jsonobj = new JSONObject();
					city_jsonobj.put(Messages.getString("FindRestaurant.148"), Messages.getString("FindRestaurant.149")); //$NON-NLS-1$ //$NON-NLS-2$
					System.out.println(Messages.getString("FindRestaurant.150") + city_jsonobj); //$NON-NLS-1$
					if (Region_id != null) {
						city_jsonobj.put(Messages.getString("FindRestaurant.151"), Region_id); //$NON-NLS-1$
					} else {
						city_jsonobj.put(Messages.getString("FindRestaurant.152"), Messages.getString("FindRestaurant.153")); //$NON-NLS-1$ //$NON-NLS-2$
					}

					System.out.println(Messages.getString("FindRestaurant.154") + city_jsonobj); //$NON-NLS-1$
					city_jsonobj
							.put(Messages.getString("FindRestaurant.155"), SharedPreference //$NON-NLS-1$
									.getsessid(getApplicationContext()));
					System.out.println(Messages.getString("FindRestaurant.156") + city_jsonobj); //$NON-NLS-1$
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				responseText = http.connection(FindRestaurant.this,
						Global_variable.rf_Geosearch_api_path, city_jsonobj);
				responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);

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

				if (responseText != null) {

					System.out.println(Messages.getString("FindRestaurant.157") + responseText); //$NON-NLS-1$
					JSONObject json = new JSONObject(responseText);
					System.out.println(Messages.getString("FindRestaurant.158") + json); //$NON-NLS-1$

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println(Messages.getString("FindRestaurant.159") + success1); //$NON-NLS-1$
					JSONArray data = json.getJSONArray(Messages.getString("FindRestaurant.160")); //$NON-NLS-1$
					System.out.println(Messages.getString("FindRestaurant.161") + data); //$NON-NLS-1$

					int length = data.length();
					System.out.println(Messages.getString("FindRestaurant.162") + length); //$NON-NLS-1$
					if (length == 0) {
						Select_city_textview.setText(R.string.No_City);
						City_Array = new String[length];
					} else {
						Select_city_textview
								.setText(R.string.Register_Select_City);
						// select_district.setText(R.string.Select_District);
						City_Array = new String[length];
						Global_variable.City_Array = new String[length];
						Hashmap_City = new HashMap<String, String>();
						Global_variable.hashmap_cities = new HashMap<String, String>();
						for (int i = 0; i < length; i++) {

							try {
								JSONObject array_Object = data.getJSONObject(i);
								String city_name = array_Object
										.getString(Messages.getString("FindRestaurant.163")); //$NON-NLS-1$
								System.out.println(Messages.getString("FindRestaurant.164") + city_name); //$NON-NLS-1$
								String city_id = array_Object.getString(Messages.getString("FindRestaurant.165")); //$NON-NLS-1$
								System.out.println(Messages.getString("FindRestaurant.166") + city_id); //$NON-NLS-1$
								Hashmap_City.put(city_name, city_id);
								City_Array[i] = city_name.toString();
								System.out.println(Messages.getString("FindRestaurant.167") + i + Messages.getString("FindRestaurant.168") //$NON-NLS-1$ //$NON-NLS-2$
										+ City_Array[i]);
								Global_variable.City_Array[i] = city_name
										.toString();
								Global_variable.hashmap_cities.put(city_name,
										city_id);

							} catch (Exception ex) {
								System.out.println(Messages.getString("FindRestaurant.169") + ex); //$NON-NLS-1$
							}
						}

						Select_city_textview.setClickable(true);
					}

				} else {
					// new async_city_Spinner().execute();
				}

				// region_flag=true;

			}

			catch (Exception e) {
				e.printStackTrace();
			}
			if (city_spinner.isShowing()) {
				city_spinner.dismiss();
			}

		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		System.out.println(Messages.getString("FindRestaurant.170")); //$NON-NLS-1$
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		System.out.println(Messages.getString("FindRestaurant.171")); //$NON-NLS-1$
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		System.out.println(Messages.getString("FindRestaurant.172")); //$NON-NLS-1$
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		System.out.println(Messages.getString("FindRestaurant.173")); //$NON-NLS-1$
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		System.out.println(Messages.getString("FindRestaurant.174")); //$NON-NLS-1$
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		System.out.println(Messages.getString("FindRestaurant.175")); //$NON-NLS-1$
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

}
