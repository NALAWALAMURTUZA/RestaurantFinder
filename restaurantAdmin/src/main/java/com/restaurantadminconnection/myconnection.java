package com.restaurantadminconnection;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.restaurantadmin.Global.Global_variable;
import com.sharedprefernce.LanguageConvertLocalPrefernce;
import com.sharedprefernce.LanguageConvertPreferenceClass;

import android.app.Activity;

public class myconnection {
	String responseText = null;

	public String connection(Activity activity, String url, JSONObject json) {
		try {

			System.out.println("post_url" + url);
			String str_response = "";
			if (LanguageConvertLocalPrefernce.getLocale(activity)
					.equalsIgnoreCase("en")) {
				str_response = Global_variable.rf_api_Url + url;
				// System.out.println("enurl"+str_response);
			} else if (LanguageConvertLocalPrefernce.getLocale(activity)
					.equalsIgnoreCase("ro")) {
				str_response = Global_variable.rf_api_Url_ro + url;
				// System.out.println("rourl"+str_response);
			} else {
				str_response = Global_variable.rf_api_Url + url;
				// System.out.println("defaultrourl"+str_response);
			}
			System.out.println("post_url" + str_response);
			// chetan****************
			// DefaultHttpClient httpclient = new DefaultHttpClient();
			// StringEntity se = new StringEntity(json.toString());
			// HttpPost httppostreq = new HttpPost(url);
			// se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
			// "application/json"));
			// se.setContentType("application/json;charset=UTF-8");
			// se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
			// "application/json;charset=UTF-8"));
			// httppostreq.setEntity(se);
			// HttpResponse httpresponse = httpclient.execute(httppostreq);
			//
			// try {
			// responseText = EntityUtils.toString(httpresponse.getEntity());
			// } catch (ParseException e) {
			// e.printStackTrace();
			//
			// } catch (NullPointerException np) {
			//
			// }
			// *****************************************************************************
			// pankaj*****************
			// String responseText = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(str_response);
			httppostreq.setHeader("Accept", "application/json");
			httppostreq.setHeader("Content-type", "application/json");
			httppostreq.setEntity(new StringEntity(json.toString(), "UTF-8"));
			HttpResponse httpresponse = httpclient.execute(httppostreq);

			try {
				responseText = EntityUtils.toString(httpresponse.getEntity(),
						"UTF-8");
				System.out.println("response=" + responseText);
			} catch (ParseException e) {
				e.printStackTrace();

			} catch (NullPointerException np) {

			}
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NullPointerException np) {

		}
		return responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
	}
}
