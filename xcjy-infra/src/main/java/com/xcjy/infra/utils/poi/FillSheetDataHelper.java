package com.xcjy.infra.utils.poi;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 用于下载时辅助填充Excel的实体类 <对小飞原有方法的改进支持> <原因: 多参应对其参数进行封装>
 * 
 * @author LCC
 *
 */
@SuppressWarnings("rawtypes")
public class FillSheetDataHelper {
	private List datas; // 数据集合
	private Sheet sheet; // 数据所填充的表单
	private Workbook workbook; // 工作簿

	private boolean hasTitle = true; // 是否把有标题行
	private List<FillSheetData> configs = new ArrayList<FillSheetData>();

	/**
	 * 是否有序号列
	 */
	private boolean hasSN = true;

	/**
	 * 序号列 标题
	 */
	private String snTitle = "序号";

	public FillSheetDataHelper() {
	}

	public FillSheetDataHelper(Workbook workbook, Sheet sheet) {
		this.workbook = workbook;
		this.sheet = sheet;
	}

	public FillSheetDataHelper(Workbook workbook, Sheet sheet, List datas) {
		this.workbook = workbook;
		this.sheet = sheet;
		this.datas = datas;
	}

	/**
	 * 数据配置的添加
	 *
	 * LCC
	 * 
	 * @param title
	 * @param attribute
	 */
	public void insertData(String title, String attribute) {
		configs.add(new FillSheetData(title, attribute));
	}

	public void insertData(String title, String attribute, String pattern) {
		configs.add(new FillSheetData(title, attribute, pattern, null));
	}

	public void insertData(String title, String attribute, ValueFilter valueFilter) {
		configs.add(new FillSheetData(title, attribute, null, valueFilter));
	}

	public void insertData(String title, String attribute, String pattern, ValueFilter valueFilter) {
		configs.add(new FillSheetData(title, attribute, pattern, valueFilter));
	}

	// 配置
	class FillSheetData {
		private String title; // 标题
		private String attribute; // 数据对象的属性名
		private String pattern; // 日期格式化的格式
		private ValueFilter valueFilter;// 过滤器

		public FillSheetData(String title, String attribute) {
			this.title = title;
			this.attribute = attribute;
		}

		public FillSheetData(String title, String attribute, String pattern, ValueFilter valueFilter) {
			this.title = title;
			this.attribute = attribute;
			this.pattern = pattern;
			this.valueFilter = valueFilter;
		}

		/// Getter And Setter ///
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAttribute() {
			return attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public ValueFilter getValueFilter() {
			return valueFilter;
		}

		public void setValueFilter(ValueFilter valueFilter) {
			this.valueFilter = valueFilter;
		}
	}

	/// Getter And Setter ///
	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public List<FillSheetData> getConfigs() {
		return configs;
	}

	public void setConfigs(List<FillSheetData> configs) {
		this.configs = configs;
	}

	public boolean isHasTitle() {
		return hasTitle;
	}

	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}

	public boolean isHasSN() {
		return hasSN;
	}

	public void setHasSN(boolean hasSN) {
		this.hasSN = hasSN;
	}

	public String getSnTitle() {
		return snTitle;
	}

	public void setSnTitle(String snTitle) {
		this.snTitle = snTitle;
	}
}
