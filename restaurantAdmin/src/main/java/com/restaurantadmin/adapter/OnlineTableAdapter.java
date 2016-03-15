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
import com.example.restaurantadmin.OnlineOrderOrderDetailsActivity;
import com.example.restaurantadmin.OnlineTable_Activity;
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class OnlineTableAdapter extends BaseAdapter {

	private static Activity context;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray array_online_food_order;
	// ********profile
	public static String str_numberof_people, booking_date, booking_time,
			booking_status, table_no, str_first_name, str_last_name,
			str_order_email, str_order_mobile;
	// **********order
	public static String str_order_no, str_orderid, str_payment_status,
			str_service_type, str_order_comment, str_service, str_Date,
			str_order_Status, str_Time;
	JSONObject Object_FoodOrder;
	public int int_service_select;
	public String str_selected_service;
	ArrayAdapter<CharSequence> adapter;
	JSONObject obj_order;
	public static ProgressDialog p;
	public static TextView txv_Dialogtext, txv_msg;
	public static ImageView img_yes, img_no, img_popupOk;
	public static Dialog dialog;
	public static String str_order_date_time;
	ConnectionDetector cd;
	/*** Network Connection Instance **/
	public OnlineTableAdapter(Activity a, JSONArray array_online_food_order) {

		System.out.println("malyoonliefodd" + array_online_food_order);
		context = a;
		this.array_online_food_order = array_online_food_order;
		System.out.println("1111array_online_food_order"
				+ array_online_food_order);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(context);
		/* create Object* */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array_online_food_order.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = array_online_food_order.getJSONObject(position);
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
		public TextView txv_datetime_persone, txv_personename, txv_order_no,
				txv_service_charge, txv_comment, txv_orderDate, txv_orderTime,
				txv_orderid, txv_main_status, txv_email, txv_mobile;
		public LinearLayout ll_orderdetails;
		public ImageView img_plus, img_minus;
		public TextView txv_payment_status, txv_servicetype, txv_order_status,
				txv_Order;
		public Button btn_send_confirme, btn_decline_order;
		public Button btn_confirme_status, btn_cancel_status;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		final ViewHolder holder;
		vi = inflater.inflate(R.layout.activity_online_table_rawfile, parent,
				false);
		holder = new ViewHolder();
		// Product p = getProduct(position);
		// ((TextView) vi.findViewById(R.id.txv_servicename)).setText(p.name);
		holder.txv_Order = (TextView) vi.findViewById(R.id.txv_Order);
		holder.txv_datetime_persone = (TextView) vi
				.findViewById(R.id.txv_datetime_persone);
		holder.txv_personename = (TextView) vi
				.findViewById(R.id.txv_personename);
		holder.txv_order_no = (TextView) vi.findViewById(R.id.txv_order_no);
		holder.txv_service_charge = (TextView) vi
				.findViewById(R.id.txv_service_charge);
		holder.txv_comment = (TextView) vi.findViewById(R.id.txv_comment);
		holder.ll_orderdetails = (LinearLayout) vi
				.findViewById(R.id.ll_order_details);
		holder.img_plus = (ImageView) vi.findViewById(R.id.img_plus);
		holder.img_minus = (ImageView) vi.findViewById(R.id.img_minus);
		holder.txv_payment_status = (TextView) vi
				.findViewById(R.id.txv_payment_status);
		holder.txv_servicetype = (TextView) vi
				.findViewById(R.id.txv_servicetype);
		holder.txv_order_status = (TextView) vi
				.findViewById(R.id.txv_order_status);
		holder.txv_orderDate = (TextView) vi.findViewById(R.id.txv_date);
		holder.txv_orderTime = (TextView) vi.findViewById(R.id.txv_time);
		holder.txv_email = (TextView) vi.findViewById(R.id.txv_oo_email);
		holder.txv_mobile = (TextView) vi.findViewById(R.id.txv_oo_mobile);
		// **************status button
		holder.btn_send_confirme = (Button) vi
				.findViewById(R.id.btn_confirm_order);
		holder.btn_decline_order = (Button) vi
				.findViewById(R.id.btn_decline_order);
		holder.btn_confirme_status = (Button) vi
				.findViewById(R.id.btn_confirme_status);
		holder.btn_cancel_status = (Button) vi
				.findViewById(R.id.btn_cancel_status);
		holder.txv_orderid = (TextView) vi.findViewById(R.id.txv_orderid);
		holder.txv_main_status = (TextView) vi
				.findViewById(R.id.txv_main_status);
		// **********************************************
		vi.setTag(holder);
		try {
			Object_FoodOrder = getItem(position);
			System.out.println("1111object_OOORADAPTER" + Object_FoodOrder);
			JSONObject obj_profile = Object_FoodOrder.getJSONObject("profile");
			obj_order = Object_FoodOrder.getJSONObject("order");
			System.out.println("1111obj_order" + obj_order);
			System.out.println("1111obj_profile" + obj_profile);

			// **********order
			// *****************
			str_order_mobile = obj_profile.getString("profile_mobile_number");
			str_order_no = obj_order.getString("order_uid");
			str_orderid = obj_order.getString("order_id");
			holder.txv_orderid.setText(str_orderid);
			str_order_email = obj_order.getString("order_email");
			holder.txv_email.setText(str_order_email);
			holder.txv_mobile.setText(str_order_mobile);
			str_payment_status = obj_order.getString("order_payment_status");
			// paymen Status**************
			// 0=Not paid
			// 1=paid
			str_order_comment = obj_order.getString("order_comments");
			String str = obj_order.getString("order_registered");
			System.out.println("str11111111" + str);
			str_Date = str.substring(0, 10);
			System.out.println("adapterdate" + str_Date);
			str_Time = str.substring(11, str.length());
			holder.txv_orderDate.setText(str_Date);
			holder.txv_orderTime.setText(str_Time);
			str_service_type = obj_order.getString("order_delivery_ok");
			// *****order_order_status
			// Confirmed
			// Waiting
			// Cancel
			str_order_Status = obj_order.getString("order_order_status");

			if (str_order_Status.equalsIgnoreCase("Waiting")) {
				holder.txv_order_status.setText(R.string.OO_statusWaiting);
				holder.txv_main_status.setText(R.string.OO_statusWaiting);
				holder.btn_send_confirme.setVisibility(View.VISIBLE);
				holder.btn_decline_order.setVisibility(View.VISIBLE);
				holder.btn_confirme_status.setVisibility(View.INVISIBLE);
				holder.btn_cancel_status.setVisibility(View.GONE);
			} else if (str_order_Status.equalsIgnoreCase("Confirmed")) {
				holder.txv_order_status.setText(R.string.oorawfile_coo);
				holder.txv_main_status.setText(R.string.oorawfile_coo);
				holder.btn_send_confirme.setVisibility(View.GONE);
				holder.btn_decline_order.setVisibility(View.GONE);
				holder.btn_confirme_status.setVisibility(View.VISIBLE);
				holder.btn_cancel_status.setVisibility(View.GONE);
			} else if (str_order_Status.equalsIgnoreCase("Cancel")) {
				holder.txv_order_status.setText(R.string.oorawfile_cancelorder);
				holder.txv_main_status.setText(R.string.oorawfile_cancelorder);
				holder.btn_send_confirme.setVisibility(View.GONE);
				holder.btn_decline_order.setVisibility(View.GONE);
				holder.btn_confirme_status.setVisibility(View.GONE);
				holder.btn_cancel_status.setVisibility(View.VISIBLE);
			} else if (str_order_Status.equalsIgnoreCase("CancelUser")) {
				holder.txv_order_status
						.setText(R.string.TG_status_cancel_by_user);
				holder.txv_main_status
						.setText(R.string.TG_status_cancel_by_user);
				holder.btn_send_confirme.setVisibility(View.GONE);
				holder.btn_decline_order.setVisibility(View.GONE);
				holder.btn_confirme_status.setVisibility(View.GONE);
				holder.btn_cancel_status.setVisibility(View.VISIBLE);
			}
			// servicetype******************
			// 0= pickup
			// 1=delivery
			// *******************
			// ***********profile
			// ********************
			str_first_name = obj_profile.getString("profile_first_name");
			System.out.println("str_first_name" + str_first_name);
			str_last_name = obj_profile.getString("profile_last_name");
			System.out.println("str_last_name" + str_last_name);
			// paymetstuts*
			if (str_payment_status.equalsIgnoreCase("0")) {
				holder.txv_payment_status.setText(R.string.oo_notpaid);
			} else {
				holder.txv_payment_status.setText(R.string.oo_paid);
			}
			// service process**********
			if (str_service_type.equalsIgnoreCase("0")) {
				holder.txv_servicetype.setText(R.string.oo_pickup);
			} else {
				holder.txv_servicetype.setText(R.string.oo_del);
			}
			// comment******
			if (str_order_comment.length() != 0
					&& !str_order_comment.equalsIgnoreCase("null")) {
				holder.txv_comment.setText(str_order_comment);
			}
			// // order Status*********
			// holder.txv_order_status.setText(str_order_Status);
			// orderid******
			holder.txv_order_no.setText(str_order_no);
			// order date****
			holder.txv_datetime_persone.setText(str);
			// personename*********
			holder.txv_personename
					.setText(str_first_name + " " + str_last_name);
			holder.txv_Order.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
						Object_FoodOrder = getItem(position);
						System.out.println("1111object_OOORADAPTERonclick"
								+ Object_FoodOrder);
						try {
							obj_order = Object_FoodOrder.getJSONObject("order");

							System.out.println("1111obj_orderonclick"
									+ obj_order);
							JSONArray array_cart_item = Object_FoodOrder
									.getJSONArray("cart_item");
							System.out.println("1111onclickcartiteam"
									+ array_cart_item);
							JSONObject obj_profile = Object_FoodOrder
									.getJSONObject("profile");
							System.out.println("oodprofile" + obj_profile);

							JSONObject obj_shipment = Object_FoodOrder
									.getJSONObject("shipment");
							System.out
									.println("oodobj_shipment" + obj_shipment);

							if (obj_order.length() == 0) {
								Toast.makeText(context,
										R.string.str_No_Data_found,
										Toast.LENGTH_SHORT).show();
							} else {
								Intent obj_intent = new Intent(context,
										OnlineOrderOrderDetailsActivity.class);
								Bundle b = new Bundle();
								b.putString("Order", obj_order.toString());
								b.putString("CartItem",
										array_cart_item.toString());
								b.putString("Shipment", obj_shipment.toString());
								b.putString("Profile", obj_profile.toString());
								obj_intent.putExtras(b);
								context.startActivity(obj_intent);
							}
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
					// TODO Auto-generated method stub
					if (cd.isConnectingToInternet()) {
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
			// **************status*************
			holder.btn_send_confirme
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (cd.isConnectingToInternet()) {
								str_orderid = "";
								str_orderid = holder.txv_orderid.getText()
										.toString();
								System.out.println("orderidCONFIRMEclick"
										+ str_orderid);
								str_order_Status = "";
								str_order_Status = "Confirmed";
								str_order_email = "";
								str_order_mobile = "";
								str_order_email = holder.txv_email.getText()
										.toString();
								str_order_mobile = holder.txv_mobile.getText()
										.toString();
								// holder.txv_order_status.setText(str_order_Status);
								System.out.println("orderstatusCONFIRMEclick"
										+ str_order_Status);
								holder.btn_send_confirme
										.setVisibility(View.GONE);
								holder.btn_decline_order
										.setVisibility(View.GONE);
								holder.btn_confirme_status
										.setVisibility(View.VISIBLE);
								holder.btn_cancel_status
										.setVisibility(View.GONE);
								new async_order_status().execute();
							} else {

								// TODO Auto-generated method stub
								Toast.makeText(context,
										R.string.No_Internet_Connection,
										Toast.LENGTH_SHORT).show();

							}
						}
					});
			holder.btn_decline_order
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (cd.isConnectingToInternet()) {
								str_orderid = "";
								str_orderid = holder.txv_orderid.getText()
										.toString();
								System.out.println("orderidDECLINEclick"
										+ str_orderid);
								str_order_Status = "";
								str_order_Status = "Cancel";
								str_order_email = "";
								str_order_mobile = "";
								str_order_email = holder.txv_email.getText()
										.toString();
								str_order_mobile = holder.txv_mobile.getText()
										.toString();
								// holder.txv_order_status.setText(str_order_Status);
								System.out.println("orderstatusDECLINEclick"
										+ str_order_Status);
								holder.btn_send_confirme
										.setVisibility(View.GONE);
								holder.btn_decline_order
										.setVisibility(View.GONE);
								holder.btn_confirme_status
										.setVisibility(View.GONE);
								holder.btn_cancel_status
										.setVisibility(View.VISIBLE);
								new async_order_status().execute();
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
		array_online_food_order1 = Global_variable.array_online_food_order;
		// for(int m=0;m<=Global_variable.array_online_food_order.length();m++)
		// {
		// array_online_food_order1.put(m);
		// }
		// array_online_food_order1.put(Global_variable.array_online_food_order);

		System.out.println("1111arrayfoodorder" + array_online_food_order1);

		Global_variable.array_online_food_order = new JSONArray();

		// routelist.clear();
		if (charText.length() == 0) {
			// array_online_food_order1=Global_variable.array_online_food_order;
			Global_variable.array_online_food_order = array_online_food_order1;
			System.out.println("1111foodorder.le"
					+ Global_variable.array_online_food_order);
		} else {

			// String searchString = constraint.toString();

			int mCount = array_online_food_order1.length();
			System.out.println("1111mcount" + mCount);
			for (int i = 0; i < mCount; i++) {
				JSONObject obj;
				try {
					obj = array_online_food_order1.getJSONObject(i);
					System.out.println("1111getobjoffoodorder" + obj);
					JSONObject obj_order = obj.getJSONObject("order");
					System.out.println("1111orderobj" + obj_order);
					// *********order status

					// date****
					String order_registered = obj_order
							.getString("order_registered");
					System.out.println("1111dateorderobj" + order_registered);
					String str_splited_date = order_registered.substring(0, 7);
					System.out.println("111111splittedstring"
							+ str_splited_date);
					System.out.println("111111chartext" + charText);
					if (str_splited_date.equals(charText)) {

						System.out.println("!!!!splitDate" + str_splited_date
								+ "--->" + charText);
						System.out.println("ketlivargyu");
						Global_variable.array_online_food_order.put(obj);
						System.out.println("afterifsplittedchar"
								+ Global_variable.array_online_food_order);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		System.out.println("afterifsplittedchar_pankaj"
				+ Global_variable.array_online_food_order);
		notifyDataSetChanged();

	}

	// ***********ORDER STATUS ASYNCH

	public class async_order_status extends AsyncTask<Void, Void, Void> {

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
				// obj_order_confirm.put("restaurant_id", "74");
				obj_order_confirm.put("id", str_orderid);
				obj_order_confirm.put("email", str_order_email);
				obj_order_confirm.put("mobile_no", str_order_mobile);
				obj_order_confirm.put("order_status", str_order_Status);

				System.out.println("1111obj_restaurant_order"
						+ obj_order_confirm);
				obj_confirmation.put("oo_status_confirmation",
						obj_order_confirm);
				obj_confirmation.put("sessid", Global_variable.sessid);
				System.out.println("requestobject" + obj_confirmation);

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
					String str_response = con.connection(context,
							Global_variable.OO_OrderStatus, obj_confirmation);

					json = new JSONObject(str_response);
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
						Global_variable.array_online_food_order = new JSONArray();
						JSONObject Data = json.getJSONObject("data");
						System.out.println("1111obj_Data" + Data);
						if (Data != null) {
							if (Data.has("online_food_order"))

							{
								Global_variable.array_online_food_order = Data
										.getJSONArray("online_food_order");
								System.out
										.println("AFTERRESParray_online_food_order"
												+ Global_variable.array_online_food_order);

								refresAdapter(Global_variable.array_online_food_order);
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
		array_online_food_order = new JSONArray();
		System.out.println("gyurefresh ma");
		array_online_food_order = Global_variable.array_online_food_order;
		System.out.println("reloadadapt" + array_online_food_order);

		// GrabTable_Activity.async_getrest_order A=ACT.new
		// async_getrest_order();
		// A.execute();
		JSONArray array1 = null;
		array1 = new JSONArray();
		array1 = Global_variable.array_online_food_order;
		if (Global_variable.array_online_food_order.length() != 0) {
			if (OnlineTable_Activity.STR_Date != null) {

				OnlineTable_Activity.OnlineTableAdapter
						.filter(OnlineTable_Activity.STR_Date);
				if (Global_variable.array_online_food_order.length() != 0) {
					System.out.println("ifmagyuknai");
					OnlineTable_Activity.lv_online_order
							.setVisibility(View.VISIBLE);
					OnlineTable_Activity.txv_invisible.setVisibility(View.GONE);
					OnlineTable_Activity.OnlineTableAdapter = new OnlineTableAdapter(
							context, Global_variable.array_online_food_order);
					OnlineTable_Activity.lv_online_order
							.setAdapter(OnlineTable_Activity.OnlineTableAdapter);
				} else {
					OnlineTable_Activity.lv_online_order
							.setVisibility(View.GONE);
					OnlineTable_Activity.txv_invisible
							.setVisibility(View.VISIBLE);
					// Toast.makeText(OnlineTable_Activity.this,
					// "No Data Found", Toast.LENGTH_LONG)
					// .show();
				}

			}
		}
		System.out.println("1111lenthorderfoodafter"
				+ Global_variable.array_online_food_order);

		Global_variable.array_online_food_order = array1;

		System.out.println("1111lenthorderfoodafter_add"
				+ Global_variable.array_online_food_order);
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
					refresAdapter(Global_variable.array_online_food_order);
				}
			});

			dialog.show();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
		} catch (NullPointerException n) {

		}
	}
}
