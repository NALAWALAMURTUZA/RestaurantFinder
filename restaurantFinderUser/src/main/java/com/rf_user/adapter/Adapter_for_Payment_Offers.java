package com.rf_user.adapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.restaurant_user.R;
import com.rf_user.activity.Checkout_Tablayout;
import com.rf_user.activity.Payment_Activity;
import com.rf_user.global.Global_variable;
import com.rf_user.internet.ConnectionDetector;
import com.rf_user.sharedpref.SharedPreference;

public class Adapter_for_Payment_Offers extends BaseAdapter {

	Payment_Cart_Adapter payment_cart_adapter;
	private Activity activity;
	private static LayoutInflater inflater = null;
	int layoutResID;
	public static String str_error;
	public JSONArray data;
	public static String str_Items_Object_total,
	              str_discount_amount,str_offer_uid;
	ProgressDialog progressDialog;
	/***Network Connection Instance**/
	ConnectionDetector cd;
	public Adapter_for_Payment_Offers(Activity a, JSONArray data) 
	{
		try{
		
		activity = a;
		this.data = data;
		System.out.println("offers_datay_value" + data);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		/*create Object**/
		cd = new ConnectionDetector(activity);
	} catch (NullPointerException n) {
		n.printStackTrace();
	}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		JSONObject j = null;
		try {
			j = data.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
		return j;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView txv_Test_offer;
		public TextView txv_percentage;
		public TextView txv_disc_onorder;
		public TextView txv_offers_desc;
		public static TextView txv_Apply;                        
		public TextView txv_offers_id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		final ViewHolder holder;
		vi = inflater.inflate(R.layout.delivery_payment_offer_raw_file, parent,
				false);
		holder = new ViewHolder();
		holder.txv_Test_offer = (TextView) vi.findViewById(R.id.txv_Test_offer);
		holder.txv_percentage = (TextView) vi.findViewById(R.id.txv_percentage);
		holder.txv_disc_onorder = (TextView) vi
				.findViewById(R.id.txv_disc_onorder);
		holder.txv_offers_desc = (TextView) vi
				.findViewById(R.id.txv_offers_desc);
		holder.txv_Apply = (TextView) vi.findViewById(R.id.txv_Apply);
		holder.txv_offers_id=(TextView) vi.findViewById(R.id.Offer_id);
		vi.setTag(holder);

		// holder=(ViewHolder)vi.getTag();

		try {
			JSONObject data_detail = getItem(position);
			System.out.println("data.json_detail" + data_detail);

			String description = data_detail.getString("description");
			final String name = data_detail.getString("name");
			final String timeleft = data_detail.getString("timeleft");
			final String to = data_detail.getString("to");
			str_offer_uid = data_detail.getString("uid");
			
			System.out.println("string_mili" + str_offer_uid);
			System.out.println("timeleft" + timeleft);
			// System.out.println("adapter_complete_address"+complete_address);
			holder.txv_offers_id.setText(str_offer_uid);
			holder.txv_Test_offer.setText(name);
			holder.txv_percentage.setText(name);
			holder.txv_disc_onorder.setText(timeleft);
			holder.txv_offers_desc.setText(description);
			
			// holder.txv_Apply.setText(complete_address);
//			if(Bool_Apply==false)
//			{
//		     if(Payment_Activity.str_delivery_charge!=null)
//	         {
//	          Reciept_Activity.receipt_txv_delivery_charge.setText(Global_variable.Categories_sr+" "+Payment_Activity.str_delivery_charge);
////	          int cart_total=Integer.parseInt(Payment_Activity.str_delivery_charge)+Integer.parseInt(String.valueOf(Global_variable.cart_total));
//	          Reciept_Activity.receipt_txv_final_total.setText(Global_variable.Categories_sr+" "+Payment_Activity.final_Cart_Total);
//	         }
//	         else
//	         {
//	        	    Reciept_Activity.receipt_txv_delivery_charge.setText(Global_variable.Categories_sr+" "+"00");
//	        	    Reciept_Activity.receipt_txv_final_total.setText(Global_variable.Categories_sr+" "+String.valueOf(Global_variable.cart_total));
//	         }
//			}
			
			holder.txv_Apply.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					Payment_Activity.Bool_Apply=true;
					Global_variable.Selected_Offer_id=(String) holder.txv_offers_id.getText();
					System.out.println("Murtuza_nalawala_apply_offer");
					System.out.println("Murtuza_nalawala_apply_offer_id"+Global_variable.Selected_Offer_id);
					/**check Internet Connectivity*/
					if (cd.isConnectingToInternet()) 
					{
						new async_applyOfferAPI().execute();
					} 
					else 
					{						
						// TODO Auto-generated method stub
						Toast.makeText(activity,
						R.string.No_Internet_Connection, Toast.LENGTH_SHORT).show();
					}
					
					Payment_Activity.dialog.dismiss();
				}
			});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException n) {

		}

		// TODO Auto-generated method stub
		return vi;
	}

	public class async_applyOfferAPI extends AsyncTask<Void, Void, Void> {
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			try {
				progressDialog = new ProgressDialog(activity);
				progressDialog.setMessage("Please wait...");
				progressDialog.setCancelable(false);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("async_apply_offer");
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			JSONObject Applay_Offer = new JSONObject();

			String delivery_ok = "0";
			/******* Shipping Form ************/
			/*if (delivery_ok == "0") // shipping information
			{

				try {
					Applay_Offer.put("delivery_ok",
							Global_variable.shipping_tag_delivery_ok);
					Applay_Offer.put("house_number",
							Global_variable.Str_Houseno);
					Applay_Offer.put("street", Global_variable.Str_Street);
					Applay_Offer.put("city_id", Global_variable.Str_CityId);
					Applay_Offer.put("district_id",
							Global_variable.Str_DistrictId);
					Applay_Offer
							.put("district", Global_variable.Str_DistrictId);
					Applay_Offer.put("lat", Global_variable.latitude);
					Applay_Offer.put("long", Global_variable.longitude);
					Applay_Offer.put("zoom", "12");
					Applay_Offer.put("zip", Global_variable.Str_Zipcode);
					Applay_Offer.put("user_first_name",
							Global_variable.Str_FirstName);
					Applay_Offer.put("user_last_name",
							Global_variable.Str_LastName);
					Applay_Offer.put("user_email", Global_variable.Str_email);
					Applay_Offer.put("user_contact_number",
							Global_variable.Str_Mobile);
					Applay_Offer.put("user_comment",
							Global_variable.Str_Comment);
					Applay_Offer.put("delivery_schedule_on",
							Global_variable.Str_Delivery_schedule_On);
					Applay_Offer.put("addr_number",
							Global_variable.Str_addr_number);

					System.out.println("ADAPTER_PAY_OFFER_addr_number"+Global_variable.Str_addr_number);
					System.out.println("Shipping_Request_Child" + Applay_Offer);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}*/
			JSONObject Shipping_Request_Main = new JSONObject();
			try {
			Shipping_Request_Main.put("rest_id", Global_variable.hotel_id);
			Shipping_Request_Main.put("addr_type",
					Global_variable.shipping_tag_addr_type);
			// System.out.println("fix_city_spinner"+fetch_spinner);

			Shipping_Request_Main.put("sessid", SharedPreference.getsessid(activity));
			System.out.println("global_shipping_child"
					+ Global_variable.Shipping_Request_Child);
			Shipping_Request_Main.put("ShippingForm",
					Global_variable.Shipping_Request_Child);
			Shipping_Request_Main.put("cart", Global_variable.cart);
			Shipping_Request_Main.put("time_from",
					Global_variable.str_Time_From);
			Shipping_Request_Main.put("time_to",
					Global_variable.str_Time_To);
			Shipping_Request_Main.put("date", Global_variable.str_Date);
			Shipping_Request_Main.put("offer_id", Global_variable.Selected_Offer_id);

			System.out.println("async_step_Shipping_Request_Child"
					+ Shipping_Request_Main);

			
			
			/********** FULL REQUEST WITH offer_id,SHIPPING,CART,AND OTHER STRING *************/
			/*
			try {
				Applay_Offer_main.put("addr_type",Global_variable.shipping_tag_addr_type);
				Applay_Offer_main.put("rest_id", Global_variable.hotel_id);
				Applay_Offer_main.put("time_from",
						Global_variable.str_Time_From);
				Applay_Offer_main.put("time_to", Global_variable.str_Time_To);
				Applay_Offer_main.put("date", Global_variable.str_Date);
				/******* offer id=uid(str_offer_uid)*******/
				/*Applay_Offer_main.put("offer_id",str_offer_uid);
				Applay_Offer_main.put("sessid", Global_variable.sessid);
				Applay_Offer_main.put("ShippingForm", Applay_Offer);
				Applay_Offer_main.put("cart", Global_variable.cart);*/

				System.out.println("Applay_Offer_main" + Shipping_Request_Main);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(NullPointerException m)
			{
				m.printStackTrace();
			}
			System.out.println("Applay_Offer_main" + Shipping_Request_Main);
			// *************
			// for request
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url+
						Global_variable.rf_applyOffer_api_path);

				System.out.println("post_url" + httppostreq);
				StringEntity se = new StringEntity(Shipping_Request_Main.toString(),"UTF-8");
				System.out.println("url_request" + se);
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				se.setContentType("application/json;charset=UTF-8");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json;charset=UTF-8"));
				httppostreq.setEntity(se);

				HttpResponse httpresponse = httpclient.execute(httppostreq);

				System.out.println("http_response" + httpresponse);
				String responseText = null;

				// ****** response text
				try {
					responseText = EntityUtils.toString(httpresponse
							.getEntity(),"UTF-8");
					responseText=responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
					System.out.println("Applay_Offer_main_last_text"
							+ responseText);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("Parse Exception", e + "");
				}
				catch(NullPointerException n)
				{
					
				}

				json = new JSONObject(responseText);
				System.out.println("Applay_Offer_main_last_json" + json);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// json success tag

			/********* Details jsonviewr ******/
			// Json
			// {
			// success{
			// success="true" ,
			// } ,

			// data
			// {
			// items{
			// []cart_cal
			// }
			// other_details{
            //              []offer_applied
			//              }
			//   final_total:
			// }
			// data[]
			// message:
			// sessid:
			// }

			try {
				String message = json.getString("message");
				String sessid = json.getString("sessid");
				System.out.println("sessid" + sessid);
				System.out.println("message" + message);
				// JSONObject data = json.getJSONObject("data");

				Global_variable.json_applyOffer_Success_Object = json
						.getJSONObject("success"); //Full Json Response
				String str_success = Global_variable.json_applyOffer_Success_Object
						.getString("success");
				if (str_success.equals("false")) {
					str_error = Global_variable.json_applyOffer_Success_Object
							.getString("error");
					System.out.println("str_error"+str_error);
					Toast.makeText(activity,str_error,Toast.LENGTH_SHORT).show();
					  
				}
				else
				{
				
				/******** DATA Object {1 ******/
				Global_variable.json_applyOffer_DATA_Object = Global_variable.json_applyOffer_Success_Object
						.getJSONObject("data");
				System.out.println("Global_variable_json_applyOffer_DATA_Object"+Global_variable.json_applyOffer_DATA_Object);

				Global_variable.str_final_total_Apply_Offer = Global_variable.json_applyOffer_DATA_Object
						.getString("final_total");
				System.out.println("str_final_total"+Global_variable.str_final_total_Apply_Offer);
				/******** ITEMS Object ******/
				Global_variable.json_applyOffer_Items_Object = Global_variable.json_applyOffer_DATA_Object
						.getJSONObject("items");
				Global_variable.cart_apply_offer= Global_variable.json_applyOffer_DATA_Object
						.getJSONObject("items").getJSONArray("cart_cal");
				System.out.println("cart_apply_offer"+Global_variable.cart_apply_offer);
				/******** Other Details Object ******/
				Global_variable.json_applyOffer_Other_Details_Object = Global_variable.json_applyOffer_DATA_Object
						.getJSONObject("other_details");
				System.out.println("Global_variable.json_applyOffer_Other_Details_Object"+Global_variable.json_applyOffer_Other_Details_Object);
				str_discount_amount = Global_variable.json_applyOffer_Other_Details_Object
						.getString("discount_amount");
				System.out.println("str_discount_amount"+str_discount_amount);
				Global_variable.json_applyOffer_Offer_Applied_Array = Global_variable.json_applyOffer_Other_Details_Object
						.getJSONArray("offer_applied");
				/******** STRING Other Details Object ******/
				Global_variable.str_delivery_charge_Apply_Offer = Global_variable.json_applyOffer_Other_Details_Object
						.getString("delivery_chargers");
				System.out.println("str_delivery_charge"+Global_variable.str_delivery_charge_Apply_Offer);
				
				
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NullPointerException nu) {
				// TODO Auto-generated catch block
				nu.printStackTrace();
			}
			progressDialog.dismiss();
			//activity.startActivity(new Intent(activity, Payment_Activity.class));
//			payment_cart_adapter.notifyDataSetChanged();
			
			payment_cart_adapter = new Payment_Cart_Adapter(activity,
					Global_variable.cart_apply_offer);

			Payment_Activity.LV_Cart_Payment.setAdapter(payment_cart_adapter);
			Payment_Activity.payment_txv_total.setText(Global_variable.Categories_sr
					+ " " +Global_variable.str_final_total_Apply_Offer  );
			
			if(Checkout_Tablayout.language.equals("ar"))
			{
				
				Checkout_Tablayout.tab.setCurrentTab(2);
				Checkout_Tablayout.tab.getTabWidget().getChildAt(2)
						.setClickable(true);
			}
			else
			{
				
				Checkout_Tablayout.tab.setCurrentTab(2);
				Checkout_Tablayout.tab.getTabWidget().getChildAt(2)
						.setClickable(true);
			}
			//Checkout_Tablayout.tab.setCurrentTab(2);
		}
	}
}
