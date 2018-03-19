package com.miot.commom.network.mlcc.parse;

/**
 * 
 * TTP content 解析代理枚举类
 * 
 * @author szf
 * @date 2014-06-16
 */
enum ProxyParseMLCCEnum {
	_SearchAck(ParseMLCCImpl_SearchAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_SearchAck.getInstance()),
	_SetWifiAck(ParseMLCCImpl_SetWifiAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_SetWifiAck.getInstance()),
	_SetUartAck(ParseMLCCImpl_SetUartAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_SetUartAck.getInstance()),
	_GetGpioAck(ParseMLCCImpl_GetGpioAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_GetGpioAck.getInstance()),
	_SetGpioAck(ParseMLCCImpl_SetGpioAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_SetGpioAck.getInstance()),
	_SetLinkInfoAck(ParseMLCCImpl_SetLinkInfo.getInstance().getMLCCCode(),
			ParseMLCCImpl_SetLinkInfo.getInstance()),
	_SetSmartConnectedAck(ParseMLCCImpl_SmartConnected.getInstance().getMLCCCode(),
			ParseMLCCImpl_SmartConnected.getInstance()),
	_Fc_CompleteAck(ParseMLCCImpl_Fc_completeAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_Fc_completeAck.getInstance()),
    _Fc_Uart_InfoAck(ParseMLCCImpl_FcUartInfoAck.getInstance().getMLCCCode(),
    		ParseMLCCImpl_FcUartInfoAck.getInstance()),
	_FcPlatFromAck(ParseMLCCImpl_FcPlatFromAck.getInstance().getMLCCCode(),
			ParseMLCCImpl_FcPlatFromAck.getInstance());
	
	private String mlccCodeName = "";
	private ParseMLCCInterface<?> parseTTPInterface;

	private ProxyParseMLCCEnum(String mlccCodeName,
			ParseMLCCInterface<?> parseTTPInterface) {
		this.mlccCodeName = mlccCodeName;
		this.parseTTPInterface = parseTTPInterface;
	}

	/**
	 * 根据ttp code解析到对应的解析类
	 * 
	 * @param ttpCode
	 * @return ParseTTPProxyEnum
	 */
	public static ProxyParseMLCCEnum getByMLCCCodeName(String ttpCode) {
		for (ProxyParseMLCCEnum p : ProxyParseMLCCEnum.values()) {
			if (ttpCode.equals(p.getMlccCodeName())) {
				return p;
			}
		}
		return null;
	}

	public String getMlccCodeName() {
		return mlccCodeName;
	}

	public ParseMLCCInterface<?> getParseTTPInterface() {
		return parseTTPInterface;
	}

	
}
