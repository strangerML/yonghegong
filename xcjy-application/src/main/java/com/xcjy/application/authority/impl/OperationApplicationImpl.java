package com.xcjy.application.authority.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.authority.IOperationApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.authority.OperationDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.Operation;

/**
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class OperationApplicationImpl extends BaseApplicationImpl<Operation>implements IOperationApplication {

	@Autowired
	private OperationDao operationDao;

	@Override
	public BaseDao<Operation> getBaseDao() {
		return operationDao;
	}

}
