package com.rf.restaurant_superadmin;

import java.util.Locale;

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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

//step 1
public class activity_admin_profile extends Activity {

	// step 4
	private LinearLayout rf_login_img_header;
	private ImageView rf_login_img_header_icon;
	private ImageView img_menu;
	private TextView rf_supper_admin_txv_changepassword;
	private ImageView img;
	private ImageView img_logo;
	private TextView textView4;
	private TextView txv_newpassword;
	private TextView txv_resetpassword;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private TextView txv;
	private EditText rf_login_ed_username;
	private Button btn_ok;
	private Button btn;
	private Button btn_cancel;
	ProgressDialog p;

	EditText rf_profile_ed_oldpassword, rf_profile_ed_newpassword,
			rf_profile_ed_retypepassword;

	HttpConnection http = new HttpConnection();

	/* Internet connection */
	ConnectionDetector cd;
	private Locale myLocale;
	// step 2
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_admin_profile);

		// step 5
		initializeWidget();
		/* create Object of internet connection* */
		cd = new ConnectionDetector(getApplicationContext());
		setonclicklistener();
//		loadLocale();
	}
//	language***************
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

//	language**************
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

	// step 8
	private void setonclicklistener() {
		// TODO Auto-generated method stub

		// radioButton1.setOnClickListener( this );
		// radioButton2.setOnClickListener( this );
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							if (rf_profile_ed_oldpassword.getText().toString()
									.equalsIgnoreCase("")) {
								rf_profile_ed_oldpassword.setError(getResources().getString(R.string.str_current_pwd));
								rf_profile_ed_oldpassword.requestFocus();

							}

							else if (rf_profile_ed_newpassword.getText()
									.toString().equalsIgnoreCase("")) {
								rf_profile_ed_newpassword.setError(getResources().getString(R.string.str_new_pwd));
								rf_profile_ed_newpassword.requestFocus();
							}

							else if (rf_profile_ed_retypepassword.getText()
									.toString().equalsIgnoreCase("")) {
								rf_profile_ed_retypepassword.setError(getResources().getString(R.string.str_confirm_pwd));
								rf_profile_ed_retypepassword.requestFocus();
							}

							else if (rf_profile_ed_newpassword
									.getText()
									.toString()
									.equalsIgnoreCase(
											rf_profile_ed_retypepassword
													.getText().toString())) {
								// Toast.makeText(getApplicationContext(),
								// "clicked", Toast.LENGTH_LONG ).show();
								new update_admin_password().execute();
							} else {
								Toast.makeText(getApplicationContext(),
										R.string.fp_pass, Toast.LENGTH_LONG)
										.show();
							}

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
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							if (rf_profile_ed_oldpassword.getText().toString()
									.equalsIgnoreCase("")
									&& rf_profile_ed_newpassword.getText()
											.toString().equalsIgnoreCase("")
									&& rf_profile_ed_retypepassword.getText()
											.toString().equalsIgnoreCase("")) {
								Intent i = new Intent(getApplicationContext(),
										activity_home.class);

								startActivity(i);

							} else {
								rf_profile_ed_oldpassword.setText("");
								rf_profile_ed_newpassword.setText("");
								rf_profile_ed_retypepassword.setText("");
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

	// step 6
	private void initializeWidget() {
		rf_login_img_header = (LinearLayout) findViewById(R.id.rf_login_img_header);
		rf_login_img_header_icon = (ImageView) findViewById(R.id.rf_login_img_header_icon);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		rf_supper_admin_txv_changepassword = (TextView) findViewById(R.id.rf_supper_admin_txv_changepassword);
		img = (ImageView) findViewById(R.id.img);
		img_logo = (ImageView) findViewById(R.id.img_logo);
		txv_newpassword = (TextView) findViewById(R.id.txv_newpassword);
		rf_profile_ed_newpassword = (EditText) findViewById(R.id.rf_profile_ed_newpassword);
		txv_resetpassword = (TextView) findViewById(R.id.txv_resetpassword);

		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		txv = (TextView) findViewById(R.id.txv);
		rf_login_ed_username = (EditText) findViewById(R.id.rf_login_ed_username);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn = (Button) findViewById(R.id.btn);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

		rf_profile_ed_oldpassword = (EditText) findViewById(R.id.rf_profile_ed_oldpassword);
		rf_profile_ed_newpassword = (EditText) findViewById(R.id.rf_profile_ed_newpassword);
		rf_profile_ed_retypepassword = (EditText) findViewById(R.id.rf_profile_ed_retypepassword);

	}

	public class update_admin_password extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_admin_profile.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("hi"
					+ rf_profile_ed_newpassword.getText().toString());
			JSONObject obj_parent_psw = new JSONObject();
			JSONObject obj_child_rpsw = new JSONObject();
			try {
				obj_child_rpsw.put("old_password", rf_profile_ed_oldpassword
						.getText().toString());
				obj_child_rpsw.put("new_password", rf_profile_ed_newpassword
						.getText().toString());
				obj_child_rpsw.put("user_id", Global_variable.admin_uid);

				obj_parent_psw.put("sessid", Global_variable.sessid);
				obj_parent_psw.put("Admin_Password", obj_child_rpsw);
				System.out.println("Activity admin profile" + obj_parent_psw);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.rf_api_update_admin_password,
							obj_parent_psw);
					
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
					JSONObject Data = json.getJSONObject("data");

					System.out.println("11111datalogin" + Data);

					Toast.makeText(getApplicationContext(),
							json.getString("message"), Toast.LENGTH_LONG)
							.show();
					Intent i = new Intent(getApplicationContext(),
							activity_home.class);
					startActivity(i);

				} else {
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("old_password")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("old_password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("new_password")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("new_password").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						if (Errors.has("status")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("status").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

						if (Errors.has("user_id")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("user_id").get(0)
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

}
