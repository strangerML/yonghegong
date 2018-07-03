package com.xcjy.infra.utils.email;


/*
 * 163邮箱的收取邮件支持POP/IMAP两种协议，发送邮件采用SMTP协议，收件和发件均使用SSL协议来进行加密传输，采用SSL协议需要单独对帐户进行设置。采用SSL协议和非SSL协议时端口号有所区别，参照下表的一些常见配置组合:
 * 	类型		服务器名称		服务器地址			SSL协议端口号	非SSL协议端口号
 * 收件服务器	 POP		pop.163.com			995			110
 * 收件服务器	 IMAP		imap.163.com		993			143
 * 发件服务器	 SMTP		smtp.163.com		465/994		25

 */

import java.util.Properties;

/**
 * 发送邮件需要使用的基本信息
 */
public class MailSenderInfo {
	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";

	// 邮件发送者的地址
	private String fromAddress;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = true;

	// 邮件接收者的地址
	private String toAddress;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;

	// 邮件附件的文件名
	private String[] attachFileNames;

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

}