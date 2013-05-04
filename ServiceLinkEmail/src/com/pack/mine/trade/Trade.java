package com.pack.mine.trade;

public class Trade {

	private String tradename;
	private String desc;
	private String wait;
	private String path;
	
	
	public void setTradeName(String tradename) {
		this.tradename = tradename;
	}
	public String getTradeName() {
		return tradename;
	}
	
	public String getdesc() {
		return desc;
	}	
	public void setdesc(String desc) {
		this.desc = desc;
	}
	
	public String getwait() {
		return wait;
	}
	public void setwait(String wait) {
		this.wait = wait;
	}
	public String getpath() {
		return path;
	}
	public void setpath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "Restros ["
		+ (tradename != null ? "name=" + tradename + ", " : "")
		+ (desc != null ? "desc=" + desc	+ ", " : "")
		+ (wait != null ? "wait=" + wait + ", " : "")
		+ (path != null ? "path=" + path + ", " : "")
		+"]";
	}
	
}
