package com.xcjy.infra.utils.beanutils;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xcjy.infra.exception.ExceptionMessage;
import com.xcjy.infra.exception.SystemException;

/**
 * @author huangsong
 * @version 1.0
 * @created on : 2012-10-9 上午9:43:14
 * 
 *          封装apache PropertyUtils的方法，将其抛出的异常转换为系统本身的SystemException
 */
public class PropertyUtils {

	private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	/**
	 * 将源对象的属性copy到目标对象。如果出现异常将其转换为系统异常。
	 * 
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            源对象
	 */
	public static void copyProperties(Object dest, Object orig) {
		try {
			if (orig == null || dest == null)
				return;
			org.apache.commons.beanutils.PropertyUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemException(new ExceptionMessage("system.busy"));
		}
	}

	/**
	 * 取数据类型
	 * 
	 * @param bean
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getPropertyType(Object bean, String name) {
		Class claz = null;
		try {
			claz = org.apache.commons.beanutils.PropertyUtils.getPropertyType(bean, name);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SystemException(new ExceptionMessage("system.busy"));
		}
		return claz;
	}

	/**
	 * 实例化某个对象，用指定属性填充
	 * 
	 * @param clazz
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T initObject(Class<T> clazz, Map<String, Object> properties) {
		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (Exception e) {
			logger.error("初始化对象失败:", e);
			throw new SystemException(new ExceptionMessage("system.busy"));
		}
		Set<String> keySet = properties.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			setProperty(object, key, properties.get(key));
		}
		return (T) object;
	}

	/**
	 * 用指定属性填充某个对象
	 * 
	 * @param clazz
	 * @param properties
	 * @return
	 */
	public static void setProperties(Object object, Map<String, Object> properties) {
		Set<String> keySet = properties.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			setProperty(object, key, properties.get(key));
		}
	}

	/**
	 * 为指定的bean设置属性
	 * 
	 * @param bean
	 * @param name
	 * @param value
	 */
	public static void setProperty(Object bean, String name, Object value) {
		try {
			org.apache.commons.beanutils.PropertyUtils.setProperty(bean, name, value);
		} catch (Exception e) {
			logger.error("set属性失败:", e);
			throw new SystemException(new ExceptionMessage("system.busy"));
		}
	}

	/**
	 * 取指定属性
	 * 
	 * @param bean
	 * @param name
	 * @return
	 */
	public static Object getProperty(Object bean, String name) {
		Object result = null;
		try {
			result = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, name);
		} catch (Exception e) {
			logger.error("get属性失败:", e);
			throw new SystemException(new ExceptionMessage("system.busy"));
		}
		return result;
	}

}
