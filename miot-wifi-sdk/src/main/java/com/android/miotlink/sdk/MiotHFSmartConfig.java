package com.android.miotlink.sdk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.android.miotlink.result.ConfigResult;
import com.android.miotlink.sdk.entity.FirstData;
import com.android.miotlink.sdk.socket.MiotlinkTools;
import com.android.miotlink.sdk.util.JsonUtil;
import com.android.miotlink.sdk.util.MacUtil;
import com.android.miotlink.sdk.util.NoFormatConsts;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v7.MulticastSmartLinker;

public class MiotHFSmartConfig {

	public static final int HAN_FENG_TIME_OUT = 1030;

	public static final int HAN_FENG_CONNECT_OK = 1031;

	public static final int HANFENG_CONNECT_INFO = 1032;

	private String mac = "";

	private int failCode = 0;

	private boolean isRuning = true;

	private boolean isPlatform = true;

	private int index = 0;

	private String configAck = "";

	private int time = 120;

	private MulticastSmartLinker snifferSmartLinker = null;

	public static MiotHFSmartConfig instance = null;

	public static MiotHFSmartConfig getInstance(Context context) {
		if (instance == null) {
			instance = new MiotHFSmartConfig(context);
		}
		return instance;
	}

	private ConfigResult configResult = null;

	private Context context = null;

	private List<FirstData> firstDatas = null;

	private MyThread thread = null;

	private MiotHFSmartConfig(Context context) {
		this.context = context;
	}

	public void setConfigResult(ConfigResult configResult) {
		this.configResult = configResult;
	}

	public void hFSmartConfig(int type, String routeName, String password,
			List<FirstData> firstDatas) {
		if (firstDatas == null) {
			firstDatas = NoFormatConsts.getHfInit();
		}
		this.firstDatas = firstDatas;
		if (type == 3) {
			snifferSmartLinker = MulticastSmartLinker.getInstance();
			try {
				snifferSmartLinker.start(context, password, routeName);
				snifferSmartLinker.setTimeoutPeriod(90000);
				snifferSmartLinker.setOnSmartLinkListener(onSmartLinkListener);
			} catch (Exception e) {

			}
		}
		initHfTools();
		thread = new MyThread();
		thread.start();
	}

	public void hFSmartConfig(int type, String routeName, String password,
			int time) {
		if (time != 0) {
			this.time = time;
		}
		if (firstDatas == null) {
			firstDatas = NoFormatConsts.getHfInit();
		}
		if (type == 3) {
			snifferSmartLinker = MulticastSmartLinker.getInstance();
			try {
				snifferSmartLinker.start(context, password, routeName);
				snifferSmartLinker.setTimeoutPeriod(90000);
				snifferSmartLinker.setOnSmartLinkListener(onSmartLinkListener);
			} catch (Exception e) {

			}
		}
		failCode = 1030;
		configAck = "检查路由器密码是否正确或者设备是否处于还原状态";
		initHfTools();
		thread = new MyThread();
		thread.start();
	}

	public void hFSmartConfig(int type, String routeName, String password,
			String qrcode, int time) {
		if (time != 0) {
			this.time = time;
		}
		if (type == 3) {
			snifferSmartLinker = MulticastSmartLinker.getInstance();
			try {
				snifferSmartLinker.start(context, password, routeName);
				snifferSmartLinker.setTimeoutPeriod(90000);
				snifferSmartLinker.setOnSmartLinkListener(onSmartLinkListener);
			} catch (Exception e) {

			}
		}
		firstDatas = JsonUtil.getInstance(qrcode);
		if (firstDatas == null) {
			firstDatas = NoFormatConsts.getHfInit();
		}
		failCode = 1030;
		configAck = "检查路由器密码是否正确或者设备是否处于还原状态";
		initHfTools();
		thread = new MyThread();
		thread.start();
	}

	public void setPlatform(String platfrom, String port, String uart) {
		firstDatas = NoFormatConsts.getHfInstance(platfrom, port, uart);
	}

