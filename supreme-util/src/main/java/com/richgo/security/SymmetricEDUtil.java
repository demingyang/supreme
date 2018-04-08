package com.richgo.security;

/**
 * 
 * 可逆加密、解密算法
 * 
 * @author zhouxy
 *
 */
public class SymmetricEDUtil {

	/**
	 * 
	 * 解密
	 * 
	 * @param pd
	 *            String 解密前值： 不能为null，不能超过50位
	 * @return String 解密后值
	 * 
	 */
	public static String decode(String pd) {
		return priDecode(pd);
	}

	/**
	 * 
	 * 加密
	 * 
	 * @param pd
	 *            String 加密前值 ：不能为null，不能超过24位
	 * 
	 * @return String 加密后值
	 * 
	 */
	public static String encrypt(String pd) {
		return priEncrypt(pd);
	}

	/**
	 * 
	 * 解密
	 * 
	 * @param pd
	 *            String 解密前值： 不能为null，不能超过50位
	 * @return String 解密后值
	 * 
	 */
	private static String priDecode(String pd) {
		if (pd == null || pd.length() > 50) {
			throw new IllegalArgumentException("参数不合法");
		}

		String value;
		char[] A1 = new char[50];
		char[] fname1 = new char[50];
		char[] A2 = new char[50];
		int t2[] = { 5, 14, 12, 9, 11, 2, 0, 15, 7, 3, 4, 13, 1, 6, 10, 8 };
		char t3[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		int i = 0, j = 0, k, p, q;
		int key = 32;
		int temp;
		fname1 = pd.toCharArray();
		k = pd.length();
		while ((i < k) && (fname1[i] != '\0')) {
			A1[j] = fname1[i];
			p = 0;
			while ((p < 16) && (A1[j] != t3[p++]))
				;
			A2[j] = fname1[i + 1];
			q = 0;
			while ((q < 16) && (A2[j] != t3[q++]))
				;
			A1[j] = (char) (t2[p - 1] * 16 + t2[q - 1]);
			temp = A1[j];
			temp = temp ^ key;
			A1[j] = (char) temp;
			i = i + 2;
			j++;
		}
		A1[j] = 0;
		value = String.valueOf(A1).trim();
		return (value);
	}

	/**
	 * 
	 * 加密
	 * 
	 * @param pd
	 *            String 加密前值 ：不能为null，不能超过24位
	 * 
	 * @return String 加密后值
	 * 
	 */
	private static String priEncrypt(String pd) {
		if (pd == null || pd.length() > 24) {
			throw new IllegalArgumentException("参数不合法");
		}

		String value;
		char[] A1 = new char[50];
		char[] A2 = new char[50];
		int t1[] = { 6, 12, 5, 9, 10, 0, 13, 8, 15, 3, 14, 4, 2, 11, 1, 7 };
		char t3[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		int i = 0, j = 0, x, y, k;
		int key = 32;
		byte temp;

		A1 = pd.toCharArray();
		k = pd.length();
		while (j < k) {
			temp = (byte) A1[j];
			temp = (byte) ((temp) ^ key);
			A1[j] = (char) temp;

			x = temp / 16;
			y = temp % 16;
			A2[i++] = t3[t1[x]];

			A2[i++] = t3[t1[y]];

			j++;
		}
		A2[i] = '\0';
		value = String.valueOf(A2).trim();
		return (value);
	}

	// public static void main(String[] arg) {
	//
	// long s = System.currentTimeMillis();
	// for (int i = 0; i < 100000; i++) {
	// String sss = "123adbdd.123"+i;
	// String ss = encrypt(sss);
	// if (!(sss).equals(decode(ss))) {
	// System.out.println(ss + ";" + i);
	// } else {
	//// System.out.println(ss + ";" + sss + ";" + decode(ss));
	//// System.out.println(ss.length());
	// }
	// }
	//
	// System.out.println("总体时间：" + (System.currentTimeMillis() - s));
	// }
}
