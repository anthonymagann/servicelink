package com.pack.mine.trade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class SampleDatabase {
	public static final String MYDATABASE_NAME = "SAMPLE_DATABASE";
	public static final int MYDATABASE_VERSION = 1;

	public static final String TRADE_TABLE = "trade_table";
	public static final String LOGIN_TABLE = "login_table";
	
	private static final String LOGIN_ID = "_id";
	private static final String TRADE_ID = "_id";
	private static final String TRADE_NAME = "trade_name";
	private static final String WAIT_TIME = "wait_time";
	private static final String DESCRIPTION = "desc";
	private static final String PHOTO = "photo";
	
	private static final String USERNAME = "username";
	private static final String ACC_NAME = "account";

	private static final String TRADE_TABLE_CREATE =
			"create table " + TRADE_TABLE + " ("
					+ TRADE_ID + " integer primary key autoincrement, "
					+ TRADE_NAME + " text, " 
					+ WAIT_TIME + " integer, "
					+ DESCRIPTION + " text, "
					+ PHOTO + " text "
					+");";
	private static final String LOGIN_TABLE_CREATE =
			"create table " + LOGIN_TABLE + " ("
					+ LOGIN_ID + " integer primary key autoincrement, "
					+ USERNAME + " text, " 
					+ ACC_NAME + " text " 
					+");";
	
	
	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;

	public SampleDatabase(Context c){
		context = c;	
	}
	public SampleDatabase openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public SampleDatabase openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		sqLiteHelper.close();
	}
	public long insertTrade(String trade, String wait, String desc, String photo){

		ContentValues contentValues = new ContentValues();
		contentValues.put(TRADE_NAME, trade);
		contentValues.put(WAIT_TIME, wait);
		contentValues.put(DESCRIPTION, desc);
		contentValues.put(PHOTO, photo);
		return sqLiteDatabase.insert(TRADE_TABLE, null, contentValues);
	}
	
	public Cursor queueTrade(){
		String[] columns = new String[]{TRADE_NAME, WAIT_TIME, DESCRIPTION, PHOTO};
		Cursor cursor = sqLiteDatabase.query(TRADE_TABLE, columns,null, null, null, null, null);
		return cursor;
	}
	public long insertUser(String username, String acc){

		ContentValues contentValues = new ContentValues();
		contentValues.put(USERNAME, username);
		contentValues.put(ACC_NAME, acc);
		return sqLiteDatabase.insert(LOGIN_TABLE, null, contentValues);
	}
	
	public Cursor queueUser(){
		String[] columns = new String[]{USERNAME, ACC_NAME};
		Cursor cursor = sqLiteDatabase.query(LOGIN_TABLE, columns,null, null, null, null, null);
		return cursor;
	}
	public Cursor queueUserForAcc(String acc, String user){
		String[] columns = new String[]{USERNAME};
		String where = "username" + "=" + "'"+user+"'"+" "+"AND" +" "+ "account" + "=" + "'"+acc +"'";
		Cursor cursor = sqLiteDatabase.query(TRADE_TABLE, columns,where, null, null, null, null);
		return cursor;
	}
	
	
	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TRADE_TABLE_CREATE);	
			db.execSQL(LOGIN_TABLE_CREATE);	
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	}
}
