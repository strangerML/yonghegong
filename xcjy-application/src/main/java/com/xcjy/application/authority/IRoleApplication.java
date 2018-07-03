package com.xcjy.application.authority;

import java.util.List;

import com.xcjy.application.base.IBaseApplication;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.User;

/**
 * @author 支亚州
 *
 */
public interface IRoleApplication extends IBaseApplication<Role> {
	public void permissionSave(Role role);

	public List<Long> findAllRolePermissions(Role role);

	public List<User> findAllUserByRoleId(Long roleId);

	public void deleteById(Long roleId);
}
