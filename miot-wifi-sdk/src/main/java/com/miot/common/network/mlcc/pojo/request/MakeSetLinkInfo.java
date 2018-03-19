package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;

public class MakeSetLinkInfo extends BaseMake implements MakePackageInterface {
	
	private String Mac="";
	private String port = "0";
	private String Func="";
	
	private String MLinkIp="";
	
	private String MlPort="";
	
	public String getMLinkIp() {
		return MLinkIp;
	}
	public String getMlPort() {
		return MlPort;
	}

	public String getMac() {
		return Mac;
	}

	public String getPort() {
		return port;
	}

	public String getFunc() {
		return Func;
	}

	public MakeSetLinkInfo(Map<String, String> setWifiMap) {
		MLCCReflectUtils.setFieldValue(setWifiMap, this);

	}
	
	public MakeSetLinkInfo(String Mac,String MLinkIp,String MlPort,  String Func, String port) {
		this.Func=Func;
		this.Mac=Mac;
		this.port=port;
		this.MLinkIp=MLinkIp;
		this.MlPort=MlPort;
	}

	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCReflectUtils.makeParam(
				MakeSetLinkInfo.class.getDeclaredFields(), this)).getBytes();
	}

	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.SET_LINK;
	}
	
	@Override
	public String toString() {
		return "MakeSetLinkInfo [Mac=" + Mac + ", port=" + port + ", Func="
				+ Func + "]";
	}


}
