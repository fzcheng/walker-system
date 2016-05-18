package com.walkersoft.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "s_department")
public class DepartmentEntity {

	public static final int TYPE_ORG = 0;  // 机构
	public static final int TYPE_DEPT = 1; // 部门
	public static final int TYPE_POST = 9; // 岗位
	
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@Id
	@GeneratedValue(generator="idGenerator")
	@Column(name="id", length=36)
	private String id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "order_num")
	private long orderNum = 0;
	
	@Column(name = "name", length=200)
	private String name;
	
	@Column(name = "simple_name", length=200)
	private String simpleName;
	
	@Column(name = "type")
	private int type = TYPE_DEPT;
	
	@Column(name = "is_system")
	private int system = 0;
	
	@Column(name = "parent_id", length=36)
	private String parentId;
	
	@Column(name = "attribute", length=100)
	private String attribute;
	
	@Column(name = "child_sum")
	private int childSum = 0;
	
	@Column(name = "administrate")
	private int administrate = 0;
	
	@Column(name = "charge_man", length=36)
	private String chargeMan;
	
	@Column(name = "manager", length=36)
	private String manager;
	
	@Column(name = "summary", length=255)
	private String summary;
	
	@Column(name = "status", length = 2)
	private int status = 0;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSystem() {
		return system;
	}

	public void setSystem(int system) {
		this.system = system;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getChildSum() {
		return childSum;
	}

	public void setChildSum(int childSum) {
		this.childSum = childSum;
	}

	public int getAdministrate() {
		return administrate;
	}

	public void setAdministrate(int administrate) {
		this.administrate = administrate;
	}

	public String getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public void increaseChildSum(){
		this.childSum += 1;
	}
	
	public void decreaseChildSum(){
		if(this.childSum >= 1)
			this.childSum -= 1;
	}
	
	public String toString(){
		return new StringBuilder().append("department: id=").append(id)
				.append(", name=").append(name)
				.append(", parentId=").append(parentId)
				.append(", childSum=").append(childSum)
				.append("").toString();
	}
	
	/**
	 * 是否单位，如果是返回<code>true</code>，如果是部门返回<code>false</code>
	 * @return
	 */
	public boolean isOrg(){
		return this.type == TYPE_ORG;
	}
	
	/**
	 * 是否部门
	 * @return
	 */
	public boolean isDept(){
		return this.type == TYPE_DEPT;
	}
	
	/**
	 * 是否岗位
	 * @return
	 */
	public boolean isPost(){
		return this.type == TYPE_POST;
	}
}
