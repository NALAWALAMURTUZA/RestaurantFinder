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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

public class activity_forget_password extends Activity{
	
	ImageView img_home;
	EditText ed_user_email;
	Button btn_reset_password;
	
	HttpConnection http = new HttpConnection();

	/* Internet connection */
	ConnectionDetector cd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_forget_password);
		initializeWidget();
		cd = new ConnectionDetector(getApplicationContext());
		setonclickListner();
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
	


	private void setonclickListner() {
		// TODO Auto-generated method stub
		
		btn_reset_password.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent i = new Intent(activity_login.this,
				// activity_home.class);
				// startActivity(i);

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							
							new login().execute();

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
		img_home = (ImageView)findViewById(R.id.img_home);
		ed_user_email = (EditText)findViewById(R.id.ed_user_email);
		btn_reset_password = (Button)findViewById(R.id.btn_reset_password);
		
		
	}
	
	public class login extends AsyncTask<Void, Void, Void> {
		JSONObject json;
		ProgressDialog p;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_forget_password.this);
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
				obj_child.put("email", ed_user_email.getText()
						.toString());
			

				obj_parent.put("Forget_Password", obj_child);
				obj_parent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("Activity_Login" + obj_parent);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_user_forget_password, obj_parent);
				
					try {

						System.out.println("after_connection.." + responseText);

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

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			
			try{
				if(json == null)
				{
					Toast.makeText(
							getApplicationContext(),
							getString(R.string.rd_wrong), Toast.LENGTH_LONG)
							.show();
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
				}
				else if (json.getString("success").equalsIgnoreCase("true")) {
					JSONObject Data = json.getJSONObject("data");
					
					System.out.println("11111datalogin" + Data);

					Toast.makeText(getApplicationContext(), json.getJSONObject("data").getString("message"), Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),
							activity_login.class);
					startActivity(i);

				} else {
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("email")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("email").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						
						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

						}
						
						
						if (Errors.has("sessid")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("sessid")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();

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
