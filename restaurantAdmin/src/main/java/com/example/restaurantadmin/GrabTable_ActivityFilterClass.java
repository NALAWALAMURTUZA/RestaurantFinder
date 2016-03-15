package com.example.restaurantadmin;

import org.json.JSONArray;
import org.json.JSONException;

import com.restaurantadmin.Global.Global_variable;

public class GrabTable_ActivityFilterClass {

	public static JSONArray filter_all(String date, String shift, String order_status,
			JSONArray json) throws JSONException {
		JSONArray json_temp = new JSONArray();

		// date = 2015-04-17
		// shift lunch = 1, dinner = 2, all = 0
		// order_status confirm = 2, pending = 1, cancel = 6, Cancel by user =8, all = 0
		// this value is from global file as per restaurant login
		// String dinner = "22";
		// String lunch = "15";
		for (int i = 0; i < json.length(); i++) {

			if (!order_status.equalsIgnoreCase("0")) {
				
				if(order_status.equalsIgnoreCase("6"))
				{
					if (date.equalsIgnoreCase(json.getJSONObject(i).getString(
							"booking_date").substring(0,7))
							&& order_status.equalsIgnoreCase(json.getJSONObject(i)
									.getString("booking_status")) || "8".equalsIgnoreCase(json.getJSONObject(i)
											.getString("booking_status")) ) {

						String[] a = json.getJSONObject(i)
								.getString("booking_time").split(":");
						System.out.println("!!!pankaj" + a[0]);
						if (shift.equalsIgnoreCase("1")) {
							if (Integer.parseInt(a[0]) < Integer
									.parseInt(Global_variable.launch)) {
								json_temp.put(json.getJSONObject(i));
							}
						} else if (shift.equalsIgnoreCase("2")) {
							if (Integer.parseInt(a[0]) > Integer
									.parseInt(Global_variable.launch)
									&& Integer.parseInt(a[0]) <= Integer
											.parseInt(Global_variable.dinner)) {
								json_temp.put(json.getJSONObject(i));
							}

						} else if (shift.equalsIgnoreCase("0")) {
							json_temp.put(json.getJSONObject(i));
						}

					}
				}
				else
				{
					if (date.equalsIgnoreCase(json.getJSONObject(i).getString(
							"booking_date").substring(0,7))
							&& order_status.equalsIgnoreCase(json.getJSONObject(i)
									.getString("booking_status"))) {

						String[] a = json.getJSONObject(i)
								.getString("booking_time").split(":");
						System.out.println("!!!pankaj" + a[0]);
						if (shift.equalsIgnoreCase("1")) {
							if (Integer.parseInt(a[0]) < Integer
									.parseInt(Global_variable.launch)) {
								json_temp.put(json.getJSONObject(i));
							}
						} else if (shift.equalsIgnoreCase("2")) {
							if (Integer.parseInt(a[0]) > Integer
									.parseInt(Global_variable.launch)
									&& Integer.parseInt(a[0]) <= Integer
											.parseInt(Global_variable.dinner)) {
								json_temp.put(json.getJSONObject(i));
							}

						} else if (shift.equalsIgnoreCase("0")) {
							json_temp.put(json.getJSONObject(i));
						}

					}
				}
				
			} else {
				if (date.equalsIgnoreCase(json.getJSONObject(i).getString(
						"booking_date").substring(0,7))) {

					String[] a = json.getJSONObject(i)
							.getString("booking_time").split(":");
					System.out.println("!!!pankaj" + a[0]);
					if (shift.equalsIgnoreCase("1")) {
						if (Integer.parseInt(a[0]) < Integer
								.parseInt(Global_variable.launch)) {
							json_temp.put(json.getJSONObject(i));
						}
					} else if (shift.equalsIgnoreCase("2")) {
						if (Integer.parseInt(a[0]) > Integer
								.parseInt(Global_variable.launch)
								&& Integer.parseInt(a[0]) <= Integer
										.parseInt(Global_variable.dinner)) {
							json_temp.put(json.getJSONObject(i));
						}

					} else if (shift.equalsIgnoreCase("0")) {
						json_temp.put(json.getJSONObject(i));
					}

					// json_temp.put(json.getJSONObject(i));
				}

			}

		}
		return json_temp;
	}

	// **************************************************************************************
	// currentjson is online_table_grabbing json array never modify this.

	// class_function is object of your function's java class.

	// date = 2015-04-17
	// shift lunch = 1, dinner = 2, all = 0
	// order_status confirm = 2, pending = 1, cancel = 6, all = 0
	// String date,String shift,String order_status, JSONArray json
	// 0 = ALL order_status

	// System.out.println(class_function.filter_all("2015-04-13", "0", "0",
	// currentJson));

}
