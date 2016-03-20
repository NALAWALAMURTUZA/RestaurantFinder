package com.rf_user.async_common_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rf_user.activity.FindRestaurant;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;

public class UserLogout extends AsyncTask<Void, Void, Void> {

	public static HttpConnection http = new HttpConnection();

	public static ConnectionDetector cd;
	public static String TAG_SUCCESS = "success";

	// public Activity activity=UserLogout.this;

	JSONObject json;
	ProgressDialog dialog;
	// UserLogout user = new UserLogout();
	Intent in;

	public Activity context;

	public UserLogout(Activity context) {
		this.context = context;
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// dialog = new ProgressDialog(context);
		// dialog.setIndeterminate(false);
		// dialog.setCancelable(true);
		// dialog.show();

	}

	/**
	 * Getting user details in background thread
	 * */

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub

		// updating UI from Background Thread
		// runOnUiThread(new Runnable() {
		// public void run() {

		try {
			JSONObject LogoutForm = new JSONObject();
			if(SharedPreference.getuser_id(
					context)!="")
			{
			if (SharedPreference
					.getuser_id(context).length() != 0) {
				LogoutForm.put("user_id", SharedPreference
						.getuser_id(context));
			}
			}else {
				LogoutForm.put("user_id", "");
			}

			System.out.println("user_id" + LogoutForm);

			LogoutForm.put("sessid", SharedPreference.getsessid(context));
			System.out.println("session_id" + LogoutForm);
			// *************

			try {

				String responseText = http.connection(context,
						 Global_variable.rf_userlogout_api_path, LogoutForm);

				System.out.println("after_connection.." + responseText);

				json = new JSONObject(responseText);
				System.out.println("responseText" + json);
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		// }
		//
		// });

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/

	@Override
	protected void onPostExecute(Void result) {

		// if (dialog.isShowing()) {
		// dialog.dismiss();
		// }
		// json success tag
		String success1;

		try {
			success1 = json.getString(TAG_SUCCESS);
			System.out.println("tag!!!!!!!!!" + success1);

			JSONObject data = json.getJSONObject("data");

			System.out.println("!!!!!!!!!!!data.." + data);

			// String Data_Success = data.getString(TAG_SUCCESS);
			// System.out.println("Data tag" + Data_Success);
			// ******** data succsess

			if (success1.equals("true")) {

				System.out.println("1" + data);

				if (data.length() != 0) {

					System.out.println("2.." + data);

					String success = data.getString("success");

					System.out.println("3.." + data + ".." + success);

					if (success.equals("true")) {

						System.out.println("4.." + data);

						String message = data.getString("message");

						System.out.println("5.." + data);

						Toast.makeText(context, message, Toast.LENGTH_LONG)
								.show();

						System.out.println("6.." + data);

						in = new Intent(context, FindRestaurant.class);

						System.out.println("7.." + data);

						Global_variable.login_user_id = "";

						/* Storing user_id in SharedPref */
						SharedPreference.setuser_id(context,"");

						System.out.println("!!!!!!!!!!!!!!!!!!!user_logout"
								+ SharedPreference.getuser_id(context));

						System.out.println("8.." + data);

					//	Global_variable.post_review_activity="";
						
						System.out.println("!!!!!!!!!!!!!!!!shikha...."
								+ SharedPreference
								.getuser_id(context));
						context.startActivity(in);

					}

				}

			}

			// **** invalid output
			else {
				if (success1.equalsIgnoreCase("false")) {
					JSONObject Data_Error = data.getJSONObject("errors");

					String success = data.getString("success");

					System.out.println("Data_Error" + Data_Error);

					if (Data_Error.has("user_id")) {
						JSONArray Array_user_id = Data_Error
								.getJSONArray("user_id");
						System.out.println("Array_email" + Array_user_id);
						String Str_user_id = Array_user_id.getString(0);
						System.out.println("Str_email" + Str_user_id);
						if (Str_user_id != null) {
							Toast.makeText(context, Str_user_id,
									Toast.LENGTH_LONG).show();
						}
					}

					else if (Data_Error.has("sessid")) {
						JSONArray Array_sessid = Data_Error
								.getJSONArray("sessid");
						System.out.println("Array_email" + Array_sessid);
						String Str_sessid = Array_sessid.getString(0);
						System.out.println("Str_sessid" + Str_sessid);
						if (Str_sessid != null) {
							Toast.makeText(context, Str_sessid,
									Toast.LENGTH_LONG).show();
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

	}

	// }

}
