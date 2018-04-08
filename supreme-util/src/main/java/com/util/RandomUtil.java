package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * 随机数工具类
 * 
 * @author zhouxy
 *
 */
public class RandomUtil {
	private final static Random random = new Random();

	/**
	 * 
	 * 生成随机数（字母+数字）
	 * 
	 * @param length
	 *            int 待生成随机数长度
	 * @return String 生成随机数
	 */
	public static String getRandomStr(int length) {
		return String.valueOf(getRandom(length));
	}

	/**
	 * 数字字母组合 获取随机数
	 * 
	 * @param length
	 *            int 随机数长度
	 * @return
	 */
	public static char[] getRandom(int length) {

		char[] ch = new char[length];

		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			boolean isChar = (random.nextInt(2) % 2 == 0);

			if (isChar) { // 字符串
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				ch[i] = ((char) (choice + random.nextInt(26)));
			} else { // 数字
				String s = String.valueOf(random.nextInt(10));
				ch[i] = s.charAt(0);
			}
		}
		return ch;
	}

	/**
	 * 随机分配整数 （发红包使用）
	 * 
	 * @param total
	 *            整数总数
	 * @param num
	 *            分成的份数
	 * @return List<Long> 整数列表
	 * @author gaoyp
	 * @date 2015年11月26日09:00:51
	 */
	public static List<Long> randomAllocationLongList(Long total, Integer num) {
		List<Long> randomInstList = new ArrayList<Long>();

		Random random = new Random();

		for (int i = 1; i < num; i++) {
			// 减去最小值还可以取值的范围
			Long scope = total - (1 * (num - i + 1));
			Integer randomInt;
			// int 最大值是 2147483647 ,随机出来也包括0，所以加上最小值1
			if (scope >= 2147483646l) {
				randomInt = random.nextInt(2147483646) + 1;
			} else if (scope == 0l) {
				randomInt = 1;
			} else {
				randomInt = random.nextInt(scope.intValue()) + 1;
			}
			randomInstList.add(randomInt.longValue());
			total = total - randomInt;
		}
		randomInstList.add(total);
		return randomInstList;
	}

	/**
	 * 
	 * 获取数字类型随机数
	 * 
	 * @param length
	 *            int 待生成随机数长度
	 * @return String 生成随机数
	 * 
	 */
	public static String getRandomNum(int length) {
		StringBuilder strBuff = new StringBuilder();
		for (int i = 0; i < length; i++) {
			strBuff.append(Integer.toString(random.nextInt(10)));
		}
		return strBuff.toString();
	}

	/**
	 * 获取指定位数的随机字符串
	 * @param length 指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		//Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获得32位随机字符串英文+数字
	 * @return
	 */
	public static String getRandom32Str(){
		return getRandomStringByLength(32);
	}
}
