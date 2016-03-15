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

public class ReviewAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public Context context = null;
	public ImageLoader imageLoader;
	int layoutResID;

	public ReviewAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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

		vi = inflater.inflate(R.layout.reviews_raw_file, parent, false);
		TextView txt_review = (TextView) vi.findViewById(R.id.txt_review);
		TextView txt_reviews_user_name = (TextView) vi
				.findViewById(R.id.txt_reviews_user_name);

		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);
		String comment = item.get("comment");
		System.out.println("comment_adapter" + comment);
		String firstname = item.get("firstname");
		System.out.println("firstname_adapter" + firstname);

		txt_review.setText(comment);
		txt_reviews_user_name.setText(firstname);

		return vi;

	}
}
