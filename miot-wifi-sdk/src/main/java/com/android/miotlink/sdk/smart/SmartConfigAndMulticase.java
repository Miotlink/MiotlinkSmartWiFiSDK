package com.android.miotlink.sdk.smart;

import android.content.Context;

public class SmartConfigAndMulticase {
	// 广播方式
	private static SmartConfig smartConfig;

	// 组播方式
	private static Smart_4004_Config smartMulticast;

	private static SmartConfigAndMulticase instance;

	// 是否配置
	private boolean isConfig;

	private String ssid, password;

	public static SmartConfigAndMulticase getInstance(Context context) {
		if (instance == null) {
			instance = new SmartConfigAndMulticase();
			smartConfig = new SmartConfig(context);
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
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
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
