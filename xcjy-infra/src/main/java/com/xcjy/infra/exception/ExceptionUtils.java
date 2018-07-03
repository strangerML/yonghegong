package com.xcjy.infra.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Nick Zhang
 * @version 1.0
 * @created on:2013年11月20日 上午11:41:17
 */
public class ExceptionUtils {

	public static String getFullMessage(Throwable ex) {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isEmpty(ex.getMessage())) {
			builder.append(ex.toString());
		} else {
			builder.append(ex.getMessage());
		}
		if (ex.getCause() != null) {
			builder.append(" caused by: [ ").append(getFullMessage(ex.getCause())).append(" ]");
		}
		return builder.toString();
	}
	
}
