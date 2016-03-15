package com.rf.restaurant_superadmin;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.adapter.generate_invoice_list;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_function;
import com.superadmin.global.Global_variable;

public class activity_generate_invoice extends Activity {

	private LinearLayout rf_login_img_header;
	private LinearLayout ll_tg_raw_file, ll_oo_raw_file;

	private ImageView rf_login_img_header_icon;
	private ImageView img_menu;
	private TextView rf_supper_admin_txv_invoice;
	private ImageView img;
	private ImageView img_logo;
	private TextView textView4;
	private TextView txv_fromdate;

	// private TextView txv_used_loyalty_points_main;
	private EditText rf_invoice_ed_fromdate;
	private TextView txv_todate;
	private EditText rf_invoice_ed_todate;
	private RadioButton radiobtn_grabtable;
	private RadioGroup rg_in_on;
	private RadioButton radiobtn_online;
	private TextView txv;
	private EditText rf_login_ed_username;
	private Button btn;
	private Button btn_submit, btn_generate_pdf;
	private Button btn1;
	ProgressDialog p;
	ListView lv_generate_pdf;
	String str_greb = "0";
	Calendar c;
	int mYear;
	int mMonth;
	int mDay;
	String date = "";
	int i = 0;
	Boolean date_picker_boolean = false;
	String autocomplete_string = "";
	DatePickerDialog dpd = null;
	JSONObject jsonobject_generate_invoice_data = new JSONObject();
	JSONObject jsonobject_generate_invoice_pdf = new JSONObject();

	AutoCompleteTextView autocomplete_textview_generate_invoice_search;

	HttpConnection http = new HttpConnection();
	generate_invoice_list adapter_generate_invoice_list;

