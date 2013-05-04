package com.android.tradedemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Util {
	public static String url="http://APG130405phase2.anthonymagann.me/";
	public static String serverUrlLogin=url+"index.php";
	public static String urlTradepersonView=url+"tradePersonView.php";
	public static String urlTradeResponseSave=url+"tradeResponseSave.php";

	public static String JsonString(String line){
		
		if(line.substring(0, 1).equals("\"")){
			line=line.substring(1, line.length());
		}
		if(line.substring(line.length()-1, line.length()).equals("\"")){
			line=line.substring(0, line.length()-1);
		}
		line=line.replace("\\\"","\"");
		return line;
	}

	public void email(String sessionName,Context sessionDetail)
	{
		String cc= "";
		Intent emailintent= new Intent(Intent.ACTION_SEND);
		emailintent.setData(Uri.parse("mailto:"));
		emailintent.putExtra(Intent.EXTRA_EMAIL, new String[] {"info@recycle-a-textbook.com"});
		emailintent.putExtra(Intent.EXTRA_CC, cc);
		emailintent.putExtra(Intent.EXTRA_SUBJECT, "Book details for "+sessionName);
		emailintent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/MyFiles/session.csv"));
		emailintent.setType("message/rfc822");
		sessionDetail.startActivity(Intent.createChooser(emailintent, "Email").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
}
