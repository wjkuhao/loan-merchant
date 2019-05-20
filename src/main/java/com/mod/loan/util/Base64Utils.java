package com.mod.loan.util;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * 
 * @author wugy
 *  2016年7月19日下午1:55:30
 *
 */
public class Base64Utils {

	
	private Base64Utils() {
		throw new Error("can't instance this tool class");
	}

	public static void main(String[] args) {
        String s = encodeOrigin("24");

        System.out.println("解码后: "+decodeOrigin(s));
    }

	// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public static String getImageToStr(String path) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return encode(data);
	}

	public static boolean GenerateStrToImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		try {
			// Base64解码
			byte[] bytes = decodeBase64(imgStr);
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 编码
	 */
	private static String encode(byte[] val) {
		return DatatypeConverter.printBase64Binary(val);
	}


	/**
	 * 解码
	 */
	private static byte[] decodeBase64(String value) {
		return DatatypeConverter.parseBase64Binary(value);
	}

    /**
     * 对渠道进行base64编码，再第二位后插入4位随机数
     * @param origin 原始渠道号
     * @return base64编码的渠道号
     */
	public static String encodeOrigin(String origin) {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String randomStr = str.substring(9,13);  //其中截取4位

        Base64.Encoder encoder=  Base64.getEncoder();
        String originBase64=  encoder.encodeToString(origin.getBytes());

        //插入到第二位之后
        StringBuilder sb = new StringBuilder(originBase64);
        sb.insert(1,randomStr);
        return  sb.toString();
    }

    /**
     * 对渠道进行base64解码，再第二位后插入4位随机数
     * @param base64origin 加密后渠道号
     * @return 原始渠道号
     */
    public static String decodeOrigin(String base64origin) {
        Base64.Decoder decoder= Base64.getDecoder();
        byte[] decode = decoder.decode(base64origin.substring(0, 1) + base64origin.substring(5));
        return new String(decode);
    }
}