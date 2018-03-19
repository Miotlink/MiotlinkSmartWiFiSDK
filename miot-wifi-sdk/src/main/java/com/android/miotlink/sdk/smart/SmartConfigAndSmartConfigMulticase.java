package com.android.miotlink.sdk.smart;

import android.content.Context;

public class SmartConfigAndSmartConfigMulticase {
	// 广播方式
	private static SmartConfig_udp smartConfig=null;

	// 组播方式
	private static Smart_4004_Config smartMulticast=null;

	private static SmartConfigAndSmartConfigMulticase instance=null;

	// 是否配置
	private boolean isConfig;

	private String ssid, password;

	public static SmartConfigAndSmartConfigMulticase getInstance(Context context) {
		if (instance == null) {
			instance = new SmartConfigAndSmartConfigMulticase();
			smartConfig = new SmartConfig_udp(context);
			smartMulticast = new Smart_4004_Config(context);
		}
		return instance;
	}

	public void sendData(String ssid, String password) {

		this.ssid = ssid;
		this.password = password;
		isConfig = true;

		new Thread(runnable).start();

	}

	public void isStop() {
		if (isConfig)
			isConfig = false;
		smartConfig=null;
		smartMulticast=null;
		instance=null;
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {

			try {
				while (isConfig) {
					for (int i = 0; i < 2; i++) {
						smartConfig.sendData(ssid, password);
					}
					Thread.sleep(50);
					for (int i = 0; i < 2; i++) {
						smartMulticast.sendData(ssid, password);
					}
					Thread.sleep(50);
				}

			} catch (Exception e) {

			}

		}
	};

}
