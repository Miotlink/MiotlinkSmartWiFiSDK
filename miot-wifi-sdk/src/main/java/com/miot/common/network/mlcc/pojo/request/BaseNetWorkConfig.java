package com.miot.common.network.mlcc.pojo.request;

public class BaseNetWorkConfig {
	public static final int MODE_AP = 1;
	public static final int MODE_STA = 2;

	public BaseNetWorkConfig(Integer mode, Integer port, String ip)
			throws Exception {
//		if (mode != 1 && mode != 2 && mode != 3) {
//			throw new Exception("mode set error ["+mode+"]. Support by [1-TCP服务端（默认）；2-TCP客户端；3-UDP客户端]");
//		}
		if(port >= 1 && port <= 65535){
			//do nothing
		}else{
			throw new Exception("port set error ["+port+"]. Support by (1~65535)");
		}
		
		this.mode = mode;
		this.port = port;
		this.ip = ip;
	}

	private Integer mode;
	private Integer port;
	private String ip;

	public Integer getMode() {
		return mode;
	}

	public Integer getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	@Override
	public String toString() {
		return "BaseNetWorkConfig [mode=" + mode + ", port=" + port + ", ip="
				+ ip + "]";
	}

}
