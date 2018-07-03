package com.xcjy.dao.authority;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xcjy.dao.base.BaseDao;
import com.xcjy.entity.authority.PageOperation;

@Repository
public class PageOperationDao extends BaseDao<PageOperation>{

	public List<PageOperation> findAllOperByModelId(Long moduleId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o.ID,o.NAME,p.ID as permissionid FROM t_authority_operation o ")
				.append(",t_authority_permission p,t_authority_module m ")
				.append(" WHERE o.ID = p.OPERATION_ID  AND p.MODULE_ID = m.ID ").append(" AND m.ID=? ORDER BY o.ID");
		List<Map<String, Object>> result = template.queryForList(sql.toString(), moduleId);
		List<PageOperation> pageOperations = new ArrayList<PageOperation>();
		for (Iterator<Map<String, Object>> iterator = result.iterator(); iterator.hasNext();) {
			Map<String, Object> record = iterator.next();
			PageOperation pageOperation = new PageOperation();
			pageOperation.setId((Long) record.get("ID"));
			pageOperation.setName((String) record.get("NAME"));
			pageOperation.setPermissionId((Long) record.get("permissionid"));
			pageOperations.add(pageOperation);
		}
		return pageOperations;
	}
	
}
