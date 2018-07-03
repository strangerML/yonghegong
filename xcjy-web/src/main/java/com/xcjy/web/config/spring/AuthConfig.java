package com.xcjy.web.config.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xcjy.application.authority.realm.SpringAuthorizingRealm;

/**
 * shiro授权配置
 * 
 * @author 支亚州
 *
 */
@Configuration
public class AuthConfig {

	@Autowired
	private SpringAuthorizingRealm springAuthorizingRealm;

	/**
	 * shiroFilter
	 * 
	 * @return
	 */
	@Bean
	@Qualifier("shiroFilter")
	public Object shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setLoginUrl("/signIn");// 登录URL
		shiroFilter.setSuccessUrl("/index");
		shiroFilter.setSecurityManager(securityManager());
		/**
		 * URL访问权限配置
		 */
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();

		// 业务相关js过滤
		filterChainDefinitionMap.put("/js/templates/**", "authc");

		// 登录请求免过滤
		filterChainDefinitionMap.put("/gologinpage", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/kaptcha.jpg", "anon");
		// 资源文件免过滤
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		// 错误页面免过滤
		filterChainDefinitionMap.put("/403", "anon");
		filterChainDefinitionMap.put("/404", "anon");
		filterChainDefinitionMap.put("/500", "anon");

		// 其它都过滤
		filterChainDefinitionMap.put("/**", "authc");
		// 其它权限的配置
		// ........
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		Object targetFilter = null;
		try {
			targetFilter = shiroFilter.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetFilter;
	}

	/**
	 * 权限管理器
	 * 
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(springAuthorizingRealm);
		securityManager.setCacheManager(cacheManager());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	/**
	 * 缓存管理器
	 * 
	 * @return
	 */
	@Bean
	public CacheManager cacheManager() {
		net.sf.ehcache.CacheManager ehCacheManager = net.sf.ehcache.CacheManager
				.create(this.getClass().getResourceAsStream("/ehcache-shiro.xml"));
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManager(ehCacheManager);
		return cacheManager;
	}

	/**
	 * 会话设置
	 * 
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);// 设置session超时时间，单位（毫秒）
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}

	/**
	 * 设置sessionid cookie
	 * 
	 * @return
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		SimpleCookie sessionIdCookie = new SimpleCookie();
		sessionIdCookie.setName("shirosid");
		sessionIdCookie.setHttpOnly(true);
		sessionIdCookie.setMaxAge(180000);
		return sessionIdCookie;
	}

}
