package com.rf_user.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class AndroidHTTPRequestsActivity  {

 {
	

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://www.wjbty.com/en/api/init");
		try 
		{
			HttpResponse response = httpClient.execute(httpPost);
			Log.d("Http Response:", response.toString());
			System.out.println("!!!!!!!Response"+response.toString());
			String responseContent = EntityUtils.toString(response.getEntity());
			Log.d("Response123", responseContent );
			JSONObject jsonObj;
			try 
			{
				jsonObj = (JSONObject) new JSONTokener(responseContent).nextValue();	
				String sessid=jsonObj.getString("sessid");
				System.out.println("sessid Chetan  :"+sessid);
		
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
	
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();

		}
	}
}