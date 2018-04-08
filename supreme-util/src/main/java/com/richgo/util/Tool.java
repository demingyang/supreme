package com.richgo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.richgo.assertions.Assertions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 工具类
 * 
 * @author zhouxy
 *
 */
public class Tool {
	private static final Logger log = LoggerFactory.getLogger(Tool.class);

	private Tool() {
	};

	/**
	 * 
	 * 获取UUID，可以作为数据库主键
	 * 
	 * @return String
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 
	 * 获取给定字符串的字节长度，utf-8:汉字为3个字节，字母数字等为一个字节
	 * 
	 * 字符串为null或""时返回0
	 * 
	 * @param text
	 *            字符串
	 * @return int 返回字符串的字节长度
	 */
	public static int getTextLength(String text) {
		try {
			return (text != null) ? text.getBytes("UTF-8").length : 0;
		} catch (UnsupportedEncodingException e) {
			return (text != null) ? text.getBytes().length : 0;
		}
	}

	/**
	 * 
	 * 获取返回前端map
	 * 
	 * @param retStatus
	 *            String 状态码
	 * @param retMsg
	 *            String 信息
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> resultMap(String retStatus, String retMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 返回状态码
		map.put("status", retStatus);
		// 返回信息
		if (retMsg != null) {
			map.put("message", retMsg);
		}
		return map;
	}

	/**
	 * 
	 * 拼装字符串
	 * 
	 * @param params
	 *            String
	 * @return String 拼装后字符串
	 */
	public static String concat(final String... params) {
		Assertions.notNull("params", params);

		StringBuffer buff = new StringBuffer();
		for (String str : params) {
			if (StringUtils.isNotEmpty(str)) {
				buff.append(str);
			}
		}
		return buff.toString();
	}

	/**
	 * 
	 * 获取安全的电话
	 * 
	 * @param tel
	 * @return String
	 */
	public static String getSecureTel(String tel) {
		if (tel != null && tel.length() >= 7) {
			StringBuffer buff = new StringBuffer();
			buff.append(tel.substring(0, 3));
			buff.append("****");
			buff.append(tel.substring(7));
			return buff.toString();
		} else {
			return "";
		}
	}

	/**
	 * 身份证中间12位加密
	 * 
	 * @param uiSn
	 * @return String
	 */
	public static String encodeUiSn(String uiSn) {
		if (uiSn != null && uiSn.length() >= 18) {
			StringBuffer buff = new StringBuffer();
			buff.append(uiSn.substring(0, 2));
			buff.append("************");
			buff.append(uiSn.substring(14, 18));
			return buff.toString();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 银行卡中间位加密
	 * 
	 * @param bankCardNum
	 *            String
	 * @return String
	 */
	public static String encodeBankNum(String bankCardNum) {
		if (bankCardNum != null && bankCardNum.length() > 4) {
			int length = bankCardNum.length();

			StringBuffer buff = new StringBuffer();
			buff.append(bankCardNum.substring(0, 4));
			if (length == 16) {
				buff.append("*******");
			} else {
				buff.append("**********");
			}
			buff.append(bankCardNum.substring(length - 4));

			return buff.toString();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 将一个对象转换成JSONObject对象并输出
	 * 
	 * @param object
	 * @param response
	 */
	public static void outJSONObject(Object object, HttpServletResponse response) {
		Assertions.notNull("object", object);
		// 设置返回类型以及编码方式
		response.setContentType("application/json;charset=utf-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();

			JSON json = null;
			if (object instanceof JSONObject) {// JSONObject
				json = (JSONObject) object;
			} else if (object instanceof Collection) {// 集合
				JSONArray.toJSON(object);
			} else {// 其他
				json = (JSON) JSONObject.toJSON(object);
			}
			out.print(json);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 
	 * 动态生成方法名setXXX
	 * 
	 * @param field
	 *            String 字段名称
	 * @param preStr
	 *            String set or get
	 * @return String
	 */
	public static String dynamicMethodName(String field, String preStr) {

		char c = field.charAt(0);
		if (Character.isUpperCase(c)) {
			return Tool.concat(preStr, field);
		} else {
			String first = String.valueOf(Character.toUpperCase(c));
			return Tool.concat(preStr, first, field.substring(1));
		}
	}

	/**
	 * 
	 * 组装路径
	 * 
	 * @return String
	 */
	public static String concatPath(String path1, String path2) {
		if (StringUtils.isBlank(path1)) {
			return path2;
		} else if (StringUtils.isBlank(path2)) {
			return path1;
		}

		String path = null;
		if (!path1.endsWith("/") && !path2.startsWith("/")) {
			path = Tool.concat(path1, "/", path2);
		} else if ((path1.endsWith("/") && !path2.startsWith("/")) || (!path1.endsWith("/") && path2.startsWith("/"))) {
			path = Tool.concat(path1, path2);
		} else {// path1.endsWith("/") && path2.startsWith("/")
			path = Tool.concat(path1, path2.substring(1));
		}

		return path;
	}

	/**
	 * 将cookie封装到Map里面
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
