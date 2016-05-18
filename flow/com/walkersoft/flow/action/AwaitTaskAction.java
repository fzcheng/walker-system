package com.walkersoft.flow.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.flow.FlowAction;
import com.walkersoft.flow.manager.ProcessInstanceManagerImpl;

@Controller
public class AwaitTaskAction extends FlowAction {

	private static final String URL_INDEX = "flow/instance/await_task";
	private static final String URL_AWAIT_RELOAD = "flow/instance/await_list";
	
	@Autowired
	private ProcessInstanceManagerImpl processInstanceManager;
	
	/**
	 * 待办任务界面
	 * @param model
	 * @return
	 */
	@RequestMapping("flow/instance/await_task")
	public String index(Model model){
		this.loadList(model, processInstanceManager.queryAwaitTaskPager());
		return URL_INDEX;
	}
	
	@RequestMapping(value = "permit/flow/instance/await_reload")
	public String reloadPage(Model model){
		loadList(model, processInstanceManager.queryAwaitTaskPager());
		return URL_AWAIT_RELOAD;
	}
}
