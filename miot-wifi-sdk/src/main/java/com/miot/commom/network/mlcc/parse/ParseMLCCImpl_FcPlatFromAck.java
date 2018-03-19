package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespFcPlatFromAck;

public class ParseMLCCImpl_FcPlatFromAck implements ParseMLCCInterface<RespFcPlatFromAck> {

	
	public static ParseMLCCImpl_FcPlatFromAck parseMLCCImpl_FcPlatFromAck;
	
	public static ParseMLCCImpl_FcPlatFromAck getInstance() {
		if (parseMLCCImpl_FcPlatFromAck == null) {
			synchronized (ParseMLCCImpl_FcPlatFromAck.class) {
				if (parseMLCCImpl_FcPlatFromAck == null) {
					parseMLCCImpl_FcPlatFromAck = new ParseMLCCImpl_FcPlatFromAck();
				}
			}
		}
		return parseMLCCImpl_FcPlatFromAck;
	}
	
	@Override
	public RespFcPlatFromAck parse(Map<String, String> contentMap)
			throws Exception {
		RespFcPlatFromAck respSetlinkInfoAck = (RespFcPlatFromAck) MLCCReflectUtils
				.setBeanUtils(contentMap, RespFcPlatFromAck.class);
		respSetlinkInfoAck.make(contentMap);
		return respSetlinkInfoAck;
	}

	@Override
	public String getMLCCCode() {
		// TODO Auto-generated method stub
		return MLCCCodeConfig.MLCCCodeReturn.FC_ML_PLATFORM_ACK;
	}

}
