package com.miot.common.network.mlcc.pojo.response;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;

public class RespFcUartInfoAck extends RespBaseAck {

	
	public RespFcUartInfoAck() {
		super.chazzType = this.getClass();
		super.clazzTypeDesc = MLCCCodeConfig.MLCCCodeReturn.FC_UART_INFO_ACK;
	}
	
	
	public static final String RESULT_SUCCESS = "1";
	public static final String RESULT_FAIL = "0";
	private String codeName;
	private String result;
	private String mac;
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@Override
	public String toString() {
		return "RespFcUartInfoAck [codeName=" + codeName + ", result=" + result
				+ ", mac=" + mac + "]";
	}
	@Override
	public void make(Map<String, String> resultMap) {
		super.setResultMap(resultMap);

	}

}
