package com.richgo.sms;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.richgo.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.richgo.assertions.Assertions;
import com.richgo.cache.CacheConts.PrefixSmsEnum;
import com.richgo.http.HttpResultBean;
import com.richgo.http.HttpUtil;

/**
 * 
 * 短信发送工具类
 * 
 * @author zhouxy
 * 
 */
public class SMSUtil {
	private final static Logger log = LoggerFactory.getLogger(SMSUtil.class);

	/** 用以替换具体验证码 */
	private final static String COV_CD = "######";

	/** 用以替换具体参数 */
	private final static String PARAM_CD = "******";

	/** 登录密码找回 */
	private final static String CD_LOGIN_MISS = "【理顾宝】申请登录密码找回操作，验证码是：###### 。如非本人操作，请忽略此信息。谢谢！";
	/** 注册手机号操作 */
	private final static String REGISTER = "【理顾宝】您正在进行注册帐号操作，验证码是：###### 。如非本人操作，请忽略此信息。谢谢！";

	// 创蓝不需要前缀
	/** 登录密码找回 */
	private final static String CD_LOGIN_MISS2 = "申请登录密码找回操作，验证码是：###### 。如非本人操作，请忽略此信息。谢谢！";
	/** 注册手机号操作 */
	private final static String REGISTER2 = "您正在进行注册帐号操作，验证码是：###### 。如非本人操作，请忽略此信息。谢谢！";


	//老带新活动发送短信


	//新客户认证成功
	private final static String INVESTOR_SUCCESS="【恒天财富】尊敬的客户您好，您的朋友######已完成合格投资者认证，您已获得100恒乐汇积分。";
	///新客户投资成功
	private final static String  INVESTMENT_SUCCESS_NEW="【恒天财富】尊敬的客户######您好，恭喜您成交！您已获得1000恒乐汇积分。";
	//老客户邀请新客户投资成功
	private final static String INVESTMENT_SUCCESS_OLD="【恒天财富】尊敬的客户您好，恭喜您的朋友######完成了一笔交易，您已获得1000恒乐汇积分";
	//注册向新用户发送短信信息
	private final static String REGISTER_TO_NEW_INFO="【恒天财富】尊敬的投资者您好，恭喜您已经注册成功！根据国家相关法律法规，请您不要忘记在恒天金服填写《合格投资者认证》。";
	//向老用户发送邀请注册成功信息
	private final static String REGISTER_TO_OLD_INFO="【恒天财富】尊敬的客户您好，您的朋友######完成了注册，您已获得100恒乐汇积分。";
	//新用户认证
    private final static String INVESTOR_SUCCESS_TO_NEW="【恒天财富】尊敬的投资者您好，恭喜您已经完成认证，可以正常选购产品。";
	//积分过期前提醒
	private final static String REMIND_EXPIRED_POINTS="【恒天财富】尊敬的投资者，您好！您有######积分，将于******过期，请您及时消费。";
	/** 天信易博：发短信URL */
	private final static String SMS_SEND_URL = ConfigUtil.getValue("SMS_SEND_URL_TIANXIN");
	/** 天信易博：用户名 */
	private final static String SMS_USERNAME = ConfigUtil.getValue("SMS_USERNAME_TIANXIN");
	/** 天信易博：密码 */
	private final static String SMS_PASSWORD = ConfigUtil.getValue("SMS_PASSWORD_TIANXIN");

	/** 创蓝文化：发短信URL */
	private final static String SMS_SEND_URL1 = "http://222.73.117.156/msg/index.jsp";
	/** 创蓝文化：用户名 */
	private final static String SMS_USERNAME1 = "";
	/** 创蓝文化：密码 */
	private final static String SMS_PASSWORD1 = "";

	/** 云片网络: */
	private final static String YUNPIAN_URI_TPL_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
	/** 云片网络: apikey */
	private final static String YUNPIAN_APIKEY = "";

	// 编码格式。发送编码格式统一用UTF-8
	private static String ENCODING = "UTF-8";

