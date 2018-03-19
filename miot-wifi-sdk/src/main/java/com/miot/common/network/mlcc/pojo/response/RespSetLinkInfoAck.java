package com.miot.common.network.mlcc.pojo.response;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;

public class RespSetLinkInfoAck extends RespBaseAck {

	public RespSetLinkInfoAck() {
		super.chazzType = this.getClass();
		super.clazzTypeDesc = MLCCCodeConfig.MLCCCodeReturn.SET_LINK_INFO;
	}

	public static final String RESULT_SUCCESS = "1";
	public static final String RESULT_FAIL = "0";

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
		return "RespSetLinkInfoAck [codeName=" + codeName + ", result=" + result
				+ "]";
	}
	@Override
	public void make(Map<String, String> resultMap) {
		super.setResultMap(resultMap);

	}

}
