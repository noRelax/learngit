/**
 * @Project:ZGHome-Common
 * @FileName:BaseModelAndView.java
 */
package com.ehome.core.frame;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.ehome.core.util.PropertyPlaceholder;
import com.ehome.core.util.StringUtils;

/**
 * @Title:BaseModelAndView
 * @Description:TODO
 * @author:ddl
 * @date:2017年1月8日
 * @version:
 */
public class BaseModelAndView extends ModelAndView {

	// 后台模板名称
	public String templateAdmin = "";

	/**
	 * 
	 */
	public BaseModelAndView(String sss) {
		super(sss);
		super.addObject("template_admin", this.getTemplateAdmin());

	}

	/**
	 * 
	 */
	public BaseModelAndView(String view, HttpServletRequest requset) {

		super(view);
		super.addObject("template_admin", this.getTemplateAdmin());

	}

	/**
	 * @return the template_admin
	 */
	public String getTemplateAdmin() {
		String str = StringUtils.obj2String(
				PropertyPlaceholder.getProperty("template_admin"), "");
		if (str.equals("")) {
			return str;
		} else {
			return "olive";
		}
	}

	/**
	 * @param template_admin
	 *            the template_admin to set
	 */
	public void setTemplateAdmin(String templateAdmin) {
		this.templateAdmin = templateAdmin;
	}

}
