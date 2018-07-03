package com.xcjy.web.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 额外配置
 * 
 * @author 支亚州
 *
 */
@Configuration
@EnableScheduling // 启动定时任务
@EnableTransactionManagement
public class ServiceConfig {

}
