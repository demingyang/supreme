package com.richgo.push.jiguang;

import cn.jiguang.common.DeviceType;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.richgo.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 极光推送处理类
 * @author shenkun
 * @date 2017年5月9日
 */
public class PushExecutor {
	
	
	protected static JPushClient _jPushClient = null;
	
	
	private static Logger log = LoggerFactory.getLogger(PushExecutor.class);

	public  static PushDeviceParent _device=null;
	
	/**
	 * 无参数构造默认android和ios同时接收
	 */
	public PushExecutor() {

		if(_device == null || _jPushClient == null) {

			_device = new PushDeviceParent();

			init();

			log.info("极光客户端初始化完成");
		}
	}


	/**
	 * 通过DeviceType指定接收设备平台
	 * @param diviceType
	 */
	public PushExecutor(DeviceType diviceType){
		if(diviceType == DeviceType.Android){
			_device = new PushAndroid();
			 
		}else if(diviceType == DeviceType.IOS){
			_device = new PushIOS();
		}
		
		init();
	}
	
	private void init(){
		
		try{
		
		 
			String online = ConfigUtil.getValue("JiguangOnline");
				
			if(online.equals("true")){
				_jPushClient = new JPushClient(ConfigUtil.getValue("JiguangMasterSecret"),ConfigUtil.getValue("JiguangAppKey"));

				log.info("极光以正式环境初始化");
			}else{
				_jPushClient = new JPushClient(ConfigUtil.getValue("JiguangMasterSecret_test"),ConfigUtil.getValue("JiguangAppKey_test"));

				log.info("极光以测试环境初始化");
			}
		}catch(Exception e){
			log.error("极光推送初始化出现异常",e);

		}
		
	}
	
