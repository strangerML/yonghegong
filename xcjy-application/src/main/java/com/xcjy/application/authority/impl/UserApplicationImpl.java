package com.xcjy.application.authority.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.authority.IUserApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.authority.UserDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.User;



/**
 * 资源菜单application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class UserApplicationImpl extends BaseApplicationImpl<User>implements IUserApplication {

	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User> getBaseDao() {
		return this.userDao;
	}

}
