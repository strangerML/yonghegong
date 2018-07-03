package com.xcjy.dao.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.User;
import com.xcjy.entity.authority.UserRole;
import com.xcjy.infra.utils.page.PageModel;

/**
 * 用户dao
 * @author 支亚州
 *
 */
@Repository
public class UserDao extends BaseDao<User> {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public PageModel<User> query(PageModel<User> pageModel) {
		PageModel<User> pm = super.query(pageModel);
		List<User> list = pm.getPageData();
		if (list != null && !list.isEmpty()) {
			for (int i = 0, j = list.size(); i < j; i++) {
				User user = list.get(i);
				UserRole userRole = userRoleDao.getByField("userId", user.getId());
				if(userRole!=null){
					Role role = roleDao.get(userRole.getRoleId());
					user.setRoleName(role.getName());
				}
			}
		}

		return pm;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

}

