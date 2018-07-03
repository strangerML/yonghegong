package com.xcjy.dao.test;

import org.junit.Test;

public class Mytest {

	@Test
	public void ttt(){
		String content = "B_dkfasd";
		 String appCode = content.substring(0,
                 content.indexOf("_"));
		 System.out.println(appCode);
	}
}
