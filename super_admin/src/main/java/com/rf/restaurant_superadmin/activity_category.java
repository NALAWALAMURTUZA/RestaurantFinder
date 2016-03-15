package com.rf.restaurant_superadmin;

import org.json.JSONArray;
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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.adapter.activity_category_adapter;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

public class activity_category extends Activity {

	TextView txv_add_category;
	ImageView img_menu,  img_edit,home_icon;
	public static ListView lv_add_category;
	EditText ed_add_category;
	String str_name;
	Button img_add;

	public static  activity_category_adapter category_adapter;

	public static ConnectionDetector cd;
	HttpConnection http = new HttpConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.add_category);
		initializ();
		setonclicklistener();

		setAdapter(Global_variable.array_restaurantcategory);

		cd = new ConnectionDetector(getApplicationContext());
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

	private void setAdapter(JSONArray array_restaurantcategory) {
		// TODO Auto-generated method stub
		category_adapter = new activity_category_adapter(
				activity_category.this, array_restaurantcategory);
		lv_add_category.setAdapter(category_adapter);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		
		

		// str_name = ed_add_category.getText().toString();
		img_add.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							new insert_restaurant().execute();

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
		
		home_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							Intent i = new Intent(activity_category.this,
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
//		home_icon.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(activity_category.this,activity_home.class);
//				startActivity(i);
//			}
//		});
//		img_edit.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//new update_restaurant().execute();
//			}
//		});

	}

	public class insert_restaurant extends AsyncTask<Void, Void, Void> {
		JSONObject json;
		ProgressDialog p;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_category.this);
			p.setMessage(getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_mparent = new JSONObject();
			JSONObject obj_parent = new JSONObject();

			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("name_en", ed_add_category.getText().toString());
				obj_mparent.put("insert", obj_child);

				obj_parent.put("operation", "insert");

				obj_mparent.put("restaurantcategory", obj_parent);

				obj_mparent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("request of insert data" + obj_mparent);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.manage_restaurant_category,
							obj_mparent);
				
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
						System.out.println("response of data" + json);
					}
					JSONObject Data = json.getJSONObject("data");
					Global_variable.array_restaurantcategory = json
							.getJSONObject("data").getJSONArray(
									"restaurantcategory");
					setAdapter(Global_variable.array_restaurantcategory);
					ed_add_category.setText("");

					System.out.println("!!!!restaurant_list"
							+ Global_variable.array_restaurantcategory);
					Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);

					// System.out.println("!!!!pankaj_sakariya_click_flag_home_screen"+Global_variable.click_flag_home_screen);
					// Global_variable.click_flag_home_screen =
					// "manage_restaurant";
					System.out
							.println("!!!!pankaj_sakariya_click_flag_home_screen_detail"
									+ Global_variable.click_flag_home_screen);

//					Intent i = new Intent(activity_category.this,
//							activity_home.class);
//					startActivity(i);

					// generatepdf();

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("name_en")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("name_en").get(0)
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

	

	private void initializ() {
		// TODO Auto-generated method stub
		txv_add_category = (TextView) findViewById(R.id.txv_add_category);
		img_add = (Button) findViewById(R.id.img_add);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		ed_add_category = (EditText) findViewById(R.id.ed_add_category);
		lv_add_category = (ListView) findViewById(R.id.lv_add_category);
		img_edit = (ImageView) findViewById(R.id.img_edit);
		home_icon = (ImageView)findViewById(R.id.home_icon);
	}

}
