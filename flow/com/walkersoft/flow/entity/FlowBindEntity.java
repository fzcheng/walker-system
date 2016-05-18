package com.walkersoft.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程绑定实体
 * @author shikeying
 *
 */
@Entity
@Table(name = "s_flow_bind")
public class FlowBindEntity {

	public static final int TYPE_ORG = 0;	// 绑定单位
	public static final int TYPE_DEPT = 1; // 绑定部门
	public static final int TYPE_ROLE = 2; // 绑定角色
	public static final int TYPE_POST = 3; // 绑定岗位
	public static final int TYPE_USER = 9; // 绑定个人
	
	@Id
	@Column(name="id")
	private long id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name="type")
	private int type = TYPE_ORG;
	
	@Column(name="bind_id", length=36)
	private String bindId;
	
	@Column(name="bind_name", length=50)
	private String bindName;
	
	@Column(name="process_define_id", length=36)
	private String processDefineId;
	
	@Column(name="process_define_name", length=100)
	private String processDefineName;
	
	@Column(name="summary", length=100)
	private String summary;
	
	@Column(name="business_type", length=12)
	private String businessType;

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}
//	public String getShowCreateTime(){
//		if(createTime > 0){
//			return DateUtils.toShowDate(createTime);
//		}
//		return StringUtils.EMPTY_STRING;
//	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}

	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public String getProcessDefineName() {
		return processDefineName;
	}

	public void setProcessDefineName(String processDefineName) {
		this.processDefineName = processDefineName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Override
	public String toString(){
		return new StringBuilder().append("{bindId=").append(bindId)
				.append(", bindName=").append(bindName)
				.append(", processDefineId=").append(processDefineId)
				.append(", processDefineName=").append(processDefineName)
				.append("}").toString();
	}
}
