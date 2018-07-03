package com.xcjy.entity.test;



import org.junit.Test;

public class MyTest {

	@Test
	public void myTest() {
		String s = "zhangsan.lisi.wangwu..";
		String[] ary = s.split("\\.");
		if (ary != null && ary.length != 0) {
			System.out.println(ary.length);
			for (String ss : ary) {
				System.out.println(ss);
			}
		}
	}
}
