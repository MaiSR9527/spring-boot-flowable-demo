package com.litsoft.flowable.commons.utils;

import java.io.Serializable;
import java.util.List;

/**
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	// 总记录数
	private int total;
	// 列表数据
	private List<?> rows;

	/**
	 * 分页
	 * 
	 * @param list
	 *            列表数据
	 *            总记录数
	 *            每页记录数
	 *            当前页数
	 */
	public PageUtils(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}
	public PageUtils() {
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
