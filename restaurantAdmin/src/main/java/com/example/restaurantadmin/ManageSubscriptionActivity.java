package com.example.restaurantadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ManageSubscriptionActivity extends Activity {
	public static int package_id = 0;
	public static String package_name = null;
	ConnectionDetector cd;
	public static RadioGroup radioGroup_subscription;
	Button btn_ms_button;
	public static ProgressDialog p;
	public static Activity activity = null;
	public static RadioButton radio_free, radio_pre, radio_gold;
	public static LinearLayout ll_free, ll_pre, ll_gold;
	public static TextView txv_free, txv_pre, txv_gold,subscripton_message;
	public static String str_package_id1, str_package_id2, str_package_name1,
			str_package_name2, str_package_name3, str_package_id3,
			str_package_global_booking_charge, str_price, str_booking_limit,
			str_online_order_limit, str_package_descript;
	public static String str_package1, str_package2, str_package3;
	public static ImageView img_home;
	private Locale myLocale;
	public String str_package_name=null,Str_current_package=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_manage_subscription);
		initialization();
		activity = ManageSubscriptionActivity.this;
		cd = new ConnectionDetector(getApplicationContext());
		new get_notifications().execute();
		if (Global_variable.array_Package != null) {
			for (int i = 0; i < Global_variable.array_Package.length(); i++) {
				JSONObject obj_package;
				try {
					// PACKAGE1*************
					obj_package = Global_variable.array_Package
							.getJSONObject(0);
					// System.out.println("1111step4objectPACKAGE1" +
					// obj_package);
					str_package_id1 = obj_package.getString("package_id");
					str_package_name1 = obj_package.getString("package_name");
					str_package_global_booking_charge = obj_package
							.getString("global_booking_charge");
					str_price = obj_package.getString("price");
					str_booking_limit = obj_package.getString("booking_limit");
					str_online_order_limit = obj_package
							.getString("online_order_limit");
					str_package_descript = obj_package
							.getString("package_description");

					// **********price limit desc
					txv_free.setText(str_package_descript);
					if (str_price.equalsIgnoreCase("0.00")
							&& str_online_order_limit.equalsIgnoreCase("0")) {
						str_package1 = "<b>"
								+ str_package_name1
								+ "</b>"
								+ " "
								+ getString(R.string.cs_free);
//								+ " "
//								+ getString(R.string.online_booking_fees_not_included);
						radio_free.setText(Html.fromHtml(str_package1));
					} else if (str_price.equalsIgnoreCase("0.00")) {
						str_package1 = "<b>" + str_package_name1 + "</b>" + " "
								+ getString(R.string.cs_free) + " ";
//								+ getString(R.string.online_booking_fees)
//								+ "<b>" + str_online_order_limit + "</b>" + " "
//								+ getString(R.string.included);
						radio_free.setText(Html.fromHtml(str_package1));
					} else if (str_online_order_limit.equalsIgnoreCase("0")) {
						str_package1 = "<b>"
								+ str_package_name1
								+ "</b>"
								+ " "
								+ "lei"
								+ "<b>"
								+ " "
								+ str_price;
//								+ "</b>"
//								+ " "
//								+ getString(R.string.online_booking_fees_not_included);
						radio_free.setText(Html.fromHtml(str_package1));
					} else {
						str_package1 = "<b>" + str_package_name1 + "</b>" + " "
								+ "lei" + "<b>" + " " + str_price;
//								+ "</b>"
//								+ " " + getString(R.string.online_booking_fees)
//								+ "<b>" + str_online_order_limit + "</b>" + " "
//								+ getString(R.string.included);
						radio_free.setText(Html.fromHtml(str_package1));
					}
					// **********price limit desc
					// ***********

					// ***********
					// PACKAGE1*************

					// PACKAGE2*************
					obj_package = Global_variable.array_Package
							.getJSONObject(1);
					// System.out.println("1111step4objectPACKAGE2" +
					// obj_package);
					str_package_id2 = obj_package.getString("package_id");
					str_package_name2 = obj_package.getString("package_name");
					str_package_global_booking_charge = obj_package
							.getString("global_booking_charge");
					str_price = obj_package.getString("price");
					str_booking_limit = obj_package.getString("booking_limit");
					str_online_order_limit = obj_package
							.getString("online_order_limit");
					str_package_descript = obj_package
							.getString("package_description");

					// **********price limit desc
					txv_pre.setText(str_package_descript);
					if (str_price.equalsIgnoreCase("0.00")
							&& str_online_order_limit.equalsIgnoreCase("0")) {
						str_package2 = "<b>"
								+ str_package_name2
								+ "</b>"
								+ " "
								+ getString(R.string.cs_free)
								+ " ";
//								+ getString(R.string.online_booking_fees_not_included);
						radio_pre.setText(Html.fromHtml(str_package2));
					} else if (str_price.equalsIgnoreCase("0.00")) {
						str_package1 = "<b>" + str_package_name2 + "</b>" + " "
								+ getString(R.string.cs_free) + " ";
//								+ getString(R.string.online_booking_fees)
//								+ "<b>" + str_online_order_limit + "</b>" + " "
//								+ getString(R.string.included);
						radio_pre.setText(Html.fromHtml(str_package2));
					} else if (str_online_order_limit.equalsIgnoreCase("0")) {
						str_package2 = "<b>"
								+ str_package_name2
								+ "</b>"
								+ " "
								+ "lei"
								+ "<b>"
								+ " "
								+ str_price;
//								+ "</b>"
//								+ " "
//								+ getString(R.string.online_booking_fees_not_included);
						radio_pre.setText(Html.fromHtml(str_package2));
					} else {
						str_package2 = "<b>" + str_package_name2 + "</b>" + " "
								+ "lei" + "<b>" + " " + str_price ;
//								+ "</b>"
//								+ " " + getString(R.string.online_booking_fees)
//								+ "<b>" + str_online_order_limit + "</b>" + " "
//								+ getString(R.string.included);
						radio_pre.setText(Html.fromHtml(str_package2));
					}
					// **********price limit desc
					// PACKAGE2*************

					// PACKAGE3*************
					obj_package = Global_variable.array_Package
							.getJSONObject(2);
					// System.out.println("1111step4objectPACKAGE3" +
					// obj_package);
					str_package_id3 = obj_package.getString("package_id");
					str_package_name3 = obj_package.getString("package_name");
					str_package_global_booking_charge = obj_package
							.getString("global_booking_charge");
					str_price = obj_package.getString("price");
					str_booking_limit = obj_package.getString("booking_limit");
					str_online_order_limit = obj_package
							.getString("online_order_limit");
					str_package_descript = obj_package
							.getString("package_description");

					// **********price limit desc
					txv_gold.setText(str_package_descript);
					if (str_price.equalsIgnoreCase("0.00")
							&& str_online_order_limit.equalsIgnoreCase("0")) {
						str_package3 = "<b>"
								+ str_package_name3
								+ "</b>"
								+ " "
								+ getString(R.string.cs_free);
//								+ " "
//								+ getString(R.string.online_booking_fees_not_included);
						radio_gold.setText(Html.fromHtml(str_package3));
					} else if (str_price.equalsIgnoreCase("0.00")) {
						str_package3 = "<b>" + str_package_name3 + "</b>" + " "
								+ getString(R.string.cs_free) ;
//								+ " "
//								+ getString(R.string.online_booking_fees)
//								+ "<b>" + str_online_order_limit + "</b>" + " "
//								+ getString(R.string.included);
						radio_gold.setText(Html.fromHtml(str_package3));
					} else if (str_online_order_limit.equalsIgnoreCase("0")) {
						str_package3 = "<b>"
								+ str_package_name3
								+ "</b>"
								+ " "
								+ "lei"
								+ "<b>"
								+ " "
								+ str_price;
//								+ "</b>"
//								+ " "
//								+ getString(R.string.online_booking_fees_not_included);
						radio_gold.setText(Html.fromHtml(str_package3));
					} else {
						str_package3 = "<b>" + str_package_name3 + "</b>" + " "
								+ "lei" + "<b>" + " " + str_price; 
//								+ "</b>"
//								+ " " + getString(R.string.online_booking_fees)
//								+ "<b>" + str_online_order_limit + "</b>" + " "
//								+ getString(R.string.included);
						radio_gold.setText(Html.fromHtml(str_package3));
					}
					// **********price limit desc

					// PACKAGE3*************
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		secOnclicklistner();
		/*try {
//			String str_package_name = mypackagename(Global_variable.logindata
//					.getJSONObject("restaurant_details")
//					.getString("package_id"));
			// System.out.println("hi12"+str_package_name);
			// System.out.println("hi12"+radioGroup_subscription.getChildCount());
			// radioGroup_subscription.check(id)

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

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
	private String mypackagename(String selected_package_id)
			throws JSONException {
		// TODO Auto-generated method stub
		for (int i = 0; i < Global_variable.array_Package.length(); i++) {
			if (Global_variable.array_Package.getJSONObject(i)
					.getString("package_id")
					.equalsIgnoreCase(selected_package_id)) {
				package_id = i;
				switch (i) {
					case 0 :
						radioGroup_subscription.check(R.id.radio_free);
						break;
					case 1 :
						radioGroup_subscription.check(R.id.radio_pre);
						break;
					case 2 :
						radioGroup_subscription.check(R.id.radio_gold);
						break;
				}
			}
		}
		return package_name;
	}

	private void secOnclicklistner() {
		// TODO Auto-generated method stub
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ManageSubscriptionActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		btn_ms_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cd.isConnectingToInternet()) {
					try {
						if(Str_current_package.equalsIgnoreCase(Global_variable.array_Package
								.getJSONObject(package_id).getString("package_id")))
						{
							Toast.makeText(getApplicationContext(),
									R.string.subscription_message,
									Toast.LENGTH_SHORT).show();
						}
						else
						{
							new update_package().execute();
						}
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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

		radioGroup_subscription
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						switch (checkedId) {
							case R.id.radio_free :
								package_id = 0;
								// System.out.println("radio_radio" + "0");
								ll_free.setVisibility(View.VISIBLE);
								ll_pre.setVisibility(View.GONE);
								ll_gold.setVisibility(View.GONE);
								break;
							case R.id.radio_pre :
								package_id = 1;
								// System.out.println("radio_radio" + "1");
								ll_free.setVisibility(View.GONE);
								ll_pre.setVisibility(View.VISIBLE);
								ll_gold.setVisibility(View.GONE);
								break;
							case R.id.radio_gold :
								package_id = 2;
								// System.out.println("radio_radio" + "2");
								ll_free.setVisibility(View.GONE);
								ll_pre.setVisibility(View.GONE);
								ll_gold.setVisibility(View.VISIBLE);
								break;
						}
					}
				});

	}

	private void initialization() {
		// TODO Auto-generated method stub
		img_home = (ImageView) findViewById(R.id.img_home);
		radioGroup_subscription = (RadioGroup) findViewById(R.id.radioGroup_subscription);
		btn_ms_button = (Button) findViewById(R.id.btn_ms_button);
		ll_free = (LinearLayout) findViewById(R.id.ll_free);
		ll_pre = (LinearLayout) findViewById(R.id.ll_pre);
		ll_gold = (LinearLayout) findViewById(R.id.ll_gold);
		txv_free = (TextView) findViewById(R.id.txv_free);
		txv_pre = (TextView) findViewById(R.id.txv_pre);
		txv_gold = (TextView) findViewById(R.id.txv_gold);
		subscripton_message=(TextView)findViewById(R.id.subscripton_message);
		radio_free = (RadioButton) findViewById(R.id.radio_free);
		radio_pre = (RadioButton) findViewById(R.id.radio_pre);
		radio_gold = (RadioButton) findViewById(R.id.radio_gold);
	}
	
	public class get_notifications extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(ManageSubscriptionActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			JSONObject obj_parent_psw = new JSONObject();
			JSONObject obj_child_rpsw = new JSONObject();
			try {
				
				obj_parent_psw.put("restaurant_id", Global_variable.restaurant_id);
				obj_parent_psw.put("sessid", Global_variable.sessid);
				System.out.println("Activity admin profile" + obj_parent_psw);

				try {

					// *************
					// for request
					myconnection con = new myconnection();
					String responseText = con.connection(ManageSubscriptionActivity.this,
							Global_variable.get_package,
							obj_parent_psw);

					try {

						System.out.println("after_connection.." + responseText);

						//json = new JSONObject(responseText);
						json = new JSONObject(responseText);
						System.out.println("responseText" + json);
					} catch (NullPointerException ex) {
						ex.printStackTrace();
					}

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

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			try {
				if (json == null) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
				} else if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Data = json.getJSONObject("data");

					System.out.println("11111datalogin" + Data);
				//	setAdapter(Data.getJSONArray("notifications"));
					
					 str_package_name = mypackagename(Data
							.getJSONObject("restaurant_details")
							.getString("package_id"));
					 Str_current_package=Data
								.getJSONObject("restaurant_details")
								.getString("package_id");
					 subscripton_message.setText(Data.getString("notification_message"));
				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("old_password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("sessid")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("sessid").get(0)
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

	/******* AsyncTask ******/
	public class update_package extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
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
				obj_main.put("sessid", Global_variable.sessid);
				obj_main.put("package_id", Global_variable.array_Package
						.getJSONObject(package_id).getString("package_id"));
				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						ManageSubscriptionActivity.this,
						Global_variable.rf_update_package, obj_main));

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
					Toast.makeText(activity, R.string.str_Package_information,
							Toast.LENGTH_LONG).show();
					Global_variable.logindata.getJSONObject(
							"restaurant_details").put(
							"package_id",
							obj_output.getJSONObject("data")
									.getJSONObject("restaurant_details")
									.getString("package_id"));
					Intent i = new Intent(ManageSubscriptionActivity.this,
							Home_Activity.class);
					startActivity(i);

				} else {

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_subscription, menu);
		return true;
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
