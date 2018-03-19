package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCFcReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespFc_completeAck;

public class ParseMLCCImpl_Fc_completeAck implements ParseMLCCInterface<RespFc_completeAck> {

	public static ParseMLCCImpl_Fc_completeAck parseMLCCImpl_Fc_completeAck;
	
	public static ParseMLCCImpl_Fc_completeAck getInstance(){
		if (parseMLCCImpl_Fc_completeAck == null) {
			synchronized (ParseMLCCImpl_Fc_completeAck.class) {
				if (parseMLCCImpl_Fc_completeAck == null) {
					parseMLCCImpl_Fc_completeAck = new ParseMLCCImpl_Fc_completeAck();
				}
			}
		}
		return parseMLCCImpl_Fc_completeAck;
	}
	
	@Override
	public RespFc_completeAck parse(Map<String, String> contentMap) throws Exception {
		RespFc_completeAck respSmartConnectedAck = (RespFc_completeAck) MLCCFcReflectUtils
				.setBeanUtils(contentMap, RespFc_completeAck.class);
		respSmartConnectedAck.make(contentMap);
		return respSmartConnectedAck;
	}

	@Override
	public String getMLCCCode() {
		// TODO Auto-generated method stub
		return MLCCCodeConfig.MLCCCodeReturn.FC_COMPLETE_ACK;
	}

}
