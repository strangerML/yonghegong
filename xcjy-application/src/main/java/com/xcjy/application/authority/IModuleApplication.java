package com.xcjy.application.authority;


import java.util.List;

import com.xcjy.application.base.IBaseApplication;
import com.xcjy.entity.authority.Module;
import com.xcjy.entity.authority.Role;

/**
 * @author 支亚州
 *
 */
public interface IModuleApplication extends IBaseApplication<Module> {

	/**
	 * 获取树形结构的所有资源菜单
	 * 
	 * @return
	 */
	public List<Module> getMenus();

	/**
	 * 根据用户账户，获取当前用户权限内的树形结构的所有资源菜单
	 * 
	 * @param userName
	 * @return
	 */
	public List<Module> getMenusByUser(String userName);
	/**
	 * 根据sn判断module是否重复
	 * @param sn
	 * @return
	 */
	public Module getBySn(String sn);
	/**
	 * 根据moduleID查找子ids
	 * @param moduleId
	 * @return
	 */
	public List<Long> findChildrenIdsByModuleId(Long moduleId);
	/**
	 * 根据ids查询是否对应角色
	 * @param ids
	 * @return
	 */
	public List<Role> findRolesByModuleIds(List<Long> ids);
	/**
	 * 
	 * @param ids
	 */
	public void delByIds(List<Long> ids);
	
	/**
	 * 
	 * @return
	 */
	public List<Module> findAllModulePermission();
}
