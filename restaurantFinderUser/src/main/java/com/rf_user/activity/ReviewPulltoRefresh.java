//package com.rf_user.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.rf_user.PullToRefreshLibrary;
//import com.rf_user.PullToRefreshLibrary.OnRefreshListener;
//import com.rf_user.adapter.ReviewAdapter;
//import com.rf_user.connection.HttpConnection;
//import com.rf_user.global.Global_variable;
//import com.rf_user.internet.ConnectionDetector;
//import com.rf_user.sharedpref.SharedPreference;
//
//public class ReviewPulltoRefresh extends Activity {
//
//	PullToRefreshLibrary pull_to_refrest_list;
//	int position = 0;
//
//	/*** Network Connection Instance **/
//	ConnectionDetector cd;
//
//	String TAG_SUCCESS = "success";
//
//	HttpConnection http = new HttpConnection();
//	ProgressDialog progressDialog;
//	ReviewAdapter review_adpater;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_review_pullto_refresh);
//
//		try {
//			/* create Object* */
//			cd = new ConnectionDetector(getApplicationContext());
//
//			Global_variable.reviews_listData = new ArrayList<HashMap<String, String>>();
//
//			pull_to_refrest_list = (PullToRefreshLibrary) findViewById(R.id.pull_to_refresh_listview);
//
//			/** check Internet Connectivity */
//			if (cd.isConnectingToInternet()) {
//
//				new async_fetch_rest_review_list().execute();
//
//			} else {
//
//				runOnUiThread(new Runnable() {
//
//					@Override
//					public void run() {
//
//						// TODO Auto-generated method stub
//						Toast.makeText(getApplicationContext(),
//								R.string.No_Internet_Connection,
//								Toast.LENGTH_LONG).show();
//
//						do {
//							System.out.println("do-while");
//							if (cd.isConnectingToInternet()) {
//
//								// Set a listener to be invoked when the list
//								// should be refreshed.
//								// ((PullToRefreshListView)
//								// getListView()).setOnRefreshListener(new
//								// OnRefreshListener() {
//								// @Override
//								// public void onRefresh() {
//								// // Do work to refresh the list here.
//								new async_fetch_rest_review_list().execute();
//								// }
//								// });
//
//							}
//						} while (cd.isConnectingToInternet() == false);
//
//					}
//
//				});
//
//				// Set a listener to be invoked when the list should be
//				// refreshed.
//
//				pull_to_refrest_list
//						.setOnRefreshListener(new OnRefreshListener() {
//							@Override
//							public void onRefresh() {
//								// Do work to refresh the list here.
//								new async_fetch_rest_review_list().execute();
//							}
//						});
//
//			}
//		} catch (NullPointerException n) {
//			n.printStackTrace();
//		}
//
//	}
//
//	public class async_fetch_rest_review_list extends
//			AsyncTask<Void, Void, Void> {
//		JSONObject data, json;
//		String overall_rating, review_count;
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			System.out.println("async_fetch_rest_review_list  Call");
//			// Showing progress dialog
//
//			progressDialog = new ProgressDialog(ReviewPulltoRefresh.this);
//			progressDialog.setCancelable(false);
//			progressDialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				JSONObject fetch_reviews = new JSONObject();
//
//				try {
//					if (Global_variable.hotel_id.length() != 0) {
//						fetch_reviews.put("rest_id", Global_variable.hotel_id);
//						System.out.println("fetch_reviews" + fetch_reviews);
//					} else {
//						fetch_reviews.put("rest_id", "");
//					}
//
//					fetch_reviews
//							.put("sessid", SharedPreference
//									.getsessid(getApplicationContext()));
//					System.out.println("fetch_reviews" + fetch_reviews);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//				String responseText = http.connection(Global_variable.rf_en_Url
//						+ Global_variable.rf_get_restaurant_review_api_path,
//						fetch_reviews);
//
//				try {
//
//					System.out.println("after_connection.." + responseText);
//
//					json = new JSONObject(responseText);
//					System.out.println("responseText" + data);
//				} catch (NullPointerException ex) {
//					ex.printStackTrace();
//				}
//
//				try {
//
//					// json success tag
//					String success1 = json.getString(TAG_SUCCESS);
//					System.out.println("tag" + success1);
//
//					String str_data = json.getString("data");
//					System.out.println("My_ReviewList_str_data" + str_data);
//
//					if (success1.equals("true")) {
//						if (str_data != "[]") {
//							data = json.getJSONObject("data");
//							System.out
//									.println("data_rsponse_categories_parameter"
//											+ data);
//
//							if (data != null) {
//
//								overall_rating = data
//										.getString("overall_rating");
//								review_count = data.getString("review_count");
//
//								JSONArray reviews_array = data
//										.getJSONArray("reviews");
//								System.out.println("reviews_array"
//										+ reviews_array.toString());
//
//								int length = reviews_array.length();
//								// System.out.println("respose_array Value"+
//								// response_array.keys().toString());
//								System.out.println("reviews_array_length"
//										+ length);
//
//								for (int i = position; i < position + 1; i++) {
//									try {
//
//										if (i < reviews_array.length()) {
//											JSONObject review_obj = reviews_array
//													.getJSONObject(i);
//											System.out.println("review_obj"
//													+ review_obj);
//
//											String tg_order_id = review_obj
//													.getString("tg_order_id");
//											System.out.println("tg_order_id"
//													+ tg_order_id);
//											String comment = review_obj
//													.getString("comment");
//											System.out.println("comment"
//													+ comment);
//											String order_rating = review_obj
//													.getString("order_rating");
//											System.out.println("order_rating"
//													+ order_rating);
//											String firstname = review_obj
//													.getString("firstname");
//											System.out.println("firstname"
//													+ firstname);
//											String lastname = review_obj
//													.getString("lastname");
//											System.out.println("lastname"
//													+ lastname);
//
//											HashMap<String, String> map = new HashMap<String, String>();
//
//											map.put("tg_order_id", tg_order_id);
//											System.out.println("map" + map);
//											map.put("comment", comment);
//											System.out.println("map" + map);
//											map.put("order_rating",
//													order_rating);
//											System.out.println("map" + map);
//											map.put("firstname", firstname);
//											System.out.println("map" + map);
//											map.put("lastname", lastname);
//											System.out.println("map" + map);
//
//											System.out.println("map" + map);
//											Global_variable.reviews_listData
//													.add(map);
//
//											System.out
//													.println("!!!!!In background..."
//															+ Global_variable.reviews_listData);
//
//											if (position == review_obj.length()) {
//												break;
//											}
//
//										}
//
//										//
//
//									} catch (Exception ex) {
//										System.out.println("Error" + ex);
//									}
//								}
//
//								position = position + 1;
//							}
//						}
//					} else {
//
//					}
//
//				} catch (NullPointerException ex) {
//					ex.printStackTrace();
//				} catch (IndexOutOfBoundsException e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//
//			} catch (NullPointerException e) {
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			//
//			// if (overall_rating != null) {
//			// overall_review_rating.setText(overall_rating + "/10");
//			//
//			// } else {
//			// overall_review_rating.setText("0/10");
//			// }
//			//
//			// if (review_count != null) {
//			// txt_count_of_reviews.setText("according to " + review_count
//			// + " reviews");
//			// } else {
//			// txt_count_of_reviews.setText("according to 0 reviews");
//			// }
//
//			// Call onRefreshComplete when the list has been refreshed.
//			pull_to_refrest_list.onRefreshComplete();
//
//			try {
//				runOnUiThread(new Runnable() {
//					public void run() {
//						System.out.println("pankajsakariyadata" + data);
//						if (data == (null)) {
//							// ly_No_reviews.setVisibility(View.VISIBLE);
//							// review_layout.setVisibility(View.GONE);
//						}
//						if (data == null) {
//							if (Global_variable.reviews_listData != null) {
//								Global_variable.reviews_listData.clear();
//							}
//
//							// pull_to_refrest_list.invalidateViews();
//							// ly_No_reviews.setVisibility(View.VISIBLE);
//							// review_layout.setVisibility(View.GONE);
//							System.out.println("pankajsakariyadata123");
//						} else {
//							// ly_No_reviews.setVisibility(View.GONE);
//							// pull_to_refrest_list.setVisibility(View.VISIBLE);
//							if (Global_variable.reviews_listData != null) {
//								review_adpater = new ReviewAdapter(
//										ReviewPulltoRefresh.this,
//										Global_variable.reviews_listData);
//								System.out.println("pankaj_inside_hotel_list");
//								if (review_adpater != null) {
//									pull_to_refrest_list
//											.setAdapter(review_adpater);
//
//									System.out
//											.println("pankaj_inside_list_adapter");
//									pull_to_refrest_list.invalidateViews();
//
//								}
//
//							} else {
//								System.out.println("pankaj_inside_else");
//								Global_variable.reviews_listData.clear();
//								review_adpater = new ReviewAdapter(
//										ReviewPulltoRefresh.this,
//										Global_variable.reviews_listData);
//								pull_to_refrest_list.setAdapter(review_adpater);
//
//							}
//
//						}
//					}
//				});
//
//			} catch (NullPointerException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (progressDialog.isShowing()) {
//				progressDialog.dismiss();
//			}
//
//		}
//	}
//
//}
//
//// package com.rf_user.activity;
////
//// import java.util.ArrayList;
//// import java.util.HashMap;
////
//// import org.json.JSONArray;
//// import org.json.JSONException;
//// import org.json.JSONObject;
////
//// import android.app.Activity;
//// import android.app.ListActivity;
//// import android.app.ProgressDialog;
//// import android.content.Context;
//// import android.os.AsyncTask;
//// import android.os.Bundle;
//// import android.widget.Toast;
////
//// import com.rf_user.adapter.ReviewAdapter;
//// import com.rf_user.connection.HttpConnection;
//// import com.rf_user.global.Global_variable;
//// import com.rf_user.internet.ConnectionDetector;
//// import com.rf_user.PullToRefreshLibrary;
//// import com.rf_user.PullToRefreshLibrary.OnRefreshListener;
//// import com.rf_user.sharedpref.SharedPreference;
////
//// public class ReviewPulltoRefresh extends Activity {
////
//// PullToRefreshLibrary pull_to_refrest_list;
//// int position = 0;
////
//// /*** Network Connection Instance **/
//// ConnectionDetector cd;
////
//// String TAG_SUCCESS = "success";
////
//// HttpConnection http = new HttpConnection();
//// ProgressDialog progressDialog;
//// ReviewAdapter review_adpater;
//// // Context context;
////
//// // public SharedPreferences sharedPref;
//// // public Editor editor;
////
//// @Override
//// protected void onCreate(Bundle savedInstanceState) {
//// super.onCreate(savedInstanceState);
//// setContentView(R.layout.activity_review_pullto_refresh);
////
//// try {
//// /* create Object* */
//// cd = new ConnectionDetector(getApplicationContext());
////
//// Global_variable.reviews_listData = new ArrayList<HashMap<String, String>>();
////
//// // sharedPref = getApplicationContext().getSharedPreferences("MyPref",
//// // Context.MODE_PRIVATE);
//// // editor = sharedPref.edit();
////
//// pull_to_refrest_list = (PullToRefreshLibrary)
//// findViewById(R.id.pull_to_refresh_listview);
////
//// /** check Internet Connectivity */
//// if (cd.isConnectingToInternet()) {
////
//// //loadMoreData();
//// new async_fetch_rest_review_list().execute();
////
//// } else {
////
//// runOnUiThread(new Runnable() {
////
//// @Override
//// public void run() {
////
//// // TODO Auto-generated method stub
//// Toast.makeText(getApplicationContext(),
//// R.string.No_Internet_Connection,
//// Toast.LENGTH_LONG).show();
////
//// do {
//// System.out.println("do-while");
//// if (cd.isConnectingToInternet()) {
////
//// // Set a listener to be invoked when the list
//// // should be refreshed.
//// // ((PullToRefreshListView)
//// // getListView()).setOnRefreshListener(new
//// // OnRefreshListener() {
//// // @Override
//// // public void onRefresh() {
//// // // Do work to refresh the list here.
////
//// //loadMoreData();
//// new async_fetch_rest_review_list().execute();
////
//// // }
//// // });
////
//// }
//// } while (cd.isConnectingToInternet() == false);
////
//// }
////
//// });
////
//// // Set a listener to be invoked when the list should be
//// // refreshed.
////
//// pull_to_refrest_list
//// .setOnRefreshListener(new OnRefreshListener() {
////
//// @Override
//// public void onRefresh() {
////
////
//// System.out.println("!!!!!!!!!!onRefresh"+position);
////
//// // Your code to refresh the list contents goes
//// // here
////
//// // for example:
//// // If this is a webservice call, it might be
//// // asynchronous so
//// // you would have to call
//// // listView.onRefreshComplete(); when
//// // the webservice returns the data
//// loadMoreData();
////
//// // Make sure you call
//// // listView.onRefreshComplete()
//// // when the loading is done. This can be done
//// // from here or any
//// // other place, like on a broadcast receive from
//// // your loading
//// // service or the onPostExecute of your
//// // AsyncTask.
////
//// // For the sake of this sample, the code will
//// // pause here to
//// // force a delay when invoking the refresh
//// // pull_to_refrest_list.postDelayed(
//// // new Runnable() {
//// //
//// // @Override
//// // public void run() {
//// // pull_to_refrest_list
//// // .onRefreshComplete();
//// // }
//// // }, 2000);
//// }
//// });
////
//// }
////
//// } catch (NullPointerException n) {
//// n.printStackTrace();
//// }
////
//// }
////
//// public class async_fetch_rest_review_list extends
//// AsyncTask<Void, Void, Void> {
////
////
////
////
//// JSONObject data, json;
//// String overall_rating, review_count;
////
//// @Override
//// protected void onPreExecute() {
//// // TODO Auto-generated method stub
//// super.onPreExecute();
//// System.out.println("async_fetch_rest_review_list  Call");
//// // Showing progress dialog
////
//// System.out.println("!!!!!!pre"+position);
////
////
//// progressDialog = new ProgressDialog(ReviewPulltoRefresh.this);
//// progressDialog.setCancelable(false);
//// progressDialog.show();
////
//// }
////
//// @Override
//// protected Void doInBackground(Void... params) {
//// // // TODO Auto-generated method stub
//// JSONObject fetch_reviews = new JSONObject();
////
//// try {
//// if (Global_variable.hotel_id.length() != 0) {
//// fetch_reviews.put("rest_id", Global_variable.hotel_id);
//// System.out.println("fetch_reviews" + fetch_reviews);
//// } else {
//// fetch_reviews.put("rest_id", "");
//// }
////
//// fetch_reviews.put("sessid",
//// SharedPreference.getsessid(getApplicationContext()));
//// System.out.println("fetch_reviews" + fetch_reviews);
//// } catch (JSONException e) {
//// e.printStackTrace();
//// }
////
//// try {
//// String responseText = http.connection(Global_variable.rf_en_Url
//// + Global_variable.rf_get_restaurant_review_api_path,
//// fetch_reviews);
////
//// System.out.println("after_connection.." + responseText);
////
//// json = new JSONObject(responseText);
//// System.out.println("responseText" + data);
////
//// // json success tag
//// String success1 = json.getString(TAG_SUCCESS);
//// System.out.println("tag" + success1);
////
//// String str_data = json.getString("data");
//// System.out.println("My_ReviewList_str_data" + str_data);
////
//// if (success1.equals("true")) {
//// if (str_data != "[]") {
//// data = json.getJSONObject("data");
//// System.out.println("data_rsponse_categories_parameter"
//// + data);
////
//// if (data != null) {
////
//// overall_rating = data.getString("overall_rating");
//// review_count = data.getString("review_count");
////
//// JSONArray reviews_array = data
//// .getJSONArray("reviews");
//// System.out.println("reviews_array"
//// + reviews_array.toString());
////
//// int length = reviews_array.length();
//// // System.out.println("respose_array Value"+
//// // response_array.keys().toString());
//// System.out.println("reviews_array_length" + length);
//// if (data.length() != position) {
////
//// System.out.println("!!!!!!data not null"+position);
////
//// for (int i = position; i < position + 1; i++) {
//// try {
////
//// // editor.putInt("position", position);
//// // editor.commit();
////
//// JSONObject review_obj = reviews_array
//// .getJSONObject(i);
//// System.out.println("review_obj"
//// + review_obj);
////
//// String tg_order_id = review_obj
//// .getString("tg_order_id");
//// System.out.println("tg_order_id"
//// + tg_order_id);
//// String comment = review_obj
//// .getString("comment");
//// System.out.println("comment" + comment);
//// String order_rating = review_obj
//// .getString("order_rating");
//// System.out.println("order_rating"
//// + order_rating);
//// String firstname = review_obj
//// .getString("firstname");
//// System.out.println("firstname"
//// + firstname);
//// String lastname = review_obj
//// .getString("lastname");
//// System.out.println("lastname"
//// + lastname);
////
//// HashMap<String, String> map = new HashMap<String, String>();
////
//// map.put("tg_order_id", tg_order_id);
//// System.out.println("map" + map);
//// map.put("comment", comment);
//// System.out.println("map" + map);
//// map.put("order_rating", order_rating);
//// System.out.println("map" + map);
//// map.put("firstname", firstname);
//// System.out.println("map" + map);
//// map.put("lastname", lastname);
//// System.out.println("map" + map);
////
//// System.out.println("map" + map);
//// Global_variable.reviews_listData
//// .add(map);
////
//// System.out
//// .println("!!!!!In background..."
//// + Global_variable.reviews_listData);
////
//// if (position == review_obj.length()) {
//// break;
//// }
////
//// } catch (NullPointerException e) {
//// e.printStackTrace();
//// }
////
//// }
//// position = position + 1;
//// }
////
////
//// }
//// }
//// } else {
////
//// }
////
//// } catch (NullPointerException ex) {
//// ex.printStackTrace();
//// } catch (JSONException e) {
//// // TODO: handle exception
//// e.printStackTrace();
//// }
////
//// //
//// return null;
//// }
////
//// @Override
//// protected void onPostExecute(Void result) {
//// // TODO Auto-generated method stub
//// super.onPostExecute(result);
//// //
//// // if (overall_rating != null) {
//// // overall_review_rating.setText(overall_rating + "/10");
//// //
//// // } else {
//// // overall_review_rating.setText("0/10");
//// // }
//// //
//// // if (review_count != null) {
//// // txt_count_of_reviews.setText("according to " + review_count
//// // + " reviews");
//// // } else {
//// // txt_count_of_reviews.setText("according to 0 reviews");
//// // }
////
//// // Call onRefreshComplete when the list has been refreshed.
//// pull_to_refrest_list.onRefreshComplete();
//// registerForContextMenu(pull_to_refrest_list);
//// try {
//// runOnUiThread(new Runnable() {
//// public void run() {
//// System.out.println("pankajsakariyadata" + data);
//// if (data == (null)) {
//// // ly_No_reviews.setVisibility(View.VISIBLE);
//// // review_layout.setVisibility(View.GONE);
//// }
//// if (data == null) {
//// if (Global_variable.reviews_listData != null) {
//// Global_variable.reviews_listData.clear();
//// }
////
//// // pull_to_refrest_list.invalidateViews();
//// // ly_No_reviews.setVisibility(View.VISIBLE);
//// // review_layout.setVisibility(View.GONE);
//// System.out.println("pankajsakariyadata123");
//// } else {
//// // ly_No_reviews.setVisibility(View.GONE);
//// // pull_to_refrest_list.setVisibility(View.VISIBLE);
//// if (Global_variable.reviews_listData != null) {
//// review_adpater = new ReviewAdapter(
//// ReviewPulltoRefresh.this,
//// Global_variable.reviews_listData);
//// System.out.println("pankaj_inside_hotel_list");
//// if (review_adpater != null) {
//// pull_to_refrest_list
//// .setAdapter(review_adpater);
////
//// System.out
//// .println("pankaj_inside_list_adapter");
//// pull_to_refrest_list.invalidateViews();
////
//// }
////
//// } else {
//// System.out.println("pankaj_inside_else");
//// Global_variable.reviews_listData.clear();
//// review_adpater = new ReviewAdapter(
//// ReviewPulltoRefresh.this,
//// Global_variable.reviews_listData);
//// pull_to_refrest_list.setAdapter(review_adpater);
////
//// }
////
//// }
//// }
//// });
////
//// } catch (NullPointerException e) {
//// e.printStackTrace();
//// } catch (Exception e) {
//// e.printStackTrace();
//// }
////
//// if (progressDialog.isShowing()) {
//// progressDialog.dismiss();
//// }
////
//// }
//// }
////
//// private void loadMoreData() {
//// new async_fetch_rest_review_list().execute();
//// }
////

//@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		
//		System.out.println("!!!!!!!!!!!!!!!onStart");
//	}
//	
//	@Override
//	public void onRestart() {
//		// TODO Auto-generated method stub
//		super.onRestart();
//		
//		System.out.println("!!!!!!!!!!!!!!!onRestart");
//	}
//	
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		
//		System.out.println("!!!!!!!!!!!!!!!onResume");
//		
//	}
//	
//	
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		
//		System.out.println("!!!!!!!!!!!!!!!onPause");
//		
//	}
//	
//	@Override
//	public void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		
//		System.out.println("!!!!!!!!!!!!!!!onStop");
//	}
//	
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		
//		System.out.println("!!!!!!!!!!!!!!!onDestroy");
//	}
//// }
