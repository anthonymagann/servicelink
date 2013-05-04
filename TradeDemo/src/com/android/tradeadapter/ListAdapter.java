package com.android.tradeadapter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tradedemo.RequestDetailActivity;
import com.android.tradedemo.ResponseActivity;
import com.example.tradedemo.R;

public class ListAdapter extends BaseAdapter{
	Context ctx;
	ArrayList<String> hm;
	ArrayList<String> alRequestId;
	String[] hmArray;
	int tradeid;
	JSONArray jsonArray;
	private LayoutInflater inflater=null;
	public ListAdapter(Context ctx,ArrayList<String> hm,int tradeid,ArrayList<String> alRequestId,JSONArray jsonArray){
		this.ctx=ctx;
		this.hm=hm;
		this.alRequestId=alRequestId;
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		hmArray = new String[hm.size()];
		hmArray = hm.toArray(hmArray);
		this.tradeid=tradeid;
		this.jsonArray=jsonArray;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hmArray.length;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi=arg1;
		if(vi==null)
			vi=inflater.inflate(R.layout.item1,null);
		TextView txt=(TextView)vi.findViewById(R.id.text);
		txt.setText(hmArray[arg0]);
		Button btnRespopnse= (Button)vi.findViewById(R.id.responsebtn);
		btnRespopnse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String requestId=alRequestId.get(arg0);
				Intent mIntent= new Intent(ctx,ResponseActivity.class);
				mIntent.putExtra("tradeid", tradeid);
				mIntent.putExtra("requestId",Integer.parseInt(requestId));
				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				ctx.startActivity(mIntent);
			}
		});
		
		RelativeLayout relativeLayout= (RelativeLayout)vi.findViewById(R.id.malayout);
		relativeLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
					Intent mIntent= new Intent(ctx,RequestDetailActivity.class);
					mIntent.putExtra("tradeid", tradeid);
					mIntent.putExtra("jsonString",jsonArray.getJSONObject(arg0).toString());
					mIntent.putExtra("hmArray",hmArray[arg0]);
					mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(mIntent);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		return vi;
	}

}
