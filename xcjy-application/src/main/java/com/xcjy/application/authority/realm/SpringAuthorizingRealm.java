package com.xcjy.application.authority.realm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.dao.authority.PermissionDao;
import com.xcjy.dao.authority.RoleDao;
import com.xcjy.dao.authority.UserDao;
import com.xcjy.entity.authority.Permission;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.User;
import com.xcjy.infra.utils.Constants;

/**
 * 自定义shiro realm
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class SpringAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PermissionDao permissionDao;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		String password = new String(upToken.getPassword());
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			throw new AccountException("用户名或密码为空！");
		}
		boolean isExist = checkUser(userName, password);
		if (!isExist) {
			throw new AccountException("用户名或密码错误！");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, password, getName());

		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();// 获取当前用户的用户名
		List<Role> roles = roleDao.findRolesByUserName(userName);// 获取角色
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 设定角色
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				info.addRole(role.getName());
			}
		}
		// 设定权限
		List<Permission> permissions = permissionDao.findPermissionsByUserName(userName);
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				info.addStringPermission(permission.getPermissionSN());
			}
		}
		return info;
	}

	/**
	 * ShiroSession设置
	 * 
	 * @see 使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	/**
	 * 查看用户是否存在
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	private boolean checkUser(String userName, String password) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userName", userName);
		userMap.put("password", password);
		User user = userDao.getByFields(userMap);
		if (user == null) {
			return false;
		}
		user.setPassword(null);
		this.setSession(Constants.CURRENT_USER, user);
		return true;
	}

}
