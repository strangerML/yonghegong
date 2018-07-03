package com.xcjy.entity.news;


import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;

/**
 * 信息实体类
 * 
 * @author 支亚州
 *
 */
@Entity(table = "news")
public class News extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4999291375859932104L;

	/**
	 * 主键id
	 */
	@Id
	@Column("id")
	private String id;
	
	@Column("newsTitle")
	private String newsTitle; // 标题
	
	@Column("picUrl")
	private String picUrl;//微信新闻封面图片
	
	@Column("publishUserId")
	private Long publishUserId; // 发帖人Id
	
	private String publishUserName;
	
	@Column("contents")
	private String contents; // 内容
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("createTime")
	private Date createTime; // 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("updateTime")
	private Date updateTime; //更新时间

	@Column("newsCount")
	private int newsCount; // 帖子前台点击次数
	
	@Column("pictures")
	private String pictures; // 附件
	
	@Column("fileNames")
	private String fileNames; // 上传附件名字

	@Column("stick")
	private String stick; // 帖子置顶
	
	@Column("newsTypeId")
	private String newsTypeId; // 关联新闻类型Id
	
	private String newsTypeName;

	@Column("postState")
	private int postState; //帖子状态 0：正常  1：冻结
	
	/**
	 * 所属栏目：
	 * 1：中心介绍
	 * 2：公告通知3：招生政策4：问题解答5：相关下载
	 * 6:中心新闻7:最新公告
	 * 8:工会活动9：党务工作10：政务公开
	 */
	@Column("columnType")
	private int columnType; //
	
	@Column("freezeUserid")
	private Long freezeUserId; //冻结人id

	private String freezeUserName; //冻结人姓名
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}


	public Long getPublishUserId() {
		return publishUserId;
	}

	public void setPublishUserId(Long publishUserId) {
		this.publishUserId = publishUserId;
	}

	public String getPublishUserName() {
		return publishUserName;
	}

	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

	public int getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(int newsCount) {
		this.newsCount = newsCount;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getStick() {
		return stick;
	}

	public void setStick(String stick) {
		this.stick = stick;
	}

	public String getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(String newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	public String getNewsTypeName() {
		return newsTypeName;
	}

	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
	}

	public int getPostState() {
		return postState;
	}

	public void setPostState(int postState) {
		this.postState = postState;
	}

	public Long getFreezeUserId() {
		return freezeUserId;
	}

	public void setFreezeUserId(Long freezeUserId) {
		this.freezeUserId = freezeUserId;
	}

	public String getFreezeUserName() {
		return freezeUserName;
	}

	public void setFreezeUserName(String freezeUserName) {
		this.freezeUserName = freezeUserName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
