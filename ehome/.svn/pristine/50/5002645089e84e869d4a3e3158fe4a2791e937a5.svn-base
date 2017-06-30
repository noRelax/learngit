/**
 * @Project:ZGHome-Common
 * @FileName:PageBean.java
 */
package com.ehome.core.frame;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * @Title:PageBean
 * @Description:TODO
 * @author:ddl
 * @date:2017年1月17日
 * @version:
 */
public class PageBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private long total; // 总记录数
	private List<T> list; // 结果集
	private int pageNum; // 第几页
	private int pageSize; // 每页记录数
	private int pages; // 总页数
	private int size; // 当前页的数量<=pageSize
	public String pageHtml;

	public PageBean(List<T> list) {
		if (list instanceof Page) {
			Page<T> page = (Page<T>) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
			this.size = page.size();
		}
	}

	public PageBean(List<T> list, String url) {
		if (list instanceof Page) {
			Page<T> page = (Page<T>) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
			this.size = page.size();
			this.pageHtml = getPageHtml(url);
		}
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	
	/**
	 * 拼接view的分頁導航
	 * @param url
	 * @return
	 */
	public String getPageHtml(String url) {

		StringBuffer sb = new StringBuffer();

		if (this.total > 0) {

			sb.append("<ul class=\"pagination\">");
			if (pageNum > 3)
				sb.append("<li><a href=\"" + url + "&page=1" + "\">1</a></li><li><a>...</a></li>");

			if (pageNum < 3) {
				sb.append(getLi(1, url));
				sb.append(getLi(2, url));
				sb.append(getLi(3, url));
				sb.append(getLi(4, url));
				sb.append(getLi(5, url));

			} else if (pageNum > this.pages - 3) {

				sb.append(getLi((pages - 4), url));
				sb.append(getLi((pages - 3), url));
				sb.append(getLi((pages - 2), url));
				sb.append(getLi((pages - 1), url));
				sb.append(getLi(pages, url));
			} else {
				sb.append(getLi((pageNum - 2), url));
				sb.append(getLi((pageNum - 1), url));
				sb.append(getLi(pageNum, url));
				sb.append(getLi(pageNum + 1, url));
				sb.append(getLi(pageNum + 2, url));
			}
			if (pageNum <= this.pages - 3)
				sb.append("<li><a>...</a></li><li><a href=\"" + url + "&page=" + pages + "\">" + this.pages
						+ "</a></li>");
			sb.append("</ul>");
		}
		return sb.toString();
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	private String getActive(int page) {
		if (pageNum == page) {
			return " class=\"active\"";
		} else {
			return "";
		}

	}

	private String getActive(long page) {
		if (pageNum == page) {
			return " class=\"active\"";
		} else {
			return "";
		}

	}

	private String getLi(int showpage, String url) {

		StringBuffer sb = new StringBuffer();
		if (showpage <= pages)
			sb.append("<li><a " + getActive(showpage) + " href=\"" + url + "&page=" + showpage + "\">" + showpage
					+ "</a></li>");
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private String getLi(long showpage, String url) {

		StringBuffer sb = new StringBuffer();
		sb.append("<li><a " + getActive(showpage) + " href=\"" + url + "&page=" + showpage + "\">" + showpage
				+ "</a></li>");
		return sb.toString();
	}

}