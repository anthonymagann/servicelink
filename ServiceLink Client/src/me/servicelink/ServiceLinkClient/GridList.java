package me.servicelink.ServiceLinkClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import me.servicelink.ServiceLinkClient.R;

public class GridList extends Activity {
	Database dbHelper;
	static public GridView grid;
	TextView txtTest;
	Spinner spinTrade1;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.gridview);
        grid=(GridView)findViewById(R.id.grid);
        txtTest=(TextView)findViewById(R.id.txtTest);
        spinTrade1=(Spinner)findViewById(R.id.spinTrade1);
        
        Utilities.ManagecoltradedesSpinner(this.getParent(),spinTrade1);
        final Database db=new Database(this);
        try
        {
         
         spinTrade1.setOnItemSelectedListener(new OnItemSelectedListener() {
        	 
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				LoadGrid();
	    		
	    		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
       
        }
        catch(Exception ex)
        {
        	txtTest.setText(ex.toString());
        }
        
        
       
        try
        {
        grid.setOnItemClickListener(new OnItemClickListener()
        {

        	@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				try
				{
			
				SQLiteCursor cr=(SQLiteCursor)parent.getItemAtPosition(position);
				String description=cr.getString(cr.getColumnIndex(Database.coldescription));
				int age=cr.getInt(cr.getColumnIndex(Database.colWait));
				String Trade=cr.getString(cr.getColumnIndex(Database.coltradedes));
				Request ser=new Request(description, age,db.GetTradeID(Trade));
				ser.SetID((int)id);
				AlertDialog diag= Alerts.ShowEditDialog(GridList.this,ser);
				diag.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						txtTest.setText("Canceled");
						//((SimpleCursorAdapter)grid.getAdapter()).notifyDataSetChanged();
						LoadGrid();
					}
				});
				diag.show();
				}
				catch(Exception ex)
				{
					Alerts.CatchError(GridList.this, ex.toString());
				}
			}

			
        }
        );
        }
        catch(Exception ex)
        {
        	
        }

    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	//LoadGrid();
    }
    
    @SuppressWarnings("deprecation")
	public void LoadGrid()
    {
    	dbHelper=new Database(this);
    	try
    	{
    		//Cursor c=dbHelper.getAllServices();
    		View v=spinTrade1.getSelectedView();
			TextView txt=(TextView)v.findViewById(R.id.txtDeptName);
			String Trade=String.valueOf(txt.getText());
    		Cursor c=dbHelper.getServiceByTrade(Trade);
    		startManagingCursor(c);
    		
    		String [] from=new String []{Database.coldescription,Database.colWait,Database.coltradedes};
    		int [] to=new int [] {R.id.coldescription,R.id.colWait,R.id.colTrade};
    		SimpleCursorAdapter sca=new SimpleCursorAdapter(this,R.layout.gridrow,c,from,to);
    		grid.setAdapter(sca);
    		
    		
    		
    	}
    	catch(Exception ex)
    	{
    		AlertDialog.Builder b=new AlertDialog.Builder(this);
    		b.setMessage(ex.toString());
    		b.show();
    	}
    }
	
}
