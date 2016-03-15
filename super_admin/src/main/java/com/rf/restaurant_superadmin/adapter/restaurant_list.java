package com.rf.restaurant_superadmin.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rf.restaurant_superadmin.R;
import com.superadmin.global.Global_variable;

public class restaurant_list extends BaseAdapter
{
	private Activity _activity;
    private JSONArray _array_restaurantlist;
    private static LayoutInflater inflater=null;
    public restaurant_list(Activity activity,JSONArray array_restaurantlist) 
	{
		// TODO Auto-generated constructor stub
		this._activity=activity;
		this._array_restaurantlist=array_restaurantlist;
		 inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_restaurantlist.length();
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
		
		
		 View vi=convertView;
        vi = inflater.inflate(R.layout.activity_rawfile_restaurant_list, null);
        TextView tv_categories = (TextView)vi.findViewById(R.id.rf_admin_tv_categories);
        ImageView img_restaurant_status = (ImageView)vi.findViewById(R.id.img_restaurant_status);
        
        
        String restaurant_status;
		try {
			restaurant_status = Global_variable.array_filter_Restaurant_List
					.getJSONObject(position).getString("status");
		
		if (restaurant_status.equalsIgnoreCase("0")) {
			
			img_restaurant_status.setImageResource(R.drawable.red_button);
			
		} else if (restaurant_status.equalsIgnoreCase("1")) {

			img_restaurant_status.setImageResource(R.drawable.green_button);
		}
		
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        String name_en=null;
		try {
			name_en = _array_restaurantlist.getJSONObject(position).getString("restaurant_name");
			   tv_categories.setText(name_en);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   
	        return vi;
	
	}

}
