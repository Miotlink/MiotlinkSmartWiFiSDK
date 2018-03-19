package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespSetWifiAck;

class ParseMLCCImpl_SetWifiAck implements ParseMLCCInterface<RespSetWifiAck> {
	
	private ParseMLCCImpl_SetWifiAck() {
	}

	private volatile static ParseMLCCImpl_SetWifiAck parseMLCCImpl_SetWifiAck = null;

	public static ParseMLCCImpl_SetWifiAck getInstance() {
		if (parseMLCCImpl_SetWifiAck == null) {
			synchronized (ParseMLCCImpl_SetWifiAck.class) {
				if (parseMLCCImpl_SetWifiAck == null) {
					parseMLCCImpl_SetWifiAck = new ParseMLCCImpl_SetWifiAck();
				}
			}
		}
		return parseMLCCImpl_SetWifiAck;
	}

	@Override
	public RespSetWifiAck parse(Map<String, String> contentMap)
			throws Exception {
		RespSetWifiAck respSetWifiAck = (RespSetWifiAck) MLCCReflectUtils
				.setBeanUtils(contentMap, RespSetWifiAck.class);
		respSetWifiAck.make(contentMap);
		return respSetWifiAck;
	}

	@Override
	public String getMLCCCode() {
		return MLCCCodeConfig.MLCCCodeReturn.SET_WIFI_ACK;
	}

}
