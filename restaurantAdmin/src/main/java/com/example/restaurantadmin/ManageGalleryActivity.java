package com.example.restaurantadmin;

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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurantadmin.Global.Global_variable;
import com.restaurantadmin.adapter.DBAdapter;
import com.restaurantadmin.adapter.ManageGalleryAdapter;
import com.restaurantadminconnection.myconnection;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.apache.http.ParseException;
import org.json.JSONArray;
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

public class ManageGalleryActivity extends Activity {

	public static TextView txv_choose;
	public static ImageView img_uploadimage, img_add;
	public static ListView lv_gallary;
	int serverResponseCode = 0;
	//ProgressDialog dialog = null;
	public static ProgressDialog p;
	public static TextView txv_invisible;
	ManageGalleryAdapter ManageGalleryAdapter;
	public static TextView txv_choose_filename;
	public static EditText ed_legand;
	public static String str_insert, str_update, str_delete;
	boolean insert = false;
	boolean update = false;
	public static String str_bannerid;
	public static String result = "", picturePath,
			SELECT = "CAMERA";
	ConnectionDetector cd;
	public static ImageView img_update_btn, img_cancel_btn;
	public static ImageView img_home;
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
		setContentView(R.layout.activity_manage_gallery);
		Initialization();
		cd = new ConnectionDetector(getApplicationContext());

