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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.food_detail.food_catagory_categories_child;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Food_Detail_Child_Activity extends Activity {
	public static ProgressDialog p;
	public static TextView rf_admin_heade_cat_name;
	static food_catagory_categories_child food_catagory_categories_child;
	static ListView lv_childfood;
	public static EditText edt_child_name, edt_child_discription,
			edt_child_price;
	String categories_name = null;
	JSONArray jsonArray_child = null;
	public static String id = null, foodname = null;;
	public static String update_uid = null, update_id = null;
	public static Spinner spin_available, spin_child_level;
	public static ArrayAdapter<CharSequence> adapter_spiner_available;
	public static ArrayAdapter<CharSequence> adapter_spiner_level;
	public static ImageView img_add_update;
	private Locale myLocale;

	Intent intent;
	ConnectionDetector cd;
	public static Activity activity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_child);
		initialization();
		secOnclicklistner();
		intent = getIntent();
		foodname = intent.getStringExtra("foodname");
		categories_name = intent.getStringExtra("categories_name");
		id = intent.getStringExtra("id");
		cd = new ConnectionDetector(getApplicationContext());
		rf_admin_heade_cat_name.setText(categories_name + "-> "
				+ foodname.substring(0, 1).toUpperCase()
				+ foodname.substring(1));
		activity = Food_Detail_Child_Activity.this;
		// System.out.println("pozition"+id);
		try {
			food_catagory_categories_child = new food_catagory_categories_child(
					Food_Detail_Child_Activity.this, getparticularcategoryfood(
							id, Global_variable.array_parentfood), foodname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lv_childfood.setAdapter(food_catagory_categories_child);

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
	private void secOnclicklistner() {
		// TODO Auto-generated method stub
		img_add_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean flag = true;
				// TODO Auto-generated method stub
				/*
				 * if(edt_child_name.getText().length()==0) { flag=false;
				 * Toast.makeText(Food_Detail_Child_Activity.this,
				 * "Food name cannot be blank", Toast.LENGTH_LONG).show(); }
				 * if(edt_child_discription.getText().length()==0) { flag=false;
				 * Toast.makeText(Food_Detail_Child_Activity.this,
				 * "food description cannot be blank",
				 * Toast.LENGTH_LONG).show(); }
				 * if(edt_child_price.getText().length()==0) { flag=false;
				 * Toast.makeText(Food_Detail_Child_Activity.this,
				 * "Price can not be nill", Toast.LENGTH_LONG).show(); }
				 */

				if (flag == true) {

					if (cd.isConnectingToInternet()) {

						new insertupdatechild().execute();
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
	}

	JSONArray getparticularcategoryfood(String id, JSONArray json)
			throws JSONException {
		for (int i = 0; i < json.length(); i++) {
			System.out
					.println("restid" + json.getJSONObject(i).getString("id"));
			if (id.equalsIgnoreCase(json.getJSONObject(i).getString("id"))) {
				System.out.println("array_child"
						+ json.getJSONObject(i).getJSONArray("child_food"));
				return json.getJSONObject(i).getJSONArray("child_food");
			} else {
			}
		}
		return null;
	}

	private void initialization() {
		// TODO Auto-generated method stub
		lv_childfood = (ListView) findViewById(R.id.lv_childlist);
		edt_child_name = (EditText) findViewById(R.id.edt_child_name);
		edt_child_discription = (EditText) findViewById(R.id.edt_child_discription);
		edt_child_price = (EditText) findViewById(R.id.edt_child_price);
		spin_child_level = (Spinner) findViewById(R.id.spin_child_level);
		spin_available = (Spinner) findViewById(R.id.spin_available);
		img_add_update = (ImageView) findViewById(R.id.img_add_update);
		rf_admin_heade_cat_name = (TextView) findViewById(R.id.rf_admin_heade_cat_name);
		adapter_spiner_available = ArrayAdapter.createFromResource(
				Food_Detail_Child_Activity.this,
				R.array.array_child_avaibility,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_spiner_available
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_available.setAdapter(adapter_spiner_available);

		adapter_spiner_level = ArrayAdapter.createFromResource(
				Food_Detail_Child_Activity.this,
				R.array.array_parent_level_of_test,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_spiner_level
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_child_level.setAdapter(adapter_spiner_level);

	}

	/* AsyncTask */
	public class insertupdatechild extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		protected void onPreExecute() {
			p = new ProgressDialog(Food_Detail_Child_Activity.this);
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
			JSONObject obj_main = new JSONObject();

			try {
				if (update_id == null) {
					obj_parent.put("id", "");
					obj_parent.put("uid", intent.getStringExtra("uid"));
				} else {
					obj_parent.put("id", update_id);
					obj_parent.put("uid", update_uid);
				}
				// obj_parent.put("restaurant_id", "50");
				obj_parent.put("restaurant_id", Global_variable.restaurant_id);
				obj_parent.put("name_en", edt_child_name.getText());
				obj_parent.put("description_en",
						edt_child_discription.getText());
				obj_parent.put("price", edt_child_price.getText());
				obj_parent.put("spicy_level_req_on",
						spin_child_level.getSelectedItemPosition() + 1);
				if (spin_available.getSelectedItemPosition() == 0) {
					obj_parent.put("available", "1");
				} else {
					obj_parent.put("available", "0");
				}
				obj_parent.put("food_id", intent.getStringExtra("id"));
				obj_main.put("child_food", obj_parent);
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);

				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						Food_Detail_Child_Activity.this,
						Global_variable.rf_api_insertupdatechildfood, obj_main));

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
					img_add_update.setImageResource(R.drawable.add_button);
					update_id = null;
					update_uid = null;
					edt_child_name.setText("");
					edt_child_discription.setText("");
					edt_child_price.setText("");
					System.out.println("hi");
					spin_available.setSelection(0);
					spin_child_level.setSelection(0);

					Global_variable.array_food = obj_output.getJSONObject(
							"data").getJSONArray("food");
					System.out.println(Global_variable.array_food);
					Food_Detail_Categories_Activity food_categories = new Food_Detail_Categories_Activity();
					food_categories.getparticularcategoryfood(
							Global_variable.selected_categories,
							Global_variable.array_food);
					// food_catagory_categories_child.notifyDataSetChanged();

					try {
						food_catagory_categories_child = new food_catagory_categories_child(
								Food_Detail_Child_Activity.this,
								getparticularcategoryfood(id,
										Global_variable.array_parentfood),
								foodname);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					lv_childfood.setAdapter(food_catagory_categories_child);
				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("icon")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("icon").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("name_en")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("name_en").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("veg")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("veg").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("description_en")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("description_en")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("spicy_level")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("spicy_level").get(0)
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

	/******* AsyncTask ******/
	public static class deleteparentchildfooddetail
			extends
				AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		protected void onPreExecute() {
			p = new ProgressDialog(activity);
			// p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_main = new JSONObject();
			try {

				// obj_parent.put("restaurant_id",
				// Global_variable.restaurant_id);
				// obj_parent.put("restaurant_id", "50");
				obj_parent.put("restaurant_id", Global_variable.restaurant_id);
				obj_parent.put("type", "C");
				obj_parent
						.put("id",
								com.restaurantadmin.food_detail.food_catagory_categories_child.str_delete_id);
				obj_main.put("delete_object", obj_parent);
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);

				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(
						activity,
						Global_variable.rf_api_deleteparentchildfooddetail,
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
				if (obj_output.getString("success").equalsIgnoreCase("true")) {

					img_add_update.setImageResource(R.drawable.add_button);
					update_id = null;
					update_uid = null;
					edt_child_name.setText("");
					edt_child_discription.setText("");
					edt_child_price.setText("");
					System.out.println("hi");
					spin_available.setSelection(0);
					spin_child_level.setSelection(0);
					Global_variable.array_food = obj_output.getJSONObject(
							"data").getJSONArray("food");
					System.out.println(Global_variable.array_food);
					Food_Detail_Categories_Activity food_categories = new Food_Detail_Categories_Activity();
					Food_Detail_Child_Activity a = new Food_Detail_Child_Activity();
					food_categories.getparticularcategoryfood(
							Global_variable.selected_categories,
							Global_variable.array_food);
					try {
						food_catagory_categories_child = new food_catagory_categories_child(
								activity, a.getparticularcategoryfood(id,
										Global_variable.array_parentfood),
								foodname);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					lv_childfood.setAdapter(food_catagory_categories_child);
				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);

					if (Errors.has("name_en")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("name_en").get(0)
										.toString(), Toast.LENGTH_LONG).show();
					}
					if (Errors.has("price")) {
						Toast.makeText(activity,
								Errors.getJSONArray("price").get(0).toString(),
								Toast.LENGTH_LONG).show();
					}
					if (Errors.has("description_en")) {
						Toast.makeText(
								activity,
								Errors.getJSONArray("description_en").get(0)
										.toString(), Toast.LENGTH_LONG).show();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			Intent in = new Intent(Food_Detail_Child_Activity.this,
					Food_Detail_Parent_Activity.class);
			in.putExtra("categories_name",
					intent.getStringExtra("categories_name"));
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
