package com.xcjy.web.controller.authority;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xcjy.application.authority.IRoleApplication;
import com.xcjy.application.authority.IUserApplication;
import com.xcjy.application.authority.IUserRoleApplication;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.User;
import com.xcjy.entity.authority.UserRole;
import com.xcjy.infra.utils.Constants;
import com.xcjy.infra.utils.encrypt.CodeUtil;
import com.xcjy.infra.utils.encrypt.Type;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;


/**
 * 用户管理
 * @author 支亚州
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserApplication userApplication;
	@Autowired
	private IRoleApplication roleApplication;
	@Autowired
	private IUserRoleApplication userRoleApplication;
	
	/**
	 * 跳到分页页面
	 * @return
	 */
	@RequestMapping("/goUserPage")
	public String goPage(){
		return "/authority/user-page";
	}
	
	/**
	 * ajax分页请求
	 * @param user
	 * @param pm
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public PageModel<User> query(DataTablesPageModel<User> pm, User user,
			HttpServletRequest request) {
		pm.setOrderMap(request);

		if (StringUtils.isNotEmpty(user.getUserName())) {
			pm.getLikeMap().put("userName", user.getUserName());
		}
		if (StringUtils.isNotEmpty(user.getPhone())) {
			pm.getLikeMap().put("phone", user.getPhone());
		}
		if (StringUtils.isNotEmpty(user.getRoleName())) {
			pm.getLikeMap().put("roleName", user.getRoleName());
		}
		
		pm = (DataTablesPageModel<User>) userApplication.query(pm);
		return pm;
	}
	
	/**
	 * 跳到模态对话页
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/goMergePage")
	public Object goMergePage(User user,Model model){
		if(user.getId()!=null){
			User userOld = userApplication.get(user.getId());
			UserRole userRole = userRoleApplication.getByUserId(user.getId());
			model.addAttribute("user", userOld);
			model.addAttribute("userRole",userRole);
		}
		List<Role> roles = roleApplication.findAll();
		model.addAttribute("roles",roles);
		
		return "/authority/user-merge";
	}
	/**
	 *  跳到-重置密码-模态对话页
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/goResetPage")
	public Object goResetPage(Long userId,Model model){
		model.addAttribute("userId",userId);
		return "/authority/user-reset";
	}
	/**
	 * ajax 新增修改
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/merge")
	public Object merge(User user,Long userRoleId,UserRole userRole){
		if(user.getId()!=null){
			user.setCreateTime(userApplication.get(user.getId()).getCreateTime());
			user.setPassword(userApplication.get(user.getId()).getPassword());
			user.setUpdateTime(new Date());
			userApplication.update(user);
			userRole.setId(userRoleId);
			userRole.setUserId(user.getId());
			userRoleApplication.update(userRole);
			
		}else{
			user.setCreateTime(new Date());
			String pwd = passwordEncode(user.getPassword(), user.getUserName());
			user.setPassword(pwd);
		 	userApplication.save(user);
		 	user = userApplication.getByField("userName", user.getUserName());
		 	if(user!=null){
			 	if(user.getId()!=null){
				 	userRole.setUserId(user.getId());
					userRoleApplication.save(userRole);
				}
		 	} 	
		}
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("success", true);
		return m;
	}
	
	
	/**
	 * ajax  删除
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del")
	public Object del(User user){
		userApplication.delete(user.getId());
		Long userRoleId = userRoleApplication.getByUserId(user.getId()).getId();
		userRoleApplication.delete(userRoleId);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("success", true);
		return m;
	}
	
	/**
	 * 密码加密
	 * 
	 * @param password
	 *            未加密密码
	 * @param salt
	 *            盐，可以为null
	 * @return 加密后的密码
	 */
	private String passwordEncode(String password, String salt) {
		CodeUtil code = CodeUtil.getInstance();
		return code.encode(password + (StringUtils.isEmpty(salt) ? "" : Constants.COLON + salt), Type.MD5);
	}
}
