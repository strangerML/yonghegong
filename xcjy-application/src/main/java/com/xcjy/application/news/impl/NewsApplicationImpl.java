package com.xcjy.application.news.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.application.news.INewsApplication;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.news.NewsDao;
import com.xcjy.entity.news.News;

/**
 * 信息发布application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class NewsApplicationImpl extends BaseApplicationImpl<News>implements INewsApplication {

	@Autowired
	private NewsDao newsDao;

	@Override
	public BaseDao<News> getBaseDao() {
		return this.newsDao;
	}

	@Override
	public List<News> findMultipleNews() {
		// TODO Auto-generated method stub
		return newsDao.findMultipleNews();
	}
	
}
