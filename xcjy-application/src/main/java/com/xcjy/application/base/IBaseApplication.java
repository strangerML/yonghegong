package com.xcjy.application.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xcjy.entity.common.BaseEntity;
import com.xcjy.infra.utils.page.PageModel;

/**
 * application层 基类
 * 
 * @author 支亚州
 *
 * @param <E>
 */
public interface IBaseApplication<E extends BaseEntity> {

	public void save(E entity);

	public Long insert(E entity);

	public void update(E entity);

	public void delete(Serializable id);

	public List<E> findAll();

	public E get(Serializable id);

	public E getByField(String fieldName, Object fieldVaue);

	public List<E> findByField(String fieldName, Object fieldVaue);

	public List<E> findByFieldsAndOrder(Map<String, Object> equalMap, Map<String, String> orderMap);

	public PageModel<E> query(PageModel<E> pageModel);
}
