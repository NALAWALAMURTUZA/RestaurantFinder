package com.rf.restaurant_superadmin;

import java.util.Locale;

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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

public class activity_home extends Activity {

	private LinearLayout rf_login_img_header;
	private ImageView rf_login_img_header_icon;
	private ImageView img_menu;
	private TextView rf_supper_admin_txv_home;
	private ImageView img;
	private ImageView img_popup;
	private LinearLayout header;
	private ImageView task_booking_icon;
	private TextView rf_supper_txv_restaurant_list;
	private ImageView setting_icon;
	private TextView rf_supper_txv_manage_restaurant;
	private ImageView profile_icon;
	private TextView rf_supper_txv_admin_profile;
	private ImageView price_icon;
	private TextView rf_supper_txv_manage_packages_price_name;
	private ImageView invoice_icon,img_refresh;
	private TextView rf_supper_txv_invoice;
	private ImageView price_icon1, img_manage_restaurant;
	private TextView txv, txt_manage_restaurant;
	ProgressDialog p;

	/* Internet connection */
	ConnectionDetector cd;
	private Locale myLocale;

	HttpConnection http = new HttpConnection();

	public LinearLayout ll_generate_invoice, ll_restuarant_list,
			ll_admin_profile, ll_manage_packages_price_name,
			ll_manage_restuarant, ll_global_setting, ll_manage_restaurant,
			ll_logout,ll_request_update_package;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

		setContentView(R.layout.activity_home);

		initializeWidget();
		cd = new ConnectionDetector(getApplicationContext());
		setonclicklistener();
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("!!!!pankaj_onstart");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("!!!!pankaj_onstop");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("!!!!pankaj_onresume");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("!!!!pankaj_onrestart");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("!!!!pankaj_ondestroy");
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

		// Intent i = new Intent(getApplicationContext(),
		// activity_home.class);
		// startActivity(i);

	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub

		ll_generate_invoice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							// new TableGrabOrder().execute();

							Global_variable.click_flag_home_screen = "generate_invoice";

							Intent i = new Intent(activity_home.this,
									activity_generate_invoice.class);
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

					}
				});
			}
		});
		ll_request_update_package
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							//Global_variable.click_flag_home_screen = "manage_package";

							Intent i = new Intent(
									activity_home.this,
									acivity_requestupdate_package.class);
							startActivity(i);

							// new TableGrabOrder().execute();
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

		ll_manage_restaurant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							// new TableGrabOrder().execute();

							Global_variable.click_flag_home_screen = "restaurantcategory";

							Intent i = new Intent(activity_home.this,
									activity_category.class);
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

					}
				});
			}
		});
		ll_restuarant_list.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							Global_variable.click_flag_home_screen = "restaurant_list";
							Intent i = new Intent(activity_home.this,
									activity_resturant_list.class);
							startActivity(i);
							// new TableGrabOrder().execute();
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
		});

		ll_global_setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							Global_variable.click_flag_home_screen = "global_setting";
							Intent i = new Intent(activity_home.this,
									activity_global_setting.class);
							startActivity(i);
							// new TableGrabOrder().execute();
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
		});
		ll_admin_profile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							Global_variable.click_flag_home_screen = "admin_profile";
							Intent i = new Intent(activity_home.this,
									activity_admin_profile.class);
							startActivity(i);

							// new TableGrabOrder().execute();
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
		});
		ll_manage_packages_price_name
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						runOnUiThread(new Runnable() {
							public void run() {

								/** check Internet Connectivity */
								if (cd.isConnectingToInternet()) {

									Global_variable.click_flag_home_screen = "manage_package";

									Intent i = new Intent(
											activity_home.this,
											activity_manage_packages_price.class);
									startActivity(i);

									// new TableGrabOrder().execute();
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

		img_popup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							// new TableGrabOrder().execute();
							PopupMenu popup = new PopupMenu(activity_home.this,
									img_popup);
							popup.getMenuInflater().inflate(R.menu.popup,
									popup.getMenu());

							popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
								public boolean onMenuItemClick(MenuItem item) {
									if (item.getTitle().toString()
											.equals(getString(R.string.pm_rl))) {
										Global_variable.click_flag_home_screen = "restaurant_list";
										Intent intent = new Intent(
												activity_home.this,
												activity_resturant_list.class);
										startActivity(intent);
										// finish();

									} else if (item.getTitle().toString()
											.equals(getString(R.string.pm_mr))) {

										{
											Global_variable.click_flag_home_screen = "manage_restaurant";
											Intent intent = new Intent(
													activity_home.this,

													activity_manage_restaurant.class);
											startActivity(intent);
										}
										// finish();

									} else if (item
											.getTitle()
											.toString()
											.equals(getString(R.string.pm_global_setting))) {

										{
											Global_variable.click_flag_home_screen = "profile";
											Intent intent = new Intent(
													activity_home.this,

													activity_global_setting.class);
											startActivity(intent);
										}
										// finish();

									} else if (item.getTitle().toString()
											.equals(getString(R.string.pm_ap))) {

										{
											Intent intent = new Intent(
													activity_home.this,

													activity_admin_profile.class);
											startActivity(intent);
										}

										// finish();

									} else if (item
											.getTitle()
											.toString()
											.equals(getString(R.string.pm_manage_restaurant))) {

										{
											Intent intent = new Intent(
													activity_home.this,

													activity_category.class);
											startActivity(intent);
										}

										// finish();

									} else if (item.getTitle().toString()
											.equals(getString(R.string.pm_mpp))) {

										{
											Intent intent = new Intent(
													activity_home.this,

													activity_manage_packages_price.class);
											startActivity(intent);
										}
										// finish();

									} else if (item.getTitle().toString()
											.equals(getString(R.string.pm_gi))) {

										{
											Intent intent = new Intent(
													activity_home.this,

													activity_generate_invoice.class);
											startActivity(intent);
										}
									}
									// finish();
									else if (item.getTitle().toString()
											.equals(getString(R.string.pm_ref))) {

										{
											new refresh().execute();
										}
										// finish();

									}

									else if (item
											.getTitle()
											.toString()
											.equals(getString(R.string.pm_logout))) {

										{
											Intent intent = new Intent(
													getApplicationContext(),

													activity_login.class);
											startActivity(intent);
										}
										// finish();

									}
									return true;

								}
							});

							popup.show();

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
		});
		ll_manage_restuarant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							Global_variable.click_flag_home_screen = "manage_restaurant";
							Intent i = new Intent(activity_home.this,
									activity_manage_restaurant.class);
							startActivity(i);
							// new TableGrabOrder().execute();
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
		});
		
		ll_logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

