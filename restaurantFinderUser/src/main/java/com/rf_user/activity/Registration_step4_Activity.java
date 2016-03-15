package com.rf_user.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.ParseException;
import org.json.JSONArray;
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
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sqlite_dbadapter.DBAdapter;

public class Registration_step4_Activity extends Activity {
	public static RadioGroup rg_package;
	public static RadioButton rb_package1;
	public static TextView txv_package1_text1;
	public static TextView txv_package1_text2;
	public static TextView txv_package1_text3;
	public static TextView txv_package1_textdesc;
	public static RadioButton rb_package2;
	public static TextView txv_package2_text1;
	public static TextView txv_package2_text2, txv_package2_textdesc,
			txv_package3_text3;
	public static TextView txv_package2_text3, txv_package3_text1,
			txv_package3_text2, txv_package3_textdesc;
	public static RadioButton rb_package3;
	public static EditText ed_corporate_name;
	public static EditText ed_tax_identifier;
	public static EditText ed_firstname_representative;
	public static EditText ed_lastname_representative;
	public static Spinner sp_step4_country;
	public static Spinner sp_step4_region;
	public static Spinner sp_step4_city;
	// public static Spinner sp_step4_district;
	public static EditText ed_inoice_address;
	public static EditText ed_zipcode;
	public static EditText ed_billing_email;
	public static CheckBox ch_billing_address;
	public static EditText ed_step4_comments;
	public static Button btn_step4_continue;

	public static String str_selected_package_id = "1", str_package_id1,
			str_package_id2, str_package_name, str_package_id3,
			str_package_global_booking_charge, str_price, str_booking_limit,
			str_online_order_limit, str_package_descript;
	public static ProgressDialog p;
	public static ArrayList<String> countryarraylist;
	public static ArrayList<String> regionarraylist;
	public static ArrayList<String> cityarraylist;
	public static ArrayList<String> districtarraylist;

	public static ArrayList<HashMap<String, String>> hash_countryarraylist;
	public static ArrayList<HashMap<String, String>> hash_regionarraylist;
	public static ArrayList<HashMap<String, String>> hash_cityarraylist;
	public static ArrayList<HashMap<String, String>> hash_districtarraylist;

	public static HashMap<String, String> hashmap_country;
	public static HashMap<String, String> hashmap_region;
	public static HashMap<String, String> hashmap_city;
	public static HashMap<String, String> hashmap_district;
	public static String str_country_id, str_country_name,
			str_country_call_code, str_selected_country_name,
			str_selected_country_id;
	public static String str_gender = null;
	public static String str_selected_region_name, str_selected_region_id;
	public static String str_selected_city_name, str_selected_city_id;
	// public static String str_selected_district_name,
	// str_selected_district_id;
	public static String str_region_id, str_region_name;
	public static String str_city_id, str_city_name;
	// public static String str_district_id, str_district_name;

