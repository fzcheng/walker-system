package com.walkersoft.system.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.JarDeployer;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.security.SecurityRuntimeException;
import com.walker.security.SystemLogMan;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.UserCoreEntity;

@Controller
public class SecurityAction extends SystemAction {

	private static final String PAGE_LOGIN = "login";
	private static final String PAGE_ACCESS_DENIED = "access_denied";
	private static final String PAGE_TIMEOUT = "timeout";
	private static final String PAGE_SESSION_INVALID = "session_invalid";
	private static final String PAGE_NOT_FOUND = "page_not_found";
	private static final String PAGE_ERROR_500 = "error";
	private static final String PAGE_EXCEPTION = "exception";
	
	private static final String PAGE_ACTIVE_USER = "active_user";
	private static final String PAGE_DEPLOY = "deploy";
	
	private static final String NAME_TIP_MSG = "msg";
	private static final String NAME_TIP_ERROR = "用户名、密码或者验证码错误。";
	private static final String NAME_ERROR = "error";
	private static final String NAME_ACTIVE_USER_SIZE = "userSize";
	private static final String NAME_ACTIVE_USER_LIST = "userList";
	
	/* 是否使用验证码 */
	private static final String NAME_USER_VALIDATE_CODE = "useValidateCode";
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@RequestMapping("/login")
	public String login(Model model){
//		try{
//			SystemLogMan.getInstance().checkMan();
//		} catch(SecurityRuntimeException ex){
//			return "lisence";
//		}
		
		// 检查是否已经部署
		//if(!JarDeployer.getDeployedStatus()){
		//	return doShowDeployPage(model, true);
		//}
		
		model.addAttribute(NAME_USER_VALIDATE_CODE, MyApplicationConfig.isValidateCode());
		String error = this.getParameter(NAME_ERROR);
		if(StringUtils.isNotEmpty(error)){
			model.addAttribute(NAME_TIP_MSG, NAME_TIP_ERROR);
			return PAGE_LOGIN;
		}
		String msg = this.getParameter(NAME_TIP_MSG);
		if(StringUtils.isNotEmpty(msg)){
			model.addAttribute(NAME_TIP_MSG, msg);
		} else
			model.addAttribute(NAME_TIP_MSG, "");
		return PAGE_LOGIN;
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied(){
		systemLog("用户试图访问未授权的页面");
		return PAGE_ACCESS_DENIED;
	}
	
	@RequestMapping("/timeout")
	public String timeout(){
		return PAGE_TIMEOUT;
	}
	
	@RequestMapping("/sessionInvalid")
	public String sessionInvalid(){
		return PAGE_SESSION_INVALID;
	}
	
	@RequestMapping("/notFound")
	public String notFound(){
		return PAGE_NOT_FOUND;
	}
	
	@RequestMapping("/error")
	public String error(){
		return PAGE_ERROR_500;
	}
	
	@RequestMapping("/exception")
	public String exception(HttpServletRequest request, Model model){
		Exception ex = (Exception)request.getAttribute("Exception");
		String exMsg = ex == null ? "no exception" : ex.getMessage();
		model.addAttribute("exception", exMsg);
		return PAGE_EXCEPTION;
	}
	
	/**
	 * 认证授权页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/reg_auth")
	public String regAuthen(HttpServletRequest request, Model model){
		model.addAttribute("reg_exp", request.getAttribute("lic_exp"));
		return "lisence";
	}
	
	private static final int MAX_ACTIVE_USER_SHOW = 50;
	
	/**
	 * 返回当前在线用户列表，最大显示50个
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/activeuser/index")
	public String showActiveUsers(Model model){
		Map<UserCoreEntity,Date> lastActivityDates = new HashMap<UserCoreEntity, Date>();
		UserCoreEntity user = null;
		int count = 1;
		for(Object principal: sessionRegistry.getAllPrincipals()){
			if(count >= MAX_ACTIVE_USER_SHOW) break;
			user = ((MyUserDetails)principal).getUserInfo();
			for(SessionInformation session : sessionRegistry.getAllSessions(principal, false)){
				if(lastActivityDates.get(user) == null){
					lastActivityDates.put(user, session.getLastRequest());
				} else {
					Date prevLastRequest = lastActivityDates.get(principal);
					if(prevLastRequest != null && session.getLastRequest().after(prevLastRequest)){
						lastActivityDates.put(user, session.getLastRequest());
					}
				}
			}
			count ++;
		}
		model.addAttribute(NAME_ACTIVE_USER_SIZE, sessionRegistry.getAllPrincipals().size());
		model.addAttribute(NAME_ACTIVE_USER_LIST, lastActivityDates);
//		logger.debug(lastActivityDates);
		return BASE_URL + PAGE_ACTIVE_USER;
	}
	
	/**
	 * 显示部署检测界面，管理后台使用
	 * @param model
	 * @return
	 */
	@RequestMapping("/supervisor/deploy")
	public String showDeployPage(Model model){
		return doShowDeployPage(model, false);
	}
	private String doShowDeployPage(Model model, boolean editable){
		model.addAttribute("deployedMap", JarDeployer.DEPLOYED_FILES);
		model.addAttribute("waitList", JarDeployer.DEPLOY_WAIT_FILES);
		model.addAttribute("editable", editable);
		return PAGE_DEPLOY;
	}
	
	/**
	 * 更新部署的jar，公开的URL
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update_deploy")
	public synchronized void updateDeployJars(HttpServletResponse response) throws IOException{
		if(JarDeployer.getDeployedStatus()){
			throw new IllegalStateException("应用程序部署完毕，无法重复执行!");
		}
//		List<File> files = JarDeployer.DEPLOY_WAIT_FILES;
//		for(File f : files){
//			JarDeployer jarDeployer = JarDeployer.getWebappLibInstance(f.getName());
//			jarDeployer.deploy();
//			JarDeployer.DEPLOYED_FILES.put(f.getName(), System.currentTimeMillis());
//		}
		try {
			JarDeployer.updateDeployedJarTimestamp();
		} catch (Exception e) {
			throw new ApplicationRuntimeException("部署应用程序包出现异常", e);
		}
		
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	public static void main(String[] args){
		System.out.println("------> " + new Date().toString());
	}
}
