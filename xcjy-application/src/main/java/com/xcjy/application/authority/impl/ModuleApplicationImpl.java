package com.xcjy.application.authority.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.authority.IModuleApplication;
import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.dao.authority.ModuleDao;
import com.xcjy.dao.authority.PageOperationDao;
import com.xcjy.dao.authority.PermissionDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.Module;
import com.xcjy.entity.authority.Permission;
import com.xcjy.entity.authority.Role;

/**
 * 资源菜单application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class ModuleApplicationImpl extends BaseApplicationImpl<Module>implements IModuleApplication {

	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private PageOperationDao pageOperationDao;
	
	@Override
	public BaseDao<Module> getBaseDao() {
		return moduleDao;
	}

	@Override
	public List<Module> getMenus() {
		return moduleDao.getMenus();
	}

	@Override
	public List<Module> getMenusByUser(String userName) {
		return moduleDao.getMenusByUser(userName);
	}

	@Override
	public Module getBySn(String sn) {
		Module module = moduleDao.getByField("sn",sn);
		return module;
	}

	@Override
	public List<Long> findChildrenIdsByModuleId(Long moduleId) {
		if(moduleId == null){
			return null;
		}
		List<Long> ids = new ArrayList<Long>();
		Module module = moduleDao.get(moduleId);
		if(module.getParentId()!=null){
			//不是根节点
			if(module.getIsLeaf()!=1){
				//二级节点
				List<Module> mList = moduleDao.findByField("parentId", moduleId);
				if(mList!=null&&mList.size()>0){
					for(Module m :mList){
						ids.add(m.getId());
					}
				}
			}
		}else{
			//是根节点
			List<Module> mListone = moduleDao.findByField("parentId", moduleId);
			if(mListone!=null && mListone.size()>0){
				//二级
				for(Module m :mListone){
					ids.add(m.getId());
					List<Module> mListtwo = moduleDao.findByField("parentId", m.getId());
					if(mListtwo!=null&&mListtwo.size()>0){
						//三级
						for(Module mo : mListtwo){
							ids.add(mo.getId());
						}
					}
					
				}
			}
		}
		ids.add(moduleId);
		
		return ids;
	}

	@Override
	public List<Role> findRolesByModuleIds(List<Long> ids) {
		return moduleDao.findRolesByModuleIds(ids);
	}

	@Override
	public void delByIds(List<Long> ids) {
		if(ids !=null && ids.size()>0){
			for(Long id:ids){
				moduleDao.delete(id);
				List<Permission> list = permissionDao.findByField("moduleId",id);
				if(list!=null && list.size()>0){
					for(Permission p:list){
						permissionDao.delete(p.getId());
					}
				}
			}
		}
	}

	@Override
	public List<Module> findAllModulePermission() {
		LinkedHashMap<String, String> orderByMap = new LinkedHashMap<String, String>();
		orderByMap.put("parentId", "asc");
		List<Module> allModules = moduleDao.findByFieldsAndOrder(null, orderByMap); 
		return createModuleTree(allModules, null);
	}
	
	private List<Module> createModuleTree(List<Module> modules, Long parentId) {
		if (modules == null || modules.isEmpty()) {
			return null;
		}
		List<Module> childrens = new ArrayList<Module>();
		for (Module module : modules) {
			if (module.getParentId() == parentId) {
				if ((new Integer(0)).equals(module.getIsLeaf())) {// 不是叶子节点
					module.setChildren(createModuleTree(modules, module.getId()));
				} else if ((new Integer(1)).equals(module.getIsLeaf())) {// 是叶子节点
					module.setPageOperations(pageOperationDao.findAllOperByModelId(module.getId()));
				}
				childrens.add(module);
			}
		}
		return childrens;
	}
}
