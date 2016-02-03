package com.example.sampleregister;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.connection.Appconstant;
import com.connection.WebClientDevWrapper;

public class RegiterActivity extends Activity{

	EditText name,organisation,exp;
	Button submit;
	RadioButton radioM, radioF;
	String field="Android";
	@Override
	public void onCreate(Bundle bun){
		super.onCreate(bun);
		setContentView(R.layout.detail);
		name=(EditText) findViewById(R.id.name);
		organisation=(EditText) findViewById(R.id.organisation);
		exp=(EditText) findViewById(R.id.exp);
		submit=(Button) findViewById(R.id.submit);
		radioM = (RadioButton) findViewById(R.id.radioM);
		radioF = (RadioButton) findViewById(R.id.radioF);
		
		radioM.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					
					field="Android";
				} else {
					
					
				}
			}
		});
		radioF.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					field="iOS";
				} else {
					
									}
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if ((TextUtils.isEmpty(name.getText()))
						|| (TextUtils.isEmpty(organisation.getText()))
						|| (TextUtils.isEmpty(exp.getText()))
						
					
						)
Toast.makeText(RegiterActivity.this, "Please fill all field", Toast.LENGTH_LONG).show();	
				else
				new Register().execute();
			}
		});
		
		
		
		

	}
	
	
	
	class Register extends AsyncTask<Void, Void, String> {
		private String result, ResponseCode,googlekey,password;
		private ProgressDialog oProgressDialog = null;
		private String url = "https://infinite-spire-5111.herokuapp.com/api/v1/register";
		JSONObject result_obj;
		JSONArray RegionLatLngarray;

		@Override
		protected String doInBackground(Void... unsued) {
			byte[] responseData;
			
		

			HttpClient httpClient = new DefaultHttpClient();
			httpClient = WebClientDevWrapper.wrapClient(httpClient);
			HttpResponse httpResponse;
			HttpPut oHttpPut = null;

			HttpParams pa = httpClient.getParams();
			HttpConnectionParams.setSoTimeout(pa, 30000);
			ConnManagerParams.setTimeout(pa, 30000);


			try {
				JSONObject User = new JSONObject();
				JSONObject json = new JSONObject();

				User.put("user", json);

				
				
				json.put("email", "ankitgupta394@gmail.com");
				json.put("name", name.getText().toString());
				json.put("gender", "male");
				json.put("phone_number", "1234567890");
				
				json.put("current_company", organisation.getText().toString());
				json.put("experience", exp.getText().toString());
				json.put("area_of_expertise", field);
				json.put("comments", "test comments");

				InputStream inputStream;
				StringBuffer stringBuffer;

				oHttpPut = new HttpPut(url);
				StringEntity oStringEntity = new StringEntity(User.toString());
				oStringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				oHttpPut.setEntity(oStringEntity);

				Log.e("Request : ", json.toString());

				httpResponse = httpClient.execute(oHttpPut);
				inputStream = httpResponse.getEntity().getContent();

				responseData = new byte[255];
				stringBuffer = new StringBuffer();

				int length = 0;
				while ((length = inputStream.read(responseData)) != -1) {
					stringBuffer.append(new String(responseData, 0, length));
				}

				result = stringBuffer.toString();
				inputStream.close();
			
				Log.e("Response : ", result);

				result_obj = new JSONObject(result);
				//Message = result_obj.getString("Message");
				ResponseCode = result_obj.getString("message");
				password= result_obj.getString("encrypted_message");

			} catch (Exception e) {
				
				System.out.println("exception occured");
			}

			return null;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
			
			oProgressDialog = new ProgressDialog(RegiterActivity.this);
			oProgressDialog.setMessage("Please Wait...");
			oProgressDialog.setCancelable(false);
			oProgressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Void... unsued) {

		}

		@Override
		protected void onPostExecute(String sResponse) {

			try {
				oProgressDialog.dismiss();
				Intent in = new Intent(RegiterActivity.this,Thanks.class);
			
				startActivity(in); 
			finish();

			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
		}
	}
}
