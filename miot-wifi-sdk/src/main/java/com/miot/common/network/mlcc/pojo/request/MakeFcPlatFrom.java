package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCFcReflectUtils;

public class MakeFcPlatFrom extends BaseMake implements MakePackageInterface{
	
	private String mac=null;
	
	private String pf_url=null;
	
	private String pf_port=null;
	
	private String pf_ip1=null;
	
	private String pf_ip2=null;
	
	public MakeFcPlatFrom(String mac,String pf_url,String pf_port,String pf_ip1,String pf_ip2){
		this.mac=mac;
		this.pf_url=pf_ip1;
		this.pf_port=pf_port;
		this.pf_ip1=pf_ip1;
		this.pf_ip2=pf_ip2;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getPf_url() {
		return pf_url;
	}
	public void setPf_url(String pf_url) {
		this.pf_url = pf_url;
	}
	public String getPf_port() {
		return pf_port;
	}
	public void setPf_port(String pf_port) {
		this.pf_port = pf_port;
	}
	public String getPf_ip1() {
		return pf_ip1;
	}
	public void setPf_ip1(String pf_ip1) {
		this.pf_ip1 = pf_ip1;
	}
	public String getPf_ip2() {
		return pf_ip2;
	}
	public void setPf_ip2(String pf_ip2) {
		this.pf_ip2 = pf_ip2;
	}
	public MakeFcPlatFrom(Map<String, String> fcPlatFrom) {
		MLCCFcReflectUtils.setFieldValue(fcPlatFrom, this);
	}
	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCFcReflectUtils.makeParam(
				MakeFcPlatFrom.class.getDeclaredFields(), this)).getBytes();
	}
	@Override
	String setCodeName() {
		return MLCCCodeConfig.MLCCCodeMake.FC_ML_PLATFORM;
	}
	@Override
	public String toString() {
		return "MakeFcPlatFrom [mac=" + mac + ", pf_url=" + pf_url
				+ ", pf_port=" + pf_port + ", pf_ip1=" + pf_ip1 + ", pf_ip2="
				+ pf_ip2 + "]";
	}

	
}
