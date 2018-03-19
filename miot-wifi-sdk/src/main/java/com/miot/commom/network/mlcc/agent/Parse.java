package com.miot.commom.network.mlcc.agent;

import java.util.Map;

import com.miot.commom.network.mlcc.parse.MLCCParseAgent;
import com.miot.common.network.mlcc.pojo.response.RespBaseAck;

public class Parse {

	private static MLCCParseAgent applicationContextByParseTTP = MLCCParseAgent
			.getInstance();

	/**
	 * 
	 * @param mlccContentPackage
	 *           mlcc���Э�����
	 * @param startPos
	 *            byte���鿪ʼλ��
	 * @param length
	 *            byte��ݵ�ʵ�ʽ�������
	 * @return ������������MLCC CodeName������
	 * @throws Exception
	 *             when parse error or params error
	 */
	public static RespBaseAck parseMLCCPackage(byte[] mlccContentPackage,
			int startPos, int length) throws Exception {
		return applicationContextByParseTTP.parse(mlccContentPackage, startPos,
				length);
	}
	
	/**
	 * 
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	public static RespBaseAck parseMLCCPackage(Map<String,String> requestMap) throws Exception{
		return applicationContextByParseTTP.parse(requestMap);
	}

//	//TODO by QiaoZhuang
//	private static void parse(RespBaseAck respBaseAck) {
//		if (respBaseAck instanceof RespGetGpioAck) {
////			System.out.println("#############RespGetGpioAck");
//			RespGetGpioAck respGetGpioAck = (RespGetGpioAck) respBaseAck;
////			System.out.println(respGetGpioAck);
//		} else if (respBaseAck instanceof RespSearchAck) {
//			System.out.println("#############RespSearchAck");
//			RespSearchAck respSearchAck = (RespSearchAck) respBaseAck;
//			System.out.println(respSearchAck);
//		} else if (respBaseAck instanceof RespSetGpioAck) {
//			System.out.println("#############RespSetGpioAck");
//			RespSetGpioAck respSetGpioAck = (RespSetGpioAck) respBaseAck;
//			System.out.println(respSetGpioAck);
//		} else if (respBaseAck instanceof RespSetUartAck) {
//			System.out.println("#############RespSetUartAck");
//			RespSetUartAck respSetUartAck = (RespSetUartAck) respBaseAck;
//			System.out.println(respSetUartAck);
//		} else if (respBaseAck instanceof RespSetWifiAck) {
//			System.out.println("#############RespSetWifiAck");
//			RespSetWifiAck respSetWifiAck = (RespSetWifiAck) respBaseAck;
//			System.out.println(respSetWifiAck);
//		}
//	}

}
