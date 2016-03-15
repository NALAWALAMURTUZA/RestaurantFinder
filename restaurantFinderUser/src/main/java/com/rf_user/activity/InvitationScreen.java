package com.rf_user.activity;

import java.util.Locale;

import org.json.JSONObject;
import org.json.JSONTokener;

import sharedprefernce.LanguageConvertPreferenceClass;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.rf.restaurant_user.R;
import com.rf_user.global.Global_variable;
import com.rf_user.sqlite_dbadapter.DBAdapter;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookSdk;
//import com.facebook.messenger.MessengerUtils;
//import com.facebook.share.widget.ShareDialog;

public class InvitationScreen extends Activity {

	TextView rf_invitation_restaurant_name;
	ImageView rf_invition_home_button, rf_invition_facebook_icon,
			rf_invition_twitter_icon, rf_invition_mail_icon,
			rf_invition_sms_icon;
	String subject;
	// String composeEmail =
	// "Hey, check this new App called Restaurant Finder. It gives you so many functionalities and you can order from Home. Here is the link for it  https://www.google.com";
	String link, booking_message;
	PackageManager pm;

	/* Facebook declaration */
	private Facebook mFacebook;
	private CheckBox mFacebookBtn;
	private ProgressDialog mProgress;
	private Handler mRunOnUi = new Handler();

	boolean flag = false;

	private static final String[] PERMISSIONS = new String[] {
			"publish_actions", "read_stream" };
	private static final String APP_ID = "841446575941953";
	
	/* Language conversion */
	private Locale myLocale;

	// ShareDialog shareDialog;
	// CallbackManager callbackManager;
	// int REQUEST_CODE_SHARE_TO_MESSENGER = 1;

	// private static final String EXTRA_PROTOCOL_VERSION =
	// "com.facebook.orca.extra.PROTOCOL_VERSION";
	// private static final String EXTRA_APP_ID =
	// "com.facebook.orca.extra.APPLICATION_ID";
	// private static final int PROTOCOL_VERSION = 20150314;
	// private static final String YOUR_APP_ID = "[841446575941953]";
	// private static final int SHARE_TO_MESSENGER_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
		setContentView(R.layout.activity_invitation_screen);

		try {
			
			subject = getString(R.string.str_invitation_subject);

			booking_message = "Your Booking has been Confirmed for "
					+ Global_variable.hotel_name + " on "
					+ Global_variable.str_booking_date + " at "
					+ Global_variable.str_booking_time + " for "
					+ Global_variable.str_booking_number_of_people + " Person.";
			System.out.println("!!!!!!!!!!!!booking_message.."
					+ booking_message);

			initializeWidgets();
			setlistner();

			if (Global_variable.hotel_name.length() != 0) {
				rf_invitation_restaurant_name
						.setText(Global_variable.hotel_name);
			}
			
			loadLocale();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		mProgress = new ProgressDialog(this);
		mFacebook = new Facebook(APP_ID);

		
	}
	
	/*Language conversion methods */
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


