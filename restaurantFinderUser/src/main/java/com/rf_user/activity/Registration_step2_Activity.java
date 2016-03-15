package com.rf_user.activity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Registration_step2_Activity extends Activity {

	public static TextView rf_regi_step2_tv_thank;
	public static TextView rf_regi_step2_tv_que;
	public static TextView rf_regi_step2_tv_no;
	public static TextView rf_regi_step2_tv_step;
	public static TextView rf_regi_step2_tv_dont;
	public static ImageView rf_regi_step2_tv_load;
	public static TextView textView2;
	public static CheckBox ch_launch_monday;
	public static CheckBox ch_launch_tuesday;
	public static CheckBox ch_launch_wednesday;
	public static CheckBox ch_launch_thursday;
	public static CheckBox ch_launch_friday;
	public static CheckBox ch_launch_saturday;
	public static CheckBox ch_launch_sunday;
	public static Spinner sp_launch_opening_time;
	public static Spinner sp_launch_last_booking;
	public static CheckBox ch_dinner_monday;
	public static CheckBox ch_dinner_tuesday;
	public static CheckBox ch_dinner_wednesday;
	public static CheckBox ch_dinner_thursday;
	public static CheckBox ch_dinner_friday;
	public static CheckBox ch_dinner_saturday;
	public static CheckBox ch_dinner_sunday;
	public static Spinner sp_dinner_opening_time;
	public static Spinner sp_dinner_last_booking;
	public static EditText ed_restaurant_seat;
	private EditText ed_average_bill;
	public static RadioGroup rg_last_minute_booking;
	public static RadioButton rb_last_minute_booking_yes;
	public static RadioButton rb_last_minute_booking_no;
	public static RadioGroup rg_facility;
	public static RadioButton rb_facility_pick;
	public static RadioButton rb_facility_delivery;
	public static RadioButton rb_facility_both;
	public static ImageView imageView29;
	public static EditText ed_add_comments;
	public static Button btn_continue;
	public static String str_rb_last_minute_booking = "1";
	public static String str_ch_pickupavail = "0", str_ch_delivery_avail = "0",
			str_ch_indine_avail = "0";
	public static String str_ch_launch_monday = "0",
			str_ch_launch_tuesday = "0", str_ch_launch_wednesday = "0",
			str_ch_launch_thursday = "0", str_ch_launch_friday = "0",
			str_ch_launch_saturday = "0", str_ch_launch_sunday = "0";

	public static String str_ch_dinner_monday = "0",
			str_ch_dinner_tuesday = "0", str_ch_dinner_wednesday = "0",
			str_ch_dinner_thursday = "0", str_ch_dinner_friday = "0",
			str_ch_dinner_saturday = "0", str_ch_dinner_sunday = "0";
	public static ProgressDialog p;
	ConnectionDetector cd;
	// **********changes
	public static CheckBox ch_pickup, ch_delivery;
	public static TextView txv_choose_icon, txv_choose_iconname,
			txv_choose_banner, txv_choose_bannername;
	public static ImageView img_upload_icon, img_upload_banner;
	//ProgressDialog dialog = null;
	int serverResponseCode = 0;
	public static String result = "", str_icon_path = "", str_banner_path = "",
			str_mto,picturePath,
			SELECT = "CAMERA";
	private Uri fileUri;
	public static boolean flag_icon = false;
	public static Spinner sp_mto;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int GALLERY_CAPTURE_IMAGE_REQUEST_CODE = 300;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

	// Opening time is always less then closing time
	public static String str_lunch_opening, str_lunch_close;
	public static String str_dinner_opening, str_dinner_close;
	public static int int_lunch_opening, int_lunch_close;
	public static int int_dinner_opening, int_dinner_close;
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_registration_step2_);
		cd = new ConnectionDetector(getApplicationContext());
		initialization();
		setlistner();
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

	private void setlistner() {
		// TODO Auto-generated method stub
		// ****************changes
		sp_dinner_opening_time
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_dinner_opening = "";
							String str_d_opening = sp_dinner_opening_time
									.getSelectedItem().toString();

							str_dinner_opening = str_d_opening.substring(0, 2);

							System.out.println("str_dinner_opening"
									+ str_dinner_opening);
							int_dinner_opening = Integer
									.parseInt(str_dinner_opening);
							System.out.println("int_dinner_opening"
									+ int_dinner_opening);
							// *************
							str_dinner_close = "";
							String str_d_close = sp_dinner_last_booking
									.getSelectedItem().toString();
							str_dinner_close = str_d_close.substring(0, 2);
							System.out.println("str_dinner_close"
									+ str_dinner_close);
							int_dinner_close = Integer
									.parseInt(str_dinner_close);
							System.out.println("int_dinner_close"
									+ int_dinner_close);
							if (int_dinner_opening < int_dinner_close) {

							} else {
								System.out.println("ifloop>");
								Toast.makeText(
										Registration_step2_Activity.this,
										getString(R.string.str_dinner_opening),
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		sp_dinner_last_booking
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_dinner_opening = "";
							String str_d_opening_booking = sp_dinner_opening_time
									.getSelectedItem().toString();

							str_dinner_opening = str_d_opening_booking
									.substring(0, 2);
							System.out.println("str_dinner_opening1"
									+ str_dinner_opening);
							int_dinner_opening = Integer
									.parseInt(str_dinner_opening);
							System.out.println("int_dinner_opening1"
									+ int_dinner_opening);
							// *************
							str_dinner_close = "";
							String str_d_close_time = sp_dinner_last_booking
									.getSelectedItem().toString();

							str_dinner_close = str_d_close_time.substring(0, 2);
							System.out.println("str_dinner_close1"
									+ str_dinner_close);
							int_dinner_close = Integer
									.parseInt(str_dinner_close);
							System.out.println("int_dinner_close1"
									+ int_dinner_close);
							if (int_dinner_opening < int_dinner_close) {

							} else {
								System.out.println("ifloop<");
								Toast.makeText(
										Registration_step2_Activity.this,
										getString(R.string.str_dinner_opening),
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		sp_launch_opening_time
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						try {
							str_lunch_opening = "";
							String str_c_opening = sp_launch_opening_time
									.getSelectedItem().toString();

							str_lunch_opening = str_c_opening.substring(0, 2);

							System.out.println("str_launch_opening"
									+ str_lunch_opening);
							int_lunch_opening = Integer
									.parseInt(str_lunch_opening);
							System.out.println("int_launch_opening"
									+ int_lunch_opening);
							// *************
							str_lunch_close = "";
							String str_c_close = sp_launch_last_booking
									.getSelectedItem().toString();
							str_lunch_close = str_c_close.substring(0, 2);
							System.out.println("str_launch_close"
									+ str_lunch_close);
							int_lunch_close = Integer.parseInt(str_lunch_close);
							System.out.println("int_launch_close"
									+ int_lunch_close);
							if (int_lunch_opening < int_lunch_close) {

							} else {
								System.out.println("ifloop>");
								Toast.makeText(
										Registration_step2_Activity.this,
										getString(R.string.str_Lunch_opening),
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		sp_launch_last_booking
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_lunch_opening = "";
							String str_c_opening = sp_launch_opening_time
									.getSelectedItem().toString();

							str_lunch_opening = str_c_opening.substring(0, 2);

							System.out.println("str_launch_opening"
									+ str_lunch_opening);
							int_lunch_opening = Integer
									.parseInt(str_lunch_opening);
							System.out.println("int_launch_opening"
									+ int_lunch_opening);
							// *************
							str_lunch_close = "";
							String str_c_close = sp_launch_last_booking
									.getSelectedItem().toString();
							str_lunch_close = str_c_close.substring(0, 2);
							System.out.println("str_launch_close"
									+ str_lunch_close);
							int_lunch_close = Integer.parseInt(str_lunch_close);
							System.out.println("int_launch_close"
									+ int_lunch_close);
							if (int_lunch_opening < int_lunch_close) {

							} else {
								System.out.println("ifloop>");
								Toast.makeText(
										Registration_step2_Activity.this,
										getString(R.string.str_Lunch_opening),
										Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException m) {
							m.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		btn_continue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (cd.isConnectingToInternet()) {
					if (int_lunch_opening >= int_lunch_close) {
						Toast.makeText(Registration_step2_Activity.this,
								getString(R.string.str_Lunch_opening),
								Toast.LENGTH_LONG).show();
					} else if (int_dinner_opening >= int_dinner_close) {
						Toast.makeText(Registration_step2_Activity.this,
								getString(R.string.str_dinner_opening),
								Toast.LENGTH_LONG).show();
					} else if (str_ch_pickupavail.equalsIgnoreCase("0")
							&& str_ch_delivery_avail.equalsIgnoreCase("0")) {
						Toast.makeText(Registration_step2_Activity.this,
								getString(R.string.str_facility),
								Toast.LENGTH_LONG).show();
					} else {
						new async_regi_step2().execute();
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
		// ***********************************
		ch_pickup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_pickupavail = "";
					str_ch_pickupavail = "1";

				} else {
					str_ch_pickupavail = "";
					str_ch_pickupavail = "0";
				}

			}
		});
		ch_delivery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_delivery_avail = "";
					str_ch_delivery_avail = "1";

				} else {
					str_ch_delivery_avail = "";
					str_ch_delivery_avail = "0";
				}

			}
		});
		// ch_indine.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// // is chkIos checked?
		// if (((CheckBox) v).isChecked()) {
		// // rdbtn_shipping_inf.setChecked(true);
		// str_ch_indine_avail = "";
		// str_ch_indine_avail = "1";
		//
		// } else {
		// str_ch_indine_avail = "";
		// str_ch_indine_avail = "0";
		// }
		//
		// }
		// });

		txv_choose_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag_icon = true;
				selectImage();

			}
		});
		txv_choose_banner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag_icon = false;
				selectImage();
			}
		});
		sp_mto.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				str_mto = sp_mto.getSelectedItemPosition()+"";
				System.out.println("str_mto" + str_mto);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		// ****************changes
		rg_last_minute_booking
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {

						case R.id.rb_last_minute_booking_yes:
							// do operations specific to this selection
							// checkbox_saved_add.setClickable(true);
							str_rb_last_minute_booking = "1";
							break;

						case R.id.rb_last_minute_booking_no:
							str_rb_last_minute_booking = "0";
							// do operations specific to this selection
							break;

						}

					}
				});
		ch_launch_monday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_monday = "1";

				} else {
					str_ch_launch_monday = "0";
				}

			}
		});
		ch_launch_tuesday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_tuesday = "1";

				} else {
					str_ch_launch_tuesday = "0";
				}

			}
		});
		ch_launch_wednesday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_wednesday = "1";

				} else {
					str_ch_launch_wednesday = "0";
				}

			}
		});
		ch_launch_thursday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_thursday = "1";

				} else {
					str_ch_launch_thursday = "0";
				}

			}
		});
		ch_launch_friday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_friday = "1";

				} else {
					str_ch_launch_friday = "0";
				}

			}
		});
		ch_launch_saturday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_saturday = "1";

				} else {
					str_ch_launch_saturday = "0";
				}

			}
		});
		ch_launch_sunday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_launch_sunday = "1";

				} else {
					str_ch_launch_sunday = "0";
				}

			}
		});

		// dinner chackbox
		ch_dinner_monday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_monday = "1";

				} else {
					str_ch_dinner_monday = "0";
				}

			}
		});
		ch_dinner_tuesday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_tuesday = "1";

				} else {
					str_ch_dinner_tuesday = "0";
				}

			}
		});
		ch_dinner_wednesday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_wednesday = "1";

				} else {
					str_ch_dinner_wednesday = "0";
				}

			}
		});
		ch_dinner_thursday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_thursday = "1";

				} else {
					str_ch_dinner_thursday = "0";
				}

			}
		});
		ch_dinner_friday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_friday = "1";

				} else {
					str_ch_dinner_friday = "0";
				}

			}
		});
		ch_dinner_saturday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_saturday = "1";

				} else {
					str_ch_dinner_saturday = "0";
				}

			}
		});
		ch_dinner_sunday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_ch_dinner_sunday = "1";

				} else {
					str_ch_dinner_sunday = "0";
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_step2_, menu);
		return true;
	}

	private void initialization() {

		rf_regi_step2_tv_step = (TextView) findViewById(R.id.rf_regi_step2_tv_step);
		rf_regi_step2_tv_dont = (TextView) findViewById(R.id.rf_regi_step2_tv_dont);
		// rf_regi_step2_tv_load = (ImageView)
		// findViewById(R.id.rf_regi_step2_tv_load);
		ch_launch_monday = (CheckBox) findViewById(R.id.ch_launch_monday);
		ch_launch_tuesday = (CheckBox) findViewById(R.id.ch_launch_tuesday);
		ch_launch_wednesday = (CheckBox) findViewById(R.id.ch_launch_wednesday);
		ch_launch_thursday = (CheckBox) findViewById(R.id.ch_launch_thursday);
		ch_launch_friday = (CheckBox) findViewById(R.id.ch_launch_friday);
		ch_launch_saturday = (CheckBox) findViewById(R.id.ch_launch_saturday);
		ch_launch_sunday = (CheckBox) findViewById(R.id.ch_launch_sunday);
		sp_launch_opening_time = (Spinner) findViewById(R.id.sp_launch_opening_time);
		sp_launch_last_booking = (Spinner) findViewById(R.id.sp_launch_last_booking);
		ch_dinner_monday = (CheckBox) findViewById(R.id.ch_dinner_monday);
		ch_dinner_tuesday = (CheckBox) findViewById(R.id.ch_dinner_tuesday);
		ch_dinner_wednesday = (CheckBox) findViewById(R.id.ch_dinner_wednesday);
		ch_dinner_thursday = (CheckBox) findViewById(R.id.ch_dinner_thursday);
		ch_dinner_friday = (CheckBox) findViewById(R.id.ch_dinner_friday);
		ch_dinner_saturday = (CheckBox) findViewById(R.id.ch_dinner_saturday);
		ch_dinner_sunday = (CheckBox) findViewById(R.id.ch_dinner_sunday);
		sp_dinner_opening_time = (Spinner) findViewById(R.id.sp_dinner_opening_time);
		sp_dinner_last_booking = (Spinner) findViewById(R.id.sp_dinner_last_booking);
		ed_restaurant_seat = (EditText) findViewById(R.id.ed_restaurant_seat);
		ed_average_bill = (EditText) findViewById(R.id.ed_average_bill);
		rg_last_minute_booking = (RadioGroup) findViewById(R.id.rg_last_minute_booking);
		rb_last_minute_booking_yes = (RadioButton) findViewById(R.id.rb_last_minute_booking_yes);
		rb_last_minute_booking_no = (RadioButton) findViewById(R.id.rb_last_minute_booking_no);
		ed_add_comments = (EditText) findViewById(R.id.ed_add_comments);
		btn_continue = (Button) findViewById(R.id.btn_continue);
		// *********changes
		ch_pickup = (CheckBox) findViewById(R.id.ch_pickup);
		ch_delivery = (CheckBox) findViewById(R.id.ch_delivery);
		// ch_indine = (CheckBox) findViewById(R.id.ch_indine);
		// image icon**********
		txv_choose_icon = (TextView) findViewById(R.id.txv_choose_icon);
		txv_choose_iconname = (TextView) findViewById(R.id.txv_choose_iconname);
		txv_choose_banner = (TextView) findViewById(R.id.txv_choose_banner);
		txv_choose_bannername = (TextView) findViewById(R.id.txv_choose_bannername);
		img_upload_icon = (ImageView) findViewById(R.id.img_upload_icon);
		img_upload_banner = (ImageView) findViewById(R.id.img_upload_banner);
		sp_mto = (Spinner) findViewById(R.id.sp_mto);
	}

	public class async_regi_step2 extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_restaurant_schedule;
		JSONObject obj_restaurant_app;
		JSONObject obj_restaurant_app_launch;
		JSONObject obj_restaurant_app_dinner;
		JSONObject obj_restaurantregistrationstep2;
		JSONObject obj_MainRequest;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(Registration_step2_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			obj_restaurant_schedule = new JSONObject();
			obj_restaurant_app = new JSONObject();
			obj_restaurant_app_launch = new JSONObject();
			obj_restaurant_app_dinner = new JSONObject();
			obj_restaurantregistrationstep2 = new JSONObject();
			obj_MainRequest = new JSONObject();

			try {
				// obj_restaurant_app_launch*********
				obj_restaurant_app_launch.put("monday", str_ch_launch_monday);
				obj_restaurant_app_launch.put("tuesday", str_ch_launch_tuesday);
				obj_restaurant_app_launch.put("wednesday",
						str_ch_launch_wednesday);
				obj_restaurant_app_launch.put("thursday",
						str_ch_launch_thursday);
				obj_restaurant_app_launch.put("friday", str_ch_launch_friday);
				obj_restaurant_app_launch.put("saturday",
						str_ch_launch_saturday);
				obj_restaurant_app_launch.put("sunday", str_ch_launch_sunday);
				obj_restaurant_app_launch.put("open_time",
						sp_launch_opening_time.getSelectedItem().toString());
				obj_restaurant_app_launch.put("last_booking_time_general",
						sp_launch_last_booking.getSelectedItem().toString());

				System.out.println("1111obj_restaurant_app_launch"
						+ obj_restaurant_app_launch);
				// obj_restaurant_app_launch*********

				// obj_restaurant_app_dinner*********
				obj_restaurant_app_dinner.put("monday", str_ch_dinner_monday);
				obj_restaurant_app_dinner.put("tuesday", str_ch_dinner_tuesday);
				obj_restaurant_app_dinner.put("wednesday",
						str_ch_dinner_wednesday);
				obj_restaurant_app_dinner.put("thursday",
						str_ch_dinner_thursday);
				obj_restaurant_app_dinner.put("friday", str_ch_dinner_friday);
				obj_restaurant_app_dinner.put("saturday",
						str_ch_dinner_saturday);
				obj_restaurant_app_dinner.put("sunday", str_ch_dinner_sunday);
				obj_restaurant_app_dinner.put("open_time",
						sp_dinner_opening_time.getSelectedItem().toString());
				obj_restaurant_app_dinner.put("last_booking_time_general",
						sp_dinner_last_booking.getSelectedItem().toString());

				System.out.println("1111obj_restaurant_app_dinner"
						+ obj_restaurant_app_dinner);
				// obj_restaurant_app_dinner*********

				// obj_restaurant_schedule********
				obj_restaurant_schedule.put("lunch", obj_restaurant_app_launch);
				obj_restaurant_schedule
						.put("dinner", obj_restaurant_app_dinner);

				System.out.println("1111obj_restaurant_schedule"
						+ obj_restaurant_schedule);
				// obj_restaurant_schedule********

				// obj_restaurant_app********
				obj_restaurant_app.put("restaurant_seat", ed_restaurant_seat
						.getText().toString());
				obj_restaurant_app.put("average_bill", ed_average_bill
						.getText().toString());
				obj_restaurant_app.put("accept_last_minute_booking",
						str_rb_last_minute_booking);
				// obj_restaurant_app.put("order_facility", str_rb_facility);s
				obj_restaurant_app.put("pickupavail", str_ch_pickupavail);
				obj_restaurant_app.put("delivery_avail", str_ch_delivery_avail);
				// obj_restaurant_app.put("indine_avail", str_ch_indine_avail);
				obj_restaurant_app.put("indine_avail", "0");
				obj_restaurant_app.put("featured_icon", str_icon_path);
				obj_restaurant_app.put("featured_image", str_banner_path);
				obj_restaurant_app.put("mto", sp_mto.getSelectedItemPosition()+"");

				System.out.println("1111obj_restaurant_app"
						+ obj_restaurant_app);
				// obj_restaurant_app********

				// obj_restaurantregistrationstep2***************

				obj_restaurantregistrationstep2.put("restaurant_schedule",
						obj_restaurant_schedule);
				obj_restaurantregistrationstep2.put("restaurant_app",
						obj_restaurant_app);

				System.out.println("1111obj_restaurantregistrationstep2"
						+ obj_restaurantregistrationstep2);
				// obj_restaurantregistrationstep2***************
				// obj_MainRequest*******************************

				System.out
						.println("1111Global_variable.restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);
				System.out.println("1111step2sessid" + Global_variable.sessid);
				obj_MainRequest.put("restaurantregistrationstep1",
						Global_variable.restaurantregistrationstep1);
				obj_MainRequest.put("restaurantregistrationstep2",
						obj_restaurantregistrationstep2);
				obj_MainRequest.put("sessid", Global_variable.sessid);
				obj_MainRequest.put("comment", ed_add_comments.getText()
						.toString());

				System.out.println("1111obj_MainRequest" + obj_MainRequest);

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
				try {
					HttpConnection con = new HttpConnection();
					String str_response = con.connection_rest_reg(
							Registration_step2_Activity.this,
							Global_variable.rf_api_registrationstep2,
							obj_MainRequest);

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
				jsonSuccessStr = json.getString("success");
				Global_variable.sessid = null;
				Global_variable.sessid = json.getString("sessid");
				System.out.println("1111sessidstep2respo"
						+ Global_variable.sessid);

				if (jsonSuccessStr.equalsIgnoreCase("true")) {
					Global_variable.comment = json.getString("comment");
					System.out.println("1111step2comments"
							+ Global_variable.comment);

					JSONObject Data = json.getJSONObject("data");
					System.out.println("1111obj_Data" + Data);
					if (Data != null) {
						Global_variable.restaurantregistrationstep1 = Data
								.getJSONObject("restaurantregistrationstep1");
						System.out.println("1111restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);
						Global_variable.restaurantregistrationstep2 = Data
								.getJSONObject("restaurantregistrationstep2");
						System.out.println("1111restaurantregistrationstep2"
								+ Global_variable.restaurantregistrationstep2);

						RegistrationTablayout.tab.setCurrentTab(2);
						RegistrationTablayout.tab.getTabWidget().getChildAt(2)
								.setClickable(true);
						// Registration_step3_Activity.rf_regi_step3_ed_comment.setText("");
						// Registration_step4_Activity.ed_step4_comments.setText("");
						Registration_step3_Activity.rf_regi_step3_ed_comment
								.setText(Global_variable.comment);
						Registration_step4_Activity.ed_step4_comments
								.setText(Global_variable.comment);
					}
				} else {
					JSONObject Error = json.getJSONObject("errors");
					System.out.println("1111errors" + Error);
					if (Error != null) {
						if (Error.has("restaurant_seat")) {
							JSONArray restaurant_seat = Error
									.getJSONArray("restaurant_seat");
							System.out.println("1111restaurant_seat"
									+ restaurant_seat);
							if (restaurant_seat != null) {
								String str_restaurant_seat = restaurant_seat
										.getString(0);
								System.out.println("1111str_FirstName"
										+ str_restaurant_seat);
								Toast.makeText(
										Registration_step2_Activity.this,
										str_restaurant_seat, Toast.LENGTH_LONG)
										.show();
							}

						}
						if (Error.has("average_bill")) {
							JSONArray average_bill = Error
									.getJSONArray("average_bill");
							System.out.println("1111average_bill"
									+ average_bill);
							if (average_bill != null) {
								String str_average_bill = average_bill
										.getString(0);
								System.out.println("1111str_FirstName"
										+ str_average_bill);
								Toast.makeText(
										Registration_step2_Activity.this,
										str_average_bill, Toast.LENGTH_LONG)
										.show();
							}

						}
						if (Error.has("featured_image")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("featured_image").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("featured_icon")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("featured_icon").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

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
			System.out.println("1111success" + jsonSuccessStr);
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

			RegistrationTablayout.tab.setCurrentTab(0);
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

	// changes ********************
	private void selectImage() {
		final CharSequence[] options = {
				getResources().getString(R.string.str_take_photo),
				getResources().getString(R.string.str_choose_from),
				getResources().getString(R.string.csm_cancel)};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				Registration_step2_Activity.this);
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
		super.onActivityResult(requestCode, resultCode, data);
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

		if (flag_icon == true) {
			img_upload_icon.setImageBitmap(bitmap);
		} else {
			img_upload_banner.setImageBitmap(bitmap);
		}

		new UploadFileToServer().execute();
	}

	private class UploadFileToServer extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			System.out.println("uploadfiletoserver===");
			// setting progress bar to zero
			p = new ProgressDialog(Registration_step2_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("amagyudoingma");

			if (SELECT.equalsIgnoreCase("CAMERA")) {
			 uploadFile(fileUri.getPath());
			} else {
				 uploadFile(picturePath);
			}
			return null;
		}

		protected void onPostExecute(String result_string) {
			
			 super.onPostExecute(result_string);
			 p.dismiss();
				System.out.println("!!!!result" + result);
				if (flag_icon == true) {
					str_icon_path = result;
					System.out.println("str_icon_path" + str_icon_path);
				} else {
					str_banner_path = result;
					System.out.println("str_banner_path" + str_banner_path);
				}
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
		/*dialog = ProgressDialog.show(Registration_step2_Activity.this, "",
				getString(R.string.str_Uploading), true);*/

		String upLoadServerUri = Global_variable.rf_api_upload_image;
		// String upLoadServerUri =
		// "http://192.168.1.17/RF_admin_api/admin_api_dev/manage_restaurant_gallery";
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
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(upLoadServerUri);
			conn = (HttpURLConnection) url.openConnection(); // Open a HTTP
																// connection to
																// the URL
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
			System.out.println("!!!!url" + url);
			System.out.println("!!!!filename" + fileName);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
					+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available(); // create a buffer of
															// maximum size

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
							+ serverResponseMessage + ": " + serverResponseCode);
			if (serverResponseCode == 200) {
				runOnUiThread(new Runnable() {
					public void run() {
						// tv.setText("File Upload Completed.");
						// Toast.makeText(Registration_step2_Activity.this,
						// getString(R.string.str_Upload_Complete),
						// Toast.LENGTH_SHORT).show();
						result = "";
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

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				result += line;
			}

			br.close();
			
		
			if (result
					.equalsIgnoreCase(getString(R.string.str_Please_Try_again))) {
				// Toast some error
				// Toast.makeText(Registration_step2_Activity.this, "" + result,
				// Toast.LENGTH_LONG).show();
			} else {
				System.out.println("final uploaded URL" + result);
				// Toast.makeText(Registration_step2_Activity.this, "" + result,
				// Toast.LENGTH_LONG).show();
			}
			
			
		} catch (MalformedURLException ex) {
			//dialog.dismiss();
			ex.printStackTrace();
			// Toast.makeText(Registration_step2_Activity.this,
			// getString(R.string.str_MalformedURLException),
			// Toast.LENGTH_SHORT).show();
			Log.e(getString(R.string.str_Upload_file_to),
					getString(R.string.str_error) + ex.getMessage(), ex);
		} catch (Exception e) {
			//dialog.dismiss();
			e.printStackTrace();
			// Toast.makeText(Registration_step2_Activity.this,
			// getString(R.string.str_Exception) + e.getMessage(),
			// Toast.LENGTH_SHORT).show();
			Log.e(getString(R.string.str_Upload_file_to_server),
					getString(R.string.str_Exception) + e.getMessage(), e);
		}
		//dialog.dismiss();
		return serverResponseCode;
			} else {
			//	dialog.dismiss();
				// Toast.makeText(getApplicationContext(),
				// "pankaj sakariya else part"+sourceFileUri+"file name",
				// Toast.LENGTH_LONG).show();
				return serverResponseCode = 0;
			}
		} catch (Exception e) {
			//dialog.dismiss();
			e.printStackTrace();
			// Toast.makeText(getApplicationContext(), "pankaj sakariya catch",
			// Toast.LENGTH_LONG).show();
			return serverResponseCode = 0;
		}
	}
}
