package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.commom.network.mlcc.utils.MLCCStringUtils;

public class MakeSetGpio extends BaseMake implements MakePackageInterface {
	
	public MakeSetGpio(Map<String, String> setGpioMap) {
		MLCCReflectUtils.setFieldValue(setGpioMap, this);
	}
	
	public MakeSetGpio(Integer[] channelArray, Integer[] cTypeArray,
			Integer[] cStateArray,String mac,int port) throws Exception {
		if (channelArray == null
				|| cTypeArray == null
				|| cStateArray == null
				|| (channelArray.length != cTypeArray.length || channelArray.length != cStateArray.length)) {
			throw new Exception(
					"param error! Cause by one is null or length error!["
							+ channelArray + "][" + cTypeArray + "]["
							+ cStateArray + "]");
		}
		this.Mac = mac;
		this.port = port+"";
		this.cChn = MLCCStringUtils.getChannelArray2String(channelArray);
		this.cType = MLCCStringUtils.getTypeArray2String(cTypeArray);
		this.cState = MLCCStringUtils.getStateArray2String(cStateArray);
	}

	private String cChn = null;
	private String cType = null;
	private String cState = null;
	private String port = "0";
	private String Mac;
	
	public String getCChn() {
		return cChn;
	}

	public String getCType() {
		return cType;
	}

	public String getCState() {
		return cState;
	}

	public String getcChn() {
		return cChn;
	}

	public String getcType() {
		return cType;
	}

	public String getcState() {
		return cState;
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
		return MLCCCodeConfig.MLCCCodeMake.SET_GPIO;
	}

}
