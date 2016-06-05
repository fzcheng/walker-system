package com.walkersoft.appmanager.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.appmanager.manager.DailyManagerImpl;
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
	private DailyManagerImpl dailyManager;
	
	public static final String APP_BASE_URL = "appos/daily/";
	
	@RequestMapping("appos/daily/index")
	public String index(Model model){
		
		this.setUserApps(model);
		
		List<AppGroup> apps = this.getCurrentUserDetails().getUserAppGroup();
		if(apps == null || apps.size() == 0)
		{
			model.addAttribute("msg", "无可查看的应用，请联系管理员进行配置。");
			return APP_BASE_URL + "error";
		}
		
		String curappid = (String)this.getParameter("appid");
		if(curappid == null || curappid.equals("") || curappid.equals("0"))
		{
			loadList(model, dailyManager.queryPageList(apps));
		}
		else
		{
			if(!IsIn(apps, curappid))
			{
				model.addAttribute("msg", "无此应用的查询权限，请联系管理员进行配置。");
				return APP_BASE_URL + "error";
			}
			loadList(model, dailyManager.queryPageList(curappid));
		}
		
		setUserPointers(model);
		
		return APP_BASE_URL + "index";
	}
	
	@RequestMapping("permit/appos/daily/reload")
	public String reload(Model model){
		
		this.setUserApps(model);
		
		List<AppGroup> apps = this.getCurrentUserDetails().getUserAppGroup();
		if(apps == null || apps.size() == 0)
		{
			model.addAttribute("msg", "无可查看的应用，请联系管理员进行配置。");
			return APP_BASE_URL + "error";
		}
		
		String curappid = (String)this.getParameter("appid");
		if(curappid == null || curappid.equals("") || curappid.equals("0"))
		{
			loadList(model, dailyManager.queryPageList(apps));
		}
		else
		{
			if(!IsIn(apps, curappid))
			{
				model.addAttribute("msg", "无此应用的查询权限，请联系管理员进行配置。");
				return APP_BASE_URL + "error";
			}
			loadList(model, dailyManager.queryPageList(curappid));
		}
		
		setUserPointers(model);
		
		return APP_BASE_URL + "list";
	}

	private boolean IsIn(List<AppGroup> apps, String curappid) {
		for(int i = 0; i < apps.size(); i ++)
		{
			if(curappid.equals(apps.get(i).getAppid()))
				return true;
		}
		return false;
	}
	
}
