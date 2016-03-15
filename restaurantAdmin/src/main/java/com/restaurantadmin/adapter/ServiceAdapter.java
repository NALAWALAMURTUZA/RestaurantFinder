package com.restaurantadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantadmin.ConnectionDetector;
import com.example.restaurantadmin.Registration_step3_Activity;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceAdapter extends BaseAdapter {

	private static Activity context;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray array_Services;
	public static String str_service_Name;
	public String str_checked_service_Name;
	public static String str_service_Id;
	public static String str_checked;
	public String str_checked_service_ID;
	public String str_checked_service_NAME;
	JSONObject Object_ContactDetails;
	public int int_service_select;
	public String str_selected_service;
	ConnectionDetector cd;
	/*** Network Connection Instance **/
	public ServiceAdapter(Activity a, JSONArray array_Services) {

		System.out.println("malyo" + array_Services);
		context = a;
		this.array_Services = array_Services;
		System.out.println("1111array_Services" + array_Services);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(context);
		/* create Object* */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array_Services.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = array_Services.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView txv_service_name, txv_service_id;
		public CheckBox ch_service;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		final ViewHolder holder;
		vi = inflater.inflate(R.layout.rawfile_service_adapter, parent, false);
		holder = new ViewHolder();
		// Product p = getProduct(position);
		// ((TextView) vi.findViewById(R.id.txv_servicename)).setText(p.name);
		holder.txv_service_name = (TextView) vi
				.findViewById(R.id.txv_servicename);
		holder.txv_service_id = (TextView) vi.findViewById(R.id.txv_service_ID);
		holder.ch_service = (CheckBox) vi.findViewById(R.id.ch_service);
		// holder.ch_service
		// .setOnCheckedChangeListener((Registration_step3_Activity) context);
		// holder.ch_service.setTag(position);
		// holder.ch_service.setChecked(p.box);

		vi.setTag(holder);
		try {
			Object_ContactDetails = getItem(position);
			System.out.println("1111object_array_Services"
					+ Object_ContactDetails);
			str_service_Id = Object_ContactDetails.getString("service_id");
			str_service_Name = Object_ContactDetails.getString("service_name");
			holder.txv_service_name.setText(str_service_Name);
			holder.txv_service_id.setText(str_service_Id);
			str_checked_service_ID = holder.txv_service_id.getText().toString();
			System.out.println("!!!!pankaj" + str_service_Id + "---"
					+ str_service_Name);
			// //
			// Registration_step3_Activity.obj_restaurant_app_service.getString(i+""))

			holder.ch_service.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					System.out.println("!!!!pankajonclick" + str_service_Id
							+ "---" + str_service_Name);
					// // is chkIos checked?
					if (cd.isConnectingToInternet()) {
						if (((CheckBox) v).isChecked()) {
							try {

								str_checked_service_ID = holder.txv_service_id
										.getText().toString();
								System.out.println("1111checkedserviceid"
										+ str_checked_service_ID);
								str_checked_service_Name = holder.txv_service_name
										.getText().toString();
								System.out.println("1111checkedservicename"
										+ str_checked_service_Name);

								System.out.println("!!!!pankajif"
										+ str_checked_service_ID + "---"
										+ str_checked_service_Name);
								Registration_step3_Activity.obj_restaurant_app_service
										.put(str_checked_service_ID,
												str_checked_service_Name);
								System.out
										.println("1111adapterobj_restaurant_app_service"
												+ Registration_step3_Activity.obj_restaurant_app_service);
								System.out
										.println("1111adapterobj_restaurant_app_service_Length"
												+ Registration_step3_Activity.obj_restaurant_app_service
														.length());
								Registration_step3_Activity.rf_txv_service
										.setText(str_checked_service_Name);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							// str_checked_service_ID = null;

							str_checked_service_ID = holder.txv_service_id
									.getText().toString();

							str_checked_service_Name = holder.txv_service_name
									.getText().toString();
							if (Registration_step3_Activity.obj_restaurant_app_service
									.has(str_checked_service_ID)) {
								Registration_step3_Activity.obj_restaurant_app_service
										.remove(str_checked_service_ID);

							}
							if (Registration_step3_Activity.obj_restaurant_app_service
									.length() == 0) {
								Registration_step3_Activity.rf_txv_service
										.setText(context
												.getResources()
												.getString(
														R.string.str_Select_Service));
							}

							System.out.println("!!!!unchecked"
									+ str_checked_service_ID);
							System.out.println("!!!!pankajelse"
									+ str_checked_service_ID + "---"
									+ str_checked_service_Name);

							System.out
									.println("1111adapterobj_restaurant_app_service"
											+ Registration_step3_Activity.obj_restaurant_app_service);

						}
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});

		} catch (JSONException e) {

		} catch (NullPointerException np) {

		}

		if (Registration_step3_Activity.obj_restaurant_app_service
				.has(holder.txv_service_id.getText().toString())) {
			holder.ch_service.setChecked(true);
		}

		return vi;
	}

}
