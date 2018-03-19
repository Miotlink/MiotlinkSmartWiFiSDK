package com.android.miotlink.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.android.miotlink.result.SearchWiFiDeviceOnReceiver;
import com.android.miotlink.sdk.entity.DeviceInfo;
import com.android.miotlink.sdk.socket.MiotlinkTools;
import com.miot.commom.network.mlcc.agent.Parse;
import com.miot.common.network.mlcc.pojo.response.RespSearchAck;

/**
 * 搜索设备方法 根据mac 地址搜索 
 * @author 
 *
 */
public class MiotSearchWiFiDevice {


	private Map<String,String> value=null;

	private String mac = "";

	private boolean isRuning = true;

	private SearchWiFiDeviceOnReceiver searchResult = null;

	private Context context;

	public static MiotSearchWiFiDevice instance = null;
	
	private int searchType=0;

	private MyThread thread=null;

	public static synchronized MiotSearchWiFiDevice getInstance(Context context) {
		if (instance == null) {
			synchronized (MiotSearchWiFiDevice.class){
				if (instance==null){
					instance = new MiotSearchWiFiDevice(context);
				}
			}

		}
		return instance;
	}

	public MiotSearchWiFiDevice(Context context) {
		this.context = context;
	}

	public void setWiFiDeviceOnReceiver(SearchWiFiDeviceOnReceiver searchResult) {
		this.searchResult = searchResult;
	}

	/**
	 * According to the MAC search Device
	 * 
	 * @param mac
	 */
	public void searchWiFiDeviceByMac(String mac) {
		this.mac=mac;
		MiotlinkTools.initial(context, 1);
		MiotlinkTools.setSearcHandler(handler);

		isRuning=true;
		this.searchType=1;

		thread=new MyThread();
		thread.start();

	}

	/**
	 * Search the router for all WIFI devices.
	 */
	public void searchAllWiFiDevice() {
		this.searchType=2;
		list=new ArrayList<>();
		MiotlinkTools.initial(context, 1);
		MiotlinkTools.setSearcHandler(handler);
		isRuning=true;
		thread=new MyThread();
		thread.start();
	}


	class MyThread extends Thread {
		int count=0;
		@Override
		public void run() {
			MiotlinkTools.MiotSearch();
			while (isRuning) {
				try {
					sleep(1500);
					if (count>3){
						isRuning = false;
						count=0;
						handler.sendEmptyMessage(1005);
					}
					MiotlinkTools.MiotSearch();
					count++;
				} catch (Exception e) {
				}
			}
			super.run();
		}
	}
	

	private RespSearchAck respSearchAck;
	private List<Map<String,String>> list=null;
	@SuppressWarnings("unchecked")
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MiotlinkTools.MW_SET_SEARCH_ACK:
				Map<String, String> map = (Map<String, String>) msg.obj;
				try {
					respSearchAck = (RespSearchAck) Parse.parseMLCCPackage(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (searchType==1){
					if(!TextUtils.isEmpty(mac)){
						if (respSearchAck.getMac().equals(mac)) {
							value=map;
						}
					}
					return;
				}
				if (!list.contains(map)){
					list.add(map);
				}

				break;
			case 1005:
				if (searchType==1&&searchResult!=null) {
					if (value==null) {
						searchResult.searchDeviceWiFiByMac(140001,"time out",null);
						return;
					}
				}
				if (searchType==2&&searchResult!=null) {
					if (list==null&&list.size()==0) {
						searchResult.searchAllWiFiDeviceOnReceiver(140001,"time out",null);
						return;
					}
					searchResult.searchAllWiFiDeviceOnReceiver(1,"success",list);
				}
				
				break;

			}
		};
	};

	public void onDestory()throws Exception{
		if (isRuning){
			isRuning=false;
		}
		if (thread!=null){
			thread.interrupt();
			thread=null;
		}

	}

}
