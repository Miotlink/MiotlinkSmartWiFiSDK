package com.miot.commom.network.mlcc.utils;

//import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MLCCReflectUtils {

	/**
	 * 
	 * @function Reflect set Class Field by Map
	 * @param map
	 * @param clazz
	 * @return
	 * @throw Exception("When there is the same field name in Class!);
	 */
	public static Object setBeanUtils(Map<String, String> map, Class<?> clazz) {

		Object obj = null;
		try {
			obj = clazz.newInstance();
			Field[] nameFields = clazz.getDeclaredFields();

			Map<String, Object> setReflectMap = new HashMap<String, Object>();
			Set<String> keySets = map.keySet();
			List<String> unkonwnKeyArray = new ArrayList<String>();
			List<String> unkonwnValueArray = new ArrayList<String>();
			for (String name : keySets) {
				int sameFieldName = 0;
				for (Field f : nameFields) {
					String fieldName = f.getName().toString();
					if (fieldName.toUpperCase().equals(name.toUpperCase())) {
						setReflectMap.put(fieldName, map.get(name));
						sameFieldName++;
					}
				}
				if (sameFieldName > 1) {
					throw new Exception("There is the same field name <" + name
							+ "> in Class! <" + clazz.getName() + ">");
				}
				if (sameFieldName == 0) {
					unkonwnKeyArray.add(name);
					unkonwnValueArray.add(map.get(name) + "");
				}
			}
			if (setReflectMap != null && setReflectMap.size() > 0) {
				for (String fieldName : setReflectMap.keySet()) {// localPhone
					// setFieldValue(obj, fieldName,
					// setReflectMap.get(fieldName));
					setProperty(obj, fieldName, setReflectMap.get(fieldName));
				}
			}
			try {
				if (unkonwnKeyArray.size() > 0 && unkonwnKeyArray.size() > 0
						&& unkonwnKeyArray.size() == unkonwnValueArray.size()) {
					for (int i = 0; i < unkonwnKeyArray.size(); i++) {
						synchronized (unkonwnKeyArray) {
							synchronized (unkonwnValueArray) {
								setProperty(obj, "unKonwnKey",
										unkonwnKeyArray.get(i));
								setProperty(obj, "unKonwnValue",
										unkonwnValueArray.get(i));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	private static void setProperty(Object obj, String key, Object value) {
		try {
			// BeanUtils.setProperty(obj, key, value);
			// setPropertyTest(obj, key, value);
			setFieldValue(obj, key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// String getMethodName = "set" + key.substring(0, 1).toUpperCase()
		// + key.substring(1);; // 转换成字段的get方法
		// Method getMethod = obj.getClass().getMethod(getMethodName,
		// new Class[] {});
		// getMethod.invoke(obj, new Object[] {value}); // 这个对象字段get方法的值
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
	 * 
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		makeAccessible(field);
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
	 * 
	 * @param valueMap
	 * @param obj
	 */
	public static void setFieldValue(Map<String,String> valueMap,Object obj){
		Iterator<Entry<String, String>> it = valueMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> next = it.next();
			String key = next.getKey();
			String value = next.getValue();
			setFieldValue(obj, key,value);
		}
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * 
	 * @param object
	 * @param filedName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String filedName) {

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				// Field 不在当前类定义, 继续向上转型
			}
		}
		return null;
	}

	/**
	 * 使 filed 变为可访问
	 * 
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 
	 * @param keyarray
	 * @param t
	 * @return
	 */
	public static <T> String makeParam(Field[] keyarray, T t) {
		StringBuilder sb = new StringBuilder();
		for (Field field : keyarray) {
			try {
				String filedName = field.getName().toString();
				String firstLetter = filedName.substring(0, 1).toUpperCase()
						+ filedName.substring(1); // 获得字段第一个字母大写
				String getMethodName = "get" + firstLetter; // 转换成字段的get方法
				Method getMethod = t.getClass().getMethod(getMethodName,
						new Class[] {});
				Object value = getMethod.invoke(t, new Object[] {}); // 这个对象字段get方法的值
				if (value == null) {
					// do nothing
				} else {
					sb.append(filedName).append("=").append(value).append("&");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		String result = sb.toString();
		return result.substring(0, (result.length()) - 1) + '\0';
	}
}
