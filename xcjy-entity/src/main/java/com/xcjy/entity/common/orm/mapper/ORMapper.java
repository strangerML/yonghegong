package com.xcjy.entity.common.orm.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xcjy.entity.common.orm.enumeration.VenderType;




/**
 * ORM元数据存储对象
 * 
 * @author WH
 *
 */
public class ORMapper {

	/**
	 * 是否启用ORM映射
	 */
	private boolean enableOrm = false;

	/**
	 * 数据库类型
	 */
	private VenderType venderType;

	/**
	 * 表映射map
	 */
	private Map<String, TableMapper> entityMeta = new LinkedHashMap<String, TableMapper>();

	/**
	 * 构造函数，给enableOrm赋值
	 * 
	 * @param enableOrm
	 * @param entityMeta
	 */
	public ORMapper(boolean enableOrm, VenderType venderType, Map<String, TableMapper> entityMeta) {
		this.enableOrm = enableOrm;
		this.entityMeta = entityMeta;
		this.venderType = venderType;
	}

	/**
	 * 默认构造函数
	 */
	public ORMapper() {

	}

	/**
	 * 获取enableOrm的值
	 * 
	 * @return
	 */
	public boolean isEnableOrm() {
		return enableOrm;
	}

	/**
	 * 获取映射元数据
	 * 
	 * @return
	 */
	public Map<String, TableMapper> getEntityMeta() {
		return entityMeta;
	}

	/**
	 * 获取数据库提供商
	 * 
	 * @return
	 */
	public VenderType getVenderType() {
		return venderType;
	}

	/*-----------------------------key method begin--------------------------------*/

	public Integer getTableCount() {
		return entityMeta.size();
	}

	/**
	 * 根据实体名获取表名
	 * 
	 * @param entityName
	 * @return
	 */
	public String getTableName(String entityName) {

		TableMapper mapper = entityMeta.get(entityName);
		if (mapper != null) {
			return mapper.getTableName();
		}
		return null;

	}

	/**
	 * 根据实体名和属性名获取列名
	 * 
	 * @param entityName
	 * @param propertyName
	 * @return
	 */
	public String getColumnName(String entityName, String propertyName) {
		TableMapper mapper = entityMeta.get(entityName);
		if (mapper != null) {
			Map<String, String> columnMapper = mapper.getColumnMapper();
			if (columnMapper != null) {
				return columnMapper.get(propertyName);
			}
		}
		return null;
	}

	/**
	 * 根据class.property字符串获取列名
	 * 
	 * @param fullPropertyName
	 * @return
	 */
	public String getColumnName(String fullPropertyName) {
		if (StringUtils.isNotEmpty(fullPropertyName)) {
			String[] path = fullPropertyName.split("\\.");
			if (path.length == 2) {
				return getColumnName(path[0], path[1]);
			}
		}

		return null;

	}

	/**
	 * 获取列名的数组
	 * 
	 * @param entityName
	 * @return
	 */
	public String[] getColumnNames(String entityName) {
		TableMapper mapper = entityMeta.get(entityName);
		if (mapper != null) {
			Map<String, String> columnMapper = mapper.getColumnMapper();
			List<String> columns = new ArrayList<String>();
			for (String column : columnMapper.values()) {
				columns.add(column);
			}

			String[] columnNames = new String[columns.size()];
			return columns.toArray(columnNames);
		}
		return null;
	}

	/*-----------------------------key method end   --------------------------------*/

}
