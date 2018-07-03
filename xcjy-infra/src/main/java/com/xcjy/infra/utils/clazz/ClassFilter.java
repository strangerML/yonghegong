package com.xcjy.infra.utils.clazz;



/**
 * 验证注解的回调函数类
 * 
 * @author binbinccut@163.com
 *
 */
public interface ClassFilter {

	/**
	 * 验证注解的回调函数
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean adopt(Class<?> clazz);
}
