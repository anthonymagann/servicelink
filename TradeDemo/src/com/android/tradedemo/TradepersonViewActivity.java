package com.android.tradedemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.tradeadapter.ListAdapter;
import com.example.tradedemo.R;

public class TradepersonViewActivity extends Activity{
	InputStream in =null;
	String query = "";
	boolean flag=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		int  tradeid = getIntent().getExtras().getInt("tradeid");
		Log.i("tradeid",tradeid+"");
		NetworkCheck networkCheck=new NetworkCheck(TradepersonViewActivity.this);
		if(networkCheck.check())
		{
			try{
				String charset = "UTF-8";
				query = String.format("tradeid=%s",URLEncoder.encode(tradeid+"", charset)); 
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try
						{
							URL url2 = new URL(Util.urlTradepersonView + "?" + query);
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
				if(flag)
				{
					BufferedReader postBufferedReader = new BufferedReader(new InputStreamReader(in));
					String postline = null;
					ArrayList<String> alResultValue = null;
					ArrayList<String> alRequestId = new ArrayList<String>();
					while((postline = postBufferedReader.readLine()) != null) {
						String jsonString=Util.JsonString(postline);
						try {
							JSONArray jsonArray = new JSONArray(jsonString);
							alResultValue=new ArrayList<String>();
							for(int i=0; i<jsonArray.length(); i++)
							{
								String st="";
								JSONObject j_object= jsonArray.getJSONObject(i);
								JSONArray inner= j_object.names();
								for(int p=0; p<inner.length(); p++)
								{
									try
									{
										if(inner.getString(p).equals("clientname")||inner.getString(p).equals("description")||inner.getString(p).equals("location_id"))
										{
											st=st+inner.getString(p).toUpperCase()+":"+j_object.getString(inner.getString(p))+"\n";
										}
										else if(inner.getString(p).equals("id"))
											alRequestId.add(j_object.getString(inner.getString(p)));
									}
									catch(Exception ex){
										ex.printStackTrace();
									}
								}
								Log.i("ST",st);
								alResultValue.add(st);

								ListAdapter adapter;
								ListView itemlist= (ListView)findViewById(R.id.list);
								adapter=new ListAdapter(this,alResultValue,tradeid,alRequestId,jsonArray);
								itemlist.setAdapter(adapter);
							}
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}
}
