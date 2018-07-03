package com.xcjy.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.enumeration.VenderType;
import com.xcjy.entity.common.orm.mapper.ORMapper;
import com.xcjy.entity.common.orm.mapper.TableMapper;
import com.xcjy.infra.utils.clazz.ReflectionUtil;
import com.xcjy.infra.utils.page.PageModel;

/**
 * 基础dao
 * 
 * @author 支亚州
 *
 * @param <E>
 */
public class BaseDao<E extends BaseEntity> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected JdbcTemplate template;// spring jdbc核心对象

	@Autowired
	protected NamedParameterJdbcTemplate namedTemplate;// spring jdbc核心对象

	protected ORMapper mapper;// ORM元数据

	private Class<E> entityClass;// E对应的具体类型

	protected TableMapper tableMapper;// 表映射mapper

	@Value("#{mapper.venderType}")
	private VenderType venderType;// 数据库提供商

	@SuppressWarnings("unchecked")
	public BaseDao() {
		entityClass = ReflectionUtil.getSuperClassGenericType(getClass());
	}

	@Autowired
	public void setMapper(ORMapper mapper) {
		this.mapper = mapper;
		tableMapper = this.mapper.getEntityMeta().get(entityClass.getName());
	}

	/*----------------------------增删改 begin-------------------------------*/
	/**
	 * 增加 无返回值
	 * 
	 * @param entity
	 */
	public void save(E entity) {
		String sql = SqlBuilder.getInsertSql(tableMapper);
		Map<String, Object> paramMap = getMapParams(entity);
		namedTemplate.update(sql, paramMap);
	}

	/**
	 * 增加 返回自增主键ID
	 * 
	 * @param entity
	 * @return
	 */
	public Long insert(E entity) {
		String sql = SqlBuilder.getInsertSql(tableMapper);
		Map<String, Object> paramMap = getMapParams(entity);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedTemplate.update(sql, new MapSqlParameterSource(paramMap), keyHolder);
		return keyHolder.getKey().longValue();
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	public void update(E entity) {
		String sql = SqlBuilder.getUpdateSql(tableMapper);
		Map<String, Object> paramMap = getMapParams(entity);
		namedTemplate.update(sql, paramMap);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(Serializable id) {
		deleteByField("id", id);
	}

	/**
	 * 根据单个相等条件删除
	 * 
	 * @param fieldName
	 * @param fieldVaue
	 */
	public void deleteByField(String fieldName, Object fieldVaue) {
		Map<String, Object> equalMap = new HashMap<String, Object>();
		equalMap.put(fieldName, fieldVaue);
		deleteByFields(equalMap);
	}

	/**
	 * 根据多个相等条件删除
	 * 
	 * @param equalMap
	 */
	public void deleteByFields(Map<String, Object> equalMap) {
		deleteByCondition(equalMap, null, null, null);
	}

	/**
	 * 根据条件删除
	 * 
	 * @param equalMap
	 * @param likeMap
	 * @param extraSQL
	 * @param extraParamMap
	 */
	public void deleteByCondition(Map<String, Object> equalMap, Map<String, String> likeMap, String extraSQL,
			Map<String, Object> extraParamMap) {
		String sql = SqlBuilder.getDeleteSql(tableMapper, equalMap, likeMap, extraSQL);
		Map<String, Object> paramMap = buildParamMap(equalMap, likeMap, extraParamMap);
		namedTemplate.update(sql, paramMap);
	}

	/*----------------------------增删改 end-------------------------------*/

	/*----------------------------查询 begin-------------------------------*/

	/**
	 * 查询所有记录 返回list
	 * 
	 * @return
	 */
	public List<E> findAll() {
		return findByCondition(null, null, null, null, null, null, null);
	}

	/**
	 * 根据主键ID查询单个实体
	 * 
	 * @param id
	 * @return
	 */
	public E get(Serializable id) {
		return getByField(tableMapper.getPkProperty(), id);
	}

	/**
	 * 根据单个相等条件查询单个实体
	 * 
	 * @param fieldName
	 * @param fieldVaue
	 * @return
	 */
	public E getByField(String fieldName, Object fieldVaue) {
		Map<String, Object> equalMap = new HashMap<String, Object>();
		equalMap.put(fieldName, fieldVaue);
		return getByFields(equalMap);
	}

	/**
	 * 根据多个相等条件查询单个实体
	 * 
	 * @param equalMap
	 * @return
	 */
	public E getByFields(Map<String, Object> equalMap) {
		List<E> list = findByCondition(equalMap, null, null, null, null, null, 1);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据单个相等条件查询 返回list
	 * 
	 * @param fieldName
	 * @param fieldVaue
	 * @return
	 */
	public List<E> findByField(String fieldName, Object fieldVaue) {
		Map<String, Object> equalMap = new HashMap<String, Object>();
		equalMap.put(fieldName, fieldVaue);
		return findByFields(equalMap);
	}

	/**
	 * 根据多个相等条件查询 返回list
	 * 
	 * @param equalMap
	 * @return
	 */
	public List<E> findByFields(Map<String, Object> equalMap) {
		return findByFieldsAndOrder(equalMap, null);
	}

	/**
	 * 根据相等条件和排序条件查询 返回list
	 * 
	 * @param equalMap
	 * @param orderMap
	 * @return
	 */
	public List<E> findByFieldsAndOrder(Map<String, Object> equalMap, Map<String, String> orderMap) {
		return findByCondition(equalMap, null, orderMap, null, null, null, null);
	}

	/**
	 * 根据条件查询 返回list
	 * 
	 * @param equalMap
	 * @param likeMap
	 * @param orderMap
	 * @param extraSQL
	 * @param extraParamMap
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<E> findByCondition(Map<String, Object> equalMap, Map<String, String> likeMap,
			Map<String, String> orderMap, String extraSQL, Map<String, Object> extraParamMap, Integer startIndex,
			Integer pageSize) {

		String sql = SqlBuilder.getSelectSql(venderType, tableMapper, equalMap, likeMap, orderMap, extraSQL, startIndex,
				pageSize);

		Map<String, Object> paramMap = buildParamMap(equalMap, likeMap, extraParamMap);

		List<Map<String, Object>> resultList = namedTemplate.queryForList(sql, paramMap);

		return EntityBuiler.buildEntity(resultList, tableMapper.getColumnMapper(), entityClass);
	}
	/*----------------------------查询 end-------------------------------*/

	/*----------------------------分页查询 begin-------------------------------*/

	/**
	 * 分页查询
	 * 
	 * @param pageModel
	 * @return
	 */
	public PageModel<E> query(PageModel<E> pageModel) {
		return query(pageModel, null, null);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageModel
	 * @param extraSQL
	 *            自定义查询条件，以AND开头的自定义sql
	 * @param extraParamMap
	 *            自定义查询条件的参数
	 * @return
	 */
	public PageModel<E> query(PageModel<E> pageModel, String extraSQL, Map<String, Object> extraParamMap) {

		Map<String, Object> equalMap = pageModel.getEqualMap();
		Map<String, String> likeMap = pageModel.getLikeMap();
		Map<String, String> orderMap = pageModel.getOrderMap();

		Integer totalCount = getTotalCount(equalMap, likeMap, orderMap, extraSQL, extraParamMap);
		if (totalCount == null || totalCount <= 0) {
			pageModel.setTotalCount(0);
			pageModel.setPageData(new ArrayList<E>());
			return pageModel;
		}

		Integer startIndex = pageModel.getStartIndex();
		Integer pageSize = pageModel.getPageSize();

		if (pageSize == null || pageSize <= 0) {
			pageSize = PageModel.DEFAULT_PAGE_SIZE;
		}

		if (startIndex == null || startIndex < 0) {
			startIndex = 0;
		} else if (startIndex > totalCount) {
			Integer totalPage = (int) Math.ceil(totalCount.doubleValue() / pageSize.doubleValue());
			startIndex = (totalPage - 1) * pageSize;
		}

		List<E> list = findByCondition(equalMap, likeMap, orderMap, extraSQL, extraParamMap, startIndex, pageSize);

		pageModel.setTotalCount(totalCount);
		pageModel.setPageData(list);

		return pageModel;
	}

	/**
	 * 根据条件查询记录总条数
	 * 
	 * @param equalMap
	 * @param likeMap
	 * @param orderMap
	 * @param extraSQL
	 * @param extraParamMap
	 * @return
	 */
	public Integer getTotalCount(Map<String, Object> equalMap, Map<String, String> likeMap,
			Map<String, String> orderMap, String extraSQL, Map<String, Object> extraParamMap) {

		String sql = SqlBuilder.getTotalSql(tableMapper, equalMap, likeMap, orderMap, extraSQL);
		Map<String, Object> paramMap = buildParamMap(equalMap, likeMap, extraParamMap);
		return namedTemplate.queryForObject(sql, paramMap, Long.class).intValue();
	}

	/*----------------------------分页查询 end-------------------------------*/

	/*----------------------------工具方法 begin-------------------------------*/
	/**
	 * 将实体对象转化为map
	 * 
	 * @param e
	 * @return
	 */
	private Map<String, Object> getMapParams(E e) {
		Map<String, Object> mapParams = new HashMap<String, Object>();
		Field[] fields = entityClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.isAnnotationPresent(Column.class)) {
				field.setAccessible(true);
				try {
					String fieldName = field.getName();
					Object fieldValue = field.get(e);
					mapParams.put(fieldName, fieldValue);
				} catch (IllegalArgumentException exception) {
					exception.printStackTrace();
				} catch (IllegalAccessException exception) {
					exception.printStackTrace();
				}
			}
		}
		return mapParams;
	}

	/**
	 * 构建查询参数
	 * 
	 * @param equalMap
	 * @param likeMap
	 * @param extraParamMap
	 * @return
	 */
	private Map<String, Object> buildParamMap(Map<String, Object> equalMap, Map<String, String> likeMap,
			Map<String, Object> extraParamMap) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (equalMap != null && !equalMap.isEmpty()) {
			paramMap.putAll(equalMap);
		}
		if (likeMap != null && !likeMap.isEmpty()) {
			for (Entry<String, String> entry : likeMap.entrySet()) {
				paramMap.put(entry.getKey(), "%" + entry.getValue() + "%");
			}
		}
		if (extraParamMap != null && !extraParamMap.isEmpty()) {
			paramMap.putAll(extraParamMap);
		}
		return paramMap;
	}

	/*----------------------------工具方法 end-------------------------------*/

}
