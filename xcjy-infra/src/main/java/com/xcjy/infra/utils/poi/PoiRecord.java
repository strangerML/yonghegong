package com.xcjy.infra.utils.poi;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 封装Excel行数据
 * 
 * @author Nick Zhang
 *
 * @createdOn:2013年11月16日 19:55:04
 */
public class PoiRecord {

	private String[] columnValues;

	public PoiRecord(String[] columnValues) {
		this.columnValues = columnValues;
	}

	public String getStringValue(int column) {
		if (column >= columnValues.length) {
			return null;
		}
		return columnValues[column];
	}

	public Date getDateValue(int column) {
		return getDateValue(column, PoiUtils.DATE_PATTERN);
	}

	public Date getDateValue(int column, String pattern) {
		String v = replaceBlank(getStringValue(column));
		Date result = null;
		if (StringUtils.isNotEmpty(v)) {
			try {
				result = DateUtils.parseDate(v, pattern);
			} catch (ParseException e) {
				throw new RuntimeException("日期转换异常！", e);
			}
		}
		return result;
	}

	public Integer getIntegerValue(int column) {
		String v = replaceBlank(getStringValue(column));
		Integer result = null;
		if (StringUtils.isNotEmpty(v)) {
			result = new Integer(v);
		}
		return result;
	}

	public Long getLongValue(int column) {
		String v = replaceBlank(getStringValue(column));
		Long result = null;
		if (StringUtils.isNotEmpty(v)) {
			result = new Long(v);
		}
		return result;
	}

	public Float getFloatValue(int column) {
		String v = replaceBlank(getStringValue(column));
		Float result = null;
		if (StringUtils.isNotEmpty(v)) {
			result = new Float(v);
		}
		return result;
	}

	public Double getDoubleValue(int column) {
		String v = replaceBlank(getStringValue(column));
		Double result = null;
		if (StringUtils.isNotEmpty(v)) {
			result = new Double(v);
		}
		return result;
	}

	public BigDecimal getBigDecimalValue(int column) {
		String v = replaceBlank(getStringValue(column));
		BigDecimal result = null;
		if (StringUtils.isNotEmpty(v)) {
			result = new BigDecimal(v);
		}
		return result;
	}

	/**
	 * 得到列数
	 * 
	 * @return
	 */
	public int getColumnSize() {
		return columnValues.length;
	}

	/**
	 * 替换字符串
	 * 
	 * @param str
	 * @return
	 */
	private String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

}
