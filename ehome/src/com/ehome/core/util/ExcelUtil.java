package com.ehome.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 共分为六部完成根据模板导出excel操作： 第一步、设置excel模板路径（setSrcPath）
 * 第二步、设置要生成excel文件路径（setDesPath） 第三步、设置模板中哪个Sheet列（setSheetName）
 * 第四步、获取所读取excel模板的对象（getSheet）
 * 第五步、设置数据（分为种类型数据：setCellStrValue、setCellDateValue、setCellDoubleValue、
 * setCellBoolValue、setCellCalendarValue、 setCellRichTextStrValue） 第六步、完成导出
 * （exportToNewFile）
 *
 * @author Administrator
 *
 */
public class ExcelUtil {
	private String srcXlsPath = ""; // // excel模板路径
	private String desXlsPath = "";
	private String sheetName = "";
	POIFSFileSystem fs = null;
	HSSFWorkbook wb = null;
	HSSFSheet sheet = null;

	/**
	 * 第一步、设置excel模板路径
	 *
	 * @param srcXlsPath
	 */
	public void setSrcPath(String srcXlsPath) {
		this.srcXlsPath = srcXlsPath;
	}

	/**
	 * 第二步、设置要生成excel文件路径
	 *
	 * @param desXlsPath
	 */
	public void setDesPath(String desXlsPath) {
		this.desXlsPath = desXlsPath;
	}

	/**
	 * 第三步、设置模板中哪个Sheet列
	 *
	 * @param sheetName
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 第四步、获取所读取excel模板的对象
	 */
	public void getSheet() {
		try {
			File fi = new File(srcXlsPath);
			if (!fi.exists()) {
				System.out.println("模板文件:" + srcXlsPath + "不存在!");
				return;
			}
			fs = new POIFSFileSystem(new FileInputStream(fi));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 第五步、设置字符串类型的数据
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --行值
	 * @param value
	 *            --字符串类型的数据
	 */
	public void setCellStrValue(int rowIndex, int cellnum, String value) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);// 先设置为自动换行
		cell.setCellStyle(cellStyle);
		cellStyle.setBorderLeft((short)2);
		cellStyle.setBorderBottom((short)2);
		cellStyle.setBorderRight((short)2);
        cellStyle.setBorderTop((short)2);
		cell.setCellValue(value);

	}

