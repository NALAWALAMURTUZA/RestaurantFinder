package com.restaurantadmin.food_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantadmin.ConnectionDetector;
import com.rf.restaurantadmin.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Method;

public class manage_customer_detail extends BaseAdapter {
	private static Activity _activity;
	private String _categories_child_name;
	private JSONArray _array_parsing;
	private static LayoutInflater inflater = null;
	public static String str_delete_id = null;
	private static Boolean hasTelephony;
	 static Boolean hasCamera;
	ConnectionDetector cd;

	public manage_customer_detail(Activity activity,JSONArray array_parsing) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._array_parsing = array_parsing;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cd = new ConnectionDetector(_activity);
		// add PhoneStateListener
			/*	PhoneCallListener phoneListener = new PhoneCallListener();
				TelephonyManager telephonyManager = (TelephonyManager) _activity.getSystemService(Context.TELEPHONY_SERVICE);
				telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);*/
		 
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return this._array_parsing.length();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String first_name = null, mobile_number = null, last_name = null, mydate = null, type = null,email=null ;

		try {
			first_name = _array_parsing.getJSONObject(position).getString("first_name");
			last_name = _array_parsing.getJSONObject(position).getString("last_name");
			email = _array_parsing.getJSONObject(position).getString("email");
			mobile_number = _array_parsing.getJSONObject(position).getString("mobile_number");
			mydate = _array_parsing.getJSONObject(position).getString("mydate");
			type = _array_parsing.getJSONObject(position).getString("type");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.activity_manage_customer_rawfile, null);
		}
		TextView txv_mb_serial_number=(TextView) vi.findViewById(R.id.txv_mb_serial_number);
		TextView txv_mb_customer_name = (TextView) vi.findViewById(R.id.txv_mb_customer_name);
		TextView txv_mb_phonenumber = (TextView) vi.findViewById(R.id.txv_mb_phonenumber);
		TextView txv_mb_email = (TextView) vi.findViewById(R.id.txv_mb_email);
		TextView txv_mb_bussiness = (TextView) vi.findViewById(R.id.txv_mb_bussiness);
		TextView txv_mb_oder_type = (TextView) vi.findViewById(R.id.txv_mb_oder_type);
		TextView txv_mb_oder_bookingdate = (TextView) vi.findViewById(R.id.txv_mb_oder_bookingdate);
		ImageView img_mb_email = (ImageView) vi.findViewById(R.id.img_mb_email);
		ImageView img_mb_phone = (ImageView) vi.findViewById(R.id.img_mb_phone);

		txv_mb_customer_name.setText(first_name +" "+last_name);
		txv_mb_phonenumber.setText(mobile_number);
		txv_mb_email.setText(email);
		txv_mb_bussiness.setText("NA");
		txv_mb_oder_type.setText(type);
		txv_mb_oder_bookingdate.setText(mydate);
		txv_mb_serial_number.setText(String.valueOf(position+1));


		img_mb_email.setOnClickListener(new View.OnClickListener() {
                
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("position" + position);
				// TODO Auto-generated method stub
				Log.i("Send email", "");

				 String[] TO = null;
				try {
					
					TO= new String[_array_parsing.getJSONObject(position).getString("email").length()];
					
					TO[0] = _array_parsing.getJSONObject(position).getString("email");
					System.out.println("TO" + TO);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// String[] CC = {"mcmohd@gmail.com"};
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setData(Uri.parse("mailto:"));
				emailIntent.setType("text/email");
				System.out.println("TO" + TO);
				 emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
				// emailIntent.putExtra(Intent.EXTRA_CC, CC);
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Customer Chat");
				emailIntent.putExtra(Intent.EXTRA_TEXT,"Thank you for using this app" );

				try {
					_activity.startActivity(Intent.createChooser(emailIntent,
							_activity.getString(R.string.str_invitation_send_mail)));
					// finish();
					//Log.i("Finished sending email...", "");
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(_activity,
							R.string.str_there_is_no,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		img_mb_phone.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("position" + position);
				
				if(hasTelephony())
				{
					try {
						if(_array_parsing.getJSONObject(position).getString("mobile_number").toString()!=null)
						{
						  makeCall(_array_parsing.getJSONObject(position).getString("mobile_number").toString());
						}
						else
						{
							Toast.makeText(_activity,R.string.str_mobile_no_not_found,Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				}
				else
				{
					Toast.makeText(_activity,R.string.str_device_not_support,Toast.LENGTH_LONG).show();
				}
			}
		});

		return vi;

	}
	
	public static boolean hasTelephony()
	{
	    if(hasTelephony==null)
	    {
	        TelephonyManager tm=(TelephonyManager )_activity.getSystemService(Context.TELEPHONY_SERVICE);
	        if(tm==null)
	        {
	            hasTelephony=new Boolean(false);
	            return hasTelephony.booleanValue();
	        }
	       /* if(this.getSDKVersion() < 5)
	        {
	            hasTelephony=new Boolean(true);
	            return hasTelephony;
	        }*/
	        PackageManager pm = _activity.getPackageManager();
	        Method method=null;
	       
			if(pm==null)
	            return hasCamera=new Boolean(false);
	        else
	        {
	            try
	            {
	                Class[] parameters=new Class[1];
	                parameters[0]=String.class;
	                method=pm.getClass().getMethod("hasSystemFeature", parameters);
	                Object[] parm=new Object[1];
	                parm[0]=new String(PackageManager.FEATURE_TELEPHONY);
	                Object retValue=method.invoke(pm, parm);
	                if(retValue instanceof Boolean)
	                    hasTelephony=new Boolean(((Boolean )retValue).booleanValue());
	                else
	                    hasTelephony=new Boolean(false);
	            }
	            catch(Exception e)
	            {
	                hasTelephony=new Boolean(false);
	            }
	        }
	    }
	    return hasTelephony;
	}
	
	protected void makeCall(String number) {
	      Log.i("Make call", "");

	      Intent phoneIntent = new Intent(Intent.ACTION_CALL);
	      phoneIntent.setData(Uri.parse("tel:"+number));

	      try {
	        _activity.startActivity(phoneIntent);
	        //_activity.finish();
	         Log.i("Finished making a call...", "");
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(_activity, R.string.str_call_failed, Toast.LENGTH_SHORT).show();
	      }
	   }
	
	//monitor phone call activities
		/*private class PhoneCallListener extends PhoneStateListener {
	 
			private boolean isPhoneCalling = false;
	 
			String LOG_TAG = "LOGGING 123";
	 
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
	 
				if (TelephonyManager.CALL_STATE_RINGING == state) {
					// phone ringing
					Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
				}
	 
				if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
					// active
					Log.i(LOG_TAG, "OFFHOOK");
	 
					isPhoneCalling = true;
				}
	 
				if (TelephonyManager.CALL_STATE_IDLE == state) {
					// run when class initial and phone call ended, 
					// need detect flag from CALL_STATE_OFFHOOK
					Log.i(LOG_TAG, "IDLE");
	 
					if (isPhoneCalling) {
	 
						Log.i(LOG_TAG, "restart app");
	 
						// restart app
						Intent i = _activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(_activity.getBaseContext().getPackageName());
						//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						_activity.startActivity(i);
	 
						isPhoneCalling = false;
					}
	 
				}
			}
		}*/

}