	/**
	 * 
	 * 天信科技：发送短信
	 * 
	 * @param phones
	 *            String 手机号
	 * @param aucd
	 *            String 验证码
	 * @param smsEnum
	 *            SmsEnum 类型
	 * @param count
	 *            int 发送次数
	 *
	 * @return String 返回状态码: 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息” 预约时间格式不正确
				*
	 */
		public static boolean sendAuCd(String phones, String aucd, PrefixSmsEnum smsEnum, int count) {
			Assertions.notEmpty("phones", phones);
		//Assertions.notEmpty("aucd", aucd);
		Assertions.notNull("smsEnum", smsEnum);

		// 返回值
		boolean ret = false;

		// 发送信息
		String msg = null;
		// if (count % 2 == 0) {
		// // 创蓝科技
		// msg = getMsg(smsEnum, false, aucd);
		// ret = chuanglanSendMsg(phones, msg);
		// } else {
		// try {
		// //云片发送
		// String tpl_value = URLEncoder.encode("#code#",ENCODING) +"="
		// + URLEncoder.encode(aucd, ENCODING);
		//
		//
		// ret = tplSendSms(yunpianGetTpl(smsEnum), tpl_value, phones);
		//
		// } catch (Exception e) {
		// log.error("云片发送短信出现异常:"+e.getMessage());
		// }
		//
		// //如果云片发送失败,用天信翼博发
		// if(!ret){
		// // sendMessage
		// msg = getMsg(smsEnum, true, aucd);
		// ret = tianxinyiboSendMessage(phones, msg);
		// }
		// }
		// sendMessage
		msg = getMsg(smsEnum, true, aucd);
		ret = tianxinyiboSendMessage(phones, msg);

		return ret;
	}

	/**
	 *
	 * 天信科技：发送短信
	 *
	 * @param phones
	 *            String 手机号
	 * @param aucd
	 *            String 参数一
	 * @param param
	 *            String 参数二
	 * @param smsEnum
	 *            SmsEnum 类型
	 * @param count
	 *            int 发送次数
	 *
	 * @return String 返回状态码: 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息” 预约时间格式不正确
	 *
	 */
	public static boolean sendAuCd(String phones, String aucd,String param, PrefixSmsEnum smsEnum, int count) {
		Assertions.notEmpty("phones", phones);
		//Assertions.notEmpty("aucd", aucd);
		Assertions.notNull("smsEnum", smsEnum);
		// 返回值
		boolean ret = false;
		// 发送信息
		String msg = getMsg(smsEnum, true, aucd,param);
		ret = tianxinyiboSendMessage(phones, msg);
		return ret;
	}

	/**
	 * 云片的短信模版选择
	 * 
	 * @param smsEnum
	 * @return
	 * @author Victor
	 * @date 2016年8月31日
	 */
	private static long yunpianGetTpl(PrefixSmsEnum smsEnum) {
		long tpl = 1;
		switch (smsEnum) {
		case smsMssPwd:// 登录密码找回
			tpl = 1546022;
			break;
		case smsBndTel:// 绑定手机号
			tpl = 1546024;
			break;
		case register:// 注册手机号
			tpl = 1575280;
			break;
		default:
		break;
	}
		return tpl;
	}

