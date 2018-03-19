package com.miot.common.network.mlcc.pojo.request;

public class BaseMode {
	public BaseMode(int mode ,String ip){
		this.ip = ip;
		this.mode = mode;
	}
		
	private int mode;
	private String ip;
	public int getMode() {
		return mode;
	}
	public String getIp() {
		return ip;
	}
	@Override
	public String toString() {
		return "BaseMode [mode=" + mode + ", ip=" + ip + "]";
	}
	
}
