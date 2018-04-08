package com.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 终端IP
 * 
 * @author zhouxy
 *
 */
public class NetworkUtil {
	private static Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

	/**
	 * 
	 * 获取用户使用的网络终端IP
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 */
	public static String getIpAddress(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		if (logger.isInfoEnabled()) {
			logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (isNull(ip)) {
			if (isNull(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (isNull(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (isNull(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (isNull(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (isNull(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else {
			// 通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
			// 取X-Forwarded-For中第一个非unknown的有效IP字符串
			String[] ips = ip.split(",");
			for (String strIp : ips) {
				if (!isNull(strIp)) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * 
	 * 是否为null或unknown
	 * 
	 * @param ip
	 * @return
	 */
	private static boolean isNull(String ip) {
		return (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) ? true : false;
	}
}
