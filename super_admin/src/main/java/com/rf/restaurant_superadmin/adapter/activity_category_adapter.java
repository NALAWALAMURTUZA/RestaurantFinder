package com.rf.restaurant_superadmin.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_superadmin.R;
import com.rf.restaurant_superadmin.activity_category;
import com.rf.restaurant_superadmin.httpconnection.HttpConnection;
import com.superadmin.global.Global_function;
import com.superadmin.global.Global_variable;

public class activity_category_adapter extends BaseAdapter {
	private Activity _activity;
	private JSONArray array_restaurantcategory;
	private static LayoutInflater inflater = null;

	// public static ImageView img_edit,img_update;
	// public static EditText ed_name;
	// public static TextView txv_name;
	public static String str_txv_name, str_txv_category_id, str_ed_name;

	HttpConnection http = new HttpConnection();

	public activity_category_adapter(Activity activity,
			JSONArray _array_invoice_data_list) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this.array_restaurantcategory = _array_invoice_data_list;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		System.out.println("!!!!generate_pdf"
				+ Global_function.generate_pdf(this.array_restaurantcategory));
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this.array_restaurantcategory.length();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		final TextView txv_name;
		final TextView txv_id;
		final EditText ed_name;
		ImageView img_delete;
		final Button img_update;
		final ImageView img_edit;

