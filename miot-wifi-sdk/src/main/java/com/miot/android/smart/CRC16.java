package com.miot.android.smart;

public class CRC16
{
  static final String HEXES = "0123456789ABCDEF";
  byte uchCRCHi = -1;
  byte uchCRCLo = -1;
  private static byte[] auchCRCHi = { 0, -63, -127, 
    64, 1, -64, -128, 65, 
    1, -64, -128, 65, 
    0, -63, -127, 64, 1, -64, 
    -128, 65, 0, -63, -127, 
    64, 0, -63, -127, 64, 
    1, -64, -128, 65, 1, 
    -64, -128, 65, 0, -63, 
    -127, 64, 0, -63, -127, 
    64, 1, -64, -128, 65, 
    0, -63, -127, 64, 1, 
    -64, -128, 65, 1, -64, 
    -128, 65, 0, -63, -127, 
    64, 1, -64, -128, 65, 
    0, -63, -127, 64, 
    0, -63, -127, 64, 1, -64, 
    -128, 65, 0, -63, -127, 
    64, 1, -64, -128, 65, 
    1, -64, -128, 65, 
    0, -63, -127, 64, 0, -63, 
    -127, 64, 1, -64, -128, 
    65, 1, -64, -128, 65, 
    0, -63, -127, 64, 1, 
    -64, -128, 65, 0, -63, 
    -127, 64, 0, -63, -127, 
    64, 1, -64, -128, 65, 
    1, -64, -128, 65, 
    0, -63, -127, 64, 0, -63, 
    -127, 64, 1, -64, -128, 
    65, 0, -63, -127, 64, 
    1, -64, -128, 65, 1, 
    -64, -128, 65, 0, -63, 
    -127, 64, 0, -63, -127, 
    64, 1, -64, -128, 65, 
    1, -64, -128, 65, 
    0, -63, -127, 64, 1, -64, 
    -128, 65, 0, -63, -127, 
    64, 0, -63, -127, 64, 
    1, -64, -128, 65, 
    0, -63, -127, 64, 1, -64, 
    -128, 65, 1, -64, -128, 
    65, 0, -63, -127, 64, 
    1, -64, -128, 65, 
    0, -63, -127, 64, 0, -63, 
    -127, 64, 1, -64, -128, 
    65, 1, -64, -128, 65, 
    0, -63, -127, 64, 
    0, -63, -127, 64, 1, -64, 
    -128, 65, 0, -63, -127, 
    64, 1, -64, -128, 65, 
    1, -64, -128, 65, 
    0, -63, -127, 64 };

  private static byte[] auchCRCLo = { 0, -64, -63, 
    1, -61, 3, 2, -62, 
    -58, 6, 7, -57, 5, 
    -59, -60, 4, -52, 12, 
    13, -51, 15, -49, -50, 
    14, 10, -54, -53, 11, 
    -55, 9, 8, -56, -40, 
    24, 25, -39, 27, -37, 
    -38, 26, 30, -34, -33, 
    31, -35, 29, 28, -36, 
    20, -44, -43, 21, -41, 
    23, 22, -42, -46, 18, 
    19, -45, 17, -47, -48, 
    16, -16, 48, 49, -15, 
    51, -13, -14, 50, 54, 
    -10, -9, 55, -11, 53, 
    52, -12, 60, -4, -3, 
    61, -1, 63, 62, -2, 
    -6, 58, 59, -5, 57, 
    -7, -8, 56, 40, -24, 
    -23, 41, -21, 43, 42, 
    -22, -18, 46, 47, -17, 
    45, -19, -20, 44, -28, 
    36, 37, -27, 39, -25, 
    -26, 38, 34, -30, -29, 
    35, -31, 33, 32, -32, 
    -96, 96, 97, -95, 99, 
    -93, -94, 98, 102, -90, 
    -89, 103, -91, 101, 100, 
    -92, 108, -84, -83, 109, 
    -81, 111, 110, -82, -86, 
    106, 107, -85, 105, -87, 
    -88, 104, 120, -72, -71, 
    121, -69, 123, 122, -70, 
    -66, 126, 127, -65, 125, 
    -67, -68, 124, -76, 116, 
    117, -75, 119, -73, -74, 
    118, 114, -78, -77, 115, 
    -79, 113, 112, -80, 80, 
    -112, -111, 81, -109, 83, 
    82, -110, -106, 86, 87, 
    -105, 85, -107, -108, 84, 
    -100, 92, 93, -99, 95, 
    -97, -98, 94, 90, -102, 
    -101, 91, -103, 89, 88, 
    -104, -120, 72, 73, -119, 
    75, -117, -118, 74, 78, 
    -114, -113, 79, -115, 77, 
    76, -116, 68, -124, -123, 
    69, -121, 71, 70, -122, 
    -126, 66, 67, -125, 65, 
    -127, -128, 64 };
  public int value;

