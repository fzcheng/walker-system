package com.walker.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.walker.infrastructure.cache.AbstractCacheProvider;
import com.walker.infrastructure.cache.support.Cachable;
import com.walker.infrastructure.cache.support.Cache;
import com.walker.infrastructure.utils.StringUtils;

public abstract class AbstractCacheTreeProvider<T> extends AbstractCacheProvider<T>
		implements CacheTree<T> {

	public static final String SUPER_ROOT_KEY = "cacheTree_super_root_key";
		
	@Override
	public List<String> getRootKeys() {
		// TODO Auto-generated method stub
		CacheTreeNode node = (CacheTreeNode)this.getCacheData(SUPER_ROOT_KEY);
		if(node != null){
			Collection<CacheTreeNode> rootList = node.getChildren();
			List<String> result = new ArrayList<String>(rootList.size());
			if(rootList != null){
				for(Iterator<CacheTreeNode> i = rootList.iterator(); i.hasNext();)
					result.add(i.next().getKey());
				return result;
			}
		}
		return null;
	}

	@Override
	public Collection<CacheTreeNode> getRootList() {
		// TODO Auto-generated method stub
		CacheTreeNode node = (CacheTreeNode)this.getCacheData(SUPER_ROOT_KEY);
		if(node != null){
			return node.getChildren();
		}
		return null;
	}

	@Override
	public CacheTreeNode getOneRootNode(String key) {
		// TODO Auto-generated method stub
		return searchTreeNode(key);
	}

	@Override
	public CacheTreeNode searchTreeNode(String key) {
		// TODO Auto-generated method stub
		assert (StringUtils.isNotEmpty(key));
		CacheTreeNode node = (CacheTreeNode)this.getCacheData(SUPER_ROOT_KEY);
		if(node != null){
			return node.search(key);
		}
		return null;
	}

	@Override
	protected int loadDataToCache(Cache cache) {
		// TODO Auto-generated method stub
		try{
			Map<String, CacheTreeNode> rootMap = loadRootList();
			if(rootMap == null || rootMap.size() == 0){
				logger.info("no root cache loaded in '" + this.getProviderName() + "'.");
				return 0;
			}
			
			/* 加载其他子节点集合，这些不是树结构，都是平级的子对象，需要组装成树 */
			Map<String, CacheTreeNode> childMap = loadChildList();
			addListToCache(rootMap.values().iterator(), cache);
			
			if(childMap != null && childMap.size() > 0){
				mountTree(childMap, cache);
			}
			
			/* 把之前多个根节点删除，合并为一个超根节点 */
			CacheTreeNode superRoot = new DefaultCacheTreeNode(SUPER_ROOT_KEY, "superRoot", null, null);
			for(Iterator<Cachable> i = cache.getIterator(); i.hasNext();){
				superRoot.addChild((CacheTreeNode)(i.next().getValue()));
			}
			cache.clear();
			cache.put(SUPER_ROOT_KEY, superRoot);
			
			int total = rootMap.size();
			
			/* 把所有加载过的业务数据以列表平铺的形式放入缓存，可以分别使用 */
			addListToCache(rootMap.values().iterator(), cache);
			if(childMap != null){
				addListToCache(childMap.values().iterator(), cache);
				total += childMap.size();
			}
			
			return total;
			
		} catch(Exception ex){
			throw new Error("failed to loading user cache:" + this.getProviderName(), ex);
		}
	}
	
	private void mountTree(Map<String, CacheTreeNode> childMap, Cache cache){
		CacheTreeNode _node = null;
		for(Iterator<CacheTreeNode> i = childMap.values().iterator(); i.hasNext();){
			_node = i.next();
			mountMiddleNode(_node, childMap, cache);
		}
	}
	
	private void mountMiddleNode(CacheTreeNode currentNode
			, Map<String, CacheTreeNode> childMap, Cache cache){
//		logger.debug("-------------> cache = " + cache);
//		logger.debug("-------------> currentNode = " + currentNode);
		CacheTreeNode _parentNode = (CacheTreeNode)cache.get(currentNode.getParentId());
		if(_parentNode == null){
			// 没有找到上级根节点，说明是比较靠下的子节点
			_parentNode = childMap.get(currentNode.getParentId());
			if(_parentNode == null)
				throw new NullPointerException("parent node not found, current: " + currentNode);
			mountMiddleNode(_parentNode, childMap, cache);
		} else if(_parentNode.search(currentNode.getKey()) == null) {
			logger.debug("找到了挂在root下的子节点：" + _parentNode);
			_parentNode.addChild(currentNode);
		}
	}
	
	private void addListToCache(Iterator<CacheTreeNode> iterator, Cache cache){
		for(Iterator<CacheTreeNode> i = iterator; i.hasNext();)
			addObjectToCache(i.next(), cache);
	}
	private void addObjectToCache(CacheTreeNode node, Cache cache){
		assert (node != null && node.getSource() != null);
		cache.put(node.getKey(), node);
	}

	/**
	 * 从业务中加载根节点，即：第一级节点所有数据。</p>
	 * 注意：在树结构中，没有默认的根节点，第一级节点会存在多个，</br>
	 * 也可以认为存在多个根节点。
	 * @return
	 */
	protected abstract Map<String, CacheTreeNode> loadRootList();
	
	/**
	 * 从业务中加载处了第一级节点之外的所有其他节点数据。</p>
	 * 加载的方式由子类实施各种策略，如：大批量的分段加载等。</br>
	 * 这些集合中的对象不是树结构，只要返回包装为<code>CacheTreeNode</code>类型对象即可。</br>
	 * 由系统负责对树的组装。
	 * @return
	 */
	protected abstract Map<String, CacheTreeNode> loadChildList();
	
	protected abstract CacheTreeNode toCacheTreeNode(T entity);
}