		try {

			str_txv_name = array_restaurantcategory.getJSONObject(position)
					.getString("name_en");
			str_txv_category_id = array_restaurantcategory.getJSONObject(
					position).getString("id");

			System.out.println("!!!!pankaj_invoice_value_restaurant_id"
					+ str_txv_name);

			System.out.println("!!!!pankaj_invoice"
					+ Global_variable.click_flag_tg_oo);

			vi = inflater.inflate(R.layout.add_category_rawfile, null);

			txv_name = (TextView) vi.findViewById(R.id.txv_name);
			txv_id = (TextView) vi.findViewById(R.id.txv_id);
			img_edit = (ImageView) vi.findViewById(R.id.img_edit);
			ed_name = (EditText) vi.findViewById(R.id.ed_name);
			img_update = (Button) vi.findViewById(R.id.img_update);

			System.out.println("!!!!pankaj_invoice_tg_temp_order_count"
					+ txv_name);

			txv_name.setText(str_txv_name);
			ed_name.setText(str_txv_name);
			txv_id.setText(str_txv_category_id);

			img_edit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ed_name.setVisibility(View.VISIBLE);
					txv_name.setVisibility(View.GONE);
					img_update.setVisibility(View.VISIBLE);
					img_edit.setVisibility(View.GONE);

					// new update_restaurant().execute();
				}
			});

			img_update.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					str_ed_name = ed_name.getText().toString();
					str_txv_category_id = txv_id.getText().toString();
					
					System.out.println("!!!!pankaj" +str_ed_name+"    "+str_txv_category_id );
					
					new update_restaurant().execute();
					
					ed_name.setVisibility(View.GONE);
					txv_name.setVisibility(View.VISIBLE);
					img_update.setVisibility(View.GONE);
					img_edit.setVisibility(View.VISIBLE);
				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vi;

	}

	public synchronized void refresAdapter(JSONArray array) {
		array_restaurantcategory = new JSONArray();
		System.out.println("gyurefresh ma");
		array_restaurantcategory = Global_variable.array_restaurantcategory;
		System.out.println("reloadadapt" + array_restaurantcategory);

		// GrabTable_Activity.async_getrest_order A=ACT.new
		// async_getrest_order();
		// A.execute();
		JSONArray array1 = null;
		array1 = new JSONArray();
		array1 = Global_variable.array_restaurantcategory;
		if (Global_variable.array_restaurantcategory.length() != 0) {

			activity_category.category_adapter = new activity_category_adapter(
					_activity, Global_variable.array_restaurantcategory);
			activity_category.lv_add_category
					.setAdapter(activity_category.category_adapter);
		} else {

			// Toast.makeText(OnlineTable_Activity.this,
			// "No Data Found", Toast.LENGTH_LONG)
			// .show();
		}

		System.out.println("1111lenthorderfoodafter"
				+ Global_variable.array_restaurantcategory);

		Global_variable.array_restaurantcategory = array1;

		System.out.println("1111lenthorderfoodafter_add"
				+ Global_variable.array_restaurantcategory);
	}

	public class update_restaurant extends AsyncTask<Void, Void, Void> {
		JSONObject json;
		ProgressDialog p;

		protected void onPreExecute() {
			super.onPreExecute();
			// / Showing progress dialog
			p = new ProgressDialog(_activity);
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_mparent = new JSONObject();
			JSONObject obj_parent = new JSONObject();

			JSONObject obj_child = new JSONObject();
			try {

				obj_child.put("id", str_txv_category_id);

				System.out.println("idddddddddddd.." + obj_child);

				obj_child.put("name_en", str_ed_name);

				System.out.println("nameeeeeee..." + obj_child);

				obj_mparent.put("update", obj_child);

				obj_parent.put("operation", "update");

				obj_mparent.put("restaurantcategory", obj_parent);

				obj_mparent.put("sessid", Global_variable.sessid);

				// System.out.print("session id..."+obj_parent);
				System.out.println("request of insert data" + obj_mparent);

				try {

					// *************
					// for request
					String responseText = http.connection(
							Global_variable.manage_restaurant_category,
							obj_mparent);

					try {

						System.out.println("after_connection.." + responseText);

						json = new JSONObject(responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1));

						System.out.println("responseText" + json);

						// data = json.getJSONObject("data");
						//
						// System.out.print("d.." + data);

					} catch (NullPointerException ex) {
						ex.printStackTrace();
					}

				} catch (NullPointerException np) {

				}

				return null;

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			try {
				if (json.getString("success").equalsIgnoreCase("true")) {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("response of data" + json);
					}
					JSONObject Data = json.getJSONObject("data");
					Global_variable.array_restaurantcategory = json
							.getJSONObject("data").getJSONArray(
									"restaurantcategory");

					System.out.println("!!!!restaurant_list"
							+ Global_variable.array_restaurantcategory);
					Global_variable.sessid = json.getString("sessid");
					System.out.println("11111datalogin" + Data);
					refresAdapter(Global_variable.array_restaurantcategory);
					//
					System.out
							.println("!!!!pankaj_sakariya_click_flag_home_screen"
									+ Global_variable.click_flag_home_screen);
					// Global_variable.click_flag_home_screen =
					// "manage_restaurant";
					System.out
							.println("!!!!pankaj_sakariya_click_flag_home_screen_detail"
									+ Global_variable.click_flag_home_screen);

					// Intent i = new Intent(_activity,
					// activity_home.class);
					// _activity.startActivity(i);

					// if(ed_name.getVisibility() == View.VISIBLE)
					// {
					// ed_name.setVisibility(View.GONE);
					// txv_name.setVisibility(View.VISIBLE);
					// }
					//
					//
					//
					// if(img_update.getVisibility() == View.VISIBLE){
					// img_update.setVisibility(View.GONE);
					// img_edit.setVisibility(View.VISIBLE);
					// }

					// generatepdf();

				} else {
					if (p.isShowing()) {
						p.dismiss();
						System.out.println("Login_response" + json);

						// ed_name.setVisibility(View.GONE);
						// txv_name.setVisibility(View.VISIBLE);
						// img_update.setVisibility(View.GONE);
						// img_edit.setVisibility(View.VISIBLE);
					}
					JSONObject Errors = json.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("name_en")) {
							Toast.makeText(
									_activity,
									Errors.getJSONArray("name_en").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}

						if (Errors.has("id")) {
							Toast.makeText(
									_activity,
									Errors.getJSONArray("id").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
						
						if (Errors.has("sessid")) {
							Toast.makeText(
									_activity,
									Errors.getJSONArray("sessid").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();

						}
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					System.out.println("Login_response" + json);
				}
				e.printStackTrace();
			} catch (NullPointerException n) {
				// TODO Auto-generated catch block
				if (p.isShowing()) {
					p.dismiss();
					System.out.println("Login_response" + json);
				}
				n.printStackTrace();
			}

		}

	}
}
