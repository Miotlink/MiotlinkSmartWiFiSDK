package com.android.miotlink.sdk.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.miot.android.socket.Tools;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class MmwParseUartUtils {


	private static String GetUartData = "GetUartData";

	private static String GetGpioData = "GetLocalData";

	private static String IRSet="IRSet";

	public static String doLinkBindMake(int type, String UserBinaryData) {
		String CodeName = "GetUartData";
		String Chn = "";
		int len = 0;
		String sMlcc = "";

		try {
			switch (type) {
				case 1:
					CodeName = GetUartData;
					break;
				case 2:
					CodeName = GetGpioData;
					break;
			}

			Chn = "0";
			byte[] by = hexString2Bytes(UserBinaryData);
			len = by.length;
			sMlcc = "CodeName=" + CodeName + "&Chn=" + Chn + "&Len=" + len
					+ "&UserBinaryData=";
			byte[] mlcc = sMlcc.getBytes("ISO-8859-1");
			byte[] bs = new byte[by.length + mlcc.length];
			System.arraycopy(mlcc, 0, bs, 0, mlcc.length);
			System.arraycopy(by, 0, bs, mlcc.length, by.length);
			sMlcc = new String(bs, "ISO-8859-1");
			return sMlcc;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sMlcc;
	}

	@NonNull
	public static String doLinkBindMake(String UserBinaryData) {
		String CodeName="";
		String Chn="";
		int len=0;
		String sMlcc="";
		try {
			CodeName = "GetUartData";
			Chn = "0";
			byte[] by=hexString2Bytes(UserBinaryData);
			len = by.length;
			sMlcc = "CodeName=" + CodeName + "&Chn=" + Chn + "&Len="
					+ len
					+ "&UserBinaryData=";
			byte[] mlcc = sMlcc.getBytes("ISO-8859-1");
			byte[] bs = new byte[by.length + mlcc.length];
			System.arraycopy(mlcc, 0, bs, 0, mlcc.length);
			System.arraycopy(by, 0, bs, mlcc.length, by.length);
			sMlcc = new String(bs, "ISO-8859-1");
			return sMlcc;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sMlcc;
	}

	public static String doInfraredLinkBindMake(int[] patternsInArray) {
		StringBuffer buffer = new StringBuffer();
		if(patternsInArray.length > 0) {
			for(int CodeName = 0; CodeName < patternsInArray.length; ++CodeName) {
				buffer.append(stringFill(Integer.toHexString(patternsInArray[CodeName]), 4, '0', true));
			}
		}

		String var10 = "";
		String Chn = "";
		boolean len = false;
		String sMlcc = "";

		try {
			var10 = IRSet;
			Chn = "0";
			byte[] e = hexString2Bytes(buffer.toString());
			int var11 = e.length;
			sMlcc = "CodeName=" + var10 + "&Len=" + var11 + "&UserBinaryData=";
			byte[] mlcc = sMlcc.getBytes("ISO-8859-1");
			byte[] bs = new byte[e.length + mlcc.length];
			System.arraycopy(mlcc, 0, bs, 0, mlcc.length);
			System.arraycopy(e, 0, bs, mlcc.length, e.length);
			sMlcc = new String(bs, "ISO-8859-1");
			return sMlcc;
		} catch (UnsupportedEncodingException var9) {
			var9.printStackTrace();
			return sMlcc;
		}
	}

	public static String stringFill(String source, int fillLength, char fillChar, boolean isLeftFill) {
		if(source != null && source.length() < fillLength) {
			StringBuilder result = new StringBuilder(fillLength);
			int len = fillLength - source.length();
			if(!isLeftFill) {
				result.append(source);

				while(len > 0) {
					result.append(fillChar);
					--len;
				}
			} else {
				while(len > 0) {
					result.append(fillChar);
					--len;
				}

				result.append(source);
			}

			return result.toString();
		} else {
			return source;
		}
	}

	@NonNull
	public static String doLinkBindParse(@NonNull String string) {
		String smlcc = "";
		try {
			if (string.startsWith("UserBinaryData=",
					string.indexOf("UserBinaryData="))) {
				smlcc = string.substring(string.indexOf("UserBinaryData=")
						+ "UserBinaryData=".length(), string.length());
				int length = Integer.parseInt(string.substring(
						string.indexOf("Len=") + "Len=".length(),
						string.indexOf("&UserBinaryData=")));
				if (length > smlcc.length()) {
					return "";
				}
				smlcc = smlcc.substring(0, length);
			}else{
				smlcc=string;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smlcc;
	}


	@NonNull
	public static String byteToStr(@NonNull String msgIn) {
		String msg = "";
		String byteStr = "";
		try {
			byte[] bytes;
			bytes = msgIn.getBytes("iso-8859-1");
			byteStr = Bytes2HexString(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byteStr = byteStr.toUpperCase();
		return byteStr;
	}

	// 从字节数组到十六进制字符串转换
	@NonNull
	public static String Bytes2HexString(@NonNull byte[] b) {
		byte[] hex = "0123456789ABCDEF".getBytes();
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	/**
	 * 二维码校验和
	 *
	 * @param preIds
	 * @return
	 */

	@Nullable
	public static Character getLastIDNum(@Nullable String preIds) {
		Character lastId = null; // 当传入的字符串没有17位的时候，则无法计算，直接返回
		if (preIds == null) { // && preIds.length()<17) {
			return null;
		}
		int[] weightArray = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
				2 };// 权数数组
		String vCode = "10X98765432";// 校验码字符串
		int sumNum = 0;// 前17为乘以权然后求和得到的数
		// 循环乘以权，再求和
		int maxI = preIds.length() > 17 ? 17 : preIds.length();
		for (int i = 0; i < maxI; i++) {
			int index = Integer.parseInt(preIds.charAt(i) + "");
			sumNum = sumNum + index * weightArray[i];// 乘以权数，再求和
		}
		int modNum = sumNum % 11;// 求模
		lastId = vCode.charAt(modNum);// 从验证码中找出对应的数
		if (lastId == 'X')
			return '0';
		else
			return lastId;
	}

	@NonNull
	public static String doLinkBindMianBanMake(@NonNull byte[] bt) {
		String CodeName="";
		String Chn="";
		String sMlcc="";
		CodeName = "GetUartData";
		Chn = "0";
		sMlcc = "CodeName=" + CodeName + "&Chn=" + Chn + "&Len=" + bt.length
				+ "&UserBinaryData=";
		try {
			byte[] mlcc = sMlcc.getBytes("ISO-8859-1");
			byte[] bs = new byte[bt.length + mlcc.length];
			System.arraycopy(mlcc, 0, bs, 0, mlcc.length);
			System.arraycopy(bt, 0, bs, mlcc.length, bt.length);
			sMlcc = new String(bs, "ISO-8859-1");
			System.out.println(sMlcc);
			return sMlcc;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sMlcc;
	}
	public static String bytesToHexString(@Nullable byte[] src) {
		StringBuilder sb = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
			sb.append(" ");
		}
		return sb.toString();
	}


	@Nullable
	public static String hexString2Byte(@NonNull byte[] bs){
		String string=null;
		try {
			string=new String(bs,"ISO-8859-1");
			return string;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	@NonNull
	public static byte[] hexString2Bytes(String src) {
		src = src.replace(" ", "");
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp;
		try {
			tmp = src.getBytes("ISO-8859-1");
			for (int i = 0; i < src.length() / 2; i++) {
				ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static int getInt(byte[] bytes) {
		return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8))
				| (0xff0000 & (bytes[2] << 16))
				| (0xff000000 & (bytes[3] << 24));
	}
	public static float getFloat(@NonNull byte[] bytes) {
		String s = "";
		for (int i = 0; i < bytes.length; i++) {
			s += bytes[i] + " ";
		}
		System.out.println(s);
		return Float.intBitsToFloat(getInt(bytes));
	}

	public static byte[] stringTo16Byte(@NonNull String temp) {

		int len = temp.length();
		for (int i = 0; i < 16 - len; i++) {
			if (temp.length() == 16) {
				break;
			}
			temp = temp + "0";

		}
		return temp.getBytes();
	}

	/** 名称： 计算18位身份证的最后一位
	 * 功能 : 根据前17位身份证号，求最后一位
	 * 身份证最后一位的算法：
	 * 1.将身份证号码的前17位的数字，分别乘以权数 ： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
	 *   (比如：第一位乘以7，第二位乘以9，以此类推)
	 * 2.再将上面的所有乘积求和
	 * 3.将求得的和mod以11（%11），得到一个小于11的数（0到11）
	 * 4.然后从1 0 X 9 8 7 6 5 4 3 2几位校验码中找出最后一位的数字
	 *  如果得到的是0，则对应第一位：1,如果得到的是1，则对应第二位：0
	 *  如果得到的是2，则对应第三位：X,如果得到的是3，则对应第四位：9,以此类推
	 * 5.最后得到的就是身份证的最后一位
	 * 返回值: 0-9, X用0代替。
	 * */
	@Nullable
	public static Character getLastQrcodeIDNum(@Nullable String preIds) {
		Character lastId = null;        //当传入的字符串没有17位的时候，则无法计算，直接返回
		if(preIds==null ) { // && preIds.length()<17) {
			return null;
		}
		int[] weightArray = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};//权数数组
		String vCode = "10X98765432";//校验码字符串
		int sumNum = 0;//前17为乘以权然后求和得到的数
		//循环乘以权，再求和
		int maxI = preIds.length()>17?17:preIds.length();
		for (int i = 0; i < maxI; i++) {
			int index = Integer.parseInt(preIds.charAt(i) + "");
			sumNum = sumNum + index * weightArray[i];// 乘以权数，再求和
		}
		int modNum = sumNum%11;//求模
		lastId = vCode.charAt(modNum);//从验证码中找出对应的数
		if ( lastId == 'X' )
			return '0';
		else
			return lastId;
	}

	public static int[] stringToIntArray(String data){
		if(TextUtils.isEmpty(data)){
			return null;
		}
		String[] strArr= data.split(",");
		if(strArr==null||strArr.length<1){
			return null;
		}
		int[] intArr=new int[strArr.length];
		for(int i=0;i<strArr.length;i++){
			intArr[i]= Integer.parseInt(strArr[i]);
		}
		return intArr;
	}


	public static byte[] formatLsscCmdBuffer(byte[] content) {

		try {
			// bsContent = content;
			int packageLen = content.length + 20;
			int contentlen = content.length + 12;
			byte[] bs = new byte[packageLen];
			bs[0] = (byte) 0x30;
			bs[1] = (byte) 104; // 00000001 00000000 // 256
			bs[2] = (byte) (packageLen >> 8 & 0xff);// (packLen/256); //

			bs[3] = (byte) (packageLen >> 0 & 0xff);// (packLen%256); //

			bs[4] = (byte) (0 >> 24 & 0xff);
			bs[5] = (byte) (0 >> 16 & 0xff);
			bs[6] = (byte) (0 >> 8 & 0xff);
			bs[7] = (byte) (0 >> 0 & 0xff);
			bs[8] = (byte) 0x65; // sessionid (byte)(packLen >> 24 & 0xff);
			bs[9] = (byte) 0; // sessionid (byte)(packLen >> 16 & 0xff);
			bs[10] = (byte) (contentlen / 256); // sessionid (byte)(packLen >> 8
			// & 0xff);
			bs[11] = (byte) (contentlen % 256); // sessionid (byte)(packLen >> 0
			// & 0xff);
			bs[12] = (byte) (1 >> 24 & 0xff);
			bs[13] = (byte) (1 >> 16 & 0xff);
			bs[14] = (byte) (1 >> 8 & 0xff);
			bs[15] = (byte) (1 >> 0 & 0xff);
			bs[16] = (byte) (0 >> 24 & 0xff);
			bs[17] = (byte) (0 >> 16 & 0xff);
			bs[18] = (byte) (0 >> 8 & 0xff);
			bs[19] = (byte) (0 >> 0 & 0xff);
			System.arraycopy(content, 0, bs, 20, content.length);

			byte[] result = Tools.encrypt(bs);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[] {};
		}

	}

}
