package com.xcjy.entity.common.orm.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 表映射mapper
 * 
 * @author WH
 *
 */
public class TableMapper {

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 主键属性名
	 */
	private String pkProperty;

	/**
	 * 属性-字段映射map
	 */
	private Map<String, String> columnMapper = new LinkedHashMap<String, String>();

	public TableMapper(String tableName, String pkProperty, Map<String, String> columnMapper) {
		this.tableName = tableName;
		this.pkProperty = pkProperty;
		this.columnMapper = columnMapper;
	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 获取主键列名
	 * 
	 * @return
	 */
	public String getPkColumn() {
		return columnMapper.get(pkProperty);
	}

	/**
	 * 获取主键属性名
	 * 
	 * @return
	 */
	public String getPkProperty() {
		return pkProperty;
	}

	/**
	 * 获取属性-字段映射map
	 * 
	 * @return
	 */
	public Map<String, String> getColumnMapper() {
		return columnMapper;
	}

}
