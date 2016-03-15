package com.example.restaurantadmin;

import android.app.Activity;
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
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.food_detail.manage_customer_detail;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ManageCustomersActivity extends Activity {
	ConnectionDetector cd;
	static ListView lv_customer_info;
	public static Activity activity = null;
	static manage_customer_detail manage_customer_detail;
	static TextView txv_invisible;
	public static JSONArray filter_array;
	static AutoCompleteTextView mc_ed_search;
	static JSONObject obj_output;
	Button btn_mc_genrate_invoice;
	public static ProgressDialog p;
	public static ImageView img_home;
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_manage_customers);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		activity = ManageCustomersActivity.this;
		cd = new ConnectionDetector(getApplicationContext());

		initializeWidgets();
		setlistener();
		mc_ed_search.setThreshold(0);

		mc_ed_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence str, int start, int before,
					int count) {
				try {
					filter_array = filter_restaurant_by_string(
							str.toString(),
							obj_output.getJSONObject("data").getJSONArray(
									"manage_customer"));
					manage_customer_detail = new manage_customer_detail(
							activity, filter_array);
					lv_customer_info.setAdapter(manage_customer_detail);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		if (cd.isConnectingToInternet()) {

			new get_unique_customer().execute();
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
	

		// loadLocale();
//		loadSavedPreferences();
//		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
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
		// TODO Auto-generated method stub
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ManageCustomersActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		btn_mc_genrate_invoice.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("himurtuza" + filter_array);
				if (filter_array != null) {
					if (filter_array.length() == 0) {
						Toast.makeText(activity,
								R.string.str_no_data_found_as_search,
								Toast.LENGTH_LONG).show();
					} else {
						if (cd.isConnectingToInternet()) {
							new generate_invoice_manage_customer().execute();
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
				} else {
					Toast.makeText(activity,
							R.string.str_no_data_found_as_search,
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub
		img_home = (ImageView) findViewById(R.id.img_home);
		lv_customer_info = (ListView) findViewById(R.id.lv_customer_info);
		mc_ed_search = (AutoCompleteTextView) findViewById(R.id.mc_ed_search);
		txv_invisible = (TextView) findViewById(R.id.txv_invisible);
		txv_invisible.setVisibility(View.GONE);
		btn_mc_genrate_invoice = (Button) findViewById(R.id.btn_mc_genrate_invoice);
	}

	public static JSONArray filter_restaurant_by_string(String str_value,
			JSONArray json) {
		JSONArray json_temp = new JSONArray();
		for (int i = 0; i < json.length(); i++) {

			System.out.print("abcccccccccccc....." + json);

			try {

				if (json.getJSONObject(i).getString("email").toLowerCase()
						.contains(str_value.toLowerCase())
						|| json.getJSONObject(i).getString("first_name")
								.toLowerCase()
								.contains(str_value.toLowerCase())
						|| json.getJSONObject(i).getString("last_name")
								.toLowerCase()
								.contains(str_value.toLowerCase())
						|| json.getJSONObject(i).getString("mobile_number")
								.toLowerCase()
								.contains(str_value.toLowerCase())
						|| (json.getJSONObject(i).getString("first_name") + " " + json
								.getJSONObject(i).getString("last_name"))
								.toLowerCase()
								.contains(str_value.toLowerCase())) {
					json_temp.put(json.getJSONObject(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json_temp;
	}

	public class generate_invoice_manage_customer
			extends
				AsyncTask<Void, Void, Void> {
		JSONObject obj_output_invoice;
		protected void onPreExecute() {
			p = new ProgressDialog(activity);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			JSONObject obj_main = new JSONObject();
			try {
				obj_main.put("restaurant_id", Global_variable.restaurant_id);
				obj_main.put("restaurant_name", Global_variable.logindata
						.getJSONObject("global_setting").getString("name_en"));
				obj_main.put(
						"email",
						Global_variable.logindata.getJSONObject(
								"global_setting").getString("contact_email"));
				// System.out.println("himurtuza"+obj_output.getJSONObject("data").getJSONArray("manage_customer"));
				// System.out.println("himurtuza"+filter_array.length());
				try {
					obj_main.put("pdf_data", filter_array);

				} catch (Exception e) {
					obj_main.put("pdf_data", obj_output.getJSONObject("data")
							.getJSONArray("manage_customer"));
				}
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_mainmanagcust" + obj_main);
				myconnection con = new myconnection();
				obj_output_invoice = new JSONObject(
						con.connection(
								ManageCustomersActivity.this,
								Global_variable.rf_api_generate_invoice_manage_customer,
								obj_main));
				System.out.println("obj_mainmanagcustoutput" + obj_output_invoice);

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
			System.out.println("output" + obj_output);
			if (p.isShowing()) {
				p.dismiss();
			}
			try {
				if (obj_output_invoice.getString("success").equalsIgnoreCase(
						"true")) {
					Toast.makeText(
							activity,
							obj_output_invoice.getJSONObject("data")
									.getString("message").toString(),
							Toast.LENGTH_LONG).show();
				} else {
					JSONObject Errors = obj_output_invoice
							.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors.has("restaurant_id")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("restaurant_id").get(0)
										.toString(), Toast.LENGTH_LONG).show();
					}
					if (Errors.has("restaurant_name")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("restaurant_name").get(0)
										.toString(), Toast.LENGTH_LONG).show();
					}
					if (Errors.has("email")) {
						Toast.makeText(activity,
								Errors.getJSONArray("email").get(0).toString(),
								Toast.LENGTH_LONG).show();
					}
					if (Errors.has("pdf_data")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("pdf_data").get(0)
										.toString(), Toast.LENGTH_LONG).show();
					}

					if (Errors.has("sessid")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("sessid").get(0).toString(),
								Toast.LENGTH_LONG).show();
					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class get_unique_customer extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			p = new ProgressDialog(activity);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			lv_customer_info.setVisibility(View.VISIBLE);
			txv_invisible.setVisibility(View.GONE);
			JSONObject obj_main = new JSONObject();
			try {
				obj_main.put("restaurant_id", Global_variable.restaurant_id);
				// obj_main.put("restaurant_id", "50");
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);
				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						ManageCustomersActivity.this,
						Global_variable.rf_api_get_unique_customer, obj_main));

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
			System.out.println("output" + obj_output);
			if (p.isShowing()) {
				p.dismiss();
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {
					try {
						if (obj_output.getJSONObject("data")
								.getJSONArray("manage_customer").length() == 0) {
							lv_customer_info.setVisibility(View.GONE);
							txv_invisible.setVisibility(View.VISIBLE);

						} else {
							lv_customer_info.setVisibility(View.VISIBLE);
							txv_invisible.setVisibility(View.GONE);
							for (int i = 0; i < obj_output
									.getJSONObject("data")
									.getJSONArray("manage_customer").length(); i++) {
								for (int j = i + 1; j < obj_output
										.getJSONObject("data")
										.getJSONArray("manage_customer")
										.length(); j++) {
									if (obj_output
											.getJSONObject("data")
											.getJSONArray("manage_customer")
											.getJSONObject(i)
											.getString("user_id")
											.equalsIgnoreCase(
													obj_output
															.getJSONObject(
																	"data")
															.getJSONArray(
																	"manage_customer")
															.getJSONObject(j)
															.getString(
																	"user_id"))) {
										obj_output
												.getJSONObject("data")
												.getJSONArray("manage_customer")
												.getJSONObject(i)
												.put("type", "Both");
										obj_output
												.getJSONObject("data")
												.put("manage_customer",
														RemoveJSONArray(
																obj_output
																		.getJSONObject(
																				"data")
																		.getJSONArray(
																				"manage_customer"),
																j));
									} else {

									}
								}
							}
							System.out.println("murtuza_nalawala"
									+ obj_output.getJSONObject("data")
											.getJSONArray("manage_customer"));
							manage_customer_detail = new manage_customer_detail(
									activity, obj_output.getJSONObject("data")
											.getJSONArray("manage_customer"));
							lv_customer_info.setAdapter(manage_customer_detail);
							filter_array = obj_output.getJSONObject("data")
									.getJSONArray("manage_customer");
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors.has("restaurant_id")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("restaurant_id").get(0)
										.toString(), Toast.LENGTH_LONG).show();
					}
					if (Errors.has("sessid")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("sessid").get(0).toString(),
								Toast.LENGTH_LONG).show();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static JSONArray RemoveJSONArray(JSONArray jarray, int pos) {

		JSONArray Njarray = new JSONArray();
		try {
			for (int i = 0; i < jarray.length(); i++) {
				if (i != pos)
					Njarray.put(jarray.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Njarray;
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

}
