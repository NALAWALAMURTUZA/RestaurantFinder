package com.rf_user.activity;

import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.Adapter_Receipt;
import com.rf_user.adapter.Adapter_for_Payment_Offers;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Reciept_Activity extends Activity {
	public static TextView receipt_txv_final_total,
			receipt_txv_delivery_charge;
	ListView LV_Cart_Details;
	public static Adapter_Receipt adapter_receipt;
	ProgressDialog progressDialog;
	TextView txv_user_FirstName, txv_user_Email, txv_user_ContactNumber,
			txv_payment_status, txv_user_address, txv_registered_date,txv_invoice;
	// public static String str_name_payment_methods;
	public static String Str_OrderId_Checkout2;
	public static String Str_PaymentUrl_Checkout2;
	String Str_checkout2_errors1;
	String str_Order_Id;
	String str_Payment_Url;
	/*** Network Connection Instance **/
	ConnectionDetector cd;

	String langPref = "Language";
	/* Declaration for http call */
	HttpConnection http = new HttpConnection();
	String TAG_SUCCESS = "success";
	/* Language conversion */
	private Locale myLocale;
	String str_order_id,str_booking_date,str_booking_time;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.delivery_receipt_layout);
		/* create Object* */
		try {
			
			System.out.println("hotel_list_sessid_lat_receipt"+Global_variable.latitude);
			System.out.println("hotel_list_sessid_long_receipt"+Global_variable.longitude);
			SharedPreferences prefs_oncreat = getSharedPreferences(
					"CommonPrefs", Activity.MODE_PRIVATE);
			String language = prefs_oncreat.getString(langPref, "");

			cd = new ConnectionDetector(getApplicationContext());
			System.out.println("Murtuza_Nalawala_language_oncreat_Tab"
					+ language);
			
			Global_variable.activity="RecieptActivity";
			
			
			initializeWidgets();
			System.out.println("Murtuza_Nalawala_language_oncreat_Tab_falg"
					+ Checkout_Tablayout.Langauge_Arabic);
			txv_user_FirstName.setText(Global_variable.str_User_FirstName + " "
					+ Global_variable.str_User_LastName);
			System.out.println("Global_variable_str_User_LastName"
					+ Global_variable.str_User_LastName);
			System.out.println("Global_variable_str_User_LastName"
					+ Global_variable.str_User_LastName);
			txv_user_Email.setText(Global_variable.str_User_Email);
			txv_user_ContactNumber
					.setText(Global_variable.str_User_ContactNumber);
			System.out.println("Global_variable_str_User_Email"
					+ Global_variable.str_User_Email);
			System.out.println("Global_variable_str_User_ContactNumber"
					+ Global_variable.str_User_ContactNumber);
			System.out.println("Global_variable_flag_arabic_receipt"
					+ Global_variable.flag_arabic_receipt);
			if (Global_variable.flag_arabic_receipt) {
				/** check Internet Connectivity */

				if (cd.isConnectingToInternet()) {
					try {
						System.out.println("Murtuza_Nalawla_Lang" + langPref);
						new async_CheckoutStep2API().execute();
						// new async_CheckoutStep3_API().execute();
					} catch (NullPointerException n) {
						n.printStackTrace();
					}

				} else {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),
									R.string.No_Internet_Connection,
									Toast.LENGTH_SHORT).show();

							//do {
								System.out.println("do-while");
								if (cd.isConnectingToInternet()) {
									try {
										new async_CheckoutStep2API().execute();
//										new async_CheckoutStep3_API().execute();
									} catch (NullPointerException n) {
										n.printStackTrace();
									}
								}
							//} while (cd.isConnectingToInternet() == false);

						}

					});
				}

				if (Global_variable.shipping_tag_delivery_ok == "1") // /
																		// Shipping
				{
					try {
						txv_user_address.setVisibility(View.VISIBLE);
						if (Global_variable.shipping_tag_addr_type == "custom") {
							txv_user_address
									.setText(Global_variable.Str_Houseno + " "
											+ Global_variable.Str_Street + " "
											+ Global_variable.Str_DistrictName
											+ " "
											+ Global_variable.Str_CityName);
						} else if (Global_variable.shipping_tag_addr_type == "pre") {
							txv_user_address
									.setText(Global_variable.str_User_saved_address);
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				} else if (Global_variable.shipping_tag_delivery_ok == "0") // will
																			// pick
																			// my
																			// self
				{
					txv_user_address.setVisibility(View.INVISIBLE);
				}
				Checkout_Tablayout.tab.getTabWidget().getChildAt(0)
						.setClickable(false);
				Checkout_Tablayout.tab.getTabWidget().getChildAt(1)
						.setClickable(false);
				Checkout_Tablayout.tab.getTabWidget().getChildAt(2)
						.setClickable(false);
				Checkout_Tablayout.tab.getTabWidget().getChildAt(3)
						.setClickable(true);

				System.out.println("Murtuza_Nalawla_Payment"
						+ Payment_Activity.Bool_Apply);
				if (Payment_Activity.Bool_Apply == false) {
					try {
						adapter_receipt = new Adapter_Receipt(
								Reciept_Activity.this, Global_variable.cart);
						System.out.println("str_delivery_charge"
								+ Payment_Activity.str_delivery_charge);

						if (!Payment_Activity.str_delivery_charge
								.equals("null")) {
							receipt_txv_delivery_charge
									.setText(Global_variable.Categories_sr
											+ " "
											+ Payment_Activity.str_delivery_charge);
							System.out.println("str_delivery_charge_1"
									+ Payment_Activity.str_delivery_charge);
							if (Payment_Activity.str_delivery_charge
									.equals("null")) {
								receipt_txv_delivery_charge
										.setText(Global_variable.Categories_sr
												+ "00");
							}
							receipt_txv_final_total
									.setText(Global_variable.Categories_sr
											+ " "
											+ Payment_Activity.final_Cart_Total);
						} else {
							System.out.println("Murtuzxa_nalawala");
							System.out.println("Murtuzxa_nalawala"
									+ Global_variable.Categories_sr + " "
									+ "00");
							System.out
									.println("Murtuzxa_nalawala"
											+ Global_variable.Categories_sr
											+ " "
											+ String.valueOf(Global_variable.cart_total));
							String dilivery_charge = Global_variable.Categories_sr
									+ " " + "00";
							String Total = Global_variable.Categories_sr
									+ " "
									+ String.valueOf(Global_variable.cart_total);
							receipt_txv_delivery_charge
									.setText(dilivery_charge);
							receipt_txv_final_total.setText(Total);
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				} else if (Payment_Activity.Bool_Apply == true) {
					try {
						adapter_receipt = new Adapter_Receipt(
								Reciept_Activity.this,
								Global_variable.cart_apply_offer);
						{
							System.out.println("str_delivery_charge_else"
									+ Payment_Activity.str_delivery_charge);
							if (!Payment_Activity.str_delivery_charge
									.equalsIgnoreCase("null")) {
								receipt_txv_delivery_charge
										.setText(Global_variable.Categories_sr
												+ " "
												+ Payment_Activity.str_delivery_charge);
								System.out.println("str_delivery_charge_1"
										+ Payment_Activity.str_delivery_charge);
								if (Payment_Activity.str_delivery_charge
										.equalsIgnoreCase("null")) {
									System.out
											.println("str_delivery_charge_null"
													+ Payment_Activity.str_delivery_charge);
									receipt_txv_delivery_charge
											.setText(Global_variable.Categories_sr
													+ "00");
								}
								receipt_txv_final_total
										.setText(Global_variable.Categories_sr
												+ " "
												+ Global_variable.str_final_total_Apply_Offer);
							} else {
								System.out.println("Murtuzxa_nalawala");
								System.out.println("Murtuzxa_nalawala"
										+ Global_variable.Categories_sr + " "
										+ "00");
								System.out
										.println("Murtuzxa_nalawala"
												+ Global_variable.Categories_sr
												+ " "
												+ String.valueOf(Global_variable.cart_total));
								String dilivery_charge = Global_variable.Categories_sr
										+ " " + "00";
								String Total = Global_variable.Categories_sr
										+ " "
										+ Global_variable.str_final_total_Apply_Offer;
								receipt_txv_delivery_charge
										.setText(dilivery_charge);
								receipt_txv_final_total.setText(Total);
							}
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
				// new async_GetCurrentUserDetails().execute();
				// new async_CheckoutStep2API().execute();
				LV_Cart_Details.setAdapter(adapter_receipt);
				// Checkout_Tablayout.txv_Receipt.setVisibility(View.VISIBLE);
				// Checkout_Tablayout.txv_Checkout.setVisibility(View.GONE);
				// Checkout_Tablayout.txv_payment.setVisibility(View.GONE);
			}
			
//			loadLocale();
			
			
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
		
		setlistner();
//		new generate_invoice().execute();

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


	private void setlistner() {
		// TODO Auto-generated method stub
		try{
			txv_invoice.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Reciept_Activity.this);
			 
						// set title
						alertDialogBuilder.setTitle("Invoice Dialog");
			 
						// set dialog message
						alertDialogBuilder
							.setMessage(getString(R.string.generate_invoice))
							.setCancelable(false)
							.setPositiveButton(getString(R.string.yes),new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									//Reciept_Activity.this.finish();
									
									new generate_invoice().execute();
									
								}
							  })
							.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
					
					
					
				}
			});
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	/* Generate invoice for user's order */

	public class generate_invoice extends AsyncTask<Void, Void, Void> {

		JSONObject json;
		ProgressDialog dialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Reciept_Activity.this);
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

			runOnUiThread(new Runnable() {
				public void run() {

					try {
						JSONObject invoice_obj = new JSONObject();

						if(txv_user_FirstName.getText().toString()!=null)
						{
							invoice_obj.put("name",
									txv_user_FirstName.getText().toString());
						}
						else
						{
							invoice_obj.put("name",
									"");
						}
						System.out.println("name" + invoice_obj);
						
						if(txv_user_address.getText().toString()!=null)
						{
							invoice_obj.put("address",
									txv_user_address.getText().toString());
						}
						else
						{
							invoice_obj.put("address",
									"");
						}
						System.out.println("address" + invoice_obj);
						
						if(txv_user_Email.getText().toString()!=null)
						{
							invoice_obj.put("email",
									txv_user_Email.getText().toString());
						}
						else
						{
							invoice_obj.put("email",
									"");
						}
						System.out.println("email" + invoice_obj);
						
						if(txv_user_ContactNumber.getText().toString()!=null)
						{
							invoice_obj.put("contact_number",
									txv_user_ContactNumber.getText().toString());
						}
						else
						{
							invoice_obj.put("contact_number",
									"");
						}
						System.out.println("contact_number" + invoice_obj);
						
						if(txv_payment_status.getText().toString()!=null)
						{
							invoice_obj.put("status",
									txv_payment_status.getText().toString());
						}
						else
						{
							invoice_obj.put("status",
									"Pending");
						}
						System.out.println("status" + invoice_obj);
						if(Payment_Activity.Bool_Apply == false)
						{
							if(Global_variable.cart!=null)
							{
								invoice_obj.put("cart",
										Global_variable.cart);
							}
							else
							{
								invoice_obj.put("cart",
										"");
							}
						}
						else
						{
							if(Global_variable.cart_apply_offer!=null)
							{
								invoice_obj.put("cart",
										Global_variable.cart_apply_offer);
							}
							else
							{
								invoice_obj.put("cart",
										"");
							}
						}
						
						System.out.println("cart" + invoice_obj);
						
						if(receipt_txv_delivery_charge.getText().toString()!=null)
						{
							invoice_obj.put("delivery_charge",
									receipt_txv_delivery_charge.getText().toString());
						}
						else
						{
							invoice_obj.put("delivery_charge",
									"");
						}
						System.out.println("delivery_charge" + invoice_obj);
						
						
						if(receipt_txv_final_total.getText().toString()!=null)
						{
							invoice_obj.put("total",
									receipt_txv_final_total.getText().toString());
						}
						else
						{
							invoice_obj.put("total",
									"");
						}
						System.out.println("total" + invoice_obj);
						
						invoice_obj.put("sessid", SharedPreference
								.getsessid(getApplicationContext()));
						System.out.println("session_id" + invoice_obj);
						// *************

						String responseText = http
								.connection(Reciept_Activity.this,
										 Global_variable.rf_generate_invoice_api_path,
												invoice_obj);

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

			// json success tag
			String success1;

			try {
				success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);
				JSONObject data = json.getJSONObject("data");
				// String Data_Success = data.getString(TAG_SUCCESS);
				// System.out.println("Data tag" + Data_Success);
				// ******** data succsess

				if (success1.equals("true")) {
					if (data.length() != 0) {

					String message = data.getString("message");
//						String close_name = data.getString("close_time");

						Global_variable.cart=new JSONArray();
						
						Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
						Intent in = new Intent(getApplicationContext(), Search_Restaurant_List.class);
						startActivity(in);
						
					}

				}

				// **** invalid output
				else {
					if (success1.equalsIgnoreCase("false")) {
						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("name")) {
							JSONArray Array_name = Data_Error
									.getJSONArray("name");
							System.out.println("Array_name" + Array_name);
							String Str_name = Array_name.getString(0);
							System.out.println("Str_rest_id" + Str_name);
							if (Str_name != null) {
								Toast.makeText(getApplicationContext(),
										Str_name, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("address")) {
							JSONArray Array_address = Data_Error
									.getJSONArray("address");
							System.out.println("Array_address" + Array_address);
							String Str_address = Array_address.getString(0);
							System.out.println("Str_address" + Str_address);
							if (Str_address != null) {
								Toast.makeText(getApplicationContext(),
										Str_address, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("email")) {
							JSONArray Array_email = Data_Error
									.getJSONArray("email");
							System.out.println("Array_email" + Array_email);
							String Str_email = Array_email.getString(0);
							System.out.println("Str_email" + Str_email);
							if (Str_email != null) {
								Toast.makeText(getApplicationContext(),
										Str_email, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("contact_number")) {
							JSONArray Array_contact_number = Data_Error
									.getJSONArray("contact_number");
							System.out.println("Array_contact_number" + Array_contact_number);
							String Str_contact_number = Array_contact_number.getString(0);
							System.out.println("Str_contact_number" + Str_contact_number);
							if (Str_contact_number != null) {
								Toast.makeText(getApplicationContext(),
										Str_contact_number, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("status")) {
							JSONArray Array_status = Data_Error
									.getJSONArray("status");
							System.out.println("Array_status" + Array_status);
							String Str_status = Array_status.getString(0);
							System.out.println("Str_status" + Str_status);
							if (Str_status != null) {
								Toast.makeText(getApplicationContext(),
										Str_status, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("cart")) {
							JSONArray Array_cart = Data_Error
									.getJSONArray("cart");
							System.out.println("Array_cart" + Array_cart);
							String Str_cart = Array_cart.getString(0);
							System.out.println("Str_cart" + Str_cart);
							if (Str_cart != null) {
								Toast.makeText(getApplicationContext(),
										Str_cart, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("delivery_charge")) {
							JSONArray Array_delivery_charge = Data_Error
									.getJSONArray("delivery_charge");
							System.out.println("Array_delivery_charge" + Array_delivery_charge);
							String Str_delivery_charge = Array_delivery_charge.getString(0);
							System.out.println("Str_delivery_charge" + Str_delivery_charge);
							if (Str_delivery_charge != null) {
								Toast.makeText(getApplicationContext(),
										Str_delivery_charge, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("total")) {
							JSONArray Array_total = Data_Error
									.getJSONArray("total");
							System.out.println("Array_total" + Array_total);
							String Str_total = Array_total.getString(0);
							System.out.println("Str_total" + Str_total);
							if (Str_total != null) {
								Toast.makeText(getApplicationContext(),
										Str_total, Toast.LENGTH_LONG).show();
							}
						}
						
						if (Data_Error.has("sessid")) {
							JSONArray Array_sessid = Data_Error
									.getJSONArray("sessid");
							System.out.println("Array_sessid" + Array_sessid);
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

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

		}

	}


	private void initializeWidgets() {
		// TODO Auto-generated method stub
		receipt_txv_final_total = (TextView) findViewById(R.id.receipt_txv_total_Reciept);
		LV_Cart_Details = (ListView) findViewById(R.id.LV_Cart_Details);
		receipt_txv_delivery_charge = (TextView) findViewById(R.id.txv_receipt_delivery_charge_Reciept);
		txv_user_FirstName = (TextView) findViewById(R.id.txv_customer_name);
		txv_user_Email = (TextView) findViewById(R.id.txv_email);
		txv_user_ContactNumber = (TextView) findViewById(R.id.txv_contact_no);
		txv_payment_status = (TextView) findViewById(R.id.txv_payment_status);
		txv_user_address = (TextView) findViewById(R.id.txv_address);
		txv_registered_date = (TextView) findViewById(R.id.txv_registered_date);
		txv_invoice=(TextView)findViewById(R.id.txv_invoice);
	}

	public class async_GetCurrentUserDetails extends
			AsyncTask<Void, Void, Void> {
		JSONObject json;
		JSONObject data;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = new ProgressDialog(Reciept_Activity.this);
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			JSONObject GetCurrentUserDetails = new JSONObject();
			try {

				GetCurrentUserDetails.put("sessid", SharedPreference.getsessid(getApplicationContext()));

				System.out.println("Shipping_Request_Child"
						+ GetCurrentUserDetails);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + GetCurrentUserDetails);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_GetCurrentUserDetails_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						GetCurrentUserDetails.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("Shipping_last_text" + responseText);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}

				json = new JSONObject(responseText);
				System.out.println("Shipping_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			data = new JSONObject();
			try {
				data = json.getJSONObject("data");
				String str_success = json.getString("success");
				if (str_success.equals("true")) {
					String str_message = json.getString("message");
					String str_User_FirstName = data
							.getString("user_first_name");
					String str_User_LastName = data.getString("user_last_name");
					String str_User_Email = data.getString("user_email");
					String str_User_ContactNumber = data
							.getString("user_contact_number");
					txv_user_FirstName.setText(str_User_FirstName + " "
							+ str_User_LastName);
					txv_user_Email.setText(str_User_Email);
					txv_user_ContactNumber.setText(str_User_ContactNumber);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {

			}

			progressDialog.dismiss();
		}

	}

	/************ async_CheckoutStep2API **************/
	public class async_CheckoutStep2API extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			//
			/********** FULL REQUEST WITH offer_id,SHIPPING,CART,AND OTHER STRING *************/
			JSONObject CheckoutStep2API_main = new JSONObject();
			try {
				CheckoutStep2API_main.put("addr_type",
						Global_variable.shipping_tag_addr_type);
				System.out.println("Global_variable.hotel_id"
						+ Global_variable.hotel_id);
				CheckoutStep2API_main.put("rest_id", Global_variable.hotel_id);
				CheckoutStep2API_main.put("time_from",
						Global_variable.str_Time_From);
				CheckoutStep2API_main.put("time_to",
						Global_variable.str_Time_To);
				CheckoutStep2API_main.put("date", Global_variable.str_Date);
				CheckoutStep2API_main.put("offer_id",
						Adapter_for_Payment_Offers.str_offer_uid);
				CheckoutStep2API_main.put("continuePay",
						Global_variable.Payment_Method_Id);
				CheckoutStep2API_main.put("sessid", SharedPreference.getsessid(getApplicationContext()));
				CheckoutStep2API_main.put("user_id", SharedPreference.getuser_id(getApplicationContext()));
				CheckoutStep2API_main.put("ShippingForm",
						Global_variable.Shipping_Request_Child);
				CheckoutStep2API_main.put("cart", Global_variable.cart);

				System.out.println("CheckoutStep2API_main"
						+ CheckoutStep2API_main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("Applay_Offer_main" + CheckoutStep2API_main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_CheckoutStep2_api_path);

				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						CheckoutStep2API_main.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("CHECKOUTSTEP2_responseText"
							+ responseText + "chetanzaps");
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}

				json = new JSONObject(responseText);
				System.out.println("CHECKOUTSTEP2_main_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// json success tag

			/********* Details jsonviewr ******/
			// Json
			// {
			// success{
			// success="true" ,

			// Order
			// {
			// []items
			// {}shipment
			// }
			// data[]
			// message:
			// sessid:
			// }
			// }

			try {
				String message = json.getString("message");
				String sessid = json.getString("sessid");

				System.out.println("sessid" + sessid);
				System.out.println("message" + message);

				/******** { Json Object {1 ******/

				Global_variable.json_CheckoutStep2API_Success_Object = json
						.getJSONObject("success");
				String str_success_checkout = Global_variable.json_CheckoutStep2API_Success_Object
						.getString("success");
				System.out.println("str_success_checkout"
						+ str_success_checkout);

				/********* ORDER ID & PAYMENT URL *********/

				// str_Order_Id =
				// Global_variable.json_CheckoutStep2API_Success_Object
				// .getString("order_id");
				// str_Payment_Url =
				// Global_variable.json_CheckoutStep2API_Success_Object
				// .getString("payment_url");
				//
				// System.out.println("str_Order_Id"+str_Order_Id);
				// System.out.println("str_Payment_Url"+str_Payment_Url);

				// Str_checkout2_errors1 =
				// Global_variable.json_CheckoutStep2API_Success_Object
				// .getString("error");
				System.out.println("Str_checkout2_errors1"
						+ Str_checkout2_errors1);
//				Toast.makeText(Reciept_Activity.this, Str_checkout2_errors1,
//						Toast.LENGTH_SHORT).show();
				System.out
						.println("Global_variable.json_CheckoutStep2API_Success_Object"
								+ Global_variable.json_CheckoutStep2API_Success_Object);
				// Global_variable.json_CheckoutStep2API_Data_Array = json
				// .getJSONArray("data");
				System.out.println("json_CheckoutStep2API_Data_Array"
						+ Global_variable.json_CheckoutStep2API_Data_Array);
				System.out.println("Json_Total"
						+ json.getJSONObject("success").getJSONObject("order")
								.getString("total"));
				String str = json.getJSONObject("success")
						.getJSONObject("order").getString("payment_method")
						.toString();

				System.out.println("str.........." + str);

				txv_payment_status.setText(str);

				if (str_success_checkout.equalsIgnoreCase("true")) {
					
					
					
/*					Str_OrderId_Checkout2 = Global_variable.json_CheckoutStep2API_Success_Object.getJSONObject("order")
							.getString("order_id");
					str_order_id = Global_variable.json_CheckoutStep2API_Success_Object.getJSONObject("order")
							.getString("uid");
					str_booking_date = Global_variable.json_CheckoutStep2API_Success_Object.getJSONObject("order")
							.getString("registered");
					

					String str_split[] = new String[str_booking_date.split(" ").length];
					str_split = str_booking_date.split(" ");
					str_booking_date = str_split[0];
					
					str_booking_time = Global_variable.json_CheckoutStep2API_Success_Object.getJSONObject("order")
							.getString("delivery_schedule_from");
					
					System.out.println("Str_OrderId_Checkout2"
							+ Str_OrderId_Checkout2+"uid = "+str_order_id+"booking_date = "+str_booking_date+"booking_time = "+str_booking_time);
					Str_PaymentUrl_Checkout2 = Global_variable.json_CheckoutStep2API_Success_Object
							.getString("payment_url");
					System.out.println("Str_PaymentUrl_Checkout2"
							+ Str_PaymentUrl_Checkout2); */
					
//					if (success1.equals("true")) {
//						if (data.length() != 0) {

//						String message = data.getString("message");
//							String close_name = data.getString("close_time");

							Global_variable.cart=new JSONArray();
							
							Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
							Intent in = new Intent(getApplicationContext(), Search_Restaurant_List.class);
							startActivity(in);
							
//						}
//
//					}
				} else if (str_success_checkout.equalsIgnoreCase("false")) {

					Str_checkout2_errors1 = Global_variable.json_CheckoutStep2API_Success_Object
							.getString("error");
					System.out.println("Str_checkout2_errors1"
							+ Str_checkout2_errors1);

				}

				/******** String ORDER Object ******/

				/******** Success Object } ******/

				/******** Json Object } ******/

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException np) {

			}

		}
	}

	/************ async_CheckoutStep2API **************/
	public class async_CheckoutStep3_API extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			//
			/********** FULL REQUEST WITH offer_id,SHIPPING,CART,AND OTHER STRING *************/
			JSONObject CheckoutStep3API_main = new JSONObject();
			try {
				CheckoutStep3API_main.put("order_id", str_Order_Id);
				CheckoutStep3API_main.put("sessid", SharedPreference.getsessid(getApplicationContext()));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("Applay_Offer_main" + CheckoutStep2API_main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httppostreq = new HttpPost(
						Global_variable.rf_lang_Url
								+ Global_variable.rf_CheckoutStep3_api_path);

				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						CheckoutStep3API_main.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("CHECKOUTSTEP3_responseText"
							+ responseText + "chetanzaps");
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}

				json = new JSONObject(responseText);
				System.out.println("CHECKOUTSTEP3_main_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// json success tag

			/********* Details jsonviewr ******/
			// Json
			// {
			// success{
			// success="true" ,

			// Order
			// {
			// []items
			// {}shipment
			// }
			// data[]
			// message:
			// sessid:
			// }
			// }

			try {
				String message = json.getString("message");
				String sessid = json.getString("sessid");

				System.out.println("sessid" + sessid);
				System.out.println("message" + message);

				/******** { Json Object {1 ******/

				JSONObject checkoutstep3_Success = json
						.getJSONObject("success");
				String str_success_checkout3 = Global_variable.json_CheckoutStep2API_Success_Object
						.getString("success");
				/**** { Object ORDER FROM sUCCESS *****/
				JSONObject Obj_Order = checkoutstep3_Success
						.getJSONObject("order");

				/***** Paymen stsatus & Registered string set *********/
				String str_json_payment_status = Obj_Order
						.getString("payment_status");
				String str_registered = Obj_Order.getString("registered");
				System.out.println("str_registered" + str_registered);

				/***** Split code *****/
				str_json_payment_status.split(">");
				String[] split_str = str_json_payment_status.split(">");
				System.out.println("split_str" + split_str);
				String[] str_status = split_str[1].split("<");
				System.out.println("str_status" + str_status);
				String str_Payment_status = str_status[0];
				System.out.println("last_status" + str_Payment_status);

				txv_payment_status.setText(str_Payment_status);
				txv_registered_date.setText(str_registered);

				/**** ARRAY ITEM FROM ORDER *****/
				JSONArray array_Item = Obj_Order.getJSONArray("items");

				/**** Object SHIPMENT FROM ORDER *****/
				JSONObject Obj_Shipment = Obj_Order.getJSONObject("shipment");

				System.out.println("str_success_checkout"
						+ str_success_checkout3);
				System.out.println("Obj_Order" + Obj_Order);
				System.out.println("Obj_Shipment" + Obj_Shipment);
				System.out.println("array_Item" + array_Item);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException np) {

			}

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
//		Payment_Activity.Bool_Apply = false;
//		Intent i = new Intent(Reciept_Activity.this, Cart.class);
//		startActivity(i);

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
