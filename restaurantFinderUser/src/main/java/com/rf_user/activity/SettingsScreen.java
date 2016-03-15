package com.rf_user.activity;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class SettingsScreen extends Activity {

	private ImageView back_imageview;
	private ImageView search_imageview_logo;
	private ImageView search_imageview;
	private ImageView search_select_imageview;
	private LinearLayout search_layout;
	private TextView Search_Restaurant_Textview;
	private LinearLayout search_spinner_linear_layout;
	private EditText search_edittext;
	private ImageView search_Layout_imageview;
	private Spinner city_spinner;
	private Spinner restaurant_list_spinner;
	private LinearLayout food_list_spinner_linear_layout;
	private LinearLayout delivery_option_layout;
	private TextView delivery_option_textview;
	private Spinner delivery_option_spinner;
	private LinearLayout categories_layout;
	private TextView categories_textview;
	private Spinner categories_spinner;
	private LinearLayout order_time_layout;
	private TextView minimumtime_order_textview;
	private Spinner minimum_time_order_spinner;
	private TextView sortby_textview;
	private TextView sortby_select_textview;
	private TextView name_textview;
	private TextView name_select_textview;
	private TextView newest_textview;
	private TextView newest_select_textview;
	private TextView top_rated_textview;
	private TextView top_rated_select_textview;
	private ImageView imageView1;
	private LinearLayout cateories_listview_contents;
	private ImageView img_my_booking;
	private LinearLayout categries_listview_contents;
	private ImageView img_myprofile;
	private LinearLayout categories_listiew_contents;
	private ImageView img_network;
	private LinearLayout categorieslistview_contents;
	private ImageView img_aboutrest;
	private ImageView img_logout;
	private LinearLayout categories_listview_contents;
	private LinearLayout footer;
	private ImageView footer_ordernow_img;
	private ImageView footer_viewmenu_img;
	private ImageView footer_featured_img;
	private ImageView footer_setting_img;
	ImageView img_change_password;
	ImageView img_loyalty_rewards;

	Intent in;

	HttpConnection http = new HttpConnection();

	ConnectionDetector cd;

	String TAG_SUCCESS = "success";
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_settings_screen);

		/* create Object* */
		try {
			cd = new ConnectionDetector(getApplicationContext());

			initialize();
			setlistner();
			
			loadLocale();

		} catch (NullPointerException n) {
			n.printStackTrace();
		}
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

		try{
			
			img_loyalty_rewards.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(getApplicationContext(), LoyaltyRewardsScreen.class);
					startActivity(i);
					
				}
			});
			
		
		
		search_imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				in = new Intent(getApplicationContext(),
						Search_Restaurant_List.class);
				startActivity(in);

			}
		});
		
		img_change_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				in = new Intent(getApplicationContext(),
						ChangePassword.class);
				startActivity(in);

			}
		});

		img_my_booking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						in = new Intent(getApplicationContext(),
								MyBooking.class);
						startActivity(in);
					} 
					}else {
						Toast.makeText(getApplicationContext(), R.string.please_login,
								Toast.LENGTH_SHORT).show();

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		img_myprofile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						in = new Intent(getApplicationContext(),
								MyProfile.class);
						startActivity(in);
					} 
					}else {
						Toast.makeText(getApplicationContext(), R.string.please_login,
								Toast.LENGTH_SHORT).show();

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		img_network.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						in = new Intent(getApplicationContext(),
								MyNetworking.class);
						startActivity(in);
					}
					}else {
						Toast.makeText(getApplicationContext(), R.string.please_login,
								Toast.LENGTH_SHORT).show();

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		img_aboutrest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+SharedPreference.getuser_id(getApplicationContext()));
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+Global_variable.abt_rest_flag);
						
						if (Global_variable.abt_rest_flag == true) {
							in = new Intent(getApplicationContext(),
									AboutRestaurant.class);
							startActivity(in);
						}

					}
					}else {
						Toast.makeText(getApplicationContext(), R.string.please_login,
								Toast.LENGTH_SHORT).show();

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		img_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							new UserLogout(SettingsScreen.this).execute();

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
					}else {
						Toast.makeText(getApplicationContext(), R.string.please_login,
								Toast.LENGTH_SHORT).show();

					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		footer_featured_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(SharedPreference.getuser_id(
							getApplicationContext())!="")
					{
					if (SharedPreference.getuser_id(getApplicationContext())
							.length() != 0) {
						Global_variable.activity = "Settings";

						Intent in = new Intent(getApplicationContext(),
								MyFavourites.class);
						startActivity(in);
					}
					}else {
						Toast.makeText(getApplicationContext(), R.string.please_login,
								Toast.LENGTH_SHORT).show();
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}

		});

		footer_viewmenu_img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/** check Internet Connectivity */
				try {
					if (cd.isConnectingToInternet()) {

						// TODO Auto-generated method stub
						Intent i = new Intent(SettingsScreen.this,
								Search_Restaurant_List.class);
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
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
		
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}

	}

	/* User LogsOut */

	class user_logout extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(SettingsScreen.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		@Override
		protected Void doInBackground(Void... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject LogoutForm = new JSONObject();
						if(SharedPreference.getuser_id(
								getApplicationContext())!="")
						{
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							LogoutForm.put("user_id", SharedPreference
									.getuser_id(getApplicationContext()));
						}
						}else {
							LogoutForm.put("user_id", "");
						}

						System.out.println("user_id" + LogoutForm);

						LogoutForm.put("sessid", SharedPreference
								.getsessid(getApplicationContext()));
						System.out.println("session_id" + LogoutForm);
						// *************

						try {

							String responseText = http
									.connection(SettingsScreen.this,
											 Global_variable.rf_userlogout_api_path,
											LogoutForm);

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
			//
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/

		@Override
		protected void onPostExecute(Void result) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag!!!!!!!!!" + success1);

				JSONObject data = json.getJSONObject("data");

				System.out.println("!!!!!!!!!!!data.." + data);

				// String Data_Success = data.getString(TAG_SUCCESS);
				// System.out.println("Data tag" + Data_Success);
				// ******** data succsess

				if (success1.equals("true")) {

					System.out.println("1" + data);

					if (data.length() != 0) {

						System.out.println("2.." + data);

						String success = data.getString("success");

						System.out.println("3.." + data + ".." + success);

						if (success.equals("true")) {

							System.out.println("4.." + data);

							String message = data.getString("message");

							System.out.println("5.." + data);

							Toast.makeText(getApplicationContext(), message,
									Toast.LENGTH_LONG).show();

							System.out.println("6.." + data);

							Intent in = new Intent(SettingsScreen.this,
									Search_Restaurant_List.class);

							System.out.println("7.." + data);

							SharedPreference.setuser_id(
									getApplicationContext(), "");
							Global_variable.login_user_id="";

							System.out.println("8.." + data);

							System.out.println("!!!!!!!!!!!!!!!!shikha...."
									+ SharedPreference
									.getuser_id(getApplicationContext()));
							startActivity(in);

						}

					}

				}

				// **** invalid output
				else {
					if (success1.equalsIgnoreCase("false")) {
						JSONObject Data_Error = data.getJSONObject("errors");

						String success = data.getString("success");

						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("user_id")) {
							JSONArray Array_user_id = Data_Error
									.getJSONArray("user_id");
							System.out.println("Array_email" + Array_user_id);
							String Str_user_id = Array_user_id.getString(0);
							System.out.println("Str_email" + Str_user_id);
							if (Str_user_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_user_id, Toast.LENGTH_LONG).show();
							}
						}

						else if (Data_Error.has("sessid")) {
							JSONArray Array_sessid = Data_Error
									.getJSONArray("sessid");
							System.out.println("Array_email" + Array_sessid);
							String Str_sessid = Array_sessid.getString(0);
							System.out.println("Str_sessid" + Str_sessid);
							if (Str_sessid != null) {
								Toast.makeText(getApplicationContext(),
										Str_sessid, Toast.LENGTH_LONG).show();
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

	private void initialize() {
		
		back_imageview = (ImageView) findViewById(R.id.back_imageview);
		search_imageview = (ImageView) findViewById(R.id.search_imageview);
		search_imageview = (ImageView) findViewById(R.id.search_imageview);
		search_select_imageview = (ImageView) findViewById(R.id.search_select_imageview);
		search_layout = (LinearLayout) findViewById(R.id.search_layout);
		Search_Restaurant_Textview = (TextView) findViewById(R.id.Search_Restaurant_Textview);
		search_spinner_linear_layout = (LinearLayout) findViewById(R.id.search_spinner_linear_layout);
		search_edittext = (EditText) findViewById(R.id.search_edittext);
		search_Layout_imageview = (ImageView) findViewById(R.id.search_Layout_imageview);
		city_spinner = (Spinner) findViewById(R.id.city_spinner);
		restaurant_list_spinner = (Spinner) findViewById(R.id.restaurant_list_spinner);
		food_list_spinner_linear_layout = (LinearLayout) findViewById(R.id.food_list_spinner_linear_layout);
		delivery_option_layout = (LinearLayout) findViewById(R.id.delivery_option_layout);
		delivery_option_textview = (TextView) findViewById(R.id.delivery_option_textview);
		delivery_option_spinner = (Spinner) findViewById(R.id.delivery_option_spinner);
		categories_layout = (LinearLayout) findViewById(R.id.categories_layout);
		categories_textview = (TextView) findViewById(R.id.categories_textview);
		categories_spinner = (Spinner) findViewById(R.id.categories_spinner);
		order_time_layout = (LinearLayout) findViewById(R.id.order_time_layout);
		minimumtime_order_textview = (TextView) findViewById(R.id.minimumtime_order_textview);
		minimum_time_order_spinner = (Spinner) findViewById(R.id.minimum_time_order_spinner);
		sortby_textview = (TextView) findViewById(R.id.sortby_textview);
		sortby_select_textview = (TextView) findViewById(R.id.sortby_select_textview);
		name_textview = (TextView) findViewById(R.id.name_textview);
		name_select_textview = (TextView) findViewById(R.id.name_select_textview);
		newest_textview = (TextView) findViewById(R.id.newest_textview);
		newest_select_textview = (TextView) findViewById(R.id.newest_select_textview);
		top_rated_textview = (TextView) findViewById(R.id.top_rated_textview);
		top_rated_select_textview = (TextView) findViewById(R.id.top_rated_select_textview);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		img_my_booking = (ImageView) findViewById(R.id.img_my_booking);
		img_myprofile = (ImageView) findViewById(R.id.img_myprofile);
		img_network = (ImageView) findViewById(R.id.img_network);
		img_aboutrest = (ImageView) findViewById(R.id.img_aboutrest);
		img_logout = (ImageView) findViewById(R.id.img_logout);
		img_logout = (ImageView) findViewById(R.id.img_logout);
		footer = (LinearLayout) findViewById(R.id.footer);
		footer_ordernow_img = (ImageView) findViewById(R.id.footer_ordernow_img);
		footer_viewmenu_img = (ImageView) findViewById(R.id.footer_viewmenu_img);
		footer_featured_img = (ImageView) findViewById(R.id.footer_featured_img);
		footer_setting_img = (ImageView) findViewById(R.id.footer_setting_img);
		img_change_password= (ImageView)findViewById(R.id.img_change_password);
	
		img_loyalty_rewards=(ImageView)findViewById(R.id.img_loyalty_rewards);
	
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
