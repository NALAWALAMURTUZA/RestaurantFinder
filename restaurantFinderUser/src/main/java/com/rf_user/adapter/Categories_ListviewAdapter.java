package com.rf_user.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.Categories_Model;

public class Categories_ListviewAdapter extends BaseAdapter {
	private Activity activity;
	private static LayoutInflater inflater = null;
	public Context context = null;

	int layoutResID;
	private ArrayList<Categories_Model> categories_model;
	private ArrayList<Categories_Model> categories_modelarraylist;

	public Categories_ListviewAdapter(Activity a,
			ArrayList<Categories_Model> categories_model) {
		
		try{
		activity = a;
		this.categories_modelarraylist = categories_model;
		System.out.println("milaki_nai" + categories_model.toString());
		this.categories_model = new ArrayList<Categories_Model>();
		this.categories_model.addAll(categories_model);

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		}catch(NullPointerException n)
		{
			n.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categories_modelarraylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categories_modelarraylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView txv_categories_name;
		public ImageView img_arraow;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;
		ViewHolder holder;

		System.out.println("adapterif" + categories_modelarraylist);
		
		System.out.println("in adapter getview" + categories_model);
		
		vi = inflater.inflate(R.layout.view_menu_categories_rawfile, null);
		holder = new ViewHolder();
		holder.txv_categories_name = (TextView) vi
				.findViewById(R.id.txv_categories);
		holder.img_arraow = (ImageView) vi.findViewById(R.id.img_listview);

		vi.setTag(holder);

		System.out.println("adapterelse" + categories_modelarraylist);
		holder = (ViewHolder) vi.getTag();
		System.out.println("forloop_arraylist_value"
				+ categories_modelarraylist);

		String str_categories_list = categories_modelarraylist.get(position)
				.getStr_Categories();
		holder.txv_categories_name.setText(str_categories_list);
		System.out.println("textview_nameset" + holder.txv_categories_name);
		return vi;
	}

	public void filter(String charText) {
		// TODO Auto-generated method stub

		try {
			charText = charText.toLowerCase(Locale.getDefault());
			categories_modelarraylist.clear();
			if (charText.length() == 0) {
				System.out.println("ifma maleche" + categories_modelarraylist);
				categories_modelarraylist.addAll(categories_model);
				System.out.println("ifma malechemodel"
						+ categories_modelarraylist);
			} else {
				System.out.println("forloopecategories_modelarraylist"
						+ categories_modelarraylist.toString());
				for (Categories_Model categories : categories_model)

				{
					System.out.println("forloopecategories_modelarraylistafter"
							+ categories_modelarraylist);
					if (categories.getStr_Categories()
							.toLowerCase(Locale.getDefault())
							.contains(charText)) {
						System.out.println("heylo...");
						categories_modelarraylist.add(categories);
						System.out.println("hie...."
								+ categories_modelarraylist);
					}
				}
			}
			notifyDataSetChanged();
		} catch (NullPointerException NU) {

		}
	}

}
