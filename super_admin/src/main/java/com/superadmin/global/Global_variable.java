package com.superadmin.global;

import org.json.JSONArray;
import org.json.JSONObject;

public class Global_variable {

//	 public static String rf_api_Url = "http://192.168.1.17/RF_admin_api/super_admin_api/";
	
	//public static String rf_api_Url = "http://192.168.1.17/RF_admin_api/super_admin_api/";
	// public static String
	// rf_api_Url="http://sattvasoft.com/rfapp/RF_admin_api/index.php?super_admin_api/";

//	public static String rf_api_Url_en = "http://android.searchkarigar.com/rfapp/RF_admin_api/index.php?super_admin_api/";
//	public static String rf_api_Url_ro = "http://android.searchkarigar.com/rfapp/RF_admin_api/index.php?super_admin_api_ro/";

	
	
	public static String rf_api_Url_en = "http://restaurantfinder.ro/rfapp/RF_admin_api/index.php?super_admin_api/";
	public static String rf_api_Url_ro = "http://restaurantfinder.ro/rfapp/RF_admin_api/index.php?super_admin_api_ro/";

	//	public static String rf_api_Url = "http://192.168.1.18/PHP/WS_Restaurant_Finder/RF_interview/super_admin_api/";
	

//	public static String rf_api_Url_en = "http://192.168.1.23/RF_interview/super_admin_api/";
//	public static String rf_api_Url_ro = "http://192.168.1.23/RF_interview/super_admin_api_ro/";
	
	public static String rf_api_Url = rf_api_Url_en;
	
	public static String rf_api_login =  "admin_login";
	public static String rf_api_update_restaurant_status ="update_restaurant_status";
	public static String rf_api_update_restaurant_booking_charge = "update_restaurant_booking_charge";
	public static String rf_api_update_admin_password = "update_admin_password";
	public static String rf_api_user_forget_password ="user_forget_password";
	public static String rf_api_update_package = "update_package";
	public static String rf_api_super_admin_invoice = "super_admin_invoice";
	public static String rf_api_generate_invoice_pdf ="generate_invoice_pdf";
	public static String manage_restaurant_category =  "manage_restaurant_category";
	public static String rf_api_get_notifications =  "get_notifications";
	public static String rf_api_update_notifications = "update_notifications";
	public static String rf_api_update_global_setting = "update_global_setting";

	public static JSONArray array_Restaurant_List;
	public static JSONArray array_profile;
	public static JSONArray array_filter_Restaurant_List;
	public static JSONArray array_restaurantcategory;

	public static JSONArray array_invoice_data;
	public static String click_flag_tg_oo = "tg";

	public static String click_flag = "";
	public static String click_flag_home_screen = "";

	public static String sessid = "fhghcngcnhg";
	
	public static String first_name;
	public static String user_id = "1";
//	public static String pd_title = "Please wait..";
	public static String comment, restaurant_id = null, rest_uid = null,
			admin_uid = null, admin_email = null, lang;

	public static JSONArray array_Package;
	public static JSONObject object_package;
	
	
	//public static JSONArray array_updatr_global_setting;
}
