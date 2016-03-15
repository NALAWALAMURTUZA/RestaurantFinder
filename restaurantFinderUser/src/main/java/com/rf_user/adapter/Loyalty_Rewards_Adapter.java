package com.rf_user.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.ImageLoader;
import com.rf_user.global.Global_variable;

public class Loyalty_Rewards_Adapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public Context context = null;
	public ImageLoader imageLoader;
	int layoutResID;

	public Loyalty_Rewards_Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
		try {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(activity.getApplicationContext());

			System.out.println("data..." + d);
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		return data.size();
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
		View vi = convertView;

		vi = inflater.inflate(R.layout.loyalty_rewards_raw_file, parent, false);
		TextView txt_review = (TextView) vi.findViewById(R.id.loyalty_txt);
		

		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);
		String rest_name= item.get("name_en");
		System.out.println("rest_name" + rest_name);
		String date = item.get("booking_date");
		System.out.println("date" + date);
		String time = item.get("booking_time");
		System.out.println("time" + time);
		
		String str = Global_variable.raw_file_details_one+rest_name+Global_variable.raw_file_details_two+date+Global_variable.raw_file_details_five+Global_variable.blank+time+Global_variable.raw_file_details_three+Global_variable.lp_to_tg_customer+Global_variable.raw_file_details_four; 
		
		txt_review.setText(str);
		//txt_reviews_user_name.setText(firstname);

		return vi;

	}
}
