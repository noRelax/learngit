package com.ehome.cloud.app.help.dto;

import com.ehome.core.model.BaseEntity;

/**
 * 帮扶维权说明接口响应给APP的值对象
 * 
 * @Title: ResponseHelpExplainDto
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月30日 上午10:07:54
 * @version
 */
public class ResponseHelpExplainDto extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103537509619346353L;

	public String content;
	public String serviceButton;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getServiceButton() {
		return serviceButton;
	}

	public void setServiceButton(String serviceButton) {
		this.serviceButton = serviceButton;
	}

}
