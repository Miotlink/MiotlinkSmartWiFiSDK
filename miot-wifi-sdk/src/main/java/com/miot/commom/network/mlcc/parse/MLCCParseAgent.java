package com.miot.commom.network.mlcc.parse;

import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCStringUtils;
import com.miot.common.network.mlcc.pojo.response.RespBaseAck;

/**
 * @功能：解析TTP内容的上下文应用，这个是唯一的对外的解析类
 * @author szf
 * 
 */
public class MLCCParseAgent extends BaseParseMLCCService {
	private static volatile MLCCParseAgent applicationContextByParseTTP = null;

	private MLCCParseAgent() {
	}

	/**
	 * @function 实例化解析TTP包的应用上下文
	 * @return 实例化对象
	 */
	public static MLCCParseAgent getInstance() {
		if (applicationContextByParseTTP == null) {
			synchronized (MLCCParseAgent.class) {
				if (applicationContextByParseTTP == null) {
					applicationContextByParseTTP = new MLCCParseAgent();
				}
			}
		}
		return applicationContextByParseTTP;
	}

	/**
	 * 
	 * @param mlccContentPackage
	 *            vsp包里面的整个 ttpContent包的內容，不含vsp包的其他内容
	 * @param startPos
	 *            byte数组开始位置
	 * @param length
	 *            byte数据的实际解析长度
	 * @return 返回类型是有TTP Code决定的
	 * @throws Exception
	 *             when parse error or params error
	 */
	public RespBaseAck parse(byte[] mlccContentPackage, int startPos, int length)
			throws Exception {
		if (length < 1) {
			throw new Exception("Param is error [ttpLength < 1]");
		}
		if (mlccContentPackage == null || mlccContentPackage.length < 1) {
			throw new NullPointerException(
					"Param is error [ttpPackage is null or ttpPackage length < 1]");
		}
		if (mlccContentPackage.length < length) {
			throw new Exception("Param is error [ttpPackage length '"
					+ mlccContentPackage.length + "' < param ttpLength '"
					+ length + "']");
		}

		// 此处有可能会变动 若ttpContent返回的是 xml格式或者json格式数据，
		// 那么这边应该单次就将ttpContent包解析到可以简单操作的数据类型
		// 例如：Map<String,Object>类型 方便后面的方法不需要再 解析byte数组
		Map<String, String> mlccContentMap = parseMLCC(mlccContentPackage,
				startPos, length);
		return parse(mlccContentMap);
	}

	/**
	 * 
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	public RespBaseAck parse(Map<String, String> requestMap) throws Exception {
		String mlccCode = MLCCStringUtils.getStringByCodeFromMap(requestMap,
				MLCCCodeConfig.MLCCKeyCodeConfig.CODE_NAME);

		ProxyParseMLCCEnum proxyEnum = ProxyParseMLCCEnum
				.getByMLCCCodeName(mlccCode);
		if (proxyEnum == null) {
			throw new NullPointerException(
					"There is not proxy parse impl by mlcc code[" + mlccCode
							+ "] cause by ProxyParseTTPEnum is null.");
		}
		ParseMLCCInterface<?> parseTTPInterface = proxyEnum
				.getParseTTPInterface();
		if (parseTTPInterface == null) {
			throw new NullPointerException(
					"There is not proxy parse impl by ttp code[" + mlccCode
							+ "] cause by ParseTTPInterface is null.");
		}
		return (RespBaseAck) parseTTPInterface.parse(requestMap);
	}
}
