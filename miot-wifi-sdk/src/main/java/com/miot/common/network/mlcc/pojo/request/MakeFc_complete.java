package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCFcReflectUtils;

public class MakeFc_complete extends BaseMake implements MakePackageInterface{

	private String mac=null;
	
	public MakeFc_complete(String mac){
		this.mac=mac;
	}
	
	@Override
	String setCodeName() {
		// TODO Auto-generated method stub
		return MLCCCodeConfig.MLCCCodeMake.FC_COMPLETE;
	}
	public String getMac() {
		return mac;
	}
	@Override
	public byte[] makePackage() {
		return (super.toString() + MLCCFcReflectUtils.makeParam(
				MakeFc_complete.class.getDeclaredFields(), this)).getBytes();
	}
	@Override
	public String toString() {
		return "MakeFc_complete [mac=" + mac + "]";
	}
	public MakeFc_complete(Map<String, String> setWifiMap) {
		MLCCFcReflectUtils.setFieldValue(setWifiMap, this);

	}
	

}
