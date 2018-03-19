package com.android.miotlink.sdk.util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 解析mlcc smart-connected 和firstconfig
 *
 * @author Administrator
 */
public class Mlcc_ParseUtils {

    private static final String MLCC_SPLIT_FLAG = "&";

    private static final String MLCC_SPLIT_KEY_VALUE_FLAG = "=";

    /**
     * 判断是否是smartconnected 返回的值
     *
     * @param value
     * @return
     */
    public static boolean isSmartConnected(String value) {
        if (value.split(MLCC_SPLIT_FLAG).length > 0) {
            if (value.split(MLCC_SPLIT_FLAG)[0]
                    .equals("CodeName=smart_connected")
                    || value.startsWith("CodeName=smart_connected")) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, String> getParse(String value) throws Exception {
        Map<String, String> parseMap = new HashMap<String, String>();
        for (String oneContent : value.split(MLCC_SPLIT_FLAG)) {
            String keyValueArray[] = oneContent
                    .split(MLCC_SPLIT_KEY_VALUE_FLAG);
            if (keyValueArray == null
                    || (keyValueArray.length != 2 && !oneContent
                    .contains(MLCC_SPLIT_KEY_VALUE_FLAG))) {
                throw new Exception("one keyValueArray length != 2, param=["
                        + oneContent + "]");
            } else {
                if (keyValueArray.length == 2) {
                    parseMap.put(keyValueArray[0], keyValueArray[1]);
                } else {
                    parseMap.put(keyValueArray[0], "");
                }
            }
        }
        return parseMap;
    }

    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getValue(Map<String, String> value) {
        Map<String, Object> mapValue = new HashMap<String, Object>();
        try {
            for (Map.Entry MapString : value.entrySet()) {
                if (!MapString.getKey().equals("CodeName")) {
                	mapValue.put(MapString.getKey() + "", MapString.getValue());
				}
            }
        } catch (Exception e) {

        }
        return mapValue;

    }

    @SuppressWarnings("rawtypes")
    public static String getSuccessValue(Map<String, Object> map) throws Exception {
        String json = "";
        JSONObject jsonObjectValue = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        if (map != null) {
            for (Map.Entry mapValue : map.entrySet()) {
                jsonObject.put(mapValue.getKey() + "", mapValue.getValue().toString());
            }
        }
        jsonObjectValue.put("code", "smartconfigResult");
        jsonObjectValue.put("data", jsonObject);
        json = jsonObjectValue.toString();
        return json;

    }
}
