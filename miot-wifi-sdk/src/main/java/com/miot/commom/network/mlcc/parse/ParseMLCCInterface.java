package com.miot.commom.network.mlcc.parse;

import java.util.Map;

/**
 * TTP content parse interface
 * 
 * @date 2014-06-16
 * @author szf
 * 
 */
interface ParseMLCCInterface<T> {

	/**
	 * 
	 * @param contentMap
	 * @return 返回的是解析之后的对象类型 此类型是有实现类决定
	 * @throws Exception
	 */
	T parse(Map<String, String> contentMap) throws Exception;

	/**
	 * 
	 * @return 返回TTP包的唯一标识 
	 */
	String getMLCCCode();
	
	
}
