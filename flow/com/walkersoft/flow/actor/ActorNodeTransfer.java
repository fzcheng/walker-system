package com.walkersoft.flow.actor;

import com.walker.flow.core.actor.node.DefaultFolderNode;
import com.walker.flow.core.actor.node.DefaultItemNode;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walkersoft.system.entity.UserCoreEntity;

public final class ActorNodeTransfer {

	/**
	 * 把机构的缓存对象，转换成工作流支持的参与者节点对象，包括：子节点对象。
	 * @param cacheTreeNode 机构缓存对象
	 * @return 工作流节点对象
	 */
	public static final DefaultFolderNode transferToFolderNode(CacheTreeNode cacheTreeNode){
		if(cacheTreeNode == null) return null;
		DefaultFolderNode dfn = new DefaultFolderNode(cacheTreeNode.getKey(), cacheTreeNode.getText());
		if(cacheTreeNode.hasChild()){
			for(CacheTreeNode ctn : cacheTreeNode.getChildren()){
				dfn.addNode(transferToFolderNode(ctn));
			}
		}
		return dfn;
	}
	
	/**
	 * 把用户实体对象转换成 工作流参与者节点对象
	 * @param entity
	 * @return
	 */
	public static final DefaultItemNode transferToItemNode(UserCoreEntity entity){
		return new DefaultItemNode(entity.getId(), entity.getName());
	}
}
