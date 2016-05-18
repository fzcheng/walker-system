package com.walkersoft.flow.portlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.walker.db.page.ListPageContext;
import com.walker.db.page.support.GenericPager;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.flow.manager.ProcessInstanceManagerImpl;
import com.walkersoft.flow.pojo.AwaitTask;
import com.walkersoft.portlet.simp.BasePortlet;

/**
 * 待办任务Portlet应用实现
 * @author shikeying
 * @date 2014-10-27
 *
 */
@Component("awaitTaskPortlet")
public class AwaitTaskPortlet extends BasePortlet {

	@Autowired
	private ProcessInstanceManagerImpl processInstanceManager;
	
	public AwaitTaskPortlet(){
		setId("awaitTaskPortlet");
		setTitle("我的任务列表");
		setDescription("需要处理的流程任务");
		setIncludePage("/flow/portlet/await_task.ftl");
	}
	
//	@Override
//	public String getId(){
//		return "awaitTaskPortlet";
//	}
//	
//	@Override
//	public String getTitle() {
//		return "我的任务列表";
//	}
//	
//	@Override
//	public String getDescription() {
//		return "需要处理的流程任务";
//	}
//	
//	@Override
//	public String getIncludePage() {
//		return "/portlet/await_task.ftl";
//	}
	
	@Override
	public void reload(Model model) throws Exception {
		ListPageContext.setCurrentPageSize(5);
		GenericPager<AwaitTask> pager = processInstanceManager.queryAwaitTaskPager(MyApplicationConfig.getCurrentUser().getId());
		model.addAttribute("awaitTaskList", pager == null? null : pager.getDatas());
	}
}
