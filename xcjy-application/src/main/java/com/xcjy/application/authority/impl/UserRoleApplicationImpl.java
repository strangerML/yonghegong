package com.xcjy.application.authority.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.authority.IUserRoleApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.authority.UserRoleDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.UserRole;

@Service
@Transactional(value = "defaultTm")
public class UserRoleApplicationImpl extends BaseApplicationImpl<UserRole> implements IUserRoleApplication{

	@Autowired
	UserRoleDao userRoleDao;
	
	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	public BaseDao<UserRole> getBaseDao() {
		return this.userRoleDao;
	}

	@Override
	public UserRole getByUserId(Long id) {
		return userRoleDao.getByField("userId", id);
	}
	
}
