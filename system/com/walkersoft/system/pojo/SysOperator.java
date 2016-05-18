package com.walkersoft.system.pojo;

/**
 * 系统操作员接口，目前权限分配功能中用到，具体用户实现由业务完成
 * @author MikeShi
 *
 */
public interface SysOperator {

	/**
	 * 返回用户主键
	 * @return
	 */
	public String getUserId();
	
	/**
	 * 返回用户名称
	 * @return
	 */
	public String getUserName();
	
	/**
	 * 返回用户分组，用于分组展示用户
	 * @return
	 */
	public String getGroup();
	
	/**
	 * 是否启用
	 * @return
	 */
	public boolean enabled();
}
