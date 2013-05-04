package com.android.tradedemo;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tradedemo.R;

public class ResponseActivity extends Activity{
	InputStream in =null;
	String query = "";
	boolean flag=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.responseentry);
		final int  tradeid = getIntent().getExtras().getInt("tradeid");
		Log.i("final int  tradeid",tradeid+"");
		final int  requestId = getIntent().getExtras().getInt("requestId");
		Button savebtn=(Button)findViewById(R.id.savebtn);
		savebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkCheck networkCheck=new NetworkCheck(ResponseActivity.this);
				if(networkCheck.check())
				{
					EditText txtprice=(EditText)findViewById(R.id.etprice);
					EditText txtEstimatedTime=(EditText)findViewById(R.id.etestimatedtime);
					String price=txtprice.getText().toString();
					String estimatedTime=txtEstimatedTime.getText().toString();

					try{
						String charset = "UTF-8";
						query = String.format("price=%s&time=%s&requestid=%s",URLEncoder.encode(price, charset),URLEncoder.encode(estimatedTime, charset),URLEncoder.encode(requestId+"", charset));
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try
								{
									URL url2 = new URL(Util.urlTradeResponseSave + "?" + query);
									URLConnection con = url2.openConnection();	
									con.setRequestProperty("Content-Type", "application/json");
									in = con.getInputStream();
									flag=true;
								}catch(Exception e){
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
									if(s!="")
									{
										Toast.makeText(ResponseActivity.this, s, Toast.LENGTH_LONG).show();
										Intent intent = new Intent(ResponseActivity.this,TradepersonViewActivity.class);
										intent.putExtra("tradeid",tradeid);
										ResponseActivity.this.startActivity(intent);
										finish();
									}
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

		Button cancelbtn=(Button)findViewById(R.id.cancelbtn);
		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});
	}
}
