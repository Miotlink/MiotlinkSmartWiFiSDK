package com.android.miotlink.sdk.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;

import com.android.miotlink.sdk.entity.FirstData;

public class NoFormatConsts {

	private static String SDK_PLATFORM = "www.51miaomiao.com";
	
	
	private static String SDK_PLATFORM_IP = "118.190.67.214";
	
	private static String SDK_MlDip="";

	private static final String SDK_PORT = "28001";

	@SuppressLint("NewApi")
	public static void setPlatformAddress(int type, String ip) throws Exception {
		if (type == 1) {
			SDK_PLATFORM = "112.124.115.125";
			if (!ip.isEmpty()) {
				SDK_PLATFORM_IP = ip;
				return;
			}
			SDK_PLATFORM_IP = "112.124.115.125";
			return;
		}
		if (type == 2) {
			SDK_PLATFORM = "www.51miaomiao.com";
			if (!ip.isEmpty()) {
				SDK_PLATFORM_IP = ip;
				return;
			}
			SDK_PLATFORM_IP = "118.190.67.214";
			return;
		}
		if (type == 3) {
			SDK_PLATFORM = "us.51miaomiao.com";
			if (!ip.isEmpty()) {
				SDK_PLATFORM_IP = ip;
				return;
			}
			SDK_PLATFORM_IP = "47.89.181.241";
		}
	}

	/**
	 * 默认 配置妙联平台
	 * 
	 * @return
	 */
	public static List<FirstData> getInstance(String uart) {
		List<FirstData> firstDatas = null;
		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
			FirstData firstData = new FirstData("1",
					"CodeName=fc_ml_platform&pf_url=" + SDK_PLATFORM
							+ "&pf_port=28001&pf_ip1=" + SDK_PLATFORM_IP
							+ "&pf_ip2=122.225.196.132&", "fc_ml_platform_ack",
					"0");
			firstDatas.add(firstData);
			if (!uart.equals("")) {
				String uartData = "CodeName=fc_uart&uart_info=0`" + uart
						+ "`8`0`1`256`100&";
				firstDatas
						.add(new FirstData("2", uartData, "fc_uart_ack", "0"));
			}
		}
		return firstDatas;

	}

	public static List<FirstData> getInstance() {
		List<FirstData> firstDatas = null;
		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
			firstDatas.add(new FirstData("1", "CodeName=fc_ml_platform&pf_url="
					+ SDK_PLATFORM + "&pf_port=28001&pf_ip1=" + SDK_PLATFORM_IP
					+ "&pf_ip2=122.225.196.132&", "fc_ml_platform_ack", "0"));
		}
		return firstDatas;

	}

	/**
	 * 配置 客户平台
	 * 
	 * @param url
	 * @param port
	 * @return
	 */
	public static List<FirstData> getPlatform(int modle, String url,
			String port, String uart) {
		List<FirstData> firstDatas = null;
		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
			firstDatas.add(new FirstData("1", "CodeName=fc_ml_platform&pf_url="
					+ SDK_PLATFORM + "&pf_port=28001&pf_ip1=" + SDK_PLATFORM_IP
					+ "&pf_ip2=122.225.196.132&", "fc_ml_platform_ack", "0"));
			if (port.equals("") && url.equals("") && modle == 0) {
				return firstDatas;
			}
			String platfrom = "CodeName=fc_user_platform&service=" + modle
					+ "&pf_url=" + url + "&pf_port=" + port + "&";
			firstDatas.add(new FirstData("2", platfrom, "fc_user_platform_ack",
					"0"));
			if (!uart.equals("")) {
				if (!uart.equals("")) {
					String uartData = "CodeName=fc_uart&uart_info=0`" + uart
							+ "`8`0`1`256`100&";
					firstDatas.add(new FirstData("3", uartData, "fc_uart_ack",
							"0"));
				}
			}
		}
		return firstDatas;
	}

	public static List<FirstData> getHfInstance(String platfrom, String port,
			String uart) {
		List<FirstData> firstDatas = null;
		if (uart.equals("") || uart == null) {
			uart = "9600";
		}
		if (platfrom.equals("") || platfrom == null) {
			platfrom = SDK_PLATFORM;
		}
		if (port.equals("") || port == null) {
			platfrom = "28001";
		}
		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
			firstDatas.add(new FirstData("1", "CodeName=SetWifi&MlPort=" + port
					+ "&MLinkIp=" + platfrom
					+ "&MlDip=1992180694&MlDipI=1887204221&UartInfo=0`" + uart
					+ "`8`0`256`100", "SetWifiAck", "0"));
		}
		return firstDatas;

	}

	public static List<FirstData> getHfInit() {
		List<FirstData> firstDatas = null;
		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
			firstDatas.add(new FirstData("1", "CodeName=SetWifi&MlPort="
					+ "28001" + "&MLinkIp=" + SDK_PLATFORM
					+ "&MlDip=1992180694&MlDipI=1887204221&UartInfo=0`"
					+ "9600" + "`8`0`256`100", "SetWifiAck", "0"));
		}
		return firstDatas;

	}

	public static List<FirstData> getLeXinUart(Map<String, Object> map) {
		List<FirstData> firstDatas = null;
		String uart = "";
		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
			if (map != null) {
				try {
					uart = map.get("uart").toString();
				} catch (Exception e) {
					uart = "9600";
				}
				if (uart.equals("")) {
					uart = "9600";
				}
				firstDatas.add(new FirstData("1", "CodeName=SetWifi&MlPort="
						+ "28001" + "&MLinkIp=" + SDK_PLATFORM
						+ "&MlDip=1992180694&MlDipI=1887204221&UartInfo=0`"
						+ uart + "`8`0`256`100", "SetWifiAck", "0"));
			}
		}
		return firstDatas;

	}
	
	
	/**
	 * 
	 * @param mode
	 *            2 客户端平台
	 * @param platform
	 *            服务端域名或者IP
	 * @param port
	 *            客户平台的端口 port
	 */
	@SuppressLint("NewApi") 
	public static List<FirstData> getMiotlinkPlatform(int mode,
			String platform, String port, String uart) {
		ArrayList<FirstData> firstDatas = null;
		if (uart.isEmpty()) {
			uart = "9600";
		}

		if (firstDatas == null) {
			firstDatas = new ArrayList<FirstData>();
		}
		if (mode == 5) {
			if (platform.isEmpty()) {
				platform="192.168.1.100";
			}
			if (port.isEmpty()) {
				port="9800";
			}
		}
		firstDatas.add(new FirstData("1", "CodeName=SetWifi&MlPort="
				+ "28001" + "&MLinkIp=" + SDK_PLATFORM
				+ "&MlDip=1926731071&MlDipI=1887204221&UartInfo=0`"
				+ uart + "`8`0`256`100" + "&tInfo=" + mode + "`" + port
				+ "`" + platform, "SetWifiAck", "0"));
		return firstDatas;

	}

}
