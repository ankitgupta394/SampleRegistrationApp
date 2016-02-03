package com.example.sampleregister;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordDisplay extends Activity {

	TextView password;
	Button login;
	String email, newString, message, joe, decrypt;

	@Override
	public void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.password_display);
		password = (TextView) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);

		if (bun == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				newString = null;
			} else {
				newString = extras.getString("Password");
				email = extras.getString("email");
				message = extras.getString("message");
			}
		} else {
			newString = (String) bun.getSerializable("Password");
			email = (String) bun.getSerializable("email");
			message = (String) bun.getSerializable("message");
		}

		password.setText(newString);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				decrypt = newString;
				for (int i = 0; i < decrypt.length() - 4; i++) {
					decrypt = convert(decrypt);
				}

				new submit().execute();
			}
		});

	}

	public String convert(String str) {
		String result = "";
		int row = 5;
		String c = "+";
		int col = 0;
		String ch[][] = new String[str.length()][5];
		for (int i = 0; i < ch.length; i++) {
			for (int j = 0; j < ch[i].length; j++) {
				ch[i][j] = "";
			}
		}
		for (int i = 0; i < str.length(); i++) {
			ch[i][col] = "" + str.charAt(i);
			// System.out.println("row-col : "+i+"-"+col+" value:"+ch[i][col]);
			if (c.equals("+")) {
				if (col == row - 1) {
					c = "-";
					col--;
				} else {
					col++;
				}
			} else if (c.equals("-")) {
				if (col == 0) {
					c = "+";
					col++;
				} else {
					col--;
				}
			}
		}
		for (int i = 0; i < ch[0].length; i++) {
			result += "";
			for (int j = 0; j < ch.length; j++) {
				result += "" + ch[j][i];
			}
		}
		return result;
	}

	class submit extends AsyncTask<Void, Void, String> {
		private String result, ResponseCode, googlekey, password, Response;
		private ProgressDialog oProgressDialog = null;
		private String url = "https://infinite-spire-5111.herokuapp.com/api/v1/submit",
				UserImage;
		JSONObject result_obj;
		JSONArray RegionLatLngarray;

		@Override
		protected String doInBackground(Void... unsued) {
			byte[] responseData;

			try {
				JSONObject User = new JSONObject();
				JSONObject json = new JSONObject();

				User.put("test", json);

				// json.put("username", entermail.getText().toString().trim());

				json.put("email", email);
				json.put("decrypted_message", decrypt);
				json.put("encrypted_message", newString);
				result = com.connection.Appconstant.getResponseFromServer(url,
						User);
				// response = new String[1];
				Log.e("Response : ", result);

				result_obj = new JSONObject(result);
				Response = result_obj.getString("success");
				ResponseCode = result_obj.getString("message");

			} catch (Exception e) {

				System.out.println("exception occured");
			}

			return null;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();

			oProgressDialog = new ProgressDialog(PasswordDisplay.this);
			oProgressDialog.setMessage("Please wait...");
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
				Toast.makeText(PasswordDisplay.this, ResponseCode,
						Toast.LENGTH_LONG).show();
				if (Response.equalsIgnoreCase("true")) {
					Intent in = new Intent(PasswordDisplay.this,
							RegiterActivity.class);

					startActivity(in);
					finish();
				}

			} catch (Exception e) {
				e.printStackTrace();
				// ShowNetworkError.ShowError(LoginActivity.this);
			}
			// oProgressDialog.dismiss();
		}
	}

}
