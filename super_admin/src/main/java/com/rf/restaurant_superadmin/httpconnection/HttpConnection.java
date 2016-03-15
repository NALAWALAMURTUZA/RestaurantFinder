package com.rf.restaurant_superadmin.httpconnection;

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
import org.json.JSONObject;

import com.superadmin.global.Global_variable;

public class HttpConnection {
	String responseText = null;

	public String connection(String url, JSONObject json) {
		try {

			System.out.println("post_url" + Global_variable.rf_api_Url+url);
		
			DefaultHttpClient httpclient = new DefaultHttpClient();
			StringEntity se = new StringEntity(json.toString(),"UTF-8");
			HttpPost httppostreq = new HttpPost(Global_variable.rf_api_Url+url);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			httppostreq.setEntity(se);
			HttpResponse httpresponse = httpclient.execute(httppostreq);

			try {
				responseText = EntityUtils.toString(httpresponse.getEntity(),"UTF-8");
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
			np.printStackTrace();
		}
		catch (IndexOutOfBoundsException np) {
			np.printStackTrace();
		}
		return responseText;
	}
}
