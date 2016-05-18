package com.walkersoft.application.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.walker.infrastructure.utils.StringUtils;
import com.walker.web.security.ResourceLoadProvider;
import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.system.manager.RoleManagerImpl;
import com.walkersoft.system.pojo.RoleFunction;

/**
 * 应用程序资源定义加载器的默认实现。</p>
 * 改加载器接口用于从<code>SpringSecurity</code>中加载自己的角色、URL等资源对应关系。<br>
 * 在角色修改过功能权限，或者新加入角色时需要更新系统的资源对应关系，通过改接口来更新<code>SpringSecurity</code>的环境。
 * @author shikeying
 *
 */
public class ApplicationResourceLoader implements ResourceLoadProvider {

	private Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private RoleManagerImpl roleManager;
	
	private FunctionCacheProvider functionCacheProvider;
	
	/**
	 * URL与ROLE对应关系Map：key=url, value=roleId集合
	 */
	private Map<String, List<String>> urlRoleMap = new HashMap<String, List<String>>();
	
//	private static final ConfigAttribute supervisorCA = new SecurityConfig(MyUserDetails.ROLE_SUPER_ADMIN);
	
	@Override
	public Map<String, Collection<ConfigAttribute>> loadResource() {
		// TODO Auto-generated method stub
		urlRoleMap.clear();
		
		addSupervisorRoles();
		
		List<RoleFunction> list = roleManager.queryRoleFunctionList();
		if(list != null){
			for(RoleFunction rf : list){
				addRoleFunction(rf);
			}
		}
		
		Map<String, Collection<ConfigAttribute>> resultMap = new HashMap<String, Collection<ConfigAttribute>>();
		
		/* 加工成要返回的对象集合 */
		if(urlRoleMap.size() > 0){
			List<ConfigAttribute> caList = null;
			for(Map.Entry<String, List<String>> entry : urlRoleMap.entrySet()){
				caList = new ArrayList<ConfigAttribute>(entry.getValue().size()+1);
				for(String s : entry.getValue()){
					ConfigAttribute ca = new SecurityConfig(s);
					caList.add(ca);
				}
				// 每个URL都加上对超级管理员的支持
//				caList.add(supervisorCA);
				resultMap.put(entry.getKey(), caList);
			}
//			return resultMap;
		}
		
		Map<String, Collection<ConfigAttribute>> defaultAttributes = getDefaultResourceList();
		if(defaultAttributes != null){
			resultMap.putAll(defaultAttributes);
		}
		logger.debug("加载的资源权限: " + urlRoleMap);
		logger.debug("组装好的对象     : " + resultMap);
		return resultMap;
	}
	
	/**
	 * 把所有URL和超级管理员角色加入到Map中
	 */
	private void addSupervisorRoles(){
		List<String> itemUrl = functionCacheProvider.getFunctionItemsUrl();
		for(String url : itemUrl){
			setUrlRoleMap(url, MyUserDetails.ROLE_SUPER_ADMIN);
		}
//		setUrlRoleMap("/supervisor/db/export.do*", MyUserDetails.ROLE_SUPER_ADMIN);
//		setUrlRoleMap("/supervisor/db/import.do*", MyUserDetails.ROLE_SUPER_ADMIN);
		setUrlRoleMap("/supervisor/**", MyUserDetails.ROLE_SUPER_ADMIN);
	}

	private void addRoleFunction(RoleFunction rf){
		assert (rf != null);
		String _url = functionCacheProvider.getFunctionUrl(rf.getFunctionId());
		if(StringUtils.isEmpty(_url)){
			throw new NullPointerException("url is not null! rf = " + rf);
		}
		setUrlRoleMap(_url, rf.getRoleId());
		
		/* 添加功能点的URL和ROLE */
		addPointerUrl(rf.getPointerList(), rf.getRoleId());
	}
	
	private void addPointerUrl(List<String> pointerIds, String roleId){
		if(pointerIds == null) return;
		String _url = null;
		for(String pointerId : pointerIds){
			_url = functionCacheProvider.getPointerUrl(pointerId);
			if(StringUtils.isEmpty(_url))
				throw new NullPointerException("url is not null! pointerId = " + pointerId);
			setUrlRoleMap(_url, roleId);
		}
	}
	
	private void setUrlRoleMap(String _url, String _roleId){
		List<String> _roles = urlRoleMap.get(_url);
		if(_roles == null){
			_roles = new ArrayList<String>(8);
			_roles.add(_roleId);
			urlRoleMap.put(_url, _roles);
		} else if(!_roles.contains(_roleId)){
			_roles.add(_roleId);
		}
	}

	public void setFunctionCacheProvider(FunctionCacheProvider functionCacheProvider) {
		this.functionCacheProvider = functionCacheProvider;
	}
	
	private static final Map<String, Collection<ConfigAttribute>> defaultResourceList 
		= new HashMap<String, Collection<ConfigAttribute>>();
	
	private Map<String, Collection<ConfigAttribute>> getDefaultResourceList(){
		List<String> urls = getDatas();
		if(urls != null){
			Collection<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(2);
			attributes.add(new SecurityConfig(MyUserDetails.ROLE_USER));
			for(String url : urls){
				defaultResourceList.put(url, attributes);
			}
		}
		return defaultResourceList;
	}
	
	/**
	 * 添加登录用户都能够访问的资源
	 * @return
	 */
	private List<String> getDatas(){
		List<String> datas = new ArrayList<String>();
		datas.add("/index.do");
		/* 以permit开头的url不受拦截，登录用户都可访问 */
		datas.add("/permit/**");
		return datas;
	}
}
