package com.walkersoft.appmanager.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.pojo.AppGroup;

/**
 * 应用
 * @author a
 *
 */

@Controller
public class DailyAction extends SystemAction {
	
	@Autowired
	private AppManagerImpl appManager;
	
	public static final String APP_BASE_URL = "appos/app/";
	
	@RequestMapping("appos/daily/index")
	public String index(Model model){
		
		this.setUserApps(model);
		
		setUserPointers(model);
		loadList(model, appManager.queryPageList(this.getCurrentUserId()));
		return APP_BASE_URL + "app_index";
	}
	
}
