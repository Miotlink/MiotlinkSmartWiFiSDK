package com.miot.android.sdk;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.miotlink.sdk.socket.MiotlinkTools;
import com.example.smartlinklib.ModuleInfo;
import com.example.smartlinklib.SmartLinkManipulator;
import com.example.smartlinklib.SmartLinkManipulator.ConnectCallBack;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.mediatek.elian.ElianNative;
import com.miot.android.smart.SmartConfig;
import com.miot.android.smart.SmartConfigAndMulticase;
import com.miot.android.socket.Tools;
import com.miot.android.util.Util;

public class Miotlink_4004 {
	private Context context;
	private MiotlinkResult result;
	private String mac = "";
	private List<ConfigData> configDatas = null;
	private String fcResult = "";
	private String lastResult = "";

	private boolean isSuccess = true;

	private boolean first = true;

	private boolean two = false;

	private int isconfig = 0;

	private boolean three = false;

	private int time = 0;

	public static Miotlink_4004 miotlink_4004 = null;

	private SmartConfig smartConfig = null;

	// new add
	private SmartConfigAndMulticase smartConfigAndMulticase = null;


	private String userPlatformData = null;
	private String uartData = "";


	public static Miotlink_4004 getInstance(Context context,
			MiotlinkResult result) {
		if (miotlink_4004 == null) {
			miotlink_4004 = new Miotlink_4004(context, result);
		}
		return miotlink_4004;
	}

