package com.rf_user.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.adapter.Adapter_for_Payment_Offers;
import com.rf_user.adapter.Payment_Cart_Adapter;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Payment_Activity extends Activity {
	public static TextView payment_txv_total;
	LinearLayout ly_offers_description;
	LinearLayout ly_offer;
	Context context;
	Payment_Cart_Adapter payment_cart_adapter;
	public static ListView LV_Cart_Payment;
	public static Boolean Bool_Apply = false;
	TextView txv_offfer_name, txv_continue, txv_select_method_title;
	public static ListView lv_cart_details;
	public static TextView txv_delivery_charge;
	public static String str_delivery_charge;
	Point p;
	// JSONArray offers = new JSONArray();
	ProgressDialog progressDialog;
	public static int final_Cart_Total;
	// public static JSONArray offers;
	Adapter_for_Payment_Offers adapter_for_payment_offer;
	public static Dialog dialog;
	public static Dialog dialog_SendVerification;
	public static Dialog dialog_EnterCode;
	RadioButton rdbtn_payment_method;
	String str_needMobileVerify;
	ImageView img_popup_next;
	EditText Ed_Enter_Code;
	public static String str_name_payment_methods;
	public static String Str_OrderId_Checkout2;
	public static String Str_PaymentUrl_Checkout2;
	String str_Success_stepcheck;
	TextView txv_verification_error, txv_rdbtn_select_method;
	/*** Network Connection Instance **/
	ConnectionDetector cd;
	String data;

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.delivery_payment_layout);

		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			/* create Object* */

			cd = new ConnectionDetector(getApplicationContext());
			initializeWidgets();
			if (Bool_Apply == false) {
				payment_cart_adapter = new Payment_Cart_Adapter(
						Payment_Activity.this, Global_variable.cart);

				LV_Cart_Payment.setAdapter(payment_cart_adapter);
				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					new async_Step_Choose_Time().execute();

				} else {

					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(),
									R.string.No_Internet_Connection,
									Toast.LENGTH_SHORT).show();
							// do {
							System.out.println("do-while");
							if (cd.isConnectingToInternet()) {

								new async_Step_Choose_Time().execute();

							}
							// } while (cd.isConnectingToInternet() == false);

						}

					});
				}

			} else if (Bool_Apply == true) {
				try {
					payment_cart_adapter = new Payment_Cart_Adapter(
							Payment_Activity.this,
							Global_variable.cart_apply_offer);

					LV_Cart_Payment.setAdapter(payment_cart_adapter);
					txv_delivery_charge
							.setText(getString(R.string.Categories_sr)
									+ " "
									+ Global_variable.str_delivery_charge_Apply_Offer);

					payment_txv_total
							.setText(getString(R.string.Categories_sr)
									+ " "
									+ Global_variable.str_final_total_Apply_Offer);
				} catch (NullPointerException n) {
					n.printStackTrace();
				}

			}

			// Checkout_Tablayout.txv_payment.setVisibility(View.VISIBLE);
			// Checkout_Tablayout.txv_Checkout.setVisibility(View.GONE);
			// Checkout_Tablayout.txv_Receipt.setVisibility(View.GONE);

			setlistener();
			// Checkout_Tablayout.tab.getTabWidget().getChildAt(2).setClickable(true);
			System.out.println("cartvalue_gai" + Global_variable.cart);

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

	private void setlistener() {
		// TODO Auto-generated method stub

		// if (offers != null) {
		// ly_offer.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// // showDialogForOffer();
		// }
		// });
		// } else {
		// Toast.makeText(Payment_Activity.this, R.string.popup_Offer_error,
		// Toast.LENGTH_SHORT).show();
		// }

		// ly_offers_description.setOnClickListener(new OnClickListener()
		// {
		// @Override
		// public void onClick(View v) {
		// ly_offers_description.setVisibility(View.GONE);
		// }
		// });
		rdbtn_payment_method.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					showDialogForSendVerificationCode();
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
		txv_continue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// if (rdbtn_payment_method.isChecked()) {
					//
					// } else {
					//
					// if(!txv_rdbtn_select_method.getText().toString().equalsIgnoreCase("Verify Your Mobile"))
					// {
					// Toast.makeText(Payment_Activity.this,
					// R.string.payment_Method, Toast.LENGTH_LONG)
					// .show();
					// }
					// else
					// {
					//
					//
					// Toast.makeText(Payment_Activity.this,
					// str_name_payment_methods, Toast.LENGTH_LONG)
					// .show();
					// }
					//
					//
					// }

					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {
						if (Checkout_Tablayout.language.equals("ar")) {
							// Checkout_Tablayout obj_chk = new
							// Checkout_Tablayout();
							// obj_chk.setNewTab(Payment_Activity.this,
							// Checkout_Tablayout.tab, "receipt",
							// R.string.tab_Reciept,
							// Checkout_Tablayout.intent3);
							// Checkout_Tablayout.Langauge_Arabic=false;
							Checkout_Tablayout.tab.getTabWidget().getChildAt(1)
									.setVisibility(View.VISIBLE);
							Checkout_Tablayout.tab.getTabWidget().getChildAt(0)
									.setVisibility(View.GONE);
							Global_variable.flag_arabic_receipt = true;
							System.out
									.println("Global_variable_flag_arabic_receipt_payment_arabic"
											+ Global_variable.flag_arabic_receipt);
							Checkout_Tablayout.tab.setCurrentTab(1);
							Checkout_Tablayout.tab.getTabWidget().getChildAt(1)
									.setClickable(true);
							System.out.println("Langauge_Arabic"
									+ Checkout_Tablayout.Langauge_Arabic);

						} else {
							
							
							
							if (cd.isConnectingToInternet()) {

								new checkoutstep1().execute();

							} else {

								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(),
												R.string.No_Internet_Connection,
												Toast.LENGTH_SHORT).show();
										// do {
										System.out.println("do-while");
										if (cd.isConnectingToInternet()) {

											new checkoutstep1().execute();

										}
										// } while (cd.isConnectingToInternet() == false);

									}

								});
							}
							

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

					// Checkout_Tablayout.tab.setCurrentTab(3);
					// Checkout_Tablayout.tab.getTabWidget().getChildAt(3)
					// .setClickable(true);
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub
		ly_offers_description = (LinearLayout) findViewById(R.id.ly_offers_description_popup);
		ly_offer = (LinearLayout) findViewById(R.id.ly_offer);
		txv_offfer_name = (TextView) findViewById(R.id.txv_offfer_name);
		LV_Cart_Payment = (ListView) findViewById(R.id.LV_Cart_Payment);
		txv_continue = (TextView) findViewById(R.id.txv_continue);
		txv_rdbtn_select_method = (TextView) findViewById(R.id.txv_rdbtn_select_method);
		payment_txv_total = (TextView) findViewById(R.id.payment_txv_total);
		txv_delivery_charge = (TextView) findViewById(R.id.txv_Payment_delivery_charge);
		rdbtn_payment_method = (RadioButton) findViewById(R.id.rdbtn_select_method);
		txv_select_method_title = (TextView) findViewById(R.id.txv_select_method_title);
	}

	public void showDialogForOffer() {

		dialog = new Dialog(Payment_Activity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_offer_layout);
		lv_cart_details = (ListView) dialog.findViewById(R.id.lv_cart_offer);
		LinearLayout desc_boarder_line_layout = (LinearLayout) dialog
				.findViewById(R.id.offer_desc_boarder_line_layout);
		LinearLayout ly_Offer = (LinearLayout) dialog
				.findViewById(R.id.ly_Not_Offer);

		ImageView img_cancel = (ImageView) dialog.findViewById(R.id.img_cancel);
		try {

			// if (offers != null) {
			// System.out.println("offersss" + offers);
			// adapter_for_payment_offer = new Adapter_for_Payment_Offers(
			// Payment_Activity.this, offers);
			// lv_cart_details.setAdapter(adapter_for_payment_offer);
			// ly_Offer.setVisibility(View.GONE);
			// desc_boarder_line_layout.setVisibility(View.VISIBLE);
			// } else {
			// desc_boarder_line_layout.setVisibility(View.GONE);
			// ly_Offer.setVisibility(View.VISIBLE);
			//
			// }
			img_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();
		} catch (NullPointerException n) {

		}
	}

	public void showDialogForSendVerificationCode() {

		dialog_SendVerification = new Dialog(Payment_Activity.this);
		dialog_SendVerification.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_SendVerification
				.setContentView(R.layout.popup_mobile_verification_sendcode);

		ImageView img_send_code = (ImageView) dialog_SendVerification
				.findViewById(R.id.img_send_code);
		ImageView img_send_code_cancel = (ImageView) dialog_SendVerification
				.findViewById(R.id.img_send_code_cancel);
		try {

			img_send_code.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						new async_SendverApi().execute();
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

					dialog_SendVerification.dismiss();

					showDialogForEntercode();
				}
			});
			img_send_code_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_SendVerification.dismiss();
					// Checkout_Tablayout.tab.setCurrentTab(3);
					// Checkout_Tablayout.tab.getTabWidget().getChildAt(3)
					// .setClickable(true);

				}
			});

			dialog_SendVerification.show();
		} catch (NullPointerException n) {

		}
	}

	public void showDialogForEntercode() {
		Context context = null;
		dialog_EnterCode = new Dialog(Payment_Activity.this);
		dialog_EnterCode.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_EnterCode.setContentView(R.layout.popup_enter_verifycode);

		img_popup_next = (ImageView) dialog_EnterCode
				.findViewById(R.id.img_popup_next);
		ImageView img_enter_code_cancel = (ImageView) dialog_EnterCode
				.findViewById(R.id.img_enter_code_cancel);
		Ed_Enter_Code = (EditText) dialog_EnterCode
				.findViewById(R.id.ed_enter_code);
		txv_verification_error = (TextView) dialog_EnterCode
				.findViewById(R.id.txv_verification_error);

		// txv_verification_error.setVisibility(View.GONE);

		Ed_Enter_Code.setText("1234");

		Ed_Enter_Code.setEnabled(false);

		// String str_ed_enter_code = Ed_Enter_Code.getText().toString();
		try {
			if (Ed_Enter_Code.equals("")) {
				Toast.makeText(context, R.string.popup_Sendcode_error,
						Toast.LENGTH_LONG).show();
				Ed_Enter_Code.requestFocus();
			} else {
				img_popup_next.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							new async_step_CheckVer().execute();

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
			img_enter_code_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog_EnterCode.dismiss();
					// Checkout_Tablayout.tab.setCurrentTab(3);
					// Checkout_Tablayout.tab.getTabWidget().getChildAt(3)
					// .setClickable(true);
				}
			});

			dialog_EnterCode.show();
		} catch (NullPointerException n) {

		}
	}

	/************* async_Step_Choose_Time ************/
	public class async_Step_Choose_Time extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		JSONObject Shipping_Request_Main = new JSONObject();
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = new ProgressDialog(Payment_Activity.this);
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("async_Step_Choose_Time+on_pre");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			

			try {
				Shipping_Request_Main.put("rest_id", Global_variable.hotel_id);
				Shipping_Request_Main.put("addr_type",
						Global_variable.shipping_tag_addr_type);
				// System.out.println("fix_city_spinner"+fetch_spinner);

				Shipping_Request_Main.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				System.out.println("global_shipping_child"
						+ Global_variable.Shipping_Request_Child);
				Shipping_Request_Main.put("ShippingForm",
						Global_variable.Shipping_Request_Child);
				Shipping_Request_Main.put("cart", Global_variable.cart);
				Shipping_Request_Main.put("time_from",
						Global_variable.str_Time_From);
				Shipping_Request_Main.put("time_to",
						Global_variable.str_Time_To);
				Shipping_Request_Main.put("date", Global_variable.str_Date);
				Shipping_Request_Main.put("user_id",
						SharedPreference.getuser_id(getApplicationContext()));

				System.out.println("async_step_Shipping_Request_Child"
						+ Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + Shipping_Request_Main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_StepChooseTime_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						Shipping_Request_Main.toString(), "UTF-8");
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
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("async_Shipping_last_text"
							+ responseText);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				} catch (NullPointerException n) {
					n.printStackTrace();
				}

				json = new JSONObject(responseText);
				System.out.println("async_Shipping_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("Step_choose_time_Shipping_last_json_in_post"
					+ json);
			JSONObject data = new JSONObject();
			try {
				data = json.getJSONObject("data");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException np) {
				np.printStackTrace();
			}
			JSONArray payment_Method = new JSONArray();
			// JSONObject payment_methods_object=new JSONObject();
			try {
				// offers = json.getJSONObject("data").getJSONArray("offers");
				// System.out.println("offerspayment" + offers);
				payment_Method = json.getJSONObject("data").getJSONArray(
						"payment_methods");
				System.out.println("payment_Method" + payment_Method);
				int lenth_payment_methods = payment_Method.length();
				System.out.println("lenth" + lenth_payment_methods);
				for (int i = 0; i <= lenth_payment_methods; i++) {
					try {
						JSONObject payment_methods_object = payment_Method
								.getJSONObject(i);

						/*********** Payment method *****/
						str_name_payment_methods = payment_methods_object
								.getString("name");
						Global_variable.Payment_Method_Id = payment_methods_object
								.getString("id");

						System.out.println("Global_variable.Payment_Method_Id"
								+ Global_variable.Payment_Method_Id);

						String str_icon_payment_methods = payment_methods_object
								.getString("icon");
						System.out.println("str_name_payment_methods"
								+ str_name_payment_methods);
						txv_select_method_title
								.setText(str_name_payment_methods);
						txv_rdbtn_select_method
								.setText(str_name_payment_methods);
					} catch (NullPointerException n) {

					} catch (Exception ex) {
						System.out.println("Error" + ex);
					}
				}
				System.out.println("payment_Method" + payment_Method);
				// System.out.println("offers" + offers);
				str_delivery_charge = data.getString("deliveryCharge");
				/*********** Mobile Verify ***********/
				str_needMobileVerify = json.getJSONObject("data").getString(
						"needMobileVerify");
				System.out.println("str_needMobileVerify"
						+ str_needMobileVerify);

				System.out.println("delivery_charge" + str_delivery_charge);

				String Cart_total = String.valueOf(Global_variable.cart_total);
				System.out.println("Cart_total" + Cart_total);
				if (!str_delivery_charge.equals("null")) {
					txv_delivery_charge
							.setText(getString(R.string.Categories_sr) + " "
									+ str_delivery_charge);
					final_Cart_Total = Integer.parseInt(str_delivery_charge)
							+ Integer.parseInt(String
									.valueOf(Global_variable.cart_total));
					System.out.println("final_Cart_Total" + final_Cart_Total);
					payment_txv_total.setText(getString(R.string.Categories_sr)
							+ " " + final_Cart_Total);
				} else if (str_delivery_charge.equals("null")) {
					txv_delivery_charge
							.setText(getString(R.string.Categories_sr) + "00");
					payment_txv_total.setText(String
							.valueOf(getString(R.string.Categories_sr) + " "
									+ Global_variable.cart_total));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException n) {

			}
			progressDialog.dismiss();

		}

	}
	
	
	
	/** Checkoutstep 1 for food active - inactive validarion */
	
	/************* async_Step_Choose_Time ************/
	public class checkoutstep1 extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		JSONObject Shipping_Request_Main = new JSONObject();
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = new ProgressDialog(Payment_Activity.this);
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("async_Step_Choose_Time+on_pre");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			

			try {
				Shipping_Request_Main.put("rest_id", Global_variable.hotel_id);
				Shipping_Request_Main.put("addr_type",
						Global_variable.shipping_tag_addr_type);
				// System.out.println("fix_city_spinner"+fetch_spinner);

				Shipping_Request_Main.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				System.out.println("global_shipping_child"
						+ Global_variable.Shipping_Request_Child);
				Shipping_Request_Main.put("ShippingForm",
						Global_variable.Shipping_Request_Child);
				Shipping_Request_Main.put("cart", Global_variable.cart);
				Shipping_Request_Main.put("time_from",
						Global_variable.str_Time_From);
				Shipping_Request_Main.put("time_to",
						Global_variable.str_Time_To);
				Shipping_Request_Main.put("date", Global_variable.str_Date);
				Shipping_Request_Main.put("user_id",
						SharedPreference.getuser_id(getApplicationContext()));

				System.out.println("async_step_Shipping_Request_Child"
						+ Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + Shipping_Request_Main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_CheckoutStep1_api_path);
				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						Shipping_Request_Main.toString(), "UTF-8");
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
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("async_Shipping_last_text"
							+ responseText);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				} catch (NullPointerException n) {
					n.printStackTrace();
				}

				json = new JSONObject(responseText);
				System.out.println("async_Shipping_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("Step_choose_time_Shipping_last_json_in_post"
					+ json);
			JSONObject data = new JSONObject();
			try {
				
				String success = json.getString("success");
				if(success.equalsIgnoreCase("true"))
				{
					Global_variable.flag_arabic_receipt = true;
					System.out
							.println("Global_variable_flag_arabic_receipt_payment"
									+ Global_variable.flag_arabic_receipt);
					Checkout_Tablayout.tab.setCurrentTab(3);
					Checkout_Tablayout.tab.getTabWidget().getChildAt(3)
							.setClickable(true);
				}
				else
				{
					data = json.getJSONObject("data");
					JSONObject errors = data.getJSONObject("errors");
					String cart = errors.getString("cart");
					Toast.makeText(getApplicationContext(), cart, Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),Cart.class);
					startActivity(i);
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException np) {
				np.printStackTrace();
			}
			
			progressDialog.dismiss();

		}

	}


	/******* Api ***********/

	/*********** async_SendverApi *************/
	public class async_SendverApi extends AsyncTask<Void, Void, Void> {
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

			/********** FULL REQUEST WITH SHIPPING,CART,AND OTHER STRING *************/

			JSONObject Shipping_Request_Main = new JSONObject();
			try {
				Shipping_Request_Main.put("rest_id", Global_variable.hotel_id);
				Shipping_Request_Main.put("addr_type",
						Global_variable.shipping_tag_addr_type);
				// System.out.println("fix_city_spinner"+fetch_spinner);

				Shipping_Request_Main.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				Shipping_Request_Main.put("ShippingForm",
						Global_variable.Shipping_Request_Child);
				Shipping_Request_Main.put("cart", Global_variable.cart);
				Shipping_Request_Main.put("time_from",
						Global_variable.str_Time_From);
				Shipping_Request_Main.put("time_to",
						Global_variable.str_Time_To);
				Shipping_Request_Main.put("date", Global_variable.str_Date);
				Shipping_Request_Main.put("sendcode", "1");
				Shipping_Request_Main.put("user_id",
						SharedPreference.getuser_id(getApplicationContext()));
				System.out.println("async_step_Shipping_Request_Child"
						+ Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Shipping_Request_Main" + Shipping_Request_Main);

			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_SendVer_api_path);

				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						Shipping_Request_Main.toString(), "UTF-8");
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
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out
							.println("Mobile_verify_last_text" + responseText);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}

				json = new JSONObject(responseText);
				System.out.println("Mobile_verify_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Global_variable.json_object_Mobile_Verify_Number = new JSONObject();
			Global_variable.json_Data_array_Mobile_Verify_Number = new JSONArray();
			// json success tag

			/********* Details jsonviewr ******/
			// data
			// { String:
			// success{
			// success="true" ,
			// number="999999999"
			// } ,
			// data[]
			// }

			try {
				String message = json.getString("message");
				String sessid = json.getString("sessid");

				System.out.println("sessid" + sessid);
				System.out.println("message" + message);
				// JSONObject data = json.getJSONObject("data");

				Global_variable.json_Data_array_Mobile_Verify_Number = json
						.getJSONArray("data");

				Global_variable.json_object_Mobile_Verify_Number = json
						.getJSONObject("success");

				/********** GET MOBILE NO ************/
				String str_Success = Global_variable.json_object_Mobile_Verify_Number
						.getString("success");
				System.out.println("str_Success" + str_Success);
				if (str_Success.equals("true")) {
					String Mobile_No = Global_variable.json_object_Mobile_Verify_Number
							.getString("number");
					System.out.println("Mobile_No" + Mobile_No);

					// runOnUiThread(new Runnable() {
					//
					// @Override
					// public void run() {
					// SendSms();
					// }
					// });

					System.out
							.println("json_Data_array_Mobile_Verify_Number"
									+ Global_variable.json_Data_array_Mobile_Verify_Number);
					System.out.println("json_object_Mobile_Verify_Number"
							+ Global_variable.json_object_Mobile_Verify_Number);
				} else if (str_Success.equals("false")) {
					String str_error = Global_variable.json_object_Mobile_Verify_Number
							.getString("error");
					System.out.println("str_error_send" + str_error);

					
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

	/******* Api ***********/
	/************ async_step_CheckVer *****************/

	public class async_step_CheckVer extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		JSONObject Shipping_Request_Main = new JSONObject();
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

			/********** FULL REQUEST WITH SHIPPING,CART,AND OTHER STRING *************/

			

			try {
				Shipping_Request_Main.put("rest_id", Global_variable.hotel_id);
				Shipping_Request_Main.put("addr_type",
						Global_variable.shipping_tag_addr_type);
				// System.out.println("fix_city_spinner"+fetch_spinner);

				Shipping_Request_Main.put("sessid",
						SharedPreference.getsessid(getApplicationContext()));
				Shipping_Request_Main.put("ShippingForm",
						Global_variable.Shipping_Request_Child);
				Shipping_Request_Main.put("cart", Global_variable.cart);
				Shipping_Request_Main.put("time_from",
						Global_variable.str_Time_From);
				Shipping_Request_Main.put("time_to",
						Global_variable.str_Time_To);
				Shipping_Request_Main.put("date", Global_variable.str_Date);
				Shipping_Request_Main.put("sendcode", Ed_Enter_Code.getText()
						.toString());
				Shipping_Request_Main.put("user_id",
						SharedPreference.getuser_id(getApplicationContext()));
				System.out.println("async_step_Shipping_Request_Child"
						+ Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url
						+ Global_variable.rf_stepCheckVer_api_path);

				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(
						Shipping_Request_Main.toString(), "UTF-8");
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
					responseText = EntityUtils.toString(
							httpresponse.getEntity(), "UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("Mobile_Verify_Sendcode_main_lasttext"
							+ responseText);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}

				json = new JSONObject(responseText);
				System.out.println("Mobile_Verify_Sendcode_main_last_json"
						+ json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				Global_variable.json_Object_stepCheckVer_Mobile_no = new JSONObject();
				Global_variable.json_Array_stepCheckVer_Mobile_no = new JSONArray();
				// json success tag

				/********* Details jsonviewr ******/
				// data
				// { message:
				// success{
				// success="true" ,
				// } ,
				// data[]
				// }

				// data
				// { message:
				// sessid:
				// success{
				// success="false" ,
				// "error":"Mobile Verification code is not valid"
				// } ,
				// data[]
				// }

				try {
					String message = json.getString("message");
					String sessid = json.getString("sessid");

					System.out.println("sessid" + sessid);
					System.out.println("message" + message);
					// JSONObject data = json.getJSONObject("data");

					Global_variable.json_Array_stepCheckVer_Mobile_no = json
							.getJSONArray("data");
					System.out
							.println("Global_variable.json_Array_stepCheckVer_Mobile_no"
									+ Global_variable.json_Array_stepCheckVer_Mobile_no);
					Global_variable.json_Object_stepCheckVer_Mobile_no = json
							.getJSONObject("success");
					System.out
							.println("Global_variable.json_Object_stepCheckVer_Mobile_no"
									+ Global_variable.json_Object_stepCheckVer_Mobile_no);

					str_Success_stepcheck = Global_variable.json_Object_stepCheckVer_Mobile_no
							.getString("success");
					System.out.println("stepcheck_str_Success"
							+ str_Success_stepcheck);

					System.out
							.println("json_Array_stepCheckVer_Mobile_no"
									+ Global_variable.json_Array_stepCheckVer_Mobile_no);
					System.out
							.println("json_Object_stepCheckVer_Mobile_no"
									+ Global_variable.json_Object_stepCheckVer_Mobile_no);
					try {
						if (str_Success_stepcheck.equalsIgnoreCase("true")) {
							// Reciept_Activity.adapter_receipt.notifyDataSetChanged();
							System.out.println("stepcheck_str_Success_true"
									+ str_Success_stepcheck);
							txv_verification_error.setVisibility(View.GONE);
							dialog_EnterCode.dismiss();
							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {
								if (Checkout_Tablayout.language.equals("ar")) {
									// Checkout_Tablayout obj_chk = new
									// Checkout_Tablayout();
									// obj_chk.setNewTab(Payment_Activity.this,
									// Checkout_Tablayout.tab, "receipt",
									// R.string.tab_Reciept,
									// Checkout_Tablayout.intent3);
									// Checkout_Tablayout.Langauge_Arabic=false;
									Checkout_Tablayout.tab.getTabWidget()
											.getChildAt(1)
											.setVisibility(View.VISIBLE);
									Checkout_Tablayout.tab.getTabWidget()
											.getChildAt(0)
											.setVisibility(View.GONE);
									Global_variable.flag_arabic_receipt = true;
									System.out
											.println("Global_variable_flag_arabic_receipt_payment_arabic"
													+ Global_variable.flag_arabic_receipt);
									Checkout_Tablayout.tab.setCurrentTab(1);
									Checkout_Tablayout.tab.getTabWidget()
											.getChildAt(1).setClickable(true);
									System.out
											.println("Langauge_Arabic"
													+ Checkout_Tablayout.Langauge_Arabic);

								} else {
									Global_variable.flag_arabic_receipt = true;
									System.out
											.println("Global_variable_flag_arabic_receipt_payment"
													+ Global_variable.flag_arabic_receipt);
									Checkout_Tablayout.tab.setCurrentTab(3);
									Checkout_Tablayout.tab.getTabWidget()
											.getChildAt(3).setClickable(true);

								}

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

						} else if (str_Success_stepcheck
								.equalsIgnoreCase("false")) {
							System.out.println("stepcheck_str_Success_false"
									+ str_Success_stepcheck);
							String str_errors = Global_variable.json_Object_stepCheckVer_Mobile_no
									.getString("error");
							txv_verification_error.setText(str_errors);
							txv_verification_error.setVisibility(View.VISIBLE);
							System.out.println("str_errror_payment"
									+ str_errors);
							// Toast.makeText(context, str_errors,
							// Toast.LENGTH_SHORT)
							// .show();
							
							System.out.println("stepcheck_str_errors"
									+ str_errors);

//							JSONObject Error = Shipping_Request_Main.getJSONObject("data");
//							if(Error.has("cart")){
//								Toast.makeText(getApplicationContext(), Error.getString("cart").toString(), Toast.LENGTH_LONG).show();
//							}
						}

					} catch (NullPointerException nupexc) {

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException nuexc) {
					// TODO Auto-generated catch block
					nuexc.printStackTrace();
				}
			} catch (NullPointerException n) {
				n.printStackTrace();
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

		Bool_Apply = false;
		Intent i = new Intent(Payment_Activity.this, Cart.class);
		startActivity(i);

	}

	private void SendSms() {

		try {
			// Construct data
			data = "";
			/*
			 * Note the suggested encoding for certain parameters, notably the
			 * username, password and especially the message. ISO-8859-1 is
			 * essentially the character set that we use for message bodies,
			 * with a few exceptions for e.g. Greek characters. For a full list,
			 * see: http://developer.bulksms
			 * .com/eapi/submission/character-encoding/
			 */
			data += "username="
					+ URLEncoder.encode(Global_variable.sms_username,
							"ISO-8859-1");
			data += "&password="
					+ URLEncoder.encode(Global_variable.sms_password,
							"ISO-8859-1");
			data += "&message=" + URLEncoder.encode("", "ISO-8859-1");
			data += "&want_report=1";
			data += "&msisdn="
					+ Global_variable.country_code
					+ Global_variable.json_object_Mobile_Verify_Number
							.getString("number");
			System.out.println("...data" + data);
			Toast.makeText(Payment_Activity.this,
					getString(R.string.str_successfully_Data_send),
					Toast.LENGTH_SHORT).show();
			// Send data
			// Please see the FAQ regarding HTTPS (port 443) and HTTP
			// (port
			// 80/5567)

			try {
				final URL url = new URL(
						"https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();

				Toast.makeText(Payment_Activity.this,
						getString(R.string.str_successfully_sent),
						Toast.LENGTH_SHORT).show();

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				System.out.println("...rdmsg" + rd);
				String line;
				while ((line = rd.readLine()) != null) {
					// Print the response output...
					System.out.println("...linee" + line);
					Toast.makeText(Payment_Activity.this,
							getString(R.string.str_successfully_sent),
							Toast.LENGTH_SHORT).show();
				}

				wr.close();
				rd.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
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

}
