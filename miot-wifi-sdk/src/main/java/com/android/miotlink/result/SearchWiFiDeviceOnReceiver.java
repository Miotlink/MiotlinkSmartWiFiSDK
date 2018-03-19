package com.android.miotlink.result;

import java.util.List;
import java.util.Map;

import com.android.miotlink.sdk.entity.DeviceInfo;
/**
 * 
 * @author Administ
 *
 */
public interface SearchWiFiDeviceOnReceiver {
	/**
	 *
	 * @param error  error code
	 * @param errorMessage
	 * @param maps result
	 */
	public void searchAllWiFiDeviceOnReceiver(int error,String errorMessage,List<Map<String,String>> maps);

	public void searchDeviceWiFiByMac(int error,String errorMessage,Map<String,String> map);

}
