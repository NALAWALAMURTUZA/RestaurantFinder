package com.rf_user.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Registration_step3_Activity extends Activity {

	public static Spinner sp_cuisine;
	public static EditText rf_regi_step3_ed_strong_points;
	public static EditText rf_regi_step3_ed_anecdote;
	public static TextView rf_txv_service;
	public static TextView txv_credit_cards;
	public static EditText rf_regi_step3_ed_starter1;
	public static EditText rf_regi_step3_ed_starter1_price1;
	public static EditText rf_regi_step3_ed_starter2;
	public static EditText rf_regi_step3_ed_starter2_price2;
	public static EditText rf_regi_step3_ed_starter3;
	public static EditText rf_regi_step3_ed_starter3_price3;
	public static EditText rf_regi_step3_ed_course1;
	public static EditText rf_regi_step3_ed_course1_price1;
	public static EditText rf_regi_step3_ed_course2;
	public static EditText rf_regi_step3_ed_course2_price2;
	public static EditText rf_regi_step3_ed_course3;
	public static EditText rf_regi_step3_ed_course3_price3;
	public static EditText rf_regi_step3_ed_dessert1;
	public static EditText rf_regi_step3_ed_dessert1_price1;
	public static EditText rf_regi_step3_ed_dessert2;
	public static EditText rf_regi_step3_ed_dessert2_price2;
	public static EditText rf_regi_step3_ed_dessert3;
	public static EditText rf_regi_step3_ed_dessert3_price3;
	public static EditText rf_regi_step3_ed_wine_glss_min_price;
	public static EditText rf_regi_step3_ed_wine_glss_max_price;
	public static EditText rf_regi_step3_ed_wine_bottel_min_price;
	public static EditText rf_regi_step3_ed_wine_bottel_max_price;
	public static EditText rf_regi_step3_ed_bottel_of_water_min;
	public static EditText rf_regi_step3_ed_bottel_of_water_max;
	public static EditText rf_regi_step3_ed_glass_of_champ_max;
	public static EditText rf_regi_step3_ed_glass_of_champ_min;
	public static EditText rf_regi_step3_ed_coffee_min;
	public static EditText rf_regi_step3_ed_coffee_max;
	public static EditText rf_regi_step3_ed_comment;
	public static Button rf_regi_step3_btn_continue;
	public static Dialog ServiceDialog, CreditCardDialog;
	public static ListView lv_service, lv_creditcards;
	ServiceAdapter ServiceAdapter;
	CreditCardsAdapter CreditCardsAdapter;
	public static ProgressDialog p;
	public static String str_select_service;
	public static JSONObject obj_restaurant_app_service, obj_restaurant_app;
	public static ArrayList<String> restaurant_category_arraylist;
	public static ArrayList<HashMap<String, String>> hash_restaurant_category_arraylist;
	public static HashMap<String, String> hashmap_restaurant_category_arraylist;
	public static String str_category_id, str_category_name,
			str_selected_category_name, str_selected_category_id;
	public static JSONArray array_credit_card;
	ConnectionDetector cd;

	public String str_glass_min, str_glass_max, str_bottel_min, str_bottel_max,
			str_bottel_water_min, str_bottel_water_max, str_champ_min,
			str_champ_max, str_coffee_min, str_coffee_max;
	public int int_glass_min, int_glass_max, int_bottel_min, int_bottel_max,
			int_bottel_water_min, int_bottel_water_max, int_champ_min,
			int_champ_max, int_coffee_min, int_coffee_max;
	Boolean continue_flage = true;
	public String str_selected_cards;
	private Locale myLocale;  
	// String f = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_registration_step3_);
		cd = new ConnectionDetector(getApplicationContext());
		obj_restaurant_app_service = new JSONObject();
		obj_restaurant_app = new JSONObject();
		initialization();
		Set_RestaurantCategory();
		setlistner();
		Locale.getDefault().getLanguage();
		System.out.println("Device_language"+Locale.getDefault().getLanguage());
		

		String langPref = "Language";
    	SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	String language = prefs_oncreat.getString(langPref, "");
    	
  
    	System.out.println("Murtuza_Nalawala_language_oncreat"+language);
    	if(language.equalsIgnoreCase(""))
    	{
    		System.out.println("Murtuza_Nalawala_language_oncreat_if");
    		
    	}
    	else if(language.equalsIgnoreCase("ro"))
    	{
    		System.out.println("Murtuza_Nalawala_language_oncreat_if_ar");
    		setLocaleonload("ro");
    		
    	}
    	else if(language.equalsIgnoreCase("en"))
    	{
    		System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
    		setLocaleonload("en");
    		
    	}
    	else
    	{
    		System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
    		setLocaleonload("en");
    		
    	}
		

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		
		
//	  	loadLocale();
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
	public void loadLocale()
    {
		
		System.out.println("Murtuza_Nalawala");
    	String langPref = "Language";
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	String language = prefs.getString(langPref, "");
    	System.out.println("Murtuza_Nalawala_language"+language);
    	
    	
    	changeLang(language);
    }
	public void changeLang(String lang)
    {
		System.out.println("Murtuza_Nalawala_changeLang");
		
    	if (lang.equalsIgnoreCase(""))
    		return;
    	myLocale = new Locale(lang);
    	System.out.println("Murtuza_Nalawala_123456"+myLocale);
    	if (myLocale.toString().equalsIgnoreCase("en")) 
    	{
    		System.out.println("Murtuza_Nalawala_language_if"+myLocale);
    		
		}
    	else if(myLocale.toString().equalsIgnoreCase("ar")) 
    	{
    		System.out.println("Murtuza_Nalawala_language_else"+myLocale);
    		System.out.println("IN_arabic");
    		
    	}
    	saveLocale(lang);
    	DBAdapter.deleteall();
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
       // updateTexts();
       
    }
    
    public void saveLocale(String lang)
    {
    	
    	String langPref = "Language";
    	System.out.println("Murtuza_Nalawala_langPref_if"+langPref);
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	System.out.println("Murtuza_Nalawala_langPref_prefs"+prefs);
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

	

	private void Set_RestaurantCategory() {
		// TODO Auto-generated method stub
		restaurant_category_arraylist = new ArrayList<String>();
		hash_restaurant_category_arraylist = new ArrayList<HashMap<String, String>>();
		if (Global_variable.array_restaurantcategory.length() != 0) {
			for (int i = 0; i < Global_variable.array_restaurantcategory
					.length(); i++) {
				try {
					JSONObject json_object = Global_variable.array_restaurantcategory
							.getJSONObject(i);
					System.out.println("1111objectRestaurantCategory"
							+ json_object);
					hashmap_restaurant_category_arraylist = new HashMap<String, String>();
					str_category_id = json_object.getString("id");
					str_category_name = json_object.getString("name_en");
					System.out.println("categoryidname" + str_category_id
							+ str_category_name);

					hashmap_restaurant_category_arraylist.put(
							"str_category_id", str_category_id);
					hashmap_restaurant_category_arraylist.put(
							"str_category_name", str_category_name);

					restaurant_category_arraylist.add(str_category_name);
					hash_restaurant_category_arraylist
							.add(hashmap_restaurant_category_arraylist);
					System.out.println("1111restaurant_category_arraylist.."
							+ restaurant_category_arraylist);

					sp_cuisine.setAdapter(new ArrayAdapter<String>(
							Registration_step3_Activity.this,
							android.R.layout.simple_spinner_dropdown_item,
							restaurant_category_arraylist));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void setlistner() {
		// TODO Auto-generated method stub
		rf_txv_service.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ServiceDialog();
			}
		});
		txv_credit_cards.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CreditCardsDialog();

			}
		});
		rf_regi_step3_btn_continue
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						boolean flag = true;
						if (rf_txv_service.getText().toString()
								.equalsIgnoreCase("Select Service")) {
							flag = false;
							Toast.makeText(Registration_step3_Activity.this,
									getString(R.string.str_Select_Service), Toast.LENGTH_LONG).show();
						}
						if (txv_credit_cards.getText().toString()
								.equalsIgnoreCase("Select Cards")) {
							flag = false;

							Toast.makeText(Registration_step3_Activity.this,
									getString(R.string.str_select_cards), Toast.LENGTH_LONG).show();
						}
						// ********************************************
						// glass wine*
						if (rf_regi_step3_ed_wine_glss_max_price.getText()
								.toString().length() != 0) {

							if (rf_regi_step3_ed_wine_glss_min_price.getText()
									.toString().length() != 0) {
								str_glass_min = "";
								str_glass_max = "";
								str_glass_min = rf_regi_step3_ed_wine_glss_min_price
										.getText().toString();
								int_glass_min = Integer.parseInt(str_glass_min);

								str_glass_max = rf_regi_step3_ed_wine_glss_max_price
										.getText().toString();
								int_glass_max = Integer.parseInt(str_glass_max);
								if (int_glass_min > int_glass_max) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_glass_wine_larger),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						if (rf_regi_step3_ed_wine_glss_min_price.getText()
								.toString().length() != 0) {
							if (rf_regi_step3_ed_wine_glss_max_price.getText()
									.toString().length() != 0) {
								str_glass_min = "";
								str_glass_max = "";
								str_glass_min = rf_regi_step3_ed_wine_glss_min_price
										.getText().toString();
								int_glass_min = Integer.parseInt(str_glass_min);

								str_glass_max = rf_regi_step3_ed_wine_glss_max_price
										.getText().toString();
								int_glass_max = Integer.parseInt(str_glass_max);
								if (int_glass_max < int_glass_min) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_glass_wine_larger),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						// *wine bottle
						if (rf_regi_step3_ed_wine_bottel_max_price.getText()
								.toString().length() != 0) {

							if (rf_regi_step3_ed_wine_bottel_min_price
									.getText().toString().length() != 0) {
								str_bottel_min = "";
								str_bottel_max = "";
								str_bottel_min = rf_regi_step3_ed_wine_bottel_min_price
										.getText().toString();
								int_bottel_min = Integer
										.parseInt(str_bottel_min);

								str_bottel_max = rf_regi_step3_ed_wine_bottel_max_price
										.getText().toString();
								int_bottel_max = Integer
										.parseInt(str_bottel_max);
								if (int_bottel_min > int_bottel_max) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_bottle_wine_larger),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						if (rf_regi_step3_ed_wine_bottel_min_price.getText()
								.toString().length() != 0) {
							if (rf_regi_step3_ed_wine_bottel_max_price
									.getText().toString().length() != 0) {
								str_bottel_min = "";
								str_bottel_max = "";
								str_bottel_min = rf_regi_step3_ed_wine_bottel_min_price
										.getText().toString();
								int_bottel_min = Integer
										.parseInt(str_bottel_min);

								str_bottel_max = rf_regi_step3_ed_wine_bottel_max_price
										.getText().toString();
								int_bottel_max = Integer
										.parseInt(str_bottel_max);
								if (int_bottel_max < int_bottel_min) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_bottle_wine_larger),
											Toast.LENGTH_LONG).show();
								}
							}
						}

						// ****water bottle*****
						if (rf_regi_step3_ed_bottel_of_water_max.getText()
								.toString().length() != 0) {

							if (rf_regi_step3_ed_bottel_of_water_min.getText()
									.toString().length() != 0) {
								str_bottel_water_min = "";
								str_bottel_water_max = "";
								str_bottel_water_min = rf_regi_step3_ed_bottel_of_water_min
										.getText().toString();
								int_bottel_water_min = Integer
										.parseInt(str_bottel_water_min);

								str_bottel_water_max = rf_regi_step3_ed_bottel_of_water_max
										.getText().toString();
								int_bottel_water_max = Integer
										.parseInt(str_bottel_water_max);
								if (int_bottel_water_min > int_bottel_water_max) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_1litter),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						if (rf_regi_step3_ed_bottel_of_water_min.getText()
								.toString().length() != 0) {
							if (rf_regi_step3_ed_bottel_of_water_max.getText()
									.toString().length() != 0) {
								str_bottel_water_min = "";
								str_bottel_water_max = "";
								str_bottel_water_min = rf_regi_step3_ed_bottel_of_water_min
										.getText().toString();
								int_bottel_water_min = Integer
										.parseInt(str_bottel_water_min);

								str_bottel_water_max = rf_regi_step3_ed_bottel_of_water_max
										.getText().toString();
								int_bottel_water_max = Integer
										.parseInt(str_bottel_water_max);
								if (int_bottel_water_max < int_bottel_water_min) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_1litter),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						// champ*************
						if (rf_regi_step3_ed_glass_of_champ_max.getText()
								.toString().length() != 0) {

							if (rf_regi_step3_ed_glass_of_champ_min.getText()
									.toString().length() != 0) {
								str_champ_min = "";
								str_champ_max = "";
								str_champ_min = rf_regi_step3_ed_glass_of_champ_min
										.getText().toString();
								int_champ_min = Integer.parseInt(str_champ_min);

								str_champ_max = rf_regi_step3_ed_glass_of_champ_max
										.getText().toString();
								int_champ_max = Integer.parseInt(str_champ_max);
								if (int_champ_min > int_champ_max) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_Price_champagne),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						if (rf_regi_step3_ed_glass_of_champ_min.getText()
								.toString().length() != 0) {
							if (rf_regi_step3_ed_glass_of_champ_max.getText()
									.toString().length() != 0) {
								str_champ_min = "";
								str_champ_max = "";
								str_champ_min = rf_regi_step3_ed_glass_of_champ_min
										.getText().toString();
								int_champ_min = Integer.parseInt(str_champ_min);

								str_champ_max = rf_regi_step3_ed_glass_of_champ_max
										.getText().toString();
								int_champ_max = Integer.parseInt(str_champ_max);
								if (int_champ_max < int_champ_min) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_Price_champagne),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						// coffee*****************

						if (rf_regi_step3_ed_coffee_max.getText().toString()
								.length() != 0) {

							if (rf_regi_step3_ed_coffee_min.getText()
									.toString().length() != 0) {
								str_coffee_min = "";
								str_coffee_max = "";
								str_coffee_min = rf_regi_step3_ed_coffee_min
										.getText().toString();
								int_coffee_min = Integer
										.parseInt(str_coffee_min);

								str_coffee_max = rf_regi_step3_ed_coffee_max
										.getText().toString();
								int_coffee_max = Integer
										.parseInt(str_coffee_max);
								if (int_coffee_min > int_coffee_max) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_coffee),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						if (rf_regi_step3_ed_coffee_min.getText().toString()
								.length() != 0) {
							if (rf_regi_step3_ed_coffee_max.getText()
									.toString().length() != 0) {
								str_coffee_min = "";
								str_coffee_max = "";
								str_coffee_min = rf_regi_step3_ed_coffee_min
										.getText().toString();
								int_coffee_min = Integer
										.parseInt(str_coffee_min);

								str_coffee_max = rf_regi_step3_ed_coffee_max
										.getText().toString();
								int_coffee_max = Integer
										.parseInt(str_coffee_max);
								if (int_coffee_max < int_coffee_min) {
									flag = false;
									Toast.makeText(
											Registration_step3_Activity.this,
											getString(R.string.str_price_coffee),
											Toast.LENGTH_LONG).show();
								}
							}
						}
						// ***********************

						if (flag == true) {
							if (cd.isConnectingToInternet()) {

								new async_regi_step3().execute();
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
					}
				});
		sp_cuisine.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextSize(14);
				str_selected_category_name = sp_cuisine.getSelectedItem()
						.toString();
				int str_cusineID = sp_cuisine.getSelectedItemPosition();

				str_selected_category_id = hash_restaurant_category_arraylist
						.get(str_cusineID).get("str_category_id");
				System.out.println("1111selectedcusine"
						+ str_selected_category_id);
				System.out.println("1111hashmapstr_cusineID" + str_cusineID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void initialization() {

		sp_cuisine = (Spinner) findViewById(R.id.rf_regi_step3_ed_cuisine);
		rf_regi_step3_ed_strong_points = (EditText) findViewById(R.id.rf_regi_step3_ed_strong_points);
		rf_regi_step3_ed_anecdote = (EditText) findViewById(R.id.rf_regi_step3_ed_anecdote);
		rf_txv_service = (TextView) findViewById(R.id.rf_txv_service);
		txv_credit_cards = (TextView) findViewById(R.id.sp_rf_credit_cards);
		rf_regi_step3_ed_starter1 = (EditText) findViewById(R.id.rf_regi_step3_ed_starter1);
		rf_regi_step3_ed_starter1_price1 = (EditText) findViewById(R.id.rf_regi_step3_ed_starter1_price1);
		rf_regi_step3_ed_starter2 = (EditText) findViewById(R.id.rf_regi_step3_ed_starter2);
		rf_regi_step3_ed_starter2_price2 = (EditText) findViewById(R.id.rf_regi_step3_ed_starter2_price2);
		rf_regi_step3_ed_starter3 = (EditText) findViewById(R.id.rf_regi_step3_ed_starter3);
		rf_regi_step3_ed_starter3_price3 = (EditText) findViewById(R.id.rf_regi_step3_ed_starter3_price3);
		rf_regi_step3_ed_course1 = (EditText) findViewById(R.id.rf_regi_step3_ed_course1);
		rf_regi_step3_ed_course1_price1 = (EditText) findViewById(R.id.rf_regi_step3_ed_course1_price1);
		rf_regi_step3_ed_course2 = (EditText) findViewById(R.id.rf_regi_step3_ed_course2);
		rf_regi_step3_ed_course2_price2 = (EditText) findViewById(R.id.rf_regi_step3_ed_course2_price2);
		rf_regi_step3_ed_course3 = (EditText) findViewById(R.id.rf_regi_step3_ed_course3);
		rf_regi_step3_ed_course3_price3 = (EditText) findViewById(R.id.rf_regi_step3_ed_course3_price3);
		rf_regi_step3_ed_dessert1 = (EditText) findViewById(R.id.rf_regi_step3_ed_dessert1);
		rf_regi_step3_ed_dessert1_price1 = (EditText) findViewById(R.id.rf_regi_step3_ed_dessert1_price1);
		rf_regi_step3_ed_dessert2 = (EditText) findViewById(R.id.rf_regi_step3_ed_dessert2);
		rf_regi_step3_ed_dessert2_price2 = (EditText) findViewById(R.id.rf_regi_step3_ed_dessert2_price2);
		rf_regi_step3_ed_dessert3 = (EditText) findViewById(R.id.rf_regi_step3_ed_dessert3);
		rf_regi_step3_ed_dessert3_price3 = (EditText) findViewById(R.id.rf_regi_step3_ed_dessert3_price3);
		rf_regi_step3_ed_wine_glss_min_price = (EditText) findViewById(R.id.rf_regi_step3_ed_wine_glass_min_price);
		rf_regi_step3_ed_wine_glss_max_price = (EditText) findViewById(R.id.rf_regi_step3_ed_wine_glss_max_price);
		rf_regi_step3_ed_wine_bottel_min_price = (EditText) findViewById(R.id.rf_regi_step3_ed_wine_bottel_min_price);
		rf_regi_step3_ed_wine_bottel_max_price = (EditText) findViewById(R.id.rf_regi_step3_ed_wine_bottel_max_price);
		rf_regi_step3_ed_bottel_of_water_min = (EditText) findViewById(R.id.rf_regi_step3_ed_half_bootel_price);
		rf_regi_step3_ed_bottel_of_water_max = (EditText) findViewById(R.id.rf_regi_step3_ed_bottel_of_water);
		rf_regi_step3_ed_glass_of_champ_min = (EditText) findViewById(R.id.rf_regi_step3_ed_min_glass_of_champ);
		rf_regi_step3_ed_glass_of_champ_max = (EditText) findViewById(R.id.rf_regi_step3_ed_max_bottel_of_champ);
		rf_regi_step3_ed_coffee_min = (EditText) findViewById(R.id.rf_regi_step3_min_ed_coffee);
		rf_regi_step3_ed_coffee_max = (EditText) findViewById(R.id.rf_regi_step3_max_ed_coffee);
		rf_regi_step3_ed_comment = (EditText) findViewById(R.id.rf_regi_step3_ed_comment);
		rf_regi_step3_btn_continue = (Button) findViewById(R.id.rf_regi_step3_btn_continue);
		rf_regi_step3_ed_comment.setText(Global_variable.comment);
		// str_select_service = rf_txv_service.getText().toString();
		// ******************************minimum max

		// **********glass minim
		rf_regi_step3_ed_wine_glss_min_price
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_wine_glss_max_price
										.getText().toString().length() != 0) {

									if (rf_regi_step3_ed_wine_glss_min_price
											.getText().toString().length() != 0) {
										str_glass_min = "";
										str_glass_max = "";
										str_glass_min = rf_regi_step3_ed_wine_glss_min_price
												.getText().toString();
										int_glass_min = Integer
												.parseInt(str_glass_min);

										str_glass_max = rf_regi_step3_ed_wine_glss_max_price
												.getText().toString();
										int_glass_max = Integer
												.parseInt(str_glass_max);
										if (int_glass_min > int_glass_max) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_glass_wine_larger),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
						}
					}
				});
		// ******max
		rf_regi_step3_ed_wine_glss_max_price
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_wine_glss_min_price
										.getText().toString().length() != 0) {
									if (rf_regi_step3_ed_wine_glss_max_price
											.getText().toString().length() != 0) {
										str_glass_min = "";
										str_glass_max = "";
										str_glass_min = rf_regi_step3_ed_wine_glss_min_price
												.getText().toString();
										int_glass_min = Integer
												.parseInt(str_glass_min);

										str_glass_max = rf_regi_step3_ed_wine_glss_max_price
												.getText().toString();
										int_glass_max = Integer
												.parseInt(str_glass_max);
										if (int_glass_max < int_glass_min) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_glass_wine_larger),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}

						}
					}
				});
		// *******************glass minimum max
		// ************bottle min
		rf_regi_step3_ed_wine_bottel_min_price
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_wine_bottel_max_price
										.getText().toString().length() != 0) {

									if (rf_regi_step3_ed_wine_bottel_min_price
											.getText().toString().length() != 0) {
										str_bottel_min = "";
										str_bottel_max = "";
										str_bottel_min = rf_regi_step3_ed_wine_bottel_min_price
												.getText().toString();
										int_bottel_min = Integer
												.parseInt(str_bottel_min);

										str_bottel_max = rf_regi_step3_ed_wine_bottel_max_price
												.getText().toString();
										int_bottel_max = Integer
												.parseInt(str_bottel_max);
										if (int_bottel_min > int_bottel_max) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_bottle_wine_larger),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
						}
					}
				});
		// bottelw max******
		rf_regi_step3_ed_wine_bottel_max_price
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_wine_bottel_min_price
										.getText().toString().length() != 0) {
									if (rf_regi_step3_ed_wine_bottel_max_price
											.getText().toString().length() != 0) {
										str_bottel_min = "";
										str_bottel_max = "";
										str_bottel_min = rf_regi_step3_ed_wine_bottel_min_price
												.getText().toString();
										int_bottel_min = Integer
												.parseInt(str_bottel_min);

										str_bottel_max = rf_regi_step3_ed_wine_bottel_max_price
												.getText().toString();
										int_bottel_max = Integer
												.parseInt(str_bottel_max);
										if (int_bottel_max < int_bottel_min) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_bottle_wine_larger),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}

						}
					}
				});
		// bottel max**************
		// ****************************************************************************
		// bottle water ************
		rf_regi_step3_ed_bottel_of_water_min
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_bottel_of_water_max
										.getText().toString().length() != 0) {

									if (rf_regi_step3_ed_bottel_of_water_min
											.getText().toString().length() != 0) {
										str_bottel_water_min = "";
										str_bottel_water_max = "";
										str_bottel_water_min = rf_regi_step3_ed_bottel_of_water_min
												.getText().toString();
										int_bottel_water_min = Integer
												.parseInt(str_bottel_water_min);

										str_bottel_water_max = rf_regi_step3_ed_bottel_of_water_max
												.getText().toString();
										int_bottel_water_max = Integer
												.parseInt(str_bottel_water_max);
										if (int_bottel_water_min > int_bottel_water_max) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_1litter),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
						}
					}
				});
		// bottelw water max******
		rf_regi_step3_ed_bottel_of_water_max
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_bottel_of_water_min
										.getText().toString().length() != 0) {
									if (rf_regi_step3_ed_bottel_of_water_max
											.getText().toString().length() != 0) {
										str_bottel_water_min = "";
										str_bottel_water_max = "";
										str_bottel_water_min = rf_regi_step3_ed_bottel_of_water_min
												.getText().toString();
										int_bottel_water_min = Integer
												.parseInt(str_bottel_water_min);

										str_bottel_water_max = rf_regi_step3_ed_bottel_of_water_max
												.getText().toString();
										int_bottel_water_max = Integer
												.parseInt(str_bottel_water_max);
										if (int_bottel_water_max < int_bottel_water_min) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_1litter),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}

						}
					}
				});

		// bootle of water ***************
		// **************************************************************
		// champ****************************
		rf_regi_step3_ed_glass_of_champ_min
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_glass_of_champ_max
										.getText().toString().length() != 0) {

									if (rf_regi_step3_ed_glass_of_champ_min
											.getText().toString().length() != 0) {
										str_champ_min = "";
										str_champ_max = "";
										str_champ_min = rf_regi_step3_ed_glass_of_champ_min
												.getText().toString();
										int_champ_min = Integer
												.parseInt(str_champ_min);

										str_champ_max = rf_regi_step3_ed_glass_of_champ_max
												.getText().toString();
										int_champ_max = Integer
												.parseInt(str_champ_max);
										if (int_champ_min > int_champ_max) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_Price_champagne),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
						}
					}
				});
		// champ max******
		rf_regi_step3_ed_glass_of_champ_max
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_glass_of_champ_min
										.getText().toString().length() != 0) {
									if (rf_regi_step3_ed_glass_of_champ_max
											.getText().toString().length() != 0) {
										str_champ_min = "";
										str_champ_max = "";
										str_champ_min = rf_regi_step3_ed_glass_of_champ_min
												.getText().toString();
										int_champ_min = Integer
												.parseInt(str_champ_min);

										str_champ_max = rf_regi_step3_ed_glass_of_champ_max
												.getText().toString();
										int_champ_max = Integer
												.parseInt(str_champ_max);
										if (int_champ_max < int_champ_min) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_Price_champagne),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}

						}
					}
				});

		// champ***************************
		// ****************************************************
		// coffeee*******************

		rf_regi_step3_ed_coffee_min
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_coffee_max.getText()
										.toString().length() != 0) {

									if (rf_regi_step3_ed_coffee_min.getText()
											.toString().length() != 0) {
										str_coffee_min = "";
										str_coffee_max = "";
										str_coffee_min = rf_regi_step3_ed_coffee_min
												.getText().toString();
										int_coffee_min = Integer
												.parseInt(str_coffee_min);

										str_coffee_max = rf_regi_step3_ed_coffee_max
												.getText().toString();
										int_coffee_max = Integer
												.parseInt(str_coffee_max);
										if (int_coffee_min > int_coffee_max) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_coffee),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
						}
					}
				});

		// coffee max******
		rf_regi_step3_ed_coffee_max
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// code to execute when EditText loses focus
							try {

								if (rf_regi_step3_ed_coffee_min.getText()
										.toString().length() != 0) {
									if (rf_regi_step3_ed_coffee_max.getText()
											.toString().length() != 0) {
										str_coffee_min = "";
										str_coffee_max = "";
										str_coffee_min = rf_regi_step3_ed_coffee_min
												.getText().toString();
										int_coffee_min = Integer
												.parseInt(str_coffee_min);

										str_coffee_max = rf_regi_step3_ed_coffee_max
												.getText().toString();
										int_coffee_max = Integer
												.parseInt(str_coffee_max);
										if (int_coffee_max < int_coffee_min) {
											Toast.makeText(
													Registration_step3_Activity.this,
													getString(R.string.str_price_coffee),
													Toast.LENGTH_LONG).show();
										}
									}
								}
							} catch (NullPointerException n) {
								n.printStackTrace();
							}

						}
					}
				});
		// coffee*********
		// *************************
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_step3_, menu);
		return true;
	}

	public void ServiceDialog() {

		ServiceDialog = new Dialog(Registration_step3_Activity.this);
		ServiceDialog.setContentView(R.layout.popup_service);
		ServiceDialog.setTitle(R.string.str_rf_regi_step3_popup_title);
		lv_service = (ListView) ServiceDialog.findViewById(R.id.lv_service);
		try {
			if (Global_variable.array_Services.length() != 0)

			{
				System.out.println("PlateChangeAdapter_ELSEif");
				ServiceAdapter = new ServiceAdapter(
						Registration_step3_Activity.this,
						Global_variable.array_Services);
				lv_service.setAdapter(ServiceAdapter);

			} else {
				Toast.makeText(Registration_step3_Activity.this,
						getString(R.string.str_Service_not_available), Toast.LENGTH_LONG).show();
			}
			ServiceDialog.show();

			ServiceDialog.setCancelable(true);
			ServiceDialog.setCanceledOnTouchOutside(true);
			int int_service_select = obj_restaurant_app_service.length();
			String str_selected_service = String.valueOf(int_service_select);
			System.out.println("1111serviceadapterint_string_service"
					+ int_service_select + str_selected_service);
			// rf_txv_service.setText("You Select" + " " + str_selected_service
			// + " " + "Service");
		} catch (NullPointerException n) {

		}
	}

	public void CreditCardsDialog() {
		CreditCardDialog = new Dialog(Registration_step3_Activity.this);
		CreditCardDialog.setContentView(R.layout.popup_creditcards);
		CreditCardDialog.setTitle(R.string.str_rf_regi_step3_popup_creditcards);
		lv_creditcards = (ListView) CreditCardDialog
				.findViewById(R.id.lv_creditcards);
		try {
			if (Global_variable.array_payment_method.length() != 0)

			{
				System.out.println("step3_popup_creditcards");
				CreditCardsAdapter = new CreditCardsAdapter(
						Registration_step3_Activity.this,
						Global_variable.array_payment_method);
				lv_creditcards.setAdapter(CreditCardsAdapter);

			} else {
				Toast.makeText(Registration_step3_Activity.this,
						getString(R.string.str_CreditCards_not_available), Toast.LENGTH_LONG).show();
			}
			CreditCardDialog.show();

			// txv_credit_cards.setText("You Select" + " " +
			// str_selected_creditcard
			// + " " + "Card");
			// txv_credit_cards.setText(CreditCardsAdapter.str_checked_creditcard_Name);
			CreditCardDialog.setCancelable(true);
			CreditCardDialog.setCanceledOnTouchOutside(true);
			int int_credit_select = obj_restaurant_app.length();
			String str_selected_creditcard = String.valueOf(int_credit_select);
			System.out.println("1111creditcardadapterint_string_creditcard"
					+ int_credit_select + str_selected_creditcard);
//			 txv_credit_cards.setText("You Select" + " "
//			 + str_selected_creditcard + " " + "Card");
		} catch (NullPointerException n) {

		}
	}

	public class async_regi_step3 extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_restaurant_drinks;
		JSONObject obj_restaurant_app_set_menu;
		JSONObject obj_restaurantregistrationstep3;
		JSONObject obj_MainRequest;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(Registration_step3_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			obj_restaurant_drinks = new JSONObject();
			// obj_restaurant_app = new JSONObject();

			obj_restaurant_app_set_menu = new JSONObject();
			obj_restaurantregistrationstep3 = new JSONObject();
			obj_MainRequest = new JSONObject();

			try {
				// obj_restaurant_app_set_menu*********
				obj_restaurant_app_set_menu.put("starter_1",
						rf_regi_step3_ed_starter1.getText().toString());
				obj_restaurant_app_set_menu.put("starter_price_1",
						rf_regi_step3_ed_starter1_price1.getText().toString());
				obj_restaurant_app_set_menu.put("starter_2",
						rf_regi_step3_ed_starter2.getText().toString());
				obj_restaurant_app_set_menu.put("starter_price_2",
						rf_regi_step3_ed_starter2_price2.getText().toString());
				obj_restaurant_app_set_menu.put("starter_3",
						rf_regi_step3_ed_starter3.getText().toString());
				obj_restaurant_app_set_menu.put("starter_price_3",
						rf_regi_step3_ed_starter3_price3.getText().toString());
				obj_restaurant_app_set_menu.put("main_course_1",
						rf_regi_step3_ed_course1.getText().toString());
				obj_restaurant_app_set_menu.put("main_course_price_1",
						rf_regi_step3_ed_course1_price1.getText().toString());
				obj_restaurant_app_set_menu.put("main_course_2",
						rf_regi_step3_ed_course2.getText().toString());
				obj_restaurant_app_set_menu.put("main_course_price_2",
						rf_regi_step3_ed_course2_price2.getText().toString());
				obj_restaurant_app_set_menu.put("main_course_3",
						rf_regi_step3_ed_course3.getText().toString());
				obj_restaurant_app_set_menu.put("main_course_price_3",
						rf_regi_step3_ed_course3_price3.getText().toString());
				obj_restaurant_app_set_menu.put("desserts_1",
						rf_regi_step3_ed_dessert1.getText().toString());
				obj_restaurant_app_set_menu.put("desserts_price_1",
						rf_regi_step3_ed_dessert1_price1.getText().toString());
				obj_restaurant_app_set_menu.put("desserts_2",
						rf_regi_step3_ed_dessert2.getText().toString());
				obj_restaurant_app_set_menu.put("desserts_price_2",
						rf_regi_step3_ed_dessert2_price2.getText().toString());
				obj_restaurant_app_set_menu.put("desserts_3",
						rf_regi_step3_ed_dessert3.getText().toString());
				obj_restaurant_app_set_menu.put("desserts_price_3",
						rf_regi_step3_ed_dessert3_price3.getText().toString());

				System.out.println("1111obj_restaurant_app_set_menu"
						+ obj_restaurant_app_set_menu);
				// obj_restaurant_app_set_menu*********

				// obj_restaurant_drinks*********
				obj_restaurant_drinks.put("glass_wine_min_price",
						rf_regi_step3_ed_wine_glss_min_price.getText()
								.toString());
				obj_restaurant_drinks.put("glass_wine_max_price",
						rf_regi_step3_ed_wine_glss_max_price.getText()
								.toString());
				obj_restaurant_drinks.put("bottle_wine_min_price",
						rf_regi_step3_ed_wine_bottel_min_price.getText()
								.toString());
				obj_restaurant_drinks.put("bottle_wine_max_price",
						rf_regi_step3_ed_wine_bottel_max_price.getText()
								.toString());
				obj_restaurant_drinks.put("water_bottle_min_price",
						rf_regi_step3_ed_bottel_of_water_min.getText()
								.toString());
				obj_restaurant_drinks.put("water_bottle_max_price",
						rf_regi_step3_ed_bottel_of_water_max.getText()
								.toString());
				obj_restaurant_drinks.put("glass_champion_min_price",
						rf_regi_step3_ed_glass_of_champ_min.getText()
								.toString());
				obj_restaurant_drinks.put("glass_champion_max_price",
						rf_regi_step3_ed_glass_of_champ_max.getText()
								.toString());
				obj_restaurant_drinks.put("cofee_min_price",
						rf_regi_step3_ed_coffee_min.getText().toString());
				obj_restaurant_drinks.put("cofee_max_price",
						rf_regi_step3_ed_coffee_max.getText().toString());
				System.out.println("1111step3obj_restaurant_drinks"
						+ obj_restaurant_drinks);
				// obj_restaurant_drinks*********
				// obj_restaurant_app********

				System.out.println("!!!!obj_restaurant_app"
						+ obj_restaurant_app);
				str_selected_cards = "";

				if (obj_restaurant_app.length() != 0) {
					for (int i = 0; i < obj_restaurant_app.length(); i++) {
						if (obj_restaurant_app.has(i + "")) {
							try {
								str_selected_cards += obj_restaurant_app.get(i + "") + ",";
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					try {
						str_selected_cards = str_selected_cards.substring(0, str_selected_cards.length() - 1);
					} catch (IndexOutOfBoundsException i) {
						i.printStackTrace();
					}
					System.out.println("!!!!ffff" + str_selected_cards);

					try {
//						 obj_restaurant_app = new JSONObject();
						obj_restaurant_app.put("accepted_credit_cards",
								str_selected_cards);
						System.out.println("obj_restaurant_app"+obj_restaurant_app);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				System.out.println("!!!!obj_restaurant_app_after"
						+ obj_restaurant_app);

				obj_restaurant_app.put("cuisine", str_selected_category_id);
				obj_restaurant_app.put("restaurant_strong_point",
						rf_regi_step3_ed_strong_points.getText().toString());
				obj_restaurant_app.put("special_anecdote",
						rf_regi_step3_ed_anecdote.getText().toString());

				System.out.println("1111checkcreditcard"
						+ CreditCardsAdapter.str_checked_creditcard_Name);
//				 obj_restaurant_app.put("accepted_credit_cards",
//				 CreditCardsAdapter.str_checked_creditcard_Name);
				System.out.println("1111obj_restaurant_app"
						+ obj_restaurant_app);
				// obj_restaurant_app********

				/** obj_restaurant_app_service ********/

				obj_restaurant_app_service.put(
						ServiceAdapter.str_checked_service_ID,
						ServiceAdapter.str_checked_service_Name);
				System.out.println("1111obj_restaurant_app_service"
						+ obj_restaurant_app_service);

				// obj_restaurant_app_service********

				// obj_restaurantregistrationstep3***************
				obj_restaurantregistrationstep3.put("restaurant_services",
						obj_restaurant_app_service);
				obj_restaurantregistrationstep3.put("restaurant_app",
						obj_restaurant_app);
				obj_restaurantregistrationstep3.put("set_menu",
						obj_restaurant_app_set_menu);
				obj_restaurantregistrationstep3.put("drinks",
						obj_restaurant_drinks);
				System.out.println("1111obj_restaurantregistrationstep3"
						+ obj_restaurantregistrationstep3);
				// obj_restaurantregistrationstep3***************
				// obj_MainRequest*******************************

				System.out
						.println("1111Global_variable.restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);
				System.out
						.println("1111Global_variable.restaurantregistrationstep2"
								+ Global_variable.restaurantregistrationstep2);
				System.out.println("1111step2sessid" + Global_variable.sessid);
				obj_MainRequest.put("restaurantregistrationstep1",
						Global_variable.restaurantregistrationstep1);
				obj_MainRequest.put("restaurantregistrationstep2",
						Global_variable.restaurantregistrationstep2);
				obj_MainRequest.put("restaurantregistrationstep3",
						obj_restaurantregistrationstep3);
				obj_MainRequest.put("sessid", Global_variable.sessid);
				obj_MainRequest.put("comment", rf_regi_step3_ed_comment
						.getText().toString());

				System.out
						.println("1111obj_MainRequeststep3" + obj_MainRequest);

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
					String str_response = con.connection_rest_reg(Registration_step3_Activity.this,
							Global_variable.rf_api_registrationstep3,
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
					System.out.println("1111step3comments"
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
						Global_variable.restaurantregistrationstep3 = Data
								.getJSONObject("restaurantregistrationstep3");
						System.out.println("1111restaurantregistrationstep3"
								+ Global_variable.restaurantregistrationstep3);

						RegistrationTablayout.tab.setCurrentTab(3);
						RegistrationTablayout.tab.getTabWidget()
								.getChildAt(3).setClickable(true);
						// Registration_step4_Activity.ed_step4_comments.setText("");
						Registration_step4_Activity.ed_step4_comments
								.setText(Global_variable.comment);
						Registration_step2_Activity.ed_add_comments
								.setText(Global_variable.comment);

					}
				} else {
					JSONObject Error = json.getJSONObject("errors");
					System.out.println("1111errors" + Error);
					if (Error != null) {
						if (Error.has("cuisine")) {
							JSONArray cuisine = Error.getJSONArray("cuisine");
							System.out.println("1111cuisine" + cuisine);
							if (cuisine != null) {
								String str_cuisine = cuisine.getString(0);
								System.out.println("1111str_cuisine"
										+ str_cuisine);
								Toast.makeText(
										Registration_step3_Activity.this,
										str_cuisine, Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("restaurant_strong_point")) {
							JSONArray restaurant_strong_point = Error
									.getJSONArray("restaurant_strong_point");
							System.out.println("1111restaurant_strong_point"
									+ restaurant_strong_point);
							if (restaurant_strong_point != null) {
								String str_restaurant_strong_point = restaurant_strong_point
										.getString(0);
								System.out
										.println("1111str_restaurant_strong_point"
												+ str_restaurant_strong_point);
								Toast.makeText(
										Registration_step3_Activity.this,
										str_restaurant_strong_point,
										Toast.LENGTH_LONG).show();
							}

						}
						if (Error.has("special_anecdote")) {
							JSONArray special_anecdote = Error
									.getJSONArray("special_anecdote");
							System.out.println("1111special_anecdote"
									+ special_anecdote);
							if (special_anecdote != null) {
								String str_special_anecdote = special_anecdote
										.getString(0);
								System.out
										.println("1111str_restaurant_strong_point"
												+ str_special_anecdote);
								Toast.makeText(
										Registration_step3_Activity.this,
										str_special_anecdote, Toast.LENGTH_LONG)
										.show();
							}

						}
						// ///******************starter
						if (Error.has("starter_1")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("starter_1").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("starter_2")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("starter_2").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("starter_3")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("starter_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("starter_price_1")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("starter_price_1")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Error.has("starter_price_2")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("starter_price_2")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Error.has("starter_price_3")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("starter_price_3")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						// ///******************starter

						// ///******************main_course
						if (Error.has("main_course_1")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("main_course_1").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("main_course_2")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("main_course_2").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("main_course_3")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("main_course_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("main_course_price_1")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("main_course_price_1")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Error.has("main_course_price_2")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("main_course_price_2")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Error.has("main_course_price_3")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("main_course_price_3")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						// ///******************main_course

						// ///******************desserts
						if (Error.has("desserts_1")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("desserts_1").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("desserts_2")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("desserts_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("desserts_3")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("desserts_3").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Error.has("desserts_price_1")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("desserts_price_1")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("desserts_price_2")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("desserts_price_2")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("desserts_price_3")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("desserts_price_3")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						// ******************************step2
						if (Error.has("glass_wine_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("glass_wine_min_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("glass_wine_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("glass_wine_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}

						if (Error.has("bottle_wine_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("bottle_wine_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("water_bottle_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("water_bottle_min_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}

						if (Error.has("water_bottle_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("water_bottle_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("glass_champion_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray(
											"glass_champion_min_price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}

						if (Error.has("glass_champion_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray(
											"glass_champion_max_price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Error.has("cofee_min_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("cofee_min_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("cofee_max_price")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("cofee_max_price")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Error.has("accepted_credit_cards")) {
							Toast.makeText(
									getApplicationContext(),
									Error.getJSONArray("accepted_credit_cards")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}

						// ******************************
						// ///******************desserts

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
			case KeyEvent.KEYCODE_BACK :
				onBackPressed();
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {

			RegistrationTablayout.tab.setCurrentTab(1);
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
}