	private void setlistner() {
		// TODO Auto-generated method stub

		try {

			rf_invition_home_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent in = new Intent(getApplicationContext(),
							Search_Restaurant_List.class);
					startActivity(in);
					finish();

				}
			});

			mFacebookBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Sharing from an Activity
					// MessengerUtils.shareToMessenger(
					// this,
					// 1,
					// booking_message);

					// String mimeType = "image/*";
					//
					// Intent intent = new Intent(Intent.ACTION_SEND);
					// intent.setPackage("com.facebook.orca");
					// intent.setType(mimeType);
					// intent.putExtra(Intent.EXTRA_STREAM, booking_message);
					// intent.putExtra(EXTRA_PROTOCOL_VERSION,
					// PROTOCOL_VERSION);
					// intent.putExtra(EXTRA_APP_ID, YOUR_APP_ID);
					//
					// this.startActivityForResult(shareIntent,
					// SHARE_TO_MESSENGER_REQUEST_CODE);

					// ShareDialog.show(this, content);

					// if (ShareDialog.canShow(ShareLinkContent.class)) {
					// ShareLinkContent linkContent = new
					// ShareLinkContent.Builder()
					// .setContentTitle("Hello Facebook")
					// .setContentDescription(
					// "The 'Hello Facebook' sample  showcases simple Facebook integration")
					// .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
					// .build();
					//
					// shareDialog.show(linkContent);
					// }
					
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!0"+flag);

					if(flag==false)
					{
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1"+flag);
					        
					        if (mFacebook.isSessionValid()) {
					        	
					        	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!2"+flag);
					        	
								mFacebookBtn.setChecked(true);
								
								String name = SessionStore.getName(getApplicationContext());
								name		= (name.equals("")) ? "Unknown" : name;
								
								
								// String review = "ishita here..!!!! Hey watsup..!!!";

									if (booking_message.equals(""))
										return;

									if (mFacebookBtn.isChecked())
										postToFacebook(booking_message);
									
									
								
								//mFacebookBtn.setText("  Facebook (" + name + ")");
								//mFacebookBtn.setTextColor(Color.WHITE);
							}
					        else
					        {
					        	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!3"+flag);
					        	onFacebookClick();
					        }
					}
					else
					{
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!4"+flag);
						onFacebookClick();
					}

					// /*Initial logic and its working..*/
					//
					// pm = getPackageManager();
					//
					// // pm.
					//
					// try {
					//
					// Intent waIntent = new Intent(Intent.ACTION_SEND);
					// waIntent.setType("text/plain");
					// // link =
					// //
					// "https://play.google.com/store/apps/details?id=com.cricketbuzz.cricscore";
					//
					// // link =
					// "https://play.google.com/store/apps/searchkarigar.com";
					//
					// //String booking_message="Hello..!!!";
					//
					// PackageInfo info = pm.getPackageInfo(
					// "com.facebook.katana",
					// PackageManager.GET_META_DATA);
					// // Check if package exists or not. If not then
					// // code
					// // in catch block will be called
					// waIntent.setPackage("com.facebook.katana");
					//
					// waIntent.putExtra(Intent.EXTRA_TEXT,
					// "http://www.google.com"+booking_message);
					// startActivity(Intent.createChooser(waIntent,
					// "Share with"));
					// //finish();
					//
					// } catch (NameNotFoundException e) {
					//
					// Toast.makeText(getApplicationContext(),
					// "Facebook is not Installed",
					// Toast.LENGTH_SHORT).show();
					// }

				}

			});

			rf_invition_twitter_icon
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							pm = getPackageManager();
							try {

								Intent waIntent = new Intent(Intent.ACTION_SEND);
								waIntent.setType("text/plain");
								// String text =
								// "https://play.google.com/store/apps/details?id=com.cricketbuzz.cricscore";

								// link =
								// "https://play.google.com/store/apps/searchkarigar.com";

								PackageInfo info = pm.getPackageInfo(
										"com.twitter.android",
										PackageManager.GET_META_DATA);
								// Check if package exists or not. If not then
								// code
								// in catch block will be called
								waIntent.setPackage("com.twitter.android");

								waIntent.putExtra(Intent.EXTRA_TEXT,
										booking_message);
								startActivity(Intent.createChooser(waIntent,
										"Share with"));

							} catch (NameNotFoundException e) {

								Toast.makeText(getApplicationContext(),
										R.string.str_twitter_not,
										Toast.LENGTH_SHORT).show();
							}

						}

					});
			rf_invition_sms_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					/** Creating an intent to initiate view action */
					Intent intent = new Intent("android.intent.action.VIEW");

					/** creates an sms uri */
					Uri data = Uri.parse("sms:");

					/* Insert Your message in msg body */
					intent.putExtra("sms_body", booking_message);

					/** Setting sms uri to the intent */
					intent.setData(data);

					/**
					 * Initiates the SMS compose screen, because the activity
					 * contain ACTION_VIEW and sms uri
					 */
					startActivity(intent);

				}
			});

			rf_invition_mail_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("Send email", "");

					// String[] TO = {"amrood.admin@gmail.com"};
					// String[] CC = {"mcmohd@gmail.com"};
					Intent emailIntent = new Intent(Intent.ACTION_SEND);
					emailIntent.setData(Uri.parse("mailto:"));
					emailIntent.setType("text/email");

					// emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
					// emailIntent.putExtra(Intent.EXTRA_CC, CC);
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
					emailIntent.putExtra(Intent.EXTRA_TEXT, booking_message);

					try {
						startActivity(Intent.createChooser(emailIntent,
								getString(R.string.str_invitation_send_mail)));
						// finish();
						//Log.i("Finished sending email...", "");
					} catch (android.content.ActivityNotFoundException ex) {
						Toast.makeText(InvitationScreen.this,
								R.string.str_there_is_no,
								Toast.LENGTH_SHORT).show();
					}
				}
			});

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	private void initializeWidgets() {
		// TODO Auto-generated method stub

		rf_invition_home_button = (ImageView) findViewById(R.id.rf_invition_home_button);

		rf_invitation_restaurant_name = (TextView) findViewById(R.id.rf_invitation_restaurant_name);

		// rf_invition_facebook_icon = (ImageView)
		// findViewById(R.id.rf_invition_facebook_icon);
		rf_invition_twitter_icon = (ImageView) findViewById(R.id.rf_invition_twitter_icon);
		rf_invition_mail_icon = (ImageView) findViewById(R.id.rf_invition_mail_icon);
		rf_invition_sms_icon = (ImageView) findViewById(R.id.rf_invition_sms_icon);

		mFacebookBtn = (CheckBox) findViewById(R.id.cb_facebook);

	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
		System.out.println("!!!!!!!!!!!!!!!onStart");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		System.out.println("!!!!!!!!!!!!!!!onRestart");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		System.out.println("!!!!!!!!!!!!!!!onResume");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		System.out.println("!!!!!!!!!!!!!!!onPause");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		System.out.println("!!!!!!!!!!!!!!!onStop");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		System.out.println("!!!!!!!!!!!!!!!onDestroy");
		LanguageConvertPreferenceClass.loadLocale(getApplicationContext());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	private void onFacebookClick() {
		if (mFacebook.isSessionValid()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setMessage(R.string.str_Delete_current_Facebook_connection)
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   fbLogout();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                
			                mFacebookBtn.setChecked(true);
			           }
			       });
			
			final AlertDialog alert = builder.create();
			
			alert.show();
		} else {
			mFacebookBtn.setChecked(false);
			
			mFacebook.authorize(this, PERMISSIONS, -1, new FbLoginDialogListener());
		}
	}
    
    private final class FbLoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
            SessionStore.save(mFacebook, InvitationScreen.this);
           
            //mFacebookBtn.setText("  Facebook (No Name)");
            mFacebookBtn.setChecked(true);
			//mFacebookBtn.setTextColor(Color.WHITE);
			 
            getFbName();
        }

        public void onFacebookError(FacebookError error) {
           Toast.makeText(InvitationScreen.this, R.string.str_fb_connection, Toast.LENGTH_SHORT).show();
           
           mFacebookBtn.setChecked(false);
        }
        
        public void onError(DialogError error) {
        	Toast.makeText(InvitationScreen.this, R.string.str_fb_connection_fail, Toast.LENGTH_SHORT).show(); 
        	
        	mFacebookBtn.setChecked(false);
        }

        public void onCancel() {
        	mFacebookBtn.setChecked(false);
        }
    }
    
	private void getFbName() {
		mProgress.setMessage("Finalizing ...");
		mProgress.show();
		
		new Thread() {
			@Override
			public void run() {
		        String name = "";
		        int what = 1;
		        
		        try {
		        	String me = mFacebook.request("me");
		        	
		        	JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
		        	name = jsonObj.getString("name");
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        
		        mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();
	}
	
	private void fbLogout() {
		mProgress.setMessage(getString(R.string.str_Disconnecting_from_Facebook));
		mProgress.show();
			
		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(InvitationScreen.this);
		        	   
				int what = 1;
					
		        try {
		        	mFacebook.logout(InvitationScreen.this);
		        		 
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        	
		        mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}
	
	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 0) {
				String username = (String) msg.obj;
		        username = (username.equals("")) ? "No Name" : username;
		            
		        SessionStore.saveName(username, InvitationScreen.this);
		        
		        
		      //  String review = "ishita here..!!!! Hey watsup..!!!";

				if (booking_message.equals(""))
					return;

				if (mFacebookBtn.isChecked())
					postToFacebook(booking_message);
				flag=true;
		        
		        //mFacebookBtn.setText("  Facebook (" + username + ")");
		         
		       // Toast.makeText(InvitationScreen.this, R.string.str_connect_to_fb_as + username, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(InvitationScreen.this, R.string.str_connect_to_fb, Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 1) {
				Toast.makeText(InvitationScreen.this, R.string.str_fb_logout_failed, Toast.LENGTH_SHORT).show();
			} else {
				mFacebookBtn.setChecked(false);
	        	//mFacebookBtn.setText("  Facebook (Not connected)");
	        	//mFacebookBtn.setTextColor(Color.GRAY);
	        	   
				Toast.makeText(InvitationScreen.this, R.string.str_disconnect_fb, Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private void postToFacebook(String review) {
		mProgress.setMessage(getString(R.string.str_invitation_posting));
		mProgress.show();

		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);

		Bundle params = new Bundle();

		params.putString("message", review);
		// params.putString("name", "Dexter");
		// params.putString("caption", "londatiga.net");
		// params.putString("link", "http://www.londatiga.net");
		// params.putString("description",
		// "Dexter, seven years old dachshund who loves to catch cats, eat carrot and krupuk");
		// params.putString("picture", "http://twitpic.com/show/thumb/6hqd44");

		mAsyncFbRunner.request("me/feed", params, "POST",
				new WallPostListener());
	}

	private final class WallPostListener extends BaseRequestListener {
		public void onComplete(final String response) {
			mRunOnUi.post(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();

					Toast.makeText(InvitationScreen.this, R.string.str_post_fb,
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onBackPressed() {

		Intent i = new Intent(InvitationScreen.this, Search_Restaurant_List.class);
		startActivity(i);

	}

}
