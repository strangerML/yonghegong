package com.xcjy.entity.authority;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;

/**
 * 页面操作实体类
 * @author Administrator
 *
 */
@Entity(table = "t_authority_operation")
public class PageOperation extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2672469707027488683L;
	
	/**
	 * 主键id
	 */
	@Id
	@Column("ID")
	private Long id;

	/**
	 * 操作名称
	 */
	@Column("NAME")
	private String name;

	/**
	 * 操作标识符
	 */
	@Column("SN")
	private String sn;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("CREATE_TIME")
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("UPDATE_TIME")
	private Date updateTime;

	@Column("REMARK")
	private String remark;

	private Long permissionId;

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}


}
