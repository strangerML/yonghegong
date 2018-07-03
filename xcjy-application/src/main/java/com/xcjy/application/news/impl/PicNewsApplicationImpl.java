package com.xcjy.application.news.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.application.news.IPicNewsApplication;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.news.PicNewsDao;
import com.xcjy.entity.news.PicNews;

/**
 * 图片新闻application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class PicNewsApplicationImpl extends BaseApplicationImpl<PicNews>implements IPicNewsApplication {

	@Autowired
	private PicNewsDao picNewsDao;

	@Override
	public BaseDao<PicNews> getBaseDao() {
		return this.picNewsDao;
	}
	
}
