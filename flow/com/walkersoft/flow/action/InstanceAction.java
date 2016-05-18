package com.walkersoft.flow.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.Assert;
import com.walkersoft.flow.FlowAction;
import com.walkersoft.flow.manager.ProcessInstanceManagerImpl;

/**
 * 流程实例管理
 * @author shikeying
 * @date 2014-8-20
 *
 */
@Controller
public class InstanceAction extends FlowAction {

	private static final String URL_INDEX = "flow/instance/index";
	private static final String URL_RELOAD = "flow/instance/inst_list";
	private static final String URL_VIEW_PROCESS = "flow/instance/view_process";
	private static final String URL_VIEW_PROCESS_INST = "flow/instance/view_process_inst";
	private static final String URL_VIEW_TASK_LIST = "flow/instance/view_task_list";
	
	@Autowired
	private ProcessInstanceManagerImpl processInstanceManager;
	
	@RequestMapping("flow/instance/index")
	public String index(Model model){
		loadList(model, processInstanceManager.queryAllProcessListPager());
		return URL_INDEX;
	}
	
	@RequestMapping(value = "permit/flow/instance/reload")
	public String reloadPage(Model model){
		loadList(model, processInstanceManager.queryAllProcessListPager());
		return URL_RELOAD;
	}
	
	@RequestMapping(value = "permit/flow/instance/view")
	public String showProcessInstanceInfo(Model model
			, String processInstId, String processDefineId){
		Assert.hasText(processInstId);
		Assert.hasText(processDefineId);
		model.addAttribute("processInstId", processInstId);
		model.addAttribute("processDefineId", processDefineId);
		return URL_VIEW_PROCESS;
	}
	
	/**
	 * 显示流程监控界面中的tab标签-流程实例明细
	 * @param model
	 * @param processInstId
	 * @return
	 */
	@RequestMapping(value = "permit/flow/instance/view_item")
	public String showProcessItem(Model model, String processInstId){
		Assert.hasText(processInstId);
		Object[] processInfo = processInstanceManager.queryProcessInstance(processInstId);
		model.addAttribute("process", processInfo[0]);
		model.addAttribute("bizDataList", processInfo[1]);
		return URL_VIEW_PROCESS_INST;
	}
	
	@RequestMapping(value = "permit/flow/instance/view_task_list")
	public String showTaskInstanceList(Model model, String processInstId){
		Assert.hasText(processInstId);
		model.addAttribute("taskList", processInstanceManager.queryTaskInstanceList(processInstId));
		return URL_VIEW_TASK_LIST;
	}
}
