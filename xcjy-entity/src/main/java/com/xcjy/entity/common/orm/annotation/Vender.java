package com.xcjy.entity.common.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xcjy.entity.common.orm.enumeration.VenderType;


/**
 * 数据库提供商注解
 * @author WH
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Vender {
	public VenderType value();
}
