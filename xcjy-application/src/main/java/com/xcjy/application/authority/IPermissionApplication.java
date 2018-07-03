package com.xcjy.application.authority;

import com.xcjy.application.base.IBaseApplication;
import com.xcjy.entity.authority.Permission;

/**
 * @author 支亚州
 *
 */
public interface IPermissionApplication extends IBaseApplication<Permission> {

	public void add(Long moduleId, Long[] operationIds) ;
	
	public void update(Long moduleId, Long[] operationIds);	
}
