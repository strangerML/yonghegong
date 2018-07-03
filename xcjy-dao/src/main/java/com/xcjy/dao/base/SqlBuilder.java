package com.xcjy.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xcjy.entity.common.orm.enumeration.VenderType;
import com.xcjy.entity.common.orm.mapper.TableMapper;
import com.xcjy.infra.exception.SystemException;

/**
 * 构建sql语句的工具类
 * 
 * @author 支亚州
 *
 */
public class SqlBuilder {

	private static final Logger logger = LoggerFactory.getLogger(SqlBuilder.class);

	public static final String SQL_INSERT = "INSERT INTO ";
	public static final String SQL_VALUES = " VALUES ";
	public static final String SQL_UPDATE = "UPDATE ";
	public static final String SQL_SET = " SET ";
	public static final String SQL_WHERE = " WHERE ";
	public static final String SQL_DELETE = "DELETE ";
	public static final String SQL_FROM = " FROM ";
	public static final String SQL_SELECT = "SELECT ";
	public static final String SQL_COUNT = " COUNT(1) ";
	public static final String SQL_LIMIT = " LIMIT ";
	public static final String SQL_AND = " AND ";
	public static final String SQL_OR = " OR ";
	public static final String SQL_LIKE = " LIKE ";
	public static final String SQL_IS_NULL = " IS NULL ";
	public static final String SQL_NOT_NULL = " IS NOT NULL ";
	public static final String SQL_ORDER = " ORDER BY ";
	public static final String SQL_DESC = " DESC ";
	public static final String SQL_ASC = " ASC ";
	public static final String SQL_DISTINCT = " DISTINCT ";
	public static final String SQL_SP = "`";

	/**
	 * 获取插入Insert SQL
	 * 
	 * @param mapper
	 * @return
	 */
	public static String getInsertSql(TableMapper mapper) {
		Map<String, String> columnMapper = mapper.getColumnMapper();
		List<String> propertys = new ArrayList<String>();
		List<String> columns = new ArrayList<String>();
		for (Entry<String, String> entry : columnMapper.entrySet()) {
			propertys.add(":" + entry.getKey());
			columns.add(SQL_SP + entry.getValue() + SQL_SP);
		}

		/**
		 * 拼接sql
		 */
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_INSERT).append(SQL_SP).append(mapper.getTableName()).append(SQL_SP).append(" (")
				.append(String.join(",", columns)).append(") ").append(SQL_VALUES).append(" (")
				.append(String.join(",", propertys)).append(") ");
		logger.info("InsertSQL : " + "\"" + sql.toString() + "\"");

