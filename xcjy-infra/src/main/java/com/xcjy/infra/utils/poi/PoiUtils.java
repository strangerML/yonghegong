package com.xcjy.infra.utils.poi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.xcjy.infra.exception.BaseException;
import com.xcjy.infra.exception.ExceptionMessage;
import com.xcjy.infra.exception.SystemException;
import com.xcjy.infra.utils.beanutils.PropertyUtils;

/**
 * 
 * @author Nick Zhang
 * 
 * @createdOn:2013年11月16日 16:23:09 读取Excell的utils
 */
public class PoiUtils {

	public static final Logger logger = LoggerFactory.getLogger(PoiUtils.class);

	/**
	 * excel xls格式
	 */
	public static final String EXCEL_TYPE_XLS = "xls";

	/**
	 * excel xlsx格式
	 */
	public static final String EXCEL_TYPE_XLSX = "xlsx";

	/**
	 * 默认的日期格式
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final NumberFormat numberformatter = new DecimalFormat("#.#");

	/**
	 * 根据文件路径获取excel工作簿
	 * 
	 * @param filePath
	 * @return excel工作簿, 调用后可以强转成HSSFWorkbook(.xls), XSSFWorkbook(.xlsx)
	 */
	public static Workbook getWorkbook(String filePath) {
		Workbook wb = null;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			String fileSuffix = filePath.substring(filePath.lastIndexOf(".") + 1);
			if (EXCEL_TYPE_XLS.equals(fileSuffix)) {
				wb = new HSSFWorkbook(is);
			} else if (EXCEL_TYPE_XLSX.equals(fileSuffix)) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new BaseException("Excel格式不正确！");
			}
		} catch (FileNotFoundException e) {
			throw new BaseException("Excel文件不存在！", e);
		} catch (IOException e) {
			throw new BaseException("读取Excel文件出错！", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("---excel--关流失败--", e);
				}
			}
		}
		return wb;
	}

	/**
	 * 根据文件获取excel工作簿
	 * 
	 * @param file
	 *            excel文件
	 * @param fileType
	 * @return excel工作簿
	 */
	public static Workbook getWorkbook(File file, String fileType) {
		Workbook wb = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			if (EXCEL_TYPE_XLS.equals(fileType)) {
				wb = new HSSFWorkbook(is);
			} else if (EXCEL_TYPE_XLSX.equals(fileType)) {
				wb = new XSSFWorkbook(is);
			} else {
				throw new BaseException("Excel格式不正确！");
			}
		} catch (FileNotFoundException e) {
			throw new BaseException("Excel文件不存在！", e);
		} catch (IOException e) {
			throw new BaseException("读取Excel文件出错！", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("---excel--关流失败--", e);
				}
			}
		}
		return wb;
	}

	/**
	 * 读取excel
	 * 
	 * @param stream
	 *            输入流
	 * @param fileType
	 *            excel类型
	 * @return
	 */
	public static Workbook getWorkbook(InputStream stream, String fileType) {
		Workbook wb = null;
		try {
			if (EXCEL_TYPE_XLS.equals(fileType)) {
				wb = new HSSFWorkbook(stream);
			} else if (EXCEL_TYPE_XLSX.equals(fileType)) {
				wb = new XSSFWorkbook(stream);
			} else {
				throw new BaseException("Excel格式不正确！");
			}
		} catch (IOException e) {
			throw new BaseException("读取Excel文件出错！", e);
		}
		return wb;
	}

	/**
	 * 根据工作簿读取所有工作表
	 * 
	 * @param wb
	 *            工作簿
	 * @return 所有工作表(读取工作表后可以强转为所需要类型工作表(HSSFSheet(xls), XSSFSheet(xlsx)))
	 */
	public static List<Sheet> getSheets(Workbook wb) {
		List<Sheet> sheetList = new ArrayList<Sheet>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			sheetList.add(sheet);
		}
		return sheetList;
	}

	/**
	 * 根据工作表第一行表头计算工作表总共有多少列
	 * 
	 * @param sheet
	 * @return
	 */
	public static int countColumnNumber(Sheet sheet) {
		int columnCount = 0;
		Row titleRow = sheet.getRow(0);
		if (titleRow != null) {
			columnCount = titleRow.getPhysicalNumberOfCells();
		}
		return columnCount;
	}

	/**
	 * 读取excel指定行每列数据(列数由首行列数决定)
	 * 
	 * @param sheet
	 *            指定工作表
	 * @param rownum
	 *            指定行
	 * @return 指定行各列的String值。（日期类型数据格式为yyyy-MM-dd）
	 */
	public static String[] getColumnsStringValue(Sheet sheet, int rownum) {
		int cells = countColumnNumber(sheet);
		String[] result = new String[cells];
		Row row = sheet.getRow(rownum);
		for (int i = 0; i < cells; i++) {
			Cell cell = row.getCell(i);
			String value = null;
			if (cell != null) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: {// 文本
					value = cell.getStringCellValue().trim();
					break;
				}
				case Cell.CELL_TYPE_BOOLEAN: {// 布尔值类型
					value = String.valueOf(cell.getBooleanCellValue());
					break;
				}
				case Cell.CELL_TYPE_NUMERIC: {// 数值类型
					double numericValue = cell.getNumericCellValue();
					if (DateUtil.isCellDateFormatted(cell)) {
						value = new SimpleDateFormat(DATE_PATTERN).format(DateUtil.getJavaDate(numericValue));
					} else {
						// value = numberformatter.format(numericValue);
						value = new BigDecimal(numericValue).toString();
					}
					break;
				}
				case Cell.CELL_TYPE_FORMULA: {// 公式
					value = "";
					break;
				}
				}
			}
			result[i] = value;
		}
		return result;
	}

	/**
	 * 读取excel指定行每列数据(列数由首行列数决定)
	 * 
	 * @param sheet
	 *            指定工作表
	 * @param rownum
	 *            指定行
	 * @return 指定行各列的值。
	 */
	public static Object[] getColumnsObjectValue(Sheet sheet, int rownum) {
		int cells = countColumnNumber(sheet);
		Object[] result = new Object[cells];
		Row row = sheet.getRow(rownum);
		for (int i = 0; i < cells; i++) {
			Cell cell = row.getCell(i);
			Object value = null;
			if (cell != null) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING: {// 文本
					value = cell.getStringCellValue().trim();
					break;
				}
				case Cell.CELL_TYPE_BOOLEAN: {// 布尔值类型
					value = cell.getBooleanCellValue();
					break;
				}
				case Cell.CELL_TYPE_NUMERIC: {// 数值类型
					double numericValue = cell.getNumericCellValue();
					if (DateUtil.isCellDateFormatted(cell)) {
						value = DateUtil.getJavaDate(numericValue);
					} else {
						value = numericValue;
					}
					break;
				}
				case Cell.CELL_TYPE_FORMULA: {// 公式
					value = "";
					break;
				}
				}
			}
			result[i] = value;
		}
		return result;
	}

	/**
	 * 获取excel单元格的Date值
	 * 
	 * @param columnValue
	 * @return
	 */
	public static Date getDateValue(Object columnValue) {
		if (columnValue instanceof Date) {
			return (Date) columnValue;
		} else if (columnValue instanceof String) {
			String value = (String) columnValue;
			if (StringUtils.isEmpty(value)) {
				return null;
			} else {
				try {
					return DateUtils.parseDate(value, DATE_PATTERN);
				} catch (Exception e) {
					throw new IllegalArgumentException("Can not convert to Date type: " + columnValue);
				}
			}
		} else if (columnValue instanceof Double) {
			Double doubleValue = (Double) columnValue;
			return DateUtil.getJavaDate(doubleValue);
		}
		return null;
	}

	/**
	 * 获取Excel单元格String值
	 * 
	 * @param columnValue
	 * @return
	 */
	public static String getStringValue(Object columnValue) {
		if (columnValue instanceof String) {
			return (String) columnValue;
		} else if (columnValue instanceof Boolean) {
			return String.valueOf((Boolean) columnValue);
		} else if (columnValue instanceof Double) {
			Double doubleValue = (Double) columnValue;
			return numberformatter.format(doubleValue.doubleValue());
		} else if (columnValue instanceof Date) {
			return new SimpleDateFormat(DATE_PATTERN).format((Date) columnValue);
		}
		return null;
	}

	/**
	 * 获取Excel单元格Decimal值
	 * 
	 * @param columnValue
	 * @return
	 */
	public static BigDecimal getDecimalValue(Object columnValue) {
		if (columnValue instanceof String) {
			return new BigDecimal((String) columnValue);
		} else if (columnValue instanceof Double) {
			return new BigDecimal((Double) columnValue);
		}
		return null;
	}

	/**
	 * 读取Excel指定计录
	 * 
	 * @param sheet
	 *            工作表
	 * @param rownum
	 *            数据开始行，从0行开始
	 * @return
	 */
	public static PoiRecord getPoiRecord(Sheet sheet, int rownum) {
		return new PoiRecord(getColumnsStringValue(sheet, rownum));
	}

	/**
	 * 根据工作表获取数据计录列表
	 * 
	 * @param sheet
	 *            工作表
	 * @param rowNum
	 *            从第几行开始读数据
	 * @return
	 */
	public static List<PoiRecord> getPoiRecords(Sheet sheet, int rowNum) {
		List<PoiRecord> poiRecordList = new ArrayList<PoiRecord>();
		for (int i = rowNum; i < sheet.getPhysicalNumberOfRows(); i++) {
			poiRecordList.add(getPoiRecord(sheet, i));
		}
		return poiRecordList;
	}

	/**
	 * 创建excel
	 * 
	 * @param excelType
	 *            xls xlsx
	 * @return
	 */
	public static Workbook createWorkbook(String excelType) {
		// 创建工作薄也可以使用WorkbookFactory
		// use the handy factory class
		// org.apache.poi.ss.usermodel.WorkbookFactory.
		// WorkbookFactory.create(file);
		Workbook workbook = null;
		if (EXCEL_TYPE_XLS.equals(excelType)) {// 创建2003(xls)
			workbook = new HSSFWorkbook();
		} else if (EXCEL_TYPE_XLSX.equals(excelType)) {// 创建2007(xlsx)
			workbook = new XSSFWorkbook();
		} else {
			throw new SystemException(new ExceptionMessage("excel.type.error"));
		}
		return workbook;
	}

	/**
	 * 创建工作表（工作表名以安全的方式创建。） Note:工作表名不能超过31个字符，不能包含0x0000 0x0003 : \ * ? /
	 * []）（以安全的方式创建时会将非法字符转换为空格，超长时截断）
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 */
	public static Sheet createSheet(Workbook workbook, String sheetName) {
		String safeName = WorkbookUtil.createSafeSheetName(sheetName);
		return workbook.createSheet(safeName);
	}

	/**
	 * 填充工作表数据
	 * 
	 * @param workbook
	 *            工作簿
	 * @param sheet
	 *            工作表
	 * @param title
	 *            表头
	 * @param dataList
	 *            数据列表
	 * @param properties
	 *            属性名
	 */
	public static <T> void fillSheetData(Workbook workbook, Sheet sheet, String[] title, List<T> dataList,
			String[] properties) {
		fillSheetData(workbook, sheet, title, dataList, properties, DATE_PATTERN, null);
	}

	/**
	 * 为单元格设置下拉列表数据校验
	 * 
	 * @param sheet
	 *            工作表
	 * @param range
	 *            下拉列表应该范围。数据形式，需要4个int值.(开始行号，结束行号，开始列号，结束列号。下标从0开始)
	 * @param listOfValues
	 *            限制输入的值列表
	 */
	public static void configDropDownList(Sheet sheet, int[] range, String[] listOfValues) {
		DataValidationHelper dvHelper = sheet.getDataValidationHelper();
		DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(listOfValues);
		CellRangeAddressList addressList = new CellRangeAddressList(range[0], range[1], range[2], range[3]);
		DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
		// Note the check on the actual type of the DataValidation object.
		// If it is an instance of the XSSFDataValidation class then the
		// boolean value 'false' must be passed to the
		// setSuppressDropDownArrow()
		// method and an explicit call made to the setShowErrorBox() method.
		if (validation instanceof XSSFDataValidation) {
			validation.setSuppressDropDownArrow(true);
			validation.setShowErrorBox(true);
		} else {
			// If the Datavalidation contains an instance of the
			// HSSFDataValidation
			// class then 'true' should be passed to the
			// setSuppressDropDownArrow()
			// method and the call to setShowErrorBox() is not necessary.
			validation.setSuppressDropDownArrow(false);
		}
		sheet.addValidationData(validation);
	}

	/**
	 * 原版优化版
	 *
	 * LCC
	 * 
	 * @param fillSheetDataHelper
	 */
	@SuppressWarnings("rawtypes")
	public static <T> void fillSheetData(FillSheetDataHelper fillSheetDataHelper) {
		List datas = fillSheetDataHelper.getDatas();
		List<FillSheetDataHelper.FillSheetData> config = fillSheetDataHelper.getConfigs();
		Workbook workbook = fillSheetDataHelper.getWorkbook();
		Sheet sheet = fillSheetDataHelper.getSheet();

		int rowNum = 0;
		// 创建title
		if (fillSheetDataHelper.isHasTitle()) {

			CellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
			titleCellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
			titleCellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
			titleCellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
			titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
			titleCellStyle.setWrapText(true);// 设置自动换行
			Font titleCellFont = workbook.createFont();
			titleCellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体显示
			titleCellFont.setFontHeightInPoints((short) 12);// 设置字体大小
			titleCellStyle.setFont(titleCellFont);

			Row titleRow = sheet.createRow(rowNum);

			if (fillSheetDataHelper.isHasSN()) {
				Cell cell = titleRow.createCell(0);
				cell.setCellStyle(titleCellStyle);
				String cellTitle = fillSheetDataHelper.getSnTitle();
				sheet.setColumnWidth(0, 512 * (cellTitle.length() + 4));
				cell.setCellValue(cellTitle);
			}

			for (int i = 1, j = config.size() + 1; i < j; i++) {
				Cell cell = titleRow.createCell(i);
				cell.setCellStyle(titleCellStyle);
				String cellTitle = config.get(i - 1).getTitle();
				sheet.setColumnWidth(i, 512 * (cellTitle.length() + 4));
				cell.setCellValue(cellTitle);
			}
		}

		for (int i = 0, x = datas.size(); i < x; i++) {
			Object data = datas.get(i);
			Row dataRow = sheet.createRow(++rowNum);

			CellStyle contentCellStyle = workbook.createCellStyle();
			contentCellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
			contentCellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
			contentCellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
			contentCellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
			contentCellStyle.setWrapText(true);// 设置自动换行

			if (fillSheetDataHelper.isHasSN()) {
				Cell cell = dataRow.createCell(0);
				cell.setCellStyle(contentCellStyle);
				cell.setCellValue(i + 1);
			}

			for (int j = 1, k = config.size() + 1; j < k; j++) {
				FillSheetDataHelper.FillSheetData conf = config.get(j - 1);
				Cell cell = dataRow.createCell(j);
				cell.setCellStyle(contentCellStyle);
				String propertyName = conf.getAttribute();
				Object property = PropertyUtils.getProperty(data, propertyName);

				if (conf.getValueFilter() != null) { // 自定义属性处理
					Object cellData = conf.getValueFilter().process(data, propertyName, property);
					if (cellData != null) {
						property = cellData;
					}
				}

				if (property instanceof Boolean) {
					cell.setCellValue((Boolean) property);
				} else if (property instanceof Short || property instanceof Integer || property instanceof Long
						|| property instanceof Float || property instanceof Double) {
					cell.setCellValue(Double.parseDouble(property.toString()));
				} else if (property instanceof BigDecimal) {
					cell.setCellValue(((BigDecimal) property).doubleValue());
				} else if (StringUtils.isNotEmpty(conf.getPattern()) && property instanceof Date) {
					cell.setCellValue(new SimpleDateFormat(conf.getPattern()).format((Date) property));
				} else if (StringUtils.isNotEmpty(conf.getPattern()) && property instanceof Calendar) {
					cell.setCellValue(new SimpleDateFormat(conf.getPattern()).format(((Calendar) property).getTime()));
				} else {
					cell.setCellValue(property != null ? property.toString() : "");
				}
			}
		}
	}

	/**
	 * 填充工作表数据
	 * 
	 * @param workbook
	 *            工作簿
	 * @param sheet
	 *            工作表
	 * @param title
	 *            表头
	 * @param dataList
	 *            数据列表
	 * @param properties
	 *            属性名
	 * @param datePattern
	 *            日期格式
	 * @param valueFilter
	 *            属性过滤器，用于自定义属性过滤
	 */
	public static <T> void fillSheetData(Workbook workbook, Sheet sheet, String[] title, List<T> dataList,
			String[] properties, String datePattern, ValueFilter valueFilter) {
		int rowNum = 0;
		if (title != null && title.length > 0) {
			// 创建title
			Row titleRow = sheet.createRow(rowNum);
			for (int i = 0; i < title.length; i++) {
				Cell cell = titleRow.createCell(i);
				// CreationHelper createHelper = wb.getCreationHelper();
				// cell.setCellValue(createHelper.createRichTextString("This is
				// a string"));//富文本方式写入（可以设置同一个单元格不同的颜色，格式等等）
				cell.setCellValue(title[i]);
			}
		}
		for (int i = 0; i < dataList.size(); i++) {
			Row dataRow = sheet.createRow(++rowNum);
			Object data = dataList.get(i);
			for (int j = 0; j < properties.length; j++) {
				Cell cell = dataRow.createCell(j);
				String propertyName = properties[j];
				Object property = PropertyUtils.getProperty(data, propertyName);
				if (valueFilter != null) {// 自定义属性处理
					Object cellData = valueFilter.process(data, propertyName, property);
					if (cellData != null) {
						property = cellData;
					}
				}
				if (property instanceof Boolean) {
					cell.setCellValue((Boolean) property);
				} else if (property instanceof Short || property instanceof Integer || property instanceof Long
						|| property instanceof Float || property instanceof Double) {
					cell.setCellValue(Double.parseDouble(property.toString()));
				} else if (property instanceof BigDecimal) {
					cell.setCellValue(((BigDecimal) property).doubleValue());
				} else if (StringUtils.isNotEmpty(datePattern) && property instanceof Date) {
					cell.setCellValue(new SimpleDateFormat(datePattern).format((Date) property));
				} else if (StringUtils.isNotEmpty(datePattern) && property instanceof Calendar) {
					cell.setCellValue(new SimpleDateFormat(datePattern).format(((Calendar) property).getTime()));
				} else {
					cell.setCellValue(property != null ? property.toString() : "");
				}

			}
		}
	}

	/**
	 * 将excel输出到response输出流
	 * 
	 * @param workbook
	 *            工作簿
	 * @param fileName
	 *            输出文件名
	 * @param request
	 * @param response
	 */
	public static void write(Workbook workbook, String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if (userAgent.indexOf("firefox") > 0) {// firefox
			try {
				fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.error("----编码错误---", e);
				// throw new SystemException(new
				// ExceptionMessage("system.busy"));//编码失败不影响导出，但对于中文文件名称而言可能出现乱码
			}
		} else {
			try {
				fileName = URLEncoder.encode(fileName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("----编码失败---", e);
				// throw new SystemException(new
				// ExceptionMessage("system.busy"));//编码失败不影响导出，但对于中文文件名称而言可能出现乱码
			}
		}
		// else if (userAgent.indexOf("msie") > 0) {//ie
		// fileName = URLEncoder.encode(fileName, "utf-8");
		// }
		response.reset();// 防止下载下来的文件多出空行
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		// response.addHeader("Content-Length", definedGsmsTemp);
		response.setContentType("application/octet-stream");
		OutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(response.getOutputStream(), 2048);
			workbook.write(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			logger.error("----导出excel失败---", e);
			throw new SystemException(new ExceptionMessage("system.busy"));
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("----流关闭失败---", e);// 关流失败不影响导出，但对系统有影响。
				}
			}
		}
	}

	/**
	 * 创建名称
	 * 
	 * @param wb
	 *            工作簿
	 * @param name
	 *            名称名
	 * @param expression
	 *            引用位置（数据范围。例如：sheet1!$A$1:$A$5，数据即引用sheet1工作表的A1:A5）
	 * @return
	 */
	public static Name createName(Workbook wb, String name, String expression) {
		Name refer = wb.createName();
		refer.setRefersToFormula(expression);
		refer.setNameName(name);
		return refer;
	}

	/**
	 * 创建公式列表约束
	 * 
	 * @param sheet
	 *            工作表
	 * @param listFormula
	 *            列表公式
	 * @param range
	 *            约束范围（开始行，结束行，开始列，结束列）指定给哪些单元格添加公式列表约束
	 */
	public static void createFormulaListConstraint(Sheet sheet, String listFormula, int[] range) {
		CellRangeAddressList addressList = new CellRangeAddressList(range[0], range[1], range[2], range[3]);
		DataValidationHelper dvHelper = sheet.getDataValidationHelper();
		DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(listFormula);
		DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
		sheet.addValidationData(validation);
	}

	// /**
	// * 创建工作表(使用安全的工作表名)
	// * @param workbook 工作簿
	// * @param sheetNames 工作表名（不指定时只包含一个默认工作表sheet0）。
	// * @return
	// */
	// public static Sheet createSheets(Workbook workbook, String... sheetNames)
	// {
	// Sheet sheet = null;
	// int sheetNum = sheetNames.length;
	// if (sheetNum <= 0) {
	// sheet = workbook.createSheet();
	// } else {
	// for (int i = 0; i < sheetNum; i++) {
	//// workbook.create
	// }
	// }
	// return sheet;
	// }

}
