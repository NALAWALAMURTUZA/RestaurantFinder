package com.example.restaurantadmin;

import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.adapter.GrabTableAdapter;
import com.restaurantadmin.adapter.OnlineTableAdapter;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

public class TakeBooking_Tablayout extends TabActivity {
	public static String STR_Date;
	public static TabHost tab;
	public static Resources res;
	public static TabSpec spec;
	public static Intent intent1, intent2, intent3, intent4;
	public static ImageView img_menu;
	ConnectionDetector cd;
	public static ImageView img_home, img_filter;
	public static Dialog FilterDialog_TG, FilterDialog_OO;
	public static RadioGroup rg_tg_order_status, rg_oo_order_status, rg_shift,
			rg_service_process;
	// public static boolean oo_flag = false;

	// **************
	public static String str_oo_service_type = "3";
	public static String str_oo_order_status = "Waiting";
	public static String str_tg_shift = "0";
	public static String str_tg_order_status = "0";
	public static JSONArray array_filter = new JSONArray();
	public static JSONArray array_filter_oo = new JSONArray();
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_booking_list_tablayout);
		cd = new ConnectionDetector(getApplicationContext());
		InitializeWidget();
		tab = getTabHost();
		tab.getTabWidget().setClickable(false);
		tab.setClickable(false);
		// Tab for Step 1
		TabSpec step1spec = tab.newTabSpec(getResources().getString(
				R.string.Tb_tablayoutGT));
		// setting Title and Icon for the Tab
		// step1spec.setIndicator("Step 1",
		// getResources().getDrawable(R.drawable.icon_photos_tab));
		step1spec.setIndicator(getResources().getString(R.string.Tb_tablayoutGT));
		intent1 = new Intent(this, GrabTable_Activity.class);
		step1spec.setContent(intent1);

		// Tab for Step 2
		TabSpec step2spec = tab.newTabSpec(getResources().getString(
				R.string.Tb_tablayoutOO));
		step2spec.setIndicator(getResources().getString(R.string.Tb_tablayoutOO),
				getResources().getDrawable(R.drawable.rounded_corner));
		intent2 = new Intent(this, OnlineTable_Activity.class);
		step2spec.setContent(intent2);

		tab.addTab(step1spec); // Adding step 1 tab
		tab.addTab(step2spec); // Adding step 2 tab
		tab.setCurrentTab(0);

		setListener();

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

	@SuppressWarnings("deprecation")
	private void setListener() {

		getTabHost().setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				int i = getTabHost().getCurrentTab();
				Log.i("@@@@@@@@ ANN CLICK TAB NUMBER", "------" + i);

				if (i == 0) {
					System.out.println("5555TGtab0");
					Log.i("@@@@@@@@@@ Inside onClick tab 0", "onClick tab");
					img_filter.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							FilterDialogTG();

						}
					});
				} else if (i == 1) {
					System.out.println("5555OOtab0");
					Log.i("@@@@@@@@@@ Inside onClick tab 1", "onClick tab");
					img_filter.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// if (OnlineTable_Activity.oo_flag == false) {
							//
							// }
							FilterDialogOO();
						}
					});
				}
			}
		});
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(TakeBooking_Tablayout.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		// img_menu.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // FilterDialog();
		// PopupMenu popup = new PopupMenu(TakeBooking_Tablayout.this,
		// TakeBooking_Tablayout.img_menu);
		// popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
		//
		// popup.setOnMenuItemClickListener(new
		// PopupMenu.OnMenuItemClickListener() {
		// public boolean onMenuItemClick(MenuItem item) {
		// if (item.getTitle().toString().equals("Manage Photos")) {
		//
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// ManageGalleryActivity.class);
		// startActivity(intent);
		// // finish();
		//
		// } else if (item.getTitle().toString()
		// .equals("Food Order")) {
		//
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		//
		// Food_Detail_Categories_Activity.class);
		// startActivity(intent);
		// // finish();
		//
		// } else if (item.getTitle().toString()
		// .equals("Take Booking")) {
		//
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		//
		// TakeBookingActivity.class);
		// startActivity(intent);
		// // finish();
		//
		// } else if (item.getTitle().toString()
		// .equals("Restaurant Presentation")) {
		//
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		//
		// RestaurantPrasentationActivity.class);
		// startActivity(intent);
		// // finish();
		//
		// } else if (item.getTitle().toString()
		// .equals("Cart and Set Menu")) {
		//
		// {
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// CartSetMenuActivity.class);
		// startActivity(intent);
		// }
		// // finish();
		// } else if (item.getTitle().toString()
		// .equals("All Booking")) {
		//
		// {
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// AllBookingActivity.class);
		// startActivity(intent);
		// }
		// // finish();
		// } else if (item.getTitle().toString()
		// .equals("Manage Customers")) {
		//
		// {
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// ManageCustomersActivity.class);
		// startActivity(intent);
		// }
		// // finish();
		// } else if (item.getTitle().toString()
		// .equals("Manage Feedback")) {
		//
		// {
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// ManageFeedbackActivity.class);
		// startActivity(intent);
		// }
		// // finish();
		// } else if (item.getTitle().toString()
		// .equals("Manage Subscription")) {
		//
		// {
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// ManageSubscriptionActivity.class);
		// startActivity(intent);
		// }
		// // finish();
		// }
		// else if (item.getTitle().toString()
		// .equals("Global Setting")) {
		//
		// {
		// Intent intent = new Intent(
		// TakeBooking_Tablayout.this,
		// GlobalSettingActivity.class);
		// startActivity(intent);
		// }
		// // finish();
		// }
		// return true;
		//
		// }
		// });
		//
		// popup.show();
		// }
		// });

	}

	private void InitializeWidget() {
		img_home = (ImageView) findViewById(R.id.img_home);
		img_filter = (ImageView) findViewById(R.id.img_filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking_list__tablayout, menu);
		return true;
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
	// // ExitFromAppDialog();
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

	public void FilterDialogTG() {
		try {
			FilterDialog_TG = new Dialog(TakeBooking_Tablayout.this);
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
										+ GrabTable_Activity.STR_Date);
								array_filter = GrabTable_ActivityFilterClass
										.filter_all(
												GrabTable_Activity.STR_Date,
												str_tg_shift,
												str_tg_order_status,
												Global_variable.array_online_table_grabbing);
								System.out.println("arrayinalltgshift"
										+ array_filter);
								if (array_filter != null) {
									GrabTable_Activity.lv_gt_order
											.setVisibility(View.VISIBLE);
									GrabTable_Activity.txv_invisible
											.setVisibility(View.GONE);
									GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
											TakeBooking_Tablayout.this,
											array_filter);
									GrabTable_Activity.lv_gt_order
											.setAdapter(GrabTable_Activity.GrabTableAdapter);
								} else {
									GrabTable_Activity.lv_gt_order
											.setVisibility(View.GONE);
									GrabTable_Activity.txv_invisible
											.setVisibility(View.VISIBLE);
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
												GrabTable_Activity.STR_Date,
												str_tg_shift,
												str_tg_order_status,
												Global_variable.array_online_table_grabbing);
								System.out.println("arrayfilterforlaunch"
										+ array_filter);
								if (array_filter != null) {
									GrabTable_Activity.lv_gt_order
											.setVisibility(View.VISIBLE);
									GrabTable_Activity.txv_invisible
											.setVisibility(View.GONE);
									GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
											TakeBooking_Tablayout.this,
											array_filter);
									GrabTable_Activity.lv_gt_order
											.setAdapter(GrabTable_Activity.GrabTableAdapter);
								} else {
									GrabTable_Activity.lv_gt_order
											.setVisibility(View.GONE);
									GrabTable_Activity.txv_invisible
											.setVisibility(View.VISIBLE);
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
												GrabTable_Activity.STR_Date,
												str_tg_shift,
												str_tg_order_status,
												Global_variable.array_online_table_grabbing);
								if (array_filter != null) {
									GrabTable_Activity.lv_gt_order
											.setVisibility(View.VISIBLE);
									GrabTable_Activity.txv_invisible
											.setVisibility(View.GONE);
									GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
											TakeBooking_Tablayout.this,
											array_filter);
									GrabTable_Activity.lv_gt_order
											.setAdapter(GrabTable_Activity.GrabTableAdapter);
								} else {
									GrabTable_Activity.lv_gt_order
											.setVisibility(View.GONE);
									GrabTable_Activity.txv_invisible
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
														GrabTable_Activity.STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.VISIBLE);
											GrabTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter);
											GrabTable_Activity.lv_gt_order
													.setAdapter(GrabTable_Activity.GrabTableAdapter);
										} else {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.GONE);
											GrabTable_Activity.txv_invisible
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
														GrabTable_Activity.STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.VISIBLE);
											GrabTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter);
											GrabTable_Activity.lv_gt_order
													.setAdapter(GrabTable_Activity.GrabTableAdapter);
										} else {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.GONE);
											GrabTable_Activity.txv_invisible
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
														GrabTable_Activity.STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.VISIBLE);
											GrabTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter);
											GrabTable_Activity.lv_gt_order
													.setAdapter(GrabTable_Activity.GrabTableAdapter);
										} else {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.GONE);
											GrabTable_Activity.txv_invisible
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
														GrabTable_Activity.STR_Date,
														str_tg_shift,
														str_tg_order_status,
														Global_variable.array_online_table_grabbing);
										if (array_filter != null) {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.VISIBLE);
											GrabTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter);
											GrabTable_Activity.lv_gt_order
													.setAdapter(GrabTable_Activity.GrabTableAdapter);
										} else {
											GrabTable_Activity.lv_gt_order
													.setVisibility(View.GONE);
											GrabTable_Activity.txv_invisible
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

	public void FilterDialogOO() {
		try {

			FilterDialog_OO = new Dialog(TakeBooking_Tablayout.this);
			FilterDialog_OO.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			FilterDialog_OO.setContentView(R.layout.popup_filter_oo);
			FilterDialog_OO.setFeatureDrawableResource(
					Window.FEATURE_LEFT_ICON, R.drawable.filter_icon);
			FilterDialog_OO.setTitle(getResources().getString(R.string.filter));
			rg_oo_order_status = (RadioGroup) FilterDialog_OO
					.findViewById(R.id.rg_oo_order_status);

			RadioButton rb_oo_order_status_all = (RadioButton) FilterDialog_OO
					.findViewById(R.id.rb_oo_order_status_all);
			RadioButton rb_oo_confirmed = (RadioButton) FilterDialog_OO
					.findViewById(R.id.rb_oo_confirmed);
			RadioButton rb_oo_canceled = (RadioButton) FilterDialog_OO
					.findViewById(R.id.rb_oo_canceled);
			rg_service_process = (RadioGroup) FilterDialog_OO
					.findViewById(R.id.rg_service_process);
			RadioButton rb_all = (RadioButton) FilterDialog_OO
					.findViewById(R.id.rb_all);
			RadioButton rb_delivery = (RadioButton) FilterDialog_OO
					.findViewById(R.id.rb_delivery);
			RadioButton rb_pickup = (RadioButton) FilterDialog_OO
					.findViewById(R.id.rb_pickup);
			// ********service ttype*********
			try {
				if (str_oo_service_type.equalsIgnoreCase("3")) {
					System.out.println("3rdbtn");
					rb_all.setChecked(true);
				} else if (str_oo_service_type.equalsIgnoreCase("1")) {
					System.out.println("0rdbtn");
					rb_delivery.setChecked(true);
				} else if (str_oo_service_type.equalsIgnoreCase("0")) {
					System.out.println("1rdbtn");
					rb_pickup.setChecked(true);
				}
			} catch (NullPointerException n) {

			}
			// ********orderstatus*******
			try {
				if (str_oo_order_status.equalsIgnoreCase("Waiting")) {
					System.out.println("6rd_oo_status");
					rb_oo_order_status_all.setChecked(true);
				} else if (str_oo_order_status.equalsIgnoreCase("Confirmed")) {
					System.out.println("2rdbtn_ooconfirme");
					rb_oo_confirmed.setChecked(true);
				} else if (str_oo_order_status.equalsIgnoreCase("Cancel")) {
					System.out.println("6rd_oo_status");
					rb_oo_canceled.setChecked(true);
				}
			} catch (NullPointerException n) {

			}

			// **********order status Radio group
			rg_oo_order_status
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							switch (checkedId) {
							// ********************ALLLLL
								case R.id.rb_oo_order_status_all :
									str_oo_order_status = "";
									str_oo_order_status = "all";
									try {
										System.out
												.println("Waiting:-date:-,service:-,status:-,arrayoofood:-"
														+ OnlineTable_Activity.STR_Date
														+ " "
														+ str_oo_service_type
														+ " "
														+ str_oo_order_status
														+ " "
														+ Global_variable.array_online_food_order);
										array_filter_oo = OnlineTable_ActivityFilterClass
												.filter_all(
														OnlineTable_Activity.STR_Date,
														str_oo_service_type,
														str_oo_order_status,
														Global_variable.array_online_food_order);

										if (array_filter_oo != null) {
											System.out
													.println("Waiting:-arrayfilter_oo"
															+ array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.VISIBLE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
										} else {
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.GONE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_OO.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									break;

								case R.id.rb_oo_confirmed :
									// confrimed click****************
									str_oo_order_status = "";
									str_oo_order_status = "Confirmed";
									try {
										System.out
												.println("Confirmed:-date:-,service:-,status:-,arrayoofood:-"
														+ OnlineTable_Activity.STR_Date
														+ " "
														+ str_oo_service_type
														+ " "
														+ str_oo_order_status
														+ " "
														+ Global_variable.array_online_food_order);
										array_filter_oo = OnlineTable_ActivityFilterClass
												.filter_all(
														OnlineTable_Activity.STR_Date,
														str_oo_service_type,
														str_oo_order_status,
														Global_variable.array_online_food_order);
										if (array_filter_oo != null) {
											System.out
													.println("Confirmed:-arrayfilter_oo"
															+ array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.VISIBLE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
										} else {
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.GONE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_OO.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case R.id.rb_oo_canceled :
									// cancel click*****************
									str_oo_order_status = "";
									str_oo_order_status = "Cancel";
									try {
										System.out
												.println("Cancel:-date:-,service:-,status:-,arrayoofood:-"
														+ OnlineTable_Activity.STR_Date
														+ " "
														+ str_oo_service_type
														+ " "
														+ str_oo_order_status
														+ " "
														+ Global_variable.array_online_food_order);
										array_filter_oo = OnlineTable_ActivityFilterClass
												.filter_all(
														OnlineTable_Activity.STR_Date,
														str_oo_service_type,
														str_oo_order_status,
														Global_variable.array_online_food_order);
										if (array_filter_oo != null) {
											System.out
													.println("Cancel:-arrayfilter_oo"
															+ array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.VISIBLE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
										} else {
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.GONE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_OO.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
							}

						}
					});
			// ********************
			rg_service_process
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							switch (checkedId) {

								case R.id.rb_all :
									// all service************
									str_oo_service_type = "";
									str_oo_service_type = "3";
									try {
										System.out
												.println("All3:-date:-,service:-,status:-,arrayoofood:-"
														+ OnlineTable_Activity.STR_Date
														+ " "
														+ str_oo_service_type
														+ " "
														+ str_oo_order_status
														+ " "
														+ Global_variable.array_online_food_order);
										array_filter_oo = OnlineTable_ActivityFilterClass
												.filter_all(
														OnlineTable_Activity.STR_Date,
														str_oo_service_type,
														str_oo_order_status,
														Global_variable.array_online_food_order);
										if (array_filter_oo != null) {
											System.out
													.println("All3:-arrayfilter_oo"
															+ array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.VISIBLE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
										} else {
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.GONE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_OO.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									break;

								case R.id.rb_delivery :
									// service delivery***************
									System.out
											.println("sysomagyuknairb_delivery");
									// do operations specific to this selection
									str_oo_service_type = "";
									str_oo_service_type = "1";
									try {
										System.out
												.println("del0:-date:-,service:-,status:-,arrayoofood:-"
														+ OnlineTable_Activity.STR_Date
														+ " "
														+ str_oo_service_type
														+ " "
														+ str_oo_order_status
														+ " "
														+ Global_variable.array_online_food_order);
										array_filter_oo = OnlineTable_ActivityFilterClass
												.filter_all(
														OnlineTable_Activity.STR_Date,
														str_oo_service_type,
														str_oo_order_status,
														Global_variable.array_online_food_order);
										if (array_filter_oo != null) {
											System.out
													.println("del0:-arrayfilter_oo"
															+ array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.VISIBLE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
										} else {
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.GONE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_OO.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case R.id.rb_pickup :
									// pickup service*********
									// do operations specific to this selection
									System.out
											.println("sysomagyuknairb_pickup");
									str_oo_service_type = "";
									str_oo_service_type = "0";
									try {
										System.out
												.println("pic1:-date:-,service:-,status:-,arrayoofood:-"
														+ OnlineTable_Activity.STR_Date
														+ " "
														+ str_oo_service_type
														+ " "
														+ str_oo_order_status
														+ " "
														+ Global_variable.array_online_food_order);
										array_filter_oo = OnlineTable_ActivityFilterClass
												.filter_all(
														OnlineTable_Activity.STR_Date,
														str_oo_service_type,
														str_oo_order_status,
														Global_variable.array_online_food_order);
										if (array_filter_oo != null) {
											System.out
													.println("pic1:-arrayfilter_oo"
															+ array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.VISIBLE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.GONE);
											OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
													TakeBooking_Tablayout.this,
													array_filter_oo);
											OnlineTable_Activity.lv_online_order
													.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
										} else {
											OnlineTable_Activity.lv_online_order
													.setVisibility(View.GONE);
											OnlineTable_Activity.txv_invisible
													.setVisibility(View.VISIBLE);
										}
										FilterDialog_OO.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;

							}

						}
					});
			// customHandler.post(updateTimerThread, 0);
			FilterDialog_OO.show();
			// FilterDialog.setCancelable(false);
			// FilterDialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {
			n.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
