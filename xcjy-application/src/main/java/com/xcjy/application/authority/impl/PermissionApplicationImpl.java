package com.xcjy.application.authority.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.authority.IPermissionApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.authority.PermissionDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.Permission;

/**
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class PermissionApplicationImpl extends BaseApplicationImpl<Permission>implements IPermissionApplication {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public BaseDao<Permission> getBaseDao() {
		return this.permissionDao;
	}
	
	@Override
	public void add(Long moduleId, Long[] operationIds) {
		for(Long operationId:operationIds){
			Permission permission = new Permission();
			permission.setModuleId(moduleId);
			permission.setOperationId(operationId);
			permissionDao.save(permission);
		}
	}

	@Override
	public void update(Long moduleId, Long[] operationIds) {
		List<Permission> list = permissionDao.findByField("moduleId", moduleId);
		if(list!=null && list.size()>0){
			for(Permission p:list){
				permissionDao.delete(p.getId());
			}
		}
		this.add(moduleId, operationIds);
	}

}
