package com.walkersoft.appmanager.action.base;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.walkersoft.appmanager.BaseConstant;
import com.walkersoft.appmanager.entity.ApposStatus;
import com.walkersoft.appmanager.entity.StrategyGroupEntity;
import com.walkersoft.appmanager.manager.StrategyDetailManagerImpl;
import com.walkersoft.system.SystemAction;

@Controller
public class ApposBaseAction extends SystemAction{

	public static Map<String, ApposStatus> statuslist = new TreeMap<String, ApposStatus>();
	public static Map<String, ApposStatus> transferStatuslist = new TreeMap<String, ApposStatus>();
	
	static{
		statuslist.put(""+BaseConstant.STATUS_CREATE, new ApposStatus(BaseConstant.STATUS_CREATE, "未完成"));
		statuslist.put(""+BaseConstant.STATUS_SUCCESS, new ApposStatus(BaseConstant.STATUS_SUCCESS, "成功"));
		statuslist.put(""+BaseConstant.STATUS_FAIL, new ApposStatus(BaseConstant.STATUS_FAIL, "失败"));
		
		transferStatuslist.put(""+BaseConstant.TRANSFER_STATUS_ING, new ApposStatus(BaseConstant.TRANSFER_STATUS_ING, "未通知"));
		transferStatuslist.put(""+BaseConstant.TRANSFER_STATUS_SUCCESS, new ApposStatus(BaseConstant.TRANSFER_STATUS_SUCCESS, "成功"));
		transferStatuslist.put(""+BaseConstant.TRANSFER_STATUS_FAIL, new ApposStatus(BaseConstant.TRANSFER_STATUS_FAIL, "失败"));
//		statuslist.put(""+BaseConstant.STATUS_CREATE, "未完成");
//		statuslist.put(""+BaseConstant.STATUS_SUCCESS, "成功");
//		statuslist.put(""+BaseConstant.STATUS_FAIL, "失败");
	}
	
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
	
	private static final String NAME_STATUS_MAP = "statuss";
	private static final String NAME_TRANSFERSTATUS_MAP = "transferstatuss";
	/**
	 * 把所有的 订单状态 的列表权限信息写入响应中，供模板页面处理对应按钮。</br>
	 * 该方法由各个action中功能首页的请求来调用，如：<code>UserAction</code>中的index.do
	 * @param model
	 */
	protected void setStatuss(Model model){
		
		if(statuslist != null){
			model.addAttribute(NAME_STATUS_MAP, statuslist);
		} else
			logger.debug("无订单状态列表！ ");
		
		if(transferStatuslist != null){
			model.addAttribute(NAME_TRANSFERSTATUS_MAP, transferStatuslist);
		} else
			logger.debug("无通知状态列表！ ");
	}
}
