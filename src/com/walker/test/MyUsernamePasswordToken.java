package com.walker.test;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * 具体说明：
 * 		自定义认证令牌（封装了用户认证全过程 【用户名 口令 授权信息 认证细节（IP sessionId）】）
 * <p>Title:MyUsernamePasswordToken.java</p>
 * <p>Description:</p>
 * <p>Company:</p>
 * <p>create time:Aug 14, 2007 4:22:32 PM</p>
 * @author shikeying
 * @version 1.0
 */
public class MyUsernamePasswordToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 信任状 */
	private Object credentials;
	
	/** 负责人 */
	private Object principal;
	
	/** 验证码 */
	private Object validatePic;

	public Object getValidatePic() {
		return validatePic;
	}

	/**
	 * 未经过认证处理的构造器 （仅保存用户名和口令）
	 * @param principal     用户名
	 * @param credentials   口令
	 */
	public MyUsernamePasswordToken(Object principal, Object credentials
			, String validatePic, Collection<GrantedAuthority> authorities){
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.validatePic = validatePic;
		
		/** 是否通过认证 未通过认证*/
		super.setAuthenticated(true);
	}
	
	/**
	 * 认证后的构造器（用户名 口令 授权信息集合 ）
	 * @param principal     负责人
	 * @param credentials   信任状
	 * @param authorities   角色授权集合
	 */
	public MyUsernamePasswordToken(Object principal, Object credentials, Collection<GrantedAuthority> authorities){
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		
		/** 经过认证处理 */
		super.setAuthenticated(true);
	}

	/**
	 * 
	 */
	public Object getCredentials() {
		return credentials;
	}

	/**
	 * 
	 */
	public Object getPrincipal() {
		return principal;
	}

	
}
