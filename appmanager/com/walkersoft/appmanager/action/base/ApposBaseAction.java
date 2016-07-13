package com.walkersoft.appmanager.action.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.walkersoft.appmanager.entity.StrategyGroupEntity;
import com.walkersoft.appmanager.manager.StrategyDetailManagerImpl;
import com.walkersoft.system.SystemAction;

@Controller
public class ApposBaseAction extends SystemAction{

	@Autowired
	StrategyDetailManagerImpl strategyDetailManager;
	
	private static final String NAME_STRATEGYGROUP_MAP = "strategygroups";
	/**
	 * 把所有的‘策略组’的列表权限信息写入响应中，供模板页面处理对应按钮。</br>
	 * 该方法由各个action中功能首页的请求来调用，如：<code>UserAction</code>中的index.do
	 * @param model
	 */
	protected void setAllStrategyGroup(Model model){
		
		List<StrategyGroupEntity> grouplist = strategyDetailManager.queryGroupPageList().getDatas();
		
		if(grouplist != null){
			model.addAttribute(NAME_STRATEGYGROUP_MAP, grouplist);
		} else
			logger.debug("无可用的策略组！ ");
	}
}
