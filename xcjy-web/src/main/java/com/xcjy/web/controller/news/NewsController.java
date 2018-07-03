package com.xcjy.web.controller.news;

import java.util.Date;
import java.util.List;
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
import com.xcjy.application.news.INewsApplication;
import com.xcjy.entity.authority.User;
import com.xcjy.entity.news.News;
import com.xcjy.infra.utils.Constants;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.web.utils.DataTablesPageModel;


/**
 * 新闻
 * 
 * @author 支亚州
 *
 */
@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	private INewsApplication newsApplication;
	
	/**
	 * 跳转到中心简介列表页面
	 * @return
	 */
	@RequestMapping("/goCenterNewsPage")
	public String goCenterNewsPage(Model model) {
		String newsTypeId="1";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到中心新闻列表页面
	 * @return
	 */
	@RequestMapping("/goNewsPage")
	public String goNewsPage(Model model) {
		String newsTypeId="2";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到最新公告列表页面
	 * @return
	 */
	@RequestMapping("/goNoticeNewsPage")
	public String goNoticeNewsPage(Model model) {
		String newsTypeId="3";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到党政工作信息列表页面
	 * @return
	 */
	@RequestMapping("/goActiveNewsPage")
	public String goActiveNewsPage(Model model) {
		String newsTypeId="4";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到普通高校招生列表页面
	 * @return
	 */
	@RequestMapping("/goUniversityNewsPage")
	public String goUniversityNewsPage(Model model) {
		String newsTypeId="5";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到成人高考列表页面
	 * @return
	 */
	@RequestMapping("/goAdultNewsPage")
	public String goAdultNewsPage(Model model) {
		String newsTypeId="6";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到中等学校招生列表页面
	 * @return
	 */
	@RequestMapping("/goMiddleNewsPage")
	public String goMiddleNewsPage(Model model) {
		String newsTypeId="7";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到高中会考列表页面
	 * @return
	 */
	@RequestMapping("/goHighNewsPage")
	public String goHighNewsPage(Model model) {
		String newsTypeId="8";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到自学考试列表页面
	 * @return
	 */
	@RequestMapping("/goSelfNewsPage")
	public String goSelfNewsPage(Model model) {
		String newsTypeId="9";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到小学入学列表页面
	 * @return
	 */
	@RequestMapping("/goPrimaryNewsPage")
	public String goPrimaryNewsPage(Model model) {
		String newsTypeId="10";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	/**
	 * 跳转到初中入学列表页面
	 * @return
	 */
	@RequestMapping("/goJuniorNewsPage")
	public String goJuniorNewsPage(Model model) {
		String newsTypeId="11";
		model.addAttribute("newsTypeId", newsTypeId);
		return "news/news-page";
	}
	
	
	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public PageModel<News> query(DataTablesPageModel<News> pm, News news,HttpServletRequest request,Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = request.getParameter("newsTypeId");
		if (StringUtils.isNotEmpty(news.getNewsTitle())) {
			pm.getLikeMap().put("newsTitle", news.getNewsTitle());
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		return pm;
	}
	/**
	 * 跳转到信息添加页面
	 * @return
	 */
	@RequestMapping("/goMergePage")
	public String goMergePage(News news, Model model,HttpServletRequest request) {
		String id = news.getId();
		String newsTypeId = request.getParameter("newsTypeId");
		if (id != null) {
			news = newsApplication.get(id);
			model.addAttribute("news", news);
			model.addAttribute("newsTypeId",news.getNewsTypeId());
		}else{
			news.setCreateTime(new Date());
			model.addAttribute("news", news);
		}
		if(newsTypeId != null){
			model.addAttribute("newsTypeId",newsTypeId);
		}
		return "news/news-merge1";
	}
	/**
	 * 信息暂存，修改
	 * @return
	 */
	@RequestMapping("/merge")
	@ResponseBody
	public JSONObject merge(News news,HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String id = news.getId();
		int columnType = news.getColumnType();
		User user = getUser(getUser(null));
		if (!"".equals(id)) {
			News oldNews = newsApplication.get(id);
			if (oldNews != null) {
				oldNews.setNewsTitle(news.getNewsTitle());
				oldNews.setContents(news.getContents());
				oldNews.setPictures(news.getPictures());
				oldNews.setColumnType(news.getColumnType());
				oldNews.setNewsTypeId(news.getNewsTypeId());
				oldNews.setCreateTime(news.getCreateTime());
				oldNews.setUpdateTime(new Date());
				oldNews.setFileNames(news.getFileNames());
				oldNews.setPicUrl(news.getPicUrl());
			
				newsApplication.update(oldNews);
				jsonObject.put("result", true);
				return jsonObject;
			} else {
				jsonObject.put("result", false);
				jsonObject.put("msg", "未选择要修改的数据！");
				return jsonObject;
			}
		} else {
			UUID uuid = UUID.randomUUID();
			news.setId(String.valueOf(uuid));
			news.setPublishUserId(user.getId());
			news.setPostState(1);
			news.setNewsCount(0);
			news.setColumnType(columnType);
			
			newsApplication.save(news);
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
	public JSONObject del(News news) {
		JSONObject jsonObject = new JSONObject();
		String id = news.getId();
		if (id != null) {
			newsApplication.delete(id);
			jsonObject.put("result", true);
			return jsonObject;
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要删除的数据！");
		return jsonObject;
	}
	
	/**
	 * 信息冻结
	 * @return
	 */
	@RequestMapping("/freezeNews")
	@ResponseBody
	public JSONObject freezeNews(String newsIds, Model model) {
		JSONObject jsonObject = new JSONObject();
		
		User user = getUser(getUser(null));
		if (newsIds != null) {
			String[] newsIdss=newsIds.split(",");
			for(int i=0;i<newsIdss.length;i++){
				News news = newsApplication.get(newsIdss[i]);
				news.setPostState(1);
				news.setUpdateTime(new Date());
				news.setFreezeUserId(user.getId());
				
				newsApplication.update(news);

			}
			jsonObject.put("result", true);
			return jsonObject;
			
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要冻结的数据！");
		return jsonObject;
	}
	/**
	 * 信息解除冻结
	 * @return
	 */
	@RequestMapping("/relieveFreezeNews")
	@ResponseBody
	public JSONObject relieveFreezeNews(String newsIds, Model model) {
		JSONObject jsonObject = new JSONObject();
		User user = getUser(getUser(null));
		if (newsIds != null) {
			String[] newsIdss=newsIds.split(",");
			for(int i=0;i<newsIdss.length;i++){
				News news = newsApplication.get(newsIdss[i]);
				news.setPostState(0);
				news.setUpdateTime(new Date());
				news.setFreezeUserId(user.getId());
				
				newsApplication.update(news);

			}
			jsonObject.put("result", true);
			return jsonObject;
			
		}
		jsonObject.put("result", false);
		jsonObject.put("msg", "未选择要解除冻结的数据！");
		return jsonObject;
	}
	/**
	 * 中心新闻,最新公告详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至中心新闻,最新公告详情页不反馈查询结果
	 */
	@RequestMapping("/findNewsDetails")
	public String findNewsDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/newsDetails";
	}
	/**
	 * 党务工作，工会活动，政务公开详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至党务工作，工会活动，政务公开详情页不反馈查询结果
	 */
	@RequestMapping("/findNewsDetail")
	public String findNewsDetail(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/newsDetail";
	}
	
	/**
	 * 普通高校招生详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至高级中等学校详情页不反馈查询结果
	 */
	@RequestMapping("/findNewsDetailss")
	public String findNewsDetailss(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/newsDetailss";
	}
	
	/**
	 * 成人高等学校招生详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至成人高等学校招生详情页不反馈查询结果
	 */
	@RequestMapping("/findAdultDetails")
	public String findAdultDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/adultDetails";
	}
	
	/**
	 * 中等学校招生详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至中等学校招生详情页不反馈查询结果
	 */
	@RequestMapping("/findMiddleDetails")
	public String findMiddleDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/middleDetails";
	}
	
	/**
	 * 高中会考详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至高中会考详情页不反馈查询结果
	 */
	@RequestMapping("/findHighsDetails")
	public String findHighsDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/highsDetails";
	}
	/**
	 * 自考详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至自考详情页不反馈查询结果
	 */
	@RequestMapping("/findSelfsDetails")
	public String findSelfsDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/selfsDetails";
	}
	
	/**
	 * 小学入学详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至小学入学详情页不反馈查询结果
	 */
	@RequestMapping("/findSchoolsDetails")
	public String findSchoolsDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/schoolsDetails";
	}
	/**
	 * 初中入学详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至初中入学详情页不反馈查询结果
	 */
	@RequestMapping("/findJuniorDetails")
	public String findJuniorDetails(String id, Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/juniorDetails";
	}
	/**
	 * 跳转到中心简介页面
	 * @return
	 */
	@RequestMapping("/goCenterNews")
	public String goCenterNews(String id,Model model) {
		if (id != null) {
			News news = newsApplication.get(id);
			model.addAttribute("news", news);
		}
		return "news/news-center";
	}

	/**
	 * 查询网站总点击次数
	 * @param 
	 * @return 返回查询到的总点击次数
	 */
	@RequestMapping("/goNewsCounts")
	public String goNewsCounts(Model model) {
		List<News> newsList = newsApplication.findAll();
		int num = 0;
		for(int i=0;i<newsList.size();i++){
			News news = newsList.get(i);
			int j = news.getNewsCount();
			num += j;
		}
		model.addAttribute("num", num);
		return "news/news-count";
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
