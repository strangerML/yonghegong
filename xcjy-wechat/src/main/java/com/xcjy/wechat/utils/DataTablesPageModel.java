package com.xcjy.wechat.utils;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.xcjy.infra.utils.page.PageModel;


public class DataTablesPageModel<E> implements PageModel<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8683652793586768851L;

	/**
	 * 请求次数计数器，每次发送给服务器后又原封返回
	 */
	private Integer draw;

	/**
	 * 第一条数据的起始位置，比如0代表第一条数据
	 */
	private Integer start=0;

	/**
	 * 告诉服务器每页显示的条数，这个数字会等于返回的记录数，可能会大于因为服务器可能没有那么多数据。这个也可能是-1，代表需要返回全部数据(
	 * 尽管这个和服务器处理的理念有点违背)
	 */
	private Integer length=10;

	/**
	 * 查询结果集
	 */
	private List<E> data;

	/**
	 * 没有过滤的记录数（数据库里总共记录数）
	 */
	private Integer recordsTotal;

	/**
	 * 过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
	 */
	private Integer recordsFiltered;

	/**
	 * 可选。你可以定义一个错误来描述服务器出了问题后的友好提示
	 */
	private String error;

	/**
	 * equal查询条件
	 */
	private HashMap<String, Object> equalMap = new HashMap<String, Object>();

	/**
	 * like查询条件
	 */
	private HashMap<String, String> likeMap = new HashMap<String, String>();

	/**
	 * 排序条件
	 */
	private HashMap<String, String> orderMap = new HashMap<String, String>();

	public void setOrderMap(HttpServletRequest request) {
		String orderNumStr = request.getParameter("order[0][column]");
		String orderFiledName = request.getParameter("columns[" + orderNumStr + "][data]");
		String orderDir = request.getParameter("order[0][dir]");
		if (StringUtils.isNotEmpty(orderFiledName) && StringUtils.isNotEmpty(orderDir)) {
			orderMap.clear();
			orderMap.put(orderFiledName, orderDir);
		}
	}

	public void setOrderMap(HttpServletRequest request, String[] data) {
		if (data == null || data.length == 0) {
			setOrderMap(request);
			return;
		}
		String orderNumStr = request.getParameter("order[0][column]");
		try {
			Integer orderNum = new Integer(orderNumStr);
			if (orderNum > data.length) {
				return;
			}
			String orderFiledName = data[orderNum];
			String orderDir = request.getParameter("order[0][dir]");
			if (StringUtils.isNotEmpty(orderFiledName) && StringUtils.isNotEmpty(orderDir)) {
				orderMap.clear();
				orderMap.put(orderFiledName, orderDir);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public Integer getPageSize() {
		return length;
	}

	@Override
	public Integer getStartIndex() {
		return start;
	}

	public HashMap<String, Object> getEqualMap() {
		return equalMap;
	}

	public void setEqualMap(HashMap<String, Object> equalMap) {
		this.equalMap = equalMap;
	}

	public HashMap<String, String> getLikeMap() {
		return likeMap;
	}

	public void setLikeMap(HashMap<String, String> likeMap) {
		this.likeMap = likeMap;
	}

	public HashMap<String, String> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(HashMap<String, String> orderMap) {
		this.orderMap = orderMap;
	}

	@Override
	public void setTotalCount(Integer totalCount) {
		recordsTotal = totalCount;
		recordsFiltered = totalCount;
	}

	@Override
	public void setPageData(List<E> pageData) {
		data = pageData;
	}

	public List<E> getData() {
		return data;
	}

	@Override
	public List<E> getPageData() {
		return data;
	}

}
