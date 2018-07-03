package com.xcjy.entity.agency;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
/**
 * 中心简介
 * 
 * @author 支亚州
 *
 */
@Entity(table = "cadre")
public class Cadre extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4999291375851932104L;

	/**
	 * 主键id
	 */
	@Id
	@Column("id")
	private String id;
	
	@Column("name")
	private String name; // 标题
	
	@Column("contents")
	private String contents; // 类型名称
	
	@Column("publishUserId")
	private Long publishUserId; // 发帖人Id
	
	private String publishUserName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("createTime")
	private Date createTime; // 创建时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("updateTime")
	private Date updateTime; //更新时间

	@Column("pictures")
	private String pictures; // 图片
	
	@Column("cadreCount")
	private int cadreCount; // 点击次数
	
	@Column("cadreType")
	private String cadreType; // 1：干部队伍2：部门职责3：机构设置
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getPublishUserId() {
		return publishUserId;
	}

	public void setPublishUserId(Long publishUserId) {
		this.publishUserId = publishUserId;
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

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getPublishUserName() {
		return publishUserName;
	}

	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCadreCount() {
		return cadreCount;
	}

	public void setCadreCount(int cadreCount) {
		this.cadreCount = cadreCount;
	}

	public String getCadreType() {
		return cadreType;
	}

	public void setCadreType(String cadreType) {
		this.cadreType = cadreType;
	}

}
