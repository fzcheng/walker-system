package com.walkersoft.appmanager.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.appmanager.action.base.ApposBaseAction;
import com.walkersoft.appmanager.entity.OrderEntity;
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
			loadList(model, orderManager.queryPageList(apps, null));
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
			loadList(model, orderManager.queryPageList(apps, status));
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

	@RequestMapping("appos/order/single")
	public String single(Model model){
		
		this.setUserApps(model);
		this.setStatuss(model);
		
		String cpOrderid = (String)this.getParameter("cpOrderid");
		String orderid = (String)this.getParameter("orderid");
		String payOrderid = (String)this.getParameter("payOrderid");
		if(StringUtils.isEmpty(cpOrderid) && StringUtils.isEmpty(orderid) && StringUtils.isEmpty(payOrderid))
		{
			//loadList(model, orderManager.queryPageList(apps));
			//loadList(model, orderManager.queryPageList(apps, null));
			loadList(model, null);
		}
		else
		{
			loadList(model, orderManager.queryPageList(cpOrderid, orderid, payOrderid));
		}
		
		setUserPointers(model);
		
		return APP_BASE_URL + "single";
	}
	
	@RequestMapping("permit/appos/order/singleReload")
	public String singleReload(Model model){
		
		this.setUserApps(model);
		this.setStatuss(model);
		
		String cpOrderid = (String)this.getParameter("cpOrderid");
		String orderid = (String)this.getParameter("orderid");
		String payOrderid = (String)this.getParameter("payOrderid");
		if(StringUtils.isEmpty(cpOrderid) && StringUtils.isEmpty(orderid) && StringUtils.isEmpty(payOrderid))
		{
			//loadList(model, orderManager.queryPageList(apps));
			//loadList(model, orderManager.queryPageList(apps, null));
			
			loadList(model, null);
		}
		else
		{
			loadList(model, orderManager.queryPageList(cpOrderid, orderid, payOrderid));
		}
		
		setUserPointers(model);
		
		return APP_BASE_URL + "list";
	}
	
	/**
	 * 重发通知
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("permit/appos/order/retransfer")
	public void retransfer(Model model, HttpServletResponse response){
		
		String id = (String)this.getParameter("id");
		if(StringUtils.isEmpty(id))
		{
			try {
				this.ajaxOutPutText("未选择订单");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			orderManager.dealReTranser(Integer.valueOf(id));
			try {
				this.ajaxOutPutText(MESSAGE_SUCCESS);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 订单详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("permit/appos/order/showOrderDetail")
	public String showOrderDetail(int id, Model model){
		//assert (StringUtils.isNotEmpty(id));
		this.setStatuss(model);
		
		OrderEntity order = orderManager.queryOrder(id);
		
		model.addAttribute("order", order);
		
		return APP_BASE_URL + "detail";
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
