package com.connection;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class Appconstant {

	
	public static String getResponseFromServer(String Url, JSONObject json) {
		String result = "";
		byte[] responseData;

		HttpClient httpClient = new DefaultHttpClient();
		httpClient = WebClientDevWrapper.wrapClient(httpClient);
		HttpResponse httpResponse;
		HttpPost oHttpPost = null;

		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setSoTimeout(params, 20000);
		ConnManagerParams.setTimeout(params, 20000);
		try {
			InputStream inputStream;
			StringBuffer stringBuffer;

			oHttpPost = new HttpPost(Url);
			StringEntity oStringEntity = new StringEntity(json.toString());
			oStringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			oHttpPost.setEntity(oStringEntity);

			httpResponse = httpClient.execute(oHttpPost);
			inputStream = httpResponse.getEntity().getContent();

			Log.e("Request:", json.toString());
			//result = EntityUtils.toString(httpResponse.getEntity());
			
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//		    StringBuilder sb = new StringBuilder();
//
//		    String line = null;
//		    try {
//		        while ((line = reader.readLine()) != null) {
//		            sb.append((line + "\n"));
//		        }
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    } finally {
//		        try {
//		        	inputStream.close();
//		        } catch (IOException e) {
//		            e.printStackTrace();
//		        }
//		    }
//		    result= sb.toString();

			responseData = new byte[255];
			stringBuffer = new StringBuffer();

			int length = 0;
			while ((length = inputStream.read(responseData)) != -1) {
				stringBuffer.append(new String(responseData, 0, length));
			}

			result = stringBuffer.toString();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
