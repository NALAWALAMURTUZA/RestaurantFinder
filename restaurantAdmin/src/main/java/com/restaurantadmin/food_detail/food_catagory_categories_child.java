package com.restaurantadmin.food_detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantadmin.ConnectionDetector;
import com.example.restaurantadmin.Food_Detail_Child_Activity;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;

public class food_catagory_categories_child extends BaseAdapter {
	private Activity _activity;
	private String _categories_child_name;
	private JSONArray _array_child_food;
	private static LayoutInflater inflater = null;
	public static String str_delete_id = null;
	public static Dialog DeleteDialog;
	public static TextView txv_Dialogtext;
	public static ImageView img_yes, img_no;
	ConnectionDetector cd;
	public food_catagory_categories_child(Activity activity,
			JSONArray array_restaurantcategory, String categories_child_name) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._array_child_food = array_restaurantcategory;
		this._categories_child_name = categories_child_name;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		System.out.println("array" + _array_child_food);
		cd = new ConnectionDetector(_activity);

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_child_food.length();
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
		String name_en = null, icon = null, categories = null, uid = null, available = null, spicy_level_req_on = null, price = null, description = null;

		try {
			name_en = _array_child_food.getJSONObject(position).getString(
					"name_en");
			categories = this._categories_child_name;
			uid = _array_child_food.getJSONObject(position).getString("uid");
			available = _array_child_food.getJSONObject(position).getString(
					"available");
			spicy_level_req_on = _array_child_food.getJSONObject(position)
					.getString("spicy_level_req_on");
			price = _array_child_food.getJSONObject(position)
					.getString("price");
			description = _array_child_food.getJSONObject(position).getString(
					"description_en");
			System.out.println("available" + available);

			if (available.equalsIgnoreCase("1")) {
				available = "Available";
			} else {
				available = "Not Available";
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.activity_child_row, null);
		}
		TextView tv_child_name = (TextView) vi
				.findViewById(R.id.rf_admin_child_food_name);
		TextView tv_child_uid = (TextView) vi
				.findViewById(R.id.rf_admin_child_uid);
		TextView tv_child_description = (TextView) vi
				.findViewById(R.id.rf_admin_child_description);
		TextView tv_child_spicy_level = (TextView) vi
				.findViewById(R.id.rf_admin_child_food_level);
		TextView tv_child_prices = (TextView) vi
				.findViewById(R.id.rf_admin_child_prices);
		TextView tv_child_available = (TextView) vi
				.findViewById(R.id.rf_admin_child_available);
		TextView tv_child_categories = (TextView) vi
				.findViewById(R.id.rf_admin_child_food_categories);
		ImageView img_child_update = (ImageView) vi
				.findViewById(R.id.child_update);
		ImageView img_child_delete = (ImageView) vi
				.findViewById(R.id.child_delete);

		tv_child_name.setText(name_en);
		tv_child_categories.setText(categories);
		tv_child_description.setText(description);
		tv_child_spicy_level.setText(_activity.getResources().getString(
				R.string.food_level)
				+ " " + spicy_level_req_on);
		tv_child_prices.setText(price);
		tv_child_available.setText(available);
		tv_child_uid.setText(uid);

		img_child_delete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("position" + position);
				try {
					str_delete_id = _array_child_food.getJSONObject(position)
							.getString("id");
					DeleteDialog();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		img_child_update.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("position" + position);
				try {
					Food_Detail_Child_Activity.update_id = _array_child_food
							.getJSONObject(position).getString("id");
					Food_Detail_Child_Activity.update_uid = _array_child_food
							.getJSONObject(position).getString("uid");
					Food_Detail_Child_Activity.edt_child_discription
							.setText(_array_child_food.getJSONObject(position)
									.getString("description_en"));
					Food_Detail_Child_Activity.edt_child_name
							.setText(_array_child_food.getJSONObject(position)
									.getString("name_en"));
					Food_Detail_Child_Activity.edt_child_price
							.setText(_array_child_food.getJSONObject(position)
									.getString("price"));
					String available = _array_child_food
							.getJSONObject(position).getString("available");
					String spicy_level_req_on = _array_child_food
							.getJSONObject(position).getString(
									"spicy_level_req_on");
					if (available.equalsIgnoreCase("1")) {
						available = "Available";
					} else {
						available = "Not Available";
					}
					int indexavailable = Food_Detail_Child_Activity.adapter_spiner_available
							.getPosition(available);
					Log.i("indexCapacity", "indexCapacity" + indexavailable);
					Food_Detail_Child_Activity.spin_available
							.setSelection(indexavailable);

					spicy_level_req_on = "Level " + spicy_level_req_on;
					System.out.println("spicy_level_req_on"
							+ spicy_level_req_on);
					int indexlevel = Food_Detail_Child_Activity.adapter_spiner_level
							.getPosition(spicy_level_req_on);
					Log.i("indexCapacity", "indexCapacity" + indexlevel);
					Food_Detail_Child_Activity.spin_child_level
							.setSelection(indexlevel);
					Food_Detail_Child_Activity.img_add_update
							.setImageResource(R.drawable.update_2);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return vi;

	}
	public void DeleteDialog() {

		DeleteDialog = new Dialog(_activity);
		DeleteDialog.setContentView(R.layout.popup_exitapp);
		DeleteDialog.setTitle("Delete");
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(DeleteDialog.getWindow().getAttributes());
		DeleteDialog.getWindow().setLayout(500,
				WindowManager.LayoutParams.WRAP_CONTENT);
		// lp.width = 500;
		// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// lp.gravity = Gravity.CENTER;
		img_yes = (ImageView) DeleteDialog.findViewById(R.id.img_yes);
		img_no = (ImageView) DeleteDialog.findViewById(R.id.img_no);
		txv_Dialogtext = (TextView) DeleteDialog.findViewById(R.id.txv_dialog);
		try {
			img_yes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (str_delete_id != null) {

						if (cd.isConnectingToInternet()) {

							Food_Detail_Child_Activity.deleteparentchildfooddetail a = new Food_Detail_Child_Activity.deleteparentchildfooddetail();
							a.execute();
							DeleteDialog.dismiss();
						} else {

							// TODO Auto-generated method stub
							Toast.makeText(_activity,
									R.string.No_Internet_Connection,
									Toast.LENGTH_SHORT).show();
						}

					} else {

					}
				}
			});
			img_no.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DeleteDialog.dismiss();
				}
			});

			// customHandler.post(updateTimerThread, 0);
			DeleteDialog.show();
			DeleteDialog.setCancelable(false);
			DeleteDialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
	}

}
