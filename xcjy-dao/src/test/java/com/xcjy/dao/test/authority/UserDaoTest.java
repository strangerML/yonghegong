package com.xcjy.dao.test.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xcjy.dao.authority.UserDao;
import com.xcjy.dao.test.config.spring.DataConfig;
import com.xcjy.entity.authority.User;

@RunWith(SpringJUnit4ClassRunner.class) // spring的测试类
@ContextConfiguration(classes = { DataConfig.class })
public class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Ignore
	@Test
	// @Transactional("defaultTm")//会导致回滚
	public void save() {
		System.out.println("测试开始……");
		userDao.save(creatUser(24));
		System.out.println("测试结束……");
	}

//	@Ignore
	@Test
	public void insert() {
		System.out.println("测试开始……");
		// for (int i = 0; i < 23; i++) {
		System.out.println(userDao.insert(creatUser(26)));
		// }
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void update() {
		System.out.println("测试开始……");
		User user = creatUser(26);
		user.setId(114L);
		userDao.update(user);
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void delete() {
		System.out.println("测试开始……");
		userDao.delete(114L);
		// userDao.deleteByField("userName", "wanshang1");
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void findAll() {
		System.out.println("测试开始……");
		List<User> list = userDao.findAll();
		printfUsers(list);
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void get() {
		System.out.println("测试开始……");
		User user = userDao.get(93);
		List<User> list = new ArrayList<User>();
		list.add(user);
		printfUsers(list);
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void findByField() {
		System.out.println("测试开始……");
		Long start = System.currentTimeMillis();
		List<User> list = userDao.findByField("id", 93);
		System.out.println(System.currentTimeMillis() - start);
		printfUsers(list);
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void query() {
//		System.out.println("测试开始……");
//		Long start = System.currentTimeMillis();
//		PageModel<User> pm = new PageModel<User>(40, 200);
//		pm = userDao.query(pm);
//		System.out.println(System.currentTimeMillis() - start);
//		printfUsers(pm.getPageData());
		System.out.println("测试结束……");
	}

	@Ignore
	@Test
	public void query2() {
		System.out.println("测试开始……");
//		Long start = System.currentTimeMillis();
//		PageModel<User> pm = new PageModel<User>(0, 20);
//		pm.getLikeMap().put("remark", "户ccut1");
//		pm.getOrderMap().put("password", SqlBuilder.SQL_ASC);
//		pm.getOrderMap().put("phone", SqlBuilder.SQL_DESC);
//		pm.getEqualMap().put("password", "pwdccut21");
//
//		String extraSQL = " AND ( CREATE_TIME <= :startDate OR CREATE_TIME >= :endDate )";
//		Map<String, Object> extraParamMap = new HashMap<String, Object>();
//		extraParamMap.put("startDate", DateUtils.strToDate("2015-12-22 13:54:51", DateUtils.defaultFormat));
//		extraParamMap.put("endDate", DateUtils.strToDate("2015-12-22 13:54:53", DateUtils.defaultFormat));
//		pm = userDao.query(pm, extraSQL, extraParamMap);
//		System.out.println(System.currentTimeMillis() - start);
//		printfUsers(pm.getPageData());
		System.out.println("测试结束……");
	}

	private void printfUsers(List<User> list) {
		if (list == null) {
			return;
		}
		System.out.println(list.size());
		for (User user : list) {
			System.out.println(user.getId() + "---" + user.getUserName() + "---" + user.getPassword() + "---"
					+ user.getPhone() + "---" + user.getCreateTime() + "---" + user.getRemark());
		}
	}

	private User creatUser(int i) {
		User user = new User();
		user.setCreateTime(new Date());
		user.setEmail("ccut" + i + "@163.com");
		user.setPassword("pwdccut" + i);
		user.setPhone("138333" + i);
		user.setRemark("测试用户ccut" + i);
		user.setUpdateTime(new Date());
		user.setUserName("ccut" + i);
		return user;
	}
}
