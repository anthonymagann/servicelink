package me.servicelink.ServiceLinkClient;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import me.servicelink.ServiceLinkClient.R;

public class Alerts {
public static void ShowSerAddedAlert(Context con)
{
	AlertDialog.Builder builder=new AlertDialog.Builder(con);
	builder.setTitle("Add new Service Request");
	builder.setIcon(android.R.drawable.ic_dialog_info);
	DialogListner listner=new DialogListner();
	builder.setMessage("Request Added successfully");
	builder.setPositiveButton("ok", listner);
	
	AlertDialog diag=builder.create();
	diag.show();
}

public static AlertDialog ShowEditDialog(final Context con,final Request ser)
{
	AlertDialog.Builder b=new AlertDialog.Builder(con);
	b.setTitle("Requests");
	LayoutInflater li=LayoutInflater.from(con);
	View v=li.inflate(R.layout.editservice, null);
	
	b.setIcon(android.R.drawable.ic_input_get);
	
	b.setView(v);
	final TextView txtDescription=(TextView)v.findViewById(R.id.txtDeldescription);
	final TextView txtWait=(TextView)v.findViewById(R.id.txtDelWait);
	final Spinner spin=(Spinner)v.findViewById(R.id.spinDiagTrade);
	Utilities.ManagecoltradedesSpinner(con, spin);
	for(int i=0;i<spin.getCount();i++)
	{
		long id=spin.getItemIdAtPosition(i);
		if(id==ser.getTrade())
		{
			spin.setSelection(i, true);
			break;
		}
	}
	
	
	txtDescription.setText(ser.getdescription());
	txtWait.setText(String.valueOf(ser.getWait()));
	
	b.setPositiveButton("Modify", new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			ser.setdescription(txtDescription.getText().toString());
			ser.setWait(Integer.valueOf(txtWait.getText().toString()));
			ser.setTrade((int)spin.getItemIdAtPosition(spin.getSelectedItemPosition()));
			
			try
			{
			Database db=new Database(con);
			db.UpdateSer(ser);
			
			}
			catch(Exception ex)
			{
				CatchError(con, ex.toString());
			}
		}
	});
	
	b.setNeutralButton("Delete", new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Database db=new Database(con);
			db.DeleteSer(ser);
		}
	});
	b.setNegativeButton("Cancel", null);
	
	return b.create();
	//diag.show();
	
}

static public void CatchError(Context con, String Exception)
{
	Dialog diag=new Dialog(con);
	diag.setTitle("Error");
	TextView txt=new TextView(con);
	txt.setText(Exception);
	diag.setContentView(txt);
	diag.show();
}


}


