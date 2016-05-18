package com.walkersoft.flow.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.flow.meta.ProcessDefine;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.FlowAction;
import com.walkersoft.flow.manager.ProcessDefineManagerImpl;

@Controller
public class ProcessDefineManageAction extends FlowAction {

	private static final String URL_DEFINE_INDEX = FLOW_BASE + "define/index";
	private static final String URL_DEFINE_RELOAD = FLOW_BASE + "define/flow_list";
	private static final String URL_DEFINE_ADD = FLOW_BASE + "define/flow_add";
	private static final String URL_DEFINE_EDIT = FLOW_BASE + "define/flow_edit";
	
	private static final String ATTR_BIZ_TYPES = "businessType";
	private static final String ATTR_CREATE_LISTENER = "createListener";
	private static final String ATTR_END_LISTENER = "endListener";
	
	@Autowired
	private ProcessDefineManagerImpl processDefineManager;
	
	@RequestMapping(value = "flow/define/index")
	public String index(Model model){
		setDefaultSearchCondition(model);
		loadList(model, processDefineManager.queryPagedProcessIdentifierList());
		return URL_DEFINE_INDEX;
	}
	
	@RequestMapping(value = "permit/flow/define/reload")
	public String reloadPage(Model model){
		loadList(model, processDefineManager.queryPagedProcessIdentifierList());
		return URL_DEFINE_RELOAD;
	}
	
	@RequestMapping(value = "permit/flow/define/add")
	public String showAddPage(Model model){
//		Map<String, String> bizTypeInfo = this.getBusinessTypeInfo();
		model.addAttribute(ATTR_BIZ_TYPES, getBusinessTypeInfo());
		model.addAttribute(ATTR_CREATE_LISTENER, getProcessCreateListenerInfo());
		model.addAttribute(ATTR_END_LISTENER, getProcessEndListenerInfo());
		return URL_DEFINE_ADD;
	}
	
	@RequestMapping("permit/flow/define/save")
	public void saveProcess(@ModelAttribute("entity") ProcessDefine entity
			, HttpServletResponse response) throws IOException{
		if(entity != null){
			logger.debug(entity);
			if(processDefineManager.queryExistIdentifier(entity.getInterIdentifier()) != null){
				this.ajaxOutPutText("已存在该流程标识，请重新输入一个不同的标识名称。");
				return;
			}
			processDefineManager.execSaveProcess(entity);
			/* 创建新流程后，保存到缓存 */
			getFlowCacheFactory().addProcessToCache(entity);
		} else
			throw new IllegalArgumentException("saved processDefine not found.");
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/define/edit")
	public String showEditPage(Model model){
		String processDefineId = this.getParameter(PARAM_ID);
		assert (StringUtils.isNotEmpty(processDefineId));
		ProcessDefine pd = processDefineManager.queryProcessDefine(processDefineId);
		model.addAttribute(ATTR_PROCESS_DEFINE, pd);
		model.addAttribute(ATTR_BIZ_TYPES, getBusinessTypeInfo());
		model.addAttribute(ATTR_CREATE_LISTENER, getProcessCreateListenerInfo());
		model.addAttribute(ATTR_END_LISTENER, getProcessEndListenerInfo());
		return URL_DEFINE_EDIT;
	}
	
	/**
	 * 修改流程基本信息，不涉及任务。
	 * @param entity
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("permit/flow/define/saveEdit")
	public void saveEditProcess(@ModelAttribute("entity") ProcessDefine entity
			, HttpServletResponse response) throws IOException{
		String id = entity.getId();
		assert (StringUtils.isNotEmpty(id));
		processDefineManager.execUpdateProcess(entity);
		/* 更新流程基本信息缓存 */
		processDefineCache.updateCacheData(id, entity);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping("permit/flow/define/deprecate")
	public void deprecateProcess(HttpServletResponse response) throws IOException{
		String processDefineId = this.getParameter(PARAM_ID);
		assert (StringUtils.isNotEmpty(processDefineId));
		processDefineManager.execDeprecateProcess(processDefineId);
		/* 更新流程标志位‘已删除’，注意：此方法并不从缓存中删除流程数据，仅删除了版本信息。 */
		/* 后面会调整代码，在此仅仅是重用了老代码，等待重构。（不应该删除版本缓存信息） */
		getFlowCacheFactory().deleteProcessFromCache(processDefineId);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping("permit/flow/define/delete")
	public void deleteProcess(HttpServletResponse response) throws IOException{
		String processDefineId = this.getParameter(PARAM_ID);
		assert (StringUtils.isNotEmpty(processDefineId));
		processDefineManager.execDeleteProcess(processDefineId);
		getFlowCacheFactory().removeProcessFromCache(processDefineId);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
}
