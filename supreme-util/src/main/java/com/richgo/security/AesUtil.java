package com.richgo.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * AES加/解密
 * 
 * @author zhouxy
 *
 */
public class AesUtil {

	// AES:ECB模式，PKCS5Padding填充，密码必须是16位,base64转码
	//
	// 模式：Java的ECB对应C#的System.Security.Cryptography.CipherMode.ECB
	//
	// 填充方法：Java的PKCS5Padding对应C#System.Security.Cryptography.PaddingMode.PKCS7
	//
	// Java和C#版的加解密是互通的，也就是能相互加解密，编码明确指定了采用UTF-8

	/**
	 * 
	 * AES(ECB模式，PKCS5Padding填充，密码必须是16位)加密后再base64
	 * 
	 * @param value
	 *            String 明文
	 * @param key
	 *            String 加密key 16位字符串
	 * @return String 密文,如果参数不合法返回null
	 * 
	 * @throws Exception
	 * 
	 */
	public static String encrypt(String value, String key) throws Exception {
		if (value == null || StringUtils.isEmpty(key) || key.length() != 16) {
			throw new IllegalArgumentException("参数为null或key长度不是16位");
		}

		// 创建
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));

		// 加密
		byte[] bytes = cipher.doFinal(value.getBytes("utf-8"));

		// 转换为base64
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * 
	 * base64转换成btye数组再AES(ECB模式，PKCS5Padding填充，密码必须是16位)解密
	 * 
	 * @param value
	 *            String 密文
	 * @param key
	 *            String 加密key 16位字符串
	 * @return String 明文,如果参数不合法返回null
	 * @throws Exception
	 * 
	 */
	public static String decode(String value, String key) throws Exception {
		if (value == null || StringUtils.isEmpty(key) || key.length() != 16) {
			throw new IllegalArgumentException("参数为null或key长度不是16位");
		}
		// base64转换
		byte[] bytes = new BASE64Decoder().decodeBuffer(value);

		// 创建
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
		// 解密
		bytes = cipher.doFinal(bytes);

		return new String(bytes, "utf-8");
	}

	// public static void main(String[] args) throws Exception {
	// System.out.println(encrypt("123456", "ab1234567890cdef"));
	// }
}