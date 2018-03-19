package com.miot.android.sdk;

public abstract interface MiotlinkResult {
	public void Success(String paramString);

	public void ConfigFail();

	public void MiotlinkAP_IP(String ip);

	/*
	 * 获取Ip
	 */
	public void ObtainFaild();
}
