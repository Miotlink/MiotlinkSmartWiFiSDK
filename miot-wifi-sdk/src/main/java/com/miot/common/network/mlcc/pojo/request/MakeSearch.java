package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;

public class MakeSearch extends BaseMake implements MakePackageInterface {
	public MakeSearch() {
	}

	public MakeSearch(int receivePort) {
		this.port = receivePort+"";
	}

	public MakeSearch(Map<String, String> searchMap) {
		MLCCReflectUtils.setFieldValue(searchMap, this);
	}

	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.SEARCH;
	}

	public String getPort() {
		return port;
	}

	private static final String ip = "0.0.0.0";
	private String port = "0";

	@Override
	public String toString() {
		return super.toString() + "ip=" + ip + "&port=" + port + '\0';
	}

	@Override
	public byte[] makePackage() {
		return toString().getBytes();
	}

}
