package com.rf.restaurant_superadmin;

import org.json.JSONArray;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.adapter.restaurant_list;
import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_function;
import com.superadmin.global.Global_variable;

public class activity_manage_restaurant extends Activity {

	private LinearLayout ll_autocomplete_search_restaurant,
			ll_restaurant_list_filter;
	private ImageView rf_login_img_header_icon;
	private ImageView img_menu;
	private TextView rf_supper_admin_txv_restaurant_list;
	private ImageView img_menu1;
	private ImageView img_logo;
	private LinearLayout header;
	private ListView lV_resturant_list;
	TextView txv_notification, txv_all_restaurant, txv_to_be_processed,
			txv_processed;
	restaurant_list adapter_restaurant_list;

	AutoCompleteTextView autocomplete_search_restaurant;

	/* Internet connection */
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_resturant_list);

		initializeWidget();
		/* create Object of internet connection* */
		cd = new ConnectionDetector(getApplicationContext());

		setonclicklistener();

		switch (Global_variable.click_flag_home_screen) {
		case "restaurant_list":

			ll_restaurant_list_filter.setVisibility(View.VISIBLE);
			ll_autocomplete_search_restaurant.setVisibility(View.GONE);
			Global_variable.array_filter_Restaurant_List = Global_function
					.filter_restaurant_by_status("0",
							Global_variable.array_Restaurant_List);
			setAdapter(Global_variable.array_filter_Restaurant_List);
			break;

		case "manage_restaurant":

			ll_restaurant_list_filter.setVisibility(View.GONE);
			ll_autocomplete_search_restaurant.setVisibility(View.VISIBLE);
			Global_variable.array_filter_Restaurant_List = Global_function
					.filter_restaurant_by_status("1",
							Global_variable.array_Restaurant_List);
			setAdapter(Global_variable.array_filter_Restaurant_List);

			rf_supper_admin_txv_restaurant_list
					.setText(R.string.str_title_manage_restaurant);
			break;

		case "":

			Global_variable.array_filter_Restaurant_List = Global_variable.array_Restaurant_List;
			setAdapter(Global_variable.array_filter_Restaurant_List);
			break;

		}
		
		

		autocomplete_search_restaurant.setThreshold(0);

		autocomplete_search_restaurant
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence str, int start,
							int before, int count) {

						Global_variable.array_filter_Restaurant_List = Global_function.filter_restaurant_by_string(
								str.toString(),
								Global_function.filter_restaurant_by_status(
										"1",
										Global_variable.array_Restaurant_List));
						setAdapter(Global_variable.array_filter_Restaurant_List);

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {

					}
				});

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

		Intent i = new Intent(activity_manage_restaurant.this,
				activity_home.class);
		startActivity(i);

	}

	private void setAdapter(JSONArray json) {
		// TODO Auto-generated method stub
		adapter_restaurant_list = new restaurant_list(
				activity_manage_restaurant.this, json);
		lV_resturant_list.setAdapter(adapter_restaurant_list);

	}

	private void setonclicklistener() {
		// TODO Auto-generated method stub
		lV_resturant_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {

						/** check Internet Connectivity */
						if (cd.isConnectingToInternet()) {

							// new TableGrabOrder().execute();

							System.out.println("!!!!listview" + position);
							Intent i = new Intent(getApplicationContext(),
									activity_restaurant_detail.class);
							i.putExtra("position", position);
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

		ll_autocomplete_search_restaurant = (LinearLayout) findViewById(R.id.ll_autocomplete_search_restaurant);
		ll_restaurant_list_filter = (LinearLayout) findViewById(R.id.ll_restaurant_list_filter);
		autocomplete_search_restaurant = (AutoCompleteTextView) findViewById(R.id.autocomplete_search_restaurant);

		rf_login_img_header_icon = (ImageView) findViewById(R.id.rf_login_img_header_icon);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		rf_supper_admin_txv_restaurant_list = (TextView) findViewById(R.id.rf_supper_admin_txv_restaurant_list);
		img_menu1 = (ImageView) findViewById(R.id.img_menu1);
		img_logo = (ImageView) findViewById(R.id.img_logo);
		header = (LinearLayout) findViewById(R.id.header);

		txv_notification = (TextView) findViewById(R.id.txv_notification);
		txv_all_restaurant = (TextView) findViewById(R.id.txv_all_restaurant);
		txv_to_be_processed = (TextView) findViewById(R.id.txv_to_be_processed);
		txv_processed = (TextView) findViewById(R.id.txv_processed);

		lV_resturant_list = (ListView) findViewById(R.id.lV_resturant_list);
	}
}