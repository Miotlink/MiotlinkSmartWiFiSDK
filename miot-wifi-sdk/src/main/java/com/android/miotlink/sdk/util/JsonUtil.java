package com.android.miotlink.sdk.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.miotlink.sdk.entity.FirstData;

public class JsonUtil {
	/**
	 * 
	 * @param name
	 * @return
	 */

	public static List<FirstData> getInstance(String name){
		List<FirstData> firstDatas=null;
		try {
			if (!name.equals("")) {
				JSONObject jsonObject=new JSONObject(name.toString());
				JSONArray array=new JSONArray(jsonObject.getString("firstConfigList"));
				firstDatas=new ArrayList<FirstData>();
				for (int i = 0; i < array.length(); i++) {
					JSONObject json=new JSONObject(array.get(i).toString());
					FirstData firstData=new FirstData(json.getString("index"), json.getString("content"), json.getString("contentAck_CodeName"), json.getString("contentAck_result"));
					firstDatas.add(firstData);
				}
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return firstDatas;
		
	}
	public static void main(String[] args) {
		
	}

}
