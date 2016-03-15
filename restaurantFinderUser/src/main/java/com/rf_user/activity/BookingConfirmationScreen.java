package com.rf_user.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class BookingConfirmationScreen extends Activity {

	/* Project resources declaration */
	RadioGroup rf_booking_rg_confirmation_radio;
	RadioButton rf_booking_rd_confirmation_redio_male,
			rf_booking_rd_confirmation_redio_female;
	EditText rf_booking_first_name, rf_booking_last_name,
			rf_registrationi_email_id, rf_booking_phone_number_box,
			rf_booking_comment_text_box;
	TextView rf_booking_plus_box, rf_booking_people_box, rf_booking_minus_box,
			rf_booking_time_box, rf_booking_date_box, rf_booking_date_header,
			rf_booking_time_header, rf_booking_number_of_people_header,
			rf_booking_restaurant_name, rf_booking_loyalty_notes;
	ImageView rf_booking_submit_button, rf_booking_time_icon,
			rf_booking_date_icon;
	boolean loyalty_flag = false;

	EditText rf_booking_loyalty_pts_value_;
	// Spinner rf_booking_sp_country_code;
	TextView rf_booking_sp_country_code;

	/* Internet connection */
	ConnectionDetector cd;

	/* String declaration */

	String str_loyalty, str_offer_id, output;
	String TAG_SUCCESS = "success";
	TextView rf_booking_user_loyalty_pts;

	java.sql.Date currDate;

	/* Declaration for http call */
	HttpConnection http = new HttpConnection();

	/* For calender and time view */
	int year, month, day, hour, minutes;
	Calendar c;

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.booking_confirmation_scroolview_contents);

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			/* create Object of internet connection* */
			cd = new ConnectionDetector(getApplicationContext());

			currDate = new java.sql.Date(System.currentTimeMillis());

			getIntialCalender();

			initializeWidget();

			setdata();

			setonclickListener();

