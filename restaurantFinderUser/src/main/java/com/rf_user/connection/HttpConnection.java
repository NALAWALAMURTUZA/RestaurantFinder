package com.rf_user.connection;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import sharedprefernce.LanguageConvertPreferenceClass;

import android.app.Activity;

import com.rf_user.global.Global_variable;

public class HttpConnection {
	String responseText = null;
	public String connection(Activity activity, String url, JSONObject json) {
		try {

			System.out.println("post_url" + url);
			System.out.println("Global_variable.rf_lang_Url+url" + Global_variable.rf_lang_Url+url);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(Global_variable.rf_lang_Url+url);
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
	
	
	
	public String connection_rest_reg(Activity activity, String url, JSONObject json) {
		try {

			System.out.println("post_url" + url);
			System.out.println("Global_variable.rf_api_Url_lang+url" + Global_variable.rf_api_Url_lang+url);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(Global_variable.rf_api_Url_lang+url);
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
		//return responseText;
		return responseText.substring(responseText.indexOf("{"), responseText.lastIndexOf("}") + 1);
	}
}
