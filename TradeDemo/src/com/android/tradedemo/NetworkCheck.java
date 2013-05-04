package com.android.tradedemo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class NetworkCheck {

	Context c;
	public NetworkCheck(Context c){
		this.c = c;
	}
	public boolean check(){
		ConnectivityManager connectivityManager = (ConnectivityManager)c.getSystemService(c.CONNECTIVITY_SERVICE);
		if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || 
				connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {	  
			return true;
		}
		return false;
	}
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle("Network Problem!");
        alertDialog.setMessage("Make sure you are connected to Internet.");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            	c.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}
}
