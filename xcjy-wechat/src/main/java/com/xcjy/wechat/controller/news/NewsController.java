package com.xcjy.wechat.controller.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xcjy.application.news.INewsApplication;
import com.xcjy.entity.news.News;
import com.xcjy.infra.utils.page.PageModel;
import com.xcjy.wechat.utils.DataTablesPageModel;

/**
 * 
 * @author BQ
 *
 */
@Controller
@RequestMapping("/news")
public class NewsController {
	@Autowired
	private INewsApplication newsApplication;

	/**
	 * 获取首页新闻内容
	 * @param pm
	 * @param newsInfo
	 * @return
	 */
	@RequestMapping("/getMainNews")
	@ResponseBody
	public PageModel<News> goMainNewsInfoPage(DataTablesPageModel<News> pm, News newsInfo) {
		pm.getOrderMap().put("createTime", "desc");
		pm.getEqualMap().put("postState", 0);
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		return pm;
	}
	/**
	 * 新闻详情
	 * 
	 * @param newsInfo
	 *            新闻表
	 * @param model
	 * @return 跳至新闻详情页不反馈查询结果
	 */
	@RequestMapping("/goFindByIdToNewsInfoDetails")
	public String goFindByIdToNewsInfoDetails(News newsInfo, Model model) {
		String id = newsInfo.getId();
		if (id != null) {
			newsInfo = newsApplication.get(id);
			model.addAttribute("newsInfo", newsInfo);
		}
		return "news/newsinfodetails";
	}
	/**
	 * 跳转不同分页的新闻页面
	 * @param newsInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/goNewInfoPage")
	public String goHealthInfoPage(News newsInfo, Model model) {
		String newsType = 3+"";
		if (newsInfo != null && newsInfo.getNewsTypeId() != null) {
			newsType = newsInfo.getNewsTypeId();
		}
		model.addAttribute("newsType", newsType);
		return "news/newsInfo";
	}
	/**
	 * 获取新闻内容
	 * @param pm
	 * @param newsInfo
	 * @return
	 */
	@RequestMapping("/getnews")
	@ResponseBody
	public PageModel<News> goNewsInfoPage(DataTablesPageModel<News> pm, News newsInfo) {
		if(newsInfo.getNewsTypeId() != null){
			pm.getOrderMap().put("createTime", "desc");
			pm.getEqualMap().put("newsTypeId", newsInfo.getNewsTypeId());
			pm.getEqualMap().put("postState", 0);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		return pm;
	}
	/**
	 * 跳转党政工作页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/goPartyInfoPage")
	public String goPartyInfoPage(News newsInfo, Model model) {
		String columnType = 9+"";
		model.addAttribute("columnType", columnType);
		return "party/partyNews";
	}
	
	/**
	 * 获取新闻内容
	 * @param pm
	 * @param newsInfo
	 * @return
	 */
	@RequestMapping("/getpartynews")
	@ResponseBody
	public PageModel<News> getpartynews(DataTablesPageModel<News> pm, News newsInfo) {
		pm.getEqualMap().put("newsTypeId", "4");
		pm.getOrderMap().put("createTime", "desc");
		pm.getEqualMap().put("postState", 0);
		if(newsInfo.getColumnType()+""!=null){
			pm.getEqualMap().put("columnType", newsInfo.getColumnType());
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		return pm;
	}
	/**
	 * 政策公告
	 * @param model
	 * @return
	 */
	@RequestMapping("/goPolicyPage")
	public String goPolicyPage(Model model){
		
		return "policy/policyPage";
	}
	/**
	 * 跳转不同等级的学校
	 * @param newsInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/goPolicyClassPage")
	public String goPolicyClassPage(News newsInfo, Model model){
		String newsType = 10+"";
		if (newsInfo != null && newsInfo.getNewsTypeId() != null) {
			newsType = newsInfo.getNewsTypeId();
		}
		model.addAttribute("newsType", newsType);
		return "policy/policyClassPage";
	}
	/**
	 * 跳转不同等级的学校的政策公告
	 * @param newsInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("goPolicyClassNewsPage")
	public String goPolicyClassNewsPage(News newsInfo, Model model){
		if (newsInfo != null ) {
			if(newsInfo.getNewsTypeId() != null){
				String newsType = newsInfo.getNewsTypeId();
				model.addAttribute("newsType", newsType);
			}
			if(newsInfo.getColumnType()+""!=null){
				Integer columnType = newsInfo.getColumnType();
				model.addAttribute("columnType", columnType);
			}
		}
		return "policy/policyClassNewsPage";
	}
	
	/**
	 * 获取政策公告新闻内容
	 * @param pm
	 * @param newsInfo
	 * @return
	 */
	@RequestMapping("/getpolicynews")
	@ResponseBody
	public PageModel<News> getpolicynews(DataTablesPageModel<News> pm, News newsInfo) {
		pm.getOrderMap().put("createTime", "desc");
		pm.getEqualMap().put("postState", 0);
		if(newsInfo.getNewsTypeId()!=null){
			pm.getEqualMap().put("newsTypeId", newsInfo.getNewsTypeId());
		}
		if(newsInfo.getColumnType()+""!=null){
			pm.getEqualMap().put("columnType", newsInfo.getColumnType());
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		return pm;
	}
	/**
	 * 获取政策公告新闻内容详情
	 * @param newsInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/goFindByIdToPolicyNewsDetails")
	public String goFindByIdToPolicyNewsDetails(News newsInfo, Model model) {
		String id = newsInfo.getId();
		if (id != null) {
			newsInfo = newsApplication.get(id);
			model.addAttribute("newsInfo", newsInfo);
		}
		return "policy/policyNewsDetails";
	}
	
	@RequestMapping("/goCenterIntro")
	public String goCenterIntro(){
		return "centerintro/centerIntro";
	}
	
	
	/**
	 * 跳转到中心简介页面
	 * @return
	 */
	@RequestMapping("/goCenterNewsPage")
	public String goCenterNewsPage(Model model) {
		String newsTypeId="1";
		News news = newsApplication.getByField("newsTypeId", newsTypeId);
		model.addAttribute("newsInfo", news);
		return "centerintro/centerDetails";
	}	
		
}