//			loadLocale();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/* Language conversion methods */
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

	private void setdata() {
		// TODO Auto-generated method stub
		// System.out.println("!!!!!!!!!!!!!!!!"+Global_variable.hotel_name);
		// if(Global_variable.hotel_name.length()!=0)
		// {
		// rf_booking_restaurant_name.setText(Global_variable.hotel_name);
		// }
		//
		try {

			if (Global_variable.str_booking_date != null) {

				System.out.println("In date not null"
						+ Global_variable.str_booking_date);

				// rf_booking_date_header.setText(Global_variable.str_booking_date);

				rf_booking_date_box.setText(Global_variable.str_booking_date);

			} else {

				System.out.println("In date  null"
						+ Global_variable.str_booking_date);

			}
			if (Global_variable.str_booking_time != null) {

				System.out.println("In time not null"
						+ Global_variable.str_booking_time);

				// rf_booking_time_header.setText(Global_variable.str_booking_time);

				rf_booking_time_box.setText(Global_variable.str_booking_time);

			} else {

				System.out.println("In time null"
						+ Global_variable.str_booking_time);

			}
			if (Global_variable.str_booking_number_of_people != null) {

				System.out.println("In no of people not null"
						+ Global_variable.str_booking_number_of_people);

				rf_booking_people_box
						.setText(Global_variable.str_booking_number_of_people);

				// rf_booking_number_of_people_header.setText(Global_variable.str_booking_number_of_people
				// + " Per.");
			} else {

				System.out.println("In no of people  null");

			}

			if (Global_variable.str_User_FirstName != null) {
				System.out.println("In firstname not null"
						+ Global_variable.str_User_FirstName);

				rf_booking_first_name
						.setText(Global_variable.str_User_FirstName);
			} else {
				System.out.println("In firstname null");
			}

			if (Global_variable.str_User_LastName != null) {
				System.out.println("In Lastname not null"
						+ Global_variable.str_User_LastName);

				rf_booking_last_name.setText(Global_variable.str_User_LastName);
			} else {
				System.out.println("In firstname null");
			}

			if (Global_variable.str_User_Email != null) {
				System.out.println("In email not null");

				rf_registrationi_email_id
						.setText(Global_variable.str_User_Email);
			} else {
				System.out.println("In email null");
			}

			if (Global_variable.str_User_ContactNumber != null) {

				System.out.println("In mobile not null");

				rf_booking_phone_number_box
						.setText(Global_variable.str_User_ContactNumber);
			} else {
				System.out.println("In mobile null");
			}

			if (Global_variable.country_code != null) {
				rf_booking_sp_country_code.setText("+"
						+ Global_variable.country_code);
			} else {
				rf_booking_sp_country_code.setText("+" + "40");
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		// if(Global_variable.country_code!=null)
		// {
		//
		//
		// rf_booking_first_name.setText("+"+Global_variable.country_code);
		// }
		// else
		// {
		//
		// }

	}

	private void initializeWidget() {
		// TODO Auto-generated method stub

		try {

			// ScrollView scrollable_contents = (ScrollView)
			// findViewById(R.id.rf_booking_confirmation_scrollview);
			// getLayoutInflater().inflate(
			// R.layout.booking_confirmation_scroolview_contents,
			// scrollable_contents);

			// rf_booking_rg_confirmation_redio = (RadioGroup)
			// findViewById(R.id.rf_booking_rg_confirmation_redio);
			// rf_booking_rd_confirmation_redio_male = (RadioButton)
			// findViewById(R.id.rf_booking_rd_confirmation_redio_male);
			// rf_booking_rd_confirmation_redio_female = (RadioButton)
			// findViewById(R.id.rf_booking_rd_confirmation_redio_female);
			rf_booking_first_name = (EditText) findViewById(R.id.rf_booking_first_name);
			rf_booking_last_name = (EditText) findViewById(R.id.rf_booking_last_name);
			rf_registrationi_email_id = (EditText) findViewById(R.id.rf_registrationi_email_id);
			rf_booking_phone_number_box = (EditText) findViewById(R.id.rf_booking_phone_number_box);
			rf_booking_comment_text_box = (EditText) findViewById(R.id.rf_booking_comment_text_box);
			rf_booking_time_box = (TextView) findViewById(R.id.rf_booking_time_box);
			rf_booking_date_box = (TextView) findViewById(R.id.rf_booking_date_box);
			rf_booking_plus_box = (TextView) findViewById(R.id.rf_booking_plus_box);
			rf_booking_people_box = (TextView) findViewById(R.id.rf_booking_no_people_box);
			rf_booking_minus_box = (TextView) findViewById(R.id.rf_booking_minus);
			rf_booking_loyalty_notes = (TextView) findViewById(R.id.rf_booking_loyalty_notes);
			
			rf_booking_user_loyalty_pts=(TextView)findViewById(R.id.rf_booking_user_loyalty_pts);
			
			if (SharedPreference
					.get_user_loyalty_pts(getApplicationContext()) != "") {
				if (SharedPreference.get_user_loyalty_pts(
						getApplicationContext())
						.length() != 0) {
		
					System.out
					.println("!!!!!!!!!!!!!!!!!!!!!!!!user_loyalty_pts fetch_after_setting"
							+ SharedPreference
									.get_user_loyalty_pts(getApplicationContext()));

					rf_booking_user_loyalty_pts
							.setText(SharedPreference
									.get_user_loyalty_pts(getApplicationContext()));

					
				}
			} else {
				
				rf_booking_user_loyalty_pts
				.setText("0");

			}
			
			if (Global_variable.lp_to_tg_customer != 0) {
				rf_booking_loyalty_notes
						.setText(getResources().getString(R.string.bc_notes) +" "+ Global_variable.lp_to_tg_customer
								+" "+ getResources().getString(R.string.bc_points));
			} else {

			}

			rf_booking_loyalty_pts_value_ = (EditText) findViewById(R.id.rf_booking_loyalty_pts_value_);

			// if
			// (SharedPreference.get_user_loyalty_pts(getApplicationContext())
			// != 0) {
			// rf_booking_loyalty_pts_value_.setText(SharedPreference
			// .get_user_loyalty_pts(getApplicationContext()));
			// } else {
			// rf_booking_loyalty_pts_value_.setText("0");
			// }

			rf_booking_date_icon = (ImageView) findViewById(R.id.rf_booking_date_icon);
			rf_booking_time_icon = (ImageView) findViewById(R.id.rf_booking_time_icon);

			rf_booking_submit_button = (ImageView) findViewById(R.id.rf_booking_submit_button);
			// rf_booking_sp_country_code = (Spinner)
			// findViewById(R.id.rf_booking_sp_country_code);
			rf_booking_sp_country_code = (TextView) findViewById(R.id.rf_booking_sp_country_code);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void getIntialCalender() {
		// TODO Auto-generated method stub

		try {

			c = Calendar.getInstance();
			String[] fields = Global_variable.str_booking_date.split("[-]");

			String str_year = fields[0];
			String str_month = fields[1];
			String str_day = fields[2];

			System.out.println("!!!!" + fields[0]);
			System.out.println("!!!!" + fields[1]);
			System.out.println("!!!!!" + fields[2]);

			if (str_year.length() != 0) {
				year = Integer.parseInt(str_year);
			} else {
				year = c.get(Calendar.YEAR);
			}

			if (str_month.length() != 0) {
				month = Integer.parseInt(str_month) - 1;
			} else {
				month = c.get(Calendar.MONTH);
			}

			if (str_year.length() != 0) {
				day = Integer.parseInt(str_day);
			} else {
				day = c.get(Calendar.DAY_OF_MONTH);
			}

			String[] fields1 = Global_variable.str_booking_time.split("[:]");

			String str_hour = fields1[0];
			String str_minutes = fields1[1];

			System.out.println("!!!!!" + fields1[0]);
			System.out.println("!!!!!" + fields1[1]);

			if (str_hour.length() != 0) {
				hour = Integer.parseInt(str_hour);
			} else {
				hour = c.get(Calendar.HOUR_OF_DAY);
			}

			if (str_minutes.length() != 0) {
				minutes = Integer.parseInt(str_minutes);
			} else {
				minutes = c.get(Calendar.MINUTE);
			}

			currDate = new java.sql.Date(System.currentTimeMillis());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date()); // Now use today date.
			c1.add(Calendar.DATE, 30); // Adding 30 days
			output = sdf.format(c1.getTime());
			System.out.println("!!!!!!!!!!!!!!!" + output);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void setonclickListener() {
		// TODO Auto-generated method stub

		try {

			// rf_booking_loyalty_pts_value_
			// .addTextChangedListener(new TextWatcher() {
			//
			// @Override
			// public void onTextChanged(CharSequence s, int start,
			// int before, int count) {
			// // TODO Auto-generated method stub
			//
			// System.out.println("!!!!!!!!!!!!!!!!!!!Shikha");
			//
			// try {
			// if (SharedPreference
			// .get_user_loyalty_pts(getApplicationContext()) != "") {
			// System.out
			// .println("!!!!!!!!!!!!!!!!!!!Shikha2");
			// int points = Integer
			// .parseInt(rf_booking_loyalty_pts_value_
			// .getText().toString());
			//
			// if (Integer.parseInt(SharedPreference
			// .get_user_loyalty_pts(getApplicationContext())) >=
			// Global_variable.min_lp_to_redeem) {
			// if (Integer.parseInt(SharedPreference
			// .get_user_loyalty_pts(getApplicationContext())) <=
			// Global_variable.max_lp_to_redeem) {
			// if (points <= Integer.parseInt(SharedPreference
			// .get_user_loyalty_pts(getApplicationContext()))) {
			// loyalty_flag = true;
			// } else {
			// loyalty_flag = false;
			// Toast.makeText(
			// getApplicationContext(),
			// getString(R.string.str_You_dont),
			// Toast.LENGTH_SHORT)
			// .show();
			// }
			// } else {
			// loyalty_flag = false;
			// Toast.makeText(
			// getApplicationContext(),
			// getString(R.string.str_Your_Loyalty)
			// + Global_variable.max_lp_to_redeem,
			// Toast.LENGTH_SHORT).show();
			// }
			// } else {
			// loyalty_flag = false;
			// Toast.makeText(
			// getApplicationContext(),
			// getString(R.string.str_less_than)
			// + Global_variable.min_lp_to_redeem,
			// Toast.LENGTH_SHORT).show();
			// }
			// } else {
			// loyalty_flag = false;
			// Toast.makeText(getApplicationContext(),
			// getString(R.string.str_You_dont),
			// Toast.LENGTH_SHORT).show();
			// }
			//
			//
			//
			// } catch (NumberFormatException e) {
			// e.printStackTrace();
			// }
			//
			// }
			//
			// @Override
			// public void beforeTextChanged(CharSequence s,
			// int start, int count, int after) {
			// // TODO Auto-generated method stub
			//
			// }
			//
			// @Override
			// public void afterTextChanged(Editable s) {
			// // TODO Auto-generated method stub
			//
			// }
			// });

			rf_booking_submit_button
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							runOnUiThread(new Runnable() {
								public void run() {

									/** check Internet Connectivity */
									if (cd.isConnectingToInternet()) {
										if (SharedPreference
												.getuser_id(getApplicationContext()) != "") {
											if (SharedPreference.getuser_id(
													getApplicationContext())
													.length() != 0) {

												if (rf_booking_loyalty_pts_value_
														.getText().toString()
														.equalsIgnoreCase("0")
														|| rf_booking_loyalty_pts_value_
																.getText()
																.toString()
																.equalsIgnoreCase(
																		"")) {
													new GetValidOrderDateTime()
															.execute();
												} else {

													try {
														if (SharedPreference
																.get_user_loyalty_pts(getApplicationContext()) != "") {
															System.out.println("!!!!!!!!!!!!!!!!!!!Shikha2"
																	+ SharedPreference
																			.get_user_loyalty_pts(getApplicationContext()));

															if (!Pattern
																	.matches(
																			"^[0-9 ]*$",
																			rf_booking_loyalty_pts_value_
																					.getText()
																					.toString()))

															{
																
																Toast.makeText(getApplicationContext(), getString(R.string.numeric_validation_error), Toast.LENGTH_SHORT).show();
																
//																rf_booking_loyalty_pts_value_.setError(Html.fromHtml("<font color='#ff0000'>"+getString(R.string.numeric_validation_error)+</font> "));
//																rf_booking_loyalty_pts_value_.requestFocus();
//																rf_booking_loyalty_pts_value_
//																		.setError(getString(R.string.numeric_validation_error));

															} else {
																int points = Integer
																		.parseInt(rf_booking_loyalty_pts_value_
																				.getText()
																				.toString());

																System.out
																		.println("!!!!!!!!!!!!!!!!!!!!!!!!!Global_variable.min_lp_to_redeem"
																				+ Global_variable.min_lp_to_redeem);

																System.out
																		.println("!!!!!!!!!!!!!!!!!!!!!!!!!Global_variable.max_lp_to_redeem"
																				+ Global_variable.max_lp_to_redeem);

																if (Integer
																		.parseInt(SharedPreference
																				.get_user_loyalty_pts(getApplicationContext())) >= Global_variable.min_lp_to_redeem
																		&& points <= Integer
																				.parseInt(SharedPreference
																						.get_user_loyalty_pts(getApplicationContext()))) {

																	System.out
																			.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!one");

																	if (points <= Global_variable.max_lp_to_redeem) {

																		System.out
																				.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!two");

																		if (points <= Integer
																				.parseInt(SharedPreference
																						.get_user_loyalty_pts(getApplicationContext()))) {

																			System.out
																					.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!three");

																			loyalty_flag = true;

																			new GetValidOrderDateTime()
																					.execute();

																		} else {
																			loyalty_flag = false;

																			System.out
																					.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!four");

																			Toast.makeText(
																					getApplicationContext(),
																					getString(R.string.str_You_dont),
																					Toast.LENGTH_SHORT)
																					.show();
																		}
																	} else {
																		loyalty_flag = false;

																		System.out
																				.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!five");

																		Toast.makeText(
																				getApplicationContext(),
																				getString(R.string.str_Your_Loyalty)
																						+ Global_variable.max_lp_to_redeem,
																				Toast.LENGTH_SHORT)
																				.show();
																	}
																} else {
																	loyalty_flag = false;

																	System.out
																			.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!six");

																	Toast.makeText(
																			getApplicationContext(),
																			getString(R.string.str_less_than)
																					+ Global_variable.min_lp_to_redeem,
																			Toast.LENGTH_SHORT)
																			.show();
																}
															}

														} else {
															loyalty_flag = false;

															System.out
																	.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!seven");

															Toast.makeText(
																	getApplicationContext(),
																	getString(R.string.str_You_dont),
																	Toast.LENGTH_SHORT)
																	.show();
														}

													} catch (NumberFormatException e) {
														e.printStackTrace();
													}

												}

												// }
												//

											}
										} else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();
										}

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

								}
							});
						}
					});

			rf_booking_date_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getCalenderView();
				}
			});

			rf_booking_time_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getTimeView();
				}
			});

			rf_booking_plus_box.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					int val = Integer.parseInt(rf_booking_people_box.getText()
							.toString());

					rf_booking_people_box.setText(String.valueOf(val + 1));

					Global_variable.str_booking_number_of_people = String
							.valueOf(val + 1);
					Booking_Screen_TabLayout.rf_booking_number_of_people_header.setText(Global_variable.str_booking_number_of_people);

				}
			});

			rf_booking_minus_box.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					int val = Integer.parseInt(rf_booking_people_box.getText()
							.toString());
					if (val <= 1) {
						rf_booking_people_box.setText("1");
						Global_variable.str_booking_number_of_people = "1";
						Booking_Screen_TabLayout.rf_booking_number_of_people_header.setText(Global_variable.str_booking_number_of_people);
					} else {
						rf_booking_people_box.setText(String.valueOf(val - 1));
						Global_variable.str_booking_number_of_people = String
								.valueOf(val - 1);
						Booking_Screen_TabLayout.rf_booking_number_of_people_header.setText(Global_variable.str_booking_number_of_people);
					}

				}
			});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	@SuppressLint("SimpleDateFormat")
	private void getCalenderView() {
		// TODO Auto-generated method stub

		try {

			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view,
								int selectedyear, int monthOfYear,
								int dayOfMonth) {
							// TODO Auto-generated method stub

							year = selectedyear;
							month = monthOfYear;
							day = dayOfMonth;
							try {

								Date date = new SimpleDateFormat("yyyy-MM-dd")
										.parse(year + "-" + (month + 1) + "-"
												+ day);

								DateFormat outputFormatter = new SimpleDateFormat(
										"yyyy-MM-dd");
								String selectedDate = outputFormatter
										.format(date); // Output : 01/20/2012

								System.out.println("!!!!!!selectedDate..."
										+ selectedDate);

								// Date currDate=new Date();

								System.out.println("!!!!!!!!!currDate."
										+ currDate);
								System.out
										.println("output.compareTo(currDate).."
												+ selectedDate
														.compareTo(currDate
																.toString()));

								if (selectedDate.compareTo(currDate.toString()) >= 0
										&& selectedDate.compareTo(output
												.toString()) <= 0) {
									// then do your work
									// Display Selected date in textbox

									DateFormat outputFormatter1 = new SimpleDateFormat(
											"yyyy-MM-dd");
									String date_formating = outputFormatter1
											.format(date);

									System.out
											.println("!!!!!!!!!after_formating.."
													+ outputFormatter1
													+ "!!!!!" + date_formating);

									rf_booking_date_box.setText(selectedDate);
									Booking_Screen_TabLayout.rf_booking_date_header.setText(selectedDate);

									// rf_booking_date_box.setText(year + "-" +
									// (month + 1)
									// + "-" + day);

									Global_variable.str_Date = date_formating;

								} else {
									// show message

									Toast.makeText(
											getApplicationContext(),
											getString(R.string.str_invalid_date),
											Toast.LENGTH_SHORT).show();

								}

							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}, year, month, day);
			dpd.getDatePicker().setMinDate(c.getTimeInMillis());

			c.add(Calendar.MONTH, 1);

			dpd.getDatePicker().setMaxDate(c.getTimeInMillis());

			c.add(Calendar.MONTH, -1);

			System.out.println("!!!!!pankaj" + c.getTimeInMillis());
			// dpd.getDatePicker().setMaxDate(c.);
			dpd.show();
			dpd.setCancelable(false);
			dpd.setCanceledOnTouchOutside(false);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void getTimeView() {
		// TODO Auto-generated method stub

		try {

			// Launch Time Picker Dialog

			System.out.println("!!!!!in time picker" + hour);
			System.out.println("!!!!!in time picker" + minutes);

			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {

							if (rf_booking_date_box.getText().toString()
									.equalsIgnoreCase(currDate.toString())) {
								c = Calendar.getInstance();
								int curr_hour = c.get(Calendar.HOUR_OF_DAY);
								int curr_minutes = c.get(Calendar.MINUTE);

								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
												+ curr_hour);
								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
												+ curr_minutes);

								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!hourOfDay<curr_hour"
												+ (hourOfDay < curr_hour));
								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!minute<curr_minutes"
												+ (minute < curr_minutes));

								if (hourOfDay < curr_hour
										&& minute < curr_minutes
										|| hourOfDay < curr_hour
										&& minute <= curr_minutes
										|| hourOfDay == curr_hour
										&& minute < curr_minutes) {
									System.out
											.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in if");
									Toast.makeText(
											getApplicationContext(),
											getString(R.string.str_Please_choose_valid_time),
											Toast.LENGTH_SHORT).show();
								} else {

									minutes = minute;
									hour = hourOfDay;

									String time1 = hour + ":" + minutes;

									Date time;
									try {
										time = new SimpleDateFormat("HH:mm")
												.parse(hour + ":" + minutes);

										DateFormat outputFormatter = new SimpleDateFormat(
												"HH:mm");
										String final_time = outputFormatter
												.format(time);

										System.out
												.println("!!!!!!!!!!!!!!!!!final_time..."
														+ final_time);

										// Display Selected time in textbox
										rf_booking_time_box.setText(final_time);
										Booking_Screen_TabLayout.rf_booking_time_header.setText(final_time);

										// Display Selected time in textbox
										// rf_booking_time_box.setText(hourOfDay
										// + ":" +
										// minute);
										Global_variable.str_Time_From = final_time;
										Global_variable.str_Time_To = final_time;

									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

							} else {

								minutes = minute;
								hour = hourOfDay;

								String time1 = hour + ":" + minutes;

								Date time;
								try {
									time = new SimpleDateFormat("HH:mm")
											.parse(hour + ":" + minutes);

									DateFormat outputFormatter = new SimpleDateFormat(
											"HH:mm");
									String final_time = outputFormatter
											.format(time);

									System.out
											.println("!!!!!!!!!!!!!!!!!final_time..."
													+ final_time);

									// Display Selected time in textbox
									rf_booking_time_box.setText(final_time);
									Booking_Screen_TabLayout.rf_booking_time_header.setText(final_time);
									// Display Selected time in textbox
									// rf_booking_time_box.setText(hourOfDay +
									// ":" +
									// minute);
									Global_variable.str_Time_From = final_time;
									Global_variable.str_Time_To = final_time;

								} catch (java.text.ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}
					}, hour, minutes, false);
			tpd.show();
			tpd.setCancelable(false);
			tpd.setCanceledOnTouchOutside(false);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/* Fetch valid date and time from db for TG */

	public class GetValidOrderDateTime extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(BookingConfirmationScreen.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject OrderDateTime = new JSONObject();

						if (Global_variable.hotel_id != null) {
							OrderDateTime.put("rest_id",
									Global_variable.hotel_id);
						} else {
							OrderDateTime.put("rest_id", "");
						}
						System.out.println("hotel_id" + OrderDateTime);

						if (Global_variable.str_Date != null) {
							OrderDateTime.put("date", Global_variable.str_Date);
						} else {
							OrderDateTime.put("date", "");
						}
						System.out.println("date" + OrderDateTime);

						if (Global_variable.str_Time_From != null) {
							OrderDateTime.put("time",
									Global_variable.str_Time_From);
						} else {
							OrderDateTime.put("time", "");
						}
						System.out.println("time" + OrderDateTime);

						OrderDateTime.put("type", "TG");

						OrderDateTime.put("sessid", SharedPreference
								.getsessid(getApplicationContext()));
						System.out.println("session_id" + OrderDateTime);
						// *************

						String responseText = http
								.connection(BookingConfirmationScreen.this,
										 Global_variable.rf_GetValidOrderDateTime_api_path,
										OrderDateTime);

						try {

							System.out.println("after_connection.."
									+ responseText);

							json = new JSONObject(responseText);
							System.out.println("responseText" + json);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			});

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(Void result) {

			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONObject data = json.getJSONObject("data");
				// String Data_Success = data.getString(TAG_SUCCESS);
				// System.out.println("Data tag" + Data_Success);
				// ******** data succsess

				if (success1.equals("true")) {
					if (data.length() != 0) {

						String open_time = data.getString("open_time");
						String close_name = data.getString("close_time");

						new TableGrabOrder().execute();

					}

				}

				// **** invalid output
				else {
					if (success1.equalsIgnoreCase("false")) {
						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("rest_id")) {
							JSONArray Array_rest_id = Data_Error
									.getJSONArray("rest_id");
							System.out.println("Array_rest_id" + Array_rest_id);
							String Str_rest_id = Array_rest_id.getString(0);
							System.out.println("Str_rest_id" + Str_rest_id);
							if (Str_rest_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_rest_id, Toast.LENGTH_LONG).show();
							}
						}

						if (Data_Error.has("type")) {
							JSONArray Array_type = Data_Error
									.getJSONArray("type");
							System.out.println("Array_type" + Array_type);
							String Str_type = Array_type.getString(0);
							System.out.println("Str_type" + Str_type);
							if (Str_type != null) {
								Toast.makeText(getApplicationContext(),
										Str_type, Toast.LENGTH_LONG).show();
							}
						}

						if (Data_Error.has("date")) {
							JSONArray Array_date = Data_Error
									.getJSONArray("date");
							System.out.println("Array_date" + Array_date);
							String Str_date = Array_date.getString(0);
							System.out.println("Str_date" + Str_date);
							if (Str_date != null) {
								Toast.makeText(getApplicationContext(),
										Str_date, Toast.LENGTH_LONG).show();
							}
						}

						if (Data_Error.has("time")) {
							JSONArray Array_time = Data_Error
									.getJSONArray("time");
							System.out.println("Array_time" + Array_time);
							String Str_time = Array_time.getString(0);
							System.out.println("Str_time" + Str_time);
							if (Str_time != null) {
								Toast.makeText(getApplicationContext(),
										Str_time, Toast.LENGTH_LONG).show();
							}
						}

						if (Data_Error.has("sessid")) {
							JSONArray Array_sessid = Data_Error
									.getJSONArray("sessid");
							System.out.println("Array_sessid" + Array_sessid);
							String Str_sessid = Array_sessid.getString(0);
							System.out.println("Str_email" + Str_sessid);
							if (Str_sessid != null) {
								Toast.makeText(getApplicationContext(),
										Str_sessid, Toast.LENGTH_LONG).show();
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

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

		}

	}

	public class TableGrabOrder extends AsyncTask<String, String, String> {

		JSONObject json;
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new ProgressDialog(BookingConfirmationScreen.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
			
			
		}

		/**
		 * Getting user details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				String text = null;

				public void run() {

					// Check for success tag
					int success;
					try {
						JSONObject tg_order_obj = new JSONObject();

						System.out.println("!!!!!!!!!!!!!!!!!!"
								+ SharedPreference
										.getuser_id(getApplicationContext()));
						try {
							if (SharedPreference
									.getuser_id(getApplicationContext()) != "") {
								System.out
										.println("!!!!!!!!!!!!!!!!!!123"
												+ SharedPreference
														.getuser_id(getApplicationContext()));

								tg_order_obj.put("user_id", SharedPreference
										.getuser_id(getApplicationContext()));
							} else {
								System.out
										.println("!!!!!!!!!!!!!!!!!!12345"
												+ SharedPreference
														.getuser_id(getApplicationContext()));
								tg_order_obj.put("user_id", "");
							}
							if (Global_variable.hotel_id != null) {
								tg_order_obj.put("rest_id",
										Global_variable.hotel_id);
							} else {
								tg_order_obj.put("rest_id", "");
							}

							if (Global_variable.str_booking_number_of_people != null) {
								tg_order_obj
										.put("number_of_people",rf_booking_people_box.getText().toString());
							} else {
								tg_order_obj.put("number_of_people", "");
							}

							tg_order_obj.put("booking_mode", "1");
							if (Global_variable.str_booking_date != null) {
								tg_order_obj.put("booking_date",
										rf_booking_date_box.getText().toString());
							} else {
								tg_order_obj.put("booking_date", "");
							}
							if (Global_variable.str_booking_time != null) {
								tg_order_obj.put("booking_time",
										rf_booking_time_box.getText().toString());
							} else {
								tg_order_obj.put("booking_time", "");
							}

							if (str_offer_id != null) {
								tg_order_obj.put("offer_id", str_offer_id);
							} else {
								tg_order_obj.put("offer_id", "");
							}

							if (SharedPreference
									.get_user_loyalty_pts(getApplicationContext()) != "") {
								if (SharedPreference.get_user_loyalty_pts(
										getApplicationContext()).length() != 0) {
									tg_order_obj
											.put("user_loyalty_pts",
													SharedPreference
															.get_user_loyalty_pts(getApplicationContext()));
								} else {
									tg_order_obj.put("user_loyalty_pts", "0");
								}

							} else {
								tg_order_obj.put("user_loyalty_pts", "0");
							}

							if (loyalty_flag == true) {
								if (rf_booking_loyalty_pts_value_.getText()
										.toString() != null) {
									if (rf_booking_loyalty_pts_value_.getText()
											.toString().length() != 0) {
										tg_order_obj.put("loyalty",
												rf_booking_loyalty_pts_value_
														.getText().toString());
									} else {
										tg_order_obj.put("loyalty", "0");
									}
								}
							} else {
								tg_order_obj.put("loyalty", "0");
							}

							// if (str_loyalty != null) {
							// tg_order_obj.put("loyalty", str_loyalty);
							// } else {
							// tg_order_obj.put("loyalty", "");
							//
							// }

							if (rf_booking_comment_text_box.getText()
									.toString() != null) {
								tg_order_obj.put("comments",
										rf_booking_comment_text_box.getText()
												.toString());
							} else {
								tg_order_obj.put("comments", "");

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// JSONObject tg_order_datastreams = new JSONObject();
						// tg_order_datastreams.put("", tg_order_obj);
						// System.out.println("tg_order_datastreams"
						// + tg_order_datastreams);
						try {
							tg_order_obj.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));

							System.out.println("final_obj" + tg_order_obj);
							// *************
							// for request

							String responseText = http
									.connection(BookingConfirmationScreen.this,
											 Global_variable.rf_TableGrabOrder_api_path,
											tg_order_obj);

							json = new JSONObject(responseText);
							System.out.println("forget_last_json" + json);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			});
			//
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// json success tag

			try {
				String success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONObject data = json.getJSONObject("data");
				String Data_Success = data.getString("success");
				System.out.println("Data tag" + Data_Success);

				/* If success true */
				if (Data_Success.equalsIgnoreCase("true")) {
					String success_message = data.getString("message");

					// Toast.makeText(getApplicationContext(),
					// success_message, Toast.LENGTH_LONG).show();
					// Intent i = new
					// Intent(BookingConfirmationScreen.this,
					// InvitationScreen.class);

					Booking_Screen_TabLayout.tab.setCurrentTab(1);
					Booking_Screen_TabLayout.tab.getTabWidget().getChildAt(1)
							.setClickable(true);
					Booking_Screen_TabLayout.checkout_tblayout_header
							.setVisibility(View.GONE);

					Booking_Screen_TabLayout.tab.getTabWidget().getChildAt(0)
							.setClickable(false);
					
					
					// i.putExtra("booking_date", txtCalender.getText()
					// .toString());
					// i.putExtra("booking_time", txtTime.getText()
					// .toString());
					// i.putExtra("number_of_people", txtCalPerson
					// .getText().toString());

					// startActivity(i);

				}
				/* If success false */
				else if (Data_Success.equalsIgnoreCase("false")) {
					JSONObject Data_Error = data.getJSONObject("errors");
					System.out.println("Data_Error" + Data_Error);

					if (Data_Error.has("sessid")) {
						JSONArray Array_sessid = Data_Error
								.getJSONArray("sessid");
						System.out.println("Array_sessid" + Array_sessid);
						String Str_sessid = Array_sessid.getString(0);
						System.out.println("Str_sessid" + Str_sessid);
						if (Str_sessid != null) {
							Toast.makeText(getApplicationContext(), Str_sessid,
									Toast.LENGTH_LONG).show();
						}

					} else if (Data_Error.has("user_id")) {
						JSONArray Array_user_id = Data_Error
								.getJSONArray("user_id");
						System.out.println("Array_user_id" + Array_user_id);
						String Str_user_id = Array_user_id.getString(0);
						System.out.println("Str_user_id" + Str_user_id);
						if (Str_user_id != null) {
							Toast.makeText(getApplicationContext(),
									Str_user_id, Toast.LENGTH_LONG).show();
						}
					} else if (Data_Error.has("rest_id")) {
						JSONArray Array_rest_id = Data_Error
								.getJSONArray("rest_id");
						System.out.println("Array_rest_id" + Array_rest_id);
						String Str_rest_id = Array_rest_id.getString(0);
						System.out.println("Str_rest_id" + Str_rest_id);
						if (Str_rest_id != null) {
							Toast.makeText(getApplicationContext(),
									Str_rest_id, Toast.LENGTH_LONG).show();
						}
					} else if (Data_Error.has("number_of_people")) {
						JSONArray Array_number_of_people = Data_Error
								.getJSONArray("number_of_people");
						System.out.println("Array_number_of_people"
								+ Array_number_of_people);
						String Str_number_of_people = Array_number_of_people
								.getString(0);
						System.out.println("Str_number_of_people"
								+ Str_number_of_people);
						if (Str_number_of_people != null) {
							Toast.makeText(getApplicationContext(),
									Str_number_of_people, Toast.LENGTH_LONG)
									.show();
						}
					} else if (Data_Error.has("booking_mode")) {
						JSONArray Array_booking_mode = Data_Error
								.getJSONArray("booking_mode");
						System.out.println("Array_booking_mode"
								+ Array_booking_mode);
						String Str_booking_mode = Array_booking_mode
								.getString(0);
						System.out.println("Str_booking_mode"
								+ Str_booking_mode);
						if (Str_booking_mode != null) {
							Toast.makeText(getApplicationContext(),
									Str_booking_mode, Toast.LENGTH_LONG).show();
						}
					} else if (Data_Error.has("booking_date")) {
						JSONArray Array_booking_date = Data_Error
								.getJSONArray("booking_date");
						System.out.println("Array_booking_date"
								+ Array_booking_date);
						String Str_booking_date = Array_booking_date
								.getString(0);
						System.out.println("Str_booking_date"
								+ Str_booking_date);
						if (Str_booking_date != null) {
							Toast.makeText(getApplicationContext(),
									Str_booking_date, Toast.LENGTH_LONG).show();
						}
					} else if (Data_Error.has("booking_time")) {
						JSONArray Array_booking_time = Data_Error
								.getJSONArray("booking_time");
						System.out.println("Array_booking_time"
								+ Array_booking_time);
						String Str_booking_time = Array_booking_time
								.getString(0);
						System.out.println("Str_booking_time"
								+ Str_booking_time);
						if (Str_booking_time != null) {
							Toast.makeText(getApplicationContext(),
									Str_booking_time, Toast.LENGTH_LONG).show();
						}
					}

				}
				
				if (dialog.isShowing()) {
					dialog.dismiss();
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NullPointerException np) {
				np.printStackTrace();
			}
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
