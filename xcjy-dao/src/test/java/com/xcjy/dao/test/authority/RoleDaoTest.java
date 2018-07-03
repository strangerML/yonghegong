package com.xcjy.dao.test.authority;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xcjy.dao.authority.RoleDao;
import com.xcjy.dao.test.config.spring.DataConfig;
import com.xcjy.entity.authority.Role;

@RunWith(SpringJUnit4ClassRunner.class) // spring的测试类
@ContextConfiguration(classes = { DataConfig.class })
public class RoleDaoTest {

	@Autowired
	private RoleDao roleDao;

	// @Ignore
	@Test
	public void findRolesByUserNameTest() {
		System.out.println("测试开始……");
		List<Role> roles = roleDao.findRolesByUserName("admin");
		System.out.println(JSON.toJSONString(roles));
		System.out.println("测试结束……");
	}

}
