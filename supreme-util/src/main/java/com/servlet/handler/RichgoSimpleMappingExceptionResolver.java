package com.servlet.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.CodeConts;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.common.ResultJson;

/**
 * 
 * 全局处理cotroller未处理异常
 * 
 * @author zhouxy
 *
 */
public class RichgoSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			logger.error("controller error.url=" + request.getContextPath(), ex);

			// //记录异常日志
			// doLog((HandlerMethod) handler, ex);
			// 获取错误json信息
			String retJson = ResultJson.getResultFail(CodeConts.SYS_ERR, ex.getMessage());
			// json 请求返回
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.write(retJson);
			writer.flush();

		} catch (Exception e) {
			logger.error("", e);
		}
		return new ModelAndView();
	}

	// /**
	// * 记录异常日志
	// *
	// * @param handler
	// * HandlerMethod
	// * @param e
	// * Exception
	// */
	// private void doLog(HandlerMethod handler, Exception e) {
	// // 异常信息日志
	// logger.error(handler.getBeanType().getName(), e);
	// }
}