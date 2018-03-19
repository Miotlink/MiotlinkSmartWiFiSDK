package com.miot.android.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class UdpSocket implements Runnable {

	public static String TAG = UdpSocket.class.getName();

	public interface IReceiver {
		public void onReceive(int localPort, String host, int port, byte[] bs,
                              int len);
	}

	private IReceiver receiver = null;

	private DatagramSocket socket = null;

	private Thread thread = null;

	int localPort = 0;

	public boolean startRecv(int port, IReceiver lrs) {
		try {
			needStop = false;
		if (thread != null) {
			Log.e(TAG, "startRecv: has start a thread yet!");
			return false;
		   }
			localPort = port;
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
			receiver = lrs;
			thread = new Thread(this);
			thread.start();
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return false;

	}

	public boolean needStop = false;

	public void stopRecv() {
		needStop = true;
		if (thread != null) {
			try {
				Thread.sleep(1000);
				thread.interrupted();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (socket!=null) {
			socket.disconnect();
//			socket.close();
		}
	}

	private InetAddress address = null;

	public boolean send(String ip, int port, byte[] bs, int len) {
//		Log.i(TAG, "send: " + ip + ":" + port + " [" + new String(bs) + "]");
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		DatagramPacket dPacket = new DatagramPacket(bs, len, address, port);
		try {
			 Tools.lock.acquire();
			 socket.send(dPacket);
			 Tools.lock.release();
		} catch (IOException e) {
			e.printStackTrace();
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
				Tools.lock.acquire();
				socket.receive(packet);
				Tools.lock.release();
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
