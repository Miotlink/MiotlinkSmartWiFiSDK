package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.commom.network.mlcc.utils.MLCCStringUtils;

public class MakeSetUart extends BaseMake implements
		MakePackageInterface {
	public MakeSetUart(Map<String, String> setUartMap) {
		MLCCReflectUtils.setFieldValue(setUartMap, this);
	}
	public MakeSetUart(String mac, BaseUartConfig uartConfig,int port) throws Exception {
		this.Mac = mac;
		this.port = port+"";
		this.UartInfo = MLCCStringUtils.getBaseUart2String(uartConfig);;
	}

	private static final String ip = "0.0.0.0";
	private String port = "0";
	private String Mac = null;
	private String UartInfo = null;

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public String getMac() {
		return Mac;
	}

	public String getUartInfo() {
		return UartInfo;
	}

	@Override
	public String toString() {
		return "MakeSetUartPackage [ip=" + ip + ", port=" + port + ", Mac="
				+ Mac + ", UartInfo=" + UartInfo + "]";
	}

	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCReflectUtils.makeParam(this.getClass()
				.getDeclaredFields(), this)).getBytes();
	}

	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.SET_UART;
	}

}
