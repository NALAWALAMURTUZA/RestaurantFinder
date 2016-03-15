package com.restaurantadmin.Global;

import org.json.JSONArray;
import org.json.JSONObject;

public class Global_variable {

	// Live**********************
	// public static String Domainname="http://opd365.in/";
	// public static String Domainname="http://sattvasoft.com/";
	// public static String Domainname="http://android.searchkarigar.com/";
	public static String Domainname = "http://restaurantfinder.ro/";

	public static String rf_api_Url = Domainname
			+ "rfapp/RF_admin_api/index.php?admin_api/";
	public static String rf_api_Url_ro = Domainname
			+ "rfapp/RF_admin_api/index.php?admin_api_ro/";
	public static String upLoadServerUri = Domainname + "rfapp/Upload.php";
	public static String rf_api_upload_image = upLoadServerUri;
	public static String img_pre_path = Domainname + "rfapp/images/";

	// *************************************************************************

	// Local*********************************************************

	// ENGLISH*********
	// public static String rf_api_Url =
	// "http://192.168.1.23/RF_interview/admin_api/";
	// public static String rf_api_Url =
	// "http://192.168.1.12/PHP/RF_interview/RF_interview/admin_api/";
	// public static String rf_api_Url =
	// "http://192.168.1.18/PHP/RF_interview/RF_interview/admin_api/";
	/* ROMANIAN******* */
	// public static String rf_api_Url_ro =
	// "http://192.168.1.23/RF_interview/admin_api_ro/";
	// public static String rf_api_Url_ro =
	// "http://192.168.1.12/PHP/RF_interview/RF_interview/admin_api_ro/";
	// public static String rf_api_Url_ro =
	// "http://192.168.1.18/PHP/RF_interview/RF_interview/admin_api_ro/";

	// public static String upLoadServerUri = "http://192.168.1.12/Upload.php";
	// public static String rf_api_upload_image = upLoadServerUri;
	// public static String img_pre_path = "http://192.168.1.12/images/";
	// ***************************************************************

	public static String rf_api_init = "init";
	public static String rf_api_registrationstep1 = "restaurantregistrationstep1";
	public static String rf_api_registrationstep2 = "restaurantregistrationstep2";
	public static String rf_api_registrationstep3 = "restaurantregistrationstep3";
	public static String rf_api_registrationstep4 = "restaurantregistrationstep4";
	public static String rf_api_registrationstep5 = "restaurantregistrationstep5";
	public static String rf_api_login = "admin_login";
	// public static String
	// rf_api_getrestaurantorder="http://192.168.1.18/PHP/WS_Restaurant_Finder/RF_interview/admin_api/getrestaurantorder";
	public static String rf_api_getrestaurantorder = "getrestaurantorder";
	public static String rf_api_manage_restaurant_gallery = "manage_restaurant_gallery";
	// public static String rf_api_confirmation=
	// "http://192.168.1.18/PHP/WS_Restaurant_Finder/RF_interview/admin_api/conformationapi";
	public static String rf_api_confirmation = "confirmationapi";
	public static String rf_api_get_unique_customer = "get_unique_customer";
	public static String rf_api_get_review = "get_review";
	public static String rf_api_takebooking = "takebooking";
	public static String rf_api_update_setmenu = "update_setmenu";
	public static String rf_api_update_restaurant_Presemtation = "update_restaurant_Presemtation";
	public static String rf_api_generate_invoice_manage_customer = "generate_invoice_manage_customer";
	public static String rf_api_generate_invoice_manage_feddback = "generate_invoice_manage_feddback";
	// public static String rf_api_upload_image= rf_api_Url+"upload_image";
	// public static String upLoadServerUri= rf_api_Url+"upload_image";
	public static String ForgetPassword = "user_forget_password";
	public static String get_package = "get_package";
	// CHETAN***********
	public static String OO_OrderStatus = "OO_OrderStatus";
	// ***********murtuza
	public static String rf_api_selectrestaurantfood = "selectrestaurantfood";
	public static String rf_api_insertupdatechildfood = "insertupdatechildfood";
	public static String rf_api_insertupdateparentfood = "insertupdateparentfood";
	public static String rf_api_deleteparentchildfooddetail = "deleteparentchildfooddetail";
	public static String rf_AllBooking_api = "get_restaurant_order_detail";
	// public static String rf_AllBooking_delete = "deleteallbooking";
	public static String rf_update_package = "update_package";
	public static String rf_update_global_setting = "update_global_setting";
	public static String sessid = "sessid";
	public static String comment, restaurant_id = null, rest_uid = null,
			admin_uid = null;

	// public static String uid=null;
	public static String str_selected_city_name;
	// public static String lang = "en";
	public static double latitude;
	public static double longitude;
	public static String zoom;
	public static String postal_code;
	public static String route;
	public static String selected_categories = null;
	public static String euro = "lei";
	public static String Categories_sr= "lei";
	public static String launch = "15";
	public static String dinner = "22";

	public static JSONArray array_CountryArray;
	public static JSONArray array_RegionArray;
	public static JSONArray array_DistrictArray;
	public static JSONArray array_CitytArray;
	public static JSONArray array_Package;
	public static JSONArray array_Services;
	public static JSONArray array_online_table_grabbing;
	public static JSONArray array_online_food_order;
	// public static JSONArray array_RestaurantCategory;
	public static JSONArray array_restaurantcategory;
	public static JSONArray array_AllBooking;
	public static JSONArray array_payment_method;
	public static JSONArray array_RestaurantBanner,
			array_RestaurantBanner_temp;
	// ******murtuza
	public static JSONArray array_food;
	public static JSONArray array_parentfood;
	public static JSONArray array_childfood;

	public static JSONObject restaurantregistrationstep1;
	public static JSONObject restaurantregistrationstep2;
	public static JSONObject restaurantregistrationstep3;
	public static JSONObject restaurantregistrationstep4;
	public static JSONObject restaurantregistrationstep5;

	public static JSONObject logindata;

	public static boolean loginflag = true;

	public static String[] array_creditcardStrings = {"Credit Card",
			"Master Cards", "Visa"};

	public static JSONArray array_error_en, array_error_other;

	// public static String language(Activity activity,String
	// function_name,JSONObject obj_parent)
	// {
	// myconnection con = new myconnection();
	// String str_response="";
	//
	// if(LanguageConvertPreferenceClass
	// .getlanguage(activity).equalsIgnoreCase("en"))
	// {
	// str_response = con.connection(
	// Global_variable.rf_api_Url+function_name, obj_parent);
	// // System.out.println("enurl"+str_response);
	// }else if(LanguageConvertPreferenceClass
	// .getlanguage(activity).equalsIgnoreCase("ro"))
	// {
	// str_response = con.connection(
	// Global_variable.rf_api_Url_ro+function_name, obj_parent);
	// // System.out.println("rourl"+str_response);
	// }else
	// {
	// str_response = con.connection(
	// Global_variable.rf_api_Url_ro+function_name, obj_parent);
	// // System.out.println("defaultrourl"+str_response);
	// }
	//
	//
	// return str_response;
	//
	// }

}
