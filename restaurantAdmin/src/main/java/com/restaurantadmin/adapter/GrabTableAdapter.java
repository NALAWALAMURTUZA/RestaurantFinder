package com.restaurantadmin.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantadmin.ConnectionDetector;
import com.example.restaurantadmin.GrabTableProfileActivity;
import com.example.restaurantadmin.GrabTable_Activity;
import com.example.restaurantadmin.TakeBooking_Tablayout;
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class GrabTableAdapter extends BaseAdapter {

	private static Activity context;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray array_online_table_grabbing;
	public static String str_numberof_people, booking_date, booking_time,
			booking_status, table_no, str_first_name, str_last_name,
			str_tg_orderID, str_email, str_order_status_tag, str_loyalty;
	JSONObject Object_TableGrabbing;
	public int int_service_select;
	public String str_selected_service;
	ArrayAdapter<CharSequence> adapter;
	public static ProgressDialog p;
	JSONObject obj_profile;
	boolean sendconfirm = false;
	public static String str_order_fname, str_order_surname, str_mobile,
			str_order_comments, str_bussiness, str_meal_deliver, str_ocsassion,
			str_booking_mode;
	GrabTable_Activity ACT = null;
	public static TextView txv_Dialogtext, txv_msg;
	public static ImageView img_yes, img_no, img_popupOk;
	public static Dialog dialog;
	public static String str_order_date_time;
	ConnectionDetector cd;
	/*** Network Connection Instance **/
	public GrabTableAdapter(Activity a, JSONArray array_online_table_grabbing) {

		System.out.println("malyo" + array_online_table_grabbing);
		context = a;
		this.array_online_table_grabbing = array_online_table_grabbing;
		System.out.println("1111array_online_table_grabbing"
				+ array_online_table_grabbing);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(context);
		/* create Object* */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array_online_table_grabbing.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = array_online_table_grabbing.getJSONObject(position);
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

	public static class ViewHolder {
		public TextView txv_numberof_people, txv_fullname, txv_booking_date,
				txv_order_time, txv_special_notes, txv_status,
				txv_tg_main_status, txv_loyalty, txv_no_per, txv_title_no_per,
				txv_id;
		public LinearLayout ll_orderdetails, ll_name;
		public ImageView img_plus, img_minus;
		public TextView txv_details, txv_tg_order_id, txv_tg_email,
				txv_tg_mobile;
		public Button btn_Sendconfirm, btn_DonotConfirm, btn_not_show,
				btn_finish, btn_midel_invisible, btn_cancel_order;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;
		final ViewHolder holder;
		vi = inflater.inflate(R.layout.activity_grab_table_rawfile, parent,
				false);
		holder = new ViewHolder();
		holder.txv_loyalty = (TextView) vi.findViewById(R.id.txv_loyalty);
		holder.txv_no_per = (TextView) vi.findViewById(R.id.txv_no_per);
		holder.txv_title_no_per = (TextView) vi
				.findViewById(R.id.txv_title_no_per);
		holder.txv_tg_order_id = (TextView) vi
				.findViewById(R.id.txv_tg_order_id);
		holder.txv_numberof_people = (TextView) vi
				.findViewById(R.id.rf_order_details_time_persone);
		holder.txv_fullname = (TextView) vi
				.findViewById(R.id.rf_order_details_person_name);
		holder.txv_booking_date = (TextView) vi
				.findViewById(R.id.txv_bookingdate);
		holder.txv_order_time = (TextView) vi.findViewById(R.id.txv_order_time);
		holder.txv_special_notes = (TextView) vi
				.findViewById(R.id.txv_comments);
		holder.ll_orderdetails = (LinearLayout) vi
				.findViewById(R.id.ll_orderdetails);
		holder.ll_name = (LinearLayout) vi.findViewById(R.id.ll_name);
		holder.img_plus = (ImageView) vi.findViewById(R.id.img_plus);
		holder.img_minus = (ImageView) vi.findViewById(R.id.img_minus);
		holder.txv_status = (TextView) vi.findViewById(R.id.txv_status);
		holder.txv_tg_main_status = (TextView) vi
				.findViewById(R.id.txv_tg_main_status);
		// holder.txv_table_no = (TextView) vi.findViewById(R.id.txv_table_no);
		holder.txv_details = (TextView) vi.findViewById(R.id.txv_details);
		holder.txv_tg_email = (TextView) vi.findViewById(R.id.txv_tg_email);
		holder.txv_tg_mobile = (TextView) vi.findViewById(R.id.txv_tg_mobile);
		// **********send do not send
		holder.btn_Sendconfirm = (Button) vi.findViewById(R.id.btn_send_conf);
		holder.btn_DonotConfirm = (Button) vi
				.findViewById(R.id.btn_donot_send_conf);
		holder.txv_id = (TextView) vi.findViewById(R.id.txv_id);
		// final Button btn_Sendconfirm = (Button) vi
		// .findViewById(R.id.btn_send_conf);
		// final Button btn_DonotConfirm = (Button) vi
		// .findViewById(R.id.btn_donot_send_conf);
		// **********send do not send
		// *******
		// **
		// **************
		// ******************not show cancel*****************************
		holder.btn_not_show = (Button) vi.findViewById(R.id.btn_not_show);
		holder.btn_finish = (Button) vi.findViewById(R.id.btn_finish);

		// final Button btn_not_show = (Button)
		// vi.findViewById(R.id.btn_not_show);
		// final Button btn_finish = (Button) vi.findViewById(R.id.btn_finish);
		// ******************not show cancel*****************************

		holder.btn_midel_invisible = (Button) vi
				.findViewById(R.id.btn_midel_invisible);

		// final Button btn_midel_invisible = (Button) vi
		// .findViewById(R.id.btn_midel_invisible);
		// **********cancel_order

		holder.btn_cancel_order = (Button) vi
				.findViewById(R.id.btn_cancel_order);

		// final Button btn_cancel_order = (Button) vi
		// .findViewById(R.id.btn_cancel_order);
		vi.setTag(holder);
		try {
			Object_TableGrabbing = getItem(position);
			System.out.println("1111object_array_Services"
					+ Object_TableGrabbing);

			booking_status = Object_TableGrabbing.getString("booking_status");
			System.out.println("booking_status" + booking_status);
			if (Object_TableGrabbing.has("order_comment")) {
				str_order_comments = Object_TableGrabbing
						.getString("order_comment");
				if (str_order_comments.length() != 0
						&& !str_order_comments.equalsIgnoreCase("null")) {
					holder.txv_special_notes.setText(str_order_comments);
				}
			}
			if (Object_TableGrabbing.has("note")) {
				str_order_comments = Object_TableGrabbing.getString("note");
				if (str_order_comments.length() != 0
						&& !str_order_comments.equalsIgnoreCase("null")) {
					holder.txv_special_notes.setText(str_order_comments);
				}
			}
			if (Object_TableGrabbing.has("firstname")) {
				str_order_fname = Object_TableGrabbing.getString("firstname");
			}
			if (Object_TableGrabbing.has("surname")) {
				str_order_surname = Object_TableGrabbing.getString("surname");
			}
			if (Object_TableGrabbing.has("mobile")) {
				str_mobile = Object_TableGrabbing.getString("mobile");
			}

			// str_booking_date =
			// Object_TableGrabbing.getString("booking_date");
			// str_booking_time =
			// Object_TableGrabbing.getString("booking_time");
			if (Object_TableGrabbing.has("bussiness")) {
				str_bussiness = Object_TableGrabbing.getString("bussiness");
			}
			if (Object_TableGrabbing.has("duration")) {
				str_meal_deliver = Object_TableGrabbing.getString("duration");
			}
			if (Object_TableGrabbing.has("occasion")) {
				str_ocsassion = Object_TableGrabbing.getString("occasion");
			}
			if (Object_TableGrabbing.has("booking_mode")) {
				str_booking_mode = Object_TableGrabbing
						.getString("booking_mode");
				System.out.println("bookingmode??" + str_booking_mode);
			}

			obj_profile = Object_TableGrabbing.getJSONObject("profile");
			System.out.println("1111profile" + obj_profile);
			if (obj_profile.length() != 0) {
				System.out.println("7777777777:if:");
				str_first_name = obj_profile.getString("profile_first_name");
				System.out.println("str_first_name" + str_first_name);
				str_last_name = obj_profile.getString("profile_last_name");
				System.out.println("str_last_name" + str_last_name);
				// *******email
				String email = obj_profile.getString("profile_email");
				str_mobile = obj_profile.getString("profile_mobile_number");
				System.out.println("EMAILTGADAPTER" + email);
				holder.txv_tg_email.setText(email);
				// ********
				holder.txv_fullname.setText(str_first_name + " "
						+ str_last_name);
				holder.txv_tg_mobile.setText(str_mobile);
			} else {
				String email = Object_TableGrabbing.getString("email");
				System.out.println("EMAILTGADAPTER" + email);
				holder.txv_tg_email.setText(email);
				System.out.println("7777777777:else:");
				holder.txv_fullname.setText(str_order_fname + " "
						+ str_order_surname);
				holder.txv_tg_mobile.setText(str_mobile);
			}
			str_numberof_people = Object_TableGrabbing
					.getString("number_of_people");
			System.out.println("str_numberof_people" + str_numberof_people);
			if (str_numberof_people != "") {
				holder.txv_no_per.setText(str_numberof_people + " "
						+ context.getString(R.string.str_moper));
				holder.txv_title_no_per.setText(str_numberof_people + " "
						+ "Per.");
			}
			if (Object_TableGrabbing.has("booking_date")) {
				booking_date = Object_TableGrabbing.getString("booking_date");
				System.out.println("booking_date" + booking_date);
			}
			if (Object_TableGrabbing.has("booking_time")) {
				booking_time = Object_TableGrabbing.getString("booking_time");
				System.out.println("booking_time" + booking_time);
			}

			holder.txv_order_time.setText(booking_time);
			table_no = Object_TableGrabbing.getString("table_number");
			System.out.println("table_no" + table_no);
			str_loyalty = Object_TableGrabbing.getString("loyalty");
			System.out.println("str_loyalty" + str_loyalty);
			holder.txv_loyalty.setText(str_loyalty);
			// *******ORDER ID TG
			String str_orderid = Object_TableGrabbing.getString("tg_order_id");
			System.out.println("11111TGOREDERID" + str_tg_orderID);
			// ***th order id
			holder.txv_tg_order_id.setText(str_orderid);
			holder.txv_id.setText(str_orderid);
			// tableno
			// if (table_no.equalsIgnoreCase("null")) {
			//
			// } else {
			// holder.txv_table_no.setText(table_no);
			// }
			// no of pwoplw full names
			holder.txv_numberof_people.setText(booking_date + " "
					+ booking_time + "\n" + str_numberof_people + ".Per");

			// order status
			if (booking_status.equalsIgnoreCase("1")) {
				holder.txv_status.setText(R.string.TG_statuspending);
				holder.txv_tg_main_status.setText(R.string.TG_statuspending);

			} else if (booking_status.equalsIgnoreCase("2")) {
				holder.txv_status.setText(R.string.TG_status_confirmed);
				holder.txv_tg_main_status.setText(R.string.TG_status_confirmed);
				holder.btn_Sendconfirm.setVisibility(View.GONE);
				holder.btn_DonotConfirm.setVisibility(View.GONE);
				// **********************
				holder.btn_not_show.setVisibility(View.VISIBLE);
				holder.btn_finish.setVisibility(View.VISIBLE);
			} else if (booking_status.equalsIgnoreCase("6")) {
				holder.txv_status.setText(R.string.TG_status_cancel);
				holder.txv_tg_main_status.setText(R.string.TG_status_cancel);
				holder.btn_Sendconfirm.setVisibility(View.GONE);
				holder.btn_DonotConfirm.setVisibility(View.GONE);
				// **********************
				holder.btn_midel_invisible.setVisibility(View.GONE);
				holder.btn_cancel_order.setVisibility(View.VISIBLE);
			} else if (booking_status.equalsIgnoreCase("8")) {
				holder.txv_status.setText(R.string.TG_status_cancel_by_user);
				holder.txv_tg_main_status
						.setText(R.string.TG_status_cancel_by_user);
				holder.btn_Sendconfirm.setVisibility(View.GONE);
				holder.btn_DonotConfirm.setVisibility(View.GONE);
				// **********************
				holder.btn_midel_invisible.setVisibility(View.GONE);
				holder.btn_cancel_order.setVisibility(View.VISIBLE);
			}
			// booking date
			holder.txv_booking_date.setText(booking_date);

			holder.img_plus.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
						holder.img_plus.setVisibility(View.GONE);
						holder.img_minus.setVisibility(View.VISIBLE);
						holder.ll_orderdetails.setVisibility(View.VISIBLE);
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});

			holder.img_minus.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (cd.isConnectingToInternet()) {
						// TODO Auto-generated method stub
						holder.img_plus.setVisibility(View.VISIBLE);
						holder.img_minus.setVisibility(View.GONE);
						holder.ll_orderdetails.setVisibility(View.GONE);
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});
			holder.txv_details.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
						Object_TableGrabbing = getItem(position);
						System.out.println("1111objectonclick"
								+ Object_TableGrabbing);
						try {
							obj_profile = Object_TableGrabbing
									.getJSONObject("profile");
							System.out.println("1111profileonclick"
									+ obj_profile);

							if (Object_TableGrabbing.has("firstname")) {
								str_order_fname = Object_TableGrabbing
										.getString("firstname");
							}
							if (Object_TableGrabbing.has("surname")) {
								str_order_surname = Object_TableGrabbing
										.getString("surname");
							}
							if (Object_TableGrabbing.has("mobile")) {
								str_mobile = Object_TableGrabbing
										.getString("mobile");
							}

							// str_booking_date =
							// Object_TableGrabbing.getString("booking_date");
							// str_booking_time =
							// Object_TableGrabbing.getString("booking_time");
							if (Object_TableGrabbing.has("bussiness")) {
								str_bussiness = Object_TableGrabbing
										.getString("bussiness");
							}
							if (Object_TableGrabbing.has("duration")) {
								str_meal_deliver = Object_TableGrabbing
										.getString("duration");
							}
							if (Object_TableGrabbing.has("occasion")) {
								str_ocsassion = Object_TableGrabbing
										.getString("occasion");
							}

							if (Object_TableGrabbing.has("booking_date")) {
								booking_date = Object_TableGrabbing
										.getString("booking_date");
								System.out.println("booking_date"
										+ booking_date);
							}
							if (Object_TableGrabbing.has("booking_time")) {
								booking_time = Object_TableGrabbing
										.getString("booking_time");
								System.out.println("booking_time"
										+ booking_time);
							}

							// if (obj_profile.length() == 0) {
							// System.out.println("tgadapterelse");
							// Toast.makeText(
							// context,
							// context.getString(R.string.str_No_Data_found),
							// Toast.LENGTH_SHORT).show();
							// } else {
							System.out.println("tgadapterelse");
							Intent i = new Intent(context,
									GrabTableProfileActivity.class);
							String email = "";
							Bundle b = new Bundle();
							if (obj_profile.length() != 0) {
								b.putString("Profile", obj_profile.toString());
								i.putExtras(b);
							}
							email = Object_TableGrabbing.getString("email");
							if (str_order_fname.length() != 0) {
								i.putExtra("firstname",
										str_order_fname.toString());
							}

							if (str_order_surname.length() != 0) {
								i.putExtra("surname",
										str_order_surname.toString());
							}
							if (email.length() != 0) {
								i.putExtra("email", email.toString());
							}
							if (str_mobile.length() != 0) {
								i.putExtra("phone", str_mobile.toString());
							}
							if (str_bussiness.length() != 0) {
								i.putExtra("bussiness",
										str_bussiness.toString());
							}
							if (booking_date.length() != 0) {
								i.putExtra("date", booking_date.toString());
							}
							if (booking_time.length() != 0) {
								i.putExtra("time", booking_time.toString());
							}
							if (str_ocsassion.length() != 0) {
								i.putExtra("occassions",
										str_ocsassion.toString());
							}

							if (str_meal_deliver.length() != 0) {

								i.putExtra("duration",
										str_meal_deliver.toString());
							}

							context.startActivity(i);
							// }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});
			holder.btn_Sendconfirm
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (cd.isConnectingToInternet()) {
								sendconfirm = true;
								str_order_status_tag = "";
								str_order_status_tag = "2";
								// holder.txv_status.setText("");
								// holder.txv_status.setText("Confirmed");
								str_tg_orderID = holder.txv_tg_order_id
										.getText().toString();
								str_email = "";
								str_mobile = "";
								str_email = holder.txv_tg_email.getText()
										.toString();
								str_mobile = holder.txv_tg_mobile.getText()
										.toString();
								System.out.println("ONCLICKSENDEMAIL"
										+ str_email);
								holder.btn_DonotConfirm
										.setVisibility(View.GONE);
								holder.btn_Sendconfirm.setVisibility(View.GONE);
								holder.btn_not_show.setVisibility(View.VISIBLE);
								holder.btn_finish.setVisibility(View.VISIBLE);
								// btn_donot_send_conf_INVISIBLE.setVisibility(View.VISIBLE);
								new async_confirmation().execute();
							} else {

								// TODO Auto-generated method stub
								Toast.makeText(context,
										R.string.No_Internet_Connection,
										Toast.LENGTH_SHORT).show();

							}
						}
					});

			holder.btn_DonotConfirm
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (cd.isConnectingToInternet()) {
								str_order_status_tag = "";
								str_order_status_tag = "6";
								// holder.txv_status.setText("");
								// holder.txv_status.setText("Cancel");
								str_tg_orderID = holder.txv_tg_order_id
										.getText().toString();
								str_email = "";
								str_mobile = "";
								str_email = holder.txv_tg_email.getText()
										.toString();
								System.out.println("ONCLICKDONOTSENDEMAIL"
										+ str_email);
								str_mobile = holder.txv_tg_mobile.getText()
										.toString();
								holder.btn_DonotConfirm
										.setVisibility(View.GONE);
								holder.btn_Sendconfirm.setVisibility(View.GONE);
								holder.btn_midel_invisible
										.setVisibility(View.GONE);
								holder.btn_cancel_order
										.setVisibility(View.VISIBLE);
								new async_confirmation().execute();
							} else {

								// TODO Auto-generated method stub
								Toast.makeText(context,
										R.string.No_Internet_Connection,
										Toast.LENGTH_SHORT).show();

							}
						}
					});

			holder.btn_not_show.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
						str_order_status_tag = "";
						str_order_status_tag = "3";
						str_tg_orderID = holder.txv_tg_order_id.getText()
								.toString();
						str_email = "";
						str_mobile = "";
						str_email = holder.txv_tg_email.getText().toString();
						System.out.println("ONCLICKDONOTSENDEMAIL" + str_email);
						str_mobile = holder.txv_tg_mobile.getText().toString();
						new async_confirmation().execute();
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});
			holder.btn_finish.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
						str_order_status_tag = "";
						str_order_status_tag = "4";
						str_tg_orderID = holder.txv_tg_order_id.getText()
								.toString();
						str_email = "";
						str_mobile = "";
						str_email = holder.txv_tg_email.getText().toString();
						System.out.println("ONCLICKDONOTSENDEMAIL" + str_email);
						str_mobile = holder.txv_tg_mobile.getText().toString();
						new async_confirmation().execute();
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});

		} catch (JSONException e) {

		} catch (NullPointerException np) {

		}

		return vi;
	}

	public void filter(String charText1) {
		// TODO Auto-generated method stub

		String charText = charText1.toLowerCase(Locale.getDefault());
		System.out.println("1111chartext" + charText);

		JSONArray array_online_food_order1 = new JSONArray();
		array_online_food_order1 = Global_variable.array_online_table_grabbing;

		System.out.println("1111arrayfoodorder" + array_online_food_order1);

		Global_variable.array_online_table_grabbing = new JSONArray();

		// routelist.clear();
		if (charText.length() == 0) {
			// array_online_food_order1=Global_variable.array_online_food_order;
			Global_variable.array_online_table_grabbing = array_online_food_order1;
			System.out.println("1111foodorder.le"
					+ Global_variable.array_online_table_grabbing);
		} else {

			// String searchString = constraint.toString();

			int mCount = array_online_food_order1.length();
			System.out.println("1111mcount" + mCount);
			for (int i = 0; i < mCount; i++) {
				JSONObject obj;
				try {
					obj = array_online_food_order1.getJSONObject(i);
					System.out.println("1111getobjoffoodorder" + obj);
					String order_registered = obj.getString("booking_date");
					String splite_date = order_registered.substring(0, 7);
					System.out.println("1111dateorderobj" + order_registered);
					System.out.println("111111chartext" + charText);
					if (splite_date.equals(charText)) {

						System.out.println("!!!!splitDate" + order_registered
								+ "--->" + charText);
						System.out.println("ketlivargyu");
						Global_variable.array_online_table_grabbing.put(obj);
						System.out.println("afterifsplittedchar"
								+ Global_variable.array_online_table_grabbing);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		System.out.println("afterifsplittedchar_pankaj"
				+ Global_variable.array_online_table_grabbing);
		notifyDataSetChanged();
	}

	public class async_confirmation extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_confirmation;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			p = new ProgressDialog(context);
			// p.setMessage("Please wait...");
			p.setCancelable(false);
			p.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			obj_confirmation = new JSONObject();
			JSONObject obj_order_confirm = new JSONObject();
			try {

				// donot send*****
				System.out.println("1111restidinGT"
						+ Global_variable.restaurant_id);
				obj_order_confirm.put("restaurant_id",
						Global_variable.restaurant_id);
				obj_order_confirm.put("tg_order_id", str_tg_orderID);
				obj_order_confirm.put("email", str_email);
				obj_order_confirm.put("mobile_no", str_mobile);
				System.out.println("99DONOTsendconfirm");
				obj_order_confirm.put("booking_status", str_order_status_tag);
				System.out.println("1111obj_restaurant_order"
						+ obj_order_confirm);
				obj_confirmation
						.put("order_tg_conformation", obj_order_confirm);
				obj_confirmation.put("sessid", Global_variable.sessid);
				System.out.println("11111tgDONOTconfirm" + obj_confirmation);

				// obj_MainRequest*******************************
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {

				// *************
				// for request
				try {
					myconnection con = new myconnection();
					// String str_response = con.connection(context,
					// Global_variable.rf_api_confirmation,
					// obj_confirmation);

					json = new JSONObject(con.connection(context,
							Global_variable.rf_api_confirmation,
							obj_confirmation));
					System.out.println("1111finaljsonstepTG" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i("Parse Exception", e + "");

				} catch (NullPointerException np) {

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NullPointerException np) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog

			if (p.isShowing())
				p.dismiss();
			try {
				if (json != null) {
					String STR_DATE = null;
					jsonSuccessStr = json.getString("success");
					Global_variable.sessid = json.getString("sessid");

					System.out.println("1111sessidstep2respo"
							+ Global_variable.sessid);
					if (jsonSuccessStr.equalsIgnoreCase("true")) {
						Global_variable.array_online_table_grabbing = new JSONArray();
						JSONObject Data = json.getJSONObject("data");
						System.out.println("1111obj_Data" + Data);
						if (Data != null) {
							if (Data.has("online_table_grabbing"))

							{
								Global_variable.array_online_table_grabbing = Data
										.getJSONArray("online_table_grabbing");
								System.out
										.println("1111array_online_table_grabbing_senddonot"
												+ Global_variable.array_online_table_grabbing);
								refresAdapter(Global_variable.array_online_table_grabbing);
							}
						}

					} else {
						JSONObject Error = json.getJSONObject("errors");
						System.out.println("1111errors" + Error);
						if (Error != null) {
							if (Error.has("date_time")) {
								str_order_date_time = Error
										.getString("date_time");
								OrderDialog();
							}
						}
					}
				} else {
					// Toast.makeText(GrabTable_Activity.this, "No Data Found",
					// Toast.LENGTH_LONG).show();
				}
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1111success" + jsonSuccessStr);
		}

	}

	public synchronized void refresAdapter(JSONArray array) {
		array_online_table_grabbing = new JSONArray();
		System.out.println("gyurefresh ma");
		array_online_table_grabbing = Global_variable.array_online_table_grabbing;
		System.out.println("reloadadapt" + array_online_table_grabbing);
		System.out.println("dateinadapter" + GrabTable_Activity.STR_Date);

		// GrabTable_Activity.async_getrest_order A=ACT.new
		// async_getrest_order();
		// A.execute();
		JSONArray array1 = null;
		array1 = new JSONArray();
		array1 = Global_variable.array_online_table_grabbing;
		if (Global_variable.array_online_table_grabbing.length() != 0) {
			if (GrabTable_Activity.STR_Date != null) {

				GrabTable_Activity.GrabTableAdapter
						.filter(GrabTable_Activity.STR_Date);
				System.out.println("1111lenthorderfoodafter_in_if_loop"
						+ Global_variable.array_online_table_grabbing);
				if (Global_variable.array_online_table_grabbing.length() != 0) {
					System.out.println("ifmagyuknai");
					GrabTable_Activity.lv_gt_order.setVisibility(View.VISIBLE);
					GrabTable_Activity.txv_invisible.setVisibility(View.GONE);
					GrabTable_Activity.GrabTableAdapter = new GrabTableAdapter(
							context,
							Global_variable.array_online_table_grabbing);
					GrabTable_Activity.lv_gt_order
							.setAdapter(GrabTable_Activity.GrabTableAdapter);
				} else {
					GrabTable_Activity.lv_gt_order.setVisibility(View.GONE);
					GrabTable_Activity.txv_invisible
							.setVisibility(View.VISIBLE);

					// Toast.makeText(GrabTable_Activity.this, "No Data Found",
					// Toast.LENGTH_LONG).show();
				}

			}
		}
		Global_variable.array_online_table_grabbing = array1;
		// notifyDataSetChanged();

		System.out.println("afterconfirmation"
				+ Global_variable.array_online_table_grabbing);
	}
	public void OrderDialog() {

		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_getin);
		txv_msg = (TextView) dialog.findViewById(R.id.txv_success);
		img_popupOk = (ImageView) dialog.findViewById(R.id.img_popupok);
		txv_msg.setText(str_order_date_time);
		System.out.println("msg" + str_order_date_time);
		try {

			img_popupOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent ii = new Intent(context, TakeBooking_Tablayout.class);
					context.startActivity(ii);
				}
			});

			dialog.show();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
	}
}
