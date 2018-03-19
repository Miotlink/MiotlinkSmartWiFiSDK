package com.miot.common.network.mlcc.pojo.request;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCFcReflectUtils;

public class MakeFc_completeFinAck extends BaseMake implements MakePackageInterface{

    private String mac=null;
	
	public MakeFc_completeFinAck(String mac){
		this.mac=mac;
	}
	
	@Override
	String setCodeName() {
		// TODO Auto-generated method stub
		return MLCCCodeConfig.MLCCCodeMake.FC_COMPLETE_FIN;
	}
	public String getMac() {
		return mac;
	}
	@Override
	public byte[] makePackage() {
		
		return (super.toString() + MLCCFcReflectUtils.makeParam(
				MakeFc_completeFinAck.class.getDeclaredFields(), this)).getBytes();
	}
	@Override
	public String toString() {
		return "MakeFc_completeFinAck [mac=" + mac + "]";
	}
	public MakeFc_completeFinAck(Map<String, String> setWifiMap) {
		MLCCFcReflectUtils.setFieldValue(setWifiMap, this);

	}

}
