package com.walkersoft.appmanager.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.appmanager.action.base.ApposBaseAction;
import com.walkersoft.appmanager.manager.OrderManagerImpl;
import com.walkersoft.system.pojo.AppGroup;

/**
 * 订单流水管理
 * @author a
 *
 */

@Controller
public class OrderAction extends ApposBaseAction {
	
	@Autowired
	private OrderManagerImpl orderManager;
	
	public static final String APP_BASE_URL = "appos/order/";
	
	@RequestMapping("appos/order/index")
	public String index(Model model){
		
		this.setUserApps(model);
		this.setStatuss(model);
		
		List<AppGroup> apps = this.getCurrentUserDetails().getUserAppGroup();
		if(apps == null || apps.size() == 0)
		{
			model.addAttribute("msg", "无可查看的应用，请联系管理员进行配置。");
			return APP_BASE_URL + "error";
		}
		
		String curappid = (String)this.getParameter("appid");
		if(curappid == null || curappid.equals("") || curappid.equals("0"))
		{
			//loadList(model, orderManager.queryPageList(apps));
			loadList(model, orderManager.queryPageList(null, null));
		}
		else
		{
			if(!IsIn(apps, curappid))
			{
				model.addAttribute("msg", "无此应用的查询权限，请联系管理员进行配置。");
				return APP_BASE_URL + "error";
			}
			loadList(model, orderManager.queryPageList(curappid, null));
		}
		
		setUserPointers(model);
		
		return APP_BASE_URL + "index";
	}
	
	@RequestMapping("permit/appos/order/reload")
	public String reload(Model model){
		
		this.setUserApps(model);
		this.setStatuss(model);
		
		List<AppGroup> apps = this.getCurrentUserDetails().getUserAppGroup();
		if(apps == null || apps.size() == 0)
		{
			model.addAttribute("msg", "无可查看的应用，请联系管理员进行配置。");
			return APP_BASE_URL + "error";
		}
		
		String curappid = (String)this.getParameter("appid");
		String status = (String)this.getParameter("status");
		if(curappid == null || curappid.equals("") || curappid.equals("0"))
		{
			//loadList(model, orderManager.queryPageList(apps));
			loadList(model, orderManager.queryPageList(null, status));
		}
		else
		{
			if(!IsIn(apps, curappid))
			{
				model.addAttribute("msg", "无此应用的查询权限，请联系管理员进行配置。");
				return APP_BASE_URL + "error";
			}
			loadList(model, orderManager.queryPageList(curappid, status));
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
