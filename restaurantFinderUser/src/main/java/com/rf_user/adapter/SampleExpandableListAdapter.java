package com.rf_user.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.Food_Categories_Details_List;
import com.rf_user.activity.ImageLoader;
import com.rf_user.activity.ViewHolder;
import com.rf_user.global.Global_variable;

public class SampleExpandableListAdapter extends BaseExpandableListAdapter
		implements ExpandableListAdapter {
	public Context context;
	// CheckBox checkBox;
	private Activity activity;
	private LayoutInflater vi;
	ArrayList<ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>>> ArrayList_Final_Item;

	private String[][] data;
	String str_categories_id;
	String str_key_children, str_value_children;
	public ImageLoader imageLoader;
	Iterator itr_keys_items, itr_keys_children, itr_keys_items_obj;
	HashMap<String, String> hashmap_item_parent_data;
	HashMap<String, String> hashmap_item_child_data;
	HashMap<String, String> hashmap_item = new HashMap<String, String>();
	private HashMap<String, String> categories_value;
	ArrayList<HashMap<String, String>> array_listhashmap_food_categories_items;
	private static final int GROUP_ITEM_RESOURCE = R.layout.food_categories_details_listview_contents;
	private static final int CHILD_ITEM_RESOURCE = R.layout.food_categories_details_childview;
	Set<HashMap<String, String>> item;
	Iterator itr_item;
	String str_key_item, str_value_item;
	ArrayList<HashMap<String, String>> hashmap_item_parent_final;
	ArrayList<ArrayList<HashMap<String, String>>> hashmap_item_child_final;
	ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>> hashmap_item_arraylist = new ArrayList<HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>>();
	String selected_id;
	HashMap<String, String> hashmap_child = new HashMap<String, String>();
	HashMap<String, String> hashmap_parent = new HashMap<String, String>();
	String Categories_parent_id;
	View v;

	public SampleExpandableListAdapter(
			Activity a,
			ArrayList<HashMap<String, String>> hashmap_item_parent_final,
			ArrayList<ArrayList<HashMap<String, String>>> hashmap_item_child_final) {
		this.hashmap_item_parent_final = hashmap_item_parent_final;
		this.hashmap_item_child_final = hashmap_item_child_final;
		this.activity = a;
		quantity_total();
		// Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
		System.out.println("Final_parent_size"
				+ hashmap_item_parent_final.size());
		System.out
				.println("Final_child_size" + hashmap_item_child_final.size());
		System.out.println("id_maliknai" + str_categories_id);
		System.out.println("data_value_explist_parent"
				+ hashmap_item_parent_final);
		System.out.println("data_value_explist_child"
				+ hashmap_item_child_final);

		this.context = context;
		vi = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public HashMap<String, String> getChild(int groupPosition, int childPosition) {
		// return data[groupPosition][childPosition];
		hashmap_item_parent_final.get(groupPosition);
		hashmap_item_child_final.get(groupPosition).get(childPosition);
		System.out.println("pankajMurtu");
		System.out.println("Final_getchild="
				+ hashmap_item_child_final.get(groupPosition)
						.get(childPosition));
		return hashmap_item_child_final.get(groupPosition).get(childPosition);

	}

	public long getChildId(int groupPosition, int childPosition) {
		System.out.println("Final_childID" + childPosition);
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		System.out.println("Final_children_count"
				+ hashmap_item_child_final.get(groupPosition).size());

		return hashmap_item_child_final.get(groupPosition).size();
	}

	public HashMap<String, String> getGroup(int groupPosition) {
		hashmap_item_parent_final.get(groupPosition);
		System.out.println("Final_getgroup"
				+ hashmap_item_parent_final.get(groupPosition));

		return hashmap_item_parent_final.get(groupPosition);

	}

	public int getGroupCount() {
		System.out.println("Final_getgroupcount"
				+ hashmap_item_parent_final.size());
		return hashmap_item_parent_final.size();
	}

	public long getGroupId(int groupPosition) {
		System.out.println("Final_getgroupId" + groupPosition);
		return groupPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!hashmap_child.size()"
				+ hashmap_child.size());

		v = convertView;

		hashmap_child = getChild(groupPosition, childPosition);
		System.out.println("hashmap_child_of_array" + hashmap_child);
		System.out
				.println("hashmap_child_of_array_size" + hashmap_child.size());

		String Categories_child_name = hashmap_child.get("name");
		final String Categories_child_fullname = hashmap_child.get("full_name");
		String Categories_child_desc = hashmap_child.get("desc");
		final String Categories_child_price = hashmap_child.get("price");
		String Categories_child_spicy_level = hashmap_child
				.get("spicy_level_req_on");

		System.out.println("!!!!!!!!!!!!!!!!Categories_child_spicy_level"
				+ Categories_child_spicy_level);

		String Categories_child_index = hashmap_child.get("index");

		System.out.println("Categories_child_name" + Categories_child_name);
		System.out.println("Categories_desc" + Categories_child_desc);
		System.out.println("Categories_price" + Categories_child_price);
		System.out.println("Categories_child_index" + Categories_child_index);

		v = vi.inflate(CHILD_ITEM_RESOURCE, null);
		final ViewHolder holder = new ViewHolder(v);
		holder.sub_child_index.setText(Categories_child_index);
		holder.subcats_textview.setText(Categories_child_fullname);
		if (Categories_child_desc != null) {
			holder.size_of_subfood_textview.setText(Categories_child_desc);
		} else {
			holder.size_of_food_textview.setVisibility(View.INVISIBLE);
		}

		holder.sub_sr_txv.setText(Global_variable.Categories_sr
				+ Global_variable.blank + Categories_child_price);
		ArrayAdapter child_adap = (ArrayAdapter) holder.subspicy_leval_spinner
				.getAdapter(); // cast to an ArrayAdapter

		// holder.subspicy_leval_spinner.setPopupBackgroundResource(android.R.color.white);

		// holder.subspicy_leval_spinner
		// .setPopupBackgroundResource(R.drawable.spinner_target);

		int spinnerPosition = Integer.parseInt(Categories_child_spicy_level) - 1;

		// int spinnerPosition = child_adap
		// .getPosition(Categories_child_spicy_level);
		//
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!spinnerPosition"+spinnerPosition);

		// holder.subspicy_leval_spinner.setSelection(Categories_child_spicy_level);

		holder.sub_plus_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int spicy_level_position = holder.subspicy_leval_spinner
						.getSelectedItemPosition();
				System.out.println("Child_view_index"
						+ holder.sub_child_index.getText().toString());
				int i = Integer.parseInt(holder.sub_quantity_text.getText()
						.toString());
				if (i >= 0) {
					i = i + 1;
					holder.sub_quantity_text.setText(String.valueOf(i));
					JSONObject cart_value = new JSONObject();
					try {
						if (Global_variable.cart.length() != 0) {
							for (int j = 0; j < Global_variable.cart.length(); j++) {
								if (Global_variable.cart
										.getJSONObject(j)
										.getString("index")
										.equals(holder.sub_child_index
												.getText().toString())) {

									JSONArray jsonArray = Global_variable.cart;
									Global_variable.cart = new JSONArray();
									int len = jsonArray.length();
									if (jsonArray != null) {
										for (int k = 0; k < len; k++) {
											// Excluding the item at position
											if (k != j) {
												Global_variable.cart
														.put(jsonArray.get(k));
											}
										}
									}

								}

							}
							if (i != 0) {
								cart_value.put("comment", "");
								cart_value.put("index", holder.sub_child_index
										.getText().toString());
								cart_value.put("quantity", i);
								cart_value.put("spicy_level_req_on",
										spicy_level_position+1);
								cart_value.put("full_name",
										Categories_child_fullname);
								cart_value.put("price", Categories_child_price);
								int price1 = Integer
										.parseInt(Categories_child_price);
								cart_value.put("total", price1 * i);

								Global_variable.cart.put(cart_value);
							}
						} else {
							cart_value.put("comment", "");
							cart_value.put("index", holder.sub_child_index
									.getText().toString());
							cart_value.put("quantity", i);
							cart_value.put("spicy_level_req_on",
									spicy_level_position+1);
							cart_value.put("full_name",
									Categories_child_fullname);
							cart_value.put("price", Categories_child_price);
							int price1 = Integer
									.parseInt(Categories_child_price);
							cart_value.put("total", price1 * i);
							Global_variable.cart.put(cart_value);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}

				quantity_total();
				// Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
				// System.out.println("Cart_sub_plus"+Global_variable.cart);

				/** Value insert for cart refresh */
				String index = holder.sub_child_index.getText().toString();
				String quantity = holder.sub_quantity_text.getText().toString();
				int spicy_level = holder.subspicy_leval_spinner
						.getSelectedItemPosition();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(index, quantity + "+" + spicy_level);
				Global_variable.parent_child_value.add(map);
				System.out.println("parent_child"
						+ Global_variable.parent_child_value);

			}
		});
		holder.sub_minus_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int spicy_level_position = holder.subspicy_leval_spinner
						.getSelectedItemPosition();
				int i = Integer.parseInt(holder.sub_quantity_text.getText()
						.toString());
				if (i > 0) {
					i = i - 1;
					holder.sub_quantity_text.setText(String.valueOf(i));
					JSONObject cart_value = new JSONObject();
					try {
						if (Global_variable.cart.length() != 0) {
							for (int j = 0; j < Global_variable.cart.length(); j++) {
								if (Global_variable.cart
										.getJSONObject(j)
										.getString("index")
										.equals(holder.sub_child_index
												.getText().toString())) {

									JSONArray jsonArray = Global_variable.cart;
									Global_variable.cart = new JSONArray();
									int len = jsonArray.length();
									if (jsonArray != null) {
										for (int k = 0; k < len; k++) {
											// Excluding the item at position
											if (k != j) {
												Global_variable.cart
														.put(jsonArray.get(k));
											}
										}
									}

								}

							}
							if (i != 0) {
								cart_value.put("comment", "");
								cart_value.put("index", holder.sub_child_index
										.getText().toString());
								cart_value.put("quantity", i);
								cart_value.put("spicy_level_req_on",
										spicy_level_position+1);
								cart_value.put("full_name",
										Categories_child_fullname);
								cart_value.put("price", Categories_child_price);
								int price1 = Integer
										.parseInt(Categories_child_price);
								cart_value.put("total", price1 * i);
								Global_variable.cart.put(cart_value);
							}
						} else {
							cart_value.put("comment", "");
							cart_value.put("index", holder.sub_child_index
									.getText().toString());
							cart_value.put("quantity", i);
							cart_value.put("spicy_level_req_on",
									spicy_level_position+1);
							cart_value.put("full_name",
									Categories_child_fullname);
							cart_value.put("price", Categories_child_price);
							int price1 = Integer
									.parseInt(Categories_child_price);
							cart_value.put("total", price1 * i);
							Global_variable.cart.put(cart_value);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					} catch (NullPointerException n) {
						n.printStackTrace();
					}
				}
				quantity_total();
				// Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
				// System.out.println("Cart_sub_minus"+Global_variable.cart);

			}
		});
		
