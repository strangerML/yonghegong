package com.xcjy.infra.utils;

import com.xcjy.infra.utils.resources.Configuration;

/**
 * Constant values used throughout the application.
 * 
 * @ClassName: Constants
 * @author: QuBinBin
 * @date: 2014-2-8 下午9:31:19
 * 
 */
public class Constants {

	/** 分隔符 逗号',' */
	public static final String DELIMITER = ",";

	/** 分隔符 冒号':' */
	public static final String COLON = ":";

	/** 分隔符 分号';' */
	public static final String SEMICOLON = ";";

	/**
	 * 获取当前用户信息名SESSION 使用方法：UserVO userVO =
	 * (UserVO)request.getSession.getAttribute(ConstantUtil.CURRENT_USER); key
	 * in Session indicating current user
	 */
	public static final String CURRENT_USER = "current_user";

	/** 公共错误页面的错误信息接收参数 */
	public static final String COMMON_ERROR_MSG = "errorMsg";

	/** 发送邮件的服务器的IP */
	public static final String MAIL_SERVERHOST = Configuration.getConfigValue("mail.serverHost");
	/** 发送邮件的服务器的端口 */
	public static final String MAIL_SERVERPORT = Configuration.getConfigValue("mail.serverPort");
	/** 邮件发送者的地址 */
	public static final String MAIL_FROMADDRESS = Configuration.getConfigValue("mail.fromAddress");
	/** 登陆邮件发送服务器的用户名 */
	public static final String MAIL_USERNAME = Configuration.getConfigValue("mail.userName");
	/** 登陆邮件发送服务器的密码 */
	public static final String MAIL_PASSWORD = Configuration.getConfigValue("mail.password");
	/** 是否需要身份验证 */
	public static final boolean MAIL_VALIDATE = Configuration.getConfigBooleanStyle("mail.validate");

	/** 预警定时任务触发时间 */
	public static final String QUARTZ_CRONEXPRESSION = "0 0 6 * * ?";

}
