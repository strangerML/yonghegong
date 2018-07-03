package com.xcjy.web.controller.authority;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xcjy.application.authority.IModuleApplication;
import com.xcjy.application.authority.IOperationApplication;
import com.xcjy.application.authority.IPermissionApplication;
import com.xcjy.entity.authority.Module;
import com.xcjy.entity.authority.Operation;
import com.xcjy.entity.authority.Permission;
import com.xcjy.infra.utils.beanutils.PropertyUtils;

/**
 * 资源管理
 * @author bianqi
 *
 */
@Controller
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	private IModuleApplication moduleApplication;
	
	@Autowired
	private IOperationApplication operationApplication;
	
	@Autowired
	private IPermissionApplication permissionApplication;

	/**
	 * 右侧页面跳转
	 * @return
	 */
	@RequestMapping("/goModulePage")
	public String goModulePage() {
		return "authority/module-page";
	}

	/**
	 * 资源树
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getModules")
	public Object getAllModeles(){
		List<Module> list =moduleApplication.findAll();
		System.out.println(JSON.toJSONString(list));
		return list;
	}
	
	/**
	 * 跳转新增模态框
	 * @param module
	 * @param model
	 * @return
	 */
	@RequestMapping("/goMerge")
	public Object goAdd(Module module,Model model){
		if(module.getId()!=null){
			Integer flag = 0;//是否是叶子节点
			Module m = moduleApplication.get(module.getId());
			if(m.getIsLeaf()!=null && m.getIsLeaf() ==1){ //是根节点，需要绑定操作
				flag = 1;
				List<Operation> operations = operationApplication.findAll();
				//查询已绑定的操作
				model.addAttribute("operations", operations);
				/*LinkedHashMap<String,Object>equalMap = new LinkedHashMap<String,Object>();
				equalMap.put("moduleId", module.getId());
				List<Permission> permissions = permissionApplication.findByFields(equalMap);*/
				List<Permission> permissions = permissionApplication.findByField("moduleId", module.getId());
				if(permissions!=null && permissions.size()>0){
					model.addAttribute("ps",permissions);
				}
			}
			model.addAttribute("isLeaf", flag);
			model.addAttribute("m", m);
		}else{
			Integer flag = 0;//是否是叶子节点
			if(module.getIsLeaf()!=null && module.getIsLeaf() ==1){ //是根节点，需要绑定操作
				flag = 1;
				List<Operation> operations = operationApplication.findAll();
				model.addAttribute("operations", operations);
			}
			model.addAttribute("isLeaf", flag);
		}
		return "/authority/module-merge";
	}

	
	/**
	 * ajax 新增修改
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/merge")
	public Object merge(Module module,Long[]operationIds){
		if(module.getId()!=null){
			module.setUpdateTime(new Date());
			Module m = moduleApplication.get(module.getId());
			PropertyUtils.copyProperties(m,module);
			moduleApplication.update(m);
			Long id = module.getId();
			if(operationIds!=null && operationIds.length>0){
				permissionApplication.update(id, operationIds);
			}
		}else{
			module.setCreateTime(new Date());
			Long id = moduleApplication.insert(module);
			if(operationIds!=null && operationIds.length>0){
				permissionApplication.add(id, operationIds);
			}
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("success", true);
		return m;
	}
	
	/**
	 * 删除节点
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del")
	public Object del(Module module){
		Long id = module.getId();
		Map<String,Object> m = new HashMap<String,Object>();
		//1.判断该module下的所有children
		List<Long> childrenIds = moduleApplication.findChildrenIdsByModuleId(id);
		//根据ids查询是否对应角色
		/*if(childrenIds!=null && childrenIds.size()>0){
			List<Role> roles = moduleApplication.findRolesByModuleIds(childrenIds);
			if(roles!=null && roles.size()>0){
				m.put("result", false);
				m.put("msg", "有角色绑定了该权限，请解绑后再删除！");
				return  m;
			}
		}*/

		moduleApplication.delByIds(childrenIds);
		m.put("result", true);
		return m;
	}
	
	/**
	 * 检测sn是否重复
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkSN")
	public Object checkSn(String sn,Long id){
		Module module = null ;
		boolean flag = true;
		if(id!=null){
			//修改时，校验用户名
			Module old = moduleApplication.get(id);
			if(!old.getSn().equals(sn)){
				module = moduleApplication.getBySn(sn);
			}
		}else{
			//增加时，校验用户名
			module = moduleApplication.getBySn(sn);
		}
		if(module!=null){
			flag = false;
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("valid", flag);
		return m;
	}
}
