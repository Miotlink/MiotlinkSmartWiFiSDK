package com.android.miotlink.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.android.miotlink.result.ConfigResult;
import com.android.miotlink.sdk.entity.FirstData;
import com.android.miotlink.sdk.smart.SmartConfigAndMulticase;
import com.android.miotlink.sdk.smart.SmartConfigAndSmartConfigMulticase;
import com.android.miotlink.sdk.socket.Tools;
import com.android.miotlink.sdk.util.JsonUtil;
import com.android.miotlink.sdk.util.MacUtil;
import com.android.miotlink.sdk.util.Mlcc_ParseUtils;
import com.android.miotlink.sdk.util.NoFormatConsts;
import com.mediatek.elian.ElianNative;

public class MiotSmartConfig {

	private static final int TIMEOUT = 1080;

	public static MiotSmartConfig instance = null;

	private Context context = null;

	private ConfigResult configResult = null;

	private int failCode = 0;

	private String configAck = "";

	List<FirstData> firstDatas = null;

	private boolean isRuning = false;

	private int type = 0;

	private SmartConfigAndMulticase smartConfigAndMulticase = null;

	private SmartConfigAndSmartConfigMulticase smart_4004_Config = null;

	private boolean isPlatformConfig = false;

	private boolean isComplete = false;

	private boolean isFinish = false;

	private int index = 0;

	private MyThread thread = null;

	private String mac = "";
	
	private int time=180;
	
	private Map<String, Object> mapValue=null;

	private ElianNative elian=null;

	public static synchronized MiotSmartConfig getInstance(Context context) {
		if (instance == null) {
			synchronized (MiotSmartConfig.class) {
				if (instance==null) {
					instance = new MiotSmartConfig(context);
				}
			}
		}
		return instance;
	} 
	
