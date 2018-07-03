package com.xcjy.web.controller.news;

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
import com.xcjy.application.news.IPicNewsApplication;
import com.xcjy.entity.authority.User;
import com.xcjy.entity.news.News;
import com.xcjy.entity.news.PicNews;
import com.xcjy.infra.utils.Constants;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;


/**
 * 图片新闻
 * 
 * @author 支亚州
 *
 */
@Controller
@RequestMapping("/picNews")
public class PicNewsController {

	@Autowired
	private IPicNewsApplication picNewsApplication;
	
	/**
	 * 跳转到中心简介列表页面
	 * @return
	 */
	@RequestMapping("/goPicNewsPage")
	public String goPicNewsPage(Model model) {
		return "news/picNews-page";
	}
	
	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public PageModel<PicNews> query(DataTablesPageModel<PicNews> pm, PicNews picNews,HttpServletRequest request,Model model) {
		pm.getOrderMap().put("createTime", "desc");
		if (StringUtils.isNotEmpty(picNews.getName())) {
			pm.getLikeMap().put("name", picNews.getName());
		}
		pm = (DataTablesPageModel<PicNews>) picNewsApplication.query(pm);
		return pm;
	}
	/**
	 * 跳转到信息添加页面
	 * @return
	 */
	@RequestMapping("/goMergePage")
	public String goMergePage(PicNews picNews, Model model,HttpServletRequest request) {
		String id = picNews.getId();
		if (id != null) {
			picNews = picNewsApplication.get(id);
			model.addAttribute("picNews", picNews);
		}
		return "news/picNews-merge";
	}
	/**
	 * 信息暂存，修改
	 * @return
	 */
	@RequestMapping("/merge")
	@ResponseBody
	public JSONObject merge(PicNews picNews,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String id = picNews.getId();
		User user = getUser(getUser(null));
		if (!"".equals(id)) {
			PicNews oldPicNews = picNewsApplication.get(id);
			if (oldPicNews != null) {
				oldPicNews.setName(picNews.getName());
				oldPicNews.setContent(picNews.getContent());
				oldPicNews.setPictures(picNews.getPictures());
				oldPicNews.setUpdateTime(new Date());
				oldPicNews.setStatus(picNews.getStatus());
			
				picNewsApplication.update(oldPicNews);
				jsonObject.put("result", true);
				return jsonObject;
			} else {
				jsonObject.put("result", false);
				jsonObject.put("msg", "未选择要修改的数据！");
				return jsonObject;
			}
		} else {
			UUID uuid = UUID.randomUUID();
			picNews.setId(String.valueOf(uuid));
			picNews.setCreateTime(new Date());
			picNews.setPublishUserId(user.getId());
			
			picNewsApplication.save(picNews);
			jsonObject.put("result", true);
			return jsonObject;
		}
	}
	
	/**
	 * 信息删除
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public JSONObject del(PicNews picNews) {
		JSONObject jsonObject = new JSONObject();
		String id = picNews.getId();
		if (id != null) {
			picNewsApplication.delete(id);
			jsonObject.put("result", true);
			return jsonObject;
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要删除的数据！");
		return jsonObject;
	}
	/**
	 * 查看服务信息
	 * @return
	 */
	@RequestMapping("/check")
	public String check(PicNews picNews, Model model) {
		String id = picNews.getId();
		if (id != null) {
			picNews = picNewsApplication.get(id);
			model.addAttribute("picNews", picNews);
		}
		return "news/picNews-check";
	}
	
	/**
	 * 图片发布
	 * @return
	 */
	@RequestMapping("/releasePicNews")
	@ResponseBody
	public JSONObject releasePicNews(String picNewsIds, Model model) {
		JSONObject jsonObject = new JSONObject();
		if (picNewsIds != null) {
			String[] newsIds=picNewsIds.split(",");
			for(int i=0;i<newsIds.length;i++){
				PicNews picNews = picNewsApplication.get(newsIds[i]);
				picNews.setStatus(1);
				picNews.setUpdateTime(new Date());
				
				picNewsApplication.update(picNews);

			}
			jsonObject.put("result", true);
			return jsonObject;
			
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要在主页显示的图片！");
		return jsonObject;
	}
	/**
	 * 图片解除主页上显示
	 * @return
	 */
	@RequestMapping("/relieveReleasePicNews")
	@ResponseBody
	public JSONObject relieveReleasePicNews(String picNewsIds, Model model) {
		JSONObject jsonObject = new JSONObject();
		if (picNewsIds != null) {
			String[] newsIds=picNewsIds.split(",");
			for(int i=0;i<newsIds.length;i++){
				PicNews picNews = picNewsApplication.get(newsIds[i]);
				picNews.setStatus(0);
				picNews.setUpdateTime(new Date());
				
				picNewsApplication.update(picNews);

			}
			jsonObject.put("result", true);
			return jsonObject;
			
		}
		
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要解除冻结的数据！");
		return jsonObject;
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
