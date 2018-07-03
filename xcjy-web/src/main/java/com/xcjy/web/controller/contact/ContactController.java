package com.xcjy.web.controller.contact;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xcjy.application.contact.IContactApplication;
import com.xcjy.entity.contact.Contact;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;


/**
 * 联系我们
 * 
 * @author 支亚州
 *
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	private IContactApplication contactApplication;
	
	/**
	 * 跳转到联系我们列表页面
	 * @return
	 */
	@RequestMapping("/goContactPage")
	public String goContactPage(Model model) {
		return "contact/contact-page";
	}
	
	
	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public PageModel<Contact> query(DataTablesPageModel<Contact> pm, Contact contact,HttpServletRequest request,Model model) {
		pm.getOrderMap().put("createTime", "desc");
		if (StringUtils.isNotEmpty(contact.getOfficeName())) {
			pm.getLikeMap().put("officeName", contact.getOfficeName());
		}
		pm = (DataTablesPageModel<Contact>) contactApplication.query(pm);
		return pm;
	}
	/**
	 * 跳转到联系我们添加页面
	 * @return
	 */
	@RequestMapping("/goMergePage")
	public String goMergePage(Contact contact, Model model,HttpServletRequest request) {
		String id = contact.getId();
		if (id != null) {
			contact = contactApplication.get(id);
			model.addAttribute("contact", contact);
		}
		return "contact/contact-merge";
	}
	/**
	 * 联系我们添加，修改
	 * @return
	 */
	@RequestMapping("/merge")
	@ResponseBody
	public JSONObject merge(Contact contact,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String id = contact.getId();
		if (!"".equals(id)) {
			Contact oldContact = contactApplication.get(id);
			if (oldContact != null) {
				oldContact.setOfficeName(contact.getOfficeName());
				oldContact.setTel(contact.getTel());
			
				contactApplication.update(oldContact);
				jsonObject.put("result", true);
				return jsonObject;
			} else {
				jsonObject.put("result", false);
				jsonObject.put("msg", "未选择要修改的数据！");
				return jsonObject;
			}
		} else {
			UUID uuid = UUID.randomUUID();
			contact.setId(String.valueOf(uuid));
			contact.setCreateTime(new Date());
			
			contactApplication.save(contact);
			jsonObject.put("result", true);
			return jsonObject;
		}
	}
	
	/**
	 * 联系我们删除
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public JSONObject del(Contact contact) {
		JSONObject jsonObject = new JSONObject();
		String id = contact.getId();
		if (id != null) {
			contactApplication.delete(id);
			jsonObject.put("result", true);
			return jsonObject;
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要删除的数据！");
		return jsonObject;
	}
	/**
	 * 查看联系我们信息
	 * @return
	 */
	@RequestMapping("/check")
	public String check(Contact contact, Model model) {
		String id = contact.getId();
		if (id != null) {
			contact = contactApplication.get(id);
			model.addAttribute("contact", contact);
		}
		return "contact/contact-check";
	}

}
