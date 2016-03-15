package com.rf_user.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.MyBookedCartAdpater;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class MyBookedCart extends Activity {

	Intent in;
	TextView txv_user_FirstName, txv_user_Email, txv_user_ContactNumber,
			txv_payment_status, txv_user_address;

	public TextView receipt_txv_final_total, receipt_txv_delivery_charge;

	ListView LV_booked_cart_Cart_Details;
	String first_name, last_name, email, contact_number, status, address,
			final_total, cart_id, delivery_charge;
	ArrayList<HashMap<String, String>> my_cart_arraylist;
	
	String TAG_SUCCESS = "success";
	
	HttpConnection http = new HttpConnection();

	/*** Network Connection Instance **/
	ConnectionDetector cd;
	
	/* Language conversion */
	private Locale myLocale;
	
	MyBookedCartAdpater adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_my_booked_cart);

		try {

			cd = new ConnectionDetector(getApplicationContext());

			initialize();
			in = getIntent();

			first_name = in.getStringExtra("first_name");
			last_name = in.getStringExtra("last_name");
			email = in.getStringExtra("email");
			contact_number = in.getStringExtra("contact_number");
			final_total = in.getStringExtra("final_total");
			delivery_charge = in.getStringExtra("delivery_charge");
			cart_id = in.getStringExtra("cart_id");
			status = in.getStringExtra("booking_status");
			address = in.getStringExtra("address");
			
			System.out.println("!!!!!!!!!!!!!!address"+address);
			
			

			txv_user_FirstName.setText(first_name + " " + last_name);
			txv_user_Email.setText(email);
			txv_user_ContactNumber.setText(contact_number);
			txv_payment_status.setText(status);
			receipt_txv_delivery_charge
					.setText(getString(R.string.Categories_sr) + " "
							+ delivery_charge);
			receipt_txv_final_total.setText(getString(R.string.Categories_sr)
					+ " " + final_total);
			
			txv_user_address.setText(address);

			if (cd.isConnectingToInternet()) {
				new async_my_booked_rest_cart().execute();
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								R.string.No_Internet_Connection,
								Toast.LENGTH_LONG).show();

						// do {
						System.out.println("do-while");
						if (cd.isConnectingToInternet()) {

							new async_my_booked_rest_cart().execute();

						}
						// } while (cd.isConnectingToInternet()
						// == false);

					}

				});
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
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

	private void initialize() {
		// TODO Auto-generated method stub
		receipt_txv_final_total = (TextView) findViewById(R.id.receipt_txv_total_Reciept);
		LV_booked_cart_Cart_Details = (ListView) findViewById(R.id.LV_booked_cart_Cart_Details);
		receipt_txv_delivery_charge = (TextView) findViewById(R.id.txv_receipt_delivery_charge_Reciept);
		txv_user_FirstName = (TextView) findViewById(R.id.txv_booked_cart_customer_name);
		txv_user_Email = (TextView) findViewById(R.id.txv_booked_cart_email);
		txv_user_ContactNumber = (TextView) findViewById(R.id.txv_booked_cart_contact_no);
		txv_payment_status = (TextView) findViewById(R.id.txv_booked_cart_payment_status);
		txv_user_address = (TextView) findViewById(R.id.txv_booked_cart_address);
		// txv_registered_date = (TextView)
		// findViewById(R.id.txv_booked_cart_registered_date);

	}

	public class async_my_booked_rest_cart extends AsyncTask<Void, Void, Void> {
		JSONObject data;
		JSONObject json;
		JSONArray my_booked_rest_cart_array;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("async_my_booked_rest_cart  Call");
			// Showing progress dialog

			// progressDialog = new ProgressDialog(MyBooking.this);
			// progressDialog.setCancelable(false);
			// progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject fetch_my_booked_rest_cart = new JSONObject();

				try {
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							fetch_my_booked_rest_cart
									.put("user_id",
											SharedPreference
													.getuser_id(getApplicationContext()));
							System.out.println("fetch_my_booked_rest_cart"
									+ fetch_my_booked_rest_cart);
						}
					} else {
						fetch_my_booked_rest_cart.put("user_id", "");
					}
					
					fetch_my_booked_rest_cart.put("cart_id", cart_id);

					fetch_my_booked_rest_cart
							.put("sessid", SharedPreference
									.getsessid(getApplicationContext()));
					System.out.println("fetch_my_booked_rest_cart"
							+ fetch_my_booked_rest_cart);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// for request
				String responseText = http.connection(MyBookedCart.this, Global_variable.rf_GetMyBookedRestCart,
						fetch_my_booked_rest_cart);

				try {

					json = new JSONObject(responseText);

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println("tag" + success1);

					String str_data = json.getString("data");
					System.out.println("my_booked_rest_cart_str_data"
							+ str_data);
					
					my_cart_arraylist = new ArrayList<HashMap<String, String>>();

					if (success1.equals("true")) {
						if (str_data != "[]") {
							data = json.getJSONObject("data");
							System.out.println("data" + data);
							
							

							if (data != null) {
								my_booked_rest_cart_array = data
										.getJSONArray("cart");
								System.out
										.println("my_booked_rest_cart_array"
												+ my_booked_rest_cart_array
														.toString());

								int length = my_booked_rest_cart_array
										.length();
								// System.out.println("respose_array Value"+
								// response_array.keys().toString());
								System.out
										.println("my_booked_rest_cart_array_length"
												+ length);
								

								for (int i = 0; i <= length; i++) {
									try {
										JSONObject my_booked_rest_cart_obj = my_booked_rest_cart_array
												.getJSONObject(i);
										System.out.println("my_booked_rest_cart_obj"
												+ my_booked_rest_cart_obj);

										String id = my_booked_rest_cart_obj
												.getString("id");
										System.out.println("id"
												+ id);
										String name = my_booked_rest_cart_obj
												.getString("name");
										System.out.println("name" + name);
										String price = my_booked_rest_cart_obj
												.getString("price");
										System.out.println("price" + price);

										String quantity = my_booked_rest_cart_obj
												.getString("quantity");
										System.out.println("quantity"
												+ quantity);
										
										
										String item_id = my_booked_rest_cart_obj
												.getString("item_id");
										System.out.println("item_id"
												+ item_id);

										String total = my_booked_rest_cart_obj
												.getString("total");
										System.out.println("total" + total);
										
										String uid = my_booked_rest_cart_obj
												.getString("uid");
										System.out.println("uid" + uid);

										HashMap<String, String> map = new HashMap<String, String>();
//
//										map.put("id", id);
//										System.out.println("map" + map);
//										map.put("rest_id", rest_id);
//										System.out.println("map" + map);
//										map.put("user_id", user_id);
//										System.out.println("map" + map);
//
//										map.put("booking_date", booking_date);
//										System.out.println("map" + map);
//										map.put("booking_time", booking_time);
//										System.out.println("map" + map);
//
//										map.put("name_en", name_en);
//										System.out.println("map" + map);
//
//										System.out.println("map" + map);
//										my_cart_arraylist.add(map);
//
//										System.out
//												.println("!!!!!In background..."
//														+ my_cart_arraylist);

										//

									} catch (Exception ex) {
										System.out.println("Error" + ex);
									}
								}
							}
						}
					} else {

					}

				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (JSONException e) {
				e.printStackTrace();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				runOnUiThread(new Runnable() {
					public void run() {
						System.out.println("pankajsakariyadata" + data);
						
							if (data.length() != 0) {
								adapter = new MyBookedCartAdpater(
										MyBookedCart.this,
										my_booked_rest_cart_array);
								System.out.println("pankaj_inside_hotel_list");
								if (adapter != null) {
									LV_booked_cart_Cart_Details
											.setAdapter(adapter);
									// listview_adapter.notifyDataSetChanged();
									System.out
											.println("pankaj_inside_list_adapter");
									LV_booked_cart_Cart_Details.invalidateViews();

								}

							} else {
								System.out.println("pankaj_inside_else");
								adapter = new MyBookedCartAdpater(
										MyBookedCart.this,
										my_booked_rest_cart_array);
								LV_booked_cart_Cart_Details
								.setAdapter(adapter);
								// listview_adapter.notifyDataSetChanged();
								// Hotel_list_listviw.invalidateViews();
							}

						//}
					}
				});

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// if (progressDialog.isShowing()) {
			// progressDialog.dismiss();
			// }

		}
	}
}
