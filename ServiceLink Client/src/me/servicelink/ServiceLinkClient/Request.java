package me.servicelink.ServiceLinkClient;

import android.content.Context;
import android.graphics.Bitmap;

public class Request {
	
	int _id;
	String _description;
	int _wait;
	int _trade;
	//Bitmap _bmp;
	
	
	public Request(String description,int Wait,int Trade)
	
	{
		
		this._description=description;
		this._wait=Wait;
		this._trade=Trade;
	}
	
	public Request(Bitmap bmp,int Wait,int Trade)
	{
		
	
		this._wait=Wait;
		this._trade=Trade;
	}
	

	
	public Request(String description,int Wait)
	{
		this._description=description;
		this._wait=Wait;
	}
	
	
	
	
	public int getID()
	{
		return this._id;
	}
	public void SetID(int ID)
	{
		this._id=ID;
	}
	
	public String getdescription()
	{
		return this._description;
	}
	
	public int getWait()
	{
		return this._wait;
	}
	
	public void setdescription(String description)
	{
		this._description=description;
	}
	
	
	public void setWait(int Wait)
	{
		this._wait=Wait;
	}
	
	
	public void setTrade(int Trade)
	{
		this._trade=Trade;
	}
	
	
	public String getTradedescription(Context con, int Trade)
	{
		return new Database(con).GetTrade(Trade);
	}
	
	
	public int getTrade()
	{
		return this._trade;
	}

	
	
	
}
