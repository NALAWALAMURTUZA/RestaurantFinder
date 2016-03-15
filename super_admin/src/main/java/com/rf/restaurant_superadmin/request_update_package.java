package com.rf.restaurant_superadmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.rf.restaurant_superadmin.internet.ConnectionDetector;
import com.superadmin.global.Global_variable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.provider.Settings.Global;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class request_update_package extends BaseAdapter {
	private Activity _activity;
	private JSONArray _obj;
	public static String str_notification_id = null,str_package_id=null,str_isRead=null,str_name_en=null,str_current_package_id=null,str_super_admin=null,str_restaurant_email=null;
	public static Dialog DeleteDialog;
	public static ImageView img_yes, img_no;
	// private JSONArray _array_invoice_data_list;
	private static LayoutInflater inflater = null;
	public static TextView txv_Dialogtext;
	ConnectionDetector cd;
	public request_update_package(Activity activity, JSONArray obj) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._obj = obj;
		// this._array_invoice_data_list = _array_invoice_data_list;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(_activity);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this._obj.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = _obj.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		TextView txt_date,txt_restaurant_name, txt_contact_number, txt_contact_email, txt_current_package, txt_requested_package, txt_package_amount, txt_decline_button, txt_approve_button;

		vi = inflater.inflate(R.layout.activity_rawfile_requestupdate_package,
				parent, false);
		// vi =
		// inflater.inflate(R.layout.activity_rawfile_requestupdate_package,
		// null);
		txt_restaurant_name = (TextView) vi
				.findViewById(R.id.txt_restaurant_name);
		txt_contact_number = (TextView) vi
				.findViewById(R.id.txt_contact_number);
		txt_contact_email = (TextView) vi.findViewById(R.id.txt_contact_email);
		txt_current_package = (TextView) vi
				.findViewById(R.id.txt_current_package);
		txt_requested_package = (TextView) vi
				.findViewById(R.id.txt_requested_package);
		txt_package_amount = (TextView) vi
				.findViewById(R.id.txt_package_amount);
		txt_decline_button = (TextView) vi
				.findViewById(R.id.txt_decline_button);
		txt_approve_button = (TextView) vi
				.findViewById(R.id.txt_approve_button);
		txt_date = (TextView) vi
				.findViewById(R.id.txt_date);

		try {
			System.out.println("obj" + _obj.getJSONObject(position));
			System.out.println("name_en"
					+ _obj.getJSONObject(position).getString("name_en"));
			txt_restaurant_name.setText(_obj.getJSONObject(position)
					.getString("name_en").toString());

			txt_contact_number.setText(_obj.getJSONObject(position)
					.getString("contact_number").toString());
			txt_contact_email.setText(_obj.getJSONObject(position)
					.getString("contact_email").toString());
			
			if(_obj.getJSONObject(position).getString("Current_package_id").toString().equals("1"))
			{
				txt_current_package.setText("FREE");
			}
			else if(_obj.getJSONObject(position).getString("Current_package_id").toString().equals("2"))
			{
				txt_current_package.setText("PRO");
			}
			else if(_obj.getJSONObject(position).getString("Current_package_id").toString().equals("3"))
			{
				txt_current_package.setText("GOLD");
			}
			if(_obj.getJSONObject(position).getString("Requested_package_id").toString().equals("1"))
			{
				txt_requested_package.setText("FREE");
			}
			else if(_obj.getJSONObject(position).getString("Requested_package_id").toString().equals("2"))
			{
				txt_requested_package.setText("PRO");
			}
			else if(_obj.getJSONObject(position).getString("Requested_package_id").toString().equals("3"))
			{
				txt_requested_package.setText("GOLD");
			}
				
			txt_package_amount.setText(_obj.getJSONObject(position)
					.getString("price").toString());
			txt_date.setText(_obj.getJSONObject(position)
					.getString("CreatedDate").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		txt_approve_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("position" + position);
				try {
					str_notification_id = _obj.getJSONObject(position)
							.getString("rest_id");
					str_package_id=_obj.getJSONObject(position)
							.getString("Requested_package_id");
					str_current_package_id=_obj.getJSONObject(position)
							.getString("Current_package_id");
					str_restaurant_email=_obj.getJSONObject(position)
							.getString("Requested_package_id");
					str_restaurant_email=_obj.getJSONObject(position)
							.getString("contact_email");
					str_super_admin=Global_variable.admin_email;
					str_name_en=_obj.getJSONObject(position)
							.getString("name_en");
					
					str_isRead="1";
					DeleteDialog();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		txt_decline_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("position" + position);
				try {
					str_notification_id = _obj.getJSONObject(position)
							.getString("rest_id");
					str_package_id=_obj.getJSONObject(position)
							.getString("Requested_package_id");
					str_current_package_id=_obj.getJSONObject(position)
							.getString("Current_package_id");
					str_restaurant_email=_obj.getJSONObject(position)
							.getString("Requested_package_id");
					str_restaurant_email=_obj.getJSONObject(position)
							.getString("contact_email");
					str_super_admin=Global_variable.admin_email;
					str_name_en=_obj.getJSONObject(position)
							.getString("name_en");
					str_isRead="2";
					DeleteDialog();
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
		DeleteDialog.setTitle(_activity.getResources().getString(R.string.update_package));
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(DeleteDialog.getWindow().getAttributes());
		DeleteDialog.getWindow().setLayout(1000,
				WindowManager.LayoutParams.WRAP_CONTENT);
		 lp.width = 1000;
		 lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		 lp.gravity = Gravity.CENTER;
		img_yes = (ImageView) DeleteDialog.findViewById(R.id.img_yes);
		img_no = (ImageView) DeleteDialog.findViewById(R.id.img_no);
		txv_Dialogtext = (TextView) DeleteDialog.findViewById(R.id.txv_dialog);
		txv_Dialogtext.setText(_activity.getResources().getString(R.string.update_package_message));
		try {
			img_yes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (str_notification_id != null) {

						if (cd.isConnectingToInternet()) {

							acivity_requestupdate_package.update_notifications a = new acivity_requestupdate_package.update_notifications();
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
