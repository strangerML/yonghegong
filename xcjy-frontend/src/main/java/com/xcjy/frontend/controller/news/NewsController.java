package com.xcjy.frontend.controller.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xcjy.application.news.INewsApplication;
import com.xcjy.entity.news.News;
import com.xcjy.frontend.utils.DataTablesPageModel;

/**
 * 
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
	 * 跳转到中心简介页面
	 * @return
	 */
	@RequestMapping("/goCenterNewsPage")
	public String goCenterNewsPage(Model model) {
		String newsTypeId="1";
		News news = newsApplication.getByField("newsTypeId", newsTypeId);
		model.addAttribute("news", news);
		return "news/news-center";
	}
	/**
	 * 跳转到中心新闻列表页面
	 * @return
	 */
	@RequestMapping("/goNewsCenterPage")
	public String goNewsCenterPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "2";
		model.addAttribute("newsTypeId", newsTypeId);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-page";
	}
	/**
	 * 跳转到最新公告列表页面
	 * @return
	 */
	@RequestMapping("/goNoticeNewsPage")
	public String goNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "3";
		model.addAttribute("newsTypeId", newsTypeId);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null){
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
			
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-page";
	}
	

	
	/**
	 * 跳转到党政工作信息列表页面
	 * @return
	 */
	@RequestMapping("/goPartyNewsPage")
	public String goPartyNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "4";
		model.addAttribute("newsTypeId", newsTypeId);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-party";
	}
	
	/**
	 * 跳转到党务工作列表页面
	 * @return
	 */
	@RequestMapping("/goPartyAffairsNewsPage")
	public String goPartyAffairsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "4";
		Integer columnType = 9;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-partyAffairs";
	}
	/**
	 * 跳转到政务公开列表页面
	 * @return
	 */
	@RequestMapping("/goAffairsPublicityNewsPage")
	public String goAffairsPublicityNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "4";
		Integer columnType = 10;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-partyAffairs";
	}
	/**
	 * 跳转到工会活动列表页面
	 * @return
	 */
	@RequestMapping("/goActivityNewsPage")
	public String goActivityNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "4";
		Integer columnType = 8;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-partyAffairs";
	}
	/**
	 * 跳转到普通高校公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goUniversityNoticeNewsPage")
	public String goUniversityNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "5";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-university";
	}
	/**
	 * 跳转到普通高校招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goUniversityStudentsNewsPage")
	public String goUniversityStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "5";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-university";
	}
	/**
	 * 跳转到普通高校问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goUniversityProblemPage")
	public String goUniversityProblemPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "5";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-university";
	}
	/**
	 * 跳转到普通高校相关下载列表页面
	 * @return
	 */
	@RequestMapping("/goUniversityUploadPage")
	public String goUniversityUploadPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "5";
		Integer columnType = 5;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-university";
	}
	/**
	 * 跳转到普通高校综合查询页面
	 * @return
	 */
	@RequestMapping("/goUniversityCheck")
	public String goUniversityCheck(Model model) {

		return "news/news-universityCheck";
	}
	/**
	 * 跳转到普通高校综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goUniversityResult")
	public String goUniversityResult(Model model) {

		return "news/news-universityResult";
	}
	
	/**
	 * 跳转到成人高考公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goAdultNoticeNewsPage")
	public String goAdultNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "6";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-adult";
	}
	/**
	 * 跳转到成人高考招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goAdultStudentsNewsPage")
	public String goAdultStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "6";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-adult";
	}
	/**
	 * 跳转到成人高考问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goAdultProblemNewsPage")
	public String goAdultProblemNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "6";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-adult";
	}
	
	/**
	 * 跳转到成人高考综合查询页面
	 * @return
	 */
	@RequestMapping("/goAdultCheck")
	public String goAdultCheck(Model model) {

		return "news/news-adultCheck";
	}
	/**
	 * 跳转到成人高考综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goAdultResult")
	public String goAdultResult(Model model) {

		return "news/news-adultResult";
	}
	/**
	 * 跳转到中等学校招生公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goMiddleNoticeNewsPage")
	public String goMiddleNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "7";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-middle";
	}
	/**
	 * 跳转到中等学校招生招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goMiddleStudentsNewsPage")
	public String goMiddleStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "7";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-middle";
	}
	/**
	 * 跳转到中等学校招生问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goMiddleProblemNewsPage")
	public String goMiddleProblemNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "7";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-middle";
	}
	
	/**
	 * 跳转到中等学校招生综合查询页面
	 * @return
	 */
	@RequestMapping("/goMiddleCheck")
	public String goMiddleCheck(Model model) {

		return "news/news-middleCheck";
	}
	/**
	 * 跳转到中等学校招生综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goMiddleResult")
	public String goMiddleResult(Model model) {

		return "news/news-middleResult";
	}
	/**
	 * 跳转到高中会考公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goHighsNoticeNewsPage")
	public String goHighsNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "8";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-highs";
	}
	/**
	 * 跳转到高中会考招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goHighsStudentsNewsPage")
	public String goHighsStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "8";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-highs";
	}
	/**
	 * 跳转到高中会考问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goHighsProblemNewsPage")
	public String goHighsProblemNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "8";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-highs";
	}
	
	/**
	 * 跳转到高中会考综合查询页面
	 * @return
	 */
	@RequestMapping("/goHighsCheck")
	public String goHighsCheck(Model model) {

		return "news/news-highsCheck";
	}
	/**
	 * 跳转到高中会考综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goHighsResult")
	public String goHighsResult(Model model) {

		return "news/news-highsResult";
	}
	/**
	 * 跳转到自学考试公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goSelfsNoticeNewsPage")
	public String goSelfsNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "9";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-selfs";
	}
	/**
	 * 跳转到自学考试招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goSelfsStudentsNewsPage")
	public String goSelfsStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "9";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-selfs";
	}
	/**
	 * 跳转到自学考试问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goSelfsProblemNewsPage")
	public String goSelfsProblemNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "9";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-selfs";
	}
	
	/**
	 * 跳转到自学考试综合查询页面
	 * @return
	 */
	@RequestMapping("/goSelfsCheck")
	public String goSelfsCheck(Model model) {

		return "news/news-selfsCheck";
	}
	/**
	 * 跳转到自学考试综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goSelfsResult")
	public String goSelfsResult(Model model) {

		return "news/news-selfsResult";
	}
	/**
	 * 跳转到小学入学公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goSchoolsNoticeNewsPage")
	public String goSchoolsNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "10";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-schools";
	}
	/**
	 * 跳转到小学入学招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goSchoolstudentsNewsPage")
	public String goSchoolsStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "10";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-schools";
	}
	/**
	 * 跳转到小学入学问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goSchoolsProblemNewsPage")
	public String goSchoolsProblemNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "10";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-schools";
	}
	
	/**
	 * 跳转到小学入学综合查询页面
	 * @return
	 */
	@RequestMapping("/goSchoolsCheck")
	public String goSchoolsCheck(Model model) {

		return "news/news-schoolsCheck";
	}
	/**
	 * 跳转到小学入学综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goSchoolsResult")
	public String goSchoolsResult(Model model) {

		return "news/news-schoolsResult";
	}
	
	/**
	 * 跳转到初中入学公告通知列表页面
	 * @return
	 */
	@RequestMapping("/goJuniorNoticeNewsPage")
	public String goJuniorNoticeNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "11";
		Integer columnType = 2;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-junior";
	}
	/**
	 * 跳转到初中入学招生政策列表页面
	 * @return
	 */
	@RequestMapping("/goJuniortudentsNewsPage")
	public String goJuniorStudentsNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "11";
		Integer columnType = 3;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-junior";
	}
	/**
	 * 跳转到初中入学问题解答列表页面
	 * @return
	 */
	@RequestMapping("/goJuniorProblemNewsPage")
	public String goJuniorProblemNewsPage(DataTablesPageModel<News> pm, Model model) {
		pm.getOrderMap().put("createTime", "desc");
		String newsTypeId = "11";
		Integer columnType = 4;
		model.addAttribute("newsTypeId", newsTypeId);
		model.addAttribute("columnType", columnType);
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		if (newsTypeId != null) {
			pm.getEqualMap().put("newsTypeId", newsTypeId);
		}
		if (columnType != null) {
			pm.getEqualMap().put("columnType", columnType);
		}
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		
		model.addAttribute("pm", pm);

		return "news/news-junior";
	}
	
	/**
	 * 跳转到初中入学综合查询页面
	 * @return
	 */
	@RequestMapping("/goJuniorCheck")
	public String goJuniorCheck(Model model) {

		return "news/news-juniorCheck";
	}
	/**
	 * 跳转到初中入学综合查询结果页面
	 * @return
	 */
	@RequestMapping("/goJuniorResult")
	public String goJuniorResult(Model model) {

		return "news/news-juniorResult";
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
	public String findNewsDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findNewsDetail(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
			model.addAttribute("news", news);
		}
		return "news/newsDetail";
	}
	/**
	 * 高级中等学校招生详情
	 * 
	 * @param news
	 *            新闻表
	 * @param model
	 * @return 跳至高级中等学校详情页不反馈查询结果
	 */
	@RequestMapping("/findNewsDetailss")
	public String findNewsDetailss(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findAdultDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findMiddleDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findHighsDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findSelfsDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findSchoolsDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
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
	public String findJuniorDetails(News news, Model model) {
		String id = news.getId();
		if (id != null) {
			news = newsApplication.get(id);
			news.setNewsCount(news.getNewsCount()+1);
			
			newsApplication.update(news);
			model.addAttribute("news", news);
		}
		return "news/juniorDetails";
	}
	
}
