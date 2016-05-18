package com.walker.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.walker.infrastructure.utils.StringUtils;

/**
 * 自定义认证过滤器实现</p>
 * 注意：一旦配置了自己的认证过滤器，那么在springscurity中http节点中，配置</br>
 * 的默认跳转和失败url就不管用了。
 * @author shikeying
 *
 */
public class MySecurityAuthenticationProcessionFilter extends
		AbstractAuthenticationProcessingFilter {

	private static final String MSG_PARAMETER_NAME = "msg";
	
	/* 默认的认证失败页面 */
	private String defaultFailureUrl;
	
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}
	
	/* 默认成功页面 */
	private String defaultTargetUrl = "/index.do";

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}
	
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		assert (StringUtils.isNotEmpty(defaultTargetUrl));
		this.defaultTargetUrl = defaultTargetUrl;
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl(defaultTargetUrl);
		this.setAuthenticationSuccessHandler(successHandler);
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		assert (StringUtils.isNotEmpty(defaultFailureUrl));
		this.defaultFailureUrl = defaultFailureUrl;
		SimpleUrlAuthenticationFailureHandler failedHandler = new SimpleUrlAuthenticationFailureHandler();
		failedHandler.setDefaultFailureUrl(defaultFailureUrl);
		this.setAuthenticationFailureHandler(failedHandler);
	}

	public MySecurityAuthenticationProcessionFilter(){
		super("/j_spring_security_check");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("j_username");
		String passWord = request.getParameter("j_password");
		String validatePic = request.getParameter("j_captcha");
		this.logger.debug("-----username: " + userName + ", pass: " + passWord + ", j_captcha: " + validatePic);
		this.logger.debug("自定义认证 SessionId=" + request.getSession().getId() ) ;
		
		/** 认证前创建令牌 */
		MyUsernamePasswordToken authRequest = new MyUsernamePasswordToken(userName,passWord,validatePic, null);
		this.setDetails(request,authRequest);
		
		try{
			return getAuthenticationManager().authenticate(authRequest);
		} catch(AuthenticationException aex){
			request.setAttribute(MSG_PARAMETER_NAME, aex.getMessage());
			throw aex;
		}
	}

	/**
	 * 装载认证信息内容
	 * @param request  认证请求对象
	 * @param authRequest  认证信息存贮对象
	 */
	protected void setDetails(HttpServletRequest request,MyUsernamePasswordToken authRequest) {
		authRequest.setDetails(new WebAuthenticationDetails(request));
	}
	
//	@Override
//	public int getOrder() {
//		// TODO Auto-generated method stub
//		return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER;
//	}
	
	private boolean useHttps = false;
	public void setUseHttps(boolean useHttps)
	  {
	    this.useHttps = useHttps;
	  }
}
