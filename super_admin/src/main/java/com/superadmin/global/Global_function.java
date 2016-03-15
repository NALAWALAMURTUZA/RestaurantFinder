package com.superadmin.global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Global_function {

	public static int restaurant_count(String status_value, JSONArray json) {
		JSONArray json_temp = new JSONArray();
		for (int i = 0; i < json.length(); i++) {

			try {

				if (status_value.equalsIgnoreCase(json.getJSONObject(i)
						.getString("status"))) {
					json_temp.put(json.getJSONObject(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return json_temp.length();
	}

	public static JSONArray filter_restaurant_by_status(String status_value,
			JSONArray json) {
		JSONArray json_temp = new JSONArray();
		for (int i = 0; i < json.length(); i++) {

			try {

				if (status_value.equalsIgnoreCase(json.getJSONObject(i)
						.getString("status"))) {
					json_temp.put(json.getJSONObject(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json_temp;
	}

	public static JSONArray filter_restaurant_by_string(String str_value,
			JSONArray json) {
		JSONArray json_temp = new JSONArray();
		for (int i = 0; i < json.length(); i++) {

			try {

				if (json.getJSONObject(i).getString("restaurant_name")
						.toLowerCase().contains(str_value.toLowerCase())) {
					json_temp.put(json.getJSONObject(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json_temp;
	}
	
	

	public static JSONObject package_data(String package_id, String price,
			String global_booking_charge,String package_description) {

		JSONObject obj_parent = new JSONObject();
		JSONObject obj_child = new JSONObject();

		try {

			obj_child.put("package_id", package_id);
			obj_child.put("price", price);
			obj_child.put("global_booking_charge", global_booking_charge);
			obj_parent.put("sessid", Global_variable.sessid);
			obj_parent.put("Update_Package", obj_child);
			obj_child.put("package_description", package_description);
//			System.out.println("Activity_manage_packages_price" + obj_parent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj_parent;
	}
	
	public static JSONObject generate_invoice_data(String from_date, String to_date,
			String generate_pdf, String admin_email) {
//		generate_pdf = 0 or 1; 0 = no and 1 = yes

		JSONObject obj_parent = new JSONObject();
		JSONObject obj_child = new JSONObject();

		try {

			obj_child.put("from_date", from_date);
			obj_child.put("to_date", to_date);
			obj_child.put("generate_pdf", generate_pdf);
			obj_child.put("admin_email", admin_email);
			obj_parent.put("sessid", Global_variable.sessid);
			obj_parent.put("Generate_Invoice", obj_child);
//			System.out.println("Activity_manage_packages_price" + obj_parent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj_parent;
	}
	
	public static JSONObject generate_invoice_data_pdf(String from_date, String to_date,
			JSONArray generate_pdf, String admin_email) {
//		generate_pdf = 0 or 1; 0 = no and 1 = yes

		JSONObject obj_parent = new JSONObject();
		JSONObject obj_child = new JSONObject();

		try {

			obj_child.put("from_date", from_date);
			obj_child.put("to_date", to_date);
			obj_child.put("generate_pdf", generate_pdf);
			obj_child.put("admin_email", admin_email);
			obj_child.put("invoice_type", Global_variable.click_flag_tg_oo);
			obj_parent.put("sessid", Global_variable.sessid);
			obj_parent.put("Generate_Invoice", obj_child);
			
//			System.out.println("Activity_manage_packages_price" + obj_parent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj_parent;
	}
	
	public static JSONArray filter_invoice_by_string(String str_value,
			JSONArray json) {
		JSONArray json_temp = new JSONArray();
		for (int i = 0; i < json.length(); i++) {

			try {

				if (json.getJSONObject(i).getString("name_en").toLowerCase().contains(str_value.toLowerCase()) || json.getJSONObject(i).getString("email").toLowerCase().contains(str_value.toLowerCase())) {
					json_temp.put(json.getJSONObject(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json_temp;
	}
	
	public static JSONArray generate_pdf(JSONArray json)
	{
		JSONArray json_temp = new JSONArray();
		for (int position = 0; position < json.length(); position++) {

			String restaurant_id, name_en, booking_charge, package_id, global_booking_charge, email, tg_count, oo_count, tg_rate, oo_rate, status, used_loyalty_points;

			String  tg_pending_order,
			tg_confirmed_order,
			tg_review_order,
			tg_finish_order,
			tg_not_show_with_in_time,
			tg_not_show_after_time,
			tg_cancel_by_admin_order,
			tg_cancel_by_user_order,
			tg_total_order;
	
	String  oo_waiting_order,
			oo_confirmed_order,
			oo_cancel_by_admin_order,
			oo_cancel_by_user_order,
			oo_total_order;
			
			String temp_order_count = null,temp_booking_charge = null,temp_total = null;
			
			try {
				restaurant_id = json.getJSONObject(position).getString("restaurant_id");
			name_en = json.getJSONObject(position).getString("name_en");
			booking_charge = json.getJSONObject(position).getString("booking_charge");
			package_id = json.getJSONObject(position).getString("package_id");
			global_booking_charge = json.getJSONObject(position).getString("global_booking_charge");
			email = json.getJSONObject(position).getString("email");
			tg_count = json.getJSONObject(position).getString("tg_count");
			oo_count = json.getJSONObject(position).getString("oo_count");
			tg_rate = json.getJSONObject(position).getString("tg_rate");
			oo_rate = json.getJSONObject(position).getString("oo_rate");
			status = json.getJSONObject(position).getString("status");
			used_loyalty_points = json.getJSONObject(position).getString("used_loyalty_points");
			
			tg_pending_order = json.getJSONObject(position).getString("tg_pending_order");
			tg_confirmed_order = json.getJSONObject(position).getString("tg_confirmed_order");
			tg_review_order = json.getJSONObject(position).getString("tg_review_order");
			tg_finish_order = json.getJSONObject(position).getString("tg_finish_order");
			tg_not_show_with_in_time = json.getJSONObject(position).getString("tg_not_show_with_in_time");
			tg_not_show_after_time = json.getJSONObject(position).getString("tg_not_show_after_time");
			tg_cancel_by_admin_order = json.getJSONObject(position).getString("tg_cancel_by_admin_order");
			tg_cancel_by_user_order = json.getJSONObject(position).getString("tg_cancel_by_user_order");
			tg_total_order = json.getJSONObject(position).getString("tg_total_order");
			
			oo_waiting_order = json.getJSONObject(position).getString("oo_waiting_order");
			oo_confirmed_order = json.getJSONObject(position).getString("oo_confirmed_order");
			oo_cancel_by_admin_order = json.getJSONObject(position).getString("oo_cancel_by_admin_order");
			oo_cancel_by_user_order = json.getJSONObject(position).getString("oo_cancel_by_user_order");
			oo_total_order = json.getJSONObject(position).getString("oo_total_order");
			

			
			System.out.println("!!!!pankaj_invoice_value_restaurant_id"+restaurant_id);
			System.out.println("!!!!pankaj_invoice_value_name_en"+name_en);
			System.out.println("!!!!pankaj_invoice_value_booking_charge"+booking_charge);
			System.out.println("!!!!pankaj_invoice_value_package_id"+package_id);
			System.out.println("!!!!pankaj_invoice_value_global_booking_charge"+global_booking_charge);
			System.out.println("!!!!pankaj_invoice_value_email"+email);
			System.out.println("!!!!pankaj_invoice_value_tg_count"+tg_count);
			System.out.println("!!!!pankaj_invoice_value_oo_count"+oo_count);
			System.out.println("!!!!pankaj_invoice_value_tg_rate"+tg_rate);
			System.out.println("!!!!pankaj_invoice_value_oo_rate"+oo_rate);
			System.out.println("!!!!pankaj_invoice_value_status"+status);
			
			System.out.println("!!!!pankaj_invoice"+Global_variable.click_flag_tg_oo);
			switch (Global_variable.click_flag_tg_oo) {
			case "tg":
				
				temp_order_count = tg_count;
				temp_total = tg_rate;
				break;

			case "oo":
				
				temp_order_count = oo_count;
				temp_total = oo_rate;

				break;
			
			}
			

			
//			if(Double.parseDouble(booking_charge)>Double.parseDouble(global_booking_charge))
//			{
//				
//				temp_booking_charge = booking_charge;
//			}
//			else if(Double.parseDouble(booking_charge)<Double.parseDouble(global_booking_charge))
//			{
//				
//				temp_booking_charge = global_booking_charge;
//			}
			
			if(Double.parseDouble(booking_charge)!=0.00)
			{
				
				temp_booking_charge = booking_charge;
			}
			else
			{
				
				temp_booking_charge = global_booking_charge;
			}
			
			System.out.println("!!!!pankaj_invoice_tg_temp_order_count"+tg_count);
			System.out.println("!!!!pankaj_invoice_tg_temp_rate"+tg_rate);
			System.out.println("!!!!pankaj_invoice_oo_temp_order_count"+tg_count);
			System.out.println("!!!!pankaj_invoice_oo_temp_rate"+tg_rate);
			System.out.println("!!!!pankaj_invoice_temp_order_count"+temp_order_count);
			System.out.println("!!!!pankaj_invoice_temp_booking_charge"+temp_booking_charge);
			System.out.println("!!!!pankaj_invoice_temp_total"+temp_total);
			
			JSONObject json_object = new JSONObject();
			
				json_object.put("restaurant_id", restaurant_id);
				json_object.put("name_en", name_en);
				json_object.put("tg_count", temp_order_count);
				json_object.put("email", email);
				json_object.put("booking_charge", temp_booking_charge);
				json_object.put("tg_rate", temp_total);
				json_object.put("status", status);
				json_object.put("used_loyalty_points", used_loyalty_points);
				json_object.put("tg_pending_order", tg_pending_order);
				json_object.put("tg_confirmed_order", tg_confirmed_order);
				json_object.put("tg_review_order", tg_review_order);
				json_object.put("tg_finish_order", tg_finish_order);
				json_object.put("tg_not_show_with_in_time", tg_not_show_with_in_time);
				json_object.put("tg_not_show_after_time", tg_not_show_after_time);
				json_object.put("tg_cancel_by_admin_order", tg_cancel_by_admin_order);
				json_object.put("tg_cancel_by_user_order", tg_cancel_by_user_order);
				json_object.put("tg_total_order", tg_total_order);
				json_object.put("number_of_people_valid_order", json.getJSONObject(position).getString("number_of_people_valid_order"));
				
				
				json_object.put("oo_waiting_order", oo_waiting_order);
				json_object.put("oo_confirmed_order", oo_confirmed_order);
				json_object.put("oo_cancel_by_admin_order", oo_cancel_by_admin_order);
				json_object.put("oo_cancel_by_user_order", oo_cancel_by_user_order);
				json_object.put("oo_total_order", oo_total_order);
				json_object.put("percentage", json.getJSONObject(position).getString("percentage"));
				json_object.put("oo_global_total", json.getJSONObject(position).getString("oo_global_total"));
				json_object.put("oo_rate", json.getJSONObject(position).getString("oo_rate"));
				


				
				json_temp.put(json_object);
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println("!!!!pankaj_invoice_json"+json_temp);
		return json_temp;
	}

}
