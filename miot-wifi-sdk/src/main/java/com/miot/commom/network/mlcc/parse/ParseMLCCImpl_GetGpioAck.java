package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespGetGpioAck;

class ParseMLCCImpl_GetGpioAck implements
		ParseMLCCInterface<RespGetGpioAck> {
	private ParseMLCCImpl_GetGpioAck(){}
	private static ParseMLCCImpl_GetGpioAck parseMLCCImpl_GetGpioAck = null;
	public static ParseMLCCImpl_GetGpioAck getInstance(){
		if(parseMLCCImpl_GetGpioAck == null){
			synchronized (ParseMLCCImpl_GetGpioAck.class) {
				if(parseMLCCImpl_GetGpioAck == null){
					parseMLCCImpl_GetGpioAck = new ParseMLCCImpl_GetGpioAck();
				}
			}
		}
		return parseMLCCImpl_GetGpioAck;
	}
	
	@Override
	public RespGetGpioAck parse(Map<String, String> contentMap)
			throws Exception {
		RespGetGpioAck respGetGpioAck = (RespGetGpioAck) MLCCReflectUtils.setBeanUtils(contentMap,
				RespGetGpioAck.class);
		respGetGpioAck.make(contentMap);
		return respGetGpioAck;
	}

	@Override
	public String getMLCCCode() {
		return MLCCCodeConfig.MLCCCodeReturn.GET_GPIO_ACK;
	}

}
