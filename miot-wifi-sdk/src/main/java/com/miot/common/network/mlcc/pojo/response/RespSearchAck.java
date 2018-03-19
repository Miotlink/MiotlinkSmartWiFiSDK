package com.miot.common.network.mlcc.pojo.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.miot.commom.network.mlcc.utils.MLCCCodeConfig;
import com.miot.commom.network.mlcc.utils.MLCCStringUtils;
import com.miot.common.network.mlcc.pojo.request.BaseMode;
import com.miot.common.network.mlcc.pojo.request.BaseNetWorkConfig;
import com.miot.common.network.mlcc.pojo.request.BaseUartConfig;

public class RespSearchAck extends RespBaseAck {
	public RespSearchAck() {
		super.chazzType = this.getClass();
		super.clazzTypeDesc = MLCCCodeConfig.MLCCCodeReturn.SEARCH_ACK;
		
	}

	private String codeName;
	private String devName;
	private String byName;
	private String mac;
	private String mip;
	private BaseMode modeAndIp;

	private String apId;
	private String apPd;
	private String staId;
	private String staPd;
	private String idList;
	private List<String> idListByWifiName;
	private String uartInfo;
	private String tInfo;
	private String cInfo;

	private BaseUartConfig uartInfoConfig = null;
	private BaseNetWorkConfig transparentTransmitInfo = null;
	private BaseNetWorkConfig controlInfo = null;

	public BaseNetWorkConfig getTransparentTransmitInfo() {
		return transparentTransmitInfo;
	}

	public BaseNetWorkConfig getControlInfo() {
		return controlInfo;
	}

	public BaseMode getModeAndIp() {
		return modeAndIp;
	}

	public List<String> getIdListByWifiName() {
		return idListByWifiName;
	}

	public void setUartInfo(String uartInfo) {
		this.uartInfo = uartInfo;
		try {
			this.uartInfoConfig = MLCCStringUtils
					.getUartString2UartInfoConfig(uartInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void settInfo(String tInfo) {
		this.tInfo = tInfo;
		try {
			this.transparentTransmitInfo = MLCCStringUtils
					.getTInfo2BaseNetWork(tInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setcInfo(String cInfo) {
		this.cInfo = cInfo;
		try {
			this.controlInfo = MLCCStringUtils.getTInfo2BaseNetWork(cInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseUartConfig getUartInfoConfig() {
		return uartInfoConfig;
	}

	public void setUartInfoConfig(BaseUartConfig uartInfoConfig) {
		this.uartInfoConfig = uartInfoConfig;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getByName() {
		return byName;
	}

	public void setByName(String byName) {
		this.byName = byName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setMip(String mip) {
		this.mip = mip;
		this.modeAndIp = MLCCStringUtils.getMIp2Mode(mip);
	}

	public String getApId() {
		return apId;
	}

	public void setApId(String apId) {
		this.apId = apId;
	}

	public String getApPd() {
		return apPd;
	}

	public void setApPd(String apPd) {
		this.apPd = apPd;
	}

	public String getStaId() {
		return staId;
	}

	public void setStaId(String staId) {
		this.staId = staId;
	}

	public String getStaPd() {
		return staPd;
	}

	public void setStaPd(String staPd) {
		this.staPd = staPd;
	}

	public void setIdList(String idList) {
		this.idList = idList;
		if (idList == null || idList.length() == 0) {
			this.idListByWifiName = new ArrayList<String>(0);
		} else {
			String[] paramArray = idList
					.split(MLCCStringUtils.MLCC_SPLIT_SAME_DH_VALUE_FLAG);
			if (paramArray != null && paramArray.length == 2) {
				this.idListByWifiName = MLCCStringUtils.list2Array(paramArray[1]
						.split(MLCCStringUtils.MLCC_SPLIT_SAME_VALUE_FLAG));
			} else {
				this.idListByWifiName = new ArrayList<String>(0);
			}
		}
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	@Override
	public String toString() {
		return "RespSearchAck [codeName=" + codeName + ", devName=" + devName
				+ ", byName=" + byName + ", mac=" + mac + ", modeAndIp="
				+ modeAndIp + ", apId=" + apId + ", apPd=" + apPd + ", staId="
				+ staId + ", staPd=" + staPd + ", idListByWifiName=" + idListByWifiName
				+ ", uartInfoConfig=" + uartInfoConfig
				+ ", transparentTransmitInfo=" + transparentTransmitInfo
				+ ", controlInfo=" + controlInfo + "]";
	}

	@Override
	public synchronized void make(Map<String, String> resultMap) {
		setUartInfo(uartInfo);
		settInfo(tInfo);
		setcInfo(cInfo);
		setMip(mip);
		setIdList(idList);
		super.setResultMap(resultMap);
	}

}
