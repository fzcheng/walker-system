package com.walkersoft.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.walker.flow.core.biz.BizDataDefine;

@Entity
@Table(name = "sw_biz_data")
public class BizDataEntity {

	@Id
	@Column(name="id", length=36)
	private String id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name="process_inst_id", length=36)
	private String processInstId;
	
	@Column(name="name", length=36)
	private String name;
	
	@Column(name="value", length=36)
	private String value;
	
	@Column(name="data_type")
	private int dataType = BizDataDefine.BIZDATA_TYPE_CHAR;

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

	public String getProcessInstId() {
		return processInstId;
	}

	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String toString(){
		return new StringBuilder().append("[id=").append(id)
				.append(", name=").append(name)
				.append(", value=").append(value).append("]").toString();
	}
}
