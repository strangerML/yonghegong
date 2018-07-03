package com.xcjy.wechat.config.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.xcjy.wechat.config.mvc.WebConfig;
import com.xcjy.wechat.config.spring.AppConfig;

/**
 * 容器启动及初始化类
 * 
 * @author 支亚州
 *
 */
@Order(1)
public class SpringWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

	}

	/**
	 * 指定总的配置类
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	/**
	 * 指定mvc配置类
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	/**
	 * 指定DispatcherServlet的url
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 配置第三方filter
	 */
	@Override
	protected Filter[] getServletFilters() {
		/**
		 * UTF-8编码filter
		 */
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

	

		// 其他filter
		return new Filter[] { characterEncodingFilter
				// 其他filter
		};
	}

}
