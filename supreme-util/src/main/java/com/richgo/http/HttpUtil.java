package com.richgo.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * http请求通用类
 * 
 * @author zhouxy
 * 
 */
public class HttpUtil {
	private static final Logger log = Logger.getLogger(HttpUtil.class);


    
	/**
	 * 
	 * post方式发送http请求。通过HttpResultBean字段httpStatus：200请求成功
	 * 
	 * @param url
	 *            String 请求地址
	 * 
	 * @param paraMap
	 *            Map<String, String> 发送内容
	 * @param posType
	 *            String 返回内容编译方式"UTF-8","GBK"等
	 * 
	 * @return HttpResultBean 成功返回
	 * @throws IOException
	 *             发送http请求时发生异常
	 * 
	 */
	public static HttpResultBean getHttpPost(String url, Map<String, String> paraMap, String posType)
			throws IOException {
		if (StringUtils.isEmpty(url)) {
			throw new IllegalArgumentException("参数为空。url=" + url);
		}

		// 返回对象
		HttpResultBean bean = new HttpResultBean();

		PostMethod post = null;
		try {
			// 通过HttpClient提交数据
			HttpClient httpclient = new HttpClient();
			// 设置连接超时时间(单位毫秒)
			httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(12000);
			// 设置读数据超时时间(单位毫秒)
			httpclient.getHttpConnectionManager().getParams().setSoTimeout(120000);
			// http编码方式
			httpclient.getParams().setContentCharset("UTF-8");

			// post方式发送请求
			post = new PostMethod(url);
			// post.addRequestHeader("Content-Type", "text/html;charset=UTF-8");

			if (paraMap != null && paraMap.size() > 0) {// 请求参数为空时不传输
				// 添加参数
				post.setRequestBody(addParams(paraMap));
			}
			
			if(log.isInfoEnabled()){
				log.info("HTTP请求开始...");
			}
			// 发送请求
			int retstat = httpclient.executeMethod(post);
			if(log.isInfoEnabled()){
			log.info("http请求完成，httpstatus=" + retstat);
			}
			
			// 返回http状态码
			bean.setHttpStatus(retstat);

			String retStr = post.getResponseBodyAsString();
			if (StringUtils.isNotBlank(retStr)) {
				// 获取响应编码方式，双方约定utf-8
				String charSet = post.getResponseCharSet();
				// 这处理目的如果对方不是提供utf-8编码方式情况下转换成utf-8
				String strRet = new String(retStr.getBytes(charSet), posType);
				// 返回内容
				bean.setResultContext(strRet);
			}
		} catch (IOException e) {
			log.error("发送http请求异常", e);
			throw e;
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		return bean;
	}

	/**
	 * 
	 * 添加参数
	 * 
	 * @param paraMap
	 *            Map<String, String>参数集合
	 * @return NameValuePair[] 待发送参数数组
	 */
	private static NameValuePair[] addParams(Map<String, String> paraMap) {
		// 发送内容
		NameValuePair[] valuePairs = new NameValuePair[paraMap.size()];
		int i = 0;
		for (String key : paraMap.keySet()) {
			if (StringUtils.isNotEmpty(key)) {// key不为空和null
				String value = paraMap.get(key);
				if (value != null) {// 不为null
					valuePairs[i] = new NameValuePair(key, value);
				}
			} else {
				log.error("发送http请求时参数key为空");
			}
			i++;
		}
		return valuePairs;
	}
	
    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                	BasicNameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
    
    /**
     * 不支持https
     * @param urlWithParams
     * @return
     * @throws Exception
     * @author Victor
     * @date 2017年2月24日
     */
    public static String getRequest(String urlWithParams) throws Exception {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
         
        HttpGet httpget = new HttpGet(urlWithParams);   
        //配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()  
                .setConnectionRequestTimeout(50)
                .setConnectTimeout(50)  
                .setSocketTimeout(50).build();  
        httpget.setConfig(requestConfig); 
         
        CloseableHttpResponse response = httpclient.execute(httpget);        
        System.out.println("StatusCode -> " + response.getStatusLine().getStatusCode());
         
        HttpEntity entity = response.getEntity();        
        String jsonStr = EntityUtils.toString(entity);//, "utf-8");
        System.out.println(jsonStr);
        
        httpget.releaseConnection();
        
        return jsonStr;
        
    }
    
}
 
