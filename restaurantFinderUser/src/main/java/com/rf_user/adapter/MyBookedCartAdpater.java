package com.rf_user.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rf.restaurant_user.R;

public class MyBookedCartAdpater extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray cart;

	public MyBookedCartAdpater(Activity a, JSONArray cart) {
		try {
			activity = a;
			this.cart = cart;

			System.out.println("cart_value" + cart);
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cart.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = cart.getJSONObject(position);
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

	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;

		vi = inflater
				.inflate(R.layout.delivery_receipt_raw_file, parent, false);
		TextView txv_cart_index = (TextView) vi.findViewById(R.id.txv_cart_index);
		TextView txv_cart_name = (TextView) vi.findViewById(R.id.txv_cart_name);
		TextView txv_cart_value = (TextView) vi.findViewById(R.id.txv_cart_value);
		TextView txv_cart_quantity = (TextView) vi
				.findViewById(R.id.txv_cart_quantity);
		TextView txv_cart_total_quantity = (TextView) vi
				.findViewById(R.id.txv_cart_total_quantity); // TODO
																// Auto-generated
																// method stub

		try {
			
				try {
					JSONObject cart_detail = getItem(position);
					System.out.println("cart.json_detail" + cart_detail);

					String quantity = cart_detail.getString("quantity");
					String index = cart_detail.getString("uid");
					final String price = cart_detail.getString("price");
					final String total = cart_detail.getString("total");
					final String full_name = cart_detail.getString("name");

					txv_cart_index.setText((position + 1) + "");
					txv_cart_name.setText(full_name);
					txv_cart_quantity.setText(quantity);
					txv_cart_value.setText(activity.getString(R.string.Categories_sr)
							+ " " + price);
					txv_cart_total_quantity
							.setText((activity.getString(R.string.Categories_sr) + " " + total)
									+ "");
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
		//	}
		} catch (NullPointerException n) {

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vi;
	}

}
