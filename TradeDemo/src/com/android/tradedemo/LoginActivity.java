package com.android.tradedemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tradedemo.R;

public class LoginActivity extends Activity{
	InputStream in =null;
	String query = "";
	boolean flag=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button bt = (Button) findViewById(R.id.btnlogin);
		if (!bt.isPressed())
		{
			bt.setOnClickListener(new View.OnClickListener() {

				@SuppressLint("NewApi")
				public void onClick(View v) {
					//flag =true;
					NetworkCheck networkCheck=new NetworkCheck(LoginActivity.this);
					Log.i("sdlgh",networkCheck.check()+"");
					if(networkCheck.check())
					{
						EditText txtUserName=(EditText)findViewById(R.id.user);
						EditText txtPassword=(EditText)findViewById(R.id.pass);
						String userName=txtUserName.getText().toString();
						String password=txtPassword.getText().toString();

						try{
							String charset = "UTF-8";
							query = String.format("name=%s&password=%s",URLEncoder.encode(userName, charset),URLEncoder.encode(password, charset)); 
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try
									{
										URL url2 = new URL(Util.serverUrlLogin + "?" + query);
										URLConnection con = url2.openConnection();	
										con.setRequestProperty("Content-Type", "application/json");
										in = con.getInputStream();
										flag=true;
									}
									catch(Exception e){
										e.printStackTrace();
									}
								}
							}).start();
							while(!flag){
							}
							if(flag){
								BufferedReader postBufferedReader = new BufferedReader(new InputStreamReader(in));
								String postline = null;

								while((postline = postBufferedReader.readLine()) != null) {
									String jsonstring=Util.JsonString(postline);
									try
									{
										JSONObject schema = new JSONObject( jsonstring );  
										String s=schema.getString("valid");
										int tradeid=Integer.parseInt(s);
										if(tradeid>0)
										{
											Intent intent = new Intent(LoginActivity.this,TradepersonViewActivity.class);
											intent.putExtra("tradeid",tradeid);
											LoginActivity.this.startActivity(intent);
										}
										else
											Toast.makeText(LoginActivity.this, "Please Check User credentials.", Toast.LENGTH_LONG).show();
									}
									catch(Exception e){
										e.printStackTrace();
									}
								}
							}
						}catch(Exception e){
							e.printStackTrace();

						}
					}
				}
			});
		}
	}
}
