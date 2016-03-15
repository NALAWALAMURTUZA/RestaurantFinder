package com.restaurantadmin.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantadmin.ConnectionDetector;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllBookingAdapter extends BaseAdapter {

	private static Activity context;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray array_AllBooking;
	// ********profile
	public static String str_numberof_people, booking_date, booking_time,
			booking_status, table_no, str_first_name, str_last_name;
	// **********order
	public static String str_type, str_service, str_Date, str_order_Status,
			str_id;
	JSONObject Object_AllOrder;
	public int int_service_select;
	public String str_selected_service;
	ArrayAdapter<CharSequence> adapter;
	JSONObject obj_order;
	Dialog DeleteDialog;
	public static TextView txv_Dialogtext;
	public static ImageView img_yes, img_no;

	public static ProgressDialog p;
	ConnectionDetector cd;
	/*** Network Connection Instance **/
	public AllBookingAdapter(Activity a, JSONArray array_AllBooking) {

		System.out.println("malyoonliefodd" + array_AllBooking);
		context = a;
		this.array_AllBooking = array_AllBooking;
		System.out.println("1111array_online_food_order" + array_AllBooking);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(context);
		/* create Object* */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array_AllBooking.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = array_AllBooking.getJSONObject(position);
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
		public TextView txv_date, txv_type, txv_custname, txv_no_per,
				txv_status, txv_id;
//		public ImageView img_delete;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		final ViewHolder holder;
		vi = inflater.inflate(R.layout.activity_all_booking_rawfile, parent,
				false);
		holder = new ViewHolder();
		// Product p = getProduct(position);
		// ((TextView) vi.findViewById(R.id.txv_servicename)).setText(p.name);
		holder.txv_date = (TextView) vi.findViewById(R.id.txv_date);
		holder.txv_type = (TextView) vi.findViewById(R.id.txv_type);
		holder.txv_custname = (TextView) vi.findViewById(R.id.txv_customername);
		holder.txv_no_per = (TextView) vi.findViewById(R.id.txv_no_per);
		holder.txv_status = (TextView) vi.findViewById(R.id.txv_status);
//		holder.img_delete = (ImageView) vi.findViewById(R.id.img_delete);
		holder.txv_id = (TextView) vi.findViewById(R.id.txv_id);
		vi.setTag(holder);
		try {
			Object_AllOrder = getItem(position);
			System.out.println("1111allbookingadapter" + Object_AllOrder);

			if (Object_AllOrder.has("type")) {
				try {
					str_type = Object_AllOrder.getString("type");

					// *********************
					// ********OO OO OO OO OO OO***************
					// ***********
					if (str_type.equalsIgnoreCase("oo")) {

						holder.txv_type.setText("OO");
						if (Object_AllOrder.has("booking_date")) {
							try {
								String str_splited_date = Object_AllOrder
										.getString("booking_date");
								str_Date = str_splited_date.substring(0, 10);
								holder.txv_date.setText(str_Date);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// *****************************

						if (Object_AllOrder.has("first_name")) {
							try {
								str_first_name = Object_AllOrder
										.getString("first_name");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (Object_AllOrder.has("last_name")) {
							try {
								str_last_name = Object_AllOrder
										.getString("last_name");
								holder.txv_custname.setText(str_first_name
										+ " " + str_last_name);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// ********************************

						if (Object_AllOrder.has("booking_status")) {
							try {
								str_order_Status = Object_AllOrder
										.getString("booking_status");
								holder.txv_status.setText(str_order_Status);
								
//								OO STATUS 28_8 CHAMGES
								
								 if (str_order_Status
										.equalsIgnoreCase("Waiting")) {
									holder.txv_status.setText(context.getString(R.string.OO_statusWaiting));
								}

								else if (str_order_Status
										.equalsIgnoreCase("Confirmed")) {
									holder.txv_status.setText(context.getString(R.string.TG_status_confirmed));
								}
								else if (str_order_Status
										.equalsIgnoreCase("Cancel")) {
									holder.txv_status.setText(context.getString(R.string.TG_status_cancel));
								}else if (str_order_Status
										.equalsIgnoreCase("CancelUser")) {
									holder.txv_status.setText(context.getString(R.string.TG_status_cancel_by_user));
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// *id********
						if (Object_AllOrder.has("id")) {
							try {
								str_id = "";
								str_id = Object_AllOrder.getString("id");
								holder.txv_id.setText(str_id);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					// *********************
					// ********OO OO OO OO OO OO***************
					// ***********
					// *********************

					// *********************
					// ********TG TG TG TG TG TG***************
					// ***********
					else {
						holder.txv_type.setText(context.getString(R.string.str_TG));
						if (Object_AllOrder.has("booking_date")) {
							try {
								String str_splited_date = Object_AllOrder
										.getString("booking_date");
								str_Date = str_splited_date.substring(0, 10);
								holder.txv_date.setText(str_Date);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (Object_AllOrder.has("first_name")) {
							try {
								str_first_name = Object_AllOrder
										.getString("first_name");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (Object_AllOrder.has("last_name")) {
							try {
								str_last_name = Object_AllOrder
										.getString("last_name");
								holder.txv_custname.setText(str_first_name
										+ " " + str_last_name);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						if (Object_AllOrder.has("number_of_people")) {
							try {
								str_numberof_people = Object_AllOrder
										.getString("number_of_people");
								holder.txv_no_per.setText(str_numberof_people);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (Object_AllOrder.has("booking_status")) {
							try {
								str_order_Status = Object_AllOrder
										.getString("booking_status");
								if (str_order_Status.equalsIgnoreCase("1")) {
									holder.txv_status.setText(context.getString(R.string.str_Pending));
								} else if (str_order_Status
										.equalsIgnoreCase("2")) {
									holder.txv_status.setText(context.getString(R.string.str_Confirmed));
								} else if (str_order_Status
										.equalsIgnoreCase("3")) {
									holder.txv_status.setText(context.getString(R.string.str_Not_Show));
								} else if (str_order_Status
										.equalsIgnoreCase("4")) {
									holder.txv_status.setText(context.getString(R.string.str_Review));
								} else if (str_order_Status
										.equalsIgnoreCase("5")) {
									holder.txv_status.setText(context.getString(R.string.str_Over));
								} else if (str_order_Status
										.equalsIgnoreCase("6")) {
									holder.txv_status.setText(context.getString(R.string.str_Cancel));
								}
								else if (str_order_Status
										.equalsIgnoreCase("7")) {
									holder.txv_status.setText(context.getString(R.string.str_Not_Show));
								}
								else if (str_order_Status
										.equalsIgnoreCase("8")) {
									holder.txv_status.setText(context.getString(R.string.TG_status_cancel_by_user));
								}
								


							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// *id********
						if (Object_AllOrder.has("id")) {
							try {
								str_id = "";
								str_id = Object_AllOrder.getString("id");
								holder.txv_id.setText(str_id);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
					// *********************
					// ********TG TG TG TG TG TG***************
					// ***********
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}



		} catch (NullPointerException np) {

		}

		return vi;
	}

}
