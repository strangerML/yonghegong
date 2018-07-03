package com.xcjy.entity.authority;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;

/**
 * 模块实体类
 * 
 * @author binbinccut@163.com
 *
 */
@Entity(table = "t_authority_module")
public class Module extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4021949236476451073L;

	/**
	 * 主键id
	 */
	@Id
	@Column("ID")
	private Long id;

	/**
	 * 模块名称
	 */
	@Column("NAME")
	private String name;

	/**
	 * 模块对应的
	 */
	@Column("URL")
	private String url;

	/**
	 * 模块标识符
	 */
	@Column("SN")
	private String sn;

	/**
	 * 父模块id
	 */
	@Column("PARENT_ID")
	private Long parentId;

	/**
	 * 是否为叶子节点。0否，1是，默认0。
	 */
	@Column("IS_LEAF")
	private Integer isLeaf;

	/**
	 * 排序字段
	 */
	@Column("ORDER")
	private Integer order;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("CREATE_TIME")
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("UPDATE_TIME")
	private Date updateTime;

	@Column("REMARK")
	private String remark;

	/**
	 * 所有的子模块
	 */
	private List<Module> children;

	/**
	 * 对应权限
	 */
	private List<PageOperation> pageOperations;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<Module> getChildren() {
		return children;
	}

	public void setChildren(List<Module> children) {
		this.children = children;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<PageOperation> getPageOperations() {
		return pageOperations;
	}

	public void setPageOperations(List<PageOperation> pageOperations) {
		this.pageOperations = pageOperations;
	}

}
