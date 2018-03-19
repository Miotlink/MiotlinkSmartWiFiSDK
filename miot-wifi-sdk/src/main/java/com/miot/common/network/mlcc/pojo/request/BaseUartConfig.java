package com.miot.common.network.mlcc.pojo.request;

public class BaseUartConfig {
	public BaseUartConfig(Integer iChn, Integer iBaud, Integer iData,
			Integer iParity) throws Exception {//, Integer iPLen, Integer iPTimeOut
		if (iChn != 0 && iChn != 1) {
			throw new Exception("iChn is error [" + iChn
					+ "], support by [0,1]");
		}

		if (iBaud != 1200 && iBaud != 2400 && iBaud != 4800 && iBaud != 9600
				&& iBaud != 19200 && iBaud != 38400 && iBaud != 57600
				&& iBaud != 115200 && iBaud != 230400 && iBaud != 460800
				&& iBaud != 921600) {
			throw new Exception(
					"iBaud is error ["
							+ iBaud
							+ "], support by [1200,2400, 4800,9600,19200,38400,57600,115200,230400,460800,921600]");
		}

		if (iData != 5 && iData != 6 && iData != 7 && iData != 8) {
			throw new Exception("iData is error [" + iData
					+ "], support by [5,6,7,8]");
		}

		if (iParity != 0 && iParity != 1 && iParity != 2) {
			throw new Exception("iParity is error [" + iParity
					+ "], support by [0,1,2]");
		}
		iPLen = 256;
		iPTimeOut = 100;
//		if (iPLen >= 1 && iPLen <= 512) {
//			//do nothing
//		}else{
//			throw new Exception("iPLen is error [" + iPLen
//					+ "], support by [1~512]");
//		}
//
//		if (iPTimeOut >= 10 && iPTimeOut <= 1000) {
//			//do nothing
//		}else{
//			throw new Exception("iPTimeOut is error [" + iPTimeOut
//					+ "], support by [10ms~1000ms]");
//		}

		this.iBaud = iBaud;
		this.iChn = iChn;
		this.iData = iData;
		this.iParity = iParity;
	}

	private Integer iChn;
	private Integer iBaud;
	private Integer iData;
	private Integer iParity;
	private Integer iPLen;
	private Integer iPTimeOut;

	public Integer getiChn() {
		return iChn;
	}

	public Integer getiBaud() {
		return iBaud;
	}

	public Integer getiData() {
		return iData;
	}

	public Integer getiParity() {
		return iParity;
	}

	public Integer getiPLen() {
		return iPLen;
	}

	public Integer getiPTimeOut() {
		return iPTimeOut;
	}

	@Override
	public String toString() {
		return "BaseUartConfig [iChn=" + iChn + ", iBaud=" + iBaud + ", iData="
				+ iData + ", iParity=" + iParity + ", iPLen=" + iPLen
				+ ", iPTimeOut=" + iPTimeOut + "]";
	}

}
