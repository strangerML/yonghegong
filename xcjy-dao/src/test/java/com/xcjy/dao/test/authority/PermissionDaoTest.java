package com.xcjy.dao.test.authority;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xcjy.dao.authority.PermissionDao;
import com.xcjy.dao.test.config.spring.DataConfig;
import com.xcjy.entity.authority.Permission;

@RunWith(SpringJUnit4ClassRunner.class) // spring的测试类
@ContextConfiguration(classes = { DataConfig.class })
public class PermissionDaoTest {

	@Autowired
	private PermissionDao permissionDao;

	// @Ignore
	@Test
	public void findRolesByUserNameTest() {
		System.out.println("测试开始……");
		List<Permission> permissions = permissionDao.findPermissionsByUserName("admin");
		System.out.println(JSON.toJSONString(permissions));
		System.out.println("测试结束……");
	}
}
