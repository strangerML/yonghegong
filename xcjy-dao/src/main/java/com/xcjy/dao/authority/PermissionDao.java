package com.xcjy.dao.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.base.SqlBuilder;
import com.xcjy.entity.authority.Permission;
import com.xcjy.infra.utils.Constants;

/**
 * 权限dao
 * 
 * @author 支亚州
 *
 */
@Repository
public class PermissionDao extends BaseDao<Permission> {

	/**
	 * 根据账户名，查询所有权限代码
	 * 
	 * @param userName
	 * @return
	 */
	public List<Permission> findPermissionsByUserName(String userName) {

		if (StringUtils.isEmpty(userName)) {
			return null;
		}

		StringBuilder sql = new StringBuilder();
		sql.append(SqlBuilder.SQL_SELECT).append(SqlBuilder.SQL_DISTINCT)
				.append("p.ID AS tpid,m.ID AS tmid,m.SN AS msn,o.ID AS toid,o.SN AS osn").append(SqlBuilder.SQL_FROM)
				.append("t_authority_user u,t_authority_user_role ur,t_authority_role r,t_authority_role_permission rp")
				.append(",t_authority_permission p,t_authority_module m,t_authority_operation o")
				.append(SqlBuilder.SQL_WHERE)
				.append("u.ID=ur.USER_ID AND ur.ROLE_ID = r.ID AND r.ID=rp.ROLE_ID AND rp.PERMISSION_ID = p.ID AND p.MODULE_ID = m.ID AND p.OPERATION_ID = o.ID")
				.append(SqlBuilder.SQL_AND).append("u.USER_NAME = :userName");
		logger.info("SQL : " + "\"" + sql.toString() + "\"");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);

		List<Map<String, Object>> resultList = namedTemplate.queryForList(sql.toString(), paramMap);
		if (resultList == null || resultList.isEmpty()) {
			return null;
		}
		List<Permission> permissions = new ArrayList<Permission>();
		for (Map<String, Object> resultMap : resultList) {
			if (resultMap == null || resultMap.isEmpty()) {
				continue;
			}
			Permission permission = new Permission();
			permission.setId((Long) resultMap.get("tpid"));
			permission.setModuleId((Long) resultMap.get("tmid"));
			permission.setOperationId((Long) resultMap.get("toid"));
			permission.setPermissionSN(resultMap.get("msn") + Constants.COLON + resultMap.get("osn"));
			permissions.add(permission);
		}

		return permissions;
	}

}
