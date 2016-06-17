package com.walkersoft.appmanager.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.AppMarketEntity;
import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.appmanager.manager.AppMarketManagerImpl;
import com.walkersoft.system.SystemAction;

/**
 * 应用
 * @author a
 *
 */

@Controller
public class AppMarketAction extends SystemAction {
	
	@Autowired
	private AppManagerImpl appManager;
	
	@Autowired
	private AppMarketManagerImpl appmarketManager;
	
	private static final String LOG_MSG_APPDEL = "删除应用渠道配置";
	private static final String LOG_MSG_APPADD = "添加应用渠道配置";
	private static final String LOG_MSG_APPEDIT = "编辑应用渠道配置";
	public static final String APP_BASE_URL = "appos/appmarket/";
	
	@RequestMapping("appos/appmarket/index")
	public String index(Model model){
		setUserPointers(model);
		loadList(model, appmarketManager.queryPageList(this.getCurrentUserId()));
		return APP_BASE_URL + "index";
	}
	
	@RequestMapping(value = "permit/appos/appmarket/reload")
	public String reloadPage(Model model){
		loadList(model, appmarketManager.queryPageList(this.getCurrentUserId()));
		return APP_BASE_URL + "list";
	}
	
	@RequestMapping("permit/appos/appmarket/showAddAppMarketItem")
	public String showAddAppItem(Model model){
		
		return APP_BASE_URL + "add";
	}
	
	@RequestMapping("permit/appos/appmarket/showUpdateAppMarketItem")
	public String showUpdateAppItem(String id, Model model){
		assert (StringUtils.isNotEmpty(id));
		
		AppMarketEntity app = appmarketManager.queryAppMarket(id);
		
		model.addAttribute("id", app.getId());
		model.addAttribute("app", app.getApp());
		model.addAttribute("market", app.getMarket());
		model.addAttribute("strategy_group", app.getStrategyGroup());
		model.addAttribute("create_time", app.getCreate_time());
		model.addAttribute("last_time", app.getLast_time());
		model.addAttribute("remark", app.getRemark());
		
		return APP_BASE_URL + "edit";
	}
	
	@RequestMapping("appos/appmarket/saveAppmarket")
	public void saveApp(AppMarketEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			appmarketManager.execSave(entity);
			systemLog(LOG_MSG_APPADD + entity.getApp().getAppid(), LogType.Delete);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/appmarket/updateAppmarket")
	public void updateApp(AppMarketEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			appmarketManager.execUpdate(entity);
			systemLog(LOG_MSG_APPADD + entity.getApp().getAppid(), LogType.Edit);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/appmarket/delAppmarket")
	public void delApp(String id, HttpServletResponse response) throws IOException{
		//String appid = appid;
		assert (StringUtils.isNotEmpty(id));
		try {
			if(id != null && !id.equals("") && !id.equals("null")){
				appmarketManager.execDeleteAppMarketInfo(id);
				systemLog(LOG_MSG_APPDEL + id, LogType.Delete);
				this.ajaxOutPutText(MESSAGE_SUCCESS);
			}else{
				this.ajaxOutPutText("删除数据失败,缺少参数：id = " + id);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.ajaxOutPutText("删除数据失败！");
			return;
		}
	}
}
