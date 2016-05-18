package com.walkersoft.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.UserManagerImpl;

public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserManagerImpl userManager;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserCoreEntity user = userManager.queryUserByLoginId(username);
		if(user == null)
			throw new UsernameNotFoundException("not found user: " + username);
		
		// 被禁用的用户也不能登录
		if(!user.enabled())
			throw new UsernameNotFoundException("user is forbidden: " + username);
		
		MyUserDetails userDetails = new MyUserDetails(user);
		
		/* 设置用户的角色类型 */
		if(user.getType() == UserCoreEntity.TYPE_SUPER){
			userDetails.addGrantedAuthority(MyUserDetails.ROLE_SUPER_ADMIN);
			userDetails.addGrantedAuthority(MyUserDetails.ROLE_ADMIN);
			userDetails.addGrantedAuthority(MyUserDetails.ROLE_USER);
			
		} else if(user.getType() == UserCoreEntity.TYPE_ADMIN){
			userDetails.addGrantedAuthority(MyUserDetails.ROLE_ADMIN);
			userDetails.addGrantedAuthority(MyUserDetails.ROLE_USER);
			
		} else if(user.getType() == UserCoreEntity.TYPE_NORMAL){
			userDetails.addGrantedAuthority(MyUserDetails.ROLE_USER);
			
		} else
			throw new IllegalArgumentException("unknown user type: " + user.getType());
		
		/* 加入用户自定义的角色 */
		if(user.getRoleIds() != null){
			for(String s : user.getRoleIds()){
				userDetails.addGrantedAuthority(s);
			}
		}
		
		return userDetails;
	}

}
