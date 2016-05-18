package com.walkersoft.flow.pojo;

import com.walker.flow.meta.instance.TaskInstance;
import com.walkersoft.flow.MiscUtils;

public class TaskItem extends TaskInstance {

	public TaskItem(){
		super(null);
	}
	
	public String getShowCreateTime(){
		return MiscUtils.getDatetimeShow(this.getCreateTime());
	}
	
	public String getShowEndTime(){
		return MiscUtils.getDatetimeShow(this.getEndTime());
	}
}
