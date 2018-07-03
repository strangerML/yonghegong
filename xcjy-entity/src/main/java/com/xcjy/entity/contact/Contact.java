package com.xcjy.entity.contact;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
/**
 * 联系我们实体类
 * 
 * @author 支亚州
 *
 */
@Entity(table = "contact")
public class Contact extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4999291375851932104L;

	/**
	 * 主键id
	 */
	@Id
	@Column("id")
	private String id;
	
	@Column("officeName")
	private String officeName; // 办公室名称
	
	@Column("tel")
	private String tel; // 联系电话
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column("createTime")
	private Date createTime; // 创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
