package com.rf_user.async_common_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rf_user.activity.MyBooking;
import com.rf_user.activity.Search_Restaurant_List;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class CancelOrder extends AsyncTask<Void, Void, Void>{
	
	public static HttpConnection http = new HttpConnection();
	JSONObject  data;

	public static ConnectionDetector cd;
	public static String TAG_SUCCESS = "success";

	// public Activity activity=UserLogout.this;

	JSONObject json;
	ProgressDialog progressDialog;
	// UserLogout user = new UserLogout();
	Intent in;

	public Activity context;

	public CancelOrder(Activity context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		System.out.println("async_cancel_order  Call");
		// Showing progress dialog

		 progressDialog = new ProgressDialog(context);
		 progressDialog.setCancelable(false);
		 progressDialog.show();
		
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try {

			JSONObject cancel_order = new JSONObject();

			try {
				if (SharedPreference.getuser_id(context) != "") {
					cancel_order.put("user_id", SharedPreference
							.getuser_id(context));
					System.out.println("cancel_order" + cancel_order);
				} else {
					cancel_order.put("user_id", "");
				}

				if (Global_variable.tg_order_id.length() != 0) {
					cancel_order.put("tg_order_id",
							Global_variable.tg_order_id);
					System.out.println("cancel_order" + cancel_order);
				} else {
					cancel_order.put("tg_order_id", "");
				}
				if(Global_variable.order_type.equalsIgnoreCase("TG"))
				{
					
					cancel_order
					.put("booking_status", "8");
				}
				else
				{
					
					cancel_order
					.put("booking_status", "CancelUser");
				}
				
				if(Global_variable.order_type!=null)
				{
					cancel_order
					.put("type", Global_variable.order_type);
				}

				cancel_order
						.put("sessid", SharedPreference
								.getsessid(context));
				System.out.println("cancel_order" + cancel_order);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			// for request
			/* Http Request */

			try {
				String responseText = http.connection(context,
						 Global_variable.rf_cancel_order_api_path,
						cancel_order);

				json = new JSONObject(responseText);
				System.out.println("last_json" + json);
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		try {

			 if (progressDialog.isShowing()) {
			 progressDialog.dismiss();
			 }

			try {

				// json success tag
				String success1 = json.getString(TAG_SUCCESS);
				System.out.println("tag" + success1);

				String str_data = json.getString("data");
				System.out.println("Cancel_order_str_data" + str_data);

				if (success1.equals("true")) {
					if (str_data != "[]") {
						data = json.getJSONObject("data");
						System.out
								.println("data_rsponse_categories_parameter"
										+ data);

						if (data != null) {

							String message = data.getString("message");

							// MyBooking booking = new MyBooking();
							//new async_my_booking_list().execute();

							 Intent in = new Intent(context,
							 Search_Restaurant_List.class);
							 context.startActivity(in);

							// MyBooking my_booking = new MyBooking();
							// my_booking.new async_my_booking_list();
							// notifyDataSetChanged();

						}
					}
				} else {

					JSONObject Data_Error = data.getJSONObject("errors");
					System.out.println("Data_Error" + Data_Error);

					if (Data_Error.has("user_id")) {
						JSONArray Array_user_id = Data_Error
								.getJSONArray("user_id");
						System.out.println("Array_user_id" + Array_user_id);
						String Str_user_id = Array_user_id.getString(0);
						System.out.println("Str_user_id" + Str_user_id);
						if (Str_user_id != null) {
							Toast.makeText(context,
									Str_user_id, Toast.LENGTH_LONG).show();
						}
					} else if (Data_Error.has("tg_order_id")) {
						JSONArray Array_tg_order_id = Data_Error
								.getJSONArray("tg_order_id");
						System.out.println("Array_tg_order_id"
								+ Array_tg_order_id);
						String Str_tg_order_id = Array_tg_order_id
								.getString(0);
						System.out.println("Str_tg_order_id"
								+ Str_tg_order_id);
						if (Str_tg_order_id != null) {
							Toast.makeText(context,
									Str_tg_order_id, Toast.LENGTH_LONG)
									.show();
						}
					} else if (Data_Error.has("sessid")) {
						JSONArray Array_sessid = Data_Error
								.getJSONArray("sessid");
						System.out.println("Array_sessid" + Array_sessid);
						String Str_sessid = Array_sessid.getString(0);
						System.out.println("Str_sessid" + Str_sessid);
						if (Str_sessid != null) {
							Toast.makeText(context,
									Str_sessid, Toast.LENGTH_LONG).show();
						}
					}

				}

			} catch (NullPointerException ex) {
				ex.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
