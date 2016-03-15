package com.rf_user.global;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Global_variable {




	/** Live URL USER */
    	
	// public static String Domainname="http://opd365.in/";
	// public static String Domainname="http://sattvasoft.com/";
	// public static String Domainname="http://android.searchkarigar.com/";
	public static String Domainname = "http://www.restaurantfinder.ro/";
    
	public static String rf_en_Url = Domainname+"rfapp/RF_admin_api/index.php?api/";
	public static String rf_ro_Url = Domainname+"rfapp/RF_admin_api/index.php?api_ro/";
	public static String rf_lang_Url = rf_en_Url;
	public static String image_url = Domainname+"rfapp/images/";
	
	public static String img_pre_path = image_url;
	public static String upLoadServerUri = Domainname+"rfapp/Upload.php";

//	/** Live URL REgistration Step Restaurant Admin */
	public static String rf_api_Url_en = Domainname+"rfapp/RF_admin_api/index.php?admin_api/";
	public static String rf_api_Url_ro = Domainname+"rfapp/RF_admin_api/index.php?admin_api_ro/";
	public static String rf_api_Url_lang = rf_api_Url_en;
	public static String rf_api_upload_image = Domainname+"rfapp/Upload.php";
	
	public static String file_url = Domainname+"PDF/rf_user_terms.pdf";
	public static String file_url_ro = Domainname+"PDF/rf_user_terms_ro.pdf";
	
//	/** Live opd365.in URL USER */
//	public static String rf_en_Url = "http://opd365.in/rfapp/RF_admin_api/index.php?api/";
//	public static String rf_ro_Url = "http://opd365.in/rfapp/RF_admin_api/index.php?api_ro/";
//	public static String rf_lang_Url = rf_en_Url;
//	public static String image_url = "http://opd365.in/rfapp/rfapp/images/";
//	
//	public static String img_pre_path = image_url;
//	public static String upLoadServerUri = "http://opd365.inrfapp/Upload.php";
//
////	/** Live URL REgistration Step Restaurant Admin */
//	public static String rf_api_Url_en = "http://opd365.in/rfapp/RF_admin_api/index.php?admin_api/";
//	public static String rf_api_Url_ro = "http://opd365.in/rfapp/RF_admin_api/index.php?admin_api_ro/";
//	public static String rf_api_Url_lang = rf_api_Url_en;
//	public static String rf_api_upload_image = "http://opd365.in/rfapp/Upload.php";

	// /** Chetan's PC USER*/
//	 public static String rf_en_Url = "http://192.168.1.23/RF_interview/api/";
	
	//pankaj pc
//	 public static String rf_en_Url = "http://192.168.1.12/PHP/RF_interview/RF_interview/api/";
//	 public static String rf_ro_Url = "http://192.168.1.12/PHP/RF_interview/RF_interview/api_ro/";
//	 public static String rf_lang_Url =rf_en_Url;
//	 public static String image_url = "http://192.168.1.12/images/";
//	 
//	 public static String img_pre_path = image_url;
//	 public static String upLoadServerUri = "http://192.168.1.12/Upload.php";
//	 
//	 public static String rf_api_Url_en = "http://192.168.1.12/PHP/RF_interview/RF_interview/admin_api/";
//     public static String rf_api_Url_ro = "http://192.168.1.12/PHP/RF_interview/RF_interview/admin_api_ro/";
//	 public static String rf_api_Url_lang = rf_api_Url_en;
//     public static String rf_api_upload_image = upLoadServerUri;
     
	/**URL for SATTVASOFT */
     
//     public static String rf_en_Url = "http://sattvasoft.com/rfapp/RF_admin_api/index.php?api/";
//	 public static String rf_ro_Url = "http://sattvasoft.com/rfapp/RF_admin_api/index.php?api_ro/";
//	 public static String rf_lang_Url =rf_en_Url;
	 
//	 public static String image_url = "http://sattvasoft.com/rfapp/images/";
//	 public static String img_pre_path = image_url;
//	 public static String upLoadServerUri = "http://sattvasoft.com/rfapp/Upload.php";
	 
//	 public static String image_url = "http://restaurantfinder.ro/rfapp/images/";
//	 public static String img_pre_path = image_url;
//	 public static String upLoadServerUri = "http://restaurantfinder.ro/rfapp/Upload.php";
	
//	 public static String rf_api_Url_en = "http://sattvasoft.com/rfapp/RF_admin_api/index.php?admin_api/";
//     public static String rf_api_Url_ro = "http://sattvasoft.com/rfapp/RF_admin_api/index.php?admin_api_ro/";
//	 public static String rf_api_Url_lang = rf_api_Url_en;
//     public static String rf_api_upload_image = upLoadServerUri;
	
     
	
//	http://restaurantfinder.ro/rfapp/images/
//     http://restaurantfinder.ro/rfapp/Upload.php
		
		
		
	 
	 
//
//	/** Chetan's PC Restaurant Admin */
//	 public static String rf_api_Url_en = "http://192.168.1.23/RF_interview/admin_api/";
//	 public static String rf_api_Url_ro ="http://192.168.1.23/RF_interview/admin_api_ro/";
//	 public static String rf_api_Url_lang =rf_api_Url_en;
//
//	
//	 public static String upLoadServerUri = "http://192.168.1.12/Upload.php";
//	
//	 public static String rf_api_upload_image =
//	 "http://192.168.1.12/Upload.php";
//	
//	 public static String img_pre_path = "http://192.168.1.12/images/";
	/** Ishita's PC USER */
	/*
	 * public static String rf_en_Url = "http://192.168.1.12/RF_interview/api/";
	 * public static String rf_ro_Url =
	 * "http://192.168.1.12/RF_interview/api_ro/"; public static String
	 * rf_lang_Url =rf_en_Url;
	 */

	/** Ishita's PC Restaurant Admin */
	/*
	 * public static String rf_api_Url_en =
	 * "http://192.168.1.12/RF_interview/admin_api/"; public static String
	 * rf_api_Url_ro = "http://192.168.1.12/RF_interview/admin_api_ro/"; public
	 * static String rf_api_Url_lang =rf_api_Url_en;
	 */

	/* OO Api */

	public static String rf_init_api_path = "init";
	public static String rf_login_api_path = "userlogin";
	public static String rf_registration_api_path = "userregistration";
	public static String rf_Geosearch_api_path = "GeoSearch";
	public static String rf_searchrestaurant_api_path = "searchrestaurant";
	public static String rf_forgetpassword_api_path = "UserForgetPassword";
	public static String rf_RestaurantMenu_api_path = "RestaurantMenu";
	public static String rf_GetSavedAddressList_api_path = "GetSavedAddressList";
	public static String rf_CheckoutStep1_api_path = "CheckoutStep1";
	public static String rf_CheckoutStep4_api_path = "CheckoutStep4";
	public static String rf_StepChooseTime_api_path = "StepChooseTime";
	public static String rf_SendVer_api_path = "SendVer";
	public static String rf_applyOffer_api_path = "applyOffer";
	public static String rf_stepCheckVer_api_path = "stepCheckVer";
	public static String rf_CheckoutStep2_api_path = "CheckoutStep2";
	public static String rf_CheckoutStep3_api_path = "CheckoutStep3";
	public static String rf_GetCurrentUserDetails_api_path = "GetCurrentUserDetails";

	/* TG Api */
	public static String rf_TableGrabOrder_api_path = "TableGrabOrder";
	public static String rf_InsertFavRestaurants_api_path = "InsertFavRestaurants";
	public static String rf_DeleteFavRestaurants_api_path = "deleteFavRestaurants";
	public static String rf_getFavRestaurants_api_path = "getFavRestaurants";
	public static String rf_GetMyBooking_api_path = "GetMyBooking";
	public static String rf_getUserAccountInfo_api_path = "getUserAccountInfo";
	public static String rf_userlogout_api_path = "userlogout";
	public static String rf_InsertReview_api_path = "InsertReview";
	public static String rf_manage_restaurant_gallery_api_path = "manage_restaurant_gallery";
	public static String rf_update_profile_api_path = "update_profile";
	public static String rf_get_restaurant_review_api_path = "GetRestaurantReview";
	public static String rf_AboutRestaurant_api_path = "AboutRestaurant";
	public static String rf_GetValidOrderDateTime_api_path = "GetValidOrderDateTime";
	public static String rf_generate_invoice_api_path = "generate_invoice";
	public static String rf_cancel_order_api_path = "cancel_order";
	public static String rf_update_user_password = "update_user_password";
	public static String rf_GetMyLoyaltyPointsDetail = "GetMyLoyaltyPointsDetail";
	public static String rf_GetMyBookedRestCart = "GetMyBookedRestCart";

	// regsitrationsteps glbal declaration

	public static String rf_api_init = "init";
	public static String rf_api_registrationstep1 = "restaurantregistrationstep1";
	public static String rf_api_registrationstep2 = "restaurantregistrationstep2";
	public static String rf_api_registrationstep3 = "restaurantregistrationstep3";
	public static String rf_api_registrationstep4 = "restaurantregistrationstep4";
	public static String rf_api_registrationstep5 = "restaurantregistrationstep5";

	// public static String image_url =
	// "http://angelitservices.com/images_app/";

	// public static String image_url = "http://wjbty.com";
	public static String minimum = "Minimum";
	public static String order = "Order";
	public static String blank = " ";
	public static String day_before = "Day Before";
	public static String order_sr = "Minimum Order lei";
	public static String Riquired = "is Required";
	public static String Mobile_no_code = "+996";
	public static String Region_Parent_id = "1";
	public static String Categories_sr = "lei";

	// public static String country_code = "91";
	public static String country_code;
	public static String sms_username = "SattvaSoft";
	public static String sms_password = "sattvasoft2014";

	public static HashMap<String, String> Hasmap_restaurantcategory = new HashMap<String, String>();

	/** By pankaj */
	// public static String user_id;
	public static String sessid;
	public static String Default_Restaurant_Category_String = "what do you want to eat";
	public static String Default_District_String = "";
	public static HashMap<String, String> hashmap_texts;
	public static HashMap<String, String> hashmap_items_object;
	public static HashMap<String, String> hashmap_cities;
	public static HashMap<String, String> hashmap_restaurantcategory;
	public static HashMap<String, String> hashmap_deliveryareas;
	public static HashMap<String, String> hashmap_district;
	public static HashMap hashmap_region;

	public static ArrayList<HashMap<String, String>> hotel_listData;
	public static ArrayList<HashMap<String, String>> fav_hotel_listData;
	public static ArrayList<HashMap<String, String>> hashmap_parent_arraylist;
	public static ArrayList<HashMap<String, String>> arraylist_item_child_parent_value;
	public static ArrayList<HashMap<String, String>> hotel_MenuData;

	public static String[] City_Array;
	public static String[] District_Array;
	public static String[] Restaurant_Category_Array;
	public static String[] Delivery_area_Array;
	public static String[] Minimum_Time_Order_Array = new String[8];
	public static String[] Region_Array;

	public static ArrayList<HashMap<String, String>> array_listhashmap_food_categories_items;
	public static ArrayList<ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>>> ArrayList_Final_Item = new ArrayList<ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>>>();

	/*********** check delivery pickup ***********/
	public static String str_data_pickup_avail = null;
	public static String str_data_delivery_avail = null;

	/* CheckouT String */

	/************* Shipping inf ********/

	public static String Str_Houseno = null;
	public static String Str_Street = null;
	public static String Str_CityName = null;
	public static String Str_CityId = null;
	public static String Str_DistrictName = null;
	public static String Str_previous_DistrictName = null;
	public static String Str_DistrictId = null;
	public static String Str_previous_DistrictId = null;
	public static String Str_Zipcode = null;
	public static String Str_Shipping_inf = null;
	public static String Str_Latitude = null;
	public static String Str_Longitude = null;
	public static String Str_Comment = null;
	public static String Str_Delivery_schedule_On = null;
	public static String Str_addr_number = null;
	/************* Used saved add ********/
	public static String Str_Usedsaved_add = null;
	public static String Str_Address = null;

	/**** Pick my self ****/
	public static String Str_Mobile_No = null;

	public static double latitude;
	public static double longitude;
	public static double latitude_del;
	public static double longitude_del;
	public static String zoom;
	public static String postal_code;
	public static String route;

	/************* Contact_details ********/
	public static String Str_FirstName = null;
	public static String Str_LastName = null;
	public static String Str_email = null;
	public static String Str_Mobile = null;
	public static String Str_Zip_code = null;

	/** For Cart */
	public static JSONArray cart = new JSONArray();
	public static ArrayList<HashMap<String, String>> parent_child_value = new ArrayList<HashMap<String, String>>();
	public static int cart_total;
	public static int cart_quantity;

	/** For Search Restaurant Back Intent */
	public static String FR_City_Name;
	public static String FR_City_id;
	public static int FR_City_Position;

	public static String FR_Region_Name;
	public static String FR_Region_id;
	public static int FR_Region_Position;

	public static String FR_Delivery_id;
	public static int FR_Delivery_Position;

	/** For Intent Food categories list */
	public static String cat_str_categories_name;
	public static String cat_strarray_categories_id;

	/** For Order now */
	public static String login_user_id;
	public static String activity = "";

	/** Shipping information Date and Time */
	public static JSONArray json_array_datetime;
	public static String shipping_tag_delivery_ok;
	public static String shipping_tag_addr_type;

	/** StepChooseTime */
	public static JSONArray json_array_Mobile_Verify;
	public static JSONObject json_object_Mobile_Verify_Data;
	public static JSONObject json_object_Mobile_cart_details;
	public static JSONArray json_array_Mobile_Verify_cart_cal;
	public static JSONArray json_object_Mobile_Verify_Data_Patyment_Array;

	/** SendVer ******/
	public static JSONObject json_object_Mobile_Verify_Number;
	public static JSONArray json_Data_array_Mobile_Verify_Number;

	/** stepCheckVer ******/
	public static JSONObject json_Object_stepCheckVer_Mobile_no;
	public static JSONArray json_Array_stepCheckVer_Mobile_no;

	/** applayOfferApi ******/
	public static JSONObject json_applyOffer_DATA_Object;
	public static String applyOffer_DATA_Object_str_final_total = null;

	public static JSONObject json_applyOffer_Success_Object;
	public static JSONObject json_applyOffer_Other_Details_Object;
	public static JSONArray json_applyOffer_Offer_Applied_Array;

	public static JSONObject json_applyOffer_Offer_Object;
	public static String applyOffer_Offer_Object_str_uid = null;

	public static JSONObject json_applyOffer_Order_Object;
	public static JSONArray json_applyOffer_Order_Object_discount_Array;
	public static JSONArray json_applyOffer_Order_Object_extras_Array;
	public static JSONObject json_applyOffer_Order_Object_delivery_Object;
	public static JSONArray json_applyOffer_Order_Object_discounts_Array;

	public static JSONObject json_applyOffer_Items_Object;
	public static JSONArray json_applyOffer_Items_Object_Cart_cal_Array;

	public static JSONArray json_applyOffer_Data_Array;

	/** CheckoutStep2API ******/

	public static JSONObject json_CheckoutStep2API_Success_Object;

	public static JSONObject json_CheckoutStep2API_Success_Object_Order_Object;
	public static JSONArray json_CheckoutStep2API_Order_Object_Items_Array;
	public static String json_CheckoutStep2API_Order_Object_str_total = null;
	public static JSONObject json_CheckoutStep2API_Order_Object_Shipment_Object;

	public static JSONArray json_CheckoutStep2API_Data_Array;

	public static String Payment_Method_Id = null;

	/** CheckoutStep3 ******/

	public static JSONObject json_CheckoutStep3_Success_Object;

	public static JSONObject json_CheckoutStep3_Success_Object_Order_Object;
	public static JSONArray json_CheckoutStep3_Order_Object_Items_Array;
	public static String json_CheckoutStep3_Order_Object_str_total = null;
	public static JSONObject json_CheckoutStep3_Order_Object_Shipment_Object;

	public static JSONArray json_CheckoutStep3_Data_Array;
	/** Contact Detail 2 Date and time */
	public static String[] Date;
	public static String[] Time_From;
	public static String[] Time_To;
	public static String str_Time_From, str_Time_To, str_Date;
	public static String str_User_FirstName = null;
	public static String str_User_LastName = null;
	public static String str_User_Email = null;
	public static String str_User_ContactNumber = null;

	/** Global Search Restaurant List */
	public static int previous_restaurant_selected_id;

	public static String hotel_iconurl;
	public static String hotel_id;
	public static String hotel_name;

	/******** Hotel address string *************/
	public static String str_data_hotel_address = null;
	public static String str_User_saved_address = null;

	public static int Selected_position = -1;

	// public static JSONObject Shipping_Request_Main = new JSONObject();
	public static JSONObject Shipping_Request_Child = new JSONObject();

	/***************/
	public static String Selected_Offer_id = null;

	/******* CART AFTER APPLY OFFER ******/
	public static JSONArray cart_apply_offer = new JSONArray();
	public static String str_final_total_Apply_Offer = null;
	public static String str_delivery_charge_Apply_Offer = null;

	public static boolean BOOL_Current_Language, FR_region_flag = false;

	// public static String lang = "en";
	public static boolean flag_arabic_receipt = false;

	/* TG */
	/* fetching current location from google */
	public static double user_latitide, user_longitude;

	public static String number_of_persons = "1", str_booking_date,
			str_booking_time, str_booking_number_of_people, tg_order_id;

	// public static String multiline_space = "\u00A0";
	// public static String multiline_space = "%-40s%s%n";
	public static String multiline_space = "\t\t\t";

	public static String my_booking = "My Booking", my_profile = "My Profile",
			my_favourites = "My Favourites",
			about_restaurant = "About Restaurant", logout = "Logout", user_fav,
			indine_available, my_networking = "Networking", booking_time_limit;

	public static boolean abt_rest_flag = false;

	public static ArrayList<HashMap<String, String>> booking_listData_tg_order;
	public static ArrayList<HashMap<String, String>> booking_listData_oo_order;
	public static ArrayList<HashMap<String, String>> reviews_listData;

	public static double hotel_map_lat;
	public static double hotel_map_long;

	public static JSONArray Country_code_array;

	/* Loyalty pts. details variables. */
	public static int min_lp_to_redeem, max_lp_to_redeem, lp_equals_to_lei,
			lp_to_tg_customer;

	public static String raw_file_details_one = "I booked table at the restaurant ",
			raw_file_details_two = " for the ",
			raw_file_details_three = ".The ",
			raw_file_details_four = " points had been rewarded to you on your Table Grab Booking. ",
			raw_file_details_five = " at ", order_type = "";

	// public static String post_review_activity="";
	public static JSONArray init_cities;

	public static HashMap<String, String> init_hashmap_city;
	public static ArrayList<String> init_cityarraylist;
	public static ArrayList<HashMap<String, String>> init_hash_cityarraylist;
	public static String init_str_region_id, init_str_city_id,
			init_str_city_name;

	/* Admin declaration for registration steps */

	public static String admin_sessid = "jksdgfkjlsdgf";
	public static String comment, restaurant_id = null, rest_uid = null,
			admin_uid = null;


	// public static String img_pre_path="http://192.168.1.17/images/";

	// public static String uid=null;
	public static String str_selected_city_name;

	public static String selected_categories = null;
	public static String euro = "lei";
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

	public static String[] array_creditcardStrings = { "Credit Card",
			"Master Cards", "Visa" };

}