	public static String str_step1_firstname, str_step1_lastname,
			str_step1_email, str_step1_address, str_step1_zipcode,
			str_step1_country_id, str_step1_region_id, str_step1_city_id,
			str_step1_district_id, str_step1_country_name,
			str_step1_region_name, str_step1_city_name,
			str_step1_district_name;
	ArrayAdapter<String> adapter_country, adapter_region, adapter_city,
			adapter_district;
	ConnectionDetector cd;
	private Locale myLocale;
	TextView txv_rf_region, txv_rf_city;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_registration_step4_);
		cd = new ConnectionDetector(getApplicationContext());
		initialization();
		Set_Spinner();

		setlistner();
		Locale.getDefault().getLanguage();
		System.out.println("Device_language"
				+ Locale.getDefault().getLanguage());

		String langPref = "Language";
		SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs_oncreat.getString(langPref, "");

		System.out.println("Murtuza_Nalawala_language_oncreat" + language);
		if (language.equalsIgnoreCase("")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if");

		} else if (language.equalsIgnoreCase("ro")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_ar");
			setLocaleonload("ro");

		} else if (language.equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
			setLocaleonload("en");

		} else {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
			setLocaleonload("en");

		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

//		loadLocale();
	}
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
	private void Set_Spinner() {
		// TODO Auto-generated method stub
		System.out.println("1111step4regidtep1"
				+ Global_variable.restaurantregistrationstep1);
		if (Global_variable.restaurantregistrationstep1 != null) {
			try {
				System.out.println("1111step4registrstep1"
						+ Global_variable.restaurantregistrationstep1);
				str_step1_firstname = Global_variable.restaurantregistrationstep1
						.getString("FirstName");
				str_step1_lastname = Global_variable.restaurantregistrationstep1
						.getString("LastName");
				str_step1_email = Global_variable.restaurantregistrationstep1
						.getString("contact_email");
				str_step1_zipcode = Global_variable.restaurantregistrationstep1
						.getString("zip");
				str_step1_address = Global_variable.restaurantregistrationstep1
						.getString("street");
				str_step1_country_id = Global_variable.restaurantregistrationstep1
						.getString("country_id");
				str_step1_region_id = Global_variable.restaurantregistrationstep1
						.getString("region_id");
				str_step1_city_id = Global_variable.restaurantregistrationstep1
						.getString("city_id");
				System.out.println("1111step4co_re_ci_di:-" + " :-"
						+ str_step1_country_id + ":-" + str_step1_region_id
						+ ";-" + str_step1_city_id + ":-");
				ed_firstname_representative.setText(str_step1_firstname);
				ed_lastname_representative.setText(str_step1_lastname);
				ed_billing_email.setText(str_step1_email);
				ed_zipcode.setText(str_step1_zipcode);
				ed_inoice_address.setText(str_step1_address);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("1111step4package" + Global_variable.array_Package);
		if (Global_variable.array_Package != null) {
			for (int i = 0; i < Global_variable.array_Package.length(); i++) {
				JSONObject obj_package;
				try {
					// PACKAGE1*************
					obj_package = Global_variable.array_Package
							.getJSONObject(0);
					System.out.println("1111step4objectPACKAGE1" + obj_package);
					str_package_id1 = obj_package.getString("package_id");
					str_package_name = obj_package.getString("package_name");
					str_package_global_booking_charge = obj_package
							.getString("global_booking_charge");
					str_price = obj_package.getString("price");
					str_booking_limit = obj_package.getString("booking_limit");
					str_online_order_limit = obj_package
							.getString("online_order_limit");
					str_package_descript = obj_package
							.getString("package_description");
					txv_package1_text1.setText(str_package_name);
					if (str_price.equalsIgnoreCase("0.00")) {
						txv_package1_text2
								.setText(getString(R.string.str_FREE));
					} else {
						txv_package1_text2.setText("lei" + " " + str_price);
					}

					if (str_online_order_limit.equalsIgnoreCase("0")) {
						txv_package1_text3
								.setText(getString(R.string.str_we_can_accept_unlimited));

					} else {
						txv_package1_text3
								.setText(getString(R.string.str_We_can_accept_maximum)
										+ " "
										+ str_online_order_limit
										+ " "
										+ getString(R.string.str_orders));
					}

					txv_package1_textdesc.setText(str_package_descript + "."
							+ str_package_global_booking_charge + " "
							+ "Global booking charge");
					// PACKAGE1*************

					// PACKAGE2*************
					obj_package = Global_variable.array_Package
							.getJSONObject(1);
					System.out.println("1111step4objectPACKAGE2" + obj_package);
					str_package_id2 = obj_package.getString("package_id");
					str_package_name = obj_package.getString("package_name");
					str_package_global_booking_charge = obj_package
							.getString("global_booking_charge");
					str_price = obj_package.getString("price");
					str_booking_limit = obj_package.getString("booking_limit");
					str_online_order_limit = obj_package
							.getString("online_order_limit");
					str_package_descript = obj_package
							.getString("package_description");

					txv_package2_text1.setText(str_package_name);
					if (str_price.equalsIgnoreCase("0.00")) {
						txv_package2_text2.setText(getResources().getString(
								R.string.str_FREE));
					} else {
						txv_package2_text2.setText("lei"
								+ " " + str_price);
					}
					if (str_online_order_limit.equalsIgnoreCase("0")) {
						txv_package2_text3
								.setText(getString(R.string.str_we_can_accept_unlimited));

					} else {
						txv_package2_text3
								.setText(getString(R.string.str_We_can_accept_maximum)
										+ " "
										+ str_online_order_limit
										+ " "
										+ getString(R.string.str_orders));
					}

					txv_package2_textdesc.setText(str_package_descript + "."
							+ str_package_global_booking_charge + " "
							+ "Global booking charge");
					// PACKAGE2*************

					// PACKAGE3*************
					obj_package = Global_variable.array_Package
							.getJSONObject(2);
					System.out.println("1111step4objectPACKAGE3" + obj_package);
					str_package_id3 = obj_package.getString("package_id");
					str_package_name = obj_package.getString("package_name");
					str_package_global_booking_charge = obj_package
							.getString("global_booking_charge");
					str_price = obj_package.getString("price");
					str_booking_limit = obj_package.getString("booking_limit");
					str_online_order_limit = obj_package
							.getString("online_order_limit");
					str_package_descript = obj_package
							.getString("package_description");

					txv_package3_text1.setText(str_package_name);
					if (str_price.equalsIgnoreCase("0.00")) {
						txv_package3_text2
								.setText(getString(R.string.str_FREE));
					} else {
						txv_package3_text2.setText("lei" + " " + str_price);
					}
					if (str_online_order_limit.equalsIgnoreCase("0")) {
						txv_package3_text3
								.setText(getString(R.string.str_we_can_accept_unlimited));

					} else {
						txv_package3_text3
								.setText(getString(R.string.str_We_can_accept_maximum)
										+ " "
										+ str_online_order_limit
										+ " "
										+ getString(R.string.str_orders));
					}

					txv_package3_textdesc.setText(str_package_descript + "."
							+ str_package_global_booking_charge + " "
							+ "Global booking charge");
					// PACKAGE3*************
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// ***********************
		// spineeeeeeeeeeeeeeer**********
		// ********************************
		countryarraylist = new ArrayList<String>();
		regionarraylist = new ArrayList<String>();
		cityarraylist = new ArrayList<String>();
		// districtarraylist = new ArrayList<String>();
		hash_countryarraylist = new ArrayList<HashMap<String, String>>();
		hash_regionarraylist = new ArrayList<HashMap<String, String>>();
		hash_cityarraylist = new ArrayList<HashMap<String, String>>();
		// hash_districtarraylist = new ArrayList<HashMap<String, String>>();
		if (Global_variable.array_CountryArray.length() != 0) {
			for (int i = 0; i < Global_variable.array_CountryArray.length(); i++) {
				try {

					JSONObject json_object = Global_variable.array_CountryArray
							.getJSONObject(i);
					System.out.println("1111json_objectcountry" + json_object);
					hashmap_country = new HashMap<String, String>();
					str_country_id = json_object.getString("country_id");
					str_country_name = json_object.getString("cname_en");
					str_country_call_code = json_object
							.getString("country_call_code");
					System.out.println("1111country_id_namne_code"
							+ str_country_call_code + str_country_id
							+ str_country_name);
					// Global_variable.SR_User_Country = json_object
					// .getString("SR_CountryName");
					hashmap_country.put("str_country_id", str_country_id);
					hashmap_country.put("str_country_name", str_country_name);
					hashmap_country.put("str_country_call_code",
							str_country_call_code);
					// countryarraylist.add(SR_CountryID);
					countryarraylist.add(str_country_name);
					hash_countryarraylist.add(hashmap_country);
					System.out.println("1111countryarraylist.."
							+ countryarraylist);
					adapter_country = new ArrayAdapter<String>(
							Registration_step4_Activity.this,
							android.R.layout.simple_spinner_dropdown_item,
							countryarraylist);
					sp_step4_country.setAdapter(adapter_country);

					// *******************step1 country id
					// name**********************
					System.out.println("1111step4countryidbefoerif"
							+ str_step1_country_id);
					if (str_step1_country_id != null) {
						System.out.println("1111step4countryidbefoerif"
								+ str_step1_country_id);
						for (int j = 0; j < hash_countryarraylist.size(); j++) {
							if (hash_countryarraylist.get(j)
									.get("str_country_id")
									.equals(str_step1_country_id))

							{
								System.out.println("1111step4countrynameif"
										+ str_step1_country_name);
								str_step1_country_name = hash_countryarraylist
										.get(j).get("str_country_name");
								System.out.println("1111step1step4cname"
										+ str_step1_country_name);
							}

							int indexCapacityCountry = adapter_country
									.getPosition(str_step1_country_name);

							Log.i(getString(R.string.str_indexCapacity),
									getString(R.string.str_indexCapacity)
											+ indexCapacityCountry);
							sp_step4_country.setSelection(indexCapacityCountry);
						}

					}
					// ********************step1country id
					// name******************
					// sp_step_4_country.setAdapter(new ArrayAdapter<String>(
					// Registration_step4_Activity.this,
					// android.R.layout.simple_spinner_dropdown_item,
					// countryarraylist));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		if (Global_variable.array_RegionArray.length() != 0) {
			for (int i = 0; i < Global_variable.array_RegionArray.length(); i++) {
				try {
					txv_rf_region.setVisibility(View.GONE);
					sp_step4_region.setVisibility(View.VISIBLE);
					JSONObject json_object = Global_variable.array_RegionArray
							.getJSONObject(i);
					System.out.println("1111json_objectregion" + json_object);
					hashmap_region = new HashMap<String, String>();
					str_country_id = json_object.getString("country_id");
					str_region_id = json_object.getString("region_id");
					str_region_name = json_object.getString("name_en");
					System.out.println("1111region_id_namne_code"
							+ str_country_id + str_region_id + str_region_name);
					// Global_variable.SR_User_Country = json_object
					// .getString("SR_CountryName");
					hashmap_region.put("str_country_id", str_country_id);
					hashmap_region.put("str_region_id", str_region_id);
					hashmap_region.put("str_region_name", str_region_name);
					// countryarraylist.add(SR_CountryID);
					regionarraylist.add(str_region_name);
					hash_regionarraylist.add(hashmap_region);
					System.out.println("1111regionarraylist.."
							+ regionarraylist);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else {
			txv_rf_region.setVisibility(View.VISIBLE);
			sp_step4_region.setVisibility(View.GONE);
		}
		if (Global_variable.array_CitytArray.length() != 0) {
			for (int i = 0; i < Global_variable.array_CitytArray.length(); i++) {
				try {
					txv_rf_city.setVisibility(View.GONE);
					sp_step4_city.setVisibility(View.VISIBLE);
					JSONObject json_object = Global_variable.array_CitytArray
							.getJSONObject(i);
					System.out.println("1111json_objectcity" + json_object);
					hashmap_city = new HashMap<String, String>();
					str_region_id = json_object.getString("region_id");
					str_city_id = json_object.getString("city_id");
					str_city_name = json_object.getString("name_en");
					System.out.println("1111city_id_namne_code" + str_region_id
							+ str_city_id + str_city_name);
					// Global_variable.SR_User_Country = json_object
					// .getString("SR_CountryName");
					hashmap_city.put("str_region_id", str_region_id);
					hashmap_city.put("str_city_id", str_city_id);
					hashmap_city.put("str_city_name", str_city_name);
					// countryarraylist.add(SR_CountryID);
					cityarraylist.add(str_city_name);
					hash_cityarraylist.add(hashmap_city);
					System.out.println("1111cityarraylist.." + cityarraylist);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else {
			txv_rf_city.setVisibility(View.VISIBLE);
			sp_step4_city.setVisibility(View.GONE);
		}

	}

	private void setlistner() {
		// TODO Auto-generated method stub
		rg_package.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
					case R.id.rb_package1 :

						str_selected_package_id = str_package_id1;
						System.out.println("1111pack1"
								+ str_selected_package_id);
						break;
					case R.id.rb_package2 :

						str_selected_package_id = str_package_id2;
						System.out.println("1111pack2"
								+ str_selected_package_id);
						break;
					case R.id.rb_package3 :

						str_selected_package_id = str_package_id3;
						System.out.println("1111pack3"
								+ str_selected_package_id);
						break;
				}
			}
		});

		btn_step4_continue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cd.isConnectingToInternet()) {

					new async_regi_step4().execute();
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
		ch_billing_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					// rdbtn_shipping_inf.setChecked(true);
					// ed_corporate_name.setText("");
					// ed_tax_identifier.setText("");
					ed_firstname_representative.setText("");
					ed_lastname_representative.setText("");
					ed_billing_email.setText("");
					ed_inoice_address.setText("");
					ed_zipcode.setText("");
				} else {
					ed_firstname_representative.setText(str_step1_firstname);
					ed_lastname_representative.setText(str_step1_lastname);
					ed_billing_email.setText(str_step1_email);
					ed_zipcode.setText(str_step1_zipcode);
					ed_inoice_address.setText(str_step1_address);
				}

			}
		});
		// spineeeeeeeeeeeerrrrrrrrrrrr*******************
		sp_step4_country
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						try {
							str_selected_country_name = sp_step4_country
									.getSelectedItem().toString();
							int str_countryID = sp_step4_country
									.getSelectedItemPosition();

							str_selected_country_id = hash_countryarraylist
									.get(str_countryID).get("str_country_id");
							str_country_call_code = hash_countryarraylist.get(
									str_countryID).get("str_country_call_code");
							System.out.println("1111selectedcountryid"
									+ str_selected_country_id);
							System.out.println("1111selectedcountrycall_id"
									+ str_country_call_code);
							System.out.println("1111ahasamapcountry"
									+ str_country_id);

							regionarraylist = new ArrayList<String>();
							if (hash_regionarraylist.size() != 0) {
								txv_rf_region.setVisibility(View.GONE);
								sp_step4_region.setVisibility(View.VISIBLE);
								for (int i = 0; i < hash_regionarraylist.size(); i++) {
									str_country_id = hash_regionarraylist
											.get(i).get("str_country_id");
									System.out.println("1111if"
											+ str_selected_country_id
											+ "--"
											+ hash_regionarraylist.get(i).get(
													"str_region_id"));
									if (str_selected_country_id
											.equalsIgnoreCase(str_country_id)) {
										System.out.println("1111if"
												+ str_selected_country_id
												+ "--"
												+ hash_regionarraylist.get(i)
														.get("str_region_id"));
										String str_hashmap = hash_regionarraylist
												.get(i).get("str_region_name");
										System.out.println("1111hashmapregion"
												+ str_hashmap);
										regionarraylist.add(str_hashmap);
										System.out
												.println("1111afterregionarray"
														+ regionarraylist);

									}
								}
								adapter_region = new ArrayAdapter<String>(
										Registration_step4_Activity.this,
										android.R.layout.simple_spinner_dropdown_item,
										regionarraylist);
								sp_step4_region.setAdapter(adapter_region);
							} else {
								txv_rf_region.setVisibility(View.VISIBLE);
								sp_step4_region.setVisibility(View.GONE);
							}
							// *******************step1 REGION id
							// name**********************
							System.out.println("1111step4regionidbefoerif"
									+ str_step1_region_id);
							if (str_step1_region_id != null) {
								try {
									System.out
											.println("1111step4regionidbefoerif"
													+ str_step1_region_id);
									for (int j = 0; j < hash_regionarraylist
											.size(); j++) {
										if (hash_regionarraylist.get(j)
												.get("str_region_id")
												.equals(str_step1_region_id))

										{
											str_step1_region_name = hash_regionarraylist
													.get(j).get(
															"str_region_name");
											System.out
													.println("1111step1step4cnameregion"
															+ str_step1_region_name);

											int indexCapacityRegion = adapter_region
													.getPosition(str_step1_region_name);

											Log.i("!!!!indexCapacityregion",
													"indexCapacityregion"
															+ indexCapacityRegion);
											sp_step4_region
													.setSelection(indexCapacityRegion);
										}
									}

								} catch (IndexOutOfBoundsException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ArrayIndexOutOfBoundsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IndexOutOfBoundsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		sp_step4_region.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					str_selected_region_name = sp_step4_region
							.getSelectedItem().toString();
					int str_regionID = sp_step4_region
							.getSelectedItemPosition();

					str_selected_region_id = hash_regionarraylist.get(
							str_regionID).get("str_region_id");
					System.out.println("1111selectedregion"
							+ str_selected_region_id);
					System.out.println("1111hashmapregion" + str_region_id);

					cityarraylist = new ArrayList<String>();
					if (hash_cityarraylist.size() != 0) {
						txv_rf_city.setVisibility(View.GONE);
						sp_step4_city.setVisibility(View.VISIBLE);
						for (int i = 0; i < hash_cityarraylist.size(); i++) {
							str_region_id = hash_cityarraylist.get(i).get(
									"str_region_id");
							System.out.println("1111ifout"
									+ str_selected_region_id
									+ "--"
									+ hash_cityarraylist.get(i).get(
											"str_region_id"));
							if (str_selected_region_id
									.equalsIgnoreCase(str_region_id)) {
								System.out.println("1111if"
										+ str_selected_region_id
										+ "--"
										+ hash_cityarraylist.get(i).get(
												"str_region_id"));
								String str_hashmap = hash_cityarraylist.get(i)
										.get("str_city_name");
								System.out.println("1111hashmapcity"
										+ str_hashmap);
								cityarraylist.add(str_hashmap);
								System.out.println("1111aftercityarray"
										+ cityarraylist);

							}

						}

						adapter_city = new ArrayAdapter<String>(
								Registration_step4_Activity.this,
								android.R.layout.simple_spinner_dropdown_item,
								cityarraylist);
						sp_step4_city.setAdapter(adapter_city);
					} else {
						txv_rf_city.setVisibility(View.VISIBLE);
						sp_step4_city.setVisibility(View.GONE);
					}
					System.out
							.println("!!!!!!!!!!!!!!!step4Global_variable.str_selected_city_name"
									+ Global_variable.str_selected_city_name);
					int indexCapacityCity = adapter_city
							.getPosition(Global_variable.str_selected_city_name);
					Log.i(getString(R.string.str_indexCapacity),
							getString(R.string.str_indexCapacity)
									+ indexCapacityCity);
					sp_step4_city.setSelection(indexCapacityCity);

				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		sp_step4_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					int str_cityID = 0;
					str_selected_city_name = sp_step4_city.getSelectedItem()
							.toString();
					System.out.println("!!!!str_hashmap" + hash_cityarraylist);
					for (int i = 0; i < hash_cityarraylist.size(); i++) {
						System.out.println("!!!!str_selected_city_name"
								+ str_selected_city_name);
						System.out.println("!!!!str_cityname"
								+ hash_cityarraylist.get(i)
										.get("str_city_name"));
						if (hash_cityarraylist.get(i).get("str_city_name")
								.equalsIgnoreCase(str_selected_city_name)) {
							System.out.println("sameinstep4");
							str_cityID = Integer.parseInt(hash_cityarraylist
									.get(i).get("str_city_id"));
							System.out.println("samenamegetidstep4"
									+ str_cityID);
						}

					}
					// System.out.println("!!!!str_selected_city_name"+str_selected_city_name);
					System.out.println("!!!!str_city_id" + str_cityID);

					// str_selected_city_id = hash_cityarraylist.get(str_cityID)
					// .get("str_city_id");
					str_selected_city_id = str_cityID + "";
					System.out.println("1111selectedcity"
							+ str_selected_city_id);

				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// sp_step4_district
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> parent,
		// View view, int position, long id) {
		//
		// try {
		// ((TextView) parent.getChildAt(0)).setTextSize(14);
		// str_selected_district_name = sp_step4_district
		// .getSelectedItem().toString();
		// int str_districtID = sp_step4_district
		// .getSelectedItemPosition();
		//
		// str_selected_district_id = hash_districtarraylist
		// .get(str_districtID).get("str_district_id");
		// System.out.println("1111selecteddistrict"
		// + str_selected_district_id);
		// } catch (NullPointerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ArrayIndexOutOfBoundsException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IndexOutOfBoundsException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	private void initialization() {
		rg_package = (RadioGroup) findViewById(R.id.rg_package);
		rb_package1 = (RadioButton) findViewById(R.id.rb_package1);

		txv_package1_text1 = (TextView) findViewById(R.id.txv_package1_text1);
		txv_package1_text2 = (TextView) findViewById(R.id.txv_package1_text2);
		txv_package1_text3 = (TextView) findViewById(R.id.txv_package1_text3);
		txv_package1_textdesc = (TextView) findViewById(R.id.txv_package1_textdesc);
		rb_package2 = (RadioButton) findViewById(R.id.rb_package2);
		txv_package2_text1 = (TextView) findViewById(R.id.txv_package2_text1);
		txv_package2_text2 = (TextView) findViewById(R.id.txv_package2_text2);
		txv_package2_text3 = (TextView) findViewById(R.id.txv_package2_text3);
		txv_package2_textdesc = (TextView) findViewById(R.id.txv_package2_textdesc);
		rb_package3 = (RadioButton) findViewById(R.id.rb_package3);
		txv_package3_text1 = (TextView) findViewById(R.id.txv_package3_text1);
		txv_package3_text2 = (TextView) findViewById(R.id.txv_package3_text2);
		txv_package3_text3 = (TextView) findViewById(R.id.txv_package3_text3);
		txv_package3_textdesc = (TextView) findViewById(R.id.txv_package3_textdesc);
		ed_corporate_name = (EditText) findViewById(R.id.ed_corporate_name);
		ed_tax_identifier = (EditText) findViewById(R.id.ed_tax_identifier);
		ed_firstname_representative = (EditText) findViewById(R.id.ed_firstname_representative);
		ed_lastname_representative = (EditText) findViewById(R.id.ed_lastname_representative);
		sp_step4_country = (Spinner) findViewById(R.id.sp_step_4_country);
		sp_step4_region = (Spinner) findViewById(R.id.sp_step4_region);
		sp_step4_city = (Spinner) findViewById(R.id.sp_step4_city);
		txv_rf_region = (TextView) findViewById(R.id.txv_rf_region);
		txv_rf_city = (TextView) findViewById(R.id.txv_rf_city);
		// sp_step4_district = (Spinner) findViewById(R.id.sp_step4_district);
		ed_inoice_address = (EditText) findViewById(R.id.ed_inoice_address);
		ed_zipcode = (EditText) findViewById(R.id.ed_zipcode);
		ed_billing_email = (EditText) findViewById(R.id.ed_billing_email);
		ch_billing_address = (CheckBox) findViewById(R.id.ch_billing_address);
		ed_step4_comments = (EditText) findViewById(R.id.ed_step4_comments);
		btn_step4_continue = (Button) findViewById(R.id.btn_step4_continue);
		ed_step4_comments.setText(Global_variable.comment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_step4, menu);
		return true;
	}

	public class async_regi_step4 extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_restaurant_app;
		JSONObject obj_restaurantregistrationstep4;
		JSONObject obj_MainRequest;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(Registration_step4_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			obj_restaurant_app = new JSONObject();
			obj_MainRequest = new JSONObject();
			obj_restaurantregistrationstep4 = new JSONObject();;
			try {

				// obj_restaurant_app********
				System.out.println("1111selectpackage_id"
						+ str_selected_package_id);
				obj_restaurant_app.put("package_id", str_selected_package_id);
				obj_restaurant_app.put("corporate_name_of_company",
						ed_corporate_name.getText().toString());
				obj_restaurant_app.put("tax_identification_number",
						ed_tax_identifier.getText().toString());
				obj_restaurant_app.put("invoice_firstname",
						ed_firstname_representative.getText().toString());
				obj_restaurant_app.put("invoice_lastname",
						ed_lastname_representative.getText().toString());
				obj_restaurant_app.put("invoice_country_id",
						str_selected_country_id);
				obj_restaurant_app.put("invoice_region_id",
						str_selected_region_id);
				obj_restaurant_app.put("invoice_city_id", str_selected_city_id);
				obj_restaurant_app.put("invoice_district_id", "");
				obj_restaurant_app.put("invoice_address", ed_inoice_address
						.getText().toString());
				obj_restaurant_app.put("invoice_zipcode", ed_zipcode.getText()
						.toString());
				obj_restaurant_app.put("invoice_email", ed_billing_email
						.getText().toString());
				System.out.println("1111obj_restaurant_app"
						+ obj_restaurant_app);
				// obj_restaurant_app********

				// obj_restaurantregistrationstep4********

				obj_restaurantregistrationstep4.put("restaurant_app",
						obj_restaurant_app);

				// obj_restaurantregistrationstep4********

				// obj_MainRequest*******************************

				System.out
						.println("1111Global_variable.restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);
				System.out
						.println("1111Global_variable.restaurantregistrationstep2"
								+ Global_variable.restaurantregistrationstep2);
				System.out
						.println("1111Global_variable.restaurantregistrationstep3"
								+ Global_variable.restaurantregistrationstep3);
				System.out.println("1111step2sessid" + Global_variable.sessid);
				obj_MainRequest.put("restaurantregistrationstep1",
						Global_variable.restaurantregistrationstep1);
				obj_MainRequest.put("restaurantregistrationstep2",
						Global_variable.restaurantregistrationstep2);
				obj_MainRequest.put("restaurantregistrationstep3",
						Global_variable.restaurantregistrationstep3);
				obj_MainRequest.put("restaurantregistrationstep4",
						obj_restaurantregistrationstep4);
				obj_MainRequest.put("sessid", Global_variable.sessid);
				obj_MainRequest.put("comment", ed_step4_comments.getText()
						.toString());

				System.out
						.println("1111obj_MainRequeststep4" + obj_MainRequest);

				// obj_MainRequest*******************************
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {

				// *************
				// for request
				try {
					HttpConnection con = new HttpConnection();
					String str_response = con.connection_rest_reg(Registration_step4_Activity.this,
							Global_variable.rf_api_registrationstep4,
							obj_MainRequest);

					json = new JSONObject(str_response);
					System.out.println("1111finaljsonstepTG" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i(getString(R.string.str_Parse_Exception), e + "");

				} catch (NullPointerException np) {

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NullPointerException np) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			if (p.isShowing())
				p.dismiss();
			try {
				jsonSuccessStr = json.getString("success");
				Global_variable.sessid = json.getString("sessid");
				System.out.println("1111sessidstep2respo"
						+ Global_variable.sessid);

				if (jsonSuccessStr.equalsIgnoreCase("true")) {
					Global_variable.comment = json.getString("comment");
					System.out.println("1111step4comments"
							+ Global_variable.comment);
					JSONObject Data = json.getJSONObject("data");
					System.out.println("1111obj_Data" + Data);
					if (Data != null) {
						Global_variable.restaurantregistrationstep1 = Data
								.getJSONObject("restaurantregistrationstep1");
						System.out.println("1111restaurantregistrationstep1"
								+ Global_variable.restaurantregistrationstep1);

						String str_country_code = Global_variable.restaurantregistrationstep1
								.getString("country_call_id");
						String str_mobileno = Global_variable.restaurantregistrationstep1
								.getString("contact_number");

						Global_variable.restaurantregistrationstep2 = Data
								.getJSONObject("restaurantregistrationstep2");
						System.out.println("1111restaurantregistrationstep2"
								+ Global_variable.restaurantregistrationstep2);
						Global_variable.restaurantregistrationstep3 = Data
								.getJSONObject("restaurantregistrationstep3");
						System.out.println("1111restaurantregistrationstep3"
								+ Global_variable.restaurantregistrationstep3);
						Global_variable.restaurantregistrationstep4 = Data
								.getJSONObject("restaurantregistrationstep4");
						System.out.println("1111restaurantregistrationstep4"
								+ Global_variable.restaurantregistrationstep4);
						// JSONObject obj_restapp =
						// Global_variable.restaurantregistrationstep4
						// .getJSONObject("restaurant_app");
						// String str_email = obj_restapp
						// .getString("invoice_email");

						RegistrationTablayout.tab.getTabWidget()
								.getChildAt(0).setClickable(true);
						RegistrationTablayout.tab.getTabWidget()
								.getChildAt(1).setClickable(true);
						RegistrationTablayout.tab.getTabWidget()
								.getChildAt(2).setClickable(true);
						RegistrationTablayout.tab.getTabWidget()
								.getChildAt(3).setClickable(true);

						// Registration_step2_Activity.ed_add_comments
						// .setText(Global_variable.comment);
						// Registration_step3_Activity.rf_regi_step3_ed_comment
						// .setText(Global_variable.comment);
						Intent i = new Intent(Registration_step4_Activity.this,
								Registration_step5_Activity.class);
						startActivity(i);
					}
				} else {
					JSONObject Error = json.getJSONObject("errors");
					System.out.println("1111errors" + Error);
					if (Error != null) {
						if (Error.has("corporate_name_of_company")) {
							JSONArray corporate_name_of_company = Error
									.getJSONArray("corporate_name_of_company");
							System.out.println("1111corporate_name_of_company"
									+ corporate_name_of_company);
							if (corporate_name_of_company != null) {
								String str_corporate_name_of_company = corporate_name_of_company
										.getString(0);
								System.out
										.println("1111str_corporate_name_of_company"
												+ str_corporate_name_of_company);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_corporate_name_of_company,
										Toast.LENGTH_LONG).show();
							}
						}
						if (Error.has("tax_identification_number")) {
							JSONArray tax_identification_number = Error
									.getJSONArray("tax_identification_number");
							System.out.println("1111tax_identification_number"
									+ tax_identification_number);
							if (tax_identification_number != null) {
								String str_tax_identification_number = tax_identification_number
										.getString(0);
								System.out
										.println("1111str_tax_identification_number"
												+ str_tax_identification_number);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_tax_identification_number,
										Toast.LENGTH_LONG).show();
							}
						}
						if (Error.has("invoice_firstname")) {
							JSONArray invoice_firstname = Error
									.getJSONArray("invoice_firstname");
							System.out.println("1111invoice_firstname"
									+ invoice_firstname);
							if (invoice_firstname != null) {
								String str_invoice_firstname = invoice_firstname
										.getString(0);
								System.out.println("1111str_invoice_firstname"
										+ str_invoice_firstname);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_invoice_firstname,
										Toast.LENGTH_LONG).show();
							}
						}
						if (Error.has("invoice_lastname")) {
							JSONArray invoice_lastname = Error
									.getJSONArray("invoice_lastname");
							System.out.println("1111invoice_lastname"
									+ invoice_lastname);
							if (invoice_lastname != null) {
								String str_invoice_lastname = invoice_lastname
										.getString(0);
								System.out.println("1111str_invoice_lastname"
										+ str_invoice_lastname);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_invoice_lastname, Toast.LENGTH_LONG)
										.show();
							}
						}

						if (Error.has("invoice_address")) {
							JSONArray invoice_address = Error
									.getJSONArray("invoice_address");
							System.out.println("1111invoice_address"
									+ invoice_address);
							if (invoice_address != null) {
								String str_invoice_address = invoice_address
										.getString(0);
								System.out.println("1111str_invoice_address"
										+ str_invoice_address);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_invoice_address, Toast.LENGTH_LONG)
										.show();
							}
						}
						if (Error.has("invoice_zipcode")) {
							JSONArray invoice_zipcode = Error
									.getJSONArray("invoice_zipcode");
							System.out.println("1111invoice_zipcode"
									+ invoice_zipcode);
							if (invoice_zipcode != null) {
								String str_invoice_zipcode = invoice_zipcode
										.getString(0);
								System.out.println("1111str_invoice_zipcode"
										+ str_invoice_zipcode);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_invoice_zipcode, Toast.LENGTH_LONG)
										.show();
							}
						}

						if (Error.has("invoice_email")) {
							JSONArray invoice_email = Error
									.getJSONArray("invoice_email");
							System.out.println("1111invoice_email"
									+ invoice_email);
							if (invoice_email != null) {
								String str_invoice_email = invoice_email
										.getString(0);
								System.out.println("1111str_invoice_email"
										+ str_invoice_email);
								Toast.makeText(
										Registration_step4_Activity.this,
										str_invoice_email, Toast.LENGTH_LONG)
										.show();
							}
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1111success" + jsonSuccessStr);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				onBackPressed();
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {

			RegistrationTablayout.tab.setCurrentTab(2);
		} else {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),
							R.string.No_Internet_Connection, Toast.LENGTH_SHORT)
							.show();

				}

			});
		}

	}
}
