package com.xcjy.dao.news;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.xcjy.dao.authority.UserDao;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.User;
import com.xcjy.entity.news.News;
import com.xcjy.entity.news.NewsType;
import com.xcjy.infra.utils.page.PageModel;

/**
 * 新闻dao
 * @author 支亚州
 *
 */
@Repository
public class NewsDao extends BaseDao<News> {
	@Autowired
	private UserDao userDao;
	@Autowired
	private NewsTypeDao newsTypeDao;
	
	
	@Override
	public PageModel<News> query(PageModel<News> pageModel) {
		PageModel<News> pm = super.query(pageModel);
		return buidPM(pm);
	}

	private PageModel<News> buidPM(PageModel<News> pm) {
		
		//用户集合
		Map<Long, String> userMap = new HashMap<>();
		List<User> userList = userDao.findAll();
		if(userList !=null && !userList.isEmpty()){
			for(User user : userList){
				userMap.put(user.getId(), user.getUserName());
			}
		}
		//新闻类型集合
		Map<String, String> typeMap = new HashMap<>();
		List<NewsType> typeList = newsTypeDao.findAll();
		if(typeList!=null && !typeList.isEmpty()){
			for(NewsType type : typeList){
				typeMap.put(type.getId(), type.getTypeName());
			}
		}
		
		//补充实体
		List<News> nList = pm.getPageData();
		if(nList != null && !nList.isEmpty()){
			for(News news : nList){
				if(news.getPublishUserId()!=null){
					news.setPublishUserName(userMap.get(news.getPublishUserId()));
				}
				if(news.getNewsTypeId()!=null){
					news.setNewsTypeName(typeMap.get(news.getNewsTypeId()));
				}
			}
		}
		
		return pm;
	}
	
	@Override
	public News get(Serializable id) {
		News news = super.get(id);
		return buidNEWS(news);
	}

	private News buidNEWS(News news) {
		
		if(news.getNewsTypeId()!=null){
			NewsType type = newsTypeDao.get(news.getNewsTypeId());
			if(type!=null && !type.getTypeName().isEmpty()){
				news.setNewsTypeName(type.getTypeName());
			}
		}
		return news;
	}
	

	public List<News> findMultipleNews(){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from news where newsTypeId in(:newsTypeId1,:newsTypeId2,:newsTypeId3,:newsTypeId4,:newsTypeId5,:newsTypeId6,:newsTypeId7,:newsTypeId8) and postState=0 order by createTime desc");
		 Map<String, Object> paramMap = new HashMap<String, Object>();    
         paramMap.put("newsTypeId1", 3);
         paramMap.put("newsTypeId2", 5);
         paramMap.put("newsTypeId3", 6);
         paramMap.put("newsTypeId4", 7);
         paramMap.put("newsTypeId5", 8);
         paramMap.put("newsTypeId6", 9);
         paramMap.put("newsTypeId7", 10);
         paramMap.put("newsTypeId8", 11);
         List<News> newsList = new ArrayList<News>();
         try {
 			List<Map<String, Object>> resultList = namedTemplate.queryForList(sql.toString(), paramMap);
 			if (resultList != null && !resultList.isEmpty()) {
 				
 				for (Map<String, Object> resultMap : resultList) {
 					News news =new News();
 					news.setId((String) resultMap.get("id"));
 					news.setNewsTitle((String) resultMap.get("newsTitle"));
 					news.setPicUrl((String)resultMap.get("picUrl"));
 					news.setPublishUserId((Long) resultMap.get("publishUserId"));
 					news.setPublishUserName((String) resultMap.get("publishUserName"));
 					news.setContents((String) resultMap.get("contents"));
 					news.setCreateTime((Date) resultMap.get("createTime"));
 					news.setUpdateTime((Date) resultMap.get("updateTime"));
 					news.setNewsCount((Integer) resultMap.get("newsCount"));
 					news.setPictures((String) resultMap.get("pictures"));
 					news.setFileNames((String) resultMap.get("fileNames"));
 					news.setStick((String) resultMap.get("stick"));
 					news.setNewsTypeId((String) resultMap.get("newsTypeId"));
 					news.setNewsTypeName((String) resultMap.get("newsTypeName"));
 					news.setPostState((Integer) resultMap.get("postState"));
 					
 					newsList.add(news);
 				}
 			}
 		} catch (DataAccessException e) {
 			e.printStackTrace();
 		}
 		return newsList;
		
	}
}
