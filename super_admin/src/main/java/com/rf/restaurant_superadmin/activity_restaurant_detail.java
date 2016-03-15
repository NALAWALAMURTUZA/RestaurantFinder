
package com.rf.restaurant_superadmin;

import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

public class activity_restaurant_detail extends Activity {

	/* Internet connection */
	ConnectionDetector cd;
	EditText rf_registration_ed_fname, rf_registration_ed_lname,
			rf_registration_country_name, rf_registration_region_name,
			rf_registration_ed_restaurant_name, rf_registration_ed_email,
			rf_country_code, rf_registration_ed_rphone,
			rf_registration_ed_address, rf_registration_ed_zip_code,
			rf_registration_ed_city_name, rf_registration_ed_website,
			rf_registration_ed_package, rf_registration_ed_status;

	Button rf_registration_btn_approve, rf_registration_btn_decline;

	RadioButton rb_rf_regi_mr, rb_rf_regi_mrs, rb_rf_regi_miss;
	int position;

	String restaurant_status, restaurant_id;
	String string_restaurant_booking_charge;

	HttpConnection http = new HttpConnection();
	
	/** Manage restaurant*/
	LinearLayout ll_number_linked_customer,ll_restaurant_booking_charge,ll_global_booking_charge;
	EditText rf_registration_ed_number_linked_customer,rf_registration_ed_restaurant_booking_charge,rf_registration_ed_global_booking_charge;
	ImageView rf_registration_img_restaurant_booking_charge_update;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_restaurant_detail);
		initializeWidget();
		/* create Object of internet connection* */
		cd = new ConnectionDetector(getApplicationContext());

		Intent i = getIntent();
		position = i.getIntExtra("position", 0);

		System.out.println("!!!!position" + position);
		setValues();
		setonclicklistener();
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		rf_registration_btn_approve
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						runOnUiThread(new Runnable() {
							public void run() {

								/** check Internet Connectivity */
								if (cd.isConnectingToInternet()) {

									restaurant_status = "1";
									new update_restaurant_status().execute();

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

						});

					}

				});

		rf_registration_btn_decline
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						runOnUiThread(new Runnable() {
							public void run() {

								/** check Internet Connectivity */
								if (cd.isConnectingToInternet()) {

									restaurant_status = "0";
									new update_restaurant_status().execute();

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

						});

					}

				});
		
		

	}

	private void initializeWidget() {
		// TODO Auto-generated method stub

		rb_rf_regi_mr = (RadioButton) findViewById(R.id.rb_rf_regi_mr);
		rb_rf_regi_mrs = (RadioButton) findViewById(R.id.rb_rf_regi_mrs);
		rb_rf_regi_miss = (RadioButton) findViewById(R.id.rb_rf_regi_miss);

		rf_registration_ed_fname = (EditText) findViewById(R.id.rf_registration_ed_fname);
		rf_registration_ed_lname = (EditText) findViewById(R.id.rf_registration_ed_lname);
		rf_registration_country_name = (EditText) findViewById(R.id.rf_registration_country_name);
		rf_registration_region_name = (EditText) findViewById(R.id.rf_registration_region_name);
		rf_registration_ed_restaurant_name = (EditText) findViewById(R.id.rf_registration_ed_restaurant_name);
		rf_registration_ed_email = (EditText) findViewById(R.id.rf_registration_ed_email);
		rf_country_code = (EditText) findViewById(R.id.rf_country_code);
		rf_registration_ed_rphone = (EditText) findViewById(R.id.rf_registration_ed_rphone);
		rf_registration_ed_address = (EditText) findViewById(R.id.rf_registration_ed_address);
		rf_registration_ed_zip_code = (EditText) findViewById(R.id.rf_registration_ed_zip_code);
		rf_registration_ed_city_name = (EditText) findViewById(R.id.rf_registration_ed_city_name);
		rf_registration_ed_website = (EditText) findViewById(R.id.rf_registration_ed_website);
		rf_registration_ed_package = (EditText) findViewById(R.id.rf_registration_ed_package);
		rf_registration_ed_status = (EditText) findViewById(R.id.rf_registration_ed_status);

		rf_registration_btn_approve = (Button) findViewById(R.id.rf_registration_btn_approve);
		rf_registration_btn_decline = (Button) findViewById(R.id.rf_registration_btn_decline);
	
		ll_number_linked_customer = (LinearLayout) findViewById(R.id.ll_number_linked_customer);
		ll_restaurant_booking_charge = (LinearLayout) findViewById(R.id.ll_restaurant_booking_charge);
		ll_global_booking_charge = (LinearLayout) findViewById(R.id.ll_global_booking_charge);
		rf_registration_ed_number_linked_customer  = (EditText) findViewById(R.id.rf_registration_ed_number_linked_customer);
		rf_registration_ed_restaurant_booking_charge   = (EditText) findViewById(R.id.rf_registration_ed_restaurant_booking_charge);
		rf_registration_ed_global_booking_charge  = (EditText) findViewById(R.id.rf_registration_ed_global_booking_charge);
		rf_registration_img_restaurant_booking_charge_update = (ImageView)findViewById(R.id.rf_registration_img_restaurant_booking_charge_update);
	
	
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

		Intent i;
		switch (Global_variable.click_flag_home_screen) {
		case "restaurant_list":
			i = new Intent(getApplicationContext(),
					activity_resturant_list.class);
			startActivity(i);

			break;

		case "manage_restaurant":

			i = new Intent(getApplicationContext(),
					activity_manage_restaurant.class);
			startActivity(i);
			break;

		}

	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	


	private void setValues() {
		// TODO Auto-generated method stub

		runOnUiThread(new Runnable() {
			public void run() {

				/** check Internet Connectivity */
				if (cd.isConnectingToInternet()) {

					try {

						String gender = Global_variable.array_filter_Restaurant_List
								.getJSONObject(position).getString("gender");
						if (gender.equalsIgnoreCase("mr")) {
							rb_rf_regi_mr.setChecked(true);
						} else if (gender.equalsIgnoreCase("mrs")) {
							rb_rf_regi_mrs.setChecked(true);
						} else if (gender.equalsIgnoreCase("miss")) {
							rb_rf_regi_miss.setChecked(true);
						}

						restaurant_id = Global_variable.array_filter_Restaurant_List
								.getJSONObject(position).getString("id");

						rf_registration_ed_fname
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"FirstName"));
						rf_registration_ed_lname
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"LastName"));
						rf_registration_country_name
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"country_name"));
						rf_registration_region_name
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"region_name"));
						rf_registration_ed_restaurant_name
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"restaurant_name"));
						rf_registration_ed_email
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"restaurant_email"));
						rf_country_code
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"country_call_code"));
						rf_registration_ed_rphone
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"contact_number"));
						rf_registration_ed_address
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"address"));
						rf_registration_ed_zip_code
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"zip"));
						rf_registration_ed_city_name
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"city_name"));
						rf_registration_ed_website
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"website"));
						rf_registration_ed_package
								.setText(Global_variable.array_filter_Restaurant_List
										.getJSONObject(position).getString(
												"package_name"));

						restaurant_status = Global_variable.array_filter_Restaurant_List
								.getJSONObject(position).getString("status");

						if (restaurant_status.equalsIgnoreCase("0")) {
							rf_registration_ed_status.setText(getString(R.string.str_Pending));
							
//							rf_registration_ed_status.setText("Pending"
//									+ restaurant_id);

							rf_registration_btn_approve
									.setVisibility(View.VISIBLE);
							rf_registration_btn_decline
									.setVisibility(View.GONE);
						} else if (restaurant_status.equalsIgnoreCase("1")) {

							rf_registration_ed_status.setText(getString(R.string.str_Approved));
//							rf_registration_ed_status.setText("Approved"
//									+ restaurant_id);

							rf_registration_btn_approve
									.setVisibility(View.GONE);
							rf_registration_btn_decline
									.setVisibility(View.VISIBLE);

						}
						
						
						switch (Global_variable.click_flag_home_screen) {
						case "restaurant_list":
							
							ll_number_linked_customer.setVisibility(View.GONE);
							ll_restaurant_booking_charge.setVisibility(View.GONE);
							ll_global_booking_charge.setVisibility(View.GONE);
							
							break;

						case "manage_restaurant":
							
//							ll_number_linked_customer,ll_restaurant_booking_charge,ll_global_booking_charge;
//							rf_registration_ed_number_linked_customer,rf_registration_ed_restaurant_booking_charge,rf_registration_ed_global_booking_charge;
						
							ll_number_linked_customer.setVisibility(View.VISIBLE);
							ll_restaurant_booking_charge.setVisibility(View.VISIBLE);
							ll_global_booking_charge.setVisibility(View.VISIBLE);

							
							rf_registration_ed_number_linked_customer
							.setText(Global_variable.array_filter_Restaurant_List
									.getJSONObject(position).getString(
											"number_of_linked_customer")+" "+getString(R.string.str_persons));
							
//							rf_registration_ed_restaurant_booking_charge
//							.setText(Global_variable.array_filter_Restaurant_List
//									.getJSONObject(position).getString(
//											"restaurant_booking_charge")+" "+getString(R.string.str_dollar));
							rf_registration_ed_restaurant_booking_charge
							.setText(Global_variable.array_filter_Restaurant_List
									.getJSONObject(position).getString(
											"restaurant_booking_charge"));
							
							rf_registration_ed_global_booking_charge
							.setText(Global_variable.array_filter_Restaurant_List
									.getJSONObject(position).getString(
											"global_booking_charge"));
//							rf_registration_ed_number_linked_customer,rf_registration_ed_restaurant_booking_charge,rf_registration_ed_global_booking_charge;

							rf_registration_img_restaurant_booking_charge_update.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
//									if(rf_registration_ed_restaurant_booking_charge.getText().toString().contains(getString(R.string.str_dollar)))
//									{
//										string_restaurant_booking_charge = rf_registration_ed_restaurant_booking_charge.getText().toString().replace(getString(R.string.str_dollar), "");
////										Toast.makeText(getApplicationContext(), string_restaurant_booking_charge, Toast.LENGTH_LONG).show();
//									}
//									else
//									{
										string_restaurant_booking_charge = rf_registration_ed_restaurant_booking_charge.getText().toString();
//										Toast.makeText(getApplicationContext(), "!!!!else"+string_restaurant_booking_charge, Toast.LENGTH_LONG).show();
//									}
									
									new update_restaurant_booking_charge().execute();
									
									
								}
							});

							
							break;

						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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

	public class update_restaurant_status extends AsyncTask<Void, Void, Void> {
		JSONObject json;
		ProgressDialog p;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_restaurant_detail.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject data = new JSONObject();

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("status", restaurant_status);
				obj_child.put("restaurant_id", restaurant_id);

				obj_parent.put("Restaurant_Status", obj_child);
				obj_parent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("Activity_Login" + obj_parent);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_update_restaurant_status,
							obj_parent);

					try {

						System.out.println("after_connection.." + responseText);

						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));

						System.out.println("responseText" + json);

						// data = json.getJSONObject("data");
						//
						// System.out.print("d.." + data);

					} catch (NullPointerException ex) {
						ex.printStackTrace();
					}

				} catch (NullPointerException np) {

				}

				return null;

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Data = json.getJSONObject("data");
					Global_variable.array_Restaurant_List = json.getJSONObject(
							"data").getJSONArray("restaurant_list");

					System.out.println("!!!!restaurant_list"
							+ Global_variable.array_Restaurant_List);
					Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);
					
