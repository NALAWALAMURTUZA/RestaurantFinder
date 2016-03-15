package com.example.restaurantadmin;

import org.json.JSONArray;
import org.json.JSONException;


public class AllBookingActivityFilterClass {

	public static JSONArray filter_all_booking(String date, String search,
			String booking_type, JSONArray json) throws JSONException {
		JSONArray json_temp = new JSONArray();

		for (int i = 0; i < json.length(); i++) {

			if (!booking_type.equalsIgnoreCase("0")) {
				if (date.equalsIgnoreCase(json.getJSONObject(i).getString(
						"booking_date").substring(0,10))
						&& booking_type.equalsIgnoreCase(json.getJSONObject(i)
								.getString("type"))) {

					try {
						if (search != null) {
							if (json.getJSONObject(i).getString("first_name")
									.toLowerCase()
									.contains(search.toLowerCase())
									|| json.getJSONObject(i)
											.getString("last_name")
											.toLowerCase()
											.contains(search.toLowerCase())) {
								json_temp.put(json.getJSONObject(i));
							}
						}
						else
						{
							json_temp.put(json.getJSONObject(i));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else {
				try {

					if (search != null) {
						if (json.getJSONObject(i).getString("first_name")
								.toLowerCase()
								.contains(search.toLowerCase())
								|| json.getJSONObject(i)
										.getString("last_name")
										.toLowerCase()
										.contains(search.toLowerCase())) {
							json_temp.put(json.getJSONObject(i));
						}
					}
					else
					{
						json_temp.put(json.getJSONObject(i));
					}
					// }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		System.out.println("filterclassjson" + json_temp);
		return json_temp;
	}
}
