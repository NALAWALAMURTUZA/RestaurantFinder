package com.example.restaurantadmin;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.food_detail.food_catagory_categories_parent;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("NewApi")
public class Food_Detail_Parent_Activity extends Activity {
	public static ProgressDialog p;
	static food_catagory_categories_parent food_catagory_categories_parent;
	static ListView lv_parentfood;
	public String categories_name = null;
	public static ImageView img_parent_select_image, img_parent_add_button;
	public static EditText txv_parent_name, txv_parent_discription,
			txv_parent_price;
	public static Spinner spiner_parent_spicy_level,
			spiner_parent_spicy_level_no, spiner_parent_spicy_available,
			spiner_parent_vag_nonvag;
	public static TextView rf_admin_heade_cat_name;
	ConnectionDetector cd;
	public static ArrayAdapter<CharSequence> adapter_parent_spiner_available;
	public static ArrayAdapter<CharSequence> adapter_parent_spiner_level;
	public static ArrayAdapter<CharSequence> adapter_parent_spiner_level_no;
	public static ArrayAdapter<CharSequence> adapter_parent_spiner_vag_nonvag;
	public static String update_parent_uid = null, update_parent_id = null,
			update_parent_vary = null;
	public static Activity activity = null;
	//public static ProgressDialog dialog = null;
	int serverResponseCode = 0;
	public static String result = "",SELECT = "CAMERA", picturePath;
	private Locale myLocale;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int GALLERY_CAPTURE_IMAGE_REQUEST_CODE = 300;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_add_food);
		initialization();
		cd = new ConnectionDetector(getApplicationContext());
		Intent intent = getIntent();
		activity = Food_Detail_Parent_Activity.this;
		categories_name = intent.getStringExtra("categories_name");
		rf_admin_heade_cat_name.setText(categories_name);
		System.out.println("my parent parent"
				+ Global_variable.array_parentfood);
		food_catagory_categories_parent = new food_catagory_categories_parent(
				Food_Detail_Parent_Activity.this,
				Global_variable.array_parentfood, categories_name);
		lv_parentfood.setAdapter(food_catagory_categories_parent);
		setOnclicklistner();
		// language*****************
		Locale.getDefault().getLanguage();
		System.out.println("Device_language"
				+ Locale.getDefault().getLanguage());
		String langPref = "Language";
		SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs_oncreat.getString(langPref, "");
		System.out.println("Murtuza_Nalawala_language_oncreat" + language);
		if (language.equalsIgnoreCase("")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if");
		} else if (language.equalsIgnoreCase("ro")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_ar");
			setLocaleonload("ro");
		} else if (language.equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
			setLocaleonload("en");
		} else {
			System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
			setLocaleonload("en");
		}
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// loadLocale();
		// LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
		// language*****************
	}

	// language***************
	public void loadLocale() {

		System.out.println("Murtuza_Nalawala");
		String langPref = "Language";
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, "");
		System.out.println("Murtuza_Nalawala_language" + language);

		changeLang(language);
	}
	public void changeLang(String lang) {
		System.out.println("Murtuza_Nalawala_changeLang");

		if (lang.equalsIgnoreCase(""))
			return;
		myLocale = new Locale(lang);
		System.out.println("Murtuza_Nalawala_123456" + myLocale);
		if (myLocale.toString().equalsIgnoreCase("en")) {
			System.out.println("Murtuza_Nalawala_language_if" + myLocale);

		} else if (myLocale.toString().equalsIgnoreCase("ar")) {
			System.out.println("Murtuza_Nalawala_language_else" + myLocale);
			System.out.println("IN_arabic");

		}
		saveLocale(lang);
		DBAdapter.deleteall();
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
		// updateTexts();

	}

	public void saveLocale(String lang) {

		String langPref = "Language";
		System.out.println("Murtuza_Nalawala_langPref_if" + langPref);
		SharedPreferences prefs = getSharedPreferences("CommonPrefs",
				Activity.MODE_PRIVATE);
		System.out.println("Murtuza_Nalawala_langPref_prefs" + prefs);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
	}
	public void setLocaleonload(String lang) {

		myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		System.out.println("Murtuza_Nalawala_deleteall");

	}

	// language***************
	@Override
	public void onResume() {
		System.out.println("murtuza_nalawala");
		super.onResume(); // Always call the superclass method first
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}
	@Override
	protected void onStop() {
		super.onStop();

	}
	@Override
	protected void onPause() {
		super.onPause();

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	private void setOnclicklistner() {
		// TODO Auto-generated method stub
		lv_parentfood.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println("himurtuza");
				System.out.println("my pozition" + position);
				if (Global_variable.array_food.length() != 0) {
					try {
						Intent in = new Intent(
								Food_Detail_Parent_Activity.this,
								Food_Detail_Child_Activity.class);
						// in.putExtra("id",Global_variable.array_food.getJSONObject(position).getString("id").toString());
						in.putExtra("id", Global_variable.array_parentfood
								.getJSONObject(position).getString("id")
								.toString());
						in.putExtra("uid", Global_variable.array_parentfood
								.getJSONObject(position).getString("uid")
								.toString());
						System.out.println("my pozition"
								+ Global_variable.array_parentfood
										.getJSONObject(position)
										.getString("id").toString());
						in.putExtra(
								"foodname",
								Global_variable.array_parentfood
										.getJSONObject(position)
										.getString("name_en").toString());
						in.putExtra("categories_name", categories_name);
						startActivity(in);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

				}

			}
		});
		img_parent_add_button.setOnClickListener(new View.OnClickListener() {
			boolean flag = true;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * if(txv_parent_name.getText().length()==0) {
				 * Toast.makeText(Food_Detail_Parent_Activity.this,
				 * "Food name cannot be blank", Toast.LENGTH_LONG).show(); }
				 * if(txv_parent_discription.getText().length()==0) {
				 * Toast.makeText(Food_Detail_Parent_Activity.this,
				 * "food description cannot be blank",
				 * Toast.LENGTH_LONG).show(); }
				 * if(txv_parent_price.getText().length()==0) {
				 * Toast.makeText(Food_Detail_Parent_Activity.this,
				 * "Price can not be nill", Toast.LENGTH_LONG).show(); }
				 */
				if (flag == true) {
					if (cd.isConnectingToInternet()) {
						new insertupdatechild().execute();
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										R.string.No_Internet_Connection,
										Toast.LENGTH_SHORT).show();

							}
						});
					}

				}

			}
		});

		img_parent_select_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				result = "";
				selectImage();
			}
		});
	}

	private void selectImage() {
		final CharSequence[] options = {
				getResources().getString(R.string.str_take_photo),
				getResources().getString(R.string.str_choose_from),
				getResources().getString(R.string.csm_cancel)};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				Food_Detail_Parent_Activity.this);
		builder.setTitle(getResources().getString(R.string.str_add_photo));
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals(getResources().getString(
						R.string.str_take_photo))) {
					/*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);*/
					SELECT = "CAMERA";
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
					startActivityForResult(intent,
							CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
					
					
					
				} else if (options[item].equals(getResources().getString(
						R.string.str_choose_from))) {
					SELECT = "GALLERY";
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent,
							GALLERY_CAPTURE_IMAGE_REQUEST_CODE);

				} else if (options[item].equals(getResources().getString(
						R.string.str_Cancel))) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
	
	private void initialization() {
		// TODO Auto-generated method stub
		lv_parentfood = (ListView) findViewById(R.id.lv_parentfood);
		img_parent_add_button = (ImageView) findViewById(R.id.img_parent_add_button);
		img_parent_select_image = (ImageView) findViewById(R.id.img_parent_select_image);
		txv_parent_name = (EditText) findViewById(R.id.txv_parent_name);
		txv_parent_discription = (EditText) findViewById(R.id.txv_parent_discription);
		txv_parent_price = (EditText) findViewById(R.id.txv_parent_price);
		spiner_parent_vag_nonvag = (Spinner) findViewById(R.id.spiner_parent_vag_nonvag);
		spiner_parent_spicy_level = (Spinner) findViewById(R.id.spiner_parent_spicy_level);
		spiner_parent_spicy_level_no = (Spinner) findViewById(R.id.spiner_parent_spicy_level_no);
		spiner_parent_spicy_available = (Spinner) findViewById(R.id.spiner_parent_spicy_available);
		rf_admin_heade_cat_name = (TextView) findViewById(R.id.rf_admin_heade_cat_name);
		adapter_parent_spiner_vag_nonvag = ArrayAdapter.createFromResource(
				Food_Detail_Parent_Activity.this,
				R.array.array_parent_vag_nonvag,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_parent_spiner_vag_nonvag
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner_parent_vag_nonvag.setAdapter(adapter_parent_spiner_vag_nonvag);

		adapter_parent_spiner_available = ArrayAdapter.createFromResource(
				Food_Detail_Parent_Activity.this,
				R.array.array_child_avaibility,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_parent_spiner_available
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner_parent_spicy_available
				.setAdapter(adapter_parent_spiner_available);

		adapter_parent_spiner_level_no = ArrayAdapter.createFromResource(
				Food_Detail_Parent_Activity.this,
				R.array.array_parent_level_of_test,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_parent_spiner_level_no
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner_parent_spicy_level_no.setAdapter(adapter_parent_spiner_level_no);

		adapter_parent_spiner_level = ArrayAdapter.createFromResource(
				Food_Detail_Parent_Activity.this, R.array.array_parent_level,
				android.R.layout.simple_spinner_dropdown_item);
		adapter_parent_spiner_level
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner_parent_spicy_level.setAdapter(adapter_parent_spiner_level);

	}
	/***** AsyncTask ****/
	public class insertupdatechild extends AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		protected void onPreExecute() {
			p = new ProgressDialog(Food_Detail_Parent_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_main = new JSONObject();

			try {
				if (update_parent_id == null) {
					obj_parent.put("id", "");
					obj_parent.put("uid", Global_variable.rest_uid);
					obj_parent.put("vary", "0");
					obj_parent.put("icon", result);
				} else {
					obj_parent.put("id", update_parent_id);
					obj_parent.put("uid", update_parent_uid);
					obj_parent.put("vary", update_parent_vary);
					obj_parent.put("icon", result);
				}
				obj_parent.put("restaurant_id", Global_variable.restaurant_id);
				obj_parent.put("foodCategory_id",
						Global_variable.selected_categories);
				obj_parent.put("name_en", txv_parent_name.getText());
				obj_parent.put("description_en",
						txv_parent_discription.getText());
				obj_parent.put("price", txv_parent_price.getText());
				obj_parent.put("spicy_level_req_on", String
						.valueOf(spiner_parent_spicy_level_no
								.getSelectedItemPosition() + 1));
				obj_parent.put("spicy_level", String
						.valueOf(spiner_parent_spicy_level
								.getSelectedItemPosition()));
				obj_parent.put("veg", String.valueOf(spiner_parent_vag_nonvag
						.getSelectedItemPosition()));

				if (spiner_parent_spicy_available.getSelectedItemPosition() == 0) {
					obj_parent.put("available", "1");
				} else {
					obj_parent.put("available", "0");
				}

				obj_main.put("food", obj_parent);
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);

				myconnection con = new myconnection();
				obj_output = new JSONObject(
						con.connection(activity,
								Global_variable.rf_api_insertupdateparentfood,
								obj_main));

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			System.out.println("output" + obj_output);
			if (p.isShowing()) {
				p.dismiss();
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {
					img_parent_add_button
							.setImageResource(R.drawable.add_button);
					update_parent_id = null;
					update_parent_uid = null;
					update_parent_vary = null;
					img_parent_select_image
							.setImageResource(R.drawable.food_images);
					Food_Detail_Parent_Activity.result = "";
					txv_parent_name.setText("");
					txv_parent_discription.setText("");
					txv_parent_price.setText("");
					spiner_parent_spicy_available.setSelection(0);
					spiner_parent_spicy_level.setSelection(0);
					spiner_parent_spicy_level_no.setSelection(0);
					spiner_parent_vag_nonvag.setSelection(0);
					Global_variable.array_food = obj_output.getJSONObject(
							"data").getJSONArray("food");
					System.out.println(Global_variable.array_food);
					Food_Detail_Categories_Activity food_categories = new Food_Detail_Categories_Activity();
					food_categories.getparticularcategoryfood(
							Global_variable.selected_categories,
							Global_variable.array_food);
					System.out.println("my parent parent"
							+ Global_variable.array_parentfood);
					food_catagory_categories_parent = new food_catagory_categories_parent(
							Food_Detail_Parent_Activity.this,
							Global_variable.array_parentfood, categories_name);
					lv_parentfood.setAdapter(food_catagory_categories_parent);

				} else {
					JSONObject Errors = obj_output.getJSONObject("errors");
					System.out.println("1111loginerrors" + Errors);
					if (Errors != null) {

						if (Errors.has("icon")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("icon").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("name_en")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("name_en").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("price")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("price").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("veg")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("veg").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
						if (Errors.has("description_en")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("description_en")
											.get(0).toString(),
									Toast.LENGTH_LONG).show();
						}
						if (Errors.has("spicy_level")) {
							Toast.makeText(
									getApplicationContext(),
									Errors.getJSONArray("spicy_level").get(0)
											.toString(), Toast.LENGTH_LONG)
									.show();
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/***** AsyncTask ******/
	public static class deleteparentchildfooddetail
			extends
				AsyncTask<Void, Void, Void> {
		JSONObject obj_output;
		protected void onPreExecute() {
			super.onPreExecute();
			p = new ProgressDialog(activity);
			// p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			JSONObject obj_parent = new JSONObject();
			JSONObject obj_main = new JSONObject();
			try {

				// obj_parent.put("restaurant_id",
				// Global_variable.restaurant_id);
				// obj_parent.put("restaurant_id", "50");
				obj_parent.put("restaurant_id", Global_variable.restaurant_id);
				obj_parent.put("type", "P");
				obj_parent
						.put("id",
								com.restaurantadmin.food_detail.food_catagory_categories_parent.str_delete_id);
				obj_main.put("delete_object", obj_parent);
				obj_main.put("sessid", Global_variable.sessid.toString());
				System.out.println("obj_main" + obj_main);
				myconnection con = new myconnection();
				obj_output = new JSONObject(con.connection(activity,
						Global_variable.rf_api_deleteparentchildfooddetail,
						obj_main));

			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NullPointerException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			System.out.println("output" + obj_output);
			if (p.isShowing()) {
				p.dismiss();
			}
			try {
				if (obj_output.getString("success").equalsIgnoreCase("true")) {

					Global_variable.array_food = obj_output.getJSONObject(
							"data").getJSONArray("food");
					System.out.println(Global_variable.array_food);
					Food_Detail_Categories_Activity food_categories = new Food_Detail_Categories_Activity();
					food_categories.getparticularcategoryfood(
							Global_variable.selected_categories,
							Global_variable.array_food);
					Food_Detail_Parent_Activity b = new Food_Detail_Parent_Activity();
					food_catagory_categories_parent = new food_catagory_categories_parent(
							activity, Global_variable.array_parentfood,
							b.categories_name);
					lv_parentfood.setAdapter(food_catagory_categories_parent);
					img_parent_add_button
							.setImageResource(R.drawable.add_button);
					update_parent_id = null;
					update_parent_uid = null;
					update_parent_vary = null;
					img_parent_select_image
							.setImageResource(R.drawable.food_images);
					Food_Detail_Parent_Activity.result = "";
					txv_parent_name.setText("");
					txv_parent_discription.setText("");
					txv_parent_price.setText("");
					spiner_parent_spicy_available.setSelection(0);
					spiner_parent_spicy_level.setSelection(0);
					spiner_parent_spicy_level_no.setSelection(0);
					spiner_parent_vag_nonvag.setSelection(0);
				} else {

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				System.out.println("camerafriom");
				launchUploadActivity(true);
//				File f = new File(Environment.getExternalStorageDirectory()
//						.toString());
//				for (File temp : f.listFiles()) {
//					if (temp.getName().equals("temp.jpg")) {
//						f = temp;
//						break;
//					}
//				}
//				try {
//					final Bitmap bitmap;
//					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//							bitmapOptions);
//
//					// bitmap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);
//					// img_parent_select_image.setImageBitmap(bitmap);
//
//					File path = android.os.Environment
//							.getExternalStorageDirectory();
//					f.delete();
//					OutputStream outFile = null;
//					final File file = new File(path, String.valueOf(System
//							.currentTimeMillis()) + ".jpg");
//					try {
//						outFile = new FileOutputStream(file);
//						bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//
//						runOnUiThread(new Runnable() {
//							public void run() {
//								// tv.setText("File Upload Completed.");
//								StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//										.permitAll().build();
//								StrictMode.setThreadPolicy(policy);
//								int i = uploadFile(file.getAbsolutePath());
//
//								System.out
//										.println("!!!!pankaj_sakariya_response_code"
//												+ i);
//								if (i == 0) {
//									Toast.makeText(getApplicationContext(),
//											getResources().getString(
//													R.string.invalid_file_name), Toast.LENGTH_LONG)
//											.show();
//								} else if (i == 200) {
//									img_parent_select_image
//											.setImageBitmap(bitmap);
//								}
//								// Toast.makeText(Food_Detail_Parent_Activity.this,
//								// "File Upload Start.",
//								// Toast.LENGTH_SHORT).show();
//							}
//						});
//						outFile.flush();
//						outFile.close();
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					} catch (IOException e) {
//						e.printStackTrace();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			} else if (requestCode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = {MediaStore.Images.Media.DATA};
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
			     picturePath = c.getString(columnIndex);
				c.close();
//				final Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//				Log.w(getString(R.string.str_path_of_image), picturePath + "");
//
//				// img_parent_select_image.setImageBitmap(thumbnail);
//
//				runOnUiThread(new Runnable() {
//					public void run() {
//						// tv.setText("File Upload Completed.");
//						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//								.permitAll().build();
//						StrictMode.setThreadPolicy(policy);
//						int i = uploadFile(picturePath);
//						if (i == 0) {
//							Toast.makeText(
//									getApplicationContext(),
//									getResources().getString(
//											R.string.invalid_file_name),
//									Toast.LENGTH_LONG).show();
//						} else if (i == 200) {
//							img_parent_select_image.setImageBitmap(thumbnail);
//						}
//						// Toast.makeText(Food_Detail_Parent_Activity.this,
//						// "File Upload Start.", Toast.LENGTH_SHORT).show();
//					}
//				});
				
				
				runOnUiThread(new Runnable() {
					public void run() {
						// tv.setText("File Upload Completed.");
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
								.permitAll().build();
						StrictMode.setThreadPolicy(policy);
						System.out.println("strictmodthead");
						launchUploadActivity(true);

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!" + picturePath);

						// Toast.makeText(MainActivity.this,
						// "File Upload Start.", Toast.LENGTH_SHORT).show();
					}
				});

			}
		}
	}*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				// successfully captured the image
				// launching upload activity
				launchUploadActivity(true);

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();

			}
			// else {
			// // failed to capture image
			// Toast.makeText(getApplicationContext(),
			// "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
			// .show();
			// }

		} else if (requestCode == GALLERY_CAPTURE_IMAGE_REQUEST_CODE) {
			System.out.println("wdqwd==");
			if (resultCode == RESULT_OK) {

				// successfully captured the image
				// launching upload activity
				System.out.println("wdqwd==ok");
				fileUri = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				System.out.println("path of image from gallery====cursur=="
						+ fileUri);
				System.out.println("FILEPATHCURSOR==" + filePath);
				Cursor c = getContentResolver().query(fileUri, filePath, null,
						null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				picturePath = c.getString(columnIndex);
				// launchUploadActivity(true);

				c.close();
				System.out.println("pictuerpathfromexternal" + picturePath);
				// Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				System.out.println("path of image from gallery====" + fileUri);
				Log.i("selectedImage", "path of image from gallery......"
						+ fileUri);

				runOnUiThread(new Runnable() {
					public void run() {
						// tv.setText("File Upload Completed.");
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
								.permitAll().build();
						StrictMode.setThreadPolicy(policy);
						System.out.println("strictmodthead");
						launchUploadActivity(true);

						System.out
								.println("!!!!!!!!!!!!!!!!!!!!" + picturePath);

						// Toast.makeText(MainActivity.this,
						// "File Upload Start.", Toast.LENGTH_SHORT).show();
					}
				});

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();

			}
			// else {
			// // failed to capture image
			// Toast.makeText(getApplicationContext(),
			// "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
			// .show();
			// }

		} else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				// video successfully recorded
				// launching upload activity
				launchUploadActivity(false);

			} else if (resultCode == RESULT_CANCELED) {

				// user cancelled recording
				Toast.makeText(getApplicationContext(),
						"User cancelled video recording", Toast.LENGTH_SHORT)
						.show();

			} else {
				// failed to record video
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to record video", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	
	private void launchUploadActivity(boolean isImage) {
		System.out.println("ayuknai");
		// System.out.println("inlaunchmethodfileuri" + fileUri.getPath());
		System.out.println("inlaunchmethodpicturePath" + picturePath);

		if (SELECT.equalsIgnoreCase("CAMERA")) {
			UploadActivity(fileUri.getPath());
		} else {
			UploadActivity(picturePath);
		}
	}
	private void UploadActivity(String path) {
		// TODO Auto-generated method stub
		if (path != null) {
			// Displaying the image or video on the screen
			//Toast.makeText(getApplicationContext(), "file path is correct",
				//	Toast.LENGTH_LONG).show();

			previewMedia(path);
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}
	}
	private void previewMedia(String path) {
		// Checking whether captured media is image or video
		System.out.println("imgPreview==visible");
		// bimatp factory
		BitmapFactory.Options options = new BitmapFactory.Options();

		// down sizing image as it throws OutOfMemory Exception for larger
		// images
		options.inSampleSize = 8;
		System.out.println("filepathein previewmwdia" + path);
		final Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		
			img_parent_select_image.setImageBitmap(bitmap);
		

		new UploadFileToServer().execute();
	}
	
	private class UploadFileToServer extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			System.out.println("uploadfiletoserver===");
			// setting progress bar to zero
			p = new ProgressDialog(Food_Detail_Parent_Activity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}
	

	
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			System.out.println("amagyudoingma");

			if (SELECT.equalsIgnoreCase("CAMERA")) {
				uploadFile(fileUri.getPath());
			} else {
				 uploadFile(picturePath);
			}
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {

			System.out.println("Murtuzanalawalaalal" + result);
			// super.onPostExecute(result);
			p.dismiss();
		
		}

	}
	public int uploadFile(String sourceFileUri) {
	/*	dialog = ProgressDialog.show(Food_Detail_Parent_Activity.this, "",
				getResources().getString(R.string.str_Uploading), true);
		/*
		 * p = new ProgressDialog(activity); p.setMessage("Please wait...");
		 * p.setCancelable(false); p.setIcon(R.drawable.ic_launcher); p.show();
		 */

		String fileName = sourceFileUri;

		try {
			if (!sourceFileUri.equalsIgnoreCase("")
					|| !sourceFileUri.equalsIgnoreCase("null")
					|| !sourceFileUri.equalsIgnoreCase(null)) {

				HttpURLConnection conn = null;
				DataOutputStream dos = null;
				String lineEnd = "\r\n";
				String twoHyphens = "--";
				String boundary = "*****";
				int bytesRead, bytesAvailable, bufferSize;
				byte[] buffer;
				int maxBufferSize = 1 * 1024 * 1024;
				File sourceFile = new File(sourceFileUri);
				if (!sourceFile.isFile()) {
					Log.e(getString(R.string.str_uploadFile),
							getString(R.string.str_Source_File_not_exist));
					return 0;
				}
				try { // open a URL connection to the Servlet
					FileInputStream fileInputStream = new FileInputStream(
							sourceFile);
					URL url = new URL(Global_variable.upLoadServerUri);
					conn = (HttpURLConnection) url.openConnection(); // Open a
																		// HTTP
																		// connection
																		// to
																		// the
																		// URL
					conn.setDoInput(true); // Allow Inputs
					conn.setDoOutput(true); // Allow Outputs
					conn.setUseCaches(false); // Don't use a Cached Copy
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Connection", "Keep-Alive");
					conn.setRequestProperty("ENCTYPE", "multipart/form-data");
					conn.setRequestProperty("Content-Type",
							"multipart/form-data;boundary=" + boundary);
					conn.setRequestProperty("uploaded_file", fileName);
					dos = new DataOutputStream(conn.getOutputStream());
					System.out.println("!!!!DOS" + dos.toString());
					System.out.println("!!!!url" + url);
					System.out.println("!!!!filename" + fileName);

					dos.writeBytes(twoHyphens + boundary + lineEnd);
					dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
							+ fileName + "\"" + lineEnd);
					dos.writeBytes(lineEnd);

					bytesAvailable = fileInputStream.available(); // create a
																	// buffer of
																	// maximum
																	// size

					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					buffer = new byte[bufferSize];

					// read file and write it into form...
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

					while (bytesRead > 0) {
						dos.write(buffer, 0, bufferSize);
						bytesAvailable = fileInputStream.available();
						bufferSize = Math.min(bytesAvailable, maxBufferSize);
						bytesRead = fileInputStream.read(buffer, 0, bufferSize);
					}

					// send multipart form data necesssary after file data...
					dos.writeBytes(lineEnd);
					dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

					// Responses from the server (code and message)
					serverResponseCode = conn.getResponseCode();
					String serverResponseMessage = conn.getResponseMessage();

					Log.i(getString(R.string.str_uploadFile),
							getString(R.string.str_http_response)
									+ serverResponseMessage + ": "
									+ serverResponseCode);
					if (serverResponseCode == 200) {
						runOnUiThread(new Runnable() {
							public void run() {
								/*
								 * if (p.isShowing()) { p.dismiss(); }
								 */
								// tv.setText("File Upload Completed.");
								// Toast.makeText(Food_Detail_Parent_Activity.this,
								// "File Upload Complete.",
								// Toast.LENGTH_SHORT).show();
							}
						});
					}

					// close the streams //
					fileInputStream.close();
					dos.flush();
					dos.close();

					InputStream stream = conn.getInputStream();
					InputStreamReader isReader = new InputStreamReader(stream);

					// put output stream into a string
					BufferedReader br = new BufferedReader(isReader);

					String line = "";
					while ((line = br.readLine()) != null) {
						System.out.println(line);
						result += line;
					}

					br.close();

					System.out.println("!!!!result" + result);
					//p.dismiss();
					if (result
							.equalsIgnoreCase(getString(R.string.str_Please_Try_again))) {
						// Toast some error
						// Toast.makeText(Food_Detail_Parent_Activity.this,
						// ""+result,
						// Toast.LENGTH_LONG).show();
					} else {
						System.out.println("final uploaded URL" + result);
						// Toast.makeText(Food_Detail_Parent_Activity.this,
						// ""+result,
						// Toast.LENGTH_LONG).show();
					}
					//p.dismiss();
				} catch (MalformedURLException ex) {
					//dialog.dismiss();
					ex.printStackTrace();
					Toast.makeText(Food_Detail_Parent_Activity.this,
							R.string.str_MalformedURLException,
							Toast.LENGTH_SHORT).show();
					Log.e(getString(R.string.str_Upload_file_to_server),
							getString(R.string.str_error) + ex.getMessage(), ex);
				} catch (Exception e) {
					//dialog.dismiss();
					e.printStackTrace();
					Toast.makeText(Food_Detail_Parent_Activity.this,
							R.string.str_Exception + e.getMessage(),
							Toast.LENGTH_SHORT).show();
					Log.e(getString(R.string.str_Upload_file_to_server),
							R.string.str_Exception + e.getMessage(), e);
				}
				//dialog.dismiss();
				return serverResponseCode;

			} else {
				//dialog.dismiss();
				// Toast.makeText(getApplicationContext(),
				// "pankaj sakariya else part"+sourceFileUri+"file name",
				// Toast.LENGTH_LONG).show();
				return serverResponseCode = 0;
			}
		} catch (Exception e) {
		//	dialog.dismiss();
			e.printStackTrace();
			// Toast.makeText(getApplicationContext(), "pankaj sakariya catch",
			// Toast.LENGTH_LONG).show();
			return serverResponseCode = 0;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK :
				onBackPressed();
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {
		/** check Internet Connectivity */
		if (cd.isConnectingToInternet()) {
			Intent in = new Intent(Food_Detail_Parent_Activity.this,
					Food_Detail_Categories_Activity.class);
			startActivity(in);
		} else {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(),
							R.string.No_Internet_Connection, Toast.LENGTH_SHORT)
							.show();
				}

			});
		}

	}
}
