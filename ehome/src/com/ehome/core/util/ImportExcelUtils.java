package com.ehome.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ehome.core.annotation.ExcelField;
import com.ehome.core.annotation.ExcelFieldType;

/**
 * Excel导入工具类 后续实现
 *
 * @Title:ImportExcelUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:12:32
 * @version:
 */
public final class ImportExcelUtils {

	private ImportExcelUtils() {
	}

	/**
	 * 默认的开始读取的行位置为第一行（索引值为0）
	 */
	private final static int READ_START_POS = 0;

	/**
	 * 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）
	 */
	private final static int READ_END_POS = 0;

	/**
	 * 默认只操作一个sheet
	 */
	private final static boolean ONLY_ONE_SHEET = true;

	/**
	 * 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）
	 */
	private final static int SELECTED_SHEET = 0;

	/**
	 * 默认从第一个sheet开始读取（索引值为0）
	 */
	private final static int READ_START_SHEET = 0;

	/**
	 * 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）
	 */
	private final static int READ_END_SHEET = 0;

	/**
	 * 设定开始读取的位置，默认为0
	 */
	private final static int startReadPos = READ_START_POS;

	/**
	 * 设定结束读取的位置，默认为0，用负数来表示倒数第n行
	 */
	private final static int endReadPos = READ_END_POS;

	/**
	 * 设定是否只操作第一个sheet
	 */
	private final static boolean onlyReadOneSheet = ONLY_ONE_SHEET;

	/**
	 * 设定操作的sheet在索引值
	 */
	private final static int selectedSheetIdx = SELECTED_SHEET;

	/**
	 * 设定操作的sheet的名称
	 */
	private final static String selectedSheetName = "";

	/**
	 * 设定开始读取的sheet，默认为0
	 */
	private final static int startSheetIdx = READ_START_SHEET;

	/**
	 * 设定结束读取的sheet，默认为0，用负数来表示倒数第n行
	 */
	private final static int endSheetIdx = READ_END_SHEET;

	public static <T> Collection<T> importExcel(InputStream in, String ext,
			Class<T> clazz) {
		Collection<T> dataList = new ArrayList<>();
		Field fields[] = clazz.getDeclaredFields();
		Map<String, List<Object>> fieldMap = new HashMap<>();
		for (Field field : fields) {
			ExcelField excelField = field.getAnnotation(ExcelField.class);
			if (excelField != null) {
				PropertyDescriptor pd = null;
				try {
					pd = new PropertyDescriptor(field.getName(), clazz);
				} catch (IntrospectionException e1) {
					e1.printStackTrace();
					throw new RuntimeException(e1.getMessage());
				}
				Method setMethod = pd.getWriteMethod();
				List<Object> paramList = new ArrayList<>();
				try {
					paramList.add(excelField.columnType());
					paramList.add(setMethod);
					fieldMap.put(excelField.columnName(), paramList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			dataList = readExcel(in, ext, fieldMap, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList;
	}

	public static <T> Collection<T> readExcel(InputStream in, String ext,
			Map<String, List<Object>> fieldMap, Class<T> target)
			throws IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel
		Workbook wb = null;
		Sheet sheet = null;
		// List<HSSFRow> rowList = new ArrayList<>();
		Collection<T> dataList = new ArrayList<>();
		try {
			// 读取Excel
			// wb = new HSSFWorkbook(in);
			if (ext.equals("xls")) {// 兼容2003excel
				wb = new HSSFWorkbook(in);
			} else if (ext.equals("xlsx")) {// 兼容2007excel
				wb = new XSSFWorkbook(in);
			}
			// 读取Excel 97-03版，xls格式
			int sheetCount = 1;// 需要操作的sheet数量

			if (onlyReadOneSheet) { // 只操作一个sheet
				// 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
				sheet = "".equals(selectedSheetName) ? wb
						.getSheetAt(selectedSheetIdx) : wb
						.getSheet(selectedSheetName);
			} else { // 操作多个sheet
				sheetCount = wb.getNumberOfSheets();// 获取可以操作的总数量
			}
			// 获取sheet数目
			for (int t = startSheetIdx; t < sheetCount + endSheetIdx; t++) {
				// 获取设定操作的sheet
				if (!onlyReadOneSheet) {
					sheet = wb.getSheetAt(t);
				}
				// 获取最后行号
				int lastRowNum = sheet.getLastRowNum();
				if (lastRowNum > 0) {// 如果>0，表示有数据
					// out("\n开始读取名为【"+sheet.getSheetName()+"】的内容：");
				}
				Row row = null;
				Map<Integer, Object> titleMap = new HashMap<>();
				// 循环读取
				for (int i = startReadPos + 1; i <= lastRowNum + endReadPos; i++) {
					row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					if (row != null) {
						if (i == 1) {// 获取标题行
							for (int j = 0; j < row.getLastCellNum(); j++) {
								titleMap.put(j,
										getCellValue(row.getCell(j), ""));
							}
						} else {// 数据行
							T object = target.newInstance();
							// rowList.add(row);
							// 获取每一单元格的值
							boolean isInsert = false;
							for (int j = 0; j < row.getLastCellNum(); j++) {
								String value = "", title = "";
								if (titleMap.get(j) != null
										&& !"".equals(titleMap.get(j))) {
									title = titleMap.get(j).toString();// 获取列对应的标题
									if (fieldMap.containsKey(title)) {
										List<Object> list = fieldMap.get(title);
										ExcelFieldType columnType = (ExcelFieldType) list
												.get(0);
										value = getCellValue(row.getCell(j),
												columnType.toString()).trim();
										if (StringUtils.isNoneBlank(value)) {
											isInsert = true;
											Method setMethod = (Method) list
													.get(1);
											Type[] type = setMethod
													.getGenericParameterTypes();
											String para = type[0].toString();
											if (para.equals("class java.lang.String")) {
												setMethod.invoke(object, value);
											}
										}
									}
								}
							}
							if (isInsert)
								dataList.add(object);
						}
					}
				}
			}
			return dataList;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			if(null !=wb ){
			    wb.close();
			}
		}
		return dataList;
	}

	/***
	 * 读取单元格的值
	 *
	 * @Title: getCellValue
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell, String type) {
		Object result = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是日期类型
					Date dt = HSSFDateUtil.getJavaDate(cell
							.getNumericCellValue());// 获取成DATE类型
					result = DateUtils.getTime(dt);
					break;
				} else {
					if (type.equals("INTEGER")) {
						result = (int) (cell.getNumericCellValue());
						break;
					} else if (type.equals("BIGDECIMAL")) {
						BigDecimal big = new BigDecimal(
								cell.getNumericCellValue()).setScale(2,
								BigDecimal.ROUND_HALF_UP);
						result = big.toString();
						break;
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						result = cell.getStringCellValue();
						break;
					}
				}
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				// result = cell.getCellFormula();
				try {
					result = String.valueOf(cell.getStringCellValue());
				} catch (IllegalStateException e) {
					java.text.DecimalFormat df = new java.text.DecimalFormat(
							"#0.00");
					result = String.valueOf(df.format(cell
							.getNumericCellValue()));
				}
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}
		return result.toString();
	}
}
