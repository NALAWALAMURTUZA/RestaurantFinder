package com.rf_user.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.activity.AboutRestaurant;
import com.rf_user.activity.ImageLoader;
import com.rf_user.activity.MyBookedCart;
import com.rf_user.activity.MyBooking;
import com.rf_user.activity.PostReview;
import com.rf_user.async_common_class.CancelOrder;
import com.rf_user.connection.HttpConnection;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;

public class MyBookingAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public Context context = null;
	public ImageLoader imageLoader;
	int layoutResID;
	String TAG_SUCCESS = "success";
	ConnectionDetector cd;
	public String order_type;
	HttpConnection http = new HttpConnection();

	public MyBookingAdapter(Activity a, ArrayList<HashMap<String, String>> d,
			String type) {
		try {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(activity.getApplicationContext());
			order_type = type;
			/* create Object* */
			cd = new ConnectionDetector(activity.getApplicationContext());

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

	@Override
	public View getView(int position, final View convertView, ViewGroup parent) {
		View vi = convertView;

		vi = inflater.inflate(R.layout.my_booking_rawfile, parent, false);
		TextView txt_rest_name = (TextView) vi.findViewById(R.id.txt_rest_name);
		final TextView status = (TextView) vi.findViewById(R.id.status);
		final TextView txt_city_country_name = (TextView) vi
				.findViewById(R.id.txt_city_country_name);

		TextView txt_date = (TextView) vi.findViewById(R.id.txt_date);
		TextView txt_time = (TextView) vi.findViewById(R.id.txt_time);
		TextView txt_no_of_pers = (TextView) vi
				.findViewById(R.id.txt_no_of_pers);

		TextView txt_discount = (TextView) vi.findViewById(R.id.discount);

		TextView order_number = (TextView) vi.findViewById(R.id.order_number);
		TextView order_details = (TextView) vi.findViewById(R.id.order_details);

		ImageView img_detail = (ImageView) vi.findViewById(R.id.img_detail);
		ImageView img_cancel = (ImageView) vi.findViewById(R.id.img_cancel);
		ImageView img_post_review = (ImageView) vi
				.findViewById(R.id.img_post_review);
		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);
		final String tg_order_id = item.get("tg_order_id");
		System.out.println("In adapter hashmap.." + tg_order_id);
		final String rest_id = item.get("rest_id");
		System.out.println("In adapter hashmap.." + rest_id);
		String user_id = item.get("user_id");
		System.out.println("In adapter hashmap.." + user_id);

		final String booking_date = item.get("booking_date");
		System.out.println("In adapter hashmap.." + booking_date);
		final String booking_time = item.get("booking_time");
		System.out.println("In adapter hashmap.." + booking_time);
		String booking_status = item.get("booking_status");
		System.out.println("In adapter hashmap.." + booking_status);

		final String name_en = item.get("name_en");
		System.out.println("In adapter hashmap.." + name_en);

		String country_id = item.get("country_id");
		System.out.println("In adapter hashmap.." + country_id);

		String city_id = item.get("city_id");
		System.out.println("In adapter hashmap.." + city_id);

		String cname_en = item.get("cname_en");
		System.out.println("In adapter hashmap.." + cname_en);

		String ciname_en = item.get("ciname_en");
		System.out.println("In adapter hashmap.." + ciname_en);

		txt_rest_name.setText(name_en);

		if (order_type == "TG") {
			String booking_mode = item.get("booking_mode");
			System.out.println("In adapter hashmap.." + booking_mode);
			String number_of_people = item.get("number_of_people");
			System.out.println("In adapter hashmap.." + number_of_people);

			txt_no_of_pers.setText(number_of_people + " " + "Pers.");
			order_number.setText(activity.getResources().getString(
					R.string.tgrawfile_bookingid)
					+" "+ tg_order_id);
			if (booking_status.equalsIgnoreCase("1")) {
				booking_status = activity.getResources().getString(R.string.TG_statuspending);
				img_cancel.setVisibility(View.VISIBLE);
				img_post_review.setVisibility(View.GONE);

			} else if (booking_status.equalsIgnoreCase("2")) {
				booking_status = activity.getResources().getString(R.string.TG_status_confirmed);
				img_cancel.setVisibility(View.VISIBLE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("3")) {
				booking_status = activity.getResources().getString(R.string.str_Not_Show);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("4")) {
				booking_status = activity.getResources().getString(R.string.str_Review);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.VISIBLE);
			} else if (booking_status.equalsIgnoreCase("5")) {
				booking_status = activity.getResources().getString(R.string.tgrawfile_finish);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("6")) {
				booking_status = activity.getResources().getString(R.string.TG_status_cancel);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("7")) {
				booking_status = activity.getResources().getString(R.string.str_Not_Show);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("8")) {
				booking_status = activity.getResources().getString(R.string.TG_status_cancel);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			} else {
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			}

		} else {

			txt_no_of_pers.setVisibility(View.INVISIBLE);
//			order_number.setVisibility(View.VISIBLE);
			order_details.setVisibility(View.VISIBLE);

			if (booking_status.equalsIgnoreCase("Waiting")) {
				booking_status = activity.getResources().getString(R.string.OO_statusWaiting);
				img_cancel.setVisibility(View.VISIBLE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("Confirmed")) {
				booking_status = activity.getResources().getString(R.string.oorawfile_coo);
				img_cancel.setVisibility(View.VISIBLE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("CancelUser")) {
				booking_status = activity.getResources().getString(R.string.oorawfile_cancelorder);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			} else if (booking_status.equalsIgnoreCase("Cancel")) {
				booking_status = activity.getResources().getString(R.string.oorawfile_cancelorder);
				img_cancel.setVisibility(View.GONE);
				img_post_review.setVisibility(View.GONE);
			}

			final String uid = item.get("uid");
			System.out.println("uid" + uid);

			final String first_name = item.get("first_name");
			System.out.println("first_name" + first_name);
			final String last_name = item.get("last_name");
			System.out.println("last_name" + last_name);

			final String email = item.get("email");
			System.out.println("email" + email);

			final String contact_number = item.get("contact_number");
			System.out.println("contact_number" + contact_number);

			final String final_total = item.get("final_total");
			System.out.println("final_total" + final_total);

			final String delivery_charge = item.get("delivery_charge");
			System.out.println("delivery_charge" + delivery_charge);

			final String cart_id = item.get("cart_id");
			System.out.println("cart_id" + cart_id);

			order_number.setText(activity.getResources().getString(
					R.string.oo_orderno)
					+" " + uid);

			order_details.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent in = new Intent(activity, MyBookedCart.class);
					in.putExtra("first_name", first_name);
					in.putExtra("last_name", last_name);
					in.putExtra("email", email);
					in.putExtra("contact_number", contact_number);
					in.putExtra("final_total", final_total);
					in.putExtra("delivery_charge", delivery_charge);
					in.putExtra("cart_id", cart_id);

					System.out.println("!!!!!!!!!!!!!!!!!!address adapter"
							+ txt_city_country_name.getText().toString());

					in.putExtra("address", txt_city_country_name.getText()
							.toString());
					in.putExtra("booking_status", status.getText().toString());
					activity.startActivity(in);

				}
			});

		}
		
		System.out.println("!!!!status"+booking_status);

		status.setText(booking_status);
		txt_city_country_name.setText(cname_en + ",  " + Global_variable.blank
				+ ciname_en);
		txt_date.setText(booking_date);
		txt_time.setText(booking_time);

		// txt_discount.setText(str_discount);

		img_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent in = new Intent(activity, AboutRestaurant.class);
					in.putExtra("rest_id", rest_id);
					in.putExtra("rest_name", name_en);
					in.putExtra("activity", "MyBooking");
					activity.startActivity(in);

				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		img_post_review.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent in = new Intent(activity, PostReview.class);
					in.putExtra("rest_name", name_en);
					in.putExtra("tg_order_id", tg_order_id);
					activity.startActivity(in);

				} catch (NullPointerException n) {
					n.printStackTrace();
				}
			}
		});

		img_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				java.sql.Date currDate = new java.sql.Date(System
						.currentTimeMillis());

				Calendar c = Calendar.getInstance();
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minutes = c.get(Calendar.MINUTE);
				int second = c.get(Calendar.SECOND);

				String time = hour + ":" + minutes + ":" + second;

				// java.util.Date today = new java.util.Date();

				// Timestamp curr_time = new
				// java.sql.Timestamp(today.getTime());

				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!curr_time"
						+ time);

				System.out
						.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!curr_time"
								+ time);

				Date date;
				try {
					date = new SimpleDateFormat("HH:mm:ss").parse(time);
					DateFormat outputFormatter = new SimpleDateFormat(
							"HH:mm:ss");
					String selectedtime = outputFormatter.format(date);

					/** check Internet Connectivity */
					if (cd.isConnectingToInternet()) {

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!booking_date"
										+ booking_date);

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!booking_time"
										+ booking_time);

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!selectedtime"
										+ selectedtime);

						if (booking_date.compareTo(currDate.toString()) < 0) {
							System.out.println("!!!!!!!!!!!!!in if...");
							Toast.makeText(activity.getApplicationContext(),
									activity.getString(R.string.order_expired),
									Toast.LENGTH_SHORT).show();
						} else {

							if (booking_date.equalsIgnoreCase(currDate
									.toString())) {
								try {

									System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!comparison1"
											+ booking_time
													.compareTo(selectedtime
															.toString()));

									if (booking_time.compareTo(selectedtime
											.toString()) <= 0) {

										Toast.makeText(
												activity.getApplicationContext(),
												activity.getString(R.string.order_expired),
												Toast.LENGTH_SHORT).show();

										System.out.println("2nd last false");
									} else {
										try {
											Calendar c1 = Calendar
													.getInstance();
											SimpleDateFormat sdf1 = new SimpleDateFormat(
													"HH:mm:ss");
											c1.add(Calendar.HOUR_OF_DAY,
													Integer.parseInt(Global_variable.booking_time_limit));
											String time_value = sdf1.format(c1
													.getTime());
											System.out
													.println("!!!!!!!!!!!!!!!time_value"
															+ time_value);

											System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!comparison2"
													+ booking_time
															.compareToIgnoreCase(time_value));

											if (booking_time
													.compareToIgnoreCase(time_value) > 0) {
												Global_variable.tg_order_id = tg_order_id;
												Global_variable.order_type = order_type;

												new CancelOrder(activity)
														.execute();

												Toast.makeText(
														activity.getApplicationContext(),
														activity.getString(R.string.order_canceled),
														Toast.LENGTH_SHORT)
														.show();

												// MyBooking booking = new
												// MyBooking();
												// booking.new
												// async_cancel_order().execute();
												System.out.println("true");
											} else {
												Toast.makeText(
														activity.getApplicationContext(),
														activity.getString(R.string.order_cancelation_rule_one)
																+ " "
																+ Global_variable.booking_time_limit
																+ " "
																+ activity
																		.getString(R.string.order_cancelation_rule_two),
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("final false");
											}

										} catch (NumberFormatException e) {
											e.printStackTrace();
										}

									}

								} catch (NumberFormatException e) {
									e.printStackTrace();
								}

							} else {
								System.out
										.println("normal flow for cancellation");
								Global_variable.tg_order_id = tg_order_id;
								Global_variable.order_type = order_type;

								new CancelOrder(activity).execute();
								Toast.makeText(
										activity.getApplicationContext(),
										activity.getString(R.string.order_canceled),
										Toast.LENGTH_SHORT).show();
								// MyBooking booking = new MyBooking();
								// booking.new async_cancel_order().execute();
								// new async_cancel_order().execute();
								// normal flow for cancellation
							}

						}

						//

					} else {

						// TODO Auto-generated method stub
						Toast.makeText(activity.getApplicationContext(),
								R.string.No_Internet_Connection,
								Toast.LENGTH_LONG).show();

						// do {
						System.out.println("do-while");
						if (cd.isConnectingToInternet()) {
							// MyBooking booking = new MyBooking();
							// booking.new async_cancel_order().execute();
							// new async_cancel_order().execute();

						}
						// } while (cd.isConnectingToInternet() == false);

					}

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// }
		return vi;

	}

	public class async_cancel_order extends AsyncTask<Void, Void, Void> {
		JSONObject json, data;
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("async_cancel_order  Call");
			// Showing progress dialog

			progressDialog = new ProgressDialog(activity);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				JSONObject cancel_order = new JSONObject();

				try {
					if (SharedPreference.getuser_id(activity
							.getApplicationContext()) != "") {
						if (SharedPreference.getuser_id(
								activity.getApplicationContext()).length() != 0) {
							cancel_order.put("user_id", SharedPreference
									.getuser_id(activity
											.getApplicationContext()));
							System.out.println("cancel_order" + cancel_order);
						}
					} else {
						cancel_order.put("user_id", "");
					}

					if (Global_variable.tg_order_id.length() != 0) {
						cancel_order.put("tg_order_id",
								Global_variable.tg_order_id);
						System.out.println("cancel_order" + cancel_order);
					} else {
						cancel_order.put("tg_order_id", "");
					}

					cancel_order.put("sessid", SharedPreference
							.getsessid(activity.getApplicationContext()));
					System.out.println("cancel_order" + cancel_order);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				// for request
				/* Http Request */

				try {
					String responseText = http.connection(activity,
							Global_variable.rf_cancel_order_api_path,
							cancel_order);

					json = new JSONObject(responseText);
					System.out.println("last_json" + json);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}

			} catch (JSONException e) {
				e.printStackTrace();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}

				try {

					// json success tag
					String success1 = json.getString(TAG_SUCCESS);
					System.out.println("tag" + success1);

					String str_data = json.getString("data");
					System.out.println("Cancel_order_str_data" + str_data);

					if (success1.equals("true")) {
						if (str_data != "[]") {
							data = json.getJSONObject("data");
							System.out
									.println("data_rsponse_categories_parameter"
											+ data);

							if (data != null) {

								String message = data.getString("message");

								// MyBooking booking = new MyBooking();
								// new async_my_booking_list().execute();

								Intent in = new Intent(activity,
										MyBooking.class);
								activity.startActivity(in);

								// MyBooking my_booking = new MyBooking();
								// my_booking.new async_my_booking_list();
								// notifyDataSetChanged();

							}
						}
					} else {

						JSONObject Data_Error = data.getJSONObject("errors");
						System.out.println("Data_Error" + Data_Error);

						if (Data_Error.has("user_id")) {
							JSONArray Array_user_id = Data_Error
									.getJSONArray("user_id");
							System.out.println("Array_user_id" + Array_user_id);
							String Str_user_id = Array_user_id.getString(0);
							System.out.println("Str_user_id" + Str_user_id);
							if (Str_user_id != null) {
								Toast.makeText(
										activity.getApplicationContext(),
										Str_user_id, Toast.LENGTH_LONG).show();
							}
						} else if (Data_Error.has("tg_order_id")) {
							JSONArray Array_tg_order_id = Data_Error
									.getJSONArray("tg_order_id");
							System.out.println("Array_tg_order_id"
									+ Array_tg_order_id);
							String Str_tg_order_id = Array_tg_order_id
									.getString(0);
							System.out.println("Str_tg_order_id"
									+ Str_tg_order_id);
							if (Str_tg_order_id != null) {
								Toast.makeText(
										activity.getApplicationContext(),
										Str_tg_order_id, Toast.LENGTH_LONG)
										.show();
							}
						} else if (Data_Error.has("sessid")) {
							JSONArray Array_sessid = Data_Error
									.getJSONArray("sessid");
							System.out.println("Array_sessid" + Array_sessid);
							String Str_sessid = Array_sessid.getString(0);
							System.out.println("Str_sessid" + Str_sessid);
							if (Str_sessid != null) {
								Toast.makeText(
										activity.getApplicationContext(),
										Str_sessid, Toast.LENGTH_LONG).show();
							}
						}

					}

				} catch (NullPointerException ex) {
					ex.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
