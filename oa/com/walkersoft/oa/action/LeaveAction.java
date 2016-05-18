package com.walkersoft.oa.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.oa.manager.LeaveManagerImpl;
import com.walkersoft.system.SystemAction;

/**
 * 请假管理
 * @author shikeying
 * @date 2014-12-2
 *
 */
@Controller
public class LeaveAction extends SystemAction {

	private static final String URL_INDEX = "oa/leave/leave_index";
	private static final String URL_LEAVE_RELOAD = "oa/leave/leave_list";
	
	@Autowired
	private LeaveManagerImpl leaveManager;
	
	@RequestMapping("oa/leave/index")
	public String index(Model model){
		loadList(model, leaveManager.queryPageList(this.getCurrentUserId()));
		return URL_INDEX;
	}
	
	@RequestMapping(value = "permit/oa/leave/leave_reload")
	public String reloadPage(Model model){
		loadList(model, leaveManager.queryPageList(this.getCurrentUserId()));
		return URL_LEAVE_RELOAD;
	}
}
