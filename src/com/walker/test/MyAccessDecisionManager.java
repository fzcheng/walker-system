package com.walker.test;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 请求拦截确定管理器-自定义实现</p>
 * 根据请求的URL查找具有的合法角色集合，如果未找到抛出异常。
 * @author shikeying
 * @throws AccessDeniedException
 *
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	private Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void decide(Authentication authentication, Object url,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		if(configAttributes == null)
			return;
//		logger.debug("......拦截的url: " + url);
		Iterator<ConfigAttribute> ite=configAttributes.iterator();
		ConfigAttribute ca = null;
		String needRole = null;
		while(ite.hasNext()){
			ca = ite.next();
			needRole = ((SecurityConfig)ca).getAttribute();
			for(GrantedAuthority ga : authentication.getAuthorities()){
				if(needRole.equals(ga.getAuthority())){
					logger.debug("......找到了匹配的角色: " + needRole);
					return;
				}
			}
		}
		logger.debug("xxxxxxxxxxxxx 未找到匹配角色，needRole = " + needRole + ", url = " + url);
		throw new AccessDeniedException("you can't access this resource: " + url);
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
