package com.restaurantadmin.food_detail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurantadmin.ConnectionDetector;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;

public class restaurant_manage_feddback extends BaseAdapter {
	private Activity _activity;
	private String _categories_child_name;
	private JSONArray _array_parsing;
	private static LayoutInflater inflater = null;
	public static String str_delete_id = null;

	ConnectionDetector cd;

	public restaurant_manage_feddback(Activity activity,JSONArray array_parsing) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._array_parsing = array_parsing;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(_activity);

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_parsing.length();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String first_name = null, mobile_number = null, last_name = null, tg_order_id = null, number_of_people = null,
			   booking_date=null,food=null,customer_services=null,look=null,clensiness=null,
					   atmosphere=null,order_ratting=null,order_comment=null;

		try {
			tg_order_id = _array_parsing.getJSONObject(position).getString("tg_order_id");
			number_of_people = _array_parsing.getJSONObject(position).getString("number_of_people");
			booking_date = _array_parsing.getJSONObject(position).getString("booking_date");
			food = _array_parsing.getJSONObject(position).getString("food");
			customer_services = _array_parsing.getJSONObject(position).getString("customer_services");
			look = _array_parsing.getJSONObject(position).getString("look");
			clensiness = _array_parsing.getJSONObject(position).getString("clensiness");
			atmosphere = _array_parsing.getJSONObject(position).getString("atmosphere");
			order_ratting = _array_parsing.getJSONObject(position).getString("order_ratting");
			order_comment = _array_parsing.getJSONObject(position).getString("order_comment");
			first_name = _array_parsing.getJSONObject(position).getString("first_name");
			last_name = _array_parsing.getJSONObject(position).getString("last_name");
			mobile_number = _array_parsing.getJSONObject(position).getString("mobile_number");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.activity_manage_feedback_rawfile, null);
		}
		TextView txv_fd_date=(TextView) vi.findViewById(R.id.txv_fd_date);
		TextView txv_fd_customer = (TextView) vi.findViewById(R.id.txv_fd_customer);
		TextView txv_fd_cumulative = (TextView) vi.findViewById(R.id.txv_fd_cumulative);
		TextView txv_fd_food = (TextView) vi.findViewById(R.id.txv_fd_food);
		TextView txv_fd_look = (TextView) vi.findViewById(R.id.txv_fd_look);
		TextView txv_fd_clean = (TextView) vi.findViewById(R.id.txv_fd_clean);
		TextView txv_fd_atmosphere = (TextView) vi.findViewById(R.id.txv_fd_atmosphere);
		TextView txv_fd_comment = (TextView) vi.findViewById(R.id.txv_fd_comment);
		TextView txv_fd_services = (TextView) vi.findViewById(R.id.txv_fd_services);

		txv_fd_customer.setText(first_name +" "+last_name);
		txv_fd_date.setText(booking_date);
		txv_fd_cumulative.setText(order_ratting);
		txv_fd_food.setText(food);
		txv_fd_look.setText(look);
		txv_fd_clean.setText(clensiness);
		txv_fd_atmosphere.setText(atmosphere);
		txv_fd_comment.setText(order_comment);
		txv_fd_cumulative.setText(order_ratting);
		txv_fd_services.setText(customer_services);

		return vi;

	}

}