	public Miotlink_4004(Context context, MiotlinkResult result) {
		this.context = context;
		this.result = result;
		
		this.smartConfigAndMulticase = smartConfigAndMulticase
				.getInstance(context);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MiotLink_4004_Info.MIOTLINK_RESULT_MAC:
				if (msg.obj.toString().equals(""))
					return;
				mac = msg.obj.toString();
				if (smartConfigAndMulticase != null) {
					smartConfigAndMulticase.isStop();
				}
				first = true;
				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_ALL_DATA:
				try {
					fcResult = msg.obj.toString().split("&")[0].split("=")[1]
							.toString();
					if ((fcResult.equals("")) || (fcResult == null)) {
						return;
					}
					ConfigData firstData = (ConfigData) configDatas
							.get(isconfig);
					if (fcResult.equals("")) {
						return;
					}
					if (((fcResult.equals(lastResult)) && (!lastResult
							.equals("")))
							|| (!fcResult.equals(firstData
									.getContentAck_CodeName()))) {
						return;
					}
					if (1 + isconfig == configDatas.size()) {
						first = false;
						two = true;

					} else {
						isconfig += 1;
						lastResult = fcResult;
					}

				} catch (Exception localException) {
				}

				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_COMPLETE:
				if (msg.obj.toString().equals("")) {
					return;
				}
				two = false;
				three = true;

				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_TIMEOUT:
				first = false;
				two = false;
				stopConnection();
				isRun = false;
				result.ConfigFail();
				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_SUCCESS:
				three = false;
				isRun = false;
				if (result != null) {
					result.Success(mac);
				}
				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_IP:
				if (!msg.obj.toString().equals("")) {
					result.MiotlinkAP_IP(msg.obj.toString());
					isserach = false;
				}
				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_FAILD:
				if (result != null) {
					result.ObtainFaild();
				}
				break;
			case MiotLink_4004_Info.HAN_FENG_TIME_OUT:
				stopConnection();
				result.ObtainFaild();
				break;
			case MiotLink_4004_Info.HAN_FENG_CONNECT_OK:
				if (snifferSmartLinker != null) {
					snifferSmartLinker.stop();
				}
				isSmartLink = true;
				break;
			case MiotLink_4004_Info.HANFENG_CONNECT_INFO:
				SmartLinkedModule info=(SmartLinkedModule) msg.obj;
				mac = Util.MakeMac(info.getMac());
				break;
			case MiotLink_4004_Info.MIOTLINK_RESULT_HANFENG:
				isRun = false;
				if (msg.obj != null) {
					if (result != null) {
						result.Success(mac);
					}
				}
				isSmartLink = false;
				break;
			}
		}
	};

	private void init() {
		Tools.smartConnectedHandler(this.handler);
		Tools.fcAllDataHandler(this.handler);
		Tools.fcCompleteHandler(this.handler);
		Tools.searchHandler(handler);

		setFalse();
	}



	/**
	 * 模组以TCP方式自己做服务器
	 * 
	 * @param port
	 *            端口号
	 */
	public void setTcpService(String port) {
		String uartData = "CodeName=fc_user_platform&service=1&dev_port="
				+ port + "&";
		// this.configDatas = MiotLink_4004_Info.getInstance(uartData);
		userPlatformData = uartData;
	}

	/**
	 * 模组以TCP方式连接客户平台
	 * 
	 * @param url
	 *            平台url
	 * @param port
	 *            平台端口
	 */
	public void setTcpPlatform(String url, String port) {
		String uartData = "CodeName=fc_user_platform&service=2&pf_url=" + url
				+ "&pf_port=" + port + "&";
		userPlatformData = uartData;

	}

	/**
	 * 模组以UDP方式连接客户平台
	 * 
	 * @param url
	 * @param port
	 */
	public void setUdpPlatform(String url, String port) {
		String uartData = "CodeName=fc_user_platform&service=4&pf_url=" + url
				+ "&pf_port=" + port + "&";
		userPlatformData = uartData;
	}

	String hanfengData = "";


	/**
	 * uart 串口
	 * 
	 * @param port
	 */
	public void setUart(String port) {
		this.uartData = port;
	}
	private String platform="";
	
	public void setHfPlatform(String platform){
		this.platform=platform;
	}

	private void setConfigDatas() {
		this.configDatas = null;
		if (type == 3) {
			hanfengData = MiotLink_4004_Info.getSetWifiData(platform,uartData);
			return;
		}
		if (type == 2) {
			this.configDatas = MiotLink_4004_Info.getInstance();
			if (!uartData .equals(""))
				this.configDatas.add(MiotLink_4004_Info.getDataMode("3",
						"CodeName=fc_uart&uart_info=0`" + uartData
						+ "`8`0`1`256`100&", "fc_uart_ack", "0"));
			return;
		}
		if (this.configDatas == null) {
			this.configDatas = MiotLink_4004_Info.getInstance();
			if (userPlatformData != null) {// null 说明连接的是妙联
				this.configDatas.add(MiotLink_4004_Info.getDataMode("2",
						userPlatformData, "fc_user_platform_ack", "0"));

			} else {
				if (!uartData.equals(""))
					this.configDatas.add(MiotLink_4004_Info.getDataMode("2",
							"CodeName=fc_uart&uart_info=0`" + uartData
							+ "`8`0`1`256`100&", "fc_uart_ack", "0"));
				return;
			}
			if (!uartData.equals(""))
				this.configDatas.add(MiotLink_4004_Info.getDataMode("3",
						"CodeName=fc_uart&uart_info=0`" + uartData
						+ "`8`0`1`256`100&", "fc_uart_ack", "0"));
		}

	}

	@Deprecated
	public void MiotlinkConfig(String route, String password, int time) {
		this.time = time;
		Tools.initial(this.context, 2);
		setFalse();
		setConfigDatas();
		if (this.smartConfigAndMulticase != null)
			this.smartConfigAndMulticase.sendData(route, password);
		new MyThread().start();
	}

	/**
	 * 
	 * @param type
	 *            配置类型 1 smartconfig_sa 2.7681 3,汉风 4组播
	 * @param route
	 *            路由器名称
	 * @param password
	 *            路由器密码
	 * @param time
	 */

	private int type = 0;
	private ElianNative elian;
	private MulticastSmartLinker snifferSmartLinker;

	public void MiotlinkConfig(int type, String route, String password, int time) {
		this.type = type;
		if (time != 0) {
			this.time = time;
		}
		setFalse();
		switch (type) {
		case 1:
			setConfigDatas();
			Tools.initial(this.context, 2);
			init();
			if (this.smartConfigAndMulticase != null)
				this.smartConfigAndMulticase.sendData(route, password);
			break;

		case 2:
			init();
			setConfigDatas();
			Tools.initial(this.context, 2);
			elian = new ElianNative();
			elian.InitSmartConnection("", 1, 0);
			elian.StartSmartConnection(route, password, "");
			break;
		case 3:
			setConfigDatas();
			Tools.initial(context, 1);
			Tools.setWifiDataHandler(handler);
			snifferSmartLinker = MulticastSmartLinker.getInstance();
			try {
				snifferSmartLinker.start(context, password, route);
				snifferSmartLinker.setTimeoutPeriod(90000);
				snifferSmartLinker.setOnSmartLinkListener(onSmartLinkListener);
			} catch (Exception e) {

			}
			break;
		case 4:
			init();
			setConfigDatas();
			Tools.initial(this.context, 2);
			break;
		}
		new MyThread().start();
	}
	
	OnSmartLinkListener onSmartLinkListener = new OnSmartLinkListener() {

		@Override
		public void onTimeOut() {
			handler.sendEmptyMessage(MiotLink_4004_Info.HAN_FENG_TIME_OUT);
		}

		@Override
		public void onLinked(SmartLinkedModule module) {
			handler.sendMessage(handler.obtainMessage(
					MiotLink_4004_Info.HANFENG_CONNECT_INFO, module));
		}

		@Override
		public void onCompleted() {
			handler.sendEmptyMessage(MiotLink_4004_Info.HAN_FENG_CONNECT_OK);
		}
	};


