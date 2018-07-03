package com.xcjy.entity.authority;

import java.io.Serializable;

import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;

/**
 * 权限实体类
 * 
 * @author WH
 *
 */
@Entity(table = "t_authority_permission")
public class Permission extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6820256045996125304L;

	/**
	 * 主键id
	 */
	@Id
	@Column("ID")
	private Long id;

	/**
	 * 模块id
	 */
	@Column("MODULE_ID")
	private Long moduleId;

	/**
	 * 操作id
	 */
	@Column("OPERATION_ID")
	private Long operationId;

	/**
	 * 权限代码，例：user:add
	 */
	private String permissionSN;

	/**
	 * 模块id对应的模块
	 */
	private Module module;

	/**
	 * 操作id对应的操作
	 */
	private Operation operation;

	@Override
	public Serializable getId() {
		return id;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionSN() {
		return permissionSN;
	}

	public void setPermissionSN(String permissionSN) {
		this.permissionSN = permissionSN;
	}
}
