package com.xcjy.dao.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.base.SqlBuilder;
import com.xcjy.entity.authority.Module;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.utils.tree.ModuleTreeUtil;

/**
 * 资源菜单 dao层
 * 
 * @author 支亚州
 *
 */
@Repository
public class ModuleDao extends BaseDao<Module> {
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 * 获取树形结构的所有资源菜单
	 * 
	 * @return
	 */
	public List<Module> getMenus() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("parentId", SqlBuilder.SQL_ASC);
		orderMap.put("order", SqlBuilder.SQL_ASC);
		orderMap.put("isLeaf", SqlBuilder.SQL_ASC);
		orderMap.put("id", SqlBuilder.SQL_ASC);
		List<Module> menus = findByFieldsAndOrder(null, orderMap);
		menus = ModuleTreeUtil.generateTree(menus);
		return menus;
	}

	/**
	 * 根据用户账户，获取当前用户权限内的树形结构的所有资源菜单
	 * 
	 * @param userName
	 * @return
	 */
	public List<Module> getMenusByUser(String userName) {

		List<Long> list = getModulesByUser(userName);
		if (list == null || list.isEmpty()) {
			return new ArrayList<Module>();
		}

		List<Module> allMenus = getMenus();
		buildUserMenus(allMenus, list);

		return allMenus;
	}

	/**
	 * 筛选用户权限内的菜单
	 * 
	 * @param menus
	 * @param userModules
	 */
	private void buildUserMenus(List<Module> menus, List<Long> userModules) {
		if (menus == null || menus.isEmpty() || userModules == null || userModules.isEmpty()) {
			menus = new ArrayList<Module>();
			return;
		}

		Iterator<Module> mit = menus.iterator();
		while (mit.hasNext()) {
			Module module = mit.next();
			if (userModules.isEmpty()) {
				mit.remove();
				continue;
			}
			if (module.getIsLeaf() == 1) {// 叶子节点
				if (userModules.contains(module.getId())) {
					userModules.remove(module.getId());
				} else {
					mit.remove();
				}
				continue;
			}
			List<Module> children = module.getChildren();
			buildUserMenus(children, userModules);
			if (children.isEmpty()) {
				mit.remove();
			}
		}
	}

	/**
	 * 获取用户权限内的资源菜单ID
	 * 
	 * @param userName
	 * @return
	 */
	private List<Long> getModulesByUser(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append(SqlBuilder.SQL_SELECT).append(SqlBuilder.SQL_DISTINCT).append("m.ID").append(SqlBuilder.SQL_FROM)
				.append("t_authority_user u,t_authority_user_role ur,t_authority_role r")
				.append(",t_authority_role_permission rp,t_authority_permission p,t_authority_module m")
				.append(SqlBuilder.SQL_WHERE)
				.append("u.ID=ur.USER_ID AND ur.ROLE_ID = r.ID AND r.ID=rp.ROLE_ID AND rp.PERMISSION_ID = p.ID AND p.MODULE_ID = m.ID")
				.append(SqlBuilder.SQL_AND).append("u.USER_NAME = :userName");
		logger.info("SQL : " + "\"" + sql.toString() + "\"");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);

		return namedTemplate.queryForList(sql.toString(), paramMap, Long.class);
	}

	public List<Long> findChildrenIdsByModuleId(Long moduleId) {
		if(moduleId == null){
			return null;
		}
		List<Long> ids = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ID from t_authority_module where FIND_IN_SET(id, queryChildrenAreaInfo(?)) ");
		try {
			List<Map<String, Object>> resultList = template.queryForList(sql.toString(), moduleId);
			if (resultList != null && !resultList.isEmpty()) {
				ids = new ArrayList<Long>();
				for (Map<String, Object> resultMap : resultList) {
					Long id = (Long) resultMap.get("ID");
					ids.add(id);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public List<Role> findRolesByModuleIds(List<Long> ids) {
		namedJdbcTemplate = new NamedParameterJdbcTemplate(template);
		if (ids == null) {
			return null;
		}
		List<Role> roles = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT(r.ID) from t_authority_permission p,t_authority_role_permission rp,t_authority_role r WHERE rp.PERMISSION_ID=p.ID AND rp.ROLE_ID=r.ID AND p.MODULE_ID in (:ids) ");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		try {
			List<Map<String, Object>> resultList = namedJdbcTemplate.queryForList(sql.toString(), parameters);
			if (resultList != null && !resultList.isEmpty()) {
				roles = new ArrayList<Role>();
				for (Map<String, Object> resultMap : resultList) {
					Role role = new Role();
					role.setId((Long) resultMap.get("ID"));
					role.setName((String) resultMap.get("NAME"));
					role.setCreateTime((Date) resultMap.get("CREATE_TIME"));
					role.setUpdateTime((Date)resultMap.get("UPDATE_TIME"));
					role.setRemark((String)resultMap.get("REMARK"));
					roles.add(role);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return roles;
	}

}
