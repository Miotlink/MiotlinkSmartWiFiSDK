package com.android.miotlink.sdk.smart;

import android.content.Context;

public class Smart_4004_Config {

	public static Smart_4004_Config instance = null;

	public static Smart_4004_Config getInstance(Context context) {
		if (instance == null) {
			instance = new Smart_4004_Config(context);
		}
		return instance;
	}

	private int data = 10;
	private SmartMulticast socket;

	@SuppressWarnings("unused")
	private Context context = null;

	public Smart_4004_Config(Context context) {
		this.context = context;
		socket = new SmartMulticast();
	}

	String ssid = "";

	String password = "";

	public void SmartConfig(String ssid, String password) {
		this.ssid = ssid;
		this.password = password;
		isSend = true;
		new Thread(smartRun).start();
	}

	private boolean isSend=true;

	public void sendData(String ssid, String password) {

		char[] charSSID = ssid.toCharArray();
		char[] charPwd = password.toCharArray();

		byte[] buffer = "SmartConfig-V2".getBytes();
		int len = buffer.length;
		int n2 = 0;

		try {
			// 标志位
			for (int i = 1; i <= 5; i++) {
				n2++;
				socket.send(30000, "239.118." + n2 + "." + i, buffer, len);
				Thread.sleep(data);
			}
			
			n2++;
			socket.send(30000, "239.119." + n2 + "." + ssid.length(), buffer,
					len);
			Thread.sleep(data);

			// password长度
			n2++;
			socket.send(30000, "239.119." + n2 + "." + password.length(),
					buffer, len);
			Thread.sleep(data);

			// ssid内容
			for (int i = 0; i < charSSID.length; i++) {
				n2++;
				socket.send(30000, "239.120." + n2 + "." + ((int) charSSID[i]),
						buffer, len);
				Thread.sleep(data);
			}

			// password内容
			for (int i = 0; i < charPwd.length; i++) {
				n2++;
				socket.send(30000, "239.121." + n2 + "." + ((int) charPwd[i]),
						buffer, len);
				Thread.sleep(data);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			n2 = 0;
		}
	}

	Runnable smartRun = new Runnable() {

		@Override
		public void run() {
			char[] charSSID = ssid.toCharArray();
			char[] charPwd = password.toCharArray();

			byte[] buffer = "SmartConfig-V2".getBytes();
			int len = buffer.length;

			int n2 = 0;
			while (isSend) {
				try {
					// 标志位
					for (int i = 1; i <= 5; i++) {
						n2++;
						socket.send(30000, "239.118." + n2 + "." + i, buffer,
								len);
						Thread.sleep(data);
					}
					// SSID长度
					n2++;
					socket.send(30000, "239.119." + n2 + "." + ssid.length(),
							buffer, len);
					Thread.sleep(data);

					// password长度
					n2++;
					socket.send(30000,
							"239.119." + n2 + "." + password.length(), buffer,
							len);
					Thread.sleep(data);

					// ssid内容
					for (int i = 0; i < charSSID.length; i++) {
						n2++;
						socket.send(30000, "239.120." + n2 + "."
								+ ((int) charSSID[i]), buffer, len);
						Thread.sleep(data);
					}

					// password内容
					for (int i = 0; i < charPwd.length; i++) {
						n2++;
						socket.send(30000, "239.121." + n2 + "."
								+ ((int) charPwd[i]), buffer, len);
						Thread.sleep(data);
					}

				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					n2 = 0;
				}

			}
		}
	};

	public void sotpConfig() {
		if (isSend) {
			isSend = false;
		}
	}

}
