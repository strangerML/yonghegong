package com.xcjy.infra.exception;

/**
 * @author Nick Zhang
 * 
 * @createdOn:2013年11月20日 上午11:21:40 
 * 封装异常信息
 */
public class ExceptionMessage {
	
	/**
	 * 异常状态码
	 */
	private String state;
	
	/**
	 * message引用的key example: Role.required=角色 未定义 key为：Role.required
	 */
	private String key;

	/**
	 * message的value中的参数 example: Role.codeError=角色代码 {0} 设置有误 args为：{0}
	 */
	private Object[] args;

	public ExceptionMessage() {

	}

	public ExceptionMessage(String key, Object[] args) {
		this.key = key;
		this.args = args;
	}

	public ExceptionMessage(String state, String key) {
		this.state = state;
		this.key = key;
	}
	
	public ExceptionMessage(String state, String key, Object[] args) {
		this.state = state;
		this.key = key;
		this.args = args;
	}
	
	public ExceptionMessage(String key) {
		this.key = key;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
