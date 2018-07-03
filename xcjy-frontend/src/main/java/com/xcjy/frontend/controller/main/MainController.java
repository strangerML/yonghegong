package com.xcjy.frontend.controller.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xcjy.application.contact.IContactApplication;
import com.xcjy.application.news.INewsApplication;
import com.xcjy.application.news.IPicNewsApplication;
import com.xcjy.entity.contact.Contact;
import com.xcjy.entity.news.News;
import com.xcjy.entity.news.PicNews;
import com.xcjy.frontend.utils.DataTablesPageModel;

/**
 * 
 * 
 * @author 支亚州
 *
 */
@Controller
public class MainController {
	@Autowired
	private INewsApplication newsApplication;
	@Autowired
	private IContactApplication contactApplication;
	@Autowired
	private IPicNewsApplication picNewsApplication;

	@RequestMapping("/main")
	public String goIndexPage(DataTablesPageModel<News> pm,DataTablesPageModel<News> pmp, Model model) throws ParseException {
		Date begin=new Date();
		begin.getTime();
		System.out.println(begin);
		
		//中心新闻
		pm.getOrderMap().put("createTime", "desc");
		String type = "2";
		Integer postState=0;
		if (postState != null) {
			pm.getEqualMap().put("postState", postState);
		}
		pm.getEqualMap().put("newsTypeId", type);
		pm = (DataTablesPageModel<News>) newsApplication.query(pm);
		List<News> newsList = pm.getPageData();
		List<News> newsLists = new ArrayList<News>();
		for(int i=0;i<6;i++){
			News news = newsList.get(i);
			newsLists.add(news);
		}
		model.addAttribute("newsList", newsLists);
		
		//最新公告
		//List<News> newssList = newsApplication.findMultipleNews();
		pmp.getOrderMap().put("createTime", "desc");
		String type1 = "3";
		pmp.getEqualMap().put("newsTypeId", type1);
		if (postState != null) {
			pmp.getEqualMap().put("postState", postState);
		}
		pmp = (DataTablesPageModel<News>) newsApplication.query(pmp);	
		List<News> newssList = pm.getPageData();
		List<News> newssLists = new ArrayList<News>();
		int i= 0;
		for (News news : newssList) {
			i++;
			newssLists.add(news);
			if(i>7)
				break;
		}
		model.addAttribute("newssList", newssLists);
		
		
		//图片新闻
		
		int satatus=1;
		Map<String, Object> equalMap = new HashMap<String, Object>();
		equalMap.put("status",satatus);
		Map<String, String> orderMap = new HashMap<String, String>();
		orderMap.put("createTime", "desc");
		
		List<PicNews> picList = picNewsApplication.findByFieldsAndOrder(equalMap, orderMap);
		List<PicNews> picNewsList = new ArrayList<PicNews>();
		int j=0;
		for (PicNews picNews : picList) {
			j++;
			picNewsList.add(picNews);
			if(j>4)
				break;
		}
		model.addAttribute("picList", picNewsList);
		
		Map<String,String> orderMap1 = new HashMap<String,String>();
		orderMap1.put("createTime", "asc");
		List<Contact> contactList = contactApplication.findByFieldsAndOrder(null, orderMap1);
		model.addAttribute("contactList",contactList);
		Date end = new Date();
		long bet = begin.getTime()-end.getTime();
		System.out.println(bet);
		return "index";	
	}
	
	
	
}
