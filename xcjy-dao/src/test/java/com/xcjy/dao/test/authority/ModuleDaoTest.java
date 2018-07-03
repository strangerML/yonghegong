package com.xcjy.dao.test.authority;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xcjy.dao.authority.ModuleDao;
import com.xcjy.dao.test.config.spring.DataConfig;
import com.xcjy.entity.authority.Module;

@RunWith(SpringJUnit4ClassRunner.class) // spring的测试类
@ContextConfiguration(classes = { DataConfig.class })
public class ModuleDaoTest {

	@Autowired
	private ModuleDao moduleDao;

	@Ignore
	@Test
	public void getMenusTest() {
		System.out.println("测试开始……");
		List<Module> list = moduleDao.getMenus();
		// System.out.println(JSON.toJSONString(list,
		// SerializerFeature.DisableCircularReferenceDetect));
		System.out.println(JSON.toJSONString(list));
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void getMenusByUserTest() {
		System.out.println("测试开始……");
		List<Module> list = moduleDao.getMenusByUser("admin");
		System.out.println(JSON.toJSONString(list));
		System.out.println("测试结束……");
	}
}
