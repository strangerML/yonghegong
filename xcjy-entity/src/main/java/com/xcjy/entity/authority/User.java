package com.xcjy.entity.authority;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;

/**
 * 用户实体类
 * @author WH
 *
 */
@Entity(table="t_authority_user")
public class User extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 2197491254490089406L;

	/**
	 * 主键ID
	 */
	@Id
	@Column("ID")
	private Long id;
	
	/**
	 * 用户名
	 */
	@Column("USER_NAME")
	private String userName;

	private String roleName;
	
	/**
	 * 密码
	 */
	@Column("PASSWORD")
	private String password;
	
	
	/**
	 * 手机号
	 */
	@Column("PHONE")
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column("EMAIL")
	private String email;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("CREATE_TIME")
	private Date createTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("UPDATE_TIME")
	private Date updateTime;
	
	@Column("REMARK")
	private String remark;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
