package com.cache;

/**
 * 
 * 缓存常量类
 * 
 * @author zhouxy
 *
 */
public class CacheConts {
	////////////////////////////////////
	//
	// key格式：前缀+业务唯一标识
	// 例：imgAucdlgn123
	//
	////////////////////////////////////

	/**
	 * 
	 * 普通前缀
	 * 
	 * @author zhouxy
	 *
	 */
	public enum PrefixGeneralEnum {
		genLogin, // 登录信息
		genMssPwd// 找回密码用户信息
	}

	/**
	 * 
	 * 次数限制前缀
	 * 
	 * @author zhouxy
	 *
	 */
	public enum PrefixLimitEnum {
		lmtLgnErr, // 登录错误次数限制
		lmtUPwdErr, // 修改密码错误次数限制
		lmtMssPwdErr,//找回密码错误次数限制
		lmtSmsDay// 一天发送短信限制
	}

	/**
	 * 
	 * token前缀
	 * 
	 * @author zhouxy
	 *
	 */
	public enum PrefixTokenEnum {
		tknLgn, // 登录Token
		tknUPwd, // 修改密码Token
		tknMssPwd, // 找回密码token
		tknUpInf, // 修改个人资料token
		tknbdTel// 绑定手机号
	}

	/**
	 * 
	 * 短信前缀
	 * 
	 * @author zhouxy
	 *
	 */
	public enum PrefixSmsEnum {
		smsMssPwd, // 找回密码验证码
		smsBndTel, // 绑定手机号验证码
		smsLstSendTime,//
		register,//注册
		smsReservePay,// 下单验证码
		//老带新活动短信发送
		registerSuccess,  //邀请注册成功
		investorSuccess,  //合格投资者认证成功
		investmentSuccessNew, //新客户投资成功
		investmentSuccessOld,  //老客户邀请新客户投资成功
		registerToNewInfo, //向新客户发送注册成功信息
		registerToOldInfo,  //向老客户发送邀请成功信息
		investorSuccessToNew, //向新用户发送合格投资认证成功
		remindExpiredPoints//积分过期前提醒
	}

	/**
	 * 
	 * 图片验证码前缀
	 * 
	 * @author zhouxy
	 *
	 */
	public enum PrefixImgAucdEnum {
		aucdLgn, // 登录验证码
		aucdReg, // 注册验证码
		aucdUPwd, // 修改密码验证码
		aucdAddAmder
	}

	/* 登录错误次数上限 */
	public static final int LOGIN_ERROR_TOTAL = 10;
	/* 支付密码错误次数上限 */
	public static final int TRADE_ERROR_TOTAL = 5;
	/* 短信发送次数上限 */
	public static final int SMS_CODE_TOTAL = 60;
	/* 图片验证错误次数上限 */
	public static final int IMG_AUCD_ERR_TOTAL = 5;
	/* 验证码有效时间：300秒（5分钟） */
	public static final int AUCD_VALID_TIME = 300;
}
