package com.walkersoft.application;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;

import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.view.WebContextAction;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.application.util.DepartmentUtils;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.pojo.AppGroup;

/**
 * 支持<code>Spring MVC</code>的控制器实现
 * @author shikeying
 *
 */
public class SpringControllerSupport extends WebContextAction {
	
	protected static final Log logger = LogFactory.getLog(SpringControllerSupport.class);

	/**
	 * 定义系统响应成功标志，浏览器端使用
	 */
	public static final String MESSAGE_SUCCESS = "success";
	
	/**
	 * 定义系统响应失败标志，浏览器端使用
	 */
	public static final String MESSAGE_ERROR = "error";
	
//	protected void setResponseXmlConfig(){
//		HttpServletResponse response = getResponse();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType(ResponseFormat.APPLICATION_XML);
//		System.out.println("thread binded response = " + response);
//	}
	
	protected String getCurrentUserId(){
		return getCurrentUser().getId();
	}
	
	/**
	 * 得到系统当前登录用户基本信息
	 * @return
	 */
	protected UserCoreEntity getCurrentUser(){
		return MyApplicationConfig.getCurrentUser();
	}
	
	protected MyUserDetails getCurrentUserDetails(){
		return MyApplicationConfig.getCurrentUserDetails();
	}
	
	/**
	 * 返回用户类型
	 * @return 枚举类型
	 */
	protected UserType getCurrentUserType(){
		return DepartmentUtils.getCurrentUserType();
//		UserCoreEntity u = getCurrentUser();
//		if(u.isSupervisor())
//			return UserType.SuperVisor;
//		else if(u.isAdmin())
//			return UserType.Administrator;
//		else
//			return UserType.User;
	}
	
	/**
	 * 写入系统日志，日志类型默认为'一般操作'
	 * @param content 日志内容
	 */
	protected void systemLog(String content){
		MyApplicationContext.systemLog(content);
	}
	
	/**
	 * 写入系统日志
	 * @param content 日志内容
	 * @param type 日志类型
	 */
	protected void systemLog(String content, LogType type){
		MyApplicationContext.systemLog(content, type);
	}
	
	/**
	 * 手动刷新日志引擎，把里面缓存的日志立即持久化。
	 */
	protected void flushSystemLog(){
		MyApplicationContext.flushSystemLog();
	}
	
	/** 功能项ID参数名字 */
	private static final String PARAMETER_NAME_FID = "fid";
	private static final String NAME_POINTER_MAP = "pointers";
	
	/**
	 * 把用户当前进入的菜单功能中的'功能点'权限信息写入响应中，供模板页面处理对应按钮。</br>
	 * 该方法由各个action中功能首页的请求来调用，如：<code>UserAction</code>中的index.do
	 * @param model
	 */
	protected void setUserPointers(Model model){
		String fid = this.getParameter(PARAMETER_NAME_FID);
		if(StringUtils.isEmpty(fid)){
			logger.debug("无法找到菜单链接传入的'fid'参数。");
			return;
		}
		Map<String, String> pointers = getCurrentUserDetails().getUserFuncPointerMap(fid);
		if(pointers != null){
			model.addAttribute(NAME_POINTER_MAP, pointers);
		} else
			logger.debug("该功能没有配置任何功能点权限或者不存在权限: " + fid);
	}
	
	private static final String PARAMETER_NAME_APPID = "appid";
	private static final String NAME_APPOPTION_MAP = "userappoptions";
	/**
	 * 把用户当前可看的的‘应用’的操作权限信息写入响应中，供模板页面处理对应按钮。</br>
	 * 该方法由各个action中功能首页的请求来调用，如：<code>UserAction</code>中的index.do
	 * @param model
	 */
	protected void setUserAppOptions(Model model){
		String appid = this.getParameter(PARAMETER_NAME_APPID);
		if(StringUtils.isEmpty(appid)){
			logger.debug("无法找到菜单链接传入的'appid'参数。");
			return;
		}
		
		MyUserDetails userDetails = getCurrentUserDetails();
		List<AppGroup> appgroup = userDetails.getUserAppGroup();
		
		Map<String, String> apps = getCurrentUserDetails().getUserAppOptionMap(appid);
		if(apps != null){
			model.addAttribute(NAME_APPOPTION_MAP, apps);
		} else
			logger.debug("该功能没有配置任何功能点权限或者不存在权限: " + appid);
	}
	
	private static final String NAME_APP_MAP = "userapps";
	/**
	 * 把用户当前可看的的‘应用’的列表权限信息写入响应中，供模板页面处理对应按钮。</br>
	 * 该方法由各个action中功能首页的请求来调用，如：<code>UserAction</code>中的index.do
	 * @param model
	 */
	protected void setUserApps(Model model){
		
		MyUserDetails userDetails = getCurrentUserDetails();
		List<AppGroup> appgroup = userDetails.getUserAppGroup();
		
		if(appgroup != null){
			model.addAttribute(NAME_APP_MAP, appgroup);
		} else
			logger.debug("该用户没有配置任何应用权限或者不存在权限: " + userDetails.getUsername());
	}
}
