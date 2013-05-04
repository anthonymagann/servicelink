package com.pack.mine.trade;

public class Tradeperson {

	private String name;
	private String price;
	private String time;
	
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public String getPrice() {
		return price;
	}	
	public void setprice(String price) {
		this.price = price;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Restros ["
		+ (name != null ? "name=" + name + ", " : "")
		+ (price != null ? "price=" + price	+ ", " : "")
		+ (time != null ? "time=" + time + ", " : "")
		+"]";
	}
	
}
