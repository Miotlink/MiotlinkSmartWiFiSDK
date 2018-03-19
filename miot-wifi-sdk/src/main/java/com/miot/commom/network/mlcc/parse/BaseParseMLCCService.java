package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCStringUtils;
import com.miot.common.network.mlcc.pojo.response.RespBaseAck;

/**
 * 
 * @function 解析MLCC content应用上下文基础类
 * @date 2014-06-16
 * @author szf
 * 
 */
abstract class BaseParseMLCCService {
	
	abstract RespBaseAck parse(byte[] ttpPackage, int startPos, int length)
			throws Exception;

	/**
	 * 
	 * @param mlccContentPackage
	 * @param startPos
	 * @param length
	 * @return
	 * @throws Exception
	 */
	Map<String, String> parseMLCC(byte[] mlccContentPackage, int startPos,
			int length) throws Exception {
		return MLCCStringUtils.parseMLCC2Map(mlccContentPackage, startPos,
				length);
	}

}
