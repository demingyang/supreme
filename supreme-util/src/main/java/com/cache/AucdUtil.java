package com.cache;

import com.common.CodeConts;
import com.assertions.Assertions;
import com.cache.EhCacheUtil.CacheTypeEnum;
import com.cache.bean.CacheAucdBean;
import com.util.Tool;

/**
 * 
 * 验证码工具类
 * 
 * @author zhouxy
 *
 */
public class AucdUtil {

	/**
	 * 
	 * 获取主键（前缀+业务标识）
	 * 
	 * @param bsKey
	 *            String 业务标识
	 * @param imgAucdEnum
	 *            PrefixImgAucdEnum
	 * @return String key
	 */
	public static String getkey(String bsKey, CacheConts.PrefixImgAucdEnum imgAucdEnum) {
		Assertions.notEmpty("bsKey", bsKey);
		Assertions.notNull("imgAucdEnum", imgAucdEnum);

		String prefix = imgAucdEnum.toString();
		// 主键
		return Tool.concat(prefix, bsKey);
	}

	/**
	 * 
	 * 修改密码验证码 ：获取主键
	 * 
	 * 前缀+sessionid+登录名
	 * 
	 * @return String
	 * 
	 */
	public static String getUpdatePwdAucdKey(String sessionid) {
		// 前缀+sessionid+登录名
		// 判断参数
		Assertions.notEmpty("sessionid", sessionid);

		return Tool.concat(CacheConts.PrefixImgAucdEnum.aucdUPwd.toString(), sessionid);
	}

	/**
	 * 
	 * 添加验证码信息到缓存
	 * 
	 * @param key
	 *            String 主键
	 * @param aucd
	 *            String 验证码
	 * @return CacheAucdBean
	 * 
	 */
	public static CacheAucdBean putAucd(String key, String aucd) {
		//
		CacheAucdBean bean = new CacheAucdBean(key, aucd);

		putValue(key, bean);
		return bean;
	}

	/**
	 * 
	 * 移除验证码数据信息
	 * 
	 * @param key
	 *            String 键
	 * @return boolean：true：成功；false：失败
	 * 
	 */
	public static boolean removeAucd(String key) {
		// 参数验证
		Assertions.notEmpty("key", key);
		return EhCacheUtil.remove(CacheTypeEnum.generalCache, key);
	}

	/**
	 * 
	 * 判断验证码是否有效,并且清除缓存数据
	 * 
	 * @param key
	 *            String 主键
	 * @param aucd
	 *            String 验证码
	 * @return boolean true:有效；false：无效
	 * 
	 */
	public static boolean isValidAucdAndClear(String key, String aucd) {
		return isValidAucd(key, aucd, true);
	}

	/**
	 * 
	 * 判断验证码是否有效,没有操作上限和超时外错误不清除缓存数据
	 * 
	 * 
	 * @param key
	 *            String 主键
	 * @param aucd
	 *            String 验证码
	 * @return boolean true:有效；false：无效
	 * 
	 */
	public static boolean isValidAucd(String key, String aucd) {
		return isValidAucd(key, aucd, false);
	}

	/**
	 * 
	 * 获取有效的验证码
	 * 
	 * @param key
	 *            String
	 * @param timeout
	 *            int 有效秒数
	 * @return String 对象不存在或超出上限或超时都返回null，有效时返回验证码
	 */
	public static String getValidAucd(String key, int timeout) {
		// 获取缓存数据
		CacheAucdBean bean = getAucd(key);
		if (bean == null) {
			return null;
		}

		if (bean.isGtLimit() || bean.isTimeout(timeout)) {// 超出上限或超时
			return null;
		}
		return bean.getAucd();
	}

	/**
	 * 
	 * 判断验证码是否有效
	 * 
	 * @param key
	 *            String 主键
	 * @param aucd
	 *            String 验证码
	 * @param isClear
	 *            boolean
	 *            true:验证失败时（AUCD_ERROR）清除缓存数据；false：验证失败时（AUCD_ERROR）不清除
	 * 
	 * @return boolean true:有效；false：无效
	 * 
	 */
	static boolean isValidAucd(String key, String aucd, boolean isClear) {
		// 获取缓存数据
		CacheAucdBean bean = getAucd(key);

		return isValidAucd(key, bean, aucd, isClear);
	}

	/**
	 * 
	 * 判断验证码是否有效
	 * 
	 * @param key
	 *            String 主键
	 * @param bean
	 *            CacheAucdBean
	 * @param aucd
	 *            String 验证码
	 * @param isClear
	 *            boolean
	 *            true:验证失败时（AUCD_ERROR）清除缓存数据；false：验证失败时（AUCD_ERROR）不清除
	 * 
	 * @return boolean true:有效；false：无效
	 * 
	 */
	static boolean isValidAucd(String key, CacheAucdBean bean, String aucd, boolean isClear) {
		if (bean == null) {
			return false;
		}

		String retStatus = bean.validAuCd(aucd, CacheConts.AUCD_VALID_TIME);

		// 验证成功为true；失败：false
		boolean ret = false;
		switch (retStatus) {
		// case CodeConts.AUCD_ERROR:// 验证错误，没有超过上限或超时
		// break;
		case CodeConts.AUCD_ERROR_LIMIT:// 验证码错误次数超出限制
		case CodeConts.AUCD_TIMEOUT:// 是否过期
			isClear = true;
			break;
		case CodeConts.SUCCESS:// 验证成功
			ret = true;
			break;
		default:
			break;
		}

		if (isClear) {
			// 清除缓存信息
			removeAucd(key);
		}
		return ret;
	}

	/**
	 * 
	 * 获取验证码信息
	 * 
	 * @param key
	 *            String 主键
	 * @return CacheAucdBean
	 * 
	 */
	private static CacheAucdBean getAucd(String key) {
		Object obj = getValue(key);
		if (obj != null && obj instanceof CacheAucdBean) {
			return (CacheAucdBean) obj;
		}
		return null;
	}

	/**
	 * 
	 * 获取缓存信息
	 * 
	 * @param key
	 *            String 键
	 * @return Object 值
	 */
	private static Object getValue(String key) {
		// 参数验证
		Assertions.notEmpty("key", key);
		// 获取
		return EhCacheUtil.get(CacheTypeEnum.generalCache, key);
	}

	/**
	 * 
	 * 存储信息到缓存中
	 * 
	 * @param key
	 *            String 键
	 * @param value
	 *            Object 值
	 */
	static void putValue(String key, Object value) {
		// 参数验证
		Assertions.notEmpty("key", key);
		Assertions.notNull("value", value);
		EhCacheUtil.put(CacheTypeEnum.generalCache, key, value);
	}
}
