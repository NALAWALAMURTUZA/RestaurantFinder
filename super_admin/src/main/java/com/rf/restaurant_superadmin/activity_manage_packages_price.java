package com.rf.restaurant_superadmin;

import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_function;
import com.superadmin.global.Global_variable;
import com.superadmin.model.packages;

public class activity_manage_packages_price extends Activity {

	private LinearLayout rf_login_img_header;
	private ImageView rf_login_img_header_icon;
	private ImageView img_menu;
	private TextView rf_super_admin_txv_manage_packages_price;
	private ImageView img;
	private ImageView img_logo;
	private LinearLayout rf_login_img_header1;
	private TextView supper_admin_tv_functionality;
	private TextView supper_admin_txv_functionality;

	private TextView super_admin_txv_free;
	private TextView super_admin_txv_pro;
	private TextView super_admin_txv_gold;
	private TextView super_admin_txv_price;
	private TextView super_admin_txv_free_price;
	private TextView super_admin_txv_pro_price;
	private TextView super_admin_txv_gold_price;
	private TextView super_admin_txv_order;
	private ImageView image_free_order;
	private ImageView image_pro_order;
	private ImageView image_gold_order,img_table_free,img_table_pro,img_table_gold;
	private TextView super_admin_txv_functionality_bookingcharges;
	private TextView super_admin_txv_free_bookingcharges;
	private TextView super_admin_txv_pro_bookingcharges;
	private TextView super_admin_txv_gold_bookingcharges;
	private TextView super_admin_txv;
	private EditText ed_free,ed_pro,ed_gold;
	TextView super_admin_txv_free_price_unit, super_admin_txv_pro_price_unit,
			super_admin_txv_gold_price_unit;
	private Button image_update_free, image_update_pro, image_update_gold;
	TextView super_admin_txv_free_package_id, super_admin_txv_pro_package_id,
			super_admin_txv_gold_package_id;

	String[] package_id, package_name, package_description,
			global_booking_charge, price, booking_limit, online_order_limit;

	ProgressDialog p;

	JSONObject package_data = new JSONObject();
	HttpConnection http = new HttpConnection();

