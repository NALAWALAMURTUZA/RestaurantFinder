package com.restaurantadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.restaurantadmin.Global.Global_variable;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnlineOrderOrderCartAdapter extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray cart;

	public OnlineOrderOrderCartAdapter(Activity a, JSONArray cart) {
		activity = a;
		this.cart = cart;

		System.out.println("carty_value" + cart);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		}
		return j;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView txv_cart_name, txv_cart_price, txv_cart_quantity,
				txv_cart_total, txv_cart_index, txv_spicylevel;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;

		final ViewHolder holder;
		vi = inflater
				.inflate(R.layout.online_order_cart_raefile, parent, false);
		holder = new ViewHolder();
		holder.txv_cart_index = (TextView) vi.findViewById(R.id.txv_cart_index);
		holder.txv_cart_name = (TextView) vi.findViewById(R.id.txv_cart_name);
		holder.txv_cart_price = (TextView) vi.findViewById(R.id.txv_cart_price);
		holder.txv_cart_quantity = (TextView) vi
				.findViewById(R.id.txv_cart_quantity);
		holder.txv_cart_total = (TextView) vi.findViewById(R.id.txv_cart_total);
		holder.txv_spicylevel = (TextView) vi.findViewById(R.id.txv_spicylevel);
		// TODO Auto-generated method stub

		vi.setTag(holder);

		try {
			{
				JSONObject cart_detail = getItem(position);
				System.out.println("cart.json_detail" + cart_detail);
				final String str_cartname = cart_detail.getString("name");
				String quantity = cart_detail.getString("quantity");
				final String price = cart_detail.getString("price");
				final String str_cart_total = cart_detail.getString("total");
				final String spicy = cart_detail
						.getString("spicy_level_req_on");
				holder.txv_cart_index.setText((position + 1) + "");
				holder.txv_cart_name.setText(str_cartname);
				holder.txv_cart_quantity.setText(quantity);
				holder.txv_spicylevel.setText(activity.getResources()
						.getString(R.string.oo_details_spicy) + " " + spicy);
				holder.txv_cart_price.setText(Global_variable.euro + " "
						+ price);
				holder.txv_cart_total.setText(Global_variable.euro + " "
						+ str_cart_total);
			}
		} catch (NullPointerException n) {

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vi;
	}

}
