package com.walkersoft.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "s_role")
public class RoleEntity {

	@GenericGenerator(name="idGenerator", strategy="uuid")
	@Id
	@GeneratedValue(generator="idGenerator")
	@Column(name="id", length=36)
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "name", length=200)
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "create_time")
	private long createTime;
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "summary", length=200)
	private String summary;
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Column(name = "r_type")
	private int type = TYPE_USER;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Transient
	private boolean selected = false;
	
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * 角色类型：0系统，1用户，默认创建的都是用户角色；系统内置的角色不允许修改。
	 */
	public static final int TYPE_SYS  = 0;
	public static final int TYPE_USER = 1;
	
	public boolean isSystemRole(){
		return this.type == TYPE_SYS;
	}
}
