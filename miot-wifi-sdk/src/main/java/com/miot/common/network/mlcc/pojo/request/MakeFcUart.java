package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCFcReflectUtils;

public class MakeFcUart extends BaseMake implements MakePackageInterface{

	
	private String mac=null;
	
	private String uart_info=null;
	
	public MakeFcUart(String mac,String uart_info){
		this.mac=mac;
		this.uart_info=uart_info;
	}
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getUart_info() {
		return uart_info;
	}

	public void setUart_info(String uart_info) {
		this.uart_info = uart_info;
	}

	
	public MakeFcUart(Map<String, String> fcuratInfo) {
		MLCCFcReflectUtils.setFieldValue(fcuratInfo, this);
	}
	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCFcReflectUtils.makeParam(
				MakeFcUart.class.getDeclaredFields(), this)).getBytes();
	}
	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.FC_UART_INFO;
	}

}
