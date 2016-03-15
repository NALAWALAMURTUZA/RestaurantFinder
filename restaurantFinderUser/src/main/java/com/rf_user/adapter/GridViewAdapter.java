package com.rf_user.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.GridImageLoader;
import com.rf_user.activity.ImageItem;

public class GridViewAdapter extends BaseAdapter {

	private Activity activity;
	private int layoutResourceId;
	private ArrayList<ImageItem> data = new ArrayList<ImageItem>();
	private static LayoutInflater inflater = null;
	GridImageLoader imageLoader;

	public GridViewAdapter(Activity a, ArrayList<ImageItem> data1) {
		try {
			this.activity = a;
			this.data = data1;

			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			imageLoader = new GridImageLoader(a.getApplicationContext());
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
	}

	// public GridViewAdapter(Activity a,ArrayList<ImageItem> data1) {
	// this.activity = a;
	// this.data = data1;
	//
	// inflater = (LayoutInflater) activity
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//
	// imageLoader = new ImageLoader(a.getApplicationContext());
	// }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
        	row = inflater.inflate(R.layout.grid_item_layout,
    				parent, false);
            holder = new ViewHolder();
           // holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        ImageItem item = data.get(position);
       // holder.imageTitle.setText(item.getTitle());
        System.out.println("!!!!!!!!!!!data."+data);
        System.out.println("!!!!!item.getBanner_name()..."+item.getBanner_name());
        
        imageLoader.DisplayImage(item.getBanner_name(),
        		holder.image);
        
        
       // holder.image.setImageBitmap(item.getBanner_name());
        return row;
    }

	static class ViewHolder {
		// TextView imageTitle;
		ImageView image;
	}

}