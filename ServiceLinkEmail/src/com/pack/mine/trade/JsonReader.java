package com.pack.mine.trade;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonReader {
	
	public ArrayList<Tradeperson> exploreListPerson(){

		StringBuilder resBuilder = new StringBuilder();
		InputStream in = null;
		ArrayList<Tradeperson> offerList = new ArrayList<Tradeperson>();


		try{			
			URL url = new URL ("http://servicelink.me/query_person.php");
			HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
			httpcon.setRequestMethod("GET");
			httpcon.connect();
			in = httpcon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) { 
				resBuilder.append(line + "\n");	
			}
			httpcon.disconnect();	
		}
		catch (Exception e){			
			Log.e("WebService Error",e.getMessage());
			return offerList;
		}
		finally{
			if (in != null ){
				try{
					in.close();
				}catch(Exception e){					
					Log.e("WebService Error",e.getMessage());
					return offerList;
				}
			}
		}
		String jsonString = resBuilder.toString();
		JSONObject object;
		try {
			String restroString;							
			JSONArray getRestro = new JSONArray(jsonString);

			for(int i=0;i<getRestro.length();i++){				
				object = getRestro.getJSONObject(i);

				Tradeperson restroObject=new Tradeperson();

				restroString = object.getString("name");
				restroObject.setName(restroString);

				restroString = object.getString("price");
				restroObject.setprice(restroString);

				restroString = object.getString("time");
				restroObject.setTime(restroString);


				offerList.add(restroObject);
			}

		} catch (JSONException e) {
			Log.e("JSON Error",e.getMessage());
		}		

		return offerList;

	}
	public ArrayList<Trade> exploreListrade(){

		StringBuilder resBuilder = new StringBuilder();
		InputStream in = null;
		ArrayList<Trade> offerList = new ArrayList<Trade>();


		try{			
			URL url = new URL ("http://servicelink.me/trade_query.php");
			HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
			httpcon.setRequestMethod("GET");
			httpcon.connect();
			in = httpcon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) { 
				resBuilder.append(line + "\n");	
			}
			httpcon.disconnect();	
		}
		catch (Exception e){			
			Log.e("WebService Error",e.getMessage());
			return offerList;
		}
		finally{
			if (in != null ){
				try{
					in.close();
				}catch(Exception e){					
					Log.e("WebService Error",e.getMessage());
					return offerList;
				}
			}
		}
		String jsonString = resBuilder.toString();
		JSONObject object;
		try {
			String restroString;							
			JSONArray getRestro = new JSONArray(jsonString);

			for(int i=0;i<getRestro.length();i++){				
				object = getRestro.getJSONObject(i);

				Trade restroObject=new Trade();

				restroString = object.getString("trade_name");
				restroObject.setTradeName(restroString);

				restroString = object.getString("wait_time");
				restroObject.setwait(restroString);

				restroString = object.getString("description");
				restroObject.setdesc(restroString);

				restroString = object.getString("photo");
				restroObject.setpath(restroString);

				offerList.add(restroObject);
			}

		} catch (JSONException e) {
			Log.e("JSON Error",e.getMessage());
		}		

		return offerList;

	}
}
