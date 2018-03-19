package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespSetLinkInfoAck;

public class ParseMLCCImpl_SetLinkInfo implements ParseMLCCInterface<RespSetLinkInfoAck>{

	private static  ParseMLCCImpl_SetLinkInfo parseMLCCImpl_SetLinkInfo;
	@Override
	public RespSetLinkInfoAck parse(Map<String, String> contentMap)
			throws Exception {
		RespSetLinkInfoAck respSetlinkInfoAck = (RespSetLinkInfoAck) MLCCReflectUtils
				.setBeanUtils(contentMap, RespSetLinkInfoAck.class);
		respSetlinkInfoAck.make(contentMap);
		return respSetlinkInfoAck;
	}

	public static ParseMLCCImpl_SetLinkInfo getInstance() {
		if (parseMLCCImpl_SetLinkInfo == null) {
			synchronized (ParseMLCCImpl_SetLinkInfo.class) {
				if (parseMLCCImpl_SetLinkInfo == null) {
					parseMLCCImpl_SetLinkInfo = new ParseMLCCImpl_SetLinkInfo();
				}
			}
		}
		return parseMLCCImpl_SetLinkInfo;
	}
	
	@Override
	public String getMLCCCode() {
		return MLCCCodeConfig.MLCCCodeReturn.SET_LINK_INFO;
	}

}
