package com.xcjy.entity.news;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
/**
 * 图片新闻
 * 
 * @author 支亚州
 *
 */
@Entity(table = "picNews")
public class PicNews extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4999291775851932104L;

	/**
	 * 主键id
	 */
	@Id
	@Column("id")
	private String id;
	
	@Column("name")
	private String name; // 标题
	@Column("content")
	private String content; // 内容
	@Column("pictures")
	private String pictures; // 图片
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("createTime")
	private Date createTime; // 创建时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("updateTime")
	private Date updateTime; //更新时间
	@Column("status")
	private int status; //是否在主页显示，0不显示，1显示
	@Column("publishUserId")
	private Long publishUserId; // 发帖人Id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
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
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getPublishUserId() {
		return publishUserId;
	}
	public void setPublishUserId(Long publishUserId) {
		this.publishUserId = publishUserId;
	}
	
}
