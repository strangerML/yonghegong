package com.xcjy.web.controller.agency;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xcjy.application.agency.ICadreApplication;
import com.xcjy.entity.agency.Cadre;
import com.xcjy.entity.authority.User;
import com.xcjy.infra.utils.Constants;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;


/**
 * 干部队伍
 * 
 * @author 支亚州
 *
 */
@Controller
@RequestMapping("/cadre")
public class CadreController {

	@Autowired
	private ICadreApplication cadreApplication;
	
	/**
	 * 跳转到干部队伍列表页面
	 * @return
	 */
	@RequestMapping("/goCadrePage")
	public String goCadrePage(Model model) {
		String cadreType="1";
		model.addAttribute("cadreType", cadreType);
		return "agency/cadre-page";
	}
	/**
	 * 跳转到部门职责列表页面
	 * @return
	 */
	@RequestMapping("/goDepartCadrePage")
	public String goDepartCadrePage(Model model,String cadreType) {
		model.addAttribute("cadreType", cadreType);
		return "agency/cadre-page";
	}
	/**
	 * 跳转到机构设置列表页面
	 * @return
	 */
	@RequestMapping("/goAgencyCadrePage")
	public String goAgencyCadrePage(Model model,String cadreType) {
		model.addAttribute("cadreType", cadreType);
		return "agency/cadre-page";
	}
	
	
	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public PageModel<Cadre> query(DataTablesPageModel<Cadre> pm, Cadre cadre,HttpServletRequest request,Model model) {
		pm.getOrderMap().put("createTime", "asc");
		String cadreType = request.getParameter("cadreType");
		if (StringUtils.isNotEmpty(cadre.getName())) {
			pm.getLikeMap().put("name", cadre.getName());
		}
		if (cadreType != null) {
			pm.getEqualMap().put("cadreType", cadreType);
		}
		pm = (DataTablesPageModel<Cadre>) cadreApplication.query(pm);
		return pm;
	}
	/**
	 * 跳转到干部队伍添加页面
	 * @return
	 */
	@RequestMapping("/goMergePage")
	public String goMergePage(Cadre cadre, Model model,HttpServletRequest request) {
		String id = cadre.getId();
		String cadreType = request.getParameter("cadreType");
		if (id != null) {
			cadre = cadreApplication.get(id);
			model.addAttribute("cadre", cadre);
			model.addAttribute("cadreType",cadre.getCadreType());
		}
		if(cadreType != null){
			model.addAttribute("cadreType",cadreType);
		}
		return "agency/cadre-merge";
	}
	/**
	 * 添加，修改干部队伍
	 * @return
	 */
	@RequestMapping("/merge")
	@ResponseBody
	public JSONObject merge(Cadre cadre,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String id = cadre.getId();
		User user = getUser(getUser(null));
		String cadreType = cadre.getCadreType();
		if (!"".equals(id)) {
			Cadre oldcadre = cadreApplication.get(id);
			if (oldcadre != null) {
				oldcadre.setContents(cadre.getContents());
				oldcadre.setName(cadre.getName());
				oldcadre.setPictures(cadre.getPictures());
				oldcadre.setUpdateTime(new Date());
			
				cadreApplication.update(oldcadre);
				jsonObject.put("result", true);
				return jsonObject;
			} else {
				jsonObject.put("result", false);
				jsonObject.put("msg", "未选择要修改的数据！");
				return jsonObject;
			}
		} else {
			UUID uuid = UUID.randomUUID();
			cadre.setId(String.valueOf(uuid));
			cadre.setCreateTime(new Date());
			cadre.setPublishUserId(user.getId());
			cadre.setCadreCount(0);
			cadre.setCadreType(cadreType);
			
			cadreApplication.save(cadre);
			jsonObject.put("result", true);
			return jsonObject;
		}
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public JSONObject del(Cadre cadre) {
		JSONObject jsonObject = new JSONObject();
		String id = cadre.getId();
		if (id != null) {
			cadreApplication.delete(id);
			jsonObject.put("result", true);
			return jsonObject;
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要删除的数据！");
		return jsonObject;
	}
	/**
	 * 查看干部队伍信息
	 * @return
	 */
	@RequestMapping("/check")
	public String check(String id, Model model) {
		if (id != null) {
			Cadre cadre = cadreApplication.get(id);
			model.addAttribute("cadre", cadre);
		}
		return "agency/cadre-List";
	}
	/**
	 * 查询当前登录用户
	 * @param user  后台登陆用户表
	 * @return 返回查询到的用户数据
	 */
	public User getUser(User user){
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
			    user = (User) session.getAttribute(Constants.CURRENT_USER);
				return user;
			}
		}
		return user;
	}
}