	private void initHfTools() {
		isRuning = true;
		isPlatform = false;
		MiotlinkTools.initial(context, 1);
		MiotlinkTools.fcAllDataHandler(handler);
		MiotlinkTools.setWifiHandler(handler);
	}

	private String fcResult = "";
	private String lastResult = "";
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HANFENG_CONNECT_INFO:
				SmartLinkedModule info = (SmartLinkedModule) msg.obj;
				mac = MacUtil.MakeMac(info.getMac());
				break;
			case HAN_FENG_TIME_OUT:
				snifferSmartLinker.stop();
				failCode = 1031;
				configAck = "HF SDK time out";
				if (configResult != null) {
					configResult.resultFail(failCode, configAck);
				}
				stopHfSmartConfig();
				break;
			case HAN_FENG_CONNECT_OK:
				snifferSmartLinker.stop();
				isPlatform = true;
				break;
			case MiotlinkTools.MW_WIFI_CONFIG_ACK:
				@SuppressWarnings("unchecked")
				Map<String, String> map = (HashMap<String, String>) msg.obj;
				if (map != null) {
					try {
						String result = map.get("Result").toString();
						if (result.equals("0")) {
							configResult.resultFail(1020, configAck);
							stopHfSmartConfig();
							return;
						}
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("mac", mac);
						if (configResult != null) {
							configResult.resultOk(map1);
						}
						stopHfSmartConfig();
					} catch (Exception e) {

					}
				}
				break;
			case MiotlinkTools.FC_ALL_DATA:
				fcResult = msg.obj.toString().split("&")[0].split("=")[1]
						.toString();
				if (fcResult.equals("") || fcResult == null) {
					return;
				} else {
					FirstData firstData = firstDatas.get(index);
					if (fcResult == null) {
						return;
					} else {
						if (!fcResult.equals(lastResult)
								|| lastResult.equals("")) {
							if (fcResult.equals(firstData
									.getContentAck_CodeName())) {
								if ((1 + index) == firstDatas.size()) {
									isPlatform = false;
								} else {
									index++;
									failCode = 2;
									lastResult = fcResult;
								}
							}
						}
					}
				}
				break;
			case 1050:
				stopHfSmartConfig();
				if (configResult != null) {
					configResult.resultFail(failCode, configAck);
				}
				break;
			}
		};
	};

	class MyThread extends Thread {

		int count = 0;

		@Override
		public void run() {
			try {
				while (isRuning) {
					if (isPlatform) {
						if (count >= time) {
							handler.sendEmptyMessage(1050);
							return;
						}
						failCode = 1033;
						FirstData firstData = firstDatas.get(index);
						if (firstData != null) {
							String string = firstData.getContent().replace(
									"&amp", "&");
							configAck = firstData.getContentAck_CodeName()
									+ "is not callback";
							String[] codeNames = string.split("&");
							if (!codeNames[0].equals("CodeName=SetWifi")) {
								MiotlinkTools.MiotFirst4004_AP_Config(string
										+ "&Mac=" + mac);
							} else {
								String code = "&Mac=" + mac;
								MiotlinkTools.MiotFirst4004_AP_Config(string
										+ code);
							}
						}
					}
					count++;
					sleep(1000);
				}

			} catch (Exception e) {

			}
			super.run();
		}
	}

	OnSmartLinkListener onSmartLinkListener = new OnSmartLinkListener() {

		@Override
		public void onTimeOut() {
			handler.sendEmptyMessage(HAN_FENG_TIME_OUT);
		}

		@Override
		public void onLinked(SmartLinkedModule module) {
			handler.sendMessage(handler.obtainMessage(HANFENG_CONNECT_INFO,
					module));
		}

		@Override
		public void onCompleted() {
			handler.sendEmptyMessage(HAN_FENG_CONNECT_OK);
		}
	};

	public void stopHfSmartConfig() {
		if (snifferSmartLinker != null) {
			snifferSmartLinker.stop();
		}
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
		if (isRuning) {
			isRuning = false;
		}
		MiotlinkTools.fcAllDataHandler(null);
		MiotlinkTools.setWifiHandler(null);
	}

}
