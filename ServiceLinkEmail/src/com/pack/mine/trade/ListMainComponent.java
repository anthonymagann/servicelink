package com.pack.mine.trade;


import java.util.ArrayList;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ListMainComponent extends Activity implements OnClickListener{
	
	Button newRequest, prvsRequest, currentRequest, location, email;
	JsonReader json ;
	ArrayList<String> desclist, waitlist, typelist, namelist, pricelist, timelist;
	Intent intent;
	GPSTracker gps;
	NetworkCheck network;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_maincomponent);
		network = new NetworkCheck(this);
		
		json = new JsonReader();
		newRequest = (Button) findViewById(R.id.button1);
		prvsRequest = (Button) findViewById(R.id.button2);
		currentRequest = (Button) findViewById(R.id.button3);
		location  = (Button) findViewById(R.id.button4);
		email  = (Button) findViewById(R.id.button5);
		newRequest.setOnClickListener(this); 
		prvsRequest.setOnClickListener(this); 
		currentRequest.setOnClickListener(this);
		location.setOnClickListener(this);
		email.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.button1:
			startActivity(new Intent(ListMainComponent.this, TradeEntry.class));
			break;
		case R.id.button2: 
			FetchTrade fetch = new FetchTrade();
			fetch.execute();
			//startActivity(new Intent(ListMainComponent.this, TradeList.class));
			break;
		case R.id.button3: 
			Fetchperson fetchperson = new Fetchperson();
			fetchperson.execute();
			break;
		case R.id.button4: 
			gps = new GPSTracker(ListMainComponent.this);
			if(network.check()){
				if(gps.canGetLocation()){
					Toast.makeText(getApplicationContext(), "Your location : "+gps.getLatitude()+" "+gps.getLongitude(), Toast.LENGTH_SHORT).show();
				}else{
					
					gps.showSettingsAlert();
				}
			}else{
				network.showSettingsAlert();
			}
			break;
		case R.id.button5:
			try{
				ConnectivityManager connectivityManager3 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		        if(connectivityManager3.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || 
		                connectivityManager3.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
			
				Intent newActivity=new Intent(android.content.Intent.ACTION_SEND);
				newActivity.putExtra(android.content.Intent.EXTRA_SUBJECT,"Assigment");
				newActivity.setType("plain/text");
				newActivity.putExtra(android.content.Intent.EXTRA_TEXT,"Check your mail. You got new assigment");
				startActivity(newActivity);
		        }else{
		        	Toast as =	Toast.makeText( getApplicationContext(), "Please check Wi-fi / Network!!", Toast.LENGTH_SHORT);
		        	as.setGravity(Gravity.CENTER, 0, 0);
		        	as.show();
		        	
		        }
			}
			catch(ActivityNotFoundException e){					
				Toast.makeText(ListMainComponent.this,"SMS not Supported",Toast.LENGTH_SHORT).show();					
			}
			break;
		}
		
	}
	public class Fetchperson extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(ListMainComponent.this);

        @Override
        public void onPreExecute() {
           // this.dialog.setTitle("Loading data. Please wait...");
            this.dialog.setMessage("Loading data. Please wait...");
            this.dialog.show();
        }

        @Override
        public Void doInBackground(String... param) {
        	// getting JSON Object
        	ArrayList<Tradeperson> list = json.exploreListPerson();
			Log.d("Joson","is"+json.toString());
			namelist = new ArrayList<String>();
			pricelist = new ArrayList<String>();
			timelist = new ArrayList<String>();
			if(list.size()!=0){
				for (int i=0 ; i< list.size();i++){
					Tradeperson obj = list.get(i);
					namelist.add(obj.getName());
					pricelist.add(obj.getPrice());
					timelist.add(obj.getTime());
				}
				intent = new Intent(ListMainComponent.this, TradePersonList.class);
				intent.putStringArrayListExtra("Namelist", namelist);
				intent.putStringArrayListExtra("Pricelist", pricelist);
				intent.putStringArrayListExtra("Timelist", timelist);
				startActivity(intent);
			}
            return null;
        }

        public void onPostExecute(final Void unsed) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }
    }
	public class FetchTrade extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(ListMainComponent.this);

        @Override
        public void onPreExecute() {
           // this.dialog.setTitle("Loading data. Please wait...");
            this.dialog.setMessage("Loading data. Please wait...");
            this.dialog.show();
        }

        @Override
        public Void doInBackground(String... param) {
        	// getting JSON Object
        	ArrayList<Trade> list = json.exploreListrade();
			Log.d("Joson","is"+json.toString());
			desclist = new ArrayList<String>();
			waitlist = new ArrayList<String>();
			typelist = new ArrayList<String>();
			if(list.size()!=0){
				for (int i=0 ; i< list.size();i++){
					Trade obj = list.get(i);
					
					desclist.add(obj.getdesc());
					waitlist.add(obj.getwait());
					typelist.add(obj.getTradeName());
				}
				intent = new Intent(ListMainComponent.this, TradeList.class);
				intent.putStringArrayListExtra("Desclist", desclist);
				intent.putStringArrayListExtra("Waitlist", waitlist);
				intent.putStringArrayListExtra("Typelist", typelist);
				startActivity(intent);
			}
            return null;
        }

        public void onPostExecute(final Void unsed) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
        }
    }

}
