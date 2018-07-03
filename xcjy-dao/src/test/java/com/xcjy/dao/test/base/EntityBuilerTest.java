package com.xcjy.dao.test.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.xcjy.dao.base.EntityBuiler;
import com.xcjy.entity.authority.User;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
import com.xcjy.entity.common.orm.mapper.TableMapper;

public class EntityBuilerTest {

	List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

	TableMapper mapper = null;

	Integer max = 100000;

	@Test
	public void testbuildEntity() {
		System.out.println("初始化开始……");
		init();
		System.out.println("初始化结束……");

		System.out.println("buildEntity开始……");
		Long now = System.currentTimeMillis();
		List<User> result = EntityBuiler.buildEntity(results, mapper.getColumnMapper(), User.class);
		Long sss = System.currentTimeMillis() - now;
		System.out.println("buildEntity耗时：" + sss / 1000 + "(" + sss + ")秒");
		System.out.println("buildEntity结束……");

		String r = JSON.toJSONString(result);
		result = null;

		results = null;
		mapper = null;

		System.out.println("result ==  " + r.length());
	}

	private void init() {

		initTableMapper();

		initResults();

	}

	private void initResults() {
		for (Long i = 0L; i < max; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", i + 1L);
			map.put("USER_NAME", "测试名" + i);
			map.put("PASSWORD", "测试密码" + i);
			map.put("PHONE", "321" + i);
			map.put("EMAIL", "email" + i + "@163.com");
			map.put("CREATE_TIME", new Date());
			map.put("UPDATE_TIME", new Date());
			map.put("REMARK", "REMARK" + i);
			results.add(map);
		}
	}

	private void initTableMapper() {
		Class<User> entity = User.class;
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
		mapper = new TableMapper(tableName, pkProperty, columnMapper);
	}

}
