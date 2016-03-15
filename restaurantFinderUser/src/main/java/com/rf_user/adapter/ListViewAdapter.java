package com.rf_user.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.FileCache;
import com.rf_user.activity.ImageLoader;
import com.rf_user.activity.Utils;
import com.rf_user.global.Global_variable;

public class ListViewAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public Context context = null;
	public ImageLoader imageLoader;
	int layoutResID;

	public ListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		try {
			activity = a;
			data = d;
			// this.layoutResID=layoutResourceId;
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

	public static class ViewHolder {

		public TextView name_hotel;
		public TextView addres_hotel;
		public TextView mto_hotel;
		public TextView pickup;
		public TextView delivery;
		public TextView indine;
		public TextView txv_rate, txv_review, txv_indine, txv_km,txt_cuisine;
		public RatingBar ratingbar;
		public LinearLayout ll_bg;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		final ViewHolder holder;

		vi = inflater.inflate(R.layout.search_restaurant_scrollview_contents,
				parent, false);
		holder = new ViewHolder();
		holder.name_hotel = (TextView) vi
				.findViewById(R.id.hotel_name_textview);
		holder.addres_hotel = (TextView) vi
				.findViewById(R.id.hotel_address_textview);
		holder.mto_hotel = (TextView) vi.findViewById(R.id.order_time_textview);

		holder.ratingbar = (RatingBar) vi.findViewById(R.id.ratingBar);
		holder.pickup = (TextView) vi.findViewById(R.id.pickup_textview);
		holder.delivery = (TextView) vi.findViewById(R.id.delivery_textview);
		holder.indine = (TextView) vi.findViewById(R.id.txv_indine);

		// ****************************
		holder.ll_bg = (LinearLayout) vi.findViewById(R.id.ll_bg);
		holder.txv_rate = (TextView) vi.findViewById(R.id.txv_rate);
		holder.txv_review = (TextView) vi.findViewById(R.id.txv_review);
		holder.txv_km = (TextView) vi.findViewById(R.id.txv_km);
		holder.txt_cuisine=(TextView)vi.findViewById(R.id.txt_cuisine);
		holder.txv_km = (TextView) vi.findViewById(R.id.txv_km);
		holder.txv_indine = (TextView) vi.findViewById(R.id.txv_indine);
		vi.setTag(holder);

		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);
		String hotel_name = item.get("hotel_name");
		System.out.println("hotel_name_adapter" + hotel_name);
		String hotel_addres = item.get("hotel_address");
		System.out.println("hotel_add_adapter" + hotel_addres);
		String hotel_mto = item.get("hotel_minimum");
		System.out.println("hotel_mto_adapter" + hotel_mto);
		String hotel_icon = item.get("hotel_iconurl");
		System.out.println("hotel_img_adapter" + hotel_icon);

		String hotel_banner_image = item.get("hotel_banner_image");
		System.out.println("hotel_banner_image_adapter" + hotel_banner_image);
		
		String hotel_distance = item.get("hotel_distance");
		System.out.println("hotel_distance_adapter" + hotel_distance);
		
		String hotel_cuisine = item.get("cuisine");
		System.out.println("hotel_cuisine_adapter" + hotel_cuisine);

		String hotel_day = item.get("day");
		System.out.println("hotel_day" + hotel_day);

		String hotel_rate = item.get("hotel_rating");
		System.out.println("hotel_rate_adapter" + hotel_rate);
		String hotel_pick = item.get("hotel_pick");
		System.out.println("hotel_rate_adapter_pick" + hotel_pick);
		String hotel_delivery = item.get("hotel_delivery");
		System.out.println("hotel_rate_adapter_delivery" + hotel_delivery);
		String hotel_indine = item.get("hotel_indine");
		System.out.println("get_view_hotel_indine" + hotel_indine);

		// if(hotel_pick.equals("1") && hotel_delivery.equals("1") &&
		// hotel_indine.equals("1"))
		// {
		// holder.pickup.setVisibility(View.VISIBLE);
		// holder.delivery.setVisibility(View.VISIBLE);
		// holder.indine.setVisibility(View.VISIBLE);
		// }

		if (!hotel_delivery.equals("1")) {
			holder.delivery.setVisibility(View.GONE);
		} else if (hotel_delivery.equals("1")) {
			holder.delivery.setVisibility(View.VISIBLE);

		}

		if (!hotel_pick.equals("1")) {
			holder.pickup.setVisibility(View.GONE);
		} else if (hotel_pick.equals("1")) {
			holder.pickup.setVisibility(View.VISIBLE);

		}

		if (!hotel_indine.equals("1")) {
			holder.indine.setVisibility(View.GONE);
		} else if (hotel_indine.equals("1")) {
			holder.indine.setVisibility(View.VISIBLE);

		}

		holder.name_hotel.setText(hotel_name);
		System.out.println("textview_nameset" + holder.name_hotel);
		holder.addres_hotel.setText(hotel_addres);
		System.out.println("textview_addset" + holder.addres_hotel);
		
		holder.mto_hotel.setText(hotel_day);
		
//		holder.mto_hotel.setText(Global_variable.order + Global_variable.blank
//				+ hotel_mto + Global_variable.blank
//				+ Global_variable.day_before);
		System.out.println("textview_mtoset" + holder.mto_hotel);
		holder.ratingbar.setRating(Float.parseFloat(hotel_rate));
		System.out.println("textview_mtoset" + hotel_rate);
		
		if(hotel_rate.length()!=0)
		{
			holder.txv_review.setText(hotel_rate+"/10");
		}
		else
		{
			holder.txv_review.setText("0/10");
		}
		
		holder.txv_km.setText(hotel_distance+" -km");
		
		holder.txt_cuisine.setText(hotel_cuisine);
		
		System.out.println("!!!!!!!!!!!!!!!!!!hotel_banner_image..."+hotel_banner_image);

		if(hotel_banner_image.length()!=0 && !hotel_banner_image.equalsIgnoreCase("null"))
		{
			System.out.println("!!!!!!!!!!!!!!!!!!result"+getBitmap(Global_variable.image_url + hotel_banner_image));
			if(getBitmap(Global_variable.image_url + hotel_banner_image)!=null)
			{
				BitmapDrawable drawableBitmap = new BitmapDrawable(
						getBitmap(Global_variable.image_url + hotel_banner_image));
				
				holder.ll_bg.setBackgroundDrawable(drawableBitmap);
			}
			else
			{
				holder.ll_bg.setBackgroundResource(R.drawable.ll_bg);
			}
			
		}
		else
		{
			holder.ll_bg.setBackgroundResource(R.drawable.ll_bg);
		}
		
		return vi;

	}

	private Bitmap getBitmap(String url) {
		File f = FileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	private  Bitmap decodeFile(File f) {
		//boolean flag=false;
		try {
			
			
			
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			
			//flag=true;
			
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
//			final ViewHolder holder;
//			holder = new ViewHolder();
//			holder.ll_bg = (LinearLayout) findViewById(R.id.ll_bg);
//			holder.ll_bg.setBackgroundResource(R.drawable.ll_bg);
			
		}
		return null;
	}

}