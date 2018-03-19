package com.android.miotlink.sdk.util;

import android.util.Log;

public class Utils {

	public static final int HANDLER_ALL_ON = 1; // 全开 handler

	public static final int HANDLER_ALL_OFF = 2; // 全关 handler

	public static final int HANDLER_LEFT_ONE_ON = 3; // 左一开 handler

	public static final int HANDLER_LEFT_TWO_ON = 4; // 左二开 handler

	public static final int HANDLER_LEFT_THREE_ON = 6; // 左三开 handler

	public static final int HANDLER_LEFT_FUOR_ON = 8; // 左四开 handler

	public static final int HANDLER_LEFT_ONE_OFF = 9; // 左一开 handler

	public static final int HANDLER_LEFT_TWO_OFF = 10; // 左二开 handler

	public static final int HANDLER_LEFT_THREE_OFF = 11; // 左三开 handler

	public static final int HANDLER_LEFT_FUOR_OFF = 12; // 左四开 handler

	public static final String ON_OFF_STATE = "01030202"; // 读开关状态 相同

	public static final String READ = "01 03"; // 读

	public static final String WRITE = "01 10"; // 写

	public static final int LOOP_ONE = 1; // 回路1
	public static final int LOOP_TWO = 2; // 回路2
	public static final int LOOP_THREE = 3; // 回路3
	public static final int LOOP_FUOR = 4; // 回路4

	public static final int HANDLER_LOOP_ON = 1; // 回路开
	public static final int HANDLER_LOOP_OFF = 2; // 回路关
	public static final int HANDLER_TIME_FRAME = 3; // 时段开关

	public static final int HANDLER_W_D = -1; // 温度
	public static final int HANDLER_D_L = -2; // 电量
	public static final int HANDLER_G_L = -3; // 功率

	public static final int HANDLER_SEARCH_SOCKET = 1001;

	public static String CMD_SEARCH_SEND = "f1f101";
	public static String CMD_SEARCH_RE  = "f2f201";
	public static String CMD_SETTING_SEND = "f1f102";
	public static String CMD_SETTING_RE = "f2f202";
	public static String CMD_DEV_SEND="f1f1f7";
	public static String CMD_DEV_RE="f2f2f7";
	public static String CMD_UPLOAD_SEND="f1f104";
	public static String CMD_UPLOAD_RE="f2f204";
	
	public static String CMD_START_SEND = "F1 F1";
	public static String CMD_START_RE = "F2 F2";
	public static final String CMD_END_7E = "7E";
	
	public static String checkValue(int function,int dataLen,int data){
		int checkValue = function+dataLen+data;
		String hexCheck = Integer.toHexString(checkValue);
		if(hexCheck.length()%2 !=0){
			hexCheck="0"+hexCheck;
		}
		Log.e("check", hexCheck);
		return hexCheck.substring(hexCheck.length()-2, hexCheck.length());
	}
	
	public static boolean isCheckSuccess(String cmd){
		
		String str1=cmd.substring(4,10);
		String str2= cmd.substring(10, 12);
		
		int data = 0;
		for(int i=0;i<str1.length();i++){
			if(i%2==0){
				String a=str1.substring(i, i+2);
				data+=Integer.parseInt(a, 16);
			}
		}
		
		String hexCheck = Integer.toHexString(data);
		if(hexCheck.length()%2 !=0){
			hexCheck="0"+hexCheck;
		}
		hexCheck = hexCheck.substring(hexCheck.length()-2, hexCheck.length());
		
		if(hexCheck.equals(str2))
			return true;
		else
			return false;
	}

	/**
	 * 获取16进制的时间，分钟在前，小时在后
	 * 
	 * @param time
	 * @return
	 */
	public static String getHexTime(String time) {
		String[] str = time.split(":");
		String h = "", m = "";
		if (str.length == 2) {

			h = Integer.toHexString(Integer.parseInt(str[0]));
			m = Integer.toHexString(Integer.parseInt(str[1]));
			if (h.length() == 1) {
				h = "0" + h;
			}
			if (m.length() == 1) {
				m = "0" + m;
			}

		}

		return m + " " + h;
	}

}
