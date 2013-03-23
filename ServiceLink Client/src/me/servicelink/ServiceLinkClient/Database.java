package me.servicelink.ServiceLinkClient;



import me.servicelink.ServiceLinkClient.Request;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;




public class Database extends SQLiteOpenHelper {

	
	static final String dbdescription="ServiceLnkClnt";
	static final String serviceTable="Services";
	static final String colID="ServiceID";
	static final String coldescription="Description";
	static final String colWait="Wait";
	
	static final String colPic= "Pic";
	
	static final String coltrade="Trades";
	
	static final String tradeTable="Trade";
	static final String coltradedesID="TradeID";
	static final String coltradedes="Tradedescription";
	
	static final String viewServe="ViewServe";
	
	
	public Database(Context context) {
		super(context, dbdescription, null,33);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE "+tradeTable+" ("+coltradedesID+ " INTEGER PRIMARY KEY , "+
				coltradedes+ " TEXT)");
		
		db.execSQL("CREATE TABLE "+serviceTable+" ("+colID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				coldescription+" TEXT, "+colWait+" Integer, "+colPic+" BLOB) "+coltrade+" INTEGER NOT NULL ,FOREIGN KEY ("+coltrade+") REFERENCES "+tradeTable+" ("+coltradedesID+"));");
		
		
		db.execSQL("CREATE TRIGGER fk_sertrade_tradeid " +
				" BEFORE INSERT "+
				" ON "+serviceTable+
				
				" FOR EACH ROW BEGIN"+
				" SELECT CASE WHEN ((SELECT "+coltradedesID+" FROM "+tradeTable+" WHERE "+coltradedesID+"=new."+coltrade+" ) IS NULL)"+
				" THEN RAISE (ABORT,'Foreign Key Violation') END;"+
				"  END;");
		
		db.execSQL("CREATE VIEW "+viewServe+
				" AS SELECT "+serviceTable+"."+colID+" AS _id,"+
				" "+serviceTable+"."+coldescription+","+
				" "+serviceTable+"."+colWait+","+
				" "+serviceTable+"."+colPic+","+
				" "+tradeTable+"."+coltradedes+""+
				" FROM "+serviceTable+" JOIN "+tradeTable+
				" ON "+serviceTable+"."+coltrade+" ="+tradeTable+"."+coltradedesID
				);
		
		InsertTrades(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+serviceTable);
		db.execSQL("DROP TABLE IF EXISTS "+tradeTable);
		
		db.execSQL("DROP TRIGGER IF EXISTS trade_id_trigger");
		db.execSQL("DROP TRIGGER IF EXISTS trade_id_trigger22");
		db.execSQL("DROP TRIGGER IF EXISTS fk_sertrade_tradeid");
		db.execSQL("DROP VIEW IF EXISTS "+viewServe);
		onCreate(db);
	}
	
	 void AddRequest(Request ser)
	{
		 
		 
		 SQLiteDatabase db= this.getWritableDatabase();
		 
		
		ContentValues cv=new ContentValues();
		
		cv.put(coldescription, ser.getdescription());
		cv.put(colWait, ser.getWait());
		cv.put(coltrade, ser.getTrade());
	
	
		
		//cv.put(coltrade,2);
		
		db.insert(serviceTable, coldescription,  cv);
		db.close();
		
		
	}
	 

	 
	 int getRequestCount() {
	 
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+serviceTable, null);
		int x= cur.getCount();
		cur.close();
		return x;
	 }
	 
	 Cursor getAllRequest()
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 
		 
		 
		 
		 Cursor cur= db.rawQuery("SELECT * FROM "+viewServe,null);
		 return cur;
		 
	 }
	 
	 Cursor getAllTrades()
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor cur=db.rawQuery("SELECT "+coltradedesID+" as _id, "+coltradedes+" from "+tradeTable,new String [] {});
		 
		 return cur;
	 }
	 
	 void InsertTrades(SQLiteDatabase db)
	 {
		 ContentValues cv=new ContentValues();
			cv.put(coltradedesID, 1);
			cv.put(coltradedes, "Electrical");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 2);
			cv.put(coltradedes, "Plumbing");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 3);
			cv.put(coltradedes, "Carpentry & Joinery");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 4);
			cv.put(coltradedes, "Motor Mechanics");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 5);
			cv.put(coltradedes, "Alarm");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 6);
			cv.put(coltradedes, "Gas Fitters");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 7);
			cv.put(coltradedes, "Window Repairs");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 8);
			cv.put(coltradedes, "Locksmiths ");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 9);
			cv.put(coltradedes, "Handyman");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 10);
			cv.put(coltradedes, "Plasterers");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 11);
			cv.put(coltradedes, "Appliance Repairs");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 12);
			cv.put(coltradedes, "Roofers");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 13);
			cv.put(coltradedes, "Ground Works");
			db.insert(tradeTable, coltradedesID, cv);
			
			cv.put(coltradedesID, 14);
			cv.put(coltradedes, "Computer Systems");
			db.insert(tradeTable, coltradedesID, cv);
			db.insert(tradeTable, coltradedesID, cv);
			
			
	 }
	 
	 public String GetTrade(int ID)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 
		 String[] params=new String[]{String.valueOf(ID)};
		 Cursor c=db.rawQuery("SELECT "+coltradedes+" FROM"+ tradeTable+" WHERE "+coltradedesID+"=?",params);
		 c.moveToFirst();
		 int index= c.getColumnIndex(coltradedes);
		 return c.getString(index);
	 }
	 
	 public Cursor getServiceByTrade(String Trade)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 String [] columns=new String[]{"_id",coldescription,colWait,coltradedes};
		 Cursor c=db.query(viewServe, columns, coltradedes+"=?", new String[]{Trade}, null, null, null);
		 return c;
	 }
	 
	 public int GetTradeID(String Trade)
	 {
		 SQLiteDatabase db=this.getReadableDatabase();
		 Cursor c=db.query(tradeTable, new String[]{coltradedesID+" as _id",coltradedes},coltradedes+"=?", new String[]{Trade}, null, null, null);
		 
		 c.moveToFirst();
		 return c.getInt(c.getColumnIndex("_id"));
		 
		 }
	 
	 public int UpdateSer(Request ser)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 ContentValues cv=new ContentValues();
		 cv.put(coldescription, ser.getdescription());
		 cv.put(colWait, ser.getWait());
		 cv.put(coltrade, ser.getTrade());
		 return db.update(serviceTable, cv, colID+"=?", new String []{String.valueOf(ser.getID())});
		 
	 }
	 
	 public void DeleteSer(Request ser)
	 {
		 SQLiteDatabase db=this.getWritableDatabase();
		 db.delete(serviceTable,colID+"=?", new String [] {String.valueOf(ser.getID())});
		 db.close();
		 
		
		
	 }

	

}
