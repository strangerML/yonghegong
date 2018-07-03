package com.xcjy.application.authority.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.authority.IRoleApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.authority.RoleDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.User;

/**
 * @author 支亚州
 */
@Service
@Transactional(value = "defaultTm")
public class RoleApplicationImpl extends BaseApplicationImpl<Role>implements IRoleApplication {

	@Autowired
	private RoleDao roleDao;

	@Override
	public BaseDao<Role> getBaseDao() {
		return this.roleDao;
	}
	@Override
    public void permissionSave(Role role) {
        this.roleDao.permissionSave(role);
    }

    @Override
    public List<Long> findAllRolePermissions(Role role) {
        return roleDao.findAllRolePermissions(role);
    }

    @Override
    public List<User> findAllUserByRoleId(Long roleId) {
        return roleDao.findAllUserByRoleId(roleId);
    }

    @Override
    public void deleteById(Long roleId) {
        roleDao.deleteById(roleId);
    }
}
