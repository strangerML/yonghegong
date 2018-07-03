package com.xcjy.infra.exception;

/**
 * @author Nick Zhang
 * @version 1.0
 * @created on:2013年11月20日 下午2:42:51
 * 系统异常
 */
public class SystemException extends BaseException {

	private static final long serialVersionUID = 1612015782926248922L;

	public SystemException() {
		super();
	}

	public SystemException(Throwable cause) {
		super(cause);
	}
	
	public SystemException(String message) {
		super(message);
	}
	
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(ExceptionMessage exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
	}

	public SystemException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}

}
