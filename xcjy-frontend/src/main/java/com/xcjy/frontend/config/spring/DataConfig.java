package com.xcjy.frontend.config.spring;

import java.beans.PropertyVetoException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.EnableOrm;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
import com.xcjy.entity.common.orm.annotation.Vender;
import com.xcjy.entity.common.orm.enumeration.VenderType;
import com.xcjy.entity.common.orm.mapper.ORMapper;
import com.xcjy.entity.common.orm.mapper.TableMapper;
import com.xcjy.infra.exception.SystemException;
import com.xcjy.infra.utils.clazz.ClassFilter;
import com.xcjy.infra.utils.clazz.PackageUtil;

/**
 * 数据库的配置
 * 
 * @author 支亚州
 *
 */
@Configuration
@EnableOrm(basePackages = "com.xcjy.entity")
@Vender(VenderType.MYSQL)
public class DataConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataConfig.class);

	// 注入数据库连接信息
	@Value("#{configProperties['db.driverClass']}")
	private String driverClass;
	@Value("#{configProperties['db.url']}")
	private String url;
	@Value("#{configProperties['db.userName']}")
	private String userName;
	@Value("#{configProperties['db.password']}")
	private String password;

	/**
	 * 数据库连接池
	 * 
	 * @return c3p0的数据库连接池
	 * @throws PropertyVetoException
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(driverClass);
			dataSource.setJdbcUrl(url);
			dataSource.setUser(userName);
			dataSource.setPassword(password);
			dataSource.setPreferredTestQuery("select 1");
			dataSource.setIdleConnectionTestPeriod(60);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	/**
	 * JdbcTemplate
	 * 
	 * @return
	 */
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}

	/**
	 * namedTemplate
	 * 
	 * @return
	 */
	@Bean
	public NamedParameterJdbcTemplate namedTemplate() {
		return new NamedParameterJdbcTemplate(jdbcTemplate());
	}

	/**
	 * 事务管理器
	 * 
	 * @return
	 */
	@Bean
	@Qualifier("defaultTm")
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	public ORMapper mapper() {
		// 判断是否启用ORM
		boolean isOrm = DataConfig.class.isAnnotationPresent(EnableOrm.class);
		if (!isOrm) {
			logger.warn("missing EnableOrm annotation on DataConfig.class");
			throw new SystemException("ORM config is not open");
		}

		// 获取注解扫描包
		String[] basePackages = DataConfig.class.getAnnotation(EnableOrm.class).basePackages();
		if (basePackages == null || basePackages.length == 0) {
			logger.warn("EnableOrm.basePackages is not set value");
			throw new SystemException("EnableOrm.basePackages is not set value");
		}

		// 判断是否指定数据库类型
		boolean isVender = DataConfig.class.isAnnotationPresent(Vender.class);
		if (!isVender) {
			logger.warn("missing Vender annotation on DataConfig.class");
			throw new SystemException("missing Vender annotation on DataConfig.class");
		}
		// 获取数据库类型
		VenderType venderType = DataConfig.class.getAnnotation(Vender.class).value();

		// 扫描该包及其子包下的所有实体类，并放入list
		List<Class<?>> entities = new ArrayList<Class<?>>();
		for (String basePackage : basePackages) {
			entities = PackageUtil.getClasses(basePackage, new ClassFilter() {

				@Override
				public boolean adopt(Class<?> clazz) {
					if (clazz.isAnnotationPresent(Entity.class)) {
						return true;
					}
					return false;
				}
			});
		}

		// 获取实体类元数据信息
		LinkedHashMap<String, TableMapper> entityMeta = new LinkedHashMap<String, TableMapper>();
		for (Class<?> entity : entities) {
			String className = entity.getName();
			String tableName = "";
			String pkProperty = "";
			LinkedHashMap<String, String> columnMapper = new LinkedHashMap<String, String>();
			// @Entity注解
			tableName = entity.getAnnotation(Entity.class).table();
			if (StringUtils.isEmpty(tableName)) {
				tableName = entity.getSimpleName();
			}
			Field[] fields = entity.getDeclaredFields();
			for (Field field : fields) {
				// @Column注解
				if (field.isAnnotationPresent(Column.class)) {
					String propertyName = field.getName();
					String columnName = field.getAnnotation(Column.class).value();
					// 如果value为空，则列名=属性名
					if (StringUtils.isEmpty(columnName)) {
						columnName = propertyName;
					}
					columnMapper.put(propertyName, columnName);

					// @Id注解
					if (field.isAnnotationPresent(Id.class)) {
						pkProperty = field.getName();
					}

				}

			}
			TableMapper tableMapper = new TableMapper(tableName, pkProperty, columnMapper);
			entityMeta.put(className, tableMapper);
		}

		ORMapper mapper = new ORMapper(true, venderType, entityMeta);

		logger.info("ORMapper building finished");
		return mapper;
	}
}
