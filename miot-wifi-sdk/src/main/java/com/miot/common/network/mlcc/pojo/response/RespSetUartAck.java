package com.miot.common.network.mlcc.pojo.response;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;

public class RespSetUartAck extends RespBaseAck{
	public RespSetUartAck(){
		super.chazzType = this.getClass();
		super.clazzTypeDesc = MLCCCodeConfig.MLCCCodeReturn.SET_UART_ACK;
	}
	
	public static final int RESULT_SUCCESS=1;
	public static final int RESULT_FAIL=0;
	
	private String codeName;
	private String result;
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
	@Override
	public String toString() {
		return "RespSetUartAck [codeName=" + codeName + ", result=" + result
				+ "]";
	}
	@Override
	public synchronized void make(Map<String, String> resultMap) {
		super.setResultMap(resultMap);
	}
	
}
