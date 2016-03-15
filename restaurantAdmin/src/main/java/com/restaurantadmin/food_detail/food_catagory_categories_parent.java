package com.restaurantadmin.food_detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.StrictMode;
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
import com.example.restaurantadmin.Food_Detail_Parent_Activity;
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.imageloader.ImageLoader;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;

public class food_catagory_categories_parent extends BaseAdapter {
	private Activity _activity;
	private String _categories_name;
	private JSONArray _array_restaurantcategory;
	private static LayoutInflater inflater = null;
	public static String id = null;
	public static String str_delete_id = null;
	ConnectionDetector cd;
	public static Dialog DeleteDialog;
	public static TextView txv_Dialogtext;
	public static ImageView img_yes, img_no;
	public food_catagory_categories_parent(Activity activity,
			JSONArray array_restaurantcategory, String categories_name) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._array_restaurantcategory = array_restaurantcategory;
		this._categories_name = categories_name;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(_activity);
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		String name_en = null, icon = null, categories = null, uid = null, spicy_level = null, spicy_level_req_on = null, price = null, available = null;
		int number_of_child = 0;
		try {
			id = _array_restaurantcategory.getJSONObject(position).getString(
					"id");
			icon = _array_restaurantcategory.getJSONObject(position).getString(
					"icon");
			name_en = _array_restaurantcategory.getJSONObject(position)
					.getString("name_en");
			categories = _array_restaurantcategory.getJSONObject(position)
					.getString("description_en");
			uid = _array_restaurantcategory.getJSONObject(position).getString(
					"uid");
			spicy_level = _array_restaurantcategory.getJSONObject(position)
					.getString("spicy_level");
			spicy_level_req_on = _array_restaurantcategory.getJSONObject(
					position).getString("spicy_level_req_on");
			price = _array_restaurantcategory.getJSONObject(position)
					.getString("price");
			number_of_child = _array_restaurantcategory.getJSONObject(position)
					.getJSONArray("child_food").length();
			available = _array_restaurantcategory.getJSONObject(position)
					.getString("available");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.activity_rawfile_add_food, null);
		}
		ImageView img_parentimage = (ImageView) vi
				.findViewById(R.id.img_parentimage);
		TextView tv_parent_name = (TextView) vi
				.findViewById(R.id.tv_parent_name);
		TextView tv_parent_category = (TextView) vi
				.findViewById(R.id.tv_parent_category);
		TextView tv_parent_uid = (TextView) vi.findViewById(R.id.tv_parent_uid);
		TextView tv_parent_spicy_level = (TextView) vi
				.findViewById(R.id.tv_parent_spicy_level);
		TextView tv_parent_prices = (TextView) vi
				.findViewById(R.id.tv_parent_prices);
		TextView tv_parent_spicy_level_req_on = (TextView) vi
				.findViewById(R.id.tv_parent_spicy_level_req_on);
		TextView tv_number_of_child = (TextView) vi
				.findViewById(R.id.tv_number_of_child);
		TextView tv_available = (TextView) vi
				.findViewById(R.id.tv_parent_available);
		ImageView img_update = (ImageView) vi.findViewById(R.id.parent_update);
		// ImageView img_delete=(ImageView)vi.findViewById(R.id.parent_delete);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		ImageLoader img = new ImageLoader(_activity);
		img.DisplayImage(icon, img_parentimage);
		// Toast.makeText(_activity, "File Upload Start.",
		// Toast.LENGTH_SHORT).show();

		tv_parent_name.setText(name_en);
		tv_parent_category.setText(categories);
		tv_parent_uid.setText(uid);
		if (spicy_level.equalsIgnoreCase("0")) {
			spicy_level = "spicy";
		}
		if (spicy_level.equalsIgnoreCase("1")) {
			spicy_level = "Sweet";
		}
		if (spicy_level.equalsIgnoreCase("2")) {
			spicy_level = "Sour";
		}
		if (spicy_level.equalsIgnoreCase("3")) {
			spicy_level = "Bitter";
		}
		if (spicy_level.equalsIgnoreCase("4")) {
			spicy_level = "Salty";
		}
		if (spicy_level.equalsIgnoreCase("5")) {
			spicy_level = "Umami";
		}

		tv_parent_spicy_level.setText(spicy_level);
		tv_parent_prices.setText(price);
		tv_parent_spicy_level_req_on.setText(_activity.getResources()
				.getString(R.string.food_level) +" "+ spicy_level_req_on);
		tv_number_of_child.setText(String.valueOf(number_of_child));
		if (available.equalsIgnoreCase("1")) {
			available = "Available";

		} else {
			available = "Not Available";
		}
		tv_available.setText(available);

		// img_delete.setOnClickListener(new View.OnClickListener() {
		//
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// System.out.println("pozitanparent"+position);
		// System.out.println("position"+position);
		// try {
		// str_delete_id=_array_restaurantcategory.getJSONObject(position).getString("id");
		// System.out.println("1111str_delete_id"+str_delete_id);
		// DeleteDialog();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// });

		img_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("pozitanparent" + position);
				try {
					Food_Detail_Parent_Activity.update_parent_id = _array_restaurantcategory
							.getJSONObject(position).getString("id");
					Food_Detail_Parent_Activity.update_parent_uid = _array_restaurantcategory
							.getJSONObject(position).getString("uid");
					String helloWorld = _array_restaurantcategory
							.getJSONObject(position).getString("icon");
					String hellWrld = helloWorld.replace(
							Global_variable.img_pre_path, "");
					Food_Detail_Parent_Activity.result = hellWrld;
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
					ImageLoader img = new ImageLoader(_activity);
					img.DisplayImage(
							_array_restaurantcategory.getJSONObject(position)
									.getString("icon"),
							Food_Detail_Parent_Activity.img_parent_select_image);
					Food_Detail_Parent_Activity.update_parent_vary = _array_restaurantcategory
							.getJSONObject(position).getString("vary");
					Food_Detail_Parent_Activity.txv_parent_name
							.setText(_array_restaurantcategory.getJSONObject(
									position).getString("name_en"));
					Food_Detail_Parent_Activity.txv_parent_discription
							.setText(_array_restaurantcategory.getJSONObject(
									position).getString("description_en"));
					Food_Detail_Parent_Activity.txv_parent_price
							.setText(_array_restaurantcategory.getJSONObject(
									position).getString("price"));

					Food_Detail_Parent_Activity.spiner_parent_vag_nonvag
							.setSelection(Integer
									.parseInt(_array_restaurantcategory
											.getJSONObject(position).getString(
													"veg")));
					String available = _array_restaurantcategory.getJSONObject(
							position).getString("available");
					if (available.equalsIgnoreCase("1")) {
						available = "Available";
					} else {
						available = "Not Available";
					}
					int indexavailable = Food_Detail_Parent_Activity.adapter_parent_spiner_available
							.getPosition(available);
					Log.i("indexCapacity", "indexCapacity" + indexavailable);
					Food_Detail_Parent_Activity.spiner_parent_spicy_available
							.setSelection(indexavailable);

					// Food_Detail_Parent_Activity.spiner_parent_spicy_available.setSelection(Integer.parseInt(_array_restaurantcategory.getJSONObject(position).getString("available"))-1);
					Food_Detail_Parent_Activity.spiner_parent_spicy_level
							.setSelection(Integer
									.parseInt(_array_restaurantcategory
											.getJSONObject(position).getString(
													"spicy_level")));
					Food_Detail_Parent_Activity.spiner_parent_spicy_level_no
							.setSelection(Integer
									.parseInt(_array_restaurantcategory
											.getJSONObject(position).getString(
													"spicy_level_req_on")) - 1);
					Food_Detail_Parent_Activity.img_parent_add_button
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

							Food_Detail_Parent_Activity.deleteparentchildfooddetail a = new Food_Detail_Parent_Activity.deleteparentchildfooddetail();
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
