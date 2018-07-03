package com.xcjy.frontend.controller.agency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xcjy.application.agency.ICadreApplication;
import com.xcjy.application.news.IPicNewsApplication;
import com.xcjy.entity.agency.Cadre;
import com.xcjy.entity.news.PicNews;

/**
 * 
 * 
 * @author 支亚州
 *
 */
@Controller
@RequestMapping("/cadre")
public class CadreController {
	@Autowired
	private ICadreApplication cadreApplication;
	@Autowired
	private IPicNewsApplication picNewsApplication;
	
	/**
	 * 跳转到干部队伍页面
	 * @return
	 */
	@RequestMapping("/goCadre")
	public String goCadre(Model model) {
		String cadreType="1";
		Cadre cadre = cadreApplication.getByField("cadreType", cadreType);
		model.addAttribute("cadre", cadre);
		return "agency/cadre-List";
	}
	/**
	 * 跳转到部门职责页面
	 * @return
	 */
	@RequestMapping("/goDepartCadre")
	public String goDepartCadre(Model model) {
		String cadreType="2";
		Cadre cadre = cadreApplication.getByField("cadreType", cadreType);
		model.addAttribute("cadre", cadre);
		return "agency/cadre-List";
	}
	/**
	 * 跳转到机构设置页面
	 * @return
	 */
	@RequestMapping("/goAgencyCadre")
	public String goAgencyCadre(Model model) {
		String cadreType="3";
		Cadre cadre = cadreApplication.getByField("cadreType", cadreType);
		model.addAttribute("cadre", cadre);
		return "agency/cadre-List";
	}
	
	@RequestMapping("/goPicNewsDetails")
	public String goPicNewsDetails(Model model,String picId){
		PicNews pns = picNewsApplication.get(picId);
		model.addAttribute("news", pns);
		return "agency/picNews";
	}
	
}
