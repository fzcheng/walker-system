package com.walkersoft.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.walker.fastlog.DataBatchInput;
import com.walker.file.FileUtils;
import com.walker.infrastructure.arguments.ArgumentsManager;
import com.walker.infrastructure.arguments.ArgumentsManagerAware;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.application.security.SystemSecurityCallback;
import com.walkersoft.system.callback.SystemUserCallback;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 应用系统参数配置调用服务</p>
 * 这由业务功能来决定使用哪些参数，读出这些参数后，固话到变量中，提高访问效率。
 * @author shikeying
 * @date 2013-11-12
 *
 */
public final class MyApplicationConfig implements ApplicationContextAware, ArgumentsManagerAware {

	private static Log logger = LogFactory.getLog(MyApplicationConfig.class);
	
	private static ApplicationContext springContext;
	
	private static DataBatchInput logEngine;
	
	public static final DataBatchInput getLogEngine() {
		return logEngine;
	}

	public void setLogEngine(DataBatchInput logEngine){
		assert (logEngine != null);
		MyApplicationConfig.logEngine = logEngine;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 设置超级管理员
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private static PasswordEncoder encoder;
	
	public void setPasswordEncoder(PasswordEncoder encoder){
		assert (encoder != null);
		MyApplicationConfig.encoder = encoder;
	}
	
	private static String supervisorName = null;
	
	private static UserCoreEntity supervisorUser = null;
	
	/**
	 * 返回系统超级管理员信息，</br>
	 * 每次登陆都要检查超级管理员密码，因为可以配置其密码。
	 * @return
	 */
	public static final UserCoreEntity getSupervisor(){
		if(supervisorUser == null){
			supervisorUser = new UserCoreEntity();
			supervisorUser.setId("0");
			supervisorUser.setLoginId(supervisorName);
			supervisorUser.setName("超级管理员");
			supervisorUser.setPassword(encodePassword(getSupervisorPassword(), supervisorName));
			supervisorUser.setType(UserCoreEntity.TYPE_SUPER);
		} else {
			supervisorUser.setPassword(encodePassword(getSupervisorPassword(), supervisorName));
		}
		return supervisorUser;
	}
	
	/**
	 * 返回超级管理员名称
	 * @return
	 */
	public static final String getSupervisorName() {
		return supervisorName;
	}
	
	public void setSupervisorName(String supervisorName) {
		assert (StringUtils.isNotEmpty(supervisorName));
		if(MyApplicationConfig.supervisorName != null)
			throw new IllegalStateException("supervisor name is already exist!");
		MyApplicationConfig.supervisorName = supervisorName;
	}

	/**
	 * 对原始字符串进行加密处理
	 * @param rawPass 原始字符串
	 * @param salt 盐值，可以为空
	 * @return
	 */
	public static final String encodePassword(String rawPass, String salt){
		assert (StringUtils.isNotEmpty(rawPass));
		if(MyApplicationConfig.isPasswordEncode()){
			return encoder.encodePassword(rawPass, salt);
		} else
			return rawPass;
	}
	
	private static int defaultPageSize = 15;
	
	public static int getDefaultPageSize() {
		return defaultPageSize;
	}
	public synchronized void setDefaultPageSize(int defaultPageSize) {
		if(defaultPageSize <=0 || defaultPageSize > Integer.MAX_VALUE)
			return;
		MyApplicationConfig.defaultPageSize = defaultPageSize;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 以下参数是在应用配置文件中的，app_config.properties</br>
	// 这些参数是初始化参数，不能在系统运行时修改的，在这里单独处理
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	private static boolean passwordEncode = false;

	public void setPasswordEncode(boolean passwordEncode) {
		MyApplicationConfig.passwordEncode = passwordEncode;
	}

	/**
	 * 系统是否启用验证码，如果是返回<code>true</code>
	 * @return
	 */
	public static final boolean isValidateCode(){
		return defaultArgumentManager.getVariable(MyArgumentsNames.SystemShowValidCode.getName()).getBooleanValue();
	}
	
	/**
	 * 系统是否启用密码加密，如果启用返回<code>true</code>，
	 * 这意味着用户保存密码和登录验证同时启用。
	 * @return
	 */
	public static final boolean isPasswordEncode(){
		return passwordEncode;
	}
	
	/**
	 * 给定的登录ID是否超级管理员ID，是返回<code>true</code>
	 * @param loginId
	 * @return
	 */
	public static final boolean isSupervisor(String loginId){
		assert (StringUtils.isNotEmpty(loginId));
		return loginId.equalsIgnoreCase(supervisorName);
	}
	
	public static final MyUserDetails getCurrentUserDetails(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null){
			logger.warn("用户未认证，无法执行当前操作，获取登录用户失败。");
//			try {
//				this.getResponse().sendRedirect(this.getContextPath()+"/login.do");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new RuntimeException("go to login failed!", e);
//			}
//			return null;
			throw new RuntimeException("Authentication not found!");
		}
		return (MyUserDetails)auth.getDetails();
	}
	
	/**
	 * 得到系统当前登录用户基本信息
	 * @return
	 */
	public static final UserCoreEntity getCurrentUser(){
		return getCurrentUserDetails().getUserInfo();
	}
	
	/**
	 * 执行事件，更新<code>spring</code>容器环境
	 * @param event
	 */
	public static final void publishEvent(ApplicationEvent event){
		assert (event != null);
		springContext.publishEvent(event);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		springContext = applicationContext;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 系统配置参数，这些参数存在于配置文件中：classpath:app_variables.xml
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
//	public static final String DEFAULT_CONFIG_FILENAME = "app_variables.xml";
	
	private static ArgumentsManager defaultArgumentManager;

//	public void setArgumentsManager(ArgumentsManager argumentsManager){
//		assert (argumentsManager != null);
//		MyApplicationConfig.defaultArgumentManager = argumentsManager;
//	}
	
	/**
	 * 返回系统初始化密码值
	 * @return
	 */
	public static final String getInitPassword() {
		return defaultArgumentManager.getVariable(MyArgumentsNames.SystemUserInitPass.getName()).getStringValue();
	}
	
	/**
	 * 返回重置密码的默认值
	 * @return
	 */
	public static String getDefaultResetPass(){
		return defaultArgumentManager.getVariable(MyArgumentsNames.SystemUserResetPass.getName()).getStringValue();
	}

	/**
	 * 返回超级管理员密码
	 * @return
	 */
	public static final String getSupervisorPassword() {
		logger.debug("defaultArgumentManager = " + defaultArgumentManager);
		return defaultArgumentManager.getVariable(MyArgumentsNames.SystemSuperPass.getName()).getStringValue();
	}
	
	/**
	 * 返回上传附件的存储根路径
	 * @return
	 */
	public static final String getAttachRootPathWindows() {
		if(FileUtils.isWindows()){
			return defaultArgumentManager.getVariable(MyArgumentsNames.AttachRootPathWin.getName()).getStringValue();
		} else
			return defaultArgumentManager.getVariable(MyArgumentsNames.AttachRootPathUnix.getName()).getStringValue();
	}
	
	public static final <T> T getBeanObject(Class<T> type){
		return springContext.getBean(type);
	}
	public static final <T> Map<String, T> getBeanObjectMap(Class<T> type){
		return springContext.getBeansOfType(type);
	}
	
	/**
	 * 返回给定类型的bean集合
	 * @param type
	 * @return
	 */
	public static final <T> List<T> getBeanObjectList(Class<T> type){
		Map<String, T> map = springContext.getBeansOfType(type);
		if(map == null || map.size() == 0) return null;
		return new ArrayList<T>(map.values());
	}
	
	public static final Object getBeanObject(String beanId){
		return springContext.getBean(beanId);
	}

	@Override
	public void setArgumentManager(ArgumentsManager argumentsManager) {
		assert (argumentsManager != null);
		MyApplicationConfig.defaultArgumentManager = argumentsManager;
	}
	
	private static SystemUserCallback systemUserCallback = null;
	private static SystemSecurityCallback systemSecurityCallback = null;

	/**
	 * 获取用户管理的回调接口，如果系统配置了就返回该对象，如果没有返回<code>null</code>
	 * @return
	 * @date 2014-11-25
	 */
	public static final SystemUserCallback getSystemUserCallback() {
		if(systemUserCallback == null){
			systemUserCallback = ApplicationCallbackPostProcessor.getCallbackObject(SystemUserCallback.class);
		}
		return systemUserCallback;
	}
	/**
	 * 获取系统安全的回调接口，如果系统配置了就返回该对象，如果没有返回<code>null</code>
	 * @return
	 * @date 2014-11-25
	 */
	public static final SystemSecurityCallback getSystemSecurityCallback() {
		if(systemSecurityCallback == null){
			systemSecurityCallback = ApplicationCallbackPostProcessor.getCallbackObject(SystemSecurityCallback.class);
		}
		return systemSecurityCallback;
	}
}