		if (cd.isConnectingToInternet()) {
			new async_maange_gallery().execute();

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

		Setlistner();

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

	// @Override
	// public void onResume() {
	// System.out.println("murtuza_nalawala");
	// super.onResume(); // Always call the superclass method first
	// }
	private void Setlistner() {
		// TODO Auto-generated method stub
		img_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ManageGalleryActivity.this,
						Home_Activity.class);
				startActivity(i);
			}

		});
		txv_choose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				result = "";
				ManageGalleryAdapter.flag_image = false;
				selectImage();
			}
		});
		img_add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insert = true;

				if (ed_legand.getText().toString().length() != 0) {
					new async_maange_gallery().execute();
				} else {
					Toast.makeText(ManageGalleryActivity.this,
							R.string.str_Enter_Legend, Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		img_update_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update = true;
				System.out.println("!!!!pankaj_legend"
						+ ed_legand.getText().toString());
				System.out.println("!!!!pankaj_result" + result);
				System.out.println("!!!!pankaj_restaurant_id"
						+ Global_variable.restaurant_id);
				System.out.println("!!!!pankaj_restaurant_banner_id"
						+ str_bannerid);

				if (ed_legand.getText().toString().length() != 0) {

					new async_maange_gallery().execute();

					img_add.setVisibility(View.VISIBLE);
					img_update_btn.setVisibility(View.GONE);
					img_cancel_btn.setVisibility(View.GONE);

					img_uploadimage.setImageResource(R.drawable.food_images);
				} else {
					Toast.makeText(ManageGalleryActivity.this,
							R.string.str_Enter_Legend, Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		img_cancel_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				img_add.setVisibility(View.VISIBLE);
				img_update_btn.setVisibility(View.GONE);
				img_cancel_btn.setVisibility(View.GONE);
				ed_legand.setText("");
				txv_choose_filename.setText("");
				img_uploadimage.setImageResource(R.drawable.food_images);
			}
		});

	}

	private void Initialization() {
		// TODO Auto-generated method stub
		img_home = (ImageView) findViewById(R.id.img_home);
		txv_choose = (TextView) findViewById(R.id.txv_choose);
		txv_choose_filename = (TextView) findViewById(R.id.txv_choose_filename);
		txv_invisible = (TextView) findViewById(R.id.txv_invisible);
		ed_legand = (EditText) findViewById(R.id.ed_legand);
		img_uploadimage = (ImageView) findViewById(R.id.img_uploadimage);
		img_add = (ImageView) findViewById(R.id.img_add);
		lv_gallary = (ListView) findViewById(R.id.lv_gallary);
		img_update_btn = (ImageView) findViewById(R.id.img_update_btn);
		img_cancel_btn = (ImageView) findViewById(R.id.img_cancel_btn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_gallery, menu);
		return true;
	}

	private void selectImage() {
		final CharSequence[] options = {
				getResources().getString(R.string.str_take_photo),
				getResources().getString(R.string.str_choose_from),
				getResources().getString(R.string.csm_cancel)};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				ManageGalleryActivity.this);
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

			img_uploadimage.setImageBitmap(bitmap);
		

		new UploadFileToServer().execute();
	}
	
	private class UploadFileToServer extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			System.out.println("uploadfiletoserver===");
			// setting progress bar to zero
			p = new ProgressDialog(ManageGalleryActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
			p.setCancelable(false);
			p.setIcon(R.drawable.ic_launcher);
			p.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			System.out.println("amagyudoingma");

			if (SELECT.equalsIgnoreCase("CAMERA")) {
				 uploadFile(fileUri.getPath());
			} else {
				 uploadFile(picturePath);
			}
			return null;
		}

		protected void onPostExecute(String result_string) {
			
			 super.onPostExecute(result_string);
			 p.dismiss();
			System.out.println("!!!!result" + result);

		}

	}
	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle("Response from Servers")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	public int uploadFile(String sourceFileUri) {
		/*dialog = ProgressDialog.show(ManageGalleryActivity.this, "",
				getResources().getString(R.string.str_Uploading), true);*/

		String upLoadServerUri = Global_variable.rf_api_upload_image;
		// String upLoadServerUri =
		// "http://192.168.1.17/RF_admin_api/admin_api_dev/manage_restaurant_gallery";
		String fileName = sourceFileUri;

		System.out.println("!!!!pankaj_sakariya_file_upload_uri"
				+ sourceFileUri);

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
					URL url = new URL(upLoadServerUri);
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
								// tv.setText("File Upload Completed.");
								// Toast.makeText(ManageGalleryActivity.this,
								// R.string.str_Upload_Complete,
								// Toast.LENGTH_SHORT).show();
								result = "";
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

					String line;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
						result += line;

					}

					br.close();
					
					if (result.equalsIgnoreCase(getResources().getString(
							R.string.str_please_wait))) {
						// Toast some error
						Toast.makeText(ManageGalleryActivity.this, "" + result,
								Toast.LENGTH_LONG).show();
					} else {
						System.out.println("final uploaded URL" + result);
						Toast.makeText(ManageGalleryActivity.this, "" + result,
								Toast.LENGTH_LONG).show();
					}
				} catch (MalformedURLException ex) {
					//dialog.dismiss();
					ex.printStackTrace();
					Toast.makeText(ManageGalleryActivity.this,
							R.string.str_MalformedURLException,
							Toast.LENGTH_SHORT).show();
					Log.e(getString(R.string.str_Upload_file_to),
							getString(R.string.str_error) + ex.getMessage(), ex);
				} catch (Exception e) {
					//dialog.dismiss();
					e.printStackTrace();
					Toast.makeText(ManageGalleryActivity.this,
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
			//dialog.dismiss();
			e.printStackTrace();
			// Toast.makeText(getApplicationContext(), "pankaj sakariya catch",
			// Toast.LENGTH_LONG).show();
			return serverResponseCode = 0;
		}

	}

	/*******************
	 * Add to listview
	 * 
	 * 
	 */
	public class async_maange_gallery extends AsyncTask<Void, Void, Void> {

		String jsonSuccessStr;
		JSONObject json;
		JSONObject obj_managephoto;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			System.out.println("Update_magyu_legendr_pre"
					+ ed_legand.getText().toString());

			p = new ProgressDialog(ManageGalleryActivity.this);
			p.setMessage(getResources().getString(R.string.str_please_wait));
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
				if (insert == true) {
					System.out.println("insertmagyu");
					obj_restbanner.put("restaurant_id",
							Global_variable.restaurant_id);
					obj_restbanner.put("operation", "insert");
					obj_restbanner_insert.put("banner_name", result);
					obj_restbanner_insert.put("description", ed_legand
							.getText().toString());
					System.out.println("1111bannerinsert"
							+ obj_restbanner_insert);
					obj_managephoto.put("restaurant_banner", obj_restbanner);
					System.out.println("1111galerybanner" + obj_restbanner);
					obj_managephoto.put("insert", obj_restbanner_insert);
					obj_managephoto.put("sessid", Global_variable.sessid);
					System.out.println("1111obj_managephotoinsert"
							+ obj_managephoto);

				}

				else if (update == true) {

					System.out.println("Update_magyu");
					System.out.println("Update_magyu_banner" + result);
					System.out.println("Update_magyu_legendr"
							+ ed_legand.getText().toString());
					System.out.println("Update_magyu_banner_id" + str_bannerid);

					obj_restbanner.put("restaurant_id",
							Global_variable.restaurant_id);
					obj_restbanner.put("operation", "update");

					obj_restbanner_update.put("banner_name", result);
					obj_restbanner_update.put("description", ed_legand
							.getText().toString());
					obj_restbanner_update.put("restaurant_banner_id",
							str_bannerid);
					System.out.println("1111banner_update"
							+ obj_restbanner_update);
					obj_managephoto.put("restaurant_banner", obj_restbanner);
					System.out.println("1111galerybanner" + obj_restbanner);
					obj_managephoto.put("update", obj_restbanner_update);
					obj_managephoto.put("sessid", Global_variable.sessid);
					System.out.println("1111obj_managephoto_update"
							+ obj_managephoto);

				}

				else {
					System.out.println("elsemagyu");
					obj_restbanner.put("restaurant_id",
							Global_variable.restaurant_id);
					obj_restbanner.put("operation", "fetch");
					obj_managephoto.put("restaurant_banner", obj_restbanner);
					System.out.println("1111galerybanner" + obj_restbanner);
					obj_managephoto.put("sessid", Global_variable.sessid);
					System.out.println("1111obj_managephoto" + obj_managephoto);
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
					String str_response = con.connection(
							ManageGalleryActivity.this,
							Global_variable.rf_api_manage_restaurant_gallery,
							obj_managephoto);

					json = new JSONObject(str_response);
					System.out.println("1111finaljsonstepTG" + json);

					System.out.println("1111finaljsonstep3" + json);
				} catch (ParseException e) {
					e.printStackTrace();

					Log.i(getString(R.string.str_Parse_Exception), e + "");

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
		protected void onPostExecute(Void result1) {
			super.onPostExecute(result1);
			// Dismiss the progress dialog

			if (p.isShowing())
				p.dismiss();
			try {
				if (json != null) {
					result = "";
					ed_legand.setText("");
					txv_choose_filename.setText("");
					Global_variable.array_RestaurantBanner = new JSONArray();
					Global_variable.array_RestaurantBanner_temp = new JSONArray();
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
								System.out.println("ifmagyuknai");
								lv_gallary.setVisibility(View.VISIBLE);
								txv_invisible.setVisibility(View.GONE);
								ManageGalleryAdapter = new ManageGalleryAdapter(
										ManageGalleryActivity.this,
										Global_variable.array_RestaurantBanner);
								lv_gallary.setAdapter(ManageGalleryAdapter);
								ed_legand.setText("");
								txv_choose_filename.setText("");
								img_uploadimage
										.setImageResource(R.drawable.food_images);
								insert = false;
							} else {
								lv_gallary.setVisibility(View.GONE);
								txv_invisible.setVisibility(View.VISIBLE);
								Toast.makeText(ManageGalleryActivity.this,
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
										getApplicationContext(),
										Error.getJSONArray("banner_name")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}
							if (Error.has("restaurant_banner_id")) {
								Toast.makeText(
										getApplicationContext(),
										Error.getJSONArray(
												"restaurant_banner_id").get(0)
												.toString(), Toast.LENGTH_LONG)
										.show();

							}
							if (Error.has("description")) {
								Toast.makeText(
										getApplicationContext(),
										Error.getJSONArray("description")
												.get(0).toString(),
										Toast.LENGTH_LONG).show();

							}

						}
					}
				} else {
					Toast.makeText(ManageGalleryActivity.this,
							R.string.str_no_data_found, Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException np) {

			}
			System.out.println("1111success" + jsonSuccessStr);
		}
	}
}