  public CRC16()
  {
    this.value = 0;
  }

  public void update(byte[] puchMsg, int usDataLen)
  {
    for (int i = 0; i < usDataLen; ++i) {
      int uIndex = (this.uchCRCHi ^ puchMsg[i]) & 0xFF;

      this.uchCRCHi = (byte)(this.uchCRCLo ^ auchCRCHi[uIndex]);
      this.uchCRCLo = auchCRCLo[uIndex];
    }
    this.value = ((this.uchCRCHi << 8 | this.uchCRCLo & 0xFF) & 0xFFFF);
  }

  public void reset()
  {
    this.value = 0;
    this.uchCRCHi = -1;
    this.uchCRCLo = -1;
  }

  public int getValue() {
    return this.value;
  }

  private static byte uniteBytes(byte src0, byte src1) {
    byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
      .byteValue();
    _b0 = (byte)(_b0 << 4);
    byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
      .byteValue();
    byte ret = (byte)(_b0 ^ _b1);
    return ret;
  }

  private static byte[] HexString2Buf(String src) {
    int len = src.length();
    byte[] ret = new byte[len / 2 + 2];
    byte[] tmp = src.getBytes();
    for (int i = 0; i < len; i += 2) {
      ret[(i / 2)] = uniteBytes(tmp[i], tmp[(i + 1)]);
    }
    return ret;
  }

  public static byte[] getSendBuf(String toSend) {
    byte[] bb = HexString2Buf(toSend.replace(" ", ""));
    CRC16 crc16 = new CRC16();
    crc16.update(bb, bb.length - 2);
    int ri = crc16.getValue();
    bb[(bb.length - 1)] = (byte)(0xFF & ri);
    bb[(bb.length - 2)] = (byte)((0xFF00 & ri) >> 8);
    return bb;
  }
  public static boolean checkBuf(byte[] bb) {
    CRC16 crc16 = new CRC16();
    crc16.update(bb, bb.length - 2);
    int ri = crc16.getValue();

    return (bb[(bb.length - 1)] == (byte)(ri & 0xFF)) && 
      (bb[(bb.length - 2)] == (byte)((0xFF00 & ri) >> 8));
  }

  public static String getBufHexStr(byte[] raw)
  {
    if (raw == null) {
      return null;
    }
    StringBuilder hex = new StringBuilder(2 * raw.length);
    byte[] arrayOfByte = raw; int j = raw.length; for (int i = 0; i < j; ++i) { byte b = arrayOfByte[i];
      hex.append("0123456789ABCDEF".charAt((b & 0xF0) >> 4))
        .append("0123456789ABCDEF".charAt(b & 0xF)); }

    return hex.toString();
  }

  public static String getCRC16(String str)
  {
    if ((str == "") || (str == null)) {
      return null;
    }
    return str.substring(str.length() - 4, str.length());
  }

  public static String getCRC16Max(String str) {
    String crc16 = getCRC16(str);
    return crc16.substring(0, crc16.length() - 2);
  }

  public static String getCRC16Min(String str) {
    String crc16 = getCRC16(str);
    return crc16.substring(2, crc16.length());
  }
}