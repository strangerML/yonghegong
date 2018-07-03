package com.xcjy.dao.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.base.EntityBuiler;
import com.xcjy.dao.base.SqlBuilder;
import com.xcjy.entity.authority.Role;
import com.xcjy.entity.authority.User;

/**
 * 角色dao
 * 
 * @author 支亚州
 *
 */
@Repository
public class RoleDao extends BaseDao<Role> {

	/**
	 * 通过用户查询所有的角色
	 * 
	 * @param userName
	 * @return
	 */
	public List<Role> findRolesByUserName(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return null;
		}

		StringBuilder sql = new StringBuilder();
		sql.append(SqlBuilder.SQL_SELECT).append("r.*").append(SqlBuilder.SQL_FROM)
				.append("t_authority_user u,t_authority_user_role ur,t_authority_role r").append(SqlBuilder.SQL_WHERE)
				.append("u.ID=ur.USER_ID AND ur.ROLE_ID = r.ID").append(SqlBuilder.SQL_AND)
				.append("u.USER_NAME = :userName");
		logger.info("SQL : " + "\"" + sql.toString() + "\"");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", userName);

		List<Map<String, Object>> resultList = namedTemplate.queryForList(sql.toString(), paramMap);

		return EntityBuiler.buildEntity(resultList, tableMapper.getColumnMapper(), Role.class);
	}
	
	public void permissionSave(Role role) {
        StringBuilder delSql = new StringBuilder();
        delSql.append(" DELETE  FROM `t_authority_role_permission`  WHERE `ROLE_ID` = ? ");
        template.update(delSql.toString(), role.getId());
        if (role.getPermissionIds() != null && role.getPermissionIds().length != 0) {
            StringBuilder insertSql = new StringBuilder();
            insertSql.append(" INSERT INTO `t_authority_role_permission` (`ROLE_ID`,`PERMISSION_ID`) VALUES ");
            List<Object> params = new ArrayList<Object>();
            for (Long permissionId : role.getPermissionIds()) {
                insertSql.append("(?,?),");
                params.add(role.getId());
                params.add(permissionId);
            }
            String insertSqlStr = insertSql.substring(0, insertSql.length() - 1);
            template.update(insertSqlStr, params.toArray());
        }
    }

    public List<Long> findAllRolePermissions(Role role) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT PERMISSION_ID  FROM `t_authority_role_permission`  WHERE `ROLE_ID` = ? ");
        try {
            List<Map<String, Object>> resultList = template.queryForList(sql.toString(), role.getId());
            if (resultList != null && !resultList.isEmpty()) {
                List<Long> list = new ArrayList<Long>();
                for (Map<String, Object> resultMap : resultList) {
                    list.add((Long) resultMap.get("PERMISSION_ID"));
                }
                return list;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAllUserByRoleId(Long roleId) {
        if (roleId == null) {
            return null;
        }
        List<User> users = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.* from t_authority_user u,t_authority_user_role ur,t_authority_role r WHERE u.ID=ur.USER_ID AND ur.ROLE_ID=r.ID AND r.ID=? ");
        try {
            List<Map<String, Object>> resultList = template.queryForList(sql.toString(), roleId);
            if (resultList != null && !resultList.isEmpty()) {
                users = new ArrayList<User>();
                for (Map<String, Object> resultMap : resultList) {
                    User user = new User();
                    user.setId((Long) resultMap.get("ID"));
                    user.setUserName((String) resultMap.get("USER_NAME"));
                    user.setPassword((String) resultMap.get("PASSWORD"));
                    user.setPhone((String) resultMap.get("PHONE"));
                    user.setEmail((String) resultMap.get("EMAIL"));
                    user.setRemark((String) resultMap.get("REMARK"));
                    user.setCreateTime((Date) resultMap.get("CREATE_TIME"));
                    user.setUpdateTime((Date) resultMap.get("UPDATE_TIME"));
                    users.add(user);
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void deleteById(Long roleId) {
        String sql="DELETE FROM `t_authority_role`  WHERE `ID` = ?";
        template.update(sql, roleId);
        sql = "DELETE FROM `t_authority_role_permission` WHERE `ROLE_ID` = ?";
        template.update(sql, roleId);
    }

}
