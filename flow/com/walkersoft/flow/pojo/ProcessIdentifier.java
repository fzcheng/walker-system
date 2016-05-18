package com.walkersoft.flow.pojo;

import com.walker.flow.meta.ProcessDefine;

public class ProcessIdentifier {

	private String identifier;
	
	private long createTime = 0;
	
	private ProcessDefine processDefine;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public ProcessDefine getProcessDefine() {
		return processDefine;
	}

	public void setProcessDefine(ProcessDefine processDefine) {
		this.processDefine = processDefine;
	}
	
}