	/**
	 * 目前没有APPid 暂时不用初始化设置
	 * @param Appid
	 */
	public void init(String Appid){
		
	}
	/**
	 * 设置平台方式
	 * @param type 1 测试服务器 2 正式服务器 3 美国服务器
	 * @param ip
	 */
	public void setPlatformAddress(int type,String ip){
		try {
			NoFormatConsts.setPlatformAddress(type,ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 设置喵喵屋 使用的配置方式
	 * @param type
	 * @param routeName
	 * @param password
	 * @param firstDatas
	 */
	public void smartConfig(int type, String routeName, String password,
			List<FirstData> firstDatas) {
		this.type = type;
		if (firstDatas != null) {
			this.firstDatas = firstDatas;
		}else{
			firstDatas=NoFormatConsts.getInstance();
		}
		switch (type) {
		case 1:
			smartConfigAndMulticase = SmartConfigAndMulticase
					.getInstance(context);
			smartConfigAndMulticase.sendData(routeName, password);

			break;
		case 2:
			boolean result = ElianNative.LoadLib();
			if (!result) {
				return;
			}
			elian = new ElianNative();
			elian.InitSmartConnection("", 1, 0);
			elian.StartSmartConnection(routeName, password, "");
			break;
		case 4:
			smart_4004_Config = SmartConfigAndSmartConfigMulticase.getInstance(context);
			smart_4004_Config.sendData(routeName, password);
			break;
		}
		failCode = 1011;
		initTools();
		thread = new MyThread();
		thread.start();
	}
	
	/**
	 * 设置喵喵屋 使用的配置方式
	 * @param type 
	 * @param routeName
	 * @param Password
	 * @param qrcode 二维码后获取的json数据
	 */
	public void smartConfig(int type, String routeName, String Password,
			String qrcode,int time) {
		this.type = type;
		if (time!=0) {
			this.time=time;
		}
		if (qrcode.equals("")) {
			System.out.println("配置信息为空");
			return;
		}
		this.firstDatas =JsonUtil.getInstance(qrcode);
		switch (type) {
		case 1:
			smartConfigAndMulticase = SmartConfigAndMulticase
					.getInstance(context);
			smartConfigAndMulticase.sendData(routeName, Password);

			break;
		case 2:
			boolean result = ElianNative.LoadLib();
			if (!result) {
				return;
			}
			elian = new ElianNative();
			elian.InitSmartConnection("", 1, 0);
			elian.StartSmartConnection(routeName, Password, "");
			break;
		case 4:
			smart_4004_Config = SmartConfigAndSmartConfigMulticase.getInstance(context);
			smart_4004_Config.sendData(routeName, Password);
			break;
		}
		failCode = 1011;
		configAck="检查路由器密码是否正确或者设备是否处于还原状态";
		initTools();
		thread = new MyThread();
		thread.start();
	}
	
	public void smartConfig(int type, String routeName, String Password,int time) {
		this.type = type;
		if (time!=0) {
			this.time=time;
		}
		if (firstDatas==null) {
			firstDatas=NoFormatConsts.getInstance();
		}
		switch (type) {
		case 1:
			smartConfigAndMulticase = SmartConfigAndMulticase
					.getInstance(context);
			smartConfigAndMulticase.sendData(routeName, Password);

			break;
		case 2:
			boolean result = ElianNative.LoadLib();
			if (!result) {
				return;
			}
			elian = new ElianNative();
			elian.InitSmartConnection("", 1, 0);
			elian.StartSmartConnection(routeName, Password, "");
			break;
		case 4:
			smart_4004_Config = SmartConfigAndSmartConfigMulticase.getInstance(context);
			smart_4004_Config.sendData(routeName, Password);
			break;
		}
		failCode = 1011;
		initTools();
		thread = new MyThread();
		thread.start();
		configAck="检查路由器密码是否正确或者设备是否处于还原状态";
	}
	
	/**
	 * 设置 客户平台调用方式
	 * @param modle 2,4
	 * @param plartform 客户平台的url
	 * @param port  客户平台的 端口
	 * @param uart  配置 uart 信息
	 */
	public void setPlatform(int modle,String plartform,String port,String uart){
		firstDatas=NoFormatConsts.getPlatform(modle, plartform, port, uart);
		if (firstDatas==null) {
			firstDatas=new ArrayList<FirstData>();
		}
	}
	
	/**
	 * 默认为自己的平台
	 * @param uart 可以设置uart信息
	 */
	public void setPlatform(String uart){
		firstDatas=NoFormatConsts.getInstance(uart);
		if (firstDatas==null) {
			firstDatas=new ArrayList<FirstData>();
		}
	}
	private void initTools() {
		isRuning = true;
		isPlatformConfig = false;
		isComplete = false;
		isFinish = false;
		mac="";
		index = 0;
		fcResult = "";
		lastResult = "";
		Tools.initial(context, 2);
		Tools.fcAllDataHandler(handler);
		Tools.fcSmartConnect(handler); 
		Tools.fcCompleteHandler(handler);
	}

	private MiotSmartConfig(Context context) {
		this.context = context;
	}

	public void setConfigResult(ConfigResult configResult) {
		this.configResult = configResult;
	}

	private String fcResult = "";
	private String lastResult = "";
	Handler handler = new Handler() {
		@SuppressLint("DefaultLocale")
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Tools.FC_ALL_DATA:
				fcResult = msg.obj.toString().split("&")[0].split("=")[1]
						.toString();
				if (fcResult.equals("") || fcResult == null) {
					return;
				}
				FirstData firstData = firstDatas.get(index);
				if (firstData == null) {
					return;
				}
				if (!fcResult.equals(lastResult) || lastResult.equals("")) {
					if (fcResult.equals(firstData.getContentAck_CodeName())) {
						if ((1 + index) == firstDatas.size()) {
							isPlatformConfig = false;
							isComplete = true;
							return;
						}
						index++;
						failCode = 2;
						lastResult = fcResult;

					}
				}

				break;
			case Tools.FC_COMPLETE_ACK:
				@SuppressWarnings("unchecked")
				Map<String, String> finishAck = (HashMap<String, String>) msg.obj;
				if (finishAck != null) {
					isComplete = false;
					isFinish = true;
					if (mapValue==null) {
						mapValue = new HashMap<String, Object>();
						mapValue.put("mac", mac);
					}
					if (configResult!=null) {
						configResult.resultOk(mapValue);
					}
					stopSmartConfig();
				}

				break;
			case Tools.FC_FIRST_CONFIG:
				@SuppressWarnings("unchecked")
				Map<String, String> smced = (HashMap<String, String>) msg.obj;
				if (smced != null) {
					if (!mac.equals("")) {
						return;
					}
					mapValue=Mlcc_ParseUtils.getValue(smced);
					mac = smced.get("mac").toUpperCase().toString();
					if (!MacUtil.isMacAddress(mac)) {
						failCode = 1012;
						configAck="mac Address format is  error";
						configResult.resultFail(failCode, configAck);
						stopSmartConfig();
						return;
					}
					isStop();
					if(firstDatas==null){
						failCode=1015;
						configAck=" params is error";
						return;
					}
					isPlatformConfig = true;
				}
				break;
			case TIMEOUT:
				if(configResult!=null)
				configResult.resultFail(failCode, configAck);
				stopSmartConfig();
				break;
			}
		};
	};

	class MyThread extends Thread {
		int count = 0;
		@Override
		public void run() {
			count=0;
			while (isRuning) {
				try {
					if (count >time) {
						handler.sendEmptyMessage(TIMEOUT);
						return;
					}
					if (isPlatformConfig) {
						failCode = 1013;
						FirstData firstData = (FirstData) firstDatas.get(index);
						if (firstData != null) {
							if (firstData.getIndex().equals((index + 1) + "")) {
								configAck = firstData.getContentAck_CodeName()+"is not callback";
								String string = firstData.getContent().replace(
										"&amp", "&");
								Tools.SmartConnectedAck(string + "mac=" + mac);
							}
						}
					}
					if (isComplete) {
						configAck = "completeAck is not callback";
						Map<String, String> map = new HashMap<String, String>();
						map.put("mac", mac);
						Tools.Fc_completeAck(map);
					}
					if (isFinish) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("mac", mac);
						Tools.Fc_completeFinAck(map);
						isFinish = false;
					}
					count++;
					sleep(1000);
				} catch (Exception e) {
				}
			}
			super.run();
		}
	}

	private void isStop() {
		if (smartConfigAndMulticase != null) {
			smartConfigAndMulticase.isStop();
		}
		if (elian != null) {
			elian.StopSmartConnection();
		}
		if (smart_4004_Config != null) {
			smart_4004_Config.isStop();
		}
	}

	public void stopSmartConfig() {
		isRuning = false;
		if (thread != null) {
			isComplete = false;
			isPlatformConfig = false;
			isFinish = false;
			thread.interrupt();
			thread = null;
		}
		if (smartConfigAndMulticase != null) {
			smartConfigAndMulticase.isStop();
		}
		if (elian != null) {
			elian.StopSmartConnection();
		}
		if (smart_4004_Config != null) {
			smart_4004_Config.isStop();
		}
		Tools.fcAllDataHandler(null);
		Tools.fcSmartConnect(null);
		Tools.fcCompleteHandler(null);
	}

}
