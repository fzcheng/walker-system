package com.walkersoft.application.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.RequestAwareContext;
import com.walker.web.jcaptcha.CaptchaServiceSingleton;
import com.walker.test.MyUsernamePasswordToken;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.MyApplicationContext;
import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.FunctionManagerImpl;
import com.walkersoft.system.pojo.FunctionGroup;
import com.walkersoft.system.util.FunctionUtils;

/**
 * 
 * 具体说明：
 * <p>Title:MyAuthenticationProvider.java</p>
 * @author 时克英
 * @version 1.0
 */
public class MyAuthenticationProvider implements AuthenticationProvider,InitializingBean, MessageSourceAware {

	protected Log logger = LogFactory.getLog(getClass());
	
	protected MessageSourceAccessor messages = null;
	
	private UserDetailsService userDetailService;
	
	public void setUserDetailService(UserDetailsService userDetailService) {
		this.userDetailService = userDetailService;
	}

	private PasswordEncoder passwordEncoder = null;

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
//	private SaltSource saltSource;
//	
//	public void setSaltSource(SaltSource saltSource) {
//		this.saltSource = saltSource;
//	}

	//菜单功能Manager，查询登录用户菜单权限信息
	@Autowired
	private FunctionManagerImpl funcManager = null;
	
	public void setFuncManager(FunctionManagerImpl funcManager) {
		this.funcManager = funcManager;
	}
	
	private FunctionCacheProvider functionCacheProvider;
	
	public void setFunctionCacheProvider(FunctionCacheProvider functionCacheProvider) {
		this.functionCacheProvider = functionCacheProvider;
	}

	/** 强制负责人为字符串 */
	private boolean forcePrincipalAsString = false;
	
	/* 密码是否加密，默认不加密 */
	private boolean encodePassword = false;
	  
	public void setEncodePassword(boolean encodePassword) {
		this.encodePassword = encodePassword;
	}
	
	/* 是否存在验证码 */
//	private boolean validateCode = false;
//	public void setValidateCode(boolean validateCode) {
//		this.validateCode = validateCode;
//	}
	
	private ImageCaptchaService jcaptchaService = CaptchaServiceSingleton.getInstance();

	public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
		this.jcaptchaService = jcaptchaService;
	}

	private static final BadCredentialsException ex = new BadCredentialsException("没有提供所需认证的账户");
	
	// 是否登录回调,默认不回调
	private boolean loginCallback = false;
	
	public void setLoginCallback(boolean loginCallback) {
		this.loginCallback = loginCallback;
	}

	/**
	 * 认证
	 * @param authentication 认证信息类
	 */
	@SuppressWarnings("unchecked")
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
//		System.out.println("进入用户认证...................");
        if (authentication.getPrincipal() == null) {
             /** 认证帐号不存在 */
             throw ex;
        }  
        
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken)authentication;
        
        WebAuthenticationDetails whd = (WebAuthenticationDetails)myToken.getDetails();
//        System.out.println("认证过程 SessionID=" + whd.getSessionId());
        
        //验证码
        boolean validateCode = MyApplicationConfig.isValidateCode();
        
        if(validateCode && jcaptchaService == null){
        	throw new Error("验证码服务包不存在: jcaptchaService == null");
//        	jcaptchaService = CaptchaServiceSingleton.getInstance();
        }
        if(validateCode){
        	try{
	        	if(!jcaptchaService.validateResponseForID(whd.getSessionId(), myToken.getValidatePic()))
	        		throw new BadCredentialsException("系统认证不通过，验证码错误！");
        	} catch(CaptchaServiceException ex){
        		throw new BadCredentialsException("无效session，验证码无法生成。");
        	}
        }
        
        //从数据库加载用户
        UserDetails userDetails = null;
        try {
        	userDetails = userDetailService.loadUserByUsername(myToken.getPrincipal().toString());
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("用户不存在: " + myToken.getPrincipal());
		}
		
        boolean flag = this.authenticationUser((String)myToken.getPrincipal()
        		,(String)myToken.getCredentials(), userDetails);
        
        if(!flag){
        	throw new BadCredentialsException("系统认证不通过,用户或口令不正确"); 
        }
        
        Object principalToReturn = userDetails;
        if (forcePrincipalAsString) {
            principalToReturn = userDetails.getUsername();
        }
        
