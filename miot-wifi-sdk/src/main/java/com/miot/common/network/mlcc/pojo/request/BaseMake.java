package com.miot.common.network.mlcc.pojo.request;

abstract class BaseMake {
	private String CodeName = getCodeName();

	public String getCodeName() {
		return setCodeName();
	}

	abstract String setCodeName();

	@Override
	public String toString() {
		return "CodeName=" + CodeName + "&";
	}

}
