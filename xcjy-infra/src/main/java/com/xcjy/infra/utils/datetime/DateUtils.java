package com.xcjy.infra.utils.datetime;


/**
 * Copyright (C) 2012 恒拓开源信息科技有限公司
 *
 * @className:org.foss.airchina.energym.util.DateUtils1
 * @description:
 * 
 * @version:v1.0.0 
 * @author:liuliang
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2012-10-9     liuliang       v1.0.0        create
 *
 *
 */

/**
 * @Description:
 * @author:liuliang
 * @version:v1.0.0
 * @Created:2012-10-9下午6:54:44
 * @Modified:
 */

/**

 * @(#)DateUtil.java

 *

 *

 * @author kidd

 * @version 1.00 2007/8/8

 */

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 
	 * 时间范围：年
	 */

	public static final int YEAR = 1;

	/**
	 * 
	 * 时间范围：季度
	 */

	public static final int QUARTER = 2;

	/**
	 * 
	 * 时间范围：月
	 */

	public static final int MONTH = 3;

	/**
	 * 
	 * 时间范围：旬
	 */

	public static final int TENDAYS = 4;

	/**
	 * 
	 * 时间范围：周
	 */

	public static final int WEEK = 5;

	/**
	 * 
	 * 时间范围：日
	 */

	public static final int DAY = 6;

	public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";

	public static final String simpleFormat = "yyyy-MM-dd";

	/* 基准时间 */

	private Date fiducialDate = null;

	private Calendar cal = null;

	private DateUtils(Date fiducialDate) {

		if (fiducialDate != null) {

			this.fiducialDate = fiducialDate;

		} else {

			this.fiducialDate = new Date(System.currentTimeMillis());

		}

		this.cal = Calendar.getInstance();

		this.cal.setTime(this.fiducialDate);

		this.cal.set(Calendar.HOUR_OF_DAY, 0);

		this.cal.set(Calendar.MINUTE, 0);

		this.cal.set(Calendar.SECOND, 0);

		this.cal.set(Calendar.MILLISECOND, 0);

		this.fiducialDate = this.cal.getTime();

	}

	/**
	 * 
	 * 获取DateHelper实例
	 * 
	 * 
	 * 
	 * @param fiducialDate
	 * 
	 *            基准时间
	 * 
	 * @return Date
	 */

	public static DateUtils getInstance(Date fiducialDate) {

		return new DateUtils(fiducialDate);

	}

	/**
	 * 
	 * 获取DateHelper实例, 使用当前时间作为基准时间
	 * 
	 * 
	 * 
	 * @return Date
	 */

	public static DateUtils getInstance() {

		return new DateUtils(null);

	}

	/**
	 * 
	 * 获取年的第一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getFirstDayOfYear(int offset) {

		cal.setTime(this.fiducialDate);

		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);

		cal.set(Calendar.MONTH, Calendar.JANUARY);

		cal.set(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取年的最后一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getLastDayOfYear(int offset) {

		cal.setTime(this.fiducialDate);

		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);

		cal.set(Calendar.MONTH, Calendar.DECEMBER);

		cal.set(Calendar.DAY_OF_MONTH, 31);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取季度的第一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getFirstDayOfQuarter(int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(Calendar.MONTH, offset * 3);

		int mon = cal.get(Calendar.MONTH);

		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {

			cal.set(Calendar.MONTH, Calendar.JANUARY);

			cal.set(Calendar.DAY_OF_MONTH, 1);

		}

		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {

			cal.set(Calendar.MONTH, Calendar.APRIL);

			cal.set(Calendar.DAY_OF_MONTH, 1);

		}

		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {

			cal.set(Calendar.MONTH, Calendar.JULY);

			cal.set(Calendar.DAY_OF_MONTH, 1);

		}

		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {

			cal.set(Calendar.MONTH, Calendar.OCTOBER);

			cal.set(Calendar.DAY_OF_MONTH, 1);

		}

		return cal.getTime();

	}

	public Date getYesterday() {

		long time = this.fiducialDate.getTime() - 60 * 60 * 24 * 1000;

		return new Date(time);

	}

	public Date getTomorrow() {

		long time = this.fiducialDate.getTime() + 60 * 60 * 24 * 1000;

		return new Date(time);

	}

	/**
	 * 
	 * 获取季度的最后一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getLastDayOfQuarter(int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(Calendar.MONTH, offset * 3);

		int mon = cal.get(Calendar.MONTH);

		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {

			cal.set(Calendar.MONTH, Calendar.MARCH);

			cal.set(Calendar.DAY_OF_MONTH, 31);

		}

		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {

			cal.set(Calendar.MONTH, Calendar.JUNE);

			cal.set(Calendar.DAY_OF_MONTH, 30);

		}

		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {

			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);

			cal.set(Calendar.DAY_OF_MONTH, 30);

		}

		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {

			cal.set(Calendar.MONTH, Calendar.DECEMBER);

			cal.set(Calendar.DAY_OF_MONTH, 31);

		}

		return cal.getTime();

	}

	/**
	 * 
	 * 获取月的最后一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getFirstDayOfMonth(int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(Calendar.MONTH, offset);

		cal.set(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取月的最后一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getLastDayOfMonth(int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(Calendar.MONTH, offset + 1);

		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取旬的第一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getFirstDayOfTendays(int offset) {

		cal.setTime(this.fiducialDate);

		int day = cal.get(Calendar.DAY_OF_MONTH);

		if (day >= 21) {

			day = 21;

		} else if (day >= 11) {

			day = 11;

		} else {

			day = 1;

		}

		if (offset > 0) {

			day = day + 10 * offset;

			int monOffset = day / 30;

			day = day % 30;

			cal.add(Calendar.MONTH, monOffset);

			cal.set(Calendar.DAY_OF_MONTH, day);

		} else {

			int monOffset = 10 * offset / 30;

			int dayOffset = 10 * offset % 30;

			if ((day + dayOffset) > 0) {

				day = day + dayOffset;

			} else {

				monOffset = monOffset - 1;

				day = day - dayOffset - 10;

			}

			cal.add(Calendar.MONTH, monOffset);

			cal.set(Calendar.DAY_OF_MONTH, day);

		}

		return cal.getTime();

	}

	/**
	 * 
	 * 获取旬的最后一天
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getLastDayOfTendays(int offset) {

		Date date = getFirstDayOfTendays(offset + 1);

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取周的第一天(MONDAY)
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getFirstDayOfWeek(int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(Calendar.DAY_OF_MONTH, offset * 7);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取周的最后一天(SUNDAY)
	 * 
	 * 
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getLastDayOfWeek(int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(Calendar.DAY_OF_MONTH, offset * 7);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		cal.add(Calendar.DAY_OF_MONTH, 6);

		return cal.getTime();

	}

	/**
	 * 
	 * 获取指定时间范围的第一天
	 * 
	 * 
	 * 
	 * @param dateRangeType
	 * 
	 *            时间范围类型
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getFirstDate(int dateRangeType, int offset) {

		return null;

	}

	/**
	 * 
	 * 获取指定时间范围的最后一天
	 * 
	 * 
	 * 
	 * @param dateRangeType
	 * 
	 *            时间范围类型
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date getLastDate(int dateRangeType, int offset) {

		return null;

	}

	/**
	 * 
	 * 根据日历的规则，为基准时间添加指定日历字段的时间量
	 * 
	 * 
	 * 
	 * @param field
	 * 
	 *            日历字段, 使用Calendar类定义的日历字段常量
	 * 
	 * @param offset
	 * 
	 *            偏移量
	 * 
	 * @return Date
	 */

	public Date add(int field, int offset) {

		cal.setTime(this.fiducialDate);

		cal.add(field, offset);

		return cal.getTime();

	}

	/**
	 * 
	 * 根据日历的规则，为基准时间添加指定日历字段的单个时间单元
	 * 
	 * 
	 * 
	 * @param field
	 * 
	 *            日历字段, 使用Calendar类定义的日历字段常量
	 * 
	 * @param up
	 * 
	 *            指定日历字段的值的滚动方向。true:向上滚动 / false:向下滚动
	 * 
	 * @return Date
	 */

	public Date roll(int field, boolean up) {

		cal.setTime(this.fiducialDate);

		cal.roll(field, up);

		return cal.getTime();

	}

	/**
	 * 
	 * 把字符串转换为日期
	 * 
	 * 
	 * 
	 * @param dateStr
	 * 
	 *            日期字符串
	 * 
	 * @param format
	 * 
	 *            日期格式
	 * 
	 * @return Date
	 */

	public static Date strToDate(String dateStr, String format) {

		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {

			DateFormat df = new SimpleDateFormat(format);

			try {

				date = df.parse(dateStr);

			} catch (ParseException e) {

				e.printStackTrace();

			}

		}

		return date;

	}

	/**
	 * 
	 * 把字符串转换为日期，日期的格式为yyyy-MM-dd HH:ss
	 * 
	 * 
	 * 
	 * @param dateStr
	 * 
	 *            日期字符串
	 * 
	 * @return Date
	 */

	public static Date strToDate(String dateStr) {

		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {

			if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {

				dateStr = dateStr + " 00:00";

			} else if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}")) {

				dateStr = dateStr + ":00";

			} else {

				System.out.println(dateStr + " 格式不正确");

				return null;

			}

			DateFormat df = new SimpleDateFormat(defaultFormat);

			try {

				date = df.parse(dateStr);

			} catch (ParseException e) {

				e.printStackTrace();

			}

		}

		return date;

	}

	/**
	 * 
	 * 把日期转换为字符串
	 * 
	 * 
	 * 
	 * @param date
	 * 
	 *            日期实例
	 * 
	 * @param format
	 * 
	 *            日期格式
	 * 
	 * @return Date
	 */

	public static String dateToStr(Date date, String format) {

		return (date == null) ? "" : new SimpleDateFormat(format).format(date);

	}

	public static String dateToStr(Date date) {

		return (date == null) ? ""

				: new SimpleDateFormat(defaultFormat).format(date);

	}

	/**
	 * 
	 * 取得当前日期 年-月-日
	 * 
	 * 
	 * 
	 * @return Date
	 */

	public static String getCurrentDate() {

		DateFormat f = new SimpleDateFormat(simpleFormat);

		return f.format(Calendar.getInstance().getTime());

	}

	/**
	 * 
	 * 取当前日期的字符串形式,"XXXX年XX月XX日"
	 * 
	 * 
	 * 
	 * @return java.lang.String
	 */

	public static String getPrintDate() {

		String printDate = "";

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		printDate += calendar.get(Calendar.YEAR) + "年";

		printDate += (calendar.get(Calendar.MONTH) + 1) + "月";

		printDate += calendar.get(Calendar.DATE) + "日";

		return printDate;

	}

	/**
	 * 
	 * 将指定的日期字符串转化为日期对象
	 * 
	 * 
	 * 
	 * @param dateStr
	 * 
	 *            日期字符串
	 * 
	 * @return java.util.Date
	 */

	public static Date getDate(String dateStr, String format) {

		if (dateStr == null) {

			return new Date();

		}

		if (format == null) {

			format = simpleFormat;

		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {

			Date date = sdf.parse(dateStr);

			return date;

		} catch (Exception e) {

			return null;

		}

	}

	/**
	 * 
	 * 从指定Timestamp中得到相应的日期的字符串形式 日期"XXXX-XX-XX"
	 * 
	 * 
	 * 
	 * @param dateTime
	 * 
	 * @return 、String
	 */

	public static String getDateFromDateTime(Timestamp dateTime) {

		SimpleDateFormat sdf = new SimpleDateFormat(simpleFormat);

		return sdf.format(dateTime).toString();

	}

	/**
	 * 
	 * 得到当前时间 return java.sql.Timestamp
	 * 
	 * 
	 * 
	 * @return Timestamp
	 */

	public static Timestamp getNowTimestamp() {

		long curTime = System.currentTimeMillis();

		return new Timestamp(curTime);

	}

	/**
	 * 
	 * <p>
	 * (适用条件[可选])
	 * <p>
	 * (执行流程[可选])
	 * <p>
	 * (使用方法[可选])
	 * <p>
	 * (注意事项[可选])
	 * 
	 * @Description:获取当前时间的上一个月份
	 * @param date
	 *            当前时间
	 * @param d
	 *            设置上个月的几号
	 * @return Date
	 * @Created:lining 2012-11-19下午12:51:18
	 * @Modified:
	 */
	public static Date getBeforeMonth(Date date, int d) {
		Calendar c = Calendar.getInstance();// 今天的时间
		c.setTime(date);
		c.add(Calendar.MONTH, -1); // 今天的时间月份-1支持1月的上月
		c.set(Calendar.DAY_OF_MONTH, d);// 设置上月1号
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		java.util.Date fillDate = c.getTime();
		return fillDate;
	}

	/**
	 * 
	 * <p>
	 * (适用条件[可选])
	 * <p>
	 * (执行流程[可选])
	 * <p>
	 * (使用方法[可选])
	 * <p>
	 * (注意事项[可选])
	 * 
	 * @Description:取上一个月的日期
	 * @param date
	 *            日期
	 * @return Date 上一个月的日期
	 * @Created:wangang 2012-11-22上午10:20:15
	 * @Modified:
	 */
	public static Date getBeforeMonth(Date date) {
		Calendar c = Calendar.getInstance();// 今天的时间
		c.setTime(date);
		c.add(Calendar.MONTH, -1); // 今天的时间月份-1支持1月的上月
		java.util.Date fillDate = c.getTime();
		return fillDate;
	}

	/**
	 * 
	 * <p>
	 * (适用条件[可选])
	 * <p>
	 * (执行流程[可选])
	 * <li>获取指定日期之前的日期
	 * <p>
	 * (使用方法[可选])
	 * <p>
	 * (注意事项[可选])
	 *
	 * @Description:
	 * @return Date 指定日期
	 * @param year
	 *            指定日期之前的年数
	 * @param month
	 *            指定日期之前的月数
	 * @param day
	 *            指定日期之前的天数
	 * @Created:wangang 2012-11-22下午4:42:24
	 * @Modified:
	 */
	public static Date getBefore(Date date, int year, int month, int day) {
		Calendar c = Calendar.getInstance();// 今天的时间
		c.setTime(date);
		c.add(Calendar.YEAR, -year);
		c.add(Calendar.MONTH, -month);
		c.add(Calendar.DAY_OF_MONTH, -day);
		Date result = c.getTime();
		return result;
	}

	/**
	 * 
	 * <p>
	 * (适用条件[可选])
	 * <p>
	 * (执行流程[可选])
	 * <p>
	 * (使用方法[可选])
	 * <p>
	 * (注意事项[可选])
	 *
	 * @Description:得到上一个月的最后一天
	 * @param date
	 * @return Date
	 * @Created:lining 2012-12-11上午11:30:38
	 * @Modified:
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 
	 * <p>
	 * (适用条件[可选])
	 * <p>
	 * (执行流程[可选])
	 * <p>
	 * (使用方法[可选])
	 * <p>
	 * (注意事项[可选])
	 *
	 * @Description:得到上一个月的最后一天
	 * @param date
	 * @return Date
	 * @Created:lining 2012-12-11上午11:30:38
	 * @Modified:
	 */
	public static String lastDayOfMonth(Date date, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		Date date2 = cal.getTime();

		String string = new SimpleDateFormat(format).format(date2);
		return string;
	}

	/**
	 * 
	 * <p>
	 * (适用条件[可选])
	 * <li>根据日期：XXXX-XX 返回这个月有多少天
	 * <p>
	 * (执行流程[可选])
	 * <p>
	 * (使用方法[可选])
	 * <p>
	 * (注意事项[可选])
	 *
	 * @Description:
	 * @param year
	 * @param month
	 * @return int
	 * @Created:wangang 2012-12-9下午7:36:36
	 * @Modified:
	 */
	public static int getDay(int year, int month) {
		int day = 0;
		switch (month) {
		case 1:
			day = 31;
			break;
		case 3:
			day = 31;
			break;
		case 5:
			day = 31;
			break;
		case 7:
			day = 31;
			break;
		case 8:
			day = 31;
			break;
		case 10:
			day = 31;
			break;
		case 12:
			day = 31;
			break;
		case 4:
			day = 30;
			break;
		case 6:
			day = 30;
			break;
		case 9:
			day = 30;
			break;
		case 11:
			day = 30;
			break;
		default:
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				day = 29;
			else
				day = 28;
			break;
		}
		return day;
	}

}