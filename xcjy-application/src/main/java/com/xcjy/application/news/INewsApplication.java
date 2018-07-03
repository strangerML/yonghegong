package com.xcjy.application.news;

import java.util.List;

import com.xcjy.application.base.IBaseApplication;
import com.xcjy.entity.news.News;

/**
 * @author 支亚州
 *
 */
public interface INewsApplication extends IBaseApplication<News> {
	/**
	 * 查询出最新公告，普通高校等7项所有新闻
	 * @param moduleId
	 * @return
	 */
	public List<News> findMultipleNews();
}
