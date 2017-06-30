package com.ehome.core.frame;

import java.io.Serializable;
import java.util.List;

/**
 * datatable分页结果集封装
 * 
 * @Title:Pagination
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月6日 下午5:27:48
 * @version:@param <E>
 */
public class Pagination<E> implements Serializable {
	private static final long serialVersionUID = 6962730353809415026L;

	private int sEcho; // Client request times
	private int iTotalRecords; // Total records number without conditions
	private int iTotalDisplayRecords; // Total records number with conditions
	private List<E> data;

	// public Integer getTotal() {
	// return total;
	// }
	//
	// public void setTotal(Integer total) {
	// this.total = total;
	// }
	//
	// public Integer getPages() {
	// return pages;
	// }
	//
	// public void setPages(Integer pages) {
	// this.pages = pages;
	// }

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public Pagination() {
		super();
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	/**
	 * @param pages
	 *            总页面数
	 * @param total
	 *            总记录数
	 * @param rows
	 *            行清单
	 */
	public Pagination(Integer sEcho, Integer iTotalRecords,
			Integer iTotalDisplayRecords, List<E> data) {
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.data = data;
	}
}