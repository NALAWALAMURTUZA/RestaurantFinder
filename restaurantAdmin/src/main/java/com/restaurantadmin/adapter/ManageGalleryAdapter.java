package com.restaurantadmin.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantadmin.ConnectionDetector;
import com.example.restaurantadmin.ManageGalleryActivity;
import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.imageloader.ImageLoader;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ManageGalleryAdapter extends BaseAdapter {

	private static Activity context;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public JSONArray array_RestaurantBanner;
	public static String str_rest_banner_id, str_rest_id, str_rest_bannerurl,
			str_legeand;
	JSONObject Object_RestBanner;
	public int int_service_select;
	// public String str_set_bannerid;
	ArrayAdapter<CharSequence> adapter;
	public ImageLoader imageLoader;

	public static ProgressDialog p;
	public static String str_update, str_delete;
	ManageGalleryAdapter ManageGalleryAdapter;
	boolean update = false;
	public String str_bannerid;
	// public ViewHolder holder;
	Dialog DeleteDialog;
	public static TextView txv_Dialogtext;
	public static ImageView img_yes, img_no;
	public static String result = "";
	// public static ImageView img_load,img_load_update;
	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	public static boolean flag_image = false;
	public static boolean flag_action = false;
	public static ImageView img_load_update;
	// TextView txv_bannerid;
	public static ImageView img_load;

	public static int position_selected;
	ConnectionDetector cd;
	/*** Network Connection Instance **/
	public ManageGalleryAdapter(Activity a, JSONArray array_RestaurantBanner) {

		System.out.println("malyo" + array_RestaurantBanner);
		context = a;
		this.array_RestaurantBanner = array_RestaurantBanner;
		System.out.println("1111array_online_table_grabbing"
				+ array_RestaurantBanner);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(context.getApplicationContext());
		cd = new ConnectionDetector(context);
		/* create Object* */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array_RestaurantBanner.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = array_RestaurantBanner.getJSONObject(position);
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

	// public static class ViewHolder {
	//
	// public ImageView img_load, img_load_update;
	// // public Spinner sp_status;
	// public ImageView img_delete, img_update, img_action;
	// // public EditText ed_legand;
	// public TextView txv_bannerid;
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;

		vi = inflater.inflate(R.layout.activity_manage_gallery_rawfile, parent,
				false);
		// holder = new ViewHolder();

		final EditText ed_legand = (EditText) vi.findViewById(R.id.ed_legand);
		final TextView txv_legand = (TextView) vi.findViewById(R.id.txv_legand);
		// holder.txv_bannerid = (TextView) vi.findViewById(R.id.txv_bannerid);
		// holder.img_load = (ImageView) vi
		// .findViewById(R.id.img_uploadimage_rawfile);
		// holder.img_delete = (ImageView) vi.findViewById(R.id.img_delete);
		// holder.img_update = (ImageView) vi.findViewById(R.id.img_update);
		// holder.img_action = (ImageView) vi.findViewById(R.id.img_action);

		final TextView txv_bannerid = (TextView) vi
				.findViewById(R.id.txv_bannerid);
		img_load = (ImageView) vi.findViewById(R.id.img_uploadimage_rawfile);
		final ImageView img_delete = (ImageView) vi
				.findViewById(R.id.img_delete);
		final ImageView img_update = (ImageView) vi
				.findViewById(R.id.img_update);
		final ImageView img_action = (ImageView) vi
				.findViewById(R.id.img_action);
		final TextView txv_banner_path = (TextView) vi
				.findViewById(R.id.txv_banner_path);

		// vi.setTag(holder);
		try {
			Object_RestBanner = getItem(position);
			System.out.println("1111Object_RestBanner" + Object_RestBanner);
			str_rest_banner_id = Object_RestBanner
					.getString("restaurant_banner_id");

			System.out.println("str_rest_banner_id" + str_rest_banner_id);
			Global_variable.restaurant_id = Object_RestBanner
					.getString("restaurant_id");
			System.out.println("restaurant_id" + Global_variable.restaurant_id);
			str_rest_bannerurl = Object_RestBanner.getString("banner_name");
			txv_banner_path.setText(str_rest_bannerurl);
			System.out.println("str_rest_bannerurl" + str_rest_bannerurl);
			str_legeand = Object_RestBanner.getString("description");
			txv_legand.setText(str_legeand);
			ed_legand.setText(str_legeand);
			txv_bannerid.setText(str_rest_banner_id);
			img_load_update = img_load;
			System.out.println("!!!!!!!url1" + str_rest_bannerurl);
			imageLoader.DisplayImage(str_rest_bannerurl, img_load_update);
			System.out.println("!!!!!!!url2" + str_rest_bannerurl);
			img_action.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("!!!!pankaj_position" + position);

					str_bannerid = "";
					str_bannerid = txv_bannerid.getText().toString();
					System.out
							.println("banneridmatchthaiaction" + str_bannerid);
					flag_action = true;
					System.out
							.println("1111banneriddeleteclick" + str_bannerid);

					// img_load.buildDrawingCache();
					//
					// Bitmap bmap = img_load.getDrawingCache();
					// ManageGalleryActivity.img_uploadimage.setImageBitmap(bmap);
					//

					System.out.println("!!!!!!!url12" + str_rest_bannerurl);
					imageLoader.DisplayImage(txv_banner_path.getText()
							.toString(), ManageGalleryActivity.img_uploadimage);
					System.out.println("!!!!!!!url22" + str_rest_bannerurl);

					ManageGalleryActivity.ed_legand.setText(ed_legand.getText()
							.toString());
					ManageGalleryActivity.str_bannerid = str_bannerid;

					ManageGalleryActivity.img_add.setVisibility(View.GONE);
					ManageGalleryActivity.img_update_btn
							.setVisibility(View.VISIBLE);
					ManageGalleryActivity.img_cancel_btn
							.setVisibility(View.VISIBLE);
					// txv_legand.setVisibility(View.GONE);
					// ed_legand.setVisibility(View.VISIBLE);
					// holder.img_action.setVisibility(View.GONE);
					// holder.img_delete.setVisibility(View.GONE);
					// holder.img_update.setVisibility(View.VISIBLE);
					// img_action.setVisibility(View.GONE);
					// img_delete.setVisibility(View.GONE);
					// img_update.setVisibility(View.VISIBLE);
				}
			});

			img_load_update.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (cd.isConnectingToInternet()) {
						if (flag_action == true) {
							// str_bannerid = "";
							// str_bannerid = txv_bannerid.getText().toString();
							// System.out.println("IDONCLICKIMAGE" +
							// str_bannerid);
							// result = "";
							// flag_image = true;
							// selectImageadapter();
						} else {
							Toast.makeText(context,
									R.string.str_Select_Edit_option,
									Toast.LENGTH_SHORT).show();
						}
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});

			img_delete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (cd.isConnectingToInternet()) {
						str_bannerid = "";
						str_bannerid = txv_bannerid.getText().toString();
						System.out.println("1111banneriddeleteclick"
								+ str_bannerid);
						DeleteDialog();
					} else {

						// TODO Auto-generated method stub
						Toast.makeText(context,
								R.string.No_Internet_Connection,
								Toast.LENGTH_SHORT).show();

					}
				}
			});
			img_update.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (cd.isConnectingToInternet()) {
						update = true;
						try {
							System.out.println("clickupdatebannerid"
									+ Object_RestBanner
											.getString("restaurant_banner_id"));
							System.out.println("0000postionupdate" + position);
							System.out.println("0000txvholder"
									+ txv_bannerid.getText().toString());
							System.out.println("0000edlegend"
									+ ed_legand.getText().toString());
							System.out.println("0000txvlegend"
									+ txv_legand.getText().toString());
							str_bannerid = "";
							str_bannerid = txv_bannerid.getText().toString();
							System.out.println("bannerid" + str_bannerid);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						str_update = ed_legand.getText().toString();
						// holder.img_update.setVisibility(View.GONE);
						// holder.img_delete.setVisibility(View.VISIBLE);
						// holder.img_action.setVisibility(View.VISIBLE);
						img_update.setVisibility(View.GONE);
						img_delete.setVisibility(View.VISIBLE);
						img_action.setVisibility(View.VISIBLE);
						if (str_update.length() != 0) {
							new async_maange_gallery().execute();
							ed_legand.setVisibility(View.GONE);
							txv_legand.setVisibility(View.VISIBLE);
						} else {
							Toast.makeText(context, R.string.str_Enter_Legend,
									Toast.LENGTH_LONG).show();
						}
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

	public class async_maange_gallery extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_managephoto;

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

			obj_managephoto = new JSONObject();
			JSONObject obj_restbanner = new JSONObject();
			JSONObject obj_restbanner_insert = new JSONObject();
			JSONObject obj_restbanner_update = new JSONObject();
			JSONObject obj_restbanner_delete = new JSONObject();

			try {
				System.out.println("1111restidinmanagegallery"
						+ Global_variable.restaurant_id);
				if (update == true) {
					System.out.println("updatemagyu");
					obj_restbanner.put("restaurant_id",
							Global_variable.restaurant_id);
					obj_restbanner.put("operation", "update");
					obj_restbanner_update.put("banner_name",
							ManageGalleryActivity.result);
					obj_restbanner_update.put("restaurant_banner_id",
							str_bannerid);
					System.out.println("1111banneridupdate"
							+ Object_RestBanner
									.getString("restaurant_banner_id"));
					// obj_restbanner_update
					// .put("restaurant_banner_id", Object_RestBanner
					// .getString("restaurant_banner_id"));

					obj_restbanner_update.put("description", str_update);
					System.out.println("1111bannerupdate"
							+ obj_restbanner_update);
					obj_managephoto.put("restaurant_banner", obj_restbanner);
					System.out.println("1111galerybanner" + obj_restbanner);
					obj_managephoto.put("update", obj_restbanner_update);
					obj_managephoto.put("sessid", Global_variable.sessid);
					System.out.println("1111obj_managephotoupdate"
							+ obj_managephoto);
					flag_action = false;
				} else {
					System.out.println("deletemagyu");
					obj_restbanner.put("restaurant_id",
							Global_variable.restaurant_id);
					obj_restbanner.put("operation", "delete");
					obj_restbanner_delete.put("restaurant_banner_id",
							str_bannerid);
					System.out.println("1111banneriddelete"
							+ Object_RestBanner
									.getString("restaurant_banner_id"));
					// obj_restbanner_delete
					// .put("restaurant_banner_id", Object_RestBanner
					// .getString("restaurant_banner_id"));
					obj_managephoto.put("restaurant_banner", obj_restbanner);
					System.out.println("1111galerybanner" + obj_restbanner);
					obj_managephoto.put("delete", obj_restbanner_delete);
					obj_managephoto.put("sessid", Global_variable.sessid);
					System.out.println("1111obj_managephotodelete"
							+ obj_managephoto);
				}

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
							Global_variable.rf_api_manage_restaurant_gallery,
							obj_managephoto);

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
					Global_variable.array_RestaurantBanner = new JSONArray();
					ManageGalleryActivity.result = "";
					jsonSuccessStr = json.getString("success");
					Global_variable.sessid = json.getString("sessid");
					System.out.println("1111sessidstep2respo"
							+ Global_variable.sessid);
					if (jsonSuccessStr.equalsIgnoreCase("true")) {
						JSONObject Data = json.getJSONObject("data");
						System.out.println("1111obj_Data" + Data);
						if (Data != null) {
							Global_variable.array_RestaurantBanner = Data
									.getJSONArray("restaurant_banner");
							System.out.println("1111array_RestaurantBanner"
									+ Global_variable.array_RestaurantBanner);

							if (Global_variable.array_RestaurantBanner.length() != 0) {
								// System.out
								// .println("ifmagyuknailength"
								// + Global_variable.array_RestaurantBanner
								// .length());
								// ManageGalleryActivity.lv_gallary
								// .setVisibility(View.VISIBLE);
								// ManageGalleryActivity.txv_invisible
								// .setVisibility(View.GONE);
								// notifyDataSetChanged();
								// //
								// refresAdapter(Global_variable.array_RestaurantBanner);
								// ManageGalleryAdapter = new
								// ManageGalleryAdapter(
								// context,
								// Global_variable.array_RestaurantBanner);
								// ManageGalleryActivity.lv_gallary
								// .setAdapter(ManageGalleryAdapter);
								// update = false;
								// if (DeleteDialog.isShowing()) {
								// DeleteDialog.dismiss();
								// }
								refresAdapter(Global_variable.array_RestaurantBanner);
							} else {
								if (DeleteDialog.isShowing()) {
									DeleteDialog.dismiss();
								}
								System.out
										.println("kelselengthma"
												+ Global_variable.array_RestaurantBanner
														.length());
								ManageGalleryActivity.lv_gallary
										.setVisibility(View.GONE);
								notifyDataSetChanged();
								ManageGalleryActivity.txv_invisible
										.setVisibility(View.VISIBLE);
								Toast.makeText(context,
										R.string.str_no_data_found,
										Toast.LENGTH_LONG).show();
							}
						}
					} else {
						JSONObject Error = json.getJSONObject("errors");
						System.out.println("1111errors" + Error);
						if (Error != null) {

							if (Error.has("banner_name")) {
								Toast.makeText(
										context,
										Error.getJSONArray("banner_name")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}
							if (Error.has("restaurant_banner_id")) {
								Toast.makeText(
										context,
										Error.getJSONArray(
												"restaurant_banner_id").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}
							if (Error.has("description")) {
								Toast.makeText(
										context,
										Error.getJSONArray("description")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}

						}
					}
				} else {
					Toast.makeText(context, R.string.str_no_data_found,
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException np) {

			}
			System.out.println("1111success" + jsonSuccessStr);
		}
	}

	public void DeleteDialog() {

		DeleteDialog = new Dialog(context);
		DeleteDialog.setContentView(R.layout.popup_exitapp);
		DeleteDialog.setTitle(context.getResources().getString(
				R.string.rf_admin_tv_add_food_delete));
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
		txv_Dialogtext.setText(context.getResources().getString(
				R.string.photo_delete));
		try {
			img_yes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					new async_maange_gallery().execute();
				}
			});
			img_no.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					str_bannerid = "";
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

	private void selectImageadapter() {
		String id = "";
		id = str_bannerid;
		final CharSequence[] options = {"Take Photo", "Choose from Gallery",
				"Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					context.startActivityForResult(intent, 1);
				} else if (options[item].equals("Choose from Gallery")) {

					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					context.startActivityForResult(intent, 2);
					//

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	public synchronized void refresAdapter(JSONArray array) {
		array_RestaurantBanner = new JSONArray();
		System.out.println("gyurefresh ma");
		array_RestaurantBanner = Global_variable.array_RestaurantBanner;
		System.out.println("reloadadapt" + array_RestaurantBanner);
		System.out.println("ifmagyuknailength"
				+ Global_variable.array_RestaurantBanner.length());

		notifyDataSetChanged();
		// refresAdapter(Global_variable.array_RestaurantBanner);
		if (Global_variable.array_RestaurantBanner != null) {
			ManageGalleryActivity.lv_gallary.setVisibility(View.VISIBLE);
			ManageGalleryActivity.txv_invisible.setVisibility(View.GONE);
			ManageGalleryAdapter = new ManageGalleryAdapter(context,
					Global_variable.array_RestaurantBanner);
			ManageGalleryActivity.lv_gallary.setAdapter(ManageGalleryAdapter);
		} else {
			ManageGalleryActivity.lv_gallary.setVisibility(View.GONE);
			ManageGalleryActivity.txv_invisible.setVisibility(View.VISIBLE);
		}
		update = false;
		if (DeleteDialog.isShowing()) {
			DeleteDialog.dismiss();
		}
	}
}
