package com.walker.test;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

/**
 * 角色与资源加载器定义</p>
 * 它主要实现从业务系统中加载系统可用的链接地址与角色的对应关系。</br>
 * 注意：这些URL通常是应该被保护的，例如：功能URL、按钮URL等。
 * @author shikeying
 *
 */
public interface ResourceLoadProvider {

	/**
	 * 加载资源URL与角色对应数据
	 * @return key=url, value=角色集合，ConfigAttribute是spring security定义的对象。
	 */
	Map<String, Collection<ConfigAttribute>> loadResource();
}
