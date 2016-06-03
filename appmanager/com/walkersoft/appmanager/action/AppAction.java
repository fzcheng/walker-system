package com.walkersoft.appmanager.action;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.system.SystemAction;

/**
 * 应用
 * @author a
 *
 */

@Controller
public class AppAction extends SystemAction {
	
	@Autowired
	private AppManagerImpl appManager;
	
	private static final String LOG_MSG_APPDEL = "删除应用";
	private static final String LOG_MSG_APPADD = "添加应用";
	private static final String LOG_MSG_APPEDIT = "编辑应用";
	public static final String APP_BASE_URL = "appos/app/";
	public static final String USERAPP_BASE_URL = "appos/userapp/";
	
	
	@RequestMapping("appos/app/index")
	public String index(Model model){
		setUserApps(model);
		setUserPointers(model);
		loadList(model, appManager.queryPageList(this.getCurrentUserId()));
		return APP_BASE_URL + "app_index";
	}
	
	@RequestMapping(value = "permit/appos/app/reload")
	public String reloadPage(Model model){
		loadList(model, appManager.queryPageList(this.getCurrentUserId()));
		return APP_BASE_URL + "app_list";
	}
	
	@RequestMapping("permit/appos/app/showAddAppItem")
	public String showAddAppItem(Model model){
		String appid = "yl" + NumberGenerator.getSequenceNumber();
		String appcode = "1234567890";
		
		model.addAttribute("appid", appid);
		model.addAttribute("appcode", appcode);
		return APP_BASE_URL + "app_add";
	}
	
	@RequestMapping("permit/appos/app/showUpdateAppItem")
	public String showUpdateAppItem(String id, Model model){
		assert (StringUtils.isNotEmpty(id));
		
		AppEntity app = appManager.queryApp(id);
		
		model.addAttribute("id", app.getId());
		model.addAttribute("appid", app.getAppid());
		model.addAttribute("appcode", app.getAppcode());
		model.addAttribute("appname", app.getAppname());
		model.addAttribute("package_name", app.getPackage_name());
		model.addAttribute("companyid", app.getCompanyid());
		
		model.addAttribute("wx_appid", app.getWx_appid());
		model.addAttribute("wx_parternerKey", app.getWx_parternerKey());
		model.addAttribute("wx_mch_id", app.getWx_mch_id());
		model.addAttribute("notify_url", app.getNotify_url());
		
		return APP_BASE_URL + "app_edit";
	}
	
	@RequestMapping("appos/app/saveApp")
	public void saveApp(AppEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			appManager.execSave(entity);
			systemLog(LOG_MSG_APPADD + entity.getAppname(), LogType.Delete);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/app/updateApp")
	public void updateApp(AppEntity entity, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		logger.debug(entity);
		try{
			appManager.execUpdate(entity);
			systemLog(LOG_MSG_APPADD + entity.getAppname(), LogType.Edit);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch(ApplicationRuntimeException ae){
			this.ajaxOutPutText(ae.getMessage());
		}
	}
	
	@RequestMapping("appos/app/delApp")
	public void delApp(String id, HttpServletResponse response) throws IOException{
		//String appid = appid;
		assert (StringUtils.isNotEmpty(id));
		try {
			if(id != null && !id.equals("") && !id.equals("null")){
				appManager.execDeleteAppInfo(id);
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
	
	
	@RequestMapping("appos/userapp/index")
	public String userApp(Model model){
		setUserApps(model);
		setUserPointers(model);
		
		//获取所有的用户
		loadList(model, appManager.queryPageList(this.getCurrentUserId()));
		return USERAPP_BASE_URL + "index";
	}
}