//					System.out.println("!!!!pankaj_sakariya_click_flag_home_screen"+Global_variable.click_flag_home_screen);
//					Global_variable.click_flag_home_screen = "manage_restaurant";
					System.out.println("!!!!pankaj_sakariya_click_flag_home_screen_detail"+Global_variable.click_flag_home_screen);
					
					Intent i ;
					switch (Global_variable.click_flag_home_screen) {
					case "restaurant_list":
						
						 i = new Intent(activity_restaurant_detail.this,
								activity_resturant_list.class);
						startActivity(i);
						
						break;

					case "manage_restaurant":
						
						 i = new Intent(activity_restaurant_detail.this,
								activity_manage_restaurant.class);
						startActivity(i);

						break;


					}

					
					
					
					// generatepdf();

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						
						if (Errors.has("restaurant_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("restaurant_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					System.out.println("Login_response" + json);
				}
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					System.out.println("Login_response" + json);
				}
				n.printStackTrace();
			}

		}

	}
	
	public class update_restaurant_booking_charge extends AsyncTask<Void, Void, Void> {
		JSONObject json;
		ProgressDialog p;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_restaurant_detail.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject data = new JSONObject();

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("restaurant_booking_charge", string_restaurant_booking_charge);
				obj_child.put("restaurant_id", restaurant_id);

				obj_parent.put("Restaurant_Booking_Charge", obj_child);
				obj_parent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("Activity_Login" + obj_parent);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_update_restaurant_booking_charge,
							obj_parent);

					try {

						System.out.println("after_connection.." + responseText);

						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));

						System.out.println("responseText" + json);

						// data = json.getJSONObject("data");
						//
						// System.out.print("d.." + data);

					} catch (NullPointerException ex) {
						ex.printStackTrace();
					}

				} catch (NullPointerException np) {

				}

				return null;

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					
					if (!json.getString("message").toString().equalsIgnoreCase("")) {
						Toast.makeText(
								getApplicationContext(),
								json.getString("message").toString(), Toast.LENGTH_LONG)
								.show();

					}
					JSONObject Data = json.getJSONObject("data");
					Global_variable.array_Restaurant_List = json.getJSONObject(
							"data").getJSONArray("restaurant_list");

					System.out.println("!!!!restaurant_list"
							+ Global_variable.array_Restaurant_List);
					Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);

			

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("restaurant_booking_charge")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("restaurant_booking_charge").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						
						if (Errors.has("restaurant_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("restaurant_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					System.out.println("Login_response" + json);
				}
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					System.out.println("Login_response" + json);
				}
				n.printStackTrace();
			}

		}

	}
}