//							Global_variable.click_flag_home_screen = "";
							Intent i = new Intent(activity_home.this,
									activity_login.class);
							startActivity(i);
							// new TableGrabOrder().execute();
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
		});


		img_refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							new refresh().execute();
							// new TableGrabOrder().execute();
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
		});
		rf_login_img_header_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							// Intent i = new Intent(activity_home.this,
							// activity_home.class);
							// startActivity(i);
							// new TableGrabOrder().execute();
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
		});
		

	}

	private void initializeWidget() {
		// TODO Auto-generated method stub

		rf_login_img_header = (LinearLayout) findViewById(R.id.rf_login_img_header);
		rf_login_img_header_icon = (ImageView) findViewById(R.id.rf_login_img_header_icon);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		rf_supper_admin_txv_home = (TextView) findViewById(R.id.rf_supper_admin_txv_home);
		img = (ImageView) findViewById(R.id.img);
		img_popup = (ImageView) findViewById(R.id.img_popup);
		header = (LinearLayout) findViewById(R.id.header);
		task_booking_icon = (ImageView) findViewById(R.id.image_task_booking_icon);
		rf_supper_txv_restaurant_list = (TextView) findViewById(R.id.rf_supper_txv_restaurant_list);
		setting_icon = (ImageView) findViewById(R.id.setting_icon);
		rf_supper_txv_manage_restaurant = (TextView) findViewById(R.id.rf_supper_txv_manage_restaurant);
		profile_icon = (ImageView) findViewById(R.id.profile_icon);
		rf_supper_txv_admin_profile = (TextView) findViewById(R.id.rf_supper_txv_admin_profile);
		price_icon = (ImageView) findViewById(R.id.price_icon);
		rf_supper_txv_manage_packages_price_name = (TextView) findViewById(R.id.rf_supper_txv_manage_packages_price_name);
		invoice_icon = (ImageView) findViewById(R.id.invoice_icon);
		img_refresh = (ImageView) findViewById(R.id.img_refresh);
		rf_supper_txv_invoice = (TextView) findViewById(R.id.rf_supper_txv_invoice);
		price_icon1 = (ImageView) findViewById(R.id.price_icon1);
		txv = (TextView) findViewById(R.id.txv);
		ll_generate_invoice = (LinearLayout) findViewById(R.id.ll_generate_invoice);
		ll_restuarant_list = (LinearLayout) findViewById(R.id.ll_restuarant_list);
		ll_admin_profile = (LinearLayout) findViewById(R.id.ll_admin_profile);
		ll_manage_packages_price_name = (LinearLayout) findViewById(R.id.ll_manage_packages_price_name);
		ll_manage_restuarant = (LinearLayout) findViewById(R.id.ll_manage_restuarant);
		ll_global_setting = (LinearLayout) findViewById(R.id.ll_global_setting);
		ll_manage_restaurant = (LinearLayout) findViewById(R.id.ll_manage_restaurant);
		ll_logout = (LinearLayout) findViewById(R.id.ll_logout);
		ll_request_update_package = (LinearLayout)findViewById(R.id.ll_request_update_package);
	}

	public class refresh extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_home.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
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
				obj_child.put("user_id", Global_variable.admin_uid);

				obj_parent.put("RefreshForm", obj_child);
				obj_parent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("Activity_Login" + obj_parent);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_login, obj_parent);

					try {

						System.out.println("after_connection.." + responseText);

						//json = new JSONObject(responseText);
						System.out.println("responseText" + json);
						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));
						data = json.getJSONObject("data");

						System.out.print("d.." + data);

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

					Global_variable.array_Package = json.getJSONObject("data")
							.getJSONArray("package");

					Global_variable.array_profile = json.getJSONObject("data")
							.getJSONArray("profile");

					System.out.println("profile details global setting..."
							+ Global_variable.array_profile);

					Global_variable.array_restaurantcategory = json
							.getJSONObject("data").getJSONArray(
									"restaurantcategory");

					System.out.println("restaurantcategory........."
							+ Global_variable.array_restaurantcategory);

					System.out.println("!!!!restaurant_list"
							+ Global_variable.array_Restaurant_List);

					Global_variable.sessid = json.getString("sessid");
					Global_variable.admin_uid = json.getJSONObject("data")
							.getString("user_id");
					Global_variable.admin_email = json.getJSONObject("data")
							.getString("user_email");
					System.out.println("11111datalogin" + Data);

					// generatepdf();

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("user_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("user_id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

						if (Errors.has("username")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("username").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("password")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("password").get(0)
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
