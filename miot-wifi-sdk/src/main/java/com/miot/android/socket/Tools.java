package com.miot.android.socket;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.miot.android.sdk.MiotLink_4004_Info;

public class Tools implements UdpSocket.IReceiver {

	public static String tag = Tools.class.getName();

	public static boolean isTest = false;

	private Context context;

	public static Tools tool = null;

	public static WifiManager.MulticastLock lock = null;

	public static UdpSocket cuUdpSocket = new UdpSocket();

	public static UdpSocket puUdpSocket = new UdpSocket();

	public static int Search_Pu_Port = 64536;

	public static int Search_Cu_Port = new Random().nextInt(3000) + 30000;

	public static final int SMART_CONNECTED_ACK = 1011;

	public static final int FC_COMPLETE_ACK = 1012;

	public static final int FC_UART_INFO_ACK = 1013;

	public static final int FC_ALL_DATA = 1020;

	public static final int SET_GPIO_ACK = 1021;
	
	public static final int SET_WIWI=1000;

	public static Handler fcUartInfoHandker = null;

	public static Handler fcfirstConfig = null;

	public static Handler smartConnectedHandler = null;

	public static Handler fccompleteHandler = null;

	public static Handler fcAllDataHandler = null;
	
	public static Handler searchHandler=null;
	
	public static Handler setWifiHandler=null;

	public static int configType = 0;
	
	public static Handler getIpHandle=null;
	
	public static void getIPHandler(Handler handler){
		getIpHandle=handler;
	}

	public static void fcUartInfoHandler(Handler handler) {
		fcUartInfoHandker = handler;
	}

	public static void fcSmartConnect(Handler handler) {
		fcfirstConfig = handler;
	}
	
	public static void searchHandler(Handler handler) {
		searchHandler = handler;
	}

	public static void smartConnectedHandler(Handler handler) {
		smartConnectedHandler = handler;
	}

	public static void fcCompleteHandler(Handler handler) {
		fccompleteHandler = handler;
	}

	public static void fcAllDataHandler(Handler handler) {
		fcAllDataHandler = handler;
	}
	
	public static void setWifiDataHandler(Handler handler) {
		setWifiHandler = handler;
	}

	public static Tools initial(Context context, int code) {
		if (tool == null) {
			tool = new Tools();
			tool.context = context;
			WifiManager manager = (WifiManager) tool.context
					.getSystemService(Context.WIFI_SERVICE);
			lock = manager.createMulticastLock("wifi");
			switch (code) {
			case 1:
				cuUdpSocket.startRecv(Search_Cu_Port, tool);
				break;
			case 2:
				cuUdpSocket.startRecv(64535, tool);
				break;
			}
		} else {
			switch (code) {
			case 1:
				cuUdpSocket.startRecv(64535, tool);
				break;
			case 2:
				cuUdpSocket.startRecv(64535, tool);
				break;
			}
		}
		return tool;
	}