	// TODO 需要改
	/**
	 * 
	 * 根据短信服务商和业务类型返回对应的短信内容
	 * 
	 * @param smsEnum
	 *            PrefixSmsEnum
	 * @param needProfix
	 *            boolean 是否需要前缀：true：需要；false：不需要
	 * @param aucd
	 *            String 验证码
	 * @return String 短信内容或null
	 */
	private static String getMsg(PrefixSmsEnum smsEnum, boolean needProfix, String aucd) {
		// 发送信息
		String msg = null;
		switch (smsEnum) {
		case smsMssPwd:// 登录密码找回
			if (needProfix) {// 需要前缀
				msg = CD_LOGIN_MISS;
			} else {
				msg = CD_LOGIN_MISS2;
			}
			break;
		case register:// 注册
			if (needProfix) {// 需要前缀
				msg = REGISTER;
			} else {
				msg = REGISTER2;
			}
			break;

		case investorSuccess:
			msg=INVESTOR_SUCCESS;
			break;
		case investmentSuccessNew:
			msg=INVESTMENT_SUCCESS_NEW;
			break;
		case investmentSuccessOld:
			msg=INVESTMENT_SUCCESS_OLD;
			break;
		case registerToNewInfo:
			msg=REGISTER_TO_NEW_INFO;
			break;
		case  registerToOldInfo:
			msg=REGISTER_TO_OLD_INFO;
			break;
		case investorSuccessToNew:
			msg=INVESTOR_SUCCESS_TO_NEW;
			break;
		case remindExpiredPoints:
			msg=REMIND_EXPIRED_POINTS;
		default:
			break;
		}
		Boolean ble=StringUtils.isNotBlank(msg)&&StringUtils.isNotBlank(aucd);
		if (ble) {
			// 替换######
			msg = msg.replace(COV_CD, aucd);
		}
		return msg;
	}


	/**
	 *
	 * 根据短信服务商和业务类型返回对应的短信内容（2个参数）
	 *
	 * @param smsEnum
	 *            PrefixSmsEnum
	 * @param needProfix
	 *            boolean 是否需要前缀：true：需要；false：不需要
	 * @param aucd
	 *            String 第一个参数
	 * @param param
	 *            String 第二个参数
	 * @return String 短信内容或null
	 */
	private static String getMsg(PrefixSmsEnum smsEnum, boolean needProfix, String aucd,String param) {
        String msg = getMsg(smsEnum,needProfix,null);
		// 发送信息
		Boolean ble=StringUtils.isNotBlank(msg)&&StringUtils.isNotBlank(aucd) && StringUtils.isNotBlank(param) ;
		if (ble) {
			// 替换######
			msg = msg.replace(COV_CD, aucd);
			msg = msg.replace(PARAM_CD, param);
		}
		return msg;
	}

	/**
	 * 
	 * 天信科技：发送短信
	 * 
	 * @param phones
	 *            String 手机号
	 * @param msg
	 *            String 发送短信内容
	 * 
	 * @return String 返回状态码: 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息” 预约时间格式不正确
	 * 
	 */
	private static boolean tianxinyiboSendMessage(String phones, String msg) {

		if (StringUtils.isBlank(phones) || StringUtils.isBlank(msg)) {
			throw new IllegalArgumentException("参数为空.phones=" + phones + ";msg=" + msg);
		}

		try {
			String repGBK = "GBK";
			msg = URLEncoder.encode(msg, repGBK);

			// 请求参数拼装
			Map<String, String> paraMap = new HashMap<String, String>();
			// 用户名
			paraMap.put("cpName", SMS_USERNAME);
			// 密码
			paraMap.put("cpPwd", SMS_PASSWORD);
			// 手机号
			paraMap.put("phones", phones);
			// 发送内容
			paraMap.put("msg", msg);
			// 用户流水号,为空表示立即发送
			// paraMap.put("spCode", "");

			// 发送请求
			HttpResultBean retBean = HttpUtil.getHttpPost(SMS_SEND_URL, paraMap, repGBK);

			if (retBean.getHttpStatus() == 200 && "0".equals(retBean.getResultContext())) {// 请求成功
				// 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息”
				return true;
			} else {
				log.error(info(phones, msg, retBean));
				return false;
			}

		} catch (IOException e) {
			log.error("发短信异常。phones=" + phones + ";msg=" + msg, e);
			return false;
		}
	}

