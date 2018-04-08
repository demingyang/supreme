package com.chtwm.insurance.agency.base.interceptor;

import com.common.CodeConts;
import com.common.ResultJson;
import com.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * 拦截器
 * 
 * @author zhouxy
 *
 */
public class AuthInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	/*  */
	private static final String contentType = "application/json;charset=utf-8";

	/** 允许通过的url */
	private String[] allowUrls;
	/** 允许通过静态文件 */
	private String[] staticFiles;

	private String[] forbidUrls;

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	public void setStaticFiles(String[] staticFiles) {
		this.staticFiles = staticFiles;
	}

	public void setForbidUrls(String[] forbidUrls) {
		this.forbidUrls = forbidUrls;
	}

	/**
	 *
	 * 在业务处理器处理请求之前被调用，在该方法中对用户请求request进行处理
	 *
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String contxtPath = request.getContextPath();
		String requestUrl = request.getRequestURI().replace(contxtPath, "");

		if (log.isDebugEnabled()) {
			log.debug("preHandle:{}", requestUrl);
		}
		//禁止访问的地址直接返回false
		if(null !=forbidUrls && forbidUrls.length>0){
			for (String url:forbidUrls){
				if(requestUrl.equals(url)){
					return false;
				}
			}
		}

		// 静态文件不需要拦截
		if (null != staticFiles && staticFiles.length >= 1) {
			for (String url : staticFiles) {
				if (requestUrl.startsWith(url)) {
					return true;
				}
			}
		}
		// 允许通过请求
		if (null != allowUrls && allowUrls.length >= 1) {
			for (int i = 0; i < allowUrls.length; i++) {
				String allowUrl = allowUrls[i];
				if (requestUrl.startsWith(allowUrl)) {
					return true;
				}
			}
		}

		// 校验登录
//		String token = request.getHeader("token");
//		if (StringUtils.isBlank(token)) {
//			returnLoginFail(response);
//			return false;
//		}
//
//		String code = RedisClusterUtil.hget(CodeConst.REDIS_USER_LOGIN_TOKENCODE,token);
//		if (StringUtils.isBlank(code)) {
//			returnLoginFail(response);
//			return false;
//		}
//		// 刷新token缓存 60 * 60 * 24 * 7
//		int time = 604800;
//		/*RedisClusterUtil.expire(token, time);
//		RedisClusterUtil.expire("token_" + code, time);*/
//		Map<String,String> codesmap=new HashMap<String,String>();
//		codesmap.put(token,code);
//		RedisClusterUtil.hmset(CodeConst.REDIS_USER_LOGIN_TOKENCODE,codesmap,time);
//		Map<String,String> tokenmap=new HashMap<String,String>();
//		tokenmap.put("token_" + code,token);
//		RedisClusterUtil.hmset(CodeConst.REDIS_USER_LOGIN_CODETOKEN,tokenmap,time);
		return true;
	}

	/**
	 * 
	 * 登录失败
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	private void returnLoginFail(HttpServletResponse response) throws IOException {
		response.getWriter().write(ResultJson.getResultFail(CodeConts.LOGIN_FAILURE));
	}

	/**
	 *
	 * 在DispatcherServlet完全处理完请求后被调用，可以在该方法中进行一些资源清理的操作。
	 *
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 *
	 * 在业务处理器处理完请求后，但是DispatcherServlet向客户端返回请求前被调用，在该方法中对用户请求request进行处理。
	 *
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
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
	private boolean headerPro(HttpServletRequest request) throws IOException {
		return ConfigUtil.checkReferer(request);
	}
}