	public static int matchStringInArrayDefault0(String[] sa, String s) {
		if (sa == null || s == null) {
			return 0;
		}
		int i = 0;
		for (i = 0; i < sa.length; i++) {
			if (s.equals(sa[i]))
				break;
		}
		return i >= sa.length ? 0 : i;
	}

	
	public static boolean MiotFirst4004_AP_Config(String string) {
		byte[] bs=null;
		String code=string+"&port="+Tools.Search_Cu_Port;
		System.out.println(code);
		try {
			bs = code.getBytes("ISO-8859-1");
			return Tools.sendUdp(Tools.cuUdpSocket, "255.255.255.255",
					Tools.Search_Pu_Port, 1, bs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	public static boolean SmartConnectedAck(String config) {
		try {
			byte[] bs = config.getBytes("ISO-8859-1");
			return Tools.sendUdp(Tools.cuUdpSocket, "255.255.255.255",
					Tools.Search_Pu_Port, 1, bs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	
	private static boolean isgetIp=false;

	public static boolean MiotGetIPAck(String mac) {
		
		String search="CodeName=GetRunNode&Mac="+mac;
		byte[] makeSearch = null;
		try {
			makeSearch = search.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tools.sendUdp(Tools.cuUdpSocket, "255.255.255.255",
				Tools.Search_Pu_Port, 1, makeSearch);
	}

	/**
	 * 加密方式
	 */
	public static byte[] encrypt(byte[] src) {
		for (int i = 8; i < src.length; i++) {
			src[i] ^= src[0];
		}
		return src;
	}

	/**
	 * 发�?lssccmd 内容的解析类
	 * 
	 * @param content
	 * @return
	 */
	public static byte[] formatLsscCmdBuffer(byte[] content) {
		byte[] bs = null;
		try {
			// bsContent = content;
			int packageLen = content.length + 20;
			int contentlen = content.length + 12;
			bs = new byte[packageLen];
			bs[0] = (byte) 0x30;
			bs[1] = (byte) 104;
			bs[2] = (byte) (packageLen >> 8 & 0xff);// (packLen/256); // //
			bs[3] = (byte) (packageLen >> 0 & 0xff);// (packLen%256); // //
			bs[4] = (byte) (0 >> 24 & 0xff);
			bs[5] = (byte) (0 >> 16 & 0xff);
			bs[6] = (byte) (0 >> 8 & 0xff);
			bs[7] = (byte) (0 >> 0 & 0xff);
			bs[8] = (byte) 0x65;
			bs[9] = (byte) 0;
			bs[10] = (byte) (contentlen / 256);
			bs[11] = (byte) (contentlen % 256);
			bs[12] = (byte) (1 >> 24 & 0xff);
			bs[13] = (byte) (1 >> 16 & 0xff);
			bs[14] = (byte) (1 >> 8 & 0xff);
			bs[15] = (byte) (1 >> 0 & 0xff);
			bs[16] = (byte) (0 >> 24 & 0xff);
			bs[17] = (byte) (0 >> 16 & 0xff);
			bs[18] = (byte) (0 >> 8 & 0xff);
			bs[19] = (byte) (0 >> 0 & 0xff);
			System.arraycopy(content, 0, bs, 20, content.length);
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[] {};
		}
		return encrypt(bs);
	}

	public static boolean sendUdp(UdpSocket udpSocket, String ip, int port,
			int code, byte[] content) {
		boolean success = false;
		try {
			byte[] bs = formatLsscCmdBuffer(content);
			success = udpSocket.send(ip, port, bs, bs.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;

	}

	public static String Charset = "ISO-8859-1";

	public static String getMlccContent(byte[] bs, int len) {
		try {
			if (bs == null || len < 20) {
				return null;
			}
			return new String(bs, 20, len - 20, Charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String bytesToHexString(byte[] src, int len) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0 || len > src.length) {
			return null;
		}
		for (int i = 0; i < len; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 没有加密类型
	 * 
	 * @param version
	 * @param code
	 * @param content
	 * @return
	 */

	@Override
	public void onReceive(int localPort, String host, int port, byte[] src,
			int len) {
		try {
			byte[] bs = encrypt(src);
			String msg = Tools.getMlccContent(bs, len);
			Log.e(tag, "onReceive: " + localPort + "<=" + host + ":" + port
					+ " [" + msg + "]");
			Message message = new Message();
			if (localPort == 64535) {
				if (msg.split("&")[0] == null || msg.split("&")[0].equals("")
						|| !msg.split("&")[0].split("=")[0].equals("CodeName")) {
					return;
				}
				
				if (msg.split("&")[0].split("=")[1].equals("smart_connected")) {
					message.what = MiotLink_4004_Info.MIOTLINK_RESULT_MAC;
					message.obj = msg.split("&")[1].split("=")[1].toString();
					if (smartConnectedHandler != null) {
						smartConnectedHandler.sendMessage(message);
					}
				} else if (msg.split("&")[0].split("=")[1]
						.equals("fc_complete_ack")) {//配置结束信息返回
					message.what = MiotLink_4004_Info.MIOTLINK_RESULT_COMPLETE;
					message.obj = msg;
					if (fccompleteHandler != null) {
						fccompleteHandler.sendMessage(message);
					}
				} else if(msg.split("&")[0].split("=")[1]
						.equals("GetRunNodeAck")){
					message.what = MiotLink_4004_Info.MIOTLINK_RESULT_IP;
					message.obj = host;
					if (searchHandler != null) {
						searchHandler.sendMessage(message);
					}
				}
				else {
					message.what = MiotLink_4004_Info.MIOTLINK_RESULT_ALL_DATA;
					message.obj = msg;
					if (fcAllDataHandler != null)
						fcAllDataHandler.sendMessage(message);
				}
			}else if (localPort==Search_Cu_Port) {
				 if (msg.split("&")[0].split("=")[1]
						.equals("SetWifiAck")) {
					message.what = MiotLink_4004_Info.MIOTLINK_RESULT_HANFENG;
					message.obj = msg;
					if (setWifiHandler != null) {
						setWifiHandler.sendMessage(message);
					}
				}
			}
		} catch (Exception e) {

		}

	}
	
	public static void stopSocket(){
		cuUdpSocket.stopRecv();
	}

}
