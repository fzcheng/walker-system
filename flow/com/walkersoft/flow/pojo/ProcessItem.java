package com.walkersoft.flow.pojo;

import com.walker.flow.meta.instance.ProcessInstance;
import com.walkersoft.flow.MiscUtils;

/**
 * 流程实例展示对象，在“流程运行监控”列表中使用。
 * @author shikeying
 * @date 2014-10-22
 *
 */
public class ProcessItem extends ProcessInstance {

	private String taskName;	// 待办任务名称
	
	private String userName;	// 待办任务执行人名字
	
	private String processTemplate;	// 使用流程模板名称
	
	public String getProcessTemplate() {
		return processTemplate;
	}

	public void setProcessTemplate(String processTemplate) {
		this.processTemplate = processTemplate;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShowCreateTime(){
		return doGetDatetimeShow(getCreateTime());
	}
	
	public String getShowEndTime(){
		return doGetDatetimeShow(getEndTime());
	}
	
	public String getShowTerminateTime(){
		return doGetDatetimeShow(getTerminateTime());
	}
	
	public String getShowPauseTime(){
		return doGetDatetimeShow(getPause_time());
	}
	
	private String doGetDatetimeShow(long datetime){
//		if(datetime > 0){
//			return DateUtils.toShowDate(datetime);
//		}
//		return StringUtils.EMPTY_STRING;
		return MiscUtils.getDatetimeShow(datetime);
	}
	
	public ProcessItem(){
		super(null);
	}
}
