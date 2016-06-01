package com.walkersoft.application.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.pojo.AppGroup;
import com.walkersoft.system.pojo.FunctionGroup;

public class MyUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1888411449109471070L;

	private String username;
	private String password;
	
	private UserCoreEntity user;
	
	private List<GrantedAuthority> grantedList = new ArrayList<GrantedAuthority>(2);
	
	public MyUserDetails(UserCoreEntity user){
		assert (user != null);
		this.user = user;
		this.username = user.getLoginId();
		this.password = user.getPassword();
	}
	
	public UserCoreEntity getUserInfo(){
		return this.user;
	}
	
	public String getUserId() {
		return this.user.getId();
	}

	public String getUserShowName() {
		return user.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.grantedList;
	}

	@Override
	public String getUsername() {
		return user.getLoginId();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.enabled();
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
	public boolean isSupervisor(){
		return user.isSupervisor();
	}

	public void addGrantedAuthority(String roleName){
//		if(roleName != null 
//				&& (roleName.equals(ROLE_SUPER_ADMIN) || roleName.equals(ROLE_ADMIN) 
//						|| roleName.equals(ROLE_USER))){
//			this.grantedList.add(gas.get(roleName));
//		}
		if(StringUtils.isNotEmpty(roleName)){
			GrantedAuthority ga = gas.get(roleName);
			if(ga == null){
				ga = new SimpleGrantedAuthority(roleName);
				gas.put(roleName, ga);
			}
			this.grantedList.add(ga);
		}
	}
	
	public static final String ROLE_SUPER_ADMIN = "ROLE_SUPERVISOR";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	
	private static final Map<String, GrantedAuthority> gas = new HashMap<String, GrantedAuthority>(3);
	static {
		gas.put(ROLE_SUPER_ADMIN, new SimpleGrantedAuthority(ROLE_SUPER_ADMIN));
		gas.put(ROLE_ADMIN, new SimpleGrantedAuthority(ROLE_ADMIN));
		gas.put(ROLE_USER, new SimpleGrantedAuthority(ROLE_USER));
	}
	
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(o instanceof MyUserDetails){
			MyUserDetails _o = (MyUserDetails)o;
			if(_o.getUsername().equals(this.username) 
					&& _o.getPassword().equals(this.password) 
					&& _o.isEnabled() == this.isEnabled()){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){
		int result = 31;
		return result + 31*user.getLoginId().hashCode() 
				+ user.getPassword().hashCode() + 31*(user.enabled() ? 1 : 0);
	}
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 用户功能权限信息，移植老代码（SSHDEMO）
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	private List<FunctionGroup> funcGroup;
	
	private Map<String, Map<String, String>> userFuncMap;
	
	public List<FunctionGroup> getUserFuncGroup() {
		return this.funcGroup;
	}

	public void setUserFuncGroup(List<FunctionGroup> funcGroup) {
		this.funcGroup = funcGroup;
	}
	
	/**
	 * 返回登录用户可用的功能点ID集合
	 * @param funcId 给定功能ID
	 * @return
	 */
	public Map<String, String> getUserFuncPointerMap(String funcId) {
		Map<String, String> pointerMap = this.userFuncMap.get(funcId);
		if(pointerMap != null){
			return pointerMap;
		}
		return null;
	}

	public void setUserFuncMap(Map<String, Map<String, String>> userFuncMap) {
		this.userFuncMap = userFuncMap;
	}
	
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 用户功能权限信息，移植老代码（SSHDEMO）
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	private List<AppGroup> appGroup;
	private Map<String, Map<String, String>> userAppMap;

	public List<AppGroup> getUserAppGroup() {
		return appGroup;
	}

	public void setUserAppGroup(List<AppGroup> appGroup) {
		this.appGroup = appGroup;
	}

	public Map<String, Map<String, String>> getUserAppMap() {
		return userAppMap;
	}

	public void setUserAppMap(Map<String, Map<String, String>> userAppMap) {
		this.userAppMap = userAppMap;
	}
	
	/**
	 * 返回登录用户可用的app options集合
	 * @param funcId 给定功能ID
	 * @return
	 */
	public Map<String, String> getUserAppOptionMap(String appid) {
		Map<String, String> optionMap = this.userAppMap.get(appid);
		if(optionMap != null){
			return optionMap;
		}
		return null;
	}
}
