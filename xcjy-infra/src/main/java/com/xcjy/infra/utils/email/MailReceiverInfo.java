package com.xcjy.infra.utils.email;

/*
 * 163邮箱的收取邮件支持POP/IMAP两种协议，发送邮件采用SMTP协议，收件和发件均使用SSL协议来进行加密传输，采用SSL协议需要单独对帐户进行设置。采用SSL协议和非SSL协议时端口号有所区别，参照下表的一些常见配置组合:
 * 	类型		服务器名称		服务器地址			SSL协议端口号	非SSL协议端口号
 * 收件服务器	 POP		pop.163.com			995			110
 * 收件服务器	 IMAP		imap.163.com		993			143
 * 发件服务器	 SMTP		smtp.163.com		465/994		25

 */

import java.io.File;
import java.util.Properties;
/**
 * 收邮件的基本信息
 */
public class MailReceiverInfo {
	// 邮件服务器的IP、端口和协议
	private String mailServerHost;
	private String mailServerPort = "110";
	private String protocal = "pop3";
	// 登陆邮件服务器的用户名和密码
	private String userName;
	private String password;
	// 保存邮件的路径
	private String attachmentDir = "C:/temp/";
	private String emailDir = "C:/temp/";
	private String emailFileSuffix = ".eml";
	// 是否需要身份验证
	private boolean validate = true;

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties(){
		Properties p = new Properties();
		p.put("mail.pop3.host", this.mailServerHost);
		p.put("mail.pop3.port", this.mailServerPort);
		p.put("mail.pop3.auth", validate ? "true" : "false");
		return p;
	}
	
	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getAttachmentDir() {
		return attachmentDir;
	}

	public void setAttachmentDir(String attachmentDir) {
		if (!attachmentDir.endsWith(File.separator)){
			attachmentDir = attachmentDir + File.separator;
		}
		this.attachmentDir = attachmentDir;
	}

	public String getEmailDir() {
		return emailDir;
	}

	public void setEmailDir(String emailDir) {
		if (!emailDir.endsWith(File.separator)){
			emailDir = emailDir + File.separator;
		}
		this.emailDir = emailDir;
	}

	public String getEmailFileSuffix() {
		return emailFileSuffix;
	}

	public void setEmailFileSuffix(String emailFileSuffix) {
		if (!emailFileSuffix.startsWith(".")){
			emailFileSuffix = "." + emailFileSuffix;
		}
		this.emailFileSuffix = emailFileSuffix;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
}
