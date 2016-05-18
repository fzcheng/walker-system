package com.walkersoft.flow.pojo;

import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 等待流程办理的任务对象定义，代办任务列表使用
 * @author shikeying
 * @date 2014-10-17
 *
 */
public class AwaitTask {

	private String taskInstanceId;	// 任务实例ID
	
	private String taskDefineId;	// 任务定义ID
	
	private String taskName;		// 任务名称
	
	private long taskArrivedTime;	// 任务到达时间
	
	private String userId;			// 任务执行人ID
	
	private String userName;		// 执行人名字
	
	private String processInstanceId;	// 流程实例ID
	
	private String processDefineId;	// 流程定义ID
	
	private String processName;		// 流程名称
	
	private long processCreateTime;	// 流程创建时间
	
	private String businessId;		// 业务ID
	
	private int end = 0;			// 是否结束:0_运行中,1_已结束
	
	private long endTime;
	
	private int terminate = 0;		// 是否被终止:0_否1_是
	
	private long terminateTime;
	
	private int pause = 0;			// 是否暂停:0_否,1_是
	
	private long pauseTime;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getTerminate() {
		return terminate;
	}

	public void setTerminate(int terminate) {
		this.terminate = terminate;
	}

	public long getTerminateTime() {
		return terminateTime;
	}

	public void setTerminateTime(long terminateTime) {
		this.terminateTime = terminateTime;
	}

	public int getPause() {
		return pause;
	}

	public void setPause(int pause) {
		this.pause = pause;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}

	public String getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(String taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getTaskDefineId() {
		return taskDefineId;
	}

	public void setTaskDefineId(String taskDefineId) {
		this.taskDefineId = taskDefineId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getTaskArrivedTime() {
		return taskArrivedTime;
	}
	
	public String getShowTaskArrivedTime(){
		if(taskArrivedTime > 0){
			return DateUtils.toShowDate(taskArrivedTime);
		}
		return StringUtils.EMPTY_STRING;
	}

	public void setTaskArrivedTime(long taskArrivedTime) {
		this.taskArrivedTime = taskArrivedTime;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public long getProcessCreateTime() {
		return processCreateTime;
	}

	public void setProcessCreateTime(long processCreateTime) {
		this.processCreateTime = processCreateTime;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
}
