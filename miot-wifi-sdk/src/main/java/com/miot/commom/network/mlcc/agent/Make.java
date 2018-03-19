package com.miot.commom.network.mlcc.agent;

import java.util.Map;

import com.miot.common.network.mlcc.pojo.request.BaseNetWorkConfig;
import com.miot.common.network.mlcc.pojo.request.BaseUartConfig;
import com.miot.common.network.mlcc.pojo.request.MakeFcPlatFrom;
import com.miot.common.network.mlcc.pojo.request.MakeFcUart;
import com.miot.common.network.mlcc.pojo.request.MakeFc_complete;
import com.miot.common.network.mlcc.pojo.request.MakeFc_completeFinAck;
import com.miot.common.network.mlcc.pojo.request.MakeGetGpio;
import com.miot.common.network.mlcc.pojo.request.MakeSearch;
import com.miot.common.network.mlcc.pojo.request.MakeSetGpio;
import com.miot.common.network.mlcc.pojo.request.MakeSetLinkInfo;
import com.miot.common.network.mlcc.pojo.request.MakeSetUart;
import com.miot.common.network.mlcc.pojo.request.MakeSetWifi;

public class Make {

	/**
	 * 生成GetGpio接口的发生byte[]数据(未加密)
	 * 
	 * @param channelArray
	 * @return
	 * @throws Exception
	 */
	public static byte[] makeGetGpio(Integer[] channelArray, String mac,
			int udpPort) throws Exception {
		return new MakeGetGpio(channelArray, mac, udpPort).makePackage();
	}

	/**
	 * 生成GetGpio接口的发生byte[]数据(未加密)
	 * 
	 * @param makeGetGpioRequestMap
	 * 
	 * @demo  Map<String,String> makeGetGpioRequestMap 里面填：
	 * makeGetGpioRequestMap.put("cChn","1`2`3");
	 * makeGetGpioRequestMap.put("port","0");
	 * makeGetGpioRequestMap.put("Mac","xxx");
	 * @return
	 */
	public static byte[] makeGetGpio(Map<String, String> makeGetGpioRequestMap) {
		return new MakeGetGpio(makeGetGpioRequestMap).makePackage();
	}

	/**
	 * 生成Search接口的发生byte[]数据(未加密)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] makeSearch(int receivePort) throws Exception {
		return new MakeSearch(receivePort).makePackage();
	}

	/**
	 * 生成Search接口的发生byte[]数据(未加密)
	 * @demo  Map<String,String> makeSearchRequestMap 里面填：
	 * makeSearchRequestMap.put("port","0");
	 * @param makeSearchRequestMap
	 * @return
	 */
	public static byte[] makeSearch(Map<String, String> makeSearchRequestMap) {
		return new MakeSearch(makeSearchRequestMap).makePackage();
	}

	/**
	 * 生成SetGpio接口的发生byte[]数据(未加密)
	 * 
	 * @param channelArray
	 * @param typeArray
	 * @param stateArray
	 * @return
	 * @throws Exception
	 */
	public static byte[] makeSetGpio(Integer[] channelArray,
			Integer[] typeArray, Integer[] stateArray, String mac, int udpPort)
			throws Exception {
		return new MakeSetGpio(channelArray, typeArray, stateArray, mac,
				udpPort).makePackage();
	}

	/**
	 * 
	 * @param makeSetGpioRequestMap
	 * @demo  Map<String,String> makeSetGpioRequestMap 里面填：
	 * makeSetGpioRequestMap.put("Mac","xxx");
	 * makeSetGpioRequestMap.put("port","0");
	 * makeSetGpioRequestMap.put("cChn","1`2");
	 * makeSetGpioRequestMap.put("cType","0`0");
	 * makeSetGpioRequestMap.put("cState","1`1");
	 * @return
	 */
	public static byte[] makeSetGpio(Map<String, String> makeSetGpioRequestMap) {
		return new MakeSetGpio(makeSetGpioRequestMap).makePackage();
	}

	/**
	 * 生成SetUartAck接口的发生byte[]数据(未加密)
	 * 
	 * @param mac
	 * @param uartConfig
	 * @return
	 * @throws Exception
	 */
	public static byte[] makeSetUartAck(int udpPort, String mac,
			BaseUartConfig uartConfig) throws Exception {
		return new MakeSetUart(mac, uartConfig, udpPort).makePackage();
	}

	/**
	 * 生成SetUartAck接口的发生byte[]数据(未加密)
	 * 
	 * @param makeSetUartAckRequestMap
	 * @demo  Map<String,String> makeSetUartAckRequestMap 里面填：
	 * makeSetUartAckRequestMap.put("Mac","xxx");
	 * makeSetUartAckRequestMap.put("port","0");
	 * makeSetUartAckRequestMap.put("UartInfo","0`115200`8`0`256`100");
	 * @return
	 */
	public static byte[] makeSetUartAck(
			Map<String, String> makeSetUartAckRequestMap) {
		return new MakeSetUart(makeSetUartAckRequestMap).makePackage();
	}

