package com.xcjy.application.news.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.application.news.INewsTypeApplication;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.news.NewsTypeDao;
import com.xcjy.entity.news.NewsType;

/**
 * 新闻类型application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class NewsTypeApplicationImpl extends BaseApplicationImpl<NewsType>implements INewsTypeApplication {

	@Autowired
	private NewsTypeDao newsTypeDao;

	@Override
	public BaseDao<NewsType> getBaseDao() {
		return this.newsTypeDao;
	}
	
}
