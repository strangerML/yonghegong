package com.xcjy.application.base.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xcjy.application.base.IBaseApplication;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.infra.utils.page.PageModel;

/**
 * application层实现类基类
 * 
 * @author 支亚州
 *
 * @param <E>
 */
public abstract class BaseApplicationImpl<E extends BaseEntity> implements IBaseApplication<E> {

	public abstract BaseDao<E> getBaseDao();

	@Override
	public void save(E entity) {
		this.getBaseDao().save(entity);
	}

	@Override
	public Long insert(E entity) {
		return this.getBaseDao().insert(entity);
	}

	@Override
	public void update(E entity) {
		this.getBaseDao().update(entity);
	}

	@Override
	public void delete(Serializable id) {
		this.getBaseDao().delete(id);
	}

	@Override
	public List<E> findAll() {
		return this.getBaseDao().findAll();
	}

	@Override
	public E get(Serializable id) {
		return this.getBaseDao().get(id);
	}

	@Override
	public E getByField(String fieldName, Object fieldVaue) {
		return this.getBaseDao().getByField(fieldName, fieldVaue);
	}

	@Override
	public List<E> findByField(String fieldName, Object fieldVaue) {
		return this.getBaseDao().findByField(fieldName, fieldVaue);
	}

	@Override
	public List<E> findByFieldsAndOrder(Map<String, Object> equalMap, Map<String, String> orderMap) {
		return this.getBaseDao().findByFieldsAndOrder(equalMap, orderMap);
	}

	@Override
	public PageModel<E> query(PageModel<E> pageModel) {
		return this.getBaseDao().query(pageModel);
	}

}
