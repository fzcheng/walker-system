package com.walkersoft.appmanager.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.system.SystemAction;

/**
 * 应用
 * @author a
 *
 */

@Controller
public class DailyAction extends SystemAction {
	
	@Autowired
	private AppManagerImpl appManager;
	
	private static final String LOG_MSG_APPDEL = "删除应用";
	private static final String LOG_MSG_APPADD = "添加应用";
	private static final String LOG_MSG_APPEDIT = "编辑应用";
	public static final String APP_BASE_URL = "appos/app/";
	
	@RequestMapping("appos/daily/index")
	public String index(Model model){
		setUserPointers(model);
		loadList(model, appManager.queryPageList(this.getCurrentUserId()));
		return APP_BASE_URL + "app_index";
	}
	
}
