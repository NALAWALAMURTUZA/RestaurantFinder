package com.rf_user.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.activity.Checkout_Tablayout;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;

public class Adapter_for_Saved_Address extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	int layoutResID;

	public JSONArray data;

	/***Network Connection Instance**/
	ConnectionDetector cd;
	
	public Adapter_for_Saved_Address(Activity a, JSONArray data) {
		try{
		activity = a;
		this.data = data;

		
		System.out.println("datay_value" + data);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		/*create Object**/
		cd = new ConnectionDetector(activity);
	} catch (NullPointerException n) {
		n.printStackTrace();
	}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length();
	}

	@Override
	public JSONObject getItem(int position) {

		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = data.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
		return j;

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView txv_saved_address;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		final ViewHolder holder;
		vi = inflater.inflate(R.layout.delivery_use_saved_address_raw_file,
				parent, false);
		holder = new ViewHolder();
		holder.txv_saved_address = (TextView) vi
				.findViewById(R.id.txv_saved_address);
		if(Global_variable.Selected_position==position)
		{
			holder.txv_saved_address.setBackgroundResource(R.drawable.customborder);
		}
		vi.setTag(holder);

		// holder=(ViewHolder)vi.getTag();

		try {
			JSONObject data_detail = getItem(position);
			System.out.println("data.json_detail" + data_detail);
//
//			String address_1 = data_detail.getString("address_1");
//			String address_2 = data_detail.getString("address_2");
//			String city_id = data_detail.getString("city_id");
//			String city_name = data_detail.getString("city_name");
			final String complete_address = data_detail.getString("complete_address");
//			String district_id = data_detail.getString("district_id");
//			String district_name = data_detail.getString("district_name");
			final String id = data_detail.getString("id");
//			String map_lat = data_detail.getString("map_lat");
//			String map_long = data_detail.getString("map_long");
//			String map_zoom = data_detail.getString("map_zoom");
//			String region_id = data_detail.getString("region_id");
//			String region_name = data_detail.getString("region_name");
//			String zip = data_detail.getString("zip");

			System.out.println("adapter_complete_address" + complete_address);

			holder.txv_saved_address.setText(complete_address);
			holder.txv_saved_address.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try{
					holder.txv_saved_address.setBackgroundResource(R.drawable.customborder);
					Global_variable.Selected_position=position;
					System.out.println("position_Selected"+position);
					Global_variable.Str_addr_number = id;
					Global_variable.str_User_saved_address=complete_address;
					System.out.println("Global_variable.Str_addr_number"
							+ Global_variable.Str_addr_number);
//					Contact_Details_Activity.async_Shipping_Request a = new Contact_Details_Activity.async_Shipping_Request();
//					a.execute();
					
					if (cd.isConnectingToInternet()) 
					{

						try{
						if(Checkout_Tablayout.language.equals("ar"))
						{
							Checkout_Tablayout.tab.getTabWidget().getChildAt(3)
							.setClickable(true);
							Checkout_Tablayout.tab.setCurrentTab(3);
						}
						else
						{
							Checkout_Tablayout.tab.setCurrentTab(1);
							Checkout_Tablayout.tab.getTabWidget().getChildAt(1)
									.setClickable(true);
						}
					} catch (NullPointerException n) {
						n.printStackTrace();
					}
					} 
					else 
					{

					
						
						// TODO Auto-generated method stub
						Toast.makeText(activity,
						R.string.No_Internet_Connection, Toast.LENGTH_SHORT).show();

						
					}
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException n)
		{
			n.printStackTrace();
		}

		// TODO Auto-generated method stub
		return vi;
	}

}
