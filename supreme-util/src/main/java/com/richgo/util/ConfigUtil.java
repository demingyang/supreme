package com.richgo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * "config.properties"文件信息操作类
 *
 */
public class ConfigUtil {
	/* 日志对象 */
	private final static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	/* 防盗链网址1 */
	private static final String WEB_URL_1 = "web.url1";
	/* 防盗链网址2 */
	private static final String WEB_URL_2 = "web.url2";
	/* 是否初始化： true：初始化过 */
	private static boolean isInit = false;

	/* 属性集合 */
	private static Map<String, String> map = new HashMap<String, String>();
	/** 防盗链url集合 */
	private static Map<String, String> webUrlMap = new HashMap<String, String>();

	static {
		initData();
	}

	/**
	 *
	 * 初始化数据
	 *
	 */
	private static void initData() {
		if (isInit) {
			return;
		}
		isInit = true;

		if (logger.isDebugEnabled()) {
			logger.debug("初始化config.properties开始");
		}

		// 获取文件路径
		String path = FileUtils.getClassPath() + "config.properties";
		// String
		// path="D:/workspace-gxdp/gxdp-parent/gxdp-web/src/main/resources/config.properties";

		// 读文件
		Properties properties = FileUtils.readProperties(path);
		if (properties == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("获取文件信息失败");
			}
			return;
		}

		// 获取所有值
		getValues(properties);

		if (logger.isDebugEnabled()) {
			logger.debug("初始化config.properties结束");
		}

	}

	private static void getValues(Properties prop) {
		// 获取Properties key集合
		Set keyValue = prop.keySet();

		for (Iterator it = keyValue.iterator(); it.hasNext();) {
			Object obj=it.next();
			if(obj==null){
				continue;
			}
			// 键
			String key = String.valueOf(obj);
			// 值
			String value = null;
			if (StringUtils.isNotEmpty(key)) {
				// 值
				value = prop.getProperty(key);
				if (StringUtils.isNotBlank(value)) {
					map.put(key, value);

					if (WEB_URL_1.equals(key) || WEB_URL_2.equals(key)) {
						webUrlMap.put(key, value);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("key={};value={}", key, value);
			}
		}
	}

	/**
	 *
	 * 获取给定key对应的value
	 *
	 * @param key
	 *            String
	 * @return String key对应的value 或null
	 */
	public static String getValue(String key) {
		if (StringUtils.isNoneBlank(key)) {
			return map.get(key);
		}
		return null;
	}

	/**
	 *
	 * 获取给定key对应的value
	 *
	 * @param key
	 *            String
	 * @return String key对应的value 或null
	 */
	public static String getWebUrl(String key) {
		if (StringUtils.isNoneBlank(key)) {
			return webUrlMap.get(key);
		}
		return null;
	}

	/**
	 *
	 * 防盗链处理
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return boolean true:来源属于本网站，false：来源不属于本网站
	 * @throws IOException
	 */
	public static boolean checkReferer(HttpServletRequest request) throws IOException {
		if (request == null) {
			return false;
		}
		// referer为客户端带来的请求头
		String referer = request.getHeader("Referer");
		if (referer == null) {
			return false;
		}

		// true：正常路径来的数据
		boolean flag = false;
		String url1 = webUrlMap.get(WEB_URL_1);
		if (StringUtils.isNotEmpty(url1)) {
			flag = referer.startsWith(url1);
		}

		if (!flag) {
			String url2 = webUrlMap.get(WEB_URL_2);
			if (StringUtils.isNotEmpty(url2)) {
				flag = referer.startsWith(url2);
			}
		}
		return flag;
	}
}