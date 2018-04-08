package com.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 
 * ehcache缓存管理器
 * 
 * @author zhouxy
 *
 */
public class EhCacheUtil {
	/** 日志对象 */
	private final static Logger log = LoggerFactory.getLogger(EhCacheUtil.class);
	/** 缓存管理器 */
	private final static CacheManager manager = CacheManager.create();
	/** 缓存集合 */
	private final static Map<String, Cache> cache = new HashMap<String, Cache>();

	private EhCacheUtil() {
	}

	/**
	 * 
	 * 缓存类型
	 * 
	 * @author zhouxy
	 *
	 */
	public enum CacheTypeEnum {
		generalCache, // 普通缓存（5分钟失效）
		sessionCache, // httpsession缓存(30分钟失效)
		dayCache,// 以天为周期缓存（登录错误次数等）（1天失效）
		tokenCache//token缓存(10分钟失效)
	}

	/**
	 * 
	 * 使用ehcache缓存实现类创建缓存
	 * 
	 * @param cacheName
	 *            String
	 */
	public static void createCache(String cacheName) {
		if (log.isInfoEnabled()) {
			log.info("使用缓存实现类[{}]创建缓存[{}]", "net.sf.ehcache.CacheManager", cacheName);
		}

		Cache tmp = manager.getCache(cacheName);
		cache.put(cacheName, tmp);
	}

	/**
	 * 
	 * put内容到缓存中
	 * 
	 * @param cacheTypeEnum
	 *            CacheTypeEnum 缓存类型
	 * @param key
	 *            String
	 * @param value
	 *            Object
	 * 
	 */
	static void put(CacheTypeEnum cacheTypeEnum, String key, Object value) {
		if (cacheTypeEnum == null || StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("参数不合法");
		}
		Element element = new Element(key, value);

		Cache c = cache.get(cacheTypeEnum.toString());
		if (c == null) {
			log.error("没有名字为{}的缓存", cacheTypeEnum.toString());
			return;
		}
		c.put(element);
	}

	/**
	 * 
	 * 根据缓存名称和key值取出缓存中的数据
	 * 
	 * @param cacheTypeEnum
	 *            CacheTypeEnum 缓存类型
	 * @param key
	 *            String
	 * @return Object key对应数据，缓存不存在或key没有对应值返回null
	 * 
	 */
	static Object get(CacheTypeEnum cacheTypeEnum, String key) {
		if (cacheTypeEnum == null || StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("参数不合法");
		}

		Cache c = cache.get(cacheTypeEnum.toString());
		if (c == null) {
			log.error("没有名字为{}的缓存", cacheTypeEnum.toString());
			return null;
		}
		Element e = c.get(key);

		if (e == null) {
			if (log.isDebugEnabled()) {
				log.debug("缓存中没有名字为{}的元素", key);
			}
			return null;
		}

		return e.getObjectValue();
	}

	/**
	 * 
	 * 删除给定key缓存中的数据
	 * 
	 * @param cacheTypeEnum
	 *            CacheTypeEnum 缓存类型
	 * @param key
	 *            String
	 * @return boolean true:数据存在且被删除；false：没有此数据或缓存不存在
	 * 
	 */
	static boolean remove(CacheTypeEnum cacheTypeEnum, String key) {
		if (cacheTypeEnum == null || StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("参数不合法");
		}

		Cache c = cache.get(cacheTypeEnum.toString());
		if (c == null) {
			log.error("没有名字为{}的缓存", cacheTypeEnum.toString());
			return false;
		}

		return c.remove(key);
	}

	/**
	 * 
	 * 清空缓存中的所有数据
	 * 
	 * @param cacheTypeEnum
	 *            CacheTypeEnum
	 * 
	 */
	static void removeAll(CacheTypeEnum cacheTypeEnum) {
		if (cacheTypeEnum == null) {
			throw new IllegalArgumentException("参数不合法");
		}
		Cache c = cache.get(cacheTypeEnum.toString());
		if (c == null) {
			log.error("没有名字为{}的缓存", cacheTypeEnum.toString());
			return;
		}
		c.removeAll();
	}
}
