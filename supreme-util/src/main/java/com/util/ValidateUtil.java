package com.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 校验工具类
 * 
 * @author zhouxy
 *
 */
public class ValidateUtil {

	/* 邮箱格式 */
	private final static String EMAIL_FORMAT = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	/* 手机格式：13、14、15、17、18 */
	private final static String TEL_FORMAT = "13[0-9]{9}|14[0-9]{9}|15[0-9]{9}|17[0-9]{9}|18[0-9]{9}";
	/* 座机格式 */
	private final static String LINE_TEL_FORMAT = "^(\\d{3,4}-){1}\\d{7,8}(-\\d{3,4})?$";
	/* 8~30:数字、字母组成 */
	private final static String PWD_FORMAT = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,30}$";
	/* 日期格式 */
	private final static String DATE_FARMAT = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

	/* 4~20:数字、字母组成 */
	private final static String LOGIN_FORMAT = "^[a-zA-Z0-9]{4,20}$";

	/* 证件类型判断是否包含非法字符（除 - () 外  ）*/
	private final static String CARD_FORMAT = "[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	/**
	 * 判断字符串是否为日期格式
	 * 
	 * @param strDate
	 * @return
	 * @author zhangsong
	 * @date 2015年8月20日 上午10:15:27
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(DATE_FARMAT);
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 登录名：4~20:数字、字母组成
	 * 
	 * @param login
	 * @return
	 */
	public static boolean loginValidate(String login) {
		return (login != null && login.matches(LOGIN_FORMAT)) ? true : false;
	}

	/**
	 * 
	 * 密码规则：8~30:数字、字母组成
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean pwdValidate(String pwd) {
		return (pwd != null && pwd.matches(PWD_FORMAT)) ? true : false;
	}

	/**
	 * 
	 * 判断手机号是否是13、14、15、17、18开头11位数字
	 * 
	 * @param tel
	 *            String 手机号
	 * @return boolean true：格式正确 ；false：格式不对
	 * 
	 */
	public static boolean isTel(String tel) {
		return (tel != null && tel.matches(TEL_FORMAT)) ? true : false;
	}

	/**
	 * 
	 * 座机格式：区号-座机号[-分机号]<br>
	 * 例：010-3855335-0870 或 010-3855335
	 * 
	 * @param lineTel
	 *            String 座机
	 * @return boolean true：格式正确 ；false：格式不对
	 * 
	 */
	public static boolean isLineTel(String lineTel) {
		return (lineTel != null && lineTel.matches(LINE_TEL_FORMAT)) ? true : false;
	}

	/**
	 * 
	 * 是否是邮箱格式
	 * 
	 * @param email
	 *            String 邮箱
	 * @return boolean true:是；false：不是
	 */
	public static boolean isEmail(String email) {
		return (email != null && email.matches(EMAIL_FORMAT)) ? true : false;
	}

	/**
	 * 
	 * 发送验证码间隔时间是否有效
	 * 
	 * 1分钟有效期
	 * 
	 * @param oldTime
	 *            long 已经发送验证码时间
	 * @return boolean true：有效；false：失效
	 */
	public static boolean authCodeSpaceValid(long oldTime) {
		if (oldTime == -1) {// session没有存储，这次验证码无效
			return false;
		}
		return DateUtil.isValidate(oldTime, 1);
	}

	/**
	 * 
	 * 银行卡中间数字加密
	 * 
	 * @param bankNum
	 *            String
	 * @return String
	 */
	public static String encodeBankNum(String bankNum) {
		if (bankNum != null && bankNum.length() > 7) {
			StringBuffer buff = new StringBuffer();
			buff.append(bankNum.substring(0, 4));
			buff.append("********");
			buff.append(bankNum.substring(bankNum.length() - 4, bankNum.length()));
			return buff.toString();
		} else {
			return bankNum;
		}
	}

	/**
	 * 判断是不是正整数
	 * 
	 * @param num
	 * @return
	 * @author zhangsong
	 * @date 2015年10月13日 下午2:29:44
	 */
	public static boolean isInteger(String num) {
		String rex = "^[0-9]*[1-9][0-9]*$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(num);
		if (num == null || "".equals(num.trim())) {
			return false;
		}
		if (m.matches()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 校验银行卡格式是否在正确
	 * 
	 * @param bankno
	 * @return true/false
	 * @author zhangsong
	 * @date 2015年12月7日 下午4:42:24
	 */
	public static Boolean isBank(String bankno) {
		Boolean ret = false;
		try {
			if (bankno == null) {
				return false;
			}
			if (bankno.length() != 16 && bankno.length() != 19) {
				return false;
			}
			if (!bankno.matches("\\d+")) {
				return false;
			}
			if ("0000000000000000".equals(bankno.trim()) || "0000000000000000000".equals(bankno.trim())) {
				return false;
			}

			String lastNum = bankno.substring(bankno.length() - 1, bankno.length());// 取出最后一位（与luhm进行比较）
			String first15Num = bankno.substring(0, bankno.length() - 1);// 前15或18位
			ArrayList<String> newArr = new ArrayList<String>();
			for (int i = first15Num.length() - 1; i > -1; i--) {// 前15或18位倒序存进数组
				newArr.add(first15Num.substring(i, i + 1));
			}
			ArrayList<String> arrJiShu = new ArrayList<String>();// 奇数位*2的积 <9
			ArrayList<String> arrJiShu2 = new ArrayList<String>();// 奇数位*2的积 >9

			ArrayList<String> arrOuShu = new ArrayList<String>();
			for (int j = 0; j < newArr.size(); j++) {
				if ((j + 1) % 2 == 1) {// 奇数位
					if (Integer.parseInt(newArr.get(j)) * 2 < 9)
						arrJiShu.add((Integer.parseInt(newArr.get(j)) * 2) + "");
					else
						arrJiShu2.add((Integer.parseInt(newArr.get(j)) * 2) + "");
				} else // 偶数位
					arrOuShu.add(newArr.get(j));
			}

			ArrayList<String> jishu_child1 = new ArrayList<String>();// 奇数位*2 >9
																		// 的分割之后的数组个位数
			ArrayList<String> jishu_child2 = new ArrayList<String>();// 奇数位*2 >9
																		// 的分割之后的数组十位数
			for (int h = 0; h < arrJiShu2.size(); h++) {
				jishu_child1.add((Integer.parseInt(arrJiShu2.get(h)) % 10) + "");
				jishu_child2.add((Integer.parseInt(arrJiShu2.get(h)) / 10) + "");
			}

			Integer sumJiShu = 0; // 奇数位*2 < 9 的数组之和
			Integer sumOuShu = 0; // 偶数位数组之和
			Integer sumJiShuChild1 = 0; // 奇数位*2 >9 的分割之后的数组个位数之和
			Integer sumJiShuChild2 = 0; // 奇数位*2 >9 的分割之后的数组十位数之和
			Integer sumTotal = 0;
			for (int m = 0; m < arrJiShu.size(); m++) {
				sumJiShu = sumJiShu + Integer.parseInt(arrJiShu.get(m));
			}
			for (int n = 0; n < arrOuShu.size(); n++) {
				sumOuShu = sumOuShu + Integer.parseInt(arrOuShu.get(n));
			}

			for (int p = 0; p < jishu_child1.size(); p++) {
				sumJiShuChild1 = sumJiShuChild1 + Integer.parseInt(jishu_child1.get(p));
				sumJiShuChild2 = sumJiShuChild2 + Integer.parseInt(jishu_child2.get(p));
			}
			sumTotal = sumJiShu + sumOuShu + sumJiShuChild1 + sumJiShuChild2;
			int k = sumTotal % 10 == 0 ? 10 : sumTotal % 10;
			int luhm = 10 - k;
			if (Integer.parseInt(lastNum) == luhm) {

				return true;
			} else {

				return false;
			}
		} catch (Exception e) {
			return ret;
		}
	}

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}


	/**
	 * 校验是否包含非法字符
	 * @param nonCheckCodeCardId  需校验数据
	 * @return  true  包含特殊字符
	 * 			 false 不包含特殊字符
	 */
	public static boolean specialSymbols(String nonCheckCodeCardId) {
		if(StringUtils.isNotBlank(nonCheckCodeCardId)){
			Pattern p = Pattern.compile(CARD_FORMAT);
			Matcher m = p.matcher(nonCheckCodeCardId);
			return  m.find();
		}
		return true;
	}

	public static void main(String[] args) {
	}

}
