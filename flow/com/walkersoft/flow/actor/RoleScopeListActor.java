package com.walkersoft.flow.actor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.flow.core.actor.AbstractBaseActor;
import com.walker.flow.core.actor.SelectScopeListActor;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.flow.core.actor.node.DefaultItemNode;
import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.manager.RoleManagerImpl;

/**
 * 选择系统角色列表，来指定参与者模型实现。<br>
 * 用户可配置从系统角色结合中选择一个角色，存储到<code>actor_assign</code>表中。
 * @author shikeying
 *
 */
public class RoleScopeListActor extends AbstractBaseActor implements
		SelectScopeListActor {

	@Autowired
	private RoleManagerImpl roleManager;
	
	private UserCoreCacheProvider userCache = null;
	
	@Override
	public List<AbstractActorNode> getDefineNodeList() {
//		if(userCache == null){
//			roleManager = MyApplicationConfig.getBeanObject(RoleManagerImpl.class);
//			userCache = (UserCoreCacheProvider)SimpleCacheManager.getCacheProvider(UserCoreEntity.class);
//		}
		doInitUserCache();
		List<RoleEntity> roles = roleManager.queryAllRoleList();
		if(roles != null && roles.size() > 0){
			List<AbstractActorNode> result = new ArrayList<AbstractActorNode>();
			for(RoleEntity entity : roles){
				result.add(new DefaultItemNode(entity.getId(), entity.getName()));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<AbstractActorNode> getExcuteNodeList(String roleId) {
		doInitUserCache();
		List<String> userIds = roleManager.queryRoleUsers(roleId);
		if(userIds == null || userIds.size() == 0)
			return null;
		List<AbstractActorNode> actorList = new ArrayList<AbstractActorNode>();
		for(String userId : userIds){
			actorList.add(new DefaultItemNode(userId, userCache.getUserName(userId)));
		}
		return actorList;
	}

	public void setRoleManager(RoleManagerImpl roleManager) {
		this.roleManager = roleManager;
	}

	public void setUserCache(UserCoreCacheProvider userCache) {
		this.userCache = userCache;
	}

	private void doInitUserCache(){
		if(userCache == null){
			userCache = (UserCoreCacheProvider)SimpleCacheManager.getCacheProvider(UserCoreEntity.class);
			roleManager = MyApplicationConfig.getBeanObject(RoleManagerImpl.class);
		}
	}
}
