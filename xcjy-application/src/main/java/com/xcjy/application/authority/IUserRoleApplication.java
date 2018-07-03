package com.xcjy.application.authority;

import com.xcjy.application.base.IBaseApplication;
import com.xcjy.entity.authority.UserRole;

public interface IUserRoleApplication extends IBaseApplication<UserRole>{

	public UserRole getByUserId(Long id);
	
}
