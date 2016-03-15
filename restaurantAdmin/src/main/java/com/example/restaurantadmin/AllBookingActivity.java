package com.example.restaurantadmin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.AllBookingAdapter;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AllBookingActivity extends Activity {
	public static TextView txv_date;
	public static AutoCompleteTextView ed_search;
	ConnectionDetector cd;
	public static TextView txv_all_default, txv_all_select, txv_online_default,
			txv_online_select, txv_grabbing_default, txv_grabbing_select;
	Calendar c;
	public static int mYear, mMonth, mDay, mHour, mMinute, mHour_end,
			mMinute_end, mHour_duration, mMinute_duration;
	public static AllBookingAdapter AllBookingAdapter;
	public static ListView lv_all_booking;
	public static TextView txv_nodata;
	public static ProgressDialog p;
	// public static Spinner sp_meals;
	public static String str_search, str_date, str_booking_type = "0";
	public static JSONArray array_filter = new JSONArray();
	public static DatePickerDialog dpd;
	public static ImageView img_home;
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_all_booking);
		cd = new ConnectionDetector(getApplicationContext());
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		initialization();

		if (cd.isConnectingToInternet()) {

			new async_getrest_order().execute();
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
		secOnclicklistner();

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

//		loadLocale();
		

		// language*****************

		ed_search.setThreshold(0);

		ed_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence str, int start, int before,
					int count) {

				try {
					str_search = str.toString();
					array_filter = AllBookingActivityFilterClass
							.filter_all_booking(str_date, str_search,
									str_booking_type,
									Global_variable.array_AllBooking);

					System.out.println("6666allsarray" + array_filter);
					if (array_filter != null) {
						AllBookingAdapter = new AllBookingAdapter(
								AllBookingActivity.this, array_filter);
						lv_all_booking.setAdapter(AllBookingAdapter);
						lv_all_booking.setVisibility(View.VISIBLE);
						txv_nodata.setVisibility(View.GONE);
					} else {
						lv_all_booking.setVisibility(View.GONE);
						txv_nodata.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

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

	@Override
	public void onResume() {
		System.out.println("murtuza_nalawala");
		super.onResume(); // Always call the superclass method first
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}
	private void initialization() {
		// TODO Auto-generated method stub
		img_home = (ImageView) findViewById(R.id.img_home);
		txv_date = (TextView) findViewById(R.id.txv_date);
		txv_all_default = (TextView) findViewById(R.id.txv_all_default);
		txv_all_select = (TextView) findViewById(R.id.txv_all_select);
		txv_online_default = (TextView) findViewById(R.id.txv_online_default);
		txv_online_select = (TextView) findViewById(R.id.txv_online_select);
		txv_grabbing_default = (TextView) findViewById(R.id.txv_grabbing_default);
		txv_grabbing_select = (TextView) findViewById(R.id.txv_grabbing_select);
		ed_search = (AutoCompleteTextView) findViewById(R.id.ed_search);
		lv_all_booking = (ListView) findViewById(R.id.lv_all_booking);
		txv_nodata = (TextView) findViewById(R.id.txv_nodata);
		String finalDate = (mYear + "-" + (mMonth + 1) + "-" + mDay);
		System.out.println("firstfinaldate" + finalDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date date;
			try {
				date = formatter.parse(finalDate);
				System.out.println("6666DATEFINAL" + date);
				System.out.println(formatter.format(date));
				str_date = formatter.format(date);
				// STR_Date=date.toString();
				txv_date.setText(str_date);
				System.out.println("6666datefirst" + str_date);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {

			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		// sp_meals = (Spinner) findViewById(R.id.sp_meals);
	}
	public class async_getrest_order extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_restaurant_order;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(AllBookingActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			Global_variable.array_AllBooking = new JSONArray();
			obj_restaurant_order = new JSONObject();
			JSONObject obj_order = new JSONObject();
			try {
				System.out.println("1111restidinOO"
						+ Global_variable.restaurant_id);
				// obj_restaurant_app********
				// obj_order.put("restaurant_id", "95");
				obj_order.put("restaurant_id", Global_variable.restaurant_id);
				obj_restaurant_order.put("Restaurant_Order_Detail", obj_order);
				obj_restaurant_order.put("sessid", Global_variable.sessid);
				System.out
						.println("1111allbookingorder" + obj_restaurant_order);

				// obj_MainRequest*******************************
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
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_AllBooking_api);
				try {
					// myconnection con = new myconnection();

				
					//newly function added***********
					myconnection con = new myconnection();
					json = new JSONObject(
							con.connection(AllBookingActivity.this,
									Global_variable.rf_AllBooking_api,
									obj_restaurant_order));
					
					// *****************************
					System.out.println("1111finaljsonstepTG" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

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
				Global_variable.sessid = json.getString("sessid");
				System.out.println("1111sessidstep2respo"
						+ Global_variable.sessid);
				if (jsonSuccessStr.equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					System.out.println("1111obj_Data" + Data);
					if (Data != null) {
						JSONObject rets_order_details = Data
								.getJSONObject("restaurant_order_detail");
						System.out.println("restaurant_order_detail"
								+ rets_order_details);
						// ***************OO
						JSONArray array_OO = rets_order_details
								.getJSONArray("online_order");
						System.out.println("1111array_OO" + array_OO);
						if (array_OO != null) {
							JSONObject obj_OO;
							for (int i = 0; i < array_OO.length(); i++) {
								obj_OO = array_OO.getJSONObject(i);
								System.out.println("objectoo" + obj_OO);
								Global_variable.array_AllBooking.put(obj_OO);
							}
						}
						// *******TG
						JSONArray array_TG = rets_order_details
								.getJSONArray("table_grabber");
						System.out.println("1111array_TG" + array_TG);
						if (array_TG != null) {
							JSONObject obj_TG;
							for (int i = 0; i < array_TG.length(); i++) {
								obj_TG = array_TG.getJSONObject(i);
								System.out.println("obj_TG" + obj_TG);
								Global_variable.array_AllBooking.put(obj_TG);
								System.out.println("lastarray_AllBooking"
										+ Global_variable.array_AllBooking);
							}
						}
						if (Global_variable.array_AllBooking != null) {
							System.out.println("notnullarrayallbooking"
									+ Global_variable.array_AllBooking);
							AllBookingAdapter = new AllBookingAdapter(
									AllBookingActivity.this,
									Global_variable.array_AllBooking);
							lv_all_booking.setAdapter(AllBookingAdapter);
							lv_all_booking.setVisibility(View.VISIBLE);
							txv_nodata.setVisibility(View.GONE);
						} else {
							lv_all_booking.setVisibility(View.GONE);
							txv_nodata.setVisibility(View.VISIBLE);
						}

					}
				} else {
					// Toast.makeText(OnlineTable_Activity.this,
					// "No Data Found",
					// Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException np) {

			}
			System.out.println("1111success" + jsonSuccessStr);
		}
	}

	private void secOnclicklistner() {
		// TODO Auto-generated method stub
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AllBookingActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		txv_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Date();
			}

		});
		txv_all_default.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_booking_type = "0";
				txv_all_select.setVisibility(View.VISIBLE);
				txv_online_default.setVisibility(View.VISIBLE);
				txv_grabbing_default.setVisibility(View.VISIBLE);
				txv_all_default.setVisibility(View.GONE);
				txv_online_select.setVisibility(View.GONE);
				txv_grabbing_select.setVisibility(View.GONE);
				try {
					System.out.println("7777moklayustr_date" + str_date);
					System.out.println("7777moklayustr_meal" + str_search);
					System.out.println("7777moklayustr_booking_type"
							+ str_booking_type);
					System.out.println("7777moklayuarray_AllBooking"
							+ Global_variable.array_AllBooking);
					array_filter = AllBookingActivityFilterClass
							.filter_all_booking(str_date, str_search,
									str_booking_type,
									Global_variable.array_AllBooking);
					System.out.println("6666allsarray" + array_filter);
					if (array_filter != null) {
						AllBookingAdapter = new AllBookingAdapter(
								AllBookingActivity.this, array_filter);
						lv_all_booking.setAdapter(AllBookingAdapter);
						lv_all_booking.setVisibility(View.VISIBLE);
						txv_nodata.setVisibility(View.GONE);
					} else {
						lv_all_booking.setVisibility(View.GONE);
						txv_nodata.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		txv_online_default.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_booking_type = "oo";
				txv_online_select.setVisibility(View.VISIBLE);
				txv_grabbing_default.setVisibility(View.VISIBLE);
				txv_all_default.setVisibility(View.VISIBLE);
				txv_online_default.setVisibility(View.GONE);
				txv_all_select.setVisibility(View.GONE);
				txv_grabbing_select.setVisibility(View.GONE);
				try {
					System.out.println("7777moklayustr_date" + str_date);
					System.out.println("7777moklayustr_meal" + str_search);
					System.out.println("7777moklayustr_booking_type"
							+ str_booking_type);
					System.out.println("7777moklayuarray_AllBooking"
							+ Global_variable.array_AllBooking);
					array_filter = AllBookingActivityFilterClass
							.filter_all_booking(str_date, str_search,
									str_booking_type,
									Global_variable.array_AllBooking);
					System.out.println("6666onlinesarray" + array_filter);
					if (array_filter != null) {
						AllBookingAdapter = new AllBookingAdapter(
								AllBookingActivity.this, array_filter);
						lv_all_booking.setAdapter(AllBookingAdapter);
						lv_all_booking.setVisibility(View.VISIBLE);
						txv_nodata.setVisibility(View.GONE);
					} else {
						lv_all_booking.setVisibility(View.GONE);
						txv_nodata.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		txv_grabbing_default.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_booking_type = "tg";
				txv_grabbing_select.setVisibility(View.VISIBLE);
				txv_all_default.setVisibility(View.VISIBLE);
				txv_online_default.setVisibility(View.VISIBLE);
				txv_online_select.setVisibility(View.GONE);
				txv_grabbing_default.setVisibility(View.GONE);
				txv_all_select.setVisibility(View.GONE);
				try {
					System.out.println("7777moklayustr_date" + str_date);
					System.out.println("7777moklayustr_meal" + str_search);
					System.out.println("7777moklayustr_booking_type"
							+ str_booking_type);
					System.out.println("7777moklayuarray_AllBooking"
							+ Global_variable.array_AllBooking);
					array_filter = AllBookingActivityFilterClass
							.filter_all_booking(str_date, str_search,
									str_booking_type,
									Global_variable.array_AllBooking);
					System.out.println("6666grabsarray" + array_filter);
					if (array_filter != null) {
						AllBookingAdapter = new AllBookingAdapter(
								AllBookingActivity.this, array_filter);
						lv_all_booking.setAdapter(AllBookingAdapter);
						lv_all_booking.setVisibility(View.VISIBLE);
						txv_nodata.setVisibility(View.GONE);
					} else {
						lv_all_booking.setVisibility(View.GONE);
						txv_nodata.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}

	private void Date() {
		// TODO Auto-generated method stub
		dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int selectedyear,
							int selectedmonth, int selectedday) {
						mYear = selectedyear;
						mMonth = selectedmonth;
						mDay = selectedday;
						// Display Selected date in textbox
						// txv_date.setText(selectedyear + "-"
						// + (selectedmonth + 1) + "-" + selectedday);
						String finalDate = (selectedyear + "-"
								+ (selectedmonth + 1) + "-" + selectedday);
						SimpleDateFormat formatter = new SimpleDateFormat(
								getString(R.string.str_yy));

						try {

							Date date;
							try {
								date = formatter.parse(finalDate);
								System.out.println("6666DATEFINAL" + date);
								System.out.println(formatter.format(date));
								str_date = formatter.format(date);
								// STR_Date=date.toString();
								txv_date.setText(str_date);
								System.out.println("6666date" + str_date);
								System.out.println("6666tgDATE" + str_date);
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NullPointerException n) {

							}

						} catch (ParseException e) {
							e.printStackTrace();
						}

						try {
							System.out
									.println("7777moklayustr_date" + str_date);
							System.out.println("7777moklayustr_meal"
									+ str_search);
							System.out.println("7777moklayustr_booking_type"
									+ str_booking_type);
							System.out.println("7777moklayuarray_AllBooking"
									+ Global_variable.array_AllBooking);
							array_filter = AllBookingActivityFilterClass
									.filter_all_booking(str_date, str_search,
											str_booking_type,
											Global_variable.array_AllBooking);
							System.out.println("6666datesarray"
									+ AllBookingActivityFilterClass
											.filter_all_booking(
													str_date,
													str_search,
													str_booking_type,
													Global_variable.array_AllBooking));
							if (array_filter != null) {
								AllBookingAdapter = new AllBookingAdapter(
										AllBookingActivity.this, array_filter);
								lv_all_booking.setAdapter(AllBookingAdapter);
								lv_all_booking.setVisibility(View.VISIBLE);
								txv_nodata.setVisibility(View.GONE);
							} else {
								lv_all_booking.setVisibility(View.GONE);
								txv_nodata.setVisibility(View.VISIBLE);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, mYear, mMonth, mDay);
		dpd.show();
		dpd.setCancelable(false);
		dpd.setCanceledOnTouchOutside(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_booking, menu);
		return true;
	}

}
