package com.rf_user.activity;

import java.util.Locale;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Checkout_Tablayout extends TabActivity implements
		OnTabChangeListener {
	public static TabHost tab;
	public static Resources res;
	public static TabHost.TabSpec spec;
	ImageView back_imageview, search_imageview;
	ImageView footer_ordernow_img, footer_viewmenu_img, footer_featured_img,
			footer_setting_img;
	public static TextView txv_Checkout;
	// public static TextView txv_payment;
	// public static TextView txv_Receipt;
	Intent intent;
	Intent intent1;
	Intent intent2;
	public static Intent intent3, intent4;
	String langPref = "Language";
	public static String language;
	/*** Network Connection Instance **/
	ConnectionDetector cd;

	public static boolean Langauge_Arabic = false;

	public static TabSpec delivery_spec, contact_spec, payment_spec,
			reciept_spec;

	/* Language conversion */
	private Locale myLocale;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.checkout_delivery);

		try {

			/* create Object* */
			cd = new ConnectionDetector(getApplicationContext());
			InitializeWidget();
			Checkout_Tablayout.txv_Checkout.setVisibility(View.VISIBLE);
			// Checkout_Tablayout.txv_payment.setVisibility(View.GONE);
			// Checkout_Tablayout.txv_Receipt.setVisibility(View.GONE);
			setListener();

			tab = getTabHost();

			tab.getTabWidget().setClickable(false);
			tab.setClickable(false);

			// Tab for Step 1
			delivery_spec = tab.newTabSpec(getString(R.string.Delivery));
			// setting Title and Icon for the Tab
			delivery_spec.setIndicator(getString(R.string.Delivery));
			// step1spec.setIndicator("Booking Confirmation");

			intent = new Intent(getApplicationContext(),
					Delivery_Activity.class);
			delivery_spec.setContent(intent);

			// spec =tab.newTabSpec("delivery")
			// .setIndicator(getTabIndicator(tab.getContext(),
			// R.string.Delivery))
			// .setContent(R.id.tab1);
			//
			// txv_Checkout.setVisibility(View.VISIBLE);
			// txv_payment.setVisibility(View.GONE);
			// txv_Receipt.setVisibility(View.GONE);
			// tab.addTab(spec);
			//
			// Tab for Step 2
			contact_spec = tab
					.newTabSpec(getString(R.string.checkout_ContactDetails));
			// setting Title and Icon for the Tab
			contact_spec
					.setIndicator(getString(R.string.checkout_ContactDetails));
			// step1spec.setIndicator("Booking Confirmation");
			intent1 = new Intent(getApplicationContext(),
					Contact_Details_Activity.class);
			contact_spec.setContent(intent1);

			// txv_Checkout.setVisibility(View.VISIBLE);
			// txv_payment.setVisibility(View.GONE);
			// txv_Receipt.setVisibility(View.GONE);
			// spec=tab.newTabSpec("contact details").
			// setIndicator(getTabIndicator(tab.getContext(),
			// R.string.tab_Contact_Details))
			// .setContent(R.id.tab2);
			//
			// tab.addTab(spec);
			//
			// Tab for Step 3
			payment_spec = tab.newTabSpec(getString(R.string.tab_Payment));
			// setting Title and Icon for the Tab
			payment_spec.setIndicator(getString(R.string.tab_Payment));
			// step1spec.setIndicator("Booking Confirmation");
			intent2 = new Intent(getApplicationContext(),
					Payment_Activity.class);
			payment_spec.setContent(intent2);

			// txv_Checkout.setVisibility(View.GONE);
			// txv_payment.setVisibility(View.VISIBLE);
			// txv_Receipt.setVisibility(View.GONE);
			// spec=tab.newTabSpec("payment")
			// .setIndicator(getTabIndicator(tab.getContext(),
			// R.string.tab_Payment))
			// .setContent(R.id.tab3);
			//
			// tab.addTab(spec);

			// Tab for Step 3
			reciept_spec = tab.newTabSpec(getString(R.string.tab_Reciept));
			// setting Title and Icon for the Tab
			reciept_spec.setIndicator(getString(R.string.tab_Reciept));
			// step1spec.setIndicator("Booking Confirmation");

			intent3 = new Intent(getApplicationContext(),
					Reciept_Activity.class);

			reciept_spec.setContent(intent3);

			// Adding all TabSpec to TabHost
			tab.addTab(delivery_spec); // Adding step 1 tab
			tab.addTab(contact_spec); // Adding step 2 tab
			tab.addTab(payment_spec); // Adding step 3 tab
			tab.addTab(reciept_spec); // Adding step 4 tab

			// intent4 = new Intent(getApplicationContext(),
			// Payment_Activity.class);

			// txv_Checkout.setVisibility(View.GONE);
			// txv_payment.setVisibility(View.GONE);
			// txv_Receipt.setVisibility(View.VISIBLE);
			// spec=tab.newTabSpec("receipt")
			// .setIndicator(getTabIndicator(tab.getContext(),
			// R.string.tab_Reciept))
			// .setContent(R.id.tab4);
			//
			// tab.addTab(spec);
			//
			// tab.setCurrentTab(0);

			SharedPreferences prefs_oncreat = getSharedPreferences(
					"CommonPrefs", Activity.MODE_PRIVATE);
			language = prefs_oncreat.getString(langPref, "");

			System.out.println("Murtuza_Nalawala_language_oncreat_Tab"
					+ language);
			if (language.equalsIgnoreCase("ar")) {
				System.out
						.println("Murtuza_Nalawala_language_oncreat_Tab_before1"
								+ Checkout_Tablayout.tab.getCurrentTab());
				// this.setNewTab(this, tab, "receipt", R.string.tab_Reciept,
				// intent3);
				// this.setNewTab(this, tab, "payment", R.string.tab_Payment,
				// intent2);
				// this.setNewTab(this, tab, "cantact detail",
				// R.string.tab_Contact_Details,intent1);
				// this.setNewTab(this, tab, "delivery", R.string.Delivery,
				// intent);
				Langauge_Arabic = true;
				tab.setCurrentTab(3);
				this.setNewTab(this, tab, "receipt", R.string.tab_Reciept,
						intent4);
				this.setNewTab(this, tab, "receipt", R.string.tab_Reciept,
						intent3);
				this.setNewTab(this, tab, "payment", R.string.tab_Payment,
						intent2);
				this.setNewTab(this, tab, "cantact detail",
						R.string.tab_Contact_Details, intent1);
				this.setNewTab(this, tab, "delivery", R.string.Delivery, intent);
				tab.getTabWidget().getChildAt(1).setVisibility(View.GONE);

				tab.setCurrentTab(4);

				System.out
						.println("Murtuza_Nalawala_language_oncreat_Tab_before2"
								+ Checkout_Tablayout.tab.getCurrentTab());

				System.out
						.println("Murtuza_Nalawala_language_oncreat_Tab_after"
								+ Checkout_Tablayout.tab.getCurrentTab());

				// tab.getTabWidget().getChildAt(1).setClickable(false);
				// tab.getTabWidget().getChildAt(2).setClickable(false);
				// tab.getTabWidget().getChildAt(0).setClickable(false);

				tab.setOnTabChangedListener(new OnTabChangeListener() {

					public void onTabChanged(String tabId) {
						Log.d("debugTAG", "onTabChanged: tab number="
								+ Checkout_Tablayout.tab.getCurrentTab());

						switch (Checkout_Tablayout.tab.getCurrentTab()) {
						case 0:
							// do what you want when tab 0 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							Delivery_Activity.adapter_for_saved_address
									.notifyDataSetChanged();
							// getApplicationContext().startActivity(new
							// Intent(getApplicationContext(),
							// Delivery_Activity.class));
							break;
						case 1:
							// do what you want when tab 1 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							break;
						case 2:
							// do what you want when tab 2 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							break;

						case 3:
							// do what you want when tab 2 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							break;

						default:

							break;
						}
					}
				});
			} else {
				System.out.println("Murtuza_Nalawala_language_oncreat_Tab_else"
						+ language);
				// // setNewTab(context, tabHost, tag, title, icon, contentID);
				// this.setNewTab(this, tab, "delivery", R.string.Delivery,
				// intent);
				//
				// this.setNewTab(this, tab, "cantact detail",
				// R.string.tab_Contact_Details, intent1);
				//
				// this.setNewTab(this, tab, "payment", R.string.tab_Payment,
				// intent2);
				//
				// this.setNewTab(this, tab, "receipt", R.string.tab_Reciept,
				// intent3);

				tab.setCurrentTab(0);

				tab.getTabWidget().getChildAt(1).setClickable(false);
				tab.getTabWidget().getChildAt(2).setClickable(false);
				tab.getTabWidget().getChildAt(3).setClickable(false);

				tab.getTabWidget()
						.getChildTabViewAt(0)
						.setBackgroundResource(
								R.drawable.icon_booking_confirmation_tab);
				tab.getTabWidget()
						.getChildTabViewAt(1)
						.setBackgroundResource(
								R.drawable.icon_booking_confirmation_tab);
				tab.getTabWidget()
						.getChildTabViewAt(2)
						.setBackgroundResource(
								R.drawable.icon_booking_confirmation_tab);
				tab.getTabWidget()
						.getChildTabViewAt(3)
						.setBackgroundResource(
								R.drawable.icon_booking_confirmation_tab);

				TextView tv = (TextView) tab.getTabWidget().getChildAt(0)
						.findViewById(android.R.id.title);
				tv.setTextColor(Color.WHITE);
				// tv.setTextSize(12);
				// center text
				tv.setGravity(Gravity.CENTER_HORIZONTAL);
				// wrap text
				tv.setSingleLine(false);

				// explicitly set layout parameters
				// tv.getLayoutParams().height =
				// ViewGroup.LayoutParams.MATCH_PARENT;
				// tv.getLayoutParams().width =
				// ViewGroup.LayoutParams.MATCH_PARENT;

				TextView tv1 = (TextView) tab.getTabWidget().getChildAt(1)
						.findViewById(android.R.id.title);
				tv1.setTextColor(Color.WHITE);
				// tv1.setTextSize(12);
				// center text
				tv1.setGravity(Gravity.CENTER_HORIZONTAL);
				// wrap text
				tv1.setSingleLine(false);

				// explicitly set layout parameters
				// tv1.getLayoutParams().height =
				// ViewGroup.LayoutParams.MATCH_PARENT;
				// tv1.getLayoutParams().width =
				// ViewGroup.LayoutParams.MATCH_PARENT;

				TextView tv2 = (TextView) tab.getTabWidget().getChildAt(2)
						.findViewById(android.R.id.title);
				tv2.setTextColor(Color.WHITE);
				// tv.setTextSize(12);
				// center text
				tv2.setGravity(Gravity.CENTER_HORIZONTAL);
				// wrap text
				tv2.setSingleLine(false);

				// explicitly set layout parameters
				// tv.getLayoutParams().height =
				// ViewGroup.LayoutParams.MATCH_PARENT;
				// tv.getLayoutParams().width =
				// ViewGroup.LayoutParams.MATCH_PARENT;

				TextView tv3 = (TextView) tab.getTabWidget().getChildAt(3)
						.findViewById(android.R.id.title);
				tv3.setTextColor(Color.WHITE);
				// tv1.setTextSize(12);
				// center text
				tv3.setGravity(Gravity.CENTER_HORIZONTAL);
				// wrap text
				tv3.setSingleLine(false);

				// explicitly set layout parameters
				// tv1.getLayoutParams().height =
				// ViewGroup.LayoutParams.MATCH_PARENT;
				// tv1.getLayoutParams().width =
				// ViewGroup.LayoutParams.MATCH_PARENT;

				tab.setOnTabChangedListener(new OnTabChangeListener() {

					public void onTabChanged(String tabId) {
						Log.d("debugTAG", "onTabChanged: tab number="
								+ Checkout_Tablayout.tab.getCurrentTab());

						switch (Checkout_Tablayout.tab.getCurrentTab()) {
						case 0:
							// do what you want when tab 0 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							Delivery_Activity.adapter_for_saved_address
									.notifyDataSetChanged();
							// getApplicationContext().startActivity(new
							// Intent(getApplicationContext(),
							// Delivery_Activity.class));
							break;
						case 1:
							// do what you want when tab 1 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							break;
						case 2:
							// do what you want when tab 2 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							break;

						case 3:
							// do what you want when tab 2 is selected
							System.out.println("!!!!!!!"
									+ Checkout_Tablayout.tab.getCurrentTab());
							break;

						default:

							break;
						}
					}
				});
			}

			loadLocale();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		// tabHost.setCurrentTabByTag("tab2"); //-- optional to set a tab
		// programmatically.
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

		if (Global_variable.activity.equalsIgnoreCase("RecieptActivity")) {
			Intent i = new Intent(Checkout_Tablayout.this,
					Search_Restaurant_List.class);
			startActivity(i);
		} else {
			Intent i = new Intent(Checkout_Tablayout.this, Cart.class);
			startActivity(i);
		}

	}

	private void setListener() {
		// TODO Auto-generated method stub
		try {

			footer_setting_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {
							Intent in = new Intent(getApplicationContext(),
									SettingsScreen.class);
							startActivity(in);
						}
					} else {
						Toast.makeText(getApplicationContext(),
								R.string.please_login, Toast.LENGTH_SHORT)
								.show();
					}

				}
			});

			footer_ordernow_img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (Global_variable.cart.length() == 0) {
						Toast.makeText(Checkout_Tablayout.this,
								R.string.empty_cart, Toast.LENGTH_SHORT).show();
					} else {
						Intent i = new Intent(Checkout_Tablayout.this,
								Cart.class);
						startActivity(i);

					}

				}
			});
			footer_viewmenu_img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						// TODO Auto-generated method stub
						Intent i = new Intent(Checkout_Tablayout.this,
								Categories.class);
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

			back_imageview.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getApplicationContext(), Cart.class);
					// i.putExtra("City_Name", Global_variable.FR_City_Name);
					// i.putExtra("City_id", Global_variable.FR_City_id);
					// i.putExtra("City_Position",
					// Global_variable.FR_City_Position);
					// i.putExtra("Delivery_id",
					// Global_variable.FR_Delivery_id);
					// i.putExtra("Delivery_Position",
					// Global_variable.FR_Delivery_Position);
					// i.putExtra("sessid", Global_variable.sessid);
					startActivity(i);

				}
			});
			search_imageview.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						// TODO Auto-generated method stub
						Intent i = new Intent(Checkout_Tablayout.this,
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

			footer_featured_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (SharedPreference.getuser_id(getApplicationContext()) != "") {
						if (SharedPreference
								.getuser_id(getApplicationContext()).length() != 0) {

							Intent in = new Intent(getApplicationContext(),
									MyFavourites.class);
							startActivity(in);
						}
					} else {
						Toast.makeText(getApplicationContext(),
								R.string.please_login, Toast.LENGTH_SHORT)
								.show();
					}

				}
			});

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void InitializeWidget() {
		// TODO Auto-generated method stub

		try {

			footer_ordernow_img = (ImageView) findViewById(R.id.footer_ordernow_img);
			footer_viewmenu_img = (ImageView) findViewById(R.id.footer_viewmenu_img);
			footer_featured_img = (ImageView) findViewById(R.id.footer_featured_img);
			back_imageview = (ImageView) findViewById(R.id.back_imageview);
			search_imageview = (ImageView) findViewById(R.id.search_imageview);
			txv_Checkout = (TextView) findViewById(R.id.txv_Checkout);
			// txv_payment =(TextView) findViewById(R.id.txv_payment);
			// txv_Receipt =(TextView) findViewById(R.id.txv_Receipt);

			footer_setting_img = (ImageView) findViewById(R.id.footer_setting_img);
			//

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void setNewTab(Context context, TabHost tabHost, String tag,
			int title, Intent contentID) {
		TabSpec tabSpec = tabHost.newTabSpec(tag);
		String titleString = getString(title);
		tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), title));

		tabSpec.setContent(contentID);
		tabHost.addTab(tabSpec);
	}

	// private void setNewTab(Context context, TabHost tabHost, String tag, int
	// title, int icon, int contentID ){
	// TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
	// tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), title, icon));
	// // new function to inject our own tab layout
	// tabSpec.setContent(contentID);
	// tabHost.addTab(tabSpec);
	// }

	private View getTabIndicator(Context context, int title) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_layout,
				null);
		TextView tv = (TextView) view.findViewById(R.id.textView);
		tv.setTextColor(Color.WHITE);
		tv.setText(title);
		return view;
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub

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
