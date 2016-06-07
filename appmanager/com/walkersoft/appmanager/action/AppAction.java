package com.walkersoft.appmanager.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.appmanager.entity.AppEntity;
import com.walkersoft.appmanager.entity.AppGroup;
import com.walkersoft.appmanager.manager.AppManagerImpl;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.UserManagerImpl;
import com.walkersoft.system.pojo.SysOperator;
import com.walkersoft.system.pojo.UserGroup;

/**
 * 应用
 * @author a
 *
 */

@Controller
public class AppAction extends SystemAction {
	
	@Autowired
	private AppManagerImpl appManager;
	
	@Autowired
	private UserManagerImpl userManager;
	
	private static final String LOG_MSG_APPDEL = "删除应用";
	private static final String LOG_MSG_APPADD = "添加应用";
	private static final String LOG_MSG_APPEDIT = "编辑应用";
	
	private static final String LOG_MSG_USERAPP = "编辑用户的可见应用";
	
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
			systemLog(LOG_MSG_APPADD + entity.getAppname(), LogType.Add);
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
		loadList(model);
		//获取所有的应用
		//loadList(model, appManager.queryPageList(this.getCurrentUserId()));
		return USERAPP_BASE_URL + "index";
	}
	
	private void loadList(Model model){
		// 通过此方法来设置列表想显示每页多少条记录
//		ListPageContext.setCurrentPageSize(5);
		GenericPager<UserCoreEntity> pager = userManager.queryAllUser();
		PagerView<UserCoreEntity> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
		model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
	}
	
	/**
	 * 保存用户的应用
	 * @throws IOException
	 */
	@RequestMapping("appos/userapp/saveUserApp")
	public void saveUserApp(HttpServletResponse response) throws IOException{
		String userId = this.getParameter("userId");
		String appIds = this.getParameter("appIds");
		
		if(userId == null || userId.equals("")){
			this.ajaxOutPutText("<span>系统错误：用户ID不存在！请先选择一个角色。</span>");
		}
		if(appIds != null && appIds.startsWith(userId + ",")){
//			userIds = userIds.replace(roleId + ",", "");
			appIds = appIds.substring(userId.length()+1);
		}
		List<String> appList = new ArrayList<String>();
		
		String[] splitIds = appIds.split(",");
		for(String _id : splitIds){
			appList.add(_id);
		}
		try {
			appManager.execSaveUserApps(userId, appList);
			systemLog(LOG_MSG_USERAPP + appList.toString(), LogType.Edit);
		} catch (Exception e) {
			this.ajaxOutPutText("<span>系统错误：保存数据时，数据库操作失败！</span>");
			e.printStackTrace();
			return;
		}
		this.ajaxOutPutText("<span>用户应用保存成功！</span>");
	}
	
	@RequestMapping("permit/appos/userapp/getUserAppsHtml")
	public void getUserAppsHtml(HttpServletResponse response) throws IOException{
		StringBuffer showEditUsrHtml = new StringBuffer();
		
		//用户id
		String userId = this.getParameter("userId");
		if(userId == null || userId.equals("")){
			this.ajaxOutPutText("加载用户权限时，用户ID不存在！");
			return;
		}
		
		//该用户包含的应用
		List<String> usrAppList = null;
		try {
			usrAppList = appManager.queryUserAppList(userId);
		} catch (Exception e) {
			e.printStackTrace();
			this.ajaxOutPutText("<span>数据库操作失败！</span>");
		}
		
		//把传入的用户ID保存到隐藏域中
		showEditUsrHtml.append("<input type='hidden' id='userId' name='userId' value='" + userId + "'/>");
		
		//返回系统所有应用
		
		List<Map<String, Object>> allApps = appManager.queryAllAppList();
		if(allApps != null && allApps.size() > 0){
			showEditUsrHtml.append("<table border='0' cellpadding='0' cellspacing='0' width='600' class='table-form'>");
			
				int counter = 0;
				for(Map<String, Object> app : allApps){
					String appid = (String)app.get("appid");
					if(counter%3 == 0){
						//补上上一个的单元格
						if(showEditUsrHtml.toString().endsWith("</label>")){
							showEditUsrHtml.append("</td></tr>");
						}
						//说明该换行了
						showEditUsrHtml.append("<tr><td>");
					}
					showEditUsrHtml.append("<label style='width:70px'><input type='checkbox' id='");
					showEditUsrHtml.append(appid);
					showEditUsrHtml.append("' name='");
					showEditUsrHtml.append(appid);
					showEditUsrHtml.append("' value='");
					showEditUsrHtml.append(appid + "'");
					
					if(usrAppList != null && usrAppList.contains(appid)){
						showEditUsrHtml.append(" checked='true'");
					}
					showEditUsrHtml.append(">");
					showEditUsrHtml.append(app.get("appname"));
					showEditUsrHtml.append("</label>");
					counter ++;
				}
				showEditUsrHtml.append("</td></tr>");

			showEditUsrHtml.append("</table>");
		} else {
			showEditUsrHtml.append("<span>没有数据</span>");
		}
		this.ajaxOutPutText(showEditUsrHtml.toString());
	}
}
