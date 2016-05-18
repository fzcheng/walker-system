package com.walkersoft.system.pojo;

public class ExportObj {

	private String id;
	private long createTime = 0;
	private String creatorName;
//	private int type = 1; // 类型为1标识导出
	private String name;
	private String fileId;
	private int exportType = 0; // 导出类型：0_全表，1_选择表
	private String summary;
	
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
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public int getExportType() {
		return exportType;
	}
	public void setExportType(int exportType) {
		this.exportType = exportType;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
