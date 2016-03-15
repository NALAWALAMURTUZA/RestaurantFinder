package com.rf.restaurant_superadmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

public class activity_global_setting extends Activity {

	EditText rf_sa_ed_fname, rf_sa_ed_lname, rf_sa_ed_mini_loy,
			rf_sa_ed_max_loy, rf_sa_loy_ed_lei, rf_sa_lp_ed_tg_cust,
			rf_sa_ed_booking_time_limit, rf_sa_ed_email,
			rf_sa_ed_oo_percentage;

	TextView rf_supper_admin_header_name, rf_regi_step2_tv_step,
			rf_regi_step2_tv_youhave, rf_sa_tv_lname, rf_sa_tv_fname,
			rf_sa_tv_mini_loy, rf_sa_tv_max_loy, rf_sa_loy_tv_lei,
			rf_sa_lp_tv_tg_cust, rf_sa_tv_booking_time_limit, rf_sa_tv_email,
			rf_sa_tv_oo_percentage;

	JSONObject obj;
	Button rf_sa_btn_update;

	ImageView home_img;
	ConnectionDetector cd;

	HttpConnection http = new HttpConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_global_setting);

		initialization();
		setonclicklistener();

		cd = new ConnectionDetector(getApplicationContext());

		try {
			if (Global_variable.array_profile != null) {
				JSONObject obj = Global_variable.array_profile.getJSONObject(0);

				System.out.println("chetan bhai.." + obj);

				rf_sa_ed_fname.setText(obj.getString("first_name"));

				rf_sa_ed_lname.setText(obj.getString("last_name"));

				rf_sa_ed_email.setText(obj.getString("email"));

				rf_sa_ed_mini_loy.setText(obj.getString("min_lp_to_redeem"));

				rf_sa_ed_max_loy.setText(obj.getString("max_lp_to_redeem"));

				rf_sa_loy_ed_lei.setText(obj.getString("lp_equals_to_lei"));

				rf_sa_lp_ed_tg_cust.setText(obj.getString("lp_to_tg_customer"));

				rf_sa_ed_booking_time_limit.setText(obj
						.getString("booking_time_limit"));

				rf_sa_ed_oo_percentage.setText(obj.getString("oo_percentage"));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// rf_sa_ed_fname.onTouchEvent(null);

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

	private void setonclicklistener() {
		// TODO Auto-generated method stub

		rf_sa_btn_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							new Update_global_setting().execute();

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

		home_img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							Intent i = new Intent(activity_global_setting.this,
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

	class Update_global_setting extends AsyncTask<String, String, String> {

		JSONObject json;
		ProgressDialog p;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			p = new ProgressDialog(activity_global_setting.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setIndeterminate(false);
			p.setCancelable(true);
			p.show();

		}

		/**
		 * Getting user details in background thread
		 * */

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject json_input = new JSONObject();

			try {

				json_input.put("sessid", Global_variable.sessid);

				JSONObject json_update_global_setting = new JSONObject();

				json_update_global_setting.put("user_id",
						Global_variable.user_id);
				json_update_global_setting.put("first_name", rf_sa_ed_fname
						.getText().toString());

				json_update_global_setting.put("last_name", rf_sa_ed_lname
						.getText().toString());

				json_update_global_setting.put("min_loy", rf_sa_ed_mini_loy
						.getText().toString());

				json_update_global_setting.put("max_loy", rf_sa_ed_max_loy
						.getText().toString());

				System.out.println("Maximum Loyality point....."
						+ json_update_global_setting);

				json_update_global_setting.put("loy_to_lei", rf_sa_loy_ed_lei
						.getText().toString());

				json_update_global_setting.put("loy_tg_cust",
						rf_sa_lp_ed_tg_cust.getText().toString());

				json_update_global_setting.put("booking_time_limit",
						rf_sa_ed_booking_time_limit.getText().toString());

				json_update_global_setting.put("oo_percentage",
						rf_sa_ed_oo_percentage.getText().toString());

				System.out.println("oo percentage...."
						+ json_update_global_setting);

				json_input.put("Update_Global_Setting",
						json_update_global_setting);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String responseText = http.connection(
					Global_variable.rf_api_update_global_setting, json_input);

			System.out.print("responsetext........" + responseText);
			try {
				json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if (json == null) {

					if (p.isShowing()) {
						p.dismiss();
						System.out.println("update_response" + json);

						// Toast.makeText(getApplicationContext(),
						// json.getString("message"),
						// Toast.LENGTH_LONG).show();
					}
				} else {
					if (json.getString("success").equalsIgnoreCase("true")) {

						// Global_variable.array_updatr_global_setting = new
						// JSONArray();
						JSONObject Data = json.getJSONObject("data");

						Global_variable.array_profile = new JSONArray();

						System.out.println("!!!!!!!!!!!!!!!!!Data" + Data);
						Global_variable.array_profile = Data
								.getJSONArray("package");
						Toast.makeText(getApplicationContext(),
								json.getString("message"), Toast.LENGTH_LONG)
								.show();
						Intent i = new Intent(getApplicationContext(),
								activity_home.class);
						startActivity(i);

					} else {
						JSONObject Errors = json.getJSONObject("errors");

						if (p.isShowing()) {
							p.dismiss();
						}
						// System.out.println("1111loginerrors" + Errors);
						if (Errors != null) {
							if (Errors.has("status")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("status").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}
							if (Errors.has("first_name")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("first_name")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}
							if (Errors.has("last_name")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("last_name").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}
							if (Errors.has("max_loy")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("max_loy").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}
							if (Errors.has("min_loy")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("min_loy").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}

							if (Errors.has("loy_to_lei")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("loy_to_lei")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}
							if (Errors.has("loy_tg_cust")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("loy_tg_cust")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}
							if (Errors.has("booking_time_limit")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray(
												"booking_time_limit").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}

							if (Errors.has("oo_percentage")) {
								Toast.makeText(
										getApplicationContext(),
										Errors.getJSONArray("oo_percentage")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}
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

	private void initialization() {
		// TODO Auto-generated method stub

		rf_sa_ed_fname = (EditText) findViewById(R.id.rf_sa_ed_fname);
		rf_sa_ed_lname = (EditText) findViewById(R.id.rf_sa_ed_lname);
		rf_sa_ed_email = (EditText) findViewById(R.id.rf_sa_ed_email);
		rf_sa_ed_mini_loy = (EditText) findViewById(R.id.rf_sa_ed_mini_loy);
		rf_sa_ed_max_loy = (EditText) findViewById(R.id.rf_sa_ed_max_loy);
		rf_sa_loy_ed_lei = (EditText) findViewById(R.id.rf_sa_loy_ed_lei);
		rf_sa_lp_ed_tg_cust = (EditText) findViewById(R.id.rf_sa_lp_ed_tg_cust);
		rf_sa_ed_booking_time_limit = (EditText) findViewById(R.id.rf_sa_ed_booking_time_limit);
		rf_sa_ed_oo_percentage = (EditText) findViewById(R.id.rf_sa_ed_oo_percentage);
		rf_sa_btn_update = (Button) findViewById(R.id.rf_sa_btn_update);

		home_img = (ImageView) findViewById(R.id.home_img);
	}

}
