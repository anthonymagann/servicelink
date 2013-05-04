package com.pack.mine.trade;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class TradeList extends Activity{

	ListView tradelist;
	TradeListAdapter adapter;
	SampleDatabase database ;
	String trade, desc, waittime;
	ArrayList<String> desclist, waitlist, typelist;
	TextView name, price, time;
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_list);
		tradelist = (ListView) findViewById(R.id.lv_tradeList);
		name = (TextView) findViewById(R.id.textView1);
		price = (TextView) findViewById(R.id.textView2);
		time = (TextView) findViewById(R.id.textView3);
		name.setText("Descriptipn");
		price.setText("Wait Time");
		time.setText("Trade type");
		desclist = new ArrayList<String>();
		waitlist= new ArrayList<String>();
		typelist= new ArrayList<String>();
		database = new SampleDatabase(this);
		
		bundle = getIntent().getExtras();
		desclist = bundle.getStringArrayList("Desclist");
		waitlist = bundle.getStringArrayList("Waitlist");
		typelist = bundle.getStringArrayList("Typelist");
		
		
//		database.openToRead();
//		Cursor cr = database.queueTrade();
//		if(cr!=null){
//			if(cr.moveToFirst()){
//				do{
//					trade = cr.getString(cr.getColumnIndex("trade_name"));
//					desc = cr.getString(cr.getColumnIndex("desc"));
//					waittime = cr.getString(cr.getColumnIndex("wait_time"));
//					desclist.add(desc);
//					waitlist.add(waittime);
//					typelist.add(trade);
//				}while(cr.moveToNext());
//			}
//			
//		}
//		database.close();
		adapter = new TradeListAdapter(getApplicationContext(), desclist, typelist, waitlist);
		tradelist.setAdapter(adapter);
	}

}
