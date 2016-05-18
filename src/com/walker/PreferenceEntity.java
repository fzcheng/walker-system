package com.walker;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统配置参数对象定义
 * @author MikeShi
 *
 */
@Entity
@Table(name = "s_preference")
public class PreferenceEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@Id
	@GeneratedValue(generator="idGenerator")
	@Column(name="id", length=36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "sxh")
	public long getSxh() {
		return sxh;
	}
	public void setSxh(long sxh) {
		this.sxh = sxh;
	}
	
	@Column(name = "name", length=200)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "data_type")
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	@Column(name = "data_value")
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
	@Column(name = "default_value")
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Column(name = "group_name")
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	private long sxh;
	private String name;
	private int dataType = 0;
	private String dataValue;
	
	private String defaultValue;
	private String groupName;
	
	public String toString(){
		return new StringBuilder().append("name = ").append(name)
				.append(", value = ").append(dataValue)
				.append(", defaultValue = ").append(defaultValue).toString();
	}
}
