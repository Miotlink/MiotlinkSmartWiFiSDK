package com.android.miotlink.result;

import java.util.Map;

public interface ConfigResult {
	
	public void resultOk(Map<String, Object> map);
	
	public void resultFail(int failCode, String failReason);

}
