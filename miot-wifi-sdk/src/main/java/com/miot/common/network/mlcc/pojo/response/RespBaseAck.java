package com.miot.common.network.mlcc.pojo.response;

import java.util.HashMap;
import java.util.Map;

public abstract class RespBaseAck {

	public String clazzTypeDesc = null;
	public Class<?> chazzType = null;

	public Map<String, String> unknownKeyAndValueMap = new HashMap<String, String>();

	private String unKonwnKey;
	private String unKonwnValue;
	
	private Map<String, String> resultMap = new HashMap<String, String>();

	public void setUnKonwnKey(String unKonwnKey) {
		this.unKonwnKey = unKonwnKey;
	}

	public void setUnKonwnValue(String unKonwnValue) {
		this.unKonwnValue = unKonwnValue;
		this.unknownKeyAndValueMap.put(this.unKonwnKey, this.unKonwnValue);
	}

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public abstract void make(Map<String, String> resultMap);

	@Override
	public String toString() {
		return "RespBaseAck [clazzTypeDesc=" + clazzTypeDesc + ", chazzType="
				+ chazzType + ", unknownKeyAndValueMap="
				+ unknownKeyAndValueMap + ", unKonwnKey=" + unKonwnKey
				+ ", unKonwnValue=" + unKonwnValue + "]";
	}

}
