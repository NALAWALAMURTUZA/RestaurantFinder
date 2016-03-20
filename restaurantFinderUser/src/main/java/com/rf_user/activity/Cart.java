package com.rf_user.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.Login;
import com.rf.restaurant_user.R;
import com.rf_user.adapter.Cart_Adapter;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

import org.json.JSONArray;

import java.util.Locale;

import sharedprefernce.LanguageConvertPreferenceClass;

public class Cart extends Activity {
	ImageView Back, Search;
	public static TextView txv_total, txv_sr_total;
	Button btn_Confirm_order, btn_Cancel_order;
	public static ListView cart_lv;
	public static Cart_Adapter cart_adapter;
	/*** Network Connection Instance **/
	ConnectionDetector cd;

	/* Language conversion */
	private Locale myLocale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.my_cart);

		try {

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());
			initializeWidgets();
			setlistener();

			System.out.println("cartvalue_gai" + Global_variable.cart);
			System.out.println("Murtuza_nala");

			if (Global_variable.cart.length() != 0) {
				cart_adapter = new Cart_Adapter(Cart.this, Global_variable.cart);
				cart_lv.setAdapter(cart_adapter);
			} else {
				Intent i = new Intent(Cart.this,
						Food_Categories_Details_List.class);
				startActivity(i);
			}

//			loadLocale();

		} catch (NullPointerException e) {
			e.printStackTrace();
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

	public void abc() {
		// TODO Auto-generated method stub
		System.out.println("Ishita");
		// cart_adapter = new Cart_Adapter(this, Global_variable.cart);

		// cart_lv.setAdapter(cart_adapter);

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		try {

			// imageview
			Back = (ImageView) findViewById(R.id.back_imageview);
			Search = (ImageView) findViewById(R.id.search_imageview);
			btn_Confirm_order = (Button) findViewById(R.id.btn_confirm);
			btn_Cancel_order = (Button) findViewById(R.id.btn_cancel);

			// textview
			txv_total = (TextView) findViewById(R.id.txv_total);
			txv_sr_total = (TextView) findViewById(R.id.txv_sr_total);

			// listview
			cart_lv = (ListView) findViewById(R.id.cart_lv);
			btn_Confirm_order.setText(R.string.confirm_order);
			btn_Cancel_order.setText(R.string.cancel_order);
		} catch (NullPointerException e) {
			e.printStackTrace();
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

		Intent i = new Intent(Cart.this, Food_Categories_Details_List.class);
		startActivity(i);

	}

	private void setlistener() {

		try {

			btn_Confirm_order.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					System.out.println("Cart.confirm_button.."
							+ SharedPreference
									.getuser_id(getApplicationContext()));
					if (Global_variable.cart.length() == 0) {

					} else {
						if (SharedPreference
								.getuser_id(getApplicationContext()).equals("")) {
							Global_variable.activity = "cart";
							Intent i = new Intent(Cart.this, Login.class);
							startActivity(i);
							/** check Internet Connectivity */
						} else {
							if (Global_variable.cart.length() != 0) {

								/** check Internet Connectivity */
								if (cd.isConnectingToInternet()) {

									Intent iN = new Intent(Cart.this,
											Checkout_Tablayout.class);
									startActivity(iN);
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

						}

					}

				}
			});
			Search.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						Intent i = new Intent(Cart.this,
								Search_Restaurant_List.class);
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
			btn_Cancel_order.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Global_variable.cart = new JSONArray();
					Intent i = new Intent(Cart.this,
							Food_Categories_Details_List.class);
					startActivity(i);

				}
			});
			Back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Cart.this,
							Food_Categories_Details_List.class);
					//
					// i.putExtra(
					// "str_categories_name",Global_variable.cat_str_categories_name);
					//
					// i.putExtra(
					// "strarray_categories_id",Global_variable.cat_strarray_categories_id);
					//

					startActivity(i);

				}
			});

		} catch (NullPointerException e) {
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
