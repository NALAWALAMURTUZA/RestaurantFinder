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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.food_detail.food_catagory_categories;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Food_Detail_Categories_Activity extends Activity {

	food_catagory_categories food_catagory_categories;
	ListView lv_categories;
	ConnectionDetector cd;
	public static ImageView img_home;
	private Locale myLocale;
	public static ProgressDialog p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_food_list);
		cd = new ConnectionDetector(getApplicationContext());
		initialization();
		secOnclicklistner();
		

		if (cd.isConnectingToInternet()) {
			
			System.out.println("!!!!!!!!!!!!Global_variable.array_food"+Global_variable.array_food);
			
			if(Global_variable.array_food == null)
			{
				
				new selectrestaurantfood().execute();
			}
			else
			{
				System.out.println("loginflag"+Global_variable.loginflag);
				if(Global_variable.loginflag==false)
				{
					new selectrestaurantfood().execute();
				}
				else
				{
				System.out.println("!!!!!!!!!!!!!!in normal else length 0");
				
				food_catagory_categories = new food_catagory_categories(
						Food_Detail_Categories_Activity.this,
						Global_variable.array_restaurantcategory,Global_variable.array_food);
				lv_categories.setAdapter(food_catagory_categories);
				}
			}
			
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


	private void initialization() {
		// TODO Auto-generated method stub
		lv_categories = (ListView) findViewById(R.id.lv_categories);
		img_home = (ImageView) findViewById(R.id.img_home);
	}

	private void secOnclicklistner() {
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Food_Detail_Categories_Activity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		lv_categories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("my pozition" + position);
				// if(Global_variable.array_food.length()!=0)
				if (cd.isConnectingToInternet()) {
					{
						try {
							System.out.println(getparticularcategoryfood(
									Global_variable.array_restaurantcategory
											.getJSONObject(position)
											.getString("id").toString(),
									Global_variable.array_food));
							Intent in = new Intent(
									Food_Detail_Categories_Activity.this,
									Food_Detail_Parent_Activity.class);
							Global_variable.selected_categories = Global_variable.array_restaurantcategory
									.getJSONObject(position).getString("id");
							in.putExtra(
									"categories_name",
									Global_variable.array_restaurantcategory
											.getJSONObject(position)
											.getString("name_en").toString());
							startActivity(in);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// else
					{

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
	}

	public JSONArray getparticularcategoryfood(String id, JSONArray json)
			throws JSONException {
		Global_variable.array_parentfood = new JSONArray();
		for (int i = 0; i < json.length(); i++) {
			System.out.println("restid"
					+ json.getJSONObject(i).getString("foodCategory_id"));
			if (id.equalsIgnoreCase(json.getJSONObject(i).getString(
					"foodCategory_id"))) {
				Global_variable.array_parentfood.put(json.getJSONObject(i));
				// int l = Global_variable.array_parentfood.length();
				// System.out.println("lengthcateparent" + l);
				// String str = String.valueOf(l);
				// System.out.println("cate"+str);
				// food_catagory_categories.rf_categories.setText(str);
			} else {
			}
		}
		System.out.println("my parent" + Global_variable.array_parentfood);
		return Global_variable.array_parentfood;
	}

	/**** AsyncClass */
	// /
	public class selectrestaurantfood extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		
		protected void onPreExecute() {
			p = new ProgressDialog(Food_Detail_Categories_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			try {
				// obj_parent.put("restaurant_id", "50");
				obj_parent.put("restaurant_id", Global_variable.restaurant_id);
				obj_parent.put("sessid", Global_variable.sessid.toString());
				System.out.println("selectrestaurantfood" + obj_parent);
				myconnection con = new myconnection();
				obj_output = new JSONObject(
						con.connection(Food_Detail_Categories_Activity.this,
								Global_variable.rf_api_selectrestaurantfood,
								obj_parent));
				System.out.println("output" + obj_output);
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
			System.out.println("output" + obj_output);
			try {
				if (obj_output.getString("success").equalsIgnoreCase("false")) {

				} else {
					boolean flag = true;
					if (obj_output.has("data")) {
						if (obj_output.getJSONObject("data")
								.getString("success").equalsIgnoreCase("false")) {
							JSONObject errors = new JSONObject();
							errors = obj_output.getJSONObject("data")
									.getJSONObject("errors");
							if (errors.has("username")) {
								// Toast.makeText(Food_Detail_Src1_Activity,errors.getJSONArray("username").get(0).toString(),
								// Toast.LENGTH_LONG).show();
							}
							if (errors.has("password")) {
								// Toast.makeText(Food_Detail_Src1_Activity,errors.getJSONArray("password").get(0).toString(),
								// Toast.LENGTH_LONG).show();
							}
						} else {
							Global_variable.array_food = obj_output
									.getJSONObject("data").getJSONArray("food");
							System.out.println(Global_variable.array_food);
							food_catagory_categories = new food_catagory_categories(
									Food_Detail_Categories_Activity.this,
									Global_variable.array_restaurantcategory,Global_variable.array_food);
							lv_categories.setAdapter(food_catagory_categories);

						}
					}
					if (p.isShowing()) {
						p.dismiss();
					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// @Override
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
			Intent in = new Intent(Food_Detail_Categories_Activity.this,
					Home_Activity.class);
			//
			startActivity(in);
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
