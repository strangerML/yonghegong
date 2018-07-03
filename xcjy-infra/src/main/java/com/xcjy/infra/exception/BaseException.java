package com.xcjy.infra.exception;

/**
 * @author Nick Zhang
 *
 * @createdOn:2013年11月20日 上午11:05:07
 * 根异常
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4660833078207088860L;
	
	/**
	 * 封装异常信息
	 */
	private ExceptionMessage exceptionMessage;
	public BaseException() {
		super();
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(ExceptionMessage exceptionMessage, Throwable cause) {
		super(cause);
		this.exceptionMessage = exceptionMessage;
	}
	
	public BaseException(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getFullMessage() {
		return ExceptionUtils.getFullMessage(this);
	}
	
	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
}