//        /**
//         * 扩展角色
//         */
//        MyUserInfo myUserInfo = (MyUserInfo)user;   
//        /** 授权角色信息 */
//        GrantedAuthority[] grants = myUserInfo.getAuthorities();
//        /** 增加一个 */
//        GrantedAuthority[] expandGrants = new GrantedAuthority[grants.length + 1];    
//        /** 复制原有 */
//        System.arraycopy(grants,0,expandGrants,0,grants.length);        
//        /** 新增 all_well_known_role 角色 */
//        expandGrants[expandGrants.length-1] = new GrantedAuthorityImpl("all_well_known_role");     
//        /** 设置用户新的权限角色集合 */
//        myUserInfo.setGrantedAuthorities(expandGrants); 
        
        //自定义设置，设置用户功能权限
//        userFuncMap.clear();
        
        MyUserDetails myUserInfo = (MyUserDetails)userDetails;
        // 返回的结果是数组Object[2],第一个是菜单组，第二个是'用户功能'Map对象
        // 这是修复bug-20140228，原来使用全局变量userFuncMap，在并发情况中会产生问题。
//        List<FunctionGroup> fgLst = this.getUserMenugroupList(myUserInfo.getUserId(), myUserInfo.isSupervisor());
//        myUserInfo.setUserFuncGroup(fgLst);
//        myUserInfo.setUserFuncMap(userFuncMap);
        Object[] menuResult = this.getUserMenugroupList(myUserInfo.getUserId(), myUserInfo.isSupervisor());
        if(menuResult == null){
        	myUserInfo.setUserFuncGroup(null);
        	myUserInfo.setUserFuncMap(null);
        } else {
	        myUserInfo.setUserFuncGroup(menuResult[0] == null ? null : (List<FunctionGroup>)menuResult[0]);
	        myUserInfo.setUserFuncMap(menuResult[1] == null ? null : (Map<String, Map<String, String>>)menuResult[1]);
        }
		return createSuccessAuthentication(principalToReturn, authentication, myUserInfo);
	}

	/**
	 * 用户认证
	 * @param userName   用户名称
	 * @param passWord   用户密码
	 * @return
	 */
	private boolean authenticationUser(String userName,String passWord, UserDetails _user){
		if(_user != null){
//			Object salt = null;
//	        if (this.saltSource != null) {
//	            salt = this.saltSource.getSalt(userDetails);
//	        }
			logger.debug("+++++++++++ 登录用户: " + userName + ", 加密密码（盐值为loginId）= " + passwordEncoder.encodePassword(passWord, userName));
			if(userName.equalsIgnoreCase(_user.getUsername())){
				if(encodePassword && passwordEncoder != null){
					// 使用登录ID作为盐值
					return passwordEncoder.isPasswordValid(_user.getPassword()
							, passWord, _user.getUsername());
				} else {
					return passWord.equalsIgnoreCase(_user.getPassword());
				}
			}
		}
		return false;
	}	
	
	private static final String LOG_MSG_LOGIN = "用户登录";
	
    /**
     * 认证成功信息
     * @param principal       认证信息
     * @param authentication  认证对象
     * @param user            用户信息
     * @param appId           系统ID
     * @return  Authentication  认证对象
     */
    @SuppressWarnings("unchecked")
	protected Authentication createSuccessAuthentication(Object principal,
            Authentication authentication, UserDetails user) {
    	logger.debug("..........> user.getAuthorities() = " + user.getAuthorities());
    	/** 认证后重新创建令牌 */
    	MyUsernamePasswordToken result = new MyUsernamePasswordToken(principal, authentication.getCredentials(), (Collection<GrantedAuthority>)user.getAuthorities());
       
    	/** 设置细节 */
        result.setDetails(user);
        MyApplicationContext.systemLog(LOG_MSG_LOGIN, LogType.Login, user.getUsername());
        
        /* 设置用户界面风格 */
        UserCoreEntity userCoreEntity = ((MyUserDetails)user).getUserInfo();
        String style = userCoreEntity.getStyle();
        if(StringUtils.isEmpty(style)){
        	style = MyApplicationContext.STYLE_DEFAULT;
        }
        
        //TODO 20160509
        //RequestAwareContext.getCurrentRequest().getSession(false).setAttribute(MyApplicationContext.STYLE_SESSION_NAME, style);
//        logger.debug("-------------> session中设置了用户style: " + style);
        
        // 回调登录接口
        if(loginCallback){
        	SystemSecurityCallback callback = MyApplicationConfig.getSystemSecurityCallback();
        	if(callback == null){
        		throw new IllegalArgumentException("配置中设置了登录回调,但是没有配置回调实现.");
        	}
        	callback.afterLogin(userCoreEntity, RequestAwareContext.getCurrentRequest());
        }
        
        RequestAwareContext.clearCurrentRequest();
        
        return result;
    }
    
	/**
	 * 判断认证信息是否统一
	 * @param authentication 认证
	 */
	public boolean supports(Class<?> authentication) {
	     return (MyUsernamePasswordToken.class.isAssignableFrom(authentication));
	}
	
	/**
	 * 初始化
	 */
	public void afterPropertiesSet() throws Exception {}

	/**
	 * 设置资源束
	 * @param messageSource
	 */
	public void setMessageSource(MessageSource messageSource) {
		 this.messages = new MessageSourceAccessor(messageSource);
	}

	private Object[] getUserMenuResult(List<FunctionGroup> availableUserGroup
			, Map<String, Map<String, String>> userFuncMap){
		// 返回用户功能菜单所有东东，第一个是菜单组，第二个是'用户功能'Map对象
		Object[] result = new Object[2];
		result[0] = availableUserGroup;
		result[1] = userFuncMap;
		return result;
	}
	
	/**
	 * 返回用户可用权限菜单
	 * @param userId
	 * @return
	 * 时克英新加方法
	 */
	private Object[] getUserMenugroupList(String userId, boolean isSupervisor){
		//存放<功能ID, 功能点ID列表>
		Map<String, List<String>> existFuncMap = new HashMap<String, List<String>>();
		
		/* 存放带URL的子系统ID的集合，即：有些子系统是可以点击打开的，但不包含任何子菜单，如：CMS */
		/* 时克英 2014-12-29*/
		List<String> systemWithUrl = new ArrayList<String>(2);
		
		// 对于超极管理员单独处理
		if(isSupervisor){
//			systemWithUrl = functionCacheProvider.getSystemWithUrl();
			return getUserMenuResult(functionCacheProvider.combinUserFuncGroup(existFuncMap)
					, functionCacheProvider.getAllFunctionPointers());
		}
		
		//存放用户所有可用功能项，里面map是功能点id
		Map<String, Map<String, String>> userFuncMap = new HashMap<String, Map<String, String>>();
		
		try {
			List<Map<String, Object>> usrMenuList = funcManager.queryFuncListByUser(userId);
			if(usrMenuList == null) return null;
			for(Map<String, Object> _menu : usrMenuList){
				String funcId = _menu.get("FUNC_ITEM_ID").toString();
				if(_menu.get("F_TYPE").toString().equals("-1")){
					// 子系统带URL单独处理
					systemWithUrl.add(funcId);
					continue;
				}
				
				if(!existFuncMap.containsKey(funcId)){
					existFuncMap.put(funcId, new ArrayList<String>());
					//先放入空对象，如果存在功能点，在加入
					userFuncMap.put(funcId, new HashMap<String, String>());
//					System.out.println("=========== userFuncMap放入了：" + funcId);
				}
				if(_menu.get("POINTER") != null){
					String[] _ps = _menu.get("POINTER").toString().split(StringUtils.DEFAULT_SPLIT_SEPARATOR);
					for(String _p : _ps){
						List<String> points = existFuncMap.get(funcId);
						if(points != null){
							if(!points.contains(_p)){
								existFuncMap.get(funcId).add(_p);
							}
						} else {
							//Map中没有就创建一个放进去
							List<String> pl = new ArrayList<String>();
							pl.add(_p);
							existFuncMap.put(funcId, pl);
						}
						//把功能点放入功能项Map中，存在了就覆盖
//						System.out.println("+++++++++取出funcId：" + userFuncMap.get(funcId));
						userFuncMap.get(funcId).put(_p, _p);
					}
				}
			}
			// 处理带URL地址的子系统菜单
			List<FunctionGroup> userFuncGroups = functionCacheProvider.combinUserFuncGroup(existFuncMap);
			FunctionUtils.addSystemToDestFuncGroup(systemWithUrl, userFuncGroups);
			
			return getUserMenuResult(userFuncGroups, userFuncMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("功能加载错误：" + e.getMessage());
		}
	}
	
}
