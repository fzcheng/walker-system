package com.walkersoft.mobile.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.file.FileEngine.StoreType;
import com.walker.infrastructure.utils.Assert;
import com.walkersoft.mobile.entity.AppInfo;
import com.walkersoft.mobile.entity.AppUpdate;
import com.walkersoft.mobile.manager.AppManagerImpl;

/**
 * APP应用维护管理
 * @author shikeying
 * @date 2015-3-19
 *
 */
@Controller
public class AppListAction extends MobileAction {

	private static final String URL_INDEX = "mobile/app/app_index";
	private static final String URL_RELOAD = "mobile/app/app_list";
	private static final String URL_ADD = "mobile/app/app_add";
	
	private static final String URL_UPDATE_INDEX = "mobile/app/app_update_index";
	private static final String URL_UPDATE_RELOAD = "mobile/app/app_update_list";
	private static final String URL_UPDATE_ADD = "mobile/app/app_update_add";
	
	@Autowired
	private AppManagerImpl appManager;
	
	@RequestMapping("mobile/app/index")
	public String index(Model model){
		this.loadList(model, appManager.queryPageAppList());
		return URL_INDEX;
	}
	
	@RequestMapping(value = "permit/mobile/app/reload")
	public String reloadPage(Model model){
		this.loadList(model, appManager.queryPageAppList());
		return URL_RELOAD;
	}
	
	@RequestMapping("permit/mobile/app/show_add")
	public String showAddPage(Model model){
		return URL_ADD;
	}
	
	@RequestMapping("permit/mobile/app/save")
	public void save(@ModelAttribute("entity") AppInfo entity
			, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		Assert.hasText(entity.getId());
		if(appManager.existSameId(entity.getId())){
			this.ajaxOutPutText("存在相同ID的APP，请重新输入ID");
			return;
		}
		String result = appManager.execSaveAppInfo(entity, this.getUploadFiles("file", StoreType.FileSystem));
		if(result != null){
			this.ajaxOutPutText(result);
			return;
		}
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping("permit/mobile/app/remove")
	public void removeAppInfo(String appId, HttpServletResponse response) throws IOException{
		Assert.hasText(appId);
		appManager.execRemoveAppInfo(appId);
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/mobile/app/update_index")
	public String showAppUpdateList(Model model, String appId){
		Assert.hasText(appId);
		this.loadList(model, appManager.queryPageAppUpdateList(appId));
		model.addAttribute("appId", appId);
		return URL_UPDATE_INDEX;
	}
	
	@RequestMapping(value = "permit/mobile/app/reload_update")
	public String reloadUpdatePage(Model model, String appId){
		Assert.hasText(appId);
		this.loadList(model, appManager.queryPageAppUpdateList(appId));
		return URL_UPDATE_RELOAD;
	}
	
	@RequestMapping("permit/mobile/app/show_add_update")
	public String showAddUpdatePage(Model model, String appId){
		Assert.hasText(appId);
		AppInfo appInfo = appManager.queryAppInfo(appId);
		if(appInfo == null){
			throw new ApplicationRuntimeException();
		}
		model.addAttribute("entity", appInfo);
		return URL_UPDATE_ADD;
	}
	
	@RequestMapping("permit/mobile/app/save_update")
	public void saveUpdate(@ModelAttribute("entity") AppUpdate entity
			, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		Assert.hasText(entity.getAppId());
		String result = appManager.execSaveAppUpdate(entity, this.getUploadFiles("file", StoreType.FileSystem));
		if(result != null){
			this.ajaxOutPutText(result);
		} else
			this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
}
