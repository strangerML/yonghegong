package com.xcjy.wechat.controller.agency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xcjy.application.agency.ICadreApplication;
import com.xcjy.entity.agency.Cadre;

/**
 * 
 * @author BQ
 *
 */
@Controller
@RequestMapping("/cadre")
public class CadreController {
	@Autowired
	private ICadreApplication cadreApplication;
	
	/**
	 * 跳转到干部队伍页面
	 * @return
	 */
	@RequestMapping("/goCadre")
	public String goCadre(Model model) {
		String cadreType="1";
		Cadre cadre = cadreApplication.getByField("cadreType", cadreType);
		//model.addAttribute("cadre", cadre);
		model.addAttribute("newsInfo", cadre);
		return "centerintro/centerDetails";
	}
	/**
	 * 跳转到部门职责页面
	 * @return
	 */
	@RequestMapping("/goDepartCadre")
	public String goDepartCadre(Model model) {
		String cadreType="10";
		Cadre cadre = cadreApplication.getByField("cadreType", cadreType);
		//model.addAttribute("cadre", cadre);
		model.addAttribute("newsInfo", cadre);
		return "centerintro/centerDetails";
	}
	/**
	 * 跳转到机构设置页面
	 * @return
	 */
	@RequestMapping("/goAgencyCadre")
	public String goAgencyCadre(Model model) {
		String cadreType="11";
		Cadre cadre = cadreApplication.getByField("cadreType", cadreType);
		//model.addAttribute("cadre", cadre);
		model.addAttribute("newsInfo", cadre);
		return "centerintro/centerDetails";
	}
	/**
	 * 跳转查询页面
	 * @return
	 */
	@RequestMapping("/goChaxun")
	public String goChaxun(){
		return "centerintro/chaxun";
	}
	
	@RequestMapping("/goChaxunerror")
	public String goChaxunerror(){
		return "centerintro/chaxunerror";
	}
	
}