	/**
	 * 
	 * 生成SetWifiAck接口的发生byte[]数据(未加密)
	 * 
	 * @param mac
	 * @param devName
	 * @param byName
	 * @param mode
	 * @param apId
	 * @param apPd
	 * @param staId
	 * @param staPd
	 * @param transparentTransmitInfo
	 * @param controlInfo
	 * @param uartConfig
	 * @return
	 * @throws Exception
	 */
	public static byte[] makeSetWifiAck(int udpPort, String mac,
			String devName, String byName,String MLport,String MLinkIp,String MlDip,
			String MlDipI,
			Integer mode, String apId,
			String apPd, String staId, String staPd,
			BaseNetWorkConfig transparentTransmitInfo,
			BaseNetWorkConfig controlInfo, BaseUartConfig uartConfig)
			throws Exception {

		MakeSetWifi makeSetWifi = new MakeSetWifi(udpPort, mac, devName,
				byName,MLport,MLinkIp,MlDip,MlDipI, mode, apId, apPd, staId, staPd,
				transparentTransmitInfo, controlInfo, uartConfig);
		return makeSetWifi.makePackage();
	}
//	map.put("MLinkIp", "114.215.149.63");
//	map.put("MlPort", "28001");
	public static byte[] makeSetLinkInfoAck(String mac,String MLinkIp,String MlPort, String func, String port)
			throws Exception {
		MakeSetLinkInfo makeSetLinkInfo = new MakeSetLinkInfo(mac, MLinkIp,MlPort,func,port);
		return makeSetLinkInfo.makePackage();
	}
	
	public static byte[] makeFcPlatFrom(String mac,String pf_url,String pf_port,String pf_ip1,String pf_ip2)
			throws Exception {
		MakeFcPlatFrom makeFcPlatFrom = new MakeFcPlatFrom( mac,pf_url, pf_port, pf_ip1,pf_ip2);
		return makeFcPlatFrom.makePackage();
	}
	
	public static byte[] makeFcComPlete(String mac)
			throws Exception {
		MakeFc_complete makeComplete = new MakeFc_complete(mac);
		return makeComplete.makePackage();
	}
	
	public static byte[] makeFcComPleteFinAck(String mac)
			throws Exception {
		MakeFc_completeFinAck makeCompleteFinAck = new MakeFc_completeFinAck(mac);
		return makeCompleteFinAck.makePackage();
	}
                                                               
	/**
	 * 生成SetWifiAck接口的发生byte[]数据(未加密)	
	 * @param makeSetWifiAckRequestMap
	 * @demo  Map<String,String> makeSetWifiAckRequestMap 里面填：
	 * makeSetWifiAckRequestMap.put("Mac","xxx");
	 * makeSetWifiAckRequestMap.put("DevName","xx");
	 * makeSetWifiAckRequestMap.put("ByName","xx");
	 * makeSetWifiAckRequestMap.put("port","0");
	 * makeSetWifiAckRequestMap.put("Mode","1");
	 * makeSetWifiAckRequestMap.put("ApId","MlinkAp");
	 * makeSetWifiAckRequestMap.put("ApPd","123456");
	 * makeSetWifiAckRequestMap.put("StaId","MlinkSta");
	 * makeSetWifiAckRequestMap.put("StaPd","123456");
	 * makeSetWifiAckRequestMap.put("tInfo","1`9800`192.168.1.100");
	 * makeSetWifiAckRequestMap.put("cInfo","1`9700`192.168.1.100");
	 * makeSetWifiAckRequestMap.put("UartInfo","0`115200`8`0`256`100");
	 * @return
	 */
	public static byte[] makeSetWifiAck  (
			Map<String, String> makeFcPlatfrom) {
		return new MakeSetWifi(makeFcPlatfrom).makePackage();
	}
	
	/**
	 * 配置平台信息
	 * @param makeFcPlatFronAckresMap
	 * @return
	 */
	public static byte[] makeFcPlatFronAck(Map<String, String> makeFcPlatFronAckresMap){
		return new MakeFcPlatFrom(makeFcPlatFronAckresMap).makePackage();
	}
	
	/**
	 * 4004 功能开启
	 * @param makeSetLinkInfoAckRequestMap
	 * @return
	 */
	public static byte[] makeSetLinkInfoAck(
			Map<String, String> makeSetLinkInfoAckRequestMap) {
		return new MakeSetLinkInfo(makeSetLinkInfoAckRequestMap).makePackage();
	}
	
	/**
	 * 结束语
	 * @param makeMap
	 * @return
	 */
	public static byte[] makeFc_completeAck(Map<String, String> makeMap){
		return new MakeFc_complete(makeMap).makePackage();
	}
	
	/**
	 * 最终结束语配置
	 * @param makeMap
	 * @return
	 */
	public static byte[] makeFc_completeFinAck(Map<String, String> makeMap){
		return new MakeFc_completeFinAck(makeMap).makePackage();
	}
	
	/**
	 * 7681配制Uart数据
	 * @param makeMap
	 * @return
	 */
	public static byte[] makeFcUartInfoAck(Map<String, String> makeMap){
		return new MakeFcUart(makeMap).makePackage();
	}
	
}
