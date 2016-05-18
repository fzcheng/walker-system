package com.walkersoft.flow.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.util.DepartmentUtils;
import com.walkersoft.flow.FlowAction;
import com.walkersoft.flow.entity.FlowBindEntity;
import com.walkersoft.flow.manager.FlowBindManagerImpl;

/**
 * 流程绑定管理界面
 * @author shikeying
 * @date 2014-11-17
 *
 */
@Controller
public class FlowBindAction extends FlowAction {

	private static final String URL_INDEX = "flow/bind/bind_index";
	private static final String URL_RELOAD = "flow/bind/bind_list";
	private static final String URL_ADD = "flow/bind/bind_add";
	
	@Autowired
	private FlowBindManagerImpl flowBindManager;
	
	@RequestMapping("flow/bind/index")
	public String index(Model model){
		this.doReloadList(model);
		return URL_INDEX;
	}
	
	@RequestMapping(value = "permit/flow/bind/reload")
	public String reloadPage(Model model){
		this.doReloadList(model);
		return URL_RELOAD;
	}
	
	private void doReloadList(Model model){
		String type = this.getParameter("type");
		if(StringUtils.isEmpty(type)){
			loadList(model, flowBindManager.queryFlowBindPager());
		} else
			loadList(model, flowBindManager.queryFlowBindPager(Integer.parseInt(type)));
	}
	
	@RequestMapping("permit/flow/bind/show_add")
	public String showAddPage(Model model){
		model.addAttribute("processes", this.getAvailablePrimaryProcessList());
		model.addAttribute("orgSet", this.getAllDepartmentTreeForJson(DepartmentUtils.selectOrgCallback, false));
		model.addAttribute("businessType", this.getBusinessTypeInfo());
		return URL_ADD;
	}
	
	@RequestMapping("permit/flow/bind/save")
	public void save(FlowBindEntity entity
			, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			flowBindManager.execSave(entity);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("permit/flow/bind/remove")
	public void remove(String id
			, HttpServletResponse response) throws IOException{
		Assert.hasText(id);
		flowBindManager.execRemove(Long.parseLong(id));
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
}
