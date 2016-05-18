package com.walkersoft.flow.actor;

import java.util.ArrayList;
import java.util.List;

import com.walker.flow.core.actor.AbstractBaseActor;
import com.walker.flow.core.actor.SelectScopeTreeActor;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.flow.core.actor.node.ActorItemDefinition;
import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walkersoft.application.cache.DepartmentCacheProvider;
import com.walkersoft.system.entity.DepartmentEntity;

/**
 * 从系统组织机构中选择一个参与者范围
 * @author shikeying
 * @date 2014-8-1
 *
 */
public class DepartmentScopeTreeActor extends AbstractBaseActor implements SelectScopeTreeActor {

//	private DepartmentCacheProvider departmentCache;
	private DepartmentCacheProvider departmentCache = null;
	
//	public void setDepartmentCache(DepartmentCacheProvider departmentCache) {
//		this.departmentCache = departmentCache;
//	}

	@Override
	public List<AbstractActorNode> getDefineChildrenNodeList(String arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<AbstractActorNode> getDefineRootNodeList() {
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

	/**
	 * 用户选择一个机构，显示其下面包含的用户集合
	 */
	@Override
	public List<AbstractActorNode> getExcuteChildrenNodeList(String deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 限制，只能选择叶子节点，即：人员
	 */
	@Override
	public int getExcuteSelectTypeLimited() {
		// TODO Auto-generated method stub
		return ActorItemDefinition.NODE_TYPE_ITEM;
	}

}
