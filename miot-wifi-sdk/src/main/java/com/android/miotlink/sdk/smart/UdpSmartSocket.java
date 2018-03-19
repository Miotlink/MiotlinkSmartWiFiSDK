package com.android.miotlink.sdk.smart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class UdpSmartSocket implements Runnable {

	public static String TAG = UdpSmartSocket.class.getName();

	public interface IReceiver {
		public void onReceive(int localPort, String host, int port, byte[] bs,
                              int len);
	}

	private IReceiver receiver = null;

	private DatagramSocket socket = null;

	private Thread thread = null;

	int localPort = 0;
	
	
	public UdpSmartSocket(int port){
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public boolean startRecv(int port, IReceiver lrs) {
		if (thread != null) {
			Log.e(TAG, "startRecv: has start a thread yet!");
			return false;
		}
		try {
			localPort = port;
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
			receiver = lrs;
			thread = new Thread(this);
			thread.start();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public boolean needStop = false;

//	private void stopRecv() {
//		if (thread != null) {
//			needStop = true;
//			try {
//				Thread.sleep(1000);
//				thread.stop();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	private InetAddress address = null;

	private DatagramPacket dPacket = null;

	public boolean send(String ip, int port, byte[] bs, int len) {
		try {
			address = InetAddress.getByName(ip);
			Log.d(TAG, "send: 已找到服务器,连接中...");
		} catch (UnknownHostException e) {
			Log.e(TAG, "send: 未找到服务器.");
			e.printStackTrace();
			return false;
		}
		dPacket = new DatagramPacket(bs, len, address, port);
		try {
			 socket.send(dPacket);
			Log.d(TAG, "send: 消息发送成功!");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "send: 消息发送失败.");
			return false;
		}
		return true;

	}

	@Override
	public void run() {
		if (socket == null || receiver == null) {
			Log.e(TAG, "run: socket and receiver should not be null!");
			return;
		}
		while (!needStop) {
			byte data[] = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
				String msg = new String(packet.getData(), packet.getOffset(),
						packet.getLength());

				byte[] bs = new byte[packet.getLength()];

				System.arraycopy(packet.getData(), packet.getOffset(), bs, 0,
						packet.getLength());

				String host = packet.getAddress().getHostAddress();
				int port = packet.getPort();
				Log.e(TAG,
						"run: recv " + host + ":" + port + " [" + msg.length()
								+ "]");
				receiver.onReceive(localPort, host, port, bs, bs.length);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

}
