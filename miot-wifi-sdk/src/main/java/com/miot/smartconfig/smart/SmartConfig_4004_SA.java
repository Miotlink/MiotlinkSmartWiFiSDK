package com.miot.smartconfig.smart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.miot.android.socket.Tools;

public class SmartConfig_4004_SA implements UdpSmartSocket.IReceiver {
	private Context context;
	private UdpSmartSocket cuUdpSocket = null;

	private Thread scThread = null;

	private int date = 3;
	private char[] chSSID;
	private char[] chPWD;
	private List<StringBuffer> listSSID = null;
	private List<StringBuffer> listPWD = null;

	private boolean isRun = true;

	private WifiManager wifiManager = null;

	public WifiManager.MulticastLock lock = null;

	public boolean isRun() {
		return this.isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public SmartConfig_4004_SA(Context context) {
		this.context = context;
		this.wifiManager = ((WifiManager) context.getSystemService("wifi"));
		this.lock = this.wifiManager.createMulticastLock("wifi");
		this.cuUdpSocket = new UdpSmartSocket(
				new Random().nextInt(3000) + 20000);
	}

	public void isSmartConfig(String ssid, String password) {
		this.isRun = true;
		this.chSSID = ssid.toCharArray();
		this.chPWD = password.toCharArray();
		if (this.listSSID == null) {
			this.listSSID = new ArrayList();
		}
		if (this.listPWD == null) {
			this.listPWD = new ArrayList();
		}
		this.scThread = new Thread(new Runnable() {
			public void run() {
				try {
					int sleep = 0;
					while (SmartConfig_4004_SA.this.isRun) {
						if (sleep == 2) {
							Thread.sleep(500L);
							sleep = 0;
							for (int i = 0; i < 3; ++i) {
								Thread.sleep(SmartConfig_4004_SA.this.date);
								StringBuffer sb = new StringBuffer();
								for (int j = 0; j < 20; ++j)
									sb.append("1");
								SmartConfig_4004_SA.this.send(sb.toString());
							}

							for (int i = 0; i < SmartConfig_4004_SA.this.chSSID.length; ++i) {
								int ascii = SmartConfig_4004_SA.this.chSSID[i];
								StringBuffer sb = new StringBuffer();
								for (int j = 0; j < ascii; ++j) {
									sb.append(SmartConfig_4004_SA.this.chSSID[i]);
								}
								SmartConfig_4004_SA.this.listSSID.add(sb);
								System.out
										.println("--------------------------------");
								Thread.sleep(SmartConfig_4004_SA.this.date);
								SmartConfig_4004_SA.this.send(sb.toString());
							}

							for (int i = 0; i < 3; ++i) {
								Thread.sleep(SmartConfig_4004_SA.this.date);
								StringBuffer sb = new StringBuffer();
								for (int j = 0; j < 21; ++j)
									sb.append("2");
								SmartConfig_4004_SA.this.send(sb.toString());
							}

							for (int i = 0; i < SmartConfig_4004_SA.this.chPWD.length; ++i) {
								int ascii = SmartConfig_4004_SA.this.chPWD[i];
								StringBuffer sb = new StringBuffer();
								for (int j = 0; j < ascii; ++j) {
									sb.append(SmartConfig_4004_SA.this.chPWD[i]);
								}
								SmartConfig_4004_SA.this.listPWD.add(sb);
								System.out
										.println("--------------------------------");

								Thread.sleep(SmartConfig_4004_SA.this.date);
								SmartConfig_4004_SA.this.send(sb.toString());
							}
							for (int i = 0; i < 3; ++i) {
								Thread.sleep(SmartConfig_4004_SA.this.date);
								StringBuffer sb = new StringBuffer();
								for (int j = 0; j < 22; ++j)
									sb.append("3");
								SmartConfig_4004_SA.this.send(sb.toString());
							}

							String str = "";
							for (int i = 0; i < SmartConfig_4004_SA.this.listSSID
									.size(); ++i) {
								StringBuffer sb = (StringBuffer) SmartConfig_4004_SA.this.listSSID
										.get(i);
								str = str + sb.length() + " ";
							}

							for (int i = 0; i < SmartConfig_4004_SA.this.listPWD.size(); ++i) {
								StringBuffer sb = (StringBuffer) SmartConfig_4004_SA.this.listPWD
										.get(i);
								str = str + sb.length() + " ";
							}

							SmartConfig_4004_SA.this.clear();

							String crc = SmartConfig_4004_SA.this.getCRC(str);
							String max = CRC16.getCRC16Max(crc);
							String min = CRC16.getCRC16Min(crc);

							int maxLen = Integer.parseInt(max, 16) + 20;
							int minLen = Integer.parseInt(min, 16) + 20;
							StringBuffer maxBuffer = new StringBuffer();
							for (int i = 0; i < maxLen; ++i) {
								maxBuffer.append(4);
							}
							Thread.sleep(SmartConfig_4004_SA.this.date);
							SmartConfig_4004_SA.this.send(maxBuffer.toString());

							StringBuffer minBuffer = new StringBuffer();
							for (int i = 0; i < minLen; ++i) {
								minBuffer.append(5);
							}
							Thread.sleep(SmartConfig_4004_SA.this.date);
							SmartConfig_4004_SA.this.send(minBuffer.toString());
						} else {
							SmartConfig_4004_SA.this.sendData();
						}
						++sleep;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.scThread.setDaemon(true);
		this.scThread.start();
	}
	
	public void sendData(String ssid,String password){
		if(chSSID == null)
			chSSID = ssid.toCharArray();
		
		if(chPWD == null)
			chPWD = password.toCharArray();
		
		if (listSSID==null) {
			listSSID=new ArrayList<StringBuffer>();
		}
		if (listPWD==null) {
			listPWD=new ArrayList<StringBuffer>();
		}
		
		try {
			sendData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendData() throws Exception {
		for (int i = 0; i < 3; ++i) {
			Thread.sleep(this.date);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 20; ++j)
				sb.append("1");
			send(sb.toString());
		}

		for (int i = 0; i < this.chSSID.length; ++i) {
			int ascii = this.chSSID[i];
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < ascii; ++j) {
				sb.append(this.chSSID[i]);
			}
			this.listSSID.add(sb);
			System.out.println("--------------------------------");
			Thread.sleep(this.date);
			send(sb.toString());
		}

		for (int i = 0; i < 3; ++i) {
			Thread.sleep(this.date);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 21; ++j)
				sb.append("2");
			send(sb.toString());
		}

		for (int i = 0; i < this.chPWD.length; ++i) {
			int ascii = this.chPWD[i];
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < ascii; ++j) {
				sb.append(this.chPWD[i]);
			}
			this.listPWD.add(sb);
			System.out.println("--------------------------------");

			Thread.sleep(this.date);
			send(sb.toString());
		}
		for (int i = 0; i < 3; ++i) {
			Thread.sleep(this.date);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < 22; ++j)
				sb.append("3");
			send(sb.toString());
		}

		String str = "";
		for (int i = 0; i < this.listSSID.size(); ++i) {
			StringBuffer sb = (StringBuffer) this.listSSID.get(i);
			str = str + sb.length() + " ";
		}

		for (int i = 0; i < this.listPWD.size(); ++i) {
			StringBuffer sb = (StringBuffer) this.listPWD.get(i);
			str = str + sb.length() + " ";
		}

		clear();

		String crc = getCRC(str);
		String max = CRC16.getCRC16Max(crc);
		String min = CRC16.getCRC16Min(crc);

		int maxLen = Integer.parseInt(max, 16) + 20;
		int minLen = Integer.parseInt(min, 16) + 20;
		StringBuffer maxBuffer = new StringBuffer();
		for (int i = 0; i < maxLen; ++i) {
			maxBuffer.append(4);
		}
		Thread.sleep(this.date);
		send(maxBuffer.toString());

		StringBuffer minBuffer = new StringBuffer();
		for (int i = 0; i < minLen; ++i) {
			minBuffer.append(5);
		}
		Thread.sleep(this.date);
		send(minBuffer.toString());
	}

	private boolean send(String string) {
		byte[] makeFirst = string.getBytes();

		return this.cuUdpSocket.send("255.255.255.255", 30000, makeFirst,
				makeFirst.length);
	}

	private void clear() {
		for (int i = 0; i < this.listSSID.size(); ++i) {
			StringBuffer sb = (StringBuffer) this.listSSID.get(i);
			sb.delete(0, sb.length());
		}

		for (int i = 0; i < this.listPWD.size(); ++i) {
			StringBuffer sb = (StringBuffer) this.listPWD.get(i);
			sb.delete(0, sb.length());
		}

		this.listSSID.removeAll(this.listSSID);
		this.listPWD.removeAll(this.listPWD);
	}

	private String getCRC(String str) {
		int crcTen = 0;
		String[] s = str.split(" ");
		byte[] bytes = new byte[s.length];

		for (int j = 0; j < bytes.length; ++j) {
			bytes[j] = Byte.parseByte(s[j]);
		}

		String hexStr = Tools.bytesToHexString(bytes, bytes.length);

		byte[] buf = CRC16.getSendBuf(hexStr);
		String crc = CRC16.getBufHexStr(buf).toLowerCase();
		return crc;
	}

	public void isStop() {
		if (this.scThread != null)
			this.isRun = false;
	}

	public void onReceive(int localPort, String host, int port, byte[] bs,
			int len) {
	}
}
