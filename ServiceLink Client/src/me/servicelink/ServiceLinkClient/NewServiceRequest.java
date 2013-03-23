package me.servicelink.ServiceLinkClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import me.servicelink.ServiceLinkClient.R;

public class NewServiceRequest extends Activity implements OnClickListener   {
	
	
	EditText txtDescription;
	EditText txtWait;
	TextView txtServe;
	Database dbHelper;
	Spinner spinTrade;
	
	ImageButton ib;
	ImageView iv;
	Intent i;
	final static int cameraData=0;
	Bitmap bmp;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newservicelink);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        txtWait=(EditText)findViewById(R.id.txtWait);
        txtServe=(TextView)findViewById(R.id.txtServe);
        spinTrade=(Spinner)findViewById(R.id.spinTrade);
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart()
	{
		try
		{
		super.onStart();
		dbHelper=new Database(this);
		txtServe.setText(txtServe.getText()+String.valueOf(dbHelper.getRequestCount()));
		
		Cursor c=dbHelper.getAllTrades();
		startManagingCursor(c);
		
		
		
		
		
		SimpleCursorAdapter ca=new SimpleCursorAdapter(this,R.layout.tradespinnerrow, c, new String [] {Database.coltradedes,"_id"}, new int []{R.id.txtDeptName});
		
		spinTrade.setAdapter(ca);
		spinTrade.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View selectedView,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//never close cursor
		}
		catch(Exception ex)
		{
			CatchError(ex.toString());
		}
	}
	
	public void btnAddSer_Click(View view)
	{
		boolean ok=true;
		try
		{
			Spannable spn=txtWait.getText();
			String description=txtDescription.getText().toString();
			int wait=Integer.valueOf(spn.toString());
			int TradeID=Integer.valueOf((int)spinTrade.getSelectedItemId());
			Request ser=new Request(description,wait,TradeID);
			
			dbHelper.AddRequest(ser);
			
		}
		catch(Exception ex)
		{
			ok=false;
			CatchError(ex.toString());
		}
		finally
		{
			if(ok)
			{
				//NotifySerAdded();
				Alerts.ShowSerAddedAlert(this);
				txtServe.setText("Number of Service Requests:        "+String.valueOf(dbHelper.getRequestCount()));
			}
		}
	}
	
	void CatchError(String Exception)
	{
		Dialog diag=new Dialog(this);
		diag.setTitle("Add a Service Request");
		TextView txt=new TextView(this);
		txt.setText(Exception);
		diag.setContentView(txt);
		diag.show();
	}
	
	void NotifySerAdded()
	{
		Dialog diag=new Dialog(this);
		diag.setTitle("Add a Service Request");
		TextView txt=new TextView(this);
		txt.setText("Request Added Successfully");
		diag.setContentView(txt);
		diag.show();
		try {
			diag.wait(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			CatchError(e.toString());
		}
		diag.notify();
		diag.dismiss();
	}
	
	private void initialize() {
		iv = (ImageView) findViewById(R.id.ivReturnPic);
		ib = (ImageButton) findViewById(R.id.ibTakePic);
		ib.setOnClickListener(this);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.ibTakePic:
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i,cameraData);
			break;
		}
		}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			Bundle extras = data.getExtras();
			bmp= (Bitmap) extras.get("data");
		}	iv.setImageBitmap(bmp);
}
	
	
	
}