	/**
	 * 创蓝科技发送短信
	 * 
	 * @param mobile
	 *            手机号码
	 * @param msg
	 *            发送消息
	 * @return 返回boolean 成功：true 失败：false
	 * @author mayunliang
	 * @date 2015年12月10日 下午8:07:42
	 */
	private static boolean chuanglanSendMsg(String mobile, String msg) {
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(msg)) {
			throw new IllegalArgumentException("参数为空.phones=" + mobile + ";msg=" + msg);
		}
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = null;// 产品ID
		String extno = null;// 扩展码
		try {
			log.info("创蓝发短信开始");
			String returnString = HttpSender.batchSend(SMS_SEND_URL1, SMS_USERNAME1, SMS_PASSWORD1, mobile, msg,
					needstatus, product, extno);
			log.info("创蓝发短信结束");
			String[] str = returnString.split(",");

			if ("0".equals(str[1].substring(0, 1))) {

				return true;
			} else {
				log.error("创蓝发短信异常。phones=" + mobile + ";msg=" + msg + ";返回值：" + returnString);
				return false;
			}
		} catch (Exception e1) {

			log.error("发短信异常。phones=" + mobile + ";msg=" + msg, e1);
			return false;
		}

	}

	/**
	 * 通过模板发送短信(不推荐)
	 *
	 * @param
	 *
	 * @param tpl_id
	 *            模板id
	 * @param tpl_value
	 *            模板变量值
	 * @param mobile
	 *            接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static boolean tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("apikey", YUNPIAN_APIKEY);
			params.put("tpl_id", String.valueOf(tpl_id));
			params.put("tpl_value", tpl_value);
			params.put("mobile", mobile);

			// 发送请求
			String result = HttpUtil.post(YUNPIAN_URI_TPL_SMS, params);

			JSONObject json = JSON.parseObject(result);

			if (json != null && json.get("code") != null && json.get("code").toString().equals("0")) {// 请求成功
				// 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息”
				return true;
			} else {
				log.error(info(mobile, result, null));
				return false;
			}

		} catch (Exception e) {
			log.error("发短信异常。phones=" + mobile + ";msg=" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 
	 * 字段信息
	 * 
	 * @return String
	 */
	private static String info(String phones, String msg, HttpResultBean retBean) {
		StringBuffer buff = new StringBuffer();
		buff.append("发短信失败。phones=");
		buff.append(phones);
		buff.append(" msg=");
		buff.append(msg);
		if (retBean != null) {
			buff.append(" HttpStatus=");
			buff.append(retBean.getHttpStatus());
			buff.append(" ResultContext=");
			buff.append(retBean.getResultContext());
		}

		return buff.toString();
	}

	/**
	 * 
	 * 天信科技：发送短信
	 * 
	 * @param phones
	 *            String 手机号
	 * @param msg
	 *            String 发送短信内容
	 * 
	 * @return String 返回状态码: 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息” 预约时间格式不正确
	 * 
	 */
	public static boolean sendMessage(String phones, String msg) {
		if (StringUtils.isBlank(phones) || StringUtils.isBlank(msg)) {
			throw new IllegalArgumentException("参数为空.phones=" + phones + ";msg=" + msg);
		}
		try {
			String repGBK = "GBK";
			msg = URLEncoder.encode(msg, repGBK);

			// 请求参数拼装
			Map<String, String> paraMap = new HashMap<String, String>();
			// 用户名
			paraMap.put("cpName", SMS_USERNAME);
			// 密码
			paraMap.put("cpPwd", SMS_PASSWORD);
			// 手机号
			paraMap.put("phones", phones);
			// 发送内容
			paraMap.put("msg", msg);
			// 用户流水号,为空表示立即发送
			// paraMap.put("spCode", "");

			// 发送请求
			HttpResultBean retBean = HttpUtil.getHttpPost(SMS_SEND_URL, paraMap, repGBK);

			if (retBean.getHttpStatus() == 200 && "0".equals(retBean.getResultContext())) {// 请求成功
				// 发送成功返回0，如果发送不成功，则返回“ERROR&&对应的错误信息”
				return true;
			} else {
				log.error(info(phones, msg, retBean));
				return false;
			}

		} catch (IOException e) {
			log.error("发短信异常。phones=" + phones + ";msg=" + msg, e);
			return false;
		}
	}

	public static void main(String[] arg) {
//		sendAuCd("13601282654", "0612", PrefixSmsEnum.smsMssPwd, 1);// 天信
	}
}
