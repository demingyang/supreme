package com.richgo.common;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.richgo.assertions.Assertions;

/**
 * 
 * 返回JSON
 * 
 * @author zhouxy
 *
 */
public class ResultJson {
	/**
	 * 
	 * 获取失败的json格式字符串
	 * 
	 * @param code
	 *            String CodeConts类中值
	 * @return String
	 */
	public static String getResultFail(String code) {
		return getResultFail(code, null);
	}

	/**
	 * 
	 * 获取失败的json格式字符串
	 * 
	 * @param code
	 *            String CodeConts类中值
	 * @param message
	 *            String 信息
	 * @return String
	 */
	public static String getResultFail(String code, String message) {
		Assertions.notEmpty("code", code);

		ResultData resultData = new ResultData();
		resultData.setStatus(code);
		if (StringUtils.isNotBlank(message)) {
			resultData.setMessage(message);
		}
		return JSONObject.toJSONString(resultData);
	}
}