//		holder.subspicy_leval_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				
//				int spicy_level_position = holder.subspicy_leval_spinner
//						.getSelectedItemPosition();
//				int i = Integer.parseInt(holder.sub_quantity_text.getText()
//						.toString());
//				if (i > 0) {
////					i = i - 1;
//					holder.sub_quantity_text.setText(String.valueOf(i));
//					JSONObject cart_value = new JSONObject();
//					try {
//						if (Global_variable.cart.length() != 0) {
//							for (int j = 0; j < Global_variable.cart.length(); j++) {
//								if (Global_variable.cart
//										.getJSONObject(j)
//										.getString("index")
//										.equals(holder.sub_child_index
//												.getText().toString())) {
//
//									JSONArray jsonArray = Global_variable.cart;
//									Global_variable.cart = new JSONArray();
//									int len = jsonArray.length();
//									if (jsonArray != null) {
//										for (int k = 0; k < len; k++) {
//											// Excluding the item at position
//											if (k != j) {
//												Global_variable.cart
//														.put(jsonArray.get(k));
//											}
//										}
//									}
//
//								}
//
//							}
//							if (i != 0) {
//								cart_value.put("comment", "");
//								cart_value.put("index", holder.sub_child_index
//										.getText().toString());
//								cart_value.put("quantity", i);
//								cart_value.put("spicy_level_req_on",
//										spicy_level_position);
//								cart_value.put("full_name",
//										Categories_child_fullname);
//								cart_value.put("price", Categories_child_price);
//								int price1 = Integer
//										.parseInt(Categories_child_price);
//								cart_value.put("total", price1 * i);
//								Global_variable.cart.put(cart_value);
//							}
//						} else {
//							cart_value.put("comment", "");
//							cart_value.put("index", holder.sub_child_index
//									.getText().toString());
//							cart_value.put("quantity", i);
//							cart_value.put("spicy_level_req_on",
//									spicy_level_position);
//							cart_value.put("full_name",
//									Categories_child_fullname);
//							cart_value.put("price", Categories_child_price);
//							int price1 = Integer
//									.parseInt(Categories_child_price);
//							cart_value.put("total", price1 * i);
//							Global_variable.cart.put(cart_value);
//						}
//						
//						/** Value insert for cart refresh */
//						String index = holder.sub_child_index.getText().toString();
//						String quantity = holder.sub_quantity_text.getText().toString();
//						int spicy_level = holder.subspicy_leval_spinner
//								.getSelectedItemPosition();
//						HashMap<String, String> map = new HashMap<String, String>();
//						map.put(index, quantity + "+" + spicy_level);
//						Global_variable.parent_child_value.add(map);
//						System.out.println("parent_child"
//								+ Global_variable.parent_child_value);
//
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//
//					} catch (NullPointerException n) {
//						n.printStackTrace();
//					}
//					
//				 catch (ArrayIndexOutOfBoundsException n) {
//					n.printStackTrace();
//				}
//				}
//				quantity_total();
//				// Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
//				// System.out.println("Cart_sub_minus"+Global_variable.cart);
//				
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});

		// set the default according to value

		if (Global_variable.cart.length() != 0) {
			for (int j = 0; j < Global_variable.cart.length(); j++) {
				try {
					if (Global_variable.cart
							.getJSONObject(j)
							.getString("index")
							.equals(holder.sub_child_index.getText().toString())) {
						int i = Global_variable.cart.getJSONObject(j).getInt(
								"spicy_level_req_on");

						holder.subspicy_leval_spinner.setSelection(i-1);
						int quantity = Global_variable.cart.getJSONObject(j)
								.getInt("quantity");
						holder.sub_quantity_text.setText(String
								.valueOf(quantity));
						//
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (NullPointerException n) {
					n.printStackTrace();
				}

			}

		} else {
			try {
				holder.subspicy_leval_spinner.setSelection(spinnerPosition);
				// holder.subspicy_leval_spinner.setSelection(Integer.parseInt(Categories_child_spicy_level));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		

		return v;
	}

	protected void quantity_total() {
		// TODO Auto-generated method stub

		if (Global_variable.cart_quantity != 0) {
			Global_variable.cart_quantity = 0;
		}
		for (int y = 0; y < Global_variable.cart.length(); y++) {

			try {
				int quantity = Integer.parseInt(Global_variable.cart
						.getJSONObject(y).getString("quantity"));
				// int price =
				// Integer.parseInt(Global_variable.cart.getJSONObject(y).getString("price"));
				Global_variable.cart_quantity = Global_variable.cart_quantity+ quantity;
				
				
				/** below line is as per flipkart **/	
//				Global_variable.cart_quantity = Global_variable.cart.length();

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException n) {
				n.printStackTrace();
			}

		}
		// quantity_total();
		Food_Categories_Details_List.txv_cart.setText(Global_variable.cart_quantity + "");
		System.out.println("Cart_quantity" + Global_variable.cart_quantity);
		// Cart.txv_sr_total.setText(Global_variable.Categories_sr+" "+String.valueOf(Global_variable.cart_total));

	}

	@SuppressLint("NewApi")
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		v = convertView;
		String group = null;

		System.out.println("!!!!!!!!!!!!!!!!!!!!!hashmap_parent"
				+ hashmap_parent);

		hashmap_parent = hashmap_item_parent_final.get(groupPosition);
		String Categories_name = hashmap_parent.get("name");
		final String Categories_fullname = hashmap_parent.get("full_name");
		String Categories_index = hashmap_parent.get("index");
		String Categories_desc = hashmap_parent.get("desc");

		String Categories_spicy_level = hashmap_parent.get("spicy_level_req_on");
		String Categories_img = hashmap_parent.get("img");
		String Categories_spicy_level_text = hashmap_parent
				.get("spicy_level_text");
		String Categories_rating = hashmap_parent.get("num_of_ratings");
		final String Categories_SR = hashmap_parent.get("price");

		System.out.println("!!!!!!!!!!!!!!!hashmap_item_parent_final.."
				+ hashmap_item_parent_final);
		System.out.println("!!!!!!!!!!!!!!!Categories_SR" + Categories_SR);

		String Categories_vary = hashmap_parent.get("vary");
		String Categories_parent_index = hashmap_parent.get("index");

		System.out.println("Categories_name" + Categories_name);
		System.out.println("Categories_full_name" + Categories_fullname);
		System.out.println("Categories_index" + Categories_index);
		System.out.println("Categories_spicy" + Categories_spicy_level);
		System.out.println("Categories_parent_index" + Categories_parent_index);

		if (Categories_name != null) {
			int spinnerPosition = 0;
			v = vi.inflate(GROUP_ITEM_RESOURCE, null);

			imageLoader = new ImageLoader(activity.getApplicationContext());
			final ViewHolder holder = new ViewHolder(v);
			ImageView image = holder.imageview;
			
			
			
			imageLoader.DisplayImage(
					Global_variable.image_url + Categories_img, image);
			holder.categories_name.setText(Categories_name);
			holder.size_of_food_textview.setText(Categories_fullname);
			holder.categories_address.setText(Categories_desc);
			// holder.spicy_level_txv.setText(Categories_spicy_level_text);
			holder.parent_index.setText(Categories_parent_index);

			if (Categories_vary.equals("true")) {

				holder.SR_txv.setVisibility(View.INVISIBLE);
				// holder.spicy_level_spinner.setVisibility(View.GONE);
				holder.ly_quantity.setVisibility(View.INVISIBLE);
				// holder.spicy_level_txv.setVisibility(View.GONE);

			} else {
				ArrayAdapter parent_adap = (ArrayAdapter) holder.spicy_level_spinner
						.getAdapter(); // cast to an ArrayAdapter
				try {

					// holder.spicy_level_spinner.setPopupBackgroundResource(android.R.color.white);

					// holder.spicy_level_spinner
					// .setPopupBackgroundResource(R.drawable.spinner_target);

					spinnerPosition = Integer.parseInt(Categories_spicy_level) - 1;

					// spinnerPosition = parent_adap
					// .getPosition(Categories_spicy_level);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// set the default according to value

				holder.spicy_level_spinner.setSelection(spinnerPosition);
				holder.SR_txv.setText(Global_variable.Categories_sr
						+ Global_variable.blank + Categories_SR);
			}
			holder.categories_rate.setRating(Float
					.parseFloat(Categories_rating));

			holder.plus_btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int spicy_level_position = holder.spicy_level_spinner
							.getSelectedItemPosition();
					System.out.println("parent_index"
							+ holder.parent_index.getText().toString());
					int i = Integer.parseInt(holder.quantity_txv.getText()
							.toString());

					if (i >= 0) {
						i = i + 1;
						holder.quantity_txv.setText(String.valueOf(i));
						JSONObject cart_value = new JSONObject();
						try {
							if (Global_variable.cart.length() != 0) {
								for (int j = 0; j < Global_variable.cart
										.length(); j++) {
									System.out.println("Global.cart.index_for"
											+ holder.parent_index.getText()
													.toString());
									if (Global_variable.cart
											.getJSONObject(j)
											.getString("index")
											.equals(holder.parent_index
													.getText().toString())) {

										System.out
												.println("Global.cart.index_if"
														+ holder.parent_index
																.getText()
																.toString());
										JSONArray jsonArray = Global_variable.cart;
										Global_variable.cart = new JSONArray();
										int len = jsonArray.length();
										if (jsonArray != null) {
											for (int k = 0; k < len; k++) {
												// Excluding the item at
												// position
												if (k != j) {
													Global_variable.cart
															.put(jsonArray
																	.get(k));
												}

											}
										}

									}

								}
								if (i != 0) {
									cart_value.put("comment", "");
									cart_value.put("index", holder.parent_index
											.getText().toString());
									cart_value.put("quantity", i);
									cart_value.put("spicy_level_req_on",
											spicy_level_position+1);
									cart_value.put("full_name",
											Categories_fullname);
									cart_value.put("price", Categories_SR);
									int price1 = Integer
											.parseInt(Categories_SR);
									cart_value.put("total", price1 * i);
									Global_variable.cart.put(cart_value);
								}
							} else {
								cart_value.put("comment", "");
								cart_value.put("index", holder.parent_index
										.getText().toString());
								cart_value.put("quantity", i);
								cart_value.put("spicy_level_req_on",
										spicy_level_position+1);
								cart_value
										.put("full_name", Categories_fullname);
								cart_value.put("price", Categories_SR);
								int price1 = Integer.parseInt(Categories_SR);
								cart_value.put("total", price1 * i);
								Global_variable.cart.put(cart_value);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}
					quantity_total();
					// Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
					// System.out.println("Cart__plus"+Global_variable.cart);

				}
			});
			holder.minus_btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int spicy_level_position = holder.spicy_level_spinner
							.getSelectedItemPosition();
					int i = Integer.parseInt(holder.quantity_txv.getText()
							.toString());
					if (i > 0) {
						i = i - 1;
						holder.quantity_txv.setText(String.valueOf(i));
						JSONObject cart_value = new JSONObject();
						try {
							if (Global_variable.cart.length() != 0) {
								for (int j = 0; j < Global_variable.cart
										.length(); j++) {
									if (Global_variable.cart
											.getJSONObject(j)
											.getString("index")
											.equals(holder.parent_index
													.getText().toString())) {

										JSONArray jsonArray = Global_variable.cart;
										Global_variable.cart = new JSONArray();
										int len = jsonArray.length();
										if (jsonArray != null) {
											for (int k = 0; k < len; k++) {
												// Excluding the item at
												// position
												if (k != j) {
													Global_variable.cart
															.put(jsonArray
																	.get(k));
												}
											}
										}

									}

								}
								if (i != 0) {
									cart_value.put("comment", "");
									cart_value.put("index", holder.parent_index
											.getText().toString());
									cart_value.put("quantity", i);
									cart_value.put("spicy_level_req_on",
											spicy_level_position+1);
									cart_value.put("full_name",
											Categories_fullname);
									cart_value.put("price", Categories_SR);
									int price1 = Integer
											.parseInt(Categories_SR);
									cart_value.put("total", price1 * i);
									Global_variable.cart.put(cart_value);
								}
							} else {
								cart_value.put("comment", "");
								cart_value.put("index", holder.parent_index
										.getText().toString());
								cart_value.put("quantity", i);
								cart_value.put("spicy_level_req_on",
										spicy_level_position+1);
								cart_value
										.put("full_name", Categories_fullname);
								cart_value.put("price", Categories_SR);
								int price1 = Integer.parseInt(Categories_SR);
								cart_value.put("total", price1 * i);
								Global_variable.cart.put(cart_value);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						} catch (NullPointerException n) {
							n.printStackTrace();
						}
					}
					quantity_total();
					// Food_Categories_Details_List.txv_cart.setText(Global_variable.cart.length()+"");
					// System.out.println("Cart__minus"+Global_variable.cart);

				}
			});

			// set the default according to value
			System.out.println("Global.cart" + Global_variable.cart.length());
			if (Global_variable.cart.length() != 0) {
				System.out.println("Global.cart.if"
						+ Global_variable.cart.length());
				for (int j = 0; j < Global_variable.cart.length(); j++) {
					Boolean flag = false;
					System.out.println("Global.cart.for"
							+ Global_variable.cart.length());
					try {
						System.out.println("Global.cart.for_json"
								+ Global_variable.cart.getJSONObject(j)
										.getString("index"));
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Global.cart.for_holder"
							+ holder.parent_index.getText().toString());

					try {

						if (Global_variable.cart
								.getJSONObject(j)
								.getString("index")
								.equals(holder.parent_index.getText()
										.toString())) {
							System.out.println("Global.cart.for_if"
									+ Global_variable.cart.length());
							System.out.println("Global.cart.for_i"
									+ Global_variable.cart.length());
							int i = Global_variable.cart.getJSONObject(j)
									.getInt("spicy_level_req_on");
							System.out.println("Global.cart.for_i" + i);
							holder.spicy_level_spinner.setSelection(i-1);
							int quantity = Global_variable.cart
									.getJSONObject(j).getInt("quantity");
							System.out.println("Global.cart.for_quantity"
									+ quantity);
							holder.quantity_txv.setText(String
									.valueOf(quantity));
							//
						}

						// }

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NullPointerException np) {

					}

				}

			} else {
				System.out.println("Global.cart.else"
						+ Global_variable.cart.length());
				try {

					holder.spicy_level_spinner.setSelection(spinnerPosition);
					// holder.spicy_level_spinner.setSelection(Integer.parseInt(Categories_spicy_level));

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}

		}

		return v;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}
}