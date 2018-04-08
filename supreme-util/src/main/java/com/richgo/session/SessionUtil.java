package com.richgo.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.richgo.assertions.Assertions;
import com.richgo.cache.CacheConts;

/**
 * 
 * 登录用户信息,存储在session中
 * 
 * @author zhouxy
 *
 */
public class SessionUtil {

	/**
	 * 
	 * 必须在web.xml中配置： <br>
	 * <listener><br>
	 * <listener-class><br>
	 * org.springframework.web.context.request.RequestContextListener
	 * </listener-class><br>
	 * </listener><br>
	 * 
	 * 获取 HttpServletRequest对象
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 
	 * 必须在web.xml中配置： <br>
	 * <listener><br>
	 * <listener-class><br>
	 * org.springframework.web.context.request.RequestContextListener
	 * </listener-class><br>
	 * </listener><br>
	 * 
	 * 获取HttpServletRequest对象
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletResponse getHttpServletResponse() {
		return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 
	 * 获取登录用户信息
	 * 
	 * @param session
	 *            HttpSession
	 * @return
	 * 
	 * @return 用户信息或null
	 * 
	 */
	public static Object getUserInfo(HttpSession session) {
		Assertions.notNull("session", session);
		// 从session中获取用户信息
		return session.getAttribute(getKey());
	}

	/**
	 * 
	 * 用户信息存储到缓存中
	 * 
	 * @param session
	 *            HttpSession
	 * @param t
	 *            T
	 */
	public static void putUserInfo(HttpSession session, Object obj) {
		// 判断参数合法性
		Assertions.notNull("session", session);
		Assertions.notNull("obj", obj);

		// 存储缓存数据
		session.setAttribute(getKey(), obj);
	}

	/**
	 * 
	 * 移除用户信息
	 * 
	 * @param session
	 *            HttpSession
	 * 
	 */
	public static void removeUserInfo(HttpSession session) {
		// 判断参数合法性
		Assertions.notNull("session", session);

		session.removeAttribute(getKey());
	}

	private static String getKey() {
		return CacheConts.PrefixGeneralEnum.genLogin.toString();
	}
}
