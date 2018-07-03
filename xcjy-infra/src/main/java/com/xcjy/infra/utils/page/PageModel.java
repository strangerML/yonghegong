package com.xcjy.infra.utils.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface PageModel<E> extends Serializable {

	/**
	 * 默认每页记录数
	 */
	public static final Integer DEFAULT_PAGE_SIZE = 15;

	// ---------------- 页面传入的参数 转化成分页查询的条件 ---------------//

	/**
	 * 获取每页查询记录数
	 * 
	 * @return
	 */
	public Integer getPageSize();

	/**
	 * 获取查询第一条数据的起始位置，比如0代表第一条数据
	 * 
	 * @return
	 */
	public Integer getStartIndex();

	/**
	 * 获取equal查询条件
	 * 
	 * @return
	 */
	public HashMap<String, Object> getEqualMap();

	/**
	 * 获取like查询条件
	 * 
	 * @return
	 */
	public HashMap<String, String> getLikeMap();

	/**
	 * 获取order排序条件
	 * 
	 * @return
	 */
	public HashMap<String, String> getOrderMap();

	/**
	 * 设置总记录数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount);

	/**
	 * 设置查询结果
	 * 
	 * @param pageData
	 */
	public void setPageData(List<E> pageData);
	
	/**
	 * 获取查询结果
	 * @return
	 */
	public List<E> getPageData();

}
