package com.android.miotlink.sdk.smart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.android.miotlink.sdk.util.CRC16;
import com.android.miotlink.sdk.util.HexString;

public class SmartConfig implements UdpSmartSocket.IReceiver{
	
	private Context context;
	
	private UdpSmartSocket cuUdpSocket=null;
	
	private Thread scThread=null;
	
	private int date=3;
	
	private char[] chSSID;
	private char[] chPWD;
	private List<StringBuffer> listSSID=null;
	private List<StringBuffer> listPWD=null;
	
	private boolean isRun=true;
	
	
	public boolean isRun() {
		return isRun;
	}



	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	private WifiManager wifiManager=null;
	
	public  WifiManager.MulticastLock lock = null;
	
	public SmartConfig(Context context){
		this.context=context;
		wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		lock = wifiManager.createMulticastLock("wifi");
		cuUdpSocket=new UdpSmartSocket(new Random().nextInt(3000) + 20000);
	}
	
	

	public void isSmartConfig(String ssid,String password){
		chSSID = ssid.toCharArray();
		chPWD = password.toCharArray();
		if (listSSID==null) {
			listSSID=new ArrayList<StringBuffer>();
		}
		if (listPWD==null) {
			listPWD=new ArrayList<StringBuffer>();
		}
		scThread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					int sleep=0;
					while (isRun) {
						if (sleep==2) {
							Thread.sleep(500);
							sleep=0;
							for (int i = 0; i < 3; i++) {
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
								System.out.println(sb.length());
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
								System.out.println(sb.length());
								
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
							
							// crc校验
							//-------------------crc校验-------------------------//	
							String crc = getCRC(str);
							String max = CRC16.getCRC16Max(crc);
							String min = CRC16.getCRC16Min(crc);
//						Log.e("crc", "max:"+max+ "   min:"+min);
							int maxLen = Integer.parseInt(max, 16)+20;
							int minLen = Integer.parseInt(min,16)+20;
							StringBuffer maxBuffer = new StringBuffer();
							for(int i=0;i<maxLen;i++){
								maxBuffer.append(4);
							}
							Thread.sleep(date);
							send(maxBuffer.toString());
							
							StringBuffer minBuffer = new StringBuffer();
							for(int i=0;i<minLen;i++){
								minBuffer.append(5);
							}
							Thread.sleep(date);
							send(minBuffer.toString());
						}else{
							sendData();
						}
						sleep++;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		scThread.setDaemon(true);
		scThread.start();

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
	
	private void sendData()throws Exception{

		for (int i = 0; i < 3; i++) {
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
			System.out.println(sb.length());
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
			System.out.println(sb.length());
			
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
		
		// crc校验
//	for (int i = 0; i < 3; i++) {
//
//		StringBuffer sb = new StringBuffer();
//		int len = getCRC(str);
//		for (int j = 0; j < len; j++) {
//			sb.append(4);
//		}
//
//		Thread.sleep(date);
//		send(sb.toString());
//	}
		//-------------------crc校验-------------------------//	
		String crc = getCRC(str);
		String max = CRC16.getCRC16Max(crc);
		String min = CRC16.getCRC16Min(crc);
//	Log.e("crc", "max:"+max+ "   min:"+min);
		int maxLen = Integer.parseInt(max, 16)+20;
		int minLen = Integer.parseInt(min,16)+20;
		StringBuffer maxBuffer = new StringBuffer();
		for(int i=0;i<maxLen;i++){
			maxBuffer.append(4);
		}
		Thread.sleep(date);
		send(maxBuffer.toString());
		
		StringBuffer minBuffer = new StringBuffer();
		for(int i=0;i<minLen;i++){
			minBuffer.append(5);
		}
		Thread.sleep(date);
		send(minBuffer.toString());
	
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
	
	
	private String getCRC(String str){
		int crcTen = 0;
		String[] s = str.split(" ");
		byte[] bytes = new byte[s.length];

		for (int j = 0; j < bytes.length; j++) {

			bytes[j] = Byte.parseByte(s[j]);
		}

		String hexStr = HexString.bytesToHexString(bytes, bytes.length);

		byte[] buf = CRC16.getSendBuf(hexStr);
		String crc = CRC16.getBufHexStr(buf).toLowerCase();
		return crc;
		
	}
	
	public void isStop(){
		if (scThread!=null) {
			isRun=false;
			scThread.interrupt();
			scThread=null;
		}
	}

	@Override
	public void onReceive(int localPort, String host, int port, byte[] bs,
			int len) {
		// TODO Auto-generated method stub
		
	}


}
