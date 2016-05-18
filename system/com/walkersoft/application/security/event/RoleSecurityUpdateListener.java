package com.walkersoft.application.security.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;

import com.walker.web.security.MySecurityMetadataSource;

/**
 * 角色对应权限更新监听器，在角色权限修改后，会触发该事件来响应。
 * @author shikeying
 *
 */
public class RoleSecurityUpdateListener implements ApplicationListener<RoleSecurityChangeEvent> {

	private Log logger = LogFactory.getLog(getClass());
	
	private MySecurityMetadataSource securityMetaSource;
	
	public void setSecurityMetaSource(MySecurityMetadataSource securityMetaSource) {
		assert (securityMetaSource != null);
		this.securityMetaSource = securityMetaSource;
	}

	@Override
	public void onApplicationEvent(RoleSecurityChangeEvent event) {
		// TODO Auto-generated method stub
		securityMetaSource.reloadResource();
		logger.info("系统重新加载了角色权限数据。");
	}

}
