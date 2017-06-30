/**
 * @Project:ZGHome
 * @FileName:SqlUtil.java
 */
package com.ehome.core.util;

/**
 * @Title:SqlUtil
 * @Description:TODO
 * @author:ddl
 * @date:2017年1月4日
 * @version:
 */
public class SqlUtil {

	/**
	 * 通过配置的表前缀得到完整的表名
	 * 
	 * @param table
	 * @return
	 */
	public static String prefixTable(String table) {
		String prefix = StringUtils.obj2String(
				PropertyPlaceholder.getProperty("prefix"), "");
		if (prefix.equals("")) {
			return "m_" + table;
		} else {
			return prefix + table;
		}
		// return "sys_"+table;
	}

	/**
	 * 将表前缀和表名组合成新的完整表名
	 * 
	 * @param table
	 * @param prefix
	 * @return
	 */
	public static String prefixTable(String table, String prefix) {
		if (StringUtils.isEmpty(prefix)) {
			return prefixTable(table);
		} else {
			return prefix + table;
		}
	}

	/**
	 * 通过Controller的类全名，得到完整的表名(将驼峰命名转化了下划线命名)
	 * 
	 * @param contallerNamer
	 * @return
	 */
	public static String getPrefixTable(String contallerNamer) {
		return prefixTable(StringUtils.toUnderlineName(ClassUtil
				.getName(contallerNamer)));
	}

	/**
	 * 通过Controller的类全名，得到完整的表名(将驼峰命名转化了下划线命名)
	 * 
	 * @param contallerNamer
	 * @return
	 */
	public static String getPrefixTable(String contallerNamer, String prefix) {
		return prefixTable(
				StringUtils.toUnderlineName(ClassUtil.getName(contallerNamer)),
				prefix);
	}

}
