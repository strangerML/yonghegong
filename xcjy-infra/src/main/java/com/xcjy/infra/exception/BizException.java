package com.xcjy.infra.exception;

/**
 * @author Nick Zhang
 * @version 1.0
 * @created on:2013年11月20日 下午2:37:45
 * 业务异常的根异常
 */
public class BizException extends BaseException {

	private static final long serialVersionUID = 397492315664225486L;

	public BizException() {
		super();
	}

	public BizException(Throwable cause) {
		super(cause);
	}
	
	public BizException(String message) {
		super(message);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BizException(ExceptionMessage exceptionMessage, Throwable cause) {
		super(exceptionMessage, cause);
	}

	public BizException(ExceptionMessage exceptionMessage) {
		super(exceptionMessage);
	}
	
}
