package com.miot.android.util;

public class Util {

	public static String MakeMac(String string) {
		String s = string;
		s = insertString1(":", string, 2);
		s = insertString1(":", s, 5);
		s = insertString1(":", s, 8);
		s = insertString1(":", s, 11);
		s = insertString1(":", s, 14);
		return s;

	}

	private static String insertString1(String srcStr, String descStr, int off) {
		if (off > descStr.length()) {
			return "";
		}

		char[] src = srcStr.toCharArray();
		char[] desc = descStr.toCharArray();
		StringBuilder ret = new StringBuilder();

		for (int i = 0; i <= desc.length; i++) {
			if (i == off) {
				for (int j = 0; j < src.length; j++) {
					ret.append(src[j]);
				}

			}

			if (i < desc.length) {
				ret.append(desc[i]);
			}
		}

		return ret.toString();
	}
}
