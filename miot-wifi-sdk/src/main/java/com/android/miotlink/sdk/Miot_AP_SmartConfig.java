package com.android.miotlink.sdk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import com.android.miotlink.receiver.MiotWiFiCallback;
import com.android.miotlink.receiver.NetworkBroadcastReceiver;
import com.android.miotlink.result.ConfigResult;
import com.android.miotlink.sdk.entity.FirstData;
import com.android.miotlink.sdk.socket.MiotlinkTools;
import com.android.miotlink.sdk.util.JsonUtil;
import com.android.miotlink.sdk.util.NoFormatConsts;
import com.android.miotlink.sdk.util.WifiAdmin;

/**
 * 用于杭州妙联设备热点为Miotlink_AP_XXXX配置方式
 * @author QIAOZHUANG
 *
 */
public class Miot_AP_SmartConfig implements MiotWiFiCallback{
	
	private static Miot_AP_SmartConfig instance=null;

	private Context context;

	private WifiAdmin wifiAdmin=null;

	private String route="";

	private String password="";

	private String miotlink_ap="";

	private boolean isRuning=true;

	private boolean isPlatform=false;

	private int index=0;

	private int failCode=1001;

	private String configAck="";

	private String mac="";

	private boolean isRouteCheck=false;

	private List<FirstData> firstDatas=null;

	private String fcResult="";
	
	private String lastResult="";

	/**
	 * 单例模式 Miot_AP_SmartConfig.getInstance(Context)
	 * @param context 上线文对象
	 */
	public static Miot_AP_SmartConfig getInstance(Context context) {
		if (instance==null) {
			synchronized (Miot_AP_SmartConfig.class) {
				if(instance==null){
					instance = new Miot_AP_SmartConfig(context);
				}
			}
		}
		return instance;
	}


	public Miot_AP_SmartConfig(Context context){
		this.context=context;
		wifiAdmin=new WifiAdmin(context);

	}
	private ConfigResult callBack;

	/**
	 * 设置回到函数MiotSmartConfigResult 一定要实现不然看不到回调
	 * @param callBack
	 */
	public void setCallBack(ConfigResult callBack){
		this.callBack=callBack;
	}


	/**
	 * 用于ap切换的妙联平台的方法  
	 * @param route 路由器账户
	 * @param password 路由器密码
	 * @param miotlink_ap 设备的SSID
	 * @param mac 设备的BSSID 既设备的MAC地址
	 * @param json 通过二维码请求妙联平台返回的结果
	 * @return ture 成功  false 失败 检查传入参数是否正确 
	 */
	public boolean isSmartConfig(String route,String password,String miotlink_ap,String mac,String json){
		initData();
		wifiAdmin.startScan();
		this.route=route;
		this.password=password;
		this.miotlink_ap=miotlink_ap;
		this.mac=mac;
		if (json.equals("")){
			return false;
		}
		firstDatas= JsonUtil.getInstance(json);
		if (firstDatas.size()<=0){
			return false;
		}
		wifiAdmin.addNetwork(wifiAdmin.createWifiInfo(miotlink_ap, "", 0));
		myThread=new MyThread();
		myThread.start();
		return true;
	}
	
	
	/**
	 * 用于ap切换的妙联平台的通用方法  默认妙联平台 串口数据为9600
	 * @param route 路由器账户
	 * @param password 路由器密码
	 * @param miotlink_ap 设备的SSID
	 * @param mac 设备的BSSID 既设备的MAC地址
	 * 
	 * @return ture 成功  false 失败 检查传入参数是否正确 
	 */
	public boolean isSmartConfig(String route,String password,String miotlink_ap,String mac){
		initData();
		wifiAdmin.startScan();
		this.route=route;
		this.password=password;
		this.miotlink_ap=miotlink_ap;
		this.mac=mac;
		if (firstDatas==null) {
			firstDatas= NoFormatConsts.getHfInit();
		}
		wifiAdmin.addNetwork(wifiAdmin.createWifiInfo(miotlink_ap, "", 0));
		myThread=new MyThread();
		myThread.start();
		return true;
	}
	
