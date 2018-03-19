package com.miot.android.sdk;

import java.util.ArrayList;
import java.util.List;

public class MiotLink_4004_Info {
	/** smart 返回 **/
	public static final int MIOTLINK_RESULT_MAC = 1001;
	/** 配置信息返回 **/
	public static final int MIOTLINK_RESULT_ALL_DATA = 1002;
	/** 配置结束 **/
	public static final int MIOTLINK_RESULT_COMPLETE = 1003;
	/** 配置超时 **/
	public static final int MIOTLINK_RESULT_TIMEOUT = 1004;
	/** 配置成功 **/
	public static final int MIOTLINK_RESULT_SUCCESS = 1005;
	/** 获取ip **/
	public static final int MIOTLINK_RESULT_IP = 1006;
	/** 配置失败 **/
	public static final int MIOTLINK_RESULT_FAILD = 1007;

	public static final int MIOTLINK_RESULT_UART = 1008;
	
	public static final int MIOTLINK_RESULT_HANFENG=1009;

	/**
	 * 汉风回调数据
	 */
	public static final int HAN_FENG_TIME_OUT = 1030;

	public static final int HAN_FENG_CONNECT_OK = 1031;

	public static final int HANFENG_CONNECT_INFO = 1032;

	static String firstData = "CodeName=fc_ml_platform&pf_url=www.51miaomiao.com&pf_port=28001&pf_ip1=118.190.67.214&pf_ip2=122.225.196.132&";

	public static final String HANFENG_CONFIG_DATA = "CodeName=SetWifi&MlPort=28001&MLinkIp=www.51miaomiao.com&MlDip=1992180694&MlDipI=1887204221&UartInfo=0`9600`8`0`256`100";

	public static List<ConfigData> getInstance() {
		List<ConfigData> data = null;
		if (data == null) {
			data = new ArrayList<ConfigData>();
		}
		data.add(getDataMode("1", firstData, "fc_ml_platform_ack", "0"));
		return data;
	}
	
	public static String getSetWifiData(String platform,String uart){
		if (uart.equals("")||uart==null) {
			uart="9600";
		}
		if (platform.equals("")||platform==null) {
			platform="www.51miaomiao.com";
		}
		return "CodeName=SetWifi&MlPort="+"28001"+"&MLinkIp="+platform+"&MlDip=1992180694&MlDipI=1887204221&UartInfo=0`"+uart+"`8`0`256`100";
		
	}

	public static ConfigData getDataMode(String index, String content,
			String contentAck_CodeName, String contentAck_result) {
		ConfigData configData = new ConfigData(index, content,
				contentAck_CodeName, contentAck_result);
		return configData;
	}
}