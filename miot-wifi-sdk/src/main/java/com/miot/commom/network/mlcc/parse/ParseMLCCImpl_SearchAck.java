package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCReflectUtils;
import com.miot.common.network.mlcc.pojo.response.RespSearchAck;

/**
 * 
 * @author Administrator
 * 
 */
class ParseMLCCImpl_SearchAck implements ParseMLCCInterface<RespSearchAck> {
	
	private static volatile ParseMLCCImpl_SearchAck parseMLCCImpl_SearchAck = null;

	/**
	 * 使用单例模式 实例化解析类
	 * 
	 * @return
	 */
	static ParseMLCCImpl_SearchAck getInstance() {
		if (parseMLCCImpl_SearchAck == null) {
			synchronized (ParseMLCCImpl_SearchAck.class) {
				if (parseMLCCImpl_SearchAck == null) {
					parseMLCCImpl_SearchAck = new ParseMLCCImpl_SearchAck();
				}
			}
		}
		return parseMLCCImpl_SearchAck;
	}

	private ParseMLCCImpl_SearchAck() {
	}

	@Override
	public RespSearchAck parse(Map<String, String> contentMap) throws Exception {
		RespSearchAck respSearchAck = (RespSearchAck) MLCCReflectUtils.setBeanUtils(contentMap, RespSearchAck.class);
		respSearchAck.make(contentMap);
		return respSearchAck;
	}

	@Override
	public String getMLCCCode() {
		return MLCCCodeConfig.MLCCCodeReturn.SEARCH_ACK;
	}

	
}
