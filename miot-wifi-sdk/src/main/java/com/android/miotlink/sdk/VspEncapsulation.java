package com.android.miotlink.sdk;

import com.android.miotlink.sdk.util.MmwParseUartUtils;
import com.miot.android.socket.Tools;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class VspEncapsulation {



    private static VspEncapsulation instance=null;

    public static VspEncapsulation getInstance() {
        if (instance==null){
            instance=new VspEncapsulation();
        }
        return instance;
    }

    public byte[] envelopedData(String uart)throws Exception{
        byte[]  by=MmwParseUartUtils.doLinkBindMake(1,uart).getBytes("ISO-8859-1");
         byte[] res=MmwParseUartUtils.formatLsscCmdBuffer(by);
        return res;
    }

    public String parseData(byte[] data,int len)throws Exception{
        String res="";
        if (data==null||data.length<20){
            throw new Exception("data is error");
        }
        byte[] bs = Tools.encrypt(data);
        String msg = Tools.getMlccContent(bs, len);
        res=MmwParseUartUtils.byteToStr(MmwParseUartUtils.doLinkBindParse(msg));
        return res;
    }



}
