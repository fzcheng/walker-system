package com.walkersoft.appmanager.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.appmanager.entity.StrategyGroupEntity;
import com.walkersoft.appmanager.manager.StrategyDetailManagerImpl;
import com.walkersoft.system.SystemAction;

@Controller
public class StrategyAction extends SystemAction {

	private static final String LOG_MSG_STRATEGY_GROUP_ADD = "添加策略组";
	
	public static final String APP_BASE_URL = "appos/strategy/";
	
	@Autowired
	StrategyDetailManagerImpl strategyDetailManager;
	
/////////////////////////////////////////////////////－－策略组－－
	@RequestMapping("appos/strategy/group")
	public String group(Model model){
		
		this.setUserApps(model);
		setUserPointers(model);
		loadList(model, strategyDetailManager.queryGroupPageList());
		return APP_BASE_URL + "group";
	}
	
	@RequestMapping(value = "permit/appos/strategy/reloadGroup")
	public String reloadGroup(Model model){
		loadList(model, strategyDetailManager.queryGroupPageList());
		return APP_BASE_URL + "group_list";
	}
	
	@RequestMapping("permit/appos/strategy/showAddGroupItem")
	public String addGroup(Model model){
		
		this.setUserApps(model);
		setUserPointers(model);
		loadList(model, strategyDetailManager.queryGroupPageList());
		return APP_BASE_URL + "group_add";
	}
	
	@RequestMapping("appos/strategy/addStrategyGroup")
	public void saveGroup(StrategyGroupEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			strategyDetailManager.execSaveGroup(entity);
			systemLog(LOG_MSG_STRATEGY_GROUP_ADD + entity.getGroup_name(), LogType.Add);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/strategy/updateStrategyGroup")
	public void updateGroup(StrategyGroupEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			strategyDetailManager.execSaveGroup(entity);
			systemLog(LOG_MSG_STRATEGY_GROUP_ADD + entity.getGroup_name(), LogType.Add);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/strategy/delStrategyGroup")
	public void delGroup(StrategyGroupEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			strategyDetailManager.execSaveGroup(entity);
			systemLog(LOG_MSG_STRATEGY_GROUP_ADD + entity.getGroup_name(), LogType.Add);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	/////////////////////////////////////////////////////－－策略－－
	@RequestMapping("appos/strategy/strategy")
	public String strategy(Model model){
		
		this.setUserApps(model);
		setUserPointers(model);
		loadList(model, strategyDetailManager.queryStrategyPageList());
		return APP_BASE_URL + "strategy";
	}
	
	@RequestMapping(value = "permit/appos/strategy/reloadStrategy")
	public String reloadStrategy(Model model){
		loadList(model, strategyDetailManager.queryStrategyPageList());
		return APP_BASE_URL + "strategy_list";
	}
	
	@RequestMapping("permit/appos/strategy/showAddStrategyItem")
	public String addStrategy(Model model){
		
		this.setUserApps(model);
		setUserPointers(model);
		loadList(model, strategyDetailManager.queryStrategyPageList());
		return APP_BASE_URL + "strategy_add";
	}
	
	@RequestMapping("appos/strategy/addStrategy")
	public void saveStrategy(StrategyGroupEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			strategyDetailManager.execSaveGroup(entity);
			systemLog(LOG_MSG_STRATEGY_GROUP_ADD + entity.getGroup_name(), LogType.Add);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/strategy/updateStrategy")
	public void updateStrategy(StrategyGroupEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			strategyDetailManager.execSaveGroup(entity);
			systemLog(LOG_MSG_STRATEGY_GROUP_ADD + entity.getGroup_name(), LogType.Add);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/strategy/delStrategy")
	public void delStrategy(StrategyGroupEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			strategyDetailManager.execSaveGroup(entity);
			systemLog(LOG_MSG_STRATEGY_GROUP_ADD + entity.getGroup_name(), LogType.Add);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	/////////////////////////////////////////////////////－－策略组详情－－
	@RequestMapping("appos/strategy/groupdetail")
	public String groupDetail(Model model){
		
		this.setUserApps(model);
		setUserPointers(model);
		
		return APP_BASE_URL + "groupdetail";
	}
	
}
