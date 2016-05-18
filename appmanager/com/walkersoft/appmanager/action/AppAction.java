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
	
	@RequestMapping("appos/app/index")
	public String index(Model model){
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
		String appcode = "1234567890";
		model.addAttribute("appcode", appcode);
		return APP_BASE_URL + "app_add";
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
	
	@RequestMapping("appos/app/delApp")
	public void delApp(String appid, HttpServletResponse response) throws IOException{
		//String appid = appid;
		assert (StringUtils.isNotEmpty(appid));
		try {
			if(appid != null && !appid.equals("") && !appid.equals("null")){
				appManager.execDeleteAppInfo(appid);
				systemLog(LOG_MSG_APPDEL + appid, LogType.Delete);
				this.ajaxOutPutText(MESSAGE_SUCCESS);
			}else{
				this.ajaxOutPutText("删除数据失败,缺少参数：appid = " + appid);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.ajaxOutPutText("删除数据失败！");
			return;
		}
	}
}
