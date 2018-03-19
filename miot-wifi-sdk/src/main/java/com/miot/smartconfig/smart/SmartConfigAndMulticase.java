package com.miot.smartconfig.smart;

import android.content.Context;

public class SmartConfigAndMulticase {
	// 广播方式
	private static SmartConfig_4004_SA smartConfig;

	// 组播方式
	private static Smartconfig_4004_ZB smartMulticast;

	private static SmartConfigAndMulticase instance;

	// 是否配置
	private boolean isConfig;

	private String ssid, password;

	public static SmartConfigAndMulticase getInstance(Context context) {
		if (instance == null) {
			instance = new SmartConfigAndMulticase();
			smartConfig = new SmartConfig_4004_SA(context);
			smartMulticast = new Smartconfig_4004_ZB(context);
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
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {

				while (isConfig) {
					for (int i = 0; i < 2; i++) {
						smartConfig.sendData(ssid, password);
					}
					Thread.sleep(100);
					for (int i = 0; i < 2; i++) {
						smartMulticast.sendData(ssid, password);
					}
					Thread.sleep(200);
				}

			} catch (Exception e) {

			}

		}
	};

}
