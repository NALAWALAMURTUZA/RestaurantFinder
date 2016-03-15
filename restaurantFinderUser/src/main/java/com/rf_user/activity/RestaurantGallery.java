package com.rf_user.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.GridViewAdapter;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class RestaurantGallery extends Activity {

	private GridView gridView;
	private GridViewAdapter gridAdapter;
	LinearLayout ly_No_Gallery;

	/*** Network Connection Instance **/
	ConnectionDetector cd;

	HttpConnection http = new HttpConnection();

	String TAG_SUCCESS = "success";

	String[] arr_restaurant_banner_id, arr_restaurant_id, arr_banner_name,
			arr_description;
	ArrayList<ImageItem> image_model;
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_restaurant_gallery);

		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			/* instance of internet connection class */
			cd = new ConnectionDetector(getApplicationContext());

			initialize();

			/** check Internet Connectivity */
			if (cd.isConnectingToInternet()) {

				new manage_restaurant_gallery().execute();

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

		} catch (NullPointerException e)

		{
			e.printStackTrace();
		}

		setlistner();
		
		loadLocale();

	}
	
	/* Language conversion methods */
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

	private void setlistner() {
		// TODO Auto-generated method stub

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				ImageItem item = (ImageItem) parent.getItemAtPosition(position);

				// Create intent
				Intent intent = new Intent(RestaurantGallery.this,
						RestaurantImageDetail.class);
				// intent.putExtra("title", item.getTitle());
				intent.putExtra("image", item.getBanner_name());

				// Start details activity
				startActivity(intent);

			}
		});

	}

	private void initialize() {
		// TODO Auto-generated method stub
		gridView = (GridView) findViewById(R.id.gridView);
		ly_No_Gallery = (LinearLayout) findViewById(R.id.ly_No_Gallery);
	}

	class manage_restaurant_gallery extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		JSONObject banner_obj = new JSONObject();
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(RestaurantGallery.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			image_model = new ArrayList<ImageItem>();

			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject main_obj = new JSONObject();
						JSONObject restaurant_banner = new JSONObject();

						if (Global_variable.hotel_id.length() != 0) {
							restaurant_banner.put("restaurant_id",
									Global_variable.hotel_id);
						}

						else {
							restaurant_banner.put("restaurant_id", "");
						}

						restaurant_banner.put("operation", "fetch");

						main_obj.put("restaurant_banner", restaurant_banner);
						System.out.println("main_obj" + main_obj);

						if (SharedPreference.getsessid(getApplicationContext())
								.length() != 0) {
							main_obj.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
						} else {
							main_obj.put("sessid", "");
						}

						System.out.println("main_obj" + main_obj);
						// *************

						String responseText = http
								.connection(RestaurantGallery.this,
										 Global_variable.rf_manage_restaurant_gallery_api_path,
										main_obj);

						try {

							System.out.println("after_connection.."
									+ responseText);

							json = new JSONObject(responseText);
							System.out.println("responseText" + json);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			});

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(Void result) {

			dialog.isShowing();
			dialog.dismiss();

			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				
				// String Data_Success = data.getString(TAG_SUCCESS);
				// System.out.println("Data tag" + Data_Success);
				// ******** data succsess

				if (success1.equals("true")) {
					JSONObject data = json.getJSONObject("data");
					if (data.length() != 0) {

						JSONArray restaurant_banner = new JSONArray();

						restaurant_banner = data
								.getJSONArray("restaurant_banner");

						arr_restaurant_banner_id = new String[restaurant_banner
								.length()];
						arr_restaurant_id = new String[restaurant_banner
								.length()];
						arr_banner_name = new String[restaurant_banner.length()];
						arr_description = new String[restaurant_banner.length()];

						int length = restaurant_banner.length();

						for (int i = 0; i < restaurant_banner.length(); i++) {

							System.out.println("!!!!!!!!banner_obj"
									+ restaurant_banner.getJSONObject(i));

							banner_obj = restaurant_banner.getJSONObject(i);

							System.out.println("!!!!!!!!banner_obj"
									+ restaurant_banner.getJSONObject(i));

							arr_restaurant_banner_id[i] = banner_obj
									.getString("restaurant_banner_id");
							arr_restaurant_id[i] = banner_obj
									.getString("restaurant_id");
							arr_banner_name[i] = banner_obj
									.getString("banner_name");
							arr_description[i] = banner_obj
									.getString("description");

							// Bitmap bitmap =
							// BitmapFactory.decodeResource(getResources(),
							// imgs.getResourceId(i, -1));

							ImageItem model = new ImageItem(
									arr_restaurant_banner_id[i],
									arr_restaurant_id[i], arr_banner_name[i],
									arr_description[i]);
							image_model.add(model);

						}

						System.out.println("!!!!!!!!!!!!!!image_model...."
								+ image_model);

						gridAdapter = new GridViewAdapter(
								RestaurantGallery.this, image_model);
						gridView.setAdapter(gridAdapter);

						// Toast.makeText(getApplicationContext(),
						// "Fetch Detail Successful", Toast.LENGTH_LONG)
						// .show();
					} else {
						ly_No_Gallery.setVisibility(View.VISIBLE);
						gridView.setVisibility(View.GONE);
					}

				}

				// **** invalid output
				else {
					
					ly_No_Gallery.setVisibility(View.VISIBLE);
					gridView.setVisibility(View.GONE);
					
					if (success1.equalsIgnoreCase("false")) {
						JSONObject Data_Error = json.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("restaurant_id")) {
							JSONArray Array_restaurant_id = Data_Error
									.getJSONArray("restaurant_id");
							System.out.println("Array_restaurant_id"
									+ Array_restaurant_id);
							String Str_restaurant_id = Array_restaurant_id
									.getString(0);
							System.out.println("Str_restaurant_id" + Str_restaurant_id);
							if (Str_restaurant_id != null) {
//								Toast.makeText(getApplicationContext(),
//										Str_restaurant_id, Toast.LENGTH_LONG)
//										.show();
							}
						}
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
}
