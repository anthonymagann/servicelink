package me.servicelink.ServiceLinkClient;


import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import me.servicelink.ServiceLinkClient.R;

public class Utilities {
static public void ManagecoltradedesSpinner(Context context,Spinner view)
{
	Database dbHelper=new Database(context);
	Cursor c=dbHelper.getAllTrades();
	
	
	
	
	
	@SuppressWarnings("deprecation")
	SimpleCursorAdapter ca=new SimpleCursorAdapter(context,R.layout.tradespinnerrow, c, new String [] {Database.coltradedes,"_id"}, new int []{R.id.txtDeptName});
	view.setAdapter(ca);
	
}
}
