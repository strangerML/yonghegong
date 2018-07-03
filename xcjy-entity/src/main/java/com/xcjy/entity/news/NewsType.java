package com.xcjy.entity.news;


import java.io.Serializable;

import com.xcjy.entity.common.BaseEntity;
import com.xcjy.entity.common.orm.annotation.Column;
import com.xcjy.entity.common.orm.annotation.Entity;
import com.xcjy.entity.common.orm.annotation.Id;
/**
 * 新闻类型
 * 
 * @author 支亚州
 *
 */
@Entity(table = "newstype")
public class NewsType extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4999291375851932104L;

	/**
	 * 主键id
	 */
	@Id
	@Column("id")
	private String id;
	
	@Column("typeName")
	private String typeName; // 类型名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
