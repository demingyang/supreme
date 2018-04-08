package com.cache;

import com.assertions.Assertions;
import com.cache.bean.CacheAucdBean;
import com.util.Tool;

/**
 * 
 * token工具类
 * 
 * @author zhouxy
 *
 */
public class TokenUtil {

	/**
	 * 
	 * 获取主键（前缀+业务标识）
	 * 
	 * @param bsKey
	 *            String 业务标识
	 * @param tokenEnum
	 *            PrefixTokenEnum
	 * @return String
	 */
	public static String getkey(String bsKey, CacheConts.PrefixTokenEnum tokenEnum) {
		Assertions.notEmpty("bsKey", bsKey);
		Assertions.notNull("tokenEnum", tokenEnum);

		String prefix = tokenEnum.toString();
		// 主键
		return Tool.concat(prefix, bsKey);
	}

	/**
	 * 
	 * 添加token信息到缓存
	 * 
	 * @param key
	 *            String 主键
	 * @param token
	 *            String token
	 * @return CachetokenBean
	 * 
	 */
	public static CacheAucdBean putToken(String key, String token) {
		//
		CacheAucdBean bean = new CacheAucdBean(key, token);

		putValue(key, bean);
		return bean;
	}

	/**
	 * 
	 * 移除token数据信息
	 * 
	 * @param key
	 *            String 键
	 * @return boolean：true：成功；false：失败
	 * 
	 */
	public static boolean removeToken(String key) {
		return EhCacheUtil.remove(EhCacheUtil.CacheTypeEnum.tokenCache, key);
	}

	// /**
	// *
	// * 判断token是否有效,并且清除缓存数据
	// *
	// * @param key
	// * String 主键
	// * @param token
	// * String token
	// * @return boolean true:有效；false：无效
	// *
	// */
	// public static boolean isValidTokenAndClear(String key, String token) {
	// // 参数验证
	// Assertions.notEmpty("key",key);
	//
	// // 获取缓存数据
	// CacheAucdBean bean = getAucd(key);
	//
	// return AucdUtil.isValidAucd(key,bean,token,true);
	// }

	/**
	 * 
	 * 判断token是否有效,没有操作上限和超时外错误不清除缓存数据
	 * 
	 * 
	 * @param key
	 *            String 主键
	 * @param token
	 *            String token
	 * @return boolean true:有效；false：无效
	 * 
	 */
	public static boolean isValidToken(String key, String token) {
		// 参数验证
		Assertions.notEmpty("key", key);

		// 获取缓存数据
		CacheAucdBean bean = getAucd(key);

		return AucdUtil.isValidAucd(key, bean, token, false);
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
		return EhCacheUtil.get(EhCacheUtil.CacheTypeEnum.tokenCache, key);
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
	private static void putValue(String key, Object value) {
		// 参数验证
		Assertions.notEmpty("key", key);
		Assertions.notNull("value", value);
		EhCacheUtil.put(EhCacheUtil.CacheTypeEnum.tokenCache, key, value);
	}
}
