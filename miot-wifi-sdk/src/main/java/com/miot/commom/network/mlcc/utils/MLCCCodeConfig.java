package com.miot.commom.network.mlcc.utils;

public class MLCCCodeConfig {
	public static String split[] = {",","`","&","="}; //TODO

	public class MLCCCodeReturn{
		public static final String SEARCH_ACK = "SearchAck";
		public static final String SET_WIFI_ACK = "SetWifiAck";
		public static final String SET_UART_ACK = "SetUartAck";
		public static final String GET_GPIO_ACK = "GetGpioAck";
		public static final String SET_GPIO_ACK = "SetGpioAck";
		public static final String SET_LINK_INFO="SetLinkInfoAck";
		public static final String FC_ML_PLATFORM_ACK="fc_ml_platform_ack";
		public static final String SMART_CONNECTED="smart_connected";
		public static final String FC_COMPLETE_ACK="fc_complete_ack";
		public static final String FC_UART_INFO_ACK="fc_uart_ack";
	}
	public class MLCCCodeMake{
		public static final String SEARCH = "Search";
		public static final String SET_WIFI = "SetWifi";
		public static final String SET_UART = "SetUart";
		public static final String GET_GPIO = "GetGpio";
		public static final String SET_GPIO = "SetGpio";
		public static final String SET_LINK="SetLinkInfo";
		public static final String FC_ML_PLATFORM="fc_ml_platform";
		public static final String FC_COMPLETE="fc_complete";
		public static final String FC_COMPLETE_FIN="fc_complete_fin";
		public static final String FC_UART_INFO="fc_uart";
	} 
	
	public static class MLCCKeyCodeConfig {
		public static final String CODE_NAME = "CodeName";
	}

	public static class MLCCValueScope{

		public static final int GPIO_C_TYPE_OUT = 0;
		public static final int GPIO_C_TYPE_IN = 1;
		
		public static final int GPIO_C_STATE_HIGH = 1;
		public static final int GPIO_C_STATE_LOW = 0;
		
	}
}
