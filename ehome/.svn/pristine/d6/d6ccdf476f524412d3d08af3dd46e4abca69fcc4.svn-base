package com.ehome.core.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ehome.core.annotation.ExcelField;

/**
 * 导出Excel工具类
 * 
 * @Title:ExportExcelUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:13:04
 * @version:
 */
public final class ExportExcelUtils {

	private ExportExcelUtils() {

	}
	public static <T> void exportExcel(List<T> list, String sheetName,
			Class<T> clazz, int sheetSize, HttpServletResponse response)
			throws IOException {
		OutputStream out = response.getOutputStream();
		Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
		List<Field> fields = new ArrayList<>();
		// 得到所有field并存放到一个list中.
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelField.class)) {
				fields.add(field);
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
		// excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
		if (sheetSize > 65536 || sheetSize < 1) {
			sheetSize = 65536;
		}
		double sheetNo = Math.ceil(list.size() / sheetSize);// 取出一共有多少个sheet.
		for (int index = 0; index <= sheetNo; index++) {
			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
			workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
			HSSFRow row;
			HSSFCell cell;// 产生单元格
			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				cell = row.createCell(i);// 创建列
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
				cell.setCellValue(excelField.columnName());// 写入列名
			}
			int startNo = index * sheetSize;
			int endNo = Math.min(startNo + sheetSize, list.size());
			// 写入各条记录,每条记录对应excel表中的一行
			for (int i = startNo; i < endNo; i++) {
				row = sheet.createRow(i + 1 - startNo);
				T vo = (T) list.get(i); // 得到导出对象.
				for (int j = 0; j < fields.size(); j++) {
					Field field = fields.get(j);// 获得field.
					field.setAccessible(true);// 设置实体类私有属性可访问
					try {
						cell = row.createCell(j);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(field.get(vo) == null ? "" : String
								.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			String fileName = "Excel-"
					+ String.valueOf(System.currentTimeMillis()).substring(4,
							13) + ".xls";
			String headStr = "attachment; filename=\"" + fileName + "\"";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-disposition", headStr);
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			workbook.close();
		}
	}
}
