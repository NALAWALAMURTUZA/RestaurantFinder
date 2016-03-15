package com.example.restaurantadmin;

import org.json.JSONArray;
import org.json.JSONException;

public class OnlineTable_ActivityFilterClass {

	public static JSONArray filter_all(String date, String service,
			String order_status, JSONArray json) throws JSONException {
		JSONArray json_temp = new JSONArray();

		// date = 2015-04-17 splite
		// service process 0=delivery,1=pickup,3=all
		// order_status Confirmed,Waiting,Cancel

		for (int i = 0; i < json.length(); i++) {
			System.out.println("spliteddateinfilter0"
					+ date.equalsIgnoreCase(json.getJSONObject(i)
							.getJSONObject("order")
							.getString("order_registered").substring(0, 7)));

			// if (!service.equalsIgnoreCase("3")) {
			if (!order_status.equalsIgnoreCase("all")) {
				
				
				if(order_status.equalsIgnoreCase("Cancel"))
				{
					if (date.equalsIgnoreCase(json.getJSONObject(i)
							.getJSONObject("order").getString("order_registered")
							.substring(0, 7))
							&& order_status.equalsIgnoreCase(json.getJSONObject(i)
									.getJSONObject("order")
									.getString("order_order_status")) || "CancelUser".equalsIgnoreCase(json.getJSONObject(i)
											.getJSONObject("order")
											.getString("order_order_status"))) {
						System.out.println("spliteddateinfilter1"
								+ date.equalsIgnoreCase(json.getJSONObject(i)
										.getJSONObject("order")
										.getString("order_registered")
										.substring(0, 7)));

						if (!service.equalsIgnoreCase("3")) {
							if (service.equalsIgnoreCase(json.getJSONObject(i)
									.getJSONObject("order")
									.getString("order_delivery_ok"))) {
								json_temp.put(json.getJSONObject(i));

							}
						} else {
							json_temp.put(json.getJSONObject(i));
						}

					}
					System.out.println("jsontempnot3bar" + json_temp);
				}
				else
				{
					if (date.equalsIgnoreCase(json.getJSONObject(i)
							.getJSONObject("order").getString("order_registered")
							.substring(0, 7))
							&& order_status.equalsIgnoreCase(json.getJSONObject(i)
									.getJSONObject("order")
									.getString("order_order_status"))) {
						System.out.println("spliteddateinfilter1"
								+ date.equalsIgnoreCase(json.getJSONObject(i)
										.getJSONObject("order")
										.getString("order_registered")
										.substring(0, 7)));

						if (!service.equalsIgnoreCase("3")) {
							if (service.equalsIgnoreCase(json.getJSONObject(i)
									.getJSONObject("order")
									.getString("order_delivery_ok"))) {
								json_temp.put(json.getJSONObject(i));

							}
						} else {
							json_temp.put(json.getJSONObject(i));
						}

					}
					System.out.println("jsontempnot3bar" + json_temp);
					
				}
				
			}
			// else if (date.equalsIgnoreCase(json.getJSONObject(i)
			// .getJSONObject("order").getString("order_registered")
			// .substring(0, 10))) {
			// if (service.equalsIgnoreCase("0")) {
			// json_temp.put(json.getJSONObject(i));
			// } else if (service.equalsIgnoreCase("1")) {
			// json_temp.put(json.getJSONObject(i));
			//
			// }
			// }

			// } else {
			else {
				if (date.equalsIgnoreCase(json.getJSONObject(i)
						.getJSONObject("order").getString("order_registered")
						.substring(0, 7))) {
					System.out.println("spliteddateinfilter1"
							+ date.equalsIgnoreCase(json.getJSONObject(i)
									.getJSONObject("order")
									.getString("order_registered")
									.substring(0, 7)));

					if (!service.equalsIgnoreCase("3")) {
						if (service.equalsIgnoreCase(json.getJSONObject(i)
								.getJSONObject("order")
								.getString("order_delivery_ok"))) {
							json_temp.put(json.getJSONObject(i));

						}
					} else {
						json_temp.put(json.getJSONObject(i));
					}

				}
				// else if (date.equalsIgnoreCase(json.getJSONObject(i)
				// .getJSONObject("order").getString("order_registered")
				// .substring(0, 10))) {
				// if (service.equalsIgnoreCase("0")) {
				// json_temp.put(json.getJSONObject(i));
				// } else if (service.equalsIgnoreCase("1")) {
				// json_temp.put(json.getJSONObject(i));
				//
				// } else {
				// json_temp.put(json.getJSONObject(i));
				// }
				// }
				System.out.println("jsontemp=3bar" + json_temp);
			}
		}
		// } else {
		// if (date.equalsIgnoreCase(json.getJSONObject(i)
		// .getString("order_registered").substring(0, 10))) {
		// System.out.println("spliteddateinfilter"
		// + (json.getJSONObject(i).getString(
		// "order_registered").substring(0, 10)));
		// if (service.equalsIgnoreCase("0")) {
		// json_temp.put(json.getJSONObject(i));
		// } else if (service.equalsIgnoreCase("1")) {
		// json_temp.put(json.getJSONObject(i));
		//
		// } else if (service.equalsIgnoreCase("3")) {
		// json_temp.put(json.getJSONObject(i));
		// }
		//
		// // json_temp.put(json.getJSONObject(i));
		// }
		//
		// }

		// }
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
