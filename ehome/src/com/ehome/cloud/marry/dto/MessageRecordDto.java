/*
 * 广州陨石互联网科技有限公司
 * 
 * 项目名称 : ZGHome-Common
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by admin
 */
package com.ehome.cloud.marry.dto;

import java.io.Serializable;

/**
 * //TODO 添加类/接口功能描述
 * @author 张钟武
 */
public class MessageRecordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String msgFrom;
	private String msgTo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public String getMsgTo() {
		return msgTo;
	}

	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}

}
