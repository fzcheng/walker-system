package com.walkersoft.flow.pojo;

public class TestProject {

	private String id;
	private long createTime = 0;
	private String name;
	private String summary;
	private String processId;	// 流程实例ID
	private String processName;
	private int status = 0;
	
	private String processDefineId;	// 流程定义ID
	
	public String getProcessDefineId() {
		return processDefineId;
	}
	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toString(){
		return new StringBuilder().append("name=").append(name)
				.append(", processName").append(processName).toString();
	}
}
