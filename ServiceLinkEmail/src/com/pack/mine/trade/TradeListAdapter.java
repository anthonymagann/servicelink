package com.pack.mine.trade;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TradeListAdapter extends ArrayAdapter<String>{

	private Context ctx;
	private ArrayList<String> desclist, waitlist, typelist;
	LayoutInflater lyi;
	TextView desc, wait, type;

	public TradeListAdapter(Context context,ArrayList<String> desc, ArrayList<String> wait, ArrayList<String> type) {
		super(context,  R.layout.adapter_layout, desc);
		ctx = context;
		this.desclist = desc;
		this.waitlist = wait;
		this.typelist = type;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		lyi = (LayoutInflater) ctx.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View view = lyi.inflate(R.layout.adapter_layout, parent, false);
		desc = (TextView) view.findViewById(R.id.textView1);
		wait = (TextView) view.findViewById(R.id.textView2);
		type = (TextView) view.findViewById(R.id.textView3);
		desc.setText(desclist.get(position));
		wait.setText(waitlist.get(position));
		type.setText(typelist.get(position));
		

		return view;
	}

}