//
//	ConnectCallBack callBack = new ConnectCallBack() {
//
//		@Override
//		public void onConnectTimeOut() {
//			handler.sendEmptyMessage(MiotLink_4004_Info.HAN_FENG_TIME_OUT);
//		}
//
//		@Override
//		public void onConnectOk() {
//
//			handler.sendEmptyMessage(MiotLink_4004_Info.HAN_FENG_CONNECT_OK);
//		}
//
//		@Override
//		public void onConnect(ModuleInfo arg0) {
//			handler.sendMessage(handler.obtainMessage(
//					MiotLink_4004_Info.HANFENG_CONNECT_INFO, arg0));
//		}
//	};

	private void setFalse() {
		this.isRun = true;
		this.first = false;
		this.two = false;
		this.three = false;
		this.isSmartLink = false;
		this.isconfig = 0;
		this.timer = 0;
		this.fcResult = "";
		this.lastResult = "";
	}

	private boolean isserach = true;

	public void GetMiotLink_AP(final String lastMac) {
		isserach = true;
		new Thread() {
			private int search = 0;

			@Override
			public void run() {
				try {
					while (isserach) {
						if (search > 3) {
							isserach = false;
							handler.sendEmptyMessage(MiotLink_4004_Info.MIOTLINK_RESULT_FAILD);
							return;
						}
						Tools.MiotGetIPAck(lastMac);
						search++;
						sleep(1000);
					}
				} catch (Exception e) {
				}
			}
		}.start();
	}

	public void stopConnection() {
		if (this.smartConfigAndMulticase != null) {
			this.smartConfigAndMulticase.isStop();
		}
		if (snifferSmartLinker != null) {
			snifferSmartLinker.stop();
		}
		if (elian != null) {
			elian.StopSmartConnection();
		}
		isRun = false;
	}

	private boolean isRun = true;

	private int timer = 0;

	private boolean isSmartLink = false;

	class MyThread extends Thread {
		@Override
		public void run() {
			int threein = 0;
			try {
				while (isRun) {
					if (timer >= time) {
						handler.sendEmptyMessage(MiotLink_4004_Info.MIOTLINK_RESULT_TIMEOUT);
						return;
					}
					if (isSmartLink) {
						first = false;
						two = false;
						three = false;
						Tools.MiotFirst4004_AP_Config(hanfengData + "&Mac="
								+ mac);
					}
					if (first) {
						ConfigData firstData = (ConfigData) configDatas
								.get(isconfig);
						if ((firstData != null)
								&& (firstData.getIndex().equals((isconfig + 1)
										+ ""))) {
							String string = firstData.getContent().replace(
									"&amp", "&");
							Tools.SmartConnectedAck(string + "mac=" + mac);
						}
					}
					if (two) {
						Tools.SmartConnectedAck("CodeName=fc_complete&mac="
								+ mac);
					}
					if (three) {
						if (threein > 1) {
							return;
						}
						Tools.SmartConnectedAck("CodeName=fc_complete_fin&mac="
								+ mac);
						handler.sendEmptyMessage(1005);
						three = false;
						isSuccess = false;
						++threein;
					}
					timer++;
					sleep(1000);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
