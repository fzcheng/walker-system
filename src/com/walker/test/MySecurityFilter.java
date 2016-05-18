package com.walker.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class MySecurityFilter extends AbstractSecurityInterceptor implements
		Filter {

	private FilterInvocationSecurityMetadataSource securityMetadataSource; 
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		FilterInvocation fi = new  FilterInvocation(request, response, chain);    
        invoke(fi); 
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getSecureObjectClass() {
		// TODO Auto-generated method stub
		return FilterInvocation.class ;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		// TODO Auto-generated method stub
		return this.securityMetadataSource;
	}

	private void invoke(FilterInvocation fi)  throws  IOException, ServletException {    
        // object为FilterInvocation对象     
                  //super.beforeInvocation(fi);源码     
        //1.获取请求资源的权限     
        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);     
        //2.是否拥有权限     
        //this.accessDecisionManager.decide(authenticated, object, attributes);     
        InterceptorStatusToken token = super.beforeInvocation(fi);    
        try  {    
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());   
        } finally  {    
            super .afterInvocation(token,  null );    
        }   
    }

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {    
        this.securityMetadataSource = securityMetadataSource;    
    } 

}
