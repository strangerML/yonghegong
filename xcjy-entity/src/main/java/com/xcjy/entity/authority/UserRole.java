package com.xcjy.entity.authority;

import java.io.Serializable;

import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
/**
 * 用户和角色关系实体
 * @author bianqi
 */
@Entity(table="t_authority_user_role")
public class UserRole extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 928528363265832811L;

	/**
	 * 主键ID
	 */
	@Id
	@Column("ID")
	private Long id;
	
	/**
	 * 用户ID
	 */
	@Column("USER_ID")
	private Long userId;
	
	/**
	 * 角色ID
	 */
	@Column("ROLE_ID")
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
