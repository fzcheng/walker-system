package com.walkersoft.quartz.action;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;

import com.walkersoft.system.SystemAction;

public class QuartzBaseAction extends SystemAction {

	@Autowired
	private Scheduler scheduler = null;

	protected Scheduler getScheduler() {
		return scheduler;
	}
}
