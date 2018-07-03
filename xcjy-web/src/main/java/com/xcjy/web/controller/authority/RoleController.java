package com.xcjy.web.controller.authority;

import com.xcjy.application.authority.IModuleApplication;
import com.xcjy.application.authority.IRoleApplication;
import com.xcjy.application.authority.IUserRoleApplication;
import com.xcjy.entity.authority.Module;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.UserRole;
import com.xcjy.infra.utils.http.MimeUtil;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 角色管理
 * 
 * @author 卞琦
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private IRoleApplication roleApplication;
	
	@Autowired
	private IUserRoleApplication userRoleApplication;
	
	@Autowired
	private IModuleApplication moduleApplication;

    /**
     * 角色列表页面
     *
     * @return
     */
    @RequestMapping("/goRolePage")
    public String goRolePage() {
        return "/authority/role-page";
    }
	
    @ResponseBody
    @RequestMapping(value = "/query")
    public PageModel<Role> queryRoles(DataTablesPageModel<Role> pm, Role role) {
        if (StringUtils.isNotEmpty(role.getName())) {
        	pm.getLikeMap().put("name",role.getName());
        } 
        pm = (DataTablesPageModel<Role>) roleApplication.query(pm);
        return pm;
    }
    /**
     * 增该页面
     * @param role
     * @param model
     * @return
     */
    @RequestMapping("/goMergePage")
    public String goMergePage(Role role, Model model) {
        model.addAttribute("role", roleApplication.get(role.getId()));
        return "/authority/role-merge";
    }
    
    @ResponseBody
    @RequestMapping(value = "/merge", produces = MimeUtil.JSON)
    public Map<String, Object> merge(Role role) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        try {
            if (StringUtils.isEmpty(role.getName())) {
                result.put("result", false);
                result.put("msg", "角色名不能为空！");
                return result;
            }
            List<Role> nameRoles = null;
            if (role.getId() != null) {
            	 Role oldRole = roleApplication.get(role.getId());
            	 if(!oldRole.getName().equals(role.getName())){
            	 nameRoles = roleApplication.findByField("name", role.getName());
            	 }
            	
            }else{
            	 nameRoles = roleApplication.findByField("name", role.getName());
            }
           
            if (nameRoles != null && !nameRoles.isEmpty()) {
                result.put("result", false);
                result.put("msg", "该角色名已存在！");
                return result;
            }
            if (role.getId() != null) {
                Role oldRole = roleApplication.get(role.getId());
                oldRole.setName(role.getName());
                oldRole.setRemark(role.getRemark());
                oldRole.setUpdateTime(new Date());
                roleApplication.update(oldRole);
            } else {
                Date date = new Date();
                role.setCreateTime(date);
                role.setUpdateTime(date);
                roleApplication.save(role);
            }
        } catch (Exception e) {
            result.put("result", false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/del", produces = MimeUtil.JSON)
    public Map<String, Object> del(Role role) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        try {
            List<UserRole> users = userRoleApplication.findByField("roleId", role.getId());
            if (users != null && !users.isEmpty()) {
                result.put("result", false);
                result.put("msg", "有用户绑定了该角色，请解绑后再删除！");
                return  result;
            }
            roleApplication.deleteById(role.getId());
        } catch (Exception e) {
            result.put("result", false);
        }
        return  result;
    }
    
    @RequestMapping("/goBindAuthPage")
    public String goBindAuthPage() {
        return "/authority/role-permission";
    }

    @ResponseBody
    @RequestMapping(value = "/findAllModulePermission", produces = MimeUtil.JSON)
    public List<Module> findAllModulePermission() {
        List<Module> modules = null;
        try {
            modules = moduleApplication.findAllModulePermission();
        } catch (Exception e) {
        }
        return modules;
    }

    @ResponseBody
    @RequestMapping(value = "/permissionSave", produces = MimeUtil.JSON)
    public Map<String, Object> permissionSave(Role role) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (role.getId() == null) {
            result.put("result", false);
            result.put("msg", "请选择角色！");
        }
        try {
            roleApplication.permissionSave(role);
            result.put("result", true);
            result.put("msg", "授权成功！");
        } catch (Exception e) {
            result.put("result", false);
            result.put("msg", "授权失败！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/findAllRolePermissions", produces = MimeUtil.JSON)
    public Map<String, Object> findAllRolePermissions(Role role) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (role.getId() == null) {
            result.put("result", false);
            result.put("msg", "请选择角色！");
        }
        try {
            List<Long> list = roleApplication.findAllRolePermissions(role);
            result.put("result", true);
            result.put("msg", "获取角色权限成功！");
            result.put("permissionIds", list);
        } catch (Exception e) {
            result.put("result", false);
            result.put("msg", "获取角色权限失败！");
        }
        return result;
    }
    
}
