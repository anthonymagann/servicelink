package com.android.tradedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.tradedemo.R;

public class RequestDetailActivity extends Activity{
	String  hmArray = "";
	int tradeid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requestdetail);
		this.hmArray = getIntent().getExtras().getString("hmArray");
		this.tradeid = getIntent().getExtras().getInt("tradeid");
		TextView txt=(TextView)findViewById(R.id.requestdetailtext);
		txt.setText(hmArray);
		
		Button btnback=(Button)findViewById(R.id.btnBack);
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
