package com.superadmin.model;

import org.json.JSONException;
import org.json.JSONObject;

public class packages {

	String package_id, package_name, package_description,
			global_booking_charge, price, booking_limit, online_order_limit;

	public packages(String package_id, String package_name,
			String package_description, String global_booking_charge,
			String price, String booking_limit, String online_order_limit) {
		super();
		this.package_id = package_id;
		this.package_name = package_name;
		this.package_description = package_description;
		this.global_booking_charge = global_booking_charge;
		this.price = price;
		this.booking_limit = booking_limit;
		this.online_order_limit = online_order_limit;
	}

	public packages(JSONObject obj_json) {

		// TODO Auto-generated constructor stub
		super();
		try {
			this.package_id = obj_json.getString("package_id");
			this.package_name = obj_json.getString("package_name");
			this.package_description = obj_json
					.getString("package_description");
			this.global_booking_charge = obj_json
					.getString("global_booking_charge");
			this.price = obj_json.getString("price");
			this.booking_limit = obj_json.getString("booking_limit");
			this.online_order_limit = obj_json.getString("online_order_limit");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getPackage_description() {
		return package_description;
	}

	public void setPackage_description(String package_description) {
		this.package_description = package_description;
	}

	public String getGlobal_booking_charge() {
		return global_booking_charge;
	}

	public void setGlobal_booking_charge(String global_booking_charge) {
		this.global_booking_charge = global_booking_charge;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBooking_limit() {
		return booking_limit;
	}

	public void setBooking_limit(String booking_limit) {
		this.booking_limit = booking_limit;
	}

	public String getOnline_order_limit() {
		return online_order_limit;
	}

	public void setOnline_order_limit(String online_order_limit) {
		this.online_order_limit = online_order_limit;
	}

}
