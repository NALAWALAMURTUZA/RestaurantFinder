package com.rf.restaurant_superadmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class acivity_requestupdate_package extends Activity{
	
	ListView lv_request_update_pack;
	request_update_package adapter_request_update_package;
	static ProgressDialog p;
	ConnectionDetector cd;
	static HttpConnection http = new HttpConnection();
	public static Activity activity = null;
	TextView lv_manage_notification;
	ImageView rf_login_img_header_icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_requestupdate_package);
		activity=acivity_requestupdate_package.this;
		cd = new ConnectionDetector(getApplicationContext());

		initializeWidget();
		setonclicklistener();
		new get_notifications().execute();
		
	}
	
	private void setAdapter(JSONArray json) {
		// TODO Auto-generated method stub
		if(json.length()==0)
		{
			lv_manage_notification.setVisibility(View.VISIBLE);
			lv_request_update_pack.setVisibility(View.GONE);
		}
		else
		{
			lv_manage_notification.setVisibility(View.GONE);
			lv_request_update_pack.setVisibility(View.VISIBLE);
			adapter_request_update_package = new request_update_package(acivity_requestupdate_package.this,json);
			lv_request_update_pack.setAdapter(adapter_request_update_package);
		}
	}

	private void initializeWidget() {
		// TODO Auto-generated method stub
		
		lv_request_update_pack = (ListView)findViewById(R.id.lv_request_update_pack);
		lv_manage_notification = (TextView) findViewById(R.id.txv_invisible);
		rf_login_img_header_icon=(ImageView)findViewById(R.id.rf_login_img_header_icon);
	}
	private void setonclicklistener() {
		// TODO Auto-generated method stub
		rf_login_img_header_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							Intent i = new Intent(acivity_requestupdate_package.this,
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
	public class get_notifications extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(acivity_requestupdate_package.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			JSONObject obj_parent_psw = new JSONObject();
			JSONObject obj_child_rpsw = new JSONObject();
			try {
				
				obj_parent_psw.put("id", "3");
				obj_parent_psw.put("sessid", Global_variable.sessid);
				System.out.println("Activity admin profile" + obj_parent_psw);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_get_notifications,
							obj_parent_psw);

					try {

						System.out.println("after_connection.." + responseText);

						//json = new JSONObject(responseText);
						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));
						System.out.println("responseText" + json);
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

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			try {
				if (json == null) {
					Toast.makeText(getApplicationContext(), R.string.rd_wrong,
							Toast.LENGTH_LONG).show();
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
				} else if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Data = json.getJSONObject("data");

					System.out.println("11111datalogin" + Data);
					setAdapter(Data.getJSONArray("notifications"));
					
					

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("old_password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("sessid")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("sessid").get(0)
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
	
	public static class update_notifications extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity);
			p.setMessage(activity.getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			JSONObject obj_parent_psw = new JSONObject();
			JSONObject obj_child_rpsw = new JSONObject();
			try {
				obj_parent_psw.put("id", "3");
				obj_parent_psw.put("rest_id", com.rf.restaurant_superadmin.request_update_package.str_notification_id);
				obj_parent_psw.put("isRead", com.rf.restaurant_superadmin.request_update_package.str_isRead);
				obj_parent_psw.put("Requested_package_id", com.rf.restaurant_superadmin.request_update_package.str_package_id);
				obj_parent_psw.put("name_en", com.rf.restaurant_superadmin.request_update_package.str_name_en);
				obj_parent_psw.put("super_admin", com.rf.restaurant_superadmin.request_update_package.str_super_admin);
				obj_parent_psw.put("restaurant_email", com.rf.restaurant_superadmin.request_update_package.str_restaurant_email);
				obj_parent_psw.put("current_package_id",com.rf.restaurant_superadmin.request_update_package.str_current_package_id);
				obj_parent_psw.put("sessid", Global_variable.sessid);
				System.out.println("Activity admin profile" + obj_parent_psw);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_update_notifications,
							obj_parent_psw);

					try {

						System.out.println("after_connection.." + responseText);

						//json = new JSONObject(responseText);
						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));
						System.out.println("responseText" + json);
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

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			try {
				if (json == null) {
					Toast.makeText(activity, R.string.rd_wrong,
							Toast.LENGTH_LONG).show();
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
				} else if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Data = json.getJSONObject("data");

					System.out.println("11111datalogin" + Data);
					((acivity_requestupdate_package) activity).setAdapter(Data.getJSONArray("notifications"));
					

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("id")) {
							Toast.makeText(
									activity,
									Errors.getJSONArray("old_password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("sessid")) {
							Toast.makeText(
									activity,
									Errors.getJSONArray("sessid").get(0)
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
