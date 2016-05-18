package com.walker.test;

import java.util.Collection;

/**
 * 缓存树节点对象定义
 * @author shikeying
 *
 */
public interface CacheTreeNode {

	String getKey();
	
	String getText();
	
	String getParentId();
	
	/**
	 * 返回原始业务数据，如果没有保留原始业务数据，返回<code>null</code>
	 * @return
	 */
	Object getSource();
	
	boolean hasChild();
	
	int getChildrenSize();
	
	Collection<CacheTreeNode> getChildren();
	
	void addChild(CacheTreeNode node);
	
	/**
	 * 根据给定的key值，找出当前节点下面，子节点中的节点。
	 * @param key 要查找的节点key
	 * @return
	 */
	CacheTreeNode search(String key);
}