	/**
	 * @param mode 2 TCP客户平台 3 UDP客户平台 
	 * @param platfrom 设置平台地址 不设置默认为妙联平台
	 * @param port 设置平台端口 不设置默认为妙联平台端口
	 * @param uart 设置UART数据 不设置默认为9600 
	 */
	public void setPlatform(int mode,String platfrom,String port,String uart){
		firstDatas=NoFormatConsts.getMiotlinkPlatform(mode,platfrom, port, uart);
	}

	private MyThread myThread=null;

	private void initData(){
		NetworkBroadcastReceiver.callback=this;
		MiotlinkTools.initial(context, 1);
		MiotlinkTools.setWifiHandler(handler);
		MiotlinkTools.fcAllDataHandler(handler);
		isRuning=true;
	    isPlatform=false;
		index=0;
		failCode=1001;
		configAck="";
		mac="";
		isRouteCheck=false;
		fcResult="";
		lastResult="";
	}

	@Override
	public void isCheck(boolean isWiFI, boolean isMoble, String ssid) {
		if (isWiFI){
			if (ssid.equals(miotlink_ap)){
				failCode=1002;
				isPlatform=true;
			}else {
				if (!isRouteCheck){
					isRouteCheck=true;
					handler.sendEmptyMessage(5050);
				}
			}
		}
	}

	class MyThread extends Thread {
		int count = 0;
		int time = 3000;
		@SuppressLint("DefaultLocale") 
		@Override
		public void run() {
			try {
				while (isRuning) {
					if (count >= 50) {
						handler.sendEmptyMessage(1050);
						return;
					}
					if (isPlatform) {
						failCode = 1003;
						FirstData firstData = firstDatas.get(index);
						if (firstData != null) {
							String string = firstData.getContent().replace("&amp", "&");

							String[] codeNames = string.split("&");
							if (!codeNames[0].equals("CodeName=SetWifi")) {
								MiotlinkTools.MiotFirst4004_AP_Config(string + "&Mac="
										+ mac.toUpperCase());
								configAck = firstData.getContentAck_CodeName();
							} else {
								String code = "&Mode=2&Mac=" + mac.toUpperCase() + "&ByName=" + miotlink_ap
										+ "&ApId=" + miotlink_ap + "&StaId=" + route
										+ "&StaPd=" + password;
								MiotlinkTools.MiotFirst4004_AP_Config(string + code);
								configAck = firstData.getContentAck_CodeName();
							}
						}
					}
					count++;
					sleep(time);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
			super.run();
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case MiotlinkTools.MW_WIFI_CONFIG_ACK:
					@SuppressWarnings("unchecked")
					Map<String, String> map = (HashMap<String, String>) msg.obj;
					isPlatform = false;
					isRouteCheck=false;
					failCode=1004;
					handler.sendEmptyMessage(5011);
					break;

				case MiotlinkTools.FC_ALL_DATA:
					fcResult = msg.obj.toString().split("&")[0].split("=")[1]
							.toString();
					if (fcResult.equals("") || fcResult == null) {
						return;
					} else {
						FirstData firstData = firstDatas.get(index);
						if (fcResult == null) {
							return;
						} else {
							if (!fcResult.equals(lastResult)
									|| lastResult.equals("")) {
								if (fcResult.equals(firstData
										.getContentAck_CodeName())) {
									if ((1 + index) == firstDatas.size()) {
										isPlatform = false;

									} else {
										index++;
										lastResult = fcResult;
									}
								}
							}
						}
					}
					break;
				case 5011:
					connectWifiState();
					break;
				case 5050:
					Map<String,Object> mapValue=new HashMap<String,Object>();
					mapValue.put("type","ap");
					mapValue.put("mac",mac);
					if (callBack!=null){
						callBack.resultOk(mapValue);
					}
					
					stopSmartConfig();
					break;
				case 1050:
					if (callBack!=null){
						if (failCode==1004) {
							callBack.resultFail(1005, "切换WIFI路由器失败,请手动切换后添加设备");
							return;
						}
						callBack.resultFail(failCode, configAck);
					}
					break;

			}
		};
	};
	private void connectWifiState() {
		wifiAdmin.connectConfiguration(wifiAdmin
				.getConfigurationIndexBySSID(route));
	}

	public void stopSmartConfig(){
		instance=null;
		NetworkBroadcastReceiver.callback=null;
		if (isRuning){
			isRuning=false;
		}
		if (myThread!=null){
			myThread.interrupt();
			myThread=null;
		}
	}

}



