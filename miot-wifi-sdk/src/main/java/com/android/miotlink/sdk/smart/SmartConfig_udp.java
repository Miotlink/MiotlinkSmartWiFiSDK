package com.android.miotlink.sdk.smart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.android.miotlink.sdk.socket.Tools;
import com.android.miotlink.sdk.util.CRC16;

public class SmartConfig_udp implements UdpSmartSocket.IReceiver {

	private Context context;

	private UdpSmartSocket cuUdpSocket = null;

	private Thread scThread = null;

	private int date = 10;

	private char[] chSSID;
	private char[] chPWD;
	private List<StringBuffer> listSSID = null;
	private List<StringBuffer> listPWD = null;

	private boolean isRun = true;

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	private WifiManager wifiManager = null;

	public WifiManager.MulticastLock lock = null;

	public SmartConfig_udp(Context context) {
		this.context = context;
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		lock = wifiManager.createMulticastLock("wifi");
		cuUdpSocket = new UdpSmartSocket(new Random().nextInt(3000) + 20000);
	}

	public void sendData(String ssid, String password) {
		chSSID = ssid.toCharArray();
		if (chPWD == null)
			chPWD = password.toCharArray();

		if (listSSID == null) {
			listSSID = new ArrayList<StringBuffer>();
		}
		if (listPWD == null) {
			listPWD = new ArrayList<StringBuffer>();
		}

		try {
			clear();
			sendData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendData() throws Exception {

		for (int i = 0; i < 30; i++) {
			Thread.sleep(date);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 20; j++)
				sb.append("1");
			send(sb.toString());

		}
		for (int i = 0; i < chSSID.length; i++) {
			int ascii = (int) chSSID[i];
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < ascii; j++) {
				sb.append(chSSID[i]);
			}
			listSSID.add(sb);
			Thread.sleep(date);
			send(sb.toString());
		}

		for (int i = 0; i < 3; i++) {

			Thread.sleep(date);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 21; j++)
				sb.append("2");
			send(sb.toString());

		}

		// password
		for (int i = 0; i < chPWD.length; i++) {
			int ascii = (int) chPWD[i];
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < ascii; j++) {
				sb.append(chPWD[i]);
			}
			listPWD.add(sb);

			Thread.sleep(date);
			send(sb.toString());
		}
		for (int i = 0; i < 3; i++) {

			Thread.sleep(date);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 22; j++)
				sb.append("3");
			send(sb.toString());

		}

		String str = "";
		for (int i = 0; i < listSSID.size(); i++) {
			StringBuffer sb = listSSID.get(i);
			str += sb.length() + " ";
		}

		for (int i = 0; i < listPWD.size(); i++) {
			StringBuffer sb = listPWD.get(i);
			str += sb.length() + " ";
		}

		clear();

		String crc = getCRC(str);
		String max = CRC16.getCRC16Max(crc);
		String min = CRC16.getCRC16Min(crc);
		int maxLen = Integer.parseInt(max, 16) + 23;
		int minLen = Integer.parseInt(min, 16) + 23;
		StringBuffer maxBuffer = new StringBuffer();
		for (int i = 0; i < maxLen; i++) {
			maxBuffer.append(4);
		}
		Thread.sleep(date);
		send(maxBuffer.toString());

		StringBuffer minBuffer = new StringBuffer();
		for (int i = 0; i < minLen; i++) {
			minBuffer.append(5);
		}
		send(minBuffer.toString());
		Thread.sleep(date);
	}

	private boolean send(String string) {

		byte[] makeFirst = string.getBytes();

		return cuUdpSocket.send("255.255.255.255", 30000, makeFirst,
				makeFirst.length);

	}

	private void clear() {
		for (int i = 0; i < listSSID.size(); i++) {
			StringBuffer sb = listSSID.get(i);
			sb.delete(0, sb.length());
		}

		for (int i = 0; i < listPWD.size(); i++) {
			StringBuffer sb = listPWD.get(i);
			sb.delete(0, sb.length());
		}

		listSSID.removeAll(listSSID);
		listPWD.removeAll(listPWD);

	}

	private String getCRC(String str) {
		String[] s = str.split(" ");
		byte[] bytes = new byte[s.length];

		for (int j = 0; j < bytes.length; j++) {
			bytes[j] = (byte) Integer.parseInt((s[j]));
		}
		String hexStr = Tools.bytesToHexString(bytes, bytes.length);

		byte[] buf = CRC16.getSendBuf(hexStr);
		String crc = CRC16.getBufHexStr(buf).toLowerCase();
		return crc;

	}

	public void isStop() {
		if (scThread != null) {
			isRun = false;
			scThread.interrupt();
			scThread = null;
		}
	}

	@Override
	public void onReceive(int localPort, String host, int port, byte[] bs,
			int len) {

	}

}