	/* Internet connection */
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_generate_invoice);

		// step 5
		initializeWidget();

		/* create Object of internet connection* */
		cd = new ConnectionDetector(getApplicationContext());
		setonclicklistener();

		autocomplete_textview_generate_invoice_search.setThreshold(0);

		autocomplete_textview_generate_invoice_search
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence str, int start,
							int before, int count) {

						if (Global_variable.array_invoice_data != null) {
							autocomplete_string = str.toString();
							setAdapter(Global_function
									.filter_invoice_by_string(str.toString(),
											Global_variable.array_invoice_data));
						}

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {

					}
				});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void setAdapter(JSONArray json) {
		// TODO Auto-generated method stub
		adapter_generate_invoice_list = new generate_invoice_list(
				activity_generate_invoice.this, json);
		lv_generate_pdf.setAdapter(adapter_generate_invoice_list);

	}

	private String Date(final EditText edit_text) {

		System.out.println("!!!!pankaj_inside_date");
		System.out.println("!!!!pankaj_inside_date_boolesn"
				+ date_picker_boolean);
		// if(date_picker_boolean)
		{
			dpd = new DatePickerDialog(activity_generate_invoice.this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view,
								int selectedyear, int selectedmonth,
								int selectedday) {
							mYear = selectedyear;
							mMonth = selectedmonth;
							mDay = selectedday;
							// Display Selected date in textbox
							// tb_ed_date.setText(selectedyear + "-" +
							// (selectedmonth + 1)
							// + "-" + selectedday);

							date = selectedyear + "-" + (selectedmonth + 1)
									+ "-" + selectedday;

							edit_text.setText(date);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
			dpd.setCancelable(false);
			dpd.setCanceledOnTouchOutside(false);

		}

		return date;
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

		Global_variable.array_invoice_data = new JSONArray();
		Intent i = new Intent(activity_generate_invoice.this,
				activity_home.class);
		startActivity(i);

	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub

		rg_in_on.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

				case R.id.radiobtn_grabtable:
					// do operations specific to this selection
					// checkbox_saved_add.setClickable(true);
					// flag_rg = true;
					ll_tg_raw_file.setVisibility(View.VISIBLE);
					ll_oo_raw_file.setVisibility(View.GONE);
					str_greb = "0";
					Global_variable.click_flag_tg_oo = "tg";
					if (Global_variable.array_invoice_data != null) {

						setAdapter(Global_function.filter_invoice_by_string(
								autocomplete_string,
								Global_variable.array_invoice_data));
						// setAdapter(Global_variable.array_invoice_data);
					}
					break;

				case R.id.radiobtn_online:
					// flag_rg = true;
					ll_tg_raw_file.setVisibility(View.GONE);
					ll_oo_raw_file.setVisibility(View.VISIBLE);
					str_greb = "1";
					Global_variable.click_flag_tg_oo = "oo";
					if (Global_variable.array_invoice_data != null) {
						setAdapter(Global_function.filter_invoice_by_string(
								autocomplete_string,
								Global_variable.array_invoice_data));
						// setAdapter(Global_variable.array_invoice_data);
					}
					// do operations specific to this selection
					break;

				}

			}
		});

		rf_invoice_ed_fromdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							System.out.println("!!!!pankaj" + (i++));
							// date_picker_boolean = true;
							//
							// if(!dpd.isShowing())
							{
								Date(rf_invoice_ed_fromdate);
							}
							// Date(rf_invoice_ed_fromdate);

							// new login().execute();
							// new TableGrabOrder().execute();
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

			}
		});

		rf_invoice_ed_todate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							System.out.println("!!!!pankaj" + (i++));

							Date(rf_invoice_ed_todate);

							// new login().execute();
							// new TableGrabOrder().execute();
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

			}
		});

		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							jsonobject_generate_invoice_data = Global_function
									.generate_invoice_data(
											rf_invoice_ed_fromdate.getText()
													.toString(),
											rf_invoice_ed_todate.getText()
													.toString(), "0",
											Global_variable.admin_email);
							new super_admin_invoice().execute();
							// new TableGrabOrder().execute();
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

			}
		});

		btn_generate_pdf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							if (Global_variable.array_invoice_data != null) {

								// Global_function.generate_pdf(Global_function.filter_invoice_by_string(autocomplete_string,Global_variable.array_invoice_data))
								// Global_function.filter_invoice_by_string(autocomplete_string,Global_variable.array_invoice_data);
								jsonobject_generate_invoice_pdf = Global_function
										.generate_invoice_data_pdf(
												rf_invoice_ed_fromdate
														.getText().toString(),
												rf_invoice_ed_todate.getText()
														.toString(),
												Global_function
														.generate_pdf(Global_function
																.filter_invoice_by_string(
																		autocomplete_string,
																		Global_variable.array_invoice_data)),
												Global_variable.admin_email);
								System.out.println("!!!!only_pdf"
										+ jsonobject_generate_invoice_pdf);
								
								if(Global_function
										.generate_pdf(Global_function
												.filter_invoice_by_string(
														autocomplete_string,
														Global_variable.array_invoice_data)).length()>1)
								{
									Toast.makeText(getApplicationContext(),
											R.string.gi_select_only_one_restaurant,
											Toast.LENGTH_SHORT).show();
									
								}
								else
								{
									new generate_invoice_pdf().execute();
								}
								

//								new generate_invoice_pdf().execute();

							} else {
								Toast.makeText(getApplicationContext(),
										R.string.gi_select_f_t_date,
										Toast.LENGTH_SHORT).show();

							}

							// new super_admin_invoice().execute();
							// new TableGrabOrder().execute();
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

			}
		});

		rf_login_img_header_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							Global_variable.array_invoice_data = new JSONArray();

							Intent i = new Intent(getApplicationContext(),
									activity_home.class);

							startActivity(i);

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

			}
		});

	}

	private void initializeWidget() {

		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);

		// TODO Auto-generated method stub
		rf_login_img_header = (LinearLayout) findViewById(R.id.rf_login_img_header);

		ll_tg_raw_file = (LinearLayout) findViewById(R.id.ll_tg_raw_file);
		ll_oo_raw_file = (LinearLayout) findViewById(R.id.ll_oo_raw_file);

		rf_login_img_header_icon = (ImageView) findViewById(R.id.rf_login_img_header_icon);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		rf_supper_admin_txv_invoice = (TextView) findViewById(R.id.rf_supper_admin_txv_invoice);
		img = (ImageView) findViewById(R.id.img);
		img_logo = (ImageView) findViewById(R.id.img_logo);
		txv_fromdate = (TextView) findViewById(R.id.txv_fromdate);
		// txv_used_loyalty_points_main = (TextView)
		// findViewById(R.id.txv_used_loyalty_points_main);

		rf_invoice_ed_fromdate = (EditText) findViewById(R.id.rf_invoice_ed_fromdate);
		txv_todate = (TextView) findViewById(R.id.txv_todate);
		rf_invoice_ed_todate = (EditText) findViewById(R.id.rf_invoice_ed_todate);
		radiobtn_grabtable = (RadioButton) findViewById(R.id.radiobtn_grabtable);
		radiobtn_online = (RadioButton) findViewById(R.id.radiobtn_online);
		rg_in_on = (RadioGroup) findViewById(R.id.rg_in_on);
		txv = (TextView) findViewById(R.id.txv);
		rf_login_ed_username = (EditText) findViewById(R.id.rf_login_ed_username);
		btn = (Button) findViewById(R.id.btn);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_generate_pdf = (Button) findViewById(R.id.btn_generate_pdf);
		btn1 = (Button) findViewById(R.id.btn1);
		lv_generate_pdf = (ListView) findViewById(R.id.lv_generate_pdf);
		autocomplete_textview_generate_invoice_search = (AutoCompleteTextView) findViewById(R.id.autocomplete_textview_generate_invoice_search);

	}

	public class super_admin_invoice extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_generate_invoice.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				// *************
				// for request
				String responseText = http.connection(
						Global_variable.rf_api_super_admin_invoice,
						jsonobject_generate_invoice_data);
			
				try {

					System.out.println("after_connection.." + responseText);

					json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));
					System.out.println("responseText" + json);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (NullPointerException np) {
				np.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (p.isShowing()) {
				p.dismiss();
				System.out.println("Activity manage packages price" + json);
			}
			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					// Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);
					Global_variable.array_invoice_data = json.getJSONObject(
							"data").getJSONArray("invoice_data");
					setAdapter(Global_variable.array_invoice_data);
					// Toast.makeText(
					// getApplicationContext(),
					// json.getJSONObject("data").getString("message"),
					// Toast.LENGTH_LONG)
					// .show();
					// Intent i = new
					// Intent(activity_manage_packages_price.this,activity_home.class);
					// startActivity(i);

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("to_date")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("to_date").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("from_date")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("from_date").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("generate_pdf")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("generate_pdf").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

		}

	}

	public class generate_invoice_pdf extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_generate_invoice.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				// *************
				// for request
				String responseText = http.connection(
						Global_variable.rf_api_generate_invoice_pdf,
						jsonobject_generate_invoice_pdf);

				try {

					System.out.println("after_connection.." + responseText);

					json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));

					System.out.println("responseText" + json);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (NullPointerException np) {
				np.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (p.isShowing()) {
				p.dismiss();
				System.out.println("Activity manage packages price" + json);
			}
			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					// Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);
					// Global_variable.array_invoice_data =
					// json.getJSONObject("data").getJSONArray("invoice_data");
					// setAdapter(Global_variable.array_invoice_data);
					Toast.makeText(getApplicationContext(),
							json.getJSONObject("data").getString("message"),
							Toast.LENGTH_LONG).show();
					// Intent i = new
					// Intent(activity_manage_packages_price.this,activity_home.class);
					// startActivity(i);

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("to_date")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("to_date").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("from_date")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("from_date").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("generate_pdf")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("generate_pdf").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

		}

	}

	public class login extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_generate_invoice.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("hi"
					+ rf_invoice_ed_fromdate.getText().toString());
			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("fromdate", rf_invoice_ed_fromdate.getText()
						.toString());
				obj_child.put("todate", rf_invoice_ed_todate.getText()
						.toString());

				obj_child.put("Radio", str_greb);

				obj_parent.put("sessid", Global_variable.sessid);
				obj_parent.put("Generate_invoice", obj_child);
				System.out.println("Activity_generate_invoice" + obj_parent);

				try {

					// *************
					// for request
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httppostreq = new HttpPost(
							Global_variable.rf_api_Url);
					System.out.println("post_url" + httppostreq);
					StringEntity se = new StringEntity(obj_parent.toString(),"UTF-8");
					System.out.println("url_request" + se);
					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
							"application/json"));
					se.setContentType("application/json;charset=UTF-8");
					se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
							"application/json;charset=UTF-8"));
					httppostreq.setEntity(se);

					HttpResponse httpresponse = httpclient.execute(httppostreq);

					String responseText = null;

					// ****** response text
					try {
						responseText = EntityUtils.toString(httpresponse
								.getEntity(),"UTF-8");
						System.out.println("final_response" + responseText);
						json = new JSONObject(responseText);
					} catch (ParseException e) {
						e.printStackTrace();

						Log.i("Parse Exception", e + "");

					} catch (NullPointerException np) {

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NullPointerException np) {

				}

				return null;

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
			// Dismiss the progress dialog
			if (p.isShowing()) {
				p.dismiss();
				System.out.println("Login_response" + obj_output);
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = obj_output.getJSONObject("data");
					Global_variable.sessid = obj_output.getString("sessid");
					System.out.println("11111datalogin" + Data);
					JSONObject obj_rest_details = Data
							.getJSONObject("restaurant_details");
					System.out
							.println("1111objrest_details" + obj_rest_details);
					Global_variable.admin_uid = Data.getString("user_id");
					Global_variable.restaurant_id = obj_rest_details
							.getString("id");
					Global_variable.rest_uid = obj_rest_details
							.getString("uid");

					System.out.println("1111adminuid"
							+ Global_variable.admin_uid);
					System.out.println("1111restaurant_id"
							+ Global_variable.restaurant_id);
					System.out.println("1111restid" + Global_variable.rest_uid);
					// Intent in = new Intent(Login_Activity.this,
					// TakeBooking_Tablayout.class);
					// startActivity(in);

					Intent i = new Intent(activity_generate_invoice.this,
							activity_home.class);
					startActivity(i);

				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("fromdate")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("fromdate").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("todate")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("todate").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

		}

	}

}
