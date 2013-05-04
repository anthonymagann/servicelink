package com.pack.mine.trade;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class TradePersonList extends Activity{

	ListView tradelist;
	TradeListAdapter adapter;
	SampleDatabase database ;
	String trade, desc, waittime;
	TextView name, price, time;
	ArrayList<String> namelist, pricelist, timelist;
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_list);
		tradelist = (ListView) findViewById(R.id.lv_tradeList);
		name = (TextView) findViewById(R.id.textView1);
		price = (TextView) findViewById(R.id.textView2);
		time = (TextView) findViewById(R.id.textView3);
		name.setText("Name");
		price.setText("Price");
		time.setText("Arrival Time");
		namelist = new ArrayList<String>();
		pricelist= new ArrayList<String>();
		timelist= new ArrayList<String>();
		database = new SampleDatabase(this);
		
		bundle = getIntent().getExtras();
		namelist = bundle.getStringArrayList("Namelist");
		pricelist = bundle.getStringArrayList("Pricelist");
		timelist = bundle.getStringArrayList("Timelist");
		adapter = new TradeListAdapter(getApplicationContext(), namelist, timelist, pricelist);
		tradelist.setAdapter(adapter);
	}

}