	/* Internet connection */
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_manage_packages_price);

		// step 5
		initializeWidget();
		/* create Object of internet connection* */
		cd = new ConnectionDetector(getApplicationContext());
		setValues();
		setonclicklistener();
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

		packages obj = null;
		// for(int i=0;i<Global_variable.array_Package.length();i++)
		{
			try {

				super_admin_txv_free_price_unit.setText(getResources()
						.getString(R.string.super_admin_txv_price_unit_lei)
						+ getResources().getString(
								R.string.super_admin_txv_price_unit_month));
				super_admin_txv_pro_price_unit.setText(getResources()
						.getString(R.string.super_admin_txv_price_unit_lei)
						+ getResources().getString(
								R.string.super_admin_txv_price_unit_month));
				super_admin_txv_gold_price_unit.setText(getResources()
						.getString(R.string.super_admin_txv_price_unit_lei)
						+ getResources().getString(
								R.string.super_admin_txv_price_unit_month));

				obj = new packages(
						Global_variable.array_Package.getJSONObject(0));
				// System.out.println("!!!!package_name"+obj.getPackage_name());
				// System.out.println("!!!!package_price"+obj.getPrice());
				// System.out.println("!!!!package_global_charge"+obj.getGlobal_booking_charge());
				// System.out.println("!!!!package_id"+obj.getPackage_id());
				// System.out.println("!!!!package_description"+obj.getPackage_description());

				super_admin_txv_free_package_id.setText(obj.getPackage_id());
				super_admin_txv_free.setText(obj.getPackage_name());
				super_admin_txv_free_price.setText(obj.getPrice());
				super_admin_txv_free_bookingcharges.setText(obj
						.getGlobal_booking_charge());
				ed_free.setText(obj.getPackage_description());
				if(obj.getBooking_limit().equalsIgnoreCase("1"))
				{
					img_table_free.setImageDrawable(getResources().getDrawable(R.drawable.true_icon_png));
				}
				else
				{
					img_table_free.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon_png));
				}
				
				if(Integer.valueOf(obj.getOnline_order_limit())>1)
				{
					image_free_order.setImageDrawable(getResources().getDrawable(R.drawable.true_icon_png));
				}
				else
				{
					image_free_order.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon_png));
				}

				obj = new packages(
						Global_variable.array_Package.getJSONObject(1));
				// System.out.println("!!!!package_name"+obj.getPackage_name());
				// System.out.println("!!!!package_price"+obj.getPrice());
				// System.out.println("!!!!package_global_charge"+obj.getGlobal_booking_charge());
				// System.out.println("!!!!package_id"+obj.getPackage_id());
				// System.out.println("!!!!package_description"+obj.getPackage_description());

				super_admin_txv_pro_package_id.setText(obj.getPackage_id());
				super_admin_txv_pro.setText(obj.getPackage_name());
				super_admin_txv_pro_price.setText(obj.getPrice());
				super_admin_txv_pro_bookingcharges.setText(obj
						.getGlobal_booking_charge());
				ed_pro.setText(obj.getPackage_description());
				if(obj.getBooking_limit().equalsIgnoreCase("1"))
				{
					img_table_pro.setImageDrawable(getResources().getDrawable(R.drawable.true_icon_png));
				}
				else
				{
					img_table_pro.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon_png));
				}
				
				if(Integer.valueOf(obj.getOnline_order_limit())>1)
				{
					image_pro_order.setImageDrawable(getResources().getDrawable(R.drawable.true_icon_png));
				}
				else
				{
					image_pro_order.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon_png));
				}
				obj = new packages(
						Global_variable.array_Package.getJSONObject(2));
				// System.out.println("!!!!package_name"+obj.getPackage_name());
				// System.out.println("!!!!package_price"+obj.getPrice());
				// System.out.println("!!!!package_global_charge"+obj.getGlobal_booking_charge());
				// System.out.println("!!!!package_id"+obj.getPackage_id());
				// System.out.println("!!!!package_description"+obj.getPackage_description());

				super_admin_txv_gold_package_id.setText(obj.getPackage_id());
				super_admin_txv_gold.setText(obj.getPackage_name());
				super_admin_txv_gold_price.setText(obj.getPrice());
				super_admin_txv_gold_bookingcharges.setText(obj
						.getGlobal_booking_charge());
				ed_gold.setText(obj.getPackage_description());
				if(obj.getBooking_limit().equalsIgnoreCase("1"))
				{
					img_table_gold.setImageDrawable(getResources().getDrawable(R.drawable.true_icon_png));
				}
				else
				{
					img_table_gold.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon_png));
				}
				
				if(Integer.valueOf(obj.getOnline_order_limit())>1)
				{
					image_gold_order.setImageDrawable(getResources().getDrawable(R.drawable.true_icon_png));
				}
				else
				{
					image_gold_order.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon_png));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub

		image_update_free.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							package_data = Global_function.package_data(
									super_admin_txv_free_package_id.getText()
											.toString(),
									super_admin_txv_free_price.getText()
											.toString(),
									super_admin_txv_free_bookingcharges
											.getText().toString(),ed_free.getText().toString());

							// super_admin_txv_free.getText().toString();
							// super_admin_txv_pro_package_id.getText().toString();
							// super_admin_txv_pro.getText().toString();
							// super_admin_txv_pro_price.getText().toString();
							// super_admin_txv_pro_bookingcharges.getText().toString();
							//
							// super_admin_txv_gold_package_id.getText().toString();
							// super_admin_txv_gold.getText().toString();
							// super_admin_txv_gold_price.getText().toString();
							// super_admin_txv_gold_bookingcharges.getText().toString();

							new update_package().execute();
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

		image_update_pro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							package_data = Global_function.package_data(
									super_admin_txv_pro_package_id.getText()
											.toString(),
									super_admin_txv_pro_price.getText()
											.toString(),
									super_admin_txv_pro_bookingcharges
											.getText().toString(),ed_pro.getText().toString());
							new update_package().execute();
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

		image_update_gold.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							package_data = Global_function.package_data(
									super_admin_txv_gold_package_id.getText()
											.toString(),
									super_admin_txv_gold_price.getText()
											.toString(),
									super_admin_txv_gold_bookingcharges
											.getText().toString(),ed_gold.getText().toString());
							new update_package().execute();
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

		rf_login_img_header_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							Intent i = new Intent(getApplicationContext(),
									activity_home.class);

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

	}

	private void initializeWidget() {
		// TODO Auto-generated method stub
		rf_login_img_header = (LinearLayout) findViewById(R.id.rf_login_img_header);
		rf_login_img_header_icon = (ImageView) findViewById(R.id.rf_login_img_header_icon);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		rf_super_admin_txv_manage_packages_price = (TextView) findViewById(R.id.rf_supper_admin_txv_manage_packages_price);
		img = (ImageView) findViewById(R.id.img);
		img_logo = (ImageView) findViewById(R.id.img_logo);
		supper_admin_tv_functionality = (TextView) findViewById(R.id.supper_admin_tv_functionality);
		supper_admin_txv_functionality = (TextView) findViewById(R.id.supper_admin_txv_functionality);

		super_admin_txv_free_price_unit = (TextView) findViewById(R.id.super_admin_txv_free_price_unit);
		super_admin_txv_pro_price_unit = (TextView) findViewById(R.id.super_admin_txv_pro_price_unit);
		super_admin_txv_gold_price_unit = (TextView) findViewById(R.id.super_admin_txv_gold_price_unit);
		
		super_admin_txv_free = (TextView) findViewById(R.id.super_admin_txv_free);
		super_admin_txv_pro = (TextView) findViewById(R.id.super_admin_txv_pro);
		super_admin_txv_gold = (TextView) findViewById(R.id.super_admin_txv_gold);
		super_admin_txv_price = (TextView) findViewById(R.id.supper_admin_txv_price);
		super_admin_txv_free_price = (TextView) findViewById(R.id.supper_admin_txv_free_price);
		super_admin_txv_pro_price = (TextView) findViewById(R.id.supper_admin_txv_pro_price);
		super_admin_txv_gold_price = (TextView) findViewById(R.id.supper_admin_txv_gold_price);
		super_admin_txv_order = (TextView) findViewById(R.id.supper_admin_txv_order);
		
		image_free_order = (ImageView) findViewById(R.id.image_free_order);
		image_pro_order = (ImageView) findViewById(R.id.image_pro_order);
		image_gold_order = (ImageView) findViewById(R.id.image_gold_order);
		
		img_table_free = (ImageView) findViewById(R.id.img_table_free);
		img_table_pro = (ImageView) findViewById(R.id.img_table_pro);
		img_table_gold = (ImageView) findViewById(R.id.img_table_gold);
		
		super_admin_txv_functionality_bookingcharges = (TextView) findViewById(R.id.supper_admin_txv_functionality_bookingcharges);
		super_admin_txv_free_bookingcharges = (TextView) findViewById(R.id.supper_admin_txv_free_bookingcharges);
		super_admin_txv_pro_bookingcharges = (TextView) findViewById(R.id.supper_admin_txv_pro_bookingcharges);
		super_admin_txv_gold_bookingcharges = (TextView) findViewById(R.id.supper_admin_txv_gold_bookingcharges);
		super_admin_txv = (TextView) findViewById(R.id.supper_admin_txv);
		
		image_update_free = (Button) findViewById(R.id.image_update_free);
		image_update_pro = (Button) findViewById(R.id.image_update_pro);
		image_update_gold = (Button) findViewById(R.id.image_update_gold);
		
		
		
		super_admin_txv_free_package_id = (TextView) findViewById(R.id.super_admin_txv_free_package_id);
		super_admin_txv_pro_package_id = (TextView) findViewById(R.id.super_admin_txv_pro_package_id);
		super_admin_txv_gold_package_id = (TextView) findViewById(R.id.super_admin_txv_gold_package_id);
		
		ed_free = (EditText) findViewById(R.id.ed_free);
		ed_gold = (EditText) findViewById(R.id.ed_gold);
		ed_pro = (EditText) findViewById(R.id.ed_pro);

		
		
		
		/* Packages */
		package_id = new String[Global_variable.array_Package.length()];
		package_name = new String[Global_variable.array_Package.length()];
		package_description = new String[Global_variable.array_Package.length()];
		global_booking_charge = new String[Global_variable.array_Package.length()];
		price = new String[Global_variable.array_Package.length()];
		booking_limit = new String[Global_variable.array_Package.length()];
		online_order_limit = new String[Global_variable.array_Package.length()];

	}

	public class update_package extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_manage_packages_price.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				// *************
				// for request
				String responseText = http.connection(
						Global_variable.rf_api_update_package, package_data);

				try {

					System.out.println("after_connection.." + responseText);

					json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));

					System.out.println("responseText" + json);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (NullPointerException np) {
				np.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (p.isShowing()) {
				p.dismiss();
				System.out.println("Activity manage packages price" + json);
			}
			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					// Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);

					Global_variable.array_Package = json.getJSONObject("data")
							.getJSONArray("package");

					Toast.makeText(getApplicationContext(),
							json.getString("message"), Toast.LENGTH_LONG)
							.show();
					// Intent i = new
					// Intent(activity_manage_packages_price.this,activity_home.class);
					// startActivity(i);

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("package_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("package_id").get(0)
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
						if (Errors.has("global_booking_charge")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("global_booking_charge")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

		}

	}

}