		return sql.toString();
	}

	/**
	 * 获取更新Update SQL
	 * 
	 * @param mapper
	 * @return
	 */
	public static String getUpdateSql(TableMapper mapper) {

		Map<String, String> columnMapper = mapper.getColumnMapper();
		List<String> columns = new ArrayList<String>();
		String pkColumn = mapper.getPkColumn();
		for (Entry<String, String> entry : columnMapper.entrySet()) {
			String column = entry.getValue();
			if (!pkColumn.equals(column)) {
				columns.add(SQL_SP + column + SQL_SP + " = :" + entry.getKey());
			}
		}

		/**
		 * 拼接sql
		 */
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_UPDATE).append(SQL_SP).append(mapper.getTableName()).append(SQL_SP).append(SQL_SET)
				.append(String.join(",", columns)).append(SQL_WHERE).append(pkColumn).append(" = :")
				.append(mapper.getPkProperty());
		logger.info("UpdateSQL : " + "\"" + sql.toString() + "\"");

		return sql.toString();
	}

	/**
	 * 获取删除 Delete SQL
	 * 
	 * @param mapper
	 * @return
	 */
	public static String getDeleteSql(TableMapper mapper, Map<String, Object> equalMap, Map<String, String> likeMap,
			String extraSQL) {

		String whereSql = buildWhereSql(mapper.getColumnMapper(), equalMap, likeMap, null, extraSQL);

		StringBuilder sql = new StringBuilder();
		sql.append(SQL_DELETE).append(SQL_FROM).append(SQL_SP).append(mapper.getTableName()).append(SQL_SP)
				.append(SQL_WHERE).append(" 1=1 ").append(whereSql);
		logger.info("DeleteSQL : " + "\"" + sql.toString() + "\"");

		return sql.toString();
	}

	/**
	 * 获取查询 Select SQL
	 * 
	 * @param venderType
	 * @param mapper
	 * @param equalMap
	 * @param likeMap
	 * @param orderMap
	 * @param extraSQL
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public static String getSelectSql(VenderType venderType, TableMapper mapper, Map<String, Object> equalMap,
			Map<String, String> likeMap, Map<String, String> orderMap, String extraSQL, Integer startIndex,
			Integer pageSize) {

		String pageSql = StringUtils.EMPTY;

		if (venderType == null) {
			logger.warn("VenderType is missing");
			throw new SystemException("VenderType is missing");
		}

		switch (venderType) {
		case MYSQL:
			pageSql = buildMysqlSelectSql(mapper, equalMap, likeMap, orderMap, extraSQL, startIndex, pageSize);
			break;

		default:
			break;
		}

		logger.info("SelectSQL : " + "\"" + pageSql.toString() + "\"");

		return pageSql;
	}

	/**
	 * 获取总记录数查询 Total SQL
	 * 
	 * @param mapper
	 * @param equalMap
	 * @param likeMap
	 * @param orderMap
	 * @param extraSQL
	 * @return
	 */
	public static String getTotalSql(TableMapper mapper, Map<String, Object> equalMap, Map<String, String> likeMap,
			Map<String, String> orderMap, String extraSQL) {

		String whereSql = buildWhereSql(mapper.getColumnMapper(), equalMap, likeMap, orderMap, extraSQL);

		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT).append(SQL_COUNT).append(SQL_FROM).append(SQL_SP).append(mapper.getTableName())
				.append(SQL_SP).append(SQL_WHERE).append(" 1=1 ").append(whereSql);
		logger.info("TotalSQL : " + "\"" + sql.toString() + "\"");

		return sql.toString();
	}

	/**
	 * 构建mysql的查询语句
	 * 
	 * @param mapper
	 * @param equalMap
	 * @param likeMap
	 * @param orderMap
	 * @param extraSQL
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public static String buildMysqlSelectSql(TableMapper mapper, Map<String, Object> equalMap,
			Map<String, String> likeMap, Map<String, String> orderMap, String extraSQL, Integer startIndex,
			Integer pageSize) {

		String selectSql = buildSelectSql(mapper);
		String whereSql = buildWhereSql(mapper.getColumnMapper(), equalMap, likeMap, orderMap, extraSQL);
		String limitSql = buildLimitSql(startIndex, pageSize);

		StringBuilder sql = new StringBuilder();
		sql.append(selectSql).append(" ").append(whereSql).append(" ").append(limitSql);

		return sql.toString();

	}

	/**
	 * 构建分页limit语句
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	private static String buildLimitSql(Integer startIndex, Integer pageSize) {
		if (pageSize == null || pageSize <= 0) {
			return StringUtils.EMPTY;
		}
		StringBuilder limitSql = new StringBuilder();
		limitSql.append(SQL_LIMIT);
		if (startIndex != null && startIndex >= 0) {
			limitSql.append(startIndex).append(",");
		}
		limitSql.append(pageSize);
		return limitSql.toString();
	}

	/**
	 * 创建SELECT子句
	 * 
	 * @param mapper
	 * @return
	 */
	public static String buildSelectSql(TableMapper mapper) {
		List<String> columns = getColumnNames(mapper);
		StringBuilder sql = new StringBuilder();
		sql.append(SQL_SELECT).append(String.join(",", columns)).append(SQL_FROM).append(SQL_SP)
				.append(mapper.getTableName()).append(SQL_SP).append(SQL_WHERE).append(" 1=1 ");
		return sql.toString();
	}

	/**
	 * 创建WHERE子句
	 * 
	 * @param columnMapper
	 * @param equalMap
	 * @param likeMap
	 * @param orderMap
	 * @param extraSQL
	 * @return
	 */
	public static String buildWhereSql(Map<String, String> columnMapper, Map<String, Object> equalMap,
			Map<String, String> likeMap, Map<String, String> orderMap, String extraSQL) {

		StringBuilder sql = new StringBuilder();

		// 相等的条件
		if (equalMap != null && !equalMap.isEmpty()) {
			for (Entry<String, Object> entry : equalMap.entrySet()) {
				// 将属性名转化为列名
				String column = columnMapper.get(entry.getKey());
				sql.append(SQL_AND).append(SQL_SP).append(column).append(SQL_SP).append(" = :").append(entry.getKey());
			}
		}

		// like的条件
		if (likeMap != null && !likeMap.isEmpty()) {
			for (Entry<String, String> entry : likeMap.entrySet()) {
				// 将属性名转化为列名
				String column = columnMapper.get(entry.getKey());
				sql.append(SQL_AND).append(SQL_SP).append(column).append(SQL_SP).append(SQL_LIKE).append(" :")
						.append(entry.getKey());
			}
		}

		// 自定义查询条件
		if (StringUtils.isNotEmpty(extraSQL)) {
			sql.append(" ").append(extraSQL).append(" ");
		}

		// 排序的条件
		if (orderMap != null && !orderMap.isEmpty()) {
			sql.append(SQL_ORDER);
			List<String> orders = new ArrayList<String>();
			for (Entry<String, String> entry : orderMap.entrySet()) {
				// 将属性名转化为列名
				String column = columnMapper.get(entry.getKey());
				orders.add(SQL_SP + column + SQL_SP + " " + entry.getValue());
			}
			sql.append(String.join(",", orders));
		}

		return sql.toString();
	}

	/**
	 * 获取列名的集合
	 * 
	 * @param mapper
	 * @return
	 */
	public static List<String> getColumnNames(TableMapper mapper) {
		Map<String, String> columnMapper = mapper.getColumnMapper();
		List<String> columns = new ArrayList<String>();
		for (String column : columnMapper.values()) {
			columns.add(SQL_SP + column + SQL_SP);
		}
		return columns;
	}

}
