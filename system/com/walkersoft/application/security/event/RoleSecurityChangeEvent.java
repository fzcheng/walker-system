package com.walkersoft.application.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * 系统角色对应权限改变事件定义，需要通知权限拦截模块，更新权限数据。
 * @author shikeying
 *
 */
public class RoleSecurityChangeEvent extends ApplicationEvent {

	public RoleSecurityChangeEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2695104485536782361L;

}
