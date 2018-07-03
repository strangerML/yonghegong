package com.xcjy.application.agency.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.agency.ICadreApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.agency.CadreDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.agency.Cadre;

/**
 * 干部队伍application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class CadreApplicationImpl extends BaseApplicationImpl<Cadre>implements ICadreApplication {

	@Autowired
	private CadreDao cadreDao;

	@Override
	public BaseDao<Cadre> getBaseDao() {
		return this.cadreDao;
	}
	
}
