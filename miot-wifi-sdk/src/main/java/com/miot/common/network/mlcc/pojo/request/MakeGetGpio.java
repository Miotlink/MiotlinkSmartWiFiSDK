package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.commom.network.mlcc.utils.MLCCStringUtils;

public class MakeGetGpio extends BaseMake implements MakePackageInterface {
	public MakeGetGpio(Integer[] channelArray, String mac, int port)
			throws Exception {
		this.cChn = MLCCStringUtils.getChannelArray2String(channelArray);
		this.port = port+"";
		this.Mac = mac;
	}

	public MakeGetGpio(Map<String, String> getGpioMap) {
		MLCCReflectUtils.setFieldValue(getGpioMap, this);
	}

	private String cChn = null;
	private String port = "0";
	private String Mac;

	public String getCChn() {
		return cChn;
	}

	public String getcChn() {
		return cChn;
	}

	public String getPort() {
		return port;
	}

	public String getMac() {
		return Mac;
	}

	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCReflectUtils.makeParam(this.getClass()
				.getDeclaredFields(), this)).getBytes();
	}

	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.GET_GPIO;
	}

}
