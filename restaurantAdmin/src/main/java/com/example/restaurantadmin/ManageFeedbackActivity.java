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
import android.view.Menu;
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
import com.restaurantadmin.food_detail.restaurant_manage_feddback;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ManageFeedbackActivity extends Activity {
	ConnectionDetector cd;
	static ListView lv_manage_review;
	static AutoCompleteTextView mc_ed_search;
	static TextView txv_invisible;
	static JSONObject obj_output;
	public static Activity activity = null;
	static JSONArray filter_array;
	static restaurant_manage_feddback restaurant_manage_feddback;
	public static ProgressDialog p;
	Button btn_mf_pdf;
	public static ImageView img_home;
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_manage_feedback);
		activity = ManageFeedbackActivity.this;
		cd = new ConnectionDetector(getApplicationContext());
		Initizlization();
		setListener();
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
					restaurant_manage_feddback = new restaurant_manage_feddback(
							activity, filter_array);
					lv_manage_review.setAdapter(restaurant_manage_feddback);

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
			new get_review().execute();
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
	private void setListener() {
		// TODO Auto-generated method stub
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ManageFeedbackActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		btn_mf_pdf.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (filter_array != null) {
					if (filter_array.length() == 0) {
						Toast.makeText(activity,
								R.string.str_no_data_found_as_search,
								Toast.LENGTH_LONG).show();
					} else {
						if (cd.isConnectingToInternet()) {
							new generate_invoice_manage_feddback().execute();
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
	private void Initizlization() {
		// TODO Auto-generated method stub
		img_home = (ImageView) findViewById(R.id.img_home);
		lv_manage_review = (ListView) findViewById(R.id.lv_manage_review);
		mc_ed_search = (AutoCompleteTextView) findViewById(R.id.mc_ed_search);
		txv_invisible = (TextView) findViewById(R.id.txv_invisible);
		txv_invisible.setVisibility(View.GONE);
		btn_mf_pdf = (Button) findViewById(R.id.btn_mf_pdf);
	}

	public static JSONArray filter_restaurant_by_string(String str_value,
			JSONArray json) {
		JSONArray json_temp = new JSONArray();
		for (int i = 0; i < json.length(); i++) {

			try {

				if (json.getJSONObject(i).getString("booking_date")
						.toLowerCase().contains(str_value.toLowerCase())
						|| json.getJSONObject(i).getString("first_name")
								.toLowerCase()
								.contains(str_value.toLowerCase())
						|| json.getJSONObject(i).getString("last_name")
								.toLowerCase()
								.contains(str_value.toLowerCase())
						|| json.getJSONObject(i).getString("order_comment")
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_feedback, menu);
		return true;
	}

	public  class generate_invoice_manage_feddback
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
				System.out.println("obj_main" + obj_main);
				myconnection con = new myconnection();
				obj_output_invoice = new JSONObject(
						con.connection(ManageFeedbackActivity.this,
								Global_variable.rf_api_generate_invoice_manage_feddback,
								obj_main));

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

	public  class get_review extends AsyncTask<Void, Void, Void> {

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
			lv_manage_review.setVisibility(View.VISIBLE);
			txv_invisible.setVisibility(View.GONE);
			JSONObject obj_main = new JSONObject();
			try {
				obj_main.put("restaurant_id", Global_variable.restaurant_id);
				// obj_main.put("restaurant_id", "50");
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);
				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(ManageFeedbackActivity.this,
						Global_variable.rf_api_get_review, obj_main));

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
							lv_manage_review.setVisibility(View.GONE);
							txv_invisible.setVisibility(View.VISIBLE);
						} else {
							lv_manage_review.setVisibility(View.VISIBLE);
							txv_invisible.setVisibility(View.GONE);
							filter_array = obj_output.getJSONObject("data")
									.getJSONArray("manage_customer");
							restaurant_manage_feddback = new restaurant_manage_feddback(
									activity, obj_output.getJSONObject("data")
											.getJSONArray("manage_customer"));
							lv_manage_review
									.setAdapter(restaurant_manage_feddback);
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
}
