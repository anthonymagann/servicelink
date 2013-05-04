package com.pack.mine.trade;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class TradeEntry extends Activity{

	Spinner tradeSelect;
	Button submit;
	EditText waitTime, desc;
	ImageView photo;
	ArrayList<String> list;
	int selectedYearFromSpinner;
	Bitmap bitmap;
	SampleDatabase database ;
	String imagePath;
	private JSONParser jsonParser;
	List<NameValuePair> params;
	private static String url = "http://servicelink.me/insert_trade.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trade_entry);

		tradeSelect = (Spinner) findViewById(R.id.spinner1);
		waitTime = (EditText) findViewById(R.id.et_waitTime);
		desc = (EditText) findViewById(R.id.et_desc);
		submit = (Button) findViewById(R.id.bt_submit);
		photo = (ImageView) findViewById(R.id.imageView1);
		database = new SampleDatabase(this);
		jsonParser = new JSONParser();
		list = new ArrayList<String>();
		list.add("Select a trade");
		list.add("Trade 1");
		list.add("Trade 2");
		list.add("Trade 3");
		list.add("Trade 4");
		list.add("Trade 5");


		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);    
		adapter2.setDropDownViewResource(R.layout.simple_droupdown_spinner);
		tradeSelect.setAdapter(adapter2);
		tradeSelect.setSelection(0);
		selectedYearFromSpinner=tradeSelect.getSelectedItemPosition();
		tradeSelect.setSelection(selectedYearFromSpinner);
		submit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(checkEmpty(waitTime) || checkEmpty(desc)){

					Toast.makeText(TradeEntry.this, "Please fill all the fiels", Toast.LENGTH_SHORT).show();
				}else if(tradeSelect.getSelectedItemPosition()==0){
					Toast.makeText(TradeEntry.this, "Select a trade", Toast.LENGTH_SHORT).show();
				}else{
//					database.openToRead();
//					database.insertTrade(list.get(tradeSelect.getSelectedItemPosition()), 
//							waitTime.getText().toString(), desc.getText().toString(), imagePath);
//					database.close();
					params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("trade_name", list.get(tradeSelect.getSelectedItemPosition())));
					params.add(new BasicNameValuePair("wait_time", waitTime.getText().toString()));
					params.add(new BasicNameValuePair("desc", desc.getText().toString()));
					params.add(new BasicNameValuePair("photo",  imagePath));
					Fetch fetch = new Fetch();
					fetch.execute();
					
				}
			}
		});
		photo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				

				AlertDialog.Builder builder = new AlertDialog.Builder(TradeEntry.this);
				builder.setMessage("Select an option").setPositiveButton("Take picture", dialogClickListener)
				    .setNegativeButton("Gallery", dialogClickListener).show();
				
			}
		});

	}
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	           startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), 1);
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
				Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 0);
	            break;
	        }
	    }
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0){
			Uri targetUri = data.getData();	
			imagePath = targetUri.getPath().toString();
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
				photo.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(requestCode == 1){
			Bundle extra = data.getExtras();
			bitmap = (Bitmap) extra.get("data");
			photo.setImageBitmap(bitmap);
		}
	}
	public boolean checkEmpty(EditText etText)
	{
		if(etText.getText().toString().trim().length() <= 0)
			return true;
		else
			return false; 
	}
	public class Fetch extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(TradeEntry.this);

        @Override
        public void onPreExecute() {
           // this.dialog.setTitle("Loading data. Please wait...");
            this.dialog.setMessage("Loading data. Please wait...");
            this.dialog.show();
        }

        @Override
        public Void doInBackground(String... param) {
        	// getting JSON Object
			JSONObject json = jsonParser.getJSONFromUrl(url, params);
			Log.d("Joson","is"+json.toString());
			
            return null;
        }

        public void onPostExecute(final Void unsed) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
                TradeEntry.this.onBackPressed();
            }
        }
    }
}
