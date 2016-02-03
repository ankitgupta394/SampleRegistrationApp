package com.example.sampleregister;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	EditText entermail;
	Button submit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		entermail=(EditText) findViewById(R.id.entermail);
		submit=(Button) findViewById(R.id.submit);
		
		
		
		entermail.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(validEmail(entermail.getText().toString().trim())){
					new Login().execute();
				}else{
					Toast.makeText(MainActivity.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
	}
	
	
	private boolean validEmail(String email) {
		Pattern pattern = Pattern
				.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}");
		// Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}

	
	
	
	class Login extends AsyncTask<Void, Void, String> {
		private String result, ResponseCode,googlekey,password;
		private ProgressDialog oProgressDialog = null;
		private String url = "https://infinite-spire-5111.herokuapp.com/api/v1/sign_up",UserImage;
		JSONObject result_obj;
		JSONArray RegionLatLngarray;

		@Override
		protected String doInBackground(Void... unsued) {
			byte[] responseData;

			try {
				JSONObject User = new JSONObject();
				JSONObject json = new JSONObject();

				User.put("user", json);

				//json.put("username", entermail.getText().toString().trim());
				
				json.put("email", "ankitgupta394gmail");

				result = com.connection.Appconstant.getResponseFromServer(url, User);
				// response = new String[1];
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
			
			oProgressDialog = new ProgressDialog(MainActivity.this);
			oProgressDialog.setMessage("Authenticating...");
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
				Toast.makeText(MainActivity.this, ResponseCode, Toast.LENGTH_LONG).show();

				Intent in = new Intent(MainActivity.this,PasswordDisplay.class);
				in.putExtra("Password", password);
				in.putExtra("message", ResponseCode);
				in.putExtra("email", entermail.getText().toString().trim());
				startActivity(in);
				finish();
			

			} catch (Exception e) {
				e.printStackTrace();
				// ShowNetworkError.ShowError(LoginActivity.this);
			}
			// oProgressDialog.dismiss();
		}
	}
	
	

}
