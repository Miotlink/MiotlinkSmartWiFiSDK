package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.commom.network.mlcc.utils.MLCCStringUtils;

public class MakeSetWifi extends BaseMake implements
		MakePackageInterface {
	public MakeSetWifi(Map<String, String> setWifiMap) {
		MLCCReflectUtils.setFieldValue(setWifiMap, this);
	}
	
	public MakeSetWifi(int port,String Mac, String DevName, String ByName,String MlPort,
			String MLinkIp,String MlDip,String MlDipI,
			Integer mode, String ApId, String ApPd, String StaId, String StaPd,
			BaseNetWorkConfig transparentTransmitInfo,
			BaseNetWorkConfig controlInfo, BaseUartConfig uartConfig) throws Exception {
		this.Mac = Mac;
		this.DevName = DevName;
		this.ByName = ByName;
		this.port = port+"";
		this.MLinkIp=MLinkIp;
		this.MlPort=MlPort;
		this.MlDip=MlDip;
		this.MlDipI=MlDipI;
		if(mode != 1 && mode !=2){
			throw new Exception("mode is error [" + mode+"]. Support by [1-AP模式；2-STA模式]");
		}
		this.Mode = mode+"";
		
		this.ApId = ApId;
		this.ApPd = ApPd;
		this.StaId = StaId;
		this.StaPd = StaPd;

		this.tInfo = MLCCStringUtils
				.getBaseNetWork2String(transparentTransmitInfo);
		this.cInfo = MLCCStringUtils.getBaseNetWork2String(controlInfo);
		this.UartInfo = MLCCStringUtils.getBaseUart2String(uartConfig);
	}

	public static final String ip = "0.0.0.0";
	private String port = "0";

	private String Mac = null;
	private String DevName = null;
	private String ByName = null;
	private String MlPort=null;
	private String MLinkIp=null;
	private String Mode = null;
	private String ApId = null;
	private String ApPd = null;
	private String StaId = null;
	private String StaPd = null;
	private String tInfo = null;
	private String cInfo = null;
	private String UartInfo = null;
	
	private String MlDip=null;
	
	private String MlDipI=null;
	
	public String getMlDipI() {
		return MlDipI;
	}
	public String getMlDip() {
		return MlDip;
	}

	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCReflectUtils.makeParam(
				MakeSetWifi.class.getDeclaredFields(), this)).getBytes();
	}

	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.SET_WIFI;
	}

	public static String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public String getMac() {
		return Mac;
	}

	public String getDevName() {
		return DevName;
	}

	public String getByName() {
		return ByName;
	}

	public String getMode() {
		return Mode;
	}

	public String getApId() {
		return ApId;
	}

	public String getApPd() {
		return ApPd;
	}

	public String getStaId() {
		return StaId;
	}

	public String getStaPd() {
		return StaPd;
	}

	public String getTInfo() {
		return tInfo;
	}

	public String getCInfo() {
		return cInfo;
	}

	public String getUartInfo() {
		return UartInfo;
	}

	public String gettInfo() {
		return tInfo;
	}

	public String getcInfo() {
		return cInfo;
	}

	public String getMLinkIp() {
		return MLinkIp;
	}
	public String getMlPort() {
		return MlPort;
	}

	@Override
	public String toString() {
		return "MakeSetWifi [port=" + port + ", Mac=" + Mac + ", DevName="
				+ DevName + ", ByName=" + ByName + ", MlPort=" + MlPort
				+ ", MLinkIp=" + MLinkIp + ", Mode=" + Mode + ", ApId=" + ApId
				+ ", ApPd=" + ApPd + ", StaId=" + StaId + ", StaPd=" + StaPd
				+ ", tInfo=" + tInfo + ", cInfo=" + cInfo + ", UartInfo="
				+ UartInfo + "]";
	}
	

}
