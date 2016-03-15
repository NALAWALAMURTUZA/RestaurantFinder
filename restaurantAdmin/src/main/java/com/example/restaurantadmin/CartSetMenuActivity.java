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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

public class CartSetMenuActivity extends Activity {

	private TextView rf_supper_admin_header_name;

	private CheckBox ch_csm;
	private ImageView imageView11;

	private TextView rf_regi_step3_tv_price;

	private TextView rf_regi_step3_tv_dishname;

	private TextView rf_regi_step3_txv_starter1;
	private EditText csm_ed_starter1;
	private EditText csm_ed_starter1_price1;
	private TextView csm_tv_starter2;
	private EditText csm_ed_starter2;
	private EditText csm_ed_starter2_price2;
	private TextView csm_tv_starter3;
	private EditText csm_ed_starter3;
	private EditText csm_ed_starter3_price3;
	private TextView csm_tv_course1;
	private EditText csm_ed_course1;
	private EditText csm_ed_course1_price1;
	private TextView csm_tv_course2;
	private EditText csm_ed_course2;
	private EditText csm_ed_course2_price2;
	private TextView csm_tv_course3;
	private EditText csm_ed_course3;
	private EditText csm_ed_course3_price3;
	private TextView csm_tv_dessert1;
	private EditText csm_ed_dessert1;
	private EditText csm_ed_dessert1_price1;
	private TextView csm_tv_dessert2;
	private EditText csm_ed_dessert2;
	private EditText csm_ed_dessert2_price2;
	private TextView csm_tv_dessert3;
	private EditText csm_ed_dessert3;
	private EditText csm_ed_dessert3_price3;
	private TextView csm_step3_drink;
	private ImageView imageView00;
	private TextView csm_glass_wine;
	private EditText csm_ed_wine_glass_min_price;
	private TextView csm_tv_min;
	private EditText csm_ed_wine_glss_max_price;
	private TextView csm_max;
	private TextView csm_bot_wine;
	private EditText csm_ed_wine_bottel_min_price;
	private TextView csm_min1;
	private EditText csm_ed_wine_bottel_max_price;
	private EditText csm_ed_bottel_of_water;
	private EditText csm_ed_half_bootel_price;
	private TextView rf_regi_step3_max2;
	private TextView rf_regi_step3_stl_wtr2;
	private EditText csm_ed_min_glass_of_champ;
	private EditText csm_ed_max_bottel_of_champ;
	private EditText csm_min_ed_coffee;
	private EditText csm_max_ed_coffee;
	private EditText csm_ed_comment;
	private TextView csm_cancel;
	private Button csm_btn_confirme;
	private Button csm_btn_cancel;
	public static ProgressDialog p;
	ConnectionDetector cd;
	public static ImageView img_home;
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_cart_set_menu);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		cd = new ConnectionDetector(getApplicationContext());
		initializeWidgets();
		setlistener();
		if (Global_variable.logindata != null) {
			System.out.println("logindataincartsetmenu"+Global_variable.logindata);
			try {
				csm_ed_starter1.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("starter_1"));
				csm_ed_starter2.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("starter_2"));
				csm_ed_starter3.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("starter_3"));
				csm_ed_course1.setText(Global_variable.logindata.getJSONObject(
						"setmenu").getString("main_course_1"));
				csm_ed_course2.setText(Global_variable.logindata.getJSONObject(
						"setmenu").getString("main_course_2"));
				csm_ed_course3.setText(Global_variable.logindata.getJSONObject(
						"setmenu").getString("main_course_3"));
				csm_ed_dessert1.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("desserts_1"));
				csm_ed_dessert2.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("desserts_2"));
				csm_ed_dessert3.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("desserts_3"));
				csm_ed_starter1_price1.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("starter_price_1"));
				csm_ed_starter2_price2.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("starter_price_2"));
				csm_ed_starter3_price3.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString("starter_price_3"));
				csm_ed_course1_price1.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString(
								"main_course_price_1"));
				csm_ed_course2_price2.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString(
								"main_course_price_2"));
				csm_ed_course3_price3.setText(Global_variable.logindata
						.getJSONObject("setmenu").getString(
								"main_course_price_3"));
				csm_ed_dessert1_price1
						.setText(Global_variable.logindata.getJSONObject(
								"setmenu").getString("desserts_price_1"));
				csm_ed_dessert2_price2
						.setText(Global_variable.logindata.getJSONObject(
								"setmenu").getString("desserts_price_2"));
				csm_ed_dessert3_price3
						.setText(Global_variable.logindata.getJSONObject(
								"setmenu").getString("desserts_price_3"));
				csm_ed_wine_glass_min_price.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"glass_wine_min_price"));
				csm_ed_wine_glss_max_price.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"glass_wine_max_price"));
				csm_ed_wine_bottel_min_price.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"bottle_wine_min_price"));
				csm_ed_wine_bottel_max_price.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"bottle_wine_max_price"));
				csm_ed_bottel_of_water.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"water_bottle_max_price"));
				csm_ed_half_bootel_price.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"water_bottle_min_price"));
				csm_ed_min_glass_of_champ.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"glass_champion_min_price"));
				csm_ed_max_bottel_of_champ.setText(Global_variable.logindata
						.getJSONObject("drinks").getString(
								"glass_champion_max_price"));
				csm_min_ed_coffee.setText(Global_variable.logindata
						.getJSONObject("drinks").getString("cofee_min_price"));
				csm_max_ed_coffee.setText(Global_variable.logindata
						.getJSONObject("drinks").getString("cofee_max_price"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

//		loadLocale();

		// language*****************
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
				Intent i = new Intent(CartSetMenuActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		csm_btn_confirme.setOnClickListener(new View.OnClickListener() {
			Boolean flag = true;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (csm_ed_starter1.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_starter1_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_starter2.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_starter2_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_starter3.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_starter3_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_course1.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_course1_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_course2.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_course2_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_course3.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_course3_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_dessert1.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_dessert1_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_dessert2.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_dessert2_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_dessert3.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Name_Field_dessert3_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_starter1_price1.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_starter1_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_starter2_price2.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_starter2_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_starter3_price3.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_starter3_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_course1_price1.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_course1_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_course2_price2.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_course2_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_course3_price3.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_course3_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_dessert1_price1.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_dessert1_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_dessert2_price2.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_dessert2_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_dessert3_price3.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_Price_Field_dessert3_null,
							Toast.LENGTH_LONG).show();
				}
				if (csm_ed_wine_glass_min_price.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_min_price_glass_wine,
							Toast.LENGTH_LONG).show();
				}
				else if (csm_ed_wine_glss_max_price.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_max_price_glass_wine,
							Toast.LENGTH_LONG).show();
				} else {

					if (Integer.parseInt(csm_ed_wine_glass_min_price.getText()
							.toString()) > Integer
							.parseInt(csm_ed_wine_glss_max_price.getText()
									.toString())) 
					{
						flag = false;
						Toast.makeText(getApplicationContext(),
								R.string.str_min_price_glass_wine_greater,
								Toast.LENGTH_LONG).show();
					}

				}

				if (csm_ed_wine_bottel_min_price.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_min_price_bottle_wine,
							Toast.LENGTH_LONG).show();
				}
				else if (csm_ed_wine_bottel_max_price.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_max_price_bottle_wine,
							Toast.LENGTH_LONG).show();
				} else {

					if (Integer.parseInt(csm_ed_wine_bottel_min_price.getText()
							.toString()) > Integer
							.parseInt(csm_ed_wine_bottel_max_price.getText()
									.toString())) {
						flag = false;
						Toast.makeText(getApplicationContext(),
								R.string.str_min_price_bottle_wine_greater,
								Toast.LENGTH_LONG).show();
					}

				}
				if (csm_ed_bottel_of_water.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_price_bottle_water, Toast.LENGTH_LONG)
							.show();
				}
				else if (csm_ed_half_bootel_price.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_price_half_bottle_water,
							Toast.LENGTH_LONG).show();
				} else {

					if (Integer.parseInt(csm_ed_half_bootel_price.getText()
							.toString()) > Integer
							.parseInt(csm_ed_bottel_of_water.getText()
									.toString())) {
						flag = false;
						Toast.makeText(getApplicationContext(),
								R.string.str_price_full_bottle_water_greater,
								Toast.LENGTH_LONG).show();
					}

				}
				if (csm_ed_min_glass_of_champ.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_min_price_glass_Champagne,
							Toast.LENGTH_LONG).show();
				}
				else if (csm_ed_max_bottel_of_champ.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_max_price_glass_Champagne,
							Toast.LENGTH_LONG).show();
				} else {

					if (Integer.parseInt(csm_ed_min_glass_of_champ.getText()
							.toString()) > Integer
							.parseInt(csm_ed_max_bottel_of_champ.getText()
									.toString())) {
						flag = false;
						Toast.makeText(getApplicationContext(),
								R.string.str_price_glass_Champagne_greater,
								Toast.LENGTH_LONG).show();
					}

				}
				if (csm_min_ed_coffee.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_min_price_cup_Coffee,
							Toast.LENGTH_LONG).show();
				}
				else if (csm_max_ed_coffee.getText().length() == 0) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							R.string.str_max_price_cup_Coffee,
							Toast.LENGTH_LONG).show();
				} else {

					if (Integer
							.parseInt(csm_min_ed_coffee.getText().toString()) > Integer
							.parseInt(csm_max_ed_coffee.getText().toString())) {
						flag = false;
						Toast.makeText(getApplicationContext(),
								R.string.str_price_cup_Coffee_greater,
								Toast.LENGTH_LONG).show();
					}

				}
				System.out.println("flag" + flag);
				if (flag == true) {
					new update_setmenu().execute();
				} else {
					flag = true;
				}

			}
		});
		csm_btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(CartSetMenuActivity.this,
						Home_Activity.class);
				startActivity(i);
			}
		});
	}

	public class update_setmenu extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(CartSetMenuActivity.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			JSONObject obj_child_second = new JSONObject();
			try {
				obj_child.put("starter_1", csm_ed_starter1.getText());
				obj_child.put("starter_2", csm_ed_starter2.getText());
				obj_child.put("starter_3", csm_ed_starter3.getText());
				obj_child.put("desserts_1", csm_ed_dessert1.getText());
				obj_child.put("desserts_2", csm_ed_dessert2.getText());
				obj_child.put("desserts_3", csm_ed_dessert3.getText());
				obj_child.put("main_course_1", csm_ed_course1.getText());
				obj_child.put("main_course_2", csm_ed_course2.getText());
				obj_child.put("main_course_3", csm_ed_course3.getText());
				obj_child.put("main_course_price_1",
						csm_ed_course1_price1.getText());
				obj_child.put("main_course_price_2",
						csm_ed_course2_price2.getText());
				obj_child.put("main_course_price_3",
						csm_ed_course3_price3.getText());
				obj_child.put("desserts_price_1",
						csm_ed_dessert1_price1.getText());
				obj_child.put("desserts_price_2",
						csm_ed_dessert2_price2.getText());
				obj_child.put("desserts_price_3",
						csm_ed_dessert3_price3.getText());
				obj_child.put("starter_price_1",
						csm_ed_starter1_price1.getText());
				obj_child.put("starter_price_2",
						csm_ed_starter2_price2.getText());
				obj_child.put("starter_price_3",
						csm_ed_starter3_price3.getText());
				obj_child_second.put("glass_wine_min_price",
						csm_ed_wine_glass_min_price.getText());
				
				System.out.println("glass_wine_min_price at registration time"+obj_child_second);
				obj_child_second.put("glass_wine_max_price",
						csm_ed_wine_glss_max_price.getText());
				obj_child_second.put("bottle_wine_min_price",
						csm_ed_wine_bottel_min_price.getText());
				obj_child_second.put("bottle_wine_max_price",
						csm_ed_wine_bottel_max_price.getText());
				obj_child_second.put("water_bottle_min_price",
						csm_ed_half_bootel_price.getText());
				obj_child_second.put("water_bottle_max_price",
						csm_ed_bottel_of_water.getText());
				obj_child_second.put("glass_champion_min_price",
						csm_ed_min_glass_of_champ.getText());
				obj_child_second.put("glass_champion_max_price",
						csm_ed_max_bottel_of_champ.getText());
				obj_child_second.put("cofee_min_price",
						csm_min_ed_coffee.getText());
				obj_child_second.put("cofee_max_price",
						csm_max_ed_coffee.getText());
				obj_parent.put("sessid", Global_variable.sessid);
				obj_parent.put("id", Global_variable.restaurant_id);
				obj_parent.put("setmenu", obj_child);
				obj_parent.put("drinks", obj_child_second);
				System.out.println("Prents" + obj_parent);

			
				myconnection con = new myconnection();
				obj_output = new JSONObject(
						con.connection(CartSetMenuActivity.this,
								Global_variable.rf_api_update_setmenu,
								obj_parent));

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
					Global_variable.logindata.put("setmenu", obj_output
							.getJSONObject("data").getJSONObject("setmenu"));
					Global_variable.logindata.put("drinks", obj_output
							.getJSONObject("data").getJSONObject("drinks"));
					Toast.makeText(getApplicationContext(),
							R.string.str_update_succ, Toast.LENGTH_LONG).show();
					Intent i = new Intent(CartSetMenuActivity.this,
							Home_Activity.class);
					startActivity(i);

				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {
						if (Errors.has("starter_1")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("starter_1").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("starter_2")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("starter_2").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("starter_3")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("starter_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("starter_price_1")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("starter_price_1")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("starter_price_2")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("starter_price_2")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("starter_price_3")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("starter_price_3")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("main_course_1")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("main_course_1").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("main_course_2")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("main_course_2").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("main_course_3")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("main_course_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("main_course_price_1")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("main_course_price_1")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("main_course_price_2")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("main_course_price_2")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("main_course_price_3")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("main_course_price_3")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("desserts_1")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("desserts_1").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("desserts_2")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("desserts_2").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("desserts_3")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("desserts_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("desserts_price_1")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("desserts_price_1")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("desserts_price_2")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("desserts_price_2")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("desserts_price_3")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("desserts_price_3")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("glass_wine_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("glass_wine_min_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("glass_wine_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("glass_wine_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("bottle_wine_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("bottle_wine_min_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("bottle_wine_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("bottle_wine_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("water_bottle_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray(
											"water_bottle_min_price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("water_bottle_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray(
											"water_bottle_max_price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("glass_champion_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray(
											"glass_champion_min_price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("glass_champion_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray(
											"glass_champion_max_price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("cofee_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("cofee_min_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("cofee_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("cofee_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
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

	private void initializeWidgets() {
		// TODO Auto-generated method stub
		img_home = (ImageView) findViewById(R.id.img_home);
		rf_supper_admin_header_name = (TextView) findViewById(R.id.rf_supper_admin_header_name);
		ch_csm = (CheckBox) findViewById(R.id.ch_csm);
		imageView11 = (ImageView) findViewById(R.id.imageView11);
		rf_regi_step3_tv_price = (TextView) findViewById(R.id.rf_regi_step3_tv_price);
		rf_regi_step3_tv_dishname = (TextView) findViewById(R.id.rf_regi_step3_tv_dishname);
		rf_regi_step3_tv_price = (TextView) findViewById(R.id.rf_regi_step3_tv_price);
		rf_regi_step3_txv_starter1 = (TextView) findViewById(R.id.rf_regi_step3_txv_starter1);
		csm_ed_starter1 = (EditText) findViewById(R.id.csm_ed_starter1);
		csm_ed_starter1_price1 = (EditText) findViewById(R.id.csm_ed_starter1_price1);
		csm_tv_starter2 = (TextView) findViewById(R.id.csm_tv_starter2);
		csm_ed_starter2 = (EditText) findViewById(R.id.csm_ed_starter2);
		csm_ed_starter2_price2 = (EditText) findViewById(R.id.csm_ed_starter2_price2);
		csm_tv_starter3 = (TextView) findViewById(R.id.csm_tv_starter3);
		csm_ed_starter3 = (EditText) findViewById(R.id.csm_ed_starter3);
		csm_ed_starter3_price3 = (EditText) findViewById(R.id.csm_ed_starter3_price3);
		csm_tv_course1 = (TextView) findViewById(R.id.csm_tv_course1);
		csm_ed_course1 = (EditText) findViewById(R.id.csm_ed_course1);
		csm_ed_course1_price1 = (EditText) findViewById(R.id.csm_ed_course1_price1);
		csm_tv_course2 = (TextView) findViewById(R.id.csm_tv_course2);
		csm_ed_course2 = (EditText) findViewById(R.id.csm_ed_course2);
		csm_ed_course2_price2 = (EditText) findViewById(R.id.csm_ed_course2_price2);
		csm_tv_course3 = (TextView) findViewById(R.id.csm_tv_course3);
		csm_ed_course3 = (EditText) findViewById(R.id.csm_ed_course3);
		csm_ed_course3_price3 = (EditText) findViewById(R.id.csm_ed_course3_price3);
		csm_tv_dessert1 = (TextView) findViewById(R.id.csm_tv_dessert1);
		csm_ed_dessert1 = (EditText) findViewById(R.id.csm_ed_dessert1);
		csm_ed_dessert1_price1 = (EditText) findViewById(R.id.csm_ed_dessert1_price1);
		csm_tv_dessert2 = (TextView) findViewById(R.id.csm_tv_dessert2);
		csm_ed_dessert2 = (EditText) findViewById(R.id.csm_ed_dessert2);
		csm_ed_dessert2_price2 = (EditText) findViewById(R.id.csm_ed_dessert2_price2);
		csm_tv_dessert3 = (TextView) findViewById(R.id.csm_tv_dessert3);
		csm_ed_dessert3 = (EditText) findViewById(R.id.csm_ed_dessert3);
		csm_ed_dessert3_price3 = (EditText) findViewById(R.id.csm_ed_dessert3_price3);
		csm_step3_drink = (TextView) findViewById(R.id.csm_step3_drink);
		imageView00 = (ImageView) findViewById(R.id.imageView00);
		csm_glass_wine = (TextView) findViewById(R.id.csm_glass_wine);
		csm_ed_wine_glass_min_price = (EditText) findViewById(R.id.csm_ed_wine_glass_min_price);
		csm_tv_min = (TextView) findViewById(R.id.csm_tv_min);
		csm_ed_wine_glss_max_price = (EditText) findViewById(R.id.csm_ed_wine_glss_max_price);
		csm_max = (TextView) findViewById(R.id.csm_max);
		csm_bot_wine = (TextView) findViewById(R.id.csm_bot_wine);
		csm_ed_wine_bottel_min_price = (EditText) findViewById(R.id.csm_ed_wine_bottel_min_price);
		csm_min1 = (TextView) findViewById(R.id.csm_min1);
		csm_ed_wine_bottel_max_price = (EditText) findViewById(R.id.csm_ed_wine_bottel_max_price);
		csm_ed_bottel_of_water = (EditText) findViewById(R.id.csm_ed_bottel_of_water);
		csm_ed_half_bootel_price = (EditText) findViewById(R.id.csm_ed_half_bootel_price);
		rf_regi_step3_max2 = (TextView) findViewById(R.id.rf_regi_step3_max2);
		rf_regi_step3_stl_wtr2 = (TextView) findViewById(R.id.rf_regi_step3_stl_wtr2);
		csm_ed_min_glass_of_champ = (EditText) findViewById(R.id.csm_ed_min_glass_of_champ);
		csm_ed_max_bottel_of_champ = (EditText) findViewById(R.id.csm_ed_max_bottel_of_champ);
		csm_min_ed_coffee = (EditText) findViewById(R.id.csm_min_ed_coffee);
		csm_max_ed_coffee = (EditText) findViewById(R.id.csm_max_ed_coffee);
		csm_ed_comment = (EditText) findViewById(R.id.csm_ed_comment);
		csm_cancel = (TextView) findViewById(R.id.csm_cancel);
		csm_btn_confirme = (Button) findViewById(R.id.csm_btn_confirme);
		csm_btn_cancel = (Button) findViewById(R.id.csm_btn_cancel);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart_set_menu, menu);
		return true;
	}

}