	/**
	 * 设置富文本数据及对齐格式
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --列
	 * @param value
	 * @param halign
	 *            水平方向对其方式
	 * @param valign
	 *            垂直方向对其方式
	 */
	public void setCellTextStrValue(int rowIndex, int cellnum, String value,
			short halign, short valign) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);// 先设置为自动换行
		cellStyle.setAlignment(halign); // 设置单元格水平方向对其方式,
		cellStyle.setVerticalAlignment(valign); // 设置单元格垂直方向对其方式
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(value));
	}

	public void setCellTextStrValue2(int rowIndex, int cellnum, String value,
			short halign, short valign) {
		float height = ExcelUtil.getExcelCellAutoHeight(value, 15f);
		sheet.getRow(rowIndex).setHeightInPoints(height);
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);// 先设置为自动换行
		cellStyle.setAlignment(halign); // 设置单元格水平方向对其方式,
		cellStyle.setVerticalAlignment(valign); // 设置单元格垂直方向对其方式
		cellStyle.setBorderLeft((short)1);
		cellStyle.setBorderBottom((short)1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new HSSFRichTextString(value));
	}

	/**
	 * 第五步、设置日期/时间类型的数据
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --列值
	 * @param value
	 *            --日期/时间类型的数据
	 */
	public void setCellDateValue(int rowIndex, int cellnum, Date value) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		cell.setCellValue(value);
	}

	/**
	 * 第五步、设置浮点类型的数据
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --列值
	 * @param value
	 *            --浮点类型的数据
	 */
	public void setCellDoubleValue(int rowIndex, int cellnum, double value) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		cell.setCellValue(value);
	}

	/**
	 * 第五步、设置Bool类型的数据
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --列值
	 * @param value
	 *            --Bool类型的数据
	 */
	public void setCellBoolValue(int rowIndex, int cellnum, boolean value) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		cell.setCellValue(value);
	}

	/**
	 * 第五步、设置日历类型的数据
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --列值
	 * @param value
	 *            --日历类型的数据
	 */
	public void setCellCalendarValue(int rowIndex, int cellnum, Calendar value) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		cell.setCellValue(value);
	}

	/**
	 * 第五步、设置富文本字符串类型的数据。可以为同一个单元格内的字符串的不同部分设置不同的字体、颜色、下划线
	 *
	 * @param rowIndex
	 *            --行值
	 * @param cellnum
	 *            --列值
	 * @param value
	 *            --富文本字符串类型的数据
	 */
	public void setCellRichTextStrValue(int rowIndex, int cellnum,
			RichTextString value) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		cell.setCellValue(value);
	}

	/**
	 * 第六步、完成导出
	 */
	public void exportToNewFile() {
		FileOutputStream out;
		try {
			out = new FileOutputStream(desXlsPath);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 第六步、完成导出
	 */
	public void exportToNewFile(OutputStream out) {
		try {
			// out = new FileOutputStream(desXlsPath);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入行
	 *
	 * @param starRow
	 *            开始的行数
	 * @param rows
	 *            插入行数
	 */
	public void insertRow(int starRow, int rows) {
		sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows, true, false);
		starRow = starRow - 1;
		for (int i = 0; i < rows; i++) {
			HSSFRow sourceRow = null;
			HSSFRow targetRow = null;
			HSSFCell sourceCell = null;
			HSSFCell targetCell = null;
			short m;
			starRow = starRow + 1;
			sourceRow = sheet.getRow(starRow);
			targetRow = sheet.createRow(starRow + 1);
			targetRow.setHeight(sourceRow.getHeight());
			for (m = sourceRow.getFirstCellNum(); m < sourceRow
					.getLastCellNum(); m++) {
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);
				// targetCell.setEncoding(sourceCell.setgetEncoding());
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());
			}
		}
	}

	/**
	 * 合并单元格 firstRow 0-based lastRow 0-based firstCol 0-based lastCol 0-based
	 */
	public void mergeRow(int firstRow, int lastRow, int firstCol, int lastCol) {
		// 指定合并区域
		// sheet.addMergedRegion(new Region(firstRow, (short) lastRow,firstCol,
		// (short) lastCol));
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
	}

	public String getCellValue(int rowIndex, int cellIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(cellIndex);
		return cell.getStringCellValue();
	}

	/**
	 * 根据行列获取cell，设置cellstyle
	 *
	 * @param rowIndex
	 *            行号
	 * @param cellnum
	 *            列号
	 * @param cs
	 *            CellStyle的属性，short类型
	 */
	public void setBorderTopStyle(int rowIndex, int cellnum, short cs) {
		HSSFCell cell = sheet.getRow(rowIndex).getCell(cellnum);
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderTop(cs);
		cell.setCellStyle(style);
	}

	public static float getExcelCellAutoHeight(String str, float fontCountInline) {
		float defaultRowHeight = 14.00f;// 每一行的高度指定
		float defaultCount = 0.00f;
		for (int i = 0; i < str.length(); i++) {
			float ff = getregex(str.substring(i, i + 1));
			defaultCount = defaultCount + ff;
		}
		return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;// 计算
	}

	public static float getregex(String charStr) {
//		if (charStr == " ") {
		if (" ".equals(charStr)) {
			return 0.5f;
		}
		// 判断是否为字母或字符
		if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
			return 0.5f;
		}
		// 判断是否为全角
		if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
			return 1.00f;
		}
		// 全角符号 及中文
		if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
			return 1.00f;
		}
		return 0.5f;
	}

	public static void main(String[] args) throws IOException {
		/*
		 * //excel模板路径 File fi=new File("D:\\test.xls"); POIFSFileSystem fs =
		 * new POIFSFileSystem(new FileInputStream(fi)); //读取excel模板
		 * HSSFWorkbook wb = new HSSFWorkbook(fs); //读取了模板内所有sheet内容 HSSFSheet
		 * sheet = wb.getSheetAt(0); //在相应的单元格进行赋值 HSSFCell cell =
		 * sheet.getRow(1).getCell(0); //从0开始 cell.setCellValue("2016年3月29日");
		 * HSSFCell cell2 = sheet.getRow(2).getCell(2);
		 * cell2.setCellValue("张三"); HSSFCell cell3 =
		 * sheet.getRow(2).getCell(5); cell3.setCellValue("女"); //修改模板内容导出新模板
		 * FileOutputStream out = new FileOutputStream("D:/export.xls");
		 * wb.write(out); out.close();
		 */
		ExcelUtil excel = new ExcelUtil();
		excel.setSrcPath("D:/test.xls");
		// excel.setDesPath("D:/export.xls");
		excel.setSheetName("Sheet1");
		excel.getSheet();
		excel.setCellStrValue(1, 0, "2016年3月29日");
		excel.setCellStrValue(2, 2, "张三22");
		excel.setCellStrValue(2, 5, "男");
		// excel.exportToNewFile();
		FileOutputStream out = new FileOutputStream("D:/export2.xls");
		excel.exportToNewFile(out);
	}
}