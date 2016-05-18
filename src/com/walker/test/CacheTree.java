package com.walker.test;

import java.util.Collection;
import java.util.List;

import com.walker.infrastructure.cache.CacheProvider;

/**
 * 缓存树结构定义，支持数据以树形结构存储。
 * @author shikeying
 *
 */
public interface CacheTree<T> extends CacheProvider<T> {

	/**
	 * 返回第一级树下的索引集合，即：根下面的子节点的索引集合。
	 * @return
	 */
	List<String> getRootKeys();
	
	Collection<CacheTreeNode> getRootList();
	
	/**
	 * 返回根节点下第一级中的某个节点对象
	 * @param key
	 * @return
	 */
	CacheTreeNode getOneRootNode(String key);
	
	CacheTreeNode searchTreeNode(String key);
}
