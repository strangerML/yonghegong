package com.xcjy.infra.utils.datetime;


/**
 * 用来计算程序运行时间的计时器
 * 
 * 用法：
 * TimerCal cal=new TimerCal(true);
 * ……执行代码
 * cal.printTime("测试代码", this.getClass());
 * 			
 */

import org.slf4j.LoggerFactory;

/**
 * 用来计算程序运行时间的计时器
 * 
 */
public class TimerCal {

	private long begintime = 0;

	/**
	 * 构造计时器
	 * 
	 * @param isbegin
	 *            是否开始计时
	 */
	public TimerCal(boolean isbegin) {
		if (isbegin) {
			begin();
		}
	}

	/**
	 * 开始计时
	 */
	public void begin() {
		begintime = System.currentTimeMillis();
	}

	/**
	 * 打印以消耗时间
	 * 
	 * @param info
	 * @param goal
	 */
	public void printTime(String info, Class<?> goal) {
		long current = System.currentTimeMillis();

		long millCost = current - begintime;

		long cost = millCost / 1000;

		if (cost < 0) {
			cost = 0;
		}
		LoggerFactory.getLogger(goal).debug("--***--timeCal cost " + cost + "(" + millCost + ")--**-- des:" + info);
	}
}
