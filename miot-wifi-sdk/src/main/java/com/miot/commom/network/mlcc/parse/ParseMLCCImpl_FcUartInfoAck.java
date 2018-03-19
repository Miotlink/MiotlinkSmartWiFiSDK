package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCFcReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespFcUartInfoAck;

public class ParseMLCCImpl_FcUartInfoAck implements ParseMLCCInterface<RespFcUartInfoAck>{

	public static ParseMLCCImpl_FcUartInfoAck parseMLCCImpl_FcUartInfoAck;
	
	public static ParseMLCCImpl_FcUartInfoAck getInstance() {
		if (parseMLCCImpl_FcUartInfoAck == null) {
			synchronized (ParseMLCCImpl_FcUartInfoAck.class) {
				if (parseMLCCImpl_FcUartInfoAck == null) {
					parseMLCCImpl_FcUartInfoAck = new ParseMLCCImpl_FcUartInfoAck();
				}
			}
		}
		return parseMLCCImpl_FcUartInfoAck;
	}
	
	@Override
	public RespFcUartInfoAck parse(Map<String, String> contentMap)
			throws Exception {
		RespFcUartInfoAck respFcUartInfoAck = (RespFcUartInfoAck) MLCCFcReflectUtils
				.setBeanUtils(contentMap, RespFcUartInfoAck.class);
		respFcUartInfoAck.make(contentMap);
		return respFcUartInfoAck;
	}

	@Override
	public String getMLCCCode() {
		
		return MLCCCodeConfig.MLCCCodeReturn.FC_UART_INFO_ACK;
	}

}
