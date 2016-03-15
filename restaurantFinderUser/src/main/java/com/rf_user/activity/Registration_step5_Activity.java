package com.rf_user.activity;

import java.util.Locale;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Registration_step5_Activity extends Activity {

	public static RadioGroup rf_bk_formate;
	public static RadioButton rf_bk_french;
	public static RadioButton rf_bk_iban;
	public static EditText ed_bic;
	public static EditText ed_iban;
	public static EditText ed_no;
	public static EditText ed_email;
	public static Button btn_validate;
	public static ProgressDialog p;
	public static String str_formate = "0";
	public static String str_country_code, str_email, str_mobileno;
	public static TextView txv_number;
	ConnectionDetector cd;
	private Locale myLocale;
	String str_terms="0";
	
	public static Dialog dialog;
	public static TextView  txv_msg,ok_text;
	public static ImageView  img_popupOk;
	
	public static CheckBox ch_terms;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_registration_step5);
		cd = new ConnectionDetector(getApplicationContext());
		initialization();
		if (Global_variable.restaurantregistrationstep4 != null) {
			try {
				str_country_code = Global_variable.restaurantregistrationstep1
						.getString("country_call_id");
				str_mobileno = Global_variable.restaurantregistrationstep1
						.getString("contact_number");
				JSONObject obj_restapp = Global_variable.restaurantregistrationstep4
						.getJSONObject("restaurant_app");
				str_email = obj_restapp.getString("invoice_email");
				ed_email.setText(str_email);
				txv_number.setText(str_country_code);
				ed_no.setText(str_mobileno);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

//		loadLocale();
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
		rf_bk_formate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
//					case R.id.rf_bk_frensh :
//						str_formate = "0";
//						break;
					case R.id.rf_bk_iban :
						str_formate = "1";
						break;
				}

			}
		});
		btn_validate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if (ed_bic.getText().toString().length() == 0) {
				// Toast.makeText(Registration_step5_Activity.this,
				// getString(R.string.str_Enter_BIC),
				// Toast.LENGTH_SHORT).show();
				// } else
				if (ed_iban.getText().toString().length() == 0) {
					Toast.makeText(Registration_step5_Activity.this,
							getString(R.string.str_Enter_IBAN),
							Toast.LENGTH_SHORT).show();
				}
				
				else if (str_terms.equalsIgnoreCase("0")) {
					Toast.makeText(Registration_step5_Activity.this,
							getString(R.string.terms_condi),
							Toast.LENGTH_SHORT).show();
				}
				
				else if (ed_iban.getText().toString().length() != 0) {
					if (cd.isConnectingToInternet()) {

						new async_regi_step5().execute();
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

			}
		});
		
		ch_terms.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					str_terms = "";
					str_terms = "1";
					TermsCondition();

				} else {
					str_terms = "";
					str_terms = "0";
				}

			}
		});
	}

	private void initialization() {

		rf_bk_formate = (RadioGroup) findViewById(R.id.rf_bk_formate);
		rf_bk_french = (RadioButton) findViewById(R.id.rf_bk_french);
		rf_bk_iban = (RadioButton) findViewById(R.id.rf_bk_iban);
		ed_bic = (EditText) findViewById(R.id.ed_bic);
		ed_iban = (EditText) findViewById(R.id.ed_iban);
		ed_no = (EditText) findViewById(R.id.ed_no);
		ed_email = (EditText) findViewById(R.id.ed_email);
		btn_validate = (Button) findViewById(R.id.btn_validate);
		txv_number = (TextView) findViewById(R.id.txv_number);
		ch_terms =  (CheckBox)findViewById(R.id.ch_terms);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_step5_, menu);
		return true;
	}

	public class async_regi_step5 extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_restaurant_app;
		JSONObject obj_restaurantregistrationstep5;
		JSONObject obj_MainRequest;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(Registration_step5_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			obj_restaurant_app = new JSONObject();
			obj_MainRequest = new JSONObject();
			obj_restaurantregistrationstep5 = new JSONObject();

			try {

				// obj_restaurant_app********
				System.out.println("1111selectstr_formate" + str_formate);
				obj_restaurant_app.put("account_format", str_formate);
				obj_restaurant_app.put("bic_number", "test");
				obj_restaurant_app.put("iban_number", ed_iban.getText()
						.toString());
				obj_restaurant_app.put("bank_mobile_number", ed_no.getText()
						.toString());
				obj_restaurant_app.put("bank_email", ed_email.getText()
						.toString());

				System.out.println("1111obj_restaurant_app"
						+ obj_restaurant_app);
				// obj_restaurant_app********

				// obj_restaurantregistrationstep5********

				obj_restaurantregistrationstep5.put("restaurant_app",
						obj_restaurant_app);

				// obj_restaurantregistrationstep5******

				// obj_MainRequest*******************************

				System.out
						.println("1111Global_variable.restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);
				System.out
						.println("1111Global_variable.restaurantregistrationstep2"
								+ Global_variable.restaurantregistrationstep2);
				System.out
						.println("1111Global_variable.restaurantregistrationstep3"
								+ Global_variable.restaurantregistrationstep3);
				System.out.println("1111step2sessid" + Global_variable.sessid);
				obj_MainRequest.put("restaurantregistrationstep1",
						Global_variable.restaurantregistrationstep1);
				obj_MainRequest.put("restaurantregistrationstep2",
						Global_variable.restaurantregistrationstep2);
				obj_MainRequest.put("restaurantregistrationstep3",
						Global_variable.restaurantregistrationstep3);
				obj_MainRequest.put("restaurantregistrationstep4",
						Global_variable.restaurantregistrationstep4);
				obj_MainRequest.put("restaurantregistrationstep5",
						obj_restaurantregistrationstep5);
				obj_MainRequest.put("sessid", Global_variable.sessid);
				obj_MainRequest.put("comment", Global_variable.comment);
				System.out
						.println("1111obj_MainRequeststep5" + obj_MainRequest);

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
					String str_response = con.connection_rest_reg(Registration_step5_Activity.this,
							Global_variable.rf_api_registrationstep5,
							obj_MainRequest);

					json = new JSONObject(str_response);
					System.out.println("1111finaljsonstepTG" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i(getString(R.string.str_please_wait), e + "");

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
				Global_variable.comment = json.getString("comment");
				System.out.println("1111step5comments"
						+ Global_variable.comment);
				if (jsonSuccessStr.equalsIgnoreCase("true")) {
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
						Global_variable.restaurantregistrationstep3 = Data
								.getJSONObject("restaurantregistrationstep3");
						System.out.println("1111restaurantregistrationstep3"
								+ Global_variable.restaurantregistrationstep3);
						Global_variable.restaurantregistrationstep4 = Data
								.getJSONObject("restaurantregistrationstep4");
						System.out.println("1111restaurantregistrationstep4"
								+ Global_variable.restaurantregistrationstep4);
						Global_variable.restaurantregistrationstep5 = Data
								.getJSONObject("restaurantregistrationstep5");
						System.out.println("1111restaurantregistrationstep5"
								+ Global_variable.restaurantregistrationstep5);
						Global_variable.admin_uid = Data
								.getString("restaurant_admin_id");
						Global_variable.restaurant_id = Data
								.getString("restaurant_id");
						Global_variable.rest_uid = Data.getString("uid");
						System.out.println("1111step5uid"
								+ Global_variable.rest_uid);
						System.out.println("1111step5admin_uid"
								+ Global_variable.admin_uid);
						System.out.println("1111step5restaurant_id"
								+ Global_variable.restaurant_id);
						Toast.makeText(Registration_step5_Activity.this,
								getString(R.string.str_Regi_succeess),
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(Registration_step5_Activity.this,
								Login.class);
						startActivity(i);
					}
				} else {
					JSONObject Error = json.getJSONObject("errors");
					System.out.println("1111errors" + Error);
					if (Error != null) {
						if (Error.has("bic_number")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("bic_number").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("iban_number")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("iban_number").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("bank_mobile_number")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("bank_mobile_number")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Error.has("bank_email")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("bank_email").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1111success" + jsonSuccessStr);
		}
	}
	
	@SuppressLint("ResourceAsColor")
	public void TermsCondition() {

		dialog = new Dialog(Registration_step5_Activity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_terms_condition);
		txv_msg = (TextView) dialog.findViewById(R.id.txv_success);
		txv_msg.setTextColor(R.color.black);
		ok_text = (TextView) dialog.findViewById(R.id.ok_text);
		txv_msg.setText(getResources().getString(R.string.terms_condi_details));
//		System.out.println("msg" + str_order_date_time);
		try {

			ok_text.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
	}
}
