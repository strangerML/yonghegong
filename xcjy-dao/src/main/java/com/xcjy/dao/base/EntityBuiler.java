package com.xcjy.dao.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实体对象创建工具类
 * 
 * @author 支亚州
 *
 */
public class EntityBuiler {

	/**
	 * 将结果集列表转换成实体列表
	 * 
	 * @param resultList
	 * @param columnMapper
	 * @param clazz
	 * @return
	 */
	public static <E> List<E> buildEntity(List<Map<String, Object>> resultList, Map<String, String> columnMapper,
			Class<E> clazz) {
		List<E> result = new ArrayList<E>();
		if (resultList == null || resultList.isEmpty()) {
			return result;
		}

		Field[] fields = clazz.getDeclaredFields();

		for (Map<String, Object> resultMap : resultList) {
			E entity = buildEntity(columnMapper, clazz, fields, resultMap);
			result.add(entity);
		}

		return result;
	}

	/**
	 * 将结果集转换成实体
	 * 
	 * @param resultMap
	 * @param columnMapper
	 * @param clazz
	 * @return
	 */
	public static <E> E buildEntity(Map<String, Object> resultMap, Map<String, String> columnMapper, Class<E> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		return buildEntity(columnMapper, clazz, fields, resultMap);
	}

	private static <E> E buildEntity(Map<String, String> columnMapper, Class<E> clazz, Field[] fields,
			Map<String, Object> resultMap) {
		E entity = null;
		if (resultMap == null) {
			return entity;
		}
		try {
			entity = clazz.newInstance();

			for (Field field : fields) {
				String fieldName = field.getName();
				if (columnMapper.containsKey(fieldName)) {
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					String columnName = columnMapper.get(fieldName);
					Object fieldValue = resultMap.get(columnName);
					field.set(entity, fieldValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
}
