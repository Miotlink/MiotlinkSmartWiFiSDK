package com.miot.commom.network.mlcc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.miot.common.network.mlcc.pojo.request.BaseMode;
import com.miot.common.network.mlcc.pojo.request.BaseNetWorkConfig;
import com.miot.common.network.mlcc.pojo.request.BaseUartConfig;

public class MLCCStringUtils {
	
	public static final String MLCC_SPLIT_FLAG = "&";
	public static final String MLCC_SPLIT_KEY_VALUE_FLAG = "=";
	public static final String MLCC_SPLIT_SAME_VALUE_FLAG = "`";
	public static final String MLCC_SPLIT_SAME_DH_VALUE_FLAG = ",";

	public static Map<String, String> parseMLCC2Map(byte[] mlccContentPackage,
			int startPos, int length) throws Exception {
		String mlccContent = "";
		try {
			byte[] mlccContentByteSrc = new byte[length];
			System.arraycopy(mlccContentPackage, startPos, mlccContentByteSrc,
					0, length);

			mlccContent = new String(mlccContentByteSrc);
		} catch (Exception e) {
		
			throw e;
		}
		return string2Map(mlccContent);
	}

	private static Map<String, String> string2Map(String src) throws Exception {
		Map<String, String> mlccContentMap = new HashMap<String, String>();
		for (String oneContent : src.split(MLCC_SPLIT_FLAG)) {
			String keyValueArray[] = oneContent
					.split(MLCC_SPLIT_KEY_VALUE_FLAG);
			if (keyValueArray == null
					|| (keyValueArray.length != 2 && !oneContent
							.contains(MLCC_SPLIT_KEY_VALUE_FLAG))) {
				throw new Exception("one keyValueArray length != 2, param=["
						+ oneContent + "]");
			} else {
				if (keyValueArray.length == 2) {
					mlccContentMap.put(keyValueArray[0], keyValueArray[1]);
				} else {
					mlccContentMap.put(keyValueArray[0], "");
				}
			}
		}
		return mlccContentMap;
	}

	public static String getStringByCodeFromMap(
			Map<String, String> mlccContentMap, String codeName) {
		return mlccContentMap.get(codeName) + "";
	}

	public static String getBaseNetWork2String(
			BaseNetWorkConfig baseNetWorkConfig) {
		if (baseNetWorkConfig == null) {
			return null;
		}
		return new StringBuilder().append(baseNetWorkConfig.getMode())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(baseNetWorkConfig.getPort())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(baseNetWorkConfig.getIp()).toString();
	}

	/**
	 * 通道号`波特率`数据位`校验位`接收包长`接收超时时间(选填)
	 * 
	 * @param uartConfig
	 * @return
	 * @throws Exception
	 */
	public static String getBaseUart2String(BaseUartConfig uartConfig) {
		if (uartConfig == null) {
			return null;
		}

		return new StringBuffer().append(uartConfig.getiChn())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(uartConfig.getiBaud())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(uartConfig.getiData())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(uartConfig.getiParity())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(uartConfig.getiPLen())
				.append(MLCC_SPLIT_SAME_VALUE_FLAG)
				.append(uartConfig.getiPTimeOut()).toString();
	}

	public static String getChannelArray2String(Integer[] channelArray)
			throws Exception {
		if (channelArray == null || channelArray.length == 0) {
			throw new Exception("channelArray is null");
		}
		StringBuilder sb = new StringBuilder();
		Integer[] chArray = null;
		if (channelArray.length == 1 && channelArray[0] == 0) {
			chArray = new Integer[8];
			chArray[0] = 1;
			chArray[1] = 2;
			chArray[2] = 3;
			chArray[3] = 4;
			chArray[4] = 5;
			chArray[5] = 6;
			chArray[6] = 7;
			chArray[7] = 8;
		} else {
			chArray = channelArray;
		}

		for (Integer channel : chArray) {
			if (channel == 0) {
				throw new Exception(
						"channel number start with [1], you put [0]! It is error!");
			}
			sb.append(channel).append(MLCC_SPLIT_SAME_VALUE_FLAG);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static String getTypeArray2String(Integer[] cTypeArray)
			throws Exception {
		if (cTypeArray == null || cTypeArray.length == 0) {
			throw new Exception("cTypeArray is null");
		}
		StringBuilder sb = new StringBuilder();

		for (Integer type : cTypeArray) {
			if (type != 0 && type != 1) {
				throw new Exception("type is error!support by [0,1], input["
						+ type + "]");
			}
			sb.append(type).append(MLCC_SPLIT_SAME_VALUE_FLAG);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static String getStateArray2String(Integer[] cStateArray)
			throws Exception {
		if (cStateArray == null || cStateArray.length == 0) {
			throw new Exception("cStateArray is null");
		}
		StringBuilder sb = new StringBuilder();

		for (Integer state : cStateArray) {
			if (state != 0 && state != 1) {
				throw new Exception("state is error!support by [0,1], input["
						+ state + "]");
			}
			sb.append(state).append(MLCC_SPLIT_SAME_VALUE_FLAG);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static BaseUartConfig getUartString2UartInfoConfig(String uartInfo)
			throws Exception {
		String[] uartInfoParamValues = uartInfo
				.split(MLCC_SPLIT_SAME_VALUE_FLAG);
		if (uartInfoParamValues == null || uartInfoParamValues.length != 6) {
			throw new Exception("uartInfo is error[" + uartInfo + "]");
		}
		return new BaseUartConfig(Integer.parseInt(uartInfoParamValues[0]),
				Integer.parseInt(uartInfoParamValues[1]),
				Integer.parseInt(uartInfoParamValues[2]),
				Integer.parseInt(uartInfoParamValues[3]));
	}

	public static BaseNetWorkConfig getTInfo2BaseNetWork(String tInfo)
			throws Exception {
		String[] baseNetWorkArray = tInfo.split(MLCC_SPLIT_SAME_VALUE_FLAG);
		if (baseNetWorkArray == null || baseNetWorkArray.length != 3) {
			throw new Exception("uartInfo is error[" + tInfo + "]");
		}
		return new BaseNetWorkConfig(Integer.parseInt(baseNetWorkArray[0]),
				Integer.parseInt(baseNetWorkArray[1]), baseNetWorkArray[2]);
	}

	public static BaseMode getMIp2Mode(String mip) {
		if (mip == null) {
			return null;
		}
		String[] mipParams = mip.split(MLCC_SPLIT_SAME_VALUE_FLAG);
		if (mipParams.length != 2) {
			return null;
		}

		return new BaseMode(Integer.parseInt(mipParams[0]), mipParams[1]);
	}

	public static List<String> list2Array(String[] src) {
		List<String> list = new ArrayList<String>();
		for (String o : src) {
			list.add(o);
		}
		return list;
	}

}
