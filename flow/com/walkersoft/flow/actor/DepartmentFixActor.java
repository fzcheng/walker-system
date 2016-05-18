package com.walkersoft.flow.actor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.flow.core.actor.AbstractBaseActor;
import com.walker.flow.core.actor.SelectScopeAndFixActor;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.cache.DepartmentCacheProvider;
import com.walkersoft.system.dao.UserDao;
import com.walkersoft.system.entity.DepartmentEntity;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 通过系统机构来选择固定参与者模型实现
 * @author shikeying
 * @date 2014-8-2
 *
 */
public class DepartmentFixActor extends AbstractBaseActor implements
		SelectScopeAndFixActor {

	private DepartmentCacheProvider departmentCache = null;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<AbstractActorNode> getDefineChildrenNodeList(String deptId) {
		// TODO Auto-generated method stub
		assert (StringUtils.isNotEmpty(deptId));
		DepartmentEntity depart = departmentCache.getCacheData(deptId);
		List<UserCoreEntity> userList = null;
		if(userDao == null)
			userDao = MyApplicationConfig.getBeanObject(UserDao.class);
		if(depart.isOrg()){
			userList = userDao.getOrgUserList(deptId);
		} else {
			userList = userDao.getDepartmentUserList(deptId);
		}
		if(userList != null){
			List<AbstractActorNode> result = new ArrayList<AbstractActorNode>();
			for(UserCoreEntity u : userList){
				result.add(ActorNodeTransfer.transferToItemNode(u));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<AbstractActorNode> getDefineRootNodeList() {
		// TODO Auto-generated method stub
		if(departmentCache == null){
			departmentCache = (DepartmentCacheProvider)SimpleCacheManager.getCacheTreeProvider(DepartmentEntity.class);
		}
		List<CacheTreeNode> rootList = departmentCache.getAvailableRootList();
		if(rootList == null) return null;
		List<AbstractActorNode> resultList = new ArrayList<AbstractActorNode>();
		for(CacheTreeNode ctn : rootList){
			resultList.add(ActorNodeTransfer.transferToFolderNode(ctn));
		}
		return resultList;
	}

}
