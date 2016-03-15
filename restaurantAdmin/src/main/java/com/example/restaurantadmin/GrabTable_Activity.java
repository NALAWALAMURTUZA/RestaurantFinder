package com.example.restaurantadmin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.adapter.GrabTableAdapter;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class GrabTable_Activity extends Activity {
	public static ProgressDialog p;
	public static ListView lv_gt_order;
	public static TextView txv_invisible;
	public static GrabTableAdapter GrabTableAdapter;
	public static Dialog FilterDialog_TG;

	// ***************wheelview
	private boolean timeChanged = false;

	// Time scrolled flag
	private boolean timeScrolled = false;
	ConnectionDetector cd;
	public static String str_year, str_month, STR_Date;
	int curDay;
	Calendar calendar;
	String finalDate;
	String CurentDate, STR_CuurentDate;
	// ***************filter
	// ***********
	// *********
	public static String str_tg_shift = "0";
	public static String str_tg_order_status = "0";
	public static RadioGroup rg_tg_order_status, rg_shift;
	public static JSONArray array_filter = new JSONArray();
	private Locale myLocale;

	// public static boolean tab_Grab_flag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_grab_table);
		initialization();
		cd = new ConnectionDetector(getApplicationContext());

		Global_variable.array_online_table_grabbing = new JSONArray();
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

		calendar = Calendar.getInstance();

		final WheelView month = (WheelView) findViewById(R.id.month);
		final WheelView year = (WheelView) findViewById(R.id.year);
		// final WheelView day = (WheelView) findViewById(R.id.day);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[]{getString(R.string.jan),
				getString(R.string.feb), getString(R.string.mar),
				getString(R.string.apr), getString(R.string.may),
				getString(R.string.june), getString(R.string.july),
				getString(R.string.augest), getString(R.string.sep),
				getString(R.string.oct), getString(R.string.nov),
				getString(R.string.dec)};
		month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		year.setViewAdapter(new DateNumericAdapter(this, curYear, curYear + 10,
				0));
		year.setCurrentItem(curYear);
		year.addChangingListener(listener);

		// day
		updateDays(year, month);
		// day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
		// day.addChangingListener(listener);
		setlistner();

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

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month) {
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		// day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar
		// .get(Calendar.DAY_OF_MONTH) - 1));

		// curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		System.out.println("11111curday" + curDay);
		// day.setCurrentItem(curDay - 1, true);
		// System.out.println("11111day11:-" + day.toString());

		// ***********useble value
		int Year = calendar.get(Calendar.YEAR);
		str_year = String.valueOf(Year);
		System.out.println("1111OTYEAR" + str_year);
		int Month = (month.getCurrentItem() + 1);
		str_month = String.valueOf(Month);
		System.out.println("1111OTMONYH" + str_month);
		// int Day = (curDay);
		// System.out.println("11111intday:-" + Day);
		// str_day = String.valueOf(Day);
		// System.out.println("11111strday" + str_day);
		// ***************current

		// /**********cuureentdate
		int DAY = calendar.get(Calendar.DAY_OF_MONTH);
		String str_cuurentday = String.valueOf(DAY);
		System.out.println("11111strcuureeent:::" + str_cuurentday);
		System.out.println("11111currentday::::" + DAY);
		// System.out.println("11111adapterday:-" + day.toString());
		CurentDate = (str_year + "-" + str_month).toLowerCase(Locale
				.getDefault());
		System.out.println("11111cuureentday" + CurentDate);
		// *********************
		// /*********final
		finalDate = (str_year + "-" + str_month).toLowerCase(Locale
				.getDefault());
		System.out.println("1111OTfinalDate" + finalDate);
		// String startDateString = "06/27/2007";
		// ********start
		SimpleDateFormat formatter = new SimpleDateFormat(
				getString(R.string.str_yyyy_MM));

		try {

			Date date;
			try {
				date = formatter.parse(finalDate);
				System.out.println("1111DATEFINAL" + date);
				System.out.println(formatter.format(date));
				STR_Date = formatter.format(date);
				// STR_Date=date.toString();

				System.out.println("1111OTDATE" + STR_Date);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {

			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		// STR_Date = timeFormat.format(finalDate);

		System.out.println("1111OTDATE" + STR_Date);
		// System.out.println("1111OTDAY" + str_day);
		System.out.println("1111year..." + calendar.get(Calendar.YEAR));
		System.out.println("1111month..." + (month.getCurrentItem() + 1));
		System.out.println("1111day..." + (curDay));
		int k = 0;
		System.out.println("1111print" + STR_Date);
		System.out.println("1111lenthorderfoodlength"
				+ Global_variable.array_online_table_grabbing.length());
		System.out.println("1111lenthorderfood"
				+ Global_variable.array_online_table_grabbing);
		JSONArray array = null;
		array = new JSONArray();
		array = Global_variable.array_online_table_grabbing;
		if (Global_variable.array_online_table_grabbing.length() != 0) {
			if (STR_Date != null) {

				GrabTableAdapter.filter(STR_Date);
				k++;
				System.out.println("111111ketlivargyuKma" + k);
				System.out.println("1111lenthorderfoodafter_in_if_loop"
						+ Global_variable.array_online_table_grabbing);
				if (Global_variable.array_online_table_grabbing.length() != 0) {
					System.out.println("ifmagyuknai");
					lv_gt_order.setVisibility(View.VISIBLE);
					txv_invisible.setVisibility(View.GONE);
					GrabTableAdapter = new GrabTableAdapter(
							GrabTable_Activity.this,
							Global_variable.array_online_table_grabbing);
					lv_gt_order.setAdapter(GrabTableAdapter);
				} else {
					lv_gt_order.setVisibility(View.GONE);
					txv_invisible.setVisibility(View.VISIBLE);
					// Toast.makeText(GrabTable_Activity.this, "No Data Found",
					// Toast.LENGTH_LONG).show();
				}

			}
		}
		System.out.println("1111lenthorderfoodafter"
				+ Global_variable.array_online_table_grabbing);

		Global_variable.array_online_table_grabbing = array;

		System.out.println("1111lenthorderfoodafter_add"
				+ Global_variable.array_online_table_grabbing);

		// ***********GET USE date
		// ********end
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * 
	 * @param wheel
	 *            the wheel
	 * @param label
	 *            the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}

	private void setlistner() {
		// TODO Auto-generated method stub
		TakeBooking_Tablayout.img_filter
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TakeBooking_Tablayout.tab.setCurrentTab(0);
						FilterDialogTG();
					}
				});

	}

	private void initialization() {
		// TODO Auto-generated method stub
		lv_gt_order = (ListView) findViewById(R.id.lv_gt_order);
		txv_invisible = (TextView) findViewById(R.id.txv_invisible);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_booking, menu);
		return true;
	}

	public class async_getrest_order extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_restaurant_order;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(GrabTable_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			obj_restaurant_order = new JSONObject();

			try {
				System.out.println("1111restidinGT"
						+ Global_variable.restaurant_id);
				// obj_restaurant_app********
				// obj_restaurant_order.put("restaurant_id","300" );
				obj_restaurant_order.put("restaurant_id",
						Global_variable.restaurant_id);
				obj_restaurant_order.put("sessid", Global_variable.sessid);
				System.out.println("1111obj_restaurant_graborder"
						+ obj_restaurant_order);

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
				// ****** response text
				try {
					myconnection con = new myconnection();
					String str_response = con.connection(
							GrabTable_Activity.this,
							Global_variable.rf_api_getrestaurantorder,
							obj_restaurant_order);

					json = new JSONObject(str_response);
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
				if (json != null) {
					String STR_DATE = null;
					jsonSuccessStr = json.getString("success");
					Global_variable.sessid = json.getString("sessid");
					System.out.println("1111sessidstep2respo"
							+ Global_variable.sessid);
					if (jsonSuccessStr.equalsIgnoreCase("true")) {
						JSONObject Data = json.getJSONObject("data");
						System.out.println("1111obj_Data" + Data);
						if (Data != null) {
							Global_variable.array_online_table_grabbing = Data
									.getJSONArray("online_table_grabbing");
							System.out
									.println("1111array_online_table_grabbing"
											+ Global_variable.array_online_table_grabbing);
							if (Global_variable.array_online_table_grabbing != null) {

								System.out.println("0000strdatebarloop"
										+ STR_DATE);
								System.out.println("0000formateddateifbefoer");
								// ********start
								SimpleDateFormat formatter1 = new SimpleDateFormat(
										getString(R.string.str_yyyy_MM));

								try {

									Date date;
									try {
										date = formatter1.parse(CurentDate);
										System.out.println("1111CurentDate"
												+ date);
										System.out.println(formatter1
												.format(date));
										STR_CuurentDate = formatter1
												.format(date);
										// STR_Date=date.toString();

										System.out
												.println("1111OTSTR_CuurentDate"
														+ STR_CuurentDate);
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} catch (ParseException e) {
									e.printStackTrace();
								}
								System.out.println("1111ifOTSTR_CuurentDate"
										+ STR_CuurentDate);
								System.out.println("1111ifOTSTR_Date"
										+ STR_DATE);
								if (Global_variable.array_online_table_grabbing != null) {
									System.out.println("gyuuuuuuu");
									GrabTableAdapter = new GrabTableAdapter(
											GrabTable_Activity.this,
											Global_variable.array_online_table_grabbing);
									lv_gt_order.setAdapter(GrabTableAdapter);
									lv_gt_order.setVisibility(View.VISIBLE);
									txv_invisible.setVisibility(View.GONE);
								} else {
									lv_gt_order.setVisibility(View.GONE);
									txv_invisible.setVisibility(View.VISIBLE);
								}
								// ********start
								System.out
										.println("1111lenthorderfoodlength"
												+ Global_variable.array_online_table_grabbing
														.length());
								System.out
										.println("1111lenthorderfood"
												+ Global_variable.array_online_table_grabbing);
								JSONArray array = null;
								array = new JSONArray();
								array = Global_variable.array_online_table_grabbing;
								if (Global_variable.array_online_table_grabbing
										.length() != 0) {
									if (STR_CuurentDate != null) {

										GrabTableAdapter
												.filter(STR_CuurentDate);
										System.out
												.println("1111lenthorderfoodafter_in_if_loop"
														+ Global_variable.array_online_table_grabbing);
										if (Global_variable.array_online_table_grabbing
												.length() != 0) {
											System.out.println("ifmagyuknai");
											lv_gt_order
													.setVisibility(View.VISIBLE);
											txv_invisible
													.setVisibility(View.GONE);
											GrabTableAdapter = new GrabTableAdapter(
													GrabTable_Activity.this,
													Global_variable.array_online_table_grabbing);
											lv_gt_order
													.setAdapter(GrabTableAdapter);

										} else {
											lv_gt_order
													.setVisibility(View.GONE);
											txv_invisible
													.setVisibility(View.VISIBLE);
										}

									} else {
										GrabTableAdapter = new GrabTableAdapter(
												GrabTable_Activity.this,
												Global_variable.array_online_table_grabbing);
										lv_gt_order
												.setAdapter(GrabTableAdapter);
										lv_gt_order.setVisibility(View.GONE);
										txv_invisible
												.setVisibility(View.VISIBLE);
									}
								}
								System.out
										.println("1111lenthorderfoodafter"
												+ Global_variable.array_online_table_grabbing);

								Global_variable.array_online_table_grabbing = array;

								System.out
										.println("1111lenthorderfoodafter_add"
												+ Global_variable.array_online_table_grabbing);

							} else {
								lv_gt_order.setVisibility(View.GONE);
								txv_invisible.setVisibility(View.VISIBLE);
							}
						}
					} else {
						Toast.makeText(GrabTable_Activity.this,
								R.string.str_no_data_found, Toast.LENGTH_LONG)
								.show();
					}
				} else {
					// Toast.makeText(GrabTable_Activity.this, "No Data Found",
					// Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1111success" + jsonSuccessStr);
		}
	}

	// Dialog
	public void FilterDialogTG() {
		try {
			FilterDialog_TG = new Dialog(GrabTable_Activity.this);
			FilterDialog_TG.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			FilterDialog_TG.setContentView(R.layout.popup_filter_tg);
			FilterDialog_TG.setFeatureDrawableResource(
					Window.FEATURE_LEFT_ICON, R.drawable.filter_icon);
			FilterDialog_TG.setTitle(getResources().getString(R.string.filter));
			rg_tg_order_status = (RadioGroup) FilterDialog_TG
					.findViewById(R.id.rg_tg_order_status);
			// ****************order statsus
			RadioButton rb_tg_order_status_all = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_tg_order_status_all);
			RadioButton rb_tg_pending = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_tg_pending);
			RadioButton rb_tg_confirmed = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_tg_confirmed);
			RadioButton rb_tg_canceled = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_tg_canceled);

			rg_shift = (RadioGroup) FilterDialog_TG.findViewById(R.id.rg_shift);

			// shift********;
			RadioButton rb_tg_all = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_tg_all);
			RadioButton rb_launch = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_launch);
			RadioButton rb_dinner = (RadioButton) FilterDialog_TG
					.findViewById(R.id.rb_dinner);
			// *******************************shift
			try {
				if (str_tg_shift.equalsIgnoreCase("0")) {
					System.out.println("0rdbtn");
					rb_tg_all.setChecked(true);
				} else if (str_tg_shift.equalsIgnoreCase("1")) {
					System.out.println("1rdbtn");
					rb_launch.setChecked(true);
				} else if (str_tg_shift.equalsIgnoreCase("2")) {
					System.out.println("2rdbtn");
					rb_dinner.setChecked(true);
				}
			} catch (NullPointerException n) {

			}
			// order status********************
			try {
				if (str_tg_order_status.equalsIgnoreCase("0")) {
					rb_tg_order_status_all.setChecked(true);
				} else if (str_tg_order_status.equalsIgnoreCase("1")) {
					rb_tg_pending.setChecked(true);
				} else if (str_tg_order_status.equalsIgnoreCase("2")) {
					rb_tg_confirmed.setChecked(true);
				} else if (str_tg_order_status.equalsIgnoreCase("6")) {
					rb_tg_canceled.setChecked(true);
				}
			} catch (NullPointerException n) {

			}
			// ************** shift onclick
			rg_shift.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					switch (checkedId) {
						case R.id.rb_tg_all :
							str_tg_shift = "0";
							System.out.println("clickall" + str_tg_shift);
							try {
								System.out.println("dategrabtableactivity"
										+ STR_Date);
								array_filter = GrabTable_ActivityFilterClass
										.filter_all(
												STR_Date,
												str_tg_shift,
												str_tg_order_status,
												Global_variable.array_online_table_grabbing);
								System.out.println("arrayinalltgshift"
										+ array_filter);
								if (array_filter != null) {
									lv_gt_order.setVisibility(View.VISIBLE);
									txv_invisible.setVisibility(View.GONE);
									GrabTableAdapter = new GrabTableAdapter(
											GrabTable_Activity.this,
											array_filter);
									lv_gt_order.setAdapter(GrabTableAdapter);
								} else {
									lv_gt_order.setVisibility(View.GONE);
									txv_invisible.setVisibility(View.VISIBLE);
								}
								FilterDialog_TG.dismiss();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case R.id.rb_launch :
							str_tg_shift = "1";
							System.out.println("clicklaunch" + str_tg_shift);
							try {
								System.out.println("dateforlaunch"
										+ GrabTable_Activity.STR_Date);
								array_filter = GrabTable_ActivityFilterClass
										.filter_all(
												STR_Date,
												str_tg_shift,
												str_tg_order_status,
												Global_variable.array_online_table_grabbing);
								System.out.println("arrayfilterforlaunch"
										+ array_filter);
								if (array_filter != null) {
									lv_gt_order.setVisibility(View.VISIBLE);
									txv_invisible.setVisibility(View.GONE);
									GrabTableAdapter = new GrabTableAdapter(
											GrabTable_Activity.this,
											array_filter);
									lv_gt_order.setAdapter(GrabTableAdapter);
								} else {
									lv_gt_order.setVisibility(View.GONE);
									txv_invisible.setVisibility(View.VISIBLE);
								}
								FilterDialog_TG.dismiss();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case R.id.rb_dinner :
							str_tg_shift = "2";
							System.out.println("clickdinner" + str_tg_shift);
							try {
								array_filter = GrabTable_ActivityFilterClass
										.filter_all(
												STR_Date,
												str_tg_shift,
												str_tg_order_status,
												Global_variable.array_online_table_grabbing);
								if (array_filter != null) {
									lv_gt_order.setVisibility(View.VISIBLE);
									txv_invisible.setVisibility(View.GONE);
									GrabTableAdapter = new GrabTableAdapter(
											GrabTable_Activity.this,
											array_filter);
									lv_gt_order.setAdapter(GrabTableAdapter);
								} else {
									lv_gt_order.setVisibility(View.GONE);
									txv_invisible.setVisibility(View.VISIBLE);
								}
								FilterDialog_TG.dismiss();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
					}

				}
			});

			// **************order status TG onclick
			rg_tg_order_status
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							switch (checkedId) {
								case R.id.rb_tg_order_status_all :
									str_tg_order_status = "0";

									try {
										array_filter = GrabTable_ActivityFilterClass
												.filter_all(
														STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											lv_gt_order
													.setVisibility(View.VISIBLE);
											txv_invisible
													.setVisibility(View.GONE);
											GrabTableAdapter = new GrabTableAdapter(
													GrabTable_Activity.this,
													array_filter);
											lv_gt_order
													.setAdapter(GrabTableAdapter);
										} else {
											lv_gt_order
													.setVisibility(View.GONE);
											txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_TG.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case R.id.rb_tg_pending :
									str_tg_order_status = "1";

									try {
										array_filter = GrabTable_ActivityFilterClass
												.filter_all(
														STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											lv_gt_order
													.setVisibility(View.VISIBLE);
											txv_invisible
													.setVisibility(View.GONE);
											GrabTableAdapter = new GrabTableAdapter(
													GrabTable_Activity.this,
													array_filter);
											lv_gt_order
													.setAdapter(GrabTableAdapter);
										} else {
											lv_gt_order
													.setVisibility(View.GONE);
											txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_TG.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case R.id.rb_tg_confirmed :
									str_tg_order_status = "2";

									try {
										array_filter = GrabTable_ActivityFilterClass
												.filter_all(
														STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											lv_gt_order
													.setVisibility(View.VISIBLE);
											txv_invisible
													.setVisibility(View.GONE);
											GrabTableAdapter = new GrabTableAdapter(
													GrabTable_Activity.this,
													array_filter);
											lv_gt_order
													.setAdapter(GrabTableAdapter);
										} else {
											lv_gt_order
													.setVisibility(View.GONE);
											txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_TG.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case R.id.rb_tg_canceled :
									str_tg_order_status = "6";

									try {
										array_filter = GrabTable_ActivityFilterClass
												.filter_all(
														STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											lv_gt_order
													.setVisibility(View.VISIBLE);
											txv_invisible
													.setVisibility(View.GONE);
											GrabTableAdapter = new GrabTableAdapter(
													GrabTable_Activity.this,
													array_filter);
											lv_gt_order
													.setAdapter(GrabTableAdapter);
										} else {
											lv_gt_order
													.setVisibility(View.GONE);
											txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_TG.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
							}

						}
					});

			FilterDialog_TG.show();
			// FilterDialog.setCancelable(false);
			// FilterDialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				onBackPressed();
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {

			// ExitFromAppDialog();
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

}
