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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.async_common_class.UserLogout;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class PostReview extends Activity {

	TextView rf_post_review_rest_name, static_txt, overall_rating;
	EditText edt_comment;
	RatingBar customer_service_Rating, food_Rating, look_Rating,
			cleanliness_Rating, atmosphere_Rating;
	ImageView rf_post_review_submit_button, save, rf_post_review_menu_icon;
	String str_post_review_rest_name, tg_order_id;

	Float float_customer_service_Rating, float_food_Rating, float_look_Rating,
			float_cleanliness_Rating, float_atmosphere_Rating;
	LinearLayout comment_linear, mainlinear;
	String str_customer_service_Rating, str_food_Rating, str_look_Rating,
			str_cleanliness_Rating, str_atmosphere_Rating, str_final_total,
			str_comment;

	String TAG_SUCCESS = "success";
	/*** Network Connection Instance **/
	ConnectionDetector cd;

	Float final_total;

	HttpConnection http = new HttpConnection();

	Intent in;
	
	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_post_review);
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());

			intialize();
			setlistner();
			setdata();
			
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

	public void setLocaleonload(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		System.out.println("Murtuza_Nalawala_deleteall");

	}


	private void setdata() {
		// TODO Auto-generated method stub
		try {
			Intent in = getIntent();
			str_post_review_rest_name = in.getStringExtra("rest_name");
			tg_order_id = in.getStringExtra("tg_order_id");
			if (str_post_review_rest_name != null) {
				rf_post_review_rest_name.setText(str_post_review_rest_name);
			} else {
				rf_post_review_rest_name.setText("");
			}

			// if(tg_order_id!=null)
			// {
			// rf_post_review_rest_name.setText(tg_order_id);
			// }
			// else
			// {
			// rf_post_review_rest_name.setText("");
			// }
			//
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	private void setlistner() {
		// TODO Auto-generated method stub

		try {

			save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						str_comment = edt_comment.getText().toString();

						System.out.println("!!!!!!!!!str_comment.."
								+ str_comment + "!!!tg_order_id.."
								+ tg_order_id);

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							new InsertReview().execute();

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

			rf_post_review_submit_button
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								float_customer_service_Rating = customer_service_Rating
										.getRating();
								float_food_Rating = food_Rating.getRating();
								float_look_Rating = look_Rating.getRating();
								float_cleanliness_Rating = cleanliness_Rating
										.getRating();
								float_atmosphere_Rating = atmosphere_Rating
										.getRating();

								System.out.println("Ratings.."
										+ float_customer_service_Rating + ".."
										+ float_food_Rating + ".."
										+ float_look_Rating + ".."
										+ float_cleanliness_Rating + ".."
										+ float_atmosphere_Rating);

								str_customer_service_Rating = String
										.valueOf(float_customer_service_Rating * 2);
								str_food_Rating = String
										.valueOf(float_food_Rating * 2);
								str_look_Rating = String
										.valueOf(float_look_Rating * 2);
								str_cleanliness_Rating = String
										.valueOf(float_cleanliness_Rating * 2);
								str_atmosphere_Rating = String
										.valueOf(float_atmosphere_Rating * 2);

								final_total = ((float_customer_service_Rating * 2)
										+ (float_food_Rating * 2)
										+ (float_look_Rating * 2)
										+ (float_cleanliness_Rating * 2) + (float_atmosphere_Rating * 2)) / 5;
								System.out.println("!!!!!!!!final_total..."
										+ final_total);

								mainlinear.setVisibility(View.GONE);
								comment_linear.setVisibility(View.VISIBLE);

								str_final_total = String.valueOf(final_total);
								System.out.println("!!!!!str...."
										+ str_final_total);
								overall_rating.setText(str_final_total + "/10");
							} catch (NullPointerException n) {
								n.printStackTrace();
							}
						}
					});

			rf_post_review_menu_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						PopupMenu popup = new PopupMenu(PostReview.this,
								rf_post_review_menu_icon);
						popup.getMenuInflater().inflate(R.menu.popup,
								popup.getMenu());

						popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem item) {
								// TODO Auto-generated method stub

								System.out.println("!!!!!Item"
										+ item.getTitle());

								if (item.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_booking))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyBooking.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_profile))) {
									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyProfile.class);
											startActivity(in);
										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_favourites))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											// Global_variable.activity =
											// "Categories";

											Intent in = new Intent(
													getApplicationContext(),
													MyFavourites.class);
											startActivity(in);
										} 
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();
										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equalsIgnoreCase(
												getString(R.string.my_networking))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											in = new Intent(
													getApplicationContext(),
													MyNetworking.class);
											startActivity(in);
										} 
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item
										.getTitle()
										.toString()
										.equals(getString(R.string.about_restaurant))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {
											if (Global_variable.abt_rest_flag == true) {
												in = new Intent(
														getApplicationContext(),
														AboutRestaurant.class);
												startActivity(in);
											}

										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								else if (item.getTitle().toString()
										.equals(getString(R.string.logout))) {

									try {
										if(SharedPreference.getuser_id(
												getApplicationContext())!="")
										{
										if (SharedPreference
												.getuser_id(getApplicationContext())
												.length() != 0) {

											/** check Internet Connectivity */
											if (cd.isConnectingToInternet()) {

												new UserLogout(PostReview.this)
														.execute();

											} else {

												runOnUiThread(new Runnable() {

													@Override
													public void run() {

														// TODO
														// Auto-generated
														// method stub
														Toast.makeText(
																getApplicationContext(),
																R.string.No_Internet_Connection,
																Toast.LENGTH_SHORT)
																.show();

													}

												});
											}

										}
										}else {
											Toast.makeText(
													getApplicationContext(),
													R.string.please_login,
													Toast.LENGTH_SHORT).show();

										}
									} catch (NullPointerException n) {
										n.printStackTrace();
									}

								}

								return false;
							}
						});

						popup.show();
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
			});

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void intialize() {
		// TODO Auto-generated method stub

		rf_post_review_rest_name = (TextView) findViewById(R.id.rf_post_review_rest_name);
		customer_service_Rating = (RatingBar) findViewById(R.id.customer_service_Rating);
		customer_service_Rating.setClickable(true);
		food_Rating = (RatingBar) findViewById(R.id.food_Rating);
		food_Rating.setClickable(true);
		look_Rating = (RatingBar) findViewById(R.id.look_Rating);
		look_Rating.setClickable(true);
		cleanliness_Rating = (RatingBar) findViewById(R.id.cleanliness_Rating);
		cleanliness_Rating.setClickable(true);
		atmosphere_Rating = (RatingBar) findViewById(R.id.atmosphere_Rating);
		atmosphere_Rating.setClickable(true);

		rf_post_review_submit_button = (ImageView) findViewById(R.id.rf_post_review_submit_button);
		static_txt = (TextView) findViewById(R.id.static_txt);
		overall_rating = (TextView) findViewById(R.id.overall_rating);
		edt_comment = (EditText) findViewById(R.id.edt_comment);

		comment_linear = (LinearLayout) findViewById(R.id.comment_linear);
		mainlinear = (LinearLayout) findViewById(R.id.mainlinear);

		save = (ImageView) findViewById(R.id.save);

		static_txt.setText(R.string.static_txt);

		rf_post_review_menu_icon = (ImageView) findViewById(R.id.rf_post_review_menu_icon);
	}

	/* Insert reviews in db */

	class InsertReview extends AsyncTask<String, String, String> {

		JSONObject json;
		ProgressDialog dialog;
		int loyalty_pts;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(PostReview.this);
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();

		}

		/**
		 * Getting user details in background thread
		 * */
		protected String doInBackground(String... params) {
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject Review_obj = new JSONObject();
						if(SharedPreference.getuser_id(
								getApplicationContext())!="")
						{
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							Review_obj.put("user_id",
									SharedPreference
									.getuser_id(getApplicationContext()));
						} 
						}else {
							Review_obj.put("user_id", "");
						}
						System.out.println("user_id" + Review_obj);

						if (tg_order_id.length() != 0) {
							Review_obj.put("tg_order_id", tg_order_id);
						} else {
							Review_obj.put("tg_order_id", "");
						}

						System.out.println("tg_order_id" + Review_obj);

						if (str_customer_service_Rating.length() != 0) {
							Review_obj.put("customer_services",
									str_customer_service_Rating);
						} else {
							Review_obj.put("customer_services", "");
						}

						System.out.println("customer_services" + Review_obj);

						if (str_food_Rating.length() != 0) {
							Review_obj.put("food", str_food_Rating);
						} else {
							Review_obj.put("food", "");
						}

						System.out.println("food" + Review_obj);

						if (str_look_Rating.length() != 0) {
							Review_obj.put("look", str_look_Rating);
						} else {
							Review_obj.put("look", "");
						}

						System.out.println("look" + Review_obj);

						if (str_cleanliness_Rating.length() != 0) {
							Review_obj.put("cleanliness",
									str_cleanliness_Rating);
						} else {
							Review_obj.put("cleanliness", "");
						}

						System.out.println("cleanliness" + Review_obj);

						if (str_atmosphere_Rating.length() != 0) {
							Review_obj.put("atmosphere", str_atmosphere_Rating);
						} else {
							Review_obj.put("atmosphere", "");
						}

						System.out.println("atmosphere" + Review_obj);

						if (str_final_total.length() != 0) {
							Review_obj.put("order_rating", str_final_total);
						} else {
							Review_obj.put("order_rating", "");
						}

						System.out.println("order_rating" + Review_obj);

						if (str_comment.length() != 0) {
							Review_obj.put("order_comment", str_comment);
						} else {
							Review_obj.put("order_comment", "");
						}
						try{
							
							if(SharedPreference.get_user_loyalty_pts(getApplicationContext())!="")
							{
								loyalty_pts =Integer.parseInt(SharedPreference.get_user_loyalty_pts(getApplicationContext()))+Global_variable.lp_to_tg_customer; 
							}
							else
							{
								loyalty_pts =0+Global_variable.lp_to_tg_customer; 
							}
							
							if (loyalty_pts != 0) {
								Review_obj.put("loyalty", loyalty_pts);
							} else {
								Review_obj.put("loyalty", "");
							}

						}
						catch(NumberFormatException e)
						{
							e.printStackTrace();
						}
						
						System.out.println("order_comment" + Review_obj);

						Review_obj.put("sessid", SharedPreference
								.getsessid(getApplicationContext()));
						System.out.println("session_id" + Review_obj);
						// *************

						String responseText = http
								.connection(PostReview.this,
										 Global_variable.rf_InsertReview_api_path,
										Review_obj);

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
			//
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			dialog.isShowing();
			dialog.dismiss();

			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONObject data = json.getJSONObject("data");

				// ******** data succsess

				if (success1.equals("true")) {
					if (data.length() != 0) {

						String message = data.getString("message");

						
						
						SharedPreference.set_user_loyalty_pts(getApplicationContext(), loyalty_pts+"");

						Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_LONG).show();
						Intent in = new Intent(getApplicationContext(),
								MyBooking.class);
						
						Global_variable.activity="PostReview";
						
						startActivity(in);
						
					}

				}

				else {
					JSONObject error_obj = json.getJSONObject("errors");
					String Data_Success = error_obj.getString(TAG_SUCCESS);
					System.out.println("Data tag" + Data_Success);

					if (Data_Success.equalsIgnoreCase("false")) {
						// JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("error_obj" + error_obj);

						if (error_obj.has("user_id")) {
							JSONArray Array_user_id = error_obj
									.getJSONArray("user_id");
							System.out.println("Array_user_id" + Array_user_id);
							String Str_user_id = Array_user_id.getString(0);
							System.out.println("Str_user_id" + Str_user_id);
							if (Str_user_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_user_id, Toast.LENGTH_LONG).show();
							}
						} else if (error_obj.has("tg_order_id")) {
							JSONArray Array_tg_order_id = error_obj
									.getJSONArray("tg_order_id");
							System.out.println("Array_tg_order_id"
									+ Array_tg_order_id);
							String Str_tg_order_id = Array_tg_order_id
									.getString(0);
							System.out.println("Str_email" + Str_tg_order_id);
							if (Str_tg_order_id != null) {
								Toast.makeText(getApplicationContext(),
										Str_tg_order_id, Toast.LENGTH_LONG)
										.show();
							}
						}

						else if (error_obj.has("sessid")) {
							JSONArray Array_sessid = error_obj
									.getJSONArray("sessid");
							System.out.println("Array_sessid" + Array_sessid);
							String Str_sessid = Array_sessid.getString(0);
							System.out.println("Str_sessid" + Str_sessid);
							if (Str_sessid != null) {
								Toast.makeText(getApplicationContext(),
										Str_sessid, Toast.LENGTH_LONG).show();
							}
						}

						else if (error_obj.has("customer_services")) {
							JSONArray Array_customer_services = error_obj
									.getJSONArray("customer_services");
							System.out.println("Array_customer_services"
									+ Array_customer_services);
							String Str_customer_services = Array_customer_services
									.getString(0);
							System.out.println("Str_customer_services"
									+ Str_customer_services);
							if (Str_customer_services != null) {
								Toast.makeText(getApplicationContext(),
										Str_customer_services,
										Toast.LENGTH_LONG).show();
							}
						}

						else if (error_obj.has("food")) {
							JSONArray Array_food = error_obj
									.getJSONArray("food");
							System.out.println("Array_food" + Array_food);
							String Str_food = Array_food.getString(0);
							System.out.println("Str_email" + Str_food);
							if (Str_food != null) {
								Toast.makeText(getApplicationContext(),
										Str_food, Toast.LENGTH_LONG).show();
							}
						}

						else if (error_obj.has("look")) {
							JSONArray Array_look = error_obj
									.getJSONArray("look");
							System.out.println("Array_look" + Array_look);
							String Str_look = Array_look.getString(0);
							System.out.println("Str_look" + Str_look);
							if (Str_look != null) {
								Toast.makeText(getApplicationContext(),
										Str_look, Toast.LENGTH_LONG).show();
							}
						}

						else if (error_obj.has("clensiness")) {
							JSONArray Array_clensiness = error_obj
									.getJSONArray("clensiness");
							System.out.println("Array_clensiness"
									+ Array_clensiness);
							String Str_clensiness = Array_clensiness
									.getString(0);
							System.out.println("Str_clensiness"
									+ Str_clensiness);
							if (Str_clensiness != null) {
								Toast.makeText(getApplicationContext(),
										Str_clensiness, Toast.LENGTH_LONG)
										.show();
							}
						}

						else if (error_obj.has("atmosphere")) {
							JSONArray Array_atmosphere = error_obj
									.getJSONArray("atmosphere");
							System.out.println("Array_atmosphere"
									+ Array_atmosphere);
							String Str_atmosphere = Array_atmosphere
									.getString(0);
							System.out.println("Str_atmosphere"
									+ Str_atmosphere);
							if (Str_atmosphere != null) {
								Toast.makeText(getApplicationContext(),
										Str_atmosphere, Toast.LENGTH_LONG)
										.show();
							}
						}

						else if (error_obj.has("order_ratting")) {
							JSONArray Array_order_ratting = error_obj
									.getJSONArray("order_ratting");
							System.out.println("Array_order_ratting"
									+ Array_order_ratting);
							String Str_order_ratting = Array_order_ratting
									.getString(0);
							System.out.println("Str_order_ratting"
									+ Str_order_ratting);
							if (Str_order_ratting != null) {
								Toast.makeText(getApplicationContext(),
										Str_order_ratting, Toast.LENGTH_LONG)
										.show();
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