	/**
	 * 执行消息推送
	 */
	private boolean excutePush(PushPayload pushPayload) throws Exception {


		boolean result =false;
        PushResult pushResult = _jPushClient.sendPush(pushPayload);
        if (pushResult != null && pushResult.isResultOK()) {

            result = true;
        }else{
        	result =false;
		}
		if(pushResult != null) {
			log.info("推送结果：{} {} {} {} {} {}", pushResult.isResultOK(),pushResult.msg_id,pushResult.sendno,pushResult.statusCode, pushResult.getOriginalContent(), pushResult.getResponseCode());
		}else{
        	log.info("推送结果对象为空");
		}

		return result;
	}
	
	
	/**
	 * 
	 * 推送app内消息
	 * 
	 * @param aliasId
	 *            设备别名
	 * @param msgContent
	 *            消息内容
	 * 
	 */
	public boolean push(String aliasId, String msgContent) throws Exception {

		Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId))
				.setMessage(Message.newBuilder().setMsgContent(msgContent).build());
			
		_device.setOption(builder);
		
		PushPayload pushPayload = builder.build();

		return excutePush(pushPayload);
	}

	

	
	/**
	 * 
	 * 推送通知和消息，不包含(自定义标题)
	 * 
	 * @param aliasId
	 *            设备别名
	 * @param alert
	 *            通知
	 * @param msgContent
	 *            自定义消息内容
	 * 
	 */
	public boolean push(String aliasId, String alert, String msgContent) throws Exception {

		 if(StringUtils.isEmpty(aliasId)){
		 	throw new Exception("别名不能为空,推送消息失败,消息:"+msgContent);
		 }

		 Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId)).setNotification(Notification.alert(alert))
				.setMessage(Message.newBuilder().setMsgContent(msgContent).build());

		_device.setOption(builder);
		 
		PushPayload pushPayload = builder.build();
				 
		return excutePush(pushPayload);

	}

	/**
	 *
	 * 推送通知和消息，不包含(自定义标题)
	 *
	 * @param aliasId
	 *            设备别名
	 * @param alert
	 *            通知
	 * @param appMessage
	 *            自定义消息内容
	 *  {"type":"A","code":"A01001","nonce":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","msg":"这是一条日程消息"}
	 *  {"type":"C","code":"C06001","nonce":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","msg":"这是一条客户消息"}
	 *  {"type":"P","code":"P02001","nonce":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","msg":"这是一条产品消息"}
	 *
	 */
	public boolean push(String aliasId, String alert, CustomMessage appMessage) throws Exception {


		if(StringUtils.isEmpty(aliasId)){
			throw new Exception("别名不能为空,推送消息失败,消息:"+ JSON.toJSONString(appMessage));
		}

		Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId)).setNotification(Notification.alert(alert))
				.setMessage(Message.newBuilder().setMsgContent(JSON.toJSONString(appMessage)).build());

		_device.setOption(builder);

		PushPayload pushPayload = builder.build();

		return excutePush(pushPayload);

	}

	/**
	 *
	 * 推送通知和消息，不包含(自定义标题)
	 *
	 * @param aliasId
	 *            设备别名
	 * @param alert
	 *            通知
	 * @param appMessage
	 *            自定义消息内容
	 *  {"type":"A","code":"A01001","nonce":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","msg":"这是一条日程消息"}
	 *  {"type":"C","code":"C06001","nonce":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","msg":"这是一条客户消息"}
	 *  {"type":"P","code":"P02001","nonce":"5K8264ILTKCH16CQ2502SI8ZNMTM67VS","msg":"这是一条产品消息"}

	 * @param map
	 *            自定义参数的内容
	 *            {"type":"P","texttype":"1","msgid":"1234","extraurl":""}
	 *            {"type":"P","texttype":"2","msgid":"","extraurl":"http://www.baidu.com"}
	 *
	 * @param needNotification
	 *            是否需要通知
	 *
	 */
	public boolean push(String aliasId, String alert, CustomMessage appMessage,Map<String, String> map,boolean needNotification) throws Exception {


		if(StringUtils.isEmpty(aliasId)){
			throw new Exception("别名不能为空,推送消息失败,消息:"+ JSON.toJSONString(appMessage));
		}

		Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId))
				.setMessage(Message.newBuilder().setMsgContent(JSON.toJSONString(appMessage)).build());

		if(needNotification) {
			_device.setNotification(builder, alert, map);
		}

		_device.setOption(builder);

		PushPayload pushPayload = builder.build();

		return excutePush(pushPayload);

	}


	/**
	 * 
	 * 推送消息,包含(通知、自定义内容(app内)、自定义参数)
	 * 
	 * @param aliasId
	 *            手机设备在极光绑定的设备别名
	 * @param alert
	 *            通知，显示在手机通知栏中的内容
	 * @param msgContent
	 *            自定义消息的内容
	 * @param map
	 *            自定义参数的内容
	 * 
	 */
	public boolean push(String aliasId, String alert, String msgContent, Map<String, String> map) throws Exception {


		Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId))
				.setMessage(Message.newBuilder().setMsgContent(msgContent).build());

		_device.setNotification(builder,alert, map);
		
		_device.setOption(builder);

		PushPayload pushPayload = builder.build();
		 
		return excutePush(pushPayload);

	}

	/**
	 * 
	 * 推送消息,包含(通知、标题(android)、自定义内容(app内)、自定义参数)
	 * TODO 标题 有问题,未起效果
	 * 
	 * @param aliasId
	 *            手机设备在极光绑定的设备别名
	 * @param alert
	 *            通知，显示在手机通知栏中的内容
	 * @param title
	 *            标题
	 * @param msgContent
	 *            自定义消息的内容
	 * @param map
	 *            自定义参数的内容
	 * 
	 */
	public boolean push(String aliasId, String alert, String msgContent,String title, Map<String, String> map) throws Exception{


		Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId))
				.setMessage(Message.newBuilder().setMsgContent(msgContent).build());

		if(map == null && !StringUtils.isEmpty(title)){
			_device.setNotification(builder,alert,title);
		}else{
			_device.setNotification(builder,alert,title, map);
		}
		
		_device.setOption(builder);

		PushPayload pushPayload = builder.build();
		 
		return excutePush(pushPayload);

	}
	
	/**
	 * 
	 * 推送 只包含（通知、自定义参数）
	 * 
	 * @Title: sendPush
	 * @param aliasId
	 *            推送别名
	 * @param alert
	 *            通知
	 * @param map
	 *            自定义参数
	 * @return boolean true 成功 false 失败
	 * 
	 */
	public boolean push(String aliasId, String alert, Map<String, String> map) throws Exception{



		Builder builder = PushPayload.newBuilder().setPlatform(_device.getPlatform())
				.setAudience(Audience.alias(aliasId));
		 

	 
		_device.setNotification(builder,alert, map);
		
		_device.setOption(builder);

		PushPayload pushPayload = builder.build();
		 
		return excutePush(pushPayload);

	}


 


}
