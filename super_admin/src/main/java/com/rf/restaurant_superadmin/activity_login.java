package com.rf.restaurant_superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

public class activity_login extends Activity {

	private LinearLayout header;
	private ImageView rf__supper_admin_login_img_header_icon;
	private ImageView rf_login_img_header_icon;
	private TextView txt_admin;
	private TextView rf_login_txv_username;
	private EditText rf_login_ed_username;
	private TextView rf_login_txv_password;
	private EditText rf_login_ed_password;
	private TextView rf_login_txv_remember;
	private CheckBox checkBox1;
	private EditText rf_login_ed_txv;
	private TextView txv_forgetpass;
	private Button button1;
	public static Button btn_confirm;
	private Button btn_clear;

	HttpConnection http = new HttpConnection();
	public static ArrayList<HashMap<String, String>> salary_count;

	// private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	// Font.BOLD);

	JSONObject restaurant_details = new JSONObject();
	ProgressDialog p;
	boolean reme = false;

	/* Internet connection */
	ConnectionDetector cd;
	private Locale myLocale;
	private ImageView img_en, img_ar;
	boolean locale_flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_login);
		this.img_en = (ImageView) findViewById(R.id.btn_en);
		this.img_ar = (ImageView) findViewById(R.id.btn_ar);
		initializeWidget();
		/* create Object of internet connection* */
		cd = new ConnectionDetector(getApplicationContext());
		setonclicklistener();
		loadSavedPreferences();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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

	private void loadSavedPreferences() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean cbValue = sp.getBoolean("CHECKBOX", false);
		String uname = sp.getString("USERNAME", "Enter username");
		String pwd = sp.getString("PASSWORD", "Enter password");
		if (cbValue) {
			checkBox1.setChecked(true);

			rf_login_ed_username.setText(uname);
			rf_login_ed_password.setText(pwd);

		} else {
			checkBox1.setChecked(false);
		}

	}

	private void savePreferences(String key, boolean value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	private void savePreferences(String username_key, String username_value,
			String password_key, String password_value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(username_key, username_value);
		edit.putString(password_key, password_value);
		edit.commit();

		System.out.print("shared.." + edit);
	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		img_ar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (LanguageConvertPreferenceClass
						.getLocale(getApplicationContext()).toString()
						.equalsIgnoreCase("ro")) {

				} else {

					Global_variable.lang = "ro";
					// Global_variable.wjbty_en_Url =
					// "http://www.wjbty.com/ar/api/";
					locale_flag = true;

					Global_variable.rf_api_Url = Global_variable.rf_api_Url_ro;

					System.out.println("!!!!pankaj_url_ro"
							+ Global_variable.rf_api_Url);
					LanguageConvertPreferenceClass.saveLocale("ro",
							getApplicationContext());
					LanguageConvertPreferenceClass.setLocale("ro",
							getApplicationContext());

					Intent refresh = new Intent(activity_login.this,
							activity_login.class);
					// refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(refresh);
				}

			}
		});
		img_en.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (LanguageConvertPreferenceClass
						.getLocale(getApplicationContext()).toString()
						.equalsIgnoreCase("en")) {

				} else {

					Global_variable.lang = "en";
					// Global_variable.wjbty_en_Url =
					// "http://www.wjbty.com/en/api/";
					locale_flag = true;
					// reload();

					Global_variable.rf_api_Url = Global_variable.rf_api_Url_en;
					System.out.println("!!!!pankaj_url_en"
							+ Global_variable.rf_api_Url);
					LanguageConvertPreferenceClass.saveLocale("en",
							getApplicationContext());
					LanguageConvertPreferenceClass.setLocale("en",
							getApplicationContext());

					Intent refresh = new Intent(activity_login.this,
							activity_login.class);
					// refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(refresh);

				}

			}
		});
		btn_confirm.setOnClickListener(new View.OnClickListener() {

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

							if (checkBox1.isChecked()) {
								reme = false;
								savePreferences("CHECKBOX",
										checkBox1.isChecked());
								savePreferences("USERNAME",
										rf_login_ed_username.getText()
												.toString(), "PASSWORD",
										rf_login_ed_password.getText()
												.toString());

							} else {
								savePreferences("CHECKBOX",
										checkBox1.isChecked());
							}
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

		btn_clear.setOnClickListener(new View.OnClickListener() {

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

							// if (checkBox1.isChecked()) {
							// reme = false;
							// savePreferences("CHECKBOX",
							// checkBox1.isChecked());
							// savePreferences("USERNAME",
							// rf_login_ed_username.getText()
							// .toString(), "PASSWORD", rf_login_ed_password
							// .getText().toString());
							//
							// }
							// else
							// {
							// savePreferences("CHECKBOX",
							// checkBox1.isChecked());
							// }
							// new login().execute();
							rf_login_ed_username.setText("");
							rf_login_ed_password.setText("");
							checkBox1.setChecked(false);

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

		checkBox1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savePreferences("CHECKBOX", checkBox1.isChecked());
				if (checkBox1.isChecked()) {
					reme = false;
					savePreferences("USERNAME", rf_login_ed_username.getText()
							.toString(), "PASSWORD", rf_login_ed_password
							.getText().toString());

					runOnUiThread(new Runnable() {
						public void run() {

							/** check Internet Connectivity */
							if (cd.isConnectingToInternet()) {
								// new login().execute();

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

			}

		});

		txv_forgetpass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(activity_login.this,
						activity_forget_password.class);
				startActivity(i);

				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {
							// new login().execute();

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

		header = (LinearLayout) findViewById(R.id.header);
		rf__supper_admin_login_img_header_icon = (ImageView) findViewById(R.id.rf__supper_admin_login_img_header_icon);
		rf_login_img_header_icon = (ImageView) findViewById(R.id.rf_login_img_header_icon);
		txt_admin = (TextView) findViewById(R.id.txt_admin);
		rf_login_txv_username = (TextView) findViewById(R.id.rf_login_txv_username);
		rf_login_ed_username = (EditText) findViewById(R.id.rf_login_ed_username);
		rf_login_txv_password = (TextView) findViewById(R.id.rf_login_txv_password);
		rf_login_ed_password = (EditText) findViewById(R.id.rf_login_ed_password);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		txv_forgetpass = (TextView) findViewById(R.id.txv_forgetpass);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		txv_forgetpass.setText(R.string.rf_login_forgetpass);
	}

	public class login extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(activity_login.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// JSONObject data = new JSONObject();

			System.out
					.println("hi" + rf_login_ed_username.getText().toString());
			JSONObject obj_parent = new JSONObject();
			JSONObject obj_child = new JSONObject();
			try {
				obj_child.put("username", rf_login_ed_username.getText()
						.toString());
				obj_child.put("password", rf_login_ed_password.getText()
						.toString());

				obj_parent.put("LoginForm", obj_child);
				obj_parent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("Activity_Login" + obj_parent);

				try {

					// *************
					// for request
					System.out.println("!!!!pankaj_login_url"
							+ Global_variable.rf_api_Url);

					System.out.println("!!!!pankaj_login_url_login"
							+ Global_variable.rf_api_login);

					System.out.println("!!!!pankaj_login_url_after"
							+ Global_variable.rf_api_Url);
					String responseText = http.connection(
							Global_variable.rf_api_login, obj_parent);

					try {

						System.out.println("after_connection.." + responseText);

						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));

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

				if (json == null) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.rd_wrong), Toast.LENGTH_LONG)
							.show();
					if (p.isShowing()) {
						p.dismiss();
						// System.out.println("Login_response" + json);
					}
				} else if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						// System.out.println("Login_response" + json);
					}
					JSONObject Data = json.getJSONObject("data");

					Global_variable.array_restaurantcategory = new JSONArray();

					Global_variable.array_Restaurant_List = json.getJSONObject(
							"data").getJSONArray("restaurant_list");

					Global_variable.array_Package = json.getJSONObject("data")
							.getJSONArray("package");

					System.out.println("!!!!restaurant_list"
							+ Global_variable.array_Restaurant_List);

					Global_variable.array_profile = json.getJSONObject("data")
							.getJSONArray("profile");

					System.out.print("array profile.............."
							+ Global_variable.array_profile);

					Global_variable.array_restaurantcategory = json
							.getJSONObject("data").getJSONArray(
									"restaurantcategory");

					System.out.println("restaurantcategory login"
							+ Global_variable.array_restaurantcategory);

					Global_variable.sessid = json.getString("sessid");

					Global_variable.admin_uid = json.getJSONObject("data")
							.getString("user_id");

					Global_variable.admin_email = json.getJSONObject("data")
							.getString("user_email");

					System.out.println("11111datalogin" + Data);

					Intent i = new Intent(activity_login.this,
							activity_home.class);
					startActivity(i);
					// generatepdf();

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);
					}
					JSONObject Errors = json.getJSONObject("errors");

					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

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
					// System.out.println("Login_response" + json);
				}
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					// System.out.println("Login_response" + json);
				}
				n.printStackTrace();
			}

		}

		// private void generatepdf() {
		// // TODO Auto-generated method stub
		//
		// // TODO Auto-generated method stub
		//
		// Document document = new Document();
		//
		// try {
		//
		// String path = Environment.getExternalStorageDirectory()
		// .getAbsolutePath() + "/PDF";
		//
		// File dir = new File(path);
		// if (!dir.exists())
		// dir.mkdirs();
		//
		// Log.d("PDFCreator", "PDF Path: " + path);
		//
		// File file = new File(dir, "SuperAdmin.pdf");
		// FileOutputStream fOut = new FileOutputStream(file);
		//
		// // f = new File(getFilesDir() + "/Order.pdf");
		//
		// try {
		// PdfWriter.getInstance(document, fOut);
		// } catch (DocumentException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// document.open();
		//
		// // Paragraph p = new Paragraph("Hie.." + "\n");
		//
		// Anchor anchor = new Anchor("Super Admin");
		// anchor.setName("Super Admin");
		//
		// // Second parameter is the number of the chapter
		// Chapter catPart = new Chapter(new Paragraph(anchor), 1);
		//
		// Paragraph subPara = new Paragraph();
		// Section subCatPart = catPart.addSection(subPara);
		//
		// subCatPart.add(new Paragraph(
		// "Here is login response of super admin "));
		// addEmptyLine(subPara, 5);
		// // add a list
		// // createList(subCatPart);
		// // Paragraph paragraph = new Paragraph();
		// // addEmptyLine(paragraph, 5);
		// // subCatPart.add(paragraph);
		//
		// createTable(subCatPart);
		//
		// try {
		// document.add(catPart);
		// } catch (DocumentException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// document.close();
		//
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		//
		// }
		//
		// private void addEmptyLine(Paragraph subPara, int number) {
		// // TODO Auto-generated method stub
		// for (int i = 0; i < number; i++) {
		// subPara.add(new Paragraph(" "));
		// }
		//
		// }
		//
		// private void createTable(Section subCatPart) {
		// // TODO Auto-generated method stub
		//
		// PdfPTable table = new PdfPTable(2);
		//
		//
		// PdfPCell c1 = new PdfPCell(new Phrase("uid"));
		// c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// table.addCell(c1);
		//
		// PdfPCell c2 = new PdfPCell(new Phrase("id"));
		// c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		// table.addCell(c2);
		//
		//
		// String uid,id;
		// try {
		// uid = restaurant_details.getString("uid");
		// id = restaurant_details.getString("id");
		//
		// table.addCell(uid);
		// table.addCell(id);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		//
		// e.printStackTrace();
		// }
		//
		//
		//
		// subCatPart.add(table);
		//
		// //boolean flag = true;
		//
		// }
		//
	}

}
