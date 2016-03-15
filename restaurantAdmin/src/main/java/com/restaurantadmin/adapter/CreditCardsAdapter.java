package com.restaurantadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantadmin.ConnectionDetector;
import com.example.restaurantadmin.Registration_step3_Activity;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreditCardsAdapter extends BaseAdapter {

	private static Activity context;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray arrayCreditCard;
	public String str_checked_creditcard_Name;
	public static String str_checked;
	public String str_checked_service_ID;
	// public String str_creditcard;
	JSONObject Object_ContactDetails;
	public int int_service_select;
	public String str_selected_service;
	ConnectionDetector cd;
	// public String str_selected_cads;
	/*** Network Connection Instance **/
	public CreditCardsAdapter(Activity a, JSONArray array_creditcardStrings) {

		System.out.println("malyo" + array_creditcardStrings);
		context = a;
		this.arrayCreditCard = array_creditcardStrings;
		System.out.println("1111array_credit_card" + array_creditcardStrings);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(context);
		/* create Object* */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayCreditCard.length();
	}

	// @Override
	// public JSONObject getItem(int position) {
	// // TODO Auto-generated method stub
	// JSONObject j = null;
	// try {
	// j = arrayCreditCard.getJSONObject(position);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return j;
	// }
	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = arrayCreditCard.getJSONObject(position);
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

		public TextView txv_creditcard_name;
		public CheckBox ch_creditcard;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		final ViewHolder holder;
		vi = inflater.inflate(R.layout.rawfile_creditcards_adapter, parent,
				false);
		holder = new ViewHolder();
		holder.txv_creditcard_name = (TextView) vi
				.findViewById(R.id.txv_cardsname);
		holder.ch_creditcard = (CheckBox) vi.findViewById(R.id.ch_cards);

		vi.setTag(holder);
		try {
			System.out.println("1111array_credit_card" + arrayCreditCard);

			// int length = arrayCreditCard.length;
			// int index=rgenerator.nextInt(length);
			JSONObject Object_Cards = getItem(position);
			try {
				String str_creditcard = Object_Cards
						.getString("credit_cards_name");
				System.out.println("creditcards" + str_creditcard);

				System.out.println("1111adapterstr_credot" + str_creditcard);
				holder.txv_creditcard_name.setText(str_creditcard);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			holder.ch_creditcard.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// // is chkIos checked?
					if (cd.isConnectingToInternet()) {
						if (((CheckBox) v).isChecked()) {
							try {
								// str_checked_creditcard_Name="";
								str_checked_creditcard_Name = holder.txv_creditcard_name
										.getText().toString();
								System.out.println("1111selectedcredt"
										+ str_checked_creditcard_Name);

								Registration_step3_Activity.obj_restaurant_app
										.put(position + "",
												str_checked_creditcard_Name);
								System.out
										.println("1111adapterobj_restaurant_app_"
												+ Registration_step3_Activity.obj_restaurant_app);

								Registration_step3_Activity.txv_credit_cards
										.setText(str_checked_creditcard_Name);

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {

							str_checked_creditcard_Name = holder.txv_creditcard_name
									.getText().toString();

							System.out.println("!!!!unchecked0"
									+ str_checked_creditcard_Name);
							System.out
									.println("creditcardsobj_before"
											+ Registration_step3_Activity.obj_restaurant_app);

							if (Registration_step3_Activity.obj_restaurant_app
									.has(position + "")) {
								// System.out
								// .println("pelaaftergyu"
								// +
								// Registration_step3_Activity.obj_restaurant_app
								// .remove(position + ""));
								Registration_step3_Activity.obj_restaurant_app
										.remove(position + "");
								// System.out
								// .println("aftergyu"
								// +
								// Registration_step3_Activity.obj_restaurant_app
								// .remove(position + ""));

							}

							System.out.println("!!!!unchecked"
									+ str_checked_creditcard_Name);
							System.out.println("!!!!pankajelse"
									+ str_checked_creditcard_Name);

							System.out
									.println("creditcardsobj_after"
											+ Registration_step3_Activity.obj_restaurant_app);

							System.out
									.println("creditcardsobj_length"
											+ Registration_step3_Activity.obj_restaurant_app
													.length());
							if (!Registration_step3_Activity.obj_restaurant_app
									.has(position + "")) {
								System.out.println("nullcardselsema1");
								System.out
										.println("creditcardsobj_null"
												+ Registration_step3_Activity.obj_restaurant_app);

								System.out
										.println("creditcardsobj_lengthin"
												+ Registration_step3_Activity.obj_restaurant_app
														.length());
								try {
									// if
									// (Registration_step3_Activity.obj_restaurant_app.getString("accepted_credit_cards")
									// == "") {
									// System.out.println("null1st");
									// Registration_step3_Activity.txv_credit_cards
									// .setText("Select Cards");
									// } else {
									System.out.println("null2st");
									if (Registration_step3_Activity.obj_restaurant_app
											.has("0")) {
										System.out.println("null2st0");
										String str = Registration_step3_Activity.obj_restaurant_app
												.getString("0");
										Registration_step3_Activity.txv_credit_cards
												.setText(str);
									} else if (Registration_step3_Activity.obj_restaurant_app
											.has("1")) {
										System.out.println("null2st0");
										String str = Registration_step3_Activity.obj_restaurant_app
												.getString("1");
										Registration_step3_Activity.txv_credit_cards
												.setText(str);
									} else if (Registration_step3_Activity.obj_restaurant_app
											.has("2")) {
										System.out.println("null2st0");
										String str = Registration_step3_Activity.obj_restaurant_app
												.getString("2");
										Registration_step3_Activity.txv_credit_cards
												.setText(str);
									} else {
										Registration_step3_Activity.txv_credit_cards
												.setText(context
														.getResources()
														.getString(
																R.string.str_select_cards));
									}

									// }
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//
							} else {

							}
						}
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});

		} catch (NullPointerException np) {

		}
		System.out.println("1111adapterobj_restaurant_appifbar"
				+ Registration_step3_Activity.obj_restaurant_app);
		try {
			if (Registration_step3_Activity.obj_restaurant_app.has(position
					+ "")) {
				holder.ch_creditcard.setChecked(true);

			}
		} catch (NullPointerException np) {

		}
		return vi;
	}

}
