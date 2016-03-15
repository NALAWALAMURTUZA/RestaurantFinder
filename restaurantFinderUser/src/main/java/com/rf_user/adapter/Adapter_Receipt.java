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
import com.rf_user.activity.Payment_Activity;
import com.rf_user.global.Global_variable;

public class Adapter_Receipt extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray cart;

	public Adapter_Receipt(Activity a, JSONArray cart) {
		try {
			activity = a;
			this.cart = cart;

			System.out.println("carty_value" + cart);
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

	public static class ViewHolder {

		public TextView txv_cart_name, txv_cart_value, txv_cart_quantity,
				txv_cart_total_quantity, txv_cart_index;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;

		final ViewHolder holder;
		vi = inflater
				.inflate(R.layout.delivery_receipt_raw_file, parent, false);
		holder = new ViewHolder();
		holder.txv_cart_index = (TextView) vi.findViewById(R.id.txv_cart_index);
		holder.txv_cart_name = (TextView) vi.findViewById(R.id.txv_cart_name);
		holder.txv_cart_value = (TextView) vi.findViewById(R.id.txv_cart_value);
		holder.txv_cart_quantity = (TextView) vi
				.findViewById(R.id.txv_cart_quantity);
		holder.txv_cart_total_quantity = (TextView) vi
				.findViewById(R.id.txv_cart_total_quantity); // TODO
																// Auto-generated
																// method stub

		vi.setTag(holder);

		try {
			if (Payment_Activity.Bool_Apply == false) {
				try {
					JSONObject cart_detail = getItem(position);
					System.out.println("cart.json_detail" + cart_detail);

					String quantity = cart_detail.getString("quantity");
					String index = cart_detail.getString("index");
					final String price = cart_detail.getString("price");
					final String spicy_level_position = cart_detail
							.getString("spicy_level_req_on");
					final String comment = cart_detail.getString("comment");
					final String full_name = cart_detail.getString("full_name");
					int Total_Price = Integer.parseInt(quantity)
							* Integer.parseInt(price);
					holder.txv_cart_index.setText((position + 1) + "");
					holder.txv_cart_name.setText(full_name);
					holder.txv_cart_quantity.setText(quantity);
					holder.txv_cart_value.setText(Global_variable.Categories_sr
							+ " " + price);
					holder.txv_cart_total_quantity
							.setText((Global_variable.Categories_sr + " " + Total_Price)
									+ "");

				} catch (NullPointerException n) {
					n.printStackTrace();
				}

				/*
				 * if(!Payment_Activity.str_delivery_charge.equalsIgnoreCase("null"
				 * )) { Reciept_Activity.receipt_txv_delivery_charge.setText(
				 * Global_variable
				 * .Categories_sr+" "+Payment_Activity.str_delivery_charge); //
				 * int
				 * cart_total=Integer.parseInt(Payment_Activity.str_delivery_charge
				 * )
				 * +Integer.parseInt(String.valueOf(Global_variable.cart_total))
				 * ;
				 * Reciept_Activity.receipt_txv_final_total.setText(Global_variable
				 * .Categories_sr+" "+Payment_Activity.final_Cart_Total); } else
				 * { Reciept_Activity.receipt_txv_delivery_charge.setText(
				 * Global_variable.Categories_sr+" "+"00");
				 * Reciept_Activity.receipt_txv_final_total
				 * .setText(Global_variable
				 * .Categories_sr+" "+String.valueOf(Global_variable
				 * .cart_total)); }
				 */
			} else if (Payment_Activity.Bool_Apply == true) {
				try {
					JSONObject cart_detail = getItem(position);
					System.out.println("cart.json_detail" + cart_detail);

					String quantity = cart_detail.getString("quantities");
					String index = cart_detail.getString("uid");
					final String price = cart_detail.getString("price");
					final String total = cart_detail.getString("total");
					final String comment = cart_detail.getString("comment");
					final String full_name = cart_detail.getString("name");

					holder.txv_cart_index.setText((position + 1) + "");
					holder.txv_cart_name.setText(full_name);
					holder.txv_cart_quantity.setText(quantity);
					holder.txv_cart_value.setText(Global_variable.Categories_sr
							+ " " + price);
					holder.txv_cart_total_quantity
							.setText((Global_variable.Categories_sr + " " + total)
									+ "");
				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		} catch (NullPointerException n) {

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vi;
	}

}
