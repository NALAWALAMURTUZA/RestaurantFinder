package com.restaurantadmin.food_detail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;

public class food_catagory_categories extends BaseAdapter {
	private Activity _activity;
	private JSONArray _array_restaurantcategory;
	private JSONArray _array_food_detail;
	private static LayoutInflater inflater = null;
//	public static TextView rf_categories ;
	public food_catagory_categories(Activity activity,
			JSONArray array_restaurantcategory,JSONArray array_food_detail) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._array_restaurantcategory = array_restaurantcategory;
		this._array_food_detail=array_food_detail;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		System.out.println("_array_restaurantcategory"
				+ _array_restaurantcategory);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_restaurantcategory.length();
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
		String name_en = null, categories = "";
		try {

			name_en = _array_restaurantcategory.getJSONObject(position)
					.getString("name_en");
//			int i = Global_variable.array_food.length();
//			System.out.println("length" + i);
//			categories = String.valueOf(i);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.activity_rawfile_food_list, null);
		}
		TextView tv_categories = (TextView) vi
				.findViewById(R.id.rf_admin_tv_categories);
		
		TextView rf_categories = (TextView) vi.findViewById(R.id.rf_categories);
		rf_categories.setVisibility(View.VISIBLE);
		int count=0;
		for(int i=0;i<_array_food_detail.length();i++)
		{
			
			try {
				System.out.println("foodid"+_array_food_detail.getJSONObject(i).getString("foodCategory_id"));
				System.out.println("cat_id"+_array_restaurantcategory.getJSONObject(position)
						.getString("id"));
				if(_array_food_detail.getJSONObject(i).getString("foodCategory_id").equalsIgnoreCase(_array_restaurantcategory.getJSONObject(position)
						.getString("id")))
				{
					count++;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		tv_categories.setText(name_en);
        rf_categories.setText(String.valueOf(count));
//		rf_categories.setText(categories);
		return vi;

	}

}
