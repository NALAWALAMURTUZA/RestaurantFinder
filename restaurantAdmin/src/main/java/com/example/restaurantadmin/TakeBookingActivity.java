package com.example.restaurantadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TakeBookingActivity extends Activity {
	private int mYear, mMonth, mDay, mHour, mMinute, mHour_end, mMinute_end,
			mHour_duration, mMinute_duration;
	private TextView rf_supper_admin_header_name;
	private TextView rf_regi_step2_tv_you;
	private TextView tv_rf_registration_your_restaurant;
	private TextView tv_rf_registration_title;
	private RadioGroup rg_rf_regi;
	private RadioButton rb_rf_regi_mr;
	private RadioButton rb_rf_regi_mrs;
	private RadioButton rb_rf_regi_misss;
	private EditText tb_surname;
	private EditText tb_ed_first;
	private EditText tb_ed_email;
	private TextView txv_rf_country_code;
	private EditText tb_ed_phone;
	private EditText tb_ed_bussiness;
	private EditText tb_ed_date;
	private EditText tb_time, rf_registration_ed_note;
	private TextView rf_registration_tv_country;
	private EditText tb_sp_size;
	private EditText rf_registration_ed_restaurant;
	private EditText rf_registration_ed_email, rf_registration_amount;
	private Spinner tb_sp_status;
	private EditText tb_sp_occasion;
	private EditText tb_end;
	public ProgressDialog p;
	private CheckBox checkBox1;
	private Spinner tb_duration;
	private Spinner tb_sp_dur_from;
	private EditText rf_registration_ed_zip_code;
	private TextView txv_choosefile;
	private TextView txv_choose_filename;
	private Button rf_registration_btn_continue;
	private TextView locinfo;
	String str_gender = "mr";
	boolean flag_rg = false;
	ConnectionDetector cd;
	public static ArrayAdapter<CharSequence> adapter_tb_sp_size;
	private static final String TAG = "TakeBookingActivity";
	private static final int PICKFILE_RESULT_CODE = 0;
	public String result = "", output = "";
	public static ImageView img_home;
	private Locale myLocale;
	public static Dialog DeleteDialog;
	java.sql.Date currDate;
	Calendar c;
	public static Activity activity = null;
	public static TextView txv_Dialogtext, txv_msg;
	public static ImageView img_yes, img_no, img_popupOk;
	public static Dialog dialog;
	public static String str_takebooking_popup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_take_booking);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		activity = TakeBookingActivity.this;
		cd = new ConnectionDetector(getApplicationContext());
		initializeWidgets();
		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mHour_end = c.get(Calendar.HOUR_OF_DAY);
		mMinute_end = c.get(Calendar.MINUTE);
		mHour_duration = c.get(Calendar.HOUR_OF_DAY);
		mMinute_duration = c.get(Calendar.MINUTE);

		currDate = new java.sql.Date(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date()); // Now use today date.
		c1.add(Calendar.DATE, 30); // Adding 30 days
		output = sdf.format(c1.getTime());
		System.out.println("!!!!!!!!!!!!!!!" + output);

		setlistener();
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
		// LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
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
	private void setlistener() {
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TakeBookingActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		rf_registration_btn_continue
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cd.isConnectingToInternet()) {
							new tagboobking().execute();
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
		// TODO Auto-generated method stub
		tb_ed_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Date();
			}
		});
		tb_time.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Time();
			}
		});
		// tb_duration.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // Time_duration();
		// }
		// });
		tb_end.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Time_end();
			}
		});

		rg_rf_regi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_rf_regi_mr :
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
		txv_choosefile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						result = "";
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						// intent.setType("file/*");
						intent.setType("text/csv");
						startActivityForResult(intent, PICKFILE_RESULT_CODE);

						/*
						 * Intent intent = new
						 * Intent(Intent.ACTION_GET_CONTENT); Uri uri =
						 * Uri.parse
						 * (Environment.getExternalStorageDirectory().getPath()
						 * + "/myFolder/"); intent.setDataAndType(uri, "");
						 * startActivity(Intent.createChooser(intent,
						 * "Open folder"));
						 */

					}

				});

			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case PICKFILE_RESULT_CODE :
				// If the file selection was successful
				if (resultCode == RESULT_OK) {
					if (data != null) {
						// Get the URI of the selected file
						final Uri uri = data.getData();
						Log.i(TAG, "Uri = " + uri.toString());
						try {
							// Get the file path from the URI
							final String path = FileUtils.getPath(this, uri);
							txv_choose_filename.setText(path);

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// tv.setText("File Upload Completed.");
									StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
											.permitAll().build();
									StrictMode.setThreadPolicy(policy);
									// uploadFile(path);
									if (FileUtils.getExtension(uri.toString()) != null) {
										String Extention = FileUtils
												.getExtension(uri.toString());
										System.out.println("Extention"
												+ Extention);
										if (Extention.equalsIgnoreCase(".txt")) {
											Toast.makeText(
													TakeBookingActivity.this,
													R.string.str_File_Selected
															+ path,
													Toast.LENGTH_LONG).show();
											uploadFile(path);
										} else if (Extention
												.equalsIgnoreCase(".csv")) {
											Toast.makeText(
													TakeBookingActivity.this,
													R.string.str_File_Selected
															+ path,
													Toast.LENGTH_LONG).show();
											uploadFile(path);
										} else if (Extention
												.equalsIgnoreCase(".pdf")) {
											Toast.makeText(
													TakeBookingActivity.this,
													R.string.str_File_Selected
															+ path,
													Toast.LENGTH_LONG).show();
											uploadFile(path);
										} else {
											txv_choose_filename.setText("");

											Toast.makeText(
													TakeBookingActivity.this,
													R.string.str_pdf_cvs,
													Toast.LENGTH_LONG).show();
										}
									} else {
										Toast.makeText(
												TakeBookingActivity.this,
												R.string.str_File_Path_not_found,
												Toast.LENGTH_LONG).show();
									}
									/*
									 * Toast.makeText(TakeBookingActivity.this,
									 * "File Upload Start.", Toast.LENGTH_SHORT)
									 * .show();
									 */
								}
							});

						} catch (Exception e) {
							Log.e(getString(R.string.str_FileSelectorTestActivity),
									getString(R.string.str_File_select_error),
									e);
						}
					}
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public int uploadFile(String sourceFileUri) {
		AlertDialog dialog;
		dialog = ProgressDialog.show(TakeBookingActivity.this, "",
				getResources().getString(R.string.str_Uploading), true);
		/*
		 * p = new ProgressDialog(TakeBookingActivity.this);
		 * p.setMessage("Please wait..."); p.setCancelable(false);
		 * p.setIcon(R.drawable.ic_launcher); p.show();
		 */
		String upLoadServerUri = Global_variable.rf_api_upload_image;
		// String upLoadServerUri =
		// "http://192.168.1.17/RF_admin_api/admin_api_dev/manage_restaurant_gallery";
		String fileName = sourceFileUri;
		int serverResponseCode = 0;
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
					@Override
					public void run() {
						/*
						 * if (p.isShowing()) { p.dismiss(); }
						 */
						// tv.setText("File Upload Completed.");
						/*
						 * Toast.makeText(TakeBookingActivity.this,
						 * "File Upload Complete.", Toast.LENGTH_SHORT) .show();
						 */
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

			System.out.println("!!!!result" + result);

			if (result.equalsIgnoreCase(getResources().getString(
					R.string.str_Please_Try_again))) {
				// Toast some error
				Toast.makeText(TakeBookingActivity.this, "" + result,
						Toast.LENGTH_LONG).show();
			} else {
				System.out.println("final uploaded URL" + result);
				Toast.makeText(TakeBookingActivity.this, "" + result,
						Toast.LENGTH_LONG).show();
			}
		} catch (MalformedURLException ex) {
			dialog.dismiss();
			ex.printStackTrace();
			Toast.makeText(TakeBookingActivity.this,
					R.string.str_MalformedURLException, Toast.LENGTH_SHORT)
					.show();
			Log.e(getString(R.string.str_Upload_file_to),
					getString(R.string.str_error) + ex.getMessage(), ex);
		} catch (Exception e) {
			dialog.dismiss();
			e.printStackTrace();
			Toast.makeText(TakeBookingActivity.this,
					R.string.str_Exception + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
			Log.e(getString(R.string.str_Upload_file_to_server),
					R.string.str_Exception + e.getMessage(), e);
		}
		dialog.dismiss();
		return serverResponseCode;
	}

	private void initializeWidgets() {
		img_home = (ImageView) findViewById(R.id.img_home);
		rf_supper_admin_header_name = (TextView) findViewById(R.id.rf_supper_admin_header_name);
		rf_regi_step2_tv_you = (TextView) findViewById(R.id.rf_regi_step2_tv_you);
		tv_rf_registration_your_restaurant = (TextView) findViewById(R.id.tv_rf_registration_your_restaurant);
		tv_rf_registration_title = (TextView) findViewById(R.id.tv_rf_registration_title);
		rg_rf_regi = (RadioGroup) findViewById(R.id.rg_rf_regi);
		rb_rf_regi_mr = (RadioButton) findViewById(R.id.rb_rf_regi_mr);
		rb_rf_regi_mrs = (RadioButton) findViewById(R.id.rb_rf_regi_mrs);
		rb_rf_regi_misss = (RadioButton) findViewById(R.id.rb_rf_regi_misss);
		tb_surname = (EditText) findViewById(R.id.tb_surname);
		tb_ed_first = (EditText) findViewById(R.id.tb_ed_first);
		tb_ed_email = (EditText) findViewById(R.id.tb_ed_email);
		txv_rf_country_code = (TextView) findViewById(R.id.txv_rf_country_code);
		tb_ed_phone = (EditText) findViewById(R.id.tb_ed_phone);
		tb_ed_bussiness = (EditText) findViewById(R.id.tb_ed_bussiness);
		tb_ed_date = (EditText) findViewById(R.id.tb_ed_date);
		tb_time = (EditText) findViewById(R.id.tb_time);
		rf_registration_tv_country = (TextView) findViewById(R.id.rf_registration_tv_country);
		tb_sp_size = (EditText) findViewById(R.id.tb_sp_size);
		rf_registration_ed_restaurant = (EditText) findViewById(R.id.rf_registration_ed_restaurant);
		rf_registration_ed_email = (EditText) findViewById(R.id.rf_registration_ed_email);
		tb_sp_status = (Spinner) findViewById(R.id.tb_sp_status);
		tb_sp_occasion = (EditText) findViewById(R.id.tb_sp_occasion);
		tb_end = (EditText) findViewById(R.id.tb_end);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		tb_duration = (Spinner) findViewById(R.id.tb_duration);
		txv_choosefile = (TextView) findViewById(R.id.txv_choosefile);
		txv_choose_filename = (TextView) findViewById(R.id.txv_choose_filename);
		rf_registration_btn_continue = (Button) findViewById(R.id.rf_registration_btn_continue);
		rf_registration_ed_note = (EditText) findViewById(R.id.rf_registration_ed_note);
		rf_registration_amount = (EditText) findViewById(R.id.rf_registration_amount);
		adapter_tb_sp_size = ArrayAdapter.createFromResource(
				TakeBookingActivity.this, R.array.array_takebooking_status,
				android.R.layout.simple_spinner_dropdown_item);
		tb_sp_status.setAdapter(adapter_tb_sp_size);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_booking, menu);
		return true;
	}

	private void Date() {

		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int selectedyear,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub

						mYear = selectedyear;
						mMonth = monthOfYear;
						mDay = dayOfMonth;
						try {

							Date date = new SimpleDateFormat("yyyy-MM-dd")
									.parse(mYear + "-" + (mMonth + 1) + "-"
											+ mDay);

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
									&& selectedDate.compareTo(output.toString()) <= 0) {
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

								tb_ed_date.setText(selectedDate);

							} else {
								// show message

								Toast.makeText(getApplicationContext(),
										getString(R.string.str_invalid_date),
										Toast.LENGTH_SHORT).show();

							}

						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, mYear, mMonth, mDay);
		dpd.show();
		dpd.setCancelable(false);
		dpd.setCanceledOnTouchOutside(false);
	}
	private void Time() {
		// Launch Time Picker Dialog
//		TimePickerDialog tpd = new TimePickerDialog(this,
//				new TimePickerDialog.OnTimeSetListener() {
//
//					@Override
//					public void onTimeSet(TimePicker view, int hourOfDay,
//							int minute) {
//						// Display Selected time in textbox
//						mHour = hourOfDay;
//						mMinute = minute;
//						tb_time.setText(hourOfDay + ":" + minute);
//
//					}
//				}, mHour, mMinute, false);
//		tpd.show();
		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {

						if (tb_ed_date.getText().toString()
								.equalsIgnoreCase(currDate.toString())) {
							c = Calendar.getInstance();
							int curr_hour = c.get(Calendar.HOUR_OF_DAY);
							int curr_minutes = c.get(Calendar.MINUTE);

							
							if (hourOfDay < curr_hour
									&& minute < curr_minutes
									|| hourOfDay < curr_hour
									&& minute <= curr_minutes
									|| hourOfDay == curr_hour
									&& minute < curr_minutes) {
							
								Toast.makeText(
										getApplicationContext(),
										getString(R.string.str_Please_choose_valid_time),
										Toast.LENGTH_SHORT).show();
							} else {

								mMinute = minute;
								mHour = hourOfDay;

								String time1 = mHour + ":" + mMinute;

								Date time;
								try {
									time = new SimpleDateFormat("HH:mm")
											.parse(mHour + ":" + mMinute);

									DateFormat outputFormatter = new SimpleDateFormat(
											"HH:mm");
									String final_time = outputFormatter
											.format(time);

									System.out
											.println("!!!!!!!!!!!!!!!!!final_time..."
													+ final_time);

									// Display Selected time in textbox
									tb_time.setText(final_time);
								

								} catch (java.text.ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						} else {

							mMinute = minute;
							mHour = hourOfDay;

							String time1 = mHour + ":" + mMinute;

							Date time;
							try {
								time = new SimpleDateFormat("HH:mm")
										.parse(mHour + ":" + mMinute);

								DateFormat outputFormatter = new SimpleDateFormat(
										"HH:mm");
								String final_time = outputFormatter
										.format(time);

							
								// Display Selected time in textbox
								tb_time.setText(final_time);


							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				}, mHour, mMinute, false);
		tpd.show();
		tpd.setCancelable(false);
		tpd.setCanceledOnTouchOutside(false);
	}
	private void Time_end() {
		// Launch Time Picker Dialog
		TimePickerDialog tpd = new TimePickerDialog(this,
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// Display Selected time in textbox
						mHour_end = hourOfDay;
						mMinute_end = minute;

						tb_end.setText(hourOfDay + ":" + minute);

					}
				}, mHour_end, mMinute_end, false);
		tpd.show();
	}
	// private void Time_duration() {
	// // Launch Time Picker Dialog
	// TimePickerDialog tpd = new TimePickerDialog(this,
	// new TimePickerDialog.OnTimeSetListener() {
	//
	// @Override
	// public void onTimeSet(TimePicker view, int hourOfDay,
	// int minute) {
	// // Display Selected time in textbox
	// mHour_duration=hourOfDay;
	// mHour_duration=minute;
	// tb_duration.setText(hourOfDay + ":" + minute);
	//
	// }
	// }, mHour_duration, mMinute_duration, false);
	// tpd.show();
	// }
	/* AsyncTask */
	public class tagboobking extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			p = new ProgressDialog(TakeBookingActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_main = new JSONObject();

			try {

				obj_parent.put("rest_id", Global_variable.restaurant_id);
				obj_parent.put("title", str_gender);
				obj_parent.put("surname", tb_surname.getText());
				obj_parent.put("firstname", tb_ed_first.getText());
				obj_parent.put("email", tb_ed_email.getText());
				obj_parent.put("mobile", tb_ed_phone.getText());
				obj_parent.put("bussiness", tb_ed_bussiness.getText());
				obj_parent.put("booking_date", tb_ed_date.getText());
				obj_parent.put("booking_time", tb_time.getText());
				obj_parent.put("note", rf_registration_ed_note.getText());
				obj_parent.put("duration", tb_duration.getSelectedItem()
						.toString().substring(0, 1));
				obj_parent.put("occasion", tb_sp_occasion.getText());
				obj_parent.put("amount", rf_registration_amount.getText());
				obj_parent.put("booking_status", String.valueOf(tb_sp_status
						.getSelectedItemPosition() + 1));
				obj_parent.put("duration", tb_duration.getSelectedItem()
						.toString().substring(0, 1));
				obj_parent.put("end_at", "00:00:00");
				obj_parent.put("number_of_people", tb_sp_size.getText());

				if (result.equalsIgnoreCase("")) {
					obj_parent.put("attached_file", "");
				} else {
					obj_parent.put("attached_file", result);
				}

				obj_main.put("order_tg_admin", obj_parent);
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);

				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						TakeBookingActivity.this,
						Global_variable.rf_api_takebooking, obj_main));

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (p.isShowing()) {
				p.dismiss();
				System.out.println("output" + obj_output);
			}
			try {
				System.out.println("mysuccess"
						+ obj_output.getString("success"));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {
					TakeBookingActivity t = new TakeBookingActivity();
					t.result = "";
					System.out.println("result123"
							+ TakeBookingActivity.this.result);
					System.out.println("objoutputdata"
							+ obj_output.getJSONObject("data"));
					str_takebooking_popup = obj_output.getJSONObject("data")
							.getString("message");
					System.out.println("str_takebooking_popup"
							+ str_takebooking_popup);
					if (str_takebooking_popup.length() != 0) {
						TakebookingPopup();
					}
					/*
					 * Global_variable.array_online_table_grabbing = obj_output
					 * .getJSONObject("data").getJSONArray(
					 * "online_table_grabbing");
					 */
					// Intent in = new Intent(TakeBookingActivity.this,
					// TakeBooking_Tablayout.class);
					// startActivity(in);

				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("booking_status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("booking_status")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("booking_date")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("booking_date").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("occasion")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("occasion").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("surname")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("surname").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("booking_time")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("booking_time").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("firstname")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("firstname").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("rest_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("rest_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("amount")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("amount").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("duration")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("duration").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("title")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("title").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("bussiness")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("bussiness").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("email")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("email").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("note")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("note").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("mobile")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("mobile").get(0)
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
	public void TakebookingPopup() {

		dialog = new Dialog(TakeBookingActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_getin);
		txv_msg = (TextView) dialog.findViewById(R.id.txv_success);
		img_popupOk = (ImageView) dialog.findViewById(R.id.img_popupok);
		txv_msg.setText(str_takebooking_popup);
		System.out.println("msg" + str_takebooking_popup);
		try {

			img_popupOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent ii = new Intent(TakeBookingActivity.this,
							TakeBooking_Tablayout.class);
					startActivity(ii);
				}
			});

			dialog.show();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
	}
}
