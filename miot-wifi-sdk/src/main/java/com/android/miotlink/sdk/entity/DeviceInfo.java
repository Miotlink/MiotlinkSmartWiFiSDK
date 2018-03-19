package com.android.miotlink.sdk.entity;

public class DeviceInfo {

	private String ip="";
	
	private String mac=null;
	
	private String uart="";

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getUart() {
		return uart;
	}

	public void setUart(String uart) {
		this.uart = uart;
	}

	@Override
	public String toString() {
		return "DeviceInfo [ip=" + ip + ", mac=" + mac + ", uart=" + uart + "]";
	}

}
