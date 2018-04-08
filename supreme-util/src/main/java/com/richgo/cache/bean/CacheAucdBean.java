package com.richgo.cache.bean;

import java.io.Serializable;

import com.richgo.assertions.Assertions;
import com.richgo.cache.CacheConts;
import com.richgo.common.CodeConts;

/**
 * 
 * 验证码bean
 * 
 * @author zhouxy
 * 
 */
public class CacheAucdBean implements Serializable {
	private static final long serialVersionUID = -7303673120702872897L;

	/* 验证码 */
	private String aucd;
	/* 生成时间 */
	private long time;
	/* 验证错误次数 */
	private int errorCount;

	public CacheAucdBean() {

	}

	public CacheAucdBean(String key, String aucd) {
		Assertions.notEmpty("key", key);
		Assertions.notEmpty("aucd", aucd);

		this.aucd = aucd;
		// 初始化当前时间
		this.time = System.currentTimeMillis();
	}

	/**
	 * 
	 * 验证验证码是否有效
	 * 
	 * 
	 * @param sessionAucd
	 *            String 验证码
	 * @param timeout
	 *            int 有效秒数,其中-1表示不限制时间
	 * @param succClear
	 *            boolean true:验证成功清除信息，false：验证成功不清除信息
	 * 
	 * @return String 返回CodeConts常量类对应类型
	 * 
	 */
	public String validAuCd(String code, int timeout) {
		Assertions.notEmpty("code", code);

		// true：超时；false：未超时
		boolean isTimeout = false;
		if (timeout != -1) {// 不限制时间
			isTimeout = isTimeout(timeout);
		}

		// 返回标识
		String ret = null;
		if (isGtLimit()) {// 验证超过给定错误次数
			ret = CodeConts.AUCD_ERROR_LIMIT;
		} else if (isTimeout) {// 是否过期
			ret = CodeConts.AUCD_TIMEOUT;
		} else if (!this.aucd.equalsIgnoreCase(code)) {// 验证码不正确
			// 错误次数加1
			++this.errorCount;
			ret = CodeConts.AUCD_ERROR;
		} else {
			ret = CodeConts.SUCCESS;
		}
		return ret;
	}

	/**
	 * 
	 * 是否超出上限
	 * 
	 * @return boolean true：超出；false：未超出
	 */
	public boolean isGtLimit() {
		return (errorCount >= CacheConts.IMG_AUCD_ERR_TOTAL) ? true : false;
	}

	/**
	 * 
	 * 是否超时
	 * 
	 * @param timeout
	 *            int 有效秒数
	 * @return boolean true：超时；false：有效
	 * 
	 */
	public boolean isTimeout(int timeout) {
		return (time + timeout * 1000) <= System.currentTimeMillis();
	}

	public String getAucd() {
		return aucd;
	}

	public void setAucd(String aucd) {
		this.aucd = aucd;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

}
