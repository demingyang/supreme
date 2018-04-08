package com.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * md5加密解密工具入口
 * 
 */
public class Md5Util {

	private final static Logger log = LoggerFactory.getLogger(Md5Util.class);

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };
	private final static String md5 = "MD5";

	/**
	 * 
	 * 第三方对接md5加密
	 * 
	 * @param s
	 *            String
	 * @return String
	 * 
	 */
	public final static String md5(String s) {
		return getMd5(s);
	}

	/**
	 * 
	 * 比较明文md5加密后值是否等于给定加密值
	 * 
	 * @param encryptValue
	 *            String md5加密的值
	 * @param notNncryptValue
	 *            String 未加密的值
	 * @return boolean true:相等；false：不相等
	 */
	public static boolean valueSame(String encryptValue, String notNncryptValue) {
		String newValue=getMd5(notNncryptValue);
		// 比较是否相等
		return (encryptValue.equals(newValue)) ? true : false;
	}

	/**
	 * 
	 * MD5加密
	 * 
	 * @param s
	 *            String 加密前内容
	 * @return String 加密后密码
	 * 
	 */
	private final static String getMd5(String s) {
		if (s == null) {
			log.error("{}:参数不合法. ", Md5Util.class.getName());
			return null;
		}
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance(md5);
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
