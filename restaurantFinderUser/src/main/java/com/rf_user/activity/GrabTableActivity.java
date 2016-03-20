package com.rf_user.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rf.restaurant_user.Login;
import com.rf.restaurant_user.R;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sharedprefernce.LanguageConvertPreferenceClass;

public class GrabTableActivity extends Activity {
	ImageView backImageview, searchImageview, imgOffers, calender, time;

	TextView txtCalender, txtTime;
	LinearLayout calender_linear, time_linear;
	ImageView nowButton, perLogo;
	TextView plus, txtCalPerson, minus, persons;
	ImageView imgFindTable, footerOrdernowImg, footerViewmenuImg,
			footerFeaturedImg, footerSettingImg, rf_grab_table_menu_icon;

	ProgressDialog progressDialog;

	/* For calender and time view */
	int year, month, day, hour, minutes, second,minutes1;
	Calendar c,c1;
	java.sql.Date currDate;

	/* Internet connection */
	ConnectionDetector cd;

	/* String declaration */
	String str_booking_date, str_booking_time, str_booking_number_of_people,
			str_loyalty, str_offer_id,output;
	String TAG_SUCCESS = "success";

	Intent in;

	/* Declaration for http call */
	HttpConnection http = new HttpConnection();
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_grab_table);
		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			/* create Object of internet connection* */
			cd = new ConnectionDetector(getApplicationContext());

			currDate = new java.sql.Date(System.currentTimeMillis());

			getInitialCalender();

			initialize();

			setlistener();
			
			loadLocale();
			
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}
	
	/*Language conversion methods */
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

	private void getInitialCalender() {
		// TODO Auto-generated method stub
		c = Calendar.getInstance();
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minutes = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);
		
		
		//int abc =Integer.parseInt(Global_variable.Ride_Later_Limit.getString("SR_BusinessRules_RideLater_Date_Limit"));
	
		currDate = new java.sql.Date(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date()); // Now use today date.
		c1.add(Calendar.DATE, 30); // Adding 30 days
		output = sdf.format(c1.getTime());
		System.out.println("!!!!!!!!!!!!!!!" + output);
		

	}

	private void initialize() {
		// TODO Auto-generated method stub

		backImageview = (ImageView) findViewById(R.id.back_imageview);
		searchImageview = (ImageView) findViewById(R.id.search_imageview);
		// searchImageview = (ImageView) findViewById(R.id.search_imageview);
		// searchSelectImageview = (ImageView)
		// findViewById(R.id.search_select_imageview);
		imgOffers = (ImageView) findViewById(R.id.img_offers);
		calender = (ImageView) findViewById(R.id.calender);
		txtCalender = (TextView) findViewById(R.id.txt_calender);
		time = (ImageView) findViewById(R.id.time);
		txtTime = (TextView) findViewById(R.id.txt_time);
		nowButton = (ImageView) findViewById(R.id.now_button);
		perLogo = (ImageView) findViewById(R.id.per_logo);
		plus = (TextView) findViewById(R.id.plus);
		txtCalPerson = (TextView) findViewById(R.id.txt_calculate_person);
		minus = (TextView) findViewById(R.id.minus);
		persons = (TextView) findViewById(R.id.persons);
		imgFindTable = (ImageView) findViewById(R.id.img_find_table);

		/* Linear Layout initialization */
		calender_linear = (LinearLayout) findViewById(R.id.calender_linear);
		time_linear = (LinearLayout) findViewById(R.id.time_linear);

		footerOrdernowImg = (ImageView) findViewById(R.id.footer_ordernow_img);
		footerViewmenuImg = (ImageView) findViewById(R.id.footer_viewmenu_img);
		footerFeaturedImg = (ImageView) findViewById(R.id.footer_featured_img);
		footerSettingImg = (ImageView) findViewById(R.id.footer_setting_img);

		rf_grab_table_menu_icon = (ImageView) findViewById(R.id.rf_grab_table_menu_icon);

		/* Intial level to set current date and time */

		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-"
					+ (month + 1) + "-" + day);
			
			DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String selectedDate = outputFormatter.format(date);
			txtCalender.setText(selectedDate);
			
			c1 = Calendar.getInstance();
			

			int hour1 = c1.get(Calendar.HOUR_OF_DAY);
			minutes1 = c1.get(Calendar.MINUTE);
			
			
			Date curr_time = new SimpleDateFormat("HH:mm").parse(hour1
					+ ":" + minutes1);

			DateFormat outputFormatter1 = new SimpleDateFormat(
					"HH:mm");
			String final_time1 = outputFormatter1
					.format(curr_time);
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!final_time1"+final_time1);
			
			
			txtTime.setText(final_time1);
			
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

	private void setlistener() {
		// TODO Auto-generated method stub

		try {

			searchImageview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							Search_Restaurant_List.class);
					startActivity(in);

				}
			});

			footerFeaturedImg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						if(SharedPreference.getuser_id(
								getApplicationContext())!="")
						{
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							Intent in = new Intent(getApplicationContext(),
									MyFavourites.class);
							startActivity(in);
						}
						}else {
							Toast.makeText(getApplicationContext(),
									R.string.please_login, Toast.LENGTH_SHORT)
									.show();
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			footerSettingImg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						if(SharedPreference.getuser_id(
								getApplicationContext())!="")
						{
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							Intent in = new Intent(getApplicationContext(),
									SettingsScreen.class);
							startActivity(in);
						}
						}else {
							Toast.makeText(getApplicationContext(),
									R.string.please_login, Toast.LENGTH_SHORT)
									.show();
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			imgFindTable.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Global_variable.grabtable="1";
					try {
						str_booking_date = txtCalender.getText().toString();
						str_booking_time = txtTime.getText().toString();
						str_booking_number_of_people = txtCalPerson.getText()
								.toString();

						try {
							Date date = new SimpleDateFormat("yyyy-MM-dd")
									.parse(str_booking_date);

							DateFormat outputFormatter1 = new SimpleDateFormat(
									"yyyy-MM-dd");
							String date_formating = outputFormatter1
									.format(date);

							System.out.println("!!!!!!!!!after_formating.."
									+ outputFormatter1 + "!!!!!"
									+ date_formating);

							Global_variable.str_Date = date_formating;

							Global_variable.str_Time_From = str_booking_time;
							Global_variable.str_Time_To = str_booking_time;

						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						runOnUiThread(new Runnable() {
							public void run() {

								/** check Internet Connectivity */
								if (cd.isConnectingToInternet()) {

									if (SharedPreference
											.getuser_id(getApplicationContext()) != "") {

										new GetValidOrderDateTime().execute();
										// new
										// async_GetCurrentUserDetails().execute();
									} else {
										Toast.makeText(getApplicationContext(),
												R.string.please_login,
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

			imgOffers.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			calender_linear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						getCalenderView();
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			time_linear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						getTimeView();
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			// calender.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// try {
			// getCalenderView();
			// } catch (NullPointerException n) {
			// n.printStackTrace();
			// }
			// }
			// });
			//
			// time.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// try {
			// getTimeView();
			// } catch (NullPointerException n) {
			// n.printStackTrace();
			// }
			// }
			// });

			nowButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						
						System.out.println("now button in 30 minute");
						c1 = Calendar.getInstance();
						c1.add(Calendar.MINUTE, 30);
						int hour1 = c1.get(Calendar.HOUR_OF_DAY);
						minutes1 = c1.get(Calendar.MINUTE);
						year = c.get(Calendar.YEAR);
						month = c.get(Calendar.MONTH);
						day = c.get(Calendar.DAY_OF_MONTH);
						hour = c.get(Calendar.HOUR_OF_DAY);
						minutes = c.get(Calendar.MINUTE);

						Date date;
						
						try {
							date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-"
									+ (month + 1) + "-" + day);
							
							DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
							String selectedDate = outputFormatter.format(date);
							txtCalender.setText(selectedDate);
							
							Date curr_time = new SimpleDateFormat("HH:mm").parse(hour1
									+ ":" + minutes1);

							DateFormat outputFormatter1 = new SimpleDateFormat(
									"HH:mm");
							String final_time1 = outputFormatter1
									.format(curr_time);
							
							System.out.println("!!!!!!!!!!!!!!!!!!!!final_time1"+final_time1);
							
							
							txtTime.setText(final_time1);
							
							
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			plus.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						int val = Integer.parseInt(txtCalPerson.getText()
								.toString());

						txtCalPerson.setText(String.valueOf(val + 1));
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			minus.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						int val = Integer.parseInt(txtCalPerson.getText()
								.toString());
						if (val <= 1) {
							txtCalPerson.setText("1");
						} else {
							txtCalPerson.setText(String.valueOf(val - 1));
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			backImageview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						if (SharedPreference
								.getuser_id(getApplicationContext()) != "") {

							if (Global_variable.activity == "Categories") {
								Intent i = new Intent(GrabTableActivity.this,
										Categories.class);
								startActivity(i);
							} else {
								// Intent i = new Intent(Login.this,
								// FindRestaurant.class);
								//
								// startActivity(i);

							}

						} else {

							Intent i = new Intent(GrabTableActivity.this,
									Login.class);

							startActivity(i);

						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

			rf_grab_table_menu_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(GrabTableActivity.this,
								rf_grab_table_menu_icon);
						popup.getMenuInflater().inflate(R.menu.popup,
								popup.getMenu());

						popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem item) {
								// TODO Auto-generated method stub

								System.out.println("!!!!!Item"
										+ item.getTitle());

								if (item.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_booking))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyBooking.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_profile))) {
									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyProfile.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_favourites))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {
											// Global_variable.activity =
											// "Categories";

											Intent in = new Intent(
													getApplicationContext(),
													MyFavourites.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();
										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_networking))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyNetworking.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equals(getString(R.string.about_restaurant))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {
											if (Global_variable.abt_rest_flag == true) {
												in = new Intent(
														getApplicationContext(),
														AboutRestaurant.class);
												startActivity(in);
											}

										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item.getTitle().toString()
										.equals(getString(R.string.logout))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference.getuser_id(
												getApplicationContext())
												.length() != 0) {

											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new UserLogout(
														GrabTableActivity.this)
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO
														// Auto-generated
														// method stub
														Toast.makeText(
																getApplicationContext(),
																R.string.No_Internet_Connection,
																Toast.LENGTH_SHORT)
																.show();

													}

												});
											}

										}
										}
										else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								return false;
							}
						});

						popup.show();
					} catch (NullPointerException n) {
						n.printStackTrace();
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

		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int selectedyear,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub

						year = selectedyear;
						month = monthOfYear;
						day = dayOfMonth;
						try {

							Date date = new SimpleDateFormat("yyyy-MM-dd")
									.parse(year + "-" + (month + 1) + "-" + day);

							DateFormat outputFormatter = new SimpleDateFormat(
									"yyyy-MM-dd");
							String selectedDate = outputFormatter.format(date); // Output
																				// :
																				// 01/20/2012

							System.out.println("!!!!!!selectedDate..."
									+ selectedDate);

							// Date currDate=new Date();

							System.out.println("!!!!!!!!!currDate." + currDate);
							System.out.println("output.compareTo(currDate).."
									+ selectedDate.compareTo(currDate
											.toString()));


							if (selectedDate.compareTo(currDate.toString()) >= 0
									&& selectedDate.compareTo(output
											.toString()) <= 0)  {
								// then do your work
								// Display Selected date in textbox

								DateFormat outputFormatter1 = new SimpleDateFormat(
										"dd MMM, yyyy");
								String date_formating = outputFormatter1
										.format(date);

								System.out.println("!!!!!!!!!after_formating.."
										+ outputFormatter1 + "!!!!!"
										+ date_formating);

								// txtCalender.setText(date_formating);

								txtCalender.setText(selectedDate);

								Global_variable.str_Date = date_formating;

							} else {
								// show message

								Toast.makeText(getApplicationContext(),
										getString(R.string.str_invalid_date), Toast.LENGTH_SHORT)
										.show();

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
		dpd.show();
		dpd.setCancelable(false);
		dpd.setCanceledOnTouchOutside(false);

	}

	private void getTimeView() {
		// TODO Auto-generated method stub

		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
										+ txtCalender.getText().toString());

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!shikha"
										+ currDate.toString());

						if (txtCalender.getText().toString()
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
									&& minute < curr_minutes || hourOfDay<curr_hour && minute<=curr_minutes || hourOfDay==curr_hour && minute<curr_minutes) {
								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in if");
								Toast.makeText(getApplicationContext(),
										getString(R.string.str_Please_choose_valid_time),
										Toast.LENGTH_SHORT).show();
							} else {

								System.out
										.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! in else");

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
									txtTime.setText(final_time);
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
								time = new SimpleDateFormat("HH:mm").parse(hour
										+ ":" + minutes);

								DateFormat outputFormatter = new SimpleDateFormat(
										"HH:mm");
								String final_time = outputFormatter
										.format(time);

								System.out
										.println("!!!!!!!!!!!!!!!!!final_time..."
												+ final_time);

								// Display Selected time in textbox
								txtTime.setText(final_time);
								Global_variable.str_Time_From = final_time;
								Global_variable.str_Time_To = final_time;

							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						// String db_time = hour + ":" + "00";

						// Display Selected time in textbox
						// txtTime.setText(hourOfDay + ":" + minute);
					}
				}, hour, minutes, false);
		tpd.show();
		tpd.setCancelable(false);
		tpd.setCanceledOnTouchOutside(false);

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

		if (SharedPreference.getuser_id(getApplicationContext()) != "") {

			if (Global_variable.activity == "Categories") {
				Intent i = new Intent(GrabTableActivity.this, Categories.class);
				startActivity(i);
			} else {
				// Intent i = new Intent(Login.this,
				// FindRestaurant.class);
				//
				// startActivity(i);

			}

		} else {

			Intent i = new Intent(GrabTableActivity.this, Login.class);
			startActivity(i);

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
			dialog = new ProgressDialog(GrabTableActivity.this);
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
								.connection(GrabTableActivity.this,
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

//						String open_time = data.getString("open_time");
//						String close_name = data.getString("close_time");

						new async_GetCurrentUserDetails().execute();

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

	public class async_GetCurrentUserDetails extends
			AsyncTask<Void, Void, Void> {
		JSONObject json;
		JSONObject data;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = new ProgressDialog(GrabTableActivity.this);
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			JSONObject GetCurrentUserDetails = new JSONObject();
			try {

				GetCurrentUserDetails.put("user_id",
						SharedPreference.getuser_id(getApplicationContext()));
				GetCurrentUserDetails.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));

				System.out.println("Shipping_Request_Child"
						+ GetCurrentUserDetails);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + GetCurrentUserDetails);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_GetCurrentUserDetails_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						GetCurrentUserDetails.toString(), "UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("Shipping_last_text" + responseText);

					json = new JSONObject(responseText);
					System.out.println("Shipping_last_json" + json);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				} catch (NullPointerException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			data = new JSONObject();
			try {
				data = json.getJSONObject("data");
				String str_success = json.getString("success");
				if (str_success.equals("true")) {
					// String str_message = json.getString("message");
					Global_variable.str_User_FirstName = data
							.getString("user_first_name");
					Global_variable.str_User_LastName = data
							.getString("user_last_name");
					Global_variable.str_User_Email = data
							.getString("user_email");
					Global_variable.str_User_ContactNumber = data
							.getString("user_contact_number");

					Global_variable.Country_code_array = new JSONArray();

					Global_variable.Country_code_array = json
							.getJSONArray("country");
					for (int i = 0; i < Global_variable.Country_code_array
							.length(); i++) {
						// JSONObject obj = new JSONObject();
						Global_variable.country_code = Global_variable.Country_code_array
								.getJSONObject(i)
								.getString("country_call_code");
					}

					Intent i = new Intent(GrabTableActivity.this,
							Booking_Screen_TabLayout.class);

					i.putExtra("booking_date", txtCalender.getText().toString());
					i.putExtra("booking_time", txtTime.getText().toString());
					i.putExtra("number_of_people", txtCalPerson.getText()
							.toString());

					startActivity(i);

					// ED_FirstName.setText(Global_variable.str_User_FirstName);
					// ED_LastName.setText(Global_variable.str_User_LastName);
					// ED_Email.setText(Global_variable.str_User_Email);
					// ED_Mobile.setText(Global_variable.str_User_ContactNumber);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {

			}

			progressDialog.dismiss();
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

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.grab_table, menu);
	// return true;
	// }

}
