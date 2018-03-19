package com.miot.android.smart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class UdpSmartSocket implements Runnable {
	public static String TAG = UdpSmartSocket.class.getName();

	private IReceiver receiver = null;

	private DatagramSocket socket = null;

	private Thread thread = null;

	int localPort = 0;

	public boolean needStop = false;

	private InetAddress address = null;

	private DatagramSocket dSocket = null;

	private DatagramPacket dPacket = null;

	public UdpSmartSocket(int port) {
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public boolean startRecv(int port, IReceiver lrs) {
		if (this.thread != null) {
			Log.e(TAG, "startRecv: has start a thread yet!");
			return false;
		}
		try {
			this.localPort = port;
			this.socket = new DatagramSocket(port);
			this.socket.setBroadcast(true);
			this.receiver = lrs;
			this.thread = new Thread(this);
			this.thread.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void stopRecv() {
		if (this.thread != null) {
			this.needStop = true;
			try {
				Thread.sleep(1000L);
				this.thread.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean send(String ip, int port, byte[] bs, int len) {
		try {
			this.address = InetAddress.getByName(ip);
			Log.d(TAG, "send: 已找到服务器,连接�?..");
		} catch (UnknownHostException e) {
			Log.e(TAG, "send: 未找到服务器.");
			e.printStackTrace();
			return false;
		}

		logSendData(bs);
		this.dPacket = new DatagramPacket(bs, len, this.address, port);
		try {
			this.socket.send(this.dPacket);
			Log.d(TAG, "send: 消息发�?成功!");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "send: 消息发�?失败.");
			return false;
		}

		return true;
	}

	private void logSendData(byte[] bs) {
		String data = "";
		for (int i = 0; i < bs.length; ++i) {
			data = data + bs[i] + " ";
		}
		System.out.println(data);
	}

	public void run() {
		if ((this.socket == null) || (this.receiver == null)) {
			Log.e(TAG, "run: socket and receiver should not be null!");
			return;
		}
		do {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				this.socket.receive(packet);
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
				this.receiver.onReceive(this.localPort, host, port, bs,
						bs.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!this.needStop);
	}

	public static abstract interface IReceiver {
		public abstract void onReceive(int paramInt1, String paramString,
                                       int paramInt2, byte[] paramArrayOfByte, int paramInt3);
	}
}