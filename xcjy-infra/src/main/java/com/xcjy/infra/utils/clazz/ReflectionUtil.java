package com.xcjy.infra.utils.clazz;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 * @author WH
 *
 */
public class ReflectionUtil {
	
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(final Class<?> clazz , final int index){
		
		Type genType = clazz.getGenericSuperclass();//获取泛型父类
		/**
		 * 判断父类是否为泛型类
		 */
		if(!(genType instanceof ParameterizedType)){
			return Object.class;
		}
		
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		
		/**
		 * 下标是否越界
		 */
		if(index>=params.length || index<0){
			return Object.class;
		}
		
		/**
		 * 是否是Class
		 */
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		
		return (Class)params[index];
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(final Class<?> clazz){
		return getSuperClassGenericType(clazz, 0);
	}
	
}